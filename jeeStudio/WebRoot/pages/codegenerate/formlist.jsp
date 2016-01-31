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
    <title>表单管理</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<script>
		
		$(function(){
            $('#fm_formlist_table1').datagrid({
                iconCls: 'icon-save',
                //width:1000,
                height: 400,
                nowrap: false,
                striped: true,
                autoFit: true,
                url: 'form/form!list.action?dataObjectId=${dataObjectId}',
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'id',
                pageSize:20,
                fitColumns:true,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
               		 {field: 'id',title: '表单编号',width: 250,sortable: true,align:'left'},
                	{field: 'formName',title: '表单名称',width: 250,sortable: true,align:'left'},
                	{field: 'type',title: '表单类型',width: 100,sortable: true,align:'center',
                		formatter:function(value){
							if ('listPage' == value) return '列表';
							else if ('editPage' == value) return '编辑';
							else if ('viewPage' == value) return '查看';
							return value;
						}},
                	{field: 'state',title: '状态',width: 100,sortable: true,align:'center'},
                	{field: 'operate',title: '操作',width: 140,align:'center',
	                    formatter:function(value,rec){    
	                       if(rec.type=="listPage"){
						    var links = '<a href="javascript:void(0)"  onclick=openCodeGuide("'+rec.id+'","'+rec.formName+'","'+rec.type+'")>固化</span>&nbsp;&nbsp;|&nbsp;&nbsp;';
							return links;
						   }
						   return "";	
						}
					}
				]],
                pagination: true,
                rownumbers: true
                //singleSelect: true,
               
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
       
        function openCodeGuide(id,formName,type){
        	 var url = "codegenerate/engineCodeGenerate!goGenerate.action?form_id="+id+"&form_formName="+formName+"&form_type="+type;
	          $("#fd_code_window").window('refresh');
	          $("#fd_code_window").window({'href':url});
	          $('#fd_code_window').window("open");
        }
         function closeWin(){
           $('#fd_code_window').window("close");
        }
        
         function engineCodeGenerate(){
           $('#fd_code_form').form("submit",{
			    url:'codegenerate/engineCodeGenerate!generateCode.action',
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
        
        function fdFormDataGridRefresh(){
        	$('#fm_formlist_table1').datagrid("reload");
        }
    </script>
  </head>  
  <body>
  <!-- 
  	<div class="navigation1">&nbsp;>>&nbsp;表单管理</div> -->
  	<div id="fm_formlist_tabs1" class="easyui-tabs" border="false" fit="true">
	  	<div title="表单列表" fit="true">
			<table id="fm_formlist_table1" fit="true"></table>      	
		</div>
		<div id="fd_code_window" class="easyui-window" style="padding:12px 12px 0 12px;"  title="代码生成向导"></div>
	</div>
	
  </body>
</html>
