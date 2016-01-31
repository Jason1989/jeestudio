<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户管理</title>
    
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
			$('#bd_usertablelist_table').datagrid({
				nowrap: true,
				striped: true,
				fitColumns:true,
				url:'authority/user!toList.action',
				fit:true,
				sortName: 'code',
	            sortOrder: 'desc',
	            idField: 'userid',
	           
				pageSize:20,
				frozenColumns: [[
						{ field: 'ck', checkbox: true},
					    {field:'userid',title:'用户ID',align:'center',width:120}
				]],
				columns:[[
					{field:'username',title:'用户名',align:'center',width:120},
					{field:'uname',title:'真实姓名',align:'center',width:120},
					{field:'sex',title:'角色',align:'center',width:150},
					{field:'levelnumber',title:'级别',align:'center',width:120},
					{field:'uname',title:'部门',align:'center',width:120},
					{field: 'operate',title: '操作',width: 100,align:'center',
	                    formatter:function(value,rec){
						    var links = '<a href="javascript:;" onclick=userUpdate("' + rec.userid + '")>修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
						    links += '<a href="javascript:;" onclick=userDelete("' + rec.userid + '")>删除</a>';
							return links;
						}
					}
				]],
				pagination:true,
				rownumbers:true,
				 toolbar: ['-', 
		  			{text: '添加',iconCls: 'icon-add',handler: userAdd},
		  			'-', 
		  			{text: '删除',iconCls: 'icon-remove',handler: userDelete}, 
		  			'-',
		  			{text: '查询',iconCls: 'icon-search',handler: userSelect}, 
		  			'-'
		  			]
				
			});
		
		$('#bd_user_add').appendTo("body").window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 460
            });
          $('#bd_user_update').appendTo("body").window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 460
            });  
            $('#bd_user_select').appendTo("body").window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 300,
                height: 220
            });        
		  });
		  function userAdd(){
          		$("#bd_user_add").window({'href':''});
  				$("#bd_user_add").window('refresh');
  				$("#bd_user_add").window({'href':'organization/organization!AddOrganizationUser.action?oid=${oid}'});			
  				$("#bd_user_add").window('open');
           } 
         function userDelete(userIds){
        	if(userIds && userIds != ""){
        		$.messager.confirm('提示', '确认删除吗？',function(a){
	         		if(a)$.post("organization/organization!deleteOrganizationUser.action",{"userIds":userIds},function(){
			   				$.messager.alert('提示','删除成功','info');			         				
				   				$('#bd_usertablelist_table').datagrid('reload');
	         		});
	         	});
        	}else{
         		var selected = $('#bd_usertablelist_table').datagrid('getSelections');
	            if (selected == null || selected.length < 1) {
	                $.messager.alert('提示', '请选择数据!', 'warning');
	            } else {
	            	$.messager.confirm('提示', '确认删除吗？',function(a){
	            		if(a){
		            		var userIds = [];
		            		for(var i=0;i<selected.length;i++){
		            			if(userIds == ""){
		            				userIds += "'" + selected[i]['userid'] + "'" ;
		            			}else{
		            				userIds += ",'" + selected[i]['userid'] + "'" ;
		            			}
		                	}
		                	$.post("organization/organization!deleteOrganizationUser.action",{"userIds":userIds},function(){
		                	  	 $.messager.alert('提示','删除成功','info');			         				
   								 $('#bd_usertablelist_table').datagrid('reload');
		                	});
	                	}
	            	});	               
	            }
            }
        }   
        
         function userUpdate(userIds){
      		$("#bd_user_update").window({'href':''});
			$("#bd_user_update").window('refresh');
			$("#bd_user_update").window({'href':'organization/organization!updateOrganizationUser.action?userId='+userIds});				
			$("#bd_user_update").window('open');
      	}
      	function userSelect(){
      	   $("#bd_user_select").window({'href':''});
			$("#bd_user_select").window('refresh');
			$("#bd_user_select").window({'href':'authority/user!userSelect.action'});				
			$("#bd_user_select").window('open');
      	}
	</script>
  </head>
    
  <body>
       <div title="用户列表" class="easyui-panel" fit="true" border="false" collapsible="false" >
			<table id="bd_usertablelist_table"></table>
		</div>
		
		<div id="bd_user_add" title="添加用户"> 
        </div>
        <div id="bd_user_update" title="修改用户"> 
        </div>
       <div id="bd_user_select" title="查询条件"> 
        </div>
  </body>
</html>
