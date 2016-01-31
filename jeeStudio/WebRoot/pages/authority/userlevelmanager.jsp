<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>管理级别用户</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
    <script type="text/javascript">
		$(function(){
		   $('#level_user_list_manage').datagrid({
                height: 400,
                nowrap: false,
                striped: true,
				fit: true,
				border:false,
				fitColumns:true,
                url: 'authority/userlevel!usersUnderLevel.action?id='+${ id},
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'userid',
                pageSize:20,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'oname',title: '部门',width: 100,sortable: true,align:'left'},
                	{field: 'uname',title: '用户名',width: 100,sortable: true,align:'left'},                	
                	{field: 'username',title: '姓名',width: 100,sortable: true,align:'left'}
				]],
                pagination: true,
                rownumbers: true,
                toolbar: ['-', 
		  			{text: '添加',iconCls: 'icon-add',handler: levelAddUser},
		  			'-', 
		  			{text: '移除',iconCls: 'icon-remove',handler:levelDeleteUser }, 
		  			'-'
		  			]
            });
            $('#level_user_all_manage').appendTo('body').window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                 width: 900,
                height: 450
            });
		});
		function levelAddUser(){
		   $("#level_user_all_manage").window({'href':''});
			$("#level_user_all_manage").window('refresh');
			$("#level_user_all_manage").window({'href':'authority/userlevel!toLevelUnderUserAll.action?id=${id}'});				
			$("#level_user_all_manage").window('open');
		}
		function levelDeleteUser(){
			var selected = $('#level_user_list_manage').datagrid('getSelections');
	            if (selected == null || selected.length < 1) {
	                $.messager.alert('提示', '请选择数据!', 'warning');
	            } else {
	            	$.messager.confirm('提示', '确认移除吗？',function(data){
	            		if(data){
		            		var cc = [];
		            		for(var i=0;i<selected.length;i++){
		            			if(cc == ""){
		            				cc += "'" + selected[i]['userid'] + "'" ;
		            			}else{
		            				cc += ",'" + selected[i]['userid'] + "'" ;
		            			}
		                	}
		                	$.post("authority/userlevel!removeUserLevel.action",{"userid":cc,"id":${id}},function(res){
		                		 	 $.messager.alert('提示','移除成功','info');			         				
   									 $('#level_user_list_manage').datagrid('reload');
		                	}); 
		                	cc = [];
	                	}
	            	});	               
	            }
	             $('#level_user_list_manage').datagrid('clearSelections');
		}
    </script>
  </head>
  <body>
  		<ul id="level_user_list_manage" ></ul>
	<div id="level_user_all_manage" title="用户列表" >      	
    </div>
  </body>
</html>
