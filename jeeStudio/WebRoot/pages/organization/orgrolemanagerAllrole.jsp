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
    
    <title>用户角色添加</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		 $(function(){
		   $('#org_under_roleall').datagrid({
                height: 400,
                nowrap: false,
                striped: true,
				fit: true,
				border:false,
				fitColumns:true,
                url: 'authority/role!listRoles.action?rname=<%=rname%>',
                queryParams: {
					isall: '${isAdmin}',
					userid: '${userid}'
				},
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'rid',
                pageSize:20,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'rid',title: '角色ID',width: 100,sortable: true,align:'left'},
                	{field: 'rname',title: '角色名',width: 100,sortable: true,align:'left'},                	
                	{field: 'rfunction',title: '角色职能',width: 100,sortable: true,align:'left'}               	
				]],
                pagination: true,
                rownumbers: true,
                toolbar: ['-', 
		  			{text: '确定',iconCls: 'icon-save',handler: saveaddRole},
		  			'-'
		  			]
            });
		});
		function saveaddRole(){
		  var selected = $('#org_under_roleall').datagrid('getSelections');
	            if (selected == null || selected.length < 1) {
	                $.messager.alert('提示', '请选择数据!', 'warning');
	            } else {
	            	$.messager.confirm('提示', '确认添加吗？',function(data){
	            		if(data){
		            		var cc = [];
		            		for(var i=0;i<selected.length;i++){
		            			if(cc == ""){
		            				cc += "" + selected[i]['rid'] + "" ;
		            			}else{
		            				cc += "," + selected[i]['rid'] + "" ;
		            			}
		                	}
		                	 $.post("organization/organization!addRoleToUser.action",{"userid":${userid},"rid":cc},function(res){
		                		 	 $.messager.alert('提示','添加成功','info');	
		                		 	 parent.$('#org_role_list').window('close');		         				
   									 $('#org_role_select').datagrid('reload');
						            $('#org_under_roleall').datagrid('clearSelections');
		                	});
		                	cc = [];
	                	}
	            	});	               
	            }
		}
	</script>
  </head>
  
  <body>
  <div class="easyui-layout" collapsible="true" fit="true" border="false">
		<div region="west"   border="true" style="width:240px;padding:5px;b">
			<ul class="easyui-tree" id="addAllRoleToUserTree">
	     		<li>查询所有角色</li>
	     	</ul>
		 	<ul id="addRoleToUserTree_eng"></ul>
    	</div>
		<div region="center"   border="false">
		    <ul id="org_under_roleall" ></ul>
		</div>
   </div>
  <script type="text/javascript">
	$(function(){
			    $('#addRoleToUserTree_eng').tree({
					checkbox: false,
					fit:true,							
					url: 'organization/organization!getAllOrganizationsByClassify.action?isAdmin=${isAdmin}&dcis='+new Date().getTime()+'&oid=${oid}',
					onClick:function(node){
						var oid = node.id;
						var otext = node.text;
						if(node){
							var orgUpId = $(this).tree('getParent',node.target);
							var url = "";
							if(orgUpId){//有父节点
								url = 'authority/role!listRoles.action?rname=<%=rname%>&isall=0&orgid='+oid+'&orgname='+encodeURI(encodeURI(otext));
							}else{
								url = 'authority/role!listRoles.action?rname=<%=rname%>&isall=1&orgid='+''+'&orgname='+'';
							}
							$('#org_under_roleall').datagrid('options').url = url;
							$('#org_under_roleall').datagrid('reload');
						}
					},
					onLoadSuccess:function(){
					   	var node = $('#addRoleToUserTree_eng').tree('getRoot');
						if(node){
							// $('#addRoleToUserTree_eng').tree('collapseAll',node.target);
							$('#addRoleToUserTree_eng').tree('expand',node.target);	
						}
					}
				});
			
				//选全部角色
				$('#addAllRoleToUserTree').tree({
					onClick:function(node){
						if(node){
							var url='authority/role!listRoles.action?rname=<%=rname%>&isall=1';
							$('#org_under_roleall').datagrid('options').url = url;
							$('#org_under_roleall').datagrid('reload');
						}
					}
				})
		});
	</script>
  </body>
</html>
