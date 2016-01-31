<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<link rel="stylesheet" href="<%= request.getContextPath()%>/common/////themes/base/jquery.ui.all.css">
	<script src="../../jquery-1.4.4.js"></script>
	<script src="../../ui/jquery.ui.core.js"></script>
	<script src="../../ui/jquery.ui.widget.js"></script>
	<script src="../../ui/jquery.ui.position.js"></script>
	<script src="../../ui/jquery.ui.autocomplete.js"></script>
	<link rel="stylesheet" href="../demos.css">
	<link rel="stylesheet" href="css/custom-data.css">

  </head>
  
  <body>
  
  
  
  <script>
	$(function() {
		var projects = [
			{
				value: "jquery",
				label: "jQuery",
				desc: "the write less, do more, JavaScript library",
				icon: "jquery_32x32.png"
			},
			{
				value: "jquery-ui",
				label: "jQuery UI",
				desc: "the official user interface library for jQuery",
				icon: "jqueryui_32x32.png"
			},
			{
				value: "sizzlejs",
				label: "Sizzle JS",
				desc: "a pure-JavaScript CSS selector engine",
				icon: "sizzlejs_32x32.png"
			}
		];

		$( "#project" ).autocomplete({
			minLength: 0,
			source: projects,
			focus: function( event, ui ) {
				$( "#project" ).val( ui.item.label );
				return false;
			},
			select: function( event, ui ) {
				$( "#project" ).val( ui.item.label );
				$( "#project-id" ).val( ui.item.value );
				$( "#project-description" ).html( ui.item.desc );
				$( "#project-icon" ).attr( "src", "images/" + ui.item.icon );

				return false;
			}
		})
		.data( "autocomplete" )._renderItem = function( ul, item ) {
			return $( "<li></li>" )
				.data( "item.autocomplete", item )
				.append( "<a>" + item.label + "<br>" + item.desc + "</a>" )
				.appendTo( ul );
		};
	});
	</script>


<div class="demo">
	<div id="project-label">Select a project (type "j" for a start):</div>
	<img id="project-icon" src="images/transparent_1x1.png" class="ui-state-default"/>
	<input id="project"/>
	<input type="hidden" id="project-id"/>
	<p id="project-description"></p>
</div><!-- End demo -->



<div class="demo-description">
<p>You can use your own custom data formats and displays by simply overriding the default focus and select actions.</p>
<p>Try typing "j" to get a list of projects or just press the down arrow.</p>
</div><!-- End demo-description -->
  
  </body>
</html>
