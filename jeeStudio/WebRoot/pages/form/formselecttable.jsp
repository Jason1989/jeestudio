<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>选择表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
		<link rel="stylesheet" type="text/css" href="styles.css">
	-->

		<script type="text/javascript" src="js/basedata.common.js"></script>
		<script type="text/javascript" src="js/form.selectobject.js"></script>
		<script>		
		var mainObjectId = '${dataObjectId}';
		var mainObjectName='${mainObjectName}';		
		var xmlOperateType = '${xmlOperateType}';		
		var updateTableNode;
		var updateGetTableNode;
		var updateFromTablesNode;
		var updateXmlUtils;
		var stringSplitId="";
		var resultUnCheckField='${resultUnCheckField}';
		var formJson='${formJson}';
		var resultListColumnJson;
		var flagGlobal="1";//用于判断是否是初始化的
		var flagIsNull="0";//用于判断是否有选中表的存在。0为不存在，1为存在
		$(function (){	  
			if("" !=resultUnCheckField && resultUnCheckField !=null){
				resultListColumnJson=eval('('+formJson+')');
				flagIsNull="1";		
			}	
			if(xmlOperateType == 'update'){
				updateXmlUtils = new XmlUtils({dataType:'json'}); 
				updateXmlUtils.loadXmlString(updateFormXML);
				updateTableNode = updateXmlUtils.getNodesByXpath('//Form/DataMapping/DataSet/Table',1);
				if(updateTableNode && updateTableNode != null){
					if(updateTableNode.length){
						 if(updateTableNode.length > 0){
						 	updateGetTableNode = updateXmlUtils.getChildNodeByTagName(updateTableNode,'GetTable');
						 }
					}else{
						updateGetTableNode = updateXmlUtils.getChildNodeByTagName(updateTableNode,'GetTable');
					}
				}
				if(updateGetTableNode && updateGetTableNode != null){
					updateFromTablesNode = updateXmlUtils.getChildNodeByTagName(updateGetTableNode,'FromTables');
				}
				//alert(updateXmlUtils.toString(updateTableNode));
			}
			if(typeof(updateFromTablesNode) != "undefined"){
				//得到表名称
				var c = updateXmlUtils.getChildNodes(updateFromTablesNode);			 			
				if (c) {
					var b = c.length;
					for (var e = 0; e < b; e++) {
						var h = c[e];
						var a = updateXmlUtils.getChildNodeByTagName(h, "TableName");
						stringSplitId+=updateXmlUtils.getInnerText(a)+",";
						}
					}		
			}		
			  fm_formselecttable_tablesNo();
               fm_formselecttable_tablesYes();		 
		      formSelectTablePageInit123(flagIsNull);   
			//防止初始化的时候  显示未初始化的界面，显的混乱
			$("#fm_selecttable_filter").show();
		});
		//-------------过滤条件END------------------
		
		function fm_formselecttable_tablesNo(){
			$('#fm_formselecttable_tablesNo').datagrid({
	                nowrap: false,
	                url:'form/form!dataRowsList.action?dataObjectId='+stringSplitId+'&mainObjectId='+mainObjectId,
	                pageNumber:1,
	                pageSize:10,
	                width: 350,
	                height: 370,
	                pagination: true,
	                rownumbers: true,
	                 frozenColumns: [[{
	                    field: 'name',
	                    title: '表名',
	                    width: 250,
	                    height: 300,
	                    sortable: true
	                },{
	                     field: 'id',title: '操作',width:40,align:'center',
                		formatter:function(value,rec,rowIndex){	     			         			
                			var links = '<img src="images/right.png" style="cursor:hand;" onclick=fmPagesetDataGridColumnSortMove("rightMove","'+rec.id+'","'+rec.name+'") title="左移"></img>&nbsp;|&nbsp;';
							return links;
                		}
	                }
	                ]], 
                });
		}
	function fm_formselecttable_tablesYes(){
	   $('#fm_formselecttable_tablesYes').datagrid({
	                nowrap: false,
	                url:'form/form!dataRowsListOnlyCheck.action?dataObjectId='+stringSplitId+'&mainObjectId='+mainObjectId,
	                 pageNumber:1,
	                pageSize:10,
	                width: 350,
	                height: 370,
	                pagination: true,
	                rownumbers: true,onSelect:flushFormSelectTableColumnsDataClick,
	                 frozenColumns: [[{
	                    field: 'id',title: '操作',width: 40,align:'center',
                		formatter:function(value,rec,rowIndex){	 
                			var links = '<img src="images/left.png" style="cursor:hand;" onclick=fmPagesetDataGridColumnSortMove("leftMove","'+rec.id+'","'+rec.name+'") title="左移"></img>&nbsp;|&nbsp;';
							return links;
                		}},
                		{
	                    field: 'name',title: '表名',width: 340,sortable: true, 
	                }]],onLoadSuccess:function (g) {
	                		flushFormSelectTableColumnsData3();
	                	}
	                });	          	 
	}	
	//表格的左右 移动操作事件	
	function fmPagesetDataGridColumnSortMove(flag,tableId,fieldName){ 
	    if(flag=="leftMove"){ //如果单击往左移 将stringSplitId中的该值移掉
		       if(mainObjectName==null || mainObjectName==""){
		       	   if(mainObjectId==tableId){
		       		alert("此表为主关系表，必须选中");
		       		return;
		       		}   
		       	}else{
			       if(mainObjectName==fieldName){
			       		alert("此表为主关系表，必须选中");
			       		return;
			       }   
		       }
	       var fieldStr=fieldName+",";
	       var newSplitId=stringSplitId.replace(fieldStr,"");
	       stringSplitId=newSplitId; 
	    }
	    if(flag=="rightMove"){ //如果单击往右移 将stringSplitId中的该值添加
	        stringSplitId+=fieldName+",";
	    }
	 fm_formselecttable_tablesYes();
	 fm_formselecttable_tablesNo();
	 }	
		
function formSelectTablePageInit123(flagIsNull) {
	 if(flagIsNull=="1"){
		setTimeout("fmFormselecttableTablesLoadData()", 1);
	}
		$("#fm_formselecttable_columns").datagrid({width:270, height:370, nowrap:false, striped:true, autoFit:true, idField:"id", frozenColumns:[[{field:"ck", checkbox:true}]], columns:[[{field:"name", title:"\u5217\u540d", width:100, sortable:true}, {field:"title", title:"\u4e2d\u6587\u540d", width:60}, {field:"datatablenname", title:"\u8868\u540d", width:60, sortable:true, formatter:function (b, c) {
			var a = "";
			if (c.dataTable && c.dataTable != null) {
				a = c.dataTable.name;
			}
			return a;
		}}]], rownumbers:true});
		$("#fm_selecttable_filter").appendTo("body").window({title:"\u8fc7\u6ee4\u6761\u4ef6", width:800, modal:true, shadow:false, closed:true, closable:false, collapsible:false, height:550});
		$("#fm_selecttable_filter_joinitemadd").linkbutton();
		$("#fm_selecttable_filter_paraitemdelete_0").linkbutton();
		$("#fm_selecttable_filter_joinitemdelete_0").linkbutton();
		$("#fm_selecttable_filter_paramitemadd").linkbutton();
		$("#fm_selecttable_filter_close").linkbutton();
		$("#fm_selecttable_filter_submit").linkbutton();
		$("#fm_selecttable_jointype_0").combobox({valueField:"id", textField:"text", listWidth:60, editable:false, data:fmSelecttableJoinTypeData});
		$("#fm_selecttable_joinoper_0").combobox({valueField:"id", textField:"text", listWidth:54, editable:false, data:fmSelecttableJoinOperData});
		$("#fm_selecttable_paraoper_0").combobox({valueField:"id", textField:"text", listWidth:54, editable:false, data:fmSelecttableJoinOperData});
		$("#fm_selecttable_parajoinstyle_0").combobox({valueField:"id", textField:"text", editable:false, data:fmSelecttableParaJoinStyleData});
		$("#fm_selecttable_paraobjtype_0").combobox();
		$("#fm_selecttable_joinobj_0").combobox();
		$("#fm_selecttable_mainobjcol_0").combobox();
		$("#fm_selecttable_joinobjcol_0").combobox();
		$("#fm_selecttable_paraobj_0").combobox();
		$("#fm_selecttable_paraobjcol_0").combobox();
		$("#fm_selecttable_parajoinobj_0").combobox();
		$("#fm_selecttable_parajoinobjcol_0").combobox();
		setTimeout("initTableColumnSelectedDataFromXML()", 400);
		
}
		//未选中的进行选中操作
function flushFormSelectTableColumnsData1(e, d) {
	var b = $("#fm_formselecttable_tablesNo").datagrid("getSelections");
		var c = "";
		for (var a = 0; a < b.length; a++) {
			c += c == "" ? "'" + b[a]["id"] + "'" : ",'" + b[a]["id"] + "'";
		}	
		$.post("form/form!getColumnInfoListByDoIds.action", {dataTablesIds:c}, function (f) {
			$("#fm_formselecttable_columns").datagrid("clearSelections");
			$("#fm_formselecttable_columns").datagrid("loadData", f);
			$("#fm_formselecttable_columns").datagrid("selectAll");
		});	
}
//初始化后，对其所属的列 进行全部选中操作
function flushFormSelectTableColumnsData3() {
	var b = $("#fm_formselecttable_tablesYes").datagrid("getRows");
	if(b==0){
		var sumColumn1=$("#fm_formselecttable_columns").datagrid("getRows");		
		if(sumColumn1.length>0){
			$.post("form/form!clearColumnInfoListGrid.action",function (f) {
			$("#fm_formselecttable_columns").datagrid("loadData", f);
			});	
		}
	}
	if(b.length>0){
			var c = "";
			for (var a = 0; a < b.length; a++) {
				c += c == "" ? "'" + b[a]["id"] + "'" : ",'" + b[a]["id"] + "'";
			}
				$.post("form/form!getColumnInfoListByDoIds.action", {dataTablesIds:c}, function (f) {
					$("#fm_formselecttable_columns").datagrid("clearSelections");
					$("#fm_formselecttable_columns").datagrid("loadData", f);
					//$("#fm_formselecttable_columns").datagrid("selectAll");
					var sumColumn=$("#fm_formselecttable_columns").datagrid("getRows");
				var isCheck=1;
				for(var i=0;i<sumColumn.length;i++){
					if(typeof(resultListColumnJson)!="undefined"){
						for(var j=0;j<resultListColumnJson["resultListColumn"].length;j++){
							//alert(sumColumn[i]["name"]+"="+sumColumn[i]["dataTable"]["name"]+"||"+resultListColumnJson["resultListColumn"][j]["dataTable"]["name"]);
							if(resultListColumnJson["resultListColumn"][j]["dataTable"]["name"]==sumColumn[i]["dataTable"]["name"]){		
									   if(resultListColumnJson["resultListColumn"][j]["name"]==sumColumn[i]["name"]){
										 isCheck=0; break;
									   }else{
									   	isCheck=1;
									   }   			
							}			
						}	
					}		
					if(isCheck==1){ $("#fm_formselecttable_columns").datagrid("selectRecord", sumColumn[i]["id"]);}		
				}
			});
	}		

}
//用鼠 标单击时将其表的 属性值显示
function flushFormSelectTableColumnsDataClick(e, d) {
	var b = $("#fm_formselecttable_tablesYes").datagrid("getSelections");
	if(b.length>0){	
		var c = "";
		for (var a = 0; a < b.length; a++) {
			c += c == "" ? "'" + b[a]["id"] + "'" : ",'" + b[a]["id"] + "'";
		}
		$.post("form/form!getColumnInfoListByDoIds.action", {dataTablesIds:c}, function (f) {
			$("#fm_formselecttable_columns").datagrid("clearSelections");
			$("#fm_formselecttable_columns").datagrid("loadData", f);
			$("#fm_formselecttable_columns").datagrid("selectAll");
		});
	}	
}

function fmSelectTableShowClick() {
	var tableName=$("#selectTableNameId").val();
	$.post("form/form!findAllDataObjectLikeName.action", {dataObjectName:tableName, dataObjectId:mainObjectId}, function (data) {
		var dataObj = eval("(" + data + ")");
		$("#fm_formselecttable_tablesNo").datagrid("loadData", dataObj);
	});
}

	
	</script>
	</head>
	<body>
		<table style="font-size: 12px;" width="100%">
			<tr>
				<td>
					未选中数据对象列表
					<input type="text" size="30" id="selectTableNameId"
						/>
						<a href="javascript:;" onclick="fmSelectTableShowClick()">查询</a>
				</td>
				<td>
					已选中数据对象列表
				</td>
				<td>
					已选中数据列列表
				</td>
			</tr>
			<tr>
				<td>
					<table id="fm_formselecttable_tablesNo"></table>
				</td>

				<td>
					<table id="fm_formselecttable_tablesYes"></table>
				</td>
				<td>
					<table id="fm_formselecttable_columns"></table>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-edit"
						onclick="fmSelecttableFilterOpen()">过滤条件</a>
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-save"
						onclick="fmFormSelectTableSubmit()">保存</a>
					<!-- 选表选列保存 -->
					<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel"
						onclick="$('#fm_formadd_selecttable').window('close');">关闭</a>
					<!-- 
					<input type="button" onclick="fmSelecttableFilterOpen()" value="过滤条件"/>
					<input type="button" onclick="fmFormSelectTableSubmit()" value="确定"/>
				 -->
				</td>
			</tr>
		</table>
		<div id="fm_selecttable_filter" class="easyui-window" 
			style="padding: 5px; display: none;">
			<table style="font-size: 12px;" width="100%">
				<tr>
					<td>
						<table width="100%" border="0" cellpadding="2" cellspacing="1"
							style="background: #dddddd;">
							<tr style="background: #ffffff;">
								<td colspan="7">
									<input type="checkbox" id="fm_selecttable_joincheck" />
									对象关联&nbsp;&nbsp;&nbsp;&nbsp;
									<!-- 
								<input type="button" value="添加" onclick="fmSelecttableFilterJoinAddItem('fm_selecttable_filterjoin_tbody')"/>
								 -->
									<a id="fm_selecttable_filter_joinitemadd" href="javascript:;"
										plain="true" class="easyui-linkbutton" icon="icon-add"
										onclick="fmSelecttableFilterJoinAddItem()">添加</a>
								</td>
							</tr>
							<tr style="background: #eeeeee;">
								<td>
									关联类型
								</td>
								<td>
									操作符
								</td>
								<td>
									主对象
								</td>
								<td>
									列
								</td>
								<td>
									关联对象
								</td>
								<td>
									列
								</td>
								<td></td>
							</tr>
							<tbody id="fm_selecttable_filterjoin_tbody">
								<tr style="background: #ffffff; display: none;"
									id="fm_selecttable_joindefault_tr">
									<td width="8%">
										<select id="fm_selecttable_jointype_-1" style="width: 60px;"></select>
									</td>
									<td width="8%">
										<select id="fm_selecttable_joinoper_-1" style="width: 55px;"></select>
									</td>
									<td width="17%">
										<!-- <span id="fm_selecttable_mainobj_-1"></span> -->
										<select id="fm_selecttable_mainobj_-1" style="width: 120px;"></select>
									</td>
									<td width="17%">
										<select id="fm_selecttable_mainobjcol_-1"
											style="width: 120px;"></select>
									</td>
									<td width="17%">
										<select id="fm_selecttable_joinobj_-1" style="width: 120px;"></select>
									</td>
									<td width="17%">
										<select id="fm_selecttable_joinobjcol_-1"
											style="width: 120px;"></select>
									</td>
									<td align="center">
										<a id="fm_selecttable_filter_joinitemdelete_-1" plain="true"
											href="javascript:;" class="easyui-linkbutton"
											icon="icon-remove" onclick="fmPagesetDynamicDeleteItem(this)">删除</a>
									</td>
								</tr>
								<tr style="background: #ffffff;">
									<td width="8%">
										<select id="fm_selecttable_jointype_0" style="width: 60px;"></select>
									</td>
									<td width="8%">
										<select id="fm_selecttable_joinoper_0" style="width: 55px;"></select>
									</td>
									<td width="17%">
										<!-- <span id="fm_selecttable_mainobj_0"></span> -->
										<select id="fm_selecttable_mainobj_0" style="width: 120px;"></select>
									</td>
									<td width="17%">
										<select id="fm_selecttable_mainobjcol_0" style="width: 120px;"></select>
									</td>
									<td width="17%">
										<select id="fm_selecttable_joinobj_0" style="width: 120px;"></select>
									</td>
									<td width="17%">
										<select id="fm_selecttable_joinobjcol_0" style="width: 120px;"></select>
									</td>
									<td align="center">
										<a id="fm_selecttable_filter_joinitemdelete_0" plain="true"
											href="javascript:;" class="easyui-linkbutton"
											icon="icon-remove" onclick="fmPagesetDynamicDeleteItem(this)">删除</a>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table style="background: #dddddd;" width="100%" border="0"
							cellpadding="2" cellspacing="1">
							<tr style="background: #ffffff;">
								<td colspan="7">
									<input type="checkbox" id="fm_selecttable_paracheck" />
									过滤条件&nbsp;&nbsp;&nbsp;&nbsp;
									<a id="fm_selecttable_filter_paramitemadd" href="javascript:;"
										plain="true" class="easyui-linkbutton" icon="icon-add"
										onclick="fmSelecttableFilterParamAddItem()">添加</a>
								</td>
							</tr>
							<tr style="background: #eeeeee">
								<td>
									联接方式
								</td>
								<td>
									操作符
								</td>
								<td>
									对象
								</td>
								<td>
									列
								</td>
								<td>
									条件类型
								</td>
								<td>
									参数
								</td>
								<td></td>
							</tr>
							<tbody id="fm_selecttable_filterparam_tbody">
								<tr style="background: #ffffff; display: none;"
									id="fm_selecttable_paradefault_tr">
									<td width="8%">
										<span id="fm_selecttable_parajoinstyletd_-1"
											style="display: none;"> <select
												id="fm_selecttable_parajoinstyle_-1" style="width: 35px;"></select>
										</span>
									</td>
									<td width="8%">
										<select id="fm_selecttable_paraoper_-1" style="width: 55px;"></select>
									</td>
									<td width="17%">
										<select id="fm_selecttable_paraobj_-1" style="width: 120px;"></select>
									</td>
									<td width="17%">
										<select id="fm_selecttable_paraobjcol_-1"
											style="width: 120px;"></select>
									</td>
									<td width="8%">
										<select id="fm_selecttable_paraobjtype_-1"
											style="width: 55px;"></select>
										<!-- <input type="radio" name="fm_selecttable_paratype_-1" value="0" checked/>变量&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="fm_selecttable_paratype_-1" value="1"/>常量 -->
									</td>
									<td id="fm_selecttable_paravaluetd_-1" width="30%"
										style="display: ">
										<input id="fm_selecttable_paravalue_-1" type="text" size="15" />
									</td>
									<td id="fm_selecttable_parajoinobjtd_-1" width="33%"
										style="display: none">
										<select id="fm_selecttable_parajoinobj_-1"
											style="width: 120px;"></select>
										<select id="fm_selecttable_parajoinobjcol_-1"
											style="width: 120px;"></select>
									</td>
									<td align="center">
										<a id="fm_selecttable_filter_paraitemdelete_-1"
											href="javascript:;" class="easyui-linkbutton" plain="true"
											icon="icon-remove" onclick="fmPagesetDynamicDeleteItem(this)">删除</a>
									</td>
								</tr>
								<tr style="background: #ffffff;">
									<td width="8%">
										<span id="fm_selecttable_parajoinstyletd_0"
											style="display: none;"> <select
												id="fm_selecttable_parajoinstyle_0" style="width: 55px;"></select>
										</span>
									</td>
									<td width="8%">
										<select id="fm_selecttable_paraoper_0" style="width: 55px;"></select>
									</td>
									<td width="17%">
										<select id="fm_selecttable_paraobj_0" style="width: 120px;"></select>
									</td>
									<td width="17%">
										<select id="fm_selecttable_paraobjcol_0" style="width: 120px;"></select>
									</td>
									<td width="8%">
										<select id="fm_selecttable_paraobjtype_0" style="width: 35px;"></select>
										<!-- <input type="radio" name="fm_selecttable_paratype_0" value="0" checked/>变量&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio" name="fm_selecttable_paratype_0" value="1"/>常量 -->
									</td>
									<td id="fm_selecttable_paravaluetd_0" width="30%"
										style="display: ">
										<input id="fm_selecttable_paravalue_0" type="text" size="15" />
									</td>
									<td id="fm_selecttable_parajoinobjtd_0" width="33%"
										style="display: none">
										<select id="fm_selecttable_parajoinobj_0"
											style="width: 120px;"></select>
										<select id="fm_selecttable_parajoinobjcol_0"
											style="width: 120px;"></select>
									</td>
									<td align="center">
										<a id="fm_selecttable_filter_paraitemdelete_0"
											href="javascript:;" class="easyui-linkbutton" plain="true"
											icon="icon-remove" onclick="fmPagesetDynamicDeleteItem(this)">删除</a>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td align="center">
						<!-- 过滤条件保存 -->
						<a id="fm_selecttable_filter_submit" href="javascript:;"
							class="easyui-linkbutton" icon="icon-save"
							onclick="fmSelecttableFilterSubmit()">保存</a>
						<a id="fm_selecttable_filter_close" href="javascript:;"
							class="easyui-linkbutton" icon="icon-cancel"
							onclick="fmSelecttableFilterClose()">关闭</a>

						<!-- 
					<input type="button" onclick="fmSelecttableFilterSubmit()" value="确定"/>				
					<input type="button" onclick="fmSelecttableFilterClose()" value="关闭"/>
					 -->
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
