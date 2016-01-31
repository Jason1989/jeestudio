<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>字典管理</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script>
		$(function(){
            $('#bd_dictionarygrouplist_table').datagrid({
                iconCls: 'icon-save',
                //width:1000,
                height: 400,
                nowrap: false,
                striped: true,
                autoFit: true,
                url: 'dictionary/dictionaryGroup!list.action',
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'id',
                pageSize:20,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'name',title: '名称',width: 250,sortable: true,align:'left'},                	
                	{field: 'operate',title: '操作',width: 100,align:'center',
	                    formatter:function(value,rec){
						    var links = '<a href="javascript:;" onclick=bdDictionaryGroupUpdate("' + rec.id + '")>修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
						    links += '<a href="javascript:;" onclick=bdDictionaryGroupDelete("' + rec.id + '")>删除</a>';
							return links;
						}
					}
				]],
                pagination: true,
                rownumbers: true,
                //singleSelect: true,
                toolbar: ['-', 
		  			{text: '添加',iconCls: 'icon-add',handler: bdDictionaryGroupAdd},
		  			'-', 
		  			{text: '删除',iconCls: 'icon-remove',handler: bdDictionaryGroupDelete}, 
		  			'-' ]
            });
            $('#bd_dictionarygroup_add').appendTo('body').window({
                title: "添加",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 380,
                height: 200
            });
            $('#bd_dictionarygroup_update').appendTo('body').window({
                title: "修改",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 380,
                height: 200
            });
            
            function bdDictionaryGroupAdd(){
            	//$.post("dictionary/dictionaryGroup!toAdd.action",{},
            	//	function(data){
		        //  		$("#bd_dictionarygroup_add").html(data);
		        //  		$("#bd_dictionarygroup_add").window('open');
          		//	}
          		//);
          		$("#bd_dictionarygroup_add").window({'href':''});//防止再次打开的时候,重复加载,造成页面的混乱
  				$("#bd_dictionarygroup_add").window('refresh');//先刷新再加载新的页面
  				$("#bd_dictionarygroup_add").window({'href':'dictionary/dictionaryGroup!toAdd.action'});				
  				$("#bd_dictionarygroup_add").window('open');
            }           
        });
        
        function bdDictionaryGroupDelete(dictionaryGroupId){
        	if(dictionaryGroupId && dictionaryGroupId != "" && typeof dictionaryGroupId =="string"){
        		$.messager.confirm('提示', '确认删除吗？',function(a){
	         		if(a)$.post("dictionary/dictionaryGroup!delete.action",{"dictionaryGroupId":dictionaryGroupId},bdDictionaryGroupDeleteRollback);
	         	});
        	}else{
         		var selected = $('#bd_dictionarygrouplist_table').datagrid('getSelections');
	            if (selected == null || selected.length < 1) {
	                $.messager.alert('提示', '请选择数据!', 'warning');
	            } else {
	            	$.messager.confirm('提示', '确认删除吗？',function(a){
	            		if(a){
		            		var dictionaryGroupIds = "";
		            		for(var i=0;i<selected.length;i++){
		            			if(dictionaryGroupIds == ""){
		            				dictionaryGroupIds += "'" + selected[i]['id'] + "'"
		            			}else{
		            				dictionaryGroupIds += ",'" + selected[i]['id'] + "'"
		            			}
		                	}
		                	$.post("dictionary/dictionaryGroup!delete.action",{"dictionaryGroupIds":dictionaryGroupIds},bdDictionaryGroupDeleteRollback);
	                	}
	            	});	               
	            }
            }
        }
        function bdDictionaryGroupDeleteRollback(data){
        	if("success" == data){
   				$.messager.alert('提示','删除成功','info');			         				
   				$('#bd_dictionarygrouplist_table').datagrid('reload');
   			}
        	if("error" == data){
   				$.messager.alert('提示','删除失败:不能删除正在使用的字典分组!','info');			         				
   				$('#bd_dictionarygrouplist_table').datagrid('reload');
   			}
        }
        function bdDictionaryGroupUpdate(dictionaryGroupId){
        	//$.post("dictionary/dictionaryGroup!toUpdate.action",{"dictionaryGroupId":dictionaryGroupId},
        	//	function(data){
	        //		$("#bd_dictionarygroup_update").html(data);
	        //		$("#bd_dictionarygroup_update").window('open');
      		//	}
      		//);
      		
      			$("#bd_dictionarygroup_update").window({'href':''});//防止再次打开的时候,重复加载,造成页面的混乱
  				$("#bd_dictionarygroup_update").window('refresh');//先刷新再加载新的页面
  				$("#bd_dictionarygroup_update").window({'href':'dictionary/dictionaryGroup!toUpdate.action?dictionaryGroupId='+dictionaryGroupId});				
  				$("#bd_dictionarygroup_update").window('open');
        }
    </script>
  </head>  
  <body>
  	<div id="bd_dictionarygrouplist_panel" title="字典分组列表" class="easyui-panel" fit="true" border="false" collapsible="true" class="main_panel">
		<table id="bd_dictionarygrouplist_table" fit="true"></table>      	
	</div>
	<div id="bd_dictionarygroup_add" class="easyui-window" style="padding:10px;">      	
    </div>
	<div id="bd_dictionarygroup_update" class="easyui-window" style="padding:10px;">      	
    </div>
  </body>
</html>
