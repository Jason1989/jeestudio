<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.zxt.compplatform.formengine.entity.view.ListPage"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.EditPage"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.Param"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.ViewPage"%>
<%@page import="java.util.Map" %>
<%@page import="com.zxt.compplatform.formengine.constant.Constant"%>

<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>

 
<!-- 列表  -->
<div id="ab3077921e0af6f52bcf7c4aacfcc9ba_gridPanel"  >
	<table  id="fd_formlist_table_ab3077921e0af6f52bcf7c4aacfcc9ba" ></table>
</div>
 
<div style="display: none"  >
    <!-- 初始化一次窗口的flag -->
	<input type="hidden" id="editPage_ab3077921e0af6f52bcf7c4aacfcc9ba" value="0"  >
	<input type="hidden" id="copyPage_ab3077921e0af6f52bcf7c4aacfcc9ba" value="0"  >
	<input type="hidden" id="viewPage_ab3077921e0af6f52bcf7c4aacfcc9ba" value="0"  >
	<input type="hidden" id="queryPage_ab3077921e0af6f52bcf7c4aacfcc9ba" value="0"  >
	<input type="hidden" id="editPage_ab3077921e0af6f52bcf7c4aacfcc9ba_zdy" value="0"  >
	
	
 
<div id="editPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba"   style="padding:5px;background: #fafafa;"  >
	<div class="easyui-layout" fit="true">
		<div id="editColumn_ab3077921e0af6f52bcf7c4aacfcc9ba" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;height: 365px;">
				
		</div>
		<div region="south" border="false" style="text-align:center;height:45px;padding-top: 13px;">
			 <!-- 判断添加页是否启动工作流  并加入日志 -->
			 <a   icon="icon-ok" id="editPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba_ok"  href="javascript:dynamicSaveData('editform_402886bb338c1f6e01338c21512a0001','fd_formlist_table_ab3077921e0af6f52bcf7c4aacfcc9ba','editPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba','queryPage_402886bb338c1f6e01338c2155fc0003');" >保存</a>&nbsp;&nbsp;
			 <a   icon='icon-undo'   id="editPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba_undo"     href="javascript:formReset('editform_402886bb338c1f6e01338c21512a0001');"  >重填</a>&nbsp;&nbsp;
			 <a   icon="icon-cancel" id="editPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba_cancel"   href="javascript:closeWindow('editPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba');"  >关闭</a>	
		</div>
	</div>
</div>
<script> 
	$(function (){
		$('#editPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba_ok').linkbutton({text:'保存'});
		$('#editPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba_tran_ok').linkbutton({text:'暂存'});
		$('#editPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba_undo').linkbutton({text:'重填'});
		$('#editPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba_cancel').linkbutton({text:'关闭'});
	});
</script>
	
<div id="viewPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba"     style="padding:5px;background: #fafafa;"  >
	<div class="easyui-layout" fit="true">
		<div id="viewPageData_ab3077921e0af6f52bcf7c4aacfcc9ba" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
		
		</div>
		<div region="south" border="false" style="text-align:center;height:45px;padding-top: 13px;">
				 <a  class="easyui-linkbutton" icon="icon-cancel"  href="javascript:closeWindow('viewPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba');"  >关闭</a>	
		</div>
	</div>
</div>
	
 
<div id="copyPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba"   style="padding:5px;background: #fafafa;"  >
	<div class="easyui-layout" fit="true">
		<div id="copyColumn_ab3077921e0af6f52bcf7c4aacfcc9ba" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;height: 365px;">
				
		</div>
		<div region="south" border="false" style="text-align:center;height:45px;padding-top:13px;">
			 <!-- 判断添加页是否启动工作流  并加入日志 -->
			 <a   icon="icon-ok" id="copyPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba_ok"  href="javascript:dynamicSaveData('copyform_','fd_formlist_table_ab3077921e0af6f52bcf7c4aacfcc9ba','copyPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba','queryPage_402886bb338c1f6e01338c2155fc0003');" >保存</a>&nbsp;&nbsp;
			 <a   icon='icon-undo'   id="copyPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba_undo"     href="javascript:formReset('copyform_');"  >重填</a>&nbsp;&nbsp;
			 <a   icon="icon-cancel" id="copyPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba_cancel"   href="javascript:closeWindow('copyPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba');"  >关闭</a>	
		</div>
	</div>
</div>
<script> 
	$(function (){
		$('#copyPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba_ok').linkbutton({text:'保存'});
		$('#copyPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba_tran_ok').linkbutton({text:'暂存'});
		$('#copyPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba_undo').linkbutton({text:'重填'});
		$('#copyPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba_cancel').linkbutton({text:'关闭'});
	});
</script>
	
 
<div id="editPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba_zdy"   style="padding:5px;background: #fafafa;"  >
	<div class="easyui-layout" fit="true">
		<div id="editColumn_ab3077921e0af6f52bcf7c4aacfcc9ba_zdy" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;height: 365px;">
				
		</div>
		<div id="editsouth_ab3077921e0af6f52bcf7c4aacfcc9ba_zdy" region="south" border="false" style="text-align:center;height:55px;padding: 13px;">
			 
		</div>
	</div>
</div>
</div>
 
<!-- 判断是否加载工作流信息 -->
 
 
<script> 

	/**
      * 定义编辑页保存业务数据 的参数
      */
    var formID='editform_402886bb338c1f6e01338c21512a0001';
	/**
	 * 定义窗口ID
	 */
	var gridID1='fd_formlist_table_ab3077921e0af6f52bcf7c4aacfcc9ba';
    var editWindowID1='editPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba';
    var editWindowID1='copyPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba';
    var viewWindowID1='viewPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba';
    var viewDataDivID1='viewPageData_ab3077921e0af6f52bcf7c4aacfcc9ba';
    var editWindowID1_zdy='editPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba_zdy';
	var editFormID='402886bb338c1f6e01338c21512a0001';//编辑页 提交的formID
	var copyFormID='-1';// 复制页 提交的formID
		
 	var gridUrl="com_reshData.action?formId=402886bb338c1f6e01338c2155fc0003&workflow_fiter=daibanxiang&isAbleWorkFlow=1";
 		gridUrl=encodeURI(gridUrl);
	var editPageDataDiv1='editColumn_ab3077921e0af6f52bcf7c4aacfcc9ba';
	var copyPageDataDiv='copyColumn_ab3077921e0af6f52bcf7c4aacfcc9ba';
	var editPageDataDiv1_zdy='editColumn_ab3077921e0af6f52bcf7c4aacfcc9ba_zdy';	
		
	<!-- 行操作图标 -->
	var jsstr_402886bb338c1f6e01338c2155fc0003 = "";
	var returnstr_402886bb338c1f6e01338c2155fc0003 = "";
	var buttonrules;
	
	 
		
$(function(){
 
		/**
		 * 初始化包含列表的panel
		 */
		$('#ab3077921e0af6f52bcf7c4aacfcc9ba_gridPanel').panel({
			fit:true,
			border: false
		});
		
	  /**
       * 列表页 初始化
 
       */
      $('#'+gridID1).datagrid({
      		iconCls: 'icon-grid',
			nowrap: false,
			fit: true,
		    //searchbar:searchbar,
		    autoFit: true,
			striped: true,//条纹
		    collapsible: false,//缩放面板
			singleSelect: true,//一次只选中单行
			url:"com_reshData.action?formId=402886bb338c1f6e01338c2155fc0003&menuId=68a74d7acc0d73c5b773aba3633ead45&workflow_fiter=daibanxiang&isAbleWorkFlow=1&date=1321855364192&page=1&rows=5",
		    //url:"worktask/workflowtask!getWorkTaskData.action?workflow_fiter=daibanxiang&page=1&rows=5",
			sortName: 'code',//排序列
			sortOrder: 'desc',//排序方式
			remoteSort: false,//远程调用
			rownumbers:true,
		    cache:false,//是否缓存
			//pagination:true,
		    //pageList:[6,9,12],//每页显示的数据条数 下拉选中可选每页大小
		    //pageSize:5,
		    //page: 1,//第几页开始
      	    //remoteSort:'',//服务器的排序字段
		    fitColumns:true,
		    //idField: 'tab_id',//只能获取一行值

		  columns:[[
					{
				        field: 'TASK_CODE',
				        title: '任务编号',
				        align: '',   	
				        hidden: false, 
				        width: 90
				     }
				    ,    
					{
				        field: 'TASK_NAME',
				        title: '任务名称',
				        align: '',   	
					    hidden: false,
						width: 90
				     }
				    ,    
					{
				        field: 'D_TASK_TYPE_ID',
				        title: '任务类型',
				        align: '',   	
					    hidden: false, 
					    width: 60
				     }
				    ,    
					{
				        field: 'CONS_START_TIME',
				        title: '登记日期',
				        hidden:true,
				        align: '',   	
				        width: 60
				     }
				    ,    
					{
				        field: 'ENV_DATASTATE',
				        title: '状态',
				        hidden:false,
				        align: '',
				        width: 60
				     },
				     {field:'col4',title:'操作',width:50,rowspan:2,align:'center',	
							formatter:function(value,rowData,rowIndex){
								//alert(rowData.TASK_ID);
							    var dataID=rowData.TASK_ID;
								var opertorString='';
								// 判断是否使用编辑
								 var loadEditUrl1='&listFormID=402886bb338c1f6e01338c2155fc0003&listPageRander=ab3077921e0af6f52bcf7c4aacfcc9ba';
								 loadEditUrl1=loadEditUrl1+'&TASK_ID='+rowData.TASK_ID;
								 //opertorString='<img  style="cursor:hand;" title="编辑" sytle="cursor:pointer;" src="/compplatform/images/ioc-editor.gif" onclick=loadEditPage_easyui("402886bb338c1f6e01338c21512a0001","'+loadEditUrl1+'","1","TASK_ID","'+editPageDataDiv1+'","false","editPageWindow_ab3077921e0af6f52bcf7c4aacfcc9ba","&nbsp;任务信息","editPage_ab3077921e0af6f52bcf7c4aacfcc9ba") />&nbsp;&nbsp;&nbsp;&nbsp;';
								// 判断是否使用复制
								//判断是否使用查看
							     var loadViewUrl='formId=402886bb338c1f6e01338c2153ca0002';
							     loadViewUrl=loadViewUrl+'&TASK_ID='+rowData.TASK_ID;
								 opertorString=opertorString+'<img  style="cursor:hand;" title="查看详情" sytle="cursor:pointer;" src="/compplatform/images/mokuai.png" onclick=loadViewPage("'+loadViewUrl+'","'+viewDataDivID1+'","false","'+viewWindowID1+'","&nbsp;任务信息","viewPage_ab3077921e0af6f52bcf7c4aacfcc9ba"); />&nbsp;&nbsp;&nbsp;&nbsp;';
								//删除列
								var deleteUrl='listFormID=402886bb338c1f6e01338c2155fc0003';
								deleteUrl=deleteUrl+'&TASK_ID='+rowData.TASK_ID;
								opertorString=opertorString+'<img  style="cursor:hand;" title="删除" sytle="cursor:pointer;" src="/compplatform/images/ioc-delete.gif"  onclick=deleteData_easyui("'+deleteUrl+'","'+gridID1+'"); />&nbsp;&nbsp;&nbsp;&nbsp;';
									
							   var returnstr_402886bb338c1f6e01338c2155fc0003xxx = returnstr_402886bb338c1f6e01338c2155fc0003.replace(/(rowData)/g,'(\''+encodeURIComponent(JSON2.stringify(rowData))+'\')');
							   opertorString += returnstr_402886bb338c1f6e01338c2155fc0003xxx;
							   return opertorString;
							}
						}
			]]
		});	
	});
</script>
<input type="hidden"  id="init_402886bb338c1f6e01338c2155fc0003" value="0"  />
<input type="hidden"  id="init_402886bb338c1f6e01338c2155fc0003_daochu" value="0"  />
<div  id="initdiv_402886bb338c1f6e01338c2155fc0003_daochu"></div>
<input type="hidden"  id="init_402886bb338c1f6e01338c2155fc0003_listpagezdy" value="0"  />
<div  id="initdiv_402886bb338c1f6e01338c2155fc0003_listpagezdy"></div>
<!-- 0 未初始化 1已初始化 -->	
				