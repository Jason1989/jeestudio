﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>代码生成</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script>
		
        $(function(){
            $('#fd_formlist_table').datagrid({
                //title: '数据列表',
                //iconCls: 'icon-save',
                //width:1000,
                height: 400,
                nowrap: false,
                url:'codegenerate/codeGenerate!getFormsData.action',
                striped: true,
                autoFit: true,
                sortName: 'id',
                idField: 'id',
                pageNumber:1,
                pageSize:10,
                frozenColumns: [[{
                    field: 'ck',
                    checkbox: true
                }, {
                    field: 'id',
                    title: '序号',
                    width: 250,
                    sortable: true
                }]],
                columns: [[ {
                    field: 'formName',
                    title: '名称',
                    align:'center',
                    width: 250
                }, {
                    field: 'type',
                    title: '类型',
                    align:'center',
                    width: 150					
                }, {
                    field: 'state',
                    title: '状态',
                    align:'center',
                    width: 150					
                },{
                    field:'opt',
	                title:'操作',
	                width:200,
	                rowspan:2,
	                align:'center',
					formatter:function(value,rec){
					    var foId = rec.id;
					    var links = '<a href="javascript:void(0)"  onclick=openCodeGuide("'+foId+'")>固化</span>&nbsp;&nbsp;|&nbsp;&nbsp;';
					        links += '<a href="javascript:void(0)"  onclick=openCodeLog("'+foId+'")>日志管理</span>';
							return links;
					}
					}]],
                pagination: true,
                rownumbers: true,
                singleSelect: true,
                toolbar: ['-', {
                    text: '查询',
                    iconCls: 'icon-search',
                    handler: function(){
                        document.getElementById("searchorg").style.display = "";
                        $('#searchorg').window("open");
                    }
                }, '-'
			]
            });
            
            $('#fd_code_window').appendTo('body').window({
                    title: "代码生成向导",
                    modal: true,
                    resizable: false,
                    minimizable: false,
                    maximizable: false,
                    closed: true,
                    top: 200,
                    left: 300,
                    width: 600,
                    height: 400
                });
                
        });
        
        function openCodeGuide(formsId){
          var url = "codegenerate/codeGenerate!goGenerate.action?form_id="+formsId;
          $('#forms_id').val(formsId);
          $("#fd_code_window").window('refresh');
          $("#fd_code_window").window({'href':url});
          $('#fd_code_window').window("open");
        }
        function closeCodeGuide(){
		  $('#fd_code_form').form('clear');
		  document.getElementById("fd_code_window").style.display = "";
          $('#fd_code_window').window('close');
        }
        function codeGenerate(){
           $('#fd_code_form').form("submit",{
			    url:'codegenerate/codeGenerate!generate.action',
			    onSubmit:function(){
			        return $(this).form('validate');
			    },
			    success:function(data){
			        if(data == "1"){
			        $('#fd_code_window').window('close');
			        $('#fd_code_form').form('clear');
			           alert("代码生成成功");
			        }else{
			           alert(data);
			        }
			    }
			});
        }
        function openCodeLog(formId){ 
          $('#fd_formlist_tab').tabs('close','表单日志');
          $('#fd_formlist_tab').tabs('add',{
						title:'表单日志', 
						href:'codegenerate/codeGenerate!goLogs.action?form_id='+formId,
						closable:true
					});
        }
        function closeWin(){
           $('#fd_code_window').window("close");
        }
        
         var p = $('#fd_formlist_table').datagrid('getPager');
		  if (p){
		   $(p).pagination({
		    showPageList:false,
		    beforePageText:'第',
		    afterPageText:'页，共{pages}页',
		    displayMsg:'当前显示从{from}到{to}共{total}记录',
		    onBeforeRefresh:function(pageNumber,pageSize){
		     $(this).pagination('loading');
		     }
		   });
		  }
        
        
        
    </script>
  </head>
  
  <body>
	<div id="fd_formlist_tab" class="easyui-tabs" border="false" fit="true">
	    <div title="表单列表" closable="true" fit="true">
	    	<table id="fd_formlist_table" fit="true">
	        </table>
	    </div>
    </div>
    
	  <div id="fd_code_window" class="easyui-window" style="padding:12px 12px 0 12px;"  title="代码生成向导">
	</div>
	
	
   
 
    
   
    
	
  </body>
</html>
