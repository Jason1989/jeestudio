<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="com.zxt.compplatform.formengine.entity.view.EditPage"%>
<%@page import="com.zxt.compplatform.formengine.constant.Constant"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%@page import="com.zxt.compplatform.formengine.util.PropertiesUtil"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
 	request.setAttribute("columnID",RandomGUID.geneGuid());//生成表单列ID
 	request.setAttribute("lineCount","0");//生成换行计数
 	request.setAttribute("otherSelectValue","0"); // 下拉选中的值 数据字典查出的值  0 没有  1 有
    String basePathEditLoad = PropertiesUtil.findSystemPath(request);
	//System.out.print(basePathEditLoad);
%>
<div class="easyui-layout" fit="true">
	<div id="editColumn_${lprid}" region="center" border="false" style="padding:10px;border:1px solid #ccc;height: 365px;">
		    <form method="post" id="editform_${editPage.id}" action="<%=basePathEditLoad%>com_dynamicSave.action" >
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
	<c:choose>
		<c:when test="${atw ne '1'}">
			<div region="south" border="false" style="text-align:center;height:45px;padding-top: 13px;">
				<!-- 判断添加页是否启动工作流  并加入日志 -->
				<c:choose>
			   		<c:when test="${isAbleWorkFlow eq '1'}"><!-- 表单需要启动工作流  -->
				 				<a class="easyui-linkbutton"  icon='icon-ok'    href="javascript:dynamicSaveData('editform_${editPage.id}','fd_formlist_table_${lprid}','editPageWindow_${lprid}','queryPage_${listpageId}','transave');" >暂存</a>&nbsp;&nbsp;						
								<a class="easyui-linkbutton"  icon="icon-ok"    href="javascript:dynamicSaveData('editform_${editPage.id}','fd_formlist_table_${lprid}','editPageWindow_${lprid}','queryPage_${listpageId}','save');submit_workflow_log('editform_${editPage.id}')" >提交</a>&nbsp;&nbsp;
					</c:when>
					<c:otherwise>
								<a class="easyui-linkbutton"  icon="icon-ok"   href="javascript:dynamicSaveData('editform_${editPage.id}','fd_formlist_table_${lprid}','editPageWindow_${lprid}','queryPage_${listpageId}');" >保存</a>&nbsp;&nbsp;
					</c:otherwise>
	   			</c:choose>
				<a  class="easyui-linkbutton" icon='icon-undo'   id="editPageWindow_${lprid}_undo"     href="javascript:formReset('editform_${editPage.id}');"  >重填</a>&nbsp;&nbsp;
			 	<a  class="easyui-linkbutton" icon="icon-cancel" id="editPageWindow_${lprid}_cancel"   href="javascript:closeWindow('editPageWindow_${lprid}');"  >关闭</a>
			</div>
		</c:when>
	</c:choose>
</div>