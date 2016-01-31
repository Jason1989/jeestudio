<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/include.jsp"%>
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
	<script>
	function closeDicKind() {

		//$("#bd_datasourceadd_form").form("clear");
		parent.$('#bd_datasource_add').window('close');

} 
	</script>
	
	

	<script language="javascript" type="text/javascript">		
		$(function(){
			//initDataSourceTypeAdd();
			
			function bdDatasourceaddFormSubmit(){
				$('#bd_datasourceadd_form').form('submit',{ 
					url:'datasource/dataSource!add.action', 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
						if("exist" == data) {						
							$.messager.alert("提示","数据源编码或者名称已存在！", 'warning');
						} else if("success" == data) {
							parent.$('#bd_datasource_add').window('close');
							$('#bd_datasourcelist_table').datagrid('reload');
						}
					} 
				}); 			
			}
			
			function bdDatasourceaddTestConnection(){
				$('#bd_datasourceadd_form').form('submit',{ 
					url:'datasource/dataSource!testDBConnection.action', 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
						if("success" == data) {
							$.messager.alert("提示","连接成功！", 'info');
						} else if("fail" == data){						
							$.messager.alert("提示","连接失败！", 'warning');
						} 
					} 
				}); 			
			}
			
			$('#bdDatasourceaddFormSaveButton').bind('click',bdDatasourceaddFormSubmit);
			$('#bdDatasourceaddFormTestConnectionButton').bind('click',bdDatasourceaddTestConnection);
		//	$.parser.parse("#bd_datasourceadd_form");
		});
		function initDataSourceTypeAdd(){
		
       
			//设置数据源的默认值
			//$('#dataSourceTypeAdd').combobox('setValue','-1');
		}
    </script>
  </head>  
  <body>
	<form id="bd_datasourceadd_form" method="post" action="datasource/dataSource!add.action">
	<div class="pop_col_col">
					<div class="pop_col_conc">
					  <dl>
						<dd>数据源编码：</dd>
						<dt><input name="dataSource.id" class="easyui-validatebox" validType="maxlength_isExist['datasource/dataSource!idExist.action',15]"/></dt>
					  </dl>
					  <dl>
						<dd>数据源名称：</dd>
						<dt>
						  <input name="dataSource.name" class="easyui-validatebox"  required="true"  />&nbsp;<font size="2" color="red">*</font>
						</dt>
					  </dl>
					</div>
					<div class="pop_col_conc">
					  <dl>
						<dd>数据源类型：</dd>
						<dt><select id="dataSourceTypeAdd" name="dataSource.dbType" style="width:150px;" required="true"></select>&nbsp;<font size="2" color="red">*</font></dt>
					  </dl>
					  <dl>
						<dd>服务器地址：</dd>
						<dt> <!-- validType="IP" -->
						  <input name="dataSource.ipAddress" class="easyui-validatebox"  required="true" />&nbsp;<font size="2" color="red">*</font>
						</dt>
						</dl>
					  </div>
				  </div>
				  <div class="pop_col_color">
					<div class="pop_col_conc">
					  <dl>
						<dd>端口号：</dd>
						<dt><input name="dataSource.port" class="easyui-validatebox" validType="numberlength[1,4]" required="true" />&nbsp;<font size="2" color="red">*</font></dt>
					  </dl>
					  <dl>
						<dd>实例名：</dd>
						<dt>
						  <input name="dataSource.sid" class="easyui-validatebox" validType="dataSourceChecked[1,9]" required="true" />&nbsp;<font size="2" color="red">*</font>
						</dt>
						</dl>
					  </div>
					<div class="pop_col_conc">
					  <dl>
						<dd>用户名：</dd>
						<dt><!-- validType="dataSourceChecked[1,9]"  -->
						  <input name="dataSource.username" class="easyui-validatebox" required="true" />&nbsp;<font size="2" color="red">*</font>
						</dt>
					  </dl>
					  <dl>
						<dd>口令：</dd>
						<dt>
						  <input name="dataSource.password" class="easyui-validatebox"  required="true" style="width:149px;" />&nbsp;<font size="2" color="red">*</font>
						</dt>
						</dl>
					  </div>
				  </div>
				  <div class="pop_col_col">
					<div class="pop_col_conc">
					  <dl>
						<dd>状态：</dd>
						<dt><input type="radio" id ="stateUsed" value="1" name="dataSource.state"  />可用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" id ="stateUnUsed" value="2" name="dataSource.state" />不可用</dt>
					  </dl>
					  </div>
				</div>	  
				
				  <div class="pop_col_col">	  
					<div class="pop_col_cons">
					  <dl>
						<dd>描述：</dd>
						<dt>
						  <textarea cols="45" rows="4" name="dataSource.description" class="easyui-validatebox"></textarea>
						</dt>
					  </dl>
					  <dl>
						<dd> </dd>
						<dt>
						   
						</dt>
						</dl>
					  </div>
				  </div>
				  <div style="float:left;padding:10px 0 0 0px;">
				  	注意：添加新的数据源之后，请重新启动应用服务器（例如：Tomcat），重新初始化连接池。
				  </div>  
				  <div style="float:left;padding:80px 0 0 180px;">
				  	<a href="javascript:;" class="easyui-linkbutton" icon="icon-redo" id="bdDatasourceaddFormTestConnectionButton">连接测试</a>
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bdDatasourceaddFormSaveButton">保存</a>
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_datasource_add').window('close');">关闭</a>
				  </div>
					 
	
	</form>
  </body>
</html>
