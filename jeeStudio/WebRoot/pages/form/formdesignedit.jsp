<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script>
	var editFormId = '${form.id}';
	var editMainObjectId = '${form.dataTable.id}';
	var editFormSettings = '${formSettings}';
	var editFieldlistJson = '${fieldlistjson}';
	$(function(){
		$('#fm_formdesignedit_setpanel').draggable({axis:null,handle:'#fm_formdesignedit_setpaneltitle'});
		$('#fm_formdesignedit_editpageset').appendTo('body').window({
               title: "编辑页配置面板",
               modal: true,
               resizable: false,
               minimizable: false,
               maximizable: false,
               shadow: false,
               closed: true,
               width: 850,
               height: 550
           });
        $("#fm_formdesignedit_editpageset").window({'href':'form/form!toEditPageSet.action'});		
		$("#fm_formdesignedit_editpageset").window('open');
		$("#fm_formdesignedit_editpageset").window('close');
           var xmlUtils = new XmlUtils({dataType:'json'});
		xmlUtils.loadXmlString(editFormSettings);
		var root = xmlUtils.getRoot();
		var columnsNode = xmlUtils.getChildNodeByTagName(root,'Columns');
		var columnsIsConfig = columnsNode.getAttribute('isConfig');
		if(columnsIsConfig && columnsIsConfig == '1'){
			$("#fm_formdesignedit_datacolumn").html(CONFIGYES);
		}else{
			$("#fm_formdesignedit_datacolumn").html(CONFIGNO);
		}
		var paramsNode = xmlUtils.getChildNodeByTagName(root,'Params');
		var paramsIsConfig = paramsNode.getAttribute('isConfig');
		if(paramsIsConfig && paramsIsConfig == '1'){
			$("#fm_formdesignedit_param").html(CONFIGYES);
		}else{
			$("#fm_formdesignedit_param").html(CONFIGNO);
		}
		var tabsNode = xmlUtils.getChildNodeByTagName(root,'Tabs');
		var tabsIsConfig = tabsNode.getAttribute('isConfig');
		if(tabsIsConfig && tabsIsConfig == '1'){
			$("#fm_formdesignedit_tabs").html(CONFIGYES);
		}else{
			$("#fm_formdesignedit_tabs").html(CONFIGNO);
		}
		var buttonsNode = xmlUtils.getChildNodeByTagName(root,'Buttons');
		var buttonsIsConfig = buttonsNode.getAttribute('isConfig');
		if(buttonsIsConfig && buttonsIsConfig == '1'){
			$("#fm_formdesignedit_basic").html(CONFIGYES);
		}else{
			$("#fm_formdesignedit_basic").html(CONFIGNO);
		}
	});
	
		
	function refreshEditPageSetData(){
      		$.ajax({
		   	type: "POST",
		   	async: false,
		   	url: "form/form!refreshEditPageDesignData.action",
		   	data: "formId="+editFormId,
		   	success: function(data){
		   		var dataObj = eval('(' + data + ')');
          			editFormSettings = dataObj['formsettings'];
          			editFieldlistJson = dataObj['fieldlistjson'];
		   	}
		});
	}
	//var editflag = false;
	function fmOpenEditPageSetWindow(title){
		$("#fm_formdesignedit_editpageset").window({'href':''});	
		$("#fm_formdesignedit_editpageset").window('refresh');		
		$("#fm_formdesignedit_editpageset").window({'href':'form/form!toEditPageSet.action'});		
		$("#fm_formdesignedit_editpageset").window('open');
		//if(!editflag){
		//	editflag = true;
			setTimeout('fmOpenEditPageSetSelectTabs("'+title+'")',500);
		//}else{
		//	alert(title);
		//	fmOpenEditPageSetSelectTabs(title); 
		//}
      		      		
	}
	function fmOpenEditPageSetSelectTabs(title){
		$("#fm_editpageset_tabs").tabs('select',title); 
	}
	function fmRefreshDesignEditIframe(){
		window.frames['fm_formdesignedit_detailarea'].location.reload();
	}
</script>

<div id="fm_formdesignedit_main" fit="true" style="height:530px;">
	<!-- 
	<div id="fm_formdesignedit_detailarea" fit="true">
	</div> -->
	<iframe  id="fm_formdesignedit_detailarea" name="detailAreaIframe" src="formengine/editPageAction.action?formId=${form.id}&preview=0&atw=1" width="100%" height="100%" framespacing="0" marginwidth="0" marginheight="0" frameborder="no" border="0">
	</iframe>
	<div id="fm_formdesignedit_setpanel" style="width:152px;background:#fafafa;border:1px solid #8DB2E3;position:absolute;top:70px;right:20px;">
		<div id="fm_formdesignedit_setpaneltitle" class="panel-header panel-header-noborder">配置面板</div>
		<div style="width:150px;">
			<a href="javascript:void(0);" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-edit" onclick="fmOpenEditPageSetWindow('页面信息配置');">页面信息配置</a>
			<span id="fm_formdesignedit_basic" style="width:10px;margin-right:3px; "></span>
		</div>
		<div  style="width:150px;">
			<a href="javascript:void(0);" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-edit" onclick="fmOpenEditPageSetWindow('数据列配置');">数据列配置</a>
			<span id="fm_formdesignedit_datacolumn" style="width:10px;margin-right:3px; "></span>
		</div>
		<div  style="width:150px;">
			<a href="javascript:void(0);" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-edit" onclick="fmOpenEditPageSetWindow('多标签配置');">多标签配置</a>
			<span id="fm_formdesignedit_tabs" style="width:10px;margin-right:3px; "></span>
		</div>
		<div  style="width:150px;">
			<a href="javascript:void(0);" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-edit" onclick="fmOpenEditPageSetWindow('参数配置');">参数配置</a>
			<span id="fm_formdesignedit_param" style="width:10px;margin-right:3px; "></span>
		</div>
	</div>
</div>	
<div id="fm_formdesignedit_editpageset" ></div>