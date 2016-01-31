<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>组织机构添加页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	 
   <script type="text/javascript">
   		$('#orgreplication').window({
   						title:'复制组织结构', 
						width: 450,
						height: 400,
						modal: true,
						shadow: false,
						closed: true,
						maximizable:false,
						minimizable:false
						
   		})
   		function openReplOrg(){
					$('#orgreplication').window('refresh');
					$('#orgreplication').window({href:'pages/organization/organization-replication.jsp'});
					$('#orgreplication').window('open');
   		}
    	$(function(){			
			function bdOrgaddFormSubmit(){
				$('#bd_organization_Org_add_form').form('submit',{ 
					url:'organization/organization!saveAddOrg.action?oid=${oid}', 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
					       if(data == "fail"){
					            $.messager.alert('提示',"系统出错，请稍候再试……！",'warning');
					       }else if (data == 'exist'){
				            	$.messager.alert('提示',"该部门已存在！",'warning');
					       }else{
								$('#bd_org_add').window('close');
					       		$.messager.alert('提示',"部门添加成功！",'info',function(){
									$('#orgl').tree('reload');
									//展开组织机构
									setTimeout(function(){
										$("#orgl").tree('expandAll');
									},500);
					       		});
					       }
					} 
				}); 			
			};
			$('#bd_orgaddFormSaveButton').bind('click',bdOrgaddFormSubmit);
		});
   </script>
  </head>  
  <body>
    <br>
    <div>
	   <form id="bd_organization_Org_add_form" method="post">  
	 		<table align="center" cellpadding="3">
	 		  <tr>
	 		   	  <td align="right">
	 		      	部门名称：
	 		      </td>
	 		      <td>
	 		      	<input name="org.oname" class="easyui-validatebox" required="true" validType="maxlength_isExist['organization/organization!isOrgExist.action?oupid=${oid}',15]"  size="23"  />&nbsp;&nbsp;<font size="2" color="red">*</font>
	 		      </td>
	 		      <td align="right">
	 		      	复制组织机构：
	 		      </td>
	 		      <td>
	 		      	<input id="repliorgtext" name="repliorgtext" class="easyui-validatebox"  type="text" size="23" onclick="javascript:openReplOrg();"/>
	 		      	<input id="repliorgno" name="repliorgno" type="hidden">
	 		      </td>
	 		   </tr>
	 		   <tr>
	 		       <td align="right">
	 		      	电话：
	 		      </td>
	 		      <td>
	 		       <input type="text" name="org.tel" class="easyui-validatebox" validType="mobile" size="23" />&nbsp;&nbsp;
	 		      </td>
	 		        <td align="right">
	 		   		传真：
	 		      </td>
	 		      <td>
	 		      <input type="text" name="org.fax" class="easyui-numberbox" validType="number" size="23"/>
					
	 		      </td>
	 		   </tr>
	 		  <tr>
	 		      <td align="right">
	 		   		  Email：
	 		      </td>
	 		      <td>
	 		      	<input type="text" name="org.oemail" class="easyui-validatebox" validType="email" size="23"  />
	 		      </td>
	 		       <td align="right">
	 		   		 地址：
	 		      </td>
	 		      <td>
	 		      	<input type="text" name="org.onote" size="23" validType="length[0,50]"/>
	 		      </td>
	 		   </tr>
	 		  <tr>
	 		      <td align="right">
	 		      	 部门类型：
	 		      </td>
	 		      <td>
	 		        <!-- 
	 		        	<input type="text" name="" class="easyui-validatebox" size="23" cols="35" rows="5"/>&nbsp;&nbsp;
	 		         -->
	 		        <select id="" name="org.orgcate" class="easyui-combobox" style="width: 162px;" >
	 		        	<option value="0" selected="selected">----请选择----</option>
	 		       		<option value="1">集团公司</option>
	 		       		<option value="2">区域公司</option>
	 		       		<option value="3">分公司</option>
	 		       		<option value="4">项目部</option>
	 		        </select>
	 		      </td>
	 		       <td align="right">
	 		      	 部门编号：
	 		      </td>
	 		      <td>
	 		        <input type="text" name="org.orgno" class="easyui-validatebox" size="23" cols="35" rows="5" validType="length[0,10]" />&nbsp;&nbsp;
	 		      </td>
	 		   </tr>
	 		    <tr>
	 		      <td align="right">
	 		      	 部门职能：
	 		      </td>
	 		      <td colspan="3"  >
	 		        <textarea type="text" name="org.ofunction" class="easyui-validatebox" size="23" cols="52" rows="5" validType="length[0,200]"></textarea>&nbsp;&nbsp;
	 		      </td>
	 		   </tr>
	 		</table>
			  <div >
			      <div align="center" style="width: 100%;height: 100%;padding-top: 20px;">
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bd_orgaddFormSaveButton">保存</a>
 				    <a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_org_add').window('close');">关闭</a>
			      </div>
			 </div>
	    </form>
	 </div>
	 <div id="orgreplication"></div>
  </body>
</html>
