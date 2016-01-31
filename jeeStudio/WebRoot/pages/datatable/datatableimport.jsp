<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
    <title>添加</title>    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript" type="text/javascript">
		$(function(){
			$('#bd_datatableimport_tables').datagrid({
				width:215,
				height: 350,
				nowrap: false,
				striped: true,
				autoFit: true,
				idField:'id',
				///url: 'datatable/dataTable!toImport.action',
				frozenColumns: [[{
					field: 'ck',
					checkbox: true
					
				}]],
				columns: [[
					{field: 'name',title: '表名',width: 150,sortable: true}
				]],
				rownumbers: true,
				singleSelect: true
			});
			//$('#bd_datatableimport_tables').datagrid('loadData',);
		});		
    </script>
  </head>  
  <body>
	<form id="bd_datatableimport_form" method="post" action="datatable/dataTable!import.action">
		<table id="bd_datatableimport_tables"></table>
	</form>
  </body>
</html>
