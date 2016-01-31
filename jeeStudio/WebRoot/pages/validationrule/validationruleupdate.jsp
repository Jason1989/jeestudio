<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/include.jsp"%>
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
			function bdValidationruleupdateFormSubmit(){
				$('#bd_validationruleupdate_form').form('submit',{ 
					url:'validationrule/validationRule!update.action', 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
						if("exist" == data) {						
							$.messager.alert("提示","验证规则编码或者名称已存在！", 'warning');
						} else if("success" == data) {
							parent.$('#bd_validationrule_update').window('close');
							$('#bd_validationrulelist_table').datagrid('reload');
							refreshValidationRuleSession();
						}
					} 
				}); 			
			}
			$('#bdValidationruleupdateFormSaveButton').bind('click',bdValidationruleupdateFormSubmit);
		});
		function initValidationRuleEditData(){
			$(":radio[value='${validationRule.state}']").attr("checked",true);
		}
    </script>
  </head>  
  <body>
	<form id="bd_validationruleupdate_form" method="post" action="validationrule/validationRule!update.action">
		 <div class="pop_col_col">
		  <div class="pop_col_conc">
		    <dl>
		      <dd>规则编码：</dd>
		      <dt><input name="validationRule.id" class="easyui-validatebox" 
		          size="23" value="${validationRule.id}" readonly/></dt>
		    </dl>
		    <dl>
		      <dd>规则名称：</dd>
		      <dt style="width:200px;"><input name="validationRule.name" class="easyui-validatebox" size="23" required="true" value="${validationRule.name}"/>&nbsp;
		          <font size="2" color="red">*</font></dt>
		    </dl>
		    </div>
		  <div class="pop_col_cons">
		    <dl>
		      <dd>表达式：</dd>
		      <dt><textarea cols="45" rows="4" name="validationRule.expression" class="easyui-validatebox" required="true">${validationRule.expression}</textarea>
		          &nbsp;<font size="2" color="red">*</font></dt>
		    </dl>
		  </div>
		   <div class="pop_col_cons">
		    <dl>
		      <dd>提示信息：</dd>
		      <dt><textarea cols="45" rows="4" name="validationRule.reminder" class="easyui-validatebox" required="true">${validationRule.reminder}</textarea>
		          &nbsp;<font size="2" color="red">*</font></dt>
		    </dl>
		  </div>
		  <div class="pop_col_conc">
		    <dl>
		      <dd>序号：</dd>
		      <dt><input name="validationRule.sortNo" value="${validationRule.sortNo}" 
		          class="easyui-numberbox" size="23"/></dt>
		    </dl>
		  </div>
		  <div class="pop_col_cons">
			       <div align="center" style="width: 100%;height: 100%;padding-top: 20px;">
				 		<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bdValidationruleupdateFormSaveButton">保存</a>
					    <a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_validationrule_update').window('close');">关闭</a>
			       </div>
				</div>
		</div>
		
	</form>
  </body>
</html>
