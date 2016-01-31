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
			$('#isOriginalSql_checkbox').click(function(){
  				if($('#isOriginalSql_checkbox').is(':checked')){
  					$('#isOriginalSql_checkbox').attr('checked',true);
  					$('#isOriginalSql_hidden').val('on');
  					$('#isOriginalSql_div').show();
				    $("#formListDesBasicPart").css({height:228});
				    $("#formListDesBasicPartPanel").panel('resize');
  				}else{
  					$('#isOriginalSql_checkbox').removeAttr('checked');
  					$('#isOriginalSql_hidden').val('off');
  					$('#isOriginalSql_div').hide();
				    $("#formListDesBasicPart").css({height:115});
				    $("#formListDesBasicPartPanel").panel('resize');
  				};
  			});
			$('#isPseudoDeleted_checkbox').click(function(){
  				if($('#isPseudoDeleted_checkbox').is(':checked')){
  					$('#isPseudoDeleted_checkbox').attr('checked',true);
  					$('#isPseudoDeleted_hidden').val('on');
  				}else{
  					$('#isPseudoDeleted_checkbox').removeAttr('checked');
  					$('#isPseudoDeleted_hidden').val('off');
  				};
  			});
			//添加页 GUOWEIXIN
			$('#fm_listpageset_selectaddpage').combobox({
            	treeWidth:157,
            	valueField:'id',
   				textField:'text',  
			    url:'form/form!getEditPageList.action?mainObjectId=' + mainObjectId,
			    editable:true
			});
			
			
			$('#fm_listpageset_selecteditpage').combobox({
            	treeWidth:157,
            	valueField:'id',
   				textField:'text',  
			    url:'form/form!getEditPageList.action?mainObjectId=' + mainObjectId,
			    editable:true
			});
            $('#fm_listpageset_selectviewpage').combobox({
           	 	treeWidth:157,
           	 	valueField:'id',
   				textField:'text',  
			    url:'form/form!getViewPageList.action?mainObjectId=' + mainObjectId,
			    editable:true
			});
            $('#fm_listpageset_selectcopypage').combobox({
           	 	treeWidth:157,
           	 	valueField:'id',
   				textField:'text',  
			    url:'form/form!getEditPageList.action?mainObjectId=' + mainObjectId,
			    editable:true
			});
			var initXmlUtils = new XmlUtils({dataType:'json'}); 
			initXmlUtils.loadXmlString(formSettings);
			var rootNode = initXmlUtils.getRoot();	
			$("#fm_listpageset_pageset_title").val(rootNode.getAttribute('title'));
			$("#fm_listpageset_pageset_css").val(rootNode.getAttribute('css'));
			$("#fm_listpageset_pageset_cssclass").val(rootNode.getAttribute('cssClass'));
			
			var isshowsingleSelect=rootNode.getAttribute("isshowsingleSelect");
			if("true"==isshowsingleSelect){
				$("#fm_listpageset_isshowsingleSelect").attr("checked",true);
			}else{
				$("#fm_listpageset_isshowsingleSelect").removeAttr("checked");
			}
			//  "是否对当前表单操作加入记录 isSystemActionlog"
			var isSystemActionlog=rootNode.getAttribute("isSystemActionlog");
			if("true"==isSystemActionlog){
				$("#isSystemActionlog").attr("checked",true);
			}else{
				$("#isSystemActionlog").removeAttr("checked");
			}
			
			
			var isPseudoDeleted = rootNode.getAttribute('isPseudoDeleted');
			$('#isPseudoDeleted_hidden').val(isPseudoDeleted);
			if('on'==isPseudoDeleted){
				$('#isPseudoDeleted_checkbox').attr('checked',true);
			}else{
				$('#isPseudoDeleted_checkbox').removeAttr('checked');
			}

			var isOriginalSql = rootNode.getAttribute('isOriginalSql');
			$('#isOriginalSql_hidden').val(isOriginalSql);
			if('on'==isOriginalSql){
				$('#isOriginalSql_checkbox').attr('checked',true);
				$('#isOriginalSql_div').show();
				$("#formListDesBasicPart").css({height:228});
				//$("#formListDesBasicPartPanel").panel('resize');
				$('#isOriginalSql_context').val(rootNode.getAttribute('isOriginalSql_context'));
				$('#isOriginalSql_param').val(rootNode.getAttribute('isOriginalSql_param'));
			}else{
				$('#isOriginalSql_checkbox').removeAttr('checked');
				$('#isOriginalSql_div').hide();
				$("#formListDesBasicPart").css({height:115});

			}
			
			var listQueryZoneNode = initXmlUtils.getChildNodeByTagName(rootNode,'QueryZone');	
			if(listQueryZoneNode && listQueryZoneNode.getAttribute('showType') == '1'){
				$('#fm_listpageset_isshowquery').attr('checked',true);
			}else{
				$('#fm_listpageset_isshowquery').attr('checked',false);
			}
			
			var listButtonsNode = initXmlUtils.getChildNodeByTagName(rootNode,'Buttons');
			//查看列
			var listViewNode = initXmlUtils.hasNodeByAttribute(listButtonsNode,'type',LIST_BUTTON_SINGLE_VIEW);
			if(listViewNode && (listViewNode.getAttribute('visible')==true || listViewNode.getAttribute('visible') == 'true')){
				$(":checkbox[id='fm_listpageset_isshowview']").attr("checked",true);
			}else{
				$(":checkbox[id='fm_listpageset_isshowview']").attr("checked",false);
			}
			
			//编辑列			
			var listEditNode = initXmlUtils.hasNodeByAttribute(listButtonsNode,'type',LIST_BUTTON_SINGLE_UPDATE);
			if(listEditNode && (listEditNode.getAttribute('visible')==true || listEditNode.getAttribute('visible') == 'true')){
				$(":checkbox[id='fm_listpageset_isshowupdate']").attr("checked",true);
			}else{
				$(":checkbox[id='fm_listpageset_isshowupdate']").attr("checked",false);
			}
					
			//删除列			
			var listDeleteNode = initXmlUtils.hasNodeByAttribute(listButtonsNode,'type',LIST_BUTTON_SINGLE_DELETE);
			if(listDeleteNode && (listDeleteNode.getAttribute('visible')==true || listDeleteNode.getAttribute('visible') == 'true')){
				$(":checkbox[id='fm_listpageset_isshowdelete']").attr("checked",true);
			}else{
				$(":checkbox[id='fm_listpageset_isshowdelete']").attr("checked",false);
			}
			
			//复制列			
			var listCopyNode = initXmlUtils.hasNodeByAttribute(listButtonsNode,'type',LIST_BUTTON_SINGLE_COPY);
			if(listCopyNode && (listCopyNode.getAttribute('visible')==true || listCopyNode.getAttribute('visible') == 'true')){
				$(":checkbox[id='fm_listpageset_isshowcopy']").attr("checked",true);
			}else{
				$(":checkbox[id='fm_listpageset_isshowdcopy']").attr("checked",false);
			}
						
			//添加按钮			
			var listAddNode = initXmlUtils.hasNodeByAttribute(listButtonsNode,'type',LIST_BUTTON_MUTI_ADD);
			if(listAddNode && (listAddNode.getAttribute('visible')==true || listAddNode.getAttribute('visible') == 'true')){
				$(":checkbox[id='fm_listpageset_isshowadd']").attr("checked",true);
			}else{
				$(":checkbox[id='fm_listpageset_isshowadd']").attr("checked",false);
			}
			
			//操作列靠前还是操作列靠后显示
			var listEditColumns = initXmlUtils.getChildNodeByTagName(rootNode,'EditColumnsSet');
			if(listEditColumns != null){			
				var editColumnsValue = listEditColumns.getAttribute('setValue');				
				if(editColumnsValue == LIST_OPERATE_COLUMNS_BEFORE){
					$("input[name=operate]:eq(1)").attr("checked",'checked'); 
				}else if(editColumnsValue == LIST_OPERATE_COLUMNS_END){
					$("input[name=operate]:eq(0)").attr("checked",'checked');
				}
			}else{
				$("input[name=operate]:eq(0)").attr("checked",'checked');//靠后
			}		
			//批量删除按钮			
			var listMutiDeleteNode = initXmlUtils.hasNodeByAttribute(listButtonsNode,'type',LIST_BUTTON_MUTI_DELETE);
			if(listMutiDeleteNode && (listMutiDeleteNode.getAttribute('visible')==true || listMutiDeleteNode.getAttribute('visible') == 'true')){
				$(":checkbox[id='fm_listpageset_isshowmutidelete']").attr("checked",true);
			}else{
				$(":checkbox[id='fm_listpageset_isshowmutidelete']").attr("checked",false);
			}
			//显示导出按钮			
			var listDaochuNode = initXmlUtils.hasNodeByAttribute(listButtonsNode,'type',LIST_BUTTON_EXPORT);
			if(listDaochuNode && (listDaochuNode.getAttribute('visible')==true || listDaochuNode.getAttribute('visible') == 'true')){
				$(":checkbox[id='fm_listpageset_isshowexport']").attr("checked",true);
			}else{
				$(":checkbox[id='fm_listpageset_isshowexport']").attr("checked",false);
			}
			
			//显示导入按钮			
			var listDaoruNode = initXmlUtils.hasNodeByAttribute(listButtonsNode,'type',LIST_BUTTON_IMPORT);
			if(listDaoruNode && (listDaoruNode.getAttribute('visible')==true || listDaoruNode.getAttribute('visible') == 'true')){
				$(":checkbox[id='fm_listpageset_isshowimport']").attr("checked",true);
			}else{
				$(":checkbox[id='fm_listpageset_isshowimport']").attr("checked",false);
			}
			
			//分页
			var listPaginationNode = initXmlUtils.getChildNodeByTagName(rootNode,'Pagination');	
			if(listPaginationNode && (listPaginationNode.getAttribute('visible')==true || listPaginationNode.getAttribute('visible') == 'true')){
				$(":checkbox[id='fm_listpageset_isshowpage']").attr("checked",true);
			}else{
				$(":checkbox[id='fm_listpageset_isshowpage']").attr("checked",false);
			}
			//页面
			var listPagesNode = initXmlUtils.getChildNodeByTagName(rootNode,'Pages');	
			
			//添加 GUOWEIXIN
			var listAddPageNode = initXmlUtils.getChildNodeByTagName(listPagesNode,'AddPage');
			if(listAddPageNode){
				var addFormId = listAddPageNode.getAttribute('formId');
				var addFormText = listAddPageNode.getAttribute('formName');
				if(addFormId && addFormId != null){
					//$('#fm_listpageset_selecteditpage').combobox('setValues',{id:editFormId,text:editFormText});
					$('#fm_listpageset_selectaddpage').combobox('setValue',addFormId);
				} 
			}
			
			var listEditPageNode = initXmlUtils.getChildNodeByTagName(listPagesNode,'EditPage');
			if(listEditPageNode){
				var editFormId = listEditPageNode.getAttribute('formId');
				var editFormText = listEditPageNode.getAttribute('formName');
				if(editFormId && editFormId != null){
					//$('#fm_listpageset_selecteditpage').combobox('setValues',{id:editFormId,text:editFormText});
					$('#fm_listpageset_selecteditpage').combobox('setValue',editFormId);
				} 
			}
			var listViewPageNode = initXmlUtils.getChildNodeByTagName(listPagesNode,'ViewPage');
			if(listViewPageNode){
				var viewFormId = listViewPageNode.getAttribute('formId');
				var viewFormText = listViewPageNode.getAttribute('formName'); 
				if(viewFormId && viewFormId != null){
					//$('#fm_listpageset_selectviewpage').combobox('setValue',{id:viewFormId,text:viewFormText});
					$('#fm_listpageset_selectviewpage').combobox('setValue',viewFormId);
				}
			}
			var listCopyPageNode = initXmlUtils.getChildNodeByTagName(listPagesNode,'CopyPage');
			if(listCopyPageNode){
				var copyFormId = listCopyPageNode.getAttribute('formId');
				var copyFormText = listCopyPageNode.getAttribute('formName'); 
				if(copyFormId && copyFormId != null){
					//$('#fm_listpageset_selectcopypage').combobox('setValue',{id:copyFormId,text:copyFormText});
					$('#fm_listpageset_selectcopypage').combobox('setValue',copyFormId);
				}
			}
		});
		
		/*
		*	保存页面值到xml中
		*/
		function fmListpagesetPagesetSave(){
			var pageSetXmlUtils = new XmlUtils({dataType:'json'}); 
			pageSetXmlUtils.loadXmlString(formSettings);
			var rootNode = pageSetXmlUtils.getRoot();
			pageSetXmlUtils.setAttribute(rootNode,'title',$("#fm_listpageset_pageset_title").val());
			pageSetXmlUtils.setAttribute(rootNode,'css',$("#fm_listpageset_pageset_css").val());
			pageSetXmlUtils.setAttribute(rootNode,'cssClass',$("#fm_listpageset_pageset_cssclass").val());
			pageSetXmlUtils.setAttribute(rootNode,'isPseudoDeleted',$("#isPseudoDeleted_hidden").val());
			
			//GUOWEIXIN  "fm_listpageset_isshowsingleSelect"
			if($('#fm_listpageset_isshowsingleSelect').attr('checked')=="true" || $('#fm_listpageset_isshowsingleSelect').attr('checked')==true){
				pageSetXmlUtils.setAttribute(rootNode,'isshowsingleSelect',"true");
			}else{
				pageSetXmlUtils.setAttribute(rootNode,'isshowsingleSelect',"false");
			}
			//GUOWEIXIN  "是否对当前表单操作加入记录 isSystemActionlog"
			if($('#isSystemActionlog').attr('checked')=="true" || $('#isSystemActionlog').attr('checked')==true){
				pageSetXmlUtils.setAttribute(rootNode,'isSystemActionlog',"true");
			}else{
				pageSetXmlUtils.setAttribute(rootNode,'isSystemActionlog',"false");
			}
			
			
			
			pageSetXmlUtils.setAttribute(rootNode,'isOriginalSql',$("#isOriginalSql_hidden").val());
			pageSetXmlUtils.setAttribute(rootNode,'isOriginalSql_context',$("#isOriginalSql_context").val());
			pageSetXmlUtils.setAttribute(rootNode,'isOriginalSql_param',$("#isOriginalSql_param").val());
			//是否显示组合查询
			var listQueryZoneNode = pageSetXmlUtils.getChildNodeByTagName(rootNode,'QueryZone');	
			if($('#fm_listpageset_isshowquery').attr('checked')){
				pageSetXmlUtils.setAttribute(listQueryZoneNode,'showType','1');
			}else{
				pageSetXmlUtils.setAttribute(listQueryZoneNode,'showType','0');
			}
			var xmlFieldDefNode = pageSetXmlUtils.getNodesByXpath('//DataMapping/DataSet/Table/FieldDef',1);
			var xmlFieldNodes = pageSetXmlUtils.getChildNodes(xmlFieldDefNode);
			var primarykeyColumnType = "";
			var primarykeyColumnName = "";
			if(xmlFieldNodes && xmlFieldNodes!=null){
				for(var i=0;i<xmlFieldNodes.length;i++){
					var xmlFieldNode = xmlFieldNodes[i];					
					var xmlFromFieldNode = pageSetXmlUtils.getChildNodeByTagName(xmlFieldNode,'FromField');
					var xmlIsPrimeKeyNode = pageSetXmlUtils.getChildNodeByTagName(xmlFromFieldNode,'IsPrimeKey');
					var xmlIsPrimeKeyFlag = pageSetXmlUtils.getInnerText(xmlIsPrimeKeyNode);
					if(xmlIsPrimeKeyFlag == true || xmlIsPrimeKeyFlag == 'TRUE' || xmlIsPrimeKeyFlag == 'true'){
						var xmlFieldNameNode = pageSetXmlUtils.getChildNodeByTagName(xmlFromFieldNode,'FieldName');
						var xmlTableNameNode = pageSetXmlUtils.getChildNodeByTagName(xmlFieldNameNode,'TableName');
						var xmlNameNode = pageSetXmlUtils.getChildNodeByTagName(xmlFieldNameNode,'Name');
						if(pageSetXmlUtils.getInnerText(xmlTableNameNode) == mainObjectName){
							var xmlTypeNode = pageSetXmlUtils.getChildNodeByTagName(xmlFromFieldNode,'Type');
							primarykeyColumnType = pageSetXmlUtils.getInnerText(xmlTypeNode);
							primarykeyColumnName += primarykeyColumnName == ""?pageSetXmlUtils.getInnerText(xmlNameNode):',' + pageSetXmlUtils.getInnerText(xmlNameNode);
						}
					} 
				}
			}
			var listButtonsNode = pageSetXmlUtils.getChildNodeByTagName(rootNode,'Buttons');
			//是否显示查看列
			var listViewNode = pageSetXmlUtils.hasNodeByAttribute(listButtonsNode,'type',LIST_BUTTON_SINGLE_VIEW);
			if($('#fm_listpageset_isshowview').attr('checked')){
				if(listViewNode){
					pageSetXmlUtils.setAttribute(listViewNode,'visible','true');
				}else{
					var viewButtonNode = pageSetXmlUtils.createNode('Button','',{id:'',type:LIST_BUTTON_SINGLE_VIEW,name:'view',visible:'true'},listButtonsNode);
					var viewEventNode = pageSetXmlUtils.createNode('Event','',{Event:'1',JSId:''},viewButtonNode);
					pageSetXmlUtils.createNode('Param','',{key:primarykeyColumnName,fieldDataType:primarykeyColumnType,value:primarykeyColumnName},viewEventNode);
				}
			}else{
				if(listViewNode){
					pageSetXmlUtils.setAttribute(listViewNode,'visible','false');
				}
			}
			//是否显示编辑列	
			var listEditNode = pageSetXmlUtils.hasNodeByAttribute(listButtonsNode,'type',LIST_BUTTON_SINGLE_UPDATE);
			if($('#fm_listpageset_isshowupdate').attr('checked')){
				if(listEditNode){
					pageSetXmlUtils.setAttribute(listEditNode,'visible','true');
				}else{
					var updateButtonNode = pageSetXmlUtils.createNode('Button','',{id:'',type:LIST_BUTTON_SINGLE_UPDATE,name:'update',visible:'true'},listButtonsNode);
					var updateEventNode = pageSetXmlUtils.createNode('Event','',{Event:'1',JSId:''},updateButtonNode);
					pageSetXmlUtils.createNode('Param','',{key:primarykeyColumnName,fieldDataType:primarykeyColumnType,value:primarykeyColumnName},updateEventNode);
				}
			}else{
				if(listEditNode){
					pageSetXmlUtils.setAttribute(listEditNode,'visible','false');
				}
			}			
			//是否显示删除列			
			var listDeleteNode = pageSetXmlUtils.hasNodeByAttribute(listButtonsNode,'type',LIST_BUTTON_SINGLE_DELETE);
			if($('#fm_listpageset_isshowdelete').attr('checked')){
				if(listDeleteNode){
					pageSetXmlUtils.setAttribute(listDeleteNode,'visible','true');
				}else{
					var deleteButtonNode = pageSetXmlUtils.createNode('Button','',{id:'',type:LIST_BUTTON_SINGLE_DELETE,name:'delete',visible:'true'},listButtonsNode);
					var deleteEventNode = pageSetXmlUtils.createNode('Event','',{Event:'1',JSId:''},deleteButtonNode);
					pageSetXmlUtils.createNode('Param','',{key:primarykeyColumnName,fieldDataType:primarykeyColumnType,value:primarykeyColumnName},deleteEventNode);
				}
			}else{
				if(listDeleteNode){
					pageSetXmlUtils.setAttribute(listDeleteNode,'visible','false');
				}
			}		
			//是否显示复制列			
			var listCopyNode = pageSetXmlUtils.hasNodeByAttribute(listButtonsNode,'type',LIST_BUTTON_SINGLE_COPY);
			if($('#fm_listpageset_isshowcopy').attr('checked')){
				if(listCopyNode){
					pageSetXmlUtils.setAttribute(listCopyNode,'visible','true');
				}else{
					var copyButtonNode = pageSetXmlUtils.createNode('Button','',{id:'',type:LIST_BUTTON_SINGLE_COPY,name:'copy',visible:'true'},listButtonsNode);
					var copyEventNode = pageSetXmlUtils.createNode('Event','',{Event:'1',JSId:''},copyButtonNode);
					pageSetXmlUtils.createNode('Param','',{key:primarykeyColumnName,fieldDataType:primarykeyColumnType,value:primarykeyColumnName},copyEventNode);
				}
			}else{
				if(listCopyNode){
					pageSetXmlUtils.setAttribute(listCopyNode,'visible','false');
				}
			}			
			//是否显示添加按钮			
			var listAddNode = pageSetXmlUtils.hasNodeByAttribute(listButtonsNode,'type',LIST_BUTTON_MUTI_ADD);
			if($('#fm_listpageset_isshowadd').attr('checked')){
				if(listAddNode){
					pageSetXmlUtils.setAttribute(listAddNode,'visible','true');
				}else{
					pageSetXmlUtils.createNode('Button','',{id:'',type:LIST_BUTTON_MUTI_ADD,name:'add',visible:'true'},listButtonsNode);
				}
			}else{
				if(listAddNode){
					pageSetXmlUtils.setAttribute(listAddNode,'visible','false');
				}
			}			
			
			//是否显示批量删除按钮			
			var listMutiDeleteNode = pageSetXmlUtils.hasNodeByAttribute(listButtonsNode,'type',LIST_BUTTON_MUTI_DELETE);
			if($('#fm_listpageset_isshowmutidelete').attr('checked')){
				if(listMutiDeleteNode){
					pageSetXmlUtils.setAttribute(listMutiDeleteNode,'visible','true');
				}else{
					pageSetXmlUtils.createNode('Button','',{id:'',type:LIST_BUTTON_MUTI_DELETE,name:'deleteBatch',visible:'true'},listButtonsNode);
				}
			}else{
				if(listMutiDeleteNode){
					pageSetXmlUtils.setAttribute(listMutiDeleteNode,'visible','false');
				}
			}
			
			//是否显示导入按钮			
			var listDaoruNode = pageSetXmlUtils.hasNodeByAttribute(listButtonsNode,'type',LIST_BUTTON_IMPORT);
			if($('#fm_listpageset_isshowimport').attr('checked')){
				if(listDaoruNode){
					pageSetXmlUtils.setAttribute(listDaoruNode,'visible','true');
				}else{
					pageSetXmlUtils.createNode('Button','',{id:'',type:LIST_BUTTON_IMPORT,name:'import',visible:'true'},listButtonsNode);
				}
			}else{
				if(listDaoruNode){
					pageSetXmlUtils.setAttribute(listDaoruNode,'visible','false');
				}
			}
			
			//是否显示导出按钮			
			var listDaochuNode = pageSetXmlUtils.hasNodeByAttribute(listButtonsNode,'type',LIST_BUTTON_EXPORT);
			if($('#fm_listpageset_isshowexport').attr('checked')){
				if(listDaochuNode){
					pageSetXmlUtils.setAttribute(listDaochuNode,'visible','true');
				}else{
					pageSetXmlUtils.createNode('Button','',{id:'',type:LIST_BUTTON_EXPORT,name:'export',visible:'true'},listButtonsNode);
				}
			}else{
				if(listDaochuNode){
					pageSetXmlUtils.setAttribute(listDaochuNode,'visible','false');
				}
			}
			
			//操作列靠前还是操作列靠后显示
			var listEditColumns = pageSetXmlUtils.getChildNodeByTagName(rootNode,'EditColumnsSet');
			var columnOrderValue = $("input[name='operate']:checked").val()+'';
			//var columnOrderValueAttribute=eval('{setValue:'+'"'+ columnOrderValue +'"'+ '}');
			
			var jsonStr='{"setValue":'+'"'+ columnOrderValue +'"'+ '}';
			var columnOrderValueAttribute=eval("("+jsonStr+")");	
			if(columnOrderValue == LIST_OPERATE_COLUMNS_BEFORE || columnOrderValue == LIST_OPERATE_COLUMNS_END){
				
				if(typeof(listEditColumns) =="undefined" || listEditColumns==null){
					pageSetXmlUtils.createNode('EditColumnsSet', '', columnOrderValueAttribute, rootNode, false);
				}else{
					pageSetXmlUtils.setAttribute(listEditColumns, 'setValue', columnOrderValue);
				}
			}

			
			var listPagesNode = pageSetXmlUtils.getChildNodeByTagName(rootNode,'Pages');
			
			//选择添加页  --GUOWEIXIN
			var fmAddPageValue="";
			var fmAddPageValue = $('#fm_listpageset_selectaddpage').combobox('getValue');
			var fmAddPageText = $('#fm_listpageset_selectaddpage').combobox('getText');
			if(fmAddPageValue!="1"){
				var listAddPageNode = pageSetXmlUtils.getChildNodeByTagName(listPagesNode,'AddPage');
				if(listAddPageNode && listAddPageNode != null){
					if(fmAddPageText.indexOf('.')!=-1){
						pageSetXmlUtils.setAttribute(listAddPageNode,'formId',fmAddPageText);
					}else{
						pageSetXmlUtils.setAttribute(listAddPageNode,'formId',fmAddPageValue);
					}
					pageSetXmlUtils.setAttribute(listAddPageNode,'formName',fmAddPageText);
				}else{
					pageSetXmlUtils.createNode('AddPage','',{formId:fmAddPageValue,formName:fmAddPageText},listPagesNode);
				}
			}	
			//选择编辑页
			var fmEditPageValue = $('#fm_listpageset_selecteditpage').combobox('getValue');
			var fmEditPageText = $('#fm_listpageset_selecteditpage').combobox('getText');		
			if(fmEditPageValue){
				var listEditPageNode = pageSetXmlUtils.getChildNodeByTagName(listPagesNode,'EditPage');
				if(listEditPageNode && listEditPageNode != null){
					if(fmEditPageText.indexOf('.')!=-1){
						pageSetXmlUtils.setAttribute(listEditPageNode,'formId',fmEditPageText);
					}else{
						pageSetXmlUtils.setAttribute(listEditPageNode,'formId',fmEditPageValue);
					}
					pageSetXmlUtils.setAttribute(listEditPageNode,'formName',fmEditPageText);
				}else{
					pageSetXmlUtils.createNode('EditPage','',{formId:fmEditPageValue,formName:fmEditPageText},listPagesNode);
				}
			}	
			//选择查看页
			var fmViewPageValue = $('#fm_listpageset_selectviewpage').combobox('getValue');
			var fmViewPageText = $('#fm_listpageset_selectviewpage').combobox('getText');
			if(fmViewPageValue){
				var listViewPageNode = pageSetXmlUtils.getChildNodeByTagName(listPagesNode,'ViewPage');
				if(listViewPageNode && listViewPageNode != null){
					pageSetXmlUtils.setAttribute(listViewPageNode,'formId',fmViewPageValue);
					pageSetXmlUtils.setAttribute(listViewPageNode,'formName',fmViewPageText);
				}else{
					pageSetXmlUtils.createNode('ViewPage','',{formId:fmViewPageValue,formName:fmViewPageText},listPagesNode);
				}
			}	
			//选择复制页
			var fmCopyPageValue = $('#fm_listpageset_selectcopypage').combobox('getValue');
			var fmCopyPageText = $('#fm_listpageset_selectcopypage').combobox('getText');
			if(fmCopyPageValue){
				var listCopyPageNode = pageSetXmlUtils.getChildNodeByTagName(listPagesNode,'CopyPage');
				if(listCopyPageNode && listCopyPageNode != null){
					pageSetXmlUtils.setAttribute(listCopyPageNode,'formId',fmCopyPageValue);
					pageSetXmlUtils.setAttribute(listCopyPageNode,'formName',fmCopyPageText);
				}else{
					pageSetXmlUtils.createNode('CopyPage','',{formId:fmCopyPageValue,formName:fmCopyPageText},listPagesNode);
				}
			}	
			//是否显示分页
			var listPaginationNode = pageSetXmlUtils.getChildNodeByTagName(rootNode,'Pagination');	
			if($('#fm_listpageset_isshowpage').attr('checked')){				
				pageSetXmlUtils.setAttribute(listPaginationNode,'visible','true');
			}else{
				pageSetXmlUtils.setAttribute(listPaginationNode,'visible','false');
			}
			pageSetXmlUtils.setAttribute(listButtonsNode,'isConfig','1');
			$("#fm_formdesignlist_basic").html(CONFIGYES);
			listPageSetSubmit(pageSetXmlUtils.toString());
		}
    </script>
  </head>  
  <body>
	<div id="formListDesBasicPart" style="width:777px;height:115px;"> 
		<div class="easyui-panel"  id="formListDesBasicPartPanel" title="基本信息配置" fit="true" headerCls="header_cls">
				<table border="0" cellpadding="2" cellspacing="0" width="100%" style="margin-top: 5px;">
			  	<tr>
				  <td width="10%" align="right">标题：</td>
				  <td width="90%"><input type="text" id="fm_listpageset_pageset_title"/>
				  &nbsp;&nbsp;&nbsp;  是否对当前表单操作加入记录：<input type="checkbox" id="isSystemActionlog" />
				  </td>
				</tr>
				<tr>
				  <td width="10%" align="right"><input type="hidden" id="isPseudoDeleted_hidden" />伪删除：</td>
				  <td  width="90%"><input type="checkbox" id="isPseudoDeleted_checkbox" ></td>
				</tr>
				<tr>
				  <td width="10%" align="right"><input type="hidden" id="isOriginalSql_hidden" />自定义SQL：</td>
				  <td  width="90%">
				    <input type="checkbox" id="isOriginalSql_checkbox" style="vertical-align: middle;"><font color="blue"> (自已定义列表查询语句)</font>
				  </td>
				</tr>
				<tr id="isOriginalSql_div" style="display:none;">
				   <td>&nbsp;</td>
				   <td>
					    <div style="border:1px solid #e4e4e4;padding-left:5px;width:500px;">
					      <label for="isOriginalSql_context" style="width:100px;"> &nbsp; -Sql：</label><textarea id="isOriginalSql_context" cols="50" style="vertical-align: middle;"></textarea><br><br>
					      <label for="isOriginalSql_param" style="width:100px;">-参数：</label><input id="isOriginalSql_param" type="text" style="vertical-align: middle;"/>
					    </div>
				   </td>
				</tr>
			</table>
		</div>
	</div>
	<div style="height:4px;"></div>
	<div style="width:777px;height:133px;">
		<div class="easyui-panel" title="功能配置" fit="true" headerCls="header_cls">
			<table border="0" cellpadding="5" cellspacing="0" width="100%">
				<tr>
					<td width="33%" ><input type="checkbox" id="fm_listpageset_isshowquery"/> 查询页面显示</td>
					<td width="33%" ><input type="checkbox" id="fm_listpageset_isshowview"/> 显示查看列</td>
					<td ><input type="checkbox" id="fm_listpageset_isshowpage"/> 显示分页</td>
				</tr>
				<tr>
					<td width="33%" ><input type="checkbox"  id="fm_listpageset_isshowimport"/> 显示导入按钮</td>
					<td width="33%" ><input type="checkbox" id="fm_listpageset_isshowexport"/> 显示导出按钮</td>
					<td >
						<input type="radio" id="fm_listpageset_operate_end" name="operate" value='end' checked="checked" /> 操作列靠后
						<input type="radio" id="fm_listpageset_operate_home" value='before' name="operate" /> 操作列靠前
					</td>
				</tr>
				<tr>
					<td width="25%"><span>选择添加页：</span><select id="fm_listpageset_selectaddpage" style="width:160px;vertical-align: middle;"></select></td>
					<td width="25%"><span>选择编辑页：</span><select id="fm_listpageset_selecteditpage" style="width:160px;vertical-align: middle;"></select></td>
					<td width="25%"><span>选择详情页：</span><select id="fm_listpageset_selectviewpage" style="width:160px;vertical-align: middle;"></select></td>
					<td width="25%"><span>选择复制页：</span><select id="fm_listpageset_selectcopypage" style="width:160px;vertical-align: middle;"></select></td>
				</tr>
			</table>
		</div>
	</div>
	<div style="height:4px;"></div>
	<div style="width:777px;height:65px;">
		<div class="easyui-panel" title="支持单行编辑" fit="true" headerCls="header_cls">
			<table border="0" cellpadding="5" cellspacing="0" width="100%">
				<tr>
					<td width="25%" ><input type="checkbox" id="fm_listpageset_isshowadd"/> 显示添加按钮</td>
					<td width="25%" ><input type="checkbox" id="fm_listpageset_isshowupdate"/> 显示编辑列</td>
					<td width="25%"><input type="checkbox" id="fm_listpageset_isshowdelete"/> 显示删除列</td>	
					<td><input type="checkbox" id="fm_listpageset_isshowcopy"/> 显示复制列</td>						
				</tr>					
			</table>
		</div>
	</div>
	<div style="height:4px;"></div>
	<div style="width:777px;height:65px;">
		<div class="easyui-panel" title="支持多行编辑" fit="true" headerCls="header_cls">
			<table border="0" cellpadding="5" cellspacing="0" width="100%">
				<tr>
					<td width="33%" ><input type="checkbox" id="fm_listpageset_isshowmutidelete"/> 显示批量删除按钮</td>
					<td width="33%" ><input type="checkbox" id="fm_listpageset_isshowsingleSelect"/>是否对列表选择数据单选(singleSelect==true)</td>	
					<td></td>						
				</tr>					
			</table>
		</div>
	</div>
	<div style="height:4px;"></div>
	<table border="0" cellpadding="5" cellspacing="0" width="100%">
		<tr>
			<td align="center"><a class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="fmListpagesetPagesetSave()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:;"  onclick="fmListPageSetClose()" >关闭</a></td>										
		</tr>					
	</table>
	
  </body>
</html>
