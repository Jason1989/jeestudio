<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>构件平台</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="css/easyui.css">
	<link rel="stylesheet" type="text/css" href="css/icon.css">
	<link rel="stylesheet" type="text/css" href="css/image.css">
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/XmlUtils.js"></script>
	<script>		
		$(function(){
			$('#mywin').window('close');
			$('#myFormulawin').window('close');
			$('#myCachewin').window('close');
			/*
			var p = $('body').layout('panel','west').panel({
				onCollapse:function(){
					
				}
			});
			
			var p = $('body').layout('panel','east').panel({
				onCollapse:function(){
					$("#main").refresh();
				},
				onExpand:function(){
					$("#main").refresh();
				}				
				
			});
			*/
			
			
			setTimeout(function(){
				$('body').layout('collapse','east');
			},0);
		});
		
		function clearDom() {
			$("div.window-mask ~ div").remove();
		}
		
		var index = 0;
		function initForm(){
			index++;
			$('#c_tabs1').tabs('add',{
				title:'New Tab ' + index,
				content:'Tab Body ' + index,
				closable:true
			});
		}		
	</script>
	
</head>
<body class="easyui-layout">
	<div id="header"   region="north" title="工具栏" border="false"  split="true" style="height:90px;overflow:hidden;" href="pages/top.jsp"></div>
	<div id="contents" region="west" split="true" title="菜单栏" style="width:180px;" href="pages/left.jsp"></div>
	<div id="main"     region="center" style="width:400px;overflow:hidden;" href="pages/pageing.jsp"></div>
	<div id="footer"   region="south" width="100%" border="false"  split="false" style="height:35px;" href="pages/footer.jsp"></div>
	<div id="state"    region="east" split="true" title="状态栏" style="width:250px;" href="pages/state.jsp"></div>
  </body>
</html>
