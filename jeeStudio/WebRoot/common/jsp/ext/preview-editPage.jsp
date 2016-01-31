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
	<script type="text/javascript" src="<%=request.getContextPath()%>/common/components/My97DatePicker3.0.1/My97DatePicker/WdatePicker.js"></script>
	
	<!-- Ext 资源 -->
	<script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/ext-3.3.1/adapter/ext/ext-base.js"></script>
    <script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/ext-3.3.1/ext-all-debug.js"></script>
    <script charset="UTF-8" type="text/javascript" src="<%=request.getContextPath()%>/common/js/common-util/ext-lang-zh_CN.js"></script>
 	
 	<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/common-util/code-engine-constant.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/common-util/jquery_form.js"></script>

	<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/version-ext/code-engine-util.js"></script>
	
</head>
  <body>
  	<div id="dynamicEditPage_${listPage.id}"   title="&nbsp;${editPage.title}" class="easyui-window" style="padding:5px;background: #fafafa;width: 720px;height: 480px;"  >
		<c:choose>
			<c:when test="${editPage.isUseTab}">
				<div id="previewTabEditPage" >
						<s:iterator  value="#request.editPage.tabs" status="stauts" >
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
								      	<jsp:include page="preview-viewpage-tab.jsp"></jsp:include>
								     </c:when>
								  </c:choose>
							</div>
						</s:iterator>
					</div>
					
					<script>
						$(function(){
							$('#previewTabEditPage').tabs({
								border:true,
								fit:true
							});	
						});
					</script>		
			</c:when>
			<c:otherwise>
				<div class="easyui-layout" fit="true">
					<div id="editColumn_${listPage.id}" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
						<jsp:include page="edit-load.jsp"></jsp:include>
					</div>
					<div region="south" border="false" style="text-align:center;height:30px;line-height:30px;">
						 <a  class="easyui-linkbutton" icon="icon-ok"  href="javascript:dynamicSaveData('editform_${editPage.id}','fd_formlist_table_${listPageRander}','dynamicEditPage_${listPage.id}','queryPage_${listPage.id}');"  >保存</a>
						 <a  class="easyui-linkbutton" icon='icon-undo'    href="javascript:formReset('editform_${editPage.id}');"  >重置</a>
						 <a  class="easyui-linkbutton" icon="icon-cancel"  href="javascript:closeWindow('dynamicEditPage_${listPage.id}');"  >关闭</a>	
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
  </body>
</html>
