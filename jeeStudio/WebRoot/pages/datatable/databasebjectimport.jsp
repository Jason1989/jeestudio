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
	<style type="text/css">
	.table_form1 table tr{
		font-size:12px;
		height:20px;
	}
	</style>
	<script type="text/javascript" src="js/basedata.common.js"></script>
	<script type="text/javascript" src="js/form.selectobject.js"></script>
	<script language="javascript" type="text/javascript">
		$(function(){
			//数据列表的初始化
			var selectedRecords = [];
			//var selectedRows = $("#bd_databaseobjectimport_oracle_tables").datagrid('getSelections');
			$("#bd_databaseobjectimport_oracle_tables").datagrid({
				nowrap: false,
				width:250,
				height:280,
				striped: true,
				autoFit: true,
				rownumbers:true,
				//url:'datasource/dataSource!getTablesList.action',
				queryParams:{dataSourceId:'-1'},
				frozenColumns: [[{
					field: 'ck',
					checkbox: true					
				}]],
				columns: [[
					{field: 'tableName',title: '对象名称',width: 180,sortable: true}
				//	{field: 'tableDesc',title: '说明',width: 80,sortable: true} 
				]],
				//通过onSelect和onUnselect两个事件，把选中的datagrid中的值添加到表的名称中
				onSelect:function(index,row){
					selectedRecords.push(row['tableName']);
					$("input[name=dataTable.name]",$("#bd_databaseobjectimport_oracle_form")).val(selectedRecords.toString());
				},onUnselect:function(index,row){
					var unselectedRow = row['tableName'];
					for (var i = 0; i < selectedRecords.length; i++) {
						var row1 = selectedRecords[i];
						if (row1 == unselectedRow) {
							for (var j = i + 1; j < selectedRecords.length; j++) {
								selectedRecords[j - 1] = selectedRecords[j];
							}
							selectedRecords.pop();
							break;
						}
					}
					$("input[name=dataTable.name]",$("#bd_databaseobjectimport_oracle_form")).val(selectedRecords.toString());
				},onUnselectAll:function(){
				
				$("input[name=dataTable.name]",$("#bd_databaseobjectimport_oracle_form")).val(null);
				
				},onSelectAll:function(){
					var rows = $('#bd_databaseobjectimport_oracle_tables').datagrid('getSelections');
					for(var i=0;i<rows.length;i++){
						selectedRecords.push(rows[i].tableName);
					}
					$("input[name=dataTable.name]",$("#bd_databaseobjectimport_oracle_form")).val(selectedRecords.toString());
				}
			});
			
	
			function bdDatabaseObjectImportOracleFormSubmit(){
				//var node = $("#dataobject_menu").tree("getSelected");
				var node = $("#lt1").tree("getSelected");//TODO 第二次进行操作出现问题，node 变为null
				if(node && node != null){
					$('#bd_databseobjectgroup_oracle_id').val(node.id);
					$('#bd_databaseobjectimport_oracle_form').form('submit',{ 
						url:'datatable/dataTable!addList.action', 
						onSubmit:function(){
							if($(this).form('validate') == false){
								return false;
							}
							if($('#bd_databaseobjectimport_oracle_datasource').combobox('getValue') == '-1'){
								$.messager.alert("提示","请选择数据源！", 'warning');
								return false;
							}
							if($('#bd_databaseobjectimport_oracle_type').combobox('getValue') == '-1'){
								$.messager.alert("提示","请选择数据类型！", 'warning');
								return false;
							}
							
							//$("input[name=dataTable.name]", this).val(selectedRecords.toString());
							return true; 
						}, 
						success:function(data){ 
							if("success" != data) {
								$.messager.alert('提示', data+'数据对象已存在!', 'warning');
							}						
							parent.$('#bd_databaseObject_import').window('close');
							$('#bd_datatablelist_table').datagrid('reload');
							
							//刷新菜单
							//refreshDataObjectGroup();
						} 
					}); 
				}else{
	        		$.messager.alert('提示', '请选择分组!', 'warning');
	        	}			
			}
			$('#bdDatabaseObjectImportFormSaveButton').bind('click',bdDatabaseObjectImportOracleFormSubmit);
			initDatabaseObjectImportData();
		});		
		
		
		
	
		
		
		function initDatabaseObjectImportData(){
			var bd_databaseobjectimport_oracle_tables = $("#bd_databaseobjectimport_oracle_tables");
			//数据库空间初始化
			var bd_databaseobjectimport_oracle_tablespaces = $("#bd_databaseobjectimport_oracle_tablespaces").combobox({
    			//url:'datasource/dataSource!getDbSpaces.action?dataSourceId=-1',
    			data:[{tablespaceId:'-1',tablespaceName:'请选择'}],
    			valueField:'tablespaceId',
    			textField:'tablespaceName',
			    listHeight:200,
	            editable:false
			});
			
			//对象类型初始化	
			var bd_databaseobjectimport_oracle_type = $('#bd_databaseobjectimport_oracle_type').combobox({	
				data:dataObjectTypeData,
				valueField:'id',
				textField:'text',
				panelHeight:'auto',
				editable:false ,
				onSelect:function(index){
					//数据对象的类型
					var dataObjectType = index['id'];
					//获取数据源的值　
					var dataSourceValue = $('#bd_databaseobjectimport_oracle_datasource').combobox('getValue');
					//var url = 'datasource/dataSource!getTablesList.action?dataSourceId='+dataSourceValue+'&dataObjectType='+index['id'];
					//bd_databaseobjectimport_oracle_tables.datagrid('reload',{dataSourceId:dataSourceValue,dataObjectType:dataObjectType});
					//设置列的标题名称
					var title = "";
					var flag = "";
					if(dataObjectType == '1'){
						title = "表名称";
						flag = "表";
					}else if (dataObjectType == '2'){
						title = "视图名称";
						flag = "视图";
					}
					//更新表格的标题
					bd_databaseobjectimport_oracle_tables.datagrid('options').columns[0][0].title=title;
					//更新表格的数据
					bd_databaseobjectimport_oracle_tables.datagrid('options').url = 'datasource/dataSource!getTablesList.action';
					bd_databaseobjectimport_oracle_tables.datagrid('options').queryParams = {dataSourceId:dataSourceValue,dataObjectType:dataObjectType};
					bd_databaseobjectimport_oracle_tables.datagrid('reload');
					bd_databaseobjectimport_oracle_tables.datagrid('options').onLoadSuccess = function(){
						//显示加载的数据记录数
						$("#bd_databaseobjectimport_oracle_tables_counts").html("").html("<font color='blue'>共"+
								bd_databaseobjectimport_oracle_tables.datagrid('getRows').length+"张"+flag+'，如右表所示  > ></font>');
					}

				}
			});
			//数据源下拉框
			$('#bd_databaseobjectimport_oracle_datasource').combobox({
			    url:'datasource/dataSource!getAllItem.action?time='+new Date().getTime(),
			    valueField:'id',
			    textField:'name',
			    listHeight:200,
			    panelHeight:'auto',
			    editable:false,
			    //数据源选择之后，相应的表空间的数据导入进来
			    onSelect:function(index){
			    	
			       //var url = 'datasource/dataSource!getDbSpaces.action?dataSourceId='+index['id'];
				   //bd_databaseobjectimport_oracle_tablespaces.combobox('enable');
			       //bd_databaseobjectimport_oracle_tablespaces.combobox('clear');
			       //bd_databaseobjectimport_oracle_tablespaces.combobox('reload',url);
				   //bd_databaseobjectimport_oracle_tablespaces.combobox('setValue',{tablespaceId:'-1',tablespaceName:'请选择'});
			       //使数据对象类型可用
			       bd_databaseobjectimport_oracle_type.combobox('enable');
			       bd_databaseobjectimport_oracle_type.combobox('setValue','-1');//使对象类型为空
			       bd_databaseobjectimport_oracle_tables.datagrid('loadData',{total:0,rows:[]});
			       $("#bd_databaseobjectimport_oracle_tables_counts").html("");
			       $("#bd_databaseobjectimport_oracle_objectName").val();
			    }
			    	
			    
			});
			$('#bd_databaseobjectimport_oracle_datasource').combobox('setValue','-1');
			bd_databaseobjectimport_oracle_type.combobox('setValue','-1');
			bd_databaseobjectimport_oracle_type.combobox('disable');
			bd_databaseobjectimport_oracle_tablespaces.combobox('setValue','-1');
			bd_databaseobjectimport_oracle_tablespaces.combobox('disable');
			//$('#bd_databaseobjectimport_oracle_tablespaces').combobox('setValue',{tablespaceId:'-1',tablespaceName:'请选择'});
		}
    </script>
  </head>  
  <body>
	<form id="bd_databaseobjectimport_oracle_form" method="post" action="datatable/dataTable!add.action">
	    <table width="100%" border="0">
	         <tr>
	           <!-- 数据库表的列表 -->
	           <td>
	           		<table class="table_form1">
						<tr style="margin-top: 10px;">
							<td align="right">数据源：</td>
							<td><select id="bd_databaseobjectimport_oracle_datasource" class="easyui-combobox" name="dataTable.dataSource.id" style="width:165px;" required="true"></select>&nbsp;&nbsp;<font size="2" color="red">*</font></td>
						</tr>
						<tr><td colspan="2" style="height: 10px;"> </td></tr>
						<!--
							<tr style="margin-top: 10px;">
								<td align="right">表空间：</td>
								<td><select id="bd_databaseobjectimport_oracle_tablespaces"  name="dataTable.spacename" style="width:180px;" required="true"></select>&nbsp;&nbsp;<font size="2" color="red">*</font></td>
							</tr>
						-->
						<tr><td colspan="2" style="height: 10px;"> </td></tr>
						<tr style="margin-top: 10px;">
							<td align="right">对象类型：</td>
							<td><select id="bd_databaseobjectimport_oracle_type" name="dataTable.type" style="width:165px;" required="true">
							</select>&nbsp;&nbsp;<font size="2" color="red">*</font></td>
						</tr>
						<tr><td colspan="2" style="height: 10px;"> </td></tr>
						<tr style="margin-top: 10px;">
							<td width="35%" align="right">对象列表：</td>
							<input id="bd_databseobjectgroup_oracle_id" name="dataTable.dataObjectGroup.id" type="hidden" />
							<td height="20"><span id="bd_databaseobjectimport_oracle_tables_counts">……</span></td>
						</tr>
						<tr><td colspan="2" style="height: 10px;"> </td></tr>
						<tr style="margin-top: 10px;" >
							<td align="right" style="display:none">对象名称：</td>
							<td style="display:none"><input name="dataTable.name" class="easyui-validatebox" size="23" required="true" id="bd_databaseobjectimport_oracle_objectName" readonly="readonly"/>&nbsp;&nbsp;<font size="2" color="red">*</font></td>
						</tr>
						<tr><td colspan="2" style="height: 10px;"> </td></tr>
						<!--
							<tr>
								<td align="right">状态：</td>
								<td><select id="bddatatableadd_state1" name="dataTable.state" style="width:155px;" required="true">
								</select>&nbsp;&nbsp;<font size="2" color="red">*</font>
								</td>
							</tr>
						<tr style="margin-top: 10px;">
							<td align="right">是否可用：</td>
							<td><input type="radio" value="1" name="dataTable.inused" checked/>可用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" value="2" name="dataTable.inused" />不可用
							</td>
						</tr>
						-->
						<tr><td colspan="2" style="height: 10px;"> </td></tr>
						<!-- 
						<tr style="margin-top: 5px;">
							<td align="right">序号：</td>
							<td><input name="dataTable.sortNo" class="easyui-numberbox" size="23" value=""/></td>
						</tr>
						 -->
						<tr >
							<td colspan="2" align="center" style="margin-top: 13px;">
								<a href="javascript:void(0);" class="easyui-linkbutton" style="margin:13px 13px 0 0;" icon="icon-save" id="bdDatabaseObjectImportFormSaveButton">保存</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_databaseObject_import').window('close');">关闭</a>
							</td>
						</tr>
					</table>
	           
	           </td>
	           <!-- 业务系统表的列表 -->
	           <td width="280">
	           		 <table width="100%">
	           		 	<tr>
	           		 	 <!--  <td><input type="text" size="35"/></td> -->  
	           		 	</tr>
	           		 	<tr>
	           		 		<td>
				              <table id="bd_databaseobjectimport_oracle_tables"></table>
	           		 	    </td>
	           		 	</tr>
	           		 </table>
	           </td>
	         </tr>
	    </table>
		
	</form>
	
  </body>
</html>
