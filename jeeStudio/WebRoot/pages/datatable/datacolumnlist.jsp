<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>数据列管理</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script>
		$(function(){
            $('#bd_datacolumnlist_table').datagrid({
                iconCls: 'icon-save',
                //width:1000,
                height: 400,
                nowrap: false,
                striped: true,
                autoFit: true,
                queryParams:{dataTableId:'${dataTableId}'},
                url: 'datacolumn/dataColumn!list.action',
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'id',
                pageSize:20,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'title',title: '字段标题',width: 150,sortable: true,align:'left'},
                	{field: 'name',title: '名称',width: 150,sortable: true,align:'left'},
                	{field: 'dataType',title: '字段类型',width: 100,sortable: true,align:'center',
                		formatter:function(value){
                			for(var i=0; i<dataColumnDataTypeData.length; i++){
								if (dataColumnDataTypeData[i].id == value) return dataColumnDataTypeData[i].text;
							}
							return value;
						}
                	},
                	{field: 'dataLength',title: '长度',width: 80,align:'right'},
                	{field: 'precision',title: '精度',width: 80,align:'right',
                		formatter:function(value){
                			if(value == 0){
                				return "";
                			}
                		}  
                	},
                	{field: 'scale',title: '级别',width: 80,align:'right',
                		formatter:function(value){
                			if(value == 0){
                				return "";
                			}
                			for(var i=0; i<dataColumnScaleData.length; i++){
								if (dataColumnScaleData[i].id == value) return dataColumnScaleData[i].text;
							}
							return value;
						}
                	},
                	{field: 'nullable',title: '是否为空',width: 80,align:'center',
                		formatter:function(value){
							if ('0' == value) return '是';
							else if ('1' == value) return '否';
							return value;
						}},
                	{field: 'defaultValue',title: '默认值',width: 150,align:'center'},
                	{field: 'operate',title: '操作',width: 120,align:'center',
	                    formatter:function(value,rec,rowIndex){
						    var links = '<a href="javascript:;" onclick=bdDataColumnUpdate("' + rec.id + '")>修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
						    links += '<a href="javascript:;" onclick=bdDataColumnDelete("' + rec.id + '")>删除</a>';
						    if(value == 'true'){	
						    	//$('#bd_datacolumnlist_table').datagrid("selectRow",rowIndex);					    	
						    	links += '&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript:;" onclick=bdDataColumnSynchronousRow("' + rec.id + '",' + rowIndex + ')>同步</a>';
						    }
							return links;
						}
					}
				]],
                pagination: true,
                rownumbers: true,
                //singleSelect: true,
                toolbar: ['-', 
		  			{text: '添加',iconCls: 'icon-add',handler: bdDataColumnAdd},
		  			'-', 
		  			{text: '删除',iconCls: 'icon-remove',handler: bdDataColumnDelete}, 
		  			'-', 
		  			{text: '检查',iconCls: 'icon-search',handler: bdDataColumnSynchronous}, 
		  			'-' ]
            });
            $('#bd_datacolumn_add').appendTo('body').window({
                title: "添加数据列",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 420
            });
            $('#bd_datacolumn_update').appendTo('body').window({
                title: "修改数据列",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 420
            });
            
            function bdDataColumnAdd(){
            	$.post("datacolumn/dataColumn!toAdd.action",{"dataTableId":'${dataTableId}'},
            		function(data){
		          		$("#bd_datacolumn_add").html(data);
		          		$("#bd_datacolumn_add").window('open');
		          		initDataColumnAddData();
          			}
          		);
            }
        });
            function bdDataColumnSynchronous(){
            	$.post("datacolumn/dataColumn!synchronous.action",{"dataTableId":'${dataTableId}'},
            		function(data){
            			var obj = eval(data);
		          		if(obj && obj != null){
		          			for(var i=0;i<obj.length;i++){
		          				$('#bd_datacolumnlist_table').datagrid("selectRecord",obj[i]);
		          			}
		          			var tableSelected = $('#bd_datacolumnlist_table').datagrid("getSelections");
							if(tableSelected && tableSelected.length > 0){
								for(var j=0;j<tableSelected.length;j++){
									tableSelected[j].operate = 'true';									
								}
								var rows = $('#bd_datacolumnlist_table').datagrid("getRows");
								if(rows){
									for(var k=0;k<rows.length;k++){
										$('#bd_datacolumnlist_table').datagrid("refreshRow",k);
									}
								}
								
							}
							
		          		}
          			}
          		);
            }           
		function bdDataColumnSynchronousRow(dataColumnId,rowIndex){
			
			$.post("datacolumn/dataColumn!synchronousUpdate.action",{"dataColumnId":dataColumnId},
           		function(data){           			
					$.post("datacolumn/dataColumn!getDataColumnInfo.action",{"dataColumnId":dataColumnId},
		           		function(data){
		           			if(data && data != null){ 
		           				var obj = eval(data);        				
								var rows = $('#bd_datacolumnlist_table').datagrid("getRows");
								if(rows){
									for(var i=0;i<rows.length;i++){
										if(rows[i].id == dataColumnId){
											rows[i].id = obj[0]['id'];
					           				rows[i].title = obj[0]['title'];
					           				rows[i].name = obj[0]['name'];
					           				rows[i].dataType = obj[0]['dataType'];
					           				rows[i].dataLength = obj[0]['dataLength'];
					           				rows[i].precision = obj[0]['precision'];
					           				rows[i].scale = obj[0]['scale'];
					           				rows[i].nullable = obj[0]['nullable'];
					           				rows[i].defaultValue =obj[0]['defaultValue'];
											rows[i].operate = 'false';
										}
										if(rows[i].operate == 'true'){
											$('#bd_datacolumnlist_table').datagrid("selectRecord",rows[i].id);
										}
									}
								}
								$('#bd_datacolumnlist_table').datagrid("unselectRow",rowIndex);								
								$('#bd_datacolumnlist_table').datagrid("refreshRow",rowIndex);
		           			}
		           		});
           		});
		}
        function bdDataColumnDelete(dataColumnId){
        	if(dataColumnId && dataColumnId != ""){
        		$.messager.confirm('提示', '确认删除吗？',function(a){
	         		if(a)$.post("datacolumn/dataColumn!delete.action",{"dataColumnId":dataColumnId},bdDataColumnDeleteRollback);
	         	});
        	}else{
         		var selected = $('#bd_datacolumnlist_table').datagrid('getSelections');
	            if (selected == null || selected.length < 1) {
	                $.messager.alert('提示', '请选择数据!', 'warning');
	            } else {
	            	$.messager.confirm('提示', '确认删除吗？',function(a){
	            		if(a){
		            		var dataColumnIds = "";
		            		for(var i=0;i<selected.length;i++){
		            			if(dataColumnIds == ""){
		            				dataColumnIds += "'" + selected[i]['id'] + "'"
		            			}else{
		            				dataColumnIds += ",'" + selected[i]['id'] + "'"
		            			}
		                	}
		                	$.post("datacolumn/dataColumn!delete.action",{"dataColumnIds":dataColumnIds},bdDataColumnDeleteRollback);
	                	}
	            	});	               
	            }
            }
        }
        function bdDataColumnDeleteRollback(data){
        	if("success" == data){
   				$.messager.alert('提示','删除成功','info');			         				
   				$('#bd_datacolumnlist_table').datagrid('reload');
   			}
        }
        function bdDataColumnUpdate(dataColumnId){
        	$.post("datacolumn/dataColumn!toUpdate.action",{"dataColumnId":dataColumnId},
        		function(data){
	        		$("#bd_datacolumn_update").html(data);
	        		$("#bd_datacolumn_update").window('open');
	        		initDataColumnEditData();
      			}
      		);
        }
    </script>
  </head>  
  <body>
  	<div id="bd_datacolumnlist_panel" class="easyui-panel" fit="true" border="false" collapsible="true" class="main_panel">
		<table id="bd_datacolumnlist_table" fit="true"></table>      	
	</div>
	<div id="bd_datacolumn_add" class="easyui-window" style="padding:10px;">      	
    </div>
	<div id="bd_datacolumn_update" class="easyui-window" style="padding:10px;">      	
    </div>
  </body>
</html>
