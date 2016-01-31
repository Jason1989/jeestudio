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
    
    <title>My JSP 'roleadd.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/page-ext.js"></script>
	<script type="text/javascript">
	 $(function(){			
			function bdroleaddFormSubmit(){
				$('#bd_role_form').form('submit',{ 
					url:'authority/role!roleAdd.action', 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
					 		if(data == "fail"){
					 		   $.messager.alert('提示', '该角色已存在!!!!', 'warning');
					 		}else if(data == "success"){
								parent.$('#role_add').window('close');
								$.messager.alert('提示','添加成功','info');
								$('#rolelist_table').datagrid('reload');
					 		}
					} 
				}); 			
			}
			$('#roleAdd').bind('click',bdroleaddFormSubmit);
		});
		$('#orgrolefindtree').window({
						title:'部门列表', 
						width: 280,
						modal: true,
						shadow: false,
						closed: true,
						maximizable:false,
						minimizable:false,
						height: 300
		})
		function openOrgRoleTree(){
			$('#orgrolefindtree').window('refresh');
			$('#orgrolefindtree').window({href:'pages/authority/organizationroletree.jsp?isAdmin=<%=isAdmin%>'});
			$('#orgrolefindtree').window('open');
		}
	</script>

  </head>
  
  <body>
  	<div id="orgrolefindtree"></div>
    <br>
    <div style="width: 96%">
       <form id="bd_role_form" method="post">
       	  <input type="hidden" name="orgroleid" id="orgroleid" value="${requestScope.orgroleid}">
          <table style="text-align: center;" cellpadding="3">
          	<tr>
              <td width="105px" align="right" >部门名称：</td>
              <td>
               		 <input type="text" style="width:200px;" id="orgrolename" name="orgrolename" value="${requestScope.orgrolename}" onclick="openOrgRoleTree();"/>
              </td>
            </tr>
            <tr>
              <td align="right">角色名称：</td>
              <td>
               		<input type="text" style="width:200px;" name="role.name"  class="easyui-validatebox"  required="true"  />
              </td>
            </tr>
             <tr>
              <td align="right">角色职能：</td>
              <td> 
                    <input type="text" style="width:200px;" name="role.rfunction"   />
              </td>
            </tr>
          </table>
            <div align="center" style="width: 100%;height: 100%;padding-top: 20px;">
				<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="roleAdd">保存</a>
			    <a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#role_add').window('close');">关闭</a>
	         </div> 
       </form>
    </div>
  </body>
</html>
