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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script>
		
        $(function(){
           $('#fd_code_log_table').datagrid({
				iconCls:'icon-edit',
				width:680,
				height:300,
				singleSelect:true,
				idField:'codeVersionId',
				queryParams:{form_id:'${formId}'},
				url:'codegenerate/codeGenerate!getCodeLogsData.action',
                columns: [[ {
                    field: 'codeVersionId',
                    title: '版本号',
                    align:'center',
                    width: 50
                }, {
                    field: 'codeUserId',
                    title: '创建者',
                    align:'center',
                    width: 100
                }, {
                    field: 'codePath',
                    title: '生成目录',
                    align:'center',
                    width: 150					
                }, {
                    field:'opt',
	                title:'操作',
	                width:200,
	                rowspan:2,
	                align:'center',
					formatter:function(value,rec){
					    var codeFormsId = rec.codeFormsId;
					    var codeVersionId = rec.codeVersionId;
					    var links = '<a href="javascript:void(0)" class="easyui-linkbutton" onclick=openLogDetail("'+codeFormsId+'","'+codeVersionId+'")>查看</span>&nbsp;&nbsp;';
							return links;
					 }
					}
				]]
			});
            
             $('#fd_log_detail_window').appendTo('body').window({
                title: "代码生成向导",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                closed: true,
                top: 200,
                left: 300,
                width: 680,
                height: 350
            });
        });
        
       
       
        function openLogDetail(codeFormsId,codeVersionId){
          $.post("codegenerate/codeGenerate!getCodeLogsDetail.action",{forms_id:codeFormsId,version_id:codeVersionId},function(data){
	          $("#fd_code_log_detail").html(data);
	          document.getElementById("fd_log_detail_window").style.display = "";
	          $("#fd_log_detail_window").window('open');         
          });
        }
        
       
    </script>
  </head>
  
  <body>
	 <div title="表单列表" closable="true" fit="true">
	    	<table id="fd_code_log_table">
	        </table>
	    </div>
    
    <div id="fd_log_detail_window" class="easyui-window" closed="true" modal="true"  style="width:auto;height:auto;" title="表单代码生成详细信息">
      <div id="fd_code_log_detail" style="background:#fafafa;padding:10px;" fit="true">
      </div>
    </div>
	
  </body>
</html>
