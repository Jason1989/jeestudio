<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>工作流表单配置</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<script>
		$(function(){
			var wfData = {"total":4,"rows":[
				{"wfid":"1","wfModelName":"请假","wfNodeName":"请假申请","formName":"请假单"},
				{"wfid":"2","wfModelName":"请假","wfNodeName":"审批","formName":"请假单"},
				{"wfid":"3","wfModelName":"报销","wfNodeName":"报销申请","formName":"报销单"},
				{"wfid":"4","wfModelName":"报销","wfNodeName":"报销审批","formName":"报销单"}
				
			]}
			/**
           	$("#wf_formconfig_form").combobox({
				valueField:'id',
   				textField:'text',  
            	listWidth:157,
            	editable:false,
			    url:'form/form!getTabsPageList.action?mainObjectId=&pageType=editPage&random='+parseInt(10*Math.random())
			});	
           	$("#wf_formconfig_workflow").combobox({
				valueField:'id',
   				textField:'text',  
            	listWidth:157,
            	editable:false,
            	url:'workflow/ModelListWorkFlow!getWorkflowModelList.action?random='+parseInt(10*Math.random()),
            	onChange:function(newValue,oldValue){		
            		if(newValue != '请选择'){					
				    	$.post("workflow/ModelListWorkFlow!getWorkflowModelNodeList.action",{"workflowModelId":newValue},
							function(data){
								$("#wf_formconfig_workflownode").combobox('loadData',eval('(' + data + ')'));
							}
						);
					}
					
					//joinObjColSelect.combobox("setValue","");
				}
			});	
           	$("#wf_formconfig_workflownode").combobox({
				valueField:'id',
   				textField:'text',  
            	listWidth:157,
            	editable:false
			});	*/
           	$('#wf_formconfig_workflowlist').datagrid({
                iconCls: 'icon-save',
                //width:1000,
                height: 450,
                nowrap: false,
                striped: true,
                autoFit: true,                
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'taskFormID',
                url: 'workflow/WorkFlowFrame_list.action',
                pageSize:10,                
                fitColumns:true,
                remoteSort:false,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'processInstanceName',title: '工作流模板名称',width: 200,sortable: true},
                	{field: 'taskNodeName',title: '流程ID',width: 200,sortable: true},                	
                	{field: 'formName',title: '表单',width: 150,align:'center',sortable: true},
                	{field: 'operate',title: '操作',width: 120,align:'center',
	                    formatter:function(value,rec){
						    var links = '<a href="javascript:;" onclick=wfFormconfigImage("' + rec.taskFormID + '")>查看流程图</a>';
						    //var links = '<a href="javascript:;" onclick=wfFormconfigUpdate("' + rec.taskFormID + '")>修改</a>&nbsp;|&nbsp;';
						    //links += '<a href="javascript:;" onclick=wfFormconfigDelete("' + rec.taskFormID + '")>删除</a>';
							return links;
						}
					}
				]],
                pagination: true,
                rownumbers: true,
                //singleSelect: true,
                toolbar: ['-', 
		  			{text: '添加',iconCls: 'icon-add',handler: wfFormconfigAdd},
		  			//'-',{text: '待办列表',iconCls: 'icon-ok',handler: wfFormconfigToDoTask},
		  			'-' ]
            });
        	//$('#wf_formconfig_workflowlist').datagrid("loadData",wfData);
        	
        	$('#wf_formconfig_add').appendTo('body').window({
                title: "添加",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 500,
                height: 400
            });
        	$('#wf_formconfig_update').appendTo('body').window({
                title: "修改",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 500,
                height: 400
            });
            $('#wf_formconfig_edit').appendTo('body').window({
            	title: "表单配置",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 800,
                height: 500
            });
            $('#wf_formconfig_Imag').appendTo('body').window({
            	title: "流程图",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 700,
                height: 400
            });
        });  
        
        function wfFormconfigEdit(processInstanceID,taskFormID){
        	$("#wf_formconfig_edit").window({'href':''});		
           	$("#wf_formconfig_edit").window('refresh');
           	$("#wf_formconfig_edit").window({'href':'workflow/WorkFlowFrame_toEdit.action?random='+parseInt(10*Math.random())+'&processInstanceID='+processInstanceID+'&taskFormID='+taskFormID});		
           	$("#wf_formconfig_edit").window('open');
        }
        function wfFormconfigAdd(){
        	$("#wf_formconfig_add").window({'href':''});		
           	$("#wf_formconfig_add").window('refresh');
           	$("#wf_formconfig_add").window({'href':'workflow/WorkFlowFrame_toAdd.action?random='+parseInt(10*Math.random())});		
           	$("#wf_formconfig_add").window('open');
           	
        }     
        function wfFormconfigUpdate(taskFormID){
        	$("#wf_formconfig_update").window({'href':''});		
           	$("#wf_formconfig_update").window('refresh');
           	$("#wf_formconfig_update").window({'href':'workflow/WorkFlowFrame_toUpdate.action?taskFormID='+taskFormID+'&random='+parseInt(10*Math.random())});		
           	$("#wf_formconfig_update").window('open');
           	
        }     
        function wfFormconfigDelete(taskFormID){
        	if(taskFormID && taskFormID != ""){
        		$.messager.confirm('提示', '确认删除吗？',function(a){
	         		if(a)$.post("workflow/WorkFlowFrame_delete.action",{"taskFormID":taskFormID},wfFormconfigDeleteCallback);
	         	});
        	}else{
         		
            }
        }
        function wfFormconfigDeleteCallback(data){
        	if("success" == data){
   				$.messager.alert('提示','删除成功','info');			         				
   				$('#wf_formconfig_workflowlist').datagrid('reload');
   			}
        }
         function wfFormconfigImage(taskFormID){
        	$("#wf_formconfig_Imag").window({'href':''});		
           	$("#wf_formconfig_Imag").window('refresh');
           	$("#wf_formconfig_Imag").window({'href':'pages/workflow/workflow_form_Imag.jsp?isShowButton=1&taskFormID='+taskFormID+'&random='+parseInt(10*Math.random())});		
           	$("#wf_formconfig_Imag").window('open');
        }
    </script>
  </head>  
  <body>
  	<div id="wf_formconfg" class="easyui-tabs" border="false" fit="true">
	  	<div title="工作流表单配置" fit="true" >
			<!-- <table border="0" cellpadding="5" cellspacing="1" width="100%">
				<tr>
				  <td width="10%" align="right">工作流</td>					  
				  <td width="18%" align="left"><select id="wf_formconfig_workflow" style="width:155px;" required="true"><option>请选择</option></select>&nbsp;&nbsp;<font size="2" color="red">*</font></td>
				  <td width="10%" align="right">节点</td>					  
				  <td width="18%" align="left"><select id="wf_formconfig_workflownode" style="width:155px;" required="true"><option>请选择</option></select>&nbsp;&nbsp;<font size="2" color="red">*</font></td>
				  <td width="10%" align="right">表单</td>
				  <td align="left"><select id="wf_formconfig_form" style="width:155px;"><option>请选择</option></select></td>
				</tr>
				<tr>
				  <td width="20%" align="center" colspan="6"><a class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="wfFormconfigSave()">保存</a></td>					  
				</tr>
			</table> -->
			<table id="wf_formconfig_workflowlist"></table> 
		</div>
	</div>	
	<div id="wf_formconfig_add" class="easyui-window" ></div>      	
	<div id="wf_formconfig_update" class="easyui-window" ></div>      	
	<div id="wf_formconfig_edit" class="easyui-window" >   
    </div>
    <div id="wf_formconfig_Imag" class="easyui-window" >      	
    </div>
  </body>
</html>
