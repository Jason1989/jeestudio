<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="<%=basePath%>">
		<title>CACHE测试</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script>
	$(function() {
		$('#bd_datasourcelist_table').datagrid( {
			iconCls : 'icon-save',
			height : 400,
			nowrap : false,
			striped : true,
			autoFit : true,
			url : 'cache/cacheing!list.action',
			idField : 'key',
			columns : [ [ {
				field : 'cacheName',
				title : '缓存名称',
				width : 60,
				sortable : true,
				align : 'left'
			}, {
				field : 'key',
				title : '缓存Key',
				width : 450,
				sortable : true,
				align : 'left'
			}, {
				field : 'valueType',
				title : '缓存数据类型 ',
				width : 100,
				align : 'left'
			}, {
				field : 'creationTime',
				title : '创建时间',
				width : 120,
				align : 'left'
			}, {
				field : 'lastUpdateTime',
				title : '更新时间',
				width : 120,
				align : 'left'
			}, {
				field : 'valueSize',
				title : '空间占用 ',
				width : 70,
				align : 'left',
				formatter:function(value){
					
						return '<span style="color: red;" >'+value+'</span> &nbsp; KB';
					}
			} ] ],
			pagination : true,
			rownumbers : true,
			singleSelect : true,
			toolbar : [ '-', {
				text : '手动刷新缓存',
				iconCls : 'icon-reload',
				handler : freshCache
			}, '-' ]
		});



	});
	
	  function freshCache() {
	  		$.ajax({
			    url: 'cache/cacheing!freshAll.action?date='+new Date(),
			    type: 'post',
			    success: function(data){
			    	$('#bd_datasourcelist_table').datagrid('reload');
				}
			});
		}
	
	

	function bdDataSourceView(dataSourceId) {
		$.post("datasource/dataSource!view.action", {
			"dataSourceId" : dataSourceId
		}, function(data) {
			$("#bd_datasource_view").html(data);
			$("#bd_datasource_view").window('open');
		});
	}

	function bdDataSourceDeleteRollback(data) {
		if ("success" == data) {
			$.messager.alert('提示', '删除成功', 'info');
			$('#bd_datasourcelist_table').datagrid('reload');
		}
	}

	function bdDataSourceUpdate(dataSourceId) {
		$.post("datasource/dataSource!toUpdate.action", {
			"dataSourceId" : dataSourceId
		}, function(data) {
			$("#bd_datasource_update").html(data);
			$("#bd_datasource_update").window('open');
			initDataSourceEditData();
		});
	}
</script>
	</head>
	<body>
		<div id="bd_datasourcelist_panel3" title="列表" class="easyui-panel"
			border="false" collapsible="true" class="main_panel">
			<table id="bd_datasourcelist_table"></table>
		</div>
	</body>
</html>
