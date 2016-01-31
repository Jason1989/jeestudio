<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script language="javascript" type="text/javascript">	
function templatefile_save(){
	$('#templatefile_list_add_form').form('submit',{ 
		url:'templatefile/templatefile!save.action', 
		onSubmit:function(){
			var flg = $(this).form('validate');
			if($('#templatefile_save_file').val()==null){
				$.messager.alert('提示','请选择模板文件！','info');
				return false;
			}
			return flg;
		}, 
		success:function(data){
			if('exists'==data){
				$.messager.alert('提示','模板标题已存在！','info');
				return;
			}else if ("success" == data) {
				$.messager.alert('提示','保存成功！','info');							
				$('#bd_form_templatefile_list_add').window('close');
				$('#bd_form_templatefile_list_table').datagrid('reload');							
			}else{
				$.messager.alert('提示','保存失败！','info');
			}
		}
	});
}

function templatefile_save_closeThis(){
	$.messager.confirm('提示','确定不保存退出？',function(r){
		if(r){
			$('#bd_form_templatefile_list_add').window('close');
		}
	});
}
</script>
<div class="easyui-layout" fit="true" border="false">
	<div region="center" border="false" style="padding:7px 0 0 12px;">
		<form id="templatefile_list_add_form" method="post"
			enctype="multipart/form-data">
			<div class="pop_col_color">
				<div class="pop_col_cons">
					<dl>
						<dd style="line-height: 22px">
							<span>模板名称：</span>
						</dd>
						<dt>
							<input type="hidden" name="formTemplateFile.dgId" value="${param.id}"/>
							<input name="formTemplateFile.name" value=""
								class="easyui-validatebox" validType="length[1,20]"
								required="true" type="text" size="17" maxlength="20" />
							&nbsp;
							<font color=red>*</font>
						</dt>
					</dl>
					<dl>
						<dd style="line-height: 53px">
							<span>模板描述：</span>
						</dd>
						<dt style="line-height: 53px">
							<textarea name="formTemplateFile.remark"
								class="easyui-validatebox" rows="3" cols="50"
								validType="length[0,255]">${depFileData.depfileContent}</textarea>
						</dt>
					</dl>
					<dl>
						<dd style="line-height: 22px">
							<span>模板文件：</span>
						</dd>
						<dt>
							<input id="templatefile_save_file"
								type="file" name="file" />
							&nbsp;
							<font color=red>*</font>
						</dt>
					</dl>
				</div>
			</div>
		</form>
	</div>
	<div region="south" style="height: 38px; padding: 0px 0 0 13;"
		border="false" align="center">
		<a class="easyui-linkbutton" href="javascript:void(0)"
			icon="icon-save" onclick="javascript:templatefile_save();">保存</a>
		<a class="easyui-linkbutton" href="javascript:void(0)"
			icon="icon-cancel" onclick="javascript:templatefile_save_closeThis();">关闭</a>
	</div>
</div>