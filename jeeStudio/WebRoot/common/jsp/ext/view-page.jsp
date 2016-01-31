<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<%
	int i=0;
%>
<table align="center" >
	<!-- 默认分组 -->
	<tr>
		<s:iterator value="#request.viewPage.viewColumn" status="stauts" id="editColumnList" >
			    <c:choose>
				      <c:when test="${textColumn.groupId eq '0' }">
		     	 		
		     	 		  <td style="font-size:12px;width:40px;" align="right"> 
							    <label style="width:100px" >${textColumn.name }：</label> 
						  </td>
						 <!-- *****************************编辑列字段********************************** -->
								<td width="25%" >
							 	${data}
							</td>
							<%
								i++;
								if(i==2){
									out.write("</tr><tr>");//2列 换行
									i=0;
								}
							%>
		     	 		
				      </c:when>
			    </c:choose>
		</s:iterator>
	</tr>
	<!-- 默认分组 -->
	
	
	<!-- 遍历分组 -->
	<c:choose>
		<c:when test="${viewPage.isGroupVisible}">
			<s:iterator value="#request.viewPage.groups" status="stauts" id="groupObject"  >
				<tr>
					<td colspan="4" >
						<div class="panel-header panel-header-noborder" style="font-weight: normal;width: 660px;" >&nbsp;${groupObject.title}</div>
					</td>
				</tr>
				<%  i=0; %>
				<tr>
					<s:iterator value="#request.viewPage.viewColumn" status="stauts" id="editColumnList" >
						    <c:choose>
							      <c:when test="${textColumn.groupId eq groupObject.id }">
					     	 		 <td style="font-size:12px;width:40px;" align="right"> 
										    <label style="width:100px" >${textColumn.name }：</label> 
									  </td>
									 <!-- *****************************编辑列字段********************************** -->
										<td width="25%" >
										 	${data}
										</td>
										<%
											i++;
											if(i==2){
												out.write("</tr><tr>");//2列 换行
												i=0;
											}
										%>
					     	 		
							      </c:when>
						    </c:choose>
						</s:iterator>
					</tr>
			   </s:iterator>
			</c:when>
		</c:choose>
</table>
