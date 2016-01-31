<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

Object obj = request.getAttribute("rname");
String rname = "";
if(obj!=null){
	rname = obj.toString();
}
String isAdmin=request.getParameter("isAdmin");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>角色复制</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/page-ext.js"></script>
	<script type="text/javascript">
	 $(function(){		
	 		//选择要复制的角色的窗口
	    	$('#rolelist').datagrid({
                iconCls: 'icon-database-table',
                width: 400,
                height: 450,
                nowrap: true,
                striped: true,
				fit: true,
				border:false,
				fitColumns:true,
                url: 'authority/role!listRoles.action?rname=<%=rname %>&isall=1&orgid=${orgid}',
                idField: 'rid',
                sortName: 'rid',
                sortOrder: 'asc',
                remoteSort: false,
                pageSize:10,
                frozenColumns: [[
					{ field: 'ck', checkbox: true},
                	{field: 'rid',title: '角色ID',width: 80,sortable: true,align:'left'}
				]],
                columns: [[
                	{field: 'rname',title: '角色名',width: 100,sortable: true,align:'left'},                	
                	{field: 'rfunction',title: '角色职能',width: 100,align:'left'}
				]],
                pagination: true,
                rownumbers: true
            });
	 	
			  $('#choose_role').appendTo('body').window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 420,
                height: 450
            });
			//$('#roleCopy').bind('click',bdroleaddFormSubmit);
            //确定选中的被复制的角色
			function roleSelect(){
				var selected = $('#rolelist').datagrid('getSelections');
	     	    if(selected == null || selected.length < 1) {
	              	 $.messager.alert('提示', '请选择数据!', 'warning');
	            }else{
	            	var i = 0;
	            	var name = "";
	            	for(i=0;i<selected.length-1;i++){
	            		name = name+selected[i]['rname']+",";
	            	}
	            	name = name+selected[i]['rname'];
	            	
	            	 $('#copyNames').val(name);
	            	 
					 $('#choose_role').window('close');
	            }
			}
			$('#roleSelect').bind('click',roleSelect);
		});
		function bdroleaddFormSubmit(){
				var copyNames = $('#copyNames').val();
				$("#roleCopy").attr('disabled',true);//设置按钮为不可用，防止多次提交			
				$('#copy_role_form').form('submit',{ 
					url:'authority/role!roleCopy.action?roleCopyNames='+copyNames, 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
					 		if(data == "fail"){
					 		   $.messager.alert('提示', '角色复制失败!!!!', 'warning');
					 		}else if(data == "success"){
								parent.$('#role_copy').window('close');
								$.messager.alert('提示','角色复制成功','info');
								$('#rolelist_table').datagrid('reload');
								$("#roleCopy").attr('disabled',false);		
					 		}
					} 
				}); 			
			}
		function chooseRole(){			
			$("#choose_role").window('open');
		}
		//验证角色名称是否存在
		function  valideRoldName(){
			var roleName = $('#roleName').val();
			if(roleName!=""){
				$.post(
					'authority/role!roleNameExist.action', 
					{
						roleName: roleName
					},
					function(data){
						if(data=="fail"){
							$.messager.alert('提示', '角色已存在!', 'warning');
							return false;
						}else{
							return true;
						}
					});
			}
		}
		$('#copyorgrolefindtree').window({
					    title:'部门列表', 
						width: 280,
						modal: true,
						shadow: false,
						closed: true,
						maximizable:false,
						minimizable:false,
						height: 300
		})
		function openCopyOrgRoleTree(){
			$('#copyorgrolefindtree').window('refresh');
			$('#copyorgrolefindtree').window({href:'pages/authority/copyorganizationroletree.jsp'});
			$('#copyorgrolefindtree').window('open');
		}
	</script>

  </head>
  
  <body>
  	<div id="copyorgrolefindtree"></div>
    <br>
    <div style="width: 96%">
       <form id="copy_role_form" method="post">
        <input type="hidden" name="copyorgroleid" id="copyorgroleid" value="${requestScope.copyorgroleid}">
          <table style="text-align: center;" cellpadding="3">
          	<tr>
              <td width="105px" align="right">部门名称：</td>
              <td align="left">
               		 <input type="text" style="width: 200px" id="copyorgrolename" name="copyorgrolename" value="${requestScope.copyorgrolename}" onclick="openCopyOrgRoleTree();"/>
               		 <font color="red">*</font>
              </td>
            </tr>
            <tr>
              <td  align="right">被复制角色名称：</td>
              <td align="left">
               		<input type="text" style="width: 200px" id="copyNames"  class="easyui-validatebox"   onclick="chooseRole()" readonly="readonly" />
              </td>
            </tr>
            <tr>
              <td  align="right">角色名称：</td>
              <td align="left">
               		<input type="text" style="width: 200px" name="role.name" id="roleName" class="easyui-validatebox"  required="true" onchange="valideRoldName()" />
               		<font color="red">*</font>
              </td>
            </tr>
             <tr>
              <td  align="right">角色职能：</td>
              <td align="left"> 
                    <input type="text" style="width: 200px" name="role.rfunction" />
              </td>
            </tr>
          </table>
            <div align="center" style="width: 100%;height: 100%;padding-top: 20px;">
				<a href="javascript:bdroleaddFormSubmit();" class="easyui-linkbutton" icon="icon-save" id="roleCopy" >保存</a>&nbsp;
			    <a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#role_copy').window('close');">关闭</a>
	         </div> 
       </form>
    </div>
    <div id="choose_role" title="角色列表">
		<div class="easyui-layout">
			<div region="north" border="false" style="height:360px;">
    			<table id="rolelist" ></table>
			</div>
			<div region="center" align="center" style="height:40px;padding-top: 10px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-save" id="roleSelect">确定</a>&nbsp;
			    <a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-cancel" id="roleClose" onclick="$('#choose_role').window('close');">关闭</a>
			    <script>
				    $(function(){
				    	$('#roleSelect').linkbutton({text:'确定'});
				    	$('#roleClose').linkbutton({text:'关闭'});
				    });
			    </script>
			</div>
		</div>
    </div>
  </body>
</html>
