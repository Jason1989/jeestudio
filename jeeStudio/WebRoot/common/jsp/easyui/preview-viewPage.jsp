<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.compplatform.formengine.util.PropertiesUtil"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
 	String basePathPreviewViewPage = PropertiesUtil.findSystemPath(request);
 	System.out.println(basePathPreviewViewPage);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="<%=basePathPreviewViewPage%>jquery-easyui-1.1.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePathPreviewViewPage%>jquery-easyui-1.1.2/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=basePathPreviewViewPage%>jquery-easyui-1.1.2/themes/particular.css"  />
	
	<link rel="stylesheet" type="text/css" href="<%=basePathPreviewViewPage%>css/image.css">
	<link rel="stylesheet" type="text/css" href="<%=basePathPreviewViewPage%>css/style.css">
	
	<script type="text/javascript" src="<%=basePathPreviewViewPage%>jquery-easyui-1.1.2/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=basePathPreviewViewPage%>jquery-easyui-1.1.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePathPreviewViewPage%>jquery-easyui-1.1.2/easyui-lang-zh_CN.js"></script>
	
	<!--  
			<script type="text/javascript" src="/js/XmlUtils.js"></script>
	-->
	<script type="text/javascript" src="<%=basePathPreviewViewPage%>common/components/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePathPreviewViewPage%>common/js/common-util/engine-common-util.js"></script>
	<script type="text/javascript" src="<%=basePathPreviewViewPage%>common/js/version-easyui/engine-util-easyui.js"></script>
	<script type="text/javascript" src="<%=basePathPreviewViewPage%>common/js/common-util/code-engine-constant.js"></script>
	<script type="text/javascript" src="<%=basePathPreviewViewPage%>common/js/common-util/jquery_form.js"></script>
	<script type="text/javascript" src="<%=basePathPreviewViewPage%>js/json2.js"></script>
 </head>
  <body>
  
	<div id="dynamicViewPage_${viewPage.id}"    title="&nbsp;${viewPage.title}" class="easyui-window" style="padding:5px;background: #fafafa;width: 815px;height: 450px;" closable=false >
		<c:choose>
			<c:when test="${viewPage.isUseTab}">
				<c:import url="loadTab-view.jsp"></c:import>
			</c:when>
			<c:otherwise>
				<jsp:include page="preview-tab-view.jsp"></jsp:include>
			</c:otherwise>
		</c:choose>
	</div>
	</body>
</html>
