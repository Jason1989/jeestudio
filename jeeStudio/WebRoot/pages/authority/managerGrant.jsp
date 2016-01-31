<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String isall = request.getParameter("isall");
String orgid = request.getParameter("orgid");
String roleId=request.getParameter("roleId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title> 权限管理</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	
	-->
    
    
  </head>
  
  <body>
	<div id="tt" class="easyui-tabs"  fit="true">
		<div title="系统授权" style="padding:20px;" cache="false" href="pages/authority/rolemenuconfig.jsp?orgid=<%=orgid%>&roleId=<%=roleId%>&isall=<%=isall%>">
		</div>
		<div title="字段授权" closable="true" cache="false" href="authority/fieldGrant!list.action?roleId=<%=roleId%>"> 
		</div>
		
	</div>
  </body>
</html>
