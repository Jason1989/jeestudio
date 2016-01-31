<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'userlevelmanager.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body >
  <div>
  
    <table >
      <tr>
       <td>
           <br>&nbsp;&nbsp;&nbsp;&nbsp;
           <textarea rows="8" cols="20">
		     dgasd
		   </textarea>
       </td>
       <td> &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
       <td>
            <br>&nbsp;&nbsp;&nbsp;&nbsp;
       		<textarea rows="8" cols="20">
		     dgasd
		    </textarea>
       </td>
      </tr>
    </table>
   		 <div >
	      <div align="center" style="width: 100%;height: 100%;padding-top: 20px;">
			<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bdorgaddFormSaveButton">保存</a>
			<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#role_permanager').window('close');">关闭</a>
	       </div>
		</div>
    </div>
  </body>
</html>
