<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	
	Object systemID = session.getAttribute("bak_url");
	Object systemName = session.getAttribute("systemName");
	request.setAttribute("systemName",systemName);
	
	String uname=String.valueOf(request.getAttribute("username"));
	String pword=String.valueOf(request.getAttribute("password"));

						
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  	<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${systemName}</title>

<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/login.css"/>
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/base.css"/>
<script type="text/javascript" language="javascript" src="jquery-easyui-1.1.2/jquery-1.4.4.min.js"></script>
<style type="text/css">
body {background: url(images/login_bg.jpg) repeat-x #555555;}
</style>
<script  language="javascript">

     var url="<%=path%>/j_spring_security_check?sysName=hbyj&j_username=<%=uname%>&j_password=<%=pword%>";
												  
     $.ajax({
		        url: url,
		        async: false,
				success: function(html){	
					window.location.href="formengine/zsf_.action?sysName=hbyj";
				}
	 });

</script>
</head>
<body>
</body>
</html>
<%

	session.invalidate();//注销
	//创建session
	if(systemID!=null){
			request.getSession(true).setAttribute("bak_url",
						systemID.toString());
	}
	
%>