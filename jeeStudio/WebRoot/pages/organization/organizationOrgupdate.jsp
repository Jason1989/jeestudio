<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>组织机构修改页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery.form.js"></script> 
   <script type="text/javascript">
    	$(function(){			
			function bdOrgaddFormSubmit(){
				$('#bd_organization_Org_update_form').form('submit',{ 
					url:'organization/organization!updateOrg.action?oid=${oid}',
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){
						if(data == 'success'){
							$.messager.alert('提示','修改成功！','info',function(){
								$('#orgl').tree('reload');
								//展开组织机构
								setTimeout(function(){
									$("#orgl").tree('expandAll');
								},500);
							});
						}else{
							$.messager.alert('提示','系统出错，请稍候再试！','warning');
						}
						parent.$('#bd_org_update').window('close');
					} 
				}); 			
			}
			$('#bd_dept_lead').appendTo("body").window({
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 480
            });        
			$('#bd_Orgupdate_FormSaveButton').bind('click',bdOrgaddFormSubmit);
			$('#lead_id').click(function(){
				   	 $("#bd_dept_lead").window({'href':''});
	  				 $("#bd_dept_lead").window('refresh');
	  				 $("#bd_dept_lead").window({'href':'organization/organization!toOrgLead.action?oid='+${org.oid}});			
	  				 $("#bd_dept_lead").window('open');
				   });
		});
   </script>
  </head>  
  <body>
    <br>
    <div>
	   <form id="bd_organization_Org_update_form" method="post">  
	   
	   		<input type="hidden" name="org.oid" value="${org.oid}">
	   		
	 		<table align="center" cellpadding="3">
	 		   <tr>
		 		  <!-- 
		 		  	   <td align="right">
		 		           部门主管：
		 		       </td>
		 		       <td>
		 		         <input type="text" name="org.lead" value="${org.lead }" readonly="readonly" id="lead_id" size="23" />&nbsp;&nbsp;
		 		       </td>
		 		  -->
		 		  <td align="right">部门编号：</td>
	 		      <td>
	 		        <input type="text" name="org.orgno" class="easyui-validatebox" size="23" cols="35" rows="5" validType="length[0,10]"/>&nbsp;&nbsp;
	 		      </td>
	 		      
	 		 	  <td align="right">部门名称：</td>
	 		      <td>
	 		      	 <input name="org.oname" class="easyui-validatebox" value="${org.oname }" required="true" invalidMessage="该部门名称已被占用！" validType="remote['organization/organization!isOrgExist.action?oupid=${orgupid}&oid=${org.oid}']" size="23"/>&nbsp;&nbsp;<font size="2" color="red">*</font>
	 		      </td>
	 		   </tr>
	 		   <tr>
	 		      <td align="right"> 
	 		      	电话：
	 		      </td>
	 		      <td>
	 		       <input type="text" name="org.tel" value="${org.tel }" class="easyui-validatebox" size="23" validType="mobile" />&nbsp;&nbsp;
	 		      </td>
	 		      <td align="right">
	 		   		 传真：
	 		      </td>
	 		      <td>
	 		      	<input type="text" name="org.fax" value="${org.fax }" class="easyui-numberbox" validType="number" size="23" />
	 		 	  </td>	
	 		   </tr>
	 		    
	 		   <tr>
	 		      <td align="right">
	 		   		  Email：
	 		      </td>
	 		      <td>
	 		      	<input type="text" name="org.oemail" value="${org.oemail }" class="easyui-validatebox" size="23" validType="email" />
	 		      </td>
	 		       <td align="right">
	 		   		 地址：
	 		      </td>
	 		      <td>
	 		      	<input type="text" name="org.onote" value="${org.onote }" size="23" validType="length[0,50]" />
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
	 		     
	 		   </tr>
	 		  
	 		   <tr>
	 		      <td align="right">
	 		      	 部门职能：
	 		      </td>
	 		      <td colspan="3" >
	 		        <textarea name="org.ofunction" class="easyui-validatebox" size="23" cols="52" rows="5" validType="length[0,200]">${org.ofunction }</textarea>&nbsp;&nbsp;
	 		      </td>
	 		   </tr>
	 		   
	 		</table>
			  <div >
			      <div align="center" style="width: 100%;height: 100%;padding-top: 20px;">
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id=bd_Orgupdate_FormSaveButton>保存</a>
 				    <a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_org_update').window('close');">关闭</a>
			       </div>
			 </div>
	    </form>
	 </div>
	 <div id="bd_dept_lead" title="主管列表"></div>
  </body>
</html>
