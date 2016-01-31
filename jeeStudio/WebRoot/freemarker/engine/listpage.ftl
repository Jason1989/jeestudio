<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%@page import="com.zxt.compplatform.formengine.util.PropertiesUtil"%>
<%
 	request.setAttribute("listTabPageID",RandomGUID.geneGuid());//生成列表页多标签ID
	String basePath = PropertiesUtil.findSystemPath(request);
%>
<