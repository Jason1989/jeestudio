<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.zxt.compplatform.formengine.util.StrTools"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${param.manageName}" var="manageName" scope="request" /> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	if(request.getAttribute("manageName")!=null){
		request.setAttribute("manageName",
				StrTools.charsetFormat(request.getAttribute("manageName").toString(),
						"ISO8859-1", "UTF-8")	
		);
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>

<script type="text/javascript">
$(function(){
  $('#com_tree').tree({
  	
		checkbox: false,	
		fit:true,							
		url: 'formengine/listPageActionTree.action?dicId=${param.dicId}&parentId=${param.parentId}&dcis='+new Date().getTime(),
		onLoadSuccess:function(node, data){
			var node = $('#com_tree').tree('getRoot');
			if(node){
				$('#com_tree').tree('collapseAll',node.target);
				$('#com_tree').tree('expand',node.target);		
			}
		},
		onClick:function(node){
			
			if(node){
				var oid = node.id;
				var treeNodeUrl= '${param.formId}';
				var listpage_parmer='${param.listpage_parmer}';
				
				if(treeNodeUrl.indexOf(".")>0){
					if(treeNodeUrl.indexOf("?")>0){
						$('#left_main').panel('refresh',treeNodeUrl+ "&date=" + new Date().getTime());
					}else{
						$('#left_main').panel('refresh',treeNodeUrl+ "?date=" + new Date().getTime());
					}
				}else{
						var url=LISTPAGE_LOAD+treeNodeUrl+ "&date=" + new Date().getTime()+'&menuId='+node.id
							+'&isAbleWorkFlow='+node.attributes.isAbleWorkFlow+'&workflow_fiter='+node.attributes.workflow_fiter
							+'&'+listpage_parmer+'='+oid+'&isShowTreeMenu=${param.isShowTreeMenu}&treelistpageID=${param.treelistpageID}&treeID=com_tree&manageName=${manageName}';
						    url=encodeURI(url);
						$('#left_main').panel('refresh',url);
				}
			}
		}
	});
	
	//展开组织机构
	setTimeout(function(){
		
		var node = $('#com_tree').tree('getRoot');
		/**
			if(node){
				$('#com_tree').tree('collapseAll',node.target);
				$('#com_tree').tree('expand',node.target);		
			}
		**/
		if(node){
				var oid = node.id;
				var treeNodeUrl= '${param.formId}';
				var listpage_parmer='${param.listpage_parmer}';
				
				if(treeNodeUrl.indexOf(".")>0){
					if(treeNodeUrl.indexOf("?")>0){
						$('#left_main').panel('refresh',treeNodeUrl+ "&date=" + new Date().getTime());
					}else{
						$('#left_main').panel('refresh',treeNodeUrl+ "?date=" + new Date().getTime());
					}
				}else{
						var url=LISTPAGE_LOAD+treeNodeUrl+ "&date=" + new Date().getTime()+'&menuId='+node.id
							+'&isAbleWorkFlow='+node.attributes.isAbleWorkFlow+'&workflow_fiter='+node.attributes.workflow_fiter
							+'&'+listpage_parmer+'='+oid+'&isShowTreeMenu=${param.isShowTreeMenu}&treelistpageID=${param.treelistpageID}&treeID=com_tree&manageName=${manageName}';
							url=encodeURI(url);
						      
					$('#left_main').panel('refresh',url);
				}
		
		}else{//没有树节点的时候
					var url=LISTPAGE_LOAD+treeNodeUrl+ "&date=" + new Date().getTime()+'&menuId=-1'
							+'&${param.listpage_parmer}=-1'+'&isShowTreeMenu=${param.isShowTreeMenu}&treelistpageID=${param.treelistpageID}&treeID=com_tree&manageName=${manageName}';
						    url=encodeURI(url);
					$('#left_main').panel('refresh',url);
		}	
		
	},300);
});
</script>     
  </head>

  <body>
    <div id="organizationlist" class="easyui-panel" border="true" fit="true" title="" icon="icon-group">
    		 <div class="easyui-layout" collapsible="true" fit="true" border="false">
				<div region="west" split="true" border="false" style="width:200px;padding:0 0 0 5px;">
				    <div class="easyui-panel" title="请选择" icon="icon-organisation" fit="true" border="true" headerCls="header_cls" style="padding:5px ;">
				     <ul id="com_tree"></ul>
				    </div>
				</div>
				<div region="center"  id="left_main"   style="overflow:hidden; width: 600px;" border="false">
				</div>
      		 </div>
   </div>
  </body>
</html>