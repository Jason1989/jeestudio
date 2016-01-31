<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.compplatform.formengine.util.PropertiesUtil"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	String basePathListTabPageView = PropertiesUtil.findSystemPath(request);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>列表页预览</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="<%=basePathListTabPageView%>css/image.css">
	<link rel="stylesheet" type="text/css" href="<%=basePathListTabPageView%>css/auto_row.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePathListTabPageView%>css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=basePathListTabPageView%>css/indexPage.css">
	<link rel="stylesheet" type="text/css" href="<%=basePathListTabPageView%>jquery-easyui-1.1.2/themes/easyui.${listpageViewThemes}.css"/>
 	
 	<script type="text/javascript" src="<%=basePathListTabPageView%>jquery-easyui-1.1.2/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=basePathListTabPageView%>jquery-easyui-1.1.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePathListTabPageView%>jquery-easyui-1.1.2/jquery.easyui.extends.js"></script>
	<script type="text/javascript" src="<%=basePathListTabPageView%>jquery-easyui-1.1.2/easyui-lang-zh_CN.js"></script>
	
	<script type="text/javascript" src="<%=basePathListTabPageView%>common/js/common-util/jquery_form.js"></script>
	<script type="text/javascript" src="<%=basePathListTabPageView%>common/components/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePathListTabPageView%>common/js/common-util/service-frame.js"></script>
	<script type="text/javascript" src="<%=basePathListTabPageView%>common/js/common-util/code-engine-constant.js"></script>
	<script type="text/javascript" src="<%=basePathListTabPageView%>common/js/common-util/engine-common-util.js"></script>
	<script type="text/javascript" src="<%=basePathListTabPageView%>common/js/common-util/upload.js"></script>

	
	<script type="text/javascript" src="<%=basePathListTabPageView%>common/js/version-easyui/engine-util-easyui.js"></script>
	<script type="text/javascript" src="<%=basePathListTabPageView%>common/js/version-easyui/workflow-util.js"></script>
	<script type="text/javascript" src="<%=basePathListTabPageView%>js/XmlUtils.js"></script>
	<script type="text/javascript" src="<%=basePathListTabPageView%>js/json2.js"></script>
	<script type="text/javascript" src="<%=basePathListTabPageView%>common/js/fund-budget/fund-budget.js"></script>

  </head>
   
  <body>
  		<div style="width: 1100px;height: 400px;" >
		 	<c:choose>
			    <c:when test="${isAbleWorkFlow  eq '1'}">
			    	<jsp:include page="workflow-listpage.jsp"></jsp:include>
			    </c:when>
			    <c:otherwise>
			        <jsp:include page="ListTabPage.jsp"></jsp:include>
			    </c:otherwise>
		    </c:choose>
	    </div>
  </body>
</html>
