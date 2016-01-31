<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>部门主管页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
	  $(function(){
		   $('#org_lead_selectlist').datagrid({
                height: 400,
                nowrap: false,
                striped: true,
				fit: true,
				border:false,
				fitColumns:true,
                url: 'organization/organization!userListForOrgLead.action?oid=${oid}',
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'userid',
                pageSize:20,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'oname',title: '部门名',width: 100,sortable: true,align:'left'},
                	{field: 'username',title: '用户名',width: 100,sortable: true,align:'left'},                	
                	{field: 'uname',title: '姓名',width: 100,sortable: true,align:'left'},
                	{field: 'levelname',title: '用户级别',width: 100,sortable: true,align:'left'}
				]],
                pagination: true,
                rownumbers: true,
                toolbar: ['-', 
		  			{text: '添加',iconCls: 'icon-add',handler: orgAddLead},
		  			'-', 
		  			{text: '移除',iconCls: 'icon-remove',handler:orgDeleteLead }, 
		  			'-'
		  			]
            });
            $('#org_lead_list').appendTo('body').window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                 width: 600,
                height: 470
            });
		});
		function orgAddLead(){
		    $("#org_lead_list").window({'href':''});
			$("#org_lead_list").window('refresh');
			$("#org_lead_list").window({'href':'organization/organization!orgUnderLeadAdd.action?oid=${oid}'});				
			$("#org_lead_list").window('open');
		}
		function orgDeleteLead(){
			var selected = $('#org_lead_selectlist').datagrid('getSelections');
	            if (selected == null || selected.length < 1) {
	                $.messager.alert('提示', '请选择数据!', 'warning');
	            } else {
	            	$.messager.confirm('提示', '确认移除吗？',function(data){
	            		if(data){
		            		var cc = [];
		            		for(var i=0;i<selected.length;i++){
		            			if(cc == ""){
		            				cc += "" + selected[i]['userid'] + "" ;
		            			}else{
		            				cc += "," + selected[i]['userid'] + "" ;
		            			}
		                	}
		                 	$.post("organization/organization!removeLeadFromOrg.action",{"oid":${oid},"userid":cc},function(res){
		                		 	 $.messager.alert('提示','移除成功','info');			         				
   									 $('#org_lead_selectlist').datagrid('reload');
		                	});
		                	cc = [];
	                	}
	            	});	               
	            }
	             $('#org_lead_selectlist').datagrid('clearSelections');
		}
	</script>

  </head>
  
  <body>
		<ul id="org_lead_selectlist"></ul>
		<div id="org_lead_list" title="人员列表"></div>
  </body>
</html>
