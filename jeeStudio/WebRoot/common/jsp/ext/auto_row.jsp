<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'auto_row.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
     <link href="css/auto_row.css" rel="stylesheet" type="text/css" />
  </head>
  
  <body>
    <div class="cont_mar">
      <div class="cont_l">
        <dl>
          <dd>编队属性：</dd>
          <dt>-----</dt>
        </dl>
        <dl>
          <dd>属性：</dd>
          <dt>内容内容内</dt>
        </dl>
      </div>
      <div class="cont_l">
        <dl>
          <dd>编队属性：</dd>
          <dt>内容内容内容内容内容</dt>
        </dl>
        <dl>
          <dd>属性：</dd>
          <dt>内容内容内</dt>
        </dl>
      </div>
      <div class="cont_l">
        <dl>
          <dd>编队属性：</dd>
          <dt>内容内容内容内容内容</dt>
        </dl>
        <dl>
          <dd>属性：</dd>
          <dt>内容内容内</dt>
        </dl>
      </div>
    </div>
  </body>
</html>
