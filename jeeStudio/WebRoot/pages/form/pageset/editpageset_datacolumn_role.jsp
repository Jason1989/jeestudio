<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String uuid = UUID.randomUUID().toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>选择角色</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <script type="text/javascript">
	    $(function(){
	    	$('#eps_dc_rolelist_table<%=uuid%>').datagrid({
                iconCls: 'icon-database-table',
                height: 400,
                nowrap: true,
                striped: true,
				fit: true,
				border:false,
				fitColumns:true,
                url: 'authority/role!listRoles.action?isall=1',
                idField: 'rid',
                sortName: 'rid',
                sortOrder: 'asc',
                remoteSort: false,
                pageSize:100,
                frozenColumns: [[
					{field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'rname',title: '角色名',width: 100,align:'center'},                	
                	{field: 'rfunction',title: '角色职能',width: 100,align:'center'}
				]],
                pagination: true,
                rownumbers: true,
                toolbar: []
            });
		});

	    function choosePageRoles(){
	    	var ids = [];
		   	var names = [];
			var rows = $('#eps_dc_rolelist_table<%=uuid%>').datagrid('getSelections');
			if(rows.length==0){
	   			$.messager.alert('提示','请选择至少一个角色！', 'warning');
				return false;
			}
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].rid);
				names.push(rows[i].rname);
			}
		    var idStr = ids.join(',');
		    var nameStr = names.join(',');
		    
	        if('${param.fun}'==''){
			   $('#${param.r_id}').val(idStr);
			   $('#${param.r_name}').val(nameStr);
			   closePageRoleWindow();
			}else{
		        eval('${param.fun}("'+idStr+'","'+nameStr+'")');
		    }
		}
			
		function closePageRoleWindow(){
			var id = '${param.wid}';
			$('#${param.wid}').window('close');
		    $('#${param.wid}').window('destroy',true);
		    var options = $("#"+id).window('options');
		    if(!$("#"+id).get(0)){
		        $("<div id='"+id+"'></div>").appendTo('body').window(options);
	        }else{
		        $('#'+id).window(options);
	        }
		}
    </script>
  </head>
  
  <body class="easyui-panel" fit="true">	
	<div class="easyui-layout" fit="true" border="false">
		<div region="center" border="false">
		  <table id="eps_dc_rolelist_table<%=uuid%>"></table>
		</div>
		<div region="south" align=center style="text-align:center;height:45px;line-height:30px;padding-top:12px" border="false">
		  <a class="easyui-linkbutton" href="javascript:void(0);" icon="icon-ok" onclick="javascript:choosePageRoles();">确定</a>
		  <a class="easyui-linkbutton" href="javascript:void(0);"  icon="icon-cancel"  onclick="javascript:closePageRoleWindow();">关闭</a>
		</div>
	</div>
  </body>
</html>
