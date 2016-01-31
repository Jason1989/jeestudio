<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
 	request.setAttribute("columnID",RandomGUID.geneGuid());//生成表单列ID
 	request.setAttribute("lineCount","0");//生成换行计数
%>
<jsp:include page="view-page.jsp?des=1"></jsp:include>
	