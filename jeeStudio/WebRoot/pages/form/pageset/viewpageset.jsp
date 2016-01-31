<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
    <title>查看页配置</title>    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<script type="text/javascript" src="js/debug/form.pageset.debug.js"></script>
	<script language="javascript" type="text/javascript">		
		
		
		function fmDataDictionaryShowKeyUp(evt,o,treeName){
			evt = (evt) ? evt : window.event;
			var valueStr = o.value;
			if(valueStr != null && valueStr != "" && valueStr.replace(/^\s+|\s+$/g,"") != ""){
				$.post("form/form!getDictionaryLikeName.action",{"dictName":o.value},
	           		function(data){           			
	           			var dataObj = eval('('+data+')');
		          		$('#'+treeName).tree("loadData",dataObj);
	       			}
	       		);
       		}else{
       			$('#'+treeName).tree("loadData",fmPagesetDataDictionary);
       		}
		}
		var fmViewpagesetDatacolumnsetTableGroup = new Array();
		function viewPageSetSubmit(formsetting){
			$.post("form/form!updateFormSettings.action",{"formId":viewFormId,"formSettings":'<?xml version="1.0" encoding="UTF-8"?>' + formsetting},
           		function(data){
	          		if("success" == data) {
	          			refreshViewPageSetData();
	          			fdFormDataGridRefresh();
						$.messager.alert("提示","保存成功！", 'info');
						fmRefreshDesignViewIframe();
					}
       			}
       		);
		}
		function fmViewPageSetClose(){
			parent.$('#fm_formdesignview_viewpageset').window('close');
		}
		
		function initFmViewPageSetGroupData(formSettings){
			var viewXmlUtils = new XmlUtils({dataType:'json'}); 
			viewXmlUtils.loadXmlString(formSettings);
			var viewRootNode = viewXmlUtils.getRoot();	
			var viewGroupsNode = viewXmlUtils.getChildNodeByTagName(viewRootNode,'Groups');	
			var viewGroupNodes = viewXmlUtils.getChildNodes(viewGroupsNode);
			fmViewpagesetDatacolumnsetTableGroup.length=0;
			fmViewpagesetDatacolumnsetTableGroup.push({id:'-1',text:'无'});
			if(viewGroupNodes){
				var len = viewGroupNodes.length;
				for (var i=0;i<len;i++){					
					var viewGroupNode = viewGroupNodes[i];										
					fmViewpagesetDatacolumnsetTableGroup.push({id:viewGroupNode.getAttribute('id'),text:viewGroupNode.getAttribute('title')});
				}
			}
		}
    </script>
  </head>  
  <body>
  	<div id="fm_viewpageset_tabs" class="easyui-tabs" fit="true" border="false">
		<div id="fm_viewpageset_pageset" title="页面信息配置" style="padding:4px;overflow-x:hidden;" href="pages/form/pageset/viewpageset_basic.jsp">			
		</div>
		<div id="fm_viewpageset_datacolumnset" title="数据列配置" style="padding:4px;overflow-x:hidden;" href="pages/form/pageset/viewpageset_datacolumn.jsp">			
		</div>
		<div id="fm_viewpageset_tabsset" title="多标签配置" style="padding:4px;overflow-x:hidden;" href="pages/form/pageset/viewpageset_tabs.jsp">
		</div>
		<div id="fm_viewpageset_paramset" title="参数配置" style="padding:4px;overflow-x:hidden;" href="pages/form/pageset/viewpageset_param.jsp">
		</div>
	</div>
	
  </body>
</html>
