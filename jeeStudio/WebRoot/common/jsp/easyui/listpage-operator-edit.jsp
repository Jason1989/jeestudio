<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@page import="com.zxt.compplatform.formengine.constant.Constant"%>
<%@page import="com.zxt.compplatform.formengine.util.PropertiesUtil"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!-- 判断是否加载工作流信息 -->
<%
	String basePathListPageOperatorEdit = PropertiesUtil.findSystemPath(request);
	if(request.getAttribute("isAbleWorkFlow")!=null&&Constant.WORKFLOW_ENABLE.equals(request.getAttribute("isAbleWorkFlow").toString())){
	
%>	   
	   var APP_ID=rowData.APP_ID+'';
	   var mid=rowData.eng_envmid+'';//mid
	   var precursorId=rowData.eng_envstatus+'';//前驱状态ID
	   if(rowData.ENV_DATASTATE=='草稿暂存'){
	   		opertorString='<img  style="cursor:hand;vertical-align:middle;" title="编辑" sytle="cursor:pointer;" src="<%=basePathListPageOperatorEdit%>images/ioc-editor.gif" onclick=loadEditPage_easyui("${listPage.id}","${isAbleWorkFlow}","${listPage.editPage.id}","'+loadEditUrl+'","1","${idkey}","'+editWindowID+'","${listPage.editPage.isUseTab}","editPageWindow_${listPageRander}","&nbsp;${editPage.editTitle}","editPage_${listPageRander}","${listPageParamerUrl}","${editPage.selfDefineHeight}","${editPage.selfDefineWidth}") />&nbsp;&nbsp;&nbsp;&nbsp;';
	   }else{
	   	    opertorString='<img  style="cursor:hand;vertical-align:middle;" title="编辑" sytle="cursor:pointer;" src="<%=basePathListPageOperatorEdit%>images/ioc-editor.gif" onclick=activity_operator("'+APP_ID+'","'+mid+'","'+precursorId+'","'+gridID+'","${editPage.editTitle}","${editPage.id}","${editPage.selfDefineHeight}","${editPage.selfDefineWidth}") />&nbsp;&nbsp;&nbsp;&nbsp;';
	   }
<%		
	}else{
%>	
			opertorString='<img  style="cursor:hand;vertical-align:middle;" title="编辑" sytle="cursor:pointer;" src="<%=basePathListPageOperatorEdit%>images/ioc-editor.gif" onclick=loadEditPage_easyui("${listPage.id}","${isAbleWorkFlow}","${listPage.editPage.id}","'+loadEditUrl+'","1","${idkey}","'+editWindowID+'","${listPage.editPage.isUseTab}","editPageWindow_${listPageRander}","&nbsp;${editPage.editTitle}","editPage_${listPageRander}","${listPageParamerUrl}","${editPage.selfDefineHeight}","${editPage.selfDefineWidth}") />&nbsp;&nbsp;&nbsp;&nbsp;';							
<%
	}	
%>