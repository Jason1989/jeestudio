<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
    <title>添加</title>    
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
			
			function bdDictionarygroupaddFormSubmit(){
				$('#bd_dictionarygroupadd_form').form('submit',{ 
					url:'dictionary/dictionaryGroup!add.action', 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
						if("exist" == data) {						
							$.messager.alert("提示","字典分组编码已存在！", 'warning');
						} 
						else if("groupExist"==data){
						$.messager.alert("提示","字典分组名称已存在！", 'warning');
						}
						else if("success" == data) {
							parent.$('#bd_dictionarygroup_add').window('close');
							$('#bd_dictionarygrouplist_table').datagrid('reload');
						}
					} 
				}); 			
			}
			$('#bdDictionarygroupaddFormSaveButton').bind('click',bdDictionarygroupaddFormSubmit);
		});

    </script>
  </head>  
  <body>
	<form id="bd_dictionarygroupadd_form" method="post" action="dictionary/dictionaryGroup!add.action" style="margin: 0;padding: 0;">
		
		<div class="pop_col_color" style="width: 250px;">
				<div class="pop_col_conc">
					<dl>
						<dd>
							字典编码：
						</dd>
						<dt>
							<input name="dictionaryGroup.id" class="easyui-validatebox" validType="entityCode[1,50]" size="20" />
						</dt>
					</dl>
					<dl>
						<dd>
							字典分组名称：
						</dd>
						<dt>
							<input name="dictionaryGroup.name" class="easyui-validatebox" size="20" required="true" validType="textContentLength[40]"  />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
			</div>
			 <div style="float:left;padding:20px 0 0 100px;">
			 		<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bdDictionarygroupaddFormSaveButton">保存</a>
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_dictionarygroup_add').window('close');">关闭</a>
				</div>
	</form>
  </body>
</html>
