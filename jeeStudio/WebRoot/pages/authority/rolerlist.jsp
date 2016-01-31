<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Object obj = request.getParameter("orgid");
Object oname = request.getParameter("orgname");
String isall = request.getParameter("isall");
if(isall==null||"".equals(isall)){
	isall="1";
}
String orgid = "";
String orgname = "";
if(obj!=null&&oname!=null){
	orgid = java.net.URLDecoder.decode(request.getParameter("orgid"),"UTF-8");
	orgname = java.net.URLDecoder.decode(request.getParameter("orgname"),"UTF-8");	
}
String isAdmin=request.getParameter("isAdmin");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>角色</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
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
                url: 'authority/role!listRoles.action?orgid=<%=orgid %>'+'&isall=<%=isall%>',
                idField: 'rid',
                sortName: 'rid',
                sortOrder: 'asc',
                remoteSort: false,
                pageSize:20,
                frozenColumns: [[
					{ field: 'ck', checkbox: true},
                	{field: 'rid',title: '角色ID',width: 80,sortable: true,align:'left'}
				]],
                columns: [[
                	{field: 'rname',title: '角色名',width: 100,sortable: true,align:'left'},                	
                	{field: 'rfunction',title: '角色职能',width: 100,align:'left'},                	
                	{field: 'usermanager',title: '管理用户',width: 50,align:'center',
                	  formatter:function(value,rec){
                	     return "<a href='javascript:;' onclick=usermanager('"+rec["uname"]+"','"+rec["rid"]+"','"+rec["rname"]+"') ><img src='images/2.gif' width='15px;'/></a>";
                	  }
                	},
                	{field: 'operate',title: '管理权限',width: 100,align:'center',
	                    formatter:function(value,rec){
						    var links = '<a href="javascript:;" onclick=roleMenuConfig("' + rec.rid + '","'+rec.rname+'")>管理权限</a>';	
						  	return links;
						}
					}
				]],
                pagination: true,
                rownumbers: true,
                toolbar: ['-', 
		  			{text: '添加角色',iconCls: 'icon-add',handler: roleAdd},
		  			'-', 
		  			{text: '删除角色',iconCls: 'icon-remove',handler:roleDelete }, 
		  			'-', 
		  			{text: '修改角色',iconCls: 'icon-cut',handler:roleUpdate },
		  			'-', 
		  			{text: '复制角色',iconCls: 'icon-copy',handler:roleCopy },
		  			'-'
		  			]
            });
            $('#role_menu_configwindow').appendTo('body').window({
                title: "菜单授权",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 1000,
                height:500
            });
            $('#role_formurl_configwindow').appendTo('body').window({
                title: "表单授权",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 480
            });
			  $('#role_add').appendTo('body').window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 400,
                height: 210
            });
             $('#role_update').appendTo('body').window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 400,
                height: 210
            });
			  $('#role_copy').appendTo('body').window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 400,
                height: 240
            });
             $('#role_usermanager').appendTo('body').window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 680,
                height: 480
            });
		});
		function usermanager(uname, rid,rname){
			$("#role_usermanager").window({'href':''});
			$("#role_usermanager").window({'title':rname+' — 用户列表'});
			$("#role_usermanager").window('refresh');
		    if(document.getElementById("isAdmin").value=="1"){
			$("#role_usermanager").window({'href':'authority/role!toroleUserManger.action?isAdmin=1&rid='+rid+'&uname='+uname});				
			}else{
			$("#role_usermanager").window({'href':'authority/role!toroleUserManger.action?rid='+rid+'&uname='+uname});	
			}
			$("#role_usermanager").window('open');
		}
		function roleMenuConfig(roleId,rname){
			$("#role_menu_configwindow").window({'href':''});
			$("#role_menu_configwindow").window({'title':'菜单授权 — '+rname});
			
			$("#role_menu_configwindow").window('refresh');
			$("#role_menu_configwindow").window({'href':'authority/authority!toConfig.action?roleId='+roleId+'&orgid=<%=orgid %>'+'&isall=<%=isall%>'});				
			$("#role_menu_configwindow").window('open');
		}
		function roleFormURLConfig(roleId){
			$("#role_formurl_configwindow").window({'href':''});
			$("#role_formurl_configwindow").window('refresh');
			$("#role_formurl_configwindow").window({'href':'authority/authority!toFormUrlConfig.action?roleId='+roleId});				
			$("#role_formurl_configwindow").window('open');
		}
       function roleAdd(){
			$("#role_add").window({'href':''});
			$("#role_add").window('refresh');
			$("#role_add").window({'href':'authority/role!toroleAdd.action?isAdmin=<%=isAdmin%>&orgid=<%=orgid %>'+'&orgname='+encodeURI(encodeURI('<%=orgname%>'))});
			$("#role_add").window('open');
	   }
	   function roleUpdate(){
	    	 	var selected = $('#rolelist_table').datagrid('getSelections');
	     	    if(selected == null || selected.length < 1) {
	              	 $.messager.alert('提示', '请选择数据!', 'warning');
	            }else if(selected.length > 1){
	            	 $.messager.alert('提示', '请选择单条数据!', 'warning');
	            }else{
	            		var ids = [];
	            		for(var i=0;i<selected.length;i++){
	            			if(ids == ""){
	            				ids += "'" + selected[i]['rid'] + "'" ;
	            			}else{
	            				ids += ",'" + selected[i]['rid'] + "'" ;
	            			}
	                	}
		                $("#role_update").window({'href':''});
						$("#role_update").window('refresh');
						$("#role_update").window({'href':'authority/role!toroleUpdate.action?rid='+ids});				
						$("#role_update").window('open');               
	            }
	            $('#rolelist_table').datagrid('clearSelections');
	   }
       	function roleDelete(){
       	   	var selected = $('#rolelist_table').datagrid('getSelections');
	            if (selected == null || selected.length < 1) {
	                $.messager.alert('提示', '请选择数据!', 'warning');
	            } else {
	            	$.messager.confirm('提示', '确认删除吗？',function(data){
	            		if(data){
		            		var cc = [];
		            		for(var i=0;i<selected.length;i++){
		            			if(cc == ""){
		            				cc += "'" + selected[i]['rid'] + "'" ;
		            			}else{
		            				cc += ",'" + selected[i]['rid'] + "'" ;
		            			}
		                	}
		                
		                	$.post("authority/role!roleDelete.action",{"rid":cc},function(res){
		                		
		                		 if(res == 'success'){
		                		 	 $.messager.alert('提示','删除成功','info');			         				
   									// $('#rolelist_table').datagrid('reload');
   									//  $('#rolelist_table').datagrid('clearSelections');
		                		 }else{
		                		 	// var data=eval('('+res+')');
		                		 	/// $('#dru_role_userlist').window('open');
		                		 	// setTimeout(function(){
			                		//   	 $('#deleterole_userslist').datagrid('loadData',data);
		                		 	// },500);
		                		 	 $.messager.alert('提示','该角色下面存在用户，不允许删除','info');		 	 
		                		 }
		                		  $('#rolelist_table').datagrid('reload');
   									  $('#rolelist_table').datagrid('clearSelections');
					           
		                	});
		                	cc = [];
	                	}
	            	});	               
	            }
       		}
       		
	   function roleCopy(){
			$("#role_copy").window({'href':''});
			$("#role_copy").window('refresh');
			$("#role_copy").window({'href':'authority/role!toroleCopy.action?orgid=<%=orgid %>'+'&orgname='+encodeURI(encodeURI('<%=orgname%>'))});				
			$("#role_copy").window('open');
	   }

    </script>
  </head>
  
  <body>
     <div id="role_panel" title="角色列表" icon="icon-database-table" class="easyui-panel" fit="true" border="false" collapsible="false" class="main_panel">
		 <table id="rolelist_table" ></table>		
	</div>
	<div id="role_menu_configwindow"  >      	
    </div>
	<div id="role_formurl_configwindow" >      	
    </div>
    <div id="role_add" title="添加角色" >      	
    </div>
     <div id="role_update" title="修改角色" >      	
    </div>
    <div id="role_copy" title="复制角色" >      	
    </div>
     <div id="role_usermanager"  >      	
    </div>
    
     <div id="dru_role_userlist"   > 
     	
    </div>
    
    <script type="text/javascript">
    			$(function(){
    				 $('#dru_role_userlist').window({
						  title: '用户列表',
						  href:'pages/authority/delete_role_userlist.jsp',
						  iconCls:"icon-task-show",
						  width: 550,
						  height: 450,
						  zIndex:9200,
						  doSize:true,
						  resizable:true,
						  modal:false,
						  shadow:false,
						  closed:true
				      });
    				
				 });
		</script>

		
    <input  type="hidden" value="<%=isAdmin %>" name="isAdmin" id="isAdmin"/>
  </body>
</html>
