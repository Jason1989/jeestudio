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
			var lastIndex;
			$('#fm_listpageset_datacolumnset_tableshow').datagrid({
                iconCls: 'icon-save',
               	width:770,
                nowrap: false,
                striped: true,
                border:false,
                fit: true,
                fitColumns:true,
                //sortOrder: 'desc',
                sortOrder: 'asc',
                sortName:'sortIndex',
                idField: 'name',
                //singleSelect:true,
                //remoteSort: false,
				//rownumbers: true,
                columns: [[                	          	
	                {field:'sortIndex',title:'序号',width:30,sortable:true,
	                	formatter:function(value,rec,rowIndex){  
	                		rec.sortIndex = parseInt(rowIndex)+1;          			
							return rec.sortIndex;
						}},
                	{field: 'name',title: '字段',width: 100,align:'left'},                	
                	{field: 'title',title: '标题',width: 100,align:'left',editor:{type:'text',options:{precision:1}}},                	
                	{field: 'textalign',title: '对齐方式',width: 80,align:'left',
                		formatter:function(value){
               				if (value == '-1') return "";
                			for(var i=0; i<fmPagesetDatacolumnAlignData.length; i++){
								if (fmPagesetDatacolumnAlignData[i].id == value) return fmPagesetDatacolumnAlignData[i].text;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'id',
								textField:'text',
								data:fmPagesetDatacolumnAlignData
							}
						}
                	},                	
                	{field: 'fromdic',title: '来自数据字典',width: 100,align:'left',formatter:function(value){
                			if (value == '') return "无";
                			return value;
                		},
                		editor:{
							type:'checkbox',
							options:{
								on: '是',
								off: '否'
							}
						}},                	
                	{field: 'dicname',title: '数据字典名称',width: 100,align:'left',
						formatter:function(value){
							if (value == '' || value == '-1') return "无";
                			for(var i=0; i<fmPagesetDataDictionary.length; i++){
                				var children = fmPagesetDataDictionary[i].children;
                				for(var j=0;j<children.length;j++){
                					if (children[j].id == value) return children[j].text;
                				}								
							}
                			//for(var i=0; i<fmPagesetDataDictionary.length; i++){
							//	if (fmPagesetDataDictionary[i].id == value) return fmPagesetDataDictionary[i].text;
							//}
							return value;
						},
						editor:{
							type:'combotree',
							options:{
								valueField:'id',
								textField:'text',
								data:fmPagesetDataDictionary
							}
						}
					},                	
                	{field: 'operate',title: '操作',width: 150,align:'center',
                		formatter:function(value,rec,rowIndex){
                			var rowData="";
                			for(var k in rec){
                				rowData += rowData==""?"{'"+k+"':'"+rec[k]+"'":",'"+k+"':'"+rec[k]+"'";	
                			}      
                			rowData+="}"; 
                			//alert(rowData);          			         			
                			var links = '<a href="javascript:;" onclick=fmListpagesetDatacolumnsetEdit('+rowIndex+')>配置</a>&nbsp;|&nbsp;';
                			links += '<a href="javascript:;" onclick=fmPagesetDataGridColumnShowHidden("fm_listpageset_datacolumnset_tableshow","fm_listpageset_datacolumnset_tablehidden",'+rowIndex+')>隐藏</a>';
               				var data = $('#fm_listpageset_datacolumnset_tableshow').datagrid('getData');
                			if((data['total']-1)>0){  
                				links += '&nbsp;|&nbsp;';              				
	                			if(rowIndex == 0){
	                				links += '&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;<img src="images/down.gif" style="cursor:hand;" onclick=fmPagesetDataGridColumnSort("down","fm_listpageset_datacolumnset_tableshow",'+rowIndex+',"'+rowData+'") title="下移"></img>';
	                			}else if(rowIndex == (data['total']-1)){
	                				links += '<img src="images/up.gif" style="cursor:hand;" onclick=fmPagesetDataGridColumnSort("up","fm_listpageset_datacolumnset_tableshow",'+rowIndex+',"'+rowData+'") title="上移"></img>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	                			}else{                			
	                				links += '<img src="images/up.gif" style="cursor:hand;" onclick=fmPagesetDataGridColumnSort("up","fm_listpageset_datacolumnset_tableshow",'+rowIndex+',"'+rowData+'") title="上移"></img>&nbsp;|&nbsp;';
	                				links += '<img src="images/down.gif" style="cursor:hand;" onclick=fmPagesetDataGridColumnSort("down","fm_listpageset_datacolumnset_tableshow",'+rowIndex+',"'+rowData+'") title="下移"></img>';  
	                			}
                			}
							return links;
                		}
                	}
				]],
				onBeforeLoad:function(){
					$(this).datagrid('rejectChanges');
				}/**,
				onClickRow:function(rowIndex){
					if (lastIndex != rowIndex){
						$('#fm_listpageset_datacolumnset_tableshow').datagrid('endEdit', lastIndex);
					}
				},
				onDblClickRow:function(rowIndex){
					$('#fm_listpageset_datacolumnset_tableshow').datagrid('beginEdit', rowIndex);
					lastIndex = rowIndex;
					$('#fm_listpageset_datacolumnset_tableshow').datagrid('resize');
				}*/
            });
			$('#fm_listpageset_datacolumnset_tablehidden').datagrid({
                iconCls: 'icon-save',
               	width:770,
                nowrap: false,
                striped: true,
                fit: true,
                fitColumns:true,
                border:false,
                sortOrder: 'asc',
                sortName:'sortIndex',
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
                	{field: 'title',title: '标题',width: 100,align:'left'},                	
                	{field: 'textalign',title: '对齐方式',width: 80,align:'left',formatter:function(value){
                			if (value == '-1') return "";
                			for(var i=0; i<fmPagesetDatacolumnAlignData.length; i++){
								if (fmPagesetDatacolumnAlignData[i].id == value) return fmPagesetDatacolumnAlignData[i].text;
							}
							return value;
						}},                	
                	{field: 'fromdic',title: '来自数据字典',width: 100,align:'left',formatter:function(value){
                			if (value == '') return "否";
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
                			var links = '<a href="javascript:;" onclick=fmPagesetDataGridColumnShowHidden("fm_listpageset_datacolumnset_tablehidden","fm_listpageset_datacolumnset_tableshow",'+rowIndex+')>显示</a>';
							return links;
                		}
                	}
				]]
				
            });
            
            $('#fm_listpageset_datacolumnset_tableshow').datagrid('loadData',eval('('+fieldshowjson+')'));
	        $('#fm_listpageset_datacolumnset_tablehidden').datagrid('loadData',eval('('+fieldhiddenjson+')'));
	        $('#fm_listpageset_dc_edit').window({
				title: '数据列配置',
				width: 660,
				modal: true,
				shadow: false,
				closed: true,
				minimizable: false,
				collapsible:false,
				height: 340
			});
	        $('#fm_listpageset_dc_dictionary_window').appendTo('body').window({
				title: '选择数据字典',
				width: 400,
				modal: true,
				shadow: false,
				closed: true,
				minimizable: false,
				collapsible:false,
				height: 500
			});
			$('#fm_listpageset_dc_dictionary_tree').tree({
				//checkbox: true,
				//url: 'grid/tree_data.json',
				data:fmPagesetDataDictionary,
				onClick:function(node){
					if($('#fm_listpageset_dc_dictionary_tree').tree("isLeaf",node.target)){
						$('#fm_listpageset_dc_dictionary_id').val(node.id);
						$('#fm_listpageset_dc_dictionary_text').val(node.text);
						$('#fm_listpageset_dc_dictionary_window').window('close');
						//alert('you click '+node.text);
					}
				}
			});
			
			//加载数据列配置的页面类型和页面
			
			//页面
			$("#tabspageurl_listPage_column").combobox({
				valueField:'id',
   				textField:'text',  
            	treeWidth:157,
            	panelHeight:160,
			    url:'form/form!getTabsPageList.action?mainObjectId=' + mainObjectId + '&pageType=listPage&random='+parseInt(10*Math.random())
			});
			
			//页面类型
			$("#tabspagetype_listPage_column").combobox({
				valueField:'value',
   				textField:'text',
   				data:[{value:'listPage',text:'列表页',selected:true},{value:'editPage',text:'编辑页'},{value:'viewPage',text:'查看页'}],
				onChange:function(newValue,oldValue){			
					$("#tabspageurl_listPage_column").combobox("setValue","");
					$("#tabspageurl_listPage_column").combobox({						
						url:'form/form!getTabsPageList.action?mainObjectId=' + mainObjectId + '&pageType=' + newValue+ '&random='+parseInt(10*Math.random())
					});	
				}
			});
			
			//切换radio 
			$("input[name=isJS_listPage_column]").click(function(){
				if($(this).val() =='jiekou'){
					$("#jiekoudiv_listpage").css('display','block');
					$("#zidingyidiv_listpage").css('display','none');
				}else{
					$("#jiekoudiv_listpage").css('display','none');
					$("#zidingyidiv_listpage").css('display','block');
				}
			
			})
		});		
		function fmListpagesetDatacolumnsetEdit(rowIndex){
			$('#fm_listpageset_datacolumnset_tableshow').datagrid("clearSelections");
			$('#fm_listpageset_datacolumnset_tableshow').datagrid("selectRow",rowIndex);
			var row = $('#fm_listpageset_datacolumnset_tableshow').datagrid("getSelected");		
			$('#fm_listpageset_dc_fieldname').html(row.name);
			$('#fm_listpageset_dc_fieldindex').val(rowIndex);
			$('#fm_listpageset_dc_fieldtitle').val(row.title);
			
			$('#fm_listpageset_dc_dataformat').combobox({
				valueField:'id',
				textField:'text',
				editable:false,
				data:fmPagesetDataFormatData
			});
			$('#fm_listpageset_dc_formula').combobox({
				valueField:'id',
				textField:'text',
				editable:false,
				data:fmPagesetFormulaData
			});
			$('#fm_listpageset_dc_align').combobox({
				valueField:'id',
				textField:'text',
				editable:false,
				data:fmPagesetDatacolumnAlignData
			});
			/**
			$('#fm_listpageset_dc_dictionary').combotree({
				valueField:'id',
				textField:'text',
				treeWidth:149,
				data:fmPagesetDataDictionary
			});*/		
			if(row.dataFormat != null && row.dataFormat != ""){
				$('#fm_listpageset_dc_dataformat').combobox("setValue",row.dataFormat);
			}else{
				$('#fm_listpageset_dc_dataformat').combobox("setValue",'-1');
			}
			if(row.formula != null && row.formula != ""){
				$('#fm_listpageset_dc_formula').combobox("setValue",row.formula);
			}else{
				$('#fm_listpageset_dc_formula').combobox("setValue",'-1');
			}
			if(row.textalign != null && row.textalign != ""){
				$('#fm_listpageset_dc_align').combobox("setValue",row.textalign);
			}else{
				$('#fm_listpageset_dc_align').combobox("setValue",'-1');
			}
			if(row.dicname != null && row.dicname != ""){
				//$('#fm_listpageset_dc_dictionary').combotree("setValue",row.dicname);
				//GUOWEIXIN 解决：数据字典如果被删除后，列表 ->列 无法打开
				$('#fm_listpageset_dc_dictionary_id').val(row.dicname);
				if($('#fm_listpageset_dc_dictionary_text').tree('find',row.dicname)==null){
					$('#fm_listpageset_dc_dictionary_id').val('-1');
					$('#fm_listpageset_dc_dictionary_text').val('无');
				}else{
					$('#fm_listpageset_dc_dictionary_text').val($('#fm_listpageset_dc_dictionary_tree').tree('find',row.dicname).text);
				}
				
			}else{
				//$('#fm_listpageset_dc_dictionary').combotree("setValue",{id:'-1',text:'无'});
				$('#fm_listpageset_dc_dictionary_id').val('-1');
				$('#fm_listpageset_dc_dictionary_text').val('无');
			}
			
			if(row.columnrules!= null &&row.columnrules!=""){
				var columnrules = eval("("+decodeURIComponent(row.columnrules)+")");
				if('zidingyi' == columnrules.isJS){
					$("#jiekoudiv").css('display','none');
					$("#zidingyidiv").css('display','block');
				}else if('jiekou' ==columnrules.isJS){
					$("#jiekoudiv_listpage").css('display','block');
					$("#zidingyidiv_listpage").css('display','none');
					
					$('#tabspagetype_listPage_column').combobox('setValue',columnrules.tabspagetype);
					$('#tabspageurl_listPage_column').combobox('setValue',columnrules.tabspageurl);
					$('#title_listPage_column').val(columnrules.tabstitle);
					$('#params_listPage_column').val(columnrules.params);
				}else{
					columnrules.isJS = 'zidingyi';
				}
				
				$("input[name=isJS_listPage_column]").each(function(){
					if($(this).val() == columnrules.isJS){
						$(this).attr('checked',true);
					}else{
						$(this).attr('checked',false);
					}
				})
			}else{
				$("#jiekoudiv_listpage").css('display','none');
				$("#zidingyidiv_listpage").css('display','block');
				$("input[name=isJS_listPage_column]").each(function(){
					if($(this).val() == 'zidingyi'){
						$(this).attr('checked',true);
					}else{
						$(this).attr('checked',false);
					}
				})
			}
			
			$('#fm_listpageset_dc_save').linkbutton();
			$('#fm_listpageset_dc_cancel').linkbutton();
			$('#fm_listpageset_dc_edit').window('open');
			var row = $('#fm_listpageset_datacolumnset_tableshow').datagrid("getSelected");	
		}
		function fmListpagesetDcSubmit(){
			var rowIndex = $('#fm_listpageset_dc_fieldindex').val();
			$('#fm_listpageset_datacolumnset_tableshow').datagrid("clearSelections");
			$('#fm_listpageset_datacolumnset_tableshow').datagrid("selectRow",rowIndex);
			var row = $('#fm_listpageset_datacolumnset_tableshow').datagrid("getSelected");	
			row.title = $('#fm_listpageset_dc_fieldtitle').val();
			row.dataFormat = $('#fm_listpageset_dc_dataformat').combobox("getValue");
			row.formula = $('#fm_listpageset_dc_formula').combobox("getValue");
			//row.dicname = $('#fm_listpageset_dc_dictionary').combotree("getValue");
			row.dicname = $('#fm_listpageset_dc_dictionary_id').val();
			row.fromdic = "否";
			if($('#fm_listpageset_dc_dictionary_id').val() != '-1')
				row.fromdic = "是";			
			row.textalign = $('#fm_listpageset_dc_align').combobox("getValue");
			
			//规则引擎
			if($("input[name=isJS_listPage_column]:checked").val() == 'zidingyi'){
				//自定义js
				var columnrules = new Object();
				columnrules.rulesService = encodeURIComponent($('#rulesService_listPage_column').val());
				columnrules.isJS = 'zidingyi';
				row.columnrules = encodeURIComponent(JSON2.stringify(columnrules));
			}else if($("input[name=isJS_listPage_column]:checked").val() == 'jiekou'){
				//平台接口
				if($('#tabspageurl_listPage_column').combobox('getValue')==''){
					$.messager.alert('提示','请选择页面','info');
					return;
				}else if($('#title_listPage_column').val()==''){
					$.messager.alert('提示','标题为空','info');
					return;
				}else{
					var columnrules = new Object();
					columnrules.tabspagetype = $('#tabspagetype_listPage_column').combobox('getValue');
					columnrules.tabspageurl = $('#tabspageurl_listPage_column').combobox('getValue');
					columnrules.tabstitle = $('#title_listPage_column').val();
					columnrules.isJS = 'jiekou';
					columnrules.params = $('#params_listPage_column').val();
					row.columnrules = encodeURIComponent(JSON2.stringify(columnrules));
				};
			}
					
			$('#fm_listpageset_datacolumnset_tableshow').datagrid("refreshRow",rowIndex);
			$('#fm_listpageset_dc_edit').window('close');
			$('#fm_listpageset_datacolumnset_tableshow').datagrid('resize');
		}
		//column set		
		function fmListpagesetDatacolumnsetSave(){
			var columnSetXmlUtils = new XmlUtils({dataType:'json'}); 
			columnSetXmlUtils.loadXmlString(formSettings);
			var root = columnSetXmlUtils.getRoot();
			var listColumnsNode = columnSetXmlUtils.getChildNodeByTagName(root,'Columns');	
			columnSetXmlUtils.removeChildNodes(listColumnsNode);
			var tableShowRows = $("#fm_listpageset_datacolumnset_tableshow").datagrid("getRows");
			var tableHiddenRows = $("#fm_listpageset_datacolumnset_tablehidden").datagrid("getRows");
			if(tableShowRows)
				for(var i=0;i<tableShowRows.length;i++){
					var ColumnNode = columnSetXmlUtils.createNode('Column','',{},listColumnsNode);
					var columnTextNodeAttr = {id:'',
						name:tableShowRows[i]['title'],
						align:tableShowRows[i]['textalign']=="-1"?"":tableShowRows[i]['textalign'],						
						dataFormat:tableShowRows[i]['dataFormat']=="-1"?"":tableShowRows[i]['dataFormat'],
						visible:'true',
						style:'',
						sort:tableShowRows[i]['sortIndex'],
						columnrules:tableShowRows[i]['columnrules']
					};
					var columnDataNodeAttr = {id:'',
						name:tableShowRows[i]['name'],
						//type:'input',
						dictionaryId:tableShowRows[i]['dicname']=="-1"?"":tableShowRows[i]['dicname'],
						fieldDataType:tableShowRows[i]['fieldDataType'],
						primaryKey:tableShowRows[i]['primaryKey'],
						formula:tableShowRows[i]['formula']=="-1"?"":tableShowRows[i]['formula']
					};
					columnSetXmlUtils.createNode('Text','',columnTextNodeAttr,ColumnNode);
					columnSetXmlUtils.createNode('Data','',columnDataNodeAttr,ColumnNode);
				}
			if(tableHiddenRows)
				for(var i=0;i<tableHiddenRows.length;i++){
					var ColumnNode = columnSetXmlUtils.createNode('Column','',{},listColumnsNode);
					var columnTextNodeAttr = {id:'',
						name:tableHiddenRows[i]['title'],
						align:tableHiddenRows[i]['textalign']=="-1"?"":tableHiddenRows[i]['textalign'],
						dataFormat:tableHiddenRows[i]['dataFormat']=="-1"?"":tableHiddenRows[i]['dataFormat'],
						visible:'false',
						style:'',
						sort:tableHiddenRows[i]['sortIndex']
					};
					var columnDataNodeAttr = {id:'',
						name:tableHiddenRows[i]['name'],
						//type:'input',
						dictionaryId:tableHiddenRows[i]['dicname']=="-1"?"":tableHiddenRows[i]['dicname'],
						fieldDataType:tableHiddenRows[i]['fieldDataType'],
						primaryKey:tableHiddenRows[i]['primaryKey'],
						formula:tableHiddenRows[i]['formula']=="-1"?"":tableHiddenRows[i]['formula']
					};
					columnSetXmlUtils.createNode('Text','',columnTextNodeAttr,ColumnNode);
					columnSetXmlUtils.createNode('Data','',columnDataNodeAttr,ColumnNode);
				}
       		columnSetXmlUtils.setAttribute(listColumnsNode,'isConfig','1');
			$("#fm_formdesignlist_datacolumn").html(CONFIGYES);
       		listPageSetSubmit(columnSetXmlUtils.toString());
		}
		//DataColumnSet End
		function fmListpagesetDcDictionaryWindow(){
		
			$.ajax({
			   type: "POST",
			   url: "form/form!dictionaryTreeJson.action",
			   //url:'form/form!getDictionaryLikeName.action',
			   data: "data=aa",
			   success: function(data){
			   		var treeData=eval(data);
			   		$('#fm_listpageset_dc_dictionary_tree').tree("loadData",treeData);
			  		$('#fm_listpageset_dc_dictionary_tree').tree('select',$('#fm_listpageset_dc_dictionary_tree').tree('find',$('#fm_listpageset_dc_dictionary_id').val()).target);
					$('#fm_listpageset_dc_dictionary_window').window('open');
		
			   }
			});
			
		}		
		
    </script>
  </head>  
  <body>
	<div class="easyui-panel" title="显示字段" collapsible="false" headerCls="header_cls" style="height:200px;">
		<table id="fm_listpageset_datacolumnset_tableshow"></table>
	</div>
	<div style="height:4px;"></div>
	<div class="easyui-panel" title="隐藏字段" collapsible="false" headerCls="header_cls" style="height:200px;">
		<table id="fm_listpageset_datacolumnset_tablehidden"></table>
	</div>
	<div style="height:4px;"></div>
	<table border="0" cellpadding="5" cellspacing="0" width="100%">
		<tr>
			<td align="center"><a class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="fmListpagesetDatacolumnsetSave()" >保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:;"  onclick="fmListPageSetClose()" >关闭</a></td>										
		</tr>					
	</table>
	<div id="fm_listpageset_dc_edit" class="easyui-window" style="padding:5px;">
		  
				<div class="pop_col_conc">
				  <dl>
					<dd>字段：</dd>
					<dt><input id="fm_listpageset_dc_fieldindex" type="hidden"/><span id="fm_listpageset_dc_fieldname"></span></dt>
				  </dl>
				  <dl>
					<dd>标题：</dd>
					<dt>
					  <input id="fm_listpageset_dc_fieldtitle" class="easyui-validatebox" style="width:147px;"/>
					</dt>
				  </dl>
				</div>
				<div class="pop_col_conc">
						<div style="display: none;"  >
							    <select id="fm_listpageset_dc_dataformat" class="easyui-combobox" listWidth="149" style="width:130px;">
								</select>
						</div>
						<div style="display: none;"  >
							     <select id="fm_listpageset_dc_formula" class="easyui-combobox" listWidth="149" style="width:130px;">
								 </select>
						</div>
				 </div>
				 
				  <div class="pop_col_conc">
					  <dl>
						<dd>对齐方式：</dd>
						<dt><select id="fm_listpageset_dc_align" class="easyui-combobox" listWidth="149" style="width:130px;">
						    </select></dt>
					  </dl>
					  <dl>
						<dd>数据字典：</dd>
						<dt>
						  <input id="fm_listpageset_dc_dictionary_id" type="hidden" />
							<input id="fm_listpageset_dc_dictionary_text" type="text" onfocus="fmListpagesetDcDictionaryWindow()" style="width:147px;cursor:pointer;" readOnly="true"/>
						</dt>
					  </dl>
				</div>
				  <div style="margin-top: 90px;padding-left: 10%;width: 90%">
				  		<input type="radio" name='isJS_listPage_column' value='zidingyi'> 自定义触发函数 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  						<input type="radio" name='isJS_listPage_column' value='jiekou'>使用平台接口 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>
					  <div id="zidingyidiv_listpage" style="padding-top:10px;">
					  	 <table>
					  	     <tr>
								<td colspan="1" align="right">
									业务逻辑：
								</td>
								<td colspan="4">
									<font color='red'>注意：字符串变量不要用单引号，要用双引号</font><br/>
									<textarea id="rulesService_listPage_column" rows="3" cols="46"></textarea>
								</td>
							</tr>
						</table>
					  </div>
					  <div id="jiekoudiv_listpage" style="padding-top:10px; display: none;">
			  				<table>
			  					<tr>
			  						<td align="right">
			  							页面类型：
			  						</td>
			  						<td align="left">
			  							<input id="tabspagetype_listPage_column" style="width:100px;"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			  						</td>
			  						<td align="right">
										页面：
									</td>
									<td align="left">
										<select id="tabspageurl_listPage_column" style="width:230px;"></select>
									</td>
							   </tr>
							   <tr>
							   		<td align="right">
										标题：
									</td>
									<td align="left">
										<input type="text" id="title_listPage_column" style="width: 180px;"/>&nbsp;
									</td>
									<td align="right">
										参数：
									</td>
									<td align="left">
										<input type="text" id="params_listPage_column" style="width: 230px;"/>&nbsp;
									</td>
							   </tr>
							    <tr>
							   		<td align="right" colspan="4">
										注：参数格式 a=1&b=2&c=#ccc a,b,c为参数名，后面为值 带#号的为这行数据的字段值 ccc为字段名
									</td>
							   </tr>
							</table>
			  		  </div>
				  </div>
					
				  <div style="padding:35px 0 0 150px;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a id="fm_listpageset_dc_save" class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="fmListpagesetDcSubmit()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a id="fm_listpageset_dc_cancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:;"  onclick="$('#fm_listpageset_dc_edit').window('close')" >关闭</a>		
				  </div>
	
		 
		<!--  <table style="font-size:12px;" width="100%" cellpadding="5" cellspacing="0">			
			<tr>	
				<td align="right">字段：</td>
			  	<td><input id="fm_listpageset_dc_fieldindex" type="hidden"/><span id="fm_listpageset_dc_fieldname"></span></td>
			  	<td align="right">标题：</td>
				<td><input id="fm_listpageset_dc_fieldtitle" class="easyui-validatebox" style="width:147px;"/></td>
			</tr>
			<tr>
				<td align="right" >数据格式：</td>
				<td><select id="fm_listpageset_dc_dataformat" class="easyui-combobox" listWidth="149" style="width:130px;">
					</select>
				</td>
				<td align="right">公式：</td>
				<td><select id="fm_listpageset_dc_formula" class="easyui-combobox" listWidth="149" style="width:130px;">
					</select>
				</td>
			</tr>
		 	<tr>
				<td align="right">对齐方式：</td>
				<td><select id="fm_listpageset_dc_align" class="easyui-combobox" listWidth="149" style="width:130px;">
					</select></td>
				<td align="right">数据字典：</td>
				<td> --><!-- <select id="fm_listpageset_dc_dictionary" class="easyui-combotree" style="width:132px;"></select> --><!-- 
					<input id="fm_listpageset_dc_dictionary_id" type="hidden" />
					<input id="fm_listpageset_dc_dictionary_text" type="text" onfocus="fmListpagesetDcDictionaryWindow()" style="width:147px;cursor:pointer;" readOnly="true"/>
				</td>
   	 		</tr>  
			<tr>
				<td align="	" colspan="4">
					<a id="fm_listpageset_dc_save" class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="fmListpagesetDcSubmit()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a id="fm_listpageset_dc_cancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:;"  onclick="$('#fm_listpageset_dc_edit').window('close')" >关闭</a>				
				</td>
			</tr>
		</table>
	 -->
	
	
	
	</div>
	<div id="fm_listpageset_dc_dictionary_window" class="easyui-window" style="padding:5px;background: #fafafa;">		
		<input type="text" onkeyup="fmDataDictionaryShowKeyUp(event,this,'fm_listpageset_dc_dictionary_tree');">
		<ul id="fm_listpageset_dc_dictionary_tree"></ul>		
 	</div>
  </body>
</html>
