<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
    <title>添加</title>    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script language="javascript" type="text/javascript">
		$.post("form/form!getDataObjectInfoList.action",{"dataObjectId":'${dataObjectId}'},
			function(data){
				fmFormSelectTableTablesData = data;
			}
		);
		$(function(){
			$('#fm_formadd_selectedtables').datagrid({
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
			$('#fm_formadd_selectedcolumns').datagrid({
				width:420,
				height:200,
				nowrap: false,
				striped: true,
				autoFit: true,
				remoteSort:false,
				idField:'id',
				columns: [[
					{field: 'name',title: '列名',width: 130,sortable: true},
					{field: 'title',title: '中文名',width: 100},
					{field: 'datatablenname',title: '表名',width: 135,sortable: true}
				]],
				rownumbers: true//,
				//singleSelect: true
			});
			function fmFormAddFormSubmit(){
				$('#fm_formadd_form').form('submit',{ 
					url:'form/form!add.action?dataObjectId=${dataObjectId}', 
					onSubmit:function(){ 
						var dataObjectInfo = $(":input[name='formSettings']").val();
						if(dataObjectInfo && dataObjectInfo != ''){
							var xmlUtilsTemp = new XmlUtils({dataType:'json'}); 
							xmlUtilsTemp.loadXmlString(dataObjectInfo);
							var dataObjectNodeTemp = xmlUtilsTemp.getRoot();
							//xmlUtilsTemp.setAttribute(dataObjectNodeTemp,'type',$(":radio[name='form.type'][checked=true]").val());
							//xmlUtilsTemp.setAttribute(dataObjectNodeTemp,'rows',$(":input[name='rowsNumber']").val());
							//xmlUtilsTemp.setAttribute(dataObjectNodeTemp,'cols',$(":input[name='colsNumber']").val());
							xmlUtilsTemp.setAttribute(dataObjectNodeTemp,'description',$(":input[name='form.description']").val());
							$(":input[name='formSettings']").val('<?xml version="1.0" encoding="UTF-8"?>' + xmlUtilsTemp.toString());
						}
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
						if("exist" == data) {						
							$.messager.alert("提示","表单已存在！", 'warning');
						} else if("success" == data) {
							parent.$('#fm_form_add').window('close');
							$('#fm_formlist_table').datagrid('reload');
						}
					} 
				}); 			
			}			
			$('#fmformaddformsavebutton').bind('click',fmFormAddFormSubmit);
			$(":radio[name='form.type']").bind('click',fmFormAddClickFormType);
			
		});		
		function fmFormAddSelectTable(){	
			$("#fm_formadd_selecttable").window({'href':''});	
			$("#fm_formadd_selecttable").window('refresh');
			$("#fm_formadd_selecttable").window({'href':'form/form!toSelectTable.action?xmlOperateType=add&dataObjectId=${dataObjectId}'});				
			$("#fm_formadd_selecttable").window('open');
			/**
			$.post("form/form!toSelectTable.action",{"dataObjectId":"${dataObjectId}"},
           		function(data){
	          		$("#fm_formadd_selecttable").html(data);
	          		$("#fm_formadd_selecttable").window('open');
	          		//$("#fm_formadd_selecttable").window('refresh');
       			}
       		);*/
       		
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
	
		}
				
		function fmFormAddClickFormType(){
			$(":input[name='formSettings']").val('');
		}
    </script>
  </head>  
  <body  >
	<form id="fm_formadd_form" method="post" action="form/form!add.action">
		<table width="100%" border="0" cellpadding="1" cellspacing="0" class="table_form1">
			<tr>
				<td width="20%" align="right">表单名称：</td>
				<td><input name="form.formName" class="easyui-validatebox" required="true" style="width:373px;"/>&nbsp;&nbsp;<font size="2" color="red">*</font></td>
			</tr>
			<tr>
				<td align="right">描述：</td>
				<td><textarea name="form.description" cols="43" rows="4" ></textarea></td>
			</tr>
			<tr>
				<td align="right">序号：</td>
				<td><input name="form.sortIndex" class="easyui-validatebox" validType="sortlength[1,10]" style="width:60px;"/></td>
			</tr>
			<tr>
				<td align="right">表单类型：</td>
				<td><input name="form.type" type="radio" value="listPage"  checked="true"/> 列表&nbsp;&nbsp;&nbsp;
				<input name="form.type" type="radio" value="editPage" /> 编辑&nbsp;&nbsp;&nbsp;
				<input name="form.type" type="radio" value="viewPage" /> 查看</td>
			</tr>
			 <tr>
			 	<td colspan="1.5" align="center" id="shows">
			 		<a id="dd" href="javascript:;" class="easyui-linkbutton" icon="icon-ok" onclick="fmFormChangeTableAndXml()">显示XML</a>
			 		<a id="dd1" href="javascript:;" class="easyui-linkbutton" icon="icon-ok" onclick="fmFormChangeXmlAndTable()" style="display:none">显示表和列</a>
			 	</td>	
				<td colspan="0.5" align="right"><a href="javascript:;" class="easyui-linkbutton" icon="icon-ok" onclick="fmFormAddSelectTable()">选择表和列</a></td>
				
			 </tr>
			 <tr>			
				<td colspan="2" align="center" id="formSettings" style="display:none;"><span style="float:left;padding-left: 30px;font-size: 12	px;">表单xml</span><br><textarea name="formSettings" cols="68" rows="6" style="height: 200px;" readonly></textarea></td>
			 </tr>			 
			 <tr id="fmFormview" >	
				<td colspan="2">
					<table>
					<tr>
						<td>选中对象列表</td>
						<td>选中列列表</td>
					</tr>
					<tr>
						<td><table id="fm_formadd_selectedtables"></table></td>
						<td><table id="fm_formadd_selectedcolumns"></table></td>
					</tr></table>
				</td>
			 </tr>			 
			<tr>
				<td colspan="2" align="center">
					<a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-save" id="fmformaddformsavebutton">保存</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#fm_form_add').window('close');">关闭</a>
				</td>
			</tr>
		</table>
		<!-- 
	<div class="pop_col_col">
		<div class="pop_col_conc">
			<dl>
				<dd>
					表单名称：
				</dd>
				<dt style="width: 220px;">
					<input name="form.formName" class="easyui-validatebox"
						required="true" style="width: 180px;" />
					&nbsp;&nbsp;
					<font size="2" color="red">*</font>
				</dt>
			</dl>
		</div>
		<div class="pop_col_cons">
			<dl>
				<dd>
					描述：
				</dd>
				<dt>
					<textarea name="form.description" cols="39" rows="4"></textarea>
				</dt>
			</dl>
		</div>
		<div class="pop_col_conc">
			<dl>
				<dd>
					序号：
				</dd>
				<dt>
					<input name="form.sortIndex" class="easyui-validatebox" size="23"/>
				</dt>
			</dl>
		</div>
		<div class="pop_col_conc">
			<dl>
				<dd>
					表单类型：
				</dd>
				<dt>
					<input name="form.type" type="radio" value="listPage"
						checked="true" />
					列表&nbsp;&nbsp;&nbsp;
					<input name="form.type" type="radio" value="editPage" />
					编辑&nbsp;&nbsp;&nbsp;
					<input name="form.type" type="radio" value="viewPage" />
					查看
				</dt>
			</dl>
		 
			<dl>
				<dd>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</dd>
				<dt>
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-ok"
						onclick="fmFormAddSelectTable()">选择表和列</a>
				</dt>
			</dl>
		</div>
		<div class="pop_col_cons">
			<dl>
				<dd>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</dd>
				<dt>
					<textarea name="formSettings" cols="50" rows="6"  readonly ></textarea>&nbsp;&nbsp;
				</dt>
			</dl>
		</div>
		<div class="pop_col_cons">
			<div align="center"
				style="width: 100%; height: 100%; padding-top: 20px;">
				<a href="javascript:;" class="easyui-linkbutton" icon="icon-save"
					id="fmformaddformsavebutton">保存</a>
				<a href="javascript:;" class="easyui-linkbutton"
					icon="icon-cancel"
					onclick="parent.$('#fm_form_add').window('close');">关闭</a>
			</div>
		</div>
		 -->
   </form>
  </body>
</html>