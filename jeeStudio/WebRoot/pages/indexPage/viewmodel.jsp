<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'viewmodel.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <br>
    <div style="width: 96%">
       <form id="bd_role_form_update" method="post">
          <table style="text-align: left;" cellpadding="3">
            <tr>
				<td>
					模块名称：
				</td>
				<td>
					<input name="model.name"  size="25" value="${model.name}" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>
					模块地址：
				</td>
				<td>
					<textarea cols="50" rows="4" name="model.url"  readonly="readonly">${model.url}</textarea>
				</td>
			</tr>
			<tr>
				<td>
					描述：
				</td>
				<td>
					<textarea cols="50" rows="4" name="model.description" readonly="readonly"
						class="easyui-validatebox">${model.description }</textarea>
				</td>
			</tr>
          </table>
       </form>
    </div>
  </body>
</html>
