<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title></title>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script language="javascript" type="text/javascript">		
		
		/*
		 * 获取所有字段列表数据
		 */	
		var allDataJson = eval('(' + fieldalljson + ')');
		
		var attributeName = 'listsort';
		
		/*
		*	定义排序数据源
		*/
		var jsonData = [{name:'正序排序',value:'asc'},{name:'倒序排序',value:'desc'}];
		
		$(function(){
			$("#fm_listpageset_sort_0").combobox({
				valueField:'nametext',
   				textField:'text',
   				width: 150,
            	data: allDataJson.rows
			});
			
			
			$("#fm_listpageset_sortspagetype_0").combobox({
				valueField:'value',
				textField:'name',
				panelHeight: 'auto',
				width: 150,
				data: jsonData,
				value: 'asc'
			});
			formSortInitPage();
		});
		
		/*
		*	初始化界面
		*/
		function formSortInitPage(){
			var initXmlUtils = new XmlUtils({dataType:'json'}); 
			initXmlUtils.loadXmlString(formSettings);
			var rootNode = initXmlUtils.getRoot();
			//获取根节点
			var listSortNodes = initXmlUtils.getChildNodeByTagName(rootNode,'QueryZone');
			
			//获取根节点下的所有子节点
			var queryZoneList = initXmlUtils.getChildNodes(listSortNodes);
			//遍历所有子节点,并根据子节点创建数据
			var irow = 0;
			for(var i=0; i<queryZoneList.length; i++){
				var queryZone = queryZoneList[i];
	   				//如果有排序属性的情况下创建并执行
	   				if(queryZone.getAttribute(attributeName)!=null && queryZone.getAttribute(attributeName)!= ""){
	   					try{
							if(irow != 0){
	        					fmsortinitCreateElements('select',queryZone,irow);
	        				}else{
								var obj = $('#fm_listpageset_sort_' + irow);
								bindcombobox(allDataJson.rows, 'fm_listpageset_sort_' + irow, queryZone.getAttribute('name') + '-' + queryZone.getAttribute('tableName'), 'nametext', 'text');
								bindcombobox(jsonData, 'fm_listpageset_sortspagetype_' + irow, queryZone.getAttribute(attributeName), 'value', 'name', true);
	        				}
						}
						catch(e){ alert(e.message); }
						irow++;
					}
			}
		}
		
		/*
		* @author shuttle
		*	初始化combobox，绑定和初始化都在此完成
		*	@data json数据源
		*	@nodeId dom节点id
		*	@nodeValue dom节点要设置的值
		*	@bindValueText 要绑定的value值
		*	@bindNameText 要绑定的text值
		*	@isAutoHeight 下拉框是否根据内容自动适应
		*/
		function bindcombobox(data, nodeId, nodeValue, bindValueText, bindNameText, isAutoHeight){
			$('#' + nodeId).combobox({
				valueField: bindValueText,
				width: 150,
   				textField: bindNameText,
            	data: data,
            	onLoadSuccess: function(){$('#' + nodeId).combobox('setValue',nodeValue);}
			});
			
			if(isAutoHeight){
				$('#' + nodeId).combobox({
					panelHeight:'auto'
				});
			}
		}
		
		/*
		* 为新生成的元素绑定值并初始化页面的值
		* @nodeType <目前只支持select>
		* @nodeObj <节点>
		* @irow 动态生成的第几行
		*/
		function fmsortinitCreateElements(nodeType, nodeObj, irow){
			var tbody = $("#fm_listpageset_sortsset_tbody");	
			var cloneObject = $(tbody.find("tr").last()).clone();		
			addSortItem(cloneObject,"fm_listpageset_tabsup_","fm_listpageset_tabsdown_");
			cloneObject.appendTo(tbody);
			fmTableRowsSortImgHidden("fm_listpageset_sortsset_tbody","fm_listpageset_tabsup_","fm_listpageset_tabsdown_");
			bindcombobox(allDataJson.rows, 	'fm_listpageset_sort_' + irow, nodeObj.getAttribute('name') + '-' + nodeObj.getAttribute('tableName'),'nametext', 'text');
			bindcombobox(jsonData, 	'fm_listpageset_sortspagetype_' + irow, nodeObj.getAttribute(attributeName),'value', 'name',true);				
		}
		
		/*
		*	保存
		*/
		function fmListSortSave(){	
			/*
			*	读取全局配置文件
			*/
			var pageSetXmlUtils = new XmlUtils({dataType:'json'}); 
			pageSetXmlUtils.loadXmlString(formSettings);
			var rootNode = pageSetXmlUtils.getRoot();
			
			/*
			*	获取QueryZone下的所有子节点
			*/
			var listSortNodes = pageSetXmlUtils.getChildNodeByTagName(rootNode,'QueryZone');
			
			var queryZoneList = pageSetXmlUtils.getChildNodes(listSortNodes);
			
			//将所有节点的listSort属性设置为空
			for (var i = 0; i < queryZoneList.length; i++){
				var queryZone = queryZoneList[i];		
				queryZone.setAttribute(attributeName,'');
			}	
			
			var canSave = true;
			
			$("#fm_listpageset_sortsset_tbody tr").each(function(i,node){
			
				if(node.id != 'fm_listpageset_default_tr'){
					
					//排序字段
					var sortBy = $(node).find("td select[id^='fm_listpageset_sort_']");
					
					//排序方式
					var sortWay = $(node).find("td select[id^='fm_listpageset_sortspagetype_']");
					
					var sortByValue = sortBy.combobox("getValue");
                     
					//字段注释
					var sortByText = sortBy.combobox("getText");
					
					//字段对应的表名
					var sortTable = sortByValue.split("-")[1];
					
					//字段名称
					var sortValue = sortByValue.split("-")[0];
					
					var sortWayValue = sortWay.combobox("getValue");
					
					var sortWayText = sortWay.combobox("getText");
					
					//alert(sortByText + ' - ' + sortByValue + ' ; ' + sortWayValue + ' - ' + sortWayText);
					
					if(sortByValue == null || sortByValue == ''){
						canSave = false;
					}
					
					var sortNode;
					
					//根据表名、字段、字段值筛选节点
					for (var i = 0; i < queryZoneList.length; i++){
						var queryZone = queryZoneList[i];
						var xmlTableName = queryZone.getAttribute("tableName");
						var xmlName = queryZone.getAttribute("name");
						var xmlText = queryZone.getAttribute("text");
						
						//alert(xmlTableName+"=="+sortTable+"--"+xmlName+"=="+sortValue+"--"+xmlText+"=="+sortByText);
						
						if(xmlTableName == sortTable && xmlName == sortValue && xmlText == sortByText){
							//如果确定是这个节点，则为这个节点赋属性及其值
							pageSetXmlUtils.setAttribute(queryZone,attributeName,sortWayValue);
						}					
					}
					
					
				}
			});
			
			if(canSave){			
			//	$("#fm_formdesignlist_sort").html(CONFIGYES);
				listPageSetSubmit(pageSetXmlUtils.toString());
			}else{
				$.messager.alert("提示","请选择排序字段！", 'warning');
			}
		}
		
		/*
		*	查找控件并复制
		*/
		function fmListpagesetSortsetAdditem(){
			var tbody = $("#fm_listpageset_sortsset_tbody");	
			var cloneObject = $(tbody.find("tr").last()).clone();		
			addSortItem(cloneObject,"fm_listpageset_tabsup_","fm_listpageset_tabsdown_");
			cloneObject.appendTo(tbody);
			fmTableRowsSortImgHidden("fm_listpageset_sortsset_tbody","fm_listpageset_tabsup_","fm_listpageset_tabsdown_");
			fmListpagesetTabssetInitData(cloneObject);
		}
		
		/*
		*	生成新的控件
		*/
		function addSortItem(cloneObject,imgup,imgdown){	
			var flag = false;
			if(cloneObject.css("display") == 'none'){
				flag = true;
				cloneObject.css("display",'');
			}
			cloneObject.attr("id","");	
			var elementTd = $(cloneObject).find("td");
			for(var j=0;j<elementTd.length;j++){			
				if(j<1)
					pageSetElementSerialNumber(elementTd.eq(j).find("input"));
				else if(j==3){
					var inputSort = elementTd.eq(j).find("input");
					pageSetElementSerialNumber(inputSort);
					$(inputSort).val(parseInt($(inputSort).val())+1);
					//alert($(inputSort).val());
				}else if(j==1 || j==2){
				/**
				*	easyUI初始化后会创建一个新的对象的实例，所以这里将它移除掉，在后面fmListpagesetTabssetInitData初始化的时候会增加上
				*/
					pageSetElementSerialNumber(elementTd.eq(j).find("select"));
					elementTd.eq(j).find("span").remove();
				}else if(j==4 || j==5){
					var imgUp = elementTd.eq(j).find("img[id^='"+imgup+"']");
					var imgDown = elementTd.eq(j).find("img[id^='"+imgdown+"']");
					pageSetElementSerialNumber(imgUp);
					pageSetElementSerialNumber(imgDown);
					//hrefChild.attr("style","display:''");
					//if(flag)
						//hrefChild.linkbutton();
				}
			}	
		}
		
		
		/*
		*	为新生成的控件绑定值
		*/
		function fmListpagesetTabssetInitData(nodeObj){
			var tabsPageUrlSelect = $(nodeObj).find("td select[id^='fm_listpageset_sort_']");
			tabsPageUrlSelect.combobox({
				valueField:'nametext',
   				textField:'text',
   				width: 150,
            	data: allDataJson.rows
			});
			
			var sortMethod = $(nodeObj).find("td select[id^='fm_listpageset_sortspagetype_']");
			sortMethod.combobox({
				valueField:'value',
				textField:'name',
				width: 150,
				panelHeight: 'auto',
				data: jsonData,
				value: 'asc'
			});
		}
    </script>
	</head>
	<body>
		<table border="0" cellpadding="1" cellspacing="1" width="100%"
			style="background: #dedede;">
			<tr style="background: #ffffff;">
				<td style="background: #eaeaea;" colspan="6">
					<a href="javascript:;" class="easyui-linkbutton" plain="true"
						icon="icon-add" onclick="fmListpagesetSortsetAdditem()">增加</a>
				</td>
			</tr>
			<tr style="background: #eeeeee; height: 24px;">
				<td width="15%" align="center" style="display: none;">
					编号
				</td>
				<td width="40%" align="center">
					排序字段
				</td>
				<td width="40%" align="center">
					排序方式
				</td>
				<td width="10%" style="display: none;">
					序号
				</td>
				<td align="center">
					操作
				</td>
			</tr>
			<tbody id="fm_listpageset_sortsset_tbody">
				<tr style="background: #ffffff; display: none;"
					id="fm_listpageset_default_tr">
					<td align="center" style="display: none;">
						<input type="text" id="fm_listpageset_sortsid_-1" size="14" />
					</td>
					<!-- <td align="center"><input type="text" id="fm_listpageset_tabstitle_-1" size="20" /></td>
					  <td align="center"><input type="checkbox" id="fm_listpageset_tabsislazy_-1"/></td> -->
					<td align="center">
						<select id="fm_listpageset_sort_-1"></select>
					</td>
					<td align="center">
						<select id="fm_listpageset_sortspagetype_-1"></select>
					</td>
					<td style="display: none;">
						<input type="text" id="fm_listpageset_sortssort_-1" size="5"
							value="0" />
					</td>
					<td align="center">
						<!-- <a id="fm_listpageset_tabsdelete_-1" href="javascript:;" plain="true" icon="icon-remove" onclick="fmPagesetDynamicDeleteItem(this)">删除</a> -->
						<img src="images/ioc-delete.gif"
							style="cursor: hand; margin: 2px 2px 0px 4px;" title="删除"
							onclick="fmPagesetSortDynamicDeleteItem(this,'fm_listpageset_sortsset_tbody','fm_listpageset_tabsup_','fm_listpageset_tabsdown_')" />
						<img id="fm_listpageset_tabsup_-1" src="images/up.gif"
							style="cursor: hand; margin: 2px 2px 0px 2px; display: none;"
							title="上移" onclick="fmTableRowsSort(this,1,4,5)" />
						<img id="fm_listpageset_tabsdown_-1" src="images/down.gif"
							style="cursor: hand; margin: 2px 2px 0px 2px; display: none;"
							title="下移" onclick="fmTableRowsSort(this,0,4,5)" />
					</td>
				</tr>
				<tr style="background: #ffffff;">
					<td align="center" style="display: none;">
						<input type="text" id="fm_listpageset_sortsid_0" size="14" />
					</td>
					<!--  <td align="center"><input type="text" id="fm_listpageset_tabstitle_0" size="20" /></td>
					  <td align="center"><input type="checkbox" id="fm_listpageset_tabsislazy_0"/></td>-->
					<td align="center">
						<select id="fm_listpageset_sort_0"></select>
					</td>
					<td align="center">
						<select id="fm_listpageset_sortspagetype_0"></select>
					</td>
					<td style="display: none;">
						<input type="text" id="fm_listpageset_sortssort_0" size="5"
							value="1" />
					</td>
					<td align="center">
						<img src="images/ioc-delete.gif"
							style="cursor: hand; margin: 2px 2px 0px 4px;" title="删除"
							onclick="fmPagesetSortDynamicDeleteItem(this,'fm_listpageset_sortsset_tbody','fm_listpageset_tabsup_','fm_listpageset_tabsdown_')" />
						<img id="fm_listpageset_tabsup_-1" src="images/up.gif"
							style="cursor: hand; margin: 2px 2px 0px 2px; display: none;"
							title="上移" onclick="fmTableRowsSort(this,1,4,5)" />
						<img id="fm_listpageset_tabsdown_-1" src="images/down.gif"
							style="cursor: hand; margin: 2px 2px 0px 2px; display: none;"
							title="下移" onclick="fmTableRowsSort(this,0,4,5)" />
					</td>
				</tr>
			</tbody>
		</table>
		<div style="height: 4px;"></div>
		<table border="0" cellpadding="5" cellspacing="0" width="100%">
			<tr>
				<td align="center">
					<a class="easyui-linkbutton" icon="icon-save" href="javascript:;"
						onclick="fmListSortSave()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:;"
						onclick="fmListPageSetClose()">关闭</a>
				</td>
			</tr>
		</table>
	</body>
</html>
