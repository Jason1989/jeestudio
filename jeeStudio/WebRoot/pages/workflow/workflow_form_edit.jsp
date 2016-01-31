<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String processInstanceID = request.getAttribute("processInstanceID").toString();
String taskFormID = request.getParameter("taskFormID");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>工作流表单配置</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<script>
		$(function(){
			$('#wf_formconfig_edit_tab').tabs({
			});
			$('#wf_tabconfig_workflowlist').datagrid({
                iconCls: 'icon-save',
                //width:1000,
                height: 400,
                nowrap: false,
                striped: true,
                autoFit: true,                
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'taskFormID',
                
				collapsible:true,
				remoteSort: false,
                url: 'workflow/WorkFlowFrame_nodelist.action?processInstanceID='+<%=processInstanceID%>,
                pageSize:10,                
                fitColumns:true,
                onLoadSuccess:function(data){
                    if (data.rows.length>0)
                    {
                        setTimeout("mergeCellsByField(\"wf_tabconfig_workflowlist\",\"taskNodeID,taskNodeName\")",2000); 
                        //mergeCellsByField("test","name,code,addr");
                    }
                },
                columns: [[
                	{field: 'taskNodeID',title: 'ID',width: 20,sortable: true},
                	{field: 'taskNodeName',title: '节点名称',width: 80,sortable: true},
	                {field: 'formName',title: '对应表单',width: 350,sortable: true,height:20},               	
                	{field: 'sortIndex',title: '序号',align:'center',width: 50,sortable: true,height:20},
                	{field: 'isShowTab',title: '是否显示子标签',align:'center',width: 100,sortable: true,height:20},
                	{field: 'operate',title: '操作',width: 80,height:20,align:'center',
	                    formatter:function(value,rec){
	                    	var taskNodeId = rec.taskNodeID;
	                    	var tfId = rec.tfId;
	                    	var formCounts = rec.formCounts;
	                    	if(tfId==''){
	                    		tfId='-1';
	                    	}else{
	                    		tfId = "'"+tfId+"'";
	                    	}
						    var links = '<a href="javascript:wf_formconfig_edit_form('+taskNodeId+','+tfId+');" >修改</a>&nbsp;|&nbsp;';
						    links += '<a href="javascript:wf_formconfig_edit_del('+tfId+');">删除</a>';
						    //links += '<a href="javascript:wf_formconfig_edit_add('+taskNodeId+');">增加</a>';
							return links;
						}
					}
				]],
                pagination: true,
                rownumbers: false
                //singleSelect: true,
            });
            $('#wf_formconfig_edit_form').appendTo('body').window({
            	title: "修改表单",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 400,
                height: 300
            });
            $('#wf_formconfig_edit_add').appendTo('body').window({
            	title: "新增表单",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 400,
                height: 300
            });
		});
        function wf_formconfig_edit_form(taskNodeId,tfId){
        	if(tfId=='-1'){
        		$.messager.alert("提示","请先新增表单后再编辑！","error");
        		return;
        	}
            $("#wf_formconfig_edit_form").window({'href':''});		
        	$("#wf_formconfig_edit_form").window('refresh');
        	$("#wf_formconfig_edit_form").window({'href':'workflow/WorkFlowFrame_toForm.action?random='+parseInt(10*Math.random())+'&taskNodeId='+taskNodeId+'&tfId='+tfId+'&processInstanceID=<%=processInstanceID%>'});		
        	$("#wf_formconfig_edit_form").window('open');
        }
        function wf_formconfig_edit_add(taskNodeId){
            $("#wf_formconfig_edit_add").window({'href':''});		
        	$("#wf_formconfig_edit_add").window('refresh');
        	$("#wf_formconfig_edit_add").window({'href':'workflow/WorkFlowFrame_toFormAdd.action?random='+parseInt(10*Math.random())+'&taskNodeId='+taskNodeId+'&processInstanceID=<%=processInstanceID%>'});		
        	$("#wf_formconfig_edit_add").window('open');
        }
        function wf_formconfig_edit_del(tfId){
        	if(tfId=='-1'){
        		$.messager.alert("提示","无记录可删！","error");
        		return;
        	}
        	$.messager.confirm("提示","确认删除当前记录？",function(data){
        		if(data){
        			$.post("workflow/WorkFlowFrame_delete.action",{"taskFormID":tfId},function(data){
        				if("success" == data){
							$.messager.alert('提示','删除成功','info');			         				
							search_tempform();
						}
        			});
        		}
        	});
        }
        function search_tempform(){ 
            $('#wf_tabconfig_workflowlist').datagrid('reload',{processInstanceID:<%=processInstanceID%>}); 
        }
       
		function mergeCellsByField(tableID,colList){
            var ColArray = colList.split(",");
            var tTable = $('#wf_tabconfig_workflowlist');
            var TableRowCnts=tTable.datagrid("getRows").length;
            var tmpA;
            var tmpB;
            var PerTxt = "";
            var CurTxt = "";
            var alertStr = "";
            //for (j=0;j<=ColArray.length-1 ;j++ )
            for (j=ColArray.length-1;j>=0 ;j-- )
            {
                //当循环至某新的列时，变量复位。
                PerTxt="";
                tmpA=1;
                tmpB=0;
                
                //从第一行（表头为第0行）开始循环，循环至行尾(溢出一位)
                for (i=0;i<=TableRowCnts ;i++ )
                {
                    if (i==TableRowCnts)
                    {
                        CurTxt="";
                    }
                    else
                    {
                        CurTxt=tTable.datagrid("getRows")[i][ColArray[j]];
                    }
                    if (PerTxt==CurTxt)
                    {
                        tmpA+=1;
                    }
                    else
                    {
                        tmpB+=tmpA;
                        tTable.datagrid('mergeCells',{
                            index:i-tmpA,
                            field:ColArray[j],
                            rowspan:tmpA,
                            colspan:null
                        });
                        tmpA=1;
                    }
                    PerTxt=CurTxt;
                }
            }
		}
		
    </script>
  </head>  
  <body>
 	 <div class="easyui-layout" fit="true" border="false">
	  	<div region="center" border="false" id="wf_formconfig_edit_tab" style="height:350px;" fit="true">
			<div title="表单配置" style="padding:20px;">
				<table id="wf_tabconfig_workflowlist" fit="true"></table> 
			</div>
			<div title="流程图" href="pages/workflow/workflow_form_Imag.jsp?isShowButton=0&taskFormID=<%=taskFormID %>" style="padding:20px;height='350';width='650';" cache="false">
			</div>
		</div>
		<div region="south" style="height: 52px;" border="false" align="center">
			<!-- <a class="easyui-linkbutton" icon="icon-save" href="javascript:;" onclick="wfFormconfigUpdateSave()">保存</a> -->	
			<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#wf_formconfig_edit').window('close');">关闭</a>				  
		</div>
		<div id="wf_formconfig_edit_form" class="easyui-window" ></div>
		<div id="wf_formconfig_edit_add" class="easyui-window" ></div>
	</div>
  </body>
</html>
