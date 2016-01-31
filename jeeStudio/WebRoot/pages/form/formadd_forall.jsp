<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
    <title>添加</title>    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script language="javascript" type="text/javascript">
	    $(function(){
		  //createListPageForm('${dataObjectId}');
			if('${pageType}'=='listPage'){
				createListPageForm('${dataObjectId}','${editPageId}','${viewPageId}');
			}else if('${pageType}'=='viewPage'){
				createViewPageForm('${dataObjectId}');
			}else if('${pageType}'=='editPage'){
				createEditPageForm('${dataObjectId}');
			}
		});		
	</script>
  </head>  
  <body>
  <div style="display: none"  >
	<form id="fm_formadd_form" method="post" action="form/form!add.action">
	<div class="pop_col_col">
		<div class="pop_col_conc">
			<dl>
				<dd>
					表单名称：
				</dd>
				<dt style="width: 220px;">
					<input name="form.formName" class="easyui-validatebox"
						required="true" style="width: 180px;" value="${dataObjectName}"  />
					&nbsp;&nbsp;
					<font size="2" color="red">*</font>
				</dt>
			</dl>
		</div>
		<div class="pop_col_cons">
			<dl>
				<dd>
					描述：
				</dd>
				<dt>
					<textarea name="form.description" cols="39" rows="4"></textarea>
				</dt>
			</dl>
		</div>
		<div class="pop_col_conc">
			<dl>
				<dd>
					序号：
				</dd>
				<dt>
					<input name="form.sortIndex" class="easyui-validatebox" size="23"/>
				</dt>
			</dl>
		</div>
		<div class="pop_col_cons">
			<dl>
				<dd>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</dd>
				<dt>
					<textarea name="formSettings" cols="50" rows="6"
						   required="true" readonly></textarea>
					&nbsp;&nbsp;
				</dt>
			</dl>
		</div>
		<div class="pop_col_cons">
			<div align="center"
				style="width: 100%; height: 100%; padding-top: 20px;">
				<a href="javascript:void(0);" onclick="createListPageForm('${dataObjectId}');"  class="easyui-linkbutton" icon="icon-save" >保存</a>
				<a href="javascript:;" class="easyui-linkbutton"
					icon="icon-cancel"
					onclick="parent.$('#fm_form_add').window('close');">关闭</a>
					
				<!-- 数据对象json和数据列json集合 -->
				<input type="hidden" id="jsonForDataObj"  value='${jsonForDataObj}'   >
				<input type="hidden" id="jsonforDataCloumn"  value='${jsonforDataCloumn}'  >
				<!-- 列表页表单 需要设置编辑页，查看页的关联 -->
				<c:choose>
					<c:when test="${pageType eq 'viewPage' }"> 
						<input type="hidden"  name="editPageId"  value='${editPageId}'  >
					</c:when>
				</c:choose>
			</div>
		</div>
   </form>
   </div>
  </body>
</html>