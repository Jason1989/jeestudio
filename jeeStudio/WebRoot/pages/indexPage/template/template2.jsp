<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s"  uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>	

<c:if test="${isconfig eq 'true'}">	
<!-- 全局设置 -->
<c:set var="title" value="未配置"></c:set>
<c:set var="url" value="pages/indexPage/model/default.jsp"></c:set>
<c:set var="clientWidth" value="800"></c:set>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>页面配置</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		
		<!-- jquery 样式 -->
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/easyui.blue.css"/>
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/particular.css"/>
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/popup.css"/>
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css" href="css/indexPage.css">
	
	<!-- jquery js-->
	<script type="text/javascript" src="jquery-easyui-1.1.2/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.1.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.1.2/jquery.easyui.extends.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.1.2/easyui-lang-zh_CN.js"></script>
	<!-- xml js -->
	<script type="text/javascript" src="js/form.common.js"></script>
	<script type="text/javascript" src="js/XmlUtils.js"></script>
	<script type="text/javascript" src="js/json2.js"></script>
	
	<!-- 快速表单js -->
	<script type="text/javascript" src="common/js/design/platform-conf.js"></script>
	<script type="text/javascript" src="common/js/design/platform-conf-view.js"></script>
	<script type="text/javascript" src="common/js/design/platform-conf-edit.js"></script>
	<!-- common-js -->
	<script type="text/javascript" src="common/js/common-util/jquery_form.js"></script>
	<script type="text/javascript" src="common/js/common-util/ajax_security.js"></script>
	<script type="text/javascript" src="common/js/common-util/service-frame.js"></script>
	<script type="text/javascript" src="common/js/common-util/code-engine-constant.js"></script>
	<script type="text/javascript" src="common/js/common-util/engine-common-util.js"></script>
	<script type="text/javascript" src="common/js/common-util/service-frame.js"></script>
	<script type="text/javascript" src="js/page-ext.js"></script>
	<!-- easyui version -->
	<script type="text/javascript" src="common/js/version-easyui/engine-util-easyui.js"></script>
	<script type="text/javascript" src="common/js/version-easyui/workflow-util.js"></script>
	</head>
	<body> 
<!-- 配置模式 -->
<script>
$(function(){
   		$(".CM_10_01").panel({
			tools:[
				{iconCls:"icon-add",
				 handler:function(event){
				    parent.conf("node1");
				 }},
				 {iconCls:"icon-undo",
				 handler:function(event){
				    parent.undo("node1");
				 }}
			]
		});
		$(".CM_10_02").panel({
			tools:[
				{iconCls:"icon-add",
				 handler:function(event){
				  parent.conf("node2");
				 }},
				 {iconCls:"icon-undo",
				 handler:function(event){
				    parent.undo("node2");
				 }}
			]
		});
		$(".CM_10_03").panel({
			tools:[
				{iconCls:"icon-add",
				 handler:function(event){
				  parent.conf("node3");
				 }},
				 {iconCls:"icon-undo",
				 handler:function(event){
				    parent.undo("node3");
				 }}
			]
		});
		$(".CM_20_01").panel({
			tools:[
				{iconCls:"icon-add",
				 handler:function(event){
				    parent.conf("node4");
				 }},
				 {iconCls:"icon-undo",
				 handler:function(event){
				    parent.undo("node4");
				 }}
			]
		});
		$(".CM_20_02").panel({
			tools:[
				{iconCls:"icon-add",
				 handler:function(event){
				    parent.conf("node5");
				 }},
				 {iconCls:"icon-undo",
				 handler:function(event){
				    parent.undo("node5");
				 }}
			]
		});
		$(".CM_20_03").panel({
			tools:[
				{iconCls:"icon-add",
				 handler:function(event){
				    parent.conf("node6");
				 }},
				 {iconCls:"icon-undo",
				 handler:function(event){
				    parent.undo("node6");
				 }}
			]
		});
		$(".CM_30_01").panel({
			tools:[
				{iconCls:"icon-add",
				 handler:function(event){
				    parent.conf("node7");
				 }},
				 {iconCls:"icon-undo",
				 handler:function(event){
				    parent.undo("node7");
				 }}
			]
		});
		$(".CM_30_02").panel({
			tools:[
				{iconCls:"icon-add",
				 handler:function(event){
				   parent.conf("node8");
				 }},
				 {iconCls:"icon-undo",
				 handler:function(event){
				    parent.undo("node8");
				 }}
			]
		});
   });
</script>
<div class="easyui-panel" fit="true" border="false" style="padding-right:5px;">
	<div class="easyui-layout" style="height: 800px;">
		<div region="north" border="false" style="height:250px;">
			<div class="easyui-layout" fit="true" border="false"> 
					<div region="west" border="false" style="width:${clientWidth*0.3}px;" class="index_model_padding">
						<c:choose>
							<c:when test="${not empty divmap['node1'].name}">
								<div class="easyui-panel CM_10_01" border="true" fit="true" href="${divmap['node1'].url}" id="node1"  title="${divmap['node1'].name}"></div>
							</c:when>
							<c:otherwise>
								<div class="easyui-panel CM_10_01" border="true" fit="true" href="${url}" id="node1"  title="${title}"></div>
							</c:otherwise>
						</c:choose>
					</div>
			    <!-- center必须显示  -->
				<div region="center" border="false"  class="index_model_padding">
					<c:choose>
						<c:when test="${not empty divmap['node2'].name}">
							<div class="easyui-panel CM_10_02" border="true" fit="true" href="${divmap['node2'].url}" id="node2"  title="${divmap['node2'].name}"></div>
						</c:when>
						<c:otherwise>
							<div class="easyui-panel CM_10_02" border="true" fit="true" href="${url}" id="node2"  title="${title}"></div>
						</c:otherwise>
					</c:choose>	
				</div>
			    <div region="east" border="false" style="width: ${clientWidth*0.3}px;" class="index_model_padding">
			    	<c:choose>
						<c:when test="${not empty divmap['node3'].name}">
							<div class="easyui-panel CM_10_03" border="true" fit="true" href="${divmap['node3'].url}" id="node3"  title="${divmap['node3'].name}"></div>
						</c:when>
						<c:otherwise>
							<div class="easyui-panel CM_10_03" border="true" fit="true" href="${url}" id="node3"  title="${title}"></div>
						</c:otherwise>
					</c:choose>
			    </div>
			</div>	    
		</div>
		<div region="center" border="false">
			<div class="easyui-layout" fit="true" border="false"> 
				<div region="west" border="false" style="width:${clientWidth*0.25}px;" class="index_model_padding">
					<c:choose>
						<c:when test="${not empty divmap['node4'].name}">
							<div class="easyui-panel CM_20_01" border="true" fit="true" href="${divmap['node4'].url}" id="node4"  title="${divmap['node4'].name}"></div>
						</c:when>
						<c:otherwise>
							<div class="easyui-panel CM_20_01" border="true" fit="true" href="${url}" id="node4"  title="${title}"></div>
						</c:otherwise>
					</c:choose>
				</div>
			    <!-- center必须显示  -->
				<div region="center" border="false"  class="index_model_padding">
					<c:choose>
						<c:when test="${not empty divmap['node5'].name}">
							<div class="easyui-panel CM_20_02" border="true" fit="true" href="${divmap['node5'].url}" id="node5"  title="${divmap['node5'].name}"></div>
						</c:when>
						<c:otherwise>
							<div class="easyui-panel CM_20_02" border="true" fit="true" href="${url}" id="node5"  title="${title}"></div>
						</c:otherwise>
					</c:choose>
				</div>
			    <div region="east" border="false" style="width: ${clientWidth*0.25}px;" class="index_model_padding">
			    	<c:choose>
						<c:when test="${not empty divmap['node6'].name}">
							<div class="easyui-panel CM_20_03" border="true" fit="true" href="${divmap['node6'].url}" id="node6"  title="${divmap['node6'].name}"></div>
						</c:when>
						<c:otherwise>
							<div class="easyui-panel CM_20_03" border="true" fit="true" href="${url}" id="node6"  title="${title}"></div>
						</c:otherwise>
					</c:choose>
			    </div>
			</div>
		</div>
		<div region="south" border="false" style="height:250px;">
			<div class="easyui-layout" fit="true" border="false">
				<div region="west" border="false" style="width:${clientWidth*0.45}px;" class="index_model_padding">
						<c:choose>
							<c:when test="${not empty divmap['node7'].name}">
								<div class="easyui-panel CM_30_01" border="true" fit="true" href="${divmap['node7'].url}" id="node7"  title="${divmap['node7'].name}"></div>
							</c:when>
							<c:otherwise>
								<div class="easyui-panel CM_30_01" border="true" fit="true" href="${url}" id="node7"  title="${title}"></div>
							</c:otherwise>
						</c:choose>
				</div>
				
				<div region="center" border="false"  class="index_model_padding">
					<c:choose>
						<c:when test="${not empty divmap['node8'].name}">
							<div class="easyui-panel CM_30_02" border="true" fit="true" href="${divmap['node8'].url}" id="node8"  title="${divmap['node8'].name}"></div>
						</c:when>
						<c:otherwise>
							<div class="easyui-panel CM_30_02" border="true" fit="true" href="${url}" id="node8"  title="${title}"></div>
						</c:otherwise>
					</c:choose>
				</div> 
			</div>	 
		</div>
	</div>
</div>
</body></html>
</c:if> 

<c:if test="${not isconfig eq 'true'}">
<c:set var="clientWidth" value="1200"></c:set>
<c:set var="clientHeight" value="650"></c:set>	
<!-- 查看模式 -->
<div class="easyui-panel" fit="true" border="false" style="padding-right:5px;">
	<div class="easyui-layout" style="height: ${clientHeight*0.8 }px;">
		<div region="north" border="false" style="height:${clientHeight*0.25}px;">
			<div class="easyui-layout" fit="true" border="false">
				<c:if test="${not empty divmap['node1'].url}">
					<div region="west" border="false" style="width:${clientWidth*0.25}px;" class="index_model_padding">
							<div class="easyui-panel" border="false" fit="true" href="${divmap['node1'].url}" title="${divmap['node1'].name}"></div>
					</div>
				</c:if>
			    <!-- center必须显示  -->
				<div region="center" border="false"  class="index_model_padding">
					<c:if test="${not empty divmap['node2'].url}">
						<div class="easyui-panel" border="false" fit="true" href="${divmap['node2'].url}" title="${divmap['node2'].name}" ></div>
					</c:if>
				</div>
				<c:if test="${not empty divmap['node3'].url}">
			   	 	<div region="east" border="false" style="width: ${clientWidth*0.25}px;" class="index_model_padding">
						<div class="easyui-panel" border="false" fit="true" href="${divmap['node3'].url}" title="${divmap['node3'].name}" ></div>
			   		 </div>
				</c:if>
			</div>    
		</div>
		<div region="center" border="false">
			<div class="easyui-layout" fit="true" border="false">
				<c:if test="${not empty divmap['node4'].url}">
					<div region="west" border="false" style="width:${clientWidth*0.25}px;" class="index_model_padding">
							<div class="easyui-panel" border="false" fit="true" href="${divmap['node4'].url}" title="${divmap['node4'].name}"></div>
					</div>
				</c:if>
			    <!-- center必须显示  -->
				<div region="center" border="false"  class="index_model_padding">
					<c:if test="${not empty divmap['node5'].url}">
						<div class="easyui-panel" border="false" fit="true" href="${divmap['node5'].url}" title="${divmap['node5'].name}" ></div>
					</c:if>
				</div>
				<c:if test="${not empty divmap['node6'].url}">
			    	<div region="east" border="false" style="width: ${clientWidth*0.25}px;" class="index_model_padding">
						<div class="easyui-panel" border="false" fit="true" href="${divmap['node6'].url}" title="${divmap['node6'].name}" ></div>
			   		 </div>
				</c:if>
			</div>
		</div>
		<div region="south" border="false" style="height:${clientHeight*0.25}px;">
			<div class="easyui-layout" fit="true" border="false">
				<!-- 设置标记字段 -->
				<c:set value="false" var="westflag"></c:set>
				<c:if test="${not empty divmap['node7'].url}">
					<c:set value="true" var="westflag"></c:set>
					<div region="west" border="false" style="width:${clientWidth*0.30}px;" class="index_model_padding">
							<div class="easyui-panel" border="false" fit="true" href="${divmap['node7'].url}" title="${divmap['node7'].name}"></div>
					</div>
				</c:if>
			   <!-- center必须显示  -->
				<div region="center" border="false"  class="index_model_padding">
					<c:if test="${not empty divmap['node8'].url}">
						<div class="easyui-panel" border="false" fit="true" href="${divmap['node8'].url}" title="${divmap['node8'].name}" ></div>
					</c:if>
				</div>
			  	<c:if test="${westflag eq false}">
					<div region="east" border="false"  class="index_model_padding" style="width:${clientWidth*0.55}px;">
					</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
</c:if>
