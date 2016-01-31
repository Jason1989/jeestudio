<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String uuid = UUID.randomUUID().toString();
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
	<script type="text/javascript" src="common/js/common-util/configuration-util.js" ></script>
	<script language="javascript" type="text/javascript">
		$(function(){
			$('#fm_viewpageset_dc_role_view').linkbutton();
			$('#viewpage_is_customRole').change(function(){
				var is_customRole = $('#viewpage_is_customRole').attr('checked');
				if( is_customRole ){
					$('#is_view_customRole_table').show();
				}else{
					$('#is_view_customRole_table').hide();
				}
			});
			$('#per_view<%=uuid%>').appendTo('body').window({
				title:"选择角色",
				width: 500,
				height: 450,
		        modal: true,
		        resizable: false,
		        minimizable: false,
		        maximizable: false,
		        collapsible:false,
		        shadow: false,
		        closed: true
		    });
			$('#fm_viewpageset_datacolumnset_fieldlist').datagrid({
                iconCls: 'icon-save',
                nowrap: false,
                striped: true,
                fit: true,
                sortOrder: 'desc',
                //singleSelect:true,
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
                	{field: 'groupid',title: '所属分组',width: 80,align:'left',
                		formatter:function(value){
                			if (value == '' || value == '-1') return "无";
                			for(var i=0; i<fmViewpagesetDatacolumnsetTableGroup.length; i++){
								if (fmViewpagesetDatacolumnsetTableGroup[i].id == value){
									return fmViewpagesetDatacolumnsetTableGroup[i].text;
								}
							}
							return value;
						}
                	},                	
                	{field: 'fromdic',title: '来自数据字典',width: 80,align:'left',formatter:function(value){
                			if (value == '') return "否";
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
								////if (fmPagesetDataDictionary[i].id == value) return fmPagesetDataDictionary[i].text;
							}
							return value;
						}
					},    
					{field: 'visible',title: '隐藏',width: 70,align:'center',
						formatter:function(value){                			
							if ("true" == value || true == value) 
								return "<img src='jquery-easyui-1.1.2/themes/icons/lightbulb.png' style='vertical-align:middle;' title='显示'/>";
							else
								return "<img src='jquery-easyui-1.1.2/themes/icons/lightbulb_delete.png' style='vertical-align:middle;' title='隐藏'/>";
						}},            	
                	{field: 'operate',title: '操作',width: 120,align:'center',
                		formatter:function(value,rec,rowIndex){  
                			var rowData="";
                			for(var k in rec){
                				rowData += rowData==""?"{'"+k+"':'"+rec[k]+"'":",'"+k+"':'"+rec[k]+"'";	
                			}      
                			rowData+="}";               			         			
                			var links = '<a href="javascript:;" onclick="fmViewpagesetDatacolumnsetView('+rowIndex+')">编辑</a>';
                			var data = $('#fm_viewpageset_datacolumnset_fieldlist').datagrid('getData');
                			if((data['total']-1)>0){  
                				links += '&nbsp;&nbsp;|&nbsp;&nbsp;'; 
	                			if(rowIndex == 0){
	                				links += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;<img src="images/down.gif" style="cursor:hand;" onclick=fmPagesetDataGridColumnSort("down","fm_viewpageset_datacolumnset_fieldlist",'+rowIndex+',"'+rowData+'") title="下移"></img>';
	                			}else if(rowIndex == (data['total']-1)){
	                				links += '<img src="images/up.gif" style="cursor:hand;" onclick=fmPagesetDataGridColumnSort("up","fm_viewpageset_datacolumnset_fieldlist",'+rowIndex+',"'+rowData+'") title="上移"></img>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	                			}else{
	                				links += '<img src="images/up.gif" style="cursor:hand;" onclick=fmPagesetDataGridColumnSort("up","fm_viewpageset_datacolumnset_fieldlist",'+rowIndex+',"'+rowData+'") title="上移"></img>&nbsp;&nbsp;|&nbsp;&nbsp;';
	                				links += '<img src="images/down.gif" style="cursor:hand;" onclick=fmPagesetDataGridColumnSort("down","fm_viewpageset_datacolumnset_fieldlist",'+rowIndex+',"'+rowData+'") title="下移"></img>';  
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
			$('#fm_viewpageset_dc_view').appendTo('body').window({
				title: '数据列编辑',
				width: 700,
				modal: true,
				shadow: false,
				closed: true,
				minimizable: false,
				collapsible:false,
				height: 400
			});
			$('#fm_viewpageset_dc_dictionary_window').appendTo('body').window({
				title: '选择数据字典',
				width: 400,
				modal: true,
				shadow: false,
				closed: true,
				minimizable: false,
				collapsible:false,
				height: 500
			});
			$('#fm_viewpageset_dc_dictionary_tree').tree({
				//checkbox: true,
				//url: 'grid/tree_data.json',
				data:fmPagesetDataDictionary,
				onClick:function(node){
					if($('#fm_viewpageset_dc_dictionary_tree').tree("isLeaf",node.target)){
						$('#fm_viewpageset_dc_dictionary_id').val(node.id);
						$('#fm_viewpageset_dc_dictionary_text').val(node.text);
						$('#fm_viewpageset_dc_dictionary_window').window('close');
						//alert('you click '+node.text);
					}
				}
			});
			initFmViewPageSetGroupData(viewFormSettings);		
			//数据列设置
			$('#fm_viewpageset_datacolumnset_fieldlist').datagrid('loadData',eval('('+viewFieldlistJson+')'));
			$('#fm_viewpageset_dc_save').linkbutton();
			$('#fm_viewpageset_dc_cancel').linkbutton();
		});	
		function fmViewpagesetDatacolumnsetView(rowIndex){
			$('#fm_viewpageset_datacolumnset_fieldlist').datagrid("clearSelections");
			$('#fm_viewpageset_datacolumnset_fieldlist').datagrid("selectRow",rowIndex);
			var row = $('#fm_viewpageset_datacolumnset_fieldlist').datagrid("getSelected");			
			$('#fm_viewpageset_dc_fieldname').html(row.name);
			$('#fm_viewpageset_dc_fieldindex').val(rowIndex);
			$('#fm_viewpageset_dc_fieldtitle').val(row.title);
			$('#fm_viewpageset_dc_tagtype').combobox({
				valueField:'id',
				textField:'text',
				data:fmPageTagTypeData
			});
			/**
			$('#fm_viewpageset_dc_validation').combobox({
				valueField:'id',
				textField:'text',
				data:fmPagesetValidationRuleData
			});*/
			$('#fm_viewpageset_dc_formula').combobox({
				valueField:'id',
				textField:'text',
				editable:false,
				data:fmPagesetFormulaData
			});
			$('#fm_viewpageset_dc_function').combobox({
				valueField:'id',
				textField:'text',
				editable:false,
				data:fmPagesetFunctionData
			});
			/**$('#fm_viewpageset_dc_dictionary').combotree({
				treeWidth:148, 
				valueField:'id',
				textField:'text',
				data:fmPagesetDataDictionary
			});*/
			
			initFmViewPageSetGroupData(viewFormSettings);
			
			$('#fm_viewpageset_dc_pagegroup').combobox({
				listWidth:148,
				data:fmViewpagesetDatacolumnsetTableGroup,
				valueField:'id',
				editable:false,
    			textField:'text'
			});
			if(row.formula != null && row.formula != ""){
				$('#fm_viewpageset_dc_formula').combobox("setValue",row.formula);
			}else{
				$('#fm_viewpageset_dc_formula').combobox("setValue",'-1');
			}
			if(row.groupid != null && row.groupid != ""){
				$('#fm_viewpageset_dc_pagegroup').combobox("setValue",row.groupid);
			}else{
				$('#fm_viewpageset_dc_pagegroup').combobox("setValue",'-1');
			}
			if(row.dicname != null && row.dicname != ""){
				//$('#fm_viewpageset_dc_dictionary').combotree("setValue",row.dicname);
				$('#fm_viewpageset_dc_dictionary_id').val(row.dicname);
				//GUOWEIXIN 解决：数据字典如果被删除后，详情列无法打开
				if($('#fm_viewpageset_dc_dictionary_tree').tree('find',row.dicname)==null){
					$('#fm_viewpageset_dc_dictionary_id').val('-1');
					$('#fm_viewpageset_dc_dictionary_text').val('无');
				}else{
					$('#fm_viewpageset_dc_dictionary_text').val($('#fm_viewpageset_dc_dictionary_tree').tree('find',row.dicname).text);
				}
			}else{
				$('#fm_viewpageset_dc_dictionary_id').val('-1');
				$('#fm_viewpageset_dc_dictionary_text').val('无');
				//$('#fm_viewpageset_dc_dictionary').combotree("setValue",{id:'-1',text:'无'});
			}
			if(row.dataFunction != null && row.dataFunction != ""){
				$('#fm_viewpageset_dc_function').combobox("setValue",row.dataFunction);
			}else{
				$('#fm_viewpageset_dc_function').combobox("setValue",'-1');
			}
			
			$('#fm_viewpageset_dc_tagtype').combobox("setValue",row.tagType);			
			//$('#fm_viewpageset_dc_validation').combobox("setValue",row.validateRule);
			
			//$(":checkbox[id='fm_viewpageset_dc_isline']").attr("checked",false);
			if(row.exclusiveLine == true || row.exclusiveLine == 'true')
				$(":checkbox[id='fm_viewpageset_dc_isline']").attr("checked",true);
			else
				$(":checkbox[id='fm_viewpageset_dc_isline']").attr("checked",false);
			if(row.visible != true && row.visible != 'true')
				$(":checkbox[id='fm_viewpageset_dc_visible']").attr("checked",true);
			else
				$(":checkbox[id='fm_viewpageset_dc_visible']").attr("checked",false);

			var isRole = row.isCustomRole;
			if(isRole==true || isRole=='true'){
				if(row.isCustomRoleReadId){
					$('#is_customRole_view_role_id').val(row.isCustomRoleReadId);
				}
				if(row.isCustomRoleReadName){
					$('#is_customRole_view_role_name').val(row.isCustomRoleReadName);
				}
				$(':checkbox[id=viewpage_is_customRole]').attr('checked',true);
				$('#is_view_customRole_table').show();
			}else{
				$(':checkbox[id=viewpage_is_customRole]').removeAttr('checked');
				$('#is_view_customRole_table').hide();
			}
			
			$('#fm_viewpageset_dc_view').window('open');
		}
		function fmViewpagesetDatacolumnsetSave(){
			var viewColumnSetXmlUtils = new XmlUtils({dataType:'json'}); 
			viewColumnSetXmlUtils.loadXmlString(viewFormSettings);
			var root = viewColumnSetXmlUtils.getRoot();
			var viewColumnsNode = viewColumnSetXmlUtils.getChildNodeByTagName(root,'Columns');	
			viewColumnSetXmlUtils.removeChildNodes(viewColumnsNode);
			var viewTableRows = $("#fm_viewpageset_datacolumnset_fieldlist").datagrid("getRows");
			if(viewTableRows)
				for(var i=0;i<viewTableRows.length;i++){
					var viewColumnNode = viewColumnSetXmlUtils.createNode('Column','',{},viewColumnsNode);	
					var viewColumnTextAttributes = {id:'',
						name:viewTableRows[i]['title'],
						align:"",
						visible:viewTableRows[i]['visible'],
						style:'',
						sortIndex:viewTableRows[i]['sortIndex'],
						event:"",
						readOnly:"true",
						groupId:viewTableRows[i]['groupid']=="-1"?"":viewTableRows[i]['groupid'],
						exclusiveLine:viewTableRows[i]['exclusiveLine']
					};
					var viewColumnDataAttributes = {id:'',
						name:viewTableRows[i]['name'],
						type:viewTableRows[i]['tagType'],
						dictionaryId:viewTableRows[i]['dicname']=="-1"?"":viewTableRows[i]['dicname'],
						style:"",
						event:"",
						fieldDataType:viewTableRows[i]['dataType'], 
						primaryKey:viewTableRows[i]['isprimekey'], 
						dictionary:"", 
						formula:viewTableRows[i]['formula']=="-1"?"":viewTableRows[i]['formula'], 
						dataFunction:viewTableRows[i]['dataFunction']=="-1"?"":viewTableRows[i]['dataFunction'], 
						needs:"false"
					};					
					viewColumnSetXmlUtils.createNode('Text','',viewColumnTextAttributes,viewColumnNode);
					viewColumnSetXmlUtils.createNode('Data','',viewColumnDataAttributes,viewColumnNode);

					var viewColumnRolesAttributes = {
						isCustomRole : viewTableRows[i]['isCustomRole'],
						isCustomRoleReadId : viewTableRows[i]['isCustomRoleReadId'],
						isCustomRoleReadName : viewTableRows[i]['isCustomRoleReadName']
					};
					viewColumnSetXmlUtils.createNode('Roles','',viewColumnRolesAttributes,viewColumnNode);
				}
				
			viewColumnSetXmlUtils.setAttribute(viewColumnsNode,'isConfig','1');
			$("#fm_formdesignview_datacolumn").html(CONFIGYES);
			viewPageSetSubmit(viewColumnSetXmlUtils.toString());
			//parent.$('#fm_formdesignview_viewpageset').window('close');
		}
		//DataColumnSet End
		function fmViewpagesetDcSubmit(){
			var rowIndex = $('#fm_viewpageset_dc_fieldindex').val();
			$('#fm_viewpageset_datacolumnset_fieldlist').datagrid("clearSelections");
			$('#fm_viewpageset_datacolumnset_fieldlist').datagrid("selectRow",rowIndex);
			var row = $('#fm_viewpageset_datacolumnset_fieldlist').datagrid("getSelected");
			row.title = $('#fm_viewpageset_dc_fieldtitle').val();
			row.groupid = $('#fm_viewpageset_dc_pagegroup').combobox("getValue");
			row.tagType = $('#fm_viewpageset_dc_tagtype').combobox("getValue");
			row.formula = $('#fm_viewpageset_dc_formula').combobox("getValue");
			row.groupid = $('#fm_viewpageset_dc_pagegroup').combobox("getValue");
			//row.dicname = $('#fm_viewpageset_dc_dictionary').combotree("getValue");
			row.dicname = $('#fm_viewpageset_dc_dictionary_id').val();
			row.dataFunction = $('#fm_viewpageset_dc_function').combobox("getValue");
			row.fromdic = "否";
			if($('#fm_viewpageset_dc_dictionary_id').val() != '-1')
				row.fromdic = "是";
			
			//row.validateRule = $('#fm_viewpageset_dc_validation').combobox("getValue");
			row.visible = $(":checkbox[id='fm_viewpageset_dc_visible']").attr("checked")?"false":"true";
			row.exclusiveLine = $(":checkbox[id='fm_viewpageset_dc_isline']").attr("checked")?"true":"false";
			row.readOnly = "true";
			row.required = "false";
			$('#fm_viewpageset_datacolumnset_fieldlist').datagrid("refreshRow",rowIndex);
			$('#fm_viewpageset_dc_view').window('close')

			var isRole = $(':checkbox[id=viewpage_is_customRole]').attr("checked");
			row.isCustomRole = isRole?"true":"false";
			if(isRole){
				row.isCustomRoleReadId = $('#is_customRole_view_role_id').val();
				row.isCustomRoleReadName = $('#is_customRole_view_role_name').val();
			}
		}
		function fmViewpagesetDcDictionaryWindow(){
			$('#fm_viewpageset_dc_dictionary_tree').tree('select',$('#fm_viewpageset_dc_dictionary_tree').tree('find',$('#fm_viewpageset_dc_dictionary_id').val()).target);
			$('#fm_viewpageset_dc_dictionary_window').window('open');
		}
    </script>
  </head>  
  <body>
	<div class="easyui-panel" title="字段列表" collapsible="false" headerCls="header_cls" style="height: 400px;">
		<table id="fm_viewpageset_datacolumnset_fieldlist"></table>
	</div>	
	<div style="height:4px;"></div>		
	<table border="0" cellpadding="5" cellspacing="0" width="100%" style="margin-top:13px;margin-bottom: 13px;">
		<tr>
			<td align="center"><a class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="fmViewpagesetDatacolumnsetSave()" >保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:;"  onclick="fmViewPageSetClose()" >关闭</a></td>										
		</tr>					
	</table>
	<div id="fm_viewpageset_dc_view" class="easyui-window" style="padding:5px;">
		<table style="font-size:12px;" width="100%" cellpadding="5" cellspacing="0">			
			<tr>	
				<td align="right">字段：</td>
			  	<td><input id="fm_viewpageset_dc_fieldindex" type="hidden"/><span id="fm_viewpageset_dc_fieldname"></span></td>
			  	<td align="right">标题：</td>
				<td><input id="fm_viewpageset_dc_fieldtitle" class="easyui-validatebox" style="width:145px;"/></td>
			</tr>
			<tr>
				
				<td align="right">所属分组：</td>
			  	<td><select id="fm_viewpageset_dc_pagegroup" class="easyui-combobox" style="width:129px;">
					</select></td>
				<td align="right">公式：</td>
				<td><select id="fm_viewpageset_dc_formula" class="easyui-combobox" listWidth="148" style="width:129px;">
					</select>
				</td>
			</tr>
		 	<tr>
				<td align="right">来自函数：</td>
				<td><select id="fm_viewpageset_dc_function" class="easyui-combobox" listWidth="148" style="width:129px;">
					</select></td>
				<td align="right">数据字典：</td>
				<td><!-- <select id="fm_viewpageset_dc_dictionary" class="easyui-combotree" style="width:131px;">
					</select> -->
					<input id="fm_viewpageset_dc_dictionary_id" type="hidden" />
					<input id="fm_viewpageset_dc_dictionary_text" type="text" onfocus="fmViewpagesetDcDictionaryWindow()" style="width:147px;cursor:pointer;" readOnly="true"/>
				</td>
   	 		</tr>  
			<tr>			
				<!-- <td align="right">验证规则：</td>
				<td><select id="fm_viewpageset_dc_validation" class="easyui-combobox" listWidth="148" style="width:130px;">
					</select>
				</td> -->
				<td align="right" >标签类型：</td>
				<td colspan="3"><select id="fm_viewpageset_dc_tagtype" class="easyui-combobox" listWidth="148" style="width:130px;">
					</select>
				</td>
			</tr> 
    		<tr>
				<td align="center"><input type="checkbox" id="fm_viewpageset_dc_isline"/> 独占行</td>
				<td align="center"><input type="checkbox" id="fm_viewpageset_dc_visible"/> 隐藏</td>
				<!-- <td align="center"><input type="checkbox" id="fm_viewpageset_dc_readonly"/>只读</td>
				<td align="center"><input type="checkbox" id="fm_viewpageset_dc_require"/>必填项</td> -->
			</tr>
			<tr>
				<td colspan="1" align="center">
				  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="viewpage_is_customRole"> 自定义权限
				  <div id="per_view<%=uuid%>"/></div>
				</td>
				<td id="is_view_customRole_table" style="display: none;" align="left" colspan="3">
				    <a id="fm_viewpageset_dc_role_view" class="easyui-linkbutton" onclick="editpage_choose_role('is_customRole_view_role_id','is_customRole_view_role_name','per_view<%=uuid%>');">选择角色</a>
				    <input id="is_customRole_view_role_id" type="hidden" value="">
				    &nbsp;角色：<input id="is_customRole_view_role_name" type="text" value="" readOnly />
				</td>
			</tr>
			<tr><td colspan="4" >  </td></tr>
			<tr>
				<td align="center" colspan="4" >
					<a id="fm_viewpageset_dc_save" class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="fmViewpagesetDcSubmit()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a id="fm_viewpageset_dc_cancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:;"  onclick="$('#fm_viewpageset_dc_view').window('close')" >关闭</a>				
				</td>
			</tr>
		</table>
	</div>
	<div id="fm_viewpageset_dc_dictionary_window" class="easyui-window" style="padding:5px;background: #fafafa;">		
		<input type="text" onkeyup="fmDataDictionaryShowKeyUp(event,this,'fm_viewpageset_dc_dictionary_tree');">
		<ul id="fm_viewpageset_dc_dictionary_tree"></ul>		
 	</div>
  </body>
</html>
