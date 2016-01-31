<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<script>
			
	$(function(){
				$.extend($.fn.validatebox.defaults.rules, {   
					    number: {   
					        validator: function (value, param) {
	            			return /^\d+$/.test(value)&&(value.length >= param[0])&&(value.length <= param[1]);
	        				},
	        				message: '请输入数字，至少 {0} 位,最长 {1} 位.'
					    }   
					});  
	
	});

	</script>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户级别添加</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
	 $(function(){			
			function bduserleveladdFormSubmit(){
				$('#bd_userleveladd_form').form('submit',{ 
					url:'authority/userlevel!saveAddUserLevel.action', 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
					 		if(data == "fail"){
					 		   $.messager.alert('提示', '该级别已存在!!!!', 'warning');
					 		}else if(data == "success"){
								parent.$('#bd_userlevel_add').window('close');
								$('#bd_userlevellist_table').datagrid('reload');
					 		}
					} 
				}); 			
			}
			$('#bduserleveladdFormSaveButton').bind('click',bduserleveladdFormSubmit);
		});
	</script>
  </head>
  
  <body >
     <br>
    <div style="width: 94%">
       <form id="bd_userleveladd_form" method="post">
          <table align="center" cellpadding="4">
            <tr>
              <td align="right">
              	级别名称：
               </td>
               <td align="left">
                 <input type="text" name="userlevel.levelname" class="easyui-validatebox" required="true" validType="length[0,10]" /> <span style="color: red;">*</span> 
               </td>
            </tr>
            <tr>
              <td align="right">
              	 级别号：
               </td>
               <td align="left">
                 <input type="text"  name="userlevel.levelnumber" class="easyui-validatebox" required="true" validType="number[1,5]"  /> <span style="color: red;">*</span>  级别号越小级别越大，1级别最大。 
               </td>
            </tr>
            <tr>
              <td align="right" valign="top">
              	 级别描述：
               </td>
               <td align="left">
                 <textarea rows="5" cols="30" name="userlevel.levelnote"></textarea> 
               </td>
            </tr>
            
          </table>
             <div align="center" style="width: 100%;height: 100%;padding-top: 20px;">
				<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bduserleveladdFormSaveButton">保存</a>
			    <a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_userlevel_add').window('close');">关闭</a>
	         </div> 
       </form>
    </div>
  </body>
</html>
