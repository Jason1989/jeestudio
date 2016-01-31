<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%
 	request.setAttribute("listTabPageID",RandomGUID.geneGuid());//生成列表页多标签ID
%>
 
<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<div  id="${listTabPageID}" >
	<div   title="月度资金-待审核" iconCls="icon-menu" cache=false href="<%=request.getContextPath()%>/formengine/listPageAction.action?formId=402886b8335866ad013358753db6000f&workflow_fiter=daibanxiang&isAbleWorkFlow=1&customPath=/fundbudget/month-fund/workflow-listpage.jsp"  >
	
	</div>
    <div  title="月度资金-暂存" iconCls="icon-menu" cache=false  href="<%=request.getContextPath()%>/formengine/listPageAction.action?formId=402886b8335866ad01335872e724000c&INV_STATE=0&customPath=/fundbudget/month-fund/list-page.jsp" style="padding:1px;">
    </div>
    <div  title="月度资金-已归档"   iconCls="icon-menu" cache=false href="<%=request.getContextPath()%>/formengine/listPageAction.action?formId=402886b8335866ad01335872e724000c&INV_STATE=1&customPath=/fundbudget/month-fund/list-page.jsp" style="padding:1px;">
    </div>
    <div  title="月度资金-变更暂存" iconCls="icon-menu" cache=false href="<%=request.getContextPath()%>/formengine/listPageAction.action?formId=402886b8335866ad01335872e724000c&INV_STATE=4&customPath=/fundbudget/month-fund/list-page.jsp" style="padding:1px;">
    </div>
    <div  title="月度资金-已变更"   iconCls="icon-menu" cache=false href="<%=request.getContextPath()%>/formengine/listPageAction.action?formId=402886b8335866ad01335872e724000c&INV_STATE=5&customPath=/fundbudget/month-fund/list-page.jsp" style="padding:1px;">
    </div>
    <div  title="月度资金-全部"   iconCls="icon-menu" cache=false href="<%=request.getContextPath()%>/formengine/listPageAction.action?formId=402886b8335866ad0133588d41b90012&customPath=/fundbudget/month-fund/list-page.jsp" style="padding:1px;">
    </div>
</div>
<script>
	$('#${listTabPageID}').tabs({
		fit:true
	});
</script>