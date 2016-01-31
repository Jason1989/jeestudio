<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jquery-easyui-1.1.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jquery-easyui-1.1.2/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jquery-easyui-1.1.2/themes/particular.css"  />
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/image.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery-easyui-1.1.2/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery-easyui-1.1.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery-easyui-1.1.2/locale/easyui-lang-zh_CN.js"></script>
	<!--  
			<script type="text/javascript" src="/js/XmlUtils.js"></script>
	-->
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/code-engine-util.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/code-engine-constant.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery_form.js"></script>
 	<script type="text/javascript" src="<%=request.getContextPath()%>/common/components/My97DatePicker3.0.1/My97DatePicker/WdatePicker.js"></script>
	
 </head>
  <body>
  
	<div id="dynamicViewPage_${viewPage.id}"    title="&nbsp;${viewPage.title}" class="easyui-window" style="padding:5px;background: #fafafa;width: 720px;height: 450px;" closable=false >
		<c:choose>
			<c:when test="${viewPage.isUseTab}">
				<div  class="easyui-tabs" style="width:695px;height:400px;">
						<div   title="${viewPage.title}">
							<jsp:include page="preview-tab-view.jsp"></jsp:include>
						</div>
						<s:iterator  value="#request.viewPage.tabs" status="stauts" >
							<div  title="${title}" style="padding:1px;">
								  <c:choose>
								     <c:when test="${type eq 'editPage'}">
								     	<c:set scope="request" var="editPage" value="${page}"  ></c:set>
								    	<jsp:include page="preview-tab-edit.jsp"></jsp:include>
								    </c:when>
								     <c:when test="${type eq 'listPage'}">
								     	<c:set scope="request" var="listPage" value="${page}"  ></c:set>
								    	 <jsp:include page="list-page.jsp"></jsp:include>
								     </c:when>
								     <c:when test="${type eq 'viewPage'}">
								        <c:set scope="request" var="viewPage" value="${page}"  ></c:set>
								      	<jsp:include page="preview-tab-view.jsp"></jsp:include>
								     </c:when>
								  </c:choose>
							</div>
						</s:iterator>
					</div>
			</c:when>
			<c:otherwise>
				<jsp:include page="preview-tab-view.jsp"></jsp:include>
			</c:otherwise>
		</c:choose>
	</div>
	</body>
</html>
