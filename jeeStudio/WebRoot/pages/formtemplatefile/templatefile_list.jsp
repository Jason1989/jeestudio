<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.zxt.compplatform.formengine.util.StrTools" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String paramName=StrTools.charsetFormat(request.getParameter("name"),
		"ISO8859-1", "UTF-8");
%>
	<script>
		$(function(){
            $('#bd_form_templatefile_list_table').datagrid({
                iconCls: 'icon-save',
                //width:1000,
                height: 400,
                nowrap: false,
                fitColumns:true,
                striped: true,
                autoFit: true,
                url: 'templatefile/templatefile!list.action?id=${param.id}',
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'id',
                pageSize:20,
                columns: [[
                	{field: 'id',title: '模板编号',width: 150,align:'center'},
                	{field: 'name',title: '模板名称',width: 150,align:'center'},
                	{field: 'type',title: '模板类型',width: 90,align:'center'},
                	{field: 'remark',title: '模板描述',width: 100,align:'left'},
                	{field: 'time',title: '上传时间',width: 90,align:'center'},
                	{field: 'operate',title: '操作',width: 100,align:'center',
	                    formatter:function(value,rec){
						    var links = '<a href="javascript:;" onclick=del("' + rec.id + '")>删除</a>';
							return links;
						}
					}
				]],
                pagination: true,
                rownumbers: true,
                //singleSelect: true,
                toolbar: [
		  			'-',
		  			{text: '添加',iconCls: 'icon-add',handler: add},
		  			'->',
		  			{text: '返回',iconCls: 'icon-remove',handler: backToGroup} 
		  		]
            });
            $('#bd_form_templatefile_list_add').appendTo('body').window({
                title: "添加",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 460
            });
            function add(){
          		$("#bd_form_templatefile_list_add").window({'href':''});//防止再次打开的时候,重复加载,造成页面的混乱
  				$("#bd_form_templatefile_list_add").window('refresh');//先刷新再加载新的页面
  				$("#bd_form_templatefile_list_add").window({'href':'pages/formtemplatefile/templatefile_list_add.jsp?id=${param.id}'});				
  				$("#bd_form_templatefile_list_add").window('open');
            }  
        });
        function del(id){
       		$.messager.confirm('提示', '确认删除吗？',function(a){
         		if(a)$.post("templatefile/templatefile!delete.action?id="+id,templatefile_delcallback);
         	});
        }
        function templatefile_delcallback(data){
        	if("success" == data){
   				$.messager.alert('提示','删除成功','info');			         				
   				$('#bd_form_templatefile_list_table').datagrid('reload');
   			}else{
   				$.messager.alert('提示','删除失败','info');
   	   		}
        }
        function backToGroup(){
        	$("#bd_form_templatefile_panel_group").panel('refresh','pages/formtemplatefile/grouplist.jsp');
        }
    </script>
	<div iconCls='icon-table' title="<%=paramName%>--子系统模板列表 " class="easyui-panel"  fit="true">
		<table id="bd_form_templatefile_list_table" fit="true"></table>
	</div>
	<div id="bd_form_templatefile_list_add" class="easyui-window" style="padding:10px;">      	
    </div>