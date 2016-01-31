<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'listpageset_operateButton.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script>
	
		
		function watchJS_toolbar(rowData){
			OpenEditPage('业务逻辑','750','380','<%=request.getContextPath()%>/pages/form/pageset/alterjs_toolbar.jsp');
		}
		function saveXmlSetting_toolbar(){
			var rows = $('#fm_listpageset_toolbarButton_table').datagrid('getRows');
			var columnSetXmlUtils = new XmlUtils({dataType:'json'}); 
			columnSetXmlUtils.loadXmlString(formSettings);
			var root = columnSetXmlUtils.getRoot();
			var listButtonsNode = columnSetXmlUtils.getChildNodeByTagName(root,'Buttons');	
			var listtoolbarButtonsNode = columnSetXmlUtils.getChildNodeByTagName(listButtonsNode,'ToolbarButtons');
			if(listtoolbarButtonsNode == undefined){
			}else{
				columnSetXmlUtils.removeChild(listtoolbarButtonsNode);
			}
			listtoolbarButtonsNode = columnSetXmlUtils.createNode('ToolbarButtons','',{},listButtonsNode);	
			var toolbarButtonAttrs;
			for(var i = 0; i<rows.length; i++){
				toolbarButtonAttrs = {
					buttonname:rows[i].buttonname,
					buttonicon:rows[i].buttonicon,
					buttonrules:rows[i].buttonrules,
					isshow:rows[i].isshow
				}
				columnSetXmlUtils.createNode('ToolbarButton','',toolbarButtonAttrs,listtoolbarButtonsNode);	
			}
			listPageSetSubmit(columnSetXmlUtils.toString());
			
		}
		$(function(){
			var lastIndex_toolbarButton_table = null;
			$('#fm_listpageset_toolbarButton_table').datagrid({
				nowrap: false,
				striped: true,
				fit: true,
				fitColumns:true,
				border:false,
				singleSelect:true,
				idField:'monitortime',
				url:'',
				//data:,
				rownumbers:true,
				toolbar:[{
					text:'增加',
					iconCls:'icon-add',
					titleCls:'link_btn_color',
					handler:function(){
					$('#fm_listpageset_toolbarButton_table').datagrid('endEdit', lastIndex_toolbarButton_table);
						$('#fm_listpageset_toolbarButton_table').datagrid('appendRow',{
							buttonname:'',
							buttonicon:'',
							buttonrules:'',
							isshow:'',
							operateButton:''
						});
						lastIndex_toolbarButton_table = $('#fm_listpageset_toolbarButton_table').datagrid('getRows').length-1;
						$('#fm_listpageset_toolbarButton_table').datagrid('beginEdit', lastIndex_toolbarButton_table);
					}
				},'-',{
					text:'删除',
					iconCls:'icon-remove',
					titleCls:'link_btn_color',
					handler:function(){
						var row = $('#fm_listpageset_toolbarButton_table').datagrid('getSelected');
						if (row){
							var index = $('#fm_listpageset_toolbarButton_table').datagrid('getRowIndex', row);
							$('#fm_listpageset_toolbarButton_table').datagrid('deleteRow', index);
						}
					}
				},'-',{
					text:'保存',
					iconCls:'icon-save',
					titleCls:'link_btn_color',
					handler:function(){
						$('#fm_listpageset_toolbarButton_table').datagrid('endEdit',lastIndex_toolbarButton_table);
						lastIndex_toolbarButton_table = null;
						var rows = $('#fm_listpageset_toolbarButton_table').datagrid('getChanges');
						if(rows.length>0){
							for(var i = 0; i<rows.length;i++){
								var rowIndex = $('#fm_listpageset_toolbarButton_table').datagrid('getRowIndex',rows[i])+1;
								if(rows[i].buttonname == ''){
									$.messager.alert('提示','按钮名称为空（第'+rowIndex+'行）','info');
									return;
								}else if(rows[i].buttonicon==''){
									$.messager.alert('提示','按钮图标地址为空（第'+rowIndex+'行）','info');
									return;
								}
							}
						
						}
					
						$('#fm_listpageset_toolbarButton_table').datagrid('acceptChanges');
						saveXmlSetting_toolbar();
					}
				},'-',{
					text:'撤销操作',
					iconCls:'icon-undo',
					titleCls:'link_btn_color',
					handler:function(){
						$('#fm_listpageset_toolbarButton_table').datagrid('rejectChanges');
					}
				}],
				columns:[[
					{field:'buttonname',title:'按钮名称',width:160,align:'center',editor:'text'},
					{field:'buttonicon',title:'图标',width:110,align:'center',editor:'text'},
					{field:'buttonrules',hidden:'true'},
					{field:'isshow',title:'是否显示',width:110,align:'center',formatter:function(value){
						if(value=='1'){
							return '是';
						}
						return '否';
					},editor:{
						type:'checkbox',
						options:{
							on:'1',
							off:'0'
						}
					}},
					{field:'operateButton',title:'业务操作',width:110,align:'center',formatter:function(value,rowData){
						var str = "";
						str += "<image style='cursor:hand;' onclick='watchJS_toolbar("+JSON2.stringify(rowData)+")' src='images/ioc-editor.gif' title='修改'>"
						return str;
					}}
				]],
				onBeforeLoad:function(){
					$(this).datagrid('rejectChanges');
				},
				onClickRow:function(rowIndex){
					if (lastIndex_toolbarButton_table != rowIndex){
						$('#fm_listpageset_toolbarButton_table').datagrid('endEdit', lastIndex_toolbarButton_table);
						$('#fm_listpageset_toolbarButton_table').datagrid('beginEdit', rowIndex);
					}
					lastIndex_toolbarButton_table = rowIndex;
				}
			
			})
			
			var initXmlUtils = new XmlUtils({dataType:'json'}); 
			initXmlUtils.loadXmlString(formSettings);
			var root_init = initXmlUtils.getRoot();
			var listButtonsNode_init = initXmlUtils.getChildNodeByTagName(root_init,'Buttons');	
			var listtoolbarButtonsNode_init = initXmlUtils.getChildNodeByTagName(listButtonsNode_init,'ToolbarButtons');
			var listtoolbarButtonNode_arr;
			if(listtoolbarButtonsNode_init == undefined){
			}else{
				listtoolbarButtonNode_arr =  initXmlUtils.getChildNodes(listtoolbarButtonsNode_init);
				var rows = new Array();
				for(var i=0;i<listtoolbarButtonNode_arr.length;i++){
					rows[i]={
						buttonname:listtoolbarButtonNode_arr[i].getAttribute('buttonname'),
						buttonicon:listtoolbarButtonNode_arr[i].getAttribute('buttonicon'),
						buttonrules:listtoolbarButtonNode_arr[i].getAttribute('buttonrules'),
						isshow:listtoolbarButtonNode_arr[i].getAttribute('isshow'),
						operateButton:''
					};
				}
				$('#fm_listpageset_toolbarButton_table').datagrid('loadData',{"total":rows.length,"rows":rows});
			}
			
		});
	</script>
  </head>
  
  <body>
    	<table id="fm_listpageset_toolbarButton_table"></table>
  </body>
</html>
