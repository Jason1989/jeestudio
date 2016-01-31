<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String isall = request.getParameter("isall");
String orgid = request.getParameter("orgid");
String roleId=request.getParameter("roleId");
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
			$('#checkresource').datagrid({
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
					{field: 'rid',title: '权限集ID',width: 60,sortable: true,align:'center'}
				]],
                columns: [[
                	{field: 'rname',title: '权限集名称',width: 60,sortable: true,align:'center'},                	
                	{field: 'rfunction',title: '权限集描述',width: 60,align:'center'},                	
                	{field: 'option',title: '选择权限集',width: 60,align:'center',
                	  formatter:function(value,rec){
                	     return "<a href='javascript:;' onclick=rorecheck('"+rec["rid"]+"') ><img src='images/2.gif' width='15px;'/></a>";
                	  }
                	}
				]],
                pagination: true
			})
			var moduleId = "";
			$('#rolemenuconfig_module').tree({
				url: 'authority/authority!getAllModule.action',
				//onLoadSuccess:function(node,data){
						//for(var k=0;k<data.length;k++){
							//var menuNode = $('#rolemenuconfig_module').tree('find',data[k].id);
							//$('#rolemenuconfig_module').tree('select',menuNode.target);
							//initAuthorizedMenuData(data[k].id);
							//break;
						//}
				//},
				onSelect:function(node){
					// initAuthorizedMenuData(node.id);				
				}
			});
	
			
			
			//初始化权限树
			$('#rolemenuconfig_menu').tree({
				checkbox: true,
				cascadeCheck:false,
				onBeforeLoad:function(node,param){
				
						},
				onLoadSuccess: function(node,data){
					var node = $('#rolemenuconfig_menu').tree('getRoot');
					if(node){
						$('#rolemenuconfig_menu').tree('expand',node.target);	
					}
					//$('#rolemenuconfig_menu').tree('collapseAll',node.target);
					
						}
				
				// url: 'authority/authority!getAllMenu.action?moduleId='+moduleId
			});
			
			//显示全部资源数据
			$('#checkallresource').click(function(){
				var moduleId = $('#rolemenuconfig_module').tree('getSelected');
				if(moduleId){
					if($(this).attr("checked")){
						//      var url = 'authority/authority!getAllMenu.action?moduleId='+moduleId.id+'&roleId=${roleId}';
						//      $.post(url,{"parmer":"parmer"},function(res){
			            //      var data=eval('('+res+')');
	                	//	 	$('#rolemenuconfig_menu').tree('loadData',data);
		                //		$('#deleterole_userslist').datagrid('loadData',data);
		                //});
					    $('#rolemenuconfig_menu').tree('options').url = 'authority/authority!getAllMenu.action?moduleId='+moduleId.id+'&roleId=<%=roleId%>';
						$('#rolemenuconfig_menu').tree('reload');
						// $('#rolemenuconfig_menu').tree("expandAll");	
					}
				}else{
					$.messager.alert('提示','请选择子系统！','info');	
				}
			});
			
			//是不是选择级联
			$("#check_box_auth_for_user").click(function(){
			    if($(this).attr("checked")){
			    	$('#rolemenuconfig_menu').tree('options').cascadeCheck = true;
			    }else{
					$('#rolemenuconfig_menu').tree('options').cascadeCheck = false;
			    }
			});
		});
		
		//点击选择权限集	
		function rorecheck(rid){
			if($('#checkallresource').attr("checked")){
				$.messager.alert('提示','请先取消全部资源！','info');
			}else{
				var moduleId = $('#rolemenuconfig_module').tree('getSelected');
				if(moduleId){
					$('#rolemenuconfig_menu').tree('options').url = 'authority/authority!getMenuByRoleId.action?moduleId='+moduleId.id+'&templateRoleId='+rid+'&roleID=<%=roleId%>';
					$('#rolemenuconfig_menu').tree('reload');
				}else{
					$.messager.alert('提示','请选择子系统！','info');	
				}
			}
		}
		function initAuthorizedMenuData(moduleId){
			$.post("authority/authority!getAuthorizedMenuIds.action",{"roleId":'<%=roleId%>',"moduleId":moduleId},
				function(resultData){
					$('#rolemenuconfig_menu').tree({
						checkbox: true,
						cascadeCheck:false,
						url: 'authority/authority!getAllMenu.action?moduleId='+moduleId,
						onLoadSuccess:function(node,data){
							if(resultData && resultData != null){										
								$('#rolemenuconfig_menu').tree("expandAll");					
								for(var i=0;i<resultData.length;i++){
									var menuNode = $('#rolemenuconfig_menu').tree('find',resultData[i].ajaxTreeId);
									if(menuNode){
										$('#rolemenuconfig_menu').tree('check',menuNode.target);
									}
								}
							}
							
							//检查相应的级联选项是不是已经选中
							 if($("#check_box_auth_for_user").attr("checked")){
			    				$('#rolemenuconfig_menu').tree('options').cascadeCheck = true;
						    }else{
								$('#rolemenuconfig_menu').tree('options').cascadeCheck = false;
						    }
						}
					});
				}
			);
		}
		/**function initMenuTreeData(moduleId){
			
			$('#unauthorized_menu').tree({
				checkbox: true,
				url: 'authority/authority!getUnauthorizedMenu.action?roleId=${roleId}&moduleId='+moduleId
			});
			$('#authorized_menu').tree({
				checkbox: true,
				url: 'authority/authority!getAuthorizedMenu.action?roleId=${roleId}&moduleId='+moduleId
			});
			
		}
	    function roleMenuConfigSave(){	    	
		    var treeIds = getMenuTreeIds("unauthorized_menu");
		    if(treeIds != ""){        		
	         	$.post("authority/authority!roleMenuConfigSave.action",{"roleId":'${roleId}',"treeIds":treeIds},roleMenuConfigCallBack);
        	}
	   	}
	   	function roleMenuConfigDelete(){
	   		var treeIds = getMenuTreeIds("authorized_menu");
		    if(treeIds != ""){        		
	         	$.post("authority/authority!roleMenuConfigDelete.action",{"roleId":'${roleId}',"treeIds":treeIds},roleMenuConfigCallBack);
        	}
	   	}*/
	   	function roleMenuConfigCallBack(data){
       		if("success" == data){
       			//$('#authorized_menu').tree("reload");
       			//$('#unauthorized_menu').tree("reload");
       			$.messager.alert('提示','操作成功!','info');
       		}	       
	   	}
	   	function roleMenuConfigSave(){
	   		var treeIds = getMenuTreeIds("rolemenuconfig_menu");
	   		//不论是不是赋权限，都执行如下操作
	         $.post("authority/authority!roleMenuConfigSave.action",{"roleId":'<%=roleId%>',"treeIds":treeIds,systemRescId:$('#rolemenuconfig_module').tree('getSelected').id},roleMenuConfigCallBack);
        	//}
	   		
	   	}
	   	function getMenuTreeIds(menu_id){
			var nodes = $('#'+menu_id).tree('getChecked');
			var treeIds = '';
			if(nodes && nodes.length > 0){
				for(var i=0; i<nodes.length; i++){
			   		treeIds += treeIds == ''?nodes[i].id:","+nodes[i].id;
			    }
			}
		    return treeIds;
	   	}
    </script>
  </head>
  
  <body>
	<!-- <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="10%">子系统：</td>
			<td><select id="module_id" style="width:250px;"></select></td>
		</tr>
	</table>
	<br/>	
	<table width="100%" border="0" cellpadding="1" cellspacing="0" class="table_form1" height="320px;">		
		 <tr>
			<td width="40%">未授权菜单</td>
			<td width="20%"></td>
			<td>已授权菜单</td>
		</tr>
		<tr>
			<td style="background:#dedede;"><table border="0" cellpadding="0" cellspacing="0" width="100%" style="background:#ffffff;" height="301px;">					
					<tr valign="top"><td><ul id="unauthorized_menu"></ul></td>
					</tr>
				</table></td>
			<td align="center"><input id="add" type="button"  value="添加>>" onclick="roleMenuConfigSave()"/><br/><br/>
				     <input id="delete" type="button"  value="删除&lt;&lt;" onclick="roleMenuConfigDelete()"/></td>
			<td style="background:#dedede;"><table border="0" cellpadding="0" cellspacing="0" width="100%" style="background:#ffffff;" height="301px;">					
					<tr valign="top"><td><ul id="authorized_menu"></ul></td>
					</tr>					
				</table></td>
		</tr> 		
		<tr>
		<td colspan="3" align="center"><br/><br/><a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#role_menu_configwindow').window('close');">关闭</a></td>
		</tr>
	</table>
	-->
	<table width="100%" border="0" cellpadding="1" cellspacing="0" class="table_form1" height="340px;">		
		 <tr>
			<td width="30%">子系统</td>
			<td width="40%">选择权限集</td>
			<td width="30%">资源权限  <span style="float: right;padding-right:13px;"><input id="checkallresource" type="checkbox">  &nbsp;全部资源</span><span style="float: right;padding-right:13px;"><input id="check_box_auth_for_user" type="checkbox">  &nbsp;级联选择</span></td>
		</tr>
		<tr>
			<td style="background:#dedede;"><table border="0" cellpadding="0" cellspacing="0" width="100%" style="background:#ffffff;" height="340px;">					
					<tr valign="top"><td><ul id="rolemenuconfig_module"></ul></td>
					</tr>
				</table></td>
			<td ><table id="checkresource"></table></td>
			<td style="background:#dedede;"><table border="0" cellpadding="0" cellspacing="0" width="100%" style="background:#ffffff;" height="340px;">					
					<tr valign="top"><td><div style="height:340px;overflow:auto;">
					<ul id="rolemenuconfig_menu"></ul></div></td>
					</tr>					
				</table></td>
		</tr> 		
		<tr>
		<td colspan="3" align="center"><br/>
			<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" onclick="roleMenuConfigSave()">保存</a>
			<!-- <a href="javascript:;" class="easyui-linkbutton" icon="icon-edit" onclick="cascadeCheck()">级联选择</a> -->
			<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#role_menu_configwindow').window('close');">关闭</a></td>
		</tr>
	</table>
  </body>
</html>
