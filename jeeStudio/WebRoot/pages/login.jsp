<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Object systemID = session.getAttribute("bak_url");
	Object systemName = session.getAttribute("systemName");
	request.setAttribute("systemName",systemName);

						
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

function registe(){
    var plandownUrl="<%=path%>/emergencydir/zxtsoft/zxtsoft.rar";
    
	window.open(plandownUrl);
  
}

$(function(){
	//回车提交表单
	$("body").bind('keyup',function(event) {   
		if(event.keyCode==13){   
			document.forms[0].submit();   
		}      
	});   
	
	$("#nc_isXian").click(function(event,tr){
		if(tr){
			$.post("formengine/loginAction!setStatus.action",{isStright:'yes',time:new Date().getTime()});
		}else{
			if($(this).attr("checked")){
				$.post("formengine/loginAction!setStatus.action",{isStright:'yes',time:new Date().getTime()});
			}else{
				$.post("formengine/loginAction!setStatus.action",{isStright:'no',time:new Date().getTime()});
			}
		}
	});
	setTimeout(function(){
		$("#nc_isXian").trigger('click',['yes']);
	},0);	
});
function formssubmit(){
	//window.location.href="http://localhost:8080/compplatform/formengine/zsf_.action";
	document.forms[0].submit();
}

$(function(){
	var ver = "";
	if(navigator.appName=="Microsoft Internet Explorer")
	ver = get360BrowserVersion();
	if( ver ) {
		$("#systemName").addClass("top1"); 
	}else{
		$("#systemName").addClass("top2"); 
	}
});
</script>

<SCRIPT language=vbscript>
Function get360BrowserVersion()
on error resume next
Dim g_strSecurityId
g_strSecurityId = "111"
g_strSecurityId = external.twGetSecurityID( window )
dim g_strTwVersion
g_strTwVersion = ""
g_strTwVersion = external.twGetVersion( g_strSecurityId )
if g_strTwVersion >= "1.0.1.3" then
g_bRunIn360se = true
end if
get360BrowserVersion = g_strTwVersion
End Function
</SCRIPT>

<style>
	.top1{margin-top:15px;}
	.top2{margin-top:40px;}
</style>
</head>
<body>
<div class="login">
  <form action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
  <div class="login_top">
  	<div style="width:410px;color:#fff;margin-left:30px;margin-top:27px;height:15px;white-space: nowrap;text-overflow:ellipsis; overflow:hidden;">${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}</div>
  	<div id="systemName" style="font-family:'微软亚黑';color:#fff;font-size:17px;font-weight:bold;margin-left:39px;">${systemName}</div>
  </div>
  <div class="login_cen">
    <div class="login_input">

						<dl>
        <dd>用户名</dd>
        <dt>
            <input name="j_username" type="text" class="input" value="${sessionScope
['SPRING_SECURITY_LAST_USERNAME']}"/>
		<!--  <select name="j_username"> 
		    <option value="chenlan" />任务创建人-》陈兰
		    <option value="weixijiao" />任务质控人-》韦细姣
		    <option value="lifenghua" />任务复核人-》李凤华
		    <option value="heyan" />任务审核人-》何 岩
		    <option value="laichunmiao" />报告审核人-》赖春苗
		    <option value="heyan" />审核人-》何 岩
		    <option value="admin" />系统管理员-》管理员
		 </select>-->
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
      <dl style="padding-top:0px;">
        
      
      </dl>
  
    </div>
    				
				
	<div class="login_btn"><a href="javascript:void(0)" onclick="formssubmit()"></a></div>
  </div>
  <div class="login_bot">
  	<div class="login_botft">&copy; 1998-2010 ${systemName}  版权所有 </div>
  </div>
  <input type="hidden" name="sysName" value="${bak_url}" />
  </form>
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