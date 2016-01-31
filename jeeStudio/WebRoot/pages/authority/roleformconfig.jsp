<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>角色</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <script type="text/javascript">
	    $(function(){	  	
			var formTypeData = [{
					"id":"listPage",
					"text":"列表页"
				}];
			$('#roleformurlconfig_formtype').tree({
				data:formTypeData,
				onLoadSuccess:function(node,data){
					if(data){
						for(var k=0;k<data.length;k++){
							var formNode = $('#roleformurlconfig_formtype').tree('find',data[k].id);
							if(formNode){
								$('#roleformurlconfig_formtype').tree('select',formNode.target);
								initAuthorizedFormUrlData(data[k].id);
								break;
							}
						}
					}
				},
				onClick:function(node){
					//alert(node.id);
					initAuthorizedFormUrlData(node.id);					
				}
			});
			/**var formTypeId = "";
			$('#roleformurlconfig_form').tree({
				checkbox: true,
				cascadeCheck:false,
				url: 'authority/authority!getAllFormByType.action?formTypeId='+formTypeId
			});*/
		});	
		function initAuthorizedFormUrlData(formTypeId){
			
			$.post("authority/authority!getAuthorizedFormIds.action",{"roleId":'${roleId}',"formTypeId":formTypeId},
				function(resultData){							
					$('#roleformurlconfig_form').tree({
						checkbox: true,
						cascadeCheck:false,
						url: 'authority/authority!getAllFormByType.action?formTypeId='+formTypeId,
						onLoadSuccess:function(node,data){
							if(resultData && resultData != null){										
								$('#roleformurlconfig_form').tree("expandAll");					
								for(var i=0;i<resultData.length;i++){
									var menuNode = $('#roleformurlconfig_form').tree('find',resultData[i].id);
									if(menuNode){
										$('#roleformurlconfig_form').tree('check',menuNode.target);
									}
								}
							}
						}
					});
				}
			);
		}
		
	   	function roleFormUrlConfigSaveCallBack(data){
       		if("success" == data){
       			$.messager.alert('提示','操作成功!','info');
       		}	       
	   	}
	   	function roleFormUrlConfigSave(){
	   		var formIds = getFormIds("roleformurlconfig_form");
		    if(formIds != ""){        		
	         	$.post("authority/authority!roleFormUrlConfigSave.action",{"roleId":'${roleId}',"formIds":formIds},roleFormUrlConfigSaveCallBack);
        	}
	   		
	   	}
	   	function getFormIds(form_id){
			var nodes = $('#'+form_id).tree('getChecked');
			var formIds = '';
			for(var i=0; i<nodes.length; i++){
		   		formIds += formIds == ''?nodes[i].id:","+nodes[i].id;
		    }
		    return formIds;
	   	}

    </script>
  </head>
  
  <body>	
	<table width="100%" border="0" cellpadding="1" cellspacing="0" class="table_form1" height="340px;">		
		 <tr>
			<td width="40%">表单类型</td>
			<td width="5%"></td>
			<td>表单</td>
		</tr>
		<tr>
			<td style="background:#dedede;"><table border="0" cellpadding="0" cellspacing="0" width="100%" style="background:#ffffff;" height="340px;">					
					<tr valign="top"><td><ul id="roleformurlconfig_formtype"></ul></td>
					</tr>
				</table></td>
			<td></td>
			<td style="background:#dedede;"><table border="0" cellpadding="0" cellspacing="0" width="100%" style="background:#ffffff;" height="340px;">					
					<tr valign="top"><td><div style="height:340px;overflow:auto;">
					<ul id="roleformurlconfig_form"></ul></div></td>
					</tr>					
				</table></td>
		</tr> 		
		<tr>
		<td colspan="3" align="center"><br/>
			<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" onclick="roleFormUrlConfigSave()">保存</a>
			<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#role_formurl_configwindow').window('close');">关闭</a></td>
		</tr>
	</table>
  </body>
</html>
