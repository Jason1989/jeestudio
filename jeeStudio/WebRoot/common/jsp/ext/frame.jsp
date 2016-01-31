<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>数字环保中心</title>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <link rel="stylesheet" type="text/css" href="css/image.css">
	<link rel="stylesheet" type="text/css" href="css/auto_row.css" />
	<link rel="stylesheet" type="text/css" href="css/style.css">

	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/particular.css"/>
	
	<script type="text/javascript" src="jquery-easyui-1.1.2/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.1.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.1.2/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="common/components/My97DatePicker3.0.1/My97DatePicker/WdatePicker.js"></script>
	
	<script type="text/javascript" src="common/js/version-easyui/code-engine-util.js"></script>
	<script type="text/javascript" src="js/engine-util-easyui.js"></script>
	<script type="text/javascript" src="js/engine_frame.js"></script>
	<script type="text/javascript" src="js/service-frame.js"></script>
	<script type="text/javascript" src="js/code-engine-constant.js"></script>
	
	<script type="text/javascript" src="jquery-easyui-1.1.2/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/XmlUtils.js"></script>
	<script type="text/javascript" src="js/jquery_form.js"></script>
	
	
	
	<script>
		$(function(){
			$(".left_nav_col li").click(function(){
				$(".left_nav_colbg").removeClass("left_nav_colbg");
				$(this).addClass("left_nav_colbg");
			});
			var widthValue = 800;
			var heightValue = 550;
			var widthCli = document.body.clientWidth;
			var heightCli = document.body.clientHeight;
			var leftValue = (widthCli-widthValue)/2;
			var topValue = (heightCli-heightValue)/2;
			$('#wf_formconfig_todotask').window({
                title: "待办任务",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                left:leftValue,
                top:topValue,
                width: widthValue,
                height: heightValue
            });
			//top标签选中
			
		   $('body').layout();
	       $('body').css('visibility', 'visible');
		});
		function clearDom() {
			$("div.window-mask ~ div").remove();
		}
		function refreshLeftMenu(){
			$("#contents").panel("refresh");
		}
		function wfFormconfigToDoTask(){
        	//$("#wf_formconfig_todotask").window({'href':''});		
          	//$("#wf_formconfig_todotask").window('refresh');
           	$("#wf_formconfig_todotask").window({'href':'workflow/DaibanWorkFlow!toToDoTaskList.action'});		
           	$("#wf_formconfig_todotask").window('open');
        }
	</script>
  </head>
  <body class="easyui-layout" style="visibility: hidden;">	
		<!-- 头部 -->
		<div region="north" border="false"  split="false" style="height:83px;padding:0;margin:0;">
		    <div class="head_bg">
				<div class="head_logo">
					<div class="head_t">
						<div class="head_t_msg" style="width: 150px;">欢迎您，${userName}！</div>
						<div class="head_t_if">
							<div class="head_t_img"><img src="jquery-easyui-1.1.2/ui/images/head_ico.jpg" width="12" height="11" /></div>
							<div class="head_t_font"><a href="${pageContext.request.contextPath}/formengine/loginAction!logout.action">注销</a></div>
							<div class="head_t_img"><img src="jquery-easyui-1.1.2/ui/images/head_ico1.jpg" width="14" height="11" /></div>
							<div class="head_t_font"><a href="javascript:;">皮肤</a></div>
						</div>
					</div>
				</div>
				<div class="head_right">
						<div class="head_btn5"  align="center"><a href="javascript:wfFormconfigToDoTask();"></a><span>我的任务</span></div>
					<s:iterator value="#request.systemMenuList" status="stauts"   >
						<div class="head_btn${stauts.index%4+1}" align="center"><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menuId}','contents','frameAccordionMenu');"></a><span>${title}</span></div>
					</s:iterator>
					<!--  
						<div class="head_btn1"><a href="javascript:;"></a></div>
						<div class="head_btn2"><a href="javascript:;"></a></div>
						<div class="head_btn3"><a href="javascript:;"></a></div>
						<div class="head_btn4"><a href="javascript:;"></a></div>
					-->
				</div>
			</div>
		</div>
		<!-- 底部 -->
		<div region="south" border="false" split="true" style="height:30px;padding:0px;background:#f5f5f5;">
			<div class="bottom">
				<div class="bottom_font" align="center">&copy; 1998-2010  版权所有  Copyright&copy;2010 All Right Reserved</div>
			</div>
		</div>
		<!--左部的菜单-->	
		<div id="contents" region="west" border="true" split="true" title="菜单栏" icon="icon-menu" style="width:200px;padding1:1px;"  >
			<div  class="easyui-accordion" fit="true" border="false" style="background: url(jquery-easyui-1.1.2/ui/images/left_bg.jpg)"   >
				<!--   
					<div title="业务功能菜单1" headerCls="accordion-font-color" icon="icon-accordion-node"  selected="true" style="overflow:auto;padding:5px;">
						<ul class="left_nav_col">
						  <li>
							  <div class="left_nav_colimg"><img src="jquery-easyui-1.1.2/ui/images/nav_ico_1.gif" width="42" height="34" /></div>
							  <div class="left_nav_colfont"><a href="javascript:void(0);">数据源文件</a></div>
						  </li>
						  <li class="left_nav_colbg">
							  <div class="left_nav_colimg"><img src="jquery-easyui-1.1.2/ui/images/nav_ico_2.gif" width="32" height="33"/></div>
							  <div class="left_nav_colfont"><a href="javascript:void(0);">数据源文件</a></div>
						  </li>
						</ul>
					</div>
				 -->
				<s:iterator value="#request.tabMenuList" status="stauts"   >
					<div title="${title}"  headerCls="accordion-font-color"  icon="icon-accordion-node" >
						<ul id="frame_menu_tree_${stauts.index}" style="padding:10px;"></ul>
						<script type="text/javascript">
							createMenuTree('${json}','frame_menu_tree_${stauts.index}');
						</script>
					</div>
				</s:iterator>
			</div>
		</div>
		
		<!-- 右部主框架 -->
		<div id="main" region="center" border="true"  style="overflow:hidden;">		
			<!-- 
				Ext 
			        iframe scrolling="no"  id='xxxframe' name="serviceSystemModel" style="width:100%; height: 100%;"  name="xxx" framespacing="0" marginwidth="0" marginheight="0" frameborder="no" border="0"  
					iframe
			-->
		</div>
		<!-- 待办的窗口 -->
			<div id="wf_formconfig_todotask" class="easyui-window" ></div>
    	<!-- 待办的详情 -->
  </body>
</html>
