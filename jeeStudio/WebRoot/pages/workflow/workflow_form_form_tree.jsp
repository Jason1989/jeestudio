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
		$("#wf_formadd_formadd").tree({
			onClick:function(node){
				var level = node.attributes.url;
				if(parseInt(level)!=3){
					$.messager.alert("提示","只能选择第三级！", 'error');
				}else{
					parent.$("#wf_formadd_form_id").val(node.id);
					parent.$("#wf_formadd_form_text").val(node.text);
					$("#wf_formadd_form_tree").window("close");
				}
			}
		});
	});
    </script>
  </head>  
  <body>			  
	<ul id="wf_formadd_formadd" class="easyui-tree" url="workflow/WorkFlowFrame_tabPageList.action" onlyLeafCheck="true" style="width:175px;"/>
  </body>
</html>
