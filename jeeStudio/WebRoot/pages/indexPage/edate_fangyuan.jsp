<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<div class="easyui-panel" fit="true" title="到期房源交租列表" iconCls="icon-menu" collapsible="false" minimizable="false" maximizable="false" >

 
<!-- 修改按钮的参数 -->
<!-- 查看按钮的参数 -->
<!-- 查看删除的参数 -->
<!-- 查看复制的参数 -->
<!-- 批量删除 -->
 <!-- 添加按钮 -->
 <!-- 导入按钮 -->
 <!-- 导出按钮 -->
 <!-- 列表  -->
<div id="3870d8b895d6bdabd382fb39299195ff_gridPanel"  >
	<table  id="fd_formlist_table_3870d8b895d6bdabd382fb39299195ff" ></table>
</div>

 
<!-- 判断是否加载工作流信息 -->
<script> 
 
 
 
 
<!-- 行操作图标 -->
	var jsstr_4028863d384173d601384184184e0003 = "";
	var returnstr_4028863d384173d601384184184e0003 = "";
	var buttonrules;
	
	 
 
 
$(function(){
			
	 /**
      * 定义编辑页保存业务数据 的参数
      */
       var formID='editform_4028864e37e42a9e0137e45c69230001';
		/**
		 * 定义窗口ID
		 */
		var gridID='fd_formlist_table_3870d8b895d6bdabd382fb39299195ff';
	    var editWindowID='editPageWindow_3870d8b895d6bdabd382fb39299195ff';
	    var copyWindowID='copyPageWindow_3870d8b895d6bdabd382fb39299195ff';
	    var viewWindowID='viewPageWindow_3870d8b895d6bdabd382fb39299195ff';
	    var viewDataDivID='viewPageData_3870d8b895d6bdabd382fb39299195ff';
	    
		
				var editFormID='4028864e37e42a9e0137e45c69230001';//编辑页 提交的formID
			
				var addFormID=editFormID;// 添加页 提交的formID  @GUOWEIXIN
			
				var copyFormID='-1';// 复制页 提交的formID
			
		
	
		var queryFormID='queryPage_4028863d384173d601384184184e0003';//组合查询表单提交的ID;
	 	var gridUrl=PROJECTNAME+"com_reshData.action?formId=4028863d384173d601384184184e0003";
	 		gridUrl=encodeURI(gridUrl)+"&date=" + new Date().getTime();
		var editPageDataDiv='editColumn_3870d8b895d6bdabd382fb39299195ff';
		var copyPageDataDiv='copyColumn_3870d8b895d6bdabd382fb39299195ff';
		
		/**
		 * 初始化包含列表的panel
		 */
		$('#3870d8b895d6bdabd382fb39299195ff_gridPanel').panel({
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
				        field: 'address',
				        title: '房屋地址',
				        align: '',   	
				        
					     	    hidden: false
					     	
								, width: 120
							
				     }
				 ,    
					{
				        field: 'o_name',
				        title: '业主姓名',
				        align: '',   	
				        
					     	    hidden: false
					     	
								, width: 120
							
				     }
				 ,    
					{
				        field: 'area',
				        title: '面积（平方米）',
				        align: '',   	
				        
					     	    hidden: false
					     	
								, width: 120
							
				     }
				 ,    
					{
				        field: 'price',
				        title: '价格（元）',
				        align: '',   	
				        
					     	    hidden: false
					     	
								, width: 120
							
				     }
				 ,    
					{
				        field: 'room_offic',
				        title: '几室几厅',
				        align: '',   	
				        
					     	    hidden: false
					     	
								, width: 120
							
				     }
				 ,    
					{
				        field: 'pay_date',
				        title: '交租时间',
				        align: '',   	
				        
					     	    hidden: false
					     	
								, width: 120
							
				     }
				 ,    
					{
				        field: 'floor',
				        title: '楼层',
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
				        field: 'interest',
				        title: '产权',
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
				        field: 'lookat',
				        title: '朝向',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'o_cardId',
				        title: '业主身份证号',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'o_phone',
				        title: '联系方式',
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
				        field: 'remark',
				        title: '备注',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'tempfield',
				        title: '临时字段',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'tempfield1',
				        title: '临时字段1',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'tempfield2',
				        title: '临时字段2',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'tempfield3',
				        title: '临时字段3',
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
				        field: 'ENV_DATAMETER',
				        title: '工作流字段',
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
				        field: 'IS_PSEUDO_DELETED',
				        title: '伪删除',
				        align: '',   	
				        
						      	hidden: true
						    
								, width: 120
							
				     }
				 ,    
					{
				        field: 'isPay',
				        title: '是否交租',
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
				        field: 'relatId',
				        title: '关联ID',
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
				        field: 'ENV_DATAMETER',
				        title: '工作流字段',
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
				 
						
				 
			]]
		});	
	 // initGridData(gridID,gridUrl,'init_4028863d384173d601384184184e0003','false');
	});
</script>
<input type="hidden"  id="init_4028863d384173d601384184184e0003" value="0"  />
<input type="hidden"  id="init_4028863d384173d601384184184e0003_daochu" value="0"  />
<div  id="initdiv_4028863d384173d601384184184e0003_daochu"></div>
<input type="hidden"  id="init_4028863d384173d601384184184e0003_listpagezdy" value="0"  />
<div  id="initdiv_4028863d384173d601384184184e0003_listpagezdy"></div>
<!-- 0 未初始化 1已初始化 -->	</div>
			
