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
    <title>修改用户信息</title>   
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
			$('#userupdate_SaveButton').bind('click',updateUserSubmit);
    		//保存表单的方法
    		function updateUserSubmit(){
				$('#user_update_form').form('submit',{ 
					url:'organization/organization!updateOrgUser.action?oid=${oid}', 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
						if(data = 'success'){
							$.messager.alert('提示','修改成功！','info');
							parent.$('#updateUserMessage').window('close');
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
		
		//验证原密码是否输入正确(密码是加密的)
		function valideOldPwd(){
				var pwd = $('#up0').val();
				if(pwd!=""){
					$.post(
						'organization/organization!checkOldPwd.action', 
						{
							pwd: pwd,
							pwdOld: $('#up00').val()
						},
						function(data){
							if(data=="fail"){
								$.messager.alert('提示', '原密码输入不正确!', 'warning');
								return false;
							}else{
								return true;
							}
						});
				}
		}
		
   </script>
  </head>  
  <body>
	<form id="user_update_form" method="post">  
		<input style="width:140px;" type="hidden" name="usertable.userid" value="${usertable.userid}">
	   <div class="pop_col_col" style="padding-top: 10px;padding-left: 10px;">
				
				<input  type="hidden" name="usertable.uname" value="${usertable.uname }" />
				<input  type="hidden" name="usertable.sex" value="${usertable.sex }" />
				<input  type="hidden" name="usertable.byschool" value="${usertable.byschool }" />
				<input  type="hidden" name="usertable.subject" value="${usertable.subject }" />
				<input  type="hidden" name="usertable.id" value="${usertable.id}" />
				<input  type="hidden" name="usertable.phone" value="${usertable.phone}" />
				<input  type="hidden" name="usertable.email" value="${usertable.email}" />
				<input  type="hidden" name="usertable.address" value="${usertable.address}" />
				<input  type="hidden" name="usertable.levelnumber" value="${usertable.levelnumber}" />
			 
				<div class="pop_col_conc">
					<dl>
						<dd>
							用户名：
						</dd>
						<dt>
							<input style="width:140px;" name="usertable.username" class="easyui-validatebox" readonly="readonly" required="true" value="${usertable.username }" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>	
				<!-- 
				<div class="pop_col_conc">
					<dl>
						<dd>
							 姓名：
						</dd>
						<dt>
							<input style="width:140px;" name="usertable.uname" class="easyui-validatebox" value="${usertable.uname }"/>&nbsp;&nbsp;
						</dt>
					</dl>
				</div>-->
				<div class="pop_col_conc">
					<dl>
						<dd>
							 原密码：
						</dd>
						<dt>
						 <input style="width:140px;" type="password" id="up0" class="easyui-validatebox" value="" required="true" onblur="valideOldPwd()"/>&nbsp;&nbsp;<font size="2" color="red">*</font>
						 <input type="hidden" id="up00" value="${usertable.userpassword}" />
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							 新密码：
						</dd>
						<dt>
						 <input style="width:140px;" type="password" name="usertable.userpassword" id="up1" class="easyui-validatebox" value="" required="true" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							确认密码：
						</dd>
						<dt>
						 <input style="width:140px;" type="password" name="userpasswords" id="up2" class="easyui-validatebox" value="" required="true" onblur="valide()"  />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<!--  
				<div class="pop_col_conc">
					<dl>
						<dd>
							性别：
						</dd>
						<dt style="text-align: left;">
							<c:if test="${usertable.sex=='0' }">
							   <input type="radio" name="usertable.sex" value="0" class="radio" checked="checked" style="border:none;"/> 男  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							   <input type="radio" name="usertable.sex" value="1" class="radio" style="border:none;"/> 女
							</c:if>
							<c:if test="${usertable.sex=='1' }">
							   <input type="radio" name="usertable.sex" value="0" class="radio" style="border:none;"/> 男  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							   <input type="radio" name="usertable.sex" value="1" class="radio" checked="checked"  style="border:none;"/> 女
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
							<input style="width:140px;" name="usertable.byschool" type="text" value="${usertable.byschool }" />&nbsp;&nbsp;
						</dt>
					</dl>
				</div>
			  <div class="pop_col_conc">
			  		<dl>
						<dd>
							 专业：
						</dd>
						<dt>
							<input style="width:140px;" name="usertable.subject" type="text"  value="${usertable.subject }"/>&nbsp;&nbsp;
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							身份证号：
						</dd>
						<dt>
						   <input style="width:140px;" name="usertable.id" type="text"class="easyui-validatebox" value="${usertable.id }"   required="true" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
			</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							 电话：   
						</dd>
						
						<dt>
							<input style="width:140px;" type="text" name="usertable.phone" class="easyui-validatebox"  value="${usertable.phone }"  required="true" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							Email：
						</dd>
						<dt>
						 <input style="width:140px;" type="text" name="usertable.email" value="${usertable.email }"  class="easyui-validatebox" validType="email" required="true" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>	
			<div class="pop_col_conc">
					<dl>
						<dd>
							地址：
						</dd>
						<dt>
							<input style="width:140px;" name="usertable.address" type="text" value="${usertable.address }" />&nbsp;&nbsp;
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
				-->
		</div>
		<div class="pop_col_cons">
			       <div align="center" style="width: 100%;height: 100%;padding-top: 20px;">
						<a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-save" id="userupdate_SaveButton">保存</a>&nbsp;&nbsp;&nbsp;
	 				    <a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#updateUserMessage').window('close');">关闭</a>
			      </div>
		</div>
	</form>
  </body>
</html>
