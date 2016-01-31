<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Object obj = request.getAttribute("oid");
String oid = "";
if(obj!=null){
	oid = obj.toString();
}
Object robj = request.getAttribute("rname");
String rname = "";
if(robj!=null){
	rname = robj.toString();
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>组织机构列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function(){
		    $('#orgl').tree({
				checkbox: false,	
				fit:true,							
				url: 'organization/organization!getAllOrganizationsBy.action?dcis='+new Date().getTime()+'&oid=<%=oid%>&isAdmin=${isAdmin}',
				onClick:function(node){
					var oid = node.id;
					if(node){
						document.getElementById("left_main").innerHTML="";
						var orgUpId = $(this).tree('getParent',node.target);
						
						var url = "";
						if(orgUpId){
						   url = 'organization/organization!show.action?isAdmin=${isAdmin}&rname=<%=rname%>&oid='+oid+'&orgname='+encodeURI(encodeURI(node.text))+'&orgupid='+orgUpId.id;
						}else{
							url = 'organization/organization!show.action?isAdmin=${isAdmin}&rname=<%=rname%>&oid='+oid+'&orgname='+encodeURI(encodeURI(node.text));
						}

						$("#left_main").panel({href :url});
						$("#left_main").panel("refresh");
					}
				}
			});
			//展开组织机构
			setTimeout(function(){
				var node = $('#orgl').tree('getRoot');
				$('#orgl').tree('collapseAll',node.target);
				$('#orgl').tree('expand',node.target);		
			},100);
		});
	</script>     
  </head>

  <body>
    <div id="organizationlist" class="easyui-panel" border="true" fit="true" title="组织机构" icon="icon-group">
    		 <div class="easyui-layout" collapsible="true" fit="true" border="false">
				<div region="west" split="true" border="false" style="width:200px;padding:5px;">
				    <div class="easyui-panel" title="组织机构树" icon="icon-organisation" fit="true" border="true" headerCls="header_cls" style="padding:5px ;">
				     <ul id="orgl"></ul>
				    </div>
				</div>
				<div region="center"  id="left_main"  href="pages/default_main.jsp" style="overflow:hidden; width: 600px;padding:5px 0 5px 0;" border="false">
				</div>
      		 </div>
   </div>
  </body>
</html>
