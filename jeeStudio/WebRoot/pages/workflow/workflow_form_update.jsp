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
           
           
           	$("#wf_formconfigupdate_workflow").combobox({
				valueField:'id',
   				textField:'text',  
            	listWidth:180,
            	editable:false,
            	url:'workflow/ModelListWorkFlow!getWorkflowModelList.action?random='+parseInt(10*Math.random()),
            	onChange:function(newValue,oldValue){		
            		if(newValue != '请选择'){					
				    	
				    	$("#wf_formconfigupdate_workflownode").combobox('reload',"workflow/ModelListWorkFlow!getWorkflowModelNodeList.action?workflowModelId="+newValue);
				    	//$.post("workflow/ModelListWorkFlow!getWorkflowModelNodeList.action",{"workflowModelId":newValue},
						//	function(data){
						//		$("#wf_formconfigupdate_workflownode").combobox('loadData',eval('(' + data + ')'));
								$("#wf_formconfigupdate_workflownode").combobox('setValue',"请选择");
						//	}
						//);
					}
					
					//joinObjColSelect.combobox("setValue","");
				}
			});	
           	$("#wf_formconfigupdate_workflownode").combobox({
				valueField:'id',
   				textField:'text',  
            	listWidth:180,
            	editable:false,
            	url:'workflow/ModelListWorkFlow!getWorkflowModelNodeList.action?workflowModelId=${processInstanceID}&random='+parseInt(10*Math.random())
			});	
			$("#wf_formconfigupdate_form").combobox({
				valueField:'id',
   				textField:'text',  
            	listWidth:180,
            	editable:false,
			    url:'form/form!getTabsPageList.action?mainObjectId=&pageType=editPage&random='+parseInt(10*Math.random())
			});	
			$("#wf_formconfigupdate_workflow").combobox('setValue',"${processInstanceID}");
			$("#wf_formconfigupdate_form").combobox('setValue',"${formId}");
			$("#wf_formconfigupdate_workflownode").combobox('setValue',"${nodeId}");
        });      
        
        function wfFormconfigUpdateSave(){
        	var formId = $("#wf_formconfigupdate_form").combobox("getValue");
        	var workflowId = $("#wf_formconfigupdate_workflow").combobox("getValue");
        	var nodeId = $("#wf_formconfigupdate_workflownode").combobox("getValue");
        	if(formId != '请选择' && workflowId != '请选择' && nodeId != '请选择'){
	        	$.post("workflow/WorkFlowFrame_update.action",{"taskFormId":"${taskFormId}","formId":formId,"workflowId":workflowId,"nodeId":nodeId},
					function(data){
						if("success" == data){
							$.messager.alert("提示","保存成功！", 'info');
							$('#wf_formconfig_update').window('close');
							$('#wf_formconfig_workflowlist').datagrid('reload');
							//保存到XML
							$.post("form/form!workFlowFormConfigSave.action",{"formId":formId,"workflowId":workflowId},
								function(data){
									if("success" == data){
									}
								}
							);
						}
					}
				);
			}
        }
        function wfFormconfigupdateTypeClick(obj){
        	$("#wf_formconfigupdate_form").combobox('reload','form/form!getTabsPageList.action?mainObjectId=&pageType='+obj.value+'&random='+parseInt(10*Math.random()));
        	$("#wf_formconfigupdate_form").combobox('setValue','请选择');
        }
    </script>
  </head>  
  <body>
  	<table border="0" cellpadding="5" cellspacing="1" width="100%">
		<tr>
		  <td width="30%" align="right">工作流：</td>					  
		  <td align="left"><select id="wf_formconfigupdate_workflow" style="width:175px;" required="true"><option>请选择</option></select>&nbsp;&nbsp;<font size="2" color="red">*</font></td>
		</tr>
		<tr>
		  <td align="right">节点：</td>					  
		  <td align="left"><select id="wf_formconfigupdate_workflownode" style="width:175px;" required="true"><option>请选择</option></select>&nbsp;&nbsp;<font size="2" color="red">*</font></td>
		</tr>
		<tr>
		  <td align="right">表单类型：</td>					  
		  <td align="left"><input name="wf_formconfigupdate_type" type="radio" value="editPage" onclick="wfFormconfigupdateTypeClick(this);"/>编辑&nbsp;&nbsp;&nbsp;
				<input name="wf_formconfigupdate_type" type="radio" value="viewPage" onclick="wfFormconfigupdateTypeClick(this);"/>查看</td>
		</tr>
		<tr>
		  <td align="right">表单：</td>					  
		  <td align="left"><select id="wf_formconfigupdate_form" style="width:175px;"><option>请选择</option></select></td>
		</tr>
	</table>
	<div style="float:left;padding:30px 0 0 180px;">
		<a class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="wfFormconfigUpdateSave()">保存</a>	
		<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#wf_formconfig_update').window('close');">关闭</a>				  
	</div>
  </body>
</html>
