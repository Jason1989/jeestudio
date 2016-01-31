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
		$(function(){			
			function bdsqlDicaddFormSubmit(){
				$('#bd_sqldicadd_form').form('submit',{ 
					url:'dictionary/dataDictionary!addsql.action', 
					onSubmit:function(){
						if(''==$('input:text[name=sqlObj.sqldicname]').val()){
							$.messager.alert("提示","请填写名称！", 'warning');
							return false;
						}
						 
						if($('#bd_sqldict_add_datasource').combobox('getValue') == "-1"){
							$.messager.alert("提示","请选择数据源！", 'warning');
							return false;
						}
						if(''==$('textarea[name=sqlObj.sqldic_expression]').val()){
							$.messager.alert("提示","请填写表达式！", 'warning');
							return false;
						}
						
						return $(this).form('validate'); 
						
					}, 
					success:function(data){ 
						if("exist" == data) {						
							$.messager.alert("提示","编码或者名称已存在！", 'warning');
						}else if("success" == data) {
							$.messager.alert("提示","操作成功", 'info');
							$('#ParentOpenWindow').window('close');
							$('#bd_sqllist_table').datagrid('reload');
						}
					} 
				}); 			
			}
			$('#bdDictionaryaddFormSaveButton').bind('click',bdsqlDicaddFormSubmit);
			initSqlDicAddData();//下拉框初始化
			//初始化保存前后执行sql参数的datagrid
			var jsonData_paramtype = [{id:'1',name:'手工输入'},{id:'2',name:'系统变量'},{id:'3',name:'变量'},{id:'4',name:'输出参数'}];
			var lastIndex_sql_pramtable = null;
			$('#sql_prams_table').datagrid({
				nowrap: false,
				striped: true,
				fit: true,
				fitColumns:true,
				border:false,
				singleSelect:true,
				idField:'monitortime',
				title:'参数列表',
				url:'',
				//data:,
				rownumbers:true,
				toolbar:[{
					text:'增加',
					iconCls:'icon-add',
					titleCls:'link_btn_color',
					handler:function(){
						$('#sql_prams_table').datagrid('endEdit', lastIndex_sql_pramtable);
						$('#sql_prams_table').datagrid('appendRow',{
							key:'',
							type:'1',
							value:''
						});
						lastIndex_sql_pramtable = $('#sql_prams_table').datagrid('getRows').length-1;
						$('#sql_prams_table').datagrid('beginEdit', lastIndex_sql_pramtable);
					}
				},'-',{
					text:'删除',
					iconCls:'icon-remove',
					titleCls:'link_btn_color',
					handler:function(){
						var row = $('#sql_prams_table').datagrid('getSelected');
						if (row){
							var index = $('#sql_prams_table').datagrid('getRowIndex', row);
							$('#sql_prams_table').datagrid('deleteRow', index);
						}
					}
				},'-',{
					text:'保存',
					iconCls:'icon-save',
					titleCls:'link_btn_color',
					handler:function(){
						$('#sql_prams_table').datagrid('endEdit',lastIndex_sql_pramtable);
						lastIndex_sql_pramtable = null;
						var rows = $('#sql_prams_table').datagrid('getChanges');
						if(rows.length>0){
							for(var i = 0; i<rows.length;i++){
								var rowIndex = $('#sql_prams_table').datagrid('getRowIndex',rows[i])+1;
								if(rows[i].key == ''){
									$.messager.alert('提示','参数名称为空（第'+rowIndex+'行）','info');
									return;
								}else if(rows[i].type==''){
									$.messager.alert('提示','参数类型为空（第'+rowIndex+'行）','info');
									return;
								}else if(rows[i].value=='' && rows[i].type!='4'){
									$.messager.alert('提示','参数值为空（第'+rowIndex+'行）','info');
									return;
								}
							}
						
						}
						$('#sql_prams_table').datagrid('acceptChanges');
						rows = $('#sql_prams_table').datagrid('getRows');
						$('#sql_prams_hidden').val(JSON2.stringify(rows));
					}
				},'-',{
					text:'撤销操作',
					iconCls:'icon-undo',
					titleCls:'link_btn_color',
					handler:function(){
						$('#sql_prams_table').datagrid('rejectChanges');
					}
				}],
				columns:[[
					{field:'key',title:'参数名称',width:160,align:'center',editor:'text'},
					
					{field:'type',title:'参数类型',width:110,align:'center',formatter:function(value){
							if(value=='1'){
								return '手工输入';
							}else if(value=='2'){
								return '系统变量';
							}else if(value=='3'){
								return '变量';
							}else if(value=='4'){
								return '输出参数';
							}
						},editor:{
							type:'combobox',
							options:{valueField:'id',textField:'name',data:jsonData_paramtype}
						}
					},
					{field:'value',title:'参数值',width:110,align:'center',editor:'text'}
				]],
				onBeforeLoad:function(){
					$(this).datagrid('rejectChanges');
				},
				onClickRow:function(rowIndex){
					if (lastIndex_sql_pramtable != rowIndex){
						$('#sql_prams_table').datagrid('endEdit', lastIndex_sql_pramtable);
						$('#sql_prams_table').datagrid('beginEdit', rowIndex);
					}
					lastIndex_sql_pramtable = rowIndex;
				}
			
			})
		});
		function initSqlDicAddData(){
			//数据源
			$('#bd_sqldict_add_datasource').combobox({
			    url:'datasource/dataSource!getAllItem.action',
			    valueField:'id',
			    textField:'name',
			    editable:false
			});
			
			var jsonData = [{id:'1',name:'查询',selected:'selected'},{id:'2',name:'增删改'},{id:'3',name:'存储过程'},{id:'4',name:'创建触发器'}]
			//sql 操作类型
			$("#sqldic_type").combobox({
			    //url:'datasource/dataSource!getAllItem.action',
			    data:jsonData,
			    valueField:'id',
			    textField:'name',
			    editable:false
			});
		}
		
    </script>
  </head>  
  <body>
	<form id="bd_sqldicadd_form" method="post" action="dictionary/dataDictionary!addsql.action">
	   
		<div class="pop_col_col">
				
				<div class="pop_col_conc">
					<dl>
						<dd>
							字典ID：
						</dd>
						<dt>
							<input name="sqlObj.sqldicid" class="easyui-validatebox" size="23"/>
						</dt>
					</dl>
					<dl>
						<dd>
							字典名称：
						</dd>
						<dt>
							<input name="sqlObj.sqldicname" class="easyui-validatebox" size="23" required="true" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
					
				</div>
			</div>
			<div class="pop_col_color">
				<div class="pop_col_conc">
					<dl>
						<dd>
							类型：
						</dd>
						<dt>
							<input id="sqldic_type" name="sqlObj.sqldic_type" type="text" style="width:170px;">
						</dt>
					</dl>
					<dl>
						<dd>
							数据源：
						</dd>
						<dt>
							<select id="bd_sqldict_add_datasource" name="sqlObj.sqldic_dataSourceid" style="width:170px;"></select>&nbsp;
						</dt>
					</dl>
					
				</div>
					
				</div>
				<div class="pop_col_cons">
					<dl>
						<dd>
							表达式：
						</dd>
						<dt>
							<textarea cols="45" rows="4" name="sqlObj.sqldic_expression" class="easyui-validatebox" required="true"></textarea><br/>
						</dt>
					</dl>
				</div>
				<div  class="pop_col_cons" style="height: 150px;width: 580px;">
					<table id="sql_prams_table"></table>
					<input type="hidden" id="sql_prams_hidden" name="sql_prams_hidden">
					<input type="hidden" id="sql_isupdate_hidden" name="sql_isupdate_hidden" value="1">
				</div>
				<div class="pop_col_cons">
					<dl>
						<dd>描述：</dd>
						<dt>
						   <textarea cols="45" rows="4" name="sqlObj.sqldic_remark" class="easyui-validatebox" ></textarea>
						</dt>
					</dl>
				</div>
			 <div class="pop_col_cons">
		       <div align="center" style="width: 100%;height: 100%;padding-top: 20px;">
			 		<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bdDictionaryaddFormSaveButton">保存</a>
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="$('#ParentOpenWindow').window('close');">关闭</a>
		       </div>
			</div>
	</form>
  </body>
</html>
