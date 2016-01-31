<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">
   $(function(){
       $("#wf_auth_config_table").datagrid({
      		url: 'workflow/WorkFlowFrame_nodelistOfProcess.action',
			fitColumns: true,
			fit:true,
            pagination: true,
            singleSelect:true,
            pageSize:20,
			columns:[[
					{field: 'activityDefId',title: '节点ID',width: 20,align:'center'},
                	{field: 'name',title: '节点名称',width: 80,sortable: false,align:'center'},
                	{field: 'isShowTab_',title: '节点配置',align:'center',width: 100,sortable: true,height:20,
                		formatter:function(value,rec){
						   var links =  '<a href=\"javascript:configNodePerm(\''+rec['activityDefId']+'\');\" >节点配置</a>&nbsp;&nbsp;';
						   return links;
						}
                	}
			]]
       });
       
       //
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
				   $("#wf_auth_config_table").datagrid('load',{processInstanceID:$('.wf_auth_template_list').combobox('getValue'),ModelID:newValue});
				}
			});
 			//节点配置
          $('#wf_formconfig_nodeperm').window({
          	title: "工作流节点权限配置",
              modal: true,
              resizable: false,
              minimizable: false,
              border:false,
              maximizable: false,
              shadow: false,
              closed: true,
              width: 600,
              height: 400,
              onClose:function(){
            	  easyuiWinClose_workflow('wf_formconfig_nodeperm');
              }
          });
          
   });
	/**
	 * 节点配置界面
	 */
	function configNodePerm(nodeId){
		$("#wf_formconfig_nodeperm").window({'href':'pages/workflow/workflow-form-ui.jsp?nodeid='+nodeId+'&modelId='
			+$('.wf_auth_template_his_list').combobox('getValue')+'&processInstanceID='+$('.wf_auth_template_list').combobox('getValue')});				
		$("#wf_formconfig_nodeperm").window('open'); 
	}
</script>
<div class="easyui-panel" fit="true" border="false"  title="工作流权限配置" >
	<div class="easyui-layout" fit="true" border="false">
	    <div region="north" border="false" style="height:40px;padding: 10px 0;margin-left:13px;vertical-align: middle; ">
	       <label style="margin-bottom: 15px;">工作流模板：</label><input class="wf_auth_template_list" style="width:150px;">
	       &nbsp; &nbsp; <label style="margin-bottom: 15px;">历史版本：</label><input class="wf_auth_template_his_list"  style="width:150px;">
	    </div>
	    <div region="center" border="false">
			<div class="easyui-panel" fit="true" border="false">
			  <table id="wf_auth_config_table"></table>
			</div>
	    </div>
	</div>
</div>

<div id="wf_formconfig_nodeperm" border="false"></div>