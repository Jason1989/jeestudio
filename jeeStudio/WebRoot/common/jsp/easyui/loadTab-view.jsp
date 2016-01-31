<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	Object obj = request.getAttribute("appid");
	String appid = "";
	if(obj!=null){
		appid = obj.toString();
	}
%>

<div id="loadTab-view-div"   >
		<div   title="${viewPage.title}">
		 	<jsp:include page="view-page.jsp"></jsp:include>
		</div>
		<s:iterator  value="#request.viewPage.tabs" status="stauts" >
			     <c:choose>
				     <c:when test="${type eq 'editPage'}">
				     	<div  title="${title}" href="<%=request.getContextPath()%>/formengine/editPageAction.action?formId=${url}${urlParmer}&PARENT_APP_ID=<%=appid %>" style="padding:1px;"></div>
					 </c:when>
				     <c:when test="${type eq 'listPage'}">
				     	<div  title="${title}" href="<%=request.getContextPath()%>/formengine/listPageAction.action?formId=${url}${urlParmer}&PARENT_APP_ID=<%=appid %>" style="padding:1px;"></div>
					 </c:when>
				     <c:when test="${type eq 'viewPage'}">
				     	<div  title="${title}" href="<%=request.getContextPath()%>/formengine/viewPageAction.action?formId=${url}${urlParmer}&PARENT_APP_ID=<%=appid %>" style="padding:1px;"></div>
					 </c:when>
				  </c:choose>
		</s:iterator>
</div>
<script>
	$(function(){
		$('#loadTab-view-div').tabs({
			border:true,
			fit:true
		});	
	});
</script>