<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>表单管理</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<script>
		var fmFormSelectTableTablesData;
		var fmPagesetValidationRuleData = eval('(${session.validationRuleJson})');
		var fmPagesetDataDictionary = eval('(${session.dictTreeJson})');
		var fmPagesetDataOrg = eval('(${session.orgTreeJson})');
		function refreshSessionData(data){
			fmPagesetValidationRuleData = eval('('+data+')');
			try{$('#fm_listpageset_qs_validation').combobox('loadData',fmPagesetValidationRuleData);}catch(e){}
			try{$('#fm_editpageset_dc_validation').combobox('loadData',fmPagesetValidationRuleData);}catch(e){}
			try{$('#fm_viewpageset_dc_validation').combobox('loadData',fmPagesetValidationRuleData);}catch(e){}
		}
		
		var fmformlistpagename;
		$(function(){
            $('#fm_formlist_table').datagrid({
                iconCls: 'icon-save',
                //width:1000,
                height: 400,
                nowrap: false,
                striped: true,
                autoFit: true,
                url: 'form/form!list.action?dataObjectId=${dataObjectId}',
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'id',
                pageSize:20,
                fitColumns:true,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
               		 {field: 'id',title: '表单编号',width: 250,sortable: true,align:'left'},
                	{field: 'formName',title: '表单名称',width: 250,sortable: true,align:'left'},
                	{field: 'type',title: '表单类型',width: 100,sortable: true,align:'center',
                		formatter:function(value){
							if ('listPage' == value) return '列表';
							else if ('editPage' == value) return '编辑';
							else if ('viewPage' == value) return '查看';
							return value;
						}},
                	//{field: 'url',title: 'URL',width: 320,align:'center'},
                	{field: 'state',title: '状态',width: 100,sortable: true,align:'center'},
                	{field: 'operate',title: '操作',width: 140,align:'center',
	                    formatter:function(value,rec){
						    var links = '<a href="javascript:;" onclick=fdFormDesign("' + rec.id + '","' + rec.formName + '")>设计</a>&nbsp;|&nbsp;';
						    links += '<a href="javascript:;" onclick=fmFormUpdate("' + rec.id + '")>修改</a>&nbsp;|&nbsp;';
						    links += '<a href="javascript:;" onclick=fmFormDelete("' + rec.id + '")>删除</a>';
							return links;
						}
					},
                	{field: 'confBasic',title: '页面信息配置',width: 70,align:'center',
	                    formatter:function(value,rec){
	                    	if(value != null && value == "1"){
	                    		return CONFIGYES;
	                    	}else{
	                    		return CONFIGNO;
	                    	}
	                    }
	                },
                	{field: 'confDatacolumn',title: '数据列配置',width: 70,align:'center',
	                    formatter:function(value,rec){
	                    	if(value != null && value == "1"){
	                    		return CONFIGYES;
	                    	}else{
	                    		return CONFIGNO;
	                    	}
	                    }
	                },
                	{field: 'confTabs',title: '多标签配置',width: 70,align:'center',
	                    formatter:function(value,rec){
	                    	if(value != null && value == "1"){
	                    		return CONFIGYES;
	                    	}else{
	                    		return CONFIGNO;
	                    	}
	                    }
	                },
                	{field: 'confParam',title: '参数配置',width: 70,align:'center',
	                    formatter:function(value,rec){
	                    	if(value != null && value == "1"){
	                    		return CONFIGYES;
	                    	}else{
	                    		return CONFIGNO;
	                    	}
	                    }
	                },
                	{field: 'confQuery',title: '组合查询配置',width: 70,align:'center',
	                    formatter:function(value,rec){	  
	                    	if(rec.type == 'listPage'){                  	
		                    	if(value != null && value == "1"){
		                    		return CONFIGYES;
		                    	}else{
		                    		return CONFIGNO;
		                    	}
	                    	}else{
	                    		return NOTTHISCONFIG;
	                    	}
	                    }
	                }
				]],
                pagination: true,
                rownumbers: true,
                //singleSelect: true,
                toolbar: ['-', 
		  			{text: '添加',iconCls: 'icon-add',handler: fmFormAdd},
		  			'-', 
		  			{text: '删除',iconCls: 'icon-remove',handler: fmFormDelete}, 
		  			"->",
		  			{text: '快速创建表单',iconCls: 'icon-add',handler: fmFormAdd_ALL}
		  			]
            });
            $('#fm_form_add').appendTo('body').window({
                title: "添加表单",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 700,
                height: 500
            });
            $('#fm_form_update').appendTo('body').window({
                title: "修改表单",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 490
            });
           	$('#fm_formadd_selecttable').appendTo('body').window({
				modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                onClose:fmFormAddSelectTableClose,
                shadow: false,
                closed: true,
                width: 1030,
                height: 500
			});	
            function fmFormAdd(){
            	fmformlistpagename = "fm_form_add";	
            	$("#fm_form_add").window({'href':''});		
            	$("#fm_form_add").window('refresh');
            	$("#fm_form_add").window({'href':'form/form!toAdd.action?dataObjectId=${dataObjectId}&random='+parseInt(10*Math.random())});		
            	$("#fm_form_add").window('open');
            }    
            function fmFormAdd_ALL(){
            	$.ajax({
					    url: 'datacolumn/dataColumn!checkTableIsParmerkey.action?dataTableId=${dataObjectId}',
					   	type: 'post',
					    success: function(data){
					    	if(data=='true'){
					    		fmformlistpagename = "fm_form_add";	
				            	$("#fm_form_add").window({'href':''});		
				            	$("#fm_form_add").window('refresh');
				            	$("#fm_form_add").window({'href':'form/form!toAddForALL.action?dataObjectId=${dataObjectId}&pageType=editPage&random='+parseInt(10*Math.random())});		
				            	$("#fm_form_add").window('open');
					    	}else{
					    		$.messager.alert("提示", '没有创建主键！','error');
					    	}
						}
				});	
            }         
        });
       
        function fmFormDelete(formId){
        	if(formId && formId != "" && typeof formId == "string"){
        		$.messager.confirm('提示', '确认删除吗？',function(a){
	         		if(a)$.post("form/form!delete.action",{"formId":formId},fmFormDeleteRollback);
	         	});
        	}else{
         		var selected = $('#fm_formlist_table').datagrid('getSelections');
	            if (selected == null || selected.length < 1) {
	                $.messager.alert('提示', '请选择数据!', 'warning');
	            } else {
	            	$.messager.confirm('提示', '确认删除吗？',function(a){
	            		if(a){
		            		var formIds = "";
		            		for(var i=0;i<selected.length;i++){
		            			formIds += formIds == ""?"'" + selected[i]['id'] + "'":",'" + selected[i]['id'] + "'";
		                	}
		                	$.post("form/form!delete.action",{"formIds":formIds},fmFormDeleteRollback);
	                	}
	            	});	               
	            }
            }
        }
        function fmFormAddSelectTableClose(){
			$('#'+fmformlistpagename).window('open');
		}
        function fmFormDeleteRollback(data){
        	if("success" == data){
   				$.messager.alert('提示','删除成功！','info',function(){
	   				$('#fm_formlist_table').datagrid('load').datagrid('clearSelections');
   				});			         				
   			}
        }
        function fmFormUpdate(formId){
        	fmformlistpagename = "fm_form_update";	
        	$("#fm_form_update").window({'href':''});	
        	$("#fm_form_update").window('refresh');
           	$("#fm_form_update").window({'href':'form/form!toUpdate.action?formId='+formId + '&random='+parseInt(10*Math.random())});		
           	$("#fm_form_update").window('open');
        	//$.post("form/form!toUpdate.action",{"formId":formId},
        	//	function(data){
	        //		$("#fm_form_update").html(data);
	        //		$("#fm_form_update").window('open');
	        //		initFormEditData();
      		//	}
      		//);
        }
        function fdFormDesign(formId,formName){   
        	//var formTitleObj = {"listPage":"列表页设计","editPage":"编辑页设计","viewPage":"查看页设计"};
			//if(formTitleObj[type] && formTitleObj[type] != null){
			if(formName && formName != null){
	        	if(!$('#fm_formlist_tabs').tabs('exists',formName+"设计")){
		      		$('#fm_formlist_tabs').tabs('add',{
						title:formName+"设计",
						href:'form/form!toDesign.action?formId=' + formId + '&random='+parseInt(10*Math.random()),
						closable:true
					});
				}else{
					$('#fm_formlist_tabs').tabs('select',formName+"设计");
				}
			}
        }
        function fdFormDataGridRefresh(){
        	$('#fm_formlist_table').datagrid("reload");
        }
    </script>
  </head>  
  <body>
  <!-- 
  	<div class="navigation1">&nbsp;>>&nbsp;表单管理</div> -->
  	<div id="fm_formlist_tabs" class="easyui-tabs" border="false" fit="true">
	  	<div title="表单列表" fit="true">
			<table id="fm_formlist_table" fit="true"></table>      	
		</div>
	</div>
	<div id="fm_form_add" class="easyui-window" style="padding:10px;">      	
    </div>
	<div id="fm_form_update" class="easyui-window" style="padding:10px;">      	
    </div>
    <div id="fm_formadd_selecttable" class="easyui-window" style="padding:5px;" title="选择表和列">
	</div>
  </body>
</html>
