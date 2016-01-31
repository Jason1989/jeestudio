<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  <% String uuid = UUID.randomUUID().toString();   %>
<script type="text/javascript">
	$(function(){
		$('#userTable<%=uuid%>').datagrid({
			nowrap: false,
			striped: true,
			fitColumns:true,
			border:true,
			url:'templatefile/templatefile!list.action',
			queryParams:{id:''},
			idField:'userid',
			singleSelect:true,
			columns:[[   
			    {title:' ',field:'ass',width:35,align:'center',			
			        formatter:function(value,rowData,index){
						return '<input id='+index+'_o type=radio  flag=<%=uuid%>'+index+'  value='+index+' name=radio_n />';
					}
				}, 	                                                   
			    {title:'模板名称',align:'center',width:200,field:'name'},
			    {title:'模板类型',align:'center',width:200,field:'type'},
			    {title:'模板描述',align:'left',width:200,field:'remark'}
			]],
			rownumbers:true,
			onClickRow:function(index, row) {	
				var ch = $('#'+index+'_o').attr('checked');
				if(ch){
					$('#'+index+'_o').removeAttr('checked');
				}else{
					$('#'+index+'_o').attr('checked',true);
				}
			}
		});
	});
	
	function chooseRadioTemplatefile(){
		 var radiolength=$(":radio[name='radio_n']:checked").length;
	     if(radiolength == 0 ){
	        $.messager.alert('提示','请选择一个模板！', 'warning');
	        return false;
	     }
	    // var index = $(":radio[name='radio_n']:checked")[0].val();
	     var obj = $("#userTable<%=uuid%>").datagrid("getSelected");

	     if('${param.fun}'==''){
	       $('#${param.fid}').val(obj.id);
	       $('#${param.fname}').val(obj.name);
	       $('#${param.wid}').window('close');		     
	     }else{
	        eval('${param.fun}("'+obj.userid+'","'+obj.uname+'")');
	     }
	}
		
	function closeTemplateFileWindow(){
	   $('#${param.wid}').window('close');
	}
</script>
  </head>  
  	<body class="easyui-panel" fit="true">	
  		<div class="easyui-layout" fit="true" border="false">
			<div region="center" border="false">
			  <table id="userTable<%=uuid%>"></table>
			</div>
			<div region="south" align=center style="text-align:center;height:45px;line-height:30px;padding-top:12px" border="false">
			  <a class="easyui-linkbutton" href="javascript:void(0)" icon="icon-ok" onclick="JavaScript:chooseRadioTemplatefile()">确定</a>
			  <a class="easyui-linkbutton" href="javascript:void(0)"  icon="icon-cancel"  onclick="closeTemplateFileWindow()">关闭</a>
			</div>
		</div>
  </body>
</html>