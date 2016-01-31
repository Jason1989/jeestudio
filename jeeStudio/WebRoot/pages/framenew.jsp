<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String validationRule = "";
if(request.getParameter("validationRule") != null){
	validationRule = request.getParameter("validationRule");
}
//System.out.println(validationRule);
//System.out.println(validationRule);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>快速开发平台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=100">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <!-- jquery 样式 -->
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/easyui.blue.css"/>
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/particular.css"/>
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/popup.css"/>
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css" href="css/indexPage.css">
	
	<!-- jquery js-->
	<script type="text/javascript" src="jquery-easyui-1.1.2/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.1.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.1.2/jquery.easyui.extends.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.1.2/easyui-lang-zh_CN.js"></script>
	<!-- xml js -->
	<script type="text/javascript" src="js/form.common.js"></script>
	<script type="text/javascript" src="js/XmlUtils.js"></script>
	<script type="text/javascript" src="js/json2.js"></script>
	
	<!-- 快速表单js -->
	<script type="text/javascript" src="common/js/design/platform-conf.js"></script>
	<script type="text/javascript" src="common/js/design/platform-conf-view.js"></script>
	<script type="text/javascript" src="common/js/design/platform-conf-edit.js"></script>
	<!-- common-js -->
	<script type="text/javascript" src="common/js/common-util/jquery_form.js"></script>
	<script type="text/javascript" src="common/js/common-util/ajax_security.js"></script>
	<script type="text/javascript" src="common/js/common-util/service-frame.js"></script>
	<script type="text/javascript" src="common/js/common-util/code-engine-constant.js"></script>
	<script type="text/javascript" src="common/js/common-util/engine-common-util.js"></script>
	<script type="text/javascript" src="common/js/common-util/service-frame.js"></script>
	<script type="text/javascript" src="js/page-ext.js"></script>
	<!-- easyui version -->
	<script type="text/javascript" src="common/js/version-easyui/engine-util-easyui.js"></script>
	<script type="text/javascript" src="common/js/version-easyui/engine-util-curing.js"></script>
	<script type="text/javascript" src="common/js/version-easyui/workflow-util.js"></script>
	
	
	
	<script>
		var validationRuleFlag = '<%=validationRule%>';
		$(function(){
			$(".left_nav_col li").click(function(){
				$(".left_nav_colbg").removeClass("left_nav_colbg");
				$(this).addClass("left_nav_colbg");
			});

			//head中选择某个标签
			$(".head_right div a").click(function(){
			      $(".head_right div a").html('');
				  $(this).html('<span></span>');
			});
			
			//top标签选中
			
		   $('body').layout();
	       $('body').css('visibility', 'visible');
		});
		function clearDom() {
			$("div.window-mask ~ div").remove();
		}
		function refreshLeftMenu(){
		    alert("12345");
			$("#contents").panel("refresh");
		}
		function killerrors() {
			return true;
		}
		window.onerror = killerrors;  
		
	</script>
  </head>
  

<body class="easyui-layout" style="visibility: hidden;">	
	<%
		request.getSession().invalidate();
	 %>
	<!-- 头部 -->
	<div region="north" border="false"  split="false" style="height:70px;padding:0;margin:0;">
	    <div class="head_bg">
			<div class="head_logo_setting">
				<div class="head_t_1">
					<font color="white" face="黑体" size="5">快速开发平台</font>
				</div>
				<div class="head_t">
					<div class="head_t_msg">欢迎您，管理员！</div>
					<div class="head_t_if">
						<div class="head_t_img"><img src="jquery-easyui-1.1.2/themes/images/blue/head_ico.jpg" width="12" height="11" /></div>
						<div class="head_t_font"><a href="javascript:;" style="text-decoration: none;">注销</a></div>
						<div class="head_t_img"><img src="jquery-easyui-1.1.2/themes/images/blue/head_ico1.jpg" width="14" height="11" /></div>
						<div class="head_t_font"><a href="javascript:;" style="text-decoration: none;">皮肤</a></div>
					</div>
				</div>
			</div>
			<div class="head_right">
				<div class="head_btn1" align="center"><a href="javascript:void(0);" onfocus="this.blur()" onclick="menuSwitch('contents','menu/menu.action')"  ><span></span></a><span>构件平台</span></div>
				<div class="head_btn4" align="center"><a href="javascript:void(0);" onfocus="this.blur()" onclick="menuSwitch('contents','pages/menu/menu-org.jsp')"  ></a><span>系统管理</span></div>
				<div class="head_btn3" align="center"><a href="javascript:void(0);" onfocus="this.blur()" onclick="menuSwitch('contents','pages/menu/menu-help.jsp')"></a><span>在线帮助</span></div>
			</div>
		</div>
	</div>
	<!-- 底部 -->
	<div region="south" border="false" split="false" style="height:30px;padding:5px 0 0 0;background:#f5f5f5;">
		<div class="bottom">
			<div class="bottom_font" align="center">&copy; 1994-2011  版权所有  </div>
		</div>
	</div>
	<!--左部的菜单-->	
	<div id="contents" region="west" border="true" split="true" title="菜单栏" icon="icon-menu" style="width:200px;padding1:1px;overflow: hidden;" href="menu/menu.action">
	</div>
	<!-- 右部主框架 -->
	<div id="main" region="center" border="true" href="pages/default_main.jsp" style="overflow:hidden;">		
	</div>
</body>
</html>
