<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>添加</title>
		<script type="text/javascript" src="js/jquery.form.js"></script>
		<script type="text/javascript">
    	$(function(){
			function bdOrguseraddFormSubmit(){
				$('#bd_organization_user_add_form').form('submit',{ 
					url:'organization/organization!addUser.action', 
					async:false,
					onSubmit:function(){
						if(vali()){
							return $(this).form('validate'); 
						}else{
							return false;
						}
					},
					success:function(data){ 
						if(data == "fail"){
							$.messager.alert('提示', '登录名已存在！', 'warning');
						}else if(data == "success"){	
							$.messager.alert('提示', '用户添加成功！', 'info');
						}
							parent.$('#bd_org_useradd').window('close');
							$('#bd_usertablelist_table').datagrid('reload');
					} 
				}); 			
			}
			$('#bdorg_useradd_button').bind('click',bdOrguseraddFormSubmit);
		});
		
		$('#bd_user_level_select_add').combobox({
			    url:'authority/userlevel!getAllLevelAdd.action?time='+new Date().getTime(),
			    valueField:'id',
			    textField:'levelname',
			    listHeight:200,
			    editable:false
		});
		function vali(){
		    var p1 =  $('#pwd1').val() ;
		    var p2 = $('#pwd2').val();
		    
			if(p1 != p2){
			$.messager.alert('提示', '密码不一致!', 'warning');
				return false ;
			}
			return true ;
		}
   </script>
	</head>
	<body>
		<form id="bd_organization_user_add_form" method="post">
			<div class="pop_col_col"
				style="padding-top: 10px; padding-left: 10px;">
				<div class="pop_col_conc">
					<dl>
						<dd>
							组织机构：
						</dd>
						<dt>
							<!-- 组织机构树 -->
							<input class="easyui-combotree"  url="organization/organization!getAllOrganizations.action?oid=1"  textField="text" valueField="id" style="width:140px;" name="organizationId" class="easyui-validatebox" readonly="readonly" required="true" value="${oid}" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							用户名：
						</dd>
						<dt>
							<input style="width:140px;"  name="usertable.username" class="easyui-validatebox" required="true" maxlength="20" validType="remote['organization/organization!isUserNameExist.action']" invalidMessage="该用户名已被占用！"/>&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							姓名：
						</dd>
						<dt>
							<input style="width:140px;" name="usertable.uname" class="easyui-validatebox"
								id="bd_user_name_choice" required="true" maxlength="20" />&nbsp;&nbsp;<font size="2" color="red">*</font>
							&nbsp;&nbsp;
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							密码：
						</dd>
						<dt>
							<input style="width:140px;" type="password" maxlength="20" name="usertable.userpassword" id="pwd1" class="easyui-validatebox" required="true" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							确认密码：
						</dd>
						<dt>
							<input style="width:140px;" type="password" name="userpasswords" maxlength="20" id="pwd2" class="easyui-validatebox" required="true" onblur="vali()" />&nbsp;&nbsp;<font size="2" color="red">*</font><span id="spn"></span>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							性别：
						</dd>
						<dt style="text-align: center;">
							<input type="radio" name="usertable.sex" value="0"
								checked="checked" />
							男 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="usertable.sex" value="1" />
							女
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							毕业院校：
						</dd>
						<dt>
							<input style="width:140px;" maxlength="50" name="usertable.byschool" type="text" />
							&nbsp;&nbsp;
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							专业：
						</dd>
						<dt>
							<input style="width:140px;" maxlength="40" name="usertable.subject" type="text" />
							&nbsp;&nbsp;
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							身份证号：
						</dd>
						<dt>
							<input style="width:140px;" name="usertable.id" type="text" maxlength="18"  required="true" 
								class="easyui-validatebox" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							电话：
						</dd>

						<dt>
							<input style="width:140px;" type="text" maxlength="18" name="usertable.phone"  
								 />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							Email：
						</dd>
						<dt>
							<input style="width:140px;" type="text" name="usertable.email" validType="email" maxlength="100" required="true" 
							value="guowxin@gmail.com"	class="easyui-validatebox" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							地址：
						</dd>
						<dt>
							<input style="width:140px;" maxlength="50" name="usertable.address" type="text" />
							&nbsp;&nbsp;
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							用户等级：
						</dd>
						<dt>
							<select id="bd_user_level_select_add"
								name="usertable.levelnumber" style="width: 140px;"
								required="true"></select>&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
			</div>
			<div class="pop_col_cons">
				<div align="center"
					style="width: 100%; height: 100%; padding-top: 20px;">
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-save"
						id="bdorg_useradd_button">保存</a>
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel"
						onclick="parent.$('#bd_org_useradd').window('close');">关闭</a>
				</div>
			</div>
		</form>
	</body>
</html>
