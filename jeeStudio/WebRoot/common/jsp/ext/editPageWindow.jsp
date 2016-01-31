<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<div id="editPageWindow_${listPage.editPage.id}"   style="padding:5px;background: #fafafa;"  >
	<div class="easyui-layout" fit="true">
		<div id="editColumn_${listPage.id}" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
				
		</div>
		<div region="south" border="false" style="text-align:center;height:30px;line-height:30px;">
			 <a  class="easyui-linkbutton" icon="icon-ok"  	   href="javascript:dynamicSaveData('editform_${listPage.editPage.id}','fd_formlist_table_${listPageRander}','editPageWindow_${listPage.editPage.id}','queryPage_${listPage.id}');" >保存</a>
			 <a  class="easyui-linkbutton" icon='icon-undo'    href="javascript:formReset('editform_${listPage.editPage.id}');"  >重置</a>
			 <a  class="easyui-linkbutton" icon="icon-cancel"  href="javascript:closeWindow('editPageWindow_${listPage.editPage.id}');"  >关闭</a>	
		</div>
	</div>
</div>