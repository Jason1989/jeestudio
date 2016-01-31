<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%
 	request.setAttribute("columnID",RandomGUID.geneGuid());//生成表单列ID
 	request.setAttribute("lineCount","0");//生成换行计数
%>
<form  method="post" id="editform_${editPage.id}" action="com_dynamicSave.action" >
	<c:import url="edit-form.jsp"></c:import>
	<!-- 操作类型 -->
	<input type="hidden" name="opertorType" value="${opertorType}" />
	<input type="hidden" name="formId" value="${editPage.id}" />
</form>

<s:iterator value="#request.editPage.editColumn" status="stauts" id="editColumnList" >
   	<c:choose>
   		<c:when test="${type==9}"><!-- 上传组件的窗体格式表单  -->
	 			<jsp:include page="/common/components/upload-file/upload-file.jsp"></jsp:include>
		</c:when>
   	</c:choose>
</s:iterator>