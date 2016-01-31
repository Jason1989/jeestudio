<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'organizationleadAll.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		<script type="text/javascript">
		 $(function(){
		   $('#org_under_leadall').datagrid({
                height: 400,
                nowrap: false,
                striped: true,
				fit: true,
				border:false,
				fitColumns:true,
                url: 'organization/organization!getUserListByGroupId.action?oid=${oid}',
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
                	{field: 'uname',title: '姓名',width: 50,sortable: true,align:'left'},
                	{field: 'levelname',title: '用户级别',width: 100,sortable: true,align:'left'}
				]],
                pagination: true,
                rownumbers: true,
                toolbar: ['-', 
		  			{text: '确定',iconCls: 'icon-save',handler: saveaddLead},
		  			'-'
		  		]
		});
	   });	
		function saveaddLead(){
		  var selected = $('#org_under_leadall').datagrid('getSelections');
	            if (selected == null || selected.length < 1) {
	                $.messager.alert('提示', '请选择数据!', 'warning');
	            } else {
	            	$.messager.confirm('提示', '确认添加吗？',function(data){
	            		if(data){
		            		var cc = [];
		            		for(var i=0;i<selected.length;i++){
		            			if(cc == ""){
		            				cc += "" + selected[i]['userid'] + "" ;
		            			}else{
		            				cc += "," + selected[i]['userid'] + "" ;
		            			}
		                	}
		                	 $.post("organization/organization!addLeadToOrg.action",{"oid":${oid},"userid":cc},function(res){
		                		 	 $.messager.alert('提示','添加成功','info');	
		                		 	 parent.$('#org_lead_list').window('close');		         				
   									 $('#org_lead_selectlist').datagrid('reload');
		                	});
		                	cc = [];
	                	}
	            	});	               
	            }
	            $('#org_under_leadall').datagrid('clearSelections');
		}
	</script>
  </head>
  
  <body>
   <ul id="org_under_leadall" ></ul> 
  </body>
</html>
