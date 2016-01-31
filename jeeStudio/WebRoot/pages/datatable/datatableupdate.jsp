<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/include.jsp"%>
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
		<title>修改</title>
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
			function bdDatatableupdateFormSubmit(){
				$('#bd_datatableupdate_form').form('submit',{ 
					url:'datatable/dataTable!update.action', 
					onSubmit:function(){ 
						if($(this).form('validate') == false){
							return false;
						}
						if($('#bd_datatable_update_datasource').combobox('getValue') == '-1'){
							$.messager.alert("提示","请选择数据源！", 'warning');
							return false;
						}
						if($('#bddatatableupdate_type').combobox('getValue') == '-1'){
							$.messager.alert("提示","请选择数据类型！", 'warning');
							return false;
						}
						/*if($('#bddatatableupdate_state').combobox('getValue') == '-1'){
							$.messager.alert("提示","请选择状态！", 'warning');
							return false;
						}*/
						return true; 
					}, 
					success:function(data){ 
						if("exist" == data) {						
							$.messager.alert("提示","数据对象编码或者名称已存在！", 'warning');
						} else if("success" == data) {
							parent.$('#bd_datatable_update').window('close');
							$('#bd_datatablelist_table').datagrid('reload');
						}
					} 
				}); 			
			}
			$('#bdDatatableupdateFormSaveButton').bind('click',bdDatatableupdateFormSubmit);
		});
		function initDataTableEditData(){
			//$(":radio[name='dataTable.state'][value='${dataTable.state}']").attr("checked",true);		
			$(":radio[name='dataTable.inused'][value='${dataTable.inused}']").attr("checked",true);
			$('#bddatatableupdate_type').combobox({		
				data:dataObjectTypeData,
				valueField:'id',
				textField:'text',
			    panelHeight:'auto',
				editable:false
			});
			$('#bddatatableupdate_state').combobox({		
				data:dataObjectStateData,
				valueField:'id',
				textField:'text',
			    panelHeight:'auto',
				editable:false
			});
			$('#bd_datatable_update_datasource').combobox({
			    ///url:'datasource/dataSource!getAllItem.action?random='+parseInt(10*Math.random()),
			    valueField:'id',
			    textField:'name',
			    panelHeight:'auto',
			    listHeight:200,
			    editable:false
			});	
			$('#bd_datatable_update_datasource').combobox("reload",'datasource/dataSource!getAllItem.action');
			for(var i=0; i<dataObjectTypeData.length; i++){
				if (dataObjectTypeData[i].id == '${dataTable.type}') { 
					$('#bddatatableupdate_type').combobox('setValue','${dataTable.type}');
					break;
				}
			}
			for(var i=0; i<dataObjectStateData.length; i++){
				if (dataObjectStateData[i].id == '${dataTable.state}') { 
					$('#bddatatableupdate_state').combobox('setValue','${dataTable.state}');
					break;
				}
			}
			$('#bd_datatable_update_datasource').combobox('setValue','${dataTable.dataSource.id}');
		}
    </script>
	</head>
	<body>
		<form id="bd_datatableupdate_form" method="post"
			action="datatable/dataTable!update.action">
			<input name="dataTable.id" type="hidden" value="${dataTable.id}" />
		
			<div class="pop_col_col">
				<div class="pop_col_conc">
					<dl>
						<dd>
							对象编码：
						</dd>
						<dt>
							<input name="dataTable.code" class="easyui-validatebox" size="20" readonly="readonly"
								value="${dataTable.id}" />
						</dt>
					</dl>
					<dl>
						<dd>
							对象名称：
						</dd>
						<dt>
							${dataTable.name}
							<input type="hidden" name="dataTable.name"
								value="${dataTable.name}" />
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							序号：
						</dd>
						<dt>
							<input name="dataTable.sortNo" class="easyui-numberbox" size="20"
								value="${dataTable.sortNo}" />
						</dt>
					</dl>
					<dl>
						<dd>
							对象类型：
						</dd>
						<dt>
							<select id="bddatatableupdate_type" name="dataTable.type" hasDownArrow="false"
								style="width: 153px;" required="true">
							</select> <font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
			</div>
			<div class="pop_col_color">
				<div class="pop_col_conc">
			<!-- 
					<dl>
						<dd>
							状态：
						</dd>
						<dt>
							<select id="bddatatableupdate_state" name="dataTable.state"
								style="width: 153px;" required="true">
							</select> <font size="2" color="red">*</font>
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
			 -->
				</div>
				<div class="pop_col_conc">
					<!-- 
						<dl>
							<dd>
								数据源：
							</dd>
							<dt>
								<select id="bd_datatable_update_datasource" hasDownArrow="false"
									name="dataTable.dataSource.id" style="width: 153px;"
									value="${dataTable.dataSource.id}" required="true"></select> <font size="2" color="red">*</font>
							</dt>
						</dl>
					-->
					<dl>
						<dd>数据对象分组</dd>
						<dt><select id="dataTable_dataObjectGroup_id" name="dataTable.dataObjectGroup.id" style="width:170px;" required="true"></select></dt>
					</dl>
				</div>
			</div>
			<div class="pop_col_col" style="width:100%;clear:both;margin-top: 20px;">
			    <div style="padding:13px 0;width: 100%;"  align="center">
				<a href="javascript:;" class="easyui-linkbutton" icon="icon-save"
					id="bdDatatableupdateFormSaveButton">保存</a>
				<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel"
					onclick="parent.$('#bd_datatable_update').window('close');">关闭</a>
			    </div>
			</div>
		</form>
		<script type="text/javascript">
			$(function (){
				$('#dataTable_dataObjectGroup_id').combotree({
					treeWidth:182,
					url:"datatable/dataObjectGroup!list.action",
					onLoadSuccess:function(node, data){
						$('#dataTable_dataObjectGroup_id').combotree('setValue','${dataTable.dataObjectGroup.id}');
					}
				});
			});
		</script>
	</body>
</html>
