<%@page language="java" contentType="text/html; charset=UTF-8" %>	

<div   class="easyui-panel" title="资源管理" fit=true  >
	<input id="gridOrSystemEdit" type="hidden" value="0" />
	<table width="99%" border="0" cellpadding="1" cellspacing="0" class="table_form1" height="340px;">		
		 <tr height="20px" ></tr>
		 <tr>
			<td width="10px"></td>
			<td width="270px;">子系统 
				<a id="systemMenu_add" class="easyui-linkbutton" plain=true iconCls="icon-add" onclick="addSystem('resource_edit_window');";   >添加</a>
				<a id="systemMenu_delete" class="easyui-linkbutton" plain=true  iconCls="icon-remove" onclick="deleteSystem('systemAutorityList');"  >删除</a>
				<a id="systemMenu_edit" class="easyui-linkbutton" plain=true  iconCls="icon-edit" onclick="editSystem('resource_edit_window','systemAutorityList');" >修改</a>
			</td>
			
			<td width="10px"></td>
			<td width="300px;">资源树</td>
			
			<td width="10px"></td>
			<td>资源列表</td>
			<td width="10px"></td>
		</tr>
		<tr>
			<td></td>
			<td style="background:#dedede;">
				<table border="0" cellpadding="0" cellspacing="0" width="100%" style="background:#ffffff;" height="340px;">					
					<tr valign="top">
						<td>
							<ul id="systemAutorityList"></ul>
							<script type="text/javascript">
								 autorityTreeForSystem('${systemJson}','systemAutorityList');
							</script>
						</td>
					</tr>
				</table>
			</td>
			
			<td></td>
			<td style="background:#dedede;">
			   <table border="0" cellpadding="0" cellspacing="0" width="100%" style="background:#ffffff;" height="340px;">					
					<tr valign="top">
						<td>
							<div class="easyui-panel" fit="true" border="false" style="margin:0px;">
								<ul id="systemChildResource"></ul>
							</div>
						</td>
					</tr>					
				</table>
			</td>
			
			<td></td>
			<td style="background:#dedede;">
				<table border="0" cellpadding="0" cellspacing="0" width="100%" style="background:#ffffff;" height="340px;">					
					<tr valign="top">
						<td>
							<table id="grid_button"></table>
						</td>
					</tr>	
					<tr valign="center">
						<td>
								<table id="autority_grid_cloumn"></table>
						</td>
					</tr>	
					<script>
					
			$(function (){
			
				var resType = [
						  		{id:"0",text:"子系统"},
						  		{id:"1",text:"菜单"},
						  		{id:"6",text:"扩展页面"},
						  		{id:"2",text:"页面"},
						  		{id:"3",text:"按钮"},
						  		{id:"4",text:"数据列"},
						  		{id:"5",text:"DIV块"}
				  			];
			
				var isMenu = [
					  		{id:"1",text:"是"},
					  		{id:"0",text:"否"}
			  			];
			
				var lastIndex;
				var gridId='autority_grid_cloumn';
					$('#'+gridId).datagrid({
						iconCls:'icon-grid',
						fit:true,
						border:false,
						singleSelect:true,
						pagination:true,
						fitColumns:true,
						columns:[[
							{field:'text',title:'资源名称',align:'center',width:150},
							{field:'resType',title:'资源类型',width:100,
								formatter:function(value){
									for(var i=0; i<resType.length; i++){
										if (resType[i].id == value) return resType[i].text;
									}
									return value;
								}
							
							},
							{field:'isMenu',title:'是否菜单项',width:80,
								formatter:function(value){
									for(var i=0; i<isMenu.length; i++){
										if (isMenu[i].id == value) return isMenu[i].text;
									}
									return value;
								}
							},
							{field:'xxx',title:'操作',align:'center',width:100,
								formatter:function(value,rowData,rowIndex){
								    var dataID=rowData.id;
									var opertorString='<img  style="cursor:hand;" title="编辑"  onclick="editGridResource(\'resource_edit_window\',\''+dataID+'\');" sytle="cursor:pointer;"  width="12px" src="<%= request.getContextPath()%>/images/ioc-editor.gif" />';
										opertorString=opertorString+'&nbsp;&nbsp;&nbsp;&nbsp;<img  style="cursor:hand;"  onclick="deleteGridResource(\''+dataID+'\',\''+rowData.parentID+'\')" title="删除" sytle="cursor:pointer;"  width="12px" src="<%= request.getContextPath()%>/images/ioc-delete.gif" />'
									return opertorString;
								}
							}
						]],
						toolbar:[{
							text:'添加',
							iconCls:'icon-add',
							titleCls:'link_btn_color',
							handler:function(){
								addGridResource('resource_edit_window');
							}
						}]
					});	
			
			});		
					</script>		
				</table>
			</td>
		</tr> 		
	</table>
</div>
<jsp:include page="resource_edit.jsp"></jsp:include>