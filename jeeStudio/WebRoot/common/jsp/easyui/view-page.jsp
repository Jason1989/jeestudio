<%@page import="com.zxt.framework.common.util.RolesCommon"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.ColumnRoles"%>
<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
<% 
request.setAttribute("lineCount","0"); 
String sessionRole = (String)request.getSession().getAttribute("roles");

//第一次固化。此参数值作用是 在编辑页时  让其 VALUE动态显示内容。
//view-columnSolidify.jsp 此页面是固化时添加。2012-11-26 GUOWEIXIN
String valueDefine=request.getAttribute("valueDefine").toString();
%>
<div class="easyui-layout" fit="true">
	<div id="viewPageData_${viewPage.id}" region="center" border="false" style="padding:10px;border:1px solid #ccc;">
		<table align="left" class="layout-body">
			<!-- 默认分组 -->
			<tr>
				<s:iterator value="#request.viewPage.button" status="stauts" id="viewButton" >
					<c:choose>
						<c:when test="${'导出' eq buttonName && 'true' eq visible}">
							<a class="easyui-linkbutton" icon="icon-cancel" onclick="view_export('${buttonParam}');">${buttonName}</a>
							<script>
							function view_export(butParam){
								var id = butParam.substring(0,butParam.indexOf(','));
								var name = butParam.substring(butParam.indexOf(',')+1,butParam.length);
								var url = '<%=request.getContextPath()%>/templatefile/templatefile!export.action?${urlParmer}&formId=${formId}&fileId='+id+'&fileName='+name;
								window.open(url);
							}
							</script>
						</c:when>
		    	 		<c:otherwise></c:otherwise>
					</c:choose>
				</s:iterator>
			</tr>
			<tr>
				<s:iterator value="#request.viewPage.viewColumn" status="stauts" id="editColumnList" >
					    <c:choose>
						      <c:when test="${textColumn.groupId eq '0' }">
					     	 		<c:choose>
					     	 			<c:when test="${textColumn.visible eq false }"></c:when>
					     	 			<c:otherwise>
					     	 				<% 
												ColumnRoles coro = (ColumnRoles)request.getAttribute("roles");
												if( coro!=null && "true".equals(coro.getIsCustomRole()) ){
													boolean readflag = RolesCommon.hasReadRole(coro.getIsCustomRoleReadName(),sessionRole);
													if(readflag){
														if("1".equals(valueDefine)){
											%>
														<c:import url="view-columnSolidify.jsp"></c:import>
											<%
														}else{
											%>			
														<c:import url="view-column.jsp"></c:import>
											<% 
														}
													}
												}else{
													if("1".equals(valueDefine)){
											%>
													<c:import url="view-columnSolidify.jsp"></c:import>
											<%
													}else{
											%>		
													<c:import url="view-column.jsp"></c:import>
											<% 
													}
												}
											%>
										</c:otherwise>
					     	 		</c:choose>
				     	 		</c:when>
					    </c:choose>
				</s:iterator>
			</tr>
			<!-- 默认分组 -->
			
			<!-- 遍历分组 -->
			<c:choose>
				<c:when test="${viewPage.isGroupVisible}">
					<s:iterator value="#request.viewPage.groups" status="stauts" id="groupObject"  >
						<tr id='${groupObject.title}_view_id'>
							<td colspan="4">
								<table>
									<tr><td colspan="4" id="nav"><ul><li><a href="javascript:void(0)" class="nocurrent"><span>${groupObject.title}</span></a></li></ul></td></tr>
									<tr>
										<s:iterator value="#request.viewPage.viewColumn" status="stauts" id="editColumnList" >
											    <c:choose>
												      <c:when test="${textColumn.groupId eq groupObject.id }">
										     	 		<%
										     	 			if("1".equals(valueDefine)){
										     	 		%>
										     	 				<c:import url="view-columnSolidify.jsp"></c:import>
										     	 		<%
										     	 			}else{
										     	 		%>	
										     	 				<c:import url="view-column.jsp"></c:import>
													  	<%
										     	 			}
													  	%>
													  </c:when>
											    </c:choose>
										</s:iterator>
									</tr>
								</table>
							</td>
						</tr>
					  </s:iterator>
					</c:when>
				</c:choose>
		</table>
	</div>
<%
	String des = request.getAttribute("des")+"";
	if(!"1".equals(des)){
%>
	<div region="south" border="false" style="text-align:center;height:45px;padding-top: 13px;">
		<a class="easyui-linkbutton" icon="icon-cancel"  href="javascript:closeWindow('${viewPageDivId}');">关闭</a>	
	</div>
<%
	}
%>
</div>
<jsp:include page="/common/jsp/easyui/view-RulesEngin.jsp"></jsp:include>
