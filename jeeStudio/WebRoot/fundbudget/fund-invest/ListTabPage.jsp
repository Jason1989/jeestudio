<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<div  id="fund-invest" >
	<div   title="${listPage.title}" iconCls="icon-menu" >
		<jsp:include page="list-page.jsp"></jsp:include>
	</div>
    <div  title="投资计划查询" iconCls="icon-menu" href="/compplatform/formengine/listPageAction.action?formId=402886d43343bc82013343c57b8a0003&INVEST_STATE=1" style="padding:1px;">
    </div>
</div>
<script>
	$('#fund-invest').tabs({
		fit:true
	});
</script>
