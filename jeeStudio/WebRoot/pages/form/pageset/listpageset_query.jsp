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
			$('#fm_listpageset_queryset_tableshow').datagrid({
                iconCls: 'icon-save',
               	width:760,
                nowrap: false,
                striped: true,
                autoFit: true,
                sortOrder: 'asc',
                singleSelect:true,
                idField: 'name',
				//rownumbers: true,
                columns: [[
               	 	{field:'sortIndex',title:'序号',width:30,sortable:true,
	                	formatter:function(value,rec,rowIndex){  
	                		rec.sortIndex = parseInt(rowIndex)+1;          			
							return rec.sortIndex;
						}},
                	{field: 'name',title: '字段',width: 100,sortable: true,align:'left'},                	
                	{field: 'text',title: '标题',width: 100,align:'left'},                	
                	{field: 'textalign',title: '对齐方式',width: 80,align:'left',
                		formatter:function(value){
                			if (value == '-1') return "";
                			for(var i=0; i<fmPagesetDatacolumnAlignData.length; i++){
								if (fmPagesetDatacolumnAlignData[i].id == value) return fmPagesetDatacolumnAlignData[i].text;
							}
							return value;
						}
                	},                	
                	{field: 'fromdic',title: '来自数据字典',width: 100,align:'left',formatter:function(value){
                			if (value == '') return "无";
                			return value;
                		}},                	
                	{field: 'dicname',title: '数据字典名称',width: 100,align:'left',
						formatter:function(value){
                			if (value == '' || value == '-1') return "无";
                			for(var i=0; i<fmPagesetDataDictionary.length; i++){
								var children = fmPagesetDataDictionary[i].children;
                				for(var j=0;j<children.length;j++){
                					if (children[j].id == value) return children[j].text;
                				}
								//if (fmPagesetDataDictionary[i].id == value) return fmPagesetDataDictionary[i].text;
							}
							return value;
						}},                	
                	{field: 'operate',title: '操作',width: 160,align:'center',
                		formatter:function(value,rec,rowIndex){  
                			var rowData="";
                			for(var k in rec){
                				rowData += rowData==""?"{'"+k+"':'"+rec[k]+"'":",'"+k+"':'"+rec[k]+"'";	
                			}      
                			rowData+="}";                			         			
                			var links = '<a href="javascript:;" onclick="fmListpagesetQuerysetConfig('+rowIndex+')">配置</a>&nbsp;|&nbsp;';
                			links += '<a href="javascript:;" onclick=fmPagesetDataGridColumnShowHidden("fm_listpageset_queryset_tableshow","fm_listpageset_queryset_tablehidden",'+rowIndex+')>隐藏</a>'
                			var data = $('#fm_listpageset_queryset_tableshow').datagrid('getData');
                			if((data['total']-1)>0){  
                				links += '&nbsp;|&nbsp;';
	                			if(rowIndex == 0){
	                				links += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;<img src="images/down.gif" style="cursor:hand;" onclick=fmPagesetDataGridColumnSort("down","fm_listpageset_queryset_tableshow",'+rowIndex+',"'+rowData+'") title="下移"></img>';
	                			}else if(rowIndex == (data['total']-1)){
	                				links += '<img src="images/up.gif" style="cursor:hand;" onclick=fmPagesetDataGridColumnSort("up","fm_listpageset_queryset_tableshow",'+rowIndex+',"'+rowData+'") title="上移"></img>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	                			}else{
	                				links += '<img src="images/up.gif" style="cursor:hand;" onclick=fmPagesetDataGridColumnSort("up","fm_listpageset_queryset_tableshow",'+rowIndex+',"'+rowData+'") title="上移"></img>&nbsp;|&nbsp;';
	                				links += '<img src="images/down.gif" style="cursor:hand;" onclick=fmPagesetDataGridColumnSort("down","fm_listpageset_queryset_tableshow",'+rowIndex+',"'+rowData+'") title="下移"></img>';  
	                			}
	                		}
							return links;
                		}
                	}
				]],
				onBeforeLoad:function(){
					$(this).datagrid('rejectChanges');
				}
            });
			$('#fm_listpageset_queryset_tablehidden').datagrid({
                iconCls: 'icon-save',
               	width:760,
                nowrap: false,
                striped: true,
                autoFit: true,
                sortOrder: 'asc',
                singleSelect:true,
                idField: 'name',
				//rownumbers: true, 
                columns: [[
                	{field:'sortIndex',title:'序号',width:30,
	                	formatter:function(value,rec,rowIndex){  
	                		rec.sortIndex = parseInt(rowIndex)+1;          			
							return rec.sortIndex;
						}},
                	{field: 'name',title: '字段',width: 100,sortable: true,align:'left'},                	
                	{field: 'text',title: '标题',width: 100,align:'left'},                	
                	{field: 'textalign',title: '对齐方式',width: 80,align:'left',formatter:function(value){
                			if (value == '-1') return "";
                			for(var i=0; i<fmPagesetDatacolumnAlignData.length; i++){
								if (fmPagesetDatacolumnAlignData[i].id == value) return fmPagesetDatacolumnAlignData[i].text;
							}
							return value;
						}},                	
                	{field: 'fromdic',title: '来自数据字典',width: 100,align:'left',formatter:function(value){
                			if (value == '') return "无";
                			return value;
                		}},                	
                	{field: 'dicname',title: '数据字典名称',width: 100,align:'left',formatter:function(value){
                			if (value == '' || value == '-1') return "无";
                			for(var i=0; i<fmPagesetDataDictionary.length; i++){
								var children = fmPagesetDataDictionary[i].children;
                				for(var j=0;j<children.length;j++){
                					if (children[j].id == value) return children[j].text;
                				}
								//if (fmPagesetDataDictionary[i].id == value) return fmPagesetDataDictionary[i].text;
							}
							return value;
						}},                	
                	{field: 'operate',title: '操作',width: 120,align:'center',
                		formatter:function(value,rec,rowIndex){                 			         			
                			var links = '<a href="javascript:;" onclick=fmPagesetDataGridColumnShowHidden("fm_listpageset_queryset_tablehidden","fm_listpageset_queryset_tableshow",'+rowIndex+')>显示</a>';
							return links;
                		}
                	}
				]],
				onBeforeLoad:function(){
					$(this).datagrid('rejectChanges');
				}
				
            });
            //组合查询
			$('#fm_listpageset_queryset_tableshow').datagrid('loadData',eval('('+queryshowjson+')'));
	        $('#fm_listpageset_queryset_tablehidden').datagrid('loadData',eval('('+queryhiddenjson+')'));
	        var initXmlUtils = new XmlUtils({dataType:'json'}); 
			initXmlUtils.loadXmlString(formSettings);
			var rootNode = initXmlUtils.getRoot();
	        var listQueryZoneNode = initXmlUtils.getChildNodeByTagName(rootNode,'QueryZone');	
			if(listQueryZoneNode && (listQueryZoneNode.getAttribute('visible')==true || listQueryZoneNode.getAttribute('visible') == 'true')){
				$('#fm_listpageset_queryset_isshow').attr('checked',true);
			}else{
				$('#fm_listpageset_queryset_isshow').attr('checked',false);
			}
			$('#fm_listpageset_qs_dictionary_window').appendTo('body').window({
				title: '选择数据字典',
				width: 400,
				modal: true,
				shadow: false,
				closed: true,
				minimizable: false,
				collapsible:false,
				height: 500
			});
			$('#fm_listpageset_qs_dictionary_tree').tree({
				//checkbox: true,
				//url: 'grid/tree_data.json',
				data:fmPagesetDataDictionary,
				onClick:function(node){
					if($('#fm_listpageset_qs_dictionary_tree').tree("isLeaf",node.target)){
						$('#fm_listpageset_qs_dictionary_id').val(node.id);
						$('#fm_listpageset_qs_dictionary_text').val(node.text);
						$('#fm_listpageset_qs_dictionary_window').window('close');
						//alert('you click '+node.text);
					}
				}
			});
		});		
		function fmListpagesetQuerysetSave(){/*保存列表页的页面配置*/
			var querySetXmlUtils = new XmlUtils({dataType:'json'}); 
			querySetXmlUtils.loadXmlString(formSettings);
			var root = querySetXmlUtils.getRoot();
			var queryZoneNode = querySetXmlUtils.getChildNodeByTagName(root,'QueryZone');	
			
			if($('#fm_listpageset_queryset_isshow').attr('checked')){
				querySetXmlUtils.setAttribute(queryZoneNode,'visible','true');
			}else{
				querySetXmlUtils.setAttribute(queryZoneNode,'visible','false');
			}
			querySetXmlUtils.removeChildNodes(queryZoneNode);
			var tableShowRows = $("#fm_listpageset_queryset_tableshow").datagrid("getRows");
			var tableHiddenRows = $("#fm_listpageset_queryset_tablehidden").datagrid("getRows");
			if(tableShowRows)
				for(var i=0;i<tableShowRows.length;i++){
					var queryColumnNodeAttr = {id:'',
						type:tableShowRows[i]['type']=="-1"?"":tableShowRows[i]['type'],
						tableName:tableShowRows[i]['tableName'],
						name:tableShowRows[i]['name'],
						text:tableShowRows[i]['text'],
						fieldDataType:tableShowRows[i]['fieldDataType'],
						visible:'true',
						readOnly:'false',
						cssClass:'',
						sortIndex:tableShowRows[i]['sortIndex'],
						dictionaryId:tableShowRows[i]['dicname']=="-1"?"":tableShowRows[i]['dicname'],
						formula:tableShowRows[i]['formula']=="-1"?"":tableShowRows[i]['formula'],
						dateformat:tableShowRows[i]['dateformat']=="0"?"":tableShowRows[i]['dateformat'],/*日期格式*/
						align:tableShowRows[i]['textalign']=="-1"?"":tableShowRows[i]['textalign'],
						exclusiveLine:tableShowRows[i]['exclusiveLine'],
						operateType:tableShowRows[i]['operateType']=="-1"?"":tableShowRows[i]['operateType'],
						style:''
					};
					var queryColumnNode = querySetXmlUtils.createNode('QueryColumn','',queryColumnNodeAttr,queryZoneNode);
					var editModeNodeAttr = {id:'',
						data:"",
						reminder:"",
						validateRule:tableShowRows[i]['validateRule']=="-1"?"":tableShowRows[i]['validateRule']
					};
					querySetXmlUtils.createNode('EditMode','',editModeNodeAttr,queryColumnNode);
					
				}
			if(tableHiddenRows)
				for(var i=0;i<tableHiddenRows.length;i++){
					var queryColumnNodeAttr = {id:'',
						type:tableHiddenRows[i]['type']=="-1"?"":tableHiddenRows[i]['type'],
						tableName:tableHiddenRows[i]['tableName'],
						name:tableHiddenRows[i]['name'],
						text:tableHiddenRows[i]['text'],
						fieldDataType:tableHiddenRows[i]['fieldDataType'],
						visible:'false',
						readOnly:'false',
						cssClass:'',
						sortIndex:tableHiddenRows[i]['sortIndex'],
						dictionaryId:tableHiddenRows[i]['dicname']=="-1"?"":tableHiddenRows[i]['dicname'],
						formula:tableHiddenRows[i]['formula']=="-1"?"":tableHiddenRows[i]['formula'],
						dateformat:tableHiddenRows[i]['dateformat']=="0"?"":tableHiddenRows[i]['dateformat'],/*日期格式*/
						align:tableHiddenRows[i]['textalign']=="-1"?"":tableHiddenRows[i]['textalign'],
						exclusiveLine:tableHiddenRows[i]['exclusiveLine'],
						operateType:tableHiddenRows[i]['operateType']=="-1"?"":tableHiddenRows[i]['operateType'],
						style:''
					};
					var queryColumnNode = querySetXmlUtils.createNode('QueryColumn','',queryColumnNodeAttr,queryZoneNode);
					var editModeNodeAttr = {id:'',
						data:"",
						reminder:"",
						validateRule:tableHiddenRows[i]['validateRule']=="-1"?"":tableHiddenRows[i]['validateRule']
					};
					querySetXmlUtils.createNode('EditMode','',editModeNodeAttr,queryColumnNode);
				}
				
			querySetXmlUtils.setAttribute(queryZoneNode,'isConfig','1');
			$("#fm_formdesignlist_query").html(CONFIGYES);
       		listPageSetSubmit(querySetXmlUtils.toString());
		}
		function fmListpagesetQuerysetConfig(rowIndex){/*读出配置信息*/
			$('#fm_listpageset_queryset_tableshow').datagrid("clearSelections");
			$('#fm_listpageset_queryset_tableshow').datagrid("selectRow",rowIndex);
			var row = $('#fm_listpageset_queryset_tableshow').datagrid("getSelected");			
			$('#fm_listpageset_qs_fieldname').html(row.name);
			$('#fm_listpageset_qs_fieldindex').val(rowIndex);
			$('#fm_listpageset_qs_fieldtitle').val(row.text);
			
			$('#fm_listpageset_qs_tagtype').combobox({
				valueField:'id',
				textField:'text',
				editable:false,
				data:COM_QUERY_TYPE,
				onSelect:function (record){
					changeExtendedAttributes('extended-attributes-query',record.id+'_extendAttributes_query');
					var val = $('#fm_listpageset_qs_tagtype').combobox('getValue');
					if(val=="4"){
					 	$("#date_format_div").show();
					}else{
						$("#date_format_div").hide();
					}
				}
			});
			$('#fm_listpageset_qs_formula').combobox({
				valueField:'id',
				textField:'text',
				editable:false,
				data:fmPagesetFormulaData
			});
			/**$('#fm_listpageset_qs_dictionary').combotree({
				valueField:'id',
				textField:'text',
				treeWidth:148,
				data:fmPagesetDataDictionary
			});*/
			$('#fm_listpageset_qs_validation').combobox({
				valueField:'id',
				textField:'text',
				editable:false,
				data:fmPagesetValidationRuleData
			});
			$('#fm_listpageset_qs_operate').combobox({
				valueField:'id',
				textField:'text',
				editable:false,
				data:fmSelecttableJoinOperData
			});
			$('#fm_listpageset_qs_textalign').combobox({
				valueField:'id',
				textField:'text',
				editable:false,
				data:fmPagesetDatacolumnAlignData
			});
			if(row.operateType != null && row.operateType != ""){/*条件操作符*/
				$('#fm_listpageset_qs_operate').combobox("setValue",row.operateType);
			}else{
				$('#fm_listpageset_qs_operate').combobox("setValue",'-1');
			}
			if(row.type != null && row.type != ""){/*标签类型*/
				$('#fm_listpageset_qs_tagtype').combobox("setValue",row.type);
			}else{
				$('#fm_listpageset_qs_tagtype').combobox("setValue",'-1');
			}
			if(row.formula != null && row.formula != ""){
				$('#fm_listpageset_qs_formula').combobox("setValue",row.formula);
			}else{
				$('#fm_listpageset_qs_formula').combobox("setValue",'-1');
			}
			if(row.textalign != null && row.textalign != ""){
				$('#fm_listpageset_qs_textalign').combobox("setValue",row.textalign);
			}else{
				$('#fm_listpageset_qs_textalign').combobox("setValue",'-1');
			}
			if(row.validateRule != null && row.validateRule != ""){
				$('#fm_listpageset_qs_validation').combobox("setValue",row.validateRule);
			}else{
				$('#fm_listpageset_qs_validation').combobox("setValue",'-1');
			}
			if(row.type=="4"){/*当标签类型为“日历控件”时，日期格式才显示*/
				$("#date_format_div").show();
			}else{
				$("#date_format_div").hide();
			}
			
			if(row.dateformat != null && row.dateformat != ""){/*日期格式*/
				$('#fm_listpageset_dateformat').combobox("setValue",row.dateformat);
			}else{
				$('#fm_listpageset_dateformat').combobox("setValue",'0');
			}
			if(row.dicname != null && row.dicname != ""){/*数据字典*/
				//$('#fm_listpageset_qs_dictionary').combotree("setValue",row.dicname);
				$('#fm_listpageset_qs_dictionary_id').val(row.dicname);
				$('#fm_listpageset_qs_dictionary_text').val($('#fm_listpageset_qs_dictionary_tree').tree('find',row.dicname).text);
			}else{
				//$('#fm_listpageset_qs_dictionary').combotree("setValue",{id:'-1',text:'无'});
				$('#fm_listpageset_qs_dictionary_id').val('-1');
				$('#fm_listpageset_qs_dictionary_text').val('无');
			}
			if(row.exclusiveLine == true || row.exclusiveLine == 'true')
				$(":checkbox[id='fm_listpageset_dc_isline']").attr("checked",true);
			else
				$(":checkbox[id='fm_listpageset_dc_isline']").attr("checked",false);
			$('#fm_listpageset_queryset_config').window('open');
		}
		function fmListpagesetQuerysetConfigSubmit(){/*保存*/
			var row = $('#fm_listpageset_queryset_tableshow').datagrid("getSelected");
			row.text = $('#fm_listpageset_qs_fieldtitle').val();
			row.type = $('#fm_listpageset_qs_tagtype').combobox("getValue");
			row.formula = $('#fm_listpageset_qs_formula').combobox("getValue");
			row.operateType = $('#fm_listpageset_qs_operate').combobox("getValue");
			row.dateformat = $('#fm_listpageset_dateformat').combobox("getValue");  /*日期格式*/
			row.dicname = $('#fm_listpageset_qs_dictionary_id').val();
			row.fromdic = "无";
			if($('#fm_listpageset_qs_dictionary_id').val() != '-1')
				row.fromdic = "是";			
			row.validateRule = $('#fm_listpageset_qs_validation').combobox("getValue");
			row.textalign = $('#fm_listpageset_qs_textalign').combobox("getValue");	
			row.exclusiveLine = $(":checkbox[id='fm_listpageset_dc_isline']").attr("checked")?"true":"false";		
			$('#fm_listpageset_queryset_tableshow').datagrid("refreshRow",$('#fm_listpageset_qs_fieldindex').val());
			$('#fm_listpageset_queryset_config').window('close');
			$('#fm_listpageset_queryset_tableshow').datagrid('resize');
		}
		function fmListpagesetQsDictionaryWindow(){
			$('#fm_listpageset_qs_dictionary_tree').tree('select',$('#fm_listpageset_qs_dictionary_tree').tree('find',$('#fm_listpageset_qs_dictionary_id').val()).target);
			$('#fm_listpageset_qs_dictionary_window').window('open');
		}
    </script>
  </head>  
  <body>
	<table width="100%"  border="0" cellpadding="5" cellspacing="0">
	  <tr><td><input type="checkbox" id="fm_listpageset_queryset_isshow"/>显示</td>
	  </tr>
	</table>
	<div class="easyui-panel" title="显示字段" collapsible="false" style="width:765px;" headerCls="header_cls">
		<table id="fm_listpageset_queryset_tableshow" width="100%"></table>
	</div>
	<div style="height:4px;"></div>
	<div class="easyui-panel" title="隐藏字段" collapsible="false" style="width:765px;" headerCls="header_cls">
		<table id="fm_listpageset_queryset_tablehidden" width="100%"></table>
	</div>
	<div style="height:4px;"></div>
	<table border="0" cellpadding="5" cellspacing="0" width="100%">
		<tr>
			<td align="center"><a class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="fmListpagesetQuerysetSave()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:;"  onclick="fmListPageSetClose()" >关闭</a></td>										
		</tr>					
	</table>
	<div id="fm_listpageset_qs_dictionary_window" class="easyui-window" style="padding:5px;background: #fafafa;">		
		<input type="text" onkeyup="fmDataDictionaryShowKeyUp(event,this,'fm_listpageset_qs_dictionary_tree');">
		<ul id="fm_listpageset_qs_dictionary_tree"></ul>		
 	</div>
  </body>
</html>
