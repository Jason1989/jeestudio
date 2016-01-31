<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%@page import="com.zxt.compplatform.formengine.util.PropertiesUtil"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!-- 
	普通页面列表
	通过ListPageAction类中的 值返回页面进行加载。
 -->
<%
 	request.setAttribute("listTabPageID",RandomGUID.geneGuid());//生成列表页多标签ID
	String basePath = PropertiesUtil.findSystemPath(request);
%>
  	<c:choose>
		<c:when test="${empty listPage.isUseTab}">
			 <c:set scope="request" var="isUseTab" value="false"  ></c:set>
		</c:when>
		<c:otherwise>
			 <c:set scope="request" var="isUseTab" value="${listPage.isUseTab}"  ></c:set>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${isUseTab}">
			<div  id="${listTabPageID}" >
			<div   title="${listPage.title}" iconCls="icon-menu" closable="false">
				 <jsp:include page="list-page.jsp"></jsp:include>
			</div>
			<s:iterator  value="#request.listPage.tabs" status="stauts" >
					   <c:choose>
						     <c:when test="${type eq 'editPage'}">
						     		<div  title="${title}" iconCls="icon-menu" href="<%=basePath%>/formengine/editPageAction.action?formId=${url}${urlParmer}" style="padding:1px;"></div>
							 </c:when>
						     <c:when test="${type eq 'listPage'}">
						     		<div  title="${title}" iconCls="icon-menu" href="<%=basePath%>/formengine/listPageAction.action?formId=${url}${urlParmer}" style="padding:1px;"></div>
							 </c:when>
						     <c:when test="${type eq 'viewPage'}">
						     		<div  title="${title}" iconCls="icon-menu" href="<%=basePath%>/formengine/viewPageAction.action?formId=${url}${urlParmer}" style="padding:1px;"></div>
							 </c:when>
					  </c:choose>
			</s:iterator>
		</div>
      <script>
			$('#${listTabPageID}').tabs({
				fit:true,
				closable:false
			});
	  </script>
	  </c:when>
	  <c:otherwise>
	  	<c:choose>
			<c:when test="${!empty listPage.title}">
				<div class="easyui-panel" fit="true" title="${listPage.title}" iconCls="icon-menu" collapsible="false" minimizable="false" maximizable="false" >
					<jsp:include page="list-page.jsp"></jsp:include>
				</div>
			</c:when>
			<c:otherwise>
				<jsp:include page="list-page.jsp"></jsp:include>
			</c:otherwise>
		</c:choose>
	  </c:otherwise>
	</c:choose>