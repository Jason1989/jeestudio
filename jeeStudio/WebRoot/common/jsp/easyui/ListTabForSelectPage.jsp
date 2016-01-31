<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%@ page trimDirectiveWhitespaces="true" %>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/page-ext.js"></script>
  	<%
	 	request.setAttribute("listTabPageID",RandomGUID.geneGuid());//生成列表页多标签ID
	%>
  	<c:choose>
		<c:when test="${empty listPage.isUseTab}">
			 <c:set scope="request" var="isUseTab" value="false"  ></c:set>
		</c:when>
		<c:otherwise>
			 <c:set scope="request" var="isUseTab" value="${listPage.isUseTab}"  ></c:set>
		</c:otherwise>
	</c:choose>
<!-- 判断是否引用tab标签页 -->
	<c:choose>
		<c:when test="${isUseTab}">
			<div  id="${listTabPageID}" >
			<div   title="${listPage.title}" iconCls="icon-menu" closable="false">
				 <jsp:include page="list-page.jsp"></jsp:include>
			</div>
			<s:iterator  value="#request.listPage.tabs" status="stauts" >
					   <c:choose>
						     <c:when test="${type eq 'editPage'}">
						     		<div  title="${title}" iconCls="icon-menu" href="<%=request.getContextPath()%>/formengine/editPageAction.action?formId=${url}${urlParmer}" style="padding:1px;"></div>
							 </c:when>
						     <c:when test="${type eq 'listPage'}">
						     		<div  title="${title}" iconCls="icon-menu" href="<%=request.getContextPath()%>/formengine/listPageAction.action?formId=${url}${urlParmer}" style="padding:1px;"></div>
							 </c:when>
						     <c:when test="${type eq 'viewPage'}">
						     		<div  title="${title}" iconCls="icon-menu" href="<%=request.getContextPath()%>/formengine/viewPageAction.action?formId=${url}${urlParmer}" style="padding:1px;"></div>
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
				<div   class="easyui-tabs" fit=true >
					<div  title="${listPage.title}" iconCls="icon-menu" closable="false"  >
						  <jsp:include page="list-page-select.jsp"></jsp:include>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<jsp:include page="list-page-select.jsp"></jsp:include>
			</c:otherwise>
		</c:choose>
	  </c:otherwise>
	</c:choose>