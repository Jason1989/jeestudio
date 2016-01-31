<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
    <title> </title>    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript" type="text/javascript">
		$(function(){						
			
		});			
        function wfToDoTaskSumbit(workitemId){
        	$.post("workflow/TijiaoWorkFlow!toDoTaskSubmit.action",{"workitemId":workitemId,"formId":"${formId}"},
				function(data){
					if(data == 'success'){	
						var tabddd = $('#wf_todotasklist_tabs').tabs('getSelected');
						$('#wf_todotasklist_tabs').tabs('select',tabddd.panel('options').title);							
						$.messager.alert('提示', '提交成功!', 'info');
					}else{
						$.messager.alert('提示', '提交失败!', 'warning');
					}
				}
			);
        }
        function wfToDoTaskBack(workitemId){
        	$.post("workflow/TuihuiWorkFlow!toDoTaskBack.action",{"workitemId":workitemId},
				function(data){
					if(data == 'success'){		
						var tabddd = $('#wf_todotasklist_tabs').tabs('getSelected');
						$('#wf_todotasklist_tabs').tabs('select',tabddd.panel('options').title);							
						$.messager.alert('提示', '退回成功!', 'info');
					}else{
						$.messager.alert('提示', '退回失败!', 'warning');
					}
				}
			);
        }
    </script>
  </head>  
  <body>
	<div class="easyui-panel" title="待办列表" headerCls="header_cls" fit="true">
		<table id="${tabId}" fit="true"></table>
	</div>	
  </body>
</html>
