<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%@page import="com.zxt.compplatform.formengine.util.PropertiesUtil"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
 	request.setAttribute("tabEditor",RandomGUID.geneGuid());//生成表单列ID
 	request.setAttribute("initEditPage",request.getAttribute("editPage"));//生成表单列ID
 	String basePathLoadTabEdit = PropertiesUtil.findSystemPath(request);
%>
<div id="${tabEditor}" class="easyui-tabs" >
		<div title="${editPage.title}" >
				<jsp:include page="edit-load.jsp"></jsp:include>
		</div>
		<s:iterator  value="#request.editPage.tabs" status="stauts" >
			
				  <c:choose>
				     <c:when test="${type eq 'editPage'}">
				     	<div cache="true" title="${title}" style="padding:1px;" href="<%=basePathLoadTabEdit%>/formengine/editPageAction.action?formId=${url}&APP_ID=${APP_ID}&workflowForm=true&lprid=${lprid}&listpageId=${listpageId}&atw=1&opertorType=${opertorType}" >
				     	</div>
				    </c:when>
				     <c:when test="${type eq 'listPage'}">
				     	<div cache="false" title="${title}" style="padding:1px;" href="<%=basePathLoadTabEdit%>/formengine/listPageAction.action?formId=${url}&APP_ID=${APP_ID}&PARENT_APP_ID=${APP_ID}&atw=1" >
			 	    	</div>
				     </c:when>
				     <c:when test="${type eq 'viewPage'}">
				     	<div  title="${title}" style="padding:1px;">
					        <c:set scope="request" var="viewPage" value="${page}"  ></c:set>
					      	<jsp:include page="tab-viewpage.jsp"></jsp:include>
				      	</div>
				     </c:when>
				  </c:choose>
			
		</s:iterator>
</div>

<script>
	$(function(){
		$('#${tabEditor}').tabs({
			border:true,
			fit:true,
			closable:false,
			tools:[{
					iconCls:'icon-save',
					text:'全部保存',
					handler: function(){
						var id = "${initEditPage.tabWindowID}".split("_")[1];
						bulkEdit('${initEditPage.idsJson}','${initEditPage.tabWindowID}','fd_formlist_table_'+id);
					}
				}
			]
		});	
	});
</script>
<%
 	request.setAttribute("editPage",request.getAttribute("initEditPage"));//生成表单列ID
%>