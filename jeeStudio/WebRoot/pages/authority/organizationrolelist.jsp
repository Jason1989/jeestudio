<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Object obj = request.getAttribute("oid");
String oid = "";
if(obj!=null){
	oid = obj.toString();
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>角色管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
		    $('#orgrole').tree({
				checkbox: false,
				fit:true,							
				url: 'organization/organization!getAllOrganizationsByClassify.action?dcis='+new Date().getTime()+'&oid=<%=oid%>&isAdmin=${isAdmin}',
				onClick:function(node){
					var oid = node.id;
					var otext = node.text;
					if(node){
						document.getElementById("orgrolelist").innerHTML="";
						var orgUpId = $(this).tree('getParent',node.target);
						
						var url = "";
						if(orgUpId){//有父节点
							url = 'pages/authority/rolerlist.jsp?isall=0&isAdmin=${isAdmin}&orgid='+oid+'&orgname='+encodeURI(encodeURI(otext));
						}else{
							url = 'pages/authority/rolerlist.jsp?isall=1&isAdmin=${isAdmin}&orgid='+''+'&orgname='+'';
						}

						$("#orgrolelist").panel({href :url});
						$("#orgrolelist").panel("refresh");
					}
				},
				
			});
			//展开组织机构
			
			$('#selallrole').tree({
				onClick:function(node){
					if(node){
						$("#orgrolelist").panel({href :'pages/authority/rolerlist.jsp?isall=1&isAdmin=${isAdmin}'});
						$("#orgrolelist").panel("refresh");
					}
				}
			})
			// $("#roleclassify").panel({href :'pages/authority/organizationroleclassify.jsp'});
			// $("#roleclassify").panel("refresh");
		});
	</script> 
	<script type="text/javascript">
		setTimeout(function(){
			var node = $('#orgrole').tree('getRoot');
			$('#orgrole').tree('collapseAll',node.target);
			$('#orgrole').tree('expand',node.target);		
		},100);
		
	</script>
	    
  </head>

  <body>
    
    <div id="organizationrolelist" class="easyui-panel" border="true" fit="true" title="角色管理" icon="icon-database-table">
    		 <div class="easyui-layout" collapsible="true" fit="true" border="false">
				
				<div region="west"   border="true" style="width:280px;padding:5px;b">
				    <div class="easyui-tabs"  fit="true"  icon="icon-organisation" headerCls="header_cls" >
			     		<div   title="角色管理"  >
			     		<ul class="easyui-tree" id="selallrole">
				     	<li>
				     		查询所有角色
				     	</li>
				     	</ul>
	 			 			<ul id="orgrole"></ul>
			    		</div>
			     		<div id="roleclassify"  title="角色分类维护" href='pages/authority/organizationroleclassify.jsp' cache=false >
			     		</div>
				    </div>
				    </div>
				<div region="center"  id="orgrolelist"  href="pages/default_main.jsp" style="overflow:hidden; width: 600px;padding:5px 0 5px 0;" border="false">
				</div>
      		 </div>
   </div>
  </body>
</html>
