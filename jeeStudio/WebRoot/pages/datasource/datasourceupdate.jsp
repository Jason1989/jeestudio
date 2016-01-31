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
			//initDataSourceEditData();
			function bdDatasourceupdateFormSubmit(){
				$('#bd_datasourceupdate_form').form('submit',{ 
					url:'datasource/dataSource!update.action', 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
						if("exist" == data) {						
							$.messager.alert("提示","数据源编码或者名称已存在！", 'warning');
						} else if("success" == data) {
							parent.$('#bd_datasource_update').window('close');
							$('#bd_datasourcelist_table').datagrid('reload');
						}
					} 
				}); 			
			}
			function bdDatasourceupdateTestConnection(){
				$('#bd_datasourceupdate_form').form('submit',{ 
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
			$('#bdDatasourceupdateFormTestConnectionButton').bind('click',bdDatasourceupdateTestConnection);
			$('#bdDatasourceupdateFormSaveButton').bind('click',bdDatasourceupdateFormSubmit);
		});
		function initDataSourceEditData(){
       		$('#dataSourceTypeEdit').combobox({		
				data:dataSourceTypeData,
				valueField:'id',
				textField:'text',
				editable:false
			});	
			$("#dataSourceTypeEdit").combobox("setValue","${dataSource.dbType}");
			$(":radio[value='${dataSource.state}']").attr("checked",true);
		}
    </script>
  </head>  
  <body>
	<form id="bd_datasourceupdate_form" method="post" action="datasource/dataSource!update.action">
		<div class="pop_col_col">
					<div class="pop_col_conc">
					  <dl>
						<dd>数据源编码：</dd>
						<dt><input name="dataSource.id" class="easyui-validatebox"  value="${dataSource.id}" readonly="readonly"/></dt>
					  </dl>
					  <dl>
						<dd>数据源名称：</dd>
						<dt>
						  <input id="dataSourceName"  name="dataSource.name" class="easyui-validatebox"  value="${dataSource.name}" />&nbsp;<font size="2" color="red">*</font>
						   <!--  
						     <script>$('#dataSourceName').validatebox(
							  		{required:true,
							 		validType:"dataSourceChecked[1,9]"
							 		})
							 </script>
							
							-->
						</dt>
					  </dl>
					</div>
					<div class="pop_col_conc">
					  <dl>
						<dd>数据源类型：</dd>
						<dt><select id="dataSourceTypeEdit" name="dataSource.dbType" style="width:150px;" required="true"></select>&nbsp;<font size="2" color="red">*</font></dt>
					  </dl>
					  <dl>
						<dd>服务器地址：</dd>
						<dt>
						  <input id="dataSourceIpAddress" name="dataSource.ipAddress" class="easyui-validatebox"  value="${dataSource.ipAddress}" />&nbsp;<font size="2" color="red">*</font>
						    <script>
						    $('#dataSourceIpAddress').validatebox(
							  		{required:true,
							  		validType:"IP"
							  		})
							 </script>
						</dt>
						</dl>
					  </div>
				  </div>
				  <div class="pop_col_color">
					<div class="pop_col_conc">
					  <dl>
						<dd>端口号：</dd>
						<dt><input ID="dataSourcePort" name="dataSource.port" class="easyui-numberbox"   value="${dataSource.port}"  />&nbsp;<font size="2" color="red">*</font></dt>
					       <script>$('#dataSourcePort').validatebox(
							  		{required:true,
							  		validType:"numberlength[1,4]"
							  		})
							 </script>   
					  </dl>
					  <dl>
						<dd>实例名：</dd>
						<dt>
						  <input id="dataSourceSid" name="dataSource.sid" class="easyui-validatebox"  value="${dataSource.sid}"  />&nbsp;<font size="2" color="red">*</font>
						   <script>$('#dataSourceSid').validatebox(
							  		{required:true,
							  		validType:"dataSourceChecked[1,9]"
							  		})
							 </script>   
						
						</dt>
						</dl>
					  </div>
					<div class="pop_col_conc">
					  <dl>
						<dd>用户名：</dd>
						<dt>
						  <input  name="dataSource.username" class="easyui-validatebox"  required="true"  value="${dataSource.username}"  />&nbsp;<font size="2" color="red">*</font>
						    <script>$('#dataSourceSid').validatebox(
							  		{required:true,
							  		validType:"dataSourceChecked[1,9]"
							  		})
							 </script>   
						</dt>
					  </dl>
					  <dl>
						<dd>口令：</dd>
						<dt>
						  <input name="dataSource.password" class="easyui-validatebox"  style="width:149px;"  required="true"  value="${dataSource.password}" />&nbsp;<font size="2" color="red">*</font>
						</dt>
						</dl>
					  </div>
				  </div>
				  <div class="pop_col_col">
					<div class="pop_col_conc">
					  <dl>
						<dd>状态：</dd>
						<dt><input id="dataSource_state1" type="radio" value="1" name="dataSource.state" />可用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="dataSource_state2" type="radio" value="2" name="dataSource.state" />不可用</dt>
					  </dl>
					  </div>
				</div>
				<div class="pop_col_col">
					<div class="pop_col_cons">
					  <dl>
						<dd>描述：</dd>
						<dt>
						  <textarea cols="45" rows="4" name="dataSource.description" class="easyui-validatebox">${dataSource.description}</textarea>
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
				  	注意：修改数据源之后，请重新启动应用服务器（例如：Tomcat），重新初始化连接池。
				  </div>
				  <div style="float:left;padding:80px 0 0 180px;">
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-redo" id="bdDatasourceupdateFormTestConnectionButton">连接测试</a>
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bdDatasourceupdateFormSaveButton">保存</a>
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_datasource_update').window('close');">关闭</a>
				  </div>
	</form>
  </body>
 
</html>
