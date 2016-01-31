<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>管理角色用户</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
    <script type="text/javascript">
		$(function(){
		   $('#role_user_list').datagrid({
                height: 400,
                nowrap: false,
                striped: true,
				fit: true,
				border:false,
				fitColumns:true,
                url: 'authority/role!getUserListUnder.action?rid=${rid}',
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'userid',
                pageSize:20,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'userid',title: '用户ID',width: 50,sortable: true,align:'left'}, 
                	{field: 'oname',title: '部门',width: 100,sortable: true,align:'left'},
                	{field: 'username',title: '登录名',width: 100,sortable: true,align:'left'}, 
                	{field: 'uname',title: '姓名',width: 100,sortable: true,align:'left'}, 
                	{field: 'levelname',title: '用户级别',width: 50,sortable: true,align:'left'}
				]],
                pagination: true,
                rownumbers: true,
                toolbar: ['-', 
		  			{text: '添加',iconCls: 'icon-add',handler: roleAddUser},
		  			'-', 
		  			{text: '移除',iconCls: 'icon-remove',handler:roleDeleteUser }, 
		  			'-'
		  			]
            });
            $('#role_user_all').appendTo('body').window({
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
		function roleAddUser(){
		   $("#role_user_all").window({'href':''});
			$("#role_user_all").window('refresh');
			 if(document.getElementById("isAdmin").value=="1"){
			    $("#role_user_all").window({'href':'authority/role!toRoleUnderUserAll.action?isAdmin=1&rid=${rid}'});				
			}else{
				$("#role_user_all").window({'href':'authority/role!toRoleUnderUserAll.action?rid=${rid}'});
			}			
			$("#role_user_all").window('open');
		}
		function roleDeleteUser(){
			var selected = $('#role_user_list').datagrid('getSelections');
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
		                	$.post("authority/role!deleteUserUnderRole.action",{"userid":cc,"rid":${rid}},function(res){
		                		 	 $.messager.alert('提示','移除成功','info');			         				
   									 $('#role_user_list').datagrid('reload');
		                	}); 
		                	cc = [];
	                	}
	            	});	               
	            }
	             $('#role_user_list').datagrid('clearSelections');
		}
    </script>
  </head>
  <body>
  		<ul id="role_user_list" ></ul>
	<div id="role_user_all" title="用户列表" >
		<%
			String isAdmin=request.getParameter("isAdmin");
		%>
	 <input  type="text" value="<%=isAdmin %>" name="isAdmin" id="isAdmin"/>
    </div>
  </body>
</html>
