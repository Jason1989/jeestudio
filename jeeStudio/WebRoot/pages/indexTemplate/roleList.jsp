<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>角色</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">

    <script type="text/javascript">
	    $(function(){
	    	$('#rolelist_table').datagrid({
                iconCls: 'icon-database-table',
                height: 400,
                nowrap: true,
                striped: true,
				fit: true,
				border:false,
				fitColumns:true,
                url: 'authority/role!getAllRoles.action',
                idField: 'id',
                sortName: 'id',
                sortOrder: 'asc',
                remoteSort: false,
                pageSize:20,
                frozenColumns: [[
					{ field: 'ck', checkbox: true},
                	{field: 'id',title: '角色ID',width: 80,sortable: true,align:'left'}
				]],
                columns: [[
                	{field: 'name',title: '角色名',width: 100,sortable: true,align:'left'},                	
                	{field: 'rfunction',title: '角色职能',width: 100,align:'left'}
				]],
                toolbar: ['-', 
		  			{text: '设置选定',iconCls: 'icon-add',handler: selected},
		  			'-'
		  			]
            });
		});
		function selected(){
			var data=$('#rolelist_table').datagrid("getSelections");
			var ids="";
			for(var i=0;i<data.length;i++){
				ids+=data[i].name;
				if(i!=data.length-1)
					ids+=",";
			}
			parent.getrols(ids);
		}
    </script>
  </head>
  
  <body>
     <div id="role_panel" icon="icon-database-table" class="easyui-panel" fit="true" border="false" collapsible="false" class="main_panel">
		 <table id="rolelist_table" ></table>		
	</div>
  </body>
</html>
