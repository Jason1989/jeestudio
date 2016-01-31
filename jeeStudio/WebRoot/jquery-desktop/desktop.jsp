<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
 String path = request.getContextPath();
 String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	if(request.getAttribute("themes")!=null){
	Cookie cookie2 = new Cookie("themes", request.getAttribute("themes").toString());
	    cookie2.setPath("/");
   //   cookie2.setDomain(host);
		cookie2.setMaxAge(33333333);
		response.addCookie(cookie2);
	}
	
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="x-ua-compatible" content="ie=emulateie8" />
		<meta name="description" content="JavaScript desktop environment built with jQuery." />
		<title>${systemName}</title>
			<!--[if lt IE 7]>
				<script>
					window.top.location = 'http://desktop.sonspring.com/ie.html';
				</script>
			<![endif]-->
		
			<link rel="stylesheet" href="assets/stylesheets/desktop.css" />
			
			<link rel="stylesheet" type="text/css" href="../jquery-easyui-1.1.2/themes/easyui.desktop.css"/>
			
			<script type="text/javascript" src="../jquery-easyui-1.1.2/jquery-1.4.4.min.js"></script>
			<script type="text/javascript" src="../jquery-easyui-1.1.2/jquery.easyui.min.js"></script>
			<script type="text/javascript" src="../jquery-easyui-1.1.2/easyui-lang-zh_CN.js"></script>
			<script type="text/javascript" src="../common/js/desktop/desktop.js"></script>
			<script src="assets/javascripts/jquery.desktop.js"></script>
			
			
			<script type="text/javascript" src="../common/js/common-util/jquery_form.js"></script>
			<script type="text/javascript" src="../common/js/common-util/ajax_security.js"></script>
			<script type="text/javascript" src="../common/js/common-util/service-frame.js"></script>
			<script type="text/javascript" src="../common/js/common-util/code-engine-constant.js"></script>
			<script type="text/javascript" src="../common/js/common-util/engine-common-util.js"></script>
			<script type="text/javascript" src="../common/js/common-util/upload.js"></script>
			<script type="text/javascript" src="../common/components/My97DatePicker/WdatePicker.js"></script>
			
			<script type="text/javascript" src="../common/js/version-easyui/engine-util-easyui.js"></script>
			<script type="text/javascript" src="../common/js/version-easyui/workflow-util.js"></script>
			<script type="text/javascript" src="../js/XmlUtils.js"></script>
			<script type="text/javascript" src="../js/json2.js"></script>
			<script type="text/javascript" src="../crm/js_crm/highcharts.js"></script>
			
			<script type="text/javascript" src="../common/js/fund-budget/fund-budget.js"></script>
		    <script type="text/javascript">
		    	$(function(){		    	
		    	   //给新添加的状态工具添加事件
		    	   $('#dock a').live('click', function() {
						// Get the link's target.
						var w_s = $($(this).attr('href'));
						
						// Hide, if visible. minimized maximized closed
						if(w_s.window("options").minimized || w_s.window("options").closed){
						     w_s.window("open");
						}else {
							w_s.window("minimize");
						}
						
						// Bring window to front.
						
					});
					//换肤窗口初始化
		           $("#zxtplat_change_skin").window({
		                closed:true,
		                width:600,
		                height:400,
		                modal:true,
		                shadow: false,
		                cache:false,
		                title:'更换皮肤',
		                onClose:function(){
		                    //when close event occour ,the following must get node by "zxtplat_change_skin" 
		                	var options = $("#zxtplat_change_skin").window('options');
		                	$("#zxtplat_change_skin").window('destroy',true);
		                	var cw = null;
		                	if(!$("#zxtplat_change_skin").get(0)){
		                		cw = $("<div id='zxtplat_change_skin'></div>").appendTo('body').window(options);
		                	}else{
		                		cw = $('#zxtplat_change_skin').window(options);
		                	}
		                }
		                
		           });
		           //换肤窗口弹出
			       $(".change_skin_zxt").click(function(){
			       		$("#zxtplat_change_skin").window('refresh','<%=basePath%>common/jsp/easyui/skin.jsp').window('open');
			       });
			       //换肤实现
			       $(".change_skin_button").live('click',function(){
			           var theme = $("input[name=skin]:checked").val();
			           if("blue" == theme || "green" == theme|| "dgreen"==theme|| "deepblue"==theme){
			          		window.location.href="<%=basePath%>compplatform/formengine/zsf_.action?sysName=${bak_url}&theme="+theme+"&cwidth="+$("body").width();
			           }else if ("desktop" == theme){
			           	    $.messager.alert('提示','此主题已应用');
			           }else {
			              $.messager.alert('提示','请选择主题','info');
			           }
			       });
			       
			       //禁用回退键
			       $('body').live('keydown',function(event){
			           if(((event.keyCode == 8) && ((event.srcElement.type != "text" && event.srcElement.type != "textarea" && event.srcElement.type != "password") || event.srcElement.readOnly == true)) 
			           || ((event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82)) ) ||(event.keyCode == 116) ) {
			                window.event.keyCode = 0; 
			            window.event.returnValue = false; 
			           }
			       });
			       
		    	});
		    </script>
			<!--[if lt IE 9]>
				<link rel="stylesheet" href="assets/stylesheets/ie.css" />
			<![endif]-->
	</head>
<body>
	<div class="abs" id="wrapper">
		<div class="abs" id="desktop">
			<jsp:include page="desktop-shortcuts.jsp"></jsp:include><!-- 快捷方式桌面菜单 begin -->
			<jsp:include page="desktop-center-setmenu.jsp"></jsp:include><!-- 快捷方式弹出窗口主体 begin   -->
		</div>
			<jsp:include page="desktop-menu.jsp"></jsp:include><!-- 头部菜单 begin -->
			<jsp:include page="desktop-taskbar.jsp"></jsp:include><!-- 底部任务栏 -->
	</div>
</body>
</html>