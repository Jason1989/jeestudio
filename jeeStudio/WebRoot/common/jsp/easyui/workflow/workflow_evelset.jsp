<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">
$(function(){
//流程模板
$('#wf_evel_template_select').combobox({
	url:'workflow/WorkFlowFrame_comlist.action?ti='+new Date().getTime(),
	valueField:'key',
	textField:'value',
	panelHeight:100,
	onChange:function(newValue, oldValue){
		$('#wf_evel_template_his_select').combobox('setValue','');
	    $('#wf_evel_template_his_select').combobox('reload','workflow/nodeAuthSet!getTemplateHis.action?processInstanceID='+newValue+"&ti="+new Date().getTime());
	}
});

//版本号选择
$('#wf_evel_template_his_select').combobox({
	url:'',
	valueField:'modelId',
	textField:'modelName',
	panelHeight:100,
	onChange:function(newValue, oldValue){
	  var processId= $('#wf_evel_template_select').combobox('getValue');
	  var mid= newValue;
	   $('#wf_evel_activity_select').combobox('setValue','');
	   $('#wf_evel_activity_select').combobox('reload','acttab_findActivityIDs.action?processId='+processId+'&mid='+mid+"&ti="+new Date().getTime());
	}
});

//活动节点选择
$('#wf_evel_activity_select').combobox({
	url:'',
	valueField:'value',
	textField:'text',
	panelHeight:100,
	onChange:function(newValue, oldValue){
	
	  var processId= $('#wf_evel_template_select').combobox('getValue');
	  var mid= $('#wf_evel_template_his_select').combobox('getValue');
	  var activityId=newValue;
	  
	   $('#wf_evel_Precursor_select').combobox('setValue','');
	   $('#wf_evel_Precursor_select').combobox('reload','acttab_findPrecursorIDs.action?processId='+processId+'&mid='+mid+'&activityId='+activityId+"&ti="+new Date().getTime());
	
	
	}
});

//前驱状态选择
$('#wf_evel_Precursor_select').combobox({
	url:'',
	valueField:'value',
	textField:'text',
	panelHeight:100,
	onChange:function(newValue, oldValue){
		refershTable();
	}
});

});

function refershTable(){
	var processId = $('#wf_evel_template_his_select').combobox('getValue');
	var precursor = $('#wf_evel_Precursor_select').combobox('getValue');
	if(!precursor || precursor==null||precursor=='') return; 
	var evel_iframe = window.frames['wf_evel_iframe_detailarea'];
	var evel_iframe_src = 'acttab_editEvelset.action?processId='+processId+'&precursor='+precursor;
	$('#wf_evel_iframe_detailarea').attr('src',evel_iframe_src);
}

function workflow_evelset_save(){
	var processId = $('#wf_evel_template_his_select').combobox('getValue');
	var precursor = $('#wf_evel_Precursor_select').combobox('getValue');
	if(!precursor || precursor==null||precursor=='') return;
	var keyid = window.frames["wf_evel_iframe_detailarea"].document.getElementById("wf_evel_id_hidden").value;
	var buildingremark = window.frames["wf_evel_iframe_detailarea"].document.getElementById("wf_evel_isBundling_remark").value;
	var isBuilding = window.frames["wf_evel_iframe_detailarea"].document.getElementById("wf_evel_isBundling_hidden").value;
	var buildingfunction = window.frames["wf_evel_iframe_detailarea"].document.getElementById("wf_evel_isBundling_function").value;
	$.post("acttab_saveEvelset.action",
		{
			processId : processId,
			precursor : precursor,
			keyid : keyid,
			buildingremark : buildingremark,
			isBuilding : isBuilding,
			buildingfunction : buildingfunction
		},
		function(data){
			var evel_iframe = window.frames['wf_evel_iframe_detailarea'];
			var evel_iframe_src = 'acttab_editEvelset.action?processId='+processId+'&precursor='+precursor;
			$('#wf_evel_iframe_detailarea').attr('src',evel_iframe_src);
			$.messager.alert("提示", '保存成功！');
		}
	);
}

</script>
<div class="easyui-panel" fit="true" border="false"  title="工作流前驱事件绑定" >
	<div class="easyui-layout" fit="true" border="false">
	    <div region="north" border="false" style="height:40px;padding: 10px 0;margin-left:13px;vertical-align: middle; ">
	       <label style="margin-bottom: 15px;">工作流模板：</label><input id="wf_evel_template_select" style="width:150px;">&nbsp; &nbsp; 
	       <label style="margin-bottom: 15px;">历史版本：  </label><input id="wf_evel_template_his_select"  style="width:150px;">&nbsp; &nbsp; 
	       <label style="margin-bottom: 15px;">活动节点：  </label><input id="wf_evel_activity_select" style="width:150px;">&nbsp; &nbsp; 
	       <label style="margin-bottom: 15px;">前驱状态：  </label><input id="wf_evel_Precursor_select"  style="width:150px;">&nbsp; &nbsp; 
	    </div>
	    
	    <div region="center" border="false">
			<div class="easyui-panel" fit="true" border="false">
			  <iframe id="wf_evel_iframe_detailarea" width="100%" height="100%" framespacing="0" marginwidth="0" marginheight="0" frameborder="no" border="0"></iframe>
			</div>
	    </div>
	    
	    <div region="south" border="false" style="text-align:center;height:30px;line-height:30px;">
			<a class="easyui-linkbutton" icon="icon-save" src="javascript:void(0);" onclick="workflow_evelset_save();">保存</a>	
		</div>
	</div>
</div>
