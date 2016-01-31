<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%
 	request.setAttribute("columnID",RandomGUID.geneGuid());//生成表单列ID
 	request.setAttribute("lineCount","0");//生成换行计数
%>
<div class="easyui-layout" fit="true">
	<div id="viewPageData_${listPage.id}" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
		<jsp:include page="view-page.jsp"></jsp:include>
	</div>
	<div region="south" border="false" style="text-align:center;height:30px;line-height:30px;">
		<a  class="easyui-linkbutton" icon="icon-cancel"  href="javascript:$.messager.alert('提示！','预览区禁用关闭','info');"  >关闭</a>	
	<!-- closeWindow('dynamicViewPage_${viewPage.id}')  -->
	</div>
</div>
