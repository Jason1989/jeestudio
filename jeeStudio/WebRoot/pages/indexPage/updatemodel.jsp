<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>更新模块</title>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">

		<script language="javascript" type="text/javascript">		
		$(function(){
			function indexModeladdFormSubmit1(){
				$('#index_model_form1').form('submit',{ 
					url:'index/indexModel!add.action', 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
						if("success" == data) {
							parent.$('#indexModel_update').window('close');
							$('#indexPageModelListTable').datagrid('reload');
						}
					} 
				}); 			
			}
			$('#indexModelSaveButton2').bind('click',indexModeladdFormSubmit1);
		});
    </script>
	</head>
	<body>
		<form id="index_model_form1" method="post"
			action="index/indexModel!add.action">
			<table style="text-align: left;" cellpadding="3">
				<input name="model.id" type="hidden" value="${model.id}" />
				<tr>
					<td>
						模块名称：
					</td>
					<td>
						<input name="model.name" class="easyui-validatebox" size="25"
							required="true" value="${model.name }" />
						&nbsp;&nbsp;
						<font size="2" color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>
						模块地址：
					</td>
					<td>
						<textarea cols="50" rows="4" name="model.url" class="easyui-validatebox" required="true"
							 >${model.url}</textarea>
						&nbsp;&nbsp;
						<font size="2" color="red">*</font>
					</td>
				</tr>
				<tr>
					<td>
						描述：
					</td>
					<td>
						<textarea cols="50" rows="4" name="model.description"
							class="easyui-validatebox">${model.description }</textarea>
					</td>
				</tr>
			</table>
			<div align="center"
				style="width: 100%; height: 100%; padding-top: 20px;">
				<a href="javascript:;" class="easyui-linkbutton" icon="icon-save"
					id="indexModelSaveButton2">保存</a>
				<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel"
					onclick="parent.$('#indexModel_update').window('close');">关闭</a>
			</div>
		</form>
	</body>
</html>
