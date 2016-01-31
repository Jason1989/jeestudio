<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
 	request.setAttribute("columnID",RandomGUID.geneGuid());//生成表单列ID
 	request.setAttribute("lineCount","0");//生成换行计数
%>
<div id="list_tab_edit${columnID}">
<table>
	<tr>
		<td>
			<jsp:include page="edit-load.jsp"></jsp:include>
		</td> 
	</tr>
	<tr>
	    <td align="center" >
			<table>
					<tr>
						<td>
						   <a id="button_ok_${columnID}" class="easyui-linkbutton" icon="icon-ok"  href="javascript:dynamicSaveData('editform_${listPage.editPage.id}','fd_formlist_table_${listPageRander}','dynamicEditPage_${listPage.id}','queryPage_${listPage.id}');"  >保存</a>
						</td>
						<td>
						   <a id="button_undo_${columnID}" class="easyui-linkbutton" icon='icon-undo'    href="javascript:formReset('editform_${listPage.editPage.id}');"  >重置</a>
						</td>
						<td> 
						    <a id="button_cancel_${columnID}" class="easyui-linkbutton" icon="icon-cancel"  href="javascript:closeWindow('dynamicEditPage_${listPage.id}');"  >关闭</a>	
						</td> 
					</tr>
			</table>
		 </td> 
	</tr>
</table>
		
		
		 	 
  </div>
<script>
		$(function(){
			$('#list_tab_edit${columnID}').panel({
				  title: '&nbsp;',
				  fit:true,
				  width:700,
				  height:400
			});
		
			$('#button_ok_${columnID}').linkbutton({text:'保存'});
			$('#button_undo_${columnID}').linkbutton({text:'重置'});
			$('#button_cancel_${columnID}').linkbutton({text:'关闭'});
		});
</script>