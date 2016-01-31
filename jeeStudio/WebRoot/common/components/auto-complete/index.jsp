<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="common/components/auto-complete/js/jquery/ddcombo/jquery.ddcombo.css" />
	
	<script type="text/javascript" src="jquery-easyui-1.1.2/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="common/components/auto-complete/js/jquery/ddcombo/lib/jquery.ready.js"></script>
	<script type="text/javascript" src="common/components/auto-complete/js/jquery/ddcombo/lib/jquery.flydom-3.1.1.js"></script>
	<script type="text/javascript" src="common/components/auto-complete/js/jquery/ddcombo/lib/autocomplete/jquery.bgiframe.min.js"></script>
	<script type="text/javascript" src="common/components/auto-complete/js/jquery/ddcombo/lib/autocomplete/jquery.dimensions.js"></script>
	<script type="text/javascript" src="common/components/auto-complete/js/jquery/ddcombo/lib/autocomplete/jquery.ajaxQueue.js"></script>
	<script type="text/javascript" src="common/components/auto-complete/js/jquery/ddcombo/lib/autocomplete/thickbox-compressed.js"></script>
	<script type="text/javascript" src="common/components/auto-complete/js/jquery/ddcombo/jquery.ddcombo.js"></script>
	<script type="text/javascript" src="common/components/auto-complete/js/jquery/main.js"></script>

  </head>
  
  <body>
  <script type="text/javascript">
  	function change(){
  		
  		  $("#ddcombo").ddcombo({
		    minChars: 0,
		    options: ['1', '2', '3']
		  });
  	}
  	
  	function change_1(){
  		$("#ddcombo").options=['4', '5', '6'];
  	
  	}
  </script>
 		<input  type="button" value="test" onclick="change();"   />
 			<input  type="button" value="test1" onclick="change_1();"   />
 		<div id="ddcombo" ></div>
  </body>
</html>
