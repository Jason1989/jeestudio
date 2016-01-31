<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.zxt.compplatform.formengine.entity.view.ListPage"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.EditPage"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.Param"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.ViewPage"%>
<%@page import="java.util.Map" %>
<%@page import="com.zxt.compplatform.formengine.constant.Constant"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.CopyPage"%>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
 	request.setAttribute("listPageRander",RandomGUID.geneGuid());//生成表单列ID
 	Map authority=(Map)request.getSession().getAttribute("authority");
 	ListPage listPage=(ListPage)request.getAttribute("listPage");
 	String workflowFiter = (String)request.getAttribute("workflowFiter"); 
 	String listvalue = (String)request.getAttribute("listvalue"); 
 	String listtext = (String)request.getAttribute("listtext"); 
 	String id = (String)request.getAttribute("id"); 
%>
<!-- 列表  -->
<div id="${listPageRander}_gridPanel"  >
	<table  id="fd_formlist_table_${listPageRander}" ></table>
</div>

<div style="display: none"  >
    <!-- 初始化一次窗口的flag -->
	<input type="hidden" id="editPage_${listPageRander}" value="0"  >
	<input type="hidden" id="copyPage_${listPageRander}" value="0"  >
	<input type="hidden" id="viewPage_${listPageRander}" value="0"  >
	<input type="hidden" id="queryPage_${listPageRander}" value="0"  >
	<input type="hidden" id="editPage_${listPageRander}_zdy" value="0"  >
	
	<jsp:include page="/common/jsp/easyui/editPageWindow.jsp"></jsp:include>
	<jsp:include page="/common/jsp/easyui/queryPageWindow.jsp"></jsp:include>
	<jsp:include page="/common/jsp/easyui/viewPageWindow.jsp"></jsp:include>
	<jsp:include page="/common/jsp/easyui/copyPageWindow.jsp"></jsp:include>
	<jsp:include page="/common/jsp/easyui/editPageWindow_zdy.jsp"></jsp:include>
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
	var array  = new Array();
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
					 returnstr_${listPage.id} += '<img style="cursor:hand;" title="${buttonname}" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/${buttonicon}" onclick='+buttonrules.funcname+'(rowData) />&nbsp;&nbsp;&nbsp;&nbsp;';
				 }
			</c:when>
		</c:choose>
	</s:iterator> 
		
$(function(){
		/**
      * 定义编辑页保存业务数据 的参数
      */
       var formID='editform_${listPage.editPage.id}';
      
		/**
		 * 定义窗口ID
		 */
		var gridID='fd_formlist_table_${listPageRander}';
	    var editWindowID='editPageWindow_${listPageRander}';
	    var copyWindowID='copyPageWindow_${listPageRander}';
	    var viewWindowID='viewPageWindow_';
	    var viewDataDivID='viewPageData_';
	    
	    var editWindowID_zdy='editPageWindow_${listPageRander}_zdy';
		<c:choose>
			<c:when test="${empty listPage.editPage.id}">
				var editFormID='-1';// 编辑页 提交的formID
			</c:when>
			<c:otherwise>
				var editFormID='${listPage.editPage.id}';//编辑页 提交的formID
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
	 	var gridUrl="${listPage.gridUrl}";
	 		gridUrl=encodeURI(gridUrl)+"&date=" + new Date().getTime();
		var editPageDataDiv='editColumn_${listPageRander}';
		var copyPageDataDiv='copyColumn_${listPageRander}';
		var editPageDataDiv_zdy='editColumn_${listPageRander}_zdy';	
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
			height:'400',
			border: false
		});

		
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
				<c:when test="${listPage.isshowsingleSelect eq 'true'}">
					singleSelect: true,//一次只选中单行
				</c:when>
				<c:otherwise>
					singleSelect: false,//一次可以选多行
				</c:otherwise>
			</c:choose>	
					
		    url:gridUrl,
			sortName: 'code',//排序列
			sortOrder: 'desc',//排序方式
			remoteSort: false,//远程调用
			rownumbers:true,
		    cache:false,//是否缓存
		 	pagination:false,
		    pageList:[6,9,12],//每页显示的数据条数 下拉选中可选每页大小
		    page: 1,//第几页开始
      	  //remoteSort:'',//服务器的排序字段
		    fitColumns:true,
		    onSelect:function(rowIndex, rowData){
		    	var obj = new Object();
		    	obj.value = rowData['<%=listvalue%>'];
		    	obj.text = rowData['<%=listtext%>'];
		    	array.push(obj);
		    }, 	
		    onUnselect:function(rowIndex, rowData){
		    	for(var i = 0;i<array.length;i++){
		    		if(array[i].value == rowData['<%=listvalue%>']){
		    			array.splice(i,1);
		    			break;
		    		} 
		    	}
		    }, 	
		    onSelectAll:function(rows){
		    	for(var i = 0;i<rows.length;i++){
		    		var obj = new Object();
		    		obj.value = rows[i]['<%=listvalue%>'];
		    		obj.text = rows[i]['<%=listtext%>'];
		    		array.push(obj);
		    	}
		    },
		    onUnselectAll:function(rows){
		    	for(var i = 0;i<rows.length;i++){
		    		for(var j = 0;j<array.length;j++){
		    		if(array[j].value == rows[i]['<%=listvalue%>']){
		    			array.splice(j,1);
		    		}
		    	}
		    	}
		    },
		    onLoadSuccess:function(data){
		   		array = new Array();
		    	var val = $('#<%=id%>').val();
		    	var vals = [];
		    	if(val!=null&&val!=''){
		    		vals = val.split(',');
		    		for(var i = 0;i<vals.length;i++){
		    			for(var j = 0;j<data.rows.length;j++){
		    				if(data.rows[j]['<%=listvalue%>']==vals[i]){
		    					$('#'+gridID).datagrid('selectRow',j);
		    				}
		    			}
		    		}
		    	}
		    },
		  //idField: 'tab_id',//只能获取一行值

		 frozenColumns: [[
				 { field: 'ck', checkbox: true}
			]],
		  
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
										str_formatterrules = '<a href="javascript:;" style="text-decoration: underline"><font onclick=OpenWindowPage("initdiv_${listPage.id}_listpagezdy","'+columnrules.tabstitle+'","init_${listPage.id}_listpagezdy","'+LISTPAGE_LOAD+columnrules.tabspageurl+params+'","380") >'+value+'</font></a>';
									}else if('zidingyi'==columnrules.isJS){
									
									}
									return str_formatterrules;
								}
							</c:otherwise>
						</c:choose>
				     }
				 </s:iterator>
			]]
		});	
	 // initGridData(gridID,gridUrl,'init_${listPage.id}','${isUseTab}');
	});
	function getSelectedData(){
		var ids = [];
		var vals = [];
		var rows = $('#fd_formlist_table_${listPageRander}').datagrid('getSelections');
		for(var i=0;i<array.length;i++){
			ids.push(array[i].value);
			vals.push(array[i].text);
		}
		var values = ids.join(',');
		var texts = vals.join(',');
		$('#<%=id%>').val(values);
		$('#<%=id%>_block').val(texts);
		closeWindow('ParentOpenWindow');
	}
</script>
<input type="hidden"  id="init_${listPage.id}" value="0"  />
<input type="hidden"  id="init_${listPage.id}_listpagezdy" value="0"  />
<div  id="initdiv_${listPage.id}_listpagezdy"></div>
<div align="center" style="padding-top: 5px;">
	<a  class="easyui-linkbutton" icon="icon-ok"  href="javascript:getSelectedData();"  >确定</a>
	<a  class="easyui-linkbutton" icon="icon-cancel"  href="javascript:closeWindow('ParentOpenWindow');"  >关闭</a>
</div>	
<!-- 0 未初始化 1已初始化 -->
	