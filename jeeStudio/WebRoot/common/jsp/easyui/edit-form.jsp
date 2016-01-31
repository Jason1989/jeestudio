<%@page import="com.zxt.compplatform.formengine.entity.view.TextColumn"%>
<%@page import="com.zxt.framework.common.util.RolesCommon"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.ColumnRoles"%>
<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.EditPage"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@taglib prefix="FCK" uri="/WEB-INF/FCKeditor.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
  String sessionRole = (String)request.getSession().getAttribute("roles");

	//第一次固化。此参数值作用是 在编辑页时  让其 VALUE动态显示内容。
	//edit-columnSolidify.jsp 此页面是固化时添加。2012-11-26 GUOWEIXIN
	String valueDefine=request.getAttribute("valueDefine").toString();
%>
<style type="text/css">
	.textIoc {
		background-image: url(<%=request.getContextPath()%>/common/images/ioc-render.gif )!important;
		background-repeat: no-repeat;
		background-position: right;
		cursor: pointer;
	}
</style>
<script type="text/javascript">
	//级联触发函数
	function jilian_onclick(id1,id2,dictionaryId){
		$('#'+id1).combobox();	
		$('#'+id1).combobox({
			onChange:function(newValue, oldValue){
				$('#'+id2).combobox('setValue','');
			    $('#'+id2).combobox('reload','com_loadCasCadeSel.action?dictionaryId='+dictionaryId+"&newValue="+newValue);
			}
		});
		//版本号选择
		$('#'+id2).combobox({
			valueField:'key',
			textField:'value',
			panelHeight:100
		});
	
	}
</script>
<table align="left" width="750px" class="layout-body">
	<!-- 默认分组列 --> 
	<tr>
		<s:iterator value="#request.editPage.editColumn" status="stauts"
			id="editColumnList">
			<c:choose>
				<c:when test="${textColumn.groupId eq '0' }">
					<!-- 默认分组下的字段 -->
							<% 
								ColumnRoles coro = (ColumnRoles)request.getAttribute("roles");
								if( coro!=null && "true".equals(coro.getIsCustomRole()) ){
									boolean writeflag = RolesCommon.hasWriteRole(coro.getIsCustomRoleWriteName(),sessionRole);
									boolean readflag = RolesCommon.hasReadRole(coro.getIsCustomRoleReadName(),sessionRole);
									if(writeflag && "true".equals(coro.getIsCustomRoleWrite())){
										if("1".equals(valueDefine)){
							%>				
												<jsp:include page="/common/jsp/easyui/edit-columnSolidify.jsp"></jsp:include>
										<%
										}else{	
										%>		
												<jsp:include page="/common/jsp/easyui/edit-column.jsp"></jsp:include>
							<% 
										}
									}else if(readflag && "true".equals(coro.getIsCustomRoleRead())){
										TextColumn teco = (TextColumn)request.getAttribute("textColumn");
										teco.setReadOnly(java.lang.Boolean.TRUE);
										if("1".equals(valueDefine)){
							%>
												<jsp:include page="/common/jsp/easyui/edit-columnSolidify.jsp"></jsp:include>
										<%
										}else{	
										%>		
												<jsp:include page="/common/jsp/easyui/edit-column.jsp"></jsp:include>
							<% 
										}
									}
								}else{
									if("1".equals(valueDefine)){
							%>
										<jsp:include page="/common/jsp/easyui/edit-columnSolidify.jsp"></jsp:include>
									<%
									}else{	
									%>		
										<%=valueDefine%><jsp:include page="/common/jsp/easyui/edit-column.jsp"></jsp:include>			
							<% 
									}
								}
							%>

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
				<tr id="${groupObject.title}_edit_id" style="display: block">
					<td colspan="4">
						<table>
							<tr><td colspan="4" id="nav"><ul><li><a href="javascript:void(0)" class="nocurrent"><span>${groupObject.title}</span></a></li></ul></td></tr>
							<s:iterator value="#request.editPage.editColumn" status="stauts"
								id="editColumnList">
								<c:choose>
									<c:when test="${groupObject.id==textColumn.groupId}">
										<%
											if("1".equals(valueDefine)){	
										%>
												<!--  该分组下的字段 -->
												<jsp:include page="/common/jsp/easyui/edit-columnSolidify.jsp"></jsp:include>
												<!--  该分组下的字段 -->
										<%
											}else{
										%>
												<!--  该分组下的字段 -->
												<jsp:include page="/common/jsp/easyui/edit-column.jsp"></jsp:include>
												<!--  该分组下的字段 -->
										<%
											}
										%>
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
						<table>
							<tr><td colspan="4" id="nav"><ul><li><a href="javascript:void(0)" class="nocurrent"><span>${groupObject.title}</span></a></li></ul></td></tr>
							<s:iterator value="#request.editPage.editColumn" status="stauts"
								id="editColumnList">
								<c:choose>
									<c:when test="${groupObject.id==textColumn.groupId}">
										<%
											if("1".equals(valueDefine)){	
										%>
												<!--  该分组下的字段 -->
												<jsp:include page="/common/jsp/easyui/edit-columnSolidify.jsp"></jsp:include>
												<!--  该分组下的字段 -->
										<%
											}else{
										%>
												<!--  该分组下的字段 -->
												<jsp:include page="/common/jsp/easyui/edit-column.jsp"></jsp:include>
												<!--  该分组下的字段 -->
										<%
											}
										%>
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
<script type="text/javascript">

	if(document.readyState=="complete"){
	var userid='${userID}';
	
	<%
		try{
		  EditPage editPage=(EditPage) request.getAttribute("editPage");
		  if(editPage.getJsRules()!=null){
	%>
				eval(decodeURIComponent('<%=editPage.getJsRules() %>'));
	<%
		  }
		}catch(Exception e){
		}
	%>
	}

</script>
