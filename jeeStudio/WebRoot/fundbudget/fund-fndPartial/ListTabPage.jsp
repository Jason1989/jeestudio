<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%
 	request.setAttribute("listTabPageID",RandomGUID.geneGuid());//生成列表页多标签ID
%>
<div  id="${listTabPageID}" >
	<div   title="零星报销录入" iconCls="icon-menu" >
		<jsp:include page="list-page.jsp"></jsp:include>
	</div>
    <div  title="零星报销-审核通过" iconCls="icon-menu" href="/compplatform/formengine/listPageAction.action?formId=402886d433496dbe0133499fbe4a0006" style="padding:1px;">
    </div>
</div>
<script>
	$('#${listTabPageID}').tabs({
		fit:true
	});
</script>