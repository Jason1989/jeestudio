<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
    <title> </title>    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript" type="text/javascript">
		$(function(){
			$('#fm_viewpageset_staticparam_delete_0').linkbutton();
			$('#fm_viewpageset_systemparam_delete_0').linkbutton();
			$("#fm_viewpageset_staticparam_type_0").combobox({
				valueField:'id',
   				textField:'text',
   				panelHeight:'auto',  
            	treeWidth:70,
			    data:fmPagesetStaticParamTypeData
			});
			var initXmlUtils = new XmlUtils({dataType:'json'}); 
			initXmlUtils.loadXmlString(viewFormSettings);
			var initRootNode = initXmlUtils.getRoot();	
			//参数设置
	        var viewParamsNode = initXmlUtils.getChildNodeByTagName(initRootNode,'Params');
			var viewParamNodes = initXmlUtils.getChildNodes(viewParamsNode);
	        if(viewParamNodes){
				var len = viewParamNodes.length;
				var systemPIndex = 0;
				var staticPIndex = 0;
				for (var j=0;j<len;j++){
					var viewParamNode = viewParamNodes[j];				
					if(viewParamNode.getAttribute('type') == '0'){
						if(systemPIndex != 0){
		        			fmPagesetSystemParamAddItem('fm_viewpageset_systemparamset_tbody');
		        		}
						$('#fm_viewpageset_systemparam_name_' + systemPIndex).val(viewParamNode.getAttribute('name'));
						systemPIndex++;
					}else{
						if(staticPIndex != 0){
	        				fmPagesetStaticParamAddItem('fm_viewpageset_staticparamset_tbody','fm_viewpageset_staticparam_type_');
	        			}
						$('#fm_viewpageset_staticparam_name_' + staticPIndex).val(viewParamNode.getAttribute('name'));
						$('#fm_viewpageset_staticparam_value_' + staticPIndex).val(viewParamNode.getAttribute('value'));					
						$('#fm_viewpageset_staticparam_type_' + staticPIndex).combobox("setValue",viewParamNode.getAttribute('type'));
						staticPIndex++;
					}
				}
			}		
		});		
		//Param Begin
		function fmViewPageSetParamsetSave(){
			var pageSetXmlUtils = new XmlUtils({dataType:'json'}); 
			pageSetXmlUtils.loadXmlString(viewFormSettings);
			var rootNode = pageSetXmlUtils.getRoot();
			var viewParamsNode = pageSetXmlUtils.getChildNodeByTagName(rootNode,'Params');
			pageSetXmlUtils.removeChildNodes(viewParamsNode);
			var staflag = false;
			var sysflag = false;
			$("#fm_viewpageset_staticparamset_tbody tr").each(function(i,node){ 
				if(node.id != 'fm_viewpageset_staticparam_default_tr'){
					var staticParamNameEle = $(node).find("td input[id^='fm_viewpageset_staticparam_name_']");
					var staticParamValueEle = $(node).find("td input[id^='fm_viewpageset_staticparam_value_']");
					var staticParamTypeEle = $(node).find("td select[id^='fm_viewpageset_staticparam_type_']");
					
					var staticParamName = staticParamNameEle.val();
					var staticParamType = staticParamTypeEle.combobox("getValue");
					var staticParamValue = staticParamValueEle.val();
					if(staticParamName != ""){
						pageSetXmlUtils.createNode('Param','',{name:staticParamName,type:staticParamType,value:staticParamValue},viewParamsNode);
					}else{
						staflag = true;
					}
				}
			});
			$("#fm_viewpageset_systemparamset_tbody tr").each(function(i,node){ 
				if(node.id != 'fm_viewpageset_systemparam_default_tr'){
					var systemParamNameEle = $(node).find("td input[id^='fm_viewpageset_systemparam_name_']");
					var systemParamName = systemParamNameEle.val();
					if(systemParamName != ""){
						pageSetXmlUtils.createNode('Param','',{name:systemParamName,type:"0",value:""},viewParamsNode);
					}else{
						sysflag = true;
					}
				}
			});
			if(staflag && sysflag){
				$.messager.alert("提示","请输入参数名称！", 'warning');	
				return false;
			}			
			pageSetXmlUtils.setAttribute(viewParamsNode,'isConfig','1');
			$("#fm_formdesignview_param").html(CONFIGYES);
			viewPageSetSubmit(pageSetXmlUtils.toString());
		}
		//Param End
    </script>
  </head>  
  <body>
	<div class="easyui-panel" title="静态参数设置" collapsible="false"  headerCls="header_cls">
		<table border="0" cellpadding="1" cellspacing="1" width="100%" style="background:#dedede;">
			<tr style="background:#ffffff;">					  
			  <td style="background:#eeeeee;" colspan="4">
			  	<a href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="fmPagesetStaticParamAddItem('fm_viewpageset_staticparamset_tbody','fm_viewpageset_staticparam_type_')">增加</a>
			  </td>
			</tr>
			<tbody id="fm_viewpageset_staticparamset_tbody">
				<tr style="background:#ffffff;display:none;" id="fm_viewpageset_staticparam_default_tr">
				  	<td width="30%">参数名称：<input type="text" id="fm_viewpageset_staticparam_name_-1"/></td>
					<td width="25%">来源：<select id="fm_viewpageset_staticparam_type_-1"></select></td>
					<td width="30%">参数值：<input type="text" id="fm_viewpageset_staticparam_value_-1"/></td>	
					<td><a id="fm_viewpageset_staticparam_delete_-1" href="javascript:;" plain="true" icon="icon-remove" onclick="fmPagesetDynamicDeleteItem(this)">删除</a></td>							
				</tr>	
				<tr style="background:#ffffff;">
				  	<td width="30%">参数名称：<input type="text" id="fm_viewpageset_staticparam_name_0"/></td>
					<td width="25%">来源：<select id="fm_viewpageset_staticparam_type_0"></select></td>
					<td width="30%">参数值：<input type="text" id="fm_viewpageset_staticparam_value_0"/></td>	
					<td><a id="fm_viewpageset_staticparam_delete_0" href="javascript:;" plain="true" icon="icon-remove" onclick="fmPagesetDynamicDeleteItem(this)">删除</a></td>							
				</tr>	
			</tbody>				
		</table>
	</div>
	<div style="height:4px;"></div>
	<div class="easyui-panel" title="系统获取参数设置" collapsible="false"  headerCls="header_cls">
		<table border="0" cellpadding="1" cellspacing="1" width="100%" style="background:#dedede;">
			<tr style="background:#ffffff;">					  
			  <td style="background:#eeeeee;" colspan="3">
			  	<a href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="fmPagesetSystemParamAddItem('fm_viewpageset_systemparamset_tbody')">增加</a>
			  </td>
			</tr>
			<tr style="background:#ffffff;height:24px;">
				<td width="33%" >参数名称</td>
			  	<td width="33%" >参数来源</td>
				<td>操作</td>						
			</tr>	
			<tbody id="fm_viewpageset_systemparamset_tbody">
				<tr style="background:#ffffff;display:none;" id="fm_viewpageset_systemparam_default_tr">
					<td width="33%" ><input type="text" id="fm_viewpageset_systemparam_name_-1"/></td>
				  	<td width="33%" >系统获取</td>
					<td><a id="fm_viewpageset_systemparam_delete_-1" href="javascript:;" plain="true" icon="icon-remove" onclick="fmPagesetDynamicDeleteItem(this)">删除</a></td>						
				</tr>
				<tr style="background:#ffffff;">
					<td width="33%" ><input type="text" id="fm_viewpageset_systemparam_name_0"/></td>
				  	<td width="33%" >系统获取</td>
					<td><a id="fm_viewpageset_systemparam_delete_0" href="javascript:;" plain="true" icon="icon-remove" onclick="fmPagesetDynamicDeleteItem(this)">删除</a></td>						
				</tr>	
			</tbody>				
		</table>
	</div>
	<div style="height:4px;"></div>
	<table border="0" cellpadding="5" cellspacing="0" width="100%">
		<tr>
			<td align="center"><a class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="fmViewPageSetParamsetSave()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:;"  onclick="fmViewPageSetClose()" >关闭</a></td>										
		</tr>					
	</table>
  </body>
</html>
