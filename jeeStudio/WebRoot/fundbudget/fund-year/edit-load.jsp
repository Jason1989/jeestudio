<%@page language="java" contentType="text/html; charset=UTF-8" %>
<div class="easyui-layout" fit="true">
    <div id="editColumn_${listPageRander}" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;height: 365px;">
		<jsp:include page="/common/jsp/easyui/edit-load.jsp"></jsp:include>
	</div>
	<div region="south" border="false" style="text-align:center;height:55px;padding: 13px;">
		 <a   icon='icon-temp'   class="easyui-linkbutton"  href="javascript:dynamicSaveData('editform_${editPage.id}','fd_formlist_table_${listPageRander}','editPageWindow_${listPageRander}','queryPage_${listPage.id}');" >暂存</a>
		 <a   icon='icon-ok' 	 class="easyui-linkbutton"  href="javascript:changeFundYearState('BUDGET_STATE','1');dynamicSaveData('editform_${editPage.id}','fd_formlist_table_${listPageRander}','editPageWindow_${listPageRander}','queryPage_${listPage.id}');" >提交</a>		
		 <a   icon='icon-undo'   class="easyui-linkbutton"  href="javascript:formReset('editform_${editPage.id}');"  >重置</a>
		 <a   icon="icon-cancel" class="easyui-linkbutton"  href="javascript:closeWindow('${editPage.tabWindowID}');"  >关闭</a>	
	</div>
</div>


