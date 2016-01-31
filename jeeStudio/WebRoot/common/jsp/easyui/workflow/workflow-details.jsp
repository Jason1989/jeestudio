<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%
String path = request.getContextPath();
request.setAttribute("listPageRander",RandomGUID.geneGuid());//生成表单列ID
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
			$('#grid_${listPageRander}').datagrid({
	      		iconCls: 'icon-grid',
				nowrap: false,
				fit: true,
				striped: true, 
				fitColumns: true,
				rownumbers:true,
				url: '<%=path%>/workflow/LogWorkFlow!workFlowDetails.action?APP_ID=${APP_ID}&time='+new Date().getTime(),
				showFooter:true,
			    pageList:[6,9,12],
			    page: 1,
				columns:[[
					{field:'uname',title:'操作人',width:1,align:'center'},
					{field:'deal_time',title:'操作时间',width:2,align:'center'},
					{field:'pioneer_operate',title:'前驱操作',width:1,align:'center'},
					{field:'pioneer_status',title:'前驱状态',width:1,align:'center'}
				]],
				pagination:true
			});
		});
	</script>
  </head>
  
  <body>
    <div class="easyui-panel" fit="true">
    	<table id="grid_${listPageRander}"></table>
    </div>
  </body>
</html>
