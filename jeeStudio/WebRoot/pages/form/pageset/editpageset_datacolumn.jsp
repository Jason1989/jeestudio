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
	
     <style>
		.td-title{background:url('<%=path%>/jquery-easyui-1.1.2/themes/default/images/tabs_enabled.gif') repeat-x;}
	 </style>
	 
	<script type="text/javascript" src="common/js/common-util/configuration-util.js" ></script>
	<script language="javascript" type="text/javascript">
		var dictionary_state = 1;
		$(function(){
			$('#per_read<%=uuid%>').appendTo('body').window({
				title:"选择可读角色",
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
			$('#per_write<%=uuid%>').appendTo('body').window({
				title:"选择读写角色",
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
			$('#fm_editpageset_datacolumnset_fieldlist').datagrid({
                iconCls: 'icon-save',
               	width:780,
                nowrap: false,
                striped: true,
                sortOrder: 'desc',
                remoteSort: false,
              //singleSelect:true,
                idField: 'name',
			  //rownumbers: true,
			  fit:true,
                columns: [[
                	{field:'sortIndex',title:'序号',width:30,sortable:true,
	                	formatter:function(value,rec,rowIndex){  
	                		rec.sortIndex = parseInt(rowIndex)+1;          			
							return rec.sortIndex;
						}},
                	{field: 'name'   ,title: '字段'   ,width: 100,sortable: true,align:'left',
                		formatter:function(value,rec,rowIndex){
                    		var tablename = rec.columnTableName;
	                		if (tablename){
								return tablename+'.'+value
							}else{
								return value;
							}
						}
					},                	
                	{field: 'title'  ,title: '标题'   ,width: 100,align:'left'},                      	
                	{field: 'groupid',title: '所属分组',width: 80 ,align:'left',
                		formatter:function(value){
	                		if (value == '' || value == '-1') return "无";
                			for(var i=0; i<fmEditpagesetDatacolumnsetTableGroup.length; i++){
								if (fmEditpagesetDatacolumnsetTableGroup[i].id == value) return fmEditpagesetDatacolumnsetTableGroup[i].text;
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
								//if (fmPagesetDataDictionary[i].id == value) return fmPagesetDataDictionary[i].text;
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
                			var links = '<a href="javascript:;" onclick="fmEditpagesetDatacolumnsetEdit('+rowIndex+')">编辑</a>';
                			var data = $('#fm_editpageset_datacolumnset_fieldlist').datagrid('getData');
                			if((data['total']-1)>0){  
                				links += '&nbsp;&nbsp;|&nbsp;&nbsp;'; 
	                			if(rowIndex == 0){
	                				//links += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript:;" onclick=fmPagesetDataGridColumnSort("down","fm_editpageset_datacolumnset_fieldlist",'+rowIndex+',"'+rowData+'")>下移</a>';
	                				links += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;<img src="images/down.gif" style="cursor:hand;" onclick=fmPagesetDataGridColumnSort("down","fm_editpageset_datacolumnset_fieldlist",'+rowIndex+',"'+rowData+'") title="下移"></img>';
	                			}else if(rowIndex == (data['total']-1)){
	                				//links += '<a href="javascript:;" onclick=fmPagesetDataGridColumnSort("up","fm_editpageset_datacolumnset_fieldlist",'+rowIndex+',"'+rowData+'")>上移</a>';
	                				links += '<img src="images/up.gif" style="cursor:hand;" onclick=fmPagesetDataGridColumnSort("up","fm_editpageset_datacolumnset_fieldlist",'+rowIndex+',"'+rowData+'") title="上移"></img>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
	                			}else{
	                				//links += '<a href="javascript:;" onclick=fmPagesetDataGridColumnSort("up","fm_editpageset_datacolumnset_fieldlist",'+rowIndex+',"'+rowData+'")>上移</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
	                				//links += '<a href="javascript:;" onclick=fmPagesetDataGridColumnSort("down","fm_editpageset_datacolumnset_fieldlist",'+rowIndex+',"'+rowData+'")>下移</a>';  
	                				links += '<img src="images/up.gif" style="cursor:hand;" onclick=fmPagesetDataGridColumnSort("up","fm_editpageset_datacolumnset_fieldlist",'+rowIndex+',"'+rowData+'") title="上移"></img>&nbsp;&nbsp;|&nbsp;&nbsp;';
	                				links += '<img src="images/down.gif" style="cursor:hand;" onclick=fmPagesetDataGridColumnSort("down","fm_editpageset_datacolumnset_fieldlist",'+rowIndex+',"'+rowData+'") title="下移"></img>';  
	                			
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
			$('#fm_editpageset_dc_edit').appendTo('body').window({
				title: '数据列编辑',
				width: 700,
				modal: true,
				shadow: false,
				closed: true,
				minimizable: false,
				collapsible:false,
				height: 400
			});
			$('#fm_editpageset_dc_dictionary_window').appendTo('body').window({
				title: '选择数据字典',
				width: 400,
				modal: true,
				shadow: false,
				closed: true,
				minimizable: false,
				collapsible:false,
				height: 500
			});
			$('#fm_editpageset_dc_org_window').appendTo('body').window({
				title: '选择组织机构',
				width: 400,
				modal: true,
				shadow: false,
				closed: true,
				minimizable: false,
				collapsible:false,
				height: 500
			});
			
			$('#fm_editpageset_dc_dictionary_tree').tree({
				//checkbox: true,
				//url: 'grid/tree_data.json',
				data:fmPagesetDataDictionary,
				onClick:function(node){
					if($('#fm_editpageset_dc_dictionary_tree').tree("isLeaf",node.target)){
						if(dictionary_state == 1){
							$('#fm_editpageset_dc_dictionary_id').val(node.id);
							$('#fm_editpageset_dc_dictionary_text').val(node.text);
						}else if(dictionary_state == 2){
							$('#fm_editpageset_dc_dictionary_id_jilian').val(node.id);
							$('#fm_editpageset_dc_dictionary_text_jilian').val(node.text);
						}
						$('#fm_editpageset_dc_dictionary_window').window('close');
						//alert('you click '+node.text);
					}
				}
			});
			$('#fm_editpageset_dc_org_tree').tree({
				//checkbox: true,
				//url: 'grid/tree_data.json',
				data:fmPagesetDataOrg,
				onClick:function(node){
					var type = $('#fm_editpageset_dc_tagtype').combobox("getValue");
				if(type==null||type==''||type==-1){
						alert('请先选择标签类型!');
					}else if(type==18){
						$('#fm_editpageset_dc_org_id_org').val(node.id);
						$('#fm_editpageset_dc_org_text_org').val(node.text);
					}else if(type==22){
						$('#fm_editpageset_dc_org_id_org_human').val(node.id);
						$('#fm_editpageset_dc_org_text_org_human').val(node.text);
					}
					$('#fm_editpageset_dc_org_window').window('close');
				}
			});
			initFmEditPageSetGroupData(editFormSettings);
			//数据列设置数据

			$('#fm_editpageset_datacolumnset_fieldlist').datagrid('loadData',eval('('+editFieldlistJson+')'));
			$('#fm_editpageset_dc_save').linkbutton();
			$('#fm_editpageset_dc_cancel').linkbutton();
			$('#fm_editpageset_dc_role_read').linkbutton();
			$('#fm_editpageset_dc_role_write').linkbutton();

			$('#editpage_is_customRole').change(function(){
				var is_customRole = $('#editpage_is_customRole').attr('checked');
				if( is_customRole ){
					$('#is_customRole_table').show();
				}else{
					$('#is_customRole_table').hide();
				}
			});
			$('#is_customRole_read').change(function(){
				var is_customRole_read = $('#is_customRole_read').attr('checked');
				if( is_customRole_read ){
					$('#is_customRole_read_show').show();
				}else{
					$('#is_customRole_read_show').hide();
				}
			});
			$('#is_customRole_write').change(function(){
				var is_customRole_write = $('#is_customRole_write').attr('checked');
				if( is_customRole_write ){
					$('#is_customRole_write_show').show();
				}else{
					$('#is_customRole_write_show').hide();
				}
			});
		});	
		//数据列设置编辑
		function fmEditpagesetDatacolumnsetEdit(rowIndex){
			$('#fm_editpageset_datacolumnset_fieldlist').datagrid("clearSelections");
			$('#fm_editpageset_datacolumnset_fieldlist').datagrid("selectRow",rowIndex);
			var row = $('#fm_editpageset_datacolumnset_fieldlist').datagrid("getSelected");	
			//alert(JSON2.stringify(row));
			var tablename = row.columnTableName;
			if(tablename){
				$('#fm_editpageset_dc_fieldname').html(tablename+'.'+row.name);
			}else{
				$('#fm_editpageset_dc_fieldname').html(row.name);
			}
			$('#fm_editpageset_dc_fieldindex').val(rowIndex);
			$('#fm_editpageset_dc_fieldtitle').val(row.title);
			$('#fm_editpageset_dc_tagtype').combobox({
				valueField:'id',
				textField:'text',
				editable:false,
				data:fmPageTagTypeData,
				onSelect:function (record){
					changeExtendedAttributes('extended-attributes',record.id+'_extendAttributes');
					if(record.id=='20'){
						$('#editPage_list_inside_tr1').show();
						$('#editPage_list_inside_tr2').show();
						$('#editPage_list_inside_tr3').show();
					}else{
						$('#editPage_list_inside_tr1').hide();
						$('#editPage_list_inside_tr2').hide();
						$('#editPage_list_inside_tr3').hide();
					}
				}
			});
			$('#fm_editpageset_dc_formula').combobox({
				valueField:'id',
				textField:'text',
				editable:false,
				data:fmPagesetFormulaData
			});
			$('#fm_editpageset_dc_function').combobox({
				valueField:'id',
				textField:'text',
				editable:false,
				data:fmPagesetFunctionData
			});
			/**
				$('#fm_editpageset_dc_dictionary').combotree({
					valueField:'id',
					textField:'text',
					treeWidth:148,
					data:fmPagesetDataDictionary
				});
			*/
			$('#fm_editpageset_dc_validation').combobox({
				valueField:'id',
				textField:'text',
				editable:false,
				data:fmPagesetValidationRuleData,
				onSelect:function (record){
					var val = $('#fm_editpageset_dc_validation').combobox('getValue');
					if(val=="de774e8e1cd463daa917a12d9ce8ab69"){
					 	$("#compare_date_div").show();
					}else{
						$("#compare_date_div").hide();
					}
					
					//if(val=="9"){
					 	$("#lengthRule_tr").css('display','block');
					//}else{
					//	$("#lengthRule_tr").css('display','none');
					//}
				
				}
				
			});
			initFmEditPageSetGroupData(editFormSettings);
			$('#fm_editpageset_dc_pagegroup').combobox({
				listWidth:148,
				data:fmEditpagesetDatacolumnsetTableGroup,
				valueField:'id',
				editable:false,
    			textField:'text'
			});
			
			$('#variant_type').combobox({
				listWidth:148,
				data:$.parseJSON(VARIANT_TYPE),
				valueField:'id',
				editable:false,
    			textField:'text'
			});
			/*日期比较*/
			$('#compare_date').combobox({
				listWidth:148,
				valueField:'name',
				textField:'title',
				data:$('#fm_editpageset_datacolumnset_fieldlist').datagrid("getRows"),
				required:true
			})
			$('#jilian_column').combobox({
				listWidth:148,
				valueField:'name',
				textField:'title',
				data:$('#fm_editpageset_datacolumnset_fieldlist').datagrid("getRows")
				//required:true
				
			})
			//页面
			$("#tabspageurl_editPage_datacolumn").combobox({
				valueField:'id',
   				textField:'text',  
            	treeWidth:157,
            	panelHeight:160,
			    url:'form/form!getTabsPageList.action?mainObjectId='+'${form.dataTable.id}' + '&pageType=listPage&random='+parseInt(10*Math.random())
			});
			
			//数据源下拉框
			$('#bd_databaseobjectimport_isExist_datasource').combobox({
			    url:'datasource/dataSource!getAllItem.action?time='+new Date().getTime(),
			    valueField:'id',
			    textField:'name',
			    listHeight:200,
			    editable:false
			});

			$("#editPage_list_inside").combobox({
				valueField:'id',
   				textField:'text',  
            	treeWidth:157,
            	panelHeight:160,
			    url:'form/form!getTabsPageList.action?mainObjectId='+'${form.dataTable.id}' + '&pageType=listPage&random='+parseInt(10*Math.random())
			});
			
			/**
			 * 年月日 选择
			 */
			 var dateFormat=[
								{id:'0',text:'请选择'},
								{id:'1',text:'yyyy'},
								{id:'2',text:'yyyy-MM'},
								{id:'3',text:'yyyy-MM-dd'}	,
								{id:'4',text:'yyyy-MM-dd HH:mm'},
								{id:'5',text:'yyyy-MM-dd HH:mm:ss'}
							];
			$('#fm_editpageset_dc_dateformat').combobox({
				listWidth:148,
				data:dateFormat,
				valueField:'id',
				editable:false,
    			textField:'text'
			});
			if(row.tagType != null && row.tagType != ""){
				$('#fm_editpageset_dc_tagtype').combobox("setValue",row.tagType);
				var selectValue=$('#fm_editpageset_dc_tagtype').combobox("getValue");
				changeExtendedAttributes('extended-attributes',selectValue+'_extendAttributes');
			}else{
				$('#fm_editpageset_dc_tagtype').combobox("setValue",'-1');
				changeExtendedAttributes('extended-attributes','');
			}
			if(row.formula != null && row.formula != ""){
				$('#fm_editpageset_dc_formula').combobox("setValue",row.formula);
			}else{
				$('#fm_editpageset_dc_formula').combobox("setValue",'-1');
			}	
			if(row.groupid != null && row.groupid != ""){
				$('#fm_editpageset_dc_pagegroup').combobox("setValue",row.groupid);
			}else{
				$('#fm_editpageset_dc_pagegroup').combobox("setValue",'-1');
			}
			if(row.dicname != null && row.dicname != ""){
				//$('#fm_editpageset_dc_dictionary').combotree("setValue",row.dicname);
				$('#fm_editpageset_dc_dictionary_id').val(row.dicname);	
				if($('#fm_editpageset_dc_dictionary_tree').tree('find',row.dicname)==null){
					$('#fm_editpageset_dc_dictionary_id').val('-1');
				    $('#fm_editpageset_dc_dictionary_text').val('无');
				}else{
					$('#fm_editpageset_dc_dictionary_text').val($('#fm_editpageset_dc_dictionary_tree').tree('find',row.dicname).text);
				}
			}else{
				$('#fm_editpageset_dc_dictionary_id').val('-1');
				$('#fm_editpageset_dc_dictionary_text').val('无');
				//$('#fm_editpageset_dc_dictionary').combotree("setValue",{id:'-1',text:'无'});
			}
			if(row.validateRule != null && row.validateRule != ""){
				$('#fm_editpageset_dc_validation').combobox("setValue",row.validateRule);
			}else{
				$('#fm_editpageset_dc_validation').combobox("setValue",'-1');
			}
			if(row.maxlength2 != null && row.maxlength2 != ""){
				$('#maxlength').val(row.maxlength2);
			}else{
				$('#maxlength').val(row.maxlength);//当没有自定义最大长度时，最大长度以数据库字段长度为准。
			}
			if(row.minlength != null && row.minlength != ""){
				$('#minlength').val(row.minlength);
			}else{
				$('#minlength').val(0);//当没有自定义最小长度时，最小长度设置为0。
			}
			if(row.maxlength != null && row.maxlength != ""){
				$('#maxlength_db').val(row.maxlength);
				
			}

			$('#maxlength').validatebox({validType:"number"});
			$('#minlength').validatebox({validType:"number"});
			$('#maxlength').keyup(function(){
				
				var val = $('#maxlength').val();
				var val2 = $('#maxlength_db').val();
				if(val != ""){
					if(parseInt(val,10)>val2){
						$.messager.alert("提示","最大长度不能大于数据库定义的长度"+val2,'info');
						$('#maxlength').val(row.maxlength);
					}
				}
			})
			//新加的验证数据是否存在
			if(row.isExistDBSource != null && row.isExistDBSource != ""){
				$('#bd_databaseobjectimport_isExist_datasource').combobox("setValue",row.isExistDBSource);
			}else{
				$('#bd_databaseobjectimport_isExist_datasource').combobox("setValue",'-1');
			}
			if(row.isExistSQL != null && row.isExistSQL != ""){
				$('#isExistSQL').val(row.isExistSQL);
			}
			if(row.dataFunction != null && row.dataFunction != ""){
				$('#fm_editpageset_dc_function').combobox("setValue",row.dataFunction);
			}else{
				$('#fm_editpageset_dc_function').combobox("setValue",'-1');
			}
			//扩展属性
			if(row.dateformat  != null && row.dateformat != ""){
				$('#fm_editpageset_dc_dateformat').combobox("setValue",row.dateformat);
			}
			if(row.rows  != null && row.rows != ""){
				document.getElementById('fm_editpageset_dc_rows').value=row.rows;
			}
			if(row.cols  != null && row.cols != ""){
				document.getElementById('fm_editpageset_dc_cols').value=row.cols;
			}
			//树的扩展GUOWEIXIN
				if(row.isCheckBox==1){
					document.getElementById('fm_editpageset_tree_isCheckBox').checked=true;
				 }
				if(row.isSpread==1){
					document.getElementById('fm_editpageset_tree_isSpread').checked=true;
				}
		
			
			
			//variantOrnot 系统变量
			//alert("ornot:"+row.variantOrnot);
			if(row.variantOrnot == "1" || row.exclusiveLine == 1){
				$(":checkbox[id='variant_ornot']").attr("checked",true);
				$("#variant_value").val(row.variantValue);
				var c = row.variantType;
				$("#variant_type").combobox("setValue",c);
			}else{
				$(":checkbox[id='variant_ornot']").attr("checked",false);
				$("#variant_value").val("");
				$("#variant_type").combobox("setValue",'0');
				$("#variant_type").combobox("setText",'常量');
			}	
			//$(":checkbox[id='fm_editpageset_dc_isline']").attr("checked",false);
			if(row.exclusiveLine == true || row.exclusiveLine == 'true')
				$(":checkbox[id='fm_editpageset_dc_isline']").attr("checked",true);
			else
				$(":checkbox[id='fm_editpageset_dc_isline']").attr("checked",false);
			if(row.visible != true && row.visible != 'true')
				$(":checkbox[id='fm_editpageset_dc_visible']").attr("checked",true);
			else
				$(":checkbox[id='fm_editpageset_dc_visible']").attr("checked",false);
			if(row.readOnly == true || row.readOnly == 'true')
				$(":checkbox[id='fm_editpageset_dc_readonly']").attr("checked",true);
			else
				$(":checkbox[id='fm_editpageset_dc_readonly']").attr("checked",false);
				
			//是否必填项
			if(row.required == true || row.required == '1'||row.required == 'true'){
				$(":checkbox[id='fm_editpageset_dc_require']").attr("checked",true);
			}else{
				$(":checkbox[id='fm_editpageset_dc_require']").attr("checked",false);
			}
							
			//判断
			if(row.rulesService!=null && row.rulesService!="" && row.rulesService != undefined){
				$("#rulesService").val(decodeURIComponent(row.rulesService));
			}else{
				$("#rulesService").val("");
			}
			if(row.eventTypes!=null && row.eventTypes!="" && row.eventTypes != undefined){
				$(":checkbox[name='eventType']").each(function(){
					if(row.eventTypes.indexOf($(this).val())>=0){
						$(this).attr('checked',true);
					}else{
						$(this).attr('checked',false);
					}
				});
			}else{
				$(":checkbox[name='eventType']").each(function(){
					$(this).attr('checked',false);
				});
			}
			//是否级联
			if(row.is_jilian=='1'){
				$('#is_jilian').attr('checked',true);
				$('#jilian_column').combobox('setValue',row.jilian_column);
				if(row.jilian_column_dictionaryId != null && row.jilian_column_dictionaryId != "" && row.jilian_column_dictionaryId != undefined){
					$('#fm_editpageset_dc_dictionary_id_jilian').val(row.jilian_column_dictionaryId);
					$('#fm_editpageset_dc_dictionary_text_jilian').val($('#fm_editpageset_dc_dictionary_tree').tree('find',row.jilian_column_dictionaryId).text);
				}else{
					$('#fm_editpageset_dc_dictionary_id_jilian').val('-1');
					$('#fm_editpageset_dc_dictionary_text_jilian').val('无');
				}
			}else{
				$('#is_jilian').attr('checked',false);
				$('#fm_editpageset_dc_dictionary_id_jilian').val('-1');
				$('#fm_editpageset_dc_dictionary_text_jilian').val('无');
			}
			//是否启用列表选择数据is_listPageForvaluepageurl_listPage
			if(row.is_listPageForvalue=='1'){
				$('#is_listPageForvalue').attr('checked',true);
				$('#tabspageurl_editPage_datacolumn').combobox('setValue',row.pageurl_listPage);
			}else{
				$('#is_listPageForvalue').attr('checked',false);
				$('#tabspageurl_editPage_datacolumn').combobox('setValue','');
			}
			if(row.list_value!=null&&row.list_value!=''){
				$('#list_value').val(row.list_value);
				$('#list_text').val(row.list_text);
			}
			//是否是工作流字段
			if(row.isworkflow == true || row.isworkflow == 'true')
				$(":checkbox[id='is_workflow']").attr("checked",true);
			else
				$(":checkbox[id='is_workflow']").attr("checked",false);
			//组织机构树控件是否可以多选
			if(row.ismultipart == true || row.ismultipart == 'true')
				$(":checkbox[id='is_multipart']").attr("checked",true);
			else
				$(":checkbox[id='is_multipart']").attr("checked",false);
			//组织机构树控件是否可以子节点可选
			if(row.isleafcheck == true || row.isleafcheck == 'true')
				$(":checkbox[id='is_leafcheck']").attr("checked",true);
			else
				$(":checkbox[id='is_leafcheck']").attr("checked",false);
			//组织机构树控件是否可以当前组织机构可选
			if(row.isselforg == true || row.isselforg == 'true')
				$(":checkbox[id='is_selforg']").attr("checked",true);
			else
				$(":checkbox[id='is_selforg']").attr("checked",false);
			if(row.orgid!=null&&row.orgid!=''&&row.orgid!=undefined){
				$('#fm_editpageset_dc_org_id_org').val(row.orgid);
				$('#fm_editpageset_dc_org_text_org').val($('#fm_editpageset_dc_org_tree').tree('find',row.orgid).text);
				//$('#fm_editpageset_dc_org_tree').tree('select',$('#fm_editpageset_dc_org_tree').tree('find',row.orgid).target);
			}
			//人员列表树控件是否可以多选
			if(row.ismultiparthuman == true || row.ismultiparthuman == 'true')
				$(":checkbox[id='is_multipart_human']").attr("checked",true);
			else
				$(":checkbox[id='is_multipart_human']").attr("checked",false);
			//人员列表树控件所选择的组织机构
			if(row.orgidhuman!=null&&row.orgidhuman!=''&&row.orgidhuman!=undefined){
				$('#fm_editpageset_dc_org_id_org_human').val(row.orgidhuman);
				$('#fm_editpageset_dc_org_text_org_human').val($('#fm_editpageset_dc_org_tree').tree('find',row.orgidhuman).text);
			}
						 //应急选择控件1： 1单张表取数据
		    if(row.issingletable==1||row.issingletable=='1'){
		       $('#is_singletable').attr('checked','checked');
		    }else{
		       $('#is_moretable').attr('checked','checked');
		    }
		  
		    //sql语句
		    if(row.isemergencytreesql!=null&&row.isemergencytreesql!=''&&row.isemergencytreesql!=undefined){
		       $('#is_emergencytreesql').val(row.isemergencytreesql);
		    }

			
			
						 //应急选择控件1： 1单张表取数据
		    if(row.issingletable==1||row.issingletable=='1'){
		       $('#is_singletable').attr('checked','checked');
		    }else{
		       $('#is_moretable').attr('checked','checked');
		    }
		  
		    //sql语句
		    if(row.isemergencytreesql!=null&&row.isemergencytreesql!=''&&row.isemergencytreesql!=undefined){
		       $('#is_emergencytreesql').val(row.isemergencytreesql);
		    }

			var cloumnRows= $('#fm_editpageset_datacolumnset_fieldlist').datagrid("getRows");
			var form_Id='';
			/**
			 * 字段规则引擎设计
			 */
			 var lastIndex;
			 $('#fieldparmer_grid').datagrid({
			    title: '参数列表',
			    headerCls:'header_cls',
			    width: 480,
				height: 200,
				fitColumns: true,
				singleSelect:true,
				columns:[[
					{field:'pramerTitle',title:'参数标题',width:160,editor:{
							type:'combobox',
							options:{
								valueField:'name',
								textField:'title',
								data:cloumnRows,
								required:true
							}
						}
					},
					{field:'pramerName',title:'参数名' ,width:160,editor:'text'},
					{field:'pramerID'  ,title:'参数id' ,width:160},
					{field:'pramerType',title:'类型'   ,width:160,align:'center'}
				]],
				toolbar:[{
					text:'添加',
					iconCls:'icon-add',
					titleCls:'link_btn_color',
					handler:function(){
					$('#fieldparmer_grid').datagrid('endEdit', lastIndex);
						$('#fieldparmer_grid').datagrid('appendRow',{
							pramerName:'',
							pramerID:'',
							pramerType:''
						});
						var lastIndex = $('#fieldparmer_grid').datagrid('getRows').length-1;
						$('#fieldparmer_grid').datagrid('beginEdit', lastIndex);
					}
				},'-',{
					text:'保存',
					iconCls:'icon-save',
					titleCls:'link_btn_color',
					handler:function(){
						$('#fieldparmer_grid').datagrid('acceptChanges');
					}
				},'-',{
					text:'删除',
					iconCls:'icon-remove',
					titleCls:'link_btn_color',
					handler:function(){
						var row = $('#fieldparmer_grid').datagrid('getSelected');
						if (row){
							var index = $('#fieldparmer_grid').datagrid('getRowIndex', row);
							$('#fieldparmer_grid').datagrid('deleteRow', index);
						}
					}
				}]
			});
			
			var val = $('#fm_editpageset_dc_validation').combobox('getValue');
			if(val=="de774e8e1cd463daa917a12d9ce8ab69"){
				$("#compare_date_div").show();
			}else{
				$("#compare_date_div").hide();
			}
			//if(val=="9"){
			 	$("#lengthRule_tr").css('display','block');
			//}else{
				//$("#lengthRule_tr").css('display','none');
			//}
			
			$('#compare_date_con_'+((row.compCon=='lt')?'lt':'rt')).attr('checked','checked');
			$('#compare_date').combobox('setValue',row.compDate);

			var selectValue = $('#fm_editpageset_dc_tagtype').combobox("getValue");
			if(selectValue=='20'){
				var listInside = row.listInside;
				var listInsideParmer = row.listInsideParmer;
				if(listInside){
					$('#editPage_list_inside').combobox('setValue',listInside);
				}
				if(listInsideParmer){
					$('#editPage_list_inside_fieldparmer').val(listInsideParmer);
				}
				$('#editPage_list_inside_tr1').show();
				$('#editPage_list_inside_tr2').show();
				$('#editPage_list_inside_tr3').show();
			}else{
				$('#editPage_list_inside_tr1').hide();
				$('#editPage_list_inside_tr2').hide();
				$('#editPage_list_inside_tr3').hide();
			}
			
			var isRole = row.isCustomRole;
			if(isRole==true || isRole=='true'){
				var isRead = row.isCustomRoleRead;
				if(row.isCustomRoleReadId){
					$('#is_customRole_read_role_id').val(row.isCustomRoleReadId);
				}
				if(row.isCustomRoleReadName){
					$('#is_customRole_read_role_name').val(row.isCustomRoleReadName);
				}
				if(isRead==true || isRead=='true'){
					$(':checkbox[id=is_customRole_read]').attr('checked',true);
					$('#is_customRole_read_show').show();
				}else{
					$(':checkbox[id=is_customRole_read]').removeAttr('checked');
					$('#is_customRole_read_show').hide();
				}
				var isWrite = row.isCustomRoleWrite;
				if(row.isCustomRoleWriteId){
					$('#is_customRole_write_role_id').val(row.isCustomRoleWriteId);
				}
				if(row.isCustomRoleWriteName){
					$('#is_customRole_write_role_name').val(row.isCustomRoleWriteName);
				}
				if(isWrite==true || isWrite=='true'){
					$(':checkbox[id=is_customRole_write]').attr('checked',true);
					$('#is_customRole_write_show').show();
				}else{
					$(':checkbox[id=is_customRole_write]').removeAttr('checked');
					$('#is_customRole_write_show').hide();
				}
				$(':checkbox[id=editpage_is_customRole]').attr('checked',true);
				$('#is_customRole_table').show();
			}else{
				$(':checkbox[id=editpage_is_customRole]').removeAttr('checked');
				$('#is_customRole_table').hide();
			}
			
			 /**
			  * 字段编辑窗口
			  */
			$('#fm_editpageset_dc_edit').window('open');
		}
		//保存整个datagrid
		function fmEditpagesetDatacolumnsetSave(){
			var editColumnSetXmlUtils = new XmlUtils({dataType:'json'}); 
			editColumnSetXmlUtils.loadXmlString(editFormSettings);
			var root = editColumnSetXmlUtils.getRoot();
			var editColumnsNode = editColumnSetXmlUtils.getChildNodeByTagName(root,'Columns');	
			editColumnSetXmlUtils.removeChildNodes(editColumnsNode);
			var editTableRows = $("#fm_editpageset_datacolumnset_fieldlist").datagrid("getRows");
			if(editTableRows)
				for(var i=0;i<editTableRows.length;i++){
				    var editColumnNode = editColumnSetXmlUtils.createNode('Column','',{},editColumnsNode);	
					var editColumnTextAttributes = {id:'',
						name:editTableRows[i]['title'],
						//扩展属性
						dateformat:editTableRows[i]['dateformat'],
						rows:editTableRows[i]['rows'],
						cols:editTableRows[i]['cols'],
						align:"",
						visible:editTableRows[i]['visible'],
						style:'',
						sortIndex:editTableRows[i]['sortIndex'],
						event:"",
						readOnly:editTableRows[i]['readOnly'],
						groupId:editTableRows[i]['groupid']=="-1"?"":editTableRows[i]['groupid'],
						exclusiveLine:editTableRows[i]['exclusiveLine'],
						variantOrnot:editTableRows[i]['variantOrnot'],
						variantType:editTableRows[i]['variantType'],
						variantValue:editTableRows[i]['variantValue'],
						isworkflow:editTableRows[i]['isworkflow'],
						ismultipart:editTableRows[i]['ismultipart'],
						isleafcheck:editTableRows[i]['isleafcheck'],
						isselforg:editTableRows[i]['isselforg'],
						orgid:editTableRows[i]['orgid'],
						
						//应急组件
						issingletable:editTableRows[i]['issingletable'],
						isemergencytreesql:editTableRows[i]['isemergencytreesql'],
						
						is_listPageForvalue:editTableRows[i]['is_listPageForvalue'],
						pageurl_listPage:editTableRows[i]['pageurl_listPage'],
						listInside : editTableRows[i]['listInside'],
						listInsideParmer : editTableRows[i]['listInsideParmer'],
						list_value : editTableRows[i]['list_value'],
						list_text : editTableRows[i]['list_text'],
						ismultiparthuman:editTableRows[i]['ismultiparthuman'],
						orgidhuman:editTableRows[i]['orgidhuman']
					};
					var editColumnDataAttributes = {id:'',
						name:editTableRows[i]['name'],
						type:editTableRows[i]['tagType']=="-1"?"":editTableRows[i]['tagType'],
						dictionaryId:editTableRows[i]['dicname']=="-1"?"":editTableRows[i]['dicname'],
						style:"",
						event:"",		
											
						isCheckBox:editTableRows[i]['isCheckBox'],
						isSpread:editTableRows[i]['isSpread'],
						fieldDataType:editTableRows[i]['dataType']=="-1"?"":editTableRows[i]['dataType'], 
						primaryKey:editTableRows[i]['isprimekey'], 
						dictionary:"", 
						formula:editTableRows[i]['formula']=="-1"?"":editTableRows[i]['formula'], 
						dataFunction:editTableRows[i]['dataFunction']=="-1"?"":editTableRows[i]['dataFunction'], 
						needs:"false",
						tablename : editTableRows[i]['columnTableName']
					};
					var editColumnEditModeAttributes = {
						id:"", 
						data:"", 
						reminder:"", 
						required:editTableRows[i]['required'], 
						validateRule:editTableRows[i]['validateRule']=="-1"?"":editTableRows[i]['validateRule'],
						maxLength:editTableRows[i]['maxlength'],
						maxLength2:editTableRows[i]['maxlength2'],
						minLength:editTableRows[i]['minlength'],
						compDate:editTableRows[i]['compDate']=="-1"?"":editTableRows[i]['compDate'],
						compCon:editTableRows[i]['compCon'], 
						isExistDBSource:editTableRows[i]['isExistDBSource'], 
						isExistSQL:editTableRows[i]['isExistSQL'],
						tdWidth :editTableRows[i]['tdWidth']==0?50:editTableRows[i]['tdWidth'],
						textWidth :editTableRows[i]['textWidth']==0?50:editTableRows[i]['textWidth']			
					};
					
					var editColumnEditRulesAttributes = {
						rulesService:editTableRows[i]['rulesService'],
						rulesRowsParmer:editTableRows[i]['rulesRowsParmer'],
						eventTypes:editTableRows[i]['eventTypes'],
						is_jilian:editTableRows[i]['is_jilian'],
						jilian_column:editTableRows[i]['jilian_column'],
						jilian_column_dictionaryId:editTableRows[i]['jilian_column_dictionaryId']
						
					}

					editColumnSetXmlUtils.createNode('Text','',editColumnTextAttributes,editColumnNode);
					editColumnSetXmlUtils.createNode('Data','',editColumnDataAttributes,editColumnNode);
					editColumnSetXmlUtils.createNode('EditMode','',editColumnEditModeAttributes,editColumnNode);
					/**
					 * 规则引擎
					 */
					editColumnSetXmlUtils.createNode('RulesEngin','',editColumnEditRulesAttributes,editColumnNode);
					//editTableRows[i]['rulesRowsParmer']
					var editColumnRolesAttributes = {
						isCustomRole :editTableRows[i]['isCustomRole'],
						isCustomRoleRead :editTableRows[i]['isCustomRoleRead'],
						isCustomRoleReadId :editTableRows[i]['isCustomRoleReadId'],
						isCustomRoleReadName :editTableRows[i]['isCustomRoleReadName'],
						isCustomRoleWrite :editTableRows[i]['isCustomRoleWrite'],
						isCustomRoleWriteId :editTableRows[i]['isCustomRoleWriteId'],
						isCustomRoleWriteName :editTableRows[i]['isCustomRoleWriteName']
					};
					editColumnSetXmlUtils.createNode('Roles','',editColumnRolesAttributes,editColumnNode);
				}
			editColumnSetXmlUtils.setAttribute(editColumnsNode,'isConfig','1');
			editPageSetSubmit(editColumnSetXmlUtils.toString());
			$("#fm_formdesignedit_datacolumn").html(CONFIGYES);
			//parent.$('#fm_formdesignedit_editpageset').window('close');
		}	
		//保存数据列编辑
		function fmEditpagesetDcSubmit(){
			//操作到这步了，，，
			//把编辑页面上编辑的数据保存到datagrid的当前行里
			var rowIndex = $('#fm_editpageset_dc_fieldindex').val();
			$('#fm_editpageset_datacolumnset_fieldlist').datagrid("clearSelections");
			$('#fm_editpageset_datacolumnset_fieldlist').datagrid("selectRow",rowIndex);
			var row = $('#fm_editpageset_datacolumnset_fieldlist').datagrid("getSelected");//获得当前编辑行
			row.title = $('#fm_editpageset_dc_fieldtitle').val();
			//扩展属性
			row.dateformat=$('#fm_editpageset_dc_dateformat').combobox("getValue");
			row.rows=document.getElementById('fm_editpageset_dc_rows').value;
			row.cols=document.getElementById('fm_editpageset_dc_cols').value;
			//扩展树控件属性 param1:树是否CheckBox，param2:树根节点是否展开  boolean @GUOWEIXIN
			 var fm_editpageset_tree_isCheckBox=0;
			 var fm_editpageset_tree_isSpread=0;
				 if(document.getElementById('fm_editpageset_tree_isCheckBox').checked){
				 	fm_editpageset_tree_isCheckBox=1;
				 }
				if(document.getElementById('fm_editpageset_tree_isSpread').checked){
					fm_editpageset_tree_isSpread=1;
				}
				//@GUOWEIXIN 树控件的两个属性			
				row.isCheckBox=fm_editpageset_tree_isCheckBox;
				row.isSpread=fm_editpageset_tree_isSpread;

				
			row.groupid = $('#fm_editpageset_dc_pagegroup').combobox("getValue");
			row.tagType = $('#fm_editpageset_dc_tagtype').combobox("getValue");
			row.formula = $('#fm_editpageset_dc_formula').combobox("getValue");
			row.groupid = $('#fm_editpageset_dc_pagegroup').combobox("getValue");
			
			row.dicname = $('#fm_editpageset_dc_dictionary_id').val();
			row.dataFunction = $('#fm_editpageset_dc_function').combobox("getValue");
			row.fromdic = "否";
			if($('#fm_editpageset_dc_dictionary_id').val() != '-1')
				row.fromdic = "是";
			
			row.validateRule = $('#fm_editpageset_dc_validation').combobox("getValue");
			//长度验证
			row.maxlength2 = $('#maxlength').val();
			row.minlength = $('#minlength').val();
			
			
			//row.tdWidth=$('#td_width').val();
			//row.textWidth=$('#text_width').val();
			//这两个暂时没有
			//row.tdHeight=$('#td_height').val();			
			//row.textHeight=$('#text_height').val();
			
			
			
			
			row.visible = $(":checkbox[id='fm_editpageset_dc_visible']").attr("checked")?"false":"true";
			row.readOnly = $(":checkbox[id='fm_editpageset_dc_readonly']").attr("checked")?"true":"false";
			row.required = $(":checkbox[id='fm_editpageset_dc_require']").attr("checked")?"true":"false";
			row.exclusiveLine = $(":checkbox[id='fm_editpageset_dc_isline']").attr("checked")?"true":"false";
			
			row.isExistDBSource = $('#bd_databaseobjectimport_isExist_datasource').combobox("getValue");//选择数据源
			row.isExistSQL = $('#isExistSQL').val();//根据选择的数据源写出的SQL
			/**
			*	时间比较
			*/
			row.compDate = $('#compare_date').combobox('getValue');
			row.compCon = $('.compare_date_con:radio:checked').val();
		
			$('#fm_editpageset_datacolumnset_fieldlist').datagrid("refreshRow",rowIndex);
			///alert($('#fm_editpageset_dc_tagtype').val());
			
			//组织机构控件属性
			row.ismultipart = $(":checkbox[id='is_multipart']").attr("checked")?"true":"false";
			row.isleafcheck = $(":checkbox[id='is_leafcheck']").attr("checked")?"true":"false";
			row.isselforg = $(":checkbox[id='is_selforg']").attr("checked")?"true":"false";
			row.orgid = $('#fm_editpageset_dc_org_id_org').val();
			//应急选择控件属性
			row.issingletable = $(".emergencytreedata:radio:checked").val();
			row.isemergencytreesql = $("#is_emergencytreesql").val();

			//人员列表树控件属性
			row.ismultiparthuman = $(":checkbox[id='is_multipart_human']").attr("checked")?"true":"false";
			row.orgidhuman = $('#fm_editpageset_dc_org_id_org_human').val();

			//应急选择控件属性
			row.issingletable = $(".emergencytreedata:radio:checked").val();
			row.isemergencytreesql = $("#is_emergencytreesql").val();

			/**
			 * 插入规则引擎配置
			 */
			var rulesRowsParmer = $('#fieldparmer_grid').datagrid('getRows');
 			var rulesRowsParmerJson=JSON2.stringify(rulesRowsParmer);
			row.rulesRowsS=rulesRowsParmerJson;
			row.rulesService = encodeURIComponent($('#rulesService').val());
			/*
			*事件类型
			*/
			
 			var eventTypearr = new Array();
 			var eventTypeobj = new Object();
			$(":checkbox[name='eventType']:checked").each(function(){
				eventTypeobj[$(this).val()] = 1;
			});
			for(var item in eventTypeobj){
				if(eventTypeobj[item] == 1){
					eventTypearr[eventTypearr.length] = item;
				}
			}			
			row.eventTypes=eventTypearr;
			
			//下拉选级联
			
			row.is_jilian = $('#is_jilian').attr('checked')?"1":"0";
			if($('#is_jilian').attr('checked')){
				row.jilian_column = $('#jilian_column').combobox('getValue');
				row.jilian_column_dictionaryId = $('#fm_editpageset_dc_dictionary_id_jilian').val();
			}
			
			//是否启用列表选择数据is_listPageForvaluepageurl_listPage
			row.is_listPageForvalue =  $('#is_listPageForvalue').attr('checked')?"1":"0";
			if($('#is_listPageForvalue').attr('checked')){
				row.pageurl_listPage = $('#tabspageurl_editPage_datacolumn').combobox('getValue');
			}

			var pageset_dc_tagtype = $('#fm_editpageset_dc_tagtype').combobox('getValue');
			if(pageset_dc_tagtype=='20'){
				row.listInside = $('#editPage_list_inside').combobox('getValue');
				row.listInsideParmer = $('#editPage_list_inside_fieldparmer').val();
			}
			
			/**
			 * 是否设置变量,值
			 */
			row.variantOrnot = $(":checkbox[id='variant_ornot']").attr("checked")?"1":"0";
			row.variantType = $('#variant_type').combobox("getValue");
			row.variantValue=document.getElementById('variant_value').value;
			$('#fm_editpageset_dc_edit').window('close');
			
			row.list_value = $('#list_value').val();
			row.list_text = $('#list_text').val();
			//是否是工作流字段
			row.isworkflow = $(":checkbox[id='is_workflow']").attr("checked")?"true":"false";

			//字段权限控制
			var isRole = $(':checkbox[id=editpage_is_customRole]').attr("checked");
			row.isCustomRole = isRole?"true":"false";
			if(isRole){
				var isRead = $(':checkbox[id=is_customRole_read]').attr("checked");
				row.isCustomRoleRead = isRead?"true":"false";
				if(isRead){
					row.isCustomRoleReadId = $('#is_customRole_read_role_id').val();
					row.isCustomRoleReadName = $('#is_customRole_read_role_name').val();
				}
				var isWrite = $(':checkbox[id=is_customRole_write]').attr("checked");
				row.isCustomRoleWrite = isWrite?"true":"false";
				if(isWrite){
					row.isCustomRoleWriteId = $('#is_customRole_write_role_id').val();
					row.isCustomRoleWriteName = $('#is_customRole_write_role_name').val();
				}
			}
		}
		//DataColumnSet End
		
		function fmEditpagesetDcDictionaryWindow(state){
			if(state == 1){
				dictionary_state = state;
				
				$.ajax({
					   type: "POST",
					   url: "form/form!dictionaryTreeJson.action",
					   //url:'form/form!getDictionaryLikeName.action',
					   data: "data=aa",
					   success: function(data){
					   		var treeData=eval(data);
					   		$('#fm_editpageset_dc_dictionary_tree').tree("loadData",treeData);
					  		$('#fm_editpageset_dc_dictionary_tree').tree('select',$('#fm_editpageset_dc_dictionary_tree').tree('find',$('#fm_editpageset_dc_dictionary_id').val()).target);
							$('#fm_editpageset_dc_dictionary_window').window('open');
					   }
					});
			}else if(state == 2){
				dictionary_state = state;
				$.ajax({
					   type: "POST",
					   url: "form/form!dictionaryTreeJson.action",
					   //url:'form/form!getDictionaryLikeName.action',
					   data: "data=aa",
					   success: function(data){
					   		var treeData=eval(data);
					  		$('#fm_editpageset_dc_dictionary_tree').tree("loadData",treeData);
					  		$('#fm_editpageset_dc_dictionary_tree').tree('select',$('#fm_editpageset_dc_dictionary_tree').tree('find',$('#fm_editpageset_dc_dictionary_id_jilian').val()).target);
							$('#fm_editpageset_dc_dictionary_window').window('open');
					   }
					});
			}else{
				//$('#fm_editpageset_dc_org_tree').tree('select',$('#fm_editpageset_dc_org_tree').tree('find',$('#fm_editpageset_dc_org_id_org').val()).target);
				$('#fm_editpageset_dc_org_window').window('open');
			}
		}
    </script>
  </head>  
  <body>
<!-- 主面板-字段列表-开始 -->	
	<div class="easyui-layout" fit="true">
		<div region="center" border="false">
		    <div title="字段列表" class="easyui-panel" fit="true" headerCls="header_cls">
				<table id="fm_editpageset_datacolumnset_fieldlist" ></table>
		    </div>
		</div>
		<div region="south" style="background:#fff;height:50px;"> 
			<table border="0" cellpadding="5" cellspacing="0" width="100%" style="margin-top: 10px;">
				<tr>
					<td align="center"><a class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="fmEditpagesetDatacolumnsetSave()" >保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:;"  onclick="fmEditPageSetClose()" >关闭</a></td>										
				</tr>					
			</table>
		</div>
	</div>
<!-- 主面板-字段列表-结束 -->	

<!-- 编辑每一个字段的弹出窗口-开始 -->	
<div id="fm_editpageset_dc_edit" class="easyui-window">
	<div class="easyui-layout" style="width:550px;height:400px;">
		<div region="center" style="background:#fff;height:500px;padding-top:13px;padding-left:13px;">
			<div class="easyui-layout" style="width:550px;height:600px;">
				<!-- 上部分开始 -->
				<div region="north" border="false" style="overflow:hidden;height:310px;background:#fff;">
					<table style="font-size:12px;" width="100%">			
						<tr>	
							<td align="right" width="85px">字段：</td>
						  	<td width="215px"><input id="fm_editpageset_dc_fieldindex" type="hidden"/><span id="fm_editpageset_dc_fieldname"></span></td>
						  	<td align="right" width="100px">标题：</td>
							<td width="215px"><input id="fm_editpageset_dc_fieldtitle" class="easyui-validatebox" style="width:145px;"/></td>
						</tr>
						<tr>
							<td align="right" >组件类型：</td>
							<td><select id="fm_editpageset_dc_tagtype" class="easyui-combobox" listWidth="148" style="width:129px;">
								</select>
							</td>
							<td align="right">数据字典：</td>
							<td><!-- <select id="fm_editpageset_dc_dictionary" class="easyui-combotree" style="width:131px;">
								</select> -->
								<input id="fm_editpageset_dc_dictionary_id" type="hidden" />
								<input id="fm_editpageset_dc_dictionary_text" type="text" onfocus="fmEditpagesetDcDictionaryWindow(1)" style="width:147px;cursor:pointer;" readOnly="true"/>
							</td>
						</tr>
					 	<div style="display: none;"  >
				 			<select id="fm_editpageset_dc_formula" class="easyui-combobox" listWidth="148" style="width:129px;">
							</select>
						</div>
						<div style="display: none;"  >
							<select id="fm_editpageset_dc_function" class="easyui-combobox" listWidth="148" style="width:129px;">
							</select>
						</div>
					   <tr>			
							<td align="right">验证规则：</td>
							<td><select id="fm_editpageset_dc_validation" class="easyui-combobox" listWidth="148" style="width:129px;">
								</select><a href="pages/framenew.jsp?validationRule=1" target="_blank">管理</a>
							</td>
							<td align="right">所属分组：</td>
						  	<td><select id="fm_editpageset_dc_pagegroup" class="easyui-combobox" style="width:129px;">
								</select>
							</td>
						</tr> 
						
					   <tr>		
							<td align="left" colspan="4">
				  				 <div id="compare_date_div" style="display:none;" >
				  				 	条件： <input type="radio" id="compare_date_con_rt" value="rt" name="compare_date_con" class="compare_date_con" checked="checked">大于<input type="radio" id="compare_date_con_lt" class="compare_date_con" value="lt" name="compare_date_con">小于<br />
									日期比较字段：<input id="compare_date" class="easyui-combobox" listWidth="148" style="width:129px;"/>
								 </div>
							</td>
						</tr>
						<tr id="lengthRule_tr">		
							<td  align="right" width="85px">
				  				 	最小长度： 
							</td>
							<td  width="215px">
				  				 	<input type="text" id="minlength" validType="number">
							</td>
							<td  align="right" width="100px">
				  				 	最大长度： 
							</td>
							<td  width="215px">
				  				 	<input type="text" id="maxlength" validType="number">
				  				 	<input type="hidden" id="maxlength_db" />
							</td>
						</tr>
						<tr >		
							<td  align="right" width="85px"  >
				  				 	标题宽度： 
							</td>
							<td  width="215px">
				  				 	<input type="text" id="td_width" validType="number" >
							</td>
							<td align="right" width="100px">
				  				 	文本框宽度： 
							</td>
							<td  width="215px">
				  				 	<input type="text" id="text_width" validType="number" >
							</td>
						</tr>
					
			    		<tr>
							<td align="center"><input type="checkbox" id="fm_editpageset_dc_isline"/> 独占行</td>
							<td align="center"><input type="checkbox" id="fm_editpageset_dc_visible"/> 隐藏</td>
							<td align="center"><input type="checkbox" id="fm_editpageset_dc_readonly"/> 只读</td>
							<td align="center"><input type="checkbox" id="fm_editpageset_dc_require"/> 必填项</td>
						</tr>
			    		<tr>
							<td align="center" colspan="4">
								<table width="100%">
									<tr>
										<td align="center"><input type="checkbox" id="variant_ornot"/> 是否设置变量</td>
										<td align="left">
											<select id="variant_type" class="easyui-combobox" listWidth="148" style="width:129px;">
											</select>
										</td>
										<td align="left">值：<input type="text" id="variant_value"/></td>
										<td align="left"><input type="checkbox" id="is_workflow"/> 是否工作流字段</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center" colspan="4">
								<table width="100%">
									<tr>
										<td align="left"><input type="checkbox" id="is_jilian"/> 是否级联</td>
										<td align="right">
											选择字段：
										</td>
										<td align="left">
											<input id="jilian_column" class="easyui-combobox" listWidth="148" style="width:129px;"/>
										</td>
										<td align="left">数据字典：
											<input id="fm_editpageset_dc_dictionary_id_jilian" type="hidden" />
											<input id="fm_editpageset_dc_dictionary_text_jilian" type="text" onfocus="fmEditpagesetDcDictionaryWindow(2)" style="width:147px;cursor:pointer;" readOnly="true"/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center" colspan="4">
								<table width="100%">
									<tr>
										<td align="left"><input type="checkbox" id="is_listPageForvalue"/>是否启用列表选择数据</td>
										<td align="right">
											选择list页：
										</td>
										<td align="left" colspan="2">
											<select id="tabspageurl_editPage_datacolumn" style="width:260px;"></select>
										</td>
										
									</tr>
									<tr>
										<td align="left">list页value项列名：</td>
										<td align="right"><input id="list_value" /></td>
										<td align="left">list页text项列名：</td>
										<td align="right"><input id="list_text" /></td>
									</tr>
								</table>
							</td>
						</tr>						
						<tr>
							<td align="center" colspan="4">
								<table width="100%">
									<tr>
										<td align="left">list页value项列名：</td>
										<td align="right"><input id="list_value" /></td>
										<td align="left">list页text项列名：</td>
										<td align="right"><input id="list_text" /></td>
									</tr>
								</table>
							</td>
						</tr>						
					</table>
				</div>
				<!-- 上部分结束 -->
				<!-- 中间部分-开始 -->
				<div region="center" border="false" style="background:#fff;height:150px;">
					<table style="font-size:12px;" width="100%">
						<tr>
							<td colspan="4" class="header_cls" height="35px">
								字段扩展属性
							</td>
						</tr>		
						<!-- 嵌入list列表页属性 -->
						<tr id="editPage_list_inside_tr2" style="display: none;">
							<td colspan="2" align="right">
								选择list页：
							</td>
							<td align="left" colspan="3">
								<select id="editPage_list_inside" style="width:260px;"></select>
							</td>
						</tr>
						<tr id="editPage_list_inside_tr3" style="display: none;">
							<td colspan="2">
								list列表页参数
							</td>
							<td colspan="3">
								<input type="text" id="editPage_list_inside_fieldparmer" />
							</td>
						</tr>
						<!-- 列表页控件  -->
						<tr>
							<td colspan="1" width="85px" align="right">
								字段值唯一性验证SQL数据源：
							</td>
							<td colspan="3" >
								<select id="bd_databaseobjectimport_isExist_datasource"  name="isExistDBSource"  required="true" style="width: 150px"></select>
							</td>
						</tr>
						<tr>
							<td colspan="1" align="right">
								字段值唯一性验证SQL配置：
							</td>
							<td colspan="3" >
								<textarea id="isExistSQL" rows="3" cols="46"></textarea>
							</td>
						</tr>
						<tr>
							<td colspan="4"  ><jsp:include page="extended-attributes.jsp"></jsp:include>		  
							</td>
						</tr>
					</table>
				</div>
				<!-- 中间部分结束 -->
				<!-- 下部分-开始 -->
				<div region="south" border="false" style="height:320px;background:#fff;">
					<table style="font-size:12px;" width="100%">
						<tr>
							<td colspan="5" class="td-title" height="35px">
								控件规则引擎配置
							</td>
						</tr>		
						<tr>
							<td align="left" colspan="5" ><input type="checkbox" value='onclick' name="eventType"> onclick &nbsp; &nbsp; 
							<input type="checkbox" value='onchange'  name="eventType"> onchange &nbsp; &nbsp; 
							<input type="checkbox" value='onblur'  name="eventType"> onblur &nbsp; &nbsp; 
							<input type="checkbox" value='focus'  name="eventType"> focus &nbsp; &nbsp; 
							<input type="checkbox" value='onkeyup'  name="eventType"> onkeyup &nbsp; &nbsp; </td>
						</tr>	
						<tr>
							<td colspan="5"  >
								<table  id="fieldparmer_grid" ></table>
							</td>
						</tr>
						<tr>
							<td colspan="5" align="left">
								<label for="rulesService">业务逻辑：</label>
								<textarea id="rulesService" rows="3" cols="46" style="vertical-align: middle;"></textarea><br/>
								<span style="width:130px;"></span><font color='red'>(注意：字符串变量不要用单引号，要用双引号)</font>
							</td>
						</tr>
						<tr>
							<td colspan="5" class="td-title" height="35px">
								组织机构控件属性
							</td>
						</tr>	
						<tr>
							<td align="left" width="215px" >组织机构：
								<input id="fm_editpageset_dc_org_id_org" type="hidden" />
								<input id="fm_editpageset_dc_org_text_org" type="text" onfocus="fmEditpagesetDcDictionaryWindow(3)" style="width:120px;cursor:pointer;" readOnly="true"/>
							</td>
							<td align="center" colspan="4"><input type="checkbox" id="is_multipart"> 多选 &nbsp; &nbsp; 
							<input type="checkbox" id="is_leafcheck">子节点可选 &nbsp; &nbsp; 
							<input type="checkbox" id="is_selforg">当前部门可选 &nbsp; &nbsp; </td>
						</tr>
						<tr>
							<td colspan="5" class="td-title" height="35px">
								人员列表页树控件属性
							</td>
						</tr>	
						<tr>
							<td align="left" width="215px" colspan="2">组织机构：
								<input id="fm_editpageset_dc_org_id_org_human" type="hidden" />
								<input id="fm_editpageset_dc_org_text_org_human" type="text" onfocus="fmEditpagesetDcDictionaryWindow(3)" style="cursor:pointer;" readOnly="true"/>
							</td>
							<td align="left" colspan="3" ><input type="checkbox" id="is_multipart_human"> 多选</td>
						</tr>	
						<tr>
							<td colspan="5" class="td-title" height="35px">
								应急选择树控件属性
							</td>
						</tr>	
						<tr>
						<tr>
							<td align="left" colspan="5" >数据来源：<span style="width:30px;"></span>
							<input type="radio" class="emergencytreedata" name="emergencytreedata" value="1" id="is_singletable"> 单表数据 &nbsp; &nbsp; 
							<input type="radio" class="emergencytreedata" name="emergencytreedata" value="2" id="is_moretable"> 两个表数据 &nbsp; &nbsp;
							</td>
						</tr>
						<tr>
							<td align="left" colspan="5" ><label for="is_emergencytreesql">sql：</label>
								<textarea id="is_emergencytreesql" rows="3" cols="46" style="vertical-align: middle;"></textarea>
							</td>
						</tr>	
						<tr>
							<td colspan="4" class="td-title" height="50px">
								字段访问权限控件属性
							</td>
						</tr>
						<tr>
							<td colspan="5">
							  <input type="checkbox" id="editpage_is_customRole">自定义权限
							  <div id="per_read<%=uuid%>"/></div>
							  <div id="per_write<%=uuid%>"/></div>
							  <table id="is_customRole_table" style="display: none;">
							  	<tr>
									<td align="left" width="350px" >
									  <input type="checkbox" id="is_customRole_read">可读&nbsp;&nbsp;&nbsp;
									  <div id="is_customRole_read_show" style="display: none;">
									    <a id="fm_editpageset_dc_role_read" class="easyui-linkbutton" onclick="editpage_choose_role('is_customRole_read_role_id','is_customRole_read_role_name','per_read<%=uuid%>');">选择可读角色</a>
									    <input id="is_customRole_read_role_id" type="hidden" value="">
									    &nbsp;可读角色：<input id="is_customRole_read_role_name" type="text" value="" readOnly />
									  </div>
									</td>
								</tr>
								<tr>
									<td align="left" width="350px" >
									  <input type="checkbox" id="is_customRole_write">读写&nbsp;&nbsp;&nbsp;
									  <div id="is_customRole_write_show" style="display: none;">
									    <a id="fm_editpageset_dc_role_write" class="easyui-linkbutton" onclick="editpage_choose_role('is_customRole_write_role_id','is_customRole_write_role_name','per_write<%=uuid%>');">选择读写角色</a>
									    <input id="is_customRole_write_role_id" type="hidden" value="">
									    &nbsp;读写角色：<input id="is_customRole_write_role_name" type="text" value="" readOnly />
									  </div>
									</td>
								</tr>
							  </table>
							</td>
						</tr>
						<tr >
							<td colspan="5" align="center">
								<a id="fm_editpageset_dc_save" class="easyui-linkbutton"
									icon="icon-save" href="javascript:;"
									onclick="fmEditpagesetDcSubmit()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<a id="fm_editpageset_dc_cancel" class="easyui-linkbutton"
									icon="icon-cancel" href="javascript:;"
									onclick="$('#fm_editpageset_dc_edit').window('close')">关闭</a>
							</td>
						</tr>
					</table>
				</div>
				<!-- 下部分结束 -->
			</div>
		</div>
	</div>
</div>		
<!-- 编辑每一个字段的弹出窗口-结束 -->
	
	
	
<!-- 数据字典窗口-开始 -->
	<div id="fm_editpageset_dc_dictionary_window" class="easyui-window" style="padding:5px;background: #fff;">	
		<ul id="fm_editpageset_dc_dictionary_tree"></ul>		
 	</div>
<!-- 数据字典窗口-结束 -->
<!-- 组织机构树窗口-开始 -->
	<div id="fm_editpageset_dc_org_window" class="easyui-window" style="padding:5px;background: #fff;">	
		<ul id="fm_editpageset_dc_org_tree"></ul>		
 	</div>
<!-- 组织机构树窗口-结束 -->
  </body>
</html>