<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
		$(function(){
			//数据字典表单提交
			function bdDictionaryupdateFormSubmit(){
				$('#bd_dictionaryupdate_form').form('submit',{ 
					url:'dictionary/dataDictionary!update.action', 
					onSubmit:function(){ 
						if($('#bd_dict_edit_dictionarygroup').combobox('getValue') == "-1"){
							$.messager.alert("提示","请选择字典分组！", 'warning');
							return false;
						}
						if($(":radio[name='dataDictionary.type'][checked=true]").val() == 2 && $('#bd_dict_edit_datasource').combobox('getValue') == "-1"){
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
							parent.$('#bd_dictionary_update').window('close');
							$('#bd_dictionarylist_table').datagrid('reload');
						}
					} 
				}); 			
			}
			$('#bdDictionaryupdateFormSaveButton').bind('click',bdDictionaryupdateFormSubmit);
			initDictionaryEditData();//初始化
			
			//数据库依赖缓存处理
		    var datasoureId = $("#bd_dict_edit_datasource").combobox('getValue');
		    var autoSql = $("#bd_dictionaryupdate_form textarea[name=dataDictionary.expression]").val();
			$('#dbDependencyAdd,#dbDependencyEdit').click(function(event){
				//判断相应的选项是不是已经填写完毕
				   //1、数据源是不是已经填写
				   if(!datasoureId || datasoureId == '-1' || datasoureId == ''){
				   	    $.messager.alert('info','数据源不能为空！','warning');
				   	    return;
				   }
				   //2、动态查询sql不能为空，而且不能存在验证一下sql是不是正确
				   var autoSqlExchange = autoSql.replace(/[\[\]]/g,''); 
				   $.post('datasource/dataSource!testSQL.action',{dataSourceId:datasoureId,sql:autoSqlExchange},function(data){
				      alert(data);
				      if(data == 'dbErro'){
				           $.messager.alert('提示','数据源不存在','warning');
				      }else if(data == 'sqlError'){
				      	  // $.messager.alert('提示','表达式有错误请更正！','warning');
				      }else if(data == 'fail'){
				      	 // $.messager.alert('提示','系统出错，请稍候再试……！','error');
				      }else{
					       easyuiWinNew({width:560,height:280,title:'数据库依赖缓存配置',
					       href:'dictionary/dictionaryCache_toDictionarySettingPage.action?sql=' + autoSqlExchange + '&dataSourceId=' + datasoureId},2);
				      }
				   });
			});
			//清楚缓存记录和表单
			$('#dbDependencyRemove').click(function(event){
				$("#cacheAndTriggerClearForm").form('submit',{
		   	   		url:'dictionary/dictionaryCache_clearCacheAndTriiger.action',
		   	   		onSubmit:function(){
		   	   		   return $(this).form('validate');
		   	   		},
		   	   		success:function(data){
		   	   		    var result = $.parseJSON(data);
		   	   		    if(result.flag == 'success'){
		   	   		    	//提交完毕之后，改变数据字典编辑页上的按钮
		   	   		    	checkDicCacheSettingFlagFun(datasoureId,autoSql);
		   	   		    } else if(result.flag == 'systemError'){
		   	   		    	$.messager.alert('提示','参数异常！','warning');
		   	   		    } else {
		   	   		    	$.messager.alert('提示','系统出错请稍候再试！','warning');
		   	   		    }
		   	   		}
		   	   	});
			});
			
			//查询数据字典是不是已经缓存了
			checkDicCacheSettingFlagFun(datasoureId,autoSql);
		});
		
		//查询数据字典是不是已经缓存了
		function checkDicCacheSettingFlagFun(datasoureId,autoSql){
			if(datasoureId && datasoureId != '-1' && datasoureId != '' && autoSql && autoSql.length > 0){
				$.post('dictionary/dictionaryCache_getCacheSettingFlag.action',{dataSourceId:datasoureId,sql:autoSql},function(data){
					var reData = $.parseJSON(data);
					if(reData.flag == 'yes'){
						$('#dbDependencyAdd').hide();
						$('#dbDependencyEdit').show();
						$('#dbDependencyRemove').show();
						//给清楚表单赋值
						$("#cacheAndTriggerClearForm input[name=cacheKey]").val(reData.cacheKey);
						$("#cacheAndTriggerClearForm input[name=triggerName]").val(reData.triggerName);
						$("#cacheAndTriggerClearForm input[name=dataSourceId]").val(datasoureId);
					}else if(reData.flag == 'no'){
						$('#dbDependencyAdd').show();
						$('#dbDependencyEdit').hide();
						$('#dbDependencyRemove').hide();
					}else{
				      	 //$.messager.alert('提示','系统出错，请稍候再试……！','error');
					}
				});
			}
		}
		
		function initDictionaryEditData(){	
			$(":radio[name='dataDictionary.type'][value='${dataDictionary.type}']").attr("checked",true);
			$(":radio[name='dataDictionary.state'][value='${dataDictionary.state}']").attr("checked",true);		
			setDatasourceDisplayEdit('${dataDictionary.type}');
			$('#bd_dict_edit_dictionarygroup').combobox({
			    url:'dictionary/dictionaryGroup!getAllItem.action?random='+parseInt(10*Math.random()),
			    valueField:'id',
			    textField:'name',
			    editable:false
			});
			$('#bd_dict_edit_dictionarygroup').combobox("setValue",'${dataDictionary.dictionaryGroup.id}');
		}
		function setDatasourceDisplayEdit(value){
			if(value=='1'){
				$(":input[name='dataDictionary.dataSourceId']").val("");	
				$("#bd_dictionaryupdate_datasource").css("display","none");
				$("#dictionaryEditExcpression").validatebox({validType:'dicType',invalidMessage:'正则表达式格式不正确'});
			}else if(value=='2'){
				$("#bd_dictionaryupdate_datasource").css("display","");
				$('#bd_dict_edit_datasource').combobox({
				    url:'datasource/dataSource!getAllItem.action',
				    valueField:'id',
				    textField:'name',
				    editable:false
				});
				$('#bd_dict_edit_datasource').combobox("setValue",'${dataDictionary.dataSource.id}');
				$("#dictionaryEditExcpression").validatebox({validType:'length[0,10000]',invalidMessage:'长度过长！'});
			}
		}
    </script>
  </head>  
  <body>
	<form id="bd_dictionaryupdate_form" method="post" action="dictionary/dataDictionary!update.action">
	<!-- 
		<table width="100%" border="0" cellpadding="1" cellspacing="0" class="table_form1">
			<tr>
				<td width="30%" align="right">字典编码：</td>
				<td><input name="dataDictionary.id" class="easyui-validatebox" size="30" value="${dataDictionary.id }" readonly/></td>
			</tr>
			<tr>
				<td width="30%" align="right">字典分组：</td>
				<td><select id="bd_dict_edit_dictionarygroup" name="dataDictionary.dictionaryGroup.id" style="width:211px;" required="true"></select>&nbsp;&nbsp;<font size="2" color="red">*</font></td>
			</tr>
			<tr>
				<td width="30%" align="right">字典名称：</td>
				<td><input name="dataDictionary.name" class="easyui-validatebox" size="30" required="true" value="${dataDictionary.name }"/>&nbsp;&nbsp;<font size="2" color="red">*</font></td>
			</tr>
			<tr>
				<td align="right">类型：</td>
				<td><input type="radio" value="1" name="dataDictionary.type" onclick="setDatasourceDisplayEdit(1)"/>静态&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" value="2" name="dataDictionary.type" onclick="setDatasourceDisplayEdit(2)"/>动态
				</td>
			</tr>
			<tr id="bd_dictionaryupdate_datasource" style="display:none">
				<td align="right">数据源：</td>
				<td><select id="bd_dict_edit_datasource" name="dataDictionary.dataSource.id" style="width:211px;"></select></td>
			</tr>
			<tr>
				<td align="right">表达式：</td>
				<td><textarea cols="45" rows="4" name="dataDictionary.expression" class="easyui-validatebox" required="true">${dataDictionary.expression }</textarea>&nbsp;&nbsp;<font size="2" color="red">*</font><br/>
				<font color="blue">说明：字典键值以英文“ , ”分隔，字典项以英文“ ; ”分隔</font></td>
			</tr>
			<tr>
				<td align="right">描述：</td>
				<td><textarea cols="45" rows="4" name="dataDictionary.description" class="easyui-validatebox" >${dataDictionary.description }</textarea></td>
			</tr>
			<tr>
				<td align="right">状态：</td>
				<td><input type="radio" value="1" name="dataDictionary.state" />可用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" value="2" name="dataDictionary.state" />不可用
				</td>
			</tr>
			<tr>
				<td align="right">序号：</td>
				<td><input name="dataDictionary.sortNo" value="${dataDictionary.sortNo}" class="easyui-numberbox" size="20"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bdDictionaryupdateFormSaveButton">保存</a>
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_dictionary_update').window('close');">关闭</a>
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
							<input name="dataDictionary.id" class="easyui-validatebox" size="20" value="${dataDictionary.id }" readonly="readonly"/>
						</dt>
					</dl>
					<dl>
						<dd>
							字典分组：
						</dd>
						<dt>
							<select id="bd_dict_edit_dictionarygroup" name="dataDictionary.dictionaryGroup.id" style="width:153px;" required="true"></select> <font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							字典名称：
						</dd>
						<dt>
							<input name="dataDictionary.name" class="easyui-validatebox" size="20" required="true" value="${dataDictionary.name }"/> <font size="2" color="red">*</font>
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
							<input name="dataDictionary.sortNo" class="easyui-numberbox" validType="sortlength[1,8]" size="23" value="${dataDictionary.sortNo}"/>
						</dt>
					</dl>
					</div>
				<div class="pop_col_conc">
					<dl id="bd_dictionaryupdate_datasource">
						<dd>
							数据源：
						</dd>
						<dt>
							<select id="bd_dict_edit_datasource" name="dataDictionary.dataSource.id" style="width:153px;"></select> <font size="2" color="red">*</font>
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
							<textarea cols="45" rows="4" name="dataDictionary.expression" class="easyui-validatebox" required="true" validType="${dataDictionary.type=='1'?'dicType':'length[0,10000]'}" id="dictionaryEditExcpression">${dataDictionary.expression }</textarea>&nbsp;&nbsp;<font size="2" color="red">*</font><br/>
							父节点ID：<input type="text" name="dic_root_id" id="dic_root_id" value="${dataDictionary.dic_root_id}"></br>
							<p style="color: blue;white-space: 2;margin:2px 0;" >说明：字典键值以英文"= "分隔，字典项以英文" ,"分隔 ;<br> &nbsp; &nbsp;  &nbsp; &nbsp; &nbsp; &nbsp;  动态数据字典树 select id,name,parent_id from table </p>
						</dt>
					</dl>
				</div>
				<div class="pop_col_cons" style="height:60px;">
					<dl>
						<dd>描述：</dd>
						<dt>
						   <textarea cols="45" rows="2" style="height:50px;" name="dataDictionary.description" class="easyui-validatebox" >${dataDictionary.description }</textarea>
						</dt>
					</dl>
				</div>
				<c:if test="${param.dicType eq 'auto'}">
					<div class="pop_col_conc">
						<dl>
							<dd>
								数据库依赖缓存：
							</dd>
							<dt>
								<img src="jquery-easyui-1.1.2/themes/icons/drive_disk.png"
									style="cursor: hand;display: none;" title="使用数据库依赖缓存" id="dbDependencyAdd"/>
								&nbsp;&nbsp;
								<img src="jquery-easyui-1.1.2/themes/icons/drive_edit.png"
									style="cursor: hand;display: none;" title="编辑数据库依赖缓存" id="dbDependencyEdit"/>
								&nbsp;&nbsp;
								<img src="jquery-easyui-1.1.2/themes/icons/drive_delete.png"
									style="cursor: hand;display: none;" title="取消数据库依赖缓存" id="dbDependencyRemove"/>
								&nbsp;&nbsp;
							</dt>
						</dl>
						<dl>
							<dd>
	
							</dd>
							<dt>
								&nbsp;
							</dt>
						</dl>
					</div>
				</c:if>
				
			 <div class="pop_col_cons">
			       <div align="center" style="width: 100%;height: 100%;padding-top: 20px;">
				 		<a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-save" id="bdDictionaryupdateFormSaveButton">保存</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_dictionary_update').window('close');">关闭</a>
				</div>
				</div>
	</form>
	<form id="cacheAndTriggerClearForm" method="post">
		<input type="hidden" name="cacheKey">
		<input type="hidden" name="triggerName">
		<input type="hidden" name="dataSourceId">
	</form>
  </body>
</html>
