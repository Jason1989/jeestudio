<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
    <title>添加</title>   
    <script type="text/javascript" src="js/jquery.form.js"></script> 
   <script type="text/javascript">
    	$(function(){
			$('#bd_user_level_select_update').combobox({
				    url:'authority/userlevel!getAllLevel.action?time='+new Date().getTime(),
				    valueField:'id',
				    textField:'levelname',
				    listHeight:200,
				    editable:false,
				    panelHeight:'auto'
			});
			
    		//初始化性别的值
    		$('#bd_organization_user_update_form input style="width:140px;"[name=usertable.sex]').each(function(){
    			if($(this).val()==${usertable.sex}){
    				$(this).attr('checked',true);
    			}
    		});
			  		
    		//点击保存按钮时的方法
			$('#bdorg_userupdate_FormSaveButton').bind('click',updateUserSubmit);
    		//保存表单的方法
    		function updateUserSubmit(){
				$('#bd_organization_user_update_form').form('submit',{ 
					url:'organization/organization!updateOrgUser.action?oid=${oid}', 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
						if(data = 'success'){
							$.messager.alert('提示','修改成功！','info');
							parent.$('#bd_org_userupdate').window('close');
							$('#bd_usertablelist_table').datagrid('reload');
						}else{
							$.messager.alert('提示','系统出错，请稍候再试……','warning');
						}
					} 
				}); 			
			};
    		
    		
			$("#bd_user_level_select_update").combobox("setValue","${usertable.levelnumber}");
			
		});
		
		function valide(){
		    var up1 =  $('#up1').val() ;
		    var up2 = $('#up2').val();
		    
			if(up1 != up2){
			$.messager.alert('提示', '密码不一致!', 'warning');
				return false ;
			}
			return true ;
		}
   </script>
  </head>  
  <body>
	<form id="bd_organization_user_update_form" method="post">  
		<input style="width:140px;" type="hidden" name="usertable.userid" value="${usertable.userid}">
	   <div class="pop_col_col" style="padding-top: 10px;padding-left: 10px;">
				<div class="pop_col_conc">
					<dl>
						<dd>
							组织机构：
						</dd>
						<dt>
							<!-- 组织机构树 -->
							<input class="easyui-combotree"  url="organization/organization!getAllOrganizations.action?oid=1"  textField="text" valueField="id" style="width:140px;" name="organizationId" class="easyui-validatebox" readonly="readonly" required="true" value="${organizationID}" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							用户名：
						</dd>
						<dt>
							<input style="width:140px;" name="usertable.username" maxlength="20" class="easyui-validatebox"  required="true" value="${usertable.username }" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							 姓名：
						</dd>
						<dt>
							<input style="width:140px;" name="usertable.uname" maxlength="20" class="easyui-validatebox" value="${usertable.uname }"/>&nbsp;&nbsp;
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							 密码：
						</dd>
						<dt>
						 <input style="width:140px;" type="password" maxlength="20" name="usertable.userpassword" id="up1" class="easyui-validatebox" value="${usertable.userpassword }" required="true" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							确认密码：
						</dd>
						<dt>
						 <input style="width:140px;" type="password" maxlength="20" name="userpasswords" id="up2" class="easyui-validatebox" value="${usertable.userpassword }" required="true" onblur="valide()"  />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							性别：
						</dd>
						<dt style="text-align: left">
							<c:if test="${usertable.sex=='0' }">
							   <input type="radio" name="usertable.sex" value="0" class="radio" checked="checked" style="border:none;"/> 男  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							   <input type="radio" name="usertable.sex" value="1" class="radio" style="border:none;"/> 女
							</c:if>
							<c:if test="${usertable.sex=='1' }">
							   <input type="radio" name="usertable.sex" value="0" class="radio" style="border:none;"/> 男  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							   <input type="radio" name="usertable.sex" value="1" class="radio" checked="checked" style="border:none;"/> 女
							</c:if>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							毕业院校：
						</dd>
						<dt>
							<input style="width:140px;" maxlength="50" name="usertable.byschool" type="text" value="${usertable.byschool }" />&nbsp;&nbsp;
						</dt>
					</dl>
				</div>
			  <div class="pop_col_conc">
			  		<dl>
						<dd>
							 专业：
						</dd>
						<dt>
							<input style="width:140px;" maxlength="40" name="usertable.subject" type="text"  value="${usertable.subject }"/>&nbsp;&nbsp;
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							身份证号：
						</dd>
						<dt>
						   <input style="width:140px;" maxlength="18" name="usertable.id" type="text"class="easyui-validatebox" value="${usertable.id }"   required="true" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
			</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							 电话：   
						</dd>
						
						<dt>
							<input style="width:140px;" maxlength="18" type="text" name="usertable.phone" class="easyui-validatebox"  value="${usertable.phone }"  required="true" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							Email：
						</dd>
						<dt>
						 <input style="width:140px;"  maxlength="100" type="text" name="usertable.email" value="${usertable.email }"  class="easyui-validatebox" validType="email" required="true" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>	
			<div class="pop_col_conc">
					<dl>
						<dd>
							地址：
						</dd>
						<dt>
							<input style="width:140px;" maxlength="50" name="usertable.address" type="text" value="${usertable.address }" />&nbsp;&nbsp;
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							用户等级：
						</dd>
						<dt>
							<select id="bd_user_level_select_update"  name="usertable.levelnumber" style="width:140px;" required="true"></select>&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>	
		</div>
		<div class="pop_col_cons">
			       <div align="center" style="width: 100%;height: 100%;padding-top: 20px;">
						<a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-save" id="bdorg_userupdate_FormSaveButton">保存</a>
	 				    <a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_org_userupdate').window('close');">关闭</a>
			      </div>
		</div>
	</form>
  </body>
</html>
