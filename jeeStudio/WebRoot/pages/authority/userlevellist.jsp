<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户级别管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		$(function(){
			$('#bd_userlevellist_table').datagrid({
				nowrap: true,
				striped: true,
				fitColumns:true,
				url:'authority/userlevel!getUserLevelList.action',
				fit:true,
				
	            idField: 'id',
	            sortName: 'levelnumber',
	            sortOrder: 'asc',
	            remoteSort: false,
				pageSize:20,
				frozenColumns: [[
						{ field: 'ck', checkbox: true}
				]],
				columns:[[
					{field:'levelname',title:'级别名',sortable: true,align:'left',width:120},
					{field:'levelnumber',title:'级别号',align:'left',width:120,sortable:true},
					{field:'levelnote',title:'级别描述',align:'left',width:120},
					{field:'usermanager',title:'用户管理',align:'center',width:120,
					  formatter:function(value,rec){
					     return "<a href='javascript:;' onclick=usermanager('"+rec['id']+"','"+rec["levelname"]+"')><img src='images/2.gif' width='13px;' /></a>"
					  }
					},
					{field: 'operate',title: '操作',width: 100,align:'center',
	                    formatter:function(value,rec){
						    var links = '<a href="javascript:;" onclick=userLevelUpdate("' + rec.id + '")>修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
						    links += '<a href="javascript:;" onclick=userLevelDelete("' + rec.id + '")>删除</a>';
							return links;
						}
					}
				]],
				pagination:true,
				rownumbers:true,
				 toolbar: ['-', 
		  			{text: '添加级别',iconCls: 'icon-add',handler: userlevelAdd},
		  			'-', 
		  			{text: '删除级别',iconCls: 'icon-remove',handler: userLevelDelete}, 
		  			'-'
		  			]
				
			});
		
		$('#bd_userlevel_add').appendTo("body").window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 500,
                height: 300
            });

          $('#bd_userlevel_update').appendTo("body").window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 500,
                height: 300
            });  
             $('#bd_userlevel_manager').appendTo("body").window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 450
            });  
		  });
		  function usermanager(id,levelname){
		        $("#bd_userlevel_manager").window({'href':''});
		        $("#bd_userlevel_manager").window({'title':levelname+' — 用户列表'});
  				$("#bd_userlevel_manager").window('refresh');
  				$("#bd_userlevel_manager").window({'href':'authority/userlevel!toManagerUser.action?id='+id});			
  				$("#bd_userlevel_manager").window('open');
		    
		  }
		  function userlevelAdd(){
          		$("#bd_userlevel_add").window({'href':''});
  				$("#bd_userlevel_add").window('refresh');
  				$("#bd_userlevel_add").window({'href':'authority/userlevel!toAddUserLevel.action'});			
  				$("#bd_userlevel_add").window('open');
           } 
         function userLevelDelete(id){
        	if(id && id != ""){
        		$.messager.confirm('提示', '确认删除吗？',function(a){
	         		if(a)$.post("authority/userlevel!deleteUserLevel.action",{"id":id},function(){
			   				$.messager.alert('提示','删除成功','info');			         				
				   				$('#bd_userlevellist_table').datagrid('reload');
	         		});
	         	});
        	}else{
         		var selected = $('#bd_userlevellist_table').datagrid('getSelections');
	            if (selected == null || selected.length < 1) {
	                $.messager.alert('提示', '请选择数据!', 'warning');
	            } else {
	            	$.messager.confirm('提示', '确认删除吗？',function(a){
	            		if(a){
		            		var ids = [];
		            		for(var i=0;i<selected.length;i++){
		            			if(ids == ""){
		            				ids += "'" + selected[i]['id'] + "'" ;
		            			}else{
		            				ids += ",'" + selected[i]['id'] + "'" ;
		            			}
		                	}
		                	$.post("authority/userlevel!deleteUserLevel.action",{"id":ids},function(){
		                	  	 $.messager.alert('提示','删除成功','info');			         				
   								 $('#bd_userlevellist_table').datagrid('reload');
					            $('#bd_userlevellist_table').datagrid('clearSelections');
		                	});
		                	ids = [] ;
	                	}
	            	});	               
	            }
            }
        }   
        
         function userLevelUpdate(id){
      		$("#bd_userlevel_update").window({'href':''});
			$("#bd_userlevel_update").window('refresh');
			$("#bd_userlevel_update").window({'href':'authority/userlevel!updateUserLevel.action?id='+id});				
			$("#bd_userlevel_update").window('open');
      	}
	</script>
  </head>
    
  <body>
       <div title="级别列表" class="easyui-panel" fit="true" border="false" collapsible="false" >
			<table id="bd_userlevellist_table"></table>
		</div>
		
		<div id="bd_userlevel_add" title="添加级别"> 
        </div>
        <div id="bd_userlevel_update" title="修改级别" > 
        </div>
        <div id="bd_userlevel_manager" title="用户管理" > 
        </div>
  </body>
</html>
