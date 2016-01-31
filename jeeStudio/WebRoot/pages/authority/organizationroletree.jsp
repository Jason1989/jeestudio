<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String isAdmin=request.getParameter("isAdmin");//用于判断是否是后台平台配置 管理员登录，1为是
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'organization-replication.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script>
     $('#orgroletree').tree({
				checkbox: false,	
				fit:true,							
				url: 'organization/organization!getAllOrganizationsByClassify.action?isAdmin=<%=isAdmin%>&dcis='+new Date().getTime(),
				onClick:function(node){
					var id = node.id;
					var text = node.text
					if(node){
						//放入添加页字段
						document.getElementById("orgroleid").value = id;
						document.getElementById("orgrolename").value = text;
						$('#orgrolefindtree').window('close');
					}
				}
			});
	</script>
  </head>
  
  <body>
    <ul id="orgroletree"></ul>
  </body>
</html>
