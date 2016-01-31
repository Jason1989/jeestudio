<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String isAdmin=request.getParameter("isAdmin");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'roleupdate.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	  	   		
			function bdroleupdateFormSubmit(){
					$('#bd_role_form_update').form('submit',{ 
						url:'authority/role!updateRole.action', 
						onSubmit:function(){ 
							return $(this).form('validate'); 
						}, 
						success:function(data){ 
						 		if(data == "fail"){
						 		   $.messager.alert('提示', '该角色已存在！', 'warning');
						 		}else if(data == "success"){
						 			$.messager.alert('提示', '修改成功！', 'info');
									parent.$('#role_update').window('close');
									$('#rolelist_table').datagrid('reload');
						 		}
						} 
					}); 			
				}
		$('#updateorgrolefindtree').window({
						title:'部门列表', 
						width: 280,
						modal: true,
						shadow: false,
						closed: true,
						maximizable:false,
						minimizable:false,
						height: 300
		
		})
		function openUpOrgRoleTree(){
			$('#updateorgrolefindtree').window('refresh');
			$('#updateorgrolefindtree').window({href:'pages/authority/updateorganizationroletree.jsp'});
			$('#updateorgrolefindtree').window('open');
		}
	</script>
  </head>
  
  <body>
  	<div id="updateorgrolefindtree"></div>
    <br>
    <div style="width: 96%">
       <form id="bd_role_form_update" method="post">
       	  <input type="hidden" name="updateorgroleid" id="updateorgroleid" value="">
       	  <input type="hidden" name="oldupdateorgroleid" id="oldupdateorgroleid" value="${requestScope.updateorgroleid}">
          <input type="hidden" name="role.id" value="${role.id }"/>
          <table style="text-align: center;" cellpadding="3">
         	 <tr>
              <td width="105px" align="right">部门名称：</td>
              <td>
               		 <input type="text" style="width:200px;" id="updateorgrolename" name="updateorgrolename" value="${requestScope.updateorgrolename}" onclick="openUpOrgRoleTree();"/>
              </td>
            </tr>
            <tr>
              <td align="right">角色名称：</td>
              <td>
               		<input type="text" style="width:200px;" name="role.name" value="${role.name }" class="easyui-validatebox"  required="true" />
              </td>
            </tr>
             <tr>
              <td align="right">角色职能：</td>
              <td> 
                    <input type="text" style="width:200px;" name="role.rfunction" value="${role.rfunction }"   />
              </td>
            </tr>
          </table>
            <div align="center" style="width: 100%;height: 100%;padding-top: 20px;">
				<a href="javascript:bdroleupdateFormSubmit();" class="easyui-linkbutton" icon="icon-save" id="roleUpdate">保存</a>
			    <a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#role_update').window('close');">关闭</a>
	         </div> 
       </form>
    </div>
  </body>
</html>
