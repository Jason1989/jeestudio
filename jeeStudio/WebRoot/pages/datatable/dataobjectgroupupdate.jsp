<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
    <title>添加</title>    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script language="javascript" type="text/javascript">
		$(function(){
			function bdDataobjectgroupupdateFormSubmit(){
				var dataObjectGroupNameId=$("#dataObjectGroupid").val();
				var bd_dataobjectgroupupdate_pid=$("#bd_dataobjectgroupupdate_pid").combobox("getValue");
				if(dataObjectGroupNameId==bd_dataobjectgroupupdate_pid)
						$.messager.alert("提示","分组名称和上级分组不能选同一个，请进行修改！", 'warning');
				else{
					$('#bd_dataobjectgroupupdate_form').form('submit',{ 
						url:'datatable/dataObjectGroup!update.action', 
						onSubmit:function(){ 
							return $(this).form('validate'); 
						}, 
						success:function(data){ 
							if("exist" == data) {						
								$.messager.alert("提示","分组名称已存在！", 'warning');
							} else if("success" == data) {
								parent.$('#bd_dataobjectgroup_update').window('close');
								//刷新菜单
								refreshLeftMenu();
							}
						} 
					}); 
				}	
			}
			$('#bdDataobjectgroupupdateFormSaveButton').bind('click',bdDataobjectgroupupdateFormSubmit);
			$('#bd_dataobjectgroupupdate_pid').combotree({
				treeWidth:182,
			 	url:"datatable/dataObjectGroup!list.action"
			 	//onBeforeExpand:function(node){
			 	//alert(node.parent);
			 		//var tree = $('#bd_dataobjectgroup_pid').combotree();
			 		//tree['combotree']['defaults']['url'] = "datatable/dataObjectGroup!getAllItemByParentId.action?parentId="+node.id
			 		
			 		
			 	//}
			});
			var node = $("#lt1").tree("getSelected");
        	if(node && node != null){
	        	$.post("datatable/dataObjectGroup!getParentById.action",{"dataObjectGroupId":node.id},
	        		function(data){
        				//var groupObj = eval('(' + data + ')');
	        			if(data != '' && data != null){
		        			$('#bd_dataobjectgroupupdate_pid').combotree('setValue',data[0].id);
		        			alert(data[0].id);
		        		}else{
		        			$('#bd_dataobjectgroupupdate_pid').combotree('setValue','-1');
		        		}
	      			},'json'
	      		);
	        }
	        initDataobjectgroupUpdateData();
		});					
		function initDataobjectgroupUpdateData(){
			//$('#bd_dataobjectgroupupdate_pid').combotree('setValue',{id:'${dataObjectGroup.id}',text:'${dataObjectGroup.name}'});
		//	$('#bd_dataobjectgroupupdate_pid').combobox({
		//	    url:'datatable/dataObjectGroup!list.action',
		//	    valueField:'id',
		//	    textField:'name',
		//	    editable:false
		//	});
		}			
    </script>
  </head>  
  <body>
	<form id="bd_dataobjectgroupupdate_form" method="post" action="datatable/dataObjectGroup!update.action">
		<input id="dataObjectGroupid" name="dataObjectGroup.id" type="hidden" value="${dataObjectGroup.id}"/>
			
		<div class="pop_col_col" style="width: 370px;">
            <div class="pop_col_conc">
                <dl>
                  <dd>上级分组：</dd>
                  <dt><select id="bd_dataobjectgroupupdate_pid" name="dataObjectGroup.pid" style="width:170px;" required="true"></select></dt>
                </dl>
                <dl>
                  <dd>分组名称：</dd>
                  <dt><input  name="dataObjectGroup.name" class="easyui-validatebox" size="23" value="${dataObjectGroup.name}" required="true" />&nbsp;&nbsp;<font size="2" color="red">*</font></dt>
                </dl>
                <dl>
                  <dd>序号：</dd>
                  <dt><input  name="dataObjectGroup.sortNo" class="easyui-numberbox" size="23" value="${dataObjectGroup.sortNo}" validType="length[1,5]"/></dt>
                </dl>
            </div>
        </div>
        
         <div style="float:left;padding:60px 0 0 120px;">
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bdDataobjectgroupupdateFormSaveButton">保存</a>
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_dataobjectgroup_update').window('close');">关闭</a>
		 </div>
		
	</form>
  </body>
</html>
