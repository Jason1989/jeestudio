<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'roleusermanagerAlluser.jsp' starting page</title>
    
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
		   $('#role_under_userall').datagrid({
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
                remoteSort: false,
                pageSize:20,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'oname',title: '部门',width: 100,sortable: true,align:'left'},
                	{field: 'username',title: '用户名',width: 100,sortable: true,align:'left'},                	
                	{field: 'uname',title: '姓名',width: 100,sortable: true,align:'left'},                	
                	{field: 'levelname',title: '用户级别',width: 100,sortable: true,align:'left'}
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
							id:"1",
							type:'validatebox',
							name:'uname',
							label:'用户名',
							options:{
								//validType:'email',
							}
	
						},{
							id:"2",
							type:'validatebox',
							name:'username',
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
			var selected = $('#role_under_userall').datagrid('getSelections');
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
	                	 $.post("authority/role!addUserToRole.action",{"userid":cc,"rid":${rid}},function(res){
	                		 	 $.messager.alert('提示','添加成功','info');	
	                		 	 parent.$('#role_user_all').window('close');
  								 $('#role_user_list').datagrid('reload');
	                	});
	                	cc = [];
                	}
            	});	               
            }
            $('#role_under_userall').datagrid('clearSelections');
		}
	</script>

  </head>
  
  <body>
    <div class="easyui-layout" collapsible="true" fit="true" border="false">
		<div region="west"   border="true" style="width:240px;padding:5px;b">
			<ul class="easyui-tree" id="addAllRoleToUserTree">
	     		<li>查询所有用户</li>
	     	</ul>
		 	<ul id="addRoleToUserTree"></ul>
    	</div>
		<div region="center"   border="false">
		     <ul id="role_under_userall" ></ul>
		</div>
   </div>
  <script type="text/javascript">
	$(function(){
			    $('#addRoleToUserTree').tree({
					checkbox: false,
					fit:true,							
					url: 'organization/organization!getAllOrganizations.action?isAdmin=${isAdmin}&dcis='+new Date().getTime()+'&oid=${oid}',
					onClick:function(node){
						var oid = node.id;
						var otext = node.text;
						if(node){
							var orgUpId = $(this).tree('getParent',node.target);
							var url = "";
							if(orgUpId){//有父节点
								url = 'organization/organization!getUserListByGroupId.action?oid='+oid;
							}else{
								url='authority/role!userListForCommon.action';
							}
							$('#role_under_userall').datagrid('options').url = url;
							$('#role_under_userall').datagrid('reload');
						}
					},
					onLoadSuccess:function(){
						  
						var node = $('#addRoleToUserTree').tree('getRoot');
						if(node){
							$('#addRoleToUserTree').tree('expand',node.target);	
						}
					}
				});
				//展开组织机构
				setTimeout(function(){
					$("addRoleToUserTree").tree('expandAll');
				},500);
				//选全部用户
				$('#addAllRoleToUserTree').tree({
					onClick:function(node){
						if(node){
							var url='authority/role!userListForCommon.action';
							$('#role_under_userall').datagrid('options').url = url;
							$('#role_under_userall').datagrid('reload');
						}
					}
				})
		});
	</script>
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  </body>
</html>
