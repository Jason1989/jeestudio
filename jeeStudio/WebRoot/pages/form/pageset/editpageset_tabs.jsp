<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
    <title> </title>    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript" type="text/javascript">
		var fmEditTabsInfoArray = new Array();
		$(function(){
			$("#fm_editpageset_tabspageurl_0").combobox({
				valueField:'id',
   				textField:'text',  
            	treeWidth:157,
			    url:'form/form!getTabsPageList.action?mainObjectId=' + editMainObjectId + '&pageType=editPage&random='+parseInt(10*Math.random())
			});			
			$("#fm_editpageset_tabspagetype_0").combobox({
				onChange:function(newValue,oldValue){							
					$("#fm_editpageset_tabspageurl_0").combobox("setValue","");
					$("#fm_editpageset_tabspageurl_0").combobox({
						url:'form/form!getTabsPageList.action?mainObjectId=' + editMainObjectId + '&pageType=' + newValue+ '&random='+parseInt(10*Math.random())
					});	
				}
			});
			$("#fm_editpageset_tabschildren_0").combobox({
				valueField:'id',
   				textField:'text',  
            	treeWidth:157,
            	data:[{id:'-1',text:'请选择'}]});
            $("#fm_editpageset_tabschildren_0").combobox('setValue',"-1");
			//$('#fm_pageset_tabs_delete_0').linkbutton();
			
			var initXmlUtils = new XmlUtils({dataType:'json'}); 
			initXmlUtils.loadXmlString(editFormSettings);
			var initRootNode = initXmlUtils.getRoot();	
			//多标签
	        var editTabsNode = initXmlUtils.getChildNodeByTagName(initRootNode,'Tabs');
	        if(editTabsNode.getAttribute('isuse')==true || editTabsNode.getAttribute('isuse') == 'true'){
				$('#fm_editpageset_tabsset_isshow').attr('checked',true);
			}else{
				$('#fm_editpageset_tabsset_isshow').attr('checked',false);
			}
	        var editTabNodes = initXmlUtils.getChildNodes(editTabsNode);
	        if(editTabNodes){
	        	var len = editTabNodes.length;
				for (var i=0;i<len;i++){
					if(len > 1 && i != 0){
	        			fmEditpagesetTabssetAdditem();					
	        		}
					var editTabNode = editTabNodes[i];					
					//$('#fm_editpageset_tabsid_' + i).val(editTabNode.getAttribute('id'));
					$('#fm_editpageset_tabssort_' + i).val(editTabNode.getAttribute('sortIndex'));
					$('#fm_editpageset_tabstitle_' + i).val(editTabNode.getAttribute('title'));
					
					$('#fm_editpageset_tabspagetype_' + i).combobox("setValue",editTabNode.getAttribute('type'));
					$('#fm_editpageset_tabspageurl_' + i).combobox("setValue",editTabNode.getAttribute('url'));
					if(editTabNode.getAttribute('lazyLading') == 'true'){
						$(":checkbox[id='fm_editpageset_tabsislazy_" + i + "']").attr("checked",true);
					}else if(editTabNode.getAttribute('lazyLading') == 'false'){
						$(":checkbox[id='fm_editpageset_tabsislazy_" + i + "']").attr("checked",false);
					}
				}
				fmEditTabsInfoToArray();
				for (var k=0;k<len;k++){
					var editTabNode = editTabNodes[k];						
					var childAppIdValue = editTabNode.getAttribute('childAppId');
					if(childAppIdValue != "")
						for(var j=0;j<fmEditTabsInfoArray.length;j++){
							if(childAppIdValue == fmEditTabsInfoArray[j].text){
								$('#fm_editpageset_tabschildren_' + k).combobox("setValue",fmEditTabsInfoArray[j].id);
								break;
							}
						}					
				}
			}
		});		
		//Tabs Begin
		function fmEditpagesetTabsSave(){
			var pageSetXmlUtils = new XmlUtils({dataType:'json'}); 
			pageSetXmlUtils.loadXmlString(editFormSettings);
			var rootNode = pageSetXmlUtils.getRoot();
			
			var editTabsNode = pageSetXmlUtils.getChildNodeByTagName(rootNode,'Tabs');
			pageSetXmlUtils.removeChildNodes(editTabsNode);
			if($('#fm_editpageset_tabsset_isshow').attr('checked')){
				pageSetXmlUtils.setAttribute(editTabsNode,'isuse','true');
			}else{
				pageSetXmlUtils.setAttribute(editTabsNode,'isuse','false');
			}
			var flag = false;
			$("#fm_editpageset_tabsset_tbody tr").each(function(i,node){ 
				if(node.id != 'fm_editpageset_default_tr'){
					//var tabsIdEle = $(node).find("td input[id^='fm_editpageset_tabsid_']");
					var tabsSortEle = $(node).find("td input[id^='fm_editpageset_tabssort_']");
					var tabsTitleEle = $(node).find("td input[id^='fm_editpageset_tabstitle_']");
					var tabsIslazyEle = $(node).find("td input[id^='fm_editpageset_tabsislazy_']");
					var tabsPageTypeEle = $(node).find("td select[id^='fm_editpageset_tabspagetype_']");
					var tabsPageUrlEle = $(node).find("td select[id^='fm_editpageset_tabspageurl_']");
					var tabsChildrenEle = $(node).find("td select[id^='fm_editpageset_tabschildren_']");
					
					//var tabsId = tabsIdEle.val();
					var tabsSort = tabsSortEle.val();
					var tabsTitle = tabsTitleEle.val();
					var tabsIslazy = 'false';
					if(tabsIslazyEle.attr("checked")){
						tabsIslazy = 'true';
					}
					var tabsPageType = tabsPageTypeEle.combobox("getValue");
					var tabsPageUrl = tabsPageUrlEle.combobox("getValue");
					var tabsChildren = tabsChildrenEle.combobox("getValue");
					var tabsChildrenText;
					if(tabsChildren == "-1"){
						tabsChildrenText = "";
					}else{
						tabsChildrenText = tabsChildrenEle.combobox("getText");
					}
					if(tabsTitle != ""){
						var pageNodeAttr = {
							id:"",
							title:tabsTitle,
							lazyLading:tabsIslazy,
							sortIndex:tabsSort,
							type:tabsPageType,
							url:tabsPageUrl,
							childAppId:tabsChildrenText
						}
						pageSetXmlUtils.createNode('Page','',pageNodeAttr,editTabsNode);
					}else{
						flag = true;
					}
				}
			});
			if(flag){
				$.messager.alert("提示","请输入标题！", 'warning');	
				return false;
			}
			pageSetXmlUtils.setAttribute(editTabsNode,'isConfig','1');
			editPageSetSubmit(pageSetXmlUtils.toString());
			$("#fm_formdesignedit_tabs").html(CONFIGYES);
		}
		function fmEditpagesetTabssetAdditem(){
			var tbody = $("#fm_editpageset_tabsset_tbody");	
			var cloneObject = $(tbody.find("tr").last()).clone();		
			fmPagesetEditTabssetAddItem(cloneObject,"fm_editpageset_tabsup_","fm_editpageset_tabsdown_");
			cloneObject.appendTo(tbody);
			fmTableRowsSortImgHidden("fm_editpageset_tabsset_tbody","fm_editpageset_tabsup_","fm_editpageset_tabsdown_");
			fmEditpagesetTabssetInitData(cloneObject);
		}
		function fmEditpagesetTabssetAdditemClick(){
			fmEditpagesetTabssetAdditem();
			fmEditTabsInfoToArray();
		}
		function fmEditpagesetTabssetInitData(nodeObj)
		{
			var tabsPageUrlSelect = $(nodeObj).find("td select[id^='fm_editpageset_tabspageurl_']");
			tabsPageUrlSelect.combobox({
				valueField:'id',
   				textField:'text',  
            	treeWidth:157,
			    url:'form/form!getTabsPageList.action?mainObjectId=' + editMainObjectId + '&pageType=editPage&random='+parseInt(10*Math.random())
			});
			var tabsPageTypeSelect = $(nodeObj).find("td select[id^='fm_editpageset_tabspagetype_']");
			tabsPageTypeSelect.combobox({
				onChange:function(newValue,oldValue){							
					tabsPageUrlSelect.combobox("setValue","");
					tabsPageUrlSelect.combobox({
						url:'form/form!getTabsPageList.action?mainObjectId=' + editMainObjectId + '&pageType=' + newValue+ '&random='+parseInt(10*Math.random())
					});	
				}
			});
			var tabsChildrenSelect = $(nodeObj).find("td select[id^='fm_editpageset_tabschildren_']");
			tabsChildrenSelect.combobox({
				valueField:'id',
   				textField:'text',  
            	treeWidth:157,
			    data:fmEditTabsInfoArray
			});	
			
		}
		
		function fmEditTabsInfoToArray(){
			fmEditTabsInfoArray.length = 0;
			fmEditTabsInfoArray.push({id:'-1',text:'请选择'});
			$("#fm_editpageset_tabsset_tbody tr").each(function(i,node){
				if(node.id != 'fm_editpageset_default_tr'){					
					var tabsIdEle = $(node).find("td input[id^='fm_editpageset_tabsid_']");
					var tabsId = tabsIdEle.val();
					var tabsTitleEle = $(node).find("td input[id^='fm_editpageset_tabstitle_']");
					var tabsTitle = tabsTitleEle.val();
					fmEditTabsInfoArray.push({id:tabsId,text:tabsTitle});
				}
			});
			$("#fm_editpageset_tabsset_tbody tr").each(function(k,nodeObj){
				var thisFmEditTabsInfoArray = new Array();
				if(nodeObj.id != 'fm_editpageset_default_tr'){					
					var tabsChildrenSelect = $(nodeObj).find("td select[id^='fm_editpageset_tabschildren_']");
					var tabsIdEle = $(nodeObj).find("td input[id^='fm_editpageset_tabsid_']");
					var tabsId = tabsIdEle.val();
					var tabsChildrenValue = tabsChildrenSelect.combobox("getValue");
					var flag = false;
					for(var j=0;j<fmEditTabsInfoArray.length;j++){
						if(tabsId != fmEditTabsInfoArray[j].id){
							thisFmEditTabsInfoArray.push(fmEditTabsInfoArray[j]);
						}
						if(tabsChildrenValue == fmEditTabsInfoArray[j].id){
							flag = true;
						}
					}
					tabsChildrenSelect.combobox({
						valueField:'id',
		   				textField:'text',  
		            	treeWidth:157,
					    data:thisFmEditTabsInfoArray
					});
					if(tabsChildrenValue != "" && flag){
						tabsChildrenSelect.combobox("setValue",tabsChildrenValue);
					}else{
						tabsChildrenSelect.combobox("setValue","-1");
					}
				}
			});
		}
		//Tabs End
    </script>
  </head>  
  <body>
	<table border="0" cellpadding="1" cellspacing="1" width="100%" style="background:#dedede;">
				<tr style="background:#ffffff;">					  
				  <td style="background:#eaeaea;" colspan="6">
				  	<input type="checkbox" id="fm_editpageset_tabsset_isshow"/>显示&nbsp;&nbsp;&nbsp;&nbsp;
				  	<a href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="fmEditpagesetTabssetAdditemClick()">增加</a></td>
				</tr>
				<tr style="background:#eeeeee;height:24px;">
				 <!--  <td width="15%" align="center">编号</td> -->				  
				  <td width="25%" align="center">标题</td>				 
				  <td width="8%" align="center">延迟加载</td>
				  <td width="15%" align="center">页面类型</td>
				  <td width="25%" align="center">页面</td>
				  <td width="15%" align="center">子标签</td>
				  <td width="10%" style="display:none;">序号</td>
				  <td align="center">操作</td>
				</tr>
				<tbody id="fm_editpageset_tabsset_tbody">
					<tr style="background:#ffffff;display:none;" id="fm_editpageset_default_tr">
					  <td style="display:none;"><input type="text" id="fm_editpageset_tabsid_-1" size="14" value="0"/></td>
					  <td align="center"><input type="text" id="fm_editpageset_tabstitle_-1" size="20" onkeyup="fmEditTabsInfoToArray();"/></td>
					  <td align="center"><input type="checkbox" id="fm_editpageset_tabsislazy_-1"/></td>
					  <td align="center"><select id="fm_editpageset_tabspagetype_-1" style="width:100px;">
					  						<option value="listPage">列表页</option>
					  						<option value="editPage">编辑页</option>
					  						<option value="viewPage">查看页</option>
					  					</select>
					  					
					  </td>
					  <td align="center"><select id="fm_editpageset_tabspageurl_-1" style="width:160px;"></select></td>
					  <td align="center"><select id="fm_editpageset_tabschildren_-1" style="width:100px;">					  						
					  					</select>
					  </td>
					  <td style="display:none;"><input type="text" id="fm_editpageset_tabssort_-1" size="5" value="0"/></td>
					  <td align="center">
					  	<!-- <a id="fm_pageset_tabs_delete_-1" href="javascript:;" plain="true" icon="icon-remove" onclick="fmPagesetDynamicDeleteItem(this)">删除</a> -->
					  	<img src="images/ioc-delete.gif" style="cursor:hand;margin:2px 2px 0px 4px;" title="删除" onclick="fmEditPagesetSortDynamicDeleteItem(this,'fm_editpageset_tabsset_tbody','fm_editpageset_tabsup_','fm_editpageset_tabsdown_')"/>
					  	<img id="fm_editpageset_tabsup_-1" src="images/up.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="上移" onclick="fmTableRowsSort(this,1,6,7)"/>
					  	<img id="fm_editpageset_tabsdown_-1" src="images/down.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="下移" onclick="fmTableRowsSort(this,0,6,7)"/>
					  </td>
					</tr>
					<tr style="background:#ffffff;">
					  <td style="display:none;"><input type="text" id="fm_editpageset_tabsid_0" size="14" value="1"/></td>
					  <td align="center"><input type="text" id="fm_editpageset_tabstitle_0" size="20" onkeyup="fmEditTabsInfoToArray();"/></td>
					  <td align="center"><input type="checkbox" id="fm_editpageset_tabsislazy_0"/></td>
					  <td align="center"><select id="fm_editpageset_tabspagetype_0" style="width:100px;">
					  						<option value="listPage">列表页</option>
					  						<option value="editPage">编辑页</option>
					  						<option value="viewPage">查看页</option>
					  					</select>
					  					
					  </td>
					  <td align="center"><select id="fm_editpageset_tabspageurl_0" style="width:160px;"></select></td>
					  <td align="center"><select id="fm_editpageset_tabschildren_0" style="width:100px;">					  						
					  					</select>
					  </td>
					  <td style="display:none;"><input type="text" id="fm_editpageset_tabssort_0" size="5" value="1"/></td>
					  <td align="center">
					  	<img src="images/ioc-delete.gif" style="cursor:hand;margin:2px 2px 0px 4px;" title="删除" onclick="fmEditPagesetSortDynamicDeleteItem(this,'fm_editpageset_tabsset_tbody','fm_editpageset_tabsup_','fm_editpageset_tabsdown_')"/>
					  	<img id="fm_editpageset_tabsup_0" src="images/up.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="上移" onclick="fmTableRowsSort(this,1,6,7)"/>
					  	<img id="fm_editpageset_tabsdown_0" src="images/down.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="下移" onclick="fmTableRowsSort(this,0,6,7)"/>
					  </td>
					</tr>
				</tbody>				
			</table>
			<div style="height:4px;"></div>
			<table border="0" cellpadding="5" cellspacing="0" width="100%">
				<tr>
					<td align="center"><a class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="fmEditpagesetTabsSave()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:;"  onclick="fmEditPageSetClose()" >关闭</a></td>										
				</tr>					
			</table>
  </body>
</html>
