<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>字段导入</title>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script type="text/javascript" src="js/basedata.common.js"></script>
	<script>
		$(function(){
			$('#db_datacolumn_import_inuse').datagrid({
				title:'就绪字段列表',
                //width:1000,
                height: 500,
                nowrap: false,
                striped: true,
				headerCls:"header_cls",
                autoFit: true,
                queryParams:{dataTableId:'${dataTableId}',isTemp:'0'},
                url: 'datacolumn/dataColumn!toimport.action',
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'id',
                pageSize:10,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'title',title: '字段标题',width: 150,sortable: true,align:'left',
                	    formatter:function(value,row,all){
                	        if ('1' == row.isPrimaryKey) return '<img src="jquery-easyui-1.1.2/themes/icons/icon_group_key.gif">&nbsp;&nbsp;&nbsp;&nbsp;'+value;
							else if ('0' == row.isPrimaryKey) return value;
							return value;
                	    }
                	
                	},
                	{field: 'name',title: '名称',width: 150,sortable: true,align:'left'},
                	{field: 'dataType',title: '字段类型',width: 100,sortable: true,align:'center',
                		formatter:function(value){
                			for(var i=0; i<dataColumnDataTypeData.length; i++){
								if (dataColumnDataTypeData[i].id == value) return dataColumnDataTypeData[i].text;
							}
							return value;
						}
                	},
                 	{field: 'dataLength',title: '长度',width: 50,align:'center',
	                 	formatter:function(value){
								if ('0' == value) return '';
								return value;
							}
                 	},
                	{field: 'precision',title: '精度',width: 50,align:'center',
	                	formatter:function(value){
								if ('0' == value) return '';
								return value;
							}
                	},
                	/*{field: 'isPrimaryKey',title: '是否主键',width: 60,align:'center',
                		formatter:function(value,row,all){
							if ('1' == value) return '是';
							else if ('0' == value) return '否';
							return value;
					}},*/
                	{field: 'nullable',title: '是否为空',width:60,align:'center',
                		formatter:function(value){
							if ('0' == value) return '是';
							else if ('1' == value) return '否';
							else if ('Y' == value) return '是';
							else if ('N' == value) return '否';
							return value;
						}},
                	{field: 'defaultValue',title: '默认值',width: 150,align:'center',formatter:function(data){
                	     if(data == null||data==""){
                	         return "无";
                	     }
                	     return data;
                	}},
                	{field: 'description',title: '字段说明',width: 150,align:'center'},
                	{field: 'operate',title: '操作',width: 120,align:'center',
	                    formatter:function(value,rec,rowIndex){
						    var links = '<a href="javascript:;" onclick=bdDataColumnUpdate("' + rec.id + '")>修改</a>&nbsp;&nbsp;';
						    var dataTableId=rec.dataTable.id+'';
						    
						    if(!rec['isPrimaryKey']=='1'){
							    links += '|&nbsp;&nbsp;<a href="javascript:;" onclick=bdDataColumnUnuse("' + rec.id+'","'+rowIndex+ '","'+dataTableId+ '")><img src="css/icons/arrow_down.png" alt="移除"></a>';
						    }
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
                          {text:'批量下移',iconCls:'icon-group-down',handler:dbDataColumnPatchDown},
      		  			//'-', 
      		  			//{text: '删除',iconCls: 'icon-remove'}, 
      		  			//'-', 
      		  			//{text: '检查',iconCls: 'icon-search',handler: bdDataColumnSynchronous}, 
		  			'-' ]
			});
			
			
			$('#db_datacolumn_import_unuse').datagrid({
				title:'暂存字段列表',
				nowrap: false,
				striped: true,
                queryParams:{dataTableId:'${dataTableId}',isTemp:'1'},
                url: 'datacolumn/dataColumn!toimport.action',
				sortName: 'code',
				sortOrder: 'desc',
				headerCls:"header_cls",
				remoteSort: false,
				idField:'id',
                pageSize:10,
				frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'title',title: '字段标题',width: 150,sortable: true,align:'left',
                	formatter:function(value,row,all){
                	        if ('1' == row.isPrimaryKey) return '<img src="jquery-easyui-1.1.2/themes/icons/icon_group_key.gif">&nbsp;&nbsp;&nbsp;&nbsp;'+value;
							else if ('0' == row.isPrimaryKey) return value;
							return value;
                	    }},
                	{field: 'name',title: '名称',width: 150,sortable: true,align:'left'},
                	{field: 'dataType',title: '字段类型',width: 100,sortable: true,align:'center',
                		formatter:function(value){
                			for(var i=0; i<dataColumnDataTypeData.length; i++){
								if (dataColumnDataTypeData[i].id == value) return dataColumnDataTypeData[i].text;
							}
							return value;
						}
                	},
                	{field: 'dataLength',title: '长度',width: 50,align:'center',
                		formatter:function(value){
							if ('0' == value) return '';
							return value;
						}
                	},
                	{field: 'precision',title: '精度',width: 50,align:'center',
                		formatter:function(value){
							if ('0' == value) return '';
							return value;
						}
                	},
                	{field: 'nullable',title: '是否为空',width: 60,align:'center',
                		formatter:function(value){
							if ('1' == value) return '是';
							else if ('0' == value) return '否';
							return value;
						}},
                	{field: 'defaultValue',title: '默认值',width: 150,align:'center',formatter:function(data){
                	     if(data == null||data==""){
                	         return "无";
                	     }
                	     return data;
                	}},
                	{field: 'description',title: '字段说明',width: 150,align:'center'},
                	{field: 'operate',title: '操作',width: 120,align:'center',
	                    formatter:function(value,rec,rowIndex){
						    var links = '<a href="javascript:;" onclick=bdDataColumnDelete("' + rec.id + '")>删除</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
						    links += '<a href="javascript:;" onclick=bdDataColumnInuse("'+ rec.id+'","'+rowIndex+ '")><img src="css/icons/arrow_up.png" alt="移除"></a>';
						    if(value == 'true'){	
						    	//$('#bd_datacolumnlist_table').datagrid("selectRow",rowIndex);					    	
						    	links += '&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript:;" onclick=bdDataColumnSynchronousRow("' + rec.id + '",' + rowIndex + ')>同步</a>';
						    }
							return links;
						}
					}
				]],
				pagination:true,
				rownumbers:true,
				toolbar: [
      		  			'-', 
      		  			{text: '删除',iconCls: 'icon-remove',handler:bdDataColumnDelete}, 
      		  			'-', 
      		  			{text: '检查',iconCls: 'icon-search',handler: bdDataColumnSynchronous1}, 
      		  			'-', 
                          {text:'批量上移',iconCls:'icon-group-up',handler:dbDataColumnPatchUp},
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
                width: 680,
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
                width: 680,
                height: 420
            });
		});
		// 添加 
		 function bdDataColumnAdd(){
            	$.post("datacolumn/dataColumn!toAdd.action",{"dataTableId":'${dataTableId}'},
            		function(data){
		          		$("#bd_datacolumn_add").html(data);
		          		$("#bd_datacolumn_add").window('open');
		          		initDataColumnAddData();
          			}
          		);
            }

         //同步
         var db_datacolumn_import_unuse = $('#db_datacolumn_import_unuse');
          function bdDataColumnSynchronous(){
            	$.post("datacolumn/dataColumn!synchronous.action",{"dataTableId":'${dataTableId}'},
            		function(data){
            			var obj = eval(data);
		          		if(obj && obj != null){
		          			for(var i=0;i<obj.length;i++){
		          				db_datacolumn_import_unuse.datagrid("selectRecord",obj[i]);
		          			}
		          			var tableSelected = db_datacolumn_import_unuse.datagrid("getSelections");
							if(tableSelected && tableSelected.length > 0){
								for(var j=0;j<tableSelected.length;j++){
									tableSelected[j].operate = 'true';									
								}
								var rows = db_datacolumn_import_unuse.datagrid("getRows");
								if(rows){
									for(var k=0;k<rows.length;k++){
										db_datacolumn_import_unuse.datagrid("refreshRow",k);
									}
								}
								
							}
							
		          		}
          			}
          		);
            }
          function bdDataColumnSynchronous1(){
          	$.post("datacolumn/dataColumn!synchronousColumn.action",{"dataTableId":'${dataTableId}'},
          		function(data){
          		  if("success" == data){
          				db_datacolumn_import_unuse.datagrid('reload');
                     }
        		}
        	);
          }     
		//删除
		function bdDataColumnDelete(dataColumnId){
        	if(dataColumnId && dataColumnId != ""){
        		$.messager.confirm('提示', '确认删除吗？',function(a){
	         		if(a)$.post("datacolumn/dataColumn!delete.action",{"dataColumnId":dataColumnId},function(data){
	         			if("success" == data){
	           				db_datacolumn_import_unuse.datagrid('reload');
	           			}
		         	});
	         	});
        	}else{
         		var selected = $('#db_datacolumn_import_unuse').datagrid('getSelections');
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
		                	
		                	$.post("datacolumn/dataColumn!delete.action",{"dataColumnIds":dataColumnIds},function(data){
		                		if("success" == data){
			           				db_datacolumn_import_unuse.datagrid('reload');
			           			}
			                });
	                	}
	            	});	               
	            }
            }
        }

        
		//同步数据到数据库中
          function bdDataColumnSynchronousRow(dataColumnId,rowIndex){
  			
  			$.post("datacolumn/dataColumn!synchronousUpdate.action",{"dataColumnId":dataColumnId},
             		function(data){           			
  					$.post("datacolumn/dataColumn!getDataColumnInfo.action",{"dataColumnId":dataColumnId},
  		           		function(data){
  		           			if(data && data != null){ 
  		           				var obj = eval(data);        				
  								var rows = db_datacolumn_import_unuse.datagrid("getRows");
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
  											db_datacolumn_import_unuse.datagrid("selectRecord",rows[i].id);
  										}
  									}
  								}
  								db_datacolumn_import_unuse.datagrid("unselectRow",rowIndex);								
  								db_datacolumn_import_unuse.datagrid("refreshRow",rowIndex);
  		           			}
  		           		});
             		});
  		}           
	//应用某个字段（目前只考虑增加数据字段的情况）
		function bdDataColumnInuse(dataColumnId,index){
        	if(dataColumnId && dataColumnId != ""){
      			   //同步改变相应的表单结构
      			   $.post('form/form!changeFormFrame.action',{dataObjectId:'${dataTableId}',columnId:dataColumnId},function(reData){
      			   		if(reData == "success"){
			         	$.post("datacolumn/dataColumn!updateTemp.action",{dataColumnId:dataColumnId,isTemp:'0'},function(data){
				         	var rows = $("#db_datacolumn_import_unuse").datagrid("getRows");
				         	$("#db_datacolumn_import_inuse").datagrid('appendRow',rows[index]);//增加一行
				         	$("#db_datacolumn_import_inuse").datagrid('acceptChanges');
				         	$("#db_datacolumn_import_unuse").datagrid('deleteRow',index);//删除一行
				         	$("#db_datacolumn_import_unuse").datagrid('acceptChanges');
				         });
      			   		}else{
      			   			$.messager.alert('提示','系统出错，请稍候再试……','warnning');
      			   		}
        		});
	        }
        }
        //将某个字段移除
		function bdDataColumnUnuse(dataColumnId,index,dataTableId){
        	if(dataColumnId && dataColumnId != ""){
	         	$.post("datacolumn/dataColumn!updateTemp.action",{dataColumnId:dataColumnId,isTemp:'1',dataTableId:dataTableId},function(data){
	         		var rows = $("#db_datacolumn_import_inuse").datagrid("getRows");
		         	$("#db_datacolumn_import_unuse").datagrid('appendRow',rows[index]);//增加一行
		         	$("#db_datacolumn_import_unuse").datagrid('acceptChanges');
		         	$("#db_datacolumn_import_inuse").datagrid('deleteRow',index);//删除一行
		         	$("#db_datacolumn_import_inuse").datagrid('acceptChanges');
		        });
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
        //批量上移
        function dbDataColumnPatchUp(){
        	var selections = $("#db_datacolumn_import_unuse").datagrid("getSelections");
        	if(selections.length<=0){
        		$.messager.alert("提示","请选择数据","info");
        		return false;
        	}
        	var selectedIds = "";
        	for(var i=0;i<selections.length;i++){
        		selectedIds = selectedIds + selections[i].id+",";
        	}
        	
        	$.post("datacolumn/dataColumn!toBatch.action",{ids:selectedIds.substring(0,(selectedIds.length-1)),isTemp:'0'},function(data){
        		if(data=="success"){
        			$("#db_datacolumn_import_inuse").datagrid("load").datagrid("clearSelections");
        			$("#db_datacolumn_import_unuse").datagrid("load").datagrid("clearSelections");
        		}else{
        			$.messager.alert("提示","服务器出错，请稍后再试……","warning");
        			$("#db_datacolumn_import_inuse").datagrid("clearSelections");
        			$("#db_datacolumn_import_unuse").datagrid("clearSelections");
        		}
        	});
        }
        //批量下移
        function dbDataColumnPatchDown(){
        	var selections = $("#db_datacolumn_import_inuse").datagrid("getSelections");
        	if(selections.length<=0){
        		$.messager.alert("提示","请选择数据","info");
        		return false;
        	}
        	var selectedIds = "";
        	for(var i=0;i<selections.length;i++){
        		//如果是主键，则不能被下移
        		if(selections[i].isPrimaryKey == '1'){
        			continue;
        		}
        		selectedIds = selectedIds + selections[i].id+",";
        	}
        	$.post("datacolumn/dataColumn!toBatch.action",{ids:selectedIds.substring(0,(selectedIds.length-1)),isTemp:'1'},function(data){
        		if(data=="success"){
        			$("#db_datacolumn_import_inuse").datagrid("load").datagrid("clearSelections");
        			$("#db_datacolumn_import_unuse").datagrid("load").datagrid("clearSelections");
        		}else{
        			$.messager.alert("提示","服务器出错，请稍后再试……","warn");
        			$("#db_datacolumn_import_inuse").datagrid("clearSelections");
        			$("#db_datacolumn_import_unuse").datagrid("clearSelections");
        		}
        	});
        }
	</script>
  </head>
  

<body>

		<div class="easyui-layout" fit="true" border="false">
			<div region="center" border="false" fit="true" border="false">
				<div class="easyui-panel" fit="true">
					<div class="easyui-layout" fit="true" border="false">
						<div region="center" border="false">
							<div class="easyui-panel" fit="true" border="false">
								<table id="db_datacolumn_import_unuse" fit="true" border="false"></table>
							</div>
						</div>
						<div region="north" split="true" border="false" style="height: 300px;"
							border="false">
							<div class="easyui-panel" fit="true" border="false">
								<table id="db_datacolumn_import_inuse" fit="true" border="false"></table>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
		<div id="bd_datacolumn_add" class="easyui-window" style="padding:10px;">      	
	    </div>
		<div id="bd_datacolumn_update" class="easyui-window" style="padding:10px;">      	
	    </div>
	</body>
</html>
