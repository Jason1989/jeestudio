<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>列表页设计</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<script>
		var formId = '${form.id}';
		var mainObjectId = '${form.dataTable.id}';
		var mainObjectName = '${form.dataTable.name}';
		var formSettings = '${formSettings}';
		var fieldshowjson = '${fieldshowjson}';
		var fieldhiddenjson = '${fieldhiddenjson}';
		var fieldalljson = '${fieldalljson}';
		var queryshowjson = '${queryshowjson}';
		var queryhiddenjson = '${queryhiddenjson}';
		var primarykeyColumnName = '${primarykey}';
		
		$(function(){
			$('#fm_formdesignlist_setpanel').draggable({axis:null,handle:'#fm_formdesignlist_setpaneltitle'});
			$('#fm_formdesignlist_listpageset').appendTo('body').window({
                title: "列表页配置面板",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 800,
                height: 550
            });
            $("#fm_formdesignlist_listpageset").window({'href':'form/form!toListPageSet.action'});	
			$("#fm_formdesignlist_listpageset").window('open');	
			$("#fm_formdesignlist_listpageset").window('close');				
			
            var xmlUtils = new XmlUtils({dataType:'json'}); 
			xmlUtils.loadXmlString(formSettings);
			var root = xmlUtils.getRoot();
			var columnsNode = xmlUtils.getChildNodeByTagName(root,'Columns');
			var columnsIsConfig = columnsNode.getAttribute('isConfig');
			if(columnsIsConfig && columnsIsConfig == '1'){
				$("#fm_formdesignlist_datacolumn").html(CONFIGYES);
			}else{
				$("#fm_formdesignlist_datacolumn").html(CONFIGNO);
			}
			var paramsNode = xmlUtils.getChildNodeByTagName(root,'Params');
			var paramsIsConfig = paramsNode.getAttribute('isConfig');
			if(paramsIsConfig && paramsIsConfig == '1'){
				$("#fm_formdesignlist_param").html(CONFIGYES);
			}else{
				$("#fm_formdesignlist_param").html(CONFIGNO);
			}
			var tabsNode = xmlUtils.getChildNodeByTagName(root,'Tabs');
			var tabsIsConfig = tabsNode.getAttribute('isConfig');
			if(tabsIsConfig && tabsIsConfig == '1'){
				$("#fm_formdesignlist_tabs").html(CONFIGYES);
			}else{
				$("#fm_formdesignlist_tabs").html(CONFIGNO);
			}
			var buttonsNode = xmlUtils.getChildNodeByTagName(root,'Buttons');
			var buttonsIsConfig = buttonsNode.getAttribute('isConfig');
			if(buttonsIsConfig && buttonsIsConfig == '1'){
				$("#fm_formdesignlist_basic").html(CONFIGYES);
			}else{
				$("#fm_formdesignlist_basic").html(CONFIGNO);
			}
			var queryZoneNode = xmlUtils.getChildNodeByTagName(root,'QueryZone');
			var queryZoneIsConfig = queryZoneNode.getAttribute('isConfig');
			if(queryZoneIsConfig && queryZoneIsConfig == '1'){
				$("#fm_formdesignlist_query").html(CONFIGYES);
			}else{
				$("#fm_formdesignlist_query").html(CONFIGNO);
			}
		});
		
		function refreshListPageSetData(){
       		$.ajax({
			   	type: "POST",
			   	async: false,
			   	url: "form/form!refreshDesignData.action",
			   	data: "formId="+formId,
			   	success: function(data){
			   		var dataObj = eval('(' + data + ')');
           			formSettings = dataObj['formsettings'];
           			fieldshowjson = dataObj['fieldshowjson'];
           			fieldhiddenjson = dataObj['fieldhiddenjson'];
           			queryshowjson = dataObj['queryshowjson'];
					queryhiddenjson = dataObj['queryhiddenjson'];
           			primarykeyColumnName = dataObj['pkey'];
			   	}
			});
		}
		//var listflag = false;
		function fmOpenListPageSetWindow(title){	
			$("#fm_formdesignlist_listpageset").window({'href':''});	
			$("#fm_formdesignlist_listpageset").window('refresh');
				
			$("#fm_formdesignlist_listpageset").window({'href':'form/form!toListPageSet.action'});	
			$("#fm_formdesignlist_listpageset").window('open');						
			//if(!listflag){
			//	listflag = true;
				setTimeout('fmOpenListPageSetSelectTabs("'+title+'")',500);
			//}else{
			//	fmOpenListPageSetSelectTabs(title);
			//}
			fmRefreshPagesCombotree();
			
		}
		function fmOpenListPageSetSelectTabs(title){
			try{
				$("#fm_listpageset_tabs").tabs('select',title);
				
			}catch(e){
				//alert(e);
			}
		}
		function fmRefreshPagesCombotree(){
			$('#fm_listpageset_selecteditpage').combotree('reload');
			$('#fm_listpageset_selectviewpage').combotree('reload');
		}
		function fmRefreshDesignListIframe(){
			window.frames['fm_formdesignlist_detailarea'].location.reload();
		}

	</script>
	</head>
	<body>
		<div id="fm_formdesignlist_main" fit="true" style="height:530px;">
			<!-- 
			<div id="fm_formdesignlist_detailarea" fit="true" class="easyui-panel" ></div>
			 -->
			<iframe  id="fm_formdesignlist_detailarea" name="detailAreaIframe" src="formengine/listPageAction.action?formId=${form.id}&preview=easyuiview" width="100%" height="100%" framespacing="0" marginwidth="0" marginheight="0" frameborder="no" border="0">
			</iframe>

			<!-- 
			<div id="fm_formdesignlist_queryarea" style="font-size:10;">
			</div>
			<div id="fm_formdesignlist_detailarea" fit="true">
				<table id="fm_formdesignlist_table"></table>
			</div>
			 -->
			<div id="fm_formdesignlist_setpanel" style="width:152px;background:#fafafa;border:1px solid #8DB2E3;position:absolute;top:70px;right:20px;">
				<div id="fm_formdesignlist_setpaneltitle" class="panel-header panel-header-noborder">配置面板</div>
				<div style="width:150px;">
					<a href="javascript:;" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-edit" onclick="fmOpenListPageSetWindow('页面配置')">页面信息配置</a>
					<span id="fm_formdesignlist_basic" style="width:10px;margin-right:3px; "></span>
				</div>
				<div style="width:150px;">
					<a href="javascript:;" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-edit" onclick="fmOpenListPageSetWindow('数据列配置')">数据列配置</a>
					<span id="fm_formdesignlist_datacolumn" style="width:10px;margin-right:3px;"></span>
				</div>
				<div style="width:150px;">
					<a href="javascript:;" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-edit" onclick="fmOpenListPageSetWindow('排序配置')">排序配置</a>
					<span id="fm_formdesignlist_sort" style="width:10px;margin-right:3px;"></span>
				</div>
				<div style="width:150px;">
					<a href="javascript:;" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-edit" onclick="fmOpenListPageSetWindow('多标签配置');">多标签配置</a>
					<span id="fm_formdesignlist_tabs"  style="width:10px;margin-right:3px;"></span>
				</div>
				<div style="width:150px;">
					<a href="javascript:;" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-edit" onclick="fmOpenListPageSetWindow('组合查询配置');">组合查询配置</a>
					<span id="fm_formdesignlist_query" style="width:10px;margin-right:3px;"></span>
				</div>
				<div style="width:150px;">
					<a href="javascript:;" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-edit" onclick="fmOpenListPageSetWindow('参数配置');">参数配置</a>
					<span id="fm_formdesignlist_param" style="width:10px;margin-right:3px;"></span>
				</div>
				<div style="width:150px;">
					<a href="javascript:;" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-edit" onclick="fmOpenListPageSetWindow('操作列配置');">操作列配置</a>
					<span id="fm_formdesignlist_operateButton" style="width:10px;margin-right:3px;"></span>
				</div>
				<div style="width:150px;">
					<a href="javascript:;" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-edit" onclick="fmOpenListPageSetWindow('toolbar配置');">toolbar配置</a>
					<span id="fm_formdesignlist_toolbarButton" style="width:10px;margin-right:3px;"></span>
				</div>
			</div>
		</div>	
		<div id="fm_formdesignlist_listpageset" class="easyui-window"></div>
	</body>
</html>
