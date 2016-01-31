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
			function bdDictionaryaddFormSubmit(){
				$('#bd_dictionaryadd_form').form('submit',{ 
					url:'dictionary/dataDictionary!add.action', 
					onSubmit:function(){ 
						if($('#bd_dict_add_dictionarygroup').combobox('getValue') == "-1"){
							$.messager.alert("提示","请选择字典分组！", 'warning');
							return false;
						}
						if($(":radio[name='dataDictionary.type'][checked=true]").val() == 2 && $('#bd_dict_add_datasource').combobox('getValue') == "-1"){
							$.messager.alert("提示","请选择数据源！", 'warning');
							return false;
						}
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
						if("exist" == data) {						
							$.messager.alert("提示","字典编码或者名称已存在！", 'warning');
						}
						else if("sqlError" == data){
							$.messager.alert("提示","表达式: sql语句错误，只允许添加一条sql，请输入正确！", 'warning');
						}
						else if("success" == data) {
							parent.$('#bd_dictionary_add').window('close');
							$('#bd_dictionarylist_table').datagrid('reload');
						}
					} 
				}); 			
			}
			$('#bdDictionaryaddFormSaveButton').bind('click',bdDictionaryaddFormSubmit);
			initDictionaryAddData();//下拉框初始化
		});
		function initDictionaryAddData(){
			$('#bd_dict_add_dictionarygroup').combobox({
			    url:'dictionary/dictionaryGroup!getAllItem.action?random='+new Date(),
			    valueField:'id',
			    textField:'name',
			    editable:false
			});
			setTimeout(function(){
				$('#bd_dict_add_dictionarygroup').combobox('setValue','-1');
			},0);
			$('#bd_dict_add_datasource').combobox({
				    url:'datasource/dataSource!getAllItem.action',
				    valueField:'id',
				    textField:'name',
				    editable:false
				});
				$("#bd_dictionaryadd_datasource").css("display","none");//默认不显示
		}
		function setDatasourceDisplayAdd(value){
			if(value=='1'){
				$(":input[name='dataDictionary.dataSourceId']").val("");	
				$("#bd_dictionaryadd_datasource").css("display","none");
				//$("#dictionaryAddExcpression").validatebox({validType:'dicType',invalidMessage:'正则表达式格式不正确'});
			}else if(value=='2'){
				$("#bd_dictionaryadd_datasource").css("display","");
				$('#bd_dict_add_datasource').combobox('setValue','-1');
				//$("#dictionaryAddExcpression").validatebox({validType:'length[0,10000]',invalidMessage:'长度过长！'});
			}
		}
    </script>
  </head>  
  <body>
	<form id="bd_dictionaryadd_form" method="post" action="dictionary/dataDictionary!add.action">
	   <!-- 
		<table width="100%" border="0" cellpadding="1" cellspacing="0" class="table_form1">			
			<tr>
				<td width="30%" align="right">字典编码：</td>
				<td><input name="dataDictionary.id" class="easyui-validatebox" size="30" /></td>
			</tr>
			<tr>
				<td width="30%" align="right">字典分组：</td>
				<td><select id="bd_dict_add_dictionarygroup" name="dataDictionary.dictionaryGroup.id" style="width:211px;" required="true"></select>&nbsp;&nbsp;<font size="2" color="red">*</font></td>
			</tr>
			<tr>
				<td width="30%" align="right">字典名称：</td>
				<td><input name="dataDictionary.name" class="easyui-validatebox" size="30" required="true" />&nbsp;&nbsp;<font size="2" color="red">*</font></td>
			</tr>
			<tr>
				<td align="right">类型：</td>
				<td><input type="radio" value="1" name="dataDictionary.type" onclick="setDatasourceDisplayAdd(1)" checked />静态&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" value="2" name="dataDictionary.type" onclick="setDatasourceDisplayAdd(2)"/>动态
				</td>
			</tr>
			<tr id="bd_dictionaryadd_datasource" style="display:none">
				<td align="right">数据源：</td>
				<td><select id="bd_dict_add_datasource" name="dataDictionary.dataSource.id" style="width:211px;"></select>&nbsp;&nbsp;<font size="2" color="red">*</font></td>
			</tr>
			<tr>
				<td align="right">表达式：</td>
				<td><textarea cols="45" rows="4" name="dataDictionary.expression" class="easyui-validatebox" required="true"></textarea>&nbsp;&nbsp;<font size="2" color="red">*</font><br/>
				<font color="blue">说明：字典键值以英文“ , ”分隔，字典项以英文“ ; ”分隔</font></td>
			</tr>
			<tr>
				<td align="right">描述：</td>
				<td><textarea cols="45" rows="4" name="dataDictionary.description" class="easyui-validatebox" ></textarea></td>
			</tr>
			<tr>
				<td align="right">状态：</td>
				<td><input type="radio" value="1" name="dataDictionary.state" checked />可用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" value="2" name="dataDictionary.state" />不可用
				</td>
			</tr>
			<tr>
				<td align="right">序号：</td>
				<td><input name="dataDictionary.sortNo" class="easyui-numberbox" size="20"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bdDictionaryaddFormSaveButton">保存</a>
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_dictionary_add').window('close');">关闭</a>
				</td>
			</tr>
		</table>
	    -->
		<div class="pop_col_col">
				<div class="pop_col_conc">
					<dl>
						<dd>
							字典编码：
						</dd>
						<dt>
							<input name="dataDictionary.id" class="easyui-validatebox" validType="entityCode[1,50]" size="20" />
						</dt>
					</dl>
					<dl>
						<dd>
							字典分组：
						</dd>
						<dt>
							<select id="bd_dict_add_dictionarygroup" name="dataDictionary.dictionaryGroup.id" style="width:153px;" required="true"></select> <font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							字典名称：
						</dd>
						<dt>
							<input name="dataDictionary.name" class="easyui-validatebox" size="20" required="true" /> <font size="2" color="red">*</font>
						</dt>
					</dl>
					<!-- 
					<dl>
						<dd>
							状态：
						</dd>
						<dt>
							<input type="radio" value="1" name="dataDictionary.state" checked />可用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="2" name="dataDictionary.state" />不可用
						</dt>
					</dl>
					 -->
					<dl>
						<dd>
							类型：
						</dd>
						<dt>
							<input type="radio" value="1" name="dataDictionary.type" onclick="setDatasourceDisplayAdd(1)" checked />静态&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="2" name="dataDictionary.type" onclick="setDatasourceDisplayAdd(2)"/>动态
						</dt>
					</dl>
				</div>
			</div>
			<div class="pop_col_color">
					<div class="pop_col_conc">
					<dl>
						<dd>
							序号：
						</dd>
						<dt>
							<input name="dataDictionary.sortNo" class="easyui-validatebox" size="20" validType="sortlength[1,8]" />
						</dt>
					</dl>
					</div>
				<div class="pop_col_conc">
					<dl id="bd_dictionaryadd_datasource">
						<dd>
							数据源：
						</dd>
						<dt>
							<select id="bd_dict_add_datasource" name="dataDictionary.dataSource.id" style="width:153px;"></select> <font size="2" color="red">*</font>
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
							<textarea cols="45" rows="4" name="dataDictionary.expression" class="easyui-validatebox" id="dictionaryAddExcpression" required="true" ></textarea>&nbsp;&nbsp;<font size="2" color="red">*</font><br/>							
							<font color="blue">说明：字典键值以英文"= "分隔，字典项以英文" ,"分隔</font><br/>
							<font color="blue">说明：动态数据字典树 select id,name,parent_id from table </font></br>
							<font color="blue">说明：节点ID(仅显示节点和其子节点，不显示该节点上级节点,不填默认显示所有节点)</font></br>
							节点ID：<input type="text" name="dic_root_id" id="dic_root_id">
						</dt>
					</dl>
				</div>
				<div class="pop_col_cons">
					<dl>
						<dd>描述：</dd>
						<dt>
						   <textarea cols="45" rows="4" name="dataDictionary.description" class="easyui-validatebox" ></textarea>
						</dt>
					</dl>
				</div>
			 <div class="pop_col_cons">
			       <div align="center" style="width: 100%;height: 100%;padding-top: 20px;">
				 		<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bdDictionaryaddFormSaveButton">保存</a>
						<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_dictionary_add').window('close');">关闭</a>
			       </div>
				</div>
	</form>
  </body>
</html>
