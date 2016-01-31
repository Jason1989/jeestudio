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
	<script type="text/javascript">
		//使用ajax 
		var orglist ;
		$.ajax({
			url:'organization/organization!getAllOrgList.action',
			type:'post',
			async:false,
			success:function(html){
			    if(html != ""){
			      orglist = eval(html);
			    }
			}
		});
	   $(function(){
		   $('#level_under_userall_id').datagrid({
                height: 400,
                nowrap: false,
                striped: true,
				fit: true,
				border:false,
				fitColumns:true,
                url: 'authority/role!userListForCommon.action',
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
                toolbar: ['->', 
		  			{text: '确定',iconCls: 'icon-ok',handler: saveaddUser},
		  			'-'
		  			],
	  			searchbar:{
					forms:[{
							//id:'',
							type:'combobox',
							name:'org',
							label:'部门',
							options:{
								 valueField:'oid',
								 textField:'oname',
								 data:orglist,
							}
						},{
							id:"11",
							type:'validatebox',
							name:'username',
							label:'用户名',
							options:{
								//validType:'email',
							}
	
						},{
							id:"22",
							type:'validatebox',
							name:'uname',
							label:'姓名',
							options:{
								//validType:'email',
							}
						}
					]
			 }
            });
		});
			function saveaddUser(){
				var selected = $('#level_under_userall_id').datagrid('getSelections');
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
		                	$.post("authority/userlevel!addUserUserLevel.action",{"userid":cc,"id":${ id}},function(res){
		                		 	 $.messager.alert('提示','添加成功','info');			         				
   									 parent.$('#level_user_all_manage').window('close');		         				
   									 $('#level_user_list_manage').datagrid('reload');
		                	});
		                	cc = [];
	                	}
	            	});	               
	            }
	            $('#level_under_userall_id').datagrid('clearSelections');
			}
	</script>

  </head>
  
  <body>
    <ul id="level_under_userall_id" ></ul>
  </body>
</html>
