<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>验证规则管理</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script>
		$(function(){
            $('#bd_validationrulelist_table').datagrid({
                iconCls: 'icon-save',
                //width:1000,
                height: 400,
                nowrap: false,
                striped: true,
                autoFit: true,
                url: 'validationrule/validationRule!list.action',
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'id',
                fitColumns:true,
                pageSize:20,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'name',title: '名称',width: 150,sortable: true},
                	{field: 'expression',title: '表达式',width: 350},
                	{field: 'reminder',title: '提示信息',width: 300},                	
                	//{field: 'state',title: '状态',width: 80,sortable: true,align:'center',
                	//	formatter:function(value){
					//		if ('1' == value) return '可用';
					//		return '不可用';
					//	}},
                	{field: 'operate',title: '操作',width: 100,align:'center',
	                    formatter:function(value,rec){
						    var links = '<a href="javascript:;" onclick=bdValidationRuleUpdate("' + rec.id + '")>修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
						    links += '<a href="javascript:;" onclick=bdValidationRuleDelete("' + rec.id + '")>删除</a>';
							return links;
						}
					}
				]],
                pagination: true,
                rownumbers: true,
                //singleSelect: true,
                toolbar: ['-', 
		  			{text: '添加',iconCls: 'icon-add',handler: bdValidationRuleAdd},
		  			'-', 
		  			{text: '删除',iconCls: 'icon-remove',handler: bdValidationRuleDelete}, 
		  			'-' ]
            });
            $('#bd_validationrule_add').appendTo('body').window({
                title: "添加验证规则",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 350
            });
            $('#bd_validationrule_update').appendTo('body').window({
                title: "修改验证规则",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 350
            });
            
            function bdValidationRuleAdd(){
            	$.post("validationrule/validationRule!toAdd.action",{},
            		function(data){
		          		$("#bd_validationrule_add").html(data);
		          		$("#bd_validationrule_add").window('open');
          			}
          		);
            }           
        });
        function bdValidationRuleDelete(validationRuleId){
        	if(validationRuleId && validationRuleId != ""){
        		$.messager.confirm('提示', '确认删除吗？',function(a){
	         		if(a)$.post("validationrule/validationRule!delete.action",{"validationRuleId":validationRuleId},bdValidationRuleDeleteRollback);
	         	});
        	}else{
         		var selected = $('#bd_validationrulelist_table').datagrid('getSelections');
	            if (selected == null || selected.length < 1) {
	                $.messager.alert('提示', '请选择数据!', 'warning');
	            } else {
	            	$.messager.confirm('提示', '确认删除吗？',function(a){
	            		if(a){
		            		var validationruleIds = "";
		            		for(var i=0;i<selected.length;i++){
		            			if(validationruleIds == ""){
		            				validationruleIds += "'" + selected[i]['id'] + "'"
		            			}else{
		            				validationruleIds += ",'" + selected[i]['id'] + "'"
		            			}
		                	}
		                	$.post("validationrule/validationRule!delete.action",{"validationRuleIds":validationruleIds},bdValidationRuleDeleteRollback);
	                	}
	            	});	               
	            }
            }
        }
        function bdValidationRuleDeleteRollback(data){
        	if("success" == data){
   				$.messager.alert('提示','删除成功','info');			         				
   				$('#bd_validationrulelist_table').datagrid('reload');
   				refreshValidationRuleSession();
   			}
        }
        function bdValidationRuleUpdate(validationruleId){
        	$.post("validationrule/validationRule!toUpdate.action",{"validationRuleId":validationruleId},
        		function(data){
	        		$("#bd_validationrule_update").html(data);
	        		$("#bd_validationrule_update").window('open');
	        		initValidationRuleEditData();
      			}
      		);
        }
        function refreshValidationRuleSession(){
        	$.post("form/form!refreshSession.action",{},
				function(data){	
					if(window.opener)
						window.opener.refreshSessionData(data);	
									
				}
			);
		}
    </script>
  </head>  
  <body>
  	<div id="bd_validationrulelist_panel" title="验证规则列表" class="easyui-panel" fit="true" border="false" collapsible="false" class="main_panel">
		<table id="bd_validationrulelist_table" fit="true"></table>      	
	</div>
	<div id="bd_validationrule_add" class="easyui-window" style="padding:10px;">      	
    </div>
	<div id="bd_validationrule_update" class="easyui-window" style="padding:10px;">      	
    </div>
  </body>
</html>
