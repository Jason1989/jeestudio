<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%
 	request.setAttribute("columnID",RandomGUID.geneGuid());//生成表单列ID
 	request.setAttribute("lineCount","0");//生成换行计数
%>



<table width="673px" >
	  <tr>
	    <td style="border:1px solid #ccc;" >
	 		<jsp:include page="view-page.jsp"></jsp:include>
	    </td>
	  </tr>
	  <tr>
	     <td align="center" >
	    	<a id="button_cancel_${columnID}" class="easyui-linkbutton" icon="icon-cancel"  href="javascript:closeWindow('dynamicViewPage_${viewPage.id}');"  >关闭</a>	
		</td>
	  </tr>
</table>


<script>
		$(function(){

			$('#button_cancel_${columnID}').linkbutton({text:'关闭'});
		});
</script>