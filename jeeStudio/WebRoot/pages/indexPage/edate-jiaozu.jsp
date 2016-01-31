<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<div class="easyui-panel" fit="true" title="到期房租列表" iconCls="icon-menu" collapsible="false" minimizable="false" maximizable="false" >
			
 
<!-- 修改按钮的参数 -->
<!-- 查看按钮的参数 -->
<!-- 查看删除的参数 -->
<!-- 查看复制的参数 -->
<!-- 批量删除 -->
 <!-- 添加按钮 -->
 <!-- 导入按钮 -->
 <!-- 导出按钮 -->
 <!-- 列表  -->
<div id="c1cd3f028cbcb1e41fcf99eab5b7aa8e_gridPanel"  >
	<table  id="fd_formlist_table_c1cd3f028cbcb1e41fcf99eab5b7aa8e" ></table>
</div>
 

 
<!-- 判断是否加载工作流信息 -->
<script> 
 
 
 
 
<!-- 行操作图标 -->
	var jsstr_4028864e37ea24ec0137ea29286b0003 = "";
	var returnstr_4028864e37ea24ec0137ea29286b0003 = "";
	var buttonrules;
	
	 
 
 
$(function(){
			
	 /**
      * 定义编辑页保存业务数据 的参数
      */
       var formID='editform_4028864e37e42a9e0137e48083ca0007';
		/**
		 * 定义窗口ID
		 */
		var gridID='fd_formlist_table_c1cd3f028cbcb1e41fcf99eab5b7aa8e';
	    var editWindowID='editPageWindow_c1cd3f028cbcb1e41fcf99eab5b7aa8e';
	    var copyWindowID='copyPageWindow_c1cd3f028cbcb1e41fcf99eab5b7aa8e';
	    var viewWindowID='viewPageWindow_c1cd3f028cbcb1e41fcf99eab5b7aa8e';
	    var viewDataDivID='viewPageData_c1cd3f028cbcb1e41fcf99eab5b7aa8e';
	    
		
				var editFormID='4028864e37e42a9e0137e48083ca0007';//编辑页 提交的formID
			
				var addFormID=editFormID;// 添加页 提交的formID  @GUOWEIXIN
			
				var copyFormID='-1';// 复制页 提交的formID
			
		
	
		var queryFormID='queryPage_4028864e37ea24ec0137ea29286b0003';//组合查询表单提交的ID;
	 	var gridUrl=PROJECTNAME+"com_reshData.action?formId=4028864e37ea24ec0137ea29286b0003";
	 		gridUrl=encodeURI(gridUrl)+"&date=" + new Date().getTime();
		var editPageDataDiv='editColumn_c1cd3f028cbcb1e41fcf99eab5b7aa8e';
		var copyPageDataDiv='copyColumn_c1cd3f028cbcb1e41fcf99eab5b7aa8e';
		
		/**
		 * 初始化包含列表的panel
		 */
		$('#c1cd3f028cbcb1e41fcf99eab5b7aa8e_gridPanel').panel({
			fit:true,
			border: false
		});
 
		
	  /**
       * 列表页 初始化
 
       */
      $('#'+gridID).datagrid({
      		iconCls: 'icon-grid',
			nowrap: false,
			fit: true,
			
		
				
		  //autoFit: false,
			striped: true,//条纹
		    collapsible: false,//缩放面板
		    
					singleSelect: false,//一次只选中单行
				
		    url:gridUrl,
			sortName: 'code',//排序列
			sortOrder: 'desc',//排序方式
			remoteSort: false,//远程调用
			rownumbers:true,
		    cache:false,//是否缓存
		    
					  pagination:true,
				
		    pageList:[6,9,12],//每页显示的数据条数 下拉选中可选每页大小
		    page: 1,//第几页开始
      	  //remoteSort:'',//服务器的排序字段
		    fitColumns:true,
		  //idField: 'tab_id',//只能获取一行值
 
		   
						 frozenColumns: [[
								 { field: 'ck', checkbox: true}
							]],
					  
		  
		  columns:[[
		  		    
					{
				        field: 'c_name',
				        title: '客户姓名',
				        align: '',   	
				        
					     	    hidden: false
					     	
								, width: 120
							
				     }
				 ,    
					{
				        field: 'houseID',
				        title: '房屋地址',
				        align: '',   	
				        
					     	    hidden: false
					     	
								, width: 120
							
								,formatter:function(value,rowData){
									var columnrules = eval('('+decodeURIComponent('%7B%22rulesService%22%3A%22%22%2C%22isJS%22%3A%22zidingyi%22%7D')+')');
									var str_formatterrules = value;
									if('jiekou'==columnrules.isJS){
										var params = '&';
										if(''!=columnrules.params){
											var paramarr = columnrules.params.split(/&/g);
											for(var i = 0; i<paramarr.length;i++){
												if(paramarr[i].indexOf('#')>-1){
													var columnnamearr = paramarr[i].split(/#/g);
													params += columnnamearr[0]+rowData[columnnamearr[1]]+'&';
												}else{
													params += paramarr[i]+'&';
												}
											}
										}
										params+='ooooooooooo=1';
										str_formatterrules = '<a href="javascript:;" style="text-decoration: underline"><font onclick=OpenEditPage("'+columnrules.tabstitle+'","600","380","'+LISTPAGE_LOAD+columnrules.tabspageurl+params+'") >'+value+'</font></a>';
									}else if('zidingyi'==columnrules.isJS){
									
									}
									return str_formatterrules;
								}
							
				     }
				 ,    
					{
				        field: 'c_phone',
				        title: '联系方式',
				        align: '',   	
				        
					     	    hidden: false
					     	
								, width: 120
							
				     }
				 ,    
					{
				        field: 'c_cardId',
				        title: '身份证号',
				        align: '',   	
				        
					     	    hidden: false
					     	
								, width: 120
							
				     }
				 ,    
					{
				        field: 'c_type',
				        title: '所租类型',
				        align: '',   	
				        
					     	    hidden: false
					     	
								, width: 120
							
				     }
				 ,    
					{
				        field: 'c_pay_date',
				        title: '交租时间',
				        align: '',   	
				        
					     	    hidden: false
					     	
								, width: 120
							
				     }
				 ,    
					{
				        field: 'tempField3',
				        title: '临时字段3',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'c_endDate_Renting',
				        title: '到期日',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'PARENT_APP_ID',
				        title: '系统添加',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'ID',
				        title: 'ID',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'tempField2',
				        title: '临时字段2',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'tempField',
				        title: '临时字段',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'remark',
				        title: '备注',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'IS_PSEUDO_DELETED',
				        title: '伪删除',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'ENV_DATASTATE',
				        title: '状态',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'ENV_DATAMETER',
				        title: '工作流字段',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'tempField1',
				        title: '临时字段1',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'APP_ID',
				        title: '系统添加',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'watch_money',
				        title: '有线电视费',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'san_money',
				        title: '卫生费',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'c_startDate_Renting',
				        title: '起租日',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 
						
				 
			]]
		});	

	});
</script>
<input type="hidden"  id="init_4028864e37ea24ec0137ea29286b0003" value="0"  />
<input type="hidden"  id="init_4028864e37ea24ec0137ea29286b0003_daochu" value="0"  />
<div  id="initdiv_4028864e37ea24ec0137ea29286b0003_daochu"></div>
<input type="hidden"  id="init_4028864e37ea24ec0137ea29286b0003_listpagezdy" value="0"  />
<div  id="initdiv_4028864e37ea24ec0137ea29286b0003_listpagezdy"></div>
<!-- 0 未初始化 1已初始化 -->	</div>
			
