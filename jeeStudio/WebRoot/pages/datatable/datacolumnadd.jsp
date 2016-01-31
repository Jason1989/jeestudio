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
	<script type="text/javascript" src="js/basedata.common.js"></script>
	
	<script>
			
	$(function(){
				$.extend($.fn.validatebox.defaults.rules, {   
					    number: {   
					        validator: function (value, param) {
	            			return /^\d+$/.test(value)&&(value.length >= param[0])&&(value.length <= param[1]);
	        				},
	        				message: '请输入数字，至少 {0} 位,最长 {1} 位.'
					    }   
					});  
	
	});

	</script>
	<script language="javascript" type="text/javascript">
		$(function(){
			function bdDatacolumnaddFormSubmit(){
				$('#bd_datacolumnadd_form').form('submit',{ 
					url:'datacolumn/dataColumn!add.action', 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
						if("exist" == data) {						
							$.messager.alert("提示","数据列编码或者名称已存在！", 'warning');
						} else if("success" == data) {
							parent.$('#bd_datacolumn_add').window('close');
							$('#bd_datacolumnlist_table').datagrid('reload');
						}
					} 
				}); 			
			}
			$('#bd_dcadd_formcancel_button').linkbutton();
			$('#bdDatacolumnaddFormSaveButton').bind('click',bdDatacolumnaddFormSubmit);
			$('#bdDatacolumnaddFormSaveButton').linkbutton();
			$.parser.parse("#bd_datacolumnadd_form");
		});
		function initDataColumnAddData(){
			$('#bddatacolumnadd_type').combobox({		
				data:dataColumnDataTypeData,
				//url:'dictionary/dataDictionary!getDictItem.action?dictionaryId=c90053b13993345a461f3c13d5c02f0d',
				valueField:'id',
				textField:'text',
				editable:false
			});
			$('#bddatacolumnadd_scale').combobox({		
				data:dataColumnScaleData,
				valueField:'id',
				textField:'text',
				editable:false
			});
			
		}
    </script>
  </head>  
  <body>
	<form id="bd_datacolumnadd_form" method="post" action="datacolumn/dataColumn!add.action">
		<input name="dataColumn.dataTable.id" type="hidden" value="${dataTableId}"/>
		<input name="dataColumn.istemp" type="hidden" value="0"/>
		<!-- 
		<table width="100%" border="0" cellpadding="1" cellspacing="0" class="table_form1">	
			<tr>
				<td width="30%" align="right">数据对象：</td>
				<td><input class="easyui-validatebox" size="30" value="${dataTable.name}" readonly/></td>
			</tr>
			<tr>
				<td width="30%" align="right">字段标题：</td>
				<td><input name="dataColumn.title" class="easyui-validatebox" size="30" /></td>
			</tr>
			<tr>
				<td width="30%" align="right">名称：</td>
				<td><input name="dataColumn.name" class="easyui-validatebox" required="true" size="30" />&nbsp;&nbsp;<font size="2" color="red">*</font></td>
			</tr>
			<tr>
				<td align="right">字段类型：</td>
				<td><select id="bddatacolumnadd_type" name="dataColumn.dataType" style="width:151px;" required="true">
					
				</select>&nbsp;&nbsp;<font size="2" color="red">*</font>
				</td>
			</tr>	
			<tr>
				<td align="right">长度：</td>
				<td><input name="dataColumn.dataLength" class="easyui-numberbox" required="true" />&nbsp;&nbsp;<font size="2" color="red">*</font></td>
			</tr>
			<tr>
				<td align="right">精度：</td>
				<td><input name="dataColumn.precision" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<td align="right">级别：</td>
				<td><select id="bddatacolumnadd_scale" name="dataColumn.scale" style="width:151px;">
				</select></td>
			</tr>
			<tr>
				<td align="right">是否为空：</td>
				<td><input type="radio" value="0" name="dataColumn.nullable"  checked />是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" value="1" name="dataColumn.nullable" />否</td>
			</tr>
			<tr>
				<td align="right">默认值：</td>
				<td><input name="dataColumn.defaultValue" class="easyui-numberbox" /></td>
			</tr>
			<tr>
				<td align="right">是否为系统字段：</td>
				<td><input type="radio" value="0" name="dataColumn.sysColumn"  checked />否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" value="1" name="dataColumn.sysColumn"/>是</td>
			</tr>
			<tr>
				<td align="right">序号：</td>
				<td><input name="dataColumn.sortNo" class="easyui-numberbox" /></td>
			</tr>
			<tr>
				<td align="right">备注：</td>
				<td><textarea name="dataColumn.description" cols="30" rows="4" ></textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bdDatacolumnaddFormSaveButton">保存</a>
					<a id="bd_dcadd_formcancel_button" href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_datacolumn_add').window('close');">关闭</a>
				</td>
			</tr>
		</table>
		 -->		
		<div class="pop_col_col">
				<div class="pop_col_conc">
					<dl>
						<dd>
							数据对象：
						</dd>
						<dt>
							<input class="easyui-validatebox" size="21" value="${dataTable.name}" readonly/>
						</dt>
					</dl>
					<dl>
						<dd>
							字段标题：
						</dd>
						<dt>
							<input name="dataColumn.title" class="easyui-validatebox" required="true" size="21" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							名称：
						</dd>
						<dt>
							<input name="dataColumn.name" class="easyui-validatebox" required="true" size="21" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
					<dl>
						<dd>
							字段类型：
						</dd>
						<dt>
							<select id="bddatacolumnadd_type" name="dataColumn.dataType" style="width:155px;" required="true">
							</select>&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
			</div>
			<div class="pop_col_color">
				<div class="pop_col_conc">
					<dl>
						<dd>
							长度：
						</dd>
						<dt>
							<input name="dataColumn.dataLength" class="easyui-validatebox" validType="number[1,4]" required="true" size="21" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
					
					
					<dl id="bd_dictionaryadd_datasource">
						<dd>
							精度：
						</dd>
						<dt>
							<input name="dataColumn.precision" class="easyui-validatebox" validType="number[1,4]" size="21"/>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							级别：
						</dd>
						<dt>
							<select id="bddatacolumnadd_scale" name="dataColumn.scale" style="width:155px;">
							</select>
						</dt>
					</dl>
					<dl>
						<dd>
							是否为空：
						</dd>
						<dt><input type="radio" value="0" name="dataColumn.nullable"  checked />是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="1" name="dataColumn.nullable" />否
						</dt>
					</dl>
				</div>
			</div>
			<div class="pop_col_col" >
				<div class="pop_col_conc">
					<dl>
						<dd>
							默认值：
						</dd>
						<dt>
							<input name="dataColumn.defaultValue" class="easyui-numberbox"  size="21"/>
						</dt>
					</dl>
					<dl>
						<dd>是否为系统字段：</dd>
						<dt>
						    <input type="radio" value="0" name="dataColumn.sysColumn"  checked />否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="1" name="dataColumn.sysColumn"/>是
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>序号：</dd>
						<dt>
						    <input name="dataColumn.sortNo" class="easyui-numberbox"  size="21"/>
						</dt>
					</dl>
				</div>
				<div class="pop_col_cons">
					<dl>
						<dd>备注：</dd>
						<dt>
						    <textarea name="dataColumn.description" cols="50" rows="4"  style="font-size: 12px;padding: 2px;"></textarea>
						</dt>
					</dl>
				</div>
			 	<div class="pop_col_cons">
			       <div align="center" style="width: 100%;height: 100%;padding-top: 20px;padding-left: 45px;">
				 		<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bdDatacolumnaddFormSaveButton">保存</a>
						<a id="bd_dcadd_formcancel_button" href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_datacolumn_add').window('close');">关闭</a>
					</div>
				</div>
		</div>
	</form>
  </body>
</html>
