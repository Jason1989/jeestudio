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
<div id="13e827113852bd3f6d571c251d1b042d_gridPanel"  >
	<table  id="fd_formlist_table_13e827113852bd3f6d571c251d1b042d" ></table>
</div>
 
<div style="display: none"  >
    <!-- 初始化一次窗口的flag -->
	<input type="hidden" id="editPage_13e827113852bd3f6d571c251d1b042d" value="0"  >
	<input type="hidden" id="copyPage_13e827113852bd3f6d571c251d1b042d" value="0"  >
	<input type="hidden" id="viewPage_13e827113852bd3f6d571c251d1b042d" value="0"  >
	<input type="hidden" id="queryPage_13e827113852bd3f6d571c251d1b042d" value="0"  >
	<input type="hidden" id="editPage_13e827113852bd3f6d571c251d1b042d_zdy" value="0"  >
	
	
 
<div id="editPageWindow_13e827113852bd3f6d571c251d1b042d"   style="padding:5px;background: #fafafa;"  >
	<div class="easyui-layout" fit="true">
		<div id="editColumn_13e827113852bd3f6d571c251d1b042d" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;height: 365px;">
				
		</div>
		<div region="south" border="false" style="text-align:center;height:45px;padding-top: 13px;">
			 <!-- 判断添加页是否启动工作流  并加入日志 -->
			
					<a   icon="icon-ok" id="editPageWindow_13e827113852bd3f6d571c251d1b042d_ok"  href="javascript:dynamicSaveData('editform_402886bb338c1f6e01338c2313f80004','fd_formlist_table_13e827113852bd3f6d571c251d1b042d','editPageWindow_13e827113852bd3f6d571c251d1b042d','queryPage_402886bb338c1f6e01338c23183e0006');" >保存</a>&nbsp;&nbsp;
			
			 
			 <a   icon='icon-undo'   id="editPageWindow_13e827113852bd3f6d571c251d1b042d_undo"     href="javascript:formReset('editform_402886bb338c1f6e01338c2313f80004');"  >重填</a>&nbsp;&nbsp;
			 <a   icon="icon-cancel" id="editPageWindow_13e827113852bd3f6d571c251d1b042d_cancel"   href="javascript:closeWindow('editPageWindow_13e827113852bd3f6d571c251d1b042d');"  >关闭</a>	
		</div>
	</div>
</div>
<script> 
	$(function (){
		$('#editPageWindow_13e827113852bd3f6d571c251d1b042d_ok').linkbutton({text:'保存'});
		$('#editPageWindow_13e827113852bd3f6d571c251d1b042d_tran_ok').linkbutton({text:'暂存'});
		$('#editPageWindow_13e827113852bd3f6d571c251d1b042d_undo').linkbutton({text:'重填'});
		$('#editPageWindow_13e827113852bd3f6d571c251d1b042d_cancel').linkbutton({text:'关闭'});
	});
</script>
	
 
 
<div id="viewPageWindow_13e827113852bd3f6d571c251d1b042d"     style="padding:5px;background: #fafafa;"  >
	<div class="easyui-layout" fit="true">
		<div id="viewPageData_13e827113852bd3f6d571c251d1b042d" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
		
		</div>
		<div region="south" border="false" style="text-align:center;height:45px;padding-top: 13px;">
				 <a  class="easyui-linkbutton" icon="icon-cancel"  href="javascript:closeWindow('viewPageWindow_13e827113852bd3f6d571c251d1b042d');"  >关闭</a>	
		</div>
	</div>
</div>
	
 
<div id="copyPageWindow_13e827113852bd3f6d571c251d1b042d"   style="padding:5px;background: #fafafa;"  >
	<div class="easyui-layout" fit="true">
		<div id="copyColumn_13e827113852bd3f6d571c251d1b042d" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;height: 365px;">
				
		</div>
		<div region="south" border="false" style="text-align:center;height:45px;padding-top:13px;">
			 <!-- 判断添加页是否启动工作流  并加入日志 -->
			 <a   icon="icon-ok" id="copyPageWindow_13e827113852bd3f6d571c251d1b042d_ok"  href="javascript:dynamicSaveData('copyform_','fd_formlist_table_13e827113852bd3f6d571c251d1b042d','copyPageWindow_13e827113852bd3f6d571c251d1b042d','queryPage_402886bb338c1f6e01338c23183e0006');" >保存</a>&nbsp;&nbsp;
			 <a   icon='icon-undo'   id="copyPageWindow_13e827113852bd3f6d571c251d1b042d_undo"     href="javascript:formReset('copyform_');"  >重填</a>&nbsp;&nbsp;
			 <a   icon="icon-cancel" id="copyPageWindow_13e827113852bd3f6d571c251d1b042d_cancel"   href="javascript:closeWindow('copyPageWindow_13e827113852bd3f6d571c251d1b042d');"  >关闭</a>	
		</div>
	</div>
</div>
<script> 
	$(function (){
		$('#copyPageWindow_13e827113852bd3f6d571c251d1b042d_ok').linkbutton({text:'保存'});
		$('#copyPageWindow_13e827113852bd3f6d571c251d1b042d_tran_ok').linkbutton({text:'暂存'});
		$('#copyPageWindow_13e827113852bd3f6d571c251d1b042d_undo').linkbutton({text:'重填'});
		$('#copyPageWindow_13e827113852bd3f6d571c251d1b042d_cancel').linkbutton({text:'关闭'});
	});
</script>
	
 
<div id="editPageWindow_13e827113852bd3f6d571c251d1b042d_zdy"   style="padding:5px;background: #fafafa;"  >
	<div class="easyui-layout" fit="true">
		<div id="editColumn_13e827113852bd3f6d571c251d1b042d_zdy" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;height: 365px;">
				
		</div>
		<div id="editsouth_13e827113852bd3f6d571c251d1b042d_zdy" region="south" border="false" style="text-align:center;height:55px;padding: 13px;">
			 
		</div>
	</div>
</div>
</div>
 
<!-- 判断是否加载工作流信息 -->
 
 
<script> 
	/**
      * 定义编辑页保存业务数据 的参数
      */
       var formID='editform_402886bb338c1f6e01338c2313f80004';
	/**
	 * 定义窗口ID
	 */
	var gridID2='fd_formlist_table_13e827113852bd3f6d571c251d1b042d';
    var editWindowID2='editPageWindow_13e827113852bd3f6d571c251d1b042d';
    var copyWindowID2='copyPageWindow_13e827113852bd3f6d571c251d1b042d';
    var viewWindowID2='viewPageWindow_13e827113852bd3f6d571c251d1b042d';
    var viewDataDivID2='viewPageData_13e827113852bd3f6d571c251d1b042d';
    var editWindowID2_zdy='editPageWindow_13e827113852bd3f6d571c251d1b042d_zdy';
	var editFormID='402886bb338c1f6e01338c2313f80004';//编辑页 提交的formID
	var copyFormID='-1';// 复制页 提交的formID
	
 
	var queryFormID='queryPage_402886bb338c1f6e01338c23183e0006';//组合查询表单提交的ID;
 	var gridUrl="com_reshData.action?formId=402886bb338c1f6e01338c23183e0006&workflow_fiter=yibanxiang&isAbleWorkFlow=1";
 		gridUrl=encodeURI(gridUrl);
	var editPageDataDiv='editColumn_13e827113852bd3f6d571c251d1b042d';
	var copyPageDataDiv='copyColumn_13e827113852bd3f6d571c251d1b042d';
	var editPageDataDiv_zdy='editColumn_13e827113852bd3f6d571c251d1b042d_zdy';	
		
	<!-- 行操作图标 -->
	var jsstr_402886bb338c1f6e01338c23183e0006 = "";
	var returnstr_402886bb338c1f6e01338c23183e0006 = "";
	var buttonrules;
	
	 
		
$(function(){
 
		/**
		 * 初始化包含列表的panel
		 */
		$('#13e827113852bd3f6d571c251d1b042d_gridPanel').panel({
			fit:true,
			border: false
		});
 
		
	  /**
       * 列表页 初始化
 
       */
      $('#'+gridID2).datagrid({
      		iconCls: 'icon-grid',
			nowrap: false,
			fit: true,
			//searchbar:searchbar,
		    autoFit: true,
			striped: true,//条纹
		    collapsible: false,//缩放面板
			singleSelect: true,//一次只选中单行
			url:"com_reshData.action?formId=402886bb338c1f6e01338c23183e0006&menuId=b57f901beedc4ee08f17ed1ec0f07fbc&workflow_fiter=yibanxiang&isAbleWorkFlow=1&date=1321855104188&page=1&rows=7",
			//url:"worktask/workflowtask!getWorkTaskData.action?workflow_fiter=yibanxiang&page=1&rows=5",
		    //url:gridUrl,
			sortName: 'code',//排序列
			sortOrder: 'desc',//排序方式
			remoteSort: false,//远程调用
			rownumbers:true,
		    cache:false,//是否缓存
		    //pageSize:5,
			//pagination:true,
		    //pageList:[6,9,12],//每页显示的数据条数 下拉选中可选每页大小
		    //page: 1,//第几页开始
      	    //remoteSort:'',//服务器的排序字段
		    fitColumns:true,
		    //idField: 'tab_id',//只能获取一行值
		  
		  columns:[[
					{
				        field: 'CONS_START_TIME',
				        title: '登记日期',
				        align: '',   	
					    hidden: true,
					    width: 60,
						fit:true
				     }
				    ,    
					{
				        field: 'TASK_CODE',
				        title: '任务编号',
				        align: '',   	
					    hidden: false, 
					    width: 100
				     }
				    ,    
					{
				        field: 'TASK_NAME',
				        title: '任务名称',
				        align: '',   	
					    hidden: false, 
					    width: 100
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
				        field: 'ENV_DATASTATE',
				        title: '状态',
				        align: '',   	
					    hidden: false, 
					    width: 60
				     }
						,{field:'col4',title:'操作',width:50,rowspan:2,align:'center',	
							formatter:function(value,rowData,rowIndex){
							    var dataID=rowData.keynull;
								var opertorString='';
								// 判断是否使用编辑
								// 判断是否使用复制
								// 判断是否使用查看
								var loadViewUrl='formId=402886bb338c1f6e01338c2153ca0002';
								loadViewUrl=loadViewUrl+'&TASK_ID='+rowData.TASK_ID;
								opertorString=opertorString+'<img  style="cursor:hand;" title="查看详情" sytle="cursor:pointer;" src="/compplatform/images/mokuai.png" onclick=loadViewPage("'+loadViewUrl+'","'+viewDataDivID2+'","false","'+viewWindowID2+'","&nbsp;任务信息","viewPage_13e827113852bd3f6d571c251d1b042d"); />&nbsp;&nbsp;&nbsp;&nbsp;';
								//opertorString=opertorString+'<img  style="cursor:hand;" title="查看详情" sytle="cursor:pointer;" src="/compplatform/images/mokuai.png" onclick=OpenEditPage("veiw",0,0,"'+loadViewUrl+'"); />&nbsp;&nbsp;&nbsp;&nbsp;';
								//删除列
							   var returnstr_402886bb338c1f6e01338c23183e0006xxx = returnstr_402886bb338c1f6e01338c23183e0006.replace(/(rowData)/g,'(\''+encodeURIComponent(JSON2.stringify(rowData))+'\')');
							   opertorString += returnstr_402886bb338c1f6e01338c23183e0006xxx;
							   return opertorString;
							}
						}
			]]
		});	
	});
</script>
<input type="hidden"  id="init_402886bb338c1f6e01338c23183e0006" value="0"  />
<input type="hidden"  id="init_402886bb338c1f6e01338c23183e0006_daochu" value="0"  />
<div  id="initdiv_402886bb338c1f6e01338c23183e0006_daochu"></div>
<input type="hidden"  id="init_402886bb338c1f6e01338c23183e0006_listpagezdy" value="0"  />
<div  id="initdiv_402886bb338c1f6e01338c23183e0006_listpagezdy"></div>
