<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>jdbc分页测试</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script>
		$(function(){
            $('#bd_datasourcelist_table').datagrid({
                iconCls: 'icon-save',
                //width:1000,
                height: 300,
                nowrap: false,
                striped: true,
                autoFit: true,
               // url: 'datasource/dataSource!list.action',
                url: 'pageing/pageing!list.action',
                sortName: 'dsIpadress',
                sortOrder: 'dsIpadress',
                idField: 'dsId',
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'dsName',title: '数据源名称',width: 150,sortable: true,align:'left'},
                	{field: 'dsType',title: '数据源类型',width: 100,sortable: true,align:'center'},
                	{field: 'dsIpadress',title: '服务器地址',width: 120,align:'center'},
                	{field: 'dsPort',title: '端口号',width: 80,align:'center'},
                	{field: 'dsUsername',title: '用户名',width: 150,align:'center'},
                	{field: 'dsState',title: '状态',width: 100,sortable: true,align:'center'},
                	{field: 'operate',title: '操作',width: 120,align:'center',
	                    formatter:function(value,rec){
						    var links = '<a href="javascript:;" onclick=bdDataSourceView("' + rec.id + '")>查看</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
						    links += '<a href="javascript:;" onclick=bdDataSourceUpdate("' + rec.id + '")>修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
						    links += '<a href="javascript:;" onclick=bdDataSourceDelete("' + rec.id + '")>删除</a>';
							return links;
						}
					}
				]],
                pagination: true,
                rownumbers: true,
                singleSelect: true,
                toolbar: ['-', 
		  			{text: '添加',iconCls: 'icon-add',handler: bdDataSourceAdd},
		  			'-', 
		  			{text: '删除',iconCls: 'icon-remove',handler: bdDataSourceDelete}, 
		  			'-' ]
            });
            $('#bd_datasource_add').appendTo('body').window({
                title: "添加数据源",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 420
            });
            $('#bd_datasource_update').appendTo('body').window({
                title: "修改数据源",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 420
            });
            $('#bd_datasource_view').appendTo('body').window({
                title: "查看数据源",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 420
            });
            function bdDataSourceAdd(){
            	$.post("datasource/dataSource!toAdd.action",{},
            		function(data){
		          		$("#bd_datasource_add").html(data);
		          		$("#bd_datasource_add").window('open');
		           		initDataSourceTypeAdd();
          			}
          		);
            }           
        });
        function bdDataSourceView(dataSourceId){
        	$.post("datasource/dataSource!view.action",{"dataSourceId":dataSourceId},
        		function(data){
	        		$("#bd_datasource_view").html(data);
	        		$("#bd_datasource_view").window('open');
      			}
      		);
        }
        function bdDataSourceDelete(dataSourceId){
        	if(dataSourceId && dataSourceId != ""){
        		$.messager.confirm('提示', '确认删除吗？',function(a){
	         		if(a)$.post("datasource/dataSource!delete.action",{"dataSourceId":dataSourceId},bdDataSourceDeleteRollback);
	         	});
        	}else{
         		var selected = $('#bd_datasourcelist_table').datagrid('getSelections');
	            if (selected == null || selected.length < 1) {
	                $.messager.alert('提示', '请选择数据!', 'warning');
	            } else {
	            	$.messager.confirm('提示', '确认删除吗？',function(a){
	            		if(a){
		            		var dataSourceIds = "";
		            		for(var i=0;i<selected.length;i++){
		            			if(dataSourceIds == ""){
		            				dataSourceIds += "'" + selected[i]['id'] + "'"
		            			}else{
		            				dataSourceIds += ",'" + selected[i]['id'] + "'"
		            			}
		                	}
		                	$.post("datasource/dataSource!delete.action",{"dataSourceIds":dataSourceIds},bdDataSourceDeleteRollback);
	                	}
	            	});	               
	            }
            }
        }
        function bdDataSourceDeleteRollback(data){
        	if("success" == data){
   				$.messager.alert('提示','删除成功','info');			         				
   				$('#bd_datasourcelist_table').datagrid('reload');
   			}
        }
        function bdDataSourceUpdate(dataSourceId){
        	$.post("datasource/dataSource!toUpdate.action",{"dataSourceId":dataSourceId},
        		function(data){
	        		$("#bd_datasource_update").html(data);
	        		$("#bd_datasource_update").window('open');
	        		initDataSourceEditData();
      			}
      		);
        }
    </script>
  </head>  
  <body>
  	<div class="navigation1">&nbsp;>>&nbsp;Pageing test</div>
  	<div id="bd_datasourcelist_panel" title="数据源列表" class="easyui-panel"  border="false" collapsible="true" class="main_panel">
		<table id="bd_datasourcelist_table"></table>      	
	</div>
	<div id="bd_datasource_add" class="easyui-window" style="padding:10px;">      	
    </div>
	<div id="bd_datasource_view" class="easyui-window" style="padding:10px;">      	
    </div>
	<div id="bd_datasource_update" class="easyui-window" style="padding:10px;">      	
    </div>
  </body>
</html>
