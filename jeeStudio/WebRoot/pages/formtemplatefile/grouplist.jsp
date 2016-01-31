<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>模板管理</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script>
		$(function(){
            $('#bd_form_templatefile_list_table_group').datagrid({
                iconCls: 'icon-save',
                //width:1000,
                height: 400,
                nowrap: false,
                striped: true,
                autoFit: true,
                url: 'templatefile/templatefile!grouplist.action',
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'id',
                pageSize:20,
                columns: [[
                	{field: 'name',title: '名称',width: 250,sortable: true,align:'left'},                	
                	{field: 'operate',title: '操作',width: 100,align:'center',
	                    formatter:function(value,rec){
						    var links = '<a href="javascript:;" onclick=update("' + rec.id + '","'+rec.name+'")>模板管理</a>&nbsp;&nbsp;';
							return links;
						}
					}
				]],
                pagination: true,
                rownumbers: true,
                //singleSelect: true,
                toolbar: []
            });
            
        });
        
        function update(dgId,dgName){
        	$("#bd_form_templatefile_panel_group").panel('refresh','pages/formtemplatefile/templatefile_list.jsp?id='+dgId+'&name='+encodeURI(dgName));
        }
    </script>
  </head>  
  <body>
  	<div id="bd_form_templatefile_panel_group" class="easyui-panel" fit="true">
  		<div iconCls='icon-table' title="模板分类列表" class="easyui-panel" fit="true" >
			<table id="bd_form_templatefile_list_table_group" fit="true"></table>
		</div>
	</div>
  </body>
</html>
