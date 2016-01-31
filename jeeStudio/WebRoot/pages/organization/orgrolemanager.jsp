<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Object robj = request.getAttribute("rname");
String rname = "";
if(robj!=null){
	rname = robj.toString();
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>组织机构内用户角色的管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
		   $('#org_role_select').datagrid({
                height: 400,
                nowrap: false,
                striped: true,
				fit: true,
				border:false,
				fitColumns:true,
                url: 'authority/role!getRoleListUnderUser.action?userid=${userid}',
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'id',
                pageSize:20,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'id',title: '角色ID',width: 100,sortable: true,align:'left'},
                	{field: 'name',title: '角色名',width: 100,sortable: true,align:'left'},                	
                	{field: 'rfunction',title: '角色职能',width: 100,sortable: true,align:'left'}               	
				]],
                pagination: true,
                rownumbers: true,
                toolbar: ['-', 
		  			{text: '添加',iconCls: 'icon-add',handler: orgAddRole},
		  			'-', 
		  			{text: '移除',iconCls: 'icon-remove',handler:orgDeleteRole }, 
		  			'-'
		  			]
            });
            $('#org_role_list').appendTo('body').window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                 width: 760,
                height: 480
            });
		});
		function orgAddRole(){
		    $("#org_role_list").window({'href':''});
			$("#org_role_list").window('refresh');
			if(document.getElementById("isAdmin").value=="1"){
				$("#org_role_list").window({'href':'organization/organization!orgUnderRoleAdd.action?isAdmin=1&rname=<%=rname%>&userid=${userid}'});
			}else{
				$("#org_role_list").window({'href':'organization/organization!orgUnderRoleAdd.action?rname=<%=rname%>&userid=${userid}'});
			}				
			$("#org_role_list").window('open');
		}
		function orgDeleteRole(){
			var selected = $('#org_role_select').datagrid('getSelections');
	            if (selected == null || selected.length < 1) {
	                $.messager.alert('提示', '请选择数据!', 'warning');
	            } else {
	            	$.messager.confirm('提示', '确认移除吗？',function(data){
	            		if(data){
		            		var cc = [];
		            		for(var i=0;i<selected.length;i++){
		            			if(cc == ""){
		            				cc += "" + selected[i]['id'] + "" ;
		            			}else{
		            				cc += "," + selected[i]['id'] + "" ;
		            			}
		                	}
		                	$.post("organization/organization!deleteRoleUnderUser.action",{"userid":${userid},"rid":cc},function(res){
		                		 	 $.messager.alert('提示','移除成功','info');			         				
   									 $('#org_role_select').datagrid('reload');
		                	}); 
		                	cc = [];
	                	}
	            	});	               
	            }
	             $('#org_role_select').datagrid('clearSelections');
		}
	</script>
  </head>
  <body >
    <ul id="org_role_select"></ul>
    <div id="org_role_list" title="角色列表"></div>
     <%
        String isAdmin=request.getParameter("isAdmin");
        %>
        <input type="hidden" name="isAdmin" id="isAdmin" value="<%=isAdmin%>"/>
  </body>
</html>
