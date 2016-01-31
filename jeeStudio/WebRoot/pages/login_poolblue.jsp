<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Object systemID = session.getAttribute("bak_url");
	Object systemName = session.getAttribute("systemName");
	request.setAttribute("systemName",systemName);

						
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${systemName}</title>

<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/login-poolblue.css"/>
<style type="text/css">
body {background: url(jquery-easyui-1.1.2/themes/images/poolblue/login_bg.jpg) repeat-x #555555;}
</style>
<script type="text/javascript" language="javascript" src="jquery-easyui-1.1.2/jquery-1.4.4.min.js"></script>
<script  language="javascript">
//回车提交表单
$(function(){
	$("body").bind('keyup',function(event) {   
		if(event.keyCode==13){   
			$(".login_btn a").trigger("click");
		}      
	});   
});
function formssubmit(){
	//window.location.href="http://localhost:8080/compplatform/formengine/zsf_.action";
	document.forms[0].submit();
}
function reset(){
	document.forms[0].reset();
}
</script>
</head>
<body>
	<div class="login">
	
		<div class="login_left">
		  	<div style="float:left;margin-left:100px;margin-top:110px;">
			  	<div style="float:left;margin-top:20px;margin-left:80px;">
			  	</div>
			  	<div style="float:left;margin-top:30px;margin-left:10px;">
			  		<span style="color:#fff;font-size:27px;font-family: '微软雅黑';line-height:25px;">${systemName}</span>
			  	</div>
		  	</div>
		</div>
		
		<div class="login_right">
			  <form action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
			 	  <input type="hidden" name="sysName" value="${bak_url}" />
				  <div class="login_top">
					  	<div style="float:left;margin-top:145px;margin-left:15px;width:400px;">
					  		<span style="color:#2d4d74;font-size:20px;font-family: '微软雅黑';line-height:25px;">系统登陆界面</span>
					  	</div>
				  </div>
			  	  <div style="width:410px;color:red;margin-left:180px;top:197px;height:15px;white-space: nowrap;text-overflow:ellipsis; overflow:hidden;position:absolute;">${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}</div>
			  	  <div class="login_cen">
				  <div class="login_input" style="margin-top:-10px;">
			      <div style="z-index:2;FILTER:alpha(opacity=90);padding-left: 90px;height:15px;"><font style="" color="red"  size="2" ><span id='mesg'><span id='login_mesg'> </span> </span></font></div>
					  <dl>
				        <dd>用户名:</dd>
				        <dt>
				            <input name="j_username" type="text" class="input" value="${sessionScope
							['SPRING_SECURITY_LAST_USERNAME']}"/>
				        </dt>
				      </dl>
					  <dl>
				        <dd>密 &nbsp;码:</dd>
				        <dt>
				         <input name="j_password" type="password" class="input" />
				        </dt>
				      </dl>
					  <dl>
				        <dd>
				        </dd>
				        <dt>
				        </dt>
				      </dl>
				    </div>
					<div class="login_btn"><a href="javascript:void(0)" onclick="formssubmit()"></a></div>
					<div class="cancel_btn"><a href="javascript:void(0)" onclick="reset()"></a></div>
			  </div>
			 
			</form>
		</div>
	</div>
	 <div class="login_bot">
			  	  <div class="login_botft">&copy; 1998-2010 ${systemName}  版权所有 </div>
	</div>
</body>
</html>
<%
	session.invalidate();//注销
	//创建session
	if(systemID!=null){
			request.getSession(true).setAttribute("bak_url",
						systemID.toString());
			request.getSession().setAttribute("isLoginSystem",
			"true");//是否是登录 写入日志
	}	
%>