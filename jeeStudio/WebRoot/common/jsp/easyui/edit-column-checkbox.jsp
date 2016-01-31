<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
<c:choose>
	<c:when test="${empty  dictionaryData }"><!-- 不带数据字典的复选框 -->
		<c:choose>
				<c:when test="${data eq '1' }">
					<input type="checkbox" name="${name}" value="1" class="easyui-validatebox" checked="checked" />	
				</c:when>
				<c:otherwise>
					<input type="checkbox" name="${name}" value="1" class="easyui-validatebox" />	
				</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise><!-- 带数据字典的复选框 -->
		<c:forEach items="${dictionaryData}" var="dictionaryDataMap" > 
			 	${dictionaryDataMap.key}
			<c:choose>
				<c:when test="${dictionaryDataMap.value eq 'checked' }">
					<input type="checkbox" name="${name}" value="${dictionaryDataMap.value}" class="easyui-validatebox" checked="checked" />	
				</c:when>
				<c:otherwise>
					<input type="checkbox" name="${name}" value="${dictionaryDataMap.value}" class="easyui-validatebox" />	
				</c:otherwise>
			</c:choose>
	
		</c:forEach> 
	</c:otherwise>
</c:choose>
			
<c:choose>
	<c:when test="${editMode.required}">
		&nbsp;&nbsp;<font size="2" color="red">*</font>
	</c:when>
</c:choose>
<!-- 验证 -->
<jsp:include page="/common/jsp/easyui/column-validate.jsp"></jsp:include>	