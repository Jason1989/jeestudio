<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>数据对象管理</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	<link rel="stylesheet" type="text/css" href="styles.css">
	<script type="text/javascript" src="js/basedata.common.js"></script>
	<script>
		$(function(){
            $('#bd_datatablelist_table').datagrid({
                iconCls: 'icon-save',
                //width:1000,
                height: 400,
                nowrap: false,
                striped: true,
                autoFit: true,
                fitColumns:true,
                url: 'datatable/dataTable!list.action?groupId=${groupId}',
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'id',
                pageSize:20,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'name',title: '名称',width: 150,sortable: true,align:'left'},
                	{field: 'dataSource',title: '数据源',width: 150,sortable: true,align:'left',formatter:function(value,rec){
						    if(rec.dataSource && rec.dataSource != null){
						    	return rec.dataSource.name;
						    }else{
								return "";
							}
						}},
                	{field: 'type',title: '对象类型',width: 100,align:'center',
                		formatter:function(value){
                			for(var i=0; i<dataObjectTypeData.length; i++){
								if (dataObjectTypeData[i].id == value) return dataObjectTypeData[i].text;
							}
							return value;
						}},
                	// {field: 'owner',title: '所有者',width: 100,align:'center'},
                   	/**
                   		{field: 'locked',title: '是否锁定',width: 80,align:'center',
                		 formatter:function(value){
							if ('0' == value) return '否';
							else if ('1' == value) return '是';
							return value;
						}},
					  
                   	{field: 'inused',title: '是否可用',width: 80,align:'center',
                		formatter:function(value){
							if ('1' == value) return '可用';
							else if ('2' == value) return '不可用';
							return value;
					}},  **/ 
			  	    {field: 'operate',title: '操作',width: 140,align:'center',
	                    formatter:function(value,rec){
						    var links = '<a href="javascript:;" onclick=bdDataColumnManagement("' + rec.id + '")>字段管理</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
						    links += '<a href="javascript:;" onclick=bdDataTableUpdate("' + rec.id + '")>修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
						    links += '<a href="javascript:;" onclick=bdDataTableDelete("' + rec.id + '")>删除</a>';
							return links;
						}
					}
				]],
                pagination: true,
                rownumbers: true,
               // singleSelect: true,
                toolbar: ['-', 
		  			{text: '添加数据对象分组',iconCls: 'icon-group-add',handler: bdDataObjectGroupAdd},
		  			'-', 
		  			{text: '修改数据对象分组',iconCls:'icon-group-edit',handler: bdDataObjectGroupUpdate},
		  			'-',
		  			{text: '删除数据对象分组',iconCls: 'icon-group-delete',handler: bdDataObjectGroupDelete}, 
		  			'->',
		  		
		  			{text: '数据库对象导入',iconCls: 'icon-databaseObjectAdd',handler:function(){
			          var node = $("#lt1").tree("getSelected");
	        	      if(node && node != null){
			  				$("#bd_databaseObject_import").window({'href':''});//防止再次打开的时候,重复加载,造成页面的混乱
			  				$("#bd_databaseObject_import").window('refresh');//先刷新再加载新的页面
			  				$("#bd_databaseObject_import").window({'href':'datatable/dataTable!toDbImport.action'});				
			  				$("#bd_databaseObject_import").window('open');
		  				}else{
				        	$.messager.alert('提示', '请选择分组!', 'warning');
					    }
			  		}},
		  			// '-', {text: '添加数据对象',iconCls: 'icon-add',handler: bdDataTableAdd},
		  			'-', {text: '删除数据对象',iconCls: 'icon-remove',handler: bdDataTableDelete}, 
		  			//'-', 
		  			//{text: '导入',iconCls: 'icon-edit',handler: bdDataTableImport}, 
		  			'-' ]
            });
            
            $('#bd_datatable_add').appendTo('body').window({
                title: "添加数据对象",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 300
            });
            $('#bd_datatable_import').appendTo('body').window({
                title: "导入数据对象",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 420
            });
            $('#bd_datatable_update').appendTo('body').window({
                title: "修改数据对象",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 250
            });            
            $('#bd_dataobjectgroup_add').appendTo('body').window({
                title: "添加分组",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 400,
                height: 260
            });            
            $('#bd_dataobjectgroup_update').appendTo('body').window({
                title: "修改分组",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 400,
                height: 260
            });
 			$('#bd_databaseObject_import').appendTo('body').window({
                title: "数据库对象导入",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 400
            });     
 			     
            $('#bd_datatable_search').window({
                title: "查询数据对象",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 400,
                height: 250
            });            
            function bdDataTableAdd(){
            	//$.post("datatable/dataTable!toAdd.action",{},
            	//	function(data){
		         // 		$("#bd_datatable_add").html(data);
		         // 		$("#bd_datatable_add").window('open');
		         // 		initDatatableAddData();
		        //  		$("#bd_datatable_add").window('refresh');
          		//	}
          		//);
          		
          		$("#bd_datatable_add").window({'href':''});//防止再次打开的时候,重复加载,造成页面的混乱
  				$("#bd_datatable_add").window('refresh');//先刷新再加载新的页面
  				$("#bd_datatable_add").window({'href':'datatable/dataTable!toAdd.action'});				
  				$("#bd_datatable_add").window('open');
            } 
            function bdDataTableImport(){
            	$.post("datatable/dataTable!toImport.action",{},
            		function(data){
		          		$("#bd_datatable_import").html(data);
		          		$("#bd_datatable_import").window('open');
          			}
          		);
            	
            }
            function bdDataTableSearch(){
            	//$("#bd_datatable_search").window('refresh');
            	//$("#bd_datatable_search").window({'href':'form/form!toSearch.action'});		
            	$("#bd_datatable_search").window('open');            	         	
            } 
           
            $('#bd_datatablesearch_type').combobox({		
				data:dataObjectTypeData,
				valueField:'id',
				textField:'text',
				listWidth:151,
				editable:false
			});         
			$('#bd_datatablesearch_type').combobox("setValue",'-1');
            $('#bd_datatable_search_sumbit').linkbutton();         
            $('#bd_datatable_search_cancel').linkbutton();         
        });
        function bdDataTableSearchSubmit(){
        	var name = $('#bd_datatablesearch_name').val();
        	var type = $('#bd_datatablesearch_type').combobox('getValue'); 
        	var pager = $('#bd_datatablelist_table').datagrid('getPager');
        	var pageNumer = 1;
        	var pageSize = 10;
			if (pager){
				pageNumer = $(pager).pagination('options').pageNumber;
				pageSize = $(pager).pagination('options').pageSize;
			}
        	$.post("datatable/dataTable!search.action",{groupId:'${groupId}',datatableName:name,datatableType:type,pageNumer:pageNumer,pageSize:pageSize},
          		function(data){
					if(data && data != ""){
						$('#bd_datatable_search').window('close');
						$('#bd_datatablelist_table').datagrid('loadData',eval('(' + data + ')'));
					}
       			}
       		);
        }
        function bdDataTableDelete(dataTableId){
        	if(dataTableId && dataTableId != ""&&typeof(dataTableId)=="string"){
        	   // var selections = $('#bd_datatablelist_table').datagrid('getSelections');
        	    //if(selections.length <= 0){
        	   //     $.messager.alert("提示","请选中表中的记录，再执行删除！",'warning');
        	  //     return ;
        	   // }
        		$.messager.confirm('提示', '确认删除吗？',function(a){
	         		if(a)$.post("datatable/dataTable!delete.action",{"dataTableId":dataTableId},function(data){
	         			if("success" == data){
			   				$.messager.alert('提示','删除成功','info');			         				
				   				$('#bd_datatablelist_table').datagrid('reload');
				   			} else if("children" == data){
				   				$.messager.confirm('提示','数据对象包含数据列，确认要删除吗？',function(a){
				   					if(a){
				   					
				   						$.post("form/form!deleteAllByDataTableId.action",{"dataTableId":dataTableId},function(data){
				   							
				   						});
				   					
				   						$.post("datatable/dataTable!deleteColumns.action",{"dataTableId":dataTableId},function(data){
				   						   if("success" ==  data){
				   						   		$.messager.alert('提示','删除成功','info');			         				
				   								$('#bd_datatablelist_table').datagrid('reload');
				   						   }else{
				   						   		$.messager.alert('提示','系统出错，请稍后再试！','info');
				   						   }
				   						});
				   					}
				   				});
				   			}
	         		});
	         	});
        	}else{
         		var selected = $('#bd_datatablelist_table').datagrid('getSelections');
         	    if (selected == null || selected.length < 1) {
	                $.messager.alert('提示', '请选择数据!', 'warning');
	            } else {
	            	$.messager.confirm('提示', '确认删除吗？',function(a){
	            		if(a){
		            		var dataTableIds = "";
		            		for(var i=0;i<selected.length;i++){
		            			if(dataTableIds == ""){
		            				dataTableIds += "'" + selected[i]['id'] + "'"
		            			}else{
		            				dataTableIds += ",'" + selected[i]['id'] + "'"
		            			}
		                	}
		                	$.post("datatable/dataTable!delete.action",{"dataTableIds":dataTableIds},bdDataTableDeleteRollback);
	                	}
	            	});	               
	            }
            }
        }
        function bdDataTableDeleteRollback(data){
        	if("success" == data){
   				$.messager.alert('提示','删除成功','info');			         				
   				$('#bd_datatablelist_table').datagrid('reload');
   			} else if("children" == data){
   				$.messager.alert('提示','数据对象包含数据列,无法删除','info');
   			}
        }
        function bdDataTableUpdate(dataTableId){
        	$.post("datatable/dataTable!toUpdate.action",{"dataTableId":dataTableId},
        		function(data){
	        		$("#bd_datatable_update").html(data);
	        		$("#bd_datatable_update").window('open');
	        		initDataTableEditData();
      			}
      		);
        }
        var dataTableOldId;
        function bdDataColumnManagement(dataTableId){
      		$('#bd_datatable_tabs').tabs('close','字段管理');
       		$('#bd_datatable_tabs').tabs('add',{
				title:'字段管理',
				href:'datacolumn/dataColumn!toImportList.action?dataTableId='+dataTableId,
				closable:true
			});
       		dataTableOldId=dataTableId;
        }
        function bdDataObjectGroupAdd(){
        	//$.post("datatable/dataObjectGroup!toAdd.action",{},
        	//	function(data){
	        //		$("#bd_dataobjectgroup_add").html(data);
	        //		$("#bd_dataobjectgroup_add").window('open');
	        //		initDataobjectgroupAddData();
      		//	}
      		//);
      		$("#bd_dataobjectgroup_add").window({'href':''});//防止再次打开的时候,重复加载,造成页面的混乱
			$("#bd_dataobjectgroup_add").window('refresh');//先刷新再加载新的页面
			$("#bd_dataobjectgroup_add").window({'href':'datatable/dataObjectGroup!toAdd.action'});	
			$("#bd_dataobjectgroup_add").window('open');
        }
        function bdDataObjectGroupUpdate(){
        	//var node = $("#dataobject_menu").tree("getSelected");
        	var node = $("#lt1").tree("getSelected");
        	
        	
        	if(node && node != null){
	        	//$.post("datatable/dataObjectGroup!toUpdate.action",{"dataObjectGroupId":node.id},
	        	//	function(data){
		        //		$("#bd_dataobjectgroup_update").html(data);
		        //		$("#bd_dataobjectgroup_update").window('open');
		        //		initDataobjectgroupUpdateData();
	      		//	}
	      		//);
	      		if(node.id==1){
	      		   $.messager.alert('提示', '数据对象管理不允许修改!', 'warning');
	      		   return;
	      		}
	      		
	      		$("#bd_dataobjectgroup_update").window({'href':''});//防止再次打开的时候,重复加载,造成页面的混乱
				$("#bd_dataobjectgroup_update").window('refresh');//先刷新再加载新的页面
				$("#bd_dataobjectgroup_update").window({'href':'datatable/dataObjectGroup!toUpdate.action?dataObjectGroupId='+node.id});	
				$("#bd_dataobjectgroup_update").window('open');
      		}else{
	        	$.messager.alert('提示', '请选择分组!', 'warning');
	        }
        }
        function bdDataObjectGroupDelete(){
        	//var node = $("#dataobject_menu").tree("getSelected");
        	var node = $("#lt1").tree("getSelected");
        	if(node && node != null){
	        	$.messager.confirm('提示', '确认删除吗？',function(a){
	        		if(a)
		         		$.post("datatable/dataObjectGroup!delete.action",{"dataObjectGroupId":node.id},bdDataObjectGroupDeleteRollback);
		        });
	        }else{
	        	$.messager.alert('提示', '请选择分组!', 'warning');
	        }
        }
        function bdDataObjectGroupDeleteRollback(data){
        	if("success" == data){
   				$.messager.alert('提示','删除成功','info');
   				//刷新菜单			         				
   				refreshLeftMenu();
   			} else if("children" == data){
   				$.messager.alert('提示','该分组包含分组或数据数据对象，不能删除！','warning');
   			} else if("nodelete" == data){
   				$.messager.alert('提示','默认分组，不能删除！','warning');
   			}
        }
    </script>
  </head>  
  <body>
  	<div id="bd_datatable_tabs" class="easyui-tabs"  border="false" fit="true">
  	<div id="bd_datatablelist_panel" title="数据对象列表"    border="false" collapsible="true" class="main_panel">
		<table id="bd_datatablelist_table"  fit="true"></table>      	
	</div>
    </div>
   <!--	
	   <div id="bd_datatable_search" class="easyui-window" style="padding:10px;">      	
			<table width="100%">
				<tr><td align="right" width="40%">对象名称：</td><td align="left"><input id="bd_datatablesearch_name" type="text"/></td></tr>
				<tr><td align="right">对象类型：</td><td align="left"><select id="bd_datatablesearch_type" style="width:132px;">
					</select></td></tr>
				<tr><td align="center" colspan="2"><a id="bd_datatable_search_sumbit" href="javascript:;" class="easyui-linkbutton" icon="icon-save" onclick="bdDataTableSearchSubmit()">提交</a>
						<a id="bd_datatable_search_cancel" href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="$('#bd_datatable_search').window('close')">关闭</a>
				</td></tr>
			</table> 
	   </div> 
    -->
    <div id="bd_databaseObject_import" class="easyui-window" style="padding:10px;">
    </div>
	<div id="bd_datatable_add" class="easyui-window" style="padding:10px;">      	
    </div>
	<div id="bd_datatable_import" class="easyui-window" style="padding:10px;">      	
    </div>
	<div id="bd_datatable_update" class="easyui-window" style="padding:10px;">      	
    </div>
	<div id="bd_dataobjectgroup_add" class="easyui-window" style="padding:10px;" icon="icon-group-add">      	
    </div>
	<div id="bd_dataobjectgroup_update" class="easyui-window" style="padding:10px;" icon="icon-group-edit">      	
    </div>
  </body>
</html>
