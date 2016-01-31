<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  <base href="<%=basePath%>">
    <title>编辑页配置</title>    
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
		var fmEditpagesetDatacolumnsetTableGroup = new Array();
		function editPageSetSubmit(formsetting){
			$.post("form/form!updateFormSettings.action",{"formId":editFormId,"formSettings":'<?xml version="1.0" encoding="UTF-8"?>' + formsetting},
           		function(data){
	          		if("success" == data) {
	          			refreshEditPageSetData();
	          			fdFormDataGridRefresh();
						$.messager.alert("提示","保存成功！", 'info');
						fmRefreshDesignEditIframe();
					}
       			}
       		);
		}
		function fmEditPageSetClose(){
			parent.$('#fm_formdesignedit_editpageset').window('close');
		}
		function initFmEditPageSetGroupData(formSettings){
			var editXmlUtils = new XmlUtils({dataType:'json'}); 
			editXmlUtils.loadXmlString(editFormSettings);
			var editRootNode = editXmlUtils.getRoot();	
			var editGroupsNode = editXmlUtils.getChildNodeByTagName(editRootNode,'Groups');	
			var editGroupNodes = editXmlUtils.getChildNodes(editGroupsNode);
			fmEditpagesetDatacolumnsetTableGroup.length=0;
			fmEditpagesetDatacolumnsetTableGroup.push({id:'-1',text:'无'});
			if(editGroupNodes){
				var len = editGroupNodes.length;
				for (var i=0;i<len;i++){					
					var editGroupNode = editGroupNodes[i];										
					fmEditpagesetDatacolumnsetTableGroup.push({id:editGroupNode.getAttribute('id'),text:editGroupNode.getAttribute('title')});
				}
			}
		}	
    </script>
  </head>  
  <body>
  
  	<div id="fm_editpageset_tabs" class="easyui-tabs" fit="true" border="false">
		<div id="fm_editpageset_pageset" title="页面信息配置" style="padding:4px;overflow-x:hidden;" href="pages/form/pageset/editpageset_basic.jsp">			
		</div>
		<div id="fm_editpageset_datacolumnset" title="数据列配置" style="padding:4px;overflow-x:hidden;" href="pages/form/pageset/editpageset_datacolumn.jsp">
		</div>
		<div id="fm_editpageset_tabsset" title="多标签配置" style="padding:4px;overflow-x:hidden;" href="pages/form/pageset/editpageset_tabs.jsp">
		</div>
		<div id="fm_editpageset_paramset" title="参数配置" style="padding:4px;overflow-x:hidden;" href="pages/form/pageset/editpageset_param.jsp">
		</div>
	</div>
	
  </body>
</html>
