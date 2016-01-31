<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.compplatform.formengine.util.TreeUtil"%>
<%@page import="java.util.List"%>
<div class="abs" id="bar_top">
	 <%
	 	String menuButton=TreeUtil.createMenuButton((List)request.getAttribute("treeDataList"),request.getAttribute("systemId").toString(),out);
		out.print(menuButton);
	 %>
</div>