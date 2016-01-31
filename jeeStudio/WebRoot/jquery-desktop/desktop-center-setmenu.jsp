<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<div id="shortCutsSetWindow" class="easyui-window" closed=true
	title="快捷方式设置" style="width: 700px; height: 450px;" >
	<table id="tt"></table>
</div>

<script>
		$(function(){
		/**
		 * 初始化关闭  状态栏
		 */
			 $('#shortCutsSetWindow').window({
				  onClose:function(){
				  	$('#taskbar_shortCutsSetWindow').remove();
				  }
			 });
		
		
			var products = [
			    {productid:'assets/images/icons/icon_32_computer.png',name:'电脑图标'},
			    {productid:'assets/images/icons/icon_32_drive.png',name:'驱动图标'},
			    {productid:'assets/images/icons/icon_32_disc.png',name:'硬盘图标'},
			    {productid:'assets/images/icons/icon_32_network.png',name:'网络图标'},
			    {productid:'assets/images/icons/ipad_32_cg.png',name:'客户管理'},
			    {productid:'assets/images/icons/ipad_32_cd.png',name:'货币图标'},
			    {productid:'assets/images/icons/ipad_32_ct.png',name:'任务图标'},
			    {productid:'assets/images/icons/ipad_32_cc.png',name:'日常管理'},
			    {productid:'assets/images/icons/ipad_32_cs.png',name:'查询图标'},
			    {productid:'assets/images/icons/ipad_32_cp.png',name:'产品图标'},
			    {productid:'assets/images/icons/ipad_32_ca.png',name:'系统图标'},
			    {productid:'assets/images/icons/ipad_32_device.png',name:'设备图标'},
			    {productid:'assets/images/icons/ipad_32_sale.png',name:'销售图标'}
			];
			var menuLayout = [
			    {productid:'1',name:'居左'},
			    {productid:'2',name:'居中 '}
			];
			var treeDataJson=eval('('+'${desktopTreeData}'+')');
			var treeDataListJson=eval('('+'${desktopTreeListData}'+')');
	
			var lastIndex;
			$('#tt').datagrid({
				//title:'Editable DataGrid',
				iconCls:'icon-edit',
				// width:650,
				// height:'auto',
				fit:true,
				border:false,
				singleSelect:true,
				// idField:'itemid',
				// url:'datagrid_data2.json',
				pagination:true,
				columns:[[
					{field:'unitcost',title:'图标显示',width:80,align:'center',
						formatter:function(value,rowData,rowIndex){
							return '<img src='+rowData.ioc+' style="width:16px;height:16px;" />';
						}
					},
					{field:'text',title:'菜单名称',width:80,editor:'text'},
					{field:'ioc',title:'图标选择',width:100,
						formatter:function(value){
							for(var i=0; i<products.length; i++){
								if (products[i].productid == value) return products[i].name;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'productid',
								textField:'name',
								data:products,
								required:true
							}
						}
					},
					{field:'id',title:'菜单选择',width:150,
						formatter:function(value){
							for(var i=0; i<treeDataListJson.length; i++){
								if (treeDataListJson[i].id == value) return treeDataListJson[i].text;
							}
							return value;
						},
						editor:{
							type:'combotree',
							options:{
								valueField:'id',
								textField:'text',
								data:treeDataJson,
								required:true
							}
						}
					},
					{field:'menuLayout',title:'菜单布局',width:100,
						formatter:function(value){
							for(var i=0; i<menuLayout.length; i++){
								if (menuLayout[i].productid == value) return menuLayout[i].name;
							}
							return value;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'productid',
								textField:'name',
								data:menuLayout,
								required:true
							}
						}
					}
				]],
				toolbar:[{
					text:'添加',
					iconCls:'icon-add',
					titleCls:'link_btn_color',
					handler:function(){
					$('#tt').datagrid('endEdit', lastIndex);
						$('#tt').datagrid('appendRow',{
							itemid:'',
							productid:'',
							listprice:'',
							unitprice:'',
							attr1:'',
							status:'P'
						});
						var lastIndex = $('#tt').datagrid('getRows').length-1;
						$('#tt').datagrid('beginEdit', lastIndex);
					}
				},'-',{
					text:'删除',
					iconCls:'icon-remove',
					titleCls:'link_btn_color',
					handler:function(){
						var row = $('#tt').datagrid('getSelected');
						if (row){
							var index = $('#tt').datagrid('getRowIndex', row);
							$('#tt').datagrid('deleteRow', index);
						}
					}
				},'-',{
					text:'保存',
					iconCls:'icon-save',
					titleCls:'link_btn_color',
					handler:function(){
						$('#tt').datagrid('acceptChanges');
						saveEditGrid('tt','zsf_saveUserSetting.action');
						$.messager.confirm('提示', '是否马上应用新的设置？', function(r){
							if (r){
									 window.location.reload(); 
							 }
						});
						
					}
				},'-',{
					text:'还原配置',
					iconCls:'icon-undo',
					titleCls:'link_btn_color',
					handler:function(){
						$('#tt').datagrid('rejectChanges');
					}
				},'-',{
					text:'当前修改',
					iconCls:'icon-search',
					titleCls:'link_btn_color',
					handler:function(){
						var rows = $('#tt').datagrid('getChanges');
						alert('changed rows: ' + rows.length + ' lines');
					}
				}],
				onBeforeLoad:function(){
					$(this).datagrid('rejectChanges');
				},
				onClickRow:function(rowIndex){
					if (lastIndex != rowIndex){
						$('#tt').datagrid('endEdit', lastIndex);
						$('#tt').datagrid('beginEdit', rowIndex);
					}
					lastIndex = rowIndex;
				}
			});
			if('${userMenuSets}'.trim().length>0){
				var dataObject=eval('('+'${userMenuSets}'+')');
		  		$('#tt').datagrid("loadData",dataObject);
			}
		  
		});
	</script>