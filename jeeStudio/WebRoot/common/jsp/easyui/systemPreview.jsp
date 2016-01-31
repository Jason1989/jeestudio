<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style type="text/css">
   .skin_list{
	   	text-align: right;
	   	margin-top:5px;
   }
   .skin_theme_title{
	   font-size:18px;
	   font-weight: bolder;
	   font-family: 黑体;
	   float: left;
	   padding:15px;
   }
</style>
<div class="easyui-panel" fit="true" title="系统预览" style="padding-top:13px;padding-left:18px;">
	<c:forEach items="${systemList}" var="system">
			<div style="float: left; padding-left: 13px;">
		<a href="formengine/zsf_.action?sysName=${system.resKey}" target="_blank" style="text-decoration: none;">
				<img src="<%=basePath%>images/blue.bmp"
					style="width: 180px; height: 120px; border: 2px solid gray; margin: 5px;">
				<div style="display: block;line-height:16px;width:180px;text-align: center;">
					系统名称：${system.text}
				</div>
			</a>
			</div> 
	</c:forEach>