<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>

<%
 	request.setAttribute("tabEditor",RandomGUID.geneGuid());//生成表单列ID
 	request.setAttribute("initEditPage",request.getAttribute("editPage"));//生成表单列ID
%>
<div id="${tabEditor}" class="easyui-tabs" >
		<s:iterator  value="#request.editPage.tabs" status="stauts" >
			<div  title="${title}" style="padding:1px;">
				  <c:choose>
				     <c:when test="${type eq 'editPage'}">
				     	<c:set scope="request" var="editPage" value="${page}"  ></c:set>
				    	<jsp:include page="tab-editpage.jsp"></jsp:include>
				    </c:when>
				     <c:when test="${type eq 'listPage'}">
				     	<c:set scope="request" var="listPage" value="${page}"  ></c:set>
				    	<jsp:include page="list-page.jsp"></jsp:include>
				     </c:when>
				     <c:when test="${type eq 'viewPage'}">
				        <c:set scope="request" var="viewPage" value="${page}"  ></c:set>
				      	<jsp:include page="tab-viewpage.jsp"></jsp:include>
				     </c:when>
				  </c:choose>
			</div>
		</s:iterator>
</div>
<script>
	$(function(){
		$('#${tabEditor}').tabs({
			border:true,
			fit:true,
			tools:[{
					iconCls:'icon-save',
					text:'全部保存',
					handler: function(){
						bulkEdit('${initEditPage.idsJson}','editPageWindow_${initEditPage.id}');
					}
				}
			]
		});	
	});
</script>