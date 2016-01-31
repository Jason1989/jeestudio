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
%>

<%-- 构件内容 --%>
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
											EditPage editPage=((ListPage)request.getAttribute("listPage")).getEditPage();
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
											CopyPage copyPage=((ListPage)request.getAttribute("listPage")).getCopyPage();
											EditPage editPage1=((ListPage)request.getAttribute("listPage")).getEditPage();
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
											opertorString+='<img  style="cursor:hand;" title="复制" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/images/ioc-copy.png" onclick=loadEditPage_easyui("${listPage.id}","${isAbleWorkFlow}","'+copyFormID+'","'+loadCopyUrl+'","1","${idkey}","'+copyPageDataDiv+'","false","'+copyWindowID+'","&nbsp;${copyPage.editPage.title}","copyPage_${listPageRander}") />&nbsp;&nbsp;&nbsp;&nbsp;';
										<% 
												}
										  }else{
											
										%>
											opertorString+='<img  style="cursor:hand;" title="复制" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/images/ioc-copy.png" onclick=loadEditPage_easyui("${listPage.id}","${isAbleWorkFlow}","'+copyFormID+'","'+loadCopyUrl+'","1","${idkey}","'+copyPageDataDiv+'","false","'+copyWindowID+'","&nbsp;${copyPage.editPage.title}","copyPage_${listPageRander}") />&nbsp;&nbsp;&nbsp;&nbsp;';
										<%
											}
										%>
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
														out.println(" loadViewUrl=loadViewUrl+'&"+param.getKey()+"='+rowData."+param.getKey()+";");
													}
												}
											}
										%>
										
									<%  
									  if(authority!=null){
										if(authority.get("'"+listPage.getId()+"_view'")!=null){
									%>
										opertorString=opertorString+'<img  style="cursor:hand;" title="查看详情" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/images/mokuai.png" onclick=loadViewPage("'+loadViewUrl+'","'+viewDataDivID+'","${viewPage.isUseTab}","'+viewWindowID+'","&nbsp;${viewPage.title}","viewPage_${listPageRander}"); />&nbsp;&nbsp;&nbsp;&nbsp;';
									<% 
										}
									  }else{
											
									%>
										opertorString=opertorString+'<img  style="cursor:hand;" title="查看详情" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/images/mokuai.png" onclick=loadViewPage("'+loadViewUrl+'","'+viewDataDivID+'","${viewPage.isUseTab}","'+viewWindowID+'","&nbsp;${viewPage.title}","viewPage_${listPageRander}"); />&nbsp;&nbsp;&nbsp;&nbsp;';
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
											String	deleteIDFields=pageContext.getAttribute("deleteIdkey").toString();
											String  []  deleteKeyArray=deleteIDFields.split(",");
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
									    opertorString=opertorString+'<img  style="cursor:hand;" title="删除" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/images/ioc-delete.gif"  onclick=deleteData_easyui("'+deleteUrl+'","'+gridID+'"); />&nbsp;&nbsp;&nbsp;&nbsp;';
									<% 
										}
								
									  }else{
									%>
									    opertorString=opertorString+'<img  style="cursor:hand;" title="删除" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/images/ioc-delete.gif"  onclick=deleteData_easyui("'+deleteUrl+'","'+gridID+'"); />&nbsp;&nbsp;&nbsp;&nbsp;';
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
											 	  	  opertorString += '<img style="cursor:hand;" title="${buttonname}" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/${buttonicon}" onclick=createSavebutt("${isAbleWorkFlow}","<%=Constant.WORKFLOW_ENABLE%>","${listPageRander}","${listPage.id}","'+buttonrules.tabspageurl+'","'+loadEditUrl_JS+'","0","${idkey}","'+editPageDataDiv_zdy+'","${editPage.isUseTab}","'+editWindowID_zdy+'","&nbsp;'+buttonrules.tabstitle+'","editPage_${listPageRander}_zdy") />&nbsp;&nbsp;&nbsp;&nbsp;';
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
											 	  	  opertorString += '<img style="cursor:hand;" title="${buttonname}" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/${buttonicon}" onclick=OpenWindowPage("initdiv_${listPage.id}_listpagezdy","'+buttonrules.tabstitle+'","init_${listPage.id}_listpagezdy","'+LISTPAGE_LOAD+buttonrules.tabspageurl+params+'","380") />&nbsp;&nbsp;&nbsp;&nbsp;';
											 	  }else if('viewPage' == buttonrules.tabspagetype){
											 	  	                  //'<img  style="cursor:hand;" title="查看详情" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/images/mokuai.png" onclick=loadViewPage("'+loadViewUrl+'","'+viewDataDivID+'","${viewPage.isUseTab}","'+viewWindowID+'","&nbsp;${viewPage.title}","viewPage_${listPageRander}"); />&nbsp;&nbsp;&nbsp;&nbsp;';
											 	  	  var loadViewUrl_JS='formId=${viewpage.id}';
											 	  	  <s:iterator value="viewpage.viewPageParams" >
											 	  	  		loadViewUrl_JS += '&${key}='+rowData['${key}'];
											 	  	  </s:iterator> 
											 	  	  //opertorString += '<img style="cursor:hand;" title="${buttonname}" sytle="cursor:pointer;" src="<%= request.getContextPath()%>/${buttonicon}" onclick=loadViewPage("'+buttonrules.tabspageurl+'","'+loadEditUrl_JS+'","1","${idkey}","'+editPageDataDiv+'","${editPage.isUseTab}","'+editWindowID+'","&nbsp;'+buttonrules.tabstitle+'","editPage_${listPageRander}") />&nbsp;&nbsp;&nbsp;&nbsp;';
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