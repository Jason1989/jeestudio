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
		<title>修改页面</title>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		
		<script language="javascript" type="text/javascript">		
		$(function(){
			function indexPageUpdateFormSubmit(){
				$('#index_page_update_form').form('submit',{ 
					url:'indexpage/indexpage!update.action', 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
						if("success" == data) { 
							parent.$('#index_Page_Update').window('close');
							$('#indexTemplateListTable').datagrid('reload');
						}
					} 
				}); 			
			}
			$('#indexPageUpdateButton11').bind('click',indexPageUpdateFormSubmit);
		});
    </script>
	</head>
	<body>
		<form id="index_page_update_form" method="post"
			action="indexpage/indexpage!update.action">
			<input type="hidden" value="${page.id}" name="pageunit.id"></input>
			<table style="text-align: left;" cellpadding="3">
				<tr>
					<td>
						首页名称：
					</td>
					<td>
						<input name="pageunit.name" class="easyui-validatebox" size="25"
							required="true" value="${page.name }"/>
						&nbsp;&nbsp;
						<font size="2" color="red">*</font>
					</td>
				</tr>
				<tr> 
					<td>
						模板页位置：
					</td>
					<td>
						<textarea cols="35" rows="4" name="pageunit.description"
							class="easyui-validatebox">${page.description}</textarea>
					</td>
				</tr>
			</table>
			<div align="center"
				style="width: 100%; height: 100%; padding-top: 20px;">
				<a href="javascript:;" class="easyui-linkbutton" icon="icon-save"
					id="indexPageUpdateButton11">保存</a>
				<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel"
					onclick="parent.$('#index_Page_Update').window('close');">关闭</a>
			</div>

		</form>
	</body>
</html>
