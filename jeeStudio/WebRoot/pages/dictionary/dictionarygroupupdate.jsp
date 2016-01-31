<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
    <title>修改</title>    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script language="javascript" type="text/javascript">
		$(function(){			
			function bdDictionarygroupupdateFormSubmit(){
				$('#bd_dictionarygroupupdate_form').form('submit',{ 
					url:'dictionary/dictionaryGroup!update.action', 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
						if("success" == data) {
							parent.$('#bd_dictionarygroup_update').window('close');
							$('#bd_dictionarygrouplist_table').datagrid('reload');
						}
						else{
						$.messager.alert("提示","分组名称已经存在，请重新输入！", 'warning');
						}
					} 
				}); 			
			}
			$('#bdDictionarygroupupdateFormSaveButton').bind('click',bdDictionarygroupupdateFormSubmit);
		});

    </script>
  </head>  
  <body>
	<form id="bd_dictionarygroupupdate_form" method="post" action="dictionary/dictionaryGroup!update.action">
		<div class="pop_col_col" style="width:300px;">
				<div class="pop_col_conc">
					<dl>
						<dd>
							字典编码：
						</dd>
						<dt>
							<input name="dictionaryGroup.id" class="easyui-validatebox" size="20" value="${dictionaryGroup.id }" readonly/>
						</dt>
					</dl>
					<dl>
						<dd>
							字典名称：
						</dd>
						<dt>
							<input name="dictionaryGroup.name" class="easyui-validatebox" size="20" required="true" value="${dictionaryGroup.name }" validType="textContentLength[40]" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
			</div>
			 <div style="float:left;padding:20px 0 0 100px;">
			 		<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bdDictionarygroupupdateFormSaveButton">保存</a>
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_dictionarygroup_update').window('close');">关闭</a>
				</div>
	</form>
  </body>
</html>
