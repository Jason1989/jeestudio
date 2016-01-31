<%@ page language="java" contentType="text/html; charset=utf-8"
    %>


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

 	<link rel="stylesheet" type="text/css" href="http://localhost:8081/compplatform/css/image.css">
	<link rel="stylesheet" type="text/css" href="http://localhost:8081/compplatform/css/auto_row.css" />
	<link rel="stylesheet" type="text/css" href="http://localhost:8081/compplatform/css/style.css">
	<link rel="stylesheet" type="text/css" href="http://localhost:8081/compplatform/css/indexPage.css">

	<link rel="stylesheet" type="text/css" href="http://localhost:8081/compplatform/jquery-easyui-1.1.2/themes/easyui.gj.css"/>
	<script type="text/javascript" src="http://localhost:8081/compplatform/jquery-easyui-1.1.2/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="http://localhost:8081/compplatform/jquery-easyui-1.1.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="http://localhost:8081/compplatform/jquery-easyui-1.1.2/jquery.easyui.extends.js"></script>
	<script type="text/javascript" src="http://localhost:8081/compplatform/jquery-easyui-1.1.2/easyui-lang-zh_CN.js"></script>
	
	<script type="text/javascript" src="http://localhost:8081/compplatform/common/js/common-util/jquery_form.js"></script>
	<script type="text/javascript" src="http://localhost:8081/compplatform/common/js/common-util/ajax_security.js"></script>
	<script type="text/javascript" src="http://localhost:8081/compplatform/common/js/common-util/service-frame.js"></script>
	<script type="text/javascript" src="http://localhost:8081/compplatform/common/js/common-util/code-engine-constant.js"></script>
	<script type="text/javascript" src="http://localhost:8081/compplatform/common/js/common-util/engine-common-util.js"></script>
	<script type="text/javascript" src="http://localhost:8081/compplatform/common/js/common-util/upload.js"></script>
	<script type="text/javascript" src="http://localhost:8081/compplatform/js/page-ext.js"></script>
	
	<script type="text/javascript" src="http://localhost:8081/compplatform/common/js/version-easyui/engine-util-easyui.js"></script>
	<script type="text/javascript" src="http://localhost:8081/compplatform/common/js/version-easyui/workflow-util.js"></script>
	<script type="text/javascript" src="http://localhost:8081/compplatform/js/XmlUtils.js"></script>
	<script type="text/javascript" src="http://localhost:8081/compplatform/js/json2.js"></script>
	<script type="text/javascript" src="http://localhost:8081/compplatform/common/js/fund-budget/fund-budget.js"></script>
 
  </head>
  
  <body>

	
	
		<div class="easyui-panel" href="http://localhost:8081/compplatform/formengine/listPageAction.action?
		formId=ff8080813c9032eb013c9033e74b0003&OBJID=11010802418" style="width:1000px;height:500px;"></div>

  </body>
</html>
