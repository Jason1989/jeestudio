<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.zxt.compplatform.formengine.entity.view.ListPage"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.EditPage"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.Param"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.ViewPage"%>
<%@page import="java.util.Map" %>
<%@page import="com.zxt.compplatform.formengine.constant.Constant"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.CopyPage"%>
<%@page import="com.zxt.compplatform.formengine.util.PropertiesUtil"%>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%@ page trimDirectiveWhitespaces="true" %>

<%
 	//request.setAttribute("listPageRander",RandomGUID.geneGuid());//生成表单列ID
 	request.setAttribute("listPageRander",request.getAttribute("listPageRander"));//生成表单列ID 此处是第一次固化需要用到
 	Map authority=(Map)request.getSession().getAttribute("authority");
 	ListPage listPage=(ListPage)request.getAttribute("listPage");//得到listPage信息 从xml中解析取得 
 	String workflowFiter = (String)request.getAttribute("workflowFiter");
 	ViewPage viewPage = null;
 	EditPage editPage = null;
 	CopyPage copyPage = null;
 	EditPage editPage1 = null;
 	String	deleteIDFields=null;
	String  []  deleteKeyArray=null;
	String basePath = PropertiesUtil.findSystemPath(request);
%>
<base href="<%=basePath%>">
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

<!-- 查看复制的参数 -->
<s:iterator value="#request.listPage.rowButton" >
   <c:choose>
		<c:when test="${buttonName eq 'copy'}">
		 <s:iterator value="event" >
		   <s:iterator value="paras" >
		  	 <c:set scope="page" var="copyIdkey" value="${value}"  ></c:set>
		   </s:iterator>  
		 </s:iterator>  
		</c:when>
	</c:choose>
</s:iterator>  
<c:choose>
	<c:when test="${empty copyIdkey}">
		<c:set scope="page" var="copyIdkey" value="keynull"  ></c:set>
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
	<!-- 第一次页面固化。用于得到其值。 EngineCodeGenerateAction GUOWEIXI -->
    
    <!-- 初始化一次窗口的flag -->
	<input type="hidden" id="editPage_${listPageRander}" value="0"  >
	<input type="hidden" id="copyPage_${listPageRander}" value="0"  >
	<input type="hidden" id="viewPage_${listPageRander}" value="0"  >
	<input type="hidden" id="queryPage_${listPageRander}" value="0"  >

	
	<jsp:include page="/common/jsp/easyui/editPageWindow.jsp"></jsp:include>
	<jsp:include page="/common/jsp/easyui/queryPageWindow.jsp"></jsp:include>
	<jsp:include page="/common/jsp/easyui/viewPageWindow.jsp"></jsp:include>
	<jsp:include page="/common/jsp/easyui/copyPageWindow.jsp"></jsp:include>

</div>

<!-- 判断是否加载工作流信息 -->
<%
	if(request.getAttribute("isAbleWorkFlow")!=null){
		if(Constant.WORKFLOW_ENABLE.equals(request.getAttribute("isAbleWorkFlow").toString())){
%>
	<jsp:include page="/common/jsp/easyui/workflow_list.jsp"></jsp:include>
<%		
		}
	}	
%>

<script>

<!-- 行操作图标 -->
	var jsstr_${listPage.id} = "";
	var returnstr_${listPage.id} = "";
	var buttonrules;
	
	<s:iterator value="#request.listPage.rowButton2" >
	   <c:choose>
			<c:when test="${isshow eq '1' && buttonrules ne ''}">
				 buttonrules = eval('(${buttonrules})');
				 if('zidingyi' == buttonrules.isJS){
				 	 jsstr_${listPage.id} = "function "+buttonrules.funcname+"(rowDataxxx){var rowData = eval('('+decodeURIComponent(rowDataxxx)+')');var path = '<%=request.getContextPath()%>';"+decodeURIComponent(buttonrules.funcmethod)+"}";
					 eval(jsstr_${listPage.id});
					 returnstr_${listPage.id} += '<img style="cursor:hand;vertical-align:middle;" title="${buttonname}" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/${buttonicon}" onclick='+buttonrules.funcname+'(rowData) />&nbsp;&nbsp;&nbsp;&nbsp;';
				 }
			</c:when>
		</c:choose>
	</s:iterator> 


$(function(){
			
		/**
		 * 定义编辑页保存业务数据 的参数
		 */
	    var formID='editform_${listPage.editPage.id}';//定义窗口ID
		var gridID='fd_formlist_table_${listPageRander}';
	    var editWindowID='editPageWindow_${listPageRander}';
	    var copyWindowID='copyPageWindow_${listPageRander}';
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
	    <c:choose>
			<c:when test="${empty listPage.addPageAttribute}">
		var addFormID=editFormID;// 添加页 提交的formID  @GUOWEIXIN
			</c:when>
			<c:otherwise>
		var addFormID='${listPage.addPageAttribute}';//添加页 提交的formID
			</c:otherwise>
	    </c:choose>
	    <c:choose>
			<c:when test="${empty listPage.copyPage.editPage.id}">
		var copyFormID='-1';// 复制页 提交的formID
			</c:when>
			<c:otherwise>
		var copyFormID='${listPage.copyPage.editPage.id}'+'@'+'${listPage.editPage.id}';//复制页 提交的formID
			</c:otherwise>
	    </c:choose>
		var queryFormID='queryPage_${listPage.id}';//组合查询表单提交的ID;
	 	var gridUrl=PROJECTNAME+"${listPage.gridUrl}";
	 		gridUrl=encodeURI(gridUrl)+"&date=" + new Date().getTime();
		var editPageDataDiv='editColumn_${listPageRander}';
		var copyPageDataDiv='copyColumn_${listPageRander}';
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
	     * 列表页 初始化
	     */
	    $('#'+'fd_formlist_table_${listPageRander}').datagrid({
	    	iconCls: 'icon-grid',
			nowrap: false,
			fit: true,
			<c:choose>
				<c:when test="${!empty listPage.queryZonePanel}">
			searchbar:searchbar,
				</c:when>
			</c:choose>
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
		    fitColumns:true,
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
		  		<c:choose>
				 	<c:when test="${listPage.opeColumnAlign eq 'before'}">
					<c:choose>
					<c:when test="${listPage.isShowOperator eq 'true'}">
						{field:'col4',title:'操作',width:100,rowspan:2,align:'center',	
							formatter:function(value,rowData,rowIndex){
							    var dataID=rowData.${pageScope.idkey};
								var opertorString='';
								// 判断是否使用编辑
								<c:choose>
									<c:when test="${idkey =='keynull'}"></c:when>
									<c:otherwise>
								var loadEditUrl='&listFormID=${listPage.id}&listPageRander=${listPageRander}';
										<%
											editPage=((ListPage)request.getAttribute("listPage")).getEditPage();
											if(editPage!=null){
												if(editPage.getEditPageParams()!=null){
												Param param=null;
													for(int i=0;i<editPage.getEditPageParams().size();i++){
														param=(Param)editPage.getEditPageParams().get(i);
														out.println(" loadEditUrl=loadEditUrl+'&"+param.getKey()+"='+rowData."+param.getKey()+";");
													}
												}
											}
										%>
										<%  
										if(authority!=null){
											if(authority.get("'"+listPage.getId()+"_edit'")!=null){
										%>
											<jsp:include page="/common/jsp/easyui/listpage-operator-edit.jsp"></jsp:include>
										<% 
												}
										  }else{
										%>
											<jsp:include page="/common/jsp/easyui/listpage-operator-edit.jsp"></jsp:include>
										<%
											}
										%>
									</c:otherwise>
								</c:choose>
								// 判断是否使用复制
								<c:choose>
									<c:when test="${copyIdkey =='keynull'}"></c:when>
									<c:otherwise>
									   var loadCopyUrl='&listFormID=${listPage.id}';
										<%
											copyPage=((ListPage)request.getAttribute("listPage")).getCopyPage();
											editPage1=((ListPage)request.getAttribute("listPage")).getEditPage();
											if(editPage1!=null){
												if(editPage1.getEditPageParams()!=null){
												Param param=null;
													for(int i=0;i<editPage1.getEditPageParams().size();i++){
														param=(Param)editPage1.getEditPageParams().get(i);
														out.println(" loadCopyUrl=loadCopyUrl+'&method=copy&"+param.getKey()+"='+rowData."+param.getKey()+";");
													}
												}
											}
										%>
										
										<%  
										if(authority!=null){
											if(authority.get("'"+listPage.getId()+"_copy'")!=null){
										%>
											opertorString+='<img  style="cursor:hand;vertical-align:middle;" title="复制" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/images/ioc-copy.png" onclick=loadEditPage_easyui("${listPage.id}","${isAbleWorkFlow}","'+copyFormID+'","'+loadCopyUrl+'","1","${idkey}","'+copyPageDataDiv+'","false","'+copyWindowID+'","&nbsp;${copyPage.editPage.title}","copyPage_${listPageRander}") />&nbsp;&nbsp;&nbsp;&nbsp;';
										<% 
												}
										  }else{
											
										%>
											opertorString+='<img  style="cursor:hand;vertical-align:middle;" title="复制" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/images/ioc-copy.png" onclick=loadEditPage_easyui("${listPage.id}","${isAbleWorkFlow}","'+copyFormID+'","'+loadCopyUrl+'","1","${idkey}","'+copyPageDataDiv+'","false","'+copyWindowID+'","&nbsp;${copyPage.editPage.title}","copyPage_${listPageRander}") />&nbsp;&nbsp;&nbsp;&nbsp;';
										<%
											}
										%>
									</c:otherwise>
								</c:choose>
								//判断是否使用查看
								<c:choose>
									<c:when test="${viewIdkey =='keynull'}"></c:when>
									<c:otherwise>
									   var loadViewUrl='${listPage.viewPage.id}';
									   <%
											viewPage=((ListPage)request.getAttribute("listPage")).getViewPage();
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
										
									<%  
									  if(authority!=null){
										if(authority.get("'"+listPage.getId()+"_view'")!=null){
									%>
										opertorString=opertorString+'<img  style="cursor:hand;vertical-align:middle;" title="查看详情" sytle="cursor:pointer;" src="<%= basePath%>images/mokuai.png" onclick=loadViewPage("'+loadViewUrl+'","'+viewDataDivID+'","${viewPage.isUseTab}","'+viewWindowID+'","&nbsp;${viewPage.title}","viewPage_${listPageRander}","${viewPage.selfDefineHeight}","${viewPage.selfDefineWidth}"); />&nbsp;&nbsp;&nbsp;&nbsp;';
									<% 
										}
									  }else{
											
									%>
										opertorString=opertorString+'<img  style="cursor:hand;vertical-align:middle;" title="查看详情" sytle="cursor:pointer;" src="<%= basePath%>images/mokuai.png" onclick=loadViewPage("'+loadViewUrl+'","'+viewDataDivID+'","${viewPage.isUseTab}","'+viewWindowID+'","&nbsp;${viewPage.title}","viewPage_${listPageRander}","${viewPage.selfDefineHeight}","${viewPage.selfDefineWidth}"); />&nbsp;&nbsp;&nbsp;&nbsp;';
									<%
										}
									%>
									</c:otherwise>
								</c:choose>
								//删除列
		
								<c:choose>
									<c:when test="${deleteIdkey =='keynull'}"></c:when>
									<c:otherwise>
										var deleteUrl='listFormID=${listPage.id}';
										<%
											deleteIDFields=pageContext.getAttribute("deleteIdkey").toString();
											deleteKeyArray=deleteIDFields.split(",");
											for(int i=0;i<deleteKeyArray.length;i++){
											if("".equals(deleteKeyArray[i])||(deleteKeyArray[i]==null)){
										    
											    }else{
														out.println(" deleteUrl=deleteUrl+'&"+deleteKeyArray[i]+"='+rowData."+deleteKeyArray[i]+";");
												 }
											}
										%>
									<%  
									  if(authority!=null){
										if(authority.get("'"+listPage.getId()+"_delete'")!=null){
									%>
									    opertorString=opertorString+'<img  style="cursor:hand;vertical-align:middle;" title="删除" sytle="cursor:pointer;" src="<%= basePath%>images/ioc-delete.gif"  onclick=deleteData_easyui("'+deleteUrl+'","'+gridID+'"); />&nbsp;&nbsp;&nbsp;&nbsp;';
									<% 
										}
								
									  }else{
									%>
									    opertorString=opertorString+'<img  style="cursor:hand;vertical-align:middle;" title="删除" sytle="cursor:pointer;" src="<%= basePath%>images/ioc-delete.gif"  onclick=deleteData_easyui("'+deleteUrl+'","'+gridID+'"); />&nbsp;&nbsp;&nbsp;&nbsp;';
									<%
										}
									%>
									</c:otherwise>
								</c:choose>
								
								<s:iterator value="#request.listPage.rowButton2" >
								   <c:choose>
										<c:when test="${isshow eq '1' && buttonrules ne ''}">
											 buttonrules = eval('(${buttonrules})');
											 if('jiekou' == buttonrules.isJS){
											 	  if('editPage' == buttonrules.tabspagetype){
											 	  	  var loadEditUrl_JS='&listFormID=${listPage.id}&listPageRander=${listPageRander}';
											 	  	  <s:iterator value="editpage.editPageParams" >
											 	  	  		loadEditUrl_JS += '&${key}='+rowData['${key}'];
											 	  	  </s:iterator> 
											 	  	  opertorString += '<img style="cursor:hand;vertical-align:middle;" title="${buttonname}" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/${buttonicon}" onclick=createSavebutt("${isAbleWorkFlow}","<%=Constant.WORKFLOW_ENABLE%>","${listPageRander}","${listPage.id}","'+buttonrules.tabspageurl+'","'+loadEditUrl_JS+'","0","${idkey}","'+editPageDataDiv+'","${editPage.isUseTab}","'+editWindowID+'","&nbsp;'+buttonrules.tabstitle+'","editPage_${listPageRander}") />&nbsp;&nbsp;&nbsp;&nbsp;';
											 	  }else if('listPage' == buttonrules.tabspagetype){
											 	  	  var params = '&';
														if(''!=buttonrules.params){
															var paramarr = buttonrules.params.split(/&/g);
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
											 	  	  opertorString += '<img style="cursor:hand;vertical-align:middle;" title="${buttonname}" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/${buttonicon}" onclick=OpenEditPage("'+buttonrules.tabstitle+'","600","380","'+LISTPAGE_LOAD+buttonrules.tabspageurl+params+'") />&nbsp;&nbsp;&nbsp;&nbsp;';
											 	  }else if('viewPage' == buttonrules.tabspagetype){
											 	  	                  //'<img  style="cursor:hand;vertical-align:middle;" title="查看详情" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/images/mokuai.png" onclick=loadViewPage("'+loadViewUrl+'","'+viewDataDivID+'","${viewPage.isUseTab}","'+viewWindowID+'","&nbsp;${viewPage.title}","viewPage_${listPageRander}"); />&nbsp;&nbsp;&nbsp;&nbsp;';
											 	  	  var loadViewUrl_JS='formId=${viewpage.id}';
											 	  	  <s:iterator value="viewpage.viewPageParams" >
											 	  	  		loadViewUrl_JS += '&${key}='+rowData['${key}'];
											 	  	  </s:iterator> 
											 	  	  //opertorString += '<img style="cursor:hand;vertical-align:middle;" title="${buttonname}" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/${buttonicon}" onclick=loadViewPage("'+buttonrules.tabspageurl+'","'+loadEditUrl_JS+'","1","${idkey}","'+editPageDataDiv+'","${editPage.isUseTab}","'+editWindowID+'","&nbsp;'+buttonrules.tabstitle+'","editPage_${listPageRander}") />&nbsp;&nbsp;&nbsp;&nbsp;';
													 	  }
													 	  
													 }
												</c:when>
											</c:choose>
										</s:iterator> 
										
									   var returnstr_${listPage.id}xxx = returnstr_${listPage.id}.replace(/(rowData)/g,'(\''+encodeURIComponent(JSON2.stringify(rowData))+'\')');
									   opertorString += returnstr_${listPage.id}xxx;
									   return opertorString;
									}
								},
						 </c:when>
				    </c:choose>
						 	</c:when>
						 </c:choose>
				  			
						<s:iterator value="#request.listPage.fields" status="status"   >
							<s:if test="#status.first"></s:if>
						    <s:else>,</s:else>    
							{
						        field: '${dataColumn}',
						        title: '${name}',
						        align: '${align}',
						        sortable:true,   	
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
										, width: 120
									</c:when>
									<c:otherwise>
										, width: ${width}
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${empty columnrules}">
										
									</c:when>
									<c:otherwise>
										,formatter:function(value,rowData){
											var columnrules = eval('('+decodeURIComponent('${columnrules}')+')');
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
									</c:otherwise>
								</c:choose>
						     }
						 </s:iterator>
						<c:choose>
						 	<c:when test="${listPage.opeColumnAlign eq 'end'}">
		<c:choose>
							<c:when test="${listPage.isShowOperator eq 'true'}">
								,{field:'col4',title:'操作',width:100,rowspan:2,align:'center',	
									formatter:function(value,rowData,rowIndex){
									    var dataID=rowData.${pageScope.idkey};
										var opertorString='';
										// 判断是否使用编辑
										<c:choose>
											<c:when test="${idkey =='keynull'}"></c:when>
											<c:otherwise>
											   var loadEditUrl='&listFormID=${listPage.id}&listPageRander=${listPageRander}';
												<%
											editPage=((ListPage)request.getAttribute("listPage")).getEditPage();
											if(editPage!=null){
												if(editPage.getEditPageParams()!=null){
												Param param=null;
													for(int i=0;i<editPage.getEditPageParams().size();i++){
														param=(Param)editPage.getEditPageParams().get(i);
														out.println(" loadEditUrl=loadEditUrl+'&"+param.getKey()+"='+rowData."+param.getKey()+";");
													
													}
												}
											}
										%>
										
										<%  
										if(authority!=null){
											if(authority.get("'"+listPage.getId()+"_edit'")!=null){
										%>
											<jsp:include page="/common/jsp/easyui/listpage-operator-edit.jsp"></jsp:include>
									
										<% 
												}
										  }else{
											
										%>
											<jsp:include page="/common/jsp/easyui/listpage-operator-edit.jsp"></jsp:include>
										<%
											}
										%>
									</c:otherwise>
								</c:choose>
								// 判断是否使用复制
								<c:choose>
									<c:when test="${copyIdkey =='keynull'}"></c:when>
									<c:otherwise>
									   var loadCopyUrl='&listFormID=${listPage.id}';
										<%
											copyPage=((ListPage)request.getAttribute("listPage")).getCopyPage();
											editPage1=((ListPage)request.getAttribute("listPage")).getEditPage();
											if(editPage1!=null){
												if(editPage1.getEditPageParams()!=null){
												Param param=null;
													for(int i=0;i<editPage1.getEditPageParams().size();i++){
														param=(Param)editPage1.getEditPageParams().get(i);
														out.println(" loadCopyUrl=loadCopyUrl+'&method=copy&"+param.getKey()+"='+rowData."+param.getKey()+";");
													}
												}
											}
										%>
										
										<%  
										if(authority!=null){
											if(authority.get("'"+listPage.getId()+"_copy'")!=null){
										%>
											opertorString+='<img  style="cursor:hand;vertical-align:middle;" title="复制" sytle="cursor:pointer;" src="<%=basePath%>/images/ioc-copy.png" onclick=loadEditPage_easyui("${listPage.id}","${isAbleWorkFlow}","'+copyFormID+'","'+loadCopyUrl+'","1","${idkey}","'+copyPageDataDiv+'","false","'+copyWindowID+'","&nbsp;${copyPage.editPage.title}","copyPage_${listPageRander}") />&nbsp;&nbsp;&nbsp;&nbsp;';
										<% 
												}
										  }else{
											
										%>
											opertorString+='<img  style="cursor:hand;vertical-align:middle;" title="复制" sytle="cursor:pointer;" src="<%=basePath%>/images/ioc-copy.png" onclick=loadEditPage_easyui("${listPage.id}","${isAbleWorkFlow}","'+copyFormID+'","'+loadCopyUrl+'","1","${idkey}","'+copyPageDataDiv+'","false","'+copyWindowID+'","&nbsp;${copyPage.editPage.title}","copyPage_${listPageRander}") />&nbsp;&nbsp;&nbsp;&nbsp;';
										<%
											}
										%>
									</c:otherwise>
								</c:choose>
								//判断是否使用查看
								<c:choose>
									<c:when test="${viewIdkey =='keynull'}"></c:when>
									<c:otherwise>
									   var loadViewUrl='${listPage.viewPage.id}';
									   <%
											viewPage=((ListPage)request.getAttribute("listPage")).getViewPage();
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
										
									<%  
									  if(authority!=null){
										if(authority.get("'"+listPage.getId()+"_view'")!=null){
									%>
										opertorString=opertorString+'<img  style="cursor:hand;vertical-align:middle;" title="查看详情" sytle="cursor:pointer;" src="<%=basePath%>images/mokuai.png" onclick=loadViewPage("'+loadViewUrl+'","'+viewDataDivID+'","${viewPage.isUseTab}","'+viewWindowID+'","&nbsp;${viewPage.title}","viewPage_${listPageRander}","${viewPage.selfDefineHeight}","${viewPage.selfDefineWidth}"); />&nbsp;&nbsp;&nbsp;&nbsp;';
									<% 
										}
									  }else{
											
									%>
										opertorString=opertorString+'<img  style="cursor:hand;vertical-align:middle;" title="查看详情" sytle="cursor:pointer;" src="<%=basePath%>images/mokuai.png" onclick=loadViewPage("'+loadViewUrl+'","'+viewDataDivID+'","${viewPage.isUseTab}","'+viewWindowID+'","&nbsp;${viewPage.title}","viewPage_${listPageRander}","${viewPage.selfDefineHeight}","${viewPage.selfDefineWidth}"); />&nbsp;&nbsp;&nbsp;&nbsp;';
									<%
										}
									%>
									</c:otherwise>
								</c:choose>
								//删除列
		
								<c:choose>
									<c:when test="${deleteIdkey =='keynull'}"></c:when>
									<c:otherwise>
										var deleteUrl='listFormID=${listPage.id}';
										<%
											deleteIDFields=pageContext.getAttribute("deleteIdkey").toString();
											deleteKeyArray=deleteIDFields.split(",");
											for(int i=0;i<deleteKeyArray.length;i++){
											if("".equals(deleteKeyArray[i])||(deleteKeyArray[i]==null)){
										    
											    }else{
														out.println(" deleteUrl=deleteUrl+'&"+deleteKeyArray[i]+"='+rowData."+deleteKeyArray[i]+";");
												 }
											}
										%>
									<%  
									  if(authority!=null){
										if(authority.get("'"+listPage.getId()+"_delete'")!=null){
									%>
									    opertorString=opertorString+'<img  style="cursor:hand;vertical-align:middle;" title="删除" sytle="cursor:pointer;" src="<%=basePath%>images/ioc-delete.gif"  onclick=deleteData_easyui("'+deleteUrl+'","'+gridID+'"); />&nbsp;&nbsp;&nbsp;&nbsp;';
									<% 
										}
								
									  }else{
									%>
									    opertorString=opertorString+'<img  style="cursor:hand;vertical-align:middle;" title="删除" sytle="cursor:pointer;" src="<%=basePath%>images/ioc-delete.gif"  onclick=deleteData_easyui("'+deleteUrl+'","'+gridID+'"); />&nbsp;&nbsp;&nbsp;&nbsp;';
									<%
										}
									%>
									</c:otherwise>
								</c:choose>
								
								<s:iterator value="#request.listPage.rowButton2" >
								   <c:choose>
										<c:when test="${isshow eq '1' && buttonrules ne ''}">
											 buttonrules = eval('(${buttonrules})');
											 if('jiekou' == buttonrules.isJS){
											 	  if('editPage' == buttonrules.tabspagetype){
											 	  	  var loadEditUrl_JS='&listFormID=${listPage.id}&listPageRander=${listPageRander}';
											 	  	  <s:iterator value="editpage.editPageParams" >
											 	  	  		loadEditUrl_JS += '&${key}='+rowData['${key}'];
											 	  	  </s:iterator> 
											 	  	  opertorString += '<img style="cursor:hand;vertical-align:middle;" title="${buttonname}" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/${buttonicon}" onclick=createSavebutt("${isAbleWorkFlow}","<%=Constant.WORKFLOW_ENABLE%>","${listPageRander}","${listPage.id}","'+buttonrules.tabspageurl+'","'+loadEditUrl_JS+'","0","${idkey}","'+editPageDataDiv+'","${editPage.isUseTab}","'+editWindowID+'","&nbsp;'+buttonrules.tabstitle+'","editPage_${listPageRander}") />&nbsp;&nbsp;&nbsp;&nbsp;';
											 	  }else if('listPage' == buttonrules.tabspagetype){
											 	  	  var params = '&';
														if(''!=buttonrules.params){
															var paramarr = buttonrules.params.split(/&/g);
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
											 	  	  opertorString += '<img style="cursor:hand;vertical-align:middle;" title="${buttonname}" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/${buttonicon}" onclick=OpenEditPage("'+buttonrules.tabstitle+'","600","380","'+LISTPAGE_LOAD+buttonrules.tabspageurl+params+'") />&nbsp;&nbsp;&nbsp;&nbsp;';
											 	  }else if('viewPage' == buttonrules.tabspagetype){
											 	  	                  //'<img  style="cursor:hand;vertical-align:middle;" title="查看详情" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/images/mokuai.png" onclick=loadViewPage("'+loadViewUrl+'","'+viewDataDivID+'","${viewPage.isUseTab}","'+viewWindowID+'","&nbsp;${viewPage.title}","viewPage_${listPageRander}"); />&nbsp;&nbsp;&nbsp;&nbsp;';
											 	  	  var loadViewUrl_JS='formId=${viewpage.id}';
											 	  	  <s:iterator value="viewpage.viewPageParams" >
											 	  	  		loadViewUrl_JS += '&${key}='+rowData['${key}'];
											 	  	  </s:iterator> 
											 	  	  //opertorString += '<img style="cursor:hand;vertical-align:middle;" title="${buttonname}" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/${buttonicon}" onclick=loadViewPage("'+buttonrules.tabspageurl+'","'+loadEditUrl_JS+'","1","${idkey}","'+editPageDataDiv+'","${editPage.isUseTab}","'+editWindowID+'","&nbsp;'+buttonrules.tabstitle+'","editPage_${listPageRander}") />&nbsp;&nbsp;&nbsp;&nbsp;';
											 	  }
											 	  
											 }
										</c:when>
									</c:choose>
								</s:iterator> 
								
							   var returnstr_${listPage.id}xxx = returnstr_${listPage.id}.replace(/(rowData)/g,'(\''+encodeURIComponent(JSON2.stringify(rowData))+'\')');
							   opertorString += returnstr_${listPage.id}xxx;
							   return opertorString;
							}
						}
				 </c:when>
		    </c:choose>
				 	</c:when>
				 </c:choose>
			]],
			toolbar:[
						<c:choose>
							<c:when test="${empty listPage.queryZonePanel}"></c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${listPage.queryZonePanel.showQuery eq '0'}">
										/**
											{
												id:'btnadd',
												text:'查询',
											    iconCls: 'icon-search',
												handler:function(){
													initOpenWindow('queryPageWindow_${listPageRander}','&nbsp;','queryPage_${listPageRander}'); 
												}
											 },'-',
										 **/
									</c:when>
								</c:choose>
							</c:otherwise>
						</c:choose>
					<c:choose>
					<c:when test="${empty gridButton_add}"></c:when>
					<c:otherwise>
						
						<%  
						  if(authority!=null){
							if(authority.get("'"+listPage.getId()+"_add'")!=null){
						%>
										
							{
								id:'btnadd',
								text:'添加',
								iconCls:'icon-add',
								handler:function(){
									loadEditPage_easyui("${listPage.id}","${isAbleWorkFlow}",""+addFormID,"","0","${idkey}",""+editPageDataDiv,"${listPage.editPage.isUseTab}",""+editWindowID,"&nbsp;${listPage.editPage.addTitle}","editPage_${listPageRander}","${listPage.parentAppId}${listPageParamerUrl}","${editPage.selfDefineHeight}","${editPage.selfDefineWidth}");
								  //loadEditPage(editFormID,"-1","0","${idkey}","editColumn_${listPage.id}","${editPage.isUseTab}",editWindowID,"&nbsp;","editPage_${listPageRander}");
								}
							 },'-',
						
						<% 
							}
					  	}else{
						%>
							{
								id:'btnadd',
								text:'添加',
								iconCls:'icon-add',
								handler:function(){
									loadEditPage_easyui("${listPage.id}","${isAbleWorkFlow}",""+addFormID,"","0","${idkey}",""+editPageDataDiv,"${listPage.editPage.isUseTab}",""+editWindowID,"&nbsp;${listPage.editPage.addTitle}","editPage_${listPageRander}","${listPage.parentAppId}${listPageParamerUrl}","${editPage.selfDefineHeight}","${editPage.selfDefineWidth}");
									 //loadEditPage(editFormID,"-1","0","${idkey}","editColumn_${listPage.id}","${editPage.isUseTab}",editWindowID,"&nbsp;","editPage_${listPageRander}");
								}
							 },'-',
						<%
						}
						%>
					</c:otherwise>
					</c:choose>	
					<s:iterator value="#request.listPage.gridButton">
					   <c:choose>
							<c:when test="${buttonName eq 'deleteBatch'}">
								<%  
								  if(authority!=null){
									if(authority.get("'"+listPage.getId()+"_batchDelete'")!=null){
								%>
										{
											id:'btnsave',
											text:'批量删除',
											iconCls:'icon-remove',
											handler:function(){
												bulkDelete(gridID,'${deleteIdkey}','${listPage.id}');
										    }
								 		},'-',
						 		<% 
									}
							  	  }else{
								%>
									 {
										id:'btnsave',
										text:'批量删除',
										iconCls:'icon-remove',
										handler:function(){
											bulkDelete(gridID,'${deleteIdkey}','${listPage.id}');
									    }
							 		  },'-',
						<%
							}
						%>
							</c:when>
							<c:when test="${buttonName eq 'import'}">
								<%  
								  if(authority!=null){
									 if(authority.get("'"+listPage.getId()+"_import'")!=null){
								%>
										{
											id:'btnimport',
											text:'导入',
											iconCls:'icon-import',
											handler:function(){
												$.messager.alert('提示','没有数据','info');
										    }
								 		},'-',
							 	<% 
									 }
							  	}else{
								%>
									   {
											id:'btnimport',
											text:'导入',
											iconCls:'icon-import',
											handler:function(){
												$.messager.alert('提示','没有数据','info');
										    }
								 		},'-',
								<%
								 }
								%>
							</c:when>
							<c:when test="${buttonName eq 'export'}">
								<%  
								  if(authority!=null){
									if(authority.get("'"+listPage.getId()+"_export'")!=null){
								%>
										{
											id:'btnexport',
											text:'导出',
											iconCls:'icon-export',
											handler:function(){
												var row_${listPage.id} = $('#'+gridID).datagrid('getRows');
												if(!row_${listPage.id}.length>0){
													$.messager.alert('提示','没有数据','info');
													return;
												};
												OpenWindowPage1('initdiv_${listPage.id}_daochu','选择导出的字段','init_${listPage.id}_daochu','<%=request.getContextPath()%>/common/jsp/easyui/list_page_daochucolumns.jsp?formId='+'${listPage.id}','380',gridID);
												return;
										    }
								 		},'-',
							 	<% 
									}
									
						  		 }else{
								%>
										{
											id:'btnexport',
											text:'导出',
											iconCls:'icon-export',
											handler:function(){
												var row_${listPage.id} = $('#'+gridID).datagrid('getRows');
												if(!row_${listPage.id}.length>0){
													$.messager.alert('提示','没有数据','info');
													return;
												};
												OpenWindowPage1('initdiv_${listPage.id}_daochu','选择导出的字段','init_${listPage.id}_daochu','<%=request.getContextPath()%>/common/jsp/easyui/list_page_daochucolumns.jsp?formId='+'${listPage.id}','380',gridID);
												return;
										    }
								 		},'-',
						 		
								<%
								}
								%>
							</c:when>
						</c:choose>
						
					</s:iterator>
					
					<s:iterator value="#request.listPage.gridButton2" status='st'>
					     {
							id:'zidingyi_'+'#st.index',
							text:'${buttonname}',
							iconCls:'${buttonicon}',
							handler:function(){
								if('(${buttonrules})' != ''){
									var buttonrules = eval('(${buttonrules})');
									var path = '<%=request.getContextPath()%>';
									 if('zidingyi' == buttonrules.isJS){
									 
										eval(decodeURIComponent(buttonrules.funcmethod));
										
									 }else if('jiekou' == buttonrules.isJS){
									 	 if('editPage' == buttonrules.tabspagetype){
									 	  	
									 	  }else if('listPage' == buttonrules.tabspagetype){
									 	  	  var params = '&';
												if(''!=buttonrules.params){
													var paramarr = buttonrules.params.split(/&/g);
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
											  if((buttonrules.tabspageurl+params).indexOf(".")!=-1){
											   		var gridButtionInterfaceUrl=buttonrules.tabspageurl+params;
									 	  	  }else{
											   		var gridButtionInterfaceUrl=LISTPAGE_LOAD+buttonrules.tabspageurl+params;
									 	  	  }
											  OpenWindowPage("initdiv_${listPage.id}_listpagezdy",buttonrules.tabstitle,"init_${listPage.id}_listpagezdy",gridButtionInterfaceUrl,"380");
									 	  }else if('viewPage' == buttonrules.tabspagetype){
									 	  }
									 
									 }
								}
						    }
				 		},'-',
					</s:iterator>
					 	   <c:choose>
								<c:when test="${isShowTreeMenu eq '1'}">
											{
												id:'xiugai',
												<c:choose>
												<c:when test="${manageName eq '修改树'}">
													text:'修改树',
												</c:when>
												<c:otherwise>
							 	   					text:"${manageName}",
							    				</c:otherwise>
					 							</c:choose>
												
												
												iconCls:'icon-organisation',
												handler:function(){
														var url='formengine/listPageAction.action?formId=${treelistpageID}';
														<c:choose>
														<c:when test="${manageName eq '修改树'}">
															openwindowhandle(url,'handle${listPageRander}','修改树','${treeID}');
														</c:when>
														<c:otherwise>
									 	   					openwindowhandle(url,'handle${listPageRander}','${manageName}','${treeID}');
									    				</c:otherwise>
							 							</c:choose>
														
											    }
											   
											    
									 		}
								</c:when>
					 			<c:otherwise>
							 	   {}
							    </c:otherwise>
						</c:choose>
					 ,'->'
				]
	});	
	 // initGridData(gridID,gridUrl,'init_${listPage.id}','${isUseTab}');
	});
</script>
<input type="hidden"  id="init_${listPage.id}" value="0"  />
<input type="hidden"  id="init_${listPage.id}_daochu" value="0"  />
<div  id="initdiv_${listPage.id}_daochu"></div>
<input type="hidden"  id="init_${listPage.id}_listpagezdy" value="0"  />
<div  id="initdiv_${listPage.id}_listpagezdy"></div>
<c:choose>
	<c:when test="${isShowTreeMenu eq 1}">
		<c:choose>
			<c:when test="${manageName eq '修改树'}">
				<div id="handle${listPageRander}"></div>
			</c:when>
			<c:otherwise>
			<div id="handle${listPageRander}"></div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>
<!-- 0 未初始化 1已初始化 -->	