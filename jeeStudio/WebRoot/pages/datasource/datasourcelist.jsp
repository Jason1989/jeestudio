<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>数据源管理</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/basedata.common.js"></script>
	<script type="text/javascript" src="js/page-ext.js"></script>
	<script>
		$(function(){
		
				$('#dataSourceTypeAdd').combobox({		
				data:dataSourceTypeData,
				valueField:'id',
				textField:'text',
				editable:false
			});	
			
			
         	$('#bd_datasourcelist_table').datagrid({
                iconCls: 'icon-database-table',
                //width:1000,
                height: 400,
                nowrap: false,
                striped: true,
				fit: true,
				border:false,
				fitColumns:true,
                url: 'datasource/dataSource!list.action',
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'id',
                pageSize:20,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'name',title: '数据源名称',width: 150,sortable: true,align:'left'},
                	{field: 'dbType',title: '数据源类型',width: 100,sortable: true,align:'center',
                		formatter:function(value){
                			for(var i=0; i<dataSourceTypeData.length; i++){
								if (dataSourceTypeData[i].id == value) return dataSourceTypeData[i].text;
							}
							return value;
						}},
                	{field: 'ipAddress',title: '服务器地址',width: 120,align:'center'},
                	{field: 'sid',title: '数据库实例',width: 150,align:'center'},
                	{field: 'port',title: '端口号',width: 80,align:'center',
                		formatter:function(value){
							if ('0' == value) return '';
							return value;
						}},
                	{field: 'username',title: '用户名',width: 150},
                	{field: 'state',title: '状态',width: 100,sortable: true,align:'center',
                		formatter:function(value){
							if ('1' == value) return '可用';
							return '不可用';
						}},
                	{field: 'operate',title: '操作',width: 150,align:'center',
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
                //singleSelect: true,
                toolbar: ['-', 
		  			{text: '添加',iconCls: 'icon-add',handler: bdDataSourceAdd}
		  			// ,'-'
		  			// ,{text: '删除',iconCls: 'icon-remove',handler: bdDataSourceDelete}
		  			// ,'-'
		  			]
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
            		   $("#bd_datasourceadd_form").form("clear");
		          		//$("#bd_datasource_add").html(data);
		          		$("#bd_datasource_add").window('open');
		          		$("#stateUsed").attr("checked","checked");
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
        	   	   $.post("datasource/dataSource!checkDataSourceIsUse.action",{"dataSourceId":dataSourceId},function (data){
         				if("use"==data){
			         			$.messager.confirm('提示', '数据源已被使用。确认删除吗？',function(a){
				         		 		if(a)$.post("datasource/dataSource!delete.action",{"dataSourceId":dataSourceId},bdDataSourceDeleteRollback);
					         	});
				         }else{
				        	 	$.messager.confirm('提示', '确认删除该数据源？',function(a){
			         		 			if(a)$.post("datasource/dataSource!delete.action",{"dataSourceId":dataSourceId},bdDataSourceDeleteRollback);
				         		});	
				         }
	         		});
	         }else{
         		/**
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
	            **/
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
  	<div id="bd_datasourcelist_panel" title="数据源列表" icon="icon-database-table" class="easyui-panel" fit="true" border="false" collapsible="false" class="main_panel">
		<table id="bd_datasourcelist_table" ></table>      	
	</div>
	<div id="bd_datasource_add" class="easyui-window" style="padding:10px;">   
	<jsp:include page="/pages/datasource/datasourceadd.jsp"></jsp:include>   	
    </div>
	<div id="bd_datasource_view" class="easyui-window" style="padding:10px;">      	
    </div>
	<div id="bd_datasource_update" class="easyui-window" style="padding:10px;">      	
    </div>
  </body>
</html>
