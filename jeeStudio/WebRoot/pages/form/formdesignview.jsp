<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>查看页设计</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<script>
		var viewFormId = '${form.id}';
		var viewMainObjectId = '${form.dataTable.id}';
		var viewFormSettings = '${formSettings}';
		var viewFieldlistJson = '${fieldlistjson}';		
		$(function(){
		
			$('#fm_formdesignview_setpanel').draggable({axis:null,handle:'#fm_formdesignview_setpaneltitle'});
			$('#fm_formdesignview_viewpageset').appendTo('body').window({
                title: "查看页配置面板",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 800,
                height: 550
            });
            $("#fm_formdesignview_viewpageset").window({'href':'form/form!toViewPageSet.action'});		
			$("#fm_formdesignview_viewpageset").window('open');
			$("#fm_formdesignview_viewpageset").window('close');
			
            var xmlUtils = new XmlUtils({dataType:'json'}); 
			xmlUtils.loadXmlString(viewFormSettings);
			var root = xmlUtils.getRoot();
			var columnsNode = xmlUtils.getChildNodeByTagName(root,'Columns');
			var columnsIsConfig = columnsNode.getAttribute('isConfig');
			if(columnsIsConfig && columnsIsConfig == '1'){
				$("#fm_formdesignview_datacolumn").html(CONFIGYES);
			}else{
				$("#fm_formdesignview_datacolumn").html(CONFIGNO);
			}
			var paramsNode = xmlUtils.getChildNodeByTagName(root,'Params');
			var paramsIsConfig = paramsNode.getAttribute('isConfig');
			if(paramsIsConfig && paramsIsConfig == '1'){
				$("#fm_formdesignview_param").html(CONFIGYES);
			}else{
				$("#fm_formdesignview_param").html(CONFIGNO);
			}
			var tabsNode = xmlUtils.getChildNodeByTagName(root,'Tabs');
			var tabsIsConfig = tabsNode.getAttribute('isConfig');
			if(tabsIsConfig && tabsIsConfig == '1'){
				$("#fm_formdesignview_tabs").html(CONFIGYES);
			}else{
				$("#fm_formdesignview_tabs").html(CONFIGNO);
			}
			var buttonsNode = xmlUtils.getChildNodeByTagName(root,'Buttons');
			var buttonsIsConfig = buttonsNode.getAttribute('isConfig');
			if(buttonsIsConfig && buttonsIsConfig == '1'){
				$("#fm_formdesignview_basic").html(CONFIGYES);
			}else{
				$("#fm_formdesignview_basic").html(CONFIGNO);
			}
		});
		
			
		function refreshViewPageSetData(){
       		$.ajax({
			   	type: "POST",
			   	async: false,
			   	url: "form/form!refreshEditPageDesignData.action",
			   	data: "formId="+viewFormId,
			   	success: function(data){
			   		var dataObj = eval('(' + data + ')');
           			viewFormSettings = dataObj['formsettings'];
           			viewFieldlistJson = dataObj['fieldlistjson'];
			   	}
			});
		}
		//var veiwflag = false;
		function fmOpenViewPageSetWindow(title){	
			$("#fm_formdesignview_viewpageset").window({'href':''});	
			$("#fm_formdesignview_viewpageset").window('refresh');
			$("#fm_formdesignview_viewpageset").window({'href':'form/form!toViewPageSet.action'});		
			$("#fm_formdesignview_viewpageset").window('open');
			//if(!veiwflag){
			//	veiwflag = true;
				setTimeout('fmOpenViewPageSetSelectTabs("'+title+'")',500);
			//}else{
			//	fmOpenViewPageSetSelectTabs(title);    
			//}
       		   		
		}
		function fmOpenViewPageSetSelectTabs(title){
			$("#fm_viewpageset_tabs").tabs('select',title); 
		}
		function fmRefreshDesignViewIframe(){
			window.frames['fm_formdesignview_detailarea'].location.reload();
		}
	</script>
  </head>
	<body>
		<div id="fm_formdesignview_main" fit="true" style="height:530px;">
			<!-- <div id="fm_formdesignview_detailarea" fit="true">
			</div>
			 -->
			<iframe  id="fm_formdesignview_detailarea" name="detailAreaIframe" src="formengine/viewPageAction.action?formId=${form.id}&preview=0" width="100%" height="100%" framespacing="0" marginwidth="0" marginheight="0" frameborder="no" border="0">
			</iframe>
			<div id="fm_formdesignview_setpanel" style="width:152px;background:#fafafa;border:1px solid #8DB2E3;position:absolute;top:70px;right:20px;">
				<div id="fm_formdesignview_setpaneltitle" class="panel-header panel-header-noborder">配置面板</div>
				<div style="width:150px;">
					<a href="javascript:void(0);" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-edit" onclick="fmOpenViewPageSetWindow('页面信息配置');">页面信息配置</a>
					<span id="fm_formdesignview_basic" style="width:10px;margin-right:3px; "></span>
				</div>
				<div  style="width:150px;">
					<a href="javascript:;" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-edit" onclick="fmOpenViewPageSetWindow('数据列配置');">数据列配置</a>
					<span id="fm_formdesignview_datacolumn" style="width:10px;margin-right:3px; "></span>
				</div>
				<div  style="width:150px;">
					<a href="javascript:;" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-edit" onclick="fmOpenViewPageSetWindow('多标签配置');">多标签配置</a>
					<span id="fm_formdesignview_tabs" style="width:10px;margin-right:3px; "></span>
				</div>
				<div  style="width:150px;">
					<a href="javascript:;" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-edit" onclick="fmOpenViewPageSetWindow('参数配置');">参数配置</a>
					<span id="fm_formdesignview_param" style="width:10px;margin-right:3px; "></span>
				</div>
			</div>
		</div>	
		<div id="fm_formdesignview_viewpageset" class="easyui-window"></div>
	</body>
</html>
