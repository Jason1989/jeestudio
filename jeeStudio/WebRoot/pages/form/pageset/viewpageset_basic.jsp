<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String uuid = UUID.randomUUID().toString();
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
			//$('#fm_viewpageset_groupdelete_0').linkbutton();
			$("#fm_viewpageset_grouptype_0").combobox({
				valueField:'id',
   				textField:'text',  
            	treeWidth:70,
            	editable:false,
			    data:fmPagesetGroupTypeData
			});
			$("#fm_viewpageset_grouptype_0").combobox('setValue','-1');
			var initXmlUtils = new XmlUtils({dataType:'json'}); 
			initXmlUtils.loadXmlString(viewFormSettings);
			var initRootNode = initXmlUtils.getRoot();		
			$("#fm_viewpageset_pageset_title").val(initRootNode.getAttribute('title'));
			var initButtonsNode = initXmlUtils.getChildNodeByTagName(initRootNode,'Buttons');
			//取消按钮			
			var initCancelButtonNode = initXmlUtils.hasNodeByAttribute(initButtonsNode,'type',VIEW_BUTTON_CANCEL);
			if(initCancelButtonNode && (initCancelButtonNode.getAttribute('visible')==true || initCancelButtonNode.getAttribute('visible') == 'true')){
				$(":checkbox[id='fm_viewpageset_isshowcancel']").attr("checked",true);
			}else{
				$(":checkbox[id='fm_viewpageset_isshowcancel']").attr("checked",false);
			}
			
			//导出按钮
			$('#per<%=uuid%>').appendTo('body').window({
				title:"选择导出模板",
				width: 500,
				height: 450,
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                collapsible:false,
                shadow: false,
                closed: true
            });
			$(":checkbox[id='fm_viewpageset_isshowexport']").change(function(){
				var viewpageset_isshowexport_check = $(":checkbox[id='fm_viewpageset_isshowexport']").attr('checked');
				if( viewpageset_isshowexport_check ){
					$('#fm_viewpageset_export_templatefile_show_div').show();
				}else{
					$('#fm_viewpageset_export_templatefile_show_div').hide();
				}
			});
			
			var initExportButtonNode = initXmlUtils.hasNodeByAttribute(initButtonsNode,'type',VIEW_BUTTON_EXPORT);
			if(initExportButtonNode && (initExportButtonNode.getAttribute('visible')==true || initExportButtonNode.getAttribute('visible') == 'true')){
				$(":checkbox[id='fm_viewpageset_isshowexport']").attr("checked",true);
				$('#fm_viewpageset_export_templatefile_show_div').show();
				//导出模板
				var expertBut_param = initExportButtonNode.getAttribute('buttonParam');
				var templateFileId = '';
				var templateFileName = '';
				if(expertBut_param){
					templateFileId = expertBut_param.substring(0,expertBut_param.indexOf(','));
					templateFileName = expertBut_param.substring(expertBut_param.indexOf(',')+1,expertBut_param.length);
				}
				
				if( templateFileId && 
						templateFileName && 
						templateFileId!=null && 
						templateFileName!=null &&
						templateFileId!='' && 
						templateFileName!='' ){
					$('#fm_viewpageset_export_templatefile_id').val(templateFileId);
					$('#fm_viewpageset_export_templatefile_name').val(templateFileName);
				}
			}else{
				$(":checkbox[id='fm_viewpageset_isshowexport']").attr("checked",false);
			}
			
			//分组设置
			var initGroupsNode = initXmlUtils.getChildNodeByTagName(initRootNode,'Groups');
			if(initGroupsNode && (initGroupsNode.getAttribute('visible')==true || initGroupsNode.getAttribute('visible') == 'true')){
				$(":checkbox[id='fm_viewpageset_isshowgroup']").attr("checked",true);
			}else{
				$(":checkbox[id='fm_viewpageset_isshowgroup']").attr("checked",false);
			}
			var initGroupNodes = initXmlUtils.getChildNodes(initGroupsNode);
			if(initGroupNodes){
				//$(":checkbox[id='fm_viewpageset_isshowgroup']").attr("checked",true);
				var len = initGroupNodes.length;
				for (var i=0;i<len;i++){
					if(len > 1 && i != 0){
	        			fmViewpagesetGroupsetAdditem();
	        		}
					var initGroupNode = initGroupNodes[i];					
					$('#fm_viewpageset_groupid_' + i).val(initGroupNode.getAttribute('id'));
					var sortIndex = 0;
					if(initGroupNode.getAttribute('sortIndex') != null && initGroupNode.getAttribute('sortIndex') != 'null'){
						sortIndex = initGroupNode.getAttribute('sortIndex');
					}
					$('#fm_viewpageset_groupsort_' + i).val(sortIndex);
					$('#fm_viewpageset_grouptitle_' + i).val(initGroupNode.getAttribute('title'));
					if(initGroupNode.getAttribute('type') != ""){
						$('#fm_viewpageset_grouptype_' + i).combobox("setValue",initGroupNode.getAttribute('type'));
					}else{
						$("#fm_viewpageset_grouptype_" + i).combobox('setValue','-1');
					}
				}
			}	
			//js规则引擎
			var jsrules = '';
			if(initGroupsNode.getAttribute('jsRules') != null && initGroupsNode.getAttribute('jsRules') != 'null' && initGroupsNode.getAttribute('jsRules') != ''){
				jsrules = initGroupsNode.getAttribute('jsRules');
			}
			$('#jsrules').val(jsrules);
			
			/*选中显示分组时，增加按钮有效*/
			$('#fm_viewpageset_isshowgroup').click(function(){
			    if($(this).attr('checked')){
			       $("#fm_viewpageset_isshowgroupButton").linkbutton("enable");
			    }else{
			       $("#fm_viewpageset_isshowgroupButton").linkbutton("disable");
			    }
			});
			
		});

		//选择模板
		function choose_templatefile(){
			var url = 'pages/formtemplatefile/templatefile_choose.jsp?wid=per<%=uuid%>&fid=fm_viewpageset_export_templatefile_id&fname=fm_viewpageset_export_templatefile_name';
			$("#per<%=uuid%>").window({href:''}).window('refresh').window({href:url}).window('open');
		}
		
		//PageSet Begin
		function fmViewpagesetPagesetSave(){
			var value=document.getElementById('fm_viewpageset_export_templatefile_name').value;
			if($(":checkbox[id='fm_viewpageset_isshowexport']").attr("checked")){
				if(value == ''){
					 $.messager.alert("提示","请选择模板！", 'info');	
					 return ;
				}
			}
			var viewpagesetXmlUtils = new XmlUtils({dataType:'json'}); 
			viewpagesetXmlUtils.loadXmlString(viewFormSettings);
			var viewpagesetRootNode = viewpagesetXmlUtils.getRoot();		
			viewpagesetXmlUtils.setAttribute(viewpagesetRootNode,'title',$("#fm_viewpageset_pageset_title").val());
			
			var viewButtonsNode = viewpagesetXmlUtils.getChildNodeByTagName(viewpagesetRootNode,'Buttons');
			
			//是否显示关闭按钮	
			var viewCancelButtonNode = viewpagesetXmlUtils.hasNodeByAttribute(viewButtonsNode,'type',VIEW_BUTTON_CANCEL);
			if($(":checkbox[id='fm_viewpageset_isshowcancel']").attr("checked")){
				if(viewCancelButtonNode){
					viewpagesetXmlUtils.setAttribute(viewCancelButtonNode,'visible','true');
				}else{
					viewpagesetXmlUtils.createNode('Button','',{id:'',type:VIEW_BUTTON_CANCEL,name:'关闭',visible:'true'},viewButtonsNode);
				}
			}else{
				if(viewCancelButtonNode){
					viewpagesetXmlUtils.setAttribute(viewCancelButtonNode,'visible','false');
				}
			}
			

			//导出按钮
			var viewExportButtonNode = viewpagesetXmlUtils.hasNodeByAttribute(viewButtonsNode,'type',VIEW_BUTTON_EXPORT);
			if($(":checkbox[id='fm_viewpageset_isshowexport']").attr("checked")){
				var templateFileIdStr = $('#fm_viewpageset_export_templatefile_id').val();
				var templateFileNameStr = $('#fm_viewpageset_export_templatefile_name').val();
				var sav_param = new Array(templateFileIdStr,templateFileNameStr);
				if(viewExportButtonNode){
					viewpagesetXmlUtils.setAttribute(viewExportButtonNode,'visible','true');
					viewpagesetXmlUtils.setAttribute(viewExportButtonNode,'buttonParam',sav_param);
				}else{
					viewpagesetXmlUtils.createNode('Button','',{
							id:'',
							type:VIEW_BUTTON_EXPORT,
							name:'导出',
							visible:'true',
							buttonParam: sav_param
						},viewButtonsNode);
				}
			}else{
				if(viewExportButtonNode){
					viewpagesetXmlUtils.setAttribute(viewExportButtonNode,'visible','false');
				}
			}
			
			//分组设置*
			var viewGroupsNode = viewpagesetXmlUtils.getChildNodeByTagName(viewpagesetRootNode,'Groups');
			if($('#fm_viewpageset_isshowgroup').attr('checked')){
				viewpagesetXmlUtils.removeChildNodes(viewGroupsNode);
				var flag = false;
				viewpagesetXmlUtils.setAttribute(viewGroupsNode,'visible','true');
				$("#fm_viewpageset_groupset_tbody tr").each(function(i,node){ 
					if(node.id != 'fm_viewpageset_default_tr'){
						var groupIdEle = $(node).find("td input[id^='fm_viewpageset_groupid_']");
						var groupSortEle = $(node).find("td input[id^='fm_viewpageset_groupsort_']");
						var groupTitleEle = $(node).find("td input[id^='fm_viewpageset_grouptitle_']");
						var groupTypeEle = $(node).find("td select[id^='fm_viewpageset_grouptype_']");
						var groupTitle = groupTitleEle.val();
						if(groupTitle != ""){
							var groupId;
							if(groupIdEle.val() == ""){
								groupId = "view" + parseInt(1000*Math.random());
							}else{
								groupId = groupIdEle.val();
							}
							var groupSort = groupSortEle.val();
							var groupType = groupTypeEle.combobox("getValue")=="-1"?"":groupTypeEle.combobox("getValue");
							var viewGroupNode = viewpagesetXmlUtils.hasNodeByAttribute(viewGroupsNode,'id',groupId);
							if(viewGroupNode){
								viewpagesetXmlUtils.setAttribute(viewGroupNode,'id',groupId);
								viewpagesetXmlUtils.setAttribute(viewGroupNode,'title',groupTitle);
								viewpagesetXmlUtils.setAttribute(viewGroupNode,'type',groupType);
								viewpagesetXmlUtils.setAttribute(viewGroupNode,'sortIndex',groupSort);
							}else{
								viewpagesetXmlUtils.createNode('Group','',{id:groupId,title:groupTitle,visible:'true',type:groupType,sortIndex:groupSort},viewGroupsNode);
							}
						}else{
							flag = true;
						}
					}
				});	
			}else{
				viewpagesetXmlUtils.setAttribute(viewGroupsNode,'visible','false');
			}
			//js规则引擎
			var jsrules = $('#jsrules').val();
			if(jsrules!=null&&jsrules!=''){
				viewpagesetXmlUtils.setAttribute(viewGroupsNode,'jsRules',jsrules);
			}else{
				viewpagesetXmlUtils.setAttribute(viewGroupsNode,'jsRules','');
			}		
			if(flag){
				$.messager.alert("提示","请输入分组名称！", 'warning');	
				return false;
			}
			//var viewJsRulesNode = viewpagesetXmlUtils.getChildNodeByTagName(viewpagesetRootNode,'Groups');
			//viewpagesetXmlUtils.setAttribute();
			viewpagesetXmlUtils.setAttribute(viewButtonsNode,'isConfig','1');
			$("#fm_formdesignview_basic").html(CONFIGYES);
			viewPageSetSubmit(viewpagesetXmlUtils.toString());
		}
		function fmViewpagesetGroupsetAdditem(){
			var tbody = $("#fm_viewpageset_groupset_tbody");	
			var cloneObject = $(tbody.find("tr").last()).clone();		
			fmPagesetGroupsetAdditem(cloneObject,"fm_viewpageset_groupup_","fm_viewpageset_groupdown_");
			cloneObject.appendTo(tbody);
			fmTableRowsSortImgHidden("fm_viewpageset_groupset_tbody","fm_viewpageset_groupup_","fm_viewpageset_groupdown_");
			fmViewpagesetGroupsetInitData(cloneObject);
		}
		function fmViewpagesetGroupsetInitData(nodeObj)
		{
			var grouptypeSelect = $(nodeObj).find("td select[id^='fm_viewpageset_grouptype_']");
			grouptypeSelect.combobox({
				valueField:'id',
   				textField:'text',  
            	treeWidth:70,
            	editable:false,
			    data:fmPagesetGroupTypeData
			});		
			grouptypeSelect.combobox('setValue','-1');
		}
		//PageSet End
		
    </script>
  </head>  
  <body>
  	<div style="width:777px;height:99px;">
		<div class="easyui-panel" title="基本信息配置" fit="true" headerCls="header_cls">
			<table border="0" cellpadding="5" cellspacing="0" width="100%">
			  	<tr>
				  <td width="40%">&nbsp;&nbsp;&nbsp;&nbsp;标题：<input type="text" id="fm_viewpageset_pageset_title"/></td>
				  <td style="display:none">&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="fm_viewpageset_isshowcancel"/> 显示取消按钮</td>	
				</tr>
			  	<tr>					  				  
				  <td colspan="2">
				    &nbsp;&nbsp;&nbsp;&nbsp;
				    <input type="checkbox" id="fm_viewpageset_isshowexport"/> 显示导出按钮
				    <span id="fm_viewpageset_export_templatefile_show_div" style="display: none;padding-left:50px;">
				      &nbsp;导出模板
				      <input id="fm_viewpageset_export_templatefile_name" type="text" value="" readOnly/>
				      <input id="fm_viewpageset_export_templatefile_id" type="hidden" value="" onclick="choose_templatefile()"/>
				      <a id="fm_viewpageset_export_templatefile_choose_but" class="easyui-linkbutton" onclick="choose_templatefile()">选择模板</a>
				    </span>
				    <div id="per<%=uuid%>"/></div>
				  </td>	
				</tr>	
			</table>
		</div>
	</div>
	<div style="height:4px;"></div>
	<div style="width:777px;height:260px;">
		<div class="easyui-panel" title="分组设置" headerCls="header_cls">
			<table border="0" cellpadding="1" cellspacing="1" width="100%" style="background:#dedede;">
				<tr style="background:#ffffff;">					  
				  <td style="background:#eeeeee;" colspan="8"><input type="checkbox" id="fm_viewpageset_isshowgroup"/> 显示分组&nbsp;&nbsp;&nbsp;&nbsp;
				  	<a href="javascript:void(0);" id="fm_viewpageset_isshowgroupButton" disabled="true" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="fmViewpagesetGroupsetAdditem()">增加</a></td>
				</tr>
				<!-- 
				<tr style="background:#ffffff;">
				  <td width="10%" align="right">ID：</td>
				  <td width="18%"><input type="text" id="fm_viewpageset_groupid"/></td>
				  <td width="10%" align="right">标题：</td>
				  <td width="18%"><input type="text" id="fm_viewpageset_grouptitle"/></td>
				  <td width="10%" align="right">布局：</td>
				  <td width="10%"><select id="fm_viewpageset_grouptype"><option value="0">上下</option><option value="1">左右</option></select></td>
				  <td><a href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-remove">删除</a></td>
				</tr>	
				-->	
				<tbody id="fm_viewpageset_groupset_tbody">		
					<tr style="background:#ffffff;display:none;" id="fm_viewpageset_default_tr">
					  <td width="8%" style="display:none;">ID：</td>
					  <td width="12%" style="display:none;"><input type="text" id="fm_viewpageset_groupid_-1" size="10"/></td>
					  <td width="10%" align="right">分组名称：</td>
					  <td width="20%"><input type="text" id="fm_viewpageset_grouptitle_-1"/></td>
					  <td width="8%" align="right">布局：</td>
					  <td width="10%"><select id="fm_viewpageset_grouptype_-1"></select></td>
					  <td width="8%" style="display:none;">序号：</td>
					  <td width="8%" style="display:none;"><input type="text" id="fm_viewpageset_groupsort_-1" size="5" value="0"/></td>
					  <td>
					  	<!-- <a id="fm_viewpageset_groupdelete_-1" href="javascript:;" plain="true" icon="icon-remove" onclick="fmPagesetDynamicDeleteItem(this)">删除</a> -->
					  	<img src="images/ioc-delete.gif" style="cursor:hand;margin:2px 2px 0px 4px;" title="删除" onclick="fmPagesetSortDynamicDeleteItem(this,'fm_viewpageset_groupset_tbody','fm_viewpageset_groupup_','fm_viewpageset_groupdown_')"/>
					  	<img id="fm_viewpageset_groupup_-1" src="images/up.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="上移" onclick="fmTableRowsSort(this,1,7,8)"/>
					  	<img id="fm_viewpageset_groupdown_-1" src="images/down.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="下移" onclick="fmTableRowsSort(this,0,7,8)"/>
					  </td>
					</tr>	
					<tr style="background:#ffffff;">
					  <td width="8%" style="display:none;">ID：</td>
					  <td width="12%" style="display:none;"><input type="text" id="fm_viewpageset_groupid_0" size="10"/></td>
					  <td width="10%" align="right">分组名称：</td>
					  <td width="20%"><input type="text" id="fm_viewpageset_grouptitle_0"/></td>
					  <td width="8%" align="right">布局：</td>
					  <td width="10%"><select id="fm_viewpageset_grouptype_0"></select></td>
					  <td width="8%" style="display:none;">序号：</td>
					  <td width="8%" style="display:none;"><input type="text" id="fm_viewpageset_groupsort_0" size="5" value="1"/></td>
					  <td>
					  	<img src="images/ioc-delete.gif" style="cursor:hand;margin:2px 2px 0px 4px;" title="删除" onclick="fmPagesetSortDynamicDeleteItem(this,'fm_viewpageset_groupset_tbody','fm_viewpageset_groupup_','fm_viewpageset_groupdown_')"/>
					  	<img id="fm_viewpageset_groupup_0" src="images/up.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="上移" onclick="fmTableRowsSort(this,1,7,8)"/>
					  	<img id="fm_viewpageset_groupdown_0" src="images/down.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="下移" onclick="fmTableRowsSort(this,0,7,8)"/>
					  </td>
					</tr>	
				</tbody>			
			</table>
		</div>
		<div class="easyui-panel" title='规则引擎(<font color="red">注意：字符串变量不要用单引号，要用双引号。$("#分组名_view_id").css("display","none");$("#字段名_view_id").val();<br/>隐藏某个字段：$("#标题名_label_id").css("display","none");$("#标题名_text_id").css("display","none");</font>)' headerCls="header_cls">
			<table border="0" cellpadding="1" cellspacing="1" width="100%" style="background:#dedede;">
				<tr>
					<td><textarea id="jsrules" rows="10" cols="93"></textarea> </td>
				</tr>
			</table>
		</div>
		<div style="height:4px;"></div>
		<div style="width: 100%;padding-top:13px;padding-bottom:13px;" align="center">
				<a class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="fmViewpagesetPagesetSave()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:;"  onclick="fmViewPageSetClose()" >关闭</a>									
		</div>
	</div>
  </body>
</html>
