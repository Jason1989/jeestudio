<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="com.zxt.compplatform.formengine.entity.view.EditPage"%>
<%@page import="com.zxt.compplatform.formengine.constant.Constant"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
 	request.setAttribute("columnID",RandomGUID.geneGuid());//生成表单列ID
 	request.setAttribute("lineCount","0");//生成换行计数
 	request.setAttribute("otherSelectValue","0"); // 下拉选中的值 数据字典查出的值  0 没有  1 有

%>
<div class="easyui-layout" fit="true">
	<div id="editColumn_${lprid}" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;height: 365px;">
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
	<div region="south" border="false" style="text-align:center;height:45px;padding-top: 13px;">
	<s:iterator value="#request.editPage.button" status="stauts" id="editButton" >
			<c:choose>
				<c:when test="${'保存' eq buttonName && 'true' eq visible}">
					<a  class="easyui-linkbutton" icon="icon-ok"  href="javascript:dynamicSaveData('editform_${editPage.id}','fd_formlist_table_${listPageRander}','dynamicEditPage_${listPage.id}','queryPage_${listPage.id}');"  >提交</a>
				</c:when>
				<c:when test="${'关闭' eq buttonName && 'true' eq visible}">
					 <a  class="easyui-linkbutton" icon='icon-undo'    href="javascript:formReset('editform_${editPage.id}');"  >取消</a>
				</c:when>
    	 		<c:otherwise>
    	 		</c:otherwise>
			</c:choose>
		</s:iterator>
			 <a  class="easyui-linkbutton" icon="icon-cancel"  href="javascript:closeWindow('dynamicEditPage_${listPage.id}');"  >关闭</a>	
	</div>	
</div>