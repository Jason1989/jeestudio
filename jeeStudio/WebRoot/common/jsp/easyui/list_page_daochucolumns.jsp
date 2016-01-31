<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String formId = request.getParameter("formId");
String gridID = request.getParameter("gridID");
%>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'list-page-daochucolumns.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$.ajax({
			type:'post',
			url: "com_findFiedsByPageId.action",
            data:"formId=${param.formId}",
            success: function(data, textStatus) {
            	var arr_daochucol = eval("("+data+")");
            	var str_daochucol = "";
            	for(var i = 0; i<arr_daochucol.length; i++){
            		str_daochucol += "<dd><input type='checkbox' name='selectColumns' checked='checked' value='"
            		+arr_daochucol[i].column+"&#"+arr_daochucol[i].name+"'>&nbsp;&nbsp;"+arr_daochucol[i].name+"</dd>"
            	
            	}
            	$("#checkdiv_${param.formId}").append(str_daochucol);
            	$("#checkdiv_${param.formId}").css({width:"100%",padding:"2 10 2 60"});
            	$("#checkdiv_${param.formId} dd").css({"float":"left","width":"100px","text-align": "left","line-height": "15px"});
            }
		})
		$(function(){
			$("#quanxuan_daochucol_${param.formId}").click(function(){
				if($("#quanxuan_daochucol_${param.formId}").attr('checked')){
					$("input[name='selectColumns']").each(function(){
						$(this).attr("checked",true);
					});
				}else{
					$("input[name='selectColumns']").each(function(){
						$(this).attr("checked",false);
					});
				}
			});
		
		})
		function daochucol_${param.formId}_func(){
	
		    var f=0;
		    var divDoc=$("input[name='selectColumns']");
	
				for(var i=0;i<divDoc.length;i++){
					if(divDoc[i].checked==true)
					 {
					 	
					 	f=1; break;
					 }
				}
		
			
			if(f==0){
				$.messager.alert("提示", '请选择导出的列！');
				return;
			}
			var gridID=document.getElementById("gridID").value;
			var paramer =$("#"+gridID).datagrid("options").queryParams;
			document.getElementById("daochucol_${param.formId}_form").action="com_exportForListPage.action?"+$.param(paramer);
			$("#daochucol_${param.formId}_form").submit();
		}
	</script>

  </head>
  
  <body>
  	<form action="com_exportForListPage.action" id="daochucol_${param.formId}_form" method="post">
    <input type="hidden" name="gridID" id="gridID" value="<%=gridID%>"/>
    <input type="hidden" name="formId" id="formId" value="<%=formId%>"/>
    <div style="width:500px;height:239px;margin-left: 20px;margin-top: 15px;">
    	<input type="checkbox" value="quanxuan" id="quanxuan_daochucol_${param.formId}" checked="checked">&nbsp;&nbsp; 全选<br/>
    	<div id='checkdiv_${param.formId}'></div>
    </div>
    <div style="width:500px;height:50px;margin-left: 20px;text-align: center;padding-top:15px; ">
    	<a  class="easyui-linkbutton" icon="icon-ok"  href="javascript:daochucol_${param.formId}_func();"  >导出</a>
		<a  class="easyui-linkbutton" icon="icon-cancel"  href="javascript:closeWindow('initdiv_${param.formId}_daochu');">关闭</a>	
    </div>
    </form>
  </body>
</html>
