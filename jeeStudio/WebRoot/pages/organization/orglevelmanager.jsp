<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>组织机构内用户级别的管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script>
		$(function(){
			$('#level_org').combobox({
			    url:'authority/userlevel!getAllLevel.action?time='+new Date().getTime(),
			    valueField:'id',
			    textField:'levelname',
			    listHeight:300,
			    editable:false,
			    panelHeight:'auto'
	 	 });
	 	 	 $('#level_org').combobox("setValue","${usertable.levelnumber}");
	   });
	   
	   function updateuserlevelbutton(){
	   		var levelID = $('#level_org').combobox('getValue');
	   	 	$.post("organization/organization!updateUser_UserLevel.action?levelID="+levelID+"&&userid="+${usertable.userid},function(data){
             			$.messager.alert('提示','设置级别成功','info');	
             			parent.$('#bd_org_levelmanager').window('close');		         				
						$('#bd_usertablelist_table').datagrid('reload');
             });
	   }
		
	</script>
  </head>
  
  <body >
  <div>
  
    <table >
      <tr>
       <td width="190px;" height="100px;" align="center">
			<div>
				<select id="level_org"  style="width: 180px;text-align: center;">
				</select>
			</div>
       </td> 
      </tr>
    </table>
   		 <div >
	      <div align="center" style="width: 100%;height: 100%;padding-top: 20px;">
			<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" onclick="updateuserlevelbutton()">保存</a>
			<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_org_levelmanager').window('close');">关闭</a>
	       </div>
		</div>
    </div>
  </body>
</html>
