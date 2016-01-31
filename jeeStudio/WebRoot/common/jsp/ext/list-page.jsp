<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.zxt.compplatform.formengine.entity.view.ListPage"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.EditPage"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.Param"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.ViewPage"%>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>

<%
 	request.setAttribute("listPageRander",RandomGUID.geneGuid());//生成表单列ID
%>

<script type="text/javascript">
 	 $(window).resize(function(){ 
	    if(document.getElementById('queryarea_${listTabPageID}')){
			document.getElementById('queryarea_${listTabPageID}').style.width= $(this).width();
		}
	})
</script>

<!-- 修改按钮的参数 -->
<s:iterator value="#request.listPage.rowButton"  >
   <c:choose>
		<c:when test="${buttonName eq 'update'}">
				<c:set scope="page" var="idkey" value="notnull" ></c:set>
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
		  	 <c:set scope="page" var="viewIdkey" value="notnull"  ></c:set>
		</c:when>
	</c:choose>
</s:iterator>  
<c:choose>
	<c:when test="${empty viewIdkey}">
		<c:set scope="page" var="viewIdkey" value="keynull"  ></c:set>
	</c:when>
</c:choose>

<!-- 删除的参数 -->
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
<s:iterator value="#request.listPage.gridButton"   >
  <c:choose>
	<c:when test="${buttonName eq 'deleteBatch'}">
		<c:set scope="page" var="deleteBatch" value="deleteBatch"  ></c:set>
	</c:when>
 </c:choose>
</s:iterator>  
<c:choose>
	<c:when test="${empty deleteBatch}">
		<c:set scope="page" var="deleteBatch" value="keynull"  ></c:set>
	</c:when>
</c:choose>

<!-- 添加按钮 -->
 <s:iterator value="#request.listPage.gridButton" >
   <c:choose>
		<c:when test="${buttonName eq 'add'}">
			<c:set scope="page" var="gridButton_add" value="add"  ></c:set>
		</c:when>
	</c:choose>
</s:iterator>  

<div style="display: none"  >
    <!-- 初始化一次窗口的flag -->
	<input type="hidden" id="editPage_${listPageRander}" value="0"  >
	<input type="hidden" id="viewPage_${listPageRander}" value="0"  >
	<input type="hidden" id="queryPage_${listPageRander}" value="0"  >
	<jsp:include page="editPageWindow.jsp"></jsp:include>
	<jsp:include page="viewPageWindow.jsp"></jsp:include>
	<c:choose>
		<c:when test="${!empty listPage.queryZonePanel}">
			<c:choose>
				<c:when test="${listPage.queryZonePanel.showQuery eq '1'}">
					<!-- 显示查询 -->
					<jsp:include page="queryForPanel.jsp"></jsp:include>
				</c:when>
				<c:when test="${listPage.queryZonePanel.showQuery eq '0'}">
					<!-- 弹出式查询 -->
					<jsp:include page="queryPageWindow.jsp"></jsp:include>
				</c:when>
			</c:choose>
		</c:when>
	</c:choose>
</div>

<script>
$(function(){
	  /**
       * 定义编辑页保存业务数据 的参数
       */

		var gridID='fd_formlist_table_${listPageRander}';
	    var editWindowID='editPageWindow_${listPage.editPage.id}';
	    var viewWindowID='viewPageWindow_${listPageRander}';
	    var viewDataDivID='viewPageData_${listPage.id}';
	
		<c:choose>
			<c:when test="${empty listPage.editPage.id}">
				var editFormID='-1';//编辑页 提交的formID
			</c:when>
			<c:otherwise>
				var editFormID='${listPage.editPage.id}';//编辑页 提交的formID
			</c:otherwise>
	    </c:choose>
		
	var queryFormID='queryPage_${listPage.id}';//组合查询表单提交的ID;
 	var gridUrl="${listPage.gridUrl}";
 		gridUrl=encodeURI(gridUrl);
 		
 	if (gridUrl.indexOf("?") != -1) {
		gridUrl = gridUrl + "&preview=extjs";
	} else {
		gridUrl = gridUrl + "?preview=extjs";
	}
	var editPageDataDiv='editColumn_${listPage.id}';
		
	/**
	 * Ext panel初始化
	 */
	 
	 //查询初始化
	   <c:choose>
			<c:when test="${!empty listPage.queryZonePanel}">
				<c:choose>
					<c:when test="${listPage.queryZonePanel.showQuery eq '1'}">
					   var panel=new Ext.Panel({
					      //applyTo:'cccc',
					    	margins: '5 5 0 0',
					      //html:'biao dan',
					    	frame: true,
					    	autoHeight:true,
					        contentEl:'queryarea_${listTabPageID}',
					    	region: 'north'
					    });
					</c:when>
				</c:choose>
			</c:when>
		</c:choose>
	 

		var sm = new Ext.grid.CheckboxSelectionModel();
       	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : gridUrl
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [   
								<s:iterator value="#request.listPage.fields" status="status"   >
									<s:if test="#status.first"></s:if>
								    <s:else>,</s:else>    
									{name: '${dataColumn}'}
								</s:iterator>
							
							]
						 }),
				remoteSort : true
			});
			
			store.baseParams = {
				start : 0,
				limit : 12
				
			}
		  //store.setDefaultSort('stopId', 'desc');
	        store.load({
						params : {
						// thisNodeId:'0',初始化加载的参数
						// 	start : 0,
						//	 limit : 3
						}
			});
	    
   var gridPanel= new Ext.grid.GridPanel({
     	 id: gridID,
         border: false,
         loadMask: true,
       	 region: 'south',
         margins: '5 5 5 0',
         cmargins:'5 5 5 0',//面板伸缩之后的位置
         region: 'center',
         style: 'border: 1px solid #8db2e3;border: 1px solid #e4e4e4;',
         store: store,
         columnLines: true,
         sm: sm,
         cm: new Ext.grid.ColumnModel([
              new Ext.grid.RowNumberer()
              <c:choose>
					<c:when test="${deleteBatch eq 'deleteBatch' }">
						  ,sm
					</c:when>
			  </c:choose>
            //{header: "地图服务名", width: 70, sortable: true, renderer: Ext.util.Format.usMoney, dataIndex: 'mapname'},
			<s:iterator value="#request.listPage.fields" status="status"   >
			  ,{
				      header: "${name}", 
				      sortable: true,  
				      dataIndex: '${dataColumn}',
				      align:'${align}',
				      width: '${width}',
				     
				      <c:choose>
					     <c:when test="${visible eq 'false'}">
					      	hidden: true
					     </c:when>
				     	<c:otherwise>
				     	    hidden: false
				     	</c:otherwise>
				     </c:choose>
				   //   
				}
            </s:iterator>
					<c:choose>
						<c:when test="${listPage.isShowOperator eq 'true'}">
							,{header: "<center>操作</center>", width: 80,align:'center', sortable: true, dataIndex: 'id',	
							 renderer: function(value, cellmeta, record, rowIndex,
										columnIndex, store) {
										var rowData = record.data;
									    var dataID=rowData.${pageScope.idkey};
									var opertorString='';
									// 判断是否使用编辑

									<c:choose>
										<c:when test="${idkey =='keynull'}"></c:when>
										<c:otherwise>
										   var loadEditUrl='&listFormID=${listPage.id}';
											<%
												EditPage editPage=((ListPage)request.getAttribute("listPage")).getEditPage();
												if(editPage!=null){
													if(editPage.getEditPageParams()!=null){
													Param param=null;
														for(int i=0;i<editPage.getEditPageParams().size();i++){
															param=(Param)editPage.getEditPageParams().get(i);
															if("".equals(param.getKey())||(param.getKey()==null)){
											    			}else{
												    		//	out.println(" loadEditUrl=loadEditUrl+'&"+param.getKey()+"='+rowData."+param.getKey()+";");
															}
														}
													}
												}
											%>
											
											<s:iterator value="#request.listPage.fields" status="status"   >
												     <c:choose>
													     <c:when test="${isParmerKey eq 'true'}">
													      	loadEditUrl=loadEditUrl+'&${dataColumn}='+rowData.${dataColumn};	
													     </c:when>
												     </c:choose>
								            </s:iterator>	
											
											opertorString='<img  style="cursor:hand;" title="编辑" sytle="cursor:pointer;"  width="12px" src="<%= request.getContextPath()%>/images/ioc-editor.gif" onclick=loadEditPage("'+editFormID+'","'+loadEditUrl+'","1","${idkey}","'+editPageDataDiv+'","${listPage.editPage.isUseTab}","'+editWindowID+'","&nbsp;${editPage.title}","editPage_${listPageRander}") />&nbsp;&nbsp;&nbsp;&nbsp;';
										</c:otherwise>
									</c:choose>
									//判断是否使用查看
									<c:choose>
										<c:when test="${viewIdkey =='keynull'}"></c:when>
										<c:otherwise>
										   var loadViewUrl='formId=${listPage.viewPage.id}';
										   <%
												ViewPage viewPage=((ListPage)request.getAttribute("listPage")).getViewPage();
												if(viewPage!=null){
													if(viewPage.getViewPageParams()!=null){
													Param param=null;
														for(int i=0;i<viewPage.getViewPageParams().size();i++){
															param=(Param)viewPage.getViewPageParams().get(i);
															if("".equals(param.getKey())||(param.getKey()==null)){
											    			}else{
												    			out.println(" loadViewUrl=loadViewUrl+'&"+param.getKey()+"='+rowData."+param.getKey()+";");
															}
														}
													}
												}
											%>
											opertorString=opertorString+'<img  style="cursor:hand;" title="查看详情" sytle="cursor:pointer;"  width="12px" src="<%= request.getContextPath()%>/images/mokuai.png" onclick=loadViewPage("'+loadViewUrl+'","'+viewDataDivID+'","${viewPage.isUseTab}","'+viewWindowID+'","&nbsp;${viewPage.title}","viewPage_${listPageRander}"); />&nbsp;&nbsp;&nbsp;&nbsp;';
										</c:otherwise>
									</c:choose>
									//删除列
									<c:choose>
										<c:when test="${deleteIdkey =='keynull'}"></c:when>
										<c:otherwise>
											var deleteUrl='listFormID=${listPage.id}';
											<%
												String	deleteIDFields=pageContext.getAttribute("deleteIdkey").toString();
												String  []  deleteKeyArray=deleteIDFields.split(",");
												for(int i=0;i<deleteKeyArray.length;i++){
												if("".equals(deleteKeyArray[i])||(deleteKeyArray[i]==null)){
											    
												    }else{
															out.println(" deleteUrl=deleteUrl+'&"+deleteKeyArray[i]+"='+rowData."+deleteKeyArray[i]+";");
													 }
												}
											%>
										    opertorString=opertorString+'<img  style="cursor:hand;" title="删除" sytle="cursor:pointer;"  width="12px" src="<%= request.getContextPath()%>/images/ioc-delete.gif"  onclick="deleteData(\''+deleteUrl+'\',\''+gridID+'\');" />&nbsp;&nbsp;&nbsp;&nbsp;';
										</c:otherwise>
									</c:choose>
								
								   return opertorString;
								}
							}
					 </c:when>
			    </c:choose>
     		 ]),
			 
			 viewConfig: {
                 forceFit:true
             },
             //autoExpandColumn:'company',
			 tbar:[
			 		   {
                            text:'<img src="<%=request.getContextPath()%>/common/images/ioc-addrender.gif" style="margin: -3"  />&nbsp;&nbsp;添加',
                            tooltip:'添加',
                            iconCls:'add',
                            listeners : {'click' : function (){
                            		loadEditPage(""+editFormID,"","0","${idkey}",""+editPageDataDiv,"${listPage.editPage.isUseTab}",""+editWindowID,"&nbsp;${editPage.title}","editPage_${listPageRander}");
                            }}
                        },'-',
                        <s:iterator value="#request.listPage.gridButton"   >
						   <c:choose>
								<c:when test="${buttonName eq 'deleteBatch'}">
									    {
			                                 text:'<img src="<%=request.getContextPath()%>/common/images/ioc-delete.gif" style="margin: -3" />&nbsp;&nbsp;批量删除',
			                              iconCls: 'remove',
			                            listeners: {'click' : function(){
			                            	    bulkDelete(gridID,'${deleteIdkey}','${listPage.id}','queryPage_${listPage.id}');
			                            }}
			                        },'-',
								</c:when>
							</c:choose>
						</s:iterator>  
                        {
                           xtype : "tbfill",
                                 text:'',
                              iconCls: 'remove'
                        },'-',{
                          
                                 text:'<img src="<%=request.getContextPath()%>/common/images/import.png" style="margin: -3"/>&nbsp;&nbsp;导入',
                              iconCls: 'remove',
                            listeners: {'click' : function (){
                            	$.messager.alert('提示','数据导入','info');
                            }}
                        },'-',{
                                 text:'<img src="<%=request.getContextPath()%>/common/images/export.png" style="margin: -3" />&nbsp;&nbsp;导出',
                              iconCls: 'remove',
                            listeners: {'click' : function (){
                            	$.messager.alert('提示','页面导出excel','info');
                            }}
                        }
                 ],
              bbar : new Ext.PagingToolbar({
				pageSize : 12,
				store : store,
				displayInfo : true,
			  //displayMsg : '当前显示{0}条 到 {1}条记录， 共{2}条记录',
				emptyMsg : "当前没有记录"
			  })
        });
	    
	    var gridWindow = new Ext.Window({
              //id: 'grid-win',
                title:'&nbsp;${listPage.title}',
              //layout:"fit",
              //layout: 'form',
                 <c:choose>
					<c:when test="${!empty listPage.queryZonePanel}">
						<c:choose>
							<c:when test="${listPage.queryZonePanel.showQuery eq '1'}">
							     layout: 'border',
							</c:when>
							<c:otherwise>
								layout:"fit",
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						layout:"fit",
					</c:otherwise>
				</c:choose>
              //border :true,
                maximized :true,
                iconCls: 'icon-grid',
                shim:false,
                closable:false,
                animCollapse:false,
                constrainHeader:true,
                <c:choose>
					<c:when test="${!empty listPage.queryZonePanel}">
						<c:choose>
							<c:when test="${listPage.queryZonePanel.showQuery eq '1'}">
							      items:[panel,gridPanel]
							</c:when>
							<c:otherwise>
						   		items:[gridPanel]
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
					   items:[gridPanel]
					</c:otherwise>
				</c:choose>
              });
	         gridWindow.show();
	});
</script>
<input type="hidden"  id="init_${listPage.id}" value="0"  />
<!-- 0 未初始化 1已初始化 -->	