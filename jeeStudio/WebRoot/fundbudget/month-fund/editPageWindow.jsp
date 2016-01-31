<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.zxt.compplatform.formengine.constant.Constant"%>
<div id="editPageWindow_${listPageRander}"   style="padding:5px;background: #fafafa;"  >
	<div class="easyui-layout" fit="true">
		<div id="editColumn_${listPageRander}" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;height: 365px;">
				
		</div>
		<div region="south" border="false" style="text-align:center;height:55px;padding: 13px;">
			 <!-- 判断添加页是否启动工作流  并加入日志 -->
			<%
				if(request.getAttribute("isAbleWorkFlow")!=null){
					if(Constant.WORKFLOW_ENABLE.equals(request.getAttribute("isAbleWorkFlow").toString())){
			%>
						<a   icon='icon-ok' id="editPageWindow_${listPageRander}_tran_ok" href="javascript:dynamicSaveData('editform_${listPage.editPage.id}','fd_formlist_table_${listPageRander}','editPageWindow_${listPageRander}','queryPage_${listPage.id}','transave');submit_workflow_log('editform_${listPage.editPage.id}')" >暂存</a>						
						<a   icon="icon-ok" id="editPageWindow_${listPageRander}_ok" href="javascript:dynamicSaveData('editform_${listPage.editPage.id}','fd_formlist_table_${listPageRander}','editPageWindow_${listPageRander}','queryPage_${listPage.id}','save');submit_workflow_log('editform_${listPage.editPage.id}')" >保存</a>
			<%		
					}else{
			%>
						<a   icon="icon-ok" id="editPageWindow_${listPageRander}_ok"  href="javascript:dynamicSaveData('editform_${listPage.editPage.id}','fd_formlist_table_${listPageRander}','editPageWindow_${listPageRander}','queryPage_${listPage.id}');" >保存</a>
			<%		
					}	
				}else{
			%>
					<a   icon="icon-ok" id="editPageWindow_${listPageRander}_ok"  href="javascript:dynamicSaveData('editform_${listPage.editPage.id}','fd_formlist_table_${listPageRander}','editPageWindow_${listPageRander}','queryPage_${listPage.id}');" >保存</a>
			<%		
				}
			%>
			 
			 <a   icon='icon-undo'   id="editPageWindow_${listPageRander}_undo"     href="javascript:formReset('editform_${listPage.editPage.id}');"  >重置</a>
			 <a   icon="icon-cancel" id="editPageWindow_${listPageRander}_cancel"   href="javascript:closeWindow('editPageWindow_${listPageRander}');"  >关闭</a>	
		</div>
	</div>
</div>
<script>
	$(function (){
		$('#editPageWindow_${listPageRander}_ok').linkbutton({text:'保存'});
		$('#editPageWindow_${listPageRander}_tran_ok').linkbutton({text:'暂存'});
		$('#editPageWindow_${listPageRander}_undo').linkbutton({text:'重置'});
		$('#editPageWindow_${listPageRander}_cancel').linkbutton({text:'关闭'});
	});
</script>