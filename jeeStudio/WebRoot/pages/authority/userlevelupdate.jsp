<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户级别修改页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/page-ext.js"></script>
	<script type="text/javascript">
	    $(function(){			
			function bduserlevelupdateFormSubmit(){
				$('#bd_userlevelupdate_form').form('submit',{ 
					url:'authority/userlevel!saveUpdateUserLevel.action', 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
					 		if(data == "fail"){
					 		   $.messager.alert('提示', '该级别已存在!!!!', 'warning');
					 		}else if(data == "success"){
								parent.$('#bd_userlevel_update').window('close');
								$('#bd_userlevellist_table').datagrid('reload');
					 		}
					} 
				}); 			
			}
			$('#bduserlevelupdateFormSaveButton').bind('click',bduserlevelupdateFormSubmit);
		});
	</script>
  </head>
  
  <body>
    <br>
    <div style="width: 94%">
       <form id="bd_userlevelupdate_form" method="post">
          <table align="center" cellpadding="4">
            <tr>
              <td align="right">
              	级别名称：
               </td>
               <td align="left">
                 <input type="hidden"  name="userlevel.id" value="${userlevel.id }"/>
                 <input type="text" name="userlevel.levelname" value="${userlevel.levelname }" class="easyui-validatebox" required="true" validType="length[0,10]" /> <span style="color: red;">*</span> 
               </td>
            </tr>
            <tr>
              <td align="right">
              	 级别号：
               </td>
               <td align="left">
                 <input type="text"  name="userlevel.levelnumber" value="${userlevel.levelnumber }"  class="easyui-validatebox" required="true" validType="number" /> <span style="color: red;">*</span>   级别号越小级别越大，1级别最大。 
               </td>
            </tr>
            <tr>
              <td align="right" valign="top">
              	 级别描述：
               </td>
               <td align="left">
                 <textarea rows="5" cols="30" name="userlevel.levelnote" >${userlevel.levelnote }</textarea> 
               </td>
            </tr>
            
          </table>
             <div align="center" style="width: 100%;height: 100%;padding-top: 20px;">
				<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bduserlevelupdateFormSaveButton">保存</a>
			    <a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_userlevel_update').window('close');">关闭</a>
	         </div> 
       </form>
    </div>
  </body>
</html>
