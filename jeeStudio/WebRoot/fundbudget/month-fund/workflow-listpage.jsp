<%@page   contentType="text/html;charset=UTF-8" language="java" %> 
<%@page import="com.zxt.compplatform.formengine.entity.view.ListPage" %>
<%@page import="com.zxt.compplatform.formengine.entity.view.Param" %>
<%@page import="com.zxt.compplatform.formengine.entity.view.ViewPage" %>
<%@page import="java.util.Map" %>
<%@page import="com.zxt.compplatform.formengine.constant.Constant" %>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>

<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<% 
 	request.setAttribute("listPageRander",RandomGUID.geneGuid());//生成表单列ID
 	Map authority=(Map)request.getSession().getAttribute("authority");
 	ListPage listPage=(ListPage)request.getAttribute("listPage");
%>
<!-- 修改按钮的参数 -->
<s:iterator value="#request.listPage.rowButton"  >
   <c:choose>
		<c:when test="${buttonName eq 'update'}">
		 <s:iterator value="event"  >
		   <s:iterator value="paras" >
				<c:set scope="page" var="idkey" value="${value}" ></c:set>
		   </s:iterator>  
		 </s:iterator>  
		</c:when>
	</c:choose>
</s:iterator>  

<c:choose>
	<c:when test="${empty idkey}">
		<c:set scope="page" var="idkey" value="keynull"  ></c:set>
	</c:when>
</c:choose>

<!-- 查看按钮的参数 -->
<s:iterator value="#request.listPage.rowButton" >
   <c:choose>
		<c:when test="${buttonName eq 'view'}">
		 <s:iterator value="event" >
		   <s:iterator value="paras" >
		  	 <c:set scope="page" var="viewIdkey" value="${value}"  ></c:set>
		   </s:iterator>  
		 </s:iterator>  
		</c:when>
	</c:choose>
</s:iterator>  

<c:choose>
	<c:when test="${empty viewIdkey}">
		<c:set scope="page" var="viewIdkey" value="keynull"  ></c:set>
	</c:when>
</c:choose>

<!-- 查看删除的参数 -->
<s:iterator value="#request.listPage.rowButton" >
   <c:choose>
		<c:when test="${buttonName eq 'delete'}">
		 <s:iterator value="event" >
		   <s:iterator value="paras" >
		  	 <c:set scope="page" var="deleteIdkey" value="${value}"  ></c:set>
		   </s:iterator>  
		 </s:iterator>  
		</c:when>
	</c:choose>
</s:iterator>  
<c:choose>
	<c:when test="${empty deleteIdkey}">
		<c:set scope="page" var="deleteIdkey" value="keynull"  ></c:set>
	</c:when>
</c:choose>

<!-- 批量删除 -->
 <s:iterator value="#request.listPage.gridButton" >
   <c:choose>
		<c:when test="${buttonName eq 'deleteBatch'}">
			<c:set scope="page" var="deleteBatch" value="deleteBatch"  ></c:set>
		</c:when>
	</c:choose>
</s:iterator>  


<!-- 添加按钮 -->
 <s:iterator value="#request.listPage.gridButton" >
   <c:choose>
		<c:when test="${buttonName eq 'add'}">
			<c:set scope="page" var="gridButton_add" value="add"  ></c:set>
		</c:when>
	</c:choose>
</s:iterator>  

<!-- 导入按钮 -->
 <s:iterator value="#request.listPage.gridButton" >
   <c:choose>
		<c:when test="${buttonName eq 'import'}">
			<c:set scope="page" var="import" value="import"  ></c:set>
		</c:when>
	</c:choose>
</s:iterator> 

<!-- 导出按钮 -->
 <s:iterator value="#request.listPage.gridButton" >
   <c:choose>
		<c:when test="${buttonName eq 'export'}">
			<c:set scope="page" var="export" value="export"  ></c:set>
		</c:when>
	</c:choose>
</s:iterator> 
<!-- 列表  -->
<div id="${listPageRander}_gridPanel"  >
	<table  id="fd_formlist_table_${listPageRander}" ></table>
</div>

<div style="display: none"  >
    <!-- 初始化一次窗口的flag -->
	<input type="hidden" id="editPage_${listPageRander}" value="0"  >
	<input type="hidden" id="viewPage_${listPageRander}" value="0"  >
	<input type="hidden" id="queryPage_${listPageRander}" value="0"  >
	
	<jsp:include page="/common/jsp/easyui/editPageWindow.jsp"></jsp:include>
	<jsp:include page="/common/jsp/easyui/queryPageWindow.jsp"></jsp:include>
	<jsp:include page="/common/jsp/easyui/viewPageWindow.jsp"></jsp:include>
</div>

<div id="activity-tab-window" ></div>


<script>

function activity_operator(APP_ID,mid,precursorId,gridId){
	
	initEasyuiWindow('activity-tab-window','月度预算审批');
	var customPath='&customPath=/fundbudget/month-fund/activity-tab.jsp';
	var url=PROJECTNAME+'fundbudget/acttab_.action?gridId='+gridId+'&APP_ID='+APP_ID+customPath+'&mid='+mid+'&precursorId='+precursorId+'&date='+new Date().getTime();

	$('#activity-tab-window').window({href:url});
	$('#activity-tab-window').window('open');
}

$(function(){
		
		var searchbar={
			forms:[
					<s:iterator value="#request.listPage.queryZone"  id="queryZone" >
					    <s:iterator value="#queryZone.queryColumns"  id="queryZone" status="status" >
							<s:if test="#status.first"></s:if>
						    <s:else>,</s:else>
						    <jsp:include page="/common/jsp/easyui/queryColumn.jsp"></jsp:include>
						</s:iterator>
			 	   </s:iterator>
			]
		}
		/**
		 * 初始化包含列表的panel
		 */
		$('#${listPageRander}_gridPanel').panel({
			fit:true,
			border: false
		});

	  /**
       * 定义编辑页保存业务数据 的参数
       */
        var formID='editform_${listPage.editPage.id}';
		/**
		 * 定义窗口ID
		 */
		var gridID='fd_formlist_table_${listPageRander}';
	    var editWindowID='editPageWindow_${listPageRander}';
	    var viewWindowID='viewPageWindow_${listPageRander}';
	    
	    var viewDataDivID='viewPageData_${listPageRander}';
	
		<c:choose>
			<c:when test="${empty listPage.editPage.id}">
				var editFormID='-1';// 编辑页 提交的formID
			</c:when>
			<c:otherwise>
				var editFormID='${listPage.editPage.id}';//编辑页 提交的formID
			</c:otherwise>
	    </c:choose>
		
	
		var queryFormID='queryPage_${listPage.id}';//组合查询表单提交的ID;
	 	var gridUrl="${listPage.gridUrl}";
	 		gridUrl=encodeURI(gridUrl);
		var editPageDataDiv='editColumn_${listPageRander}';
		
	  /**
       * 列表页 初始化

       */
      $('#'+gridID).datagrid({
      		iconCls: 'icon-grid',
			nowrap: false,
			fit: true,
			<c:choose>
				<c:when test="${!empty listPage.queryZonePanel}">
					searchbar:searchbar,
				</c:when>
			</c:choose>
		  //autoFit: false,
			striped: true,//条纹
		    collapsible: false,//缩放面板
		    <c:choose>
				<c:when test="${deleteBatch eq 'deleteBatch'}">
					singleSelect: false,//一次只选中单行
				</c:when>
				<c:otherwise>
					singleSelect: true,//一次只选中单行
				</c:otherwise>
			</c:choose>
		    url:gridUrl,
			sortName: 'code',//排序列
			sortOrder: 'desc',//排序方式
			remoteSort: false,//远程调用
			rownumbers:true,
		    cache:false,//是否缓存
		    <c:choose>
				<c:when test="${listPage.canShowPagination}">
					  pagination:true,
				</c:when>
				<c:otherwise>
				 	  pagination:false,
				</c:otherwise>
			</c:choose>
		    pageList:[6,9,12],//每页显示的数据条数 下拉选中可选每页大小
		    page: 1,//第几页开始
      	  //remoteSort:'',//服务器的排序字段
		    fitColumns:true,
		  //idField: 'tab_id',//只能获取一行值

		   <s:iterator value="#request.listPage.gridButton"   >
			   <c:choose>
					<c:when test="${buttonName eq 'deleteBatch'}">
						 frozenColumns: [[
								 { field: 'ck', checkbox: true}
							]],
					</c:when>
				</c:choose>
			</s:iterator>  
		  
		  columns:[[
			  	   <s:iterator value="#request.listPage.fields" status="status"   >
					<s:if test="#status.first"></s:if>
				    <s:else>,</s:else>    
					{
				        field: '${dataColumn}',
				        title: '${name}',
				        align: '${align}',   	
				        <c:choose>
						    <c:when test="${visible eq 'false'}">
						      	hidden: true
						    </c:when>
					     	<c:otherwise>
					     	    hidden: false
					     	</c:otherwise>
				        </c:choose>
				        <c:choose>
							<c:when test="${empty width}">
								, width: 60
							</c:when>
							<c:otherwise>
								, width: ${width}
							</c:otherwise>
						</c:choose>
				     }
				 </s:iterator>
		     	<c:choose>
					<c:when test="${listPage.isShowOperator eq 'true'}">
						,{field:'col4',title:'操作',width:100,rowspan:2,align:'center',	
							formatter:function(value,rowData,rowIndex){
								    var dataID=rowData.${pageScope.idkey};
									var opertorString='';
									// 判断是否使用编辑
									
									var APP_ID=rowData.APP_ID+'';
								    var mid=rowData.eng_envmid+'';//mid
								    var precursorId=rowData.eng_envstatus+'';//前驱状态ID
									opertorString='<img  style="cursor:hand;" title="编辑" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/images/ioc-editor.gif" onclick=activity_operator("'+APP_ID+'","'+mid+'","'+precursorId+'","'+gridID+'") />&nbsp;&nbsp;&nbsp;&nbsp;';
										
									
								 //判断是否使用查看
								   var loadViewUrl='formId=${listPage.viewPage.id}';
								   <%
										ViewPage viewPage=((ListPage)request.getAttribute("listPage")).getViewPage();
										if(viewPage!=null){
											if(viewPage.getViewPageParams()!=null){
											Param param=null;
												for(int i=0;i<viewPage.getViewPageParams().size();i++){
													param=(Param)viewPage.getViewPageParams().get(i);
													out.println(" loadViewUrl=loadViewUrl+'&"+param.getKey()+"='+rowData."+param.getKey()+";");
												}
											}
										}
									%>
								 opertorString=opertorString+'<img  style="cursor:hand;" title="查看详情" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/images/mokuai.png" onclick=loadViewPage("'+loadViewUrl+'","'+viewDataDivID+'","${viewPage.isUseTab}","'+viewWindowID+'","&nbsp;${viewPage.title}","viewPage_${listPageRander}"); />&nbsp;&nbsp;&nbsp;&nbsp;';
								 opertorString=opertorString+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
										  
								  	
						       return opertorString;
							}
						}
				 </c:when>
		    </c:choose>
			]],
			toolbar:[ 
						<c:choose>
							<c:when test="${empty gridButton_add}"></c:when>
							<c:otherwise>
								{
									id:'btnadd',
									text:'添加',
									iconCls:'icon-add',
									handler:function(){
										loadEditPage_easyui(""+editFormID,"&gridID=fd_formlist_table_${listPageRander}&startOrNextNode=start","0","${idkey}",""+editPageDataDiv,"${listPage.editPage.isUseTab}",""+editWindowID,"&nbsp;月度预算","editPage_${listPageRander}","${listPage.parentAppId}");
									}
							   },'-',	
						    </c:otherwise>
						 </c:choose>	
						<s:iterator value="#request.listPage.gridButton">
						   <c:choose>
								<c:when test="${buttonName eq 'deleteBatch'}">
								   {
										id:'btnsave',
										text:'批量删除',
										iconCls:'icon-remove',
										handler:function(){
											bulkDelete(gridID,'${deleteIdkey}','${listPage.id}');
									    }
							 		}
							 		 ,'-',
					 		 	</c:when>
							</c:choose>
						</s:iterator>
						{
							id:'btnexport',
							text:'导出',
							iconCls:'icon-export',
							handler:function(){
								$("#btnexport").attr("href","com_exportForListPage.action");
						    }
				 		},'->',
				 		{
								text:'编辑',
								iconCls:'icon-edit',
								handler:function(){
								}
							}
						,{
								text:'查看',
								iconCls:'icon-view',
								handler:function(){
								}
						}
						,{
								text:'删除',
								iconCls:'icon-remove',
								handler:function(){
								}
						}
				]
		});	
	});
</script>
<input type="hidden"  id="init_${listPage.id}" value="0"  />