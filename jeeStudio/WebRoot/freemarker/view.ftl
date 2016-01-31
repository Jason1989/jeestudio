<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>代码生成</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="../css/easyui.css">
		<link rel="stylesheet" type="text/css" href="../css/icon.css">
		<link rel="stylesheet" type="text/css" href="../css/image.css">
		<link rel="stylesheet" type="text/css" href="../css/style.css">
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="../js/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="../js/XmlUtils.js"></script>
		<script>
		
        $(function(){
            $('#fd_formlist_table').datagrid({
                //title: '列表页',
                fit:true,
                nowrap: false,
                url:'${url}',
                striped: true,
                autoFit: true,
                sortName: 'foId',
                idField: 'foId',
                <#if canBatch="true">
                frozenColumns: [[{
                    field: 'ck',
                    checkbox: true
                }]],
                </#if>
                columns: [[
                 <#list fields as field> 
				{
                    field: '${field.dataColumnName}',
                    title: '${field.name}',
                    align: '${field.align}',
                    width: '${field.width}'
                },
                </#list>
                {
                    field:'opt',
	                title:'操作',
	                width:200,
	                rowspan:2,
	                align:'center',
					formatter:function(value,rec){
					    var foId = rec.foId;
					    var links = '';
					     <#list rowButtons as rowBtn> 
					         links += '&nbsp;&nbsp;<a href="javascript:void(0)"  onclick=openCodeLog("'+foId+'")>${rowBtn.buttonName}</span>&nbsp;&nbsp;<#if rowBtn_has_next>|</#if>';
					     </#list>
						return links;
					}
					}]],
		       <#if canShowPagination="true">
                pagination: true,
                </#if>
                rownumbers: true,
                singleSelect: true,
                toolbar: [ 
                      <#list gridButtons as gridBtn> 
					{
	                    text: '${gridBtn.buttonName}',
	                    iconCls: '${gridBtn.iconCls}',
	                    handler: function(){
	                        <#if (gridBtn.iconCls!="icon-cancel")>
	                        document.getElementById("<#if (gridBtn.iconCls="icon-search")>searcharea<#elseif (gridBtn.iconCls="icon-add")>addarea<#else>editarea</#if>").style.display = "";
	                        $('#searcharea').window("open");
	                        <#else>doDelete();
	                        </#if>
	                    }
	                }
	                <#if gridBtn_has_next>,"-",</#if>
                    </#list>
                
			]
            });
            
             </script>
	</head>

	<body>
	</body>
            
</html>
