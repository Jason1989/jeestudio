<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'organization-replication.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script>
	$(function(){
	     $('#orgroleclassifytree').tree({
					checkbox: true,	
					cascadeCheck:false,
					fit:true,							
					url: 'organization/organization!getAllOrganizations.action?isAdmin=${isAdmin}&dcis='+new Date().getTime()
					
		});
		
	});
	function orgclassifychange(){
		var nodes = $('#orgroleclassifytree').tree('getChecked');
		if(nodes!=""){
			var idsArr = [];
			var textArr = [];
				for(var i=0;i<nodes.length;i++){
					idsArr.push(nodes[i].id);
					textArr.push(nodes[i].text);
				}
			var ids = idsArr.join(',');
			var texts = textArr.join(',');
			var url='organization/organization!changeClassify.action?ids='+ids;
			$.ajax({
				type: "post",
			    url: url,
			    success: function(data,textstatus) {
					if(data=="ok"){
						$('#orgrole').tree("reload");
						$.messager.alert('提示', '修改成功!', 'info');
					}
				}
			});
		
		}else{
			 $.messager.alert('提示', '请选择数据!', 'warning');
		}
	}
	</script>
	<script type="text/javascript">
		setTimeout(function(){
			var node = $('#orgroleclassifytree').tree('getRoot');
			$('#orgroleclassifytree').tree('collapseAll',node.target);
			$('#orgroleclassifytree').tree('expand',node.target);		
		},100);
		
	</script>
  </head>
  
  <body>
  	<div class="easyui-layout" collapsible="true" fit="true" border="false">
			<div region="center"   border="true" style="width:280px;padding:5px;b">
				     <ul id="orgroleclassifytree"></ul>
			</div>
			<div region="south" style="height:50px; padding-top: 10px;text-align: center;">
				<a href="javascript:orgclassifychange();"  class="easyui-linkbutton" icon="icon-save">保存</a>
	 		</div>
     </div>
  </body>
</html>
