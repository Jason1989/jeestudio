<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String codeFlag=request.getParameter("codeFlag");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>数据对象列表</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/basedata.common.js"></script>
	<script>
		$(function(){
			 
            $('#fm_menudolist_table').datagrid({
                iconCls: 'icon-save',
                //width:1000,
                height: 400,
                nowrap: false,
                striped: true,
                autoFit: true,
                url: 'datatable/dataTable!list.action?groupId=${groupId}',
                sortName: 'code',
                sortOrder: 'desc',
                fitColumns:true,
                idField: 'id',
                pageSize:20,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'dataSource',title: '数据源',width: 150,sortable: true,align:'left',formatter:function(value,rec){
						    if(rec.dataSource && rec.dataSource != null){
						    	return rec.dataSource.name;
						    }else{
								return "";
							}
						}},
                	{field: 'name',title: '名称',width: 150,sortable: true,align:'left'},
                	{field: 'type',title: '对象类型',width: 100,align:'center',
                		formatter:function(value){
                			for(var i=0; i<dataObjectTypeData.length; i++){
								if (dataObjectTypeData[i].id == value) return dataObjectTypeData[i].text;
							}
							return value;
						}},                	              	
                	{field: 'state',title: '状态',width: 120,align:'center',formatter:function(value){
                			for(var i=0; i<dataObjectStateData.length; i++){
								if (dataObjectStateData[i].id == value) return dataObjectStateData[i].text;
							}
							return value;
						}},
                	{field: 'operate',title: '操作',width: 140,align:'center',
	                    formatter:function(value,rec){
						    var links = '<a href="javascript:;" onclick=fmFormManagement("' + rec.id + '")>表单管理</a>';
							return links;
						}
					}
				]],
                pagination: true,
                rownumbers: true,
                //singleSelect: true,
                toolbar: ['-', 
		  			{text: '查询',iconCls: 'icon-search',handler: fmDataTableSearch},
		  			'-']
            });
            
            function fmDataTableSearch(){
            	$("#fm_datatable_search").window('open');            	         	
            } 
            $('#fm_datatablesearch_type').combobox({		
				data:dataObjectTypeData,
				valueField:'id',
				textField:'text',
				listWidth:151,
				editable:false
			});  
			$('#fm_datatablesearch_type').combobox("setValue",'-1');     
            $('#fm_datatable_search_submit').linkbutton();       
            $('#fm_datatable_search_cancel').linkbutton(); 
            $('#fm_datatable_search').window({
                title: "查询数据对象",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 400,
                height: 250
            });      
        });
       	function fmDataTableSearchSubmit(){
        	var name = $('#fm_datatablesearch_name').val();
        	var type = $('#fm_datatablesearch_type').combobox('getValue'); 
        	var pager = $('#fm_menudolist_table').datagrid('getPager');
        	var pageNumer = 1;
        	var pageSize = 10;
			if (pager){
				pageNumer = $(pager).pagination('options').pageNumber;
				pageSize = $(pager).pagination('options').pageSize;
			}
        	$.post("datatable/dataTable!search.action",{groupId:'${groupId}',datatableName:name,datatableType:type,pageNumer:pageNumer,pageSize:pageSize},
          		function(data){
					if(data && data != ""){
						$('#fm_datatable_search').window('close');
						$('#fm_menudolist_table').datagrid('loadData',eval('(' + data + ')'));
					}
       			}
       		);
        }
        var tempId = '';
        function fmFormManagement(dataTableId){
		//	if(tempId != dataTableId){
				if(tempId!='')$('#fm_menudolist_tabs').tabs('close','表单管理代码生成');
				$('#fm_menudolist_tabs').tabs('add',{
					title:'表单管理',
					href:'form/form!toList.action?codeFlag=codeFlag&dataObjectId='+dataTableId + '&random='+parseInt(10*Math.random()),
					closable:true
				});
		//	}else{
		//		$('#fm_menudolist_tabs').tabs('select','表单管理');
		//	}
            tempId = dataTableId;
        }       
    </script>
  </head>  
  <body>
  	<div id="fm_menudolist_tabs" class="easyui-tabs"  border="false" fit="true">
  	<div id="fm_menudolist_panel" title="数据对象列表"    border="false" collapsible="true" class="main_panel">
		<table id="fm_menudolist_table" fit="true"></table>      	
	</div>
    </div>
     <div id="fm_datatable_search" class="easyui-window" style="padding:10px;">      	
		<table width="100%">
			<tr><td align="right" width="40%">对象名称：</td><td align="left"><input id="fm_datatablesearch_name" type="text"/></td></tr>
			<tr><td align="right">对象类型：</td><td align="left"><select id="fm_datatablesearch_type" style="width:132px;">
				</select></td></tr>
			<tr><td align="center" colspan="2"><a id="fm_datatable_search_submit" href="javascript:;" class="easyui-linkbutton" icon="icon-save" onclick="fmDataTableSearchSubmit()">提交</a>
					<a id="fm_datatable_search_cancel" href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="$('#fm_datatable_search').window('close')">关闭</a>
			</td></tr>
		</table>
    </div>	  
  </body>
</html>
