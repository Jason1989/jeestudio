<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
	$(function (){
		 $("#uat_chooseFormWindow").window({
		      closed:true,
		      width:650,
		      height:450,
		      modal:true,
		      shadow: false,
		      cache:false,
		      minimizable:false,
		      title:'选择表单',
		      onClose:function(){
		      }
		 });

		var isMainTable_val = $('#updateacttab_isMainTable_hidden').val();
		if(isMainTable_val && isMainTable_val=='on'){
			$('#updateacttab_isMainTable_chenckbox').attr('checked',true);
		}else{
			$('#updateacttab_isMainTable_chenckbox').removeAttr('checked');
			$('#updateacttab_isMainTable_hidden').val('off');
		}

		$('#updateacttab_isMainTable_chenckbox').click(function(){
			var val = $('#updateacttab_isMainTable_chenckbox').attr('checked');
			if(val){
				$('#updateacttab_isMainTable_hidden').val('on');
			}else{
				$('#updateacttab_isMainTable_hidden').val('off');
			}
		});
	});

  function activitytabFormSet(){
 		$("#uat_chooseFormWindow").window({'href':''});		
       	$("#uat_chooseFormWindow").window('refresh');
        $("#uat_chooseFormWindow").window({'href':'common/jsp/easyui/workflow/choose_form_tree.jsp'});		
       	$("#uat_chooseFormWindow").window('open');
       }
</script>
<div    id="uat_chooseFormWindow"  ></div>

<div class="easyui-layout" fit="true">
	<div region="center"  border="false" style="padding:8px;background:#fff;border:1px solid #ccc;width:100%;height:365px;">
		<form  id="activityTabConfig"  method="post" >

			<input type="hidden" name="activityTabSettingVo.id"  value="${activityTabSettingVo.id}" />
			<input type="hidden" id="mid"  name="activityTabSettingVo.mid"  value="${activityTabSettingVo.mid}" />
			<input type="hidden" id="processId" name="activityTabSettingVo.processId"  value="${activityTabSettingVo.processId}" />
			<input type="hidden" id="activityId" name="activityTabSettingVo.activityId"  value="${activityTabSettingVo.activityId}" />
			<input type="hidden" id="toActivityId" name="activityTabSettingVo.toActivityId"  value="${activityTabSettingVo.toActivityId}" />
			
			<table>
				<tr>
					<td>标签名称：</td>
					<td><input type="text" class="easyui-validatebox" required=true  name="activityTabSettingVo.title"  value="${activityTabSettingVo.title}" />   </td>
				</tr>
				<tr>
					<td>标签URL：</td>
					<td><input id="activityTabSettingVoUrl"   name="activityTabSettingVo.url"  /><a href="javascript:void(0);" onclick="activitytabFormSet();return false;"  >选择</a>   </td>
				</tr>
				<tr>
					<td>标签序号：</td>
					<td><input type="text" class="easyui-validatebox"  required="true"  validType="positive_Integer"  name="activityTabSettingVo.sortIndex"  value="${activityTabSettingVo.sortIndex}"/>   </td>
				</tr>
				<tr>
					<td>是否为主表：</td>
					<td>
					  <input type="checkbox" id="updateacttab_isMainTable_chenckbox"/>
					  <input type="hidden" id="updateacttab_isMainTable_hidden" name="activityTabSettingVo.isMainTable" value="${activityTabSettingVo.isMainTable}"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div region="south" border="false" style="text-align:center;height:40px;padding: 4px;">
		<a  class="easyui-linkbutton" icon="icon-ok"  href="javascript:void(0);" onclick="saveActiviConfigTab();"  >保存</a>&nbsp;&nbsp;&nbsp;
		<a  class="easyui-linkbutton" icon="icon-cancel"  href="javascript:void(0);" onclick="$('#wf_tabConfig_activityPerm').window('close');" >关闭</a>
	</div>
</div>

<script>
	$(function (){
	
		var url ='acttab_findEditForm.action?date='+new Date().getTime();
	    //流程模板
	    $('#activityTabSettingVoUrl').combobox({
			url:url,
			valueField:'FO_ID',
			textField:'FO_NAME',
			panelHeight:100,
			panelWidth:200,
			width:200,
			onChange:function(newValue, oldValue){
				
			}
	    });
	    
	    $('#activityTabSettingVoUrl').combobox('setValue','${activityTabSettingVo.url}');
	});
</script>