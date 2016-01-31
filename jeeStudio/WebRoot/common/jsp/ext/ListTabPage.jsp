<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/image.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext-3.3.1/resources/css/ext-all.css" />
 
    <link href="<%=request.getContextPath()%>/css/auto_row.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jquery-easyui-1.1.2/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jquery-easyui-1.1.2/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jquery-easyui-1.1.2/themes/particular.css"  />
	
	<!-- jquery 资源 -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery-easyui-1.1.2/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery-easyui-1.1.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/jquery-easyui-1.1.2/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/common/components/My97DatePicker3.0.1/My97DatePicker/WdatePicker.js"></script>
	
	<!-- Ext 资源 -->
	<script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/ext-3.3.1/adapter/ext/ext-base.js"></script>
    <script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/ext-3.3.1/ext-all-debug.js"></script>
    
    <!-- 公共JS文件 -->
    <script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/common/js/common-util/ext-lang-zh_CN.js"></script>
 	<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/common-util/code-engine-constant.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/common-util/jquery_form.js"></script>
	
	<!-- 引擎 ext版 工具JS -->
	<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/version-ext/code-engine-util.js"></script>
</head>
  <body>
  	<%
	 	request.setAttribute("listTabPageID",RandomGUID.geneGuid());//生成列表页多标签ID
	%>
	
  	<c:choose>
		<c:when test="${empty listPage.isUseTab}">
			 <c:set scope="request" var="isUseTab" value="false"  ></c:set>
		</c:when>
		<c:otherwise>
			 <c:set scope="request" var="isUseTab" value="${listPage.isUseTab}"  ></c:set>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${isUseTab}">
			<div  id="${listTabPageID}" >
			<div   title="${listPage.title}">
				 <jsp:include page="list-page.jsp"></jsp:include>
			</div>
			<s:iterator  value="#request.listPage.tabs" status="stauts" >
				<div  title="${title}" style="padding:1px;">
					  <c:choose>
					     <c:when test="${type eq 'editPage'}">
					     	<c:set scope="request" var="editPage" value="${page}"  ></c:set>
					     	<jsp:include page="list-tab-edit.jsp"></jsp:include>
					     </c:when>
					     <c:when test="${type eq 'listPage'}">
					     	<c:set scope="request" var="listPage" value="${page}"  ></c:set>
					    	 <jsp:include page="list-page.jsp"></jsp:include>
					     </c:when>
					     <c:when test="${type eq 'viewPage'}">
					     	<c:set scope="request" var="viewPage" value="${page}"  ></c:set>
					      	<jsp:include page="tab-viewpage.jsp"></jsp:include>
					     </c:when>
					  </c:choose>
				</div>
			</s:iterator>
		</div>
	      <script>
				$('#${listTabPageID}').tabs({
					fit:true
				});
		  </script>
	  </c:when>
	  <c:otherwise>
		  		<jsp:include page="list-page.jsp"></jsp:include>
	  </c:otherwise>
	</c:choose>
	
	<script>
		function MyClass(){
		        this.UpdateEditorFormValue = function(){
		               for ( i = 0; i < parent.frames.length; ++i )
		                       if ( parent.frames[i].FCK )
		                          parent.frames[i].FCK.UpdateLinkedField();
		        }
		}
		var MyObject = new MyClass();
	</script>
  </body>
</html>
