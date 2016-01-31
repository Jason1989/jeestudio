<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="com.zxt.compplatform.formengine.constant.Constant"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%
	Object rolesObject=request.getSession().getAttribute("roles");
	String  roles ="";
	if(rolesObject!=null){
		roles =request.getSession().getAttribute("roles").toString();
	}
	
	if(roles.indexOf("Second_Assets")!=-1){
		request.setAttribute(Constant.CUSTOM_PROCESSDEFID_ID,"1931622166");
	}
%>