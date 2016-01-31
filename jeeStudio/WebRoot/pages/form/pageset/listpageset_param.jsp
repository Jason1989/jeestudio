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
			$("#fm_listpageset_staticparam_type_0").combobox({
				valueField:'id',
   				textField:'text', 
   				panelHeight:'auto', 
            	treeWidth:70,
			    data:fmPagesetStaticParamTypeData
			});
			$('#fm_listpageset_staticparam_delete_0').linkbutton();
			$('#fm_listpageset_systemparam_delete_0').linkbutton();
			var initXmlUtils = new XmlUtils({dataType:'json'}); 
			initXmlUtils.loadXmlString(formSettings);
			var rootNode = initXmlUtils.getRoot();
			//参数设置
	        var listParamsNode = initXmlUtils.getChildNodeByTagName(rootNode,'Params');
			var listParamNodes = initXmlUtils.getChildNodes(listParamsNode);
	        if(listParamNodes){
				var len = listParamNodes.length;
				var systemPIndex = 0;
				var staticPIndex = 0;
				for (var j=0;j<len;j++){
					var listParamNode = listParamNodes[j];				
					if(listParamNode.getAttribute('type') == '0'){
						if(systemPIndex != 0){
		        			fmPagesetSystemParamAddItem('fm_listpageset_systemparamset_tbody');
		        		}
						$('#fm_listpageset_systemparam_name_' + systemPIndex).val(listParamNode.getAttribute('name'));
						systemPIndex++;
					}else{
						if(staticPIndex != 0){
	        				fmPagesetStaticParamAddItem('fm_listpageset_staticparamset_tbody','fm_listpageset_staticparam_type_');
	        			}
						$('#fm_listpageset_staticparam_name_' + staticPIndex).val(listParamNode.getAttribute('name'));
						$('#fm_listpageset_staticparam_value_' + staticPIndex).val(listParamNode.getAttribute('value'));					
						$('#fm_listpageset_staticparam_type_' + staticPIndex).combobox("setValue",listParamNode.getAttribute('type'));
						staticPIndex++;
					}
				}
			}
		});		
		//Param Begin
		function fmListPageSetParamsetSave(){
			var pageSetXmlUtils = new XmlUtils({dataType:'json'}); 
			pageSetXmlUtils.loadXmlString(formSettings);
			var rootNode = pageSetXmlUtils.getRoot();
			var listParamsNode = pageSetXmlUtils.getChildNodeByTagName(rootNode,'Params');
			pageSetXmlUtils.removeChildNodes(listParamsNode);
			var staflag = false;
			var sysflag = false;
			$("#fm_listpageset_staticparamset_tbody tr").each(function(i,node){ 
				if(node.id != 'fm_listpageset_staticparam_default_tr'){
					var staticParamNameEle = $(node).find("td input[id^='fm_listpageset_staticparam_name_']");
					var staticParamValueEle = $(node).find("td input[id^='fm_listpageset_staticparam_value_']");
					var staticParamTypeEle = $(node).find("td select[id^='fm_listpageset_staticparam_type_']");
					
					var staticParamName = staticParamNameEle.val();
					var staticParamType = staticParamTypeEle.combobox("getValue");
					var staticParamValue = staticParamValueEle.val();
					if(staticParamName != ""){
						pageSetXmlUtils.createNode('Param','',{name:staticParamName,type:staticParamType,value:staticParamValue},listParamsNode);
					}else{
						staflag = true;
					}
				}	
			});
			$("#fm_listpageset_systemparamset_tbody tr").each(function(i,node){ 
				if(node.id != 'fm_listpageset_systemparam_default_tr'){
					var systemParamNameEle = $(node).find("td input[id^='fm_listpageset_systemparam_name_']");
					var systemParamName = systemParamNameEle.val();
					if(systemParamName != ""){
						pageSetXmlUtils.createNode('Param','',{name:systemParamName,type:"0",value:""},listParamsNode);
					}else{
						sysflag = true;
					}
				}
			});
			if(staflag && sysflag){
				$.messager.alert("提示","请输入参数名称！", 'warning');	
				return false;
			}
			pageSetXmlUtils.setAttribute(listParamsNode,'isConfig','1');
			$("#fm_formdesignlist_param").html(CONFIGYES);
			listPageSetSubmit(pageSetXmlUtils.toString());
		}
		//Param End
    </script>
  </head>  
  <body>
	<div class="easyui-panel" title="静态参数设置" collapsible="true" headerCls="header_cls">
				<table border="0" cellpadding="1" cellspacing="1" width="100%" style="background:#dedede;">
					<tr style="background:#ffffff;">					  
					  <td style="background:#eeeeee;" colspan="4">
					  	<a href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="fmPagesetStaticParamAddItem('fm_listpageset_staticparamset_tbody','fm_listpageset_staticparam_type_')">增加</a>
					  </td>
					</tr>
					<tbody id="fm_listpageset_staticparamset_tbody">
						<tr style="background:#ffffff;display:none;" id="fm_listpageset_staticparam_default_tr">
						  	<td width="30%">参数名称：<input type="text" id="fm_listpageset_staticparam_name_-1"/></td>
							<td width="25%">来源：<select id="fm_listpageset_staticparam_type_-1"></select></td>
							<td width="30%">参数值：<input type="text" id="fm_listpageset_staticparam_value_-1"/></td>	
							<td><a id="fm_listpageset_staticparam_delete_-1" href="javascript:;" plain="true" icon="icon-remove" onclick="fmPagesetDynamicDeleteItem(this)">删除</a></td>						
						</tr>		
						<tr style="background:#ffffff;">
						  	<td width="30%">参数名称：<input type="text" id="fm_listpageset_staticparam_name_0"/></td>
							<td width="25%">来源：<select id="fm_listpageset_staticparam_type_0"></select></td>
							<td width="30%">参数值：<input type="text" id="fm_listpageset_staticparam_value_0"/></td>	
							<td><a id="fm_listpageset_staticparam_delete_0" href="javascript:;" plain="true" icon="icon-remove" onclick="fmPagesetDynamicDeleteItem(this)">删除</a></td>						
						</tr>		
					</tbody>			
				</table>
			</div>
			<div style="height:4px;"></div>
			<div class="easyui-panel" title="系统获取参数设置" collapsible="true" headerCls="header_cls">
				<table border="0" cellpadding="1" cellspacing="1" width="100%" style="background:#dedede;">
					<tr style="background:#ffffff;">					  
					  <td style="background:#eeeeee;" colspan="3">
					  	<a href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="fmPagesetSystemParamAddItem('fm_listpageset_systemparamset_tbody')">增加</a>
					  </td>
					</tr>
					<tr style="background:#ffffff;height:24px;">
						<td width="33%" >参数名称</td>
					  	<td width="33%" >参数来源</td>
						<td>操作</td>						
					</tr>
					<tbody id="fm_listpageset_systemparamset_tbody">					
						<tr style="background:#ffffff;display:none;" id="fm_listpageset_systemparam_default_tr">
							<td width="33%" ><input type="text" id="fm_listpageset_systemparam_name_-1"/></td>
						  	<td width="33%" >系统获取</td>
							<td><a id="fm_listpageset_systemparam_delete_-1" href="javascript:;" plain="true" icon="icon-remove" onclick="fmPagesetDynamicDeleteItem(this)">删除</a></td>						
						</tr>		
						<tr style="background:#ffffff;">
							<td width="33%" ><input type="text" id="fm_listpageset_systemparam_name_0"/></td>
						  	<td width="33%" >系统获取</td>
							<td><a id="fm_listpageset_systemparam_delete_0" href="javascript:;" plain="true" icon="icon-remove" onclick="fmPagesetDynamicDeleteItem(this)">删除</a></td>						
						</tr>		
					</tbody>			
				</table>
			</div>
			<div style="height:4px;"></div>
			<table border="0" cellpadding="5" cellspacing="0" width="100%">
				<tr>
					<td align="center"><a class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="fmListPageSetParamsetSave()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:;"  onclick="fmListPageSetClose()" >关闭</a></td>										
				</tr>					
			</table>
  </body>
</html>
