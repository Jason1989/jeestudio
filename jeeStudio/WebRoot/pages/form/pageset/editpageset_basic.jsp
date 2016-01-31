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
		var fmPagesetGroupShowData=[{"showkey":-1,"showtext":"请选择"},{"showkey":"true","showtext":"显示"},{"showkey":"false","showtext":"不显示"}];
		$(function(){
		    /*是否执行sql，选中时，是否显示*/
  			$('#isWorkFlow_comPar_checkbox').click(function(){
  				if($('#isWorkFlow_comPar_checkbox').is(':checked')){
  					$("#workflow_comPar_td").css('display','block');
  					$('#isWorkFlow_comPar_checkbox').attr('checked',true);
  					$('#isWorkFlow_comPar_hidden').val('on');
  					$("#editPageSetBasicPanelBasiceInfo").height(250);
  					$("#editPageSetBasicPanelBasiceInfoPanel").panel("resize");
  				}else{
  					$("#workflow_comPar_td").css('display','none');
  					$('#isWorkFlow_comPar_checkbox').removeAttr('checked');
  					$('#isWorkFlow_comPar_hidden').val('off');
  					$("#editPageSetBasicPanelBasiceInfo").height(160);
  					$("#editPageSetBasicPanelBasiceInfoPanel").panel("resize");
  				};
  			});
  			$('#selfDefine').click(function(){
  				if($('#selfDefine').is(':checked')){
	  				$("#selfDefineDiv").css('display','inline');
  				}else{
	  				$("#selfDefineDiv").css('display','none');
  				}
  			});
			$("#fm_editpageset_grouptype_0").combobox({
				valueField:'id',
   				textField:'text',  
            	treeWidth:70,
            	editable:false,
			    data:fmPagesetGroupTypeData
			});
			$("#fm_editpageset_grccoupshow_0").combobox({
				valueField:'showkey',
   				textField:'showtext',  
            	treeWidth:30,
            	editable:false,
			    data:fmPagesetGroupShowData
			});
			$("#fm_editpageset_grouptype_0").combobox('setValue','-1');
			$("#fm_editpageset_grccoupshow_0").combobox('setValue','-1');
			//$("#fm_editpageset_groupset_delete_0").linkbutton();
			var initXmlUtils = new XmlUtils({dataType:'json'});
			initXmlUtils.loadXmlString(editFormSettings);
			var initRootNode = initXmlUtils.getRoot();		
			$("#fm_editpageset_pageset_title").val(initRootNode.getAttribute('title'));
			//保存前，后SQL 加载数据
			$("editPage_before_sql").combobox({
				url:'dictionary/dataDictionary!sqlEditPageAfter.action',
				valueField:'id',   
    			textField:'name'  
			});
			//$("#editPage_before_sql").combobox("setValue",initRootNode.getAttribute('editPage_before_sql'));			
			$("#editPage_after_sql").combobox({
				url:'dictionary/dataDictionary!sqlEditPageAfter.action',
				valueField:'id',   
    			textField:'name'  
				
			});
			$("#editPage_after_sql").combobox("setValue",initRootNode.getAttribute('editPage_after_sql'));

			var workflowParCom = initRootNode.getAttribute('workflowParCom');
			$("#isWorkFlow_comPar_hidden").val(workflowParCom);
			if( 'on'==workflowParCom ){
				$('#isWorkFlow_comPar_checkbox').attr('checked',true);
				$("#workflow_comPar_td").css('display','block');
			}else{
				$('#isWorkFlow_comPar_checkbox').removeAttr('checked');
				$("#workflow_comPar_td").css('display','none');
			}
			if(initRootNode.getAttribute('workflowParComId')){
				$("#isWorkFlow_comPar_id").val(initRootNode.getAttribute('workflowParComId'));
			}
			if(initRootNode.getAttribute('workflowParComArray')){
				$("#isWorkFlow_comPar_array").val(initRootNode.getAttribute('workflowParComArray'));
			}
			
			
			//  "是否对当前编辑表单 添加、修改 操作加入记录 isSystemActionlog"
			var isSystemActionlog=initRootNode.getAttribute("isSystemActionlog");
			if("true"==isSystemActionlog){
				$("#isSystemActionlog").attr("checked",true);
			}else{
				$("#isSystemActionlog").removeAttr("checked");
			}
			
			//自定义宽高配置
			var selfDefine = initRootNode.getAttribute('selfDefine');
			var selfDefineWidth = initRootNode.getAttribute('selfDefineWidth');
			var selfDefineHeight = initRootNode.getAttribute('selfDefineHeight');
			if( 'on'==selfDefine ){
				$('#selfDefine').attr('checked',true);
				$("#selfDefineDiv").css('display','inline');
			}else{
				$('#selfDefine').removeAttr('checked');
				$("#selfDefineDiv").css('display','none');
			}
			$("#selfDefineWidth").val(selfDefineWidth);
			$("#selfDefineHeight").val(selfDefineHeight);
			
			//保存按钮
			var initButtonsNode = initXmlUtils.getChildNodeByTagName(initRootNode,'Buttons');
			var initSaveButtonNode = initXmlUtils.hasNodeByAttribute(initButtonsNode,'type',EDIT_BUTTON_SAVE);
			if(initSaveButtonNode && (initSaveButtonNode.getAttribute('visible')==true || initSaveButtonNode.getAttribute('visible') == 'true')){
				$(":checkbox[id='fm_editpageset_isshowsave']").attr("checked",true);
			}else{
				$(":checkbox[id='fm_editpageset_isshowsave']").attr("checked",false);
			}
			//取消按钮			
			var initCancelButtonNode = initXmlUtils.hasNodeByAttribute(initButtonsNode,'type',EDIT_BUTTON_CANCEL);
			if(initCancelButtonNode && (initCancelButtonNode.getAttribute('visible')==true || initCancelButtonNode.getAttribute('visible') == 'true')){
				$(":checkbox[id='fm_editpageset_isshowcancel']").attr("checked",true);
			}else{
				$(":checkbox[id='fm_editpageset_isshowcancel']").attr("checked",false);
			}
			//分组设置
			var initGroupsNode = initXmlUtils.getChildNodeByTagName(initRootNode,'Groups');
			if(initGroupsNode && (initGroupsNode.getAttribute('visible')==true || initGroupsNode.getAttribute('visible') == 'true')){
				$(":checkbox[id='fm_editpageset_isshowgroup']").attr("checked",true);
			}else{
				$(":checkbox[id='fm_editpageset_isshowgroup']").attr("checked",false);
			}
			//js规则引擎
			var jsrules = '';
			if(initGroupsNode.getAttribute('jsRules') != null && initGroupsNode.getAttribute('jsRules') != 'null' && initGroupsNode.getAttribute('jsRules') != ''){
				jsrules = initGroupsNode.getAttribute('jsRules');
			}
			$('#edit_jsrules').val(jsrules);
			var initGroupNodes = initXmlUtils.getChildNodes(initGroupsNode);
			if(initGroupNodes){
				//$(":checkbox[id='fm_editpageset_isshowgroup']").attr("checked",true);
				var len = initGroupNodes.length;
				for (var i=0;i<len;i++){
					if(len > 1 && i != 0){
	        			fmEditpagesetGroupsetAdditem();
	        		}
					var initGroupNode = initGroupNodes[i];										
					$('#fm_editpageset_groupid_' + i).val(initGroupNode.getAttribute('id'));
					$('#fm_editpageset_grouptitle_' + i).val(initGroupNode.getAttribute('title'));
					var sortIndex = 0;
					if(initGroupNode.getAttribute('sortIndex') != null && initGroupNode.getAttribute('sortIndex') != 'null'){
						sortIndex = initGroupNode.getAttribute('sortIndex');
					}
					$('#fm_editpageset_groupsort_' + i).val(sortIndex);
					if(initGroupNode.getAttribute('type') != ""){
						$('#fm_editpageset_grouptype_' + i).combobox("setValue",initGroupNode.getAttribute('type'));
					}else{
						$("#fm_editpageset_grouptype_" + i).combobox('setValue','-1');
					}
					if(initGroupNode.getAttribute('visible') != ""){
						$('#fm_editpageset_grccoupshow_' + i).combobox("setValue",initGroupNode.getAttribute('visible'));
					}else{
						$("#fm_editpageset_grccoupshow_" + i).combobox('setValue','-1');
					}
					
				}
			}	
			
			
			
			
		});		
		//PageSet Begin
		function fmEditpagesetPagesetSave(){
			var editpagesetXmlUtils = new XmlUtils({dataType:'json'}); 
			editpagesetXmlUtils.loadXmlString(editFormSettings);
			var editpagesetRootNode = editpagesetXmlUtils.getRoot();		
			editpagesetXmlUtils.setAttribute(editpagesetRootNode,'title',$("#fm_editpageset_pageset_title").val());
			
			//GUOWEIXIN  "是否对当前表单操作加入记录 isSystemActionlog"
			if($('#isSystemActionlog').attr('checked')=="true" || $('#isSystemActionlog').attr('checked')==true){
				editpagesetXmlUtils.setAttribute(editpagesetRootNode,'isSystemActionlog',"true");
			}else{
				editpagesetXmlUtils.setAttribute(editpagesetRootNode,'isSystemActionlog',"false");
			}
			
			//保存前SQL，保存后SQL值
			//var editBeforeSqlCom=$("#editPage_before_sql").combobox("getValue");
			//editpagesetXmlUtils.setAttribute(editpagesetRootNode,'editPage_before_sql',editBeforeSqlCom);
			var editAfterSqlCom=$("#editPage_after_sql").combobox("getValue");
			editpagesetXmlUtils.setAttribute(editpagesetRootNode,'editPage_after_sql',editAfterSqlCom);
			
			if($('#selfDefine').is(':checked')){
				editpagesetXmlUtils.setAttribute(editpagesetRootNode,'selfDefine','on');
			}else{
				editpagesetXmlUtils.setAttribute(editpagesetRootNode,'selfDefine','off');
			}
			editpagesetXmlUtils.setAttribute(editpagesetRootNode,'selfDefineWidth',$("#selfDefineWidth").val());
			editpagesetXmlUtils.setAttribute(editpagesetRootNode,'selfDefineHeight',$("#selfDefineHeight").val());
			
			var editButtonsNode = editpagesetXmlUtils.getChildNodeByTagName(editpagesetRootNode,'Buttons');
			//是否显示保存按钮
			var editSaveButtonNode = editpagesetXmlUtils.hasNodeByAttribute(editButtonsNode,'type',EDIT_BUTTON_SAVE);
			if($(":checkbox[id='fm_editpageset_isshowsave']").attr("checked")){
				if(editSaveButtonNode){
					editpagesetXmlUtils.setAttribute(editSaveButtonNode,'visible','true');
				}else{
					editpagesetXmlUtils.createNode('Button','',{id:'',type:EDIT_BUTTON_SAVE,name:'保存',visible:'true'},editButtonsNode);
				}
			}else{
				if(editSaveButtonNode){
					editpagesetXmlUtils.setAttribute(editSaveButtonNode,'visible','false');
				}
			}
			//是否显示取消按钮			
			var editCancelButtonNode = editpagesetXmlUtils.hasNodeByAttribute(editButtonsNode,'type',EDIT_BUTTON_CANCEL);
			if($(":checkbox[id='fm_editpageset_isshowcancel']").attr("checked")){
				if(editCancelButtonNode){
					editpagesetXmlUtils.setAttribute(editCancelButtonNode,'visible','true');
				}else{
					editpagesetXmlUtils.createNode('Button','',{id:'',type:EDIT_BUTTON_CANCEL,name:'关闭',visible:'true'},editButtonsNode);
				}
			}else{
				if(editCancelButtonNode){
					editpagesetXmlUtils.setAttribute(editCancelButtonNode,'visible','false');
				}
			}

			editpagesetXmlUtils.setAttribute(editpagesetRootNode,'workflowParCom',$("#isWorkFlow_comPar_hidden").val());
			editpagesetXmlUtils.setAttribute(editpagesetRootNode,'workflowParComId',$("#isWorkFlow_comPar_id").val());
			editpagesetXmlUtils.setAttribute(editpagesetRootNode,'workflowParComArray',$("#isWorkFlow_comPar_array").val());

			//分组设置
			var editGroupsNode = editpagesetXmlUtils.getChildNodeByTagName(editpagesetRootNode,'Groups');
			if($('#fm_editpageset_isshowgroup').attr('checked')){
				editpagesetXmlUtils.removeChildNodes(editGroupsNode);
				var flag = false;
				editpagesetXmlUtils.setAttribute(editGroupsNode,'visible','true');
				$("#fm_editpageset_groupset_tbody tr").each(function(i,node){ 
					if(node.id != 'fm_editpageset_default_tr'){
						var groupIdEle = $(node).find("td input[id^='fm_editpageset_groupid_']");
						var groupTitleEle = $(node).find("td input[id^='fm_editpageset_grouptitle_']");
						var groupSortEle = $(node).find("td input[id^='fm_editpageset_groupsort_']");
						var groupTypeEle = $(node).find("td select[id^='fm_editpageset_grouptype_']");
						var groupShowEle = $(node).find("td select[id^='fm_editpageset_grccoupshow_']");
						var groupTitle = groupTitleEle.val();
						if(groupTitle != ""){
							var groupId;
							if(groupIdEle.val() == ""){
								groupId = "edit" + parseInt(1000*Math.random());
							}else{
								groupId = groupIdEle.val();
							}
							var groupSort = groupSortEle.val();
							var groupType = groupTypeEle.combobox("getValue")=="-1"?"":groupTypeEle.combobox("getValue");
							var groupShow = groupShowEle.combobox("getValue")=="-1"?"":groupShowEle.combobox("getValue");
							var editGroupNode = editpagesetXmlUtils.hasNodeByAttribute(editGroupsNode,'id',groupId);
							if(editGroupNode){
								editpagesetXmlUtils.setAttribute(editGroupNode,'id',groupId);
								editpagesetXmlUtils.setAttribute(editGroupNode,'title',groupTitle);
								editpagesetXmlUtils.setAttribute(editGroupNode,'sortIndex',groupSort);
								editpagesetXmlUtils.setAttribute(editGroupNode,'type',groupType);
								editpagesetXmlUtils.setAttribute(editGroupNode,'visible',groupShow);
							}else{
								editpagesetXmlUtils.createNode('Group','',{id:groupId,title:groupTitle,visible:groupShow,type:groupType,sortIndex:groupSort},editGroupsNode);
							}
						}else{
							flag = true;
						}
					}
				});
			}else{
				editpagesetXmlUtils.setAttribute(editGroupsNode,'visible','false');
			}
			//js规则引擎
			var jsrules = $('#edit_jsrules').val();
			if(jsrules!=null&&jsrules!=''){
				editpagesetXmlUtils.setAttribute(editGroupsNode,'jsRules',jsrules);
			}else{
				editpagesetXmlUtils.setAttribute(editGroupsNode,'jsRules','');
			}		
			if(flag){
				$.messager.alert("提示","请输入分组名称！", 'warning');	
				return false;
			}
			editpagesetXmlUtils.setAttribute(editButtonsNode,'isConfig','1');
			editPageSetSubmit(editpagesetXmlUtils.toString());
			$("#fm_formdesignedit_basic").html(CONFIGYES);
		}
		function fmEditpagesetGroupsetAdditem(){
			var tbody = $("#fm_editpageset_groupset_tbody");
			var cloneObject = $(tbody.find("tr").last()).clone();
			fmPagesetGroupsetAdditem(cloneObject,"fm_editpageset_groupup_","fm_editpageset_groupdown_");
			cloneObject.appendTo(tbody);
			fmTableRowsSortImgHidden("fm_editpageset_groupset_tbody","fm_editpageset_groupup_","fm_editpageset_groupdown_");
			fmEditpagesetGroupsetInitData(cloneObject);
			resizeGroupSettingPanel();
		}
		function fmEditpagesetGroupsetInitData(nodeObj)
		{
			var grouptypeSelect = $(nodeObj).find("td select[id^='fm_editpageset_grouptype_']");
			grouptypeSelect.combobox({
				valueField:'id',
   				textField:'text',  
            	treeWidth:70,
            	editable:false,
			    data:fmPagesetGroupTypeData
			});
			grouptypeSelect.combobox('setValue','-1');
			
			var groupshowSelect = $(nodeObj).find("td select[id^='fm_editpageset_grccoupshow_']");
			groupshowSelect.combobox({
				valueField:'showkey',
   				textField:'showtext',  
            	treeWidth:30,
            	editable:false,
			    data:fmPagesetGroupShowData
			});
			groupshowSelect.combobox('setValue','-1');

		}
		function resizeGroupSettingPanel(){
			$("#editPageSetBasicGroupSettingPanelParent").height($("#editPageSetBasicGroupSettingPanelTable").height()+133);
		}
		//PageSet End
    </script>
  </head>  
  <body>
  	<div style="width:777px;height:160px;" id="editPageSetBasicPanelBasiceInfo">
		
		<div class="easyui-panel" id="editPageSetBasicPanelBasiceInfoPanel" title="基本信息配置" fit="true" headerCls="header_cls">
			<table border="0" cellpadding="5" cellspacing="0" width="100%">
			  	<tr>
				  <td width="40%">&nbsp;&nbsp;&nbsp;&nbsp;标题： <input type="text" id="fm_editpageset_pageset_title"/>
				  	
				  </td>
				  <td> 
				  	  <input type="checkbox" id="isSystemActionlog" />是否对当前表单操作加入日志记录
				  </td>					  
				</tr>
			  	<tr>					  
				  <td>&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="fm_editpageset_isshowsave"/> 显示提交按钮</td>					  
				  <td><input type="checkbox" id="fm_editpageset_isshowcancel"/> 显示取消按钮</td>
				</tr>
				<tr>
				  <td colspan="2" >
				    &nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="selfDefine" /> 自定义宽高 
				    &nbsp;&nbsp;&nbsp;&nbsp;<span id="selfDefineDiv" style="display: none;">
			  		 宽度： <input type="text" id="selfDefineWidth" />
			  		 高度： <input type="text" id="selfDefineHeight" />
			  		</span>
				  </td>
				</tr>
				<tr>
				  <td  colspan="0.5" valign="top">
				    &nbsp;&nbsp;&nbsp;&nbsp;<input type="hidden" id="isWorkFlow_comPar_hidden" /> 提交参数：
				    <input type="checkbox" id="isWorkFlow_comPar_checkbox" style="vertical-align: middle;">
				  </td>
				  <td align="left" colspan="1.5">
				      <div  id="workflow_comPar_td" style="display: none;border:1px solid #e4e4e4;padding:4px;">
			  		  关联字段name： <input type="text" id="isWorkFlow_comPar_id" maxlength="16" style="vertical-align: middle;"><br/>
			  		  <div style="height:6px;visibility: hidden;"></div>
			  		  &nbsp; &nbsp; 关联状态组： <textarea id="isWorkFlow_comPar_array" style="vertical-align: middle;" cols="39" rows="5"></textarea>
				      </div>
				  </td>
				</tr>
			</table>
		</div>
	</div>
	<div style="height:4px;"></div>
	<div style="width:777px;height:190px;" id="editPageSetBasicGroupSettingPanelParent">
		<div class="easyui-panel" title="分组设置"   headerCls="header_cls">
			<table border="0" cellpadding="1" cellspacing="1" width="100%" style="background:#dedede;" id="editPageSetBasicGroupSettingPanelTable">
				<tr style="background:#ffffff;">					  
				  <td style="background:#eeeeee;" colspan="9"><input type="checkbox" id="fm_editpageset_isshowgroup"/> 显示分组&nbsp;&nbsp;&nbsp;&nbsp;
				  <a href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="fmEditpagesetGroupsetAdditem()">增加</a></td>
				</tr>
				<!-- 
					<tr style="background:#ffffff;">
					  <td width="10%" align="right">ID：</td>
					  <td width="18%"><input type="text" id="fm_editpageset_groupid"/></td>
					  <td width="10%" align="right">标题：</td>
					  <td width="18%"><input type="text" id="fm_editpageset_grouptitle"/></td>
					  <td width="10%" align="right">布局：</td>
					  <td width="10%"><select id="fm_editpageset_grouptype"><option value="0">上下</option><option value="1">左右</option></select></td>
					  <td><a href="javascript:;" class="easyui-linkbutton" plain="true" icon="icon-remove">删除</a></td>
					</tr>
				 -->
				<tbody id="fm_editpageset_groupset_tbody">
					<tr style="background:#ffffff;display:none;" id="fm_editpageset_default_tr">
					  <td width="8%" style="display:none;">ID：</td>
					  <td width="12%" style="display:none;"><input type="text" id="fm_editpageset_groupid_-1" size="10"/></td>
					  <td width="10%" align="right">分组名称：</td>
					  <td width="20%"><input type="text" id="fm_editpageset_grouptitle_-1"/></td>
					  <td width="8%" align="right">布局：</td>
					  <td width="10%"><select id="fm_editpageset_grouptype_-1"></select></td>
					  <td width="8%" style="display:none;">序号：</td>
					  <td width="8%" style="display:none;"><input type="text" id="fm_editpageset_groupsort_-1" size="5" value="0"/></td>
					  <td width="10%" align="right">是否显示：</td>
					  <td width="5%"><select id="fm_editpageset_grccoupshow_-1"></select></td>
					  <td>
					  	<!-- <a id="fm_editpageset_groupset_delete_-1" href="javascript:;" plain="true" icon="icon-remove" onclick="fmPagesetDynamicDeleteItem(this)">删除</a> -->
					  	<img src="images/ioc-delete.gif" style="cursor:hand;margin:2px 2px 0px 4px;" title="删除" onclick="fmPagesetSortDynamicDeleteItem(this,'fm_editpageset_groupset_tbody','fm_editpageset_groupup_','fm_editpageset_groupdown_');"/>
					  	<img id="fm_editpageset_groupup_-1" src="images/up.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="上移" onclick="fmTableRowsSort(this,1,7,8)"/>
					  	<img id="fm_editpageset_groupdown_-1" src="images/down.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="下移" onclick="fmTableRowsSort(this,0,7,8)"/>
					  </td>
					</tr>	
					<tr style="background:#ffffff;">
					  <td style="display:none;">ID：</td>
					  <td width="12%" style="display:none;"><input type="text" id="fm_editpageset_groupid_0" size="10"/></td>
					  <td width="10%" align="right">分组名称：</td>
					  <td width="20%"><input type="text" id="fm_editpageset_grouptitle_0"/></td>
					  <td width="8%" align="right">布局：</td>
					  <td width="10%"><select id="fm_editpageset_grouptype_0"></select></td>
					  <td width="8%" style="display:none;">序号：</td>
					  <td width="8%" style="display:none;"><input type="text" id="fm_editpageset_groupsort_0" size="5" value="1"/></td>
					  <td width="10%" align="right">是否显示：</td>
					  <td width="5%"><select id="fm_editpageset_grccoupshow_0"></select></td>
					  <td>
					  	<img src="images/ioc-delete.gif" style="cursor:hand;margin:2px 2px 0px 4px;" title="删除" onclick="fmPagesetSortDynamicDeleteItem(this,'fm_editpageset_groupset_tbody','fm_editpageset_groupup_','fm_editpageset_groupdown_');resizeGroupSettingPanel();"/>
					  	<img id="fm_editpageset_groupup_0" src="images/up.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="上移" onclick="fmTableRowsSort(this,1,7,8)"/>
					  	<img id="fm_editpageset_groupdown_0" src="images/down.gif" style="cursor:hand;margin:2px 2px 0px 2px;display:none;" title="下移" onclick="fmTableRowsSort(this,0,7,8)"/>
					  </td>
					</tr>	
				</tbody>			
			</table>
		</div>
		<div class="easyui-panel" title='规则引擎(<font color="red">注意：字符串变量不要用单引号，要用双引号。$("#分组名_edit_id").css("display","none");$("input:last[name=字段名]").val();</font>)' headerCls="header_cls">
			<table border="0" cellpadding="1" cellspacing="1" width="100%" style="background:#dedede;">
				<tr>
					<td><textarea id="edit_jsrules" rows="10" cols="93"></textarea> </td>
				</tr>
			</table>
		</div>
	</div>
	<div style="height:4px;"></div>
	<div style="width:777px;height:100px;">
		<div class="easyui-panel" title="保存前或者后执行sql语句设置" id="editPageSetExcuteSQLPanel"  headerCls="header_cls">
		保存前执行sql语句
		<input  id="editPage_before_sql"> 
		<div style="height:0;line-height: 0;"></div>
		 保存后执行sql语句
		<input  value=""  id="editPage_after_sql">
		</div>
		<table border="0" cellpadding="5" cellspacing="0" width="100%" style="">
			<tr>
				<td align="center"><a class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="fmEditpagesetPagesetSave()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="easyui-linkbutton" icon="icon-cancel" href="javascript:;"  onclick="fmEditPageSetClose()" >关闭</a></td>										
			</tr>					
		</table>
	</div>
  </body>
</html>
