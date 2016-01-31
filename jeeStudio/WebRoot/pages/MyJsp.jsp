<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script>
function formssubmit(){
	//window.location.href="http://localhost:8080/compplatform/formengine/zsf_.action";
	alert(11);
	document.forms["Loginform"].submit();
}
</script>
  </head>
  
  <body>
<form name=Loginform action='http://127.0.0.1：8989/compplatform/pages/loginnewForWin.jsp' method=post target='_self'>
<INPUT type='hidden' NAME='gopage' VALUE=''><INPUT type='text' NAME='username' VALUE='zhaishengli'><INPUT type='text' NAME='password' VALUE='123456'>
<a href="javascript:void(0)" onclick="formssubmit()">ssss</a>
</form>
  </body>
</html>
