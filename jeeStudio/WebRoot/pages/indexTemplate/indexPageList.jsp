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
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	</head>

	<script type="text/javascript">
   $(function(){
       //首页模板配置列表
       $('#indexTemplateListTable').datagrid({
                iconCls: 'icon-grid',
                width:600,
                height: 400,
				border:true,
				nowrap: false,
				fit: true,
				fitColumns:true,
                url: 'indexpage/indexpage!allPageList.action',
                sortName: 'id',
                sortOrder: 'desc',
                border:true,
                idField: 'id',
                pageSize:20,
                frozenColumns: [[
					{ field: 'id', checkbox: true}
				]],
                columns: [[
                	{field: 'name',title: '页面名称',width: 150,sortable: true,align:'left'},
                	{field: 'description',title: '应用的模板',width: 200,align:'center'},
                	{field: 'operate',title: '操作',width: 200,align:'center',
	                    formatter:function(value,rec){
	                    	var links = '<a href="javascript:;" onclick=templateupdate("' + rec.id + '")>修改</a>';
	                    	links += '&nbsp;|&nbsp;<a href="javascript:;" onclick=templatedelete("' + rec.id + '")>删除</a>';
						    links += '&nbsp;&nbsp;||&nbsp;<a href="javascript:;" onclick=pageManagement("' + rec.id + '")>页面模块配置</a>';
							return links;
						}
					}
				]],
                pagination: true,
                rownumbers: true,
                toolbar: ['-', 
		  			{text: '添加新首页',iconCls: 'icon-add',handler: templateAdd},
		  			'-', 
		  			{text: '批量删除',iconCls: 'icon-remove',handler: templateDeletes}, 
		  			'-' ]
            });
     		$('#index_Page_Add').appendTo('body').window({
               title: "首页添加",
               modal: true,
               resizable: false,
               minimizable: false,
               maximizable: false,
               shadow: false,
               closed: true,
               width: 500,
               height: 300
           });
           $('#index_Page_Update').appendTo('body').window({
               title: "首页修改",
               modal: true,
               resizable: false,
               minimizable: false,
               maximizable: false,
               shadow: false,
               closed: true,
               width: 500,
               height: 300
           });
   });
   //tools bar
	function templateDeletes(){
   		   	 var selected = $('#indexTemplateListTable').datagrid('getSelections');
	          if (selected == null || selected.length < 1) {
	              $.messager.alert('提示', '请选择数据!', 'warning');
	          } else {
	          	$.messager.confirm('提示', '确认删除吗？',function(data){
	          		if(data){
		           		var cc = [];
		           		for(var i=0;i<selected.length;i++){
		           			if(cc == ""){
		           				cc += "" + selected[i]['id'] + "" ;
		           			}else{
		           				cc += "," + selected[i]['id'] + "" ;
		           			}
		               	}
		               	$.post("indexpage/indexpage!delete.action",{"ids":cc},function(res){
		               		 if(res == 'success'){
		               		 	 $.messager.alert('提示','删除成功','info');			         				
								 $('#indexTemplateListTable').datagrid('reload');
		               		 }
				            $('#indexTemplateListTable').datagrid('clearSelections');
		               	});
		               	cc = [];
	              	}
	          	});	               
	          }
    }
    function templateAdd(){
    		 $("#index_Page_Add").window({'href':''});
			 $("#index_Page_Add").window('refresh');
			 $("#index_Page_Add").window({'href':'indexpage/indexpage!toAdd.action'});				
			 $("#index_Page_Add").window('open');
		}
    function templateupdate(modelid){
        	 $("#index_Page_Update").window({'href':''});
			 $("#index_Page_Update").window('refresh');
			 $("#index_Page_Update").window({'href':'indexpage/indexpage!toPageUpdate.action?subSystemId='+modelid});				
			 $("#index_Page_Update").window('open');
        }
   function templatedelete(templateid){
   			 $.messager.confirm('提示', '确认删除吗？',function(data){
	        	if(data){
	        		 $.post("indexpage/indexpage!delete.action",{"id":templateid},function(res){
	               		 if(res == 'success'){
	               		 	 $.messager.alert('提示','删除成功','info');			         				
							 $('#indexTemplateListTable').datagrid('reload');
	               		 }
			            $('#indexTemplateListTable').datagrid('clearSelections');
	               	});
	        	}
          	});	 
   }
   var tempId='';
   function pageManagement(dataTableId){
			if(tempId != dataTableId){
				if(tempId!='')$('#fm_indexPage_tabs').tabs('close','页面配置');
				$('#fm_indexPage_tabs').tabs('add',{
					title:'页面配置',
					href:'indexpage/indexpage!toConfig.action?subSystemId='+dataTableId,
					closable:true
				});
			}else{
				$('#fm_indexPage_tabs').tabs('select','页面配置');
			}
           var tempId = dataTableId;
  }
   function refreshChild(subsystemid){
		$('#fm_indexPage_tabs').tabs('close','页面配置');
		$('#fm_indexPage_tabs').tabs('add',{
			title:'页面配置',
			href:'indexpage/indexpage!toConfig.action?subSystemId='+subsystemid,
			closable:true
		});	
		$('#fm_indexPage_tabs').tabs('select','页面配置');
  }
</script>
<body>
	<div id="fm_indexPage_tabs" class="easyui-tabs"  border="false" fit="true">
	  	<div  title="首页列表"    border="false" collapsible="true" class="main_panel">
			<table id="indexTemplateListTable" fit="true"></table>   
			<div id="index_Page_Add"></div>   	
			<div id="index_Page_Update"></div>
		</div>
    </div>
</body>