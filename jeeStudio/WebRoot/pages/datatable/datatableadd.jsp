<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	<script type="text/javascript" src="js/basedata.common.js"></script>
	<script language="javascript" type="text/javascript">
		$(function(){
			initDatatableAddData();
	
			function bdDatatableaddFormSubmit(){
				//var node = $("#dataobject_menu").tree("getSelected");
				var node = $("#lt1").tree("getSelected");
				if(node && node != null){
					$('#bd_dt_dataobjectgroup_id').val(node.id);
					$('#bd_datatableadd_form').form('submit',{ 
						url:'datatable/dataTable!add.action', 
						onSubmit:function(){ 
							if($(this).form('validate') == false){
								return false;
							}
							if($('#bd_datatable_add_datasource').combobox('getValue') == '-1'){
								$.messager.alert("提示","请选择数据源！", 'warning');
								return false;
							}
							if($('#bddatatableadd_type').combobox('getValue') == '-1'){
								$.messager.alert("提示","请选择数据类型！", 'warning');
								return false;
							}
							if($('#bddatatableadd_state').combobox('getValue') == '-1'){
								$.messager.alert("提示","请选择状态！", 'warning');
								return false;
							}
							return true; 	
						}, 
						success:function(data){ 
							if("exist" == data) {						
								$.messager.alert("提示","数据对象编码或者名称已存在！", 'warning');
							} else if("success" == data) {
								parent.$('#bd_datatable_add').window('close');
								$('#bd_datatablelist_table').datagrid('reload');
								//刷新菜单
								//refreshDataObjectGroup();
							}
						} 
					}); 
				}else{
	        		$.messager.alert('提示', '请选择分组!', 'warning');
	        	}			
			}
			$('#bdDatatableaddFormSaveButton').bind('click',bdDatatableaddFormSubmit);
			
		});		
		function initDatatableAddData(){
			$('#bddatatableadd_type').combobox({		
				data:dataObjectTypeData,
				valueField:'id',
				textField:'text',
				panelHeight:'auto',
				editable:false
			});
			$('#bddatatableadd_state').combobox({		
				data:dataObjectStateData,
				valueField:'id',
				textField:'text',
				editable:false
			});
			$('#bd_datatable_add_datasource').combobox({
			    /////url:'datasource/dataSource!getAllItem.action',
			    valueField:'id',
			    textField:'name',
			    listHeight:200,
			    editable:false
			});
			$('#bd_datatable_add_datasource').combobox("reload",'datasource/dataSource!getAllItem.action?time='+new Date().getTime());
			$('#bddatatableadd_type').combobox('setValue','-1');
			$('#bddatatableadd_state').combobox('setValue','-1');
			$('#bd_datatable_add_datasource').combobox('setValue','-1');
		}
    </script>
  </head>  
  <body>
	<form id="bd_datatableadd_form" method="post" action="datatable/dataTable!add.action">
	<input id="bd_dt_dataobjectgroup_id" name="dataTable.dataObjectGroup.id" type="hidden" />
			<div class="pop_col_col">
				<div class="pop_col_conc">
					<dl>
						<dd>
							对象编码：
						</dd>
						<dt>
							<input name="dataTable.code" class="easyui-validatebox" size="23" />
						</dt>
					</dl>
					<dl>
						<dd>
							对象名称：
						</dd>
						<dt>
							<input name="dataTable.name" class="easyui-validatebox" size="23"
								required="true" />
							&nbsp;&nbsp;
							<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							数据源：
						</dd>
						<dt>
							<select id="bd_datatable_add_datasource"
								name="dataTable.dataSource.id" style="width: 170px;"
								required="true"></select>
							&nbsp;&nbsp;
							<font size="2" color="red">*</font>
						</dt>
					</dl>
					<dl>
						<dd>
							对象类型：
						</dd>
						<dt>
							<select id="bddatatableadd_type" name="dataTable.type"
								style="width: 170px;" required="true">
							</select>
							&nbsp;&nbsp;
							<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
			</div>
			<div class="pop_col_color">
				<div class="pop_col_conc">
					<dl>
						<dd>
							状态：
						</dd>
						<dt>
							<select id="bddatatableadd_state" name="dataTable.state"
								style="width: 170px;" required="true">
							</select>
							&nbsp;&nbsp;
							<font size="2" color="red">*</font>
						</dt>
					</dl>
					<dl>
						<dd>
							是否可用：
						</dd>
						<dt>
							<input type="radio" value="1" name="dataTable.inused" checked />
							可用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="2" name="dataTable.inused" />
							不可用
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							序号：
						</dd>
						<dt>
							<input name="dataTable.sortNo" class="easyui-numberbox" size="23" />
						</dt>
					</dl>
					<dl>
						<dd>
							
						</dd>
						<dt>
							
						</dt>
					</dl>
				</div>
			</div>
			 <div style="float:left;padding:50px 0 0 230px;">
			 	<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bdDatatableaddFormSaveButton">保存</a>
				<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_datatable_add').window('close');">关闭</a>
			</div>
	</form>
  </body>
</html>
