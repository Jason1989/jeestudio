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
		$(function(){	
			$("#fm_listpageset_tabspageurl_0").combobox({
				valueField:'id',
   				textField:'text',  
            	treeWidth:157,
			    url:'form/form!getTabsPageList.action?mainObjectId=' + mainObjectId + '&pageType=editPage&random='+parseInt(10*Math.random())
			});			
			$("#fm_listpageset_tabspagetype_0").combobox({
				onChange:function(newValue,oldValue){							
					$("#fm_listpageset_tabspageurl_0").combobox("setValue","");
					$("#fm_listpageset_tabspageurl_0").combobox({						
						url:'form/form!getTabsPageList.action?mainObjectId=' + mainObjectId + '&pageType=' + newValue+ '&random='+parseInt(10*Math.random())
					});	
				}
			});
			
			//$('#fm_listpageset_tabsdelete_0').linkbutton();
			
			var initXmlUtils = new XmlUtils({dataType:'json'}); 
			initXmlUtils.loadXmlString(formSettings);
			var rootNode = initXmlUtils.getRoot();		
			//多标签
	        var listTabsNode = initXmlUtils.getChildNodeByTagName(rootNode,'Tabs');
	        if(listTabsNode.getAttribute('isuse')==true || listTabsNode.getAttribute('isuse') == 'true'){
				$('#fm_listpageset_tabsset_isshow').attr('checked',true);
			}else{
				$('#fm_listpageset_tabsset_isshow').attr('checked',false);
			}
	        var listTabNodes = initXmlUtils.getChildNodes(listTabsNode);
	        if(listTabNodes){
	        	var len = listTabNodes.length;
				for (var i=0;i<len;i++){
					if(len > 1 && i != 0){
	        			fmListpagesetTabssetAdditem();
	        		}
					var listTabNode = listTabNodes[i];					
					//$('#fm_listpageset_tabsid_' + i).val(listTabNode.getAttribute('id'));
					$('#fm_listpageset_tabssort_' + i).val(listTabNode.getAttribute('sortIndex'));
					$('#fm_listpageset_tabstitle_' + i).val(listTabNode.getAttribute('title'));
					$('#fm_listpageset_tabspagetype_' + i).combobox("setValue",listTabNode.getAttribute('type'));
					$('#fm_listpageset_tabspageurl_' + i).combobox("setValue",listTabNode.getAttribute('url'));
					if(listTabNode.getAttribute('lazyLading') == 'true'){
						$(":checkbox[id='fm_listpageset_tabsislazy_" + i + "']").attr("checked",true);
					}else if(listTabNode.getAttribute('lazyLading') == 'false'){
						$(":checkbox[id='fm_listpageset_tabsislazy_" + i + "']").attr("checked",false);
					}
				}
			}
		});		
		//Tabs Begin
		function fmListpagesetTabsSave(){
		
			var pageSetXmlUtils = new XmlUtils({dataType:'json'}); 
			pageSetXmlUtils.loadXmlString(formSettings);
			var rootNode = pageSetXmlUtils.getRoot();
			
			var listTabsNode = pageSetXmlUtils.getChildNodeByTagName(rootNode,'Tabs');
			pageSetXmlUtils.removeChildNodes(listTabsNode);
			if($('#fm_listpageset_tabsset_isshow').attr('checked')){
				pageSetXmlUtils.setAttribute(listTabsNode,'isuse','true');
			}else{
				pageSetXmlUtils.setAttribute(listTabsNode,'isuse','false');
			}
			var flag = false;
			$("#fm_listpageset_tabsset_tbody tr").each(function(i,node){ 
				if(node.id != 'fm_listpageset_default_tr'){
					//var tabsIdEle = $(node).find("td input[id^='fm_listpageset_tabsid_']");
					var tabsSortEle = $(node).find("td input[id^='fm_listpageset_tabssort_']");
					var tabsTitleEle = $(node).find("td input[id^='fm_listpageset_tabstitle_']");
					var tabsIslazyEle = $(node).find("td input[id^='fm_listpageset_tabsislazy_']");
					var tabsPageTypeEle = $(node).find("td select[id^='fm_listpageset_tabspagetype_']");
					var tabsPageUrlEle = $(node).find("td select[id^='fm_listpageset_tabspageurl_']");
					
					//var tabsId = tabsIdEle.val();
					var tabsSort = tabsSortEle.val();
					var tabsTitle = tabsTitleEle.val();
					var tabsIslazy = 'false';
					if(tabsIslazyEle.attr("checked")){
						tabsIslazy = 'true';
					}
					var tabsPageType = tabsPageTypeEle.combobox("getValue");
					var tabsPageUrl = tabsPageUrlEle.combobox("getValue");
					if(tabsTitle != ""){
						//return false;
						/**
						var listTabNode = pageSetXmlUtils.hasNodeByAttribute(listTabsNode,'id',tabsId);
						if(listTabNode){
							pageSetXmlUtils.setAttribute(listTabNode,'id',tabsId);
							pageSetXmlUtils.setAttribute(listTabNode,'title',tabsTitle);
							pageSetXmlUtils.setAttribute(listTabNode,'lazyLading',tabsIslazy);
							pageSetXmlUtils.setAttribute(listTabNode,'sortIndex','0');
							pageSetXmlUtils.setAttribute(listTabNode,'type',tabsPageType);
							pageSetXmlUtils.setAttribute(listTabNode,'url',tabsPageUrl);
						}else{*/
							var pageNodeAttr = {
								id:"",
								title:tabsTitle,
								lazyLading:tabsIslazy,
								sortIndex:tabsSort,
								type:tabsPageType,
								url:tabsPageUrl
							}
							pageSetXmlUtils.createNode('Page','',pageNodeAttr,listTabsNode);
						//}
					}else{
						flag = true;
					}
				}
			});		
			if(flag){
				$.messager.alert("提示","请输入标题！", 'warning');	
				return false;
			}
			/**$("#fm_listpageset_tabsset_tbody tr").each(function(i,node){ 
				var tabsIdEle = $(node).find("td input[id^='fm_listpageset_tabsid_']");
				if(tabsIdEle.attr('id') != 'fm_listpageset_tabsid_0'){
					$(node).remove();
				}
			});*/
			pageSetXmlUtils.setAttribute(listTabsNode,'isConfig','1');
			$("#fm_formdesignlist_tabs").html(CONFIGYES);
			listPageSetSubmit(pageSetXmlUtils.toString());
		}
		//Tabs End
		function fmListpagesetTabssetAdditem(){
			var tbody = $("#fm_listpageset_tabsset_tbody");	
			var cloneObject = $(tbody.find("tr").last()).clone();		
			fmPagesetTabssetAddItem(cloneObject,"fm_listpageset_tabsup_","fm_listpageset_tabsdown_");
			cloneObject.appendTo(tbody);
			fmTableRowsSortImgHidden("fm_listpageset_tabsset_tbody","fm_listpageset_tabsup_","fm_listpageset_tabsdown_");
			fmListpagesetTabssetInitData(cloneObject);
		}
		function fmListpagesetTabssetInitData(nodeObj)
		{
			var tabsPageUrlSelect = $(nodeObj).find("td select[id^='fm_listpageset_tabspageurl_']");
			//alert(tabsPageUrlSelect.attr('id'));
			tabsPageUrlSelect.combobox({
				valueField:'id',
   				textField:'text',  
            	treeWidth:157,
			    url:'form/form!getTabsPageList.action?mainObjectId=' + mainObjectId + '&pageType=editPage&random='+parseInt(10*Math.random())
			});
			var tabsPageTypeSelect = $(nodeObj).find("td select[id^='fm_listpageset_tabspagetype_']");
			tabsPageTypeSelect.combobox({
				onChange:function(newValue,oldValue){							
					tabsPageUrlSelect.combobox("setValue","");
					tabsPageUrlSelect.combobox({
						url:'form/form!getTabsPageList.action?mainObjectId=' + mainObjectId + '&pageType=' + newValue+ '&random='+parseInt(10*Math.random())
					});	
				}
			});

		}
    </script>
  </head>  
  <body>
	<table border="0" cellpadding="1" cellspacing="1" width="100%" style="background:#dedede;">
				<tr style="background:#ffffff;">					  
				  <td style="background:#eaeaea;" colspan="6">
				  	<input type="checkbox" id="fm_listpageset_tabsset_isshow"/>显示&nbsp;&nbsp;&nbsp;&nbsp;
				  	<a href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="fmListpagesetTabssetAdditem()">增加</a></td>
				</tr>
				<!-- 
				<tr style="background:#ffffff;">
				  <td width="6%" align="right">ID：</td>
				  <td width="15%"><input type="text" id="fm_listpageset_tabsid"/></td>
				  <td width="6%" align="right">标题：</td>
				  <td width="18%"><input type="text" id="fm_listpageset_tabstitle"/></td>
				  <td width="10%"><input type="checkbox" id="fm_listpageset_tabsislazy"/>延迟加载</td>
				  <td width="8%" align="right">选择页面</td>
				  <td width="20%"><select id="fm_listpageset_tabspageurl" style="width:160px;"></select></td>
				  <td><a href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-remove" onclick="fmListpagesetTabssetDeleteitem()">删除</a></td>
				</tr>
				 -->
				<tr style="background:#eeeeee;height:24px;">
				  <!-- <td width="15%" align="center">编号</td> -->				  
				  <td width="25%" align="center">标题</td>				 
				  <td width="8%" align="center">延迟加载</td>
				  <td width="15%" align="center">页面类型</td>
				  <td width="25%" align="center">页面</td>
				  <td width="10%" style="display:none;">序号</td>
				  <td align="center">操作</td>
				</tr>
				<tbody id="fm_listpageset_tabsset_tbody">
					<tr style="background:#ffffff;display:none;" id="fm_listpageset_default_tr">
					  <!-- <td align="center"><input type="text" id="fm_listpageset_tabsid_-1" size="14" /></td> -->
					  <td align="center"><input type="text" id="fm_listpageset_tabstitle_-1" size="20" /></td>
					  <td align="center"><input type="checkbox" id="fm_listpageset_tabsislazy_-1"/></td>
					  <td align="center"><select id="fm_listpageset_tabspagetype_-1" style="width:100px;">
					  						<option value="listPage">列表页</option>
					  						<option value="editPage">编辑页</option>
					  						<option value="viewPage">查看页</option>
					  					</select>
					  					
					  </td>
					  <td align="center"><select id="fm_listpageset_tabspageurl_-1" style="width:160px;"></select></td>
					  <td style="display:none;"><input type="text" id="fm_listpageset_tabssort_-1" size="5" value="0"/></td>
					  <td align="center">
					  	<!-- <a id="fm_listpageset_tabsdelete_-1" href="javascript:;" plain="true" icon="icon-remove" onclick="fmPagesetDynamicDeleteItem(this)">删除</a> -->
					  	<img src="images/ioc-delete.gif" style="cursor:hand;margin:2px 2px 0px 4px;" title="删除" onclick="fmPagesetSortDynamicDeleteItem(this,'fm_listpageset_tabsset_tbody','fm_listpageset_tabsup_','fm_listpageset_tabsdown_')"/>
					  	<img id="fm_listpageset_tabsup_-1" src="images/up.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="上移" onclick="fmTableRowsSort(this,1,4,5)"/>
					  	<img id="fm_listpageset_tabsdown_-1" src="images/down.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="下移" onclick="fmTableRowsSort(this,0,4,5)"/>
					  </td>
					</tr>
					<tr style="background:#ffffff;">
					  <!-- <td align="center"><input type="text" id="fm_listpageset_tabsid_0" size="14" /></td> -->
					  <td align="center"><input type="text" id="fm_listpageset_tabstitle_0" size="20" /></td>
					  <td align="center"><input type="checkbox" id="fm_listpageset_tabsislazy_0"/></td>
					  <td align="center"><select id="fm_listpageset_tabspagetype_0" style="width:100px;">
					  						<option value="listPage">列表页</option>
					  						<option value="editPage">编辑页</option>
					  						<option value="viewPage">查看页</option>
					  					</select>
					  					
					  </td>
					  <td align="center"><select id="fm_listpageset_tabspageurl_0" style="width:160px;"></select></td>
					  <td style="display:none;"><input type="text" id="fm_listpageset_tabssort_0" size="5" value="1"/></td>
					  <td align="center">
					  	<img src="images/ioc-delete.gif" style="cursor:hand;margin:2px 2px 0px 4px;" title="删除" onclick="fmPagesetSortDynamicDeleteItem(this,'fm_listpageset_tabsset_tbody','fm_listpageset_tabsup_','fm_listpageset_tabsdown_')"/>
					  	<img id="fm_listpageset_tabsup_-1" src="images/up.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="上移" onclick="fmTableRowsSort(this,1,4,5)"/>
					  	<img id="fm_listpageset_tabsdown_-1" src="images/down.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="下移" onclick="fmTableRowsSort(this,0,4,5)"/>
					  </td>
					</tr>
				</tbody>					
			</table>
			<div style="height:4px;"></div>
			<table border="0" cellpadding="5" cellspacing="0" width="100%">
				<tr>
					<td align="center">
					<a class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="fmListpagesetTabsSave()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:;"  onclick="fmListPageSetClose()" >关闭</a></td>										
				</tr>					
			</table>
  </body>
</html>
