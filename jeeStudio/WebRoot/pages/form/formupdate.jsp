<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
    <title>修改</title>    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script language="javascript" type="text/javascript">
		var updateFormXML = '${formSettings }';
		var stringSplitId="";//用于所传选中的表的名称
		$.post("form/form!dataRowsList.action",{"dataObjectId":'${form.dataTable.id}'},
			function(data){
				fmFormSelectTableTablesData = data;					
			}
		);
		$(function(){	
			var updateGetTableNode;
			var updateFromTablesNode;
			var updateFieldDefNode;
			var updateXmlUtils = new XmlUtils({dataType:'json'}); 			
			updateXmlUtils.loadXmlString(updateFormXML);
			var updateTableNode = updateXmlUtils.getNodesByXpath('//Form/DataMapping/DataSet/Table',1);

			if(updateTableNode && updateTableNode != null){			
				if(updateTableNode.length){
					 if(updateTableNode.length > 0){
					 	updateGetTableNode = updateXmlUtils.getChildNodeByTagName(updateTableNode,'GetTable');
						updateFieldDefNode = updateXmlUtils.getChildNodeByTagName(updateTableNode,'FieldDef');
					 }
				}else{
					updateGetTableNode = updateXmlUtils.getChildNodeByTagName(updateTableNode,'GetTable');
					updateFieldDefNode = updateXmlUtils.getChildNodeByTagName(updateTableNode,'FieldDef');
				}
				
			}
			if(updateGetTableNode && updateGetTableNode != null){
				updateFromTablesNode = updateXmlUtils.getChildNodeByTagName(updateGetTableNode,'FromTables');
			}

			var updateFromTableNodes = updateXmlUtils.getChildNodes(updateFromTablesNode);
			var tableObjectArray = new Array();

	        if(updateFromTableNodes){
	        	var len = updateFromTableNodes.length;

				for (var i=0;i<len;i++){
					var updateFromTableNode = updateFromTableNodes[i];	
					var updateTableNameNode = updateXmlUtils.getChildNodeByTagName(updateFromTableNode,'TableName');									
					if(updateXmlUtils.getInnerText(updateTableNameNode) != '') {
						tableObjectArray.push({name:updateXmlUtils.getInnerText(updateTableNameNode)});
						stringSplitId+=updateXmlUtils.getInnerText(updateTableNameNode)+",";
					}								
       			}
      		}
			var updateFieldNodes;
			if(updateFieldDefNode && updateFieldDefNode != null)
				updateFieldNodes = updateXmlUtils.getChildNodes(updateFieldDefNode);
	        var columnObjectArray = new Array();
	        if(updateFieldNodes){
	        	var len = updateFieldNodes.length;
				for (var i=0;i<len;i++){
					var updateFieldNode = updateFieldNodes[i];	
					var updateFromFieldNode = updateXmlUtils.getChildNodeByTagName(updateFieldNode,'FromField');
					var updateDisplayNameNode = updateXmlUtils.getChildNodeByTagName(updateFieldNode,'DisplayName');
					var updateFieldNameNode = updateXmlUtils.getChildNodeByTagName(updateFromFieldNode,'FieldName');
					var updateNameNode = updateXmlUtils.getChildNodeByTagName(updateFieldNameNode,'Name');										
					var updateTableNameNode = updateXmlUtils.getChildNodeByTagName(updateFieldNameNode,'TableName');										
					var tableName = updateXmlUtils.getInnerText(updateTableNameNode);
					var columnName = updateXmlUtils.getInnerText(updateNameNode);
					var columnTitle = updateXmlUtils.getInnerText(updateDisplayNameNode);
					if(tableName != '' && columnName != ''){
						columnObjectArray.push({name:columnName,title:columnTitle,datatablenname:tableName});
					}
       			}
      		}
			$('#fm_formupdate_selectedtables').datagrid({
				width:190,
				height:200,
				nowrap: false,
				striped: true,
				autoFit: true,
				idField:'id',
				columns: [[
					{field: 'name',title: '表名',width: 140,sortable: true}
				]],
				rownumbers: true//,
				//singleSelect: true
			});
			$('#fm_formupdate_selectedtables').datagrid('loadData',{total:tableObjectArray.length,rows:tableObjectArray});
			$('#fm_formupdate_selectedcolumns').datagrid({
				width:420,
				height:200,
				nowrap: true,
				striped: true,
				autoFit: true,
				idField:'id',
				columns: [[
					{field: 'name',title: '列名',width: 130,sortable: true},
					{field: 'title',title: '中文名'},
					{field: 'datatablenname',title: '表名',width: 135,sortable: true}
				]],
				rownumbers: true//,
				//singleSelect: true
			});	
			$('#fm_formupdate_selectedcolumns').datagrid('loadData',{total:columnObjectArray.length,rows:columnObjectArray});
			function fmFormUpdateFormSubmit(){
				$('#fm_formupdate_form').form('submit',{ 
					url:'form/form!update.action', 
					onSubmit:function(){ 
						var dataObjectInfo = $(":input[name='formSettings']").val();
						if(dataObjectInfo && dataObjectInfo != ''){
							var xmlUtilsTemp = new XmlUtils({dataType:'json'}); 
							xmlUtilsTemp.loadXmlString(dataObjectInfo);
							var dataObjectNodeTemp = xmlUtilsTemp.getRoot();
							//xmlUtilsTemp.setAttribute(dataObjectNodeTemp,'type',$(":radio[name='form.type'][checked=true]").val());
							//xmlUtilsTemp.setAttribute(dataObjectNodeTemp,'rows',$(":input[name='rowsNumber']").val());
							//xmlUtilsTemp.setAttribute(dataObjectNodeTemp,'cols',$(":input[name='colsNumber']").val());
							var formDescript=$(":input[name='form.description']").val();
							
							xmlUtilsTemp.setAttribute(dataObjectNodeTemp,'description',$(":input[name='form.description']").val());
							
							$(":input[name='formSettings']").val('<?xml version="1.0" encoding="UTF-8"?>' + xmlUtilsTemp.toString());
						}
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
						if("success" == data) {
							parent.$('#fm_form_update').window('close');
							$('#fm_formlist_table').datagrid('reload');
						}
					} 
				}); 			
			}
			$('#fmformupdateformsavebutton').bind('click',fmFormUpdateFormSubmit);
			$(":radio[name='form.type']").bind('click',fmFormUpdateClickFormType);
			initFormEditData();
		});		
		function fmFormUpdateSelectTable(){
			$("#fm_formadd_selecttable").window({'href':''});
			$("#fm_formadd_selecttable").window('refresh');
			$("#fm_formadd_selecttable").window({'href':'form/form!toSelectTable.action?xmlOperateType=update&dataObjectId=${form.dataTable.id}&mainObjectName=${form.dataTable.name}&formId=${form.id }&dataTablesNames='+stringSplitId});				
			$("#fm_formadd_selecttable").window('open');
			/**
			$.post("form/form!toSelectTable.action",{"dataObjectId":"${form.dataTable.id}"},
           		function(data){
	          		$("#fm_formadd_selecttable").html(data);
	          		$("#fm_formadd_selecttable").window('open');
       			}
       		);
       		*/
		}
		function initFormEditData(){
			$(":radio[name='form.type'][value='${form.type}']").attr("checked",true);
          //  $(":radio[name='form.type']").attr("disabled",true);		
            }
		function fmFormUpdateClickFormType(){
			$(":input[name='formSettings']").val('');
			 $.messager.alert('提示','修改了表单类型，必须重新选择表和列！','waring');	
		}
		
		function fmFormChangeTableAndXml(){
			$("#dd1").css("display","table-cell");
			$("#dd").css("display","none");
			$("#fmFormview").css("display","none"); 
			$("#formSettings").css("display","table-cell");
			////$("#shows").html('<a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-ok" onclick="fmFormChangeXmlAndTable()">显示列表</a>');
			 
		}
		function fmFormChangeXmlAndTable(){
			$("#dd").css("display","table-cell");
			$("#dd1").css("display","none");
			$("#fmFormview").removeAttr("style"); 
			$("#formSettings").css("display","none");
			///$("#shows").html('<a href="javascript:;" class="easyui-linkbutton" icon="icon-ok" onclick="fmFormChangeTableAndXml()">显示XML</a>');
	
		}
		
    </script>
  </head>  
  <body>
	<form id="fm_formupdate_form" method="post" action="form/form!update.action">
		<table width="100%" border="0" cellpadding="1" cellspacing="0" class="table_form1">
			<tr>
				<td width="20%" align="right">表单名称：</td>
				<td><input name="form.id" type="hidden" value="${form.id }"/>
					<input name="form.dataTable.id" type="hidden" value="${form.dataTable.id }"/>
					<input name="form.state" type="hidden" value="${form.state }"/>
				<input name="form.formName" class="easyui-validatebox" required="true" style="width:363px;" value="${form.formName }"/>&nbsp;&nbsp;<font size="2" color="red">*</font></td>
			</tr>
			<!-- 
			<tr>
				<td align="right">URL：</td>
				<td><input name="url" class="easyui-validatebox" required="true" size="50" value="${url }"/></td>
			</tr> 
			<tr>
				<td align="right">每页行数：</td>
				<td align="left"><input name="rowsNumber" class="easyui-numberbox" required="true" value="${rowsNumber }"/></td> 
			 </tr>
			 <tr>
				<td align="right">每行列数：</td>
				<td><input name="colsNumber" class="easyui-numberbox" required="true" value="${colsNumber }"/></td>
			 </tr>-->
			<tr>
				<td align="right">描述：</td>
				<td><textarea name="form.description" cols="43" rows="4">${form.description }</textarea></td>
			</tr>
			<tr>
				<td align="right">序号：</td>
				<td><input name="form.sortIndex" class="easyui-validatebox" value="${form.sortIndex }" style="width:60px;"/></td>
			</tr>
			<tr >
				<td align="right">表单类型：</td>
				<td><input name="form.type" type="radio" value="listPage"  checked="true"/>列表&nbsp;&nbsp;&nbsp;
				<input name="form.type" type="radio" value="editPage" />编辑&nbsp;&nbsp;&nbsp;
				<input name="form.type" type="radio" value="viewPage"/>查看</td>
			</tr>
			 <tr>	
			 	<td colspan="1" align="center" id="shows">
			 		<a id="dd" href="javascript:;" class="easyui-linkbutton" icon="icon-ok" onclick="fmFormChangeTableAndXml()">显示XML</a>
			 		<a id="dd1" href="javascript:;" class="easyui-linkbutton" icon="icon-ok" onclick="fmFormChangeXmlAndTable()" style="display:none">显示表和列</a>
			 	</td>		
				<td colspan="2" align="right"><a href="javascript:;" class="easyui-linkbutton" icon="icon-ok" onclick="fmFormUpdateSelectTable()">选择表和列</a></td>
			 </tr>
			 <tr>			
				<td colspan="2" align="center" id="formSettings" style="display:none;"><textarea name="formSettings" cols="60" rows="6" class="easyui-validatebox">${formSettings }</textarea>&nbsp;&nbsp;<font size="2" color="red">*</font></td>
			 </tr>
			 <tr id="fmFormview">			
				<td colspan="2">
					<table>
					<tr>
						<td>选中对象列表</td>
						<td>选中列列表</td>
					</tr>
					<tr>
						<td><table id="fm_formupdate_selectedtables"></table></td>
						<td><table id="fm_formupdate_selectedcolumns"></table></td>
					</tr></table>
				</td>
			 </tr>	
			<tr>
				<td colspan="2" align="center">
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="fmformupdateformsavebutton">保存</a>
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#fm_form_update').window('close');">关闭</a>
				</td>
			</tr>
		</table>
	</form>
  </body>
</html>
