<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
    <title>列表页配置</title>    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<script type="text/javascript" src="js/debug/form.pageset.debug.js"></script>
	<script type="text/javascript" src="common/js/common-util/configuration-util.js" ></script>
	<script language="javascript" type="text/javascript">
		function fmDataDictionaryShowKeyUp(evt,o,treeName){
			evt = (evt) ? evt : window.event;
			var valueStr = o.value;
			if(valueStr != null && valueStr != "" && valueStr.replace(/^\s+|\s+$/g,"") != ""){
				$.post("form/form!getDictionaryLikeName.action",{"dictName":o.value},
	           		function(data){           			
	           			var dataObj = eval('('+data+')');
		          		$('#'+treeName).tree("loadData",dataObj);
	       			}
	       		);
       		}else{
       			$('#'+treeName).tree("loadData",fmPagesetDataDictionary);
       		}
		}
		$(function(){			
			//datacolumnset begin
			
            //datacolumnset end
            //queryset begin
			
            $('#fm_listpageset_queryset_config').appendTo('body').window({
				title: '组合查询列配置',
				width: 550,
				modal: true,
				shadow: false,
				closed: true,
				minimizable: false,
				collapsible:false,
				height: 350
			});
            //queryset end
			$('#fm_listpageset_querysetconfig_save').linkbutton();
			$('#fm_listpageset_querysetconfig_cancel').linkbutton();
		});		
		function listPageSetSubmit(formsetting){
			$.post("form/form!updateFormSettings.action",{"formId":formId,"formSettings":'<?xml version="1.0" encoding="UTF-8"?>' + formsetting},
           		function(data){
	          		if("success" == data) {
	          			refreshListPageSetData();
	          			fdFormDataGridRefresh();
						$.messager.alert("提示","保存成功！", 'info');
						fmRefreshDesignListIframe();
					}
       			}
       		);
		}
		function fmListPageSetClose(){
			parent.$('#fm_formdesignlist_listpageset').window('close');
		}
	/*
		日历控件--格式化
	*/
	$(function(){
			var dateFormat=[
							{id:'0',text:'请选择'},
							{id:'1',text:'yyyy'},
							{id:'2',text:'yyyy-MM'},
							{id:'3',text:'yyyy-MM-dd'}	,
							{id:'4',text:'yyyy-MM-dd HH:mm'},
							{id:'5',text:'yyyy-MM-dd HH:mm:ss'}
						];
			$('#fm_listpageset_dateformat').combobox({
				listWidth:148,
				data:dateFormat,
				valueField:'id',
				editable:false,
		  		textField:'text'
			});
			
	 });	
	 
    </script>
  </head>  
  <body>
  	<div id="fm_listpageset_tabs" class="easyui-tabs" fit="true" border="false">
		<!-- 页面配置 Tabs Begin -->
		<div id="fm_listpageset_pageset" title="页面配置" style="padding:4px;overflow-x:hidden;" href="pages/form/pageset/listpageset_basic.jsp">
			
		</div>
		<!-- 页面配置 Tabs End -->
		<!-- 数据列配置 Tabs Begin -->
		<div id="fm_listpageset_datacolumnset" title="数据列配置" style="padding:4px;" href="pages/form/pageset/listpageset_datacolumn.jsp"></div>
		<!-- 数据列配置 Tabs End -->
		
		<!-- 排序配置 Tabs Begin -->
		<div id="fm_listpageset_sortset" title="排序配置" style="padding:4px;" href="pages/form/pageset/listpageset_sortset.jsp"></div>
		<!-- 排序配置Tabs End -->
		
		<!-- 多标签配置 Tabs Begin -->
		<div id="fm_listpageset_tabsset" title="多标签配置" style="padding:4px;" href="pages/form/pageset/listpageset_tabs.jsp">
			
		</div>
		<!-- 多标签配置 Tabs End -->
		<!-- 组合查询配置 Tabs Begin -->
		<div id="fm_listpageset_queryset" title="组合查询配置" style="padding:4px;" href="pages/form/pageset/listpageset_query.jsp">
			
		</div>
		<!-- 组合查询配置 Tabs End -->
		<!-- 参数配置 Tabs Begin -->
		<div id="fm_listpageset_paramset" title="参数配置" style="padding:4px;" href="pages/form/pageset/listpageset_param.jsp">
			
		</div>
		<!-- 参数配置 Tabs End -->
		<!-- 操作列按钮配置配置 Tabs Begin -->
		<div id="fm_listpageset_operateButton" title="操作列配置" style="padding:4px;" href="pages/form/pageset/listpageset_operateButton.jsp">
			
		</div>
		<!-- 操作列按钮配置配置 Tabs End -->
		<!-- toolbar按钮配置 Tabs Begin -->
		<div id="fm_listpageset_toolbarButton" title="toolbar配置" style="padding:4px;" href="pages/form/pageset/listpageset_toolbarButton.jsp">
			
		</div>
		<!-- toolbar配置 Tabs End -->
	</div>
	<div id="fm_listpageset_queryset_config" class="easyui-window" style="padding:5px;">
		<table style="font-size:12px;" width="100%" cellpadding="5" cellspacing="0">			
			<tr>	
				<td align="right">字段：</td>
			  	<td><input id="fm_listpageset_qs_fieldindex" type="hidden"/><span id="fm_listpageset_qs_fieldname"></span></td>
			  	<td align="right">标题：</td>
				<td><input id="fm_listpageset_qs_fieldtitle" class="easyui-validatebox" style="width:145px;"/></td>
			</tr>
			<tr>
				<td align="right" >标签类型：</td>
				<td>
					<select id="fm_listpageset_qs_tagtype" class="easyui-combobox" listWidth="148" style="width:129px;">
					</select>
				</td>
				<td align="right">数据字典：</td>
				<td><!-- <select id="fm_listpageset_qs_dictionary" class="easyui-combotree" style="width:131px;"></select> -->
					<input id="fm_listpageset_qs_dictionary_id" type="hidden" />
					<input id="fm_listpageset_qs_dictionary_text" type="text" onfocus="fmListpagesetQsDictionaryWindow()" style="width:147px;cursor:pointer;" readOnly="true"/>
				</td>
			</tr>
			<div style="display: none" >
				<select id="fm_listpageset_qs_formula" class="easyui-combobox" listWidth="148" style="width:129px;"></select>
				<select id="fm_listpageset_qs_textalign" class="easyui-combobox" listWidth="148" style="width:129px;"></select>
				<select id="fm_listpageset_qs_validation" class="easyui-combobox" listWidth="148" style="width:129px;"></select>
			</div>
		 	<tr>
				<td align="right">条件操作符：</td>
			  	<td><select id="fm_listpageset_qs_operate" class="easyui-combobox" listWidth="148" style="width:129px;">
					</select></td>
			</tr> 	
			<tr>
				<td colspan="4"  >
					<div id="date_format_div" style="display:none;padding-left:25px;">	
							日期格式：&nbsp;&nbsp;
							<select id="fm_listpageset_dateformat" style="width:129px;">
							</select>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="4"  >
					<div class="panel-header panel-header-noborder header_cls" style="font-weight: normal;" >&nbsp;字段扩展属性</div>
				</td>
			</tr>
			
			<tr>
				<td colspan="4"  >
					<jsp:include page="extended-attributes-query.jsp"></jsp:include>		  
				</td>
			</tr>
    		<tr>
				<td align="center"></td>
				<td align="center"></td>
				<td align="center"></td>
			</tr>
			<tr>
				<td align="center" colspan="4">
					<a id="fm_listpageset_querysetconfig_save" class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="fmListpagesetQuerysetConfigSubmit()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a id="fm_listpageset_querysetconfig_cancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:;"  onclick="$('#fm_listpageset_queryset_config').window('close')" >关闭</a>				
				</td>
			</tr>
		</table>
	</div>
	
  </body>
</html>
