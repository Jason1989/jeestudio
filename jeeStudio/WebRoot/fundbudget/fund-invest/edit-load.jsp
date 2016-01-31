<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%
 	request.setAttribute("columnID",RandomGUID.geneGuid());//生成表单列ID
 	request.setAttribute("lineCount","0");//生成换行计数
 	request.setAttribute("otherSelectValue","0"); // 下拉选中的值 数据字典查出的值  0 没有  1 有
%>
<div class="easyui-layout" fit="true">
   <div id="editColumn_${listPageRander}" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;height: 365px;">
			<form  method="post" id="editform_${editPage.id}" action="com_dynamicSave.action" >
				<c:import url="/common/jsp/easyui/edit-form.jsp"></c:import>
				<!-- 操作类型 -->
				<input type="hidden" name="opertorType" value="${opertorType}" />
				<input type="hidden" name="formId" value="${editPage.id}" />
			</form>
			<s:iterator value="#request.editPage.editColumn" status="stauts" id="editColumnList" >
			   	<c:choose>
			   		<c:when test="${type==9}"><!-- 上传组件的窗体格式表单  -->
				 			<jsp:include page="/common/components/upload-file/upload-file.jsp"></jsp:include>
					</c:when>
			   	</c:choose>
			</s:iterator>
	</div>
	<div region="south" border="false" style="text-align:center;height:55px;padding: 13px;">
		 <a   icon='icon-temp'   class="easyui-linkbutton"     href="javascript:dynamicSaveData('editform_${editPage.id}','fd_formlist_table_${listPageRander}','editPageWindow_${listPageRander}','queryPage_${listPage.id}','transave');" >暂存</a>		
		 <a   icon='icon-ok'     class="easyui-linkbutton"     href="javascript:changeFundYearState('INVEST_STATE','1');dynamicSaveData('editform_${editPage.id}','fd_formlist_table_${listPageRander}','editPageWindow_${listPageRander}','queryPage_${listPage.id}','transave');" >发布</a>		
		 <a   icon='icon-undo'   class="easyui-linkbutton"     href="javascript:formReset('editform_${editPage.id}');"  >重置</a>
		 <a   icon="icon-cancel" class="easyui-linkbutton"     href="javascript:closeWindow('${editPage.tabWindowID}');"  >关闭</a>	
	</div>
</div>


