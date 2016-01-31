<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String taskNodeId = request.getAttribute("taskNodeId").toString();
String processInstanceID = request.getAttribute("processInstanceID").toString();
String tfId = request.getAttribute("tfId").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>工作流表单配置</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<script>
		  $(function(){
		  	showCheckTab();
			$('#wf_formadd_form_tree_edit').appendTo('body').window({
            	title: "选择表单",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 400,
                height: 300
            });
		  });
        function wfFormconfigUpdate(){
        	var formId = $("#wf_formadd_form_edit_id").val();
        	if(formId==null||formId==''){
        		formId = '${taskFormNodeEntity.formID }';
        	}
        	var taskNodeId = <%=taskNodeId%>;
        	var processInstanceID = <%=processInstanceID%>;
        	var tfId = "<%=tfId%>";
        	var isShowTab = document.getElementById("showTab1").value;
        	var sortIndex = document.getElementById("sortIndex1").value;
        	$.post("workflow/WorkFlowFrame_updateForm.action",{"formId":formId,"workflowId":processInstanceID,"nodeId":taskNodeId,"isShowTab":isShowTab,"sortIndex":sortIndex,"tfId":tfId},
				function(data){
					if("success" == data){
						$.messager.alert("提示","修改成功！", 'info');
						$('#wf_formconfig_edit_form').window('close');
						$('#wf_tabconfig_workflowlist').datagrid('reload');
						//保存到XML
						$.post("form/form!workFlowFormConfigSave.action",{"formId":formId,"workflowId":processInstanceID},
							function(data){
								if("success" == data){
								}
							}
						);
					}
				}
			);
        }
        function checkTab(par){
        	document.getElementById("showTab1").value = par;
        }
        function showCheckTab(){
        	if(${taskFormNodeEntity.isShowTab=='1' }){
        		document.getElementById("showTab1").value = 1;
        		document.getElementById("isShowTab11").checked = "checked";
        	}else{
        		document.getElementById("showTab1").value = 0;
        		document.getElementById("isShowTab22").checked = "checked";
        	}
        }
        function selectformedit(){
        	$("#wf_formadd_form_tree_edit").window({'href':''});		
        	$("#wf_formadd_form_tree_edit").window('refresh');
        	$("#wf_formadd_form_tree_edit").window({'href':'pages/workflow/workflow_form_form_edit_tree.jsp?formId=${taskFormNodeEntity.formID}'});		
        	$("#wf_formadd_form_tree_edit").window('open');
        }
    </script>
  </head>  
  <body>
  	<table border="0" cellpadding="5" cellspacing="1" width="100%">
		<br/><br/><tr>
		  <td align="right">表单：</td>					  
		  <td><input type="text" readonly="readonly" id="wf_formadd_form_edit_text" value="${taskFormNodeEntity.formName }"/><input type="button" onclick="selectformedit()" value="选择"><input type="hidden" id="wf_formadd_form_edit_id" />
		  <input type="hidden" id="wf_formadd_form_edit_id" value="${taskFormNodeEntity.formID }"/></td>
		</tr>
		<tr>
		  <td align="right">显示顺序：</td>					  
		  <td align="left"><input id="sortIndex1" type="text" value="${taskFormNodeEntity.sortIndex }"></input></td>
		</tr>
		<tr>
		  <td align="right">是否显示子标签：</td>					  
		  <td align="left">　　<input id="isShowTab11" name="isShowTab" type="radio" value="1" />　是　　
				<input id="isShowTab22" name="isShowTab" type="radio" value="0"/>　否</td>
		</tr>
	</table>
	<div align="center">
		<br/><br/><a class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="wfFormconfigUpdate()">保存</a>	
		<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#wf_formconfig_edit_form').window('close');">关闭</a>				  
	</div>
	<input type="hidden" id="showTab1" value="1"/>
	<div id="wf_formadd_form_tree_edit" class="easyui-window" >
  </body>
</html>
