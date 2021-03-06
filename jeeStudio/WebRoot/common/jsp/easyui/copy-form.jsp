<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@taglib prefix="FCK" uri="/WEB-INF/FCKeditor.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
<table align="left" width="647px">
	<!-- 默认分组列 -->
	<tr>
		<s:iterator value="#request.editPage.editColumn" status="stauts"
			id="editColumnList">
			<c:choose>
				<c:when test="${textColumn.groupId eq '0' }">
					<!-- 默认分组下的字段 -->
					<jsp:include page="/common/jsp/easyui/edit-column.jsp"></jsp:include>
					<!-- 默认分组下的字段 -->
				</c:when>
			</c:choose>
		</s:iterator>
	</tr>
	<!--遍历分组 -->

	<c:choose>
		<c:when test="${editPage.isGroupVisible}">

			<s:iterator value="#request.editPage.groups" status="stauts"
				id="groupObject">
				<c:if test='${groupObject.visible=="true"}'>
				<tr id="${groupObject.title}_id" style="display: block">
					<td colspan="4">
						<div class="panel-header panel-header-noborder"
							style="font-weight: normal;">
							&nbsp;${groupObject.title}
						</div>
						<table>
							<s:iterator value="#request.editPage.editColumn" status="stauts"
								id="editColumnList">
								<c:choose>
									<c:when test="${groupObject.id==textColumn.groupId}">
										<!--  该分组下的字段 -->
										<jsp:include page="/common/jsp/easyui/edit-column.jsp"></jsp:include>
										<!--  该分组下的字段 -->
									</c:when>
								</c:choose>
							</s:iterator>
						</table>
					</td>
				</tr>
				</c:if>
				<c:if test='${groupObject.visible=="false"}'>
				<tr id="${groupObject.title}_id" style="display: none">
					<td colspan="4">
						<div class="panel-header panel-header-noborder"
							style="font-weight: normal;">
							&nbsp;${groupObject.title}
						</div>
						<table>
							<s:iterator value="#request.editPage.editColumn" status="stauts"
								id="editColumnList">
								<c:choose>
									<c:when test="${groupObject.id==textColumn.groupId}">
										<!--  该分组下的字段 -->
										<jsp:include page="/common/jsp/easyui/edit-column.jsp"></jsp:include>
										<!--  该分组下的字段 -->
									</c:when>
								</c:choose>
							</s:iterator>
						</table>
					</td>
				</tr>
				
				</c:if>
				<%
					request.setAttribute("lineCount", "0");
				%>
			</s:iterator>
		</c:when>
	</c:choose>
	<!--遍历分组 end -->
</table>
