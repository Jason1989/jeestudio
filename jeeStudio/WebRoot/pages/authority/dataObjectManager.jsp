<%@page language="java" contentType="text/html; charset=UTF-8" %>	
<%
	String roleId=request.getParameter("roleId");
%>
<script type="text/javascript">		
		var formJson='${formJson}'; //判断得到返回的FieldGrant
		var resultListColumnJson="";
		var tableNameSplit='${tableNameSplit}';//得到表名称数组,
		//对数据对象表名行进行选中时操作
		function flushFormSelectTableDataClick(e,d){
			var b = $("#bd_datatablelist_table").datagrid("getSelections");
			if(b.length>0){	
				var c = "";
				for (var a = 0; a < b.length; a++) {
					c += b[a]["id"]+",";
				}
				var url='datacolumn/dataColumn!toimportByArrayId.action';
				$("#db_datacolumn_import_inuse").datagrid({
					queryParams:{dataTableId:c,isTemp:'0'},
					url:url
				});//直接加载json对象		
			}	
		}	
		//对数据表名的列进行取消的时候的操作
		function unFlushFormSelectTableDataClick(e,d){
			var b = $("#bd_datatablelist_table").datagrid("getSelections");
			 if(b){	
				if(b.length>0){	
					var c = "";
					for (var a = 0; a < b.length; a++) {
						c += b[a]["id"]+",";
					}
					var url='datacolumn/dataColumn!toimportByArrayId.action';
					$("#db_datacolumn_import_inuse").datagrid({
						queryParams:{dataTableId:c,isTemp:'0'},
						url:url
					});//直接加载json对象				
				}else {
					$.post("form/form!clearColumnInfoListGrid.action",function (f) {
						$("#db_datacolumn_import_inuse").datagrid("loadData", f);
						});	
				}
			}			
		}
		//数据对象列表加载成功后，进行操作
		function datatableListLoadSuccess(){
	        if(formJson!=""){
	        
	        	resultListColumnJson=eval('('+formJson+')');  
		    	//alert(formJson);
		      	//alert(resultListColumnJson["resultListColumnJson"][1]["fieldname"]);
		      	var tableNameArray=tableNameSplit.split(",");	
		       	var b = $("#bd_datatablelist_table").datagrid("getRows");
		       	if(b){
		       		for(var i=0;i<tableNameArray.length;i++){
		       			for(var j=0;j<b.length;j++){
		       				if(tableNameArray[i]==b[j]["name"]){
		       					 $("#bd_datatablelist_table").datagrid("selectRecord", b[j]["id"]);
		       				}
		       			}
		       		}
		       	}
		       	var d = $("#bd_datatablelist_table").datagrid("getSelections");//判断是否有选中的表 如果有，给个请求，将其清空
		       	if(d){
		       		if(d.length>0){
		       			$.post("form/form!clearColumnInfoListGrid.action",function (f) {
						$("#db_datacolumn_import_inuse").datagrid("loadData", f);
						});	
		       		}
		       	} 
		       	 	
	       	} 	
		}
		//数据对象列表所属的列加载成功后，进行操作
		function dataColumnListLoadSuccess(){
			//判断默认：将其某表下的字段列 数据库存在的，进行相应显示
		       		var columnData = $("#db_datacolumn_import_inuse").datagrid("getRows");	
		       		//var columnDataSel = $("#db_datacolumn_import_inuse").datagrid("getSelections"); 
		       	//作用为：获得表名称的数组.判断选中的表 是否在数据库中，如果在，执行相应的列选中，如果不在，全部选中	
		       
		       	/**	var b = $("#bd_datatablelist_table").datagrid("getSelections");//获得表对象选中的名称 
			       	 for(var z=0;z<b.length;z++){	
			       		for(var j=0;j<tableNameArray.length;j++){
			       				if(tableNameArray[j]==b[z]["name"]){			       								       					
			       				}
			       			}	 
		       		}**/				
		       	if(formJson!=""){	
		       	  	var tableNameArray=tableNameSplit.split(",");	
			       		if(columnData){
				       			for(var i=0;i<columnData.length;i++){
				       				for(var j=0;j<resultListColumnJson["resultListColumnJson"].length;j++){
				       				  for(var z=0;z<tableNameArray.length;z++){
						       				  if(resultListColumnJson["resultListColumnJson"][j]["tablename"]==columnData[i].dataTable.name && columnData[i]["name"]==resultListColumnJson["resultListColumnJson"][j]["fieldname"]){
						       				  	$("#db_datacolumn_import_inuse").datagrid("selectRecord", columnData[i]["id"]);
						       				  	break;
						       				  }
					       				}
			       					}			       				
			       				}
			       		}
		       		
		       		}
		
		}
		
		
		$(function(){
		    	//数据对象分组数
		    var dataObjectTypeData=[{id:-1,text:"请选择"},{id:1,text:"表"},{id:2,text:"视图"}];
			$('#dataobject_menu').tree({			
			 	url: 'datatable/dataObjectGroup!getAllItemByParentId.action?parentId=-1',height:350,width:200,		 		 		 	
				onClick:function(node){
					 var url='datatable/dataTable!listFildGrant.action?groupId='+node.id;
				 	 $("#bd_datatablelist_table").datagrid({url:url});//直接加载json对象
								
				},
				onBeforeExpand:function(node){
					$('#dataobject_menu').tree('options').url = "datatable/dataObjectGroup!getAllItemByParentId.action?parentId=" + node.id;
				}
			});
			//数据对象列表
			 $('#bd_datatablelist_table').datagrid({
		                iconCls: 'icon-save',
		                //width:1000,
		                height: 358,
		                nowrap: false,
		                striped: true,
		                autoFit: true,
		                fitColumns:true,
		                sortName: 'code',
		               // url: 'datatable/dataTable!list.action',	
		                sortOrder: 'desc',
		                idField: 'id',
		                pageSize:10,
		                frozenColumns: [[
							{ field: 'ck', checkbox: true,
							}
						]],
		                columns: [[
		                	{field: 'name',title: '名称',width: 200,sortable: true,align:'left',
		                	},
		                	{field: 'dataSource',title: '数据源',width: 180,sortable: true,align:'left',formatter:function(value,rec){
								    if(rec.dataSource && rec.dataSource != null){
								    	return rec.dataSource.name;
								    }else{
										return "";
									}
								}},
		                	{field: 'type',title: '对象类型',width: 130,align:'center',
		                		formatter:function(value){
		                			for(var i=0; i<dataObjectTypeData.length; i++){
										if (dataObjectTypeData[i].id == value) return dataObjectTypeData[i].text;
									}
									return "表";
								}
							}
						]],onSelect:flushFormSelectTableDataClick,onUnselect:unFlushFormSelectTableDataClick,
		                pagination: true,
		                rownumbers: true,onLoadSuccess:function (g) {
	                		datatableListLoadSuccess();
	                	}
		               // singleSelect: true,
		            });
		            //数据对象就绪字段
		           $("#db_datacolumn_import_inuse").datagrid({ 
		           	height:358, 
		           	nowrap:false, 
		           	striped:true, 
		           	autoFit:true, 
		           	idField:"id", 
		           	frozenColumns:[[
		           		{field:"ck", checkbox:true}]], 
		           		columns:[[{field:"name", title:"\u5217\u540d", width:150, sortable:true}, 
		           		{field:"datatablenname", title:"\u8868\u540d", width:100,
		           		 sortable:true, 
		           		 formatter:function (b, c) {
							var a = "";
							if (c.dataTable && c.dataTable != null) {
								a = c.dataTable.name;
							}
							return a;
					}},
		           		{field:"title", title:"\u4e2d\u6587\u540d", width:100}
		           		]], rownumbers:true,onLoadSuccess:function (g) {
	                		//初始化后，对其所属的列 进行全部选中操作，此处进行判断，目前全部选中
	                		//$("#db_datacolumn_import_inuse").datagrid("selectAll");
	                		dataColumnListLoadSuccess();
	                	}					
					});			 
		});
				//保存操作
				function fmFormDataObjectSubmit(){
					var b = $("#db_datacolumn_import_inuse").datagrid("getSelections");
						if(b){
							if(b.length<1){ alert("无法保存，请选择相应的表和列");
								return;
							}
						}
					//得到所有列的ID，和该列所属表的名称 条件：选中的CH=TRUE	
						var tableObject = $("#bd_datatablelist_table").datagrid("getSelections");
						var tableArray=new Array();//用于存放选中的 表名的数组
						var tableName=""; //用于在action中取得的 表名 数组 ,号隔开
						for(var i=0;i<tableObject.length;i++){
							tableArray[i]=tableObject[i]["name"]; 
							tableName+=tableObject[i]["name"]+",";
						}
						//得到表名的顺序进行排序
						var tableNameStr="";
						var fieldString="";//用于存放数组的列。根据表名 
						for (var j = 0; j < b.length; j++) { //多的循环少的
							
							for(var i=0;i<tableArray.length;i++){
								if(b[j].dataTable.name==tableArray[i]){
									fieldString+=b[j]["name"]+tableArray[i];//列名+表名 
									if(tableNameStr.indexOf(b[j].dataTable.name)<0){
										tableNameStr+=b[j].dataTable.name+",";//依次得到表名称，用,号分隔
								}
								}else{ 	
									continue;}
							}
						}
					//进行插入数据库
					var url='authority/fieldGrant!addFieldGrant.action?fieldString='+fieldString+"&tableName="+tableNameStr+"&roleId=<%=roleId%>";
					$.ajax({
				    url: url,
				    type: 'post',
					    success: function(data){
								$.messager.alert("提示", '保存成功！');								
				 		}
					});	
					
					
				}
</script>
	 <div class="easyui-layout" collapsible="true" fit="true" border="false">
				<div region="west" split="true" border="false" style="width:220px;padding:5px;height:380px">
					</br>数据对象分组</br>
				    <div class="easyui-panel"  icon="icon-organisation"  border="true" headerCls="header_cls" style="padding:3px ;width:210px;height:364px">	 
								 <ul id="dataobject_menu"></ul>
				    </div>
				</div>
				<div region="center"   style="overflow:hidden; width: 600px;padding:5px 0 5px 0;" border="false">
				
	<table width="99%" border="0" cellpadding="1" cellspacing="0" class="table_form1" height="380px;">		
		 <tr  ></br></tr>
		 <tr>		
			<td width="10px"></td>
			<td width="300px;">数据对象</td>
			
			<td width="10px"></td>
			<td>数据对象字段列</td>
			<td width="10px"></td>
		</tr>
		<tr>
			
			<td></td>
			<td style="background:#dedede;">
			   <table border="0" cellpadding="0" cellspacing="0" width="100%" style="background:#ffffff;" height="360px;">					
					<tr valign="top">
						<td>
							<div class="easyui-panel" fit="true" border="false" style="margin:0px;">
								<table id="bd_datatablelist_table"></table>
							</div>
						</td>
					</tr>					
				</table>
			</td>
			
			<td></td>
			<td style="background:#dedede;">
				<table border="0" cellpadding="0" cellspacing="0" width="100%" style="background:#ffffff;" height="360px;">					
					<tr valign="top">
						<td>
							<div class="easyui-panel" fit="true" border="false" style="margin:0px;">
								<table id="db_datacolumn_import_inuse"></table>
							</div>
						</td>
					</tr>					
				</table>
			</td>	
			<tr >
					<td colspan="4" align="center">
					
					<%
						if(roleId!=null){
					%>
						
						<a href="javascript:;" class="easyui-linkbutton" icon="icon-save"
							onclick="fmFormDataObjectSubmit()">保存</a>
						<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="parent.$('#role_menu_configwindow').window('close');">关闭</a>
					<%} %>
						
					</td>
			</tr>	
	</table>
	
	
	
				
				</div>
     </div>

 
	
