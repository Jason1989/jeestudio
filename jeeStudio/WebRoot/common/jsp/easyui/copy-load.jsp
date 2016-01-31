<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="com.zxt.compplatform.formengine.entity.view.EditPage"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
 	request.setAttribute("columnID",RandomGUID.geneGuid());//生成表单列ID
 	request.setAttribute("lineCount","0");//生成换行计数
 	request.setAttribute("otherSelectValue","0"); // 下拉选中的值 数据字典查出的值  0 没有  1 有
%>
<script type="text/javascript">
	/**
  * @param form       提交的表单
  * @param datagrid   列表页grid
  * @param editWindow 编辑页窗口 
  * 保存编辑页  业务数据
  */
 function dynamicSaveDataCopy(form,gridID,editWindowID,queryFormID,opertype){
 	var batchsize=$("#batchsize").val();
 	var ieorf = navigator.appName;
 	var type = opertype;
 	type=batchsize;
 	window.location.reload();
 	if(ieorf=="Microsoft Internet Explorer"){
	 	if(MyObject){
	    	MyObject.UpdateEditorFormValue();
	    }
 	}else{}
    
    var flag=true;
    
    try{
    	flag= $('#'+form).form('validate');
    }catch(err){
    
    }
   
	var options = {
		              type:"post",
		              data:{type:type,batchsize:batchsize},
		              success:function (responseText, statusText) {
			              		closeDialog(editWindowID);//关闭编辑页
			              		refreshGrid(gridID);
		              }};
	/**
	 * 验证成功
	 */			
	if(flag){
		$('[id^='+editWindowID+']').each(function(){
			$(this).attr('disabled',true);
		});
		$("#" + form).ajaxSubmit(options);
	}
 } 

</script>
<form  method="post" id="copyform_${editPage.id}" action="com_dynamicSave.action?method=copy" >
	<c:import url="copy-form.jsp"></c:import>
	<!-- 操作类型 -->
	<input type="hidden" name="opertorType" value="${opertorType}" />
	<input type="hidden" name="formId" value="${editPage.id}" />
	</br></br></br></br></br></br></br></br>
	<p >
	批量添加数量：<input type="text" name="batchsize"  value="1" id="batchsize"/>
	</p>
<P align="center">
	<a class="easyui-linkbutton"  icon="icon-ok"   href="javascript:dynamicSaveDataCopy('copyform_${editPage.id}','fd_formlist_table_${lprid}','copyPageWindow_${lprid}','queryPage_${listpageId}','copy');" >保存</a>&nbsp;&nbsp;
</P>
</form>
<s:iterator value="#request.copyPage.editPage.editColumn" status="stauts" id="editColumnList" >
   	<c:choose>
   		<c:when test="${type==9}"><!-- 上传组件的窗体格式表单  -->
	 			<jsp:include page="/common/components/upload-file/upload-file.jsp"></jsp:include>
		</c:when>
   	</c:choose>
</s:iterator>
