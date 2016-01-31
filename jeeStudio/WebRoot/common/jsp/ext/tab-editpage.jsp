<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%
 	request.setAttribute("columnID",RandomGUID.geneGuid());//生成表单列ID
 	request.setAttribute("lineCount","0");//生成换行计数
%>
  
<table>
	  <tr>
	    <td>
	  		<jsp:include page="tab-edit-load.jsp"></jsp:include>
	    </td>
	  </tr>
	  <!--  
		  <tr>
		     <td align="center" >
		    	<a id="button_ok_${columnID}" class="easyui-linkbutton" icon="icon-ok"  href="javascript:bulkEdit('${editPage.idsJson}');"  >保存</a>
			 	<a id="button_undo_${columnID}" class="easyui-linkbutton" icon='icon-undo'    href="javascript:formReset('editform_${editPage.id}');"  >重置</a>
			 	<a id="button_cancel_${columnID}" class="easyui-linkbutton" icon="icon-cancel"  href="javascript:closeWindow('dynamicEditPage_${listPage.id}');"  >关闭</a>	
		     </td>
		  </tr>
	  -->
</table>
	
<script>
		$(function(){
			/**
			 	$('#button_ok_${columnID}').linkbutton({text:'保存'});
				$('#button_undo_${columnID}').linkbutton({text:'重置'});
				$('#button_cancel_${columnID}').linkbutton({text:'关闭'});
			 */
		});
</script>