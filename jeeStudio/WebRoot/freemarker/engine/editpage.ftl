<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.compplatform.formengine.util.PropertiesUtil"%>
<%
 	String basePathPreviewEditPage = PropertiesUtil.findSystemPath(request);
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
	
	<link rel="stylesheet" type="text/css" href="<%=basePathPreviewEditPage%>jquery-easyui-1.1.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePathPreviewEditPage%>jquery-easyui-1.1.2/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=basePathPreviewEditPage%>jquery-easyui-1.1.2/themes/particular.css"  />
	
	<link rel="stylesheet" type="text/css" href="<%=basePathPreviewEditPage%>css/image.css">
	<link rel="stylesheet" type="text/css" href="<%=basePathPreviewEditPage%>css/style.css">
	
	<script type="text/javascript" src="<%=basePathPreviewEditPage%>jquery-easyui-1.1.2/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=basePathPreviewEditPage%>jquery-easyui-1.1.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePathPreviewEditPage%>jquery-easyui-1.1.2/easyui-lang-zh_CN.js"></script>
	
	<script type="text/javascript" src="<%=basePathPreviewEditPage%>common/components/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePathPreviewEditPage%>common/js/common-util/engine-common-util.js"></script>
	<script type="text/javascript" src="<%=basePathPreviewEditPage%>common/js/version-easyui/engine-util-easyui.js"></script>
	<script type="text/javascript" src="<%=basePathPreviewEditPage%>common/js/common-util/code-engine-constant.js"></script>
	<script type="text/javascript" src="<%=basePathPreviewEditPage%>common/js/common-util/jquery_form.js"></script>
	<script type="text/javascript" src="<%=basePathPreviewEditPage%>js/json2.js"></script>
 </head>
  <body>
  	<div id="dynamicEditPage_${listPage.id}"   title="&nbsp;${editPage.title}" class="easyui-window" style="padding:5px;background: #fafafa;width: 820px;height: 480px;"  >
		<@c:choose>
			<@c:when test="${editPage.isUseTab}">
				<@c:import url="loadTab-edit.jsp"></c:import>
			</c:when>
			<@c:otherwise>
				<jsp:include page="preview-edit-load.jsp"></jsp:include>
			</c:otherwise>
		</c:choose>
	</div>
  </body>
</html>
