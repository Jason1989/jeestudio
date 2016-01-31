<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%
 	request.setAttribute("listTabPageID",RandomGUID.geneGuid());//生成列表页多标签ID
%>
 
<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<div  id="${listTabPageID}" >
	<div   title="合同资金录入" iconCls="icon-menu" >
		<jsp:include page="list-page.jsp"></jsp:include>
	</div>
    <div  title="审核通过合同" iconCls="icon-menu" href="/compplatform/formengine/listPageAction.action?formId=402886d4335465630133547e05be0003" style="padding:1px;">
    </div>
</div>
<script>
	$('#${listTabPageID}').tabs({
		fit:true
	});
</script>