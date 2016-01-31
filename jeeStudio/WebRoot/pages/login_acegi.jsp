<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  	<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>

<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/login.css"/>
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/particular.css"/>
<style type="text/css">
body {background: url(images/login_bg.jpg) repeat-x #555555;}
</style>
<script  language="javascript">
function formssubmit(){
	//window.location.href="http://localhost:8080/compplatform/formengine/zsf_.action";
	document.forms[0].submit();
}
</script>
</head>
<body>
<div class="login">
  <form action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
  <div class="login_top">${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}</div>
  <div class="login_cen">
    <div class="login_input">
      <dl>
        <dd>用户名</dd>
        <dt>
          <input name="j_username" type="text" class="input" value="${sessionScope
['SPRING_SECURITY_LAST_USERNAME']}"/>
        </dt>
      </dl>
	  <dl>
        <dd>密 &nbsp;码</dd>
        <dt>
          <input name="j_password" type="password" class="input" />
        </dt>
      </dl>
      <!-- 
      <dl style="padding-top:0px;">
        <dd style="padding:0px;"><input type="checkbox" name="_spring_security_remember_me " /></dd>
        <dt style="padding-top:3px;color:#ffffff;">
         两周之内不必登陆
        </dt>
      </dl>
       -->
    </div>
    
				
	<div class="login_btn"><a href="javascript:void(0)" onclick="formssubmit()"></a></div>
  </div>
  <div class="login_bot">
  	<div class="login_botft">&copy; 1998-2010  版权所有 </div>
  </div>
  </form>
</div>
</body>
</html>
