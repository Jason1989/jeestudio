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
			function bdDataobjectgroupaddFormSubmit(){
				$('#bd_dataobjectgroupadd_form').form('submit',{ 
					url:'datatable/dataObjectGroup!add.action', 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
						if("exist" == data) {						
							$.messager.alert("提示","分组名称已存在！", 'warning');
						} else if("success" == data) {
							parent.$('#bd_dataobjectgroup_add').window('close');
							//刷新菜单							
							refreshLeftMenu();
						}
					} 
				}); 
			}
			$('#bdDataobjectgroupaddFormSaveButton').bind('click',bdDataobjectgroupaddFormSubmit);
			
			$('#bd_dataobjectgroup_pid').combotree({
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
		        			$('#bd_dataobjectgroup_pid').combotree('setValue',data[0].id);
		        		}else{
		        			$('#bd_dataobjectgroup_pid').combotree('setValue','');
		        		}
	      			},'json'
	      		);
	        }
	        			
      		initDataobjectgroupAddData();
		});					
		function initDataobjectgroupAddData(){
		//	$('#bd_dataobjectgroup_pid').combobox({
		//	  	url:'datatable/dataObjectGroup!list.action',
		//	    valueField:'id',
		//	   	textField:'text',
		 //   	editable:false
		//	});
		}		
		
    </script>
  </head>  
  <body style="border:1px solid red;">
	<form id="bd_dataobjectgroupadd_form" method="post" action="datatable/dataObjectGroup!add.action" style="padding:0;margin: 0;">
		<div class="pop_col_col" style="width: 370px;">
            <div class="pop_col_conc" >
                <dl>
                  <dd>上级分组：</dd>
                  <dt><select id="bd_dataobjectgroup_pid" name="dataObjectGroup.pid" style="width:170px;" required="true"></select></dt>
                </dl>
               
                 <dl>
                  <dd>分组名称：</dd>
                  <dt><input name="dataObjectGroup.name" class="easyui-validatebox" size="23" validType="remote['datatable/dataObjectGroup!nameExist.action']" required="true" />&nbsp;&nbsp;<font size="2" color="red">*</font></dt>
                 </dl>
                <dl>
                  <dd>序号：</dd>
                  <dt><input name="dataObjectGroup.sortNo" class="easyui-validatebox" size="23" validType="sortlength[1,6]"/></dt>
                </dl>
            </div>
        </div>
            <div style="float:left;padding:60px 0 0 120px;">
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-save" id="bdDataobjectgroupaddFormSaveButton">保存</a>
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#bd_dataobjectgroup_add').window('close');">关闭</a>
		    </div>
	</form>
  </body>
</html>
