<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
    <title>待办列表</title>    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript" type="text/javascript">
		var dataObject;
		$(function(){
			$('#wf_todotasklist_tabs').tabs({
				onSelect:function (title){	
					var tabId = "";	
					if(dataObject){
						for(var i=0;i<dataObject.length;i++){
							if(title == dataObject[i]){
								tabId = "wf_todotasktabs_list" + i;	
								break;
							}
						}
						$.post("workflow/DaibanWorkFlow!getToDoTaskList.action",{"taskTypeId":title},
							function(data){
								if(data){			
									var dataObject = eval('('+data+')');
									$('#'+tabId).datagrid({
						                iconCls: 'icon-save',
						               	width:770,
						                nowrap: false,
						                striped: true,
						                sortOrder: 'desc',
						                remoteSort: false,
						                //singleSelect:true,
						                idField: 'activityInsId',
						                frozenColumns: [[
											{ field: 'ck', checkbox: true}
										]],
									  //rownumbers: true,
						                columns: [[                	
						                	{field: 'activityInsName',title: '实例名',width: 200,sortable: true,align:'left'},                	
						                	{field: 'processDefName',title: '定义名',width: 200,align:'left'},                	
						                	{field: 'operate',title: '操作',width: 100,align:'center',
						                		formatter:function(value,rec){  
						                			var links = '<a href="javascript:wfToDoTaskSumbit('+rec.workitemId+');">提交</a>';           
						                			var flagBack = rec.tuihui;
						                			if(flagBack){
							                			var flag = flagBack.substring(0,1);
							                			if(flag == "1")//退回标记
							                				links += '&nbsp;&nbsp;|&nbsp;&nbsp;<a href="javascript:wfToDoTaskBack('+rec.workitemId+');">退回</a>';                			
						                			}
						                			links=links+'&nbsp;&nbsp;<a href="javascript:showTaskViewInfo('+rec.workitemId+',\'viewTaskNode\',\''+rec.serviceAppId+'\');">查看详情</a>';		
													return links;
						                		}
						                	}
										]]
						            });	
									$('#'+tabId).datagrid('loadData',dataObject);
								}
							}
						);	
					}
				}
			});
			$.post("workflow/DaibanWorkFlow!getToDoTaskTypeList.action",{},
				function(data){
					if(data){
						dataObject = eval('('+data+')');
						for(var i=0;i<dataObject.length;i++){
							var tabId = "wf_todotasktabs_list" + i;
							var tabName = dataObject[i];
							if(!$('#wf_todotasklist_tabs').tabs('exists',tabName)){
					      		$('#wf_todotasklist_tabs').tabs('add',{
									title:tabName,
									href:'workflow/DaibanWorkFlow!toToDoTaskTabs.action?tabId='+tabId,
									closable:true
								});
							}else{
								$('#wf_todotasklist_tabs').tabs('select',tabName);
							}
							
						}
					}
				}
			);
		});				
    </script>
  </head>  
  <body>
  	<div id="wf_todotasklist_tabs" class="easyui-tabs" fit="true" border="false">		
	</div>	
  </body>
</html>
