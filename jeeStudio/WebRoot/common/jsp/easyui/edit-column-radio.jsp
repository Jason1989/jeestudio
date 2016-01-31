<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
 <c:forEach items="${dictionaryData}" var="dictionaryDataMap" > 
	<c:choose>
		<c:when test="${dictionaryDataMap.key ==data}">
			  <input  type="radio"  name="${name}"  value="${dictionaryDataMap.key}"  checked="checked" class="radio">
			  <span style="font-size:12px;" >${dictionaryDataMap.value}</span>
		</c:when>
		<c:otherwise>
			  <input  type="radio"  name="${name}"  value="${dictionaryDataMap.key}"  class="radio">
			  <span style="font-size:12px;" >${dictionaryDataMap.value}</span>
		</c:otherwise>
	</c:choose>
</c:forEach> 
<c:choose>
	<c:when test="${editMode.required}">
		&nbsp;&nbsp;<font size="2" color="red">*</font>
	</c:when>
</c:choose>
		<!-- 验证 -->
<jsp:include page="/common/jsp/easyui/column-validate.jsp"></jsp:include>	