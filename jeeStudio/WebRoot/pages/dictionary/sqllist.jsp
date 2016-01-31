<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>字典管理</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	
	<script>
		
		$(function(){
			//execteSqlDic('664dfb46446da331cf42d0435e5fb6a5');
			var jsonData_sqllist = [{id:'1',name:'查询',selected:'selected'},{id:'2',name:'增删改'},{id:'3',name:'存储过程'},{id:'4',name:'创建触发器'}];
			var jsonData_sqllist_dataSource = new Array();
			$.ajax({
				url:'datasource/dataSource!getAllItem.action',
				type:'post',
				success:function(data, textStatus){
					jsonData_sqllist_dataSource = data;
				}
			});
			
            $('#bd_sqllist_table').datagrid({
                iconCls: 'icon-save',
                //width:1000,
                height: 400,
                nowrap: false,
                fitColumns:true,
                striped: true,
                autoFit: true,
                url: 'dictionary/dataDictionary!sqlDicList.action',
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'id',
                pageSize:20,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'name',title: '名称',width: 80,sortable: true},
                	{field: 'value',title: '表达式',width: 150,sortable: true},
                	{field: 'type',title: '类型',width: 80,sortable: true,align:'center',formatter:function(value){
                		for(var i=0; i<jsonData_sqllist.length; i++){
							if (jsonData_sqllist[i].id == value) return jsonData_sqllist[i].name;
						}
						return value;
                	}},
                	{field: 'datasourceid',title: '数据源',width: 120,formatter:function(value){
                		for(var i=0; i<jsonData_sqllist_dataSource.length; i++){
							if (jsonData_sqllist_dataSource[i].id == value) return jsonData_sqllist_dataSource[i].name;
						}
						return value;
                	}},               	
                	{field: 'operate',title: '操作',width: 100,align:'center',formatter:function(value,rowData){
                		return "<img style='cursor:hand;' onclick='editSqlDic("+JSON2.stringify(rowData)+")' src='images/ioc-editor.gif' title='修改' >"+
                			   "&nbsp;&nbsp;&nbsp;&nbsp;<img style='cursor:hand;' onclick=deletesql_dic(\"\'"+rowData.id+"\'\") src='images/ioc-delete.gif' title='删除' >";
                	}}
				]],
                pagination: true,
                rownumbers: true,
              	toolbar: ['-',{text: '添加',iconCls: 'icon-add',handler: function(){
		              			OpenEditPage("添加","650","460","pages/dictionary/sqladd.jsp");
		              		}
              			 },'-',{text: '删除',iconCls: 'icon-remove',handler: function(){
		              			var rows = $('#bd_sqllist_table').datagrid('getSelections');
		              			if(rows.length>0){
		              				var idstr = '';
		              				for(var i=0;i<rows.length;i++){
		              					if(i==0){
		              						idstr+="\'"+rows[i].id+"\'";
		              					}else{
		              						idstr+=",\'"+rows[i].id+"\'";
		              					}
		              				}
		              				deletesql_dic(idstr);
		              				
		              			}
		              	 	}
		              	 }
		        ]
            });
         
        });
   		
   		function editSqlDic(rowData){
       		OpenEditPage("添加","650","460","pages/dictionary/sqladd.jsp");
       		setTimeout(function(){
       			$('input:text[name=sqlObj.sqldicid]').val(rowData.id);
	       		$('input:text[name=sqlObj.sqldicname]').val(rowData.name);
	       		$('#sqldic_type').combobox('select',rowData.type);
	       		$('#bd_sqldict_add_datasource').combobox('select',rowData.datasourceid);
	       		$('textarea[name=sqlObj.sqldic_expression]').val(rowData.value);
	       		$('textarea[name=sqlObj.sqldic_remark]').val(rowData.remark);
	       		if(rowData.params!=""){
	       			$.ajax({
	       				type:'post',
	       				data:'id='+rowData.id,
	       				url:'dictionary/dataDictionary!getParamsDataById.action',
	       				success:function(data, textStatus){
							$('#sql_prams_table').datagrid('loadData',eval("("+data+")"));
							var rows = $('#sql_prams_table').datagrid('getRows');
							$('#sql_prams_hidden').val(JSON2.stringify(rows));
						}
	       			})
	       		}
	       		$("#sql_isupdate_hidden").val('2');
	       		$('input:text[name=sqlObj.sqldicid]').attr('readonly','readonly');
       		},500);
       	
       	}
       	function deletesql_dic(ids){
       		$.messager.confirm('确认', '确认删除？', function(r){
				if (r){
					$.ajax({
	      				type:'post',
	      				data:'ids='+ids,
	      				url:'dictionary/dataDictionary!deleteSqlDataById.action',
	      				success:function(data, textStatus){
							$.messager.alert("提示","操作成功", 'info');
							$('#bd_sqllist_table').datagrid('reload');
						}
      				})
				}
			})
       		
       	}
    </script>
  </head>  
  <body>
  	<div id="bd_sqllist_panel" title="SQL字典列表" class="easyui-panel" fit="true" border="false" collapsible="true" class="main_panel">
		<table id="bd_sqllist_table" fit="true"></table>      	
	</div>
  </body>
</html>
