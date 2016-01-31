<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="easyui-layout" fit="true">   
	     	<div region="center">
	     		<table id="deleterole_userslist"></table>
     		</div>	 
	     	<div region="south" style="height:45px;padding-top:10px;">
			    <a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-cancel" onclick="$('#dru_role_userlist').window('close');">关闭</a>
	     	</div>	
     	</div>  
     	
     	  
    <script type="text/javascript">
    			$(function(){
    				$('#deleterole_userslist').datagrid({
						border:false,
						pageSize:6,
						fit:true,
						columns: [[
		                	{field: 'userid',title: '用户ID',width: 70,align:'left'},                	
		                	{field: 'uname',title: '用户名',width: 80,align:'left'},
		                	{field: 'oname',title: '所在部门',width: 100,align:'left'},                	
		                	{field: 'levelname',title: '用户等级',width: 100,align:'left'}
						]]
		           	 });
    				
				 });
		</script>
     	