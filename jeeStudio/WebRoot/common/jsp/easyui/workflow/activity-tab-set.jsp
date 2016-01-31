<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">
   $(function(){
   			//前驱下 标签列表
	       $("#wf_auth_config_table").datagrid({
	       	 	url:'acttab_findActivitySetList.action',
				fitColumns: true,
				fit:true,
	            pagination: true,
	            singleSelect:true,
	            pageSize:20,
				columns:[[
						{field: 'title',title: '标签页标题',width: 20,align:'center'},
	                	{field: 'url',title: '标签页URL',width: 80,sortable: false,align:'center'},
	                	{field: 'sortIndex',title: '标签页序号',width: 80,sortable: false,align:'center'},
	                	{field: 'tabId',title: '操作',align:'center',width: 100,sortable: true,height:20,
	                		formatter:function(value,rec){

							   var links =  '<a href=\"javascript:addActivityConfigTab(\''+rec['tabId']+'\');\" >修改</a>&nbsp;&nbsp;';
							       links = links+ '<a href=\"javascript:deleteActivityConfig(\''+rec['tabId']+'\');\" >删除</a>&nbsp;&nbsp;';
							   return links;
							}
	                	}
				]],
				toolbar:[			
					{
						id:'btnadd',
						text:'添加',
						iconCls:'icon-add',
						handler:function(){
							addActivityConfigTab('-1');
						}
					 }
			    ]
	       });
       
           //流程模板
           $('.wf_auth_template_list').combobox({
				url:'workflow/WorkFlowFrame_comlist.action?ti='+new Date().getTime(),
				valueField:'key',
				textField:'value',
				panelHeight:100,
				onChange:function(newValue, oldValue){
					$('.wf_auth_template_his_list').combobox('setValue','');
				    $('.wf_auth_template_his_list').combobox('reload','workflow/nodeAuthSet!getTemplateHis.action?processInstanceID='+newValue+"&ti="+new Date().getTime());
				}
			});
			
			//版本号选择
			$('.wf_auth_template_his_list').combobox({
				url:'',
				valueField:'modelId',
				textField:'modelName',
				panelHeight:100,
				onChange:function(newValue, oldValue){
				  var processId= $('.wf_auth_template_list').combobox('getValue');
				  var mid= newValue;
				   $('.wf_tab_activity_list').combobox('setValue','');
				   $('.wf_tab_activity_list').combobox('reload','acttab_findActivityIDs.action?processId='+processId+'&mid='+mid+"&ti="+new Date().getTime());
				}
			});
			
			//活动节点选择
			$('.wf_tab_activity_list').combobox({
				url:'',
				valueField:'value',
				textField:'text',
				panelHeight:100,
				onChange:function(newValue, oldValue){
				
				  var processId= $('.wf_auth_template_list').combobox('getValue');
				  var mid= $('.wf_auth_template_his_list').combobox('getValue');
				  var activityId=newValue;
				  
				   $('.wf_tab_Precursor_list').combobox('setValue','');
				   $('.wf_tab_Precursor_list').combobox('reload','acttab_findPrecursorIDs.action?processId='+processId+'&mid='+mid+'&activityId='+activityId+"&ti="+new Date().getTime());
				
				
				}
			});
			
			//前驱状态选择
			$('.wf_tab_Precursor_list').combobox({
				url:'',
				valueField:'value',
				textField:'text',
				panelHeight:100,
				onChange:function(newValue, oldValue){
					refershGrid();
				}
			});
          
   });

</script>
<div class="easyui-panel" fit="true" border="false"  title="工作流状态多标签配置" >
	<div class="easyui-layout" fit="true" border="false">
	    <div region="north" border="false" style="height:40px;padding: 10px 0;margin-left:13px;vertical-align: middle; ">
	       <label style="margin-bottom: 15px;">工作流模板：</label><input class="wf_auth_template_list" style="width:150px;">&nbsp; &nbsp; 
	       <label style="margin-bottom: 15px;">历史版本：  </label><input class="wf_auth_template_his_list"  style="width:150px;">&nbsp; &nbsp; 
	       <label style="margin-bottom: 15px;">活动节点：  </label><input class="wf_tab_activity_list" style="width:150px;">&nbsp; &nbsp; 
	       <label style="margin-bottom: 15px;">前驱状态：  </label><input class="wf_tab_Precursor_list"  style="width:150px;">&nbsp; &nbsp; 
	    </div>
	    
	    <div region="center" border="false">
			<div class="easyui-panel" fit="true" border="false">
			  <table id="wf_auth_config_table"></table>
			</div>
	    </div>
	</div>
</div>

<!-- 前驱标签配置修改 -->
<div id="wf_tabConfig_activityPerm" border="false"></div>
<script>
	function initTabConfigActivityPermWindow(windowId){
		 //前驱多标签配置
	        $('#'+windowId).window({
	        	title: "工作流前驱标签配置",
	            modal: true,
	            resizable: false,
	            minimizable: false,
	            border:false,
	            maximizable: false,
	            shadow: false,
	            closed: true,
	            width: 450,
	            height: 250,
	            onClose:function(){
	            	easyuiWinClose_workflow(windowId);
	            }
	        });
	}
	function addActivityConfigTab(id){
		var url='acttab_loadActivityConfig.action?id='+id+'&date='+new Date().getTime();
	
		initTabConfigActivityPermWindow('wf_tabConfig_activityPerm');
		$('#wf_tabConfig_activityPerm').window({href:url});
		$('#wf_tabConfig_activityPerm').window('open');
	}
	function saveActiviConfigTab(){

		$('#activityTabConfig').form('submit',{ 
			url:'acttab_saveActivityConfig.action', 
			onSubmit:function(){ 
		
			   if(document.getElementById('mid')){
			   		document.getElementById('mid').value=$('.wf_auth_template_his_list').combobox('getValue');
			   }
			   if(document.getElementById('processId')){
			   		document.getElementById('processId').value=$('.wf_auth_template_list').combobox('getValue');
			   }
			   if(document.getElementById('activityId')){
			   		document.getElementById('activityId').value=$('.wf_tab_activity_list').combobox('getValue');
			   }
			   if(document.getElementById('toActivityId')){
			   		document.getElementById('toActivityId').value=$('.wf_tab_Precursor_list').combobox('getValue');
			   }
		
				return $(this).form('validate'); 
			}, 
			success:function(data){ 
					$.messager.alert("提示", '保存成功！');
					$('#wf_tabConfig_activityPerm').window('close');
					refershGrid();
			} 
		}); 		
	}
	
	function deleteActivityConfig(id){
	var url='acttab_deleteActivityConfig.action?id='+id+'&date='+new Date();
		$.messager.confirm('删除确认框', '您确认删除该条记录？', function(r){
				if (r){
					$.ajax({
				    url: url,
				    type: 'post',
					    success: function(data){
								$.messager.alert("提示", '删除成功！');
								refershGrid();
								
				 		}
					});	
				}
		});
	}
	
	function refershGrid(){
			var precursorId=$('.wf_tab_Precursor_list').combobox('getValue')+'';
			var mid=$('.wf_auth_template_his_list').combobox('getValue')+'';
			$("#wf_auth_config_table").datagrid('load',{precursorId:precursorId,mid:mid});
	}
</script>
