<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
	<%-- 首页配置，模块列表页面 --%>


	<script type="text/javascript">
   $(function(){
       //首页模块配置列表
       $('#indexPageModelListTable').datagrid({
                iconCls: 'icon-grid',
                width:600,
                height: 400,
				border:true,
				title:'首页模块配置',
				nowrap: false,
				fit: true,
				fitColumns:true,
                url: 'index/indexModel!list.action',
                sortName: 'id',
                sortOrder: 'desc',
                border:true,
                idField: 'id',
                pageSize:20,
                frozenColumns: [[
					{ field: 'id', checkbox: true}
				]],
                columns: [[
                	{field: 'name',title: '模块名称',width: 100,sortable: true,align:'left'},
                	{field: 'url',title: '模块地址',width: 150,sortable: true,align:'center'},
                	{field: 'description',title: '模块描述',width: 150,align:'center'},
                	{field: 'operate',title: '操作',width: 120,align:'center',
	                    formatter:function(value,rec){
	                    	var links = '<a href="javascript:;" onclick=modelview("' + rec.id + '")>查看</a>&nbsp;|&nbsp;';
						    links += '<a href="javascript:;" onclick=modelupdate("' + rec.id + '")>修改</a>&nbsp;|&nbsp;';
						    links += '<a href="javascript:;" onclick=modeldelect("' + rec.id + '")>删除</a>';
							return links;
						}
					}
				]],
                pagination: true,
                rownumbers: true,
                toolbar: ['-', 
		  			{text: '添加',iconCls: 'icon-add',handler: indexModelAdd},
		  			'-', 
		  			{text: '批量删除',iconCls: 'icon-remove',handler: indexModelDelete}, 
		  			'-' ]
            });
      		 $('#indexModel_add').appendTo('body').window({
                title: "模块操作",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 500,
                height: 300
            });
            $('#indexModel_update').appendTo('body').window({
                title: "修改模块",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 600,
                height: 300
            });
            $('#indexModel_view').appendTo('body').window({
                title: "查看模块",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 600,
                height: 250
            });
   });
   //tools bar
   
	function indexModelDelete(){
   		  var selected = $('#indexPageModelListTable').datagrid('getSelections');
          if (selected == null || selected.length < 1) {
              $.messager.alert('提示', '请选择数据!', 'warning');
          } else {
          	$.messager.confirm('提示', '确认删除吗？',function(data){
          		if(data){
	           		var cc = [];
	           		for(var i=0;i<selected.length;i++){
	           			if(cc == ""){
	           				cc += "'" + selected[i]['id'] + "'" ;
	           			}else{
	           				cc += ",'" + selected[i]['id'] + "'" ;
	           			}
	               	}
	               	$.post("index/indexModel!delete.action",{"ids":cc},function(res){
	               		 if(res == 'success'){
	               		 	 $.messager.alert('提示','删除成功','info');			         				
							 $('#indexPageModelListTable').datagrid('reload');
	               		 }
			            $('#indexPageModelListTable').datagrid('clearSelections');
	               	});
	               	cc = [];
              	}
          	});	               
          }
    }
    //col fun
    function indexModelAdd(){
			$("#indexModel_add").window({'href':''});
			$("#indexModel_add").window('refresh');
			$("#indexModel_add").window({'href':'index/indexModel!toAdd.action'});				
			$("#indexModel_add").window('open');
		}
    function modelview(modelid){
        	$("#indexModel_view").window({'href':''});
			$("#indexModel_view").window('refresh');
			$("#indexModel_view").window({'href':'index/indexModel!toView.action?model.id='+modelid});				
			$("#indexModel_view").window('open');
        }
   function modelupdate(modelid){
        	$("#indexModel_update").window({'href':''});
			$("#indexModel_update").window('refresh');
			$("#indexModel_update").window({'href':'index/indexModel!toupdate.action?model.id='+modelid});				
			$("#indexModel_update").window('open');
        }
        
    function modeldelect(modelid){
        	$.messager.confirm('提示', '确认删除吗？',function(data){
	        	if(data){
	        		 $.post("index/indexModel!delete.action",{"id":modelid},function(res){
	               		 if(res == 'success'){
	               		 	 $.messager.alert('提示','删除成功','info');			         				
							 $('#indexPageModelListTable').datagrid('reload');
	               		 }
			            $('#indexPageModelListTable').datagrid('clearSelections');
	               	});
	        	}
          	});	               
   }
</script>
<body>
	<div class="easyui-panel" fit="true" border="false">
		<table id="indexPageModelListTable"></table>
	</div>
	<div id="indexModel_add">
	</div>
	<div id="indexModel_view">
	</div>
	<div id="indexModel_update">
	</div>
</body>