<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.zxt.compplatform.formengine.constant.Constant"%>
<%@ page trimDirectiveWhitespaces="true" %>
<div id="copyPageWindow_${listPageRander}"   style="padding:5px;background: #fafafa;"  >
	<div class="easyui-layout" fit="true">
		<div id="copyColumn_${listPageRander}" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;height: 365px;">
      
		</div>
		<div region="south" border="false" style="text-align:center;height:45px;padding-top:13px;">
			 <!-- 判断添加页是否启动工作流  并加入日志 -->
			 <a   icon="icon-ok" id="copyPageWindow_${listPageRander}_ok"  href="javascript:dynamicSaveData('copyform_${copyPage.editPage.id}','fd_formlist_table_${listPageRander}','copyPageWindow_${listPageRander}','queryPage_${listPage.id}');" >保存</a>&nbsp;&nbsp;
			 <a   icon='icon-undo'   id="copyPageWindow_${listPageRander}_undo"     href="javascript:formReset('copyform_${copyPage.editPage.id}');"  >重填</a>&nbsp;&nbsp;
			 <a   icon="icon-cancel" id="copyPageWindow_${listPageRander}_cancel"   href="javascript:closeWindow('copyPageWindow_${listPageRander}');"  >关闭</a>	
		</div>
	</div>
</div>
<script>
	$(function (){
		$('#copyPageWindow_${listPageRander}_ok').linkbutton({text:'保存'});
		$('#copyPageWindow_${listPageRander}_tran_ok').linkbutton({text:'暂存'});
		$('#copyPageWindow_${listPageRander}_undo').linkbutton({text:'重填'});
		$('#copyPageWindow_${listPageRander}_cancel').linkbutton({text:'关闭'});
	});
</script>