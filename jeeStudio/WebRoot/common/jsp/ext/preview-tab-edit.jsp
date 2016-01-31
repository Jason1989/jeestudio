<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%
 	request.setAttribute("columnID",RandomGUID.geneGuid());//生成表单列ID
 	request.setAttribute("lineCount","0");//生成换行计数
%>
 <div class="easyui-layout" fit="true">
	<div id="editColumnPanel_${columnID}" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
		<jsp:include page="edit-load.jsp"></jsp:include>
	</div>
	<div region="south" border="false" style="text-align:center;height:30px;line-height:30px;">
		 <a id="button_ok_${columnID}" class="easyui-linkbutton" icon="icon-ok"  href="javascript:dynamicSaveData('editform_${listPage.editPage.id}','fd_formlist_table_${listPageRander}','dynamicEditPage_${listPage.id}','queryPage_${listPage.id}');"  >保存</a>
		 <a id="button_undo_${columnID}" class="easyui-linkbutton" icon='icon-undo'    href="javascript:formReset('editform_${listPage.editPage.id}');"  >重置</a>
		 <a id="button_cancel_${columnID}" class="easyui-linkbutton" icon="icon-cancel"  href="javascript:closeWindow('dynamicEditPage_${listPage.id}');"  >关闭</a>	
	</div>
</div>