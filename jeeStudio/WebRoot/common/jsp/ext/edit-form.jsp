<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@taglib prefix="FCK" uri="/WEB-INF/FCKeditor.tld"%>
<style type="text/css">
	.textIoc{   
	    background-image:url(<%=request.getContextPath()%>/common/image/gis-tree.png) !important;   
	    background-repeat:no-repeat;   
	    background-position:right;   
	 	  cursor:pointer;                 
	}
</style>

<table align="left" width="650px" >
<!-- 默认分组列 -->
<tr>
	<s:iterator value="#request.editPage.editColumn" status="stauts" id="editColumnList" >
	       <c:choose>
			    <c:when test="${textColumn.groupId eq '0' }">
			  		<!-- 默认分组下的字段 -->
	     	 			<jsp:include page="edit-column.jsp"></jsp:include>
	     	 		<!-- 默认分组下的字段 -->
			  </c:when>
			</c:choose>
		</s:iterator>
	</tr>
<!--遍历分组 -->	

	<c:choose>
		<c:when test="${editPage.isGroupVisible}">
		
			<s:iterator value="#request.editPage.groups" status="stauts" id="groupObject"  >
				<tr>
					<td colspan="4" >
						<div class="panel-header panel-header-noborder" style="font-weight: normal;" >&nbsp;${groupObject.title}</div>
					</td>
				</tr>
			   <% request.setAttribute("lineCount","0"); %>	
				<tr>
					<s:iterator value="#request.editPage.editColumn" status="stauts" id="editColumnList" >
					    <c:choose>
						    <c:when test="${groupObject.id==textColumn.groupId}">
								<!--  该分组下的字段 -->
									<jsp:include page="edit-column.jsp"></jsp:include>
				     	 		<!--  该分组下的字段 -->   
						    </c:when>
						</c:choose>
					</s:iterator>
				</tr>
			</s:iterator>
		</c:when>
	</c:choose>
	<!--遍历分组 end -->	
</table>
