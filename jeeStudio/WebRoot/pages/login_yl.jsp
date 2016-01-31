<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Object systemID = session.getAttribute("bak_url");
	Object systemName = session.getAttribute("systemName");
	request.setAttribute("systemName",systemName);

						
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>${systemName}</title>

		<link rel="stylesheet" type="text/css"
			href="jquery-easyui-1.1.2/themes/login_yl.css" />
		<script type="text/javascript" language="javascript"
			src="jquery-easyui-1.1.2/jquery-1.4.4.min.js"></script>
		<script type="text/javascript">
		   $(function(){
		       $("body").height($(window).height());//改变body的大小
		       $(window).resize(function(){//窗口大小改变是版权信息改变位置
		       	  $("body").height($(window).height());
		       });
		       $(".loginButtons").each(function(i){
		       	   if(i==0){
				       $(this).hover(function(){
				            $(this).attr("src",src="jquery-easyui-1.1.2/themes/images/loginyl/loginbtn1.png");
				       },function(){
				            $(this).attr("src",src="jquery-easyui-1.1.2/themes/images/loginyl/loginbtn.png");
				       }).click(function(){
				       	  document.forms[0].submit();
				       });
		       	   }else{
		       	       $(this).hover(function(){
				            $(this).attr("src",src="jquery-easyui-1.1.2/themes/images/loginyl/reset1.png");
				       },function(){
				            $(this).attr("src",src="jquery-easyui-1.1.2/themes/images/loginyl/reset.png");
				       }).click(function(){
				          document.forms[0].reset();
				       });
		       	   }
		       });
		       
		       //回车提交表单
				$("body").bind('keyup',function(event) {
					if(event.keyCode==13){ 
						$(".loginButtons:first").trigger('click');  
					}      
				});   
		   });
		</script>
	</head>
	<body topmargin="0" bottommargin="0" scroll="no">
	<form action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
		<div class="loginContent">
			<div class="loginForm" align="center">
				<div class="loginTitle"><img src="jquery-easyui-1.1.2/themes/images/loginyl/logintitle.png" style="width:498px;height:88px;"/></div>
				<div class="loginInput">
					<div class="textfield">
				     <label>用户名称：</label><input name="j_username" type="text" class="input" value="${sessionScope
['SPRING_SECURITY_LAST_USERNAME']}">
					</div>
					<div class="textfield">
				     <label>用户密码：</label><input name="j_password" type="password"> 
				     </div>
				     <div style="padding:20px  0 0 80px; ">
				     <img src="jquery-easyui-1.1.2/themes/images/loginyl/loginbtn.png" alt="登录" class="loginButtons"/>&nbsp; &nbsp;
				     <img src="jquery-easyui-1.1.2/themes/images/loginyl/reset.png" alt="重置" class="loginButtons"/>
				     </div>
				</div>
			</div>
		</div>
		<div class="ylcopyright" align="center">
			<span style="color:white;height: 100%;vertical-align: middle;line-height: 30px;">
			</span>
		</div>
		<input type="hidden" name="sysName" value="${bak_url}" />
	</form>
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