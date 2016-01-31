<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <title>添加</title>    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<script language="javascript" type="text/javascript">		
		$(function(){
			var cvalue = decodeURI(decodeURI('${ orgname}'));	
			$('#userListPanel').panel({
			  title: cvalue+' — 用户列表',
			  iconCls:'icon-vcard'
			});
			
			$('#bd_usertablelist_table').datagrid({
				nowrap: true,
				striped: true,
				fitColumns:true,
				headerCls:'header_cls',
				url:'organization/organization!getUserListByGroupId.action?oid=${oid}',
				fit:true,
				sortName: 'userid',
	            sortOrder: 'desc',
	            idField: 'userid',
	            remoteSort: false,
	            width:700,
				pageSize:20,
				frozenColumns: [[
						{ field: 'ck', checkbox: true},
					    {field:'oname',title:'所属部门',align:'left',width:120,sortable: true}
				]],
				columns:[[
					{field:'username',title:'登录名',align:'left',width:120,sortable: true},
					{field:'uname',title:'姓名',align:'left',width:120,sortable: true},
					{field:'levelname',title:'用户级别',align:'center',width:120},
					{field:'rolemanager',title:'管理角色',align:'center',width:120,formatter:function(value,rec){
					    return "<a href='javascript:void(0);' onclick=rolemanager('"+rec["userid"]+"','"+rec["username"]+"') ><img src='images/3.gif' width='16px;' /></a>";
					}},
					{field: 'operate',title: '操作',width: 100,align:'center',
	                    formatter:function(value,rec){
							  var links = '<a href="javascript:void(0);" onclick=orgUserUpdate("' + rec['userid'] + '")>修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
						      links += '<a href="javascript:void(0);" onclick=userDelete("' + rec['userid'] + '")>删除</a>';
							  return links;
						}
					}
				]],
				pagination:true,
				rownumbers:true,
				 toolbar: ['-', 
		  			{text: '添加部门',iconCls: 'icon-department',handler: orgAdd},
		  			'-', 
		  			{text: '修改部门',iconCls: 'icon-edit',handler: orgUpdate}, 
		  			'-',
		  			{text: '删除部门',iconCls: 'icon-cancel',handler: orgDelete}, 
		  			'-',
		  			{text: '添加用户',iconCls: 'icon-add',handler: orgAddUser}, 
		  			'-'
		  			]
				
			});
		
		$('#bd_org_add').appendTo("body").window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 800,
                height: 380
            });
          $('#bd_org_update').appendTo("body").window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 380
            });   
            $('#bd_org_useradd').appendTo("body").window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 300
            });        
             $('#bd_org_rolemanager').appendTo("body").window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 450
            });            
           	 $('#bd_org_userupdate').appendTo("body").window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 300
            });          
		  });
		  function rolemanager(userid,username){
			    $("#bd_org_rolemanager").window({'href':''});
			    $("#bd_org_rolemanager").window({'title':username+" — 角色"});
  				$("#bd_org_rolemanager").window('refresh');
  				if(document.getElementById("isAdmin").value=="1")
  				  $("#bd_org_rolemanager").window({'href':'organization/organization!roleManager.action?isAdmin=1&rname=<%=rname%>&userid='+userid});			
  				else 
  				  $("#bd_org_rolemanager").window({'href':'organization/organization!roleManager.action?rname=<%=rname%>&userid='+userid});	
  				$("#bd_org_rolemanager").window('open');
		  }

		  function orgAdd(){
          		$("#bd_org_add").window({'href':''});
  				$("#bd_org_add").window('refresh');
  				$("#bd_org_add").window({'href':'organization/organization!AddOrg.action?oid=${oid}&orgupid=${orgupid}'});			
  				$("#bd_org_add").window('open');
           } 
           function orgAddUser(){
           		$("#bd_org_useradd").window({'href':''});
  				$("#bd_org_useradd").window('refresh');
  				$("#bd_org_useradd").window({'href':'organization/organization!AddOrgUser.action?oid=${oid}&orgupid=${orgupid}'});			
  				$("#bd_org_useradd").window('open');
           }
           function orgUpdate(){
           		var node = parent.$('#orgl').tree('getSelected');
				if(!node){
					$.messager.alert('提示', '请选择一个部门！', 'warning');
				}else{
					$("#bd_org_update").window({'href':''});
	  				$("#bd_org_update").window('refresh');
	  				$("#bd_org_update").window({'href':'organization/organization!toupdateOrg.action?oid=${oid}&orgupid=${orgupid}'});			
	  				$("#bd_org_update").window('open');
				}
	            
           }
           
           function orgDelete(){
	           	var node = parent.$('#orgl').tree('getSelected');
				if(!node){
					$.messager.alert('提示', '请选择数据!', 'warning');
				}else{
					$.messager.confirm('提示', '确认删除吗？',function(choose){
	            		if(choose){
		            		var orgid = node.id;
		            		if(orgid==1){//根目录不允许删除
		                			$.messager.alert('提示', '根目录禁止删除！', 'warning');
		                			return;
		            		}
		                	$.post("organization/organization!deleteOrg.action?oid="+orgid,function(data){
		                		if(data=="do"){
		                			$.messager.alert('提示','删除成功！','info');			         				
	  								parent.$('#orgl').tree('reload');
	  								//展开组织机构
									setTimeout(function(){
										var node = $('#orgl').tree('getRoot');
										$("#orgl").tree('collapseAll');
										$('#orgl').tree('expand',node.target);
									},500);
		                		}
		                		
		                		if(data=="hasuser"){
		                			$.messager.alert('提示', '当前部门下有用户存在，禁止删除！', 'warning');
		                		}
		                		
		                		if(data=="hasorg"){
		                			$.messager.alert('提示', '当前部门下有子部门存在，禁止删除！', 'warning');
		                		}
		                	});
	                	}
	            	});	               
				}
           }
           
          function userDelete(userid){
        	if(userid && userid != ""){
       			$.post("organization/organization!checkUserIsUse.action",{"userid":userid},function(data){
		   				if(data=='true'){
		   					$.messager.alert('提示','该用户已被使用不能删除！','info');
		   				}else{
			   				$.messager.confirm('提示', '确认删除吗？',function(a){
				         		if(a)$.post("organization/organization!deleteOrgUser.action",{"userid":userid},function(){
						   				$.messager.alert('提示','删除成功！','info');			         				
							   			$('#bd_usertablelist_table').datagrid('reload');
				         		});
				         	});
		   				}
		   		 	});
        	/** **/
            }
       	 }   
        
         function orgUserUpdate(id){
      		$("#bd_org_userupdate").window({'href':''});
			$("#bd_org_userupdate").window('refresh');
			$("#bd_org_userupdate").window({'href':'organization/organization!toupdateOrgUser.action?userId='+id+'&&oid=${oid}'});				
			$("#bd_org_userupdate").window('open');
      	}	  
    </script>
  </head>  
  <body>
		  
		<div class="easyui-panel"  border="false" fit="true" id="userListPanel">
			<table id="bd_usertablelist_table"></table>
		</div>
		
		<div id="bd_org_add" title="添加部门" icon="icon-department"></div>
        <div id="bd_org_update" title="修改部门" icon="icon-edit"></div>
       <div id="bd_org_delete" title="删除部门" icon="icon-cancel"></div>
       <div id="bd_org_useradd" title="添加用户" icon="icon-add"></div>
       <div id="bd_org_userupdate" title="修改用户"></div>
        <div id="bd_org_rolemanager" title="管理角色"></div>
        <%
        String isAdmin=request.getParameter("isAdmin");
        %>
        <input type="hidden" name="isAdmin" id="isAdmin" value="<%=isAdmin%>"/>
  </body>
</html>
