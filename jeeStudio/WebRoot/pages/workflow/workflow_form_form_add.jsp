<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String taskNodeId = request.getAttribute("taskNodeId").toString();
String processInstanceID = request.getAttribute("processInstanceID").toString();
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
			$('#wf_formadd_form_tree').appendTo('body').window({
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
        function wfFormAdd(){
        	var formId = $("#wf_formadd_form_id").val();
        	var taskNodeId = <%=taskNodeId%>;
        	var processInstanceID = <%=processInstanceID%>;
        	var isShowTab = document.getElementById("showTab").value;
        	var sortIndex = document.getElementById("sortIndex").value;
        	$.post("workflow/WorkFlowFrame_addForm.action",{"formId":formId,"workflowId":processInstanceID,"nodeId":taskNodeId,"isShowTab":isShowTab,"sortIndex":sortIndex},
				function(data){
					if("success" == data){
						$('#wf_formconfig_edit_add').window('close');
						$('#wf_tabconfig_workflowlist').datagrid('reload');
						$.messager.alert("提示","保存成功！", 'info');
						search_tempform();
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
        	document.getElementById("showTab").value = par;
        }
        function selectform(){
        	$("#wf_formadd_form_tree").window({'href':''});		
        	$("#wf_formadd_form_tree").window('refresh');
        	$("#wf_formadd_form_tree").window({'href':'pages/workflow/workflow_form_form_tree.jsp'});		
        	$("#wf_formadd_form_tree").window('open');
        }
    </script>
  </head>  
  <body>
  	<br/><br/><table border="0" cellpadding="5" cellspacing="1" width="100%">
		<tr>
		  <td align="right">表单：</td>					  
		  <!-- <td align="left"><input class="easyui-combotree" id="wf_formadd_form" url="workflow/WorkFlowFrame_tabPageList.action" onlyLeafCheck="true" style="width:175px;"/></td> -->
		  <td><input type="text" id="wf_formadd_form_text" readonly /><input type="button" onclick="selectform()" value="选择"><input type="hidden" id="wf_formadd_form_id" /></td>
		</tr>
		<tr>
		  <td align="right">显示顺序：</td>					  
		  <td align="left"><input id="sortIndex" type="text"></input></td>
		</tr>
		<tr>
		  <td align="right">是否显示子标签：</td>					  
		  <td align="left"><input id="isShowTab1" name="isShowTab" type="radio" value="1" onclick="checkTab(1)"/>　是　
				<input id="isShowTab2" name="isShowTab" type="radio" value="0" onclick="checkTab(0)"/>　否</td>
		</tr>
	</table>
	<div  align="center">
		<br/><br/><a class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="wfFormAdd()">保存</a>	
		<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#wf_formconfig_edit_add').window('close');">关闭</a>				  
	</div>
	<input type="hidden" id="showTab" value="1"/>
	<div id="wf_formadd_form_tree" class="easyui-window" >
  </body>
</html>
