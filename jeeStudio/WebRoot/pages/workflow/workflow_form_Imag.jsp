<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>工作流表单配置</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<script>
	$(function(){
		$.ajax({
       		url: "workflow/WorkFlowFrame_toImage.action?taskFormID=<%=request.getParameter("taskFormID")%>&random="+parseInt(10*Math.random()),
		    type: "post",
		    async: true,
		    success: function(data){
		    	if('success'==data){
		    	 	setTimeout("showImg()",3000); 
		    	}
		    	//parent.$('#wf_formconfig_workflowlist').datagrid('reload');
			}
		 });
	});
	function showImg(){
		document.getElementById("workflow_imag").innerHTML ="<img src='pages/workflow/img/flow.jpg' height='300' width='650' />";
	}
    </script>
  </head>  
  <body>
 	 <div  id="workflow_imag" style="height: 300px;" >
 	 </div>
 
 	 	<c:choose>
			<c:when test="${param.isShowButton eq '1' }">
				<div region="south" style="height: 52px;" border="false" align="center">
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="$('#wf_formconfig_Imag').window('close');">关闭</a>				  
				</div>
			</c:when>
		</c:choose>
  </body>
</html>
