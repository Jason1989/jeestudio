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
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<script language="javascript" type="text/javascript">
		$(function(){
			$("#fm_viewpageset_tabspageurl_0").combobox({
				valueField:'id',
   				textField:'text',  
            	treeWidth:157,
			    url:'form/form!getTabsPageList.action?mainObjectId=' + viewMainObjectId + '&pageType=editPage&random='+parseInt(10*Math.random())
			});			
			$("#fm_viewpageset_tabspagetype_0").combobox({
				onChange:function(newValue,oldValue){							
					$("#fm_viewpageset_tabspageurl_0").combobox("setValue","");
					$("#fm_viewpageset_tabspageurl_0").combobox({
						url:'form/form!getTabsPageList.action?mainObjectId=' + viewMainObjectId + '&pageType=' + newValue+ '&random='+parseInt(10*Math.random())
					});	
				}
			});
			//$('#fm_viewpageset_tabsdelete_0').linkbutton();
			var initXmlUtils = new XmlUtils({dataType:'json'}); 
			initXmlUtils.loadXmlString(viewFormSettings);
			var initRootNode = initXmlUtils.getRoot();	
			//多标签
	        var viewTabsNode = initXmlUtils.getChildNodeByTagName(initRootNode,'Tabs');
	        if(viewTabsNode.getAttribute('isuse')==true || viewTabsNode.getAttribute('isuse') == 'true'){
				$('#fm_viewpageset_tabsset_isshow').attr('checked',true);
			}else{
				$('#fm_viewpageset_tabsset_isshow').attr('checked',false);
			}
	        var viewTabNodes = initXmlUtils.getChildNodes(viewTabsNode);
	        if(viewTabNodes){
	        	var len = viewTabNodes.length;
				for (var i=0;i<len;i++){
					if(len > 1 && i != 0){
	        			fmViewpagesetTabssetAdditem();
	        		}
					var viewTabNode = viewTabNodes[i];					
					//$('#fm_viewpageset_tabsid_' + i).val(viewTabNode.getAttribute('id'));
					$('#fm_viewpageset_tabssort_' + i).val(viewTabNode.getAttribute('sortIndex'));
					$('#fm_viewpageset_tabstitle_' + i).val(viewTabNode.getAttribute('title'));
					$('#fm_viewpageset_tabspagetype_' + i).combobox("setValue",viewTabNode.getAttribute('type'));
					$('#fm_viewpageset_tabspageurl_' + i).combobox("setValue",viewTabNode.getAttribute('url'));
					if(viewTabNode.getAttribute('lazyLading') == 'true'){
						$(":checkbox[id='fm_viewpageset_tabsislazy_" + i + "']").attr("checked",true);
					}else if(viewTabNode.getAttribute('lazyLading') == 'false'){
						$(":checkbox[id='fm_viewpageset_tabsislazy_" + i + "']").attr("checked",false);
					}
				}
			}
			
			/*选中应用时，增加按钮有效*/
			$('#fm_viewpageset_tabsset_isshow').click(function(){
			    if($(this).attr('checked')){
			       $("#fm_viewpageset_tabsset_isshowButton").linkbutton("enable");
			    }else{
			       $("#fm_viewpageset_tabsset_isshowButton").linkbutton("disable");
			    }
			});
		});		
		//Tabs Begin
		function fmViewpagesetTabsSave(){
			var pageSetXmlUtils = new XmlUtils({dataType:'json'}); 
			pageSetXmlUtils.loadXmlString(viewFormSettings);
			var rootNode = pageSetXmlUtils.getRoot();
			
			var viewTabsNode = pageSetXmlUtils.getChildNodeByTagName(rootNode,'Tabs');
			pageSetXmlUtils.removeChildNodes(viewTabsNode);
			if($('#fm_viewpageset_tabsset_isshow').attr('checked')){
				pageSetXmlUtils.setAttribute(viewTabsNode,'isuse','true');
			}else{
				pageSetXmlUtils.setAttribute(viewTabsNode,'isuse','false');
			}
			var flag = false;
			$("#fm_viewpageset_tabsset_tbody tr").each(function(i,node){ 
				if(node.id != 'fm_viewpageset_default_tr'){
					//var tabsIdEle = $(node).find("td input[id^='fm_viewpageset_tabsid_']");
					var tabsTitleEle = $(node).find("td input[id^='fm_viewpageset_tabstitle_']");
					var tabsIslazyEle = $(node).find("td input[id^='fm_viewpageset_tabsislazy_']");
					var tabsSortEle = $(node).find("td input[id^='fm_viewpageset_tabssort_']");
					var tabsPageTypeEle = $(node).find("td select[id^='fm_viewpageset_tabspagetype_']");
					var tabsPageUrlEle = $(node).find("td select[id^='fm_viewpageset_tabspageurl_']");
					
					//var tabsId = tabsIdEle.val();
					var tabsTitle = tabsTitleEle.val();
					var tabsSort = tabsSortEle.val();
					var tabsIslazy = 'false';
					if(tabsIslazyEle.attr("checked")){
						tabsIslazy = 'true';
					}
					var tabsPageType = tabsPageTypeEle.combobox("getValue");
					var tabsPageUrl = tabsPageUrlEle.combobox("getValue");
					if(tabsTitle != ""){
						//return false;
						/**
						var viewTabNode = pageSetXmlUtils.hasNodeByAttribute(viewTabsNode,'id',tabsId);
						if(viewTabNode){
							pageSetXmlUtils.setAttribute(viewTabNode,'id',tabsId);
							pageSetXmlUtils.setAttribute(viewTabNode,'title',tabsTitle);
							pageSetXmlUtils.setAttribute(viewTabNode,'lazyLading',tabsIslazy);
							pageSetXmlUtils.setAttribute(viewTabNode,'sortIndex','0');
							pageSetXmlUtils.setAttribute(viewTabNode,'type',tabsPageType);
							pageSetXmlUtils.setAttribute(viewTabNode,'url',tabsPageUrl);
						}else{*/
							var pageNodeAttr = {
								id:"",
								title:tabsTitle,
								lazyLading:tabsIslazy,
								sortIndex:tabsSort,
								type:tabsPageType,
								url:tabsPageUrl
							}
							pageSetXmlUtils.createNode('Page','',pageNodeAttr,viewTabsNode);
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
			/**
			var tabsId = $('#fm_viewpageset_tabsid').val();
			var tabsTitle = $('#fm_viewpageset_tabstitle').val();
			var tabsIslazy = 'false';
			var tabsPageurl = $('#fm_viewpageset_tabspageurl').combobox("getValue");
			if($(":checkbox[id='fm_viewpageset_tabsislazy']").attr("checked")){
				tabsIslazy = 'true';
			}
			var editTabNode = pageSetXmlUtils.hasNodeByAttribute(viewTabsNode,'id',tabsId);
			if(editTabNode){
				pageSetXmlUtils.setAttribute(editTabNode,'id',tabsId);
				pageSetXmlUtils.setAttribute(editTabNode,'title',tabsTitle);
				pageSetXmlUtils.setAttribute(editTabNode,'lazyLading',groupType);
				pageSetXmlUtils.setAttribute(editTabNode,'sortIndex','0');
				pageSetXmlUtils.setAttribute(editTabNode,'url',tabsPageurl);
			}else{
				pageSetXmlUtils.createNode('Page','',{id:tabsId,title:tabsTitle,lazyLading:tabsIslazy,sortIndex:'0',url:tabsPageurl},viewTabsNode);
			}*/
			pageSetXmlUtils.setAttribute(viewTabsNode,'isConfig','1');
			$("#fm_formdesignview_tabs").html(CONFIGYES);
			viewPageSetSubmit(pageSetXmlUtils.toString());
		}
		function fmViewpagesetTabssetAdditem(){
			var tbody = $("#fm_viewpageset_tabsset_tbody");	
			var cloneObject = $(tbody.find("tr").last()).clone();		
			fmPagesetTabssetAddItem(cloneObject,"fm_viewpageset_tabsup_","fm_viewpageset_tabsdown_");
			cloneObject.appendTo(tbody);
			fmTableRowsSortImgHidden("fm_viewpageset_tabsset_tbody","fm_viewpageset_tabsup_","fm_viewpageset_tabsdown_");
			/**
			var tbodyLength = $("#fm_viewpageset_tabsset_tbody tr").size();
			$("#fm_viewpageset_tabsset_tbody tr").each(function(i,node){ 
				var tabsUpEle = $(node).find("td img[id^='fm_viewpageset_tabsup_']");
				var tabsDownEle = $(node).find("td img[id^='fm_viewpageset_tabsdown_']");					
				if(tbodyLength > 2){
					if(i > 1 && i < tbodyLength-1){
						tabsUpEle.css("display",'');
						tabsDownEle.css("display",'');
					}else if(i==1){
						tabsDownEle.css("display",'');
					}else if(i == tbodyLength-1){
						tabsUpEle.css("display",'');
					}
				}else{
					tabsUpEle.css("display",'none');
					tabsDownEle.css("display",'none');
				}
			});*/
			
			fmViewpagesetTabssetInitData(cloneObject);
		}
		function fmViewpagesetTabssetInitData(nodeObj)
		{
			var tabsPageUrlSelect = $(nodeObj).find("td select[id^='fm_viewpageset_tabspageurl_']");			
			tabsPageUrlSelect.combobox({
				valueField:'id',
   				textField:'text',  
            	treeWidth:157,
			    url:'form/form!getTabsPageList.action?mainObjectId=' + viewMainObjectId + '&pageType=editPage&random='+parseInt(10*Math.random())
			});
			var tabsPageTypeSelect = $(nodeObj).find("td select[id^='fm_viewpageset_tabspagetype_']");
			tabsPageTypeSelect.combobox({
				onChange:function(newValue,oldValue){							
					tabsPageUrlSelect.combobox("setValue","");
					tabsPageUrlSelect.combobox({
						url:'form/form!getTabsPageList.action?mainObjectId=' + viewMainObjectId + '&pageType=' + newValue+ '&random='+parseInt(10*Math.random())
					});	
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
				  	<input type="checkbox" id="fm_viewpageset_tabsset_isshow"/> 应用&nbsp;&nbsp;&nbsp;&nbsp;
				  	<a href="javascript:;" id="fm_viewpageset_tabsset_isshowButton" class="easyui-linkbutton" plain="true" disabled="true" icon="icon-add" onclick="fmViewpagesetTabssetAdditem()">增加</a></td>
				</tr>
				<!-- 
				<tr style="background:#ffffff;">
				  <td width="6%" align="right">ID：</td>
				  <td width="15%"><input type="text" id="fm_viewpageset_tabsid"/></td>
				  <td width="6%" align="right">标题：</td>
				  <td width="18%"><input type="text" id="fm_viewpageset_tabstitle"/></td>
				  <td width="10%"><input type="checkbox" id="fm_viewpageset_tabsislazy"/>延迟加载</td>
				  <td width="8%" align="right">选择页面</td>
				  <td width="20%"><select id="fm_viewpageset_tabspageurl"></select></td>
				  <td><a href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-remove">删除</a></td>
				</tr>
				-->	
				<tr style="background:#eeeeee;height:24px;">
				 <!--  <td width="15%" align="center">编号</td> -->				  
				  <td width="25%" align="center">标题</td>				 
				  <td width="8%" align="center">延迟加载</td>
				  <td width="15%" align="center">页面类型</td>
				  <td width="25%" align="center">页面</td>
				  <td width="10%" style="display:none;">序号</td>
				  <td align="center">操作</td>
				</tr>
				<tbody id="fm_viewpageset_tabsset_tbody">
					<tr style="background:#ffffff;display:none;" id="fm_viewpageset_default_tr">
					 <!-- <td align="center"><input type="text" id="fm_viewpageset_tabsid_-1" size="14" /></td> -->
					  <td align="center"><input type="text" id="fm_viewpageset_tabstitle_-1" size="20" /></td>
					  <td align="center"><input type="checkbox" id="fm_viewpageset_tabsislazy_-1"/></td>
					  <td align="center"><select id="fm_viewpageset_tabspagetype_-1" style="width:100px;">
					  						<option value="listPage">列表页</option>
					  						<option value="editPage">编辑页</option>
					  						<option value="viewPage">查看页</option>
					  					</select>
					  					
					  </td>
					  <td align="center"><select id="fm_viewpageset_tabspageurl_-1" style="width:160px;"></select></td>
					  <td align="center" style="display:none;"><input type="text" id="fm_viewpageset_tabssort_-1" size="5" value="0"/></td>
					  <td align="center"><img src="images/ioc-delete.gif" style="cursor:hand;margin:2px 2px 0px 4px;" title="删除" onclick="fmPagesetSortDynamicDeleteItem(this,'fm_viewpageset_tabsset_tbody','fm_viewpageset_tabsup_','fm_viewpageset_tabsdown_')"/>
					  	<img id="fm_viewpageset_tabsup_-1" src="images/up.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="上移" onclick="fmTableRowsSort(this,1,4,5)"/>
					  	<img id="fm_viewpageset_tabsdown_-1" src="images/down.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="下移" onclick="fmTableRowsSort(this,0,4,5)"/>
					  </td>
					</tr>
					<tr style="background:#ffffff;">
					<!-- 
					  <td align="center"><input type="text" id="fm_viewpageset_tabsid_0" size="14" /></td> -->
					  <td align="center"><input type="text" id="fm_viewpageset_tabstitle_0" size="20" /></td>
					  <td align="center"><input type="checkbox" id="fm_viewpageset_tabsislazy_0"/></td>
					  <td align="center"><select id="fm_viewpageset_tabspagetype_0" style="width:100px;">
					  						<option value="listPage">列表页</option>
					  						<option value="editPage">编辑页</option>
					  						<option value="viewPage">查看页</option>
					  					</select>
					  					
					  </td>
					  <td align="center"><select id="fm_viewpageset_tabspageurl_0" style="width:160px;"></select></td>
					  <td align="center" style="display:none;"><input type="text" id="fm_viewpageset_tabssort_0" size="5" value="1"/></td>
					  <td align="center">
					  	<img src="images/ioc-delete.gif" style="cursor:hand;margin:2px 2px 0px 4px;" title="删除" onclick="fmPagesetSortDynamicDeleteItem(this,'fm_viewpageset_tabsset_tbody','fm_viewpageset_tabsup_','fm_viewpageset_tabsdown_')"/>
					  	<img id="fm_viewpageset_tabsup_0" src="images/up.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="上移" onclick="fmTableRowsSort(this,1,4,5)"/>
					  	<img id="fm_viewpageset_tabsdown_0" src="images/down.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="下移" onclick="fmTableRowsSort(this,0,4,5)"/>
					  </td>
					</tr>
				</tbody>			
			</table>
			<div style="height:4px;"></div>
			<table border="0" cellpadding="5" cellspacing="0" width="100%">
				<tr>
					<td align="center"><a class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="fmViewpagesetTabsSave()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:;"  onclick="fmViewPageSetClose()" >关闭</a></td>										
				</tr>					
			</table>
  </body>
</html>
