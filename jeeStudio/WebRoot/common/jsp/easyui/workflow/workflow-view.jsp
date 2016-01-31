<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<div  class="easyui-tabs"  fit=true >
	<s:iterator value="#request.workItemFormList" status="stauts" id="editColumnList" >
		<c:choose>
	   		<c:when test="${type==0}">
		 		<div  id="${stauts.index}_workinfo" title="表单_${stauts.index}" href="${formID}" >
		 		</div>
			</c:when>
			<c:when test="${type eq 'editPage' }">
				<div  id="${stauts.index}_workinfo" title="表单_${stauts.index}" href="formengine/editPageAction.action?formId=${formID}&APP_ID=${APP_ID}&opertorType=1" >
				</div>
		 	</c:when>
		 	<c:when test="${type eq 'viewPage' }">
				<div  id="${stauts.index}_workinfo" title="表单_${stauts.index}" href="formengine/editPageAction.action?formId=${formID}&APP_ID=${APP_ID}" >
				</div>
		 	</c:when>
	   	</c:choose>
	</s:iterator>
</div>