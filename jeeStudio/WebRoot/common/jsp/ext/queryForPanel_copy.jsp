<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>

<div id="queryarea" style="font-size:10;background-color: #F5F5F5;">

   <input type="hidden" name="sql" value="${listPage.sql}" />
   <input type="hidden" name="formID" value="${listPage.id}" /><!-- listPage id -->
   
   <input type="hidden" name="rows" value="9" />
   <input type="hidden" name="page" value="1" />
	
	<%
		request.setAttribute("queryColumnID",RandomGUID.geneGuid());//生成表单列ID
	%>
	<table>
		<tr>
			<s:iterator value="#request.listPage.queryZone"  id="queryZone" >
			    <s:iterator value="#queryZone.queryColumns"  id="queryZone"  status="status" >
			 		<td>&nbsp;&nbsp;&nbsp;</td>	
			 		<td style="font-size:12px;"> 
					     <label >${text}： </label> 
					</td>
			 		<td style="font-size:12px;" >
						 	 <c:choose>
						    	<c:when test="${type==1}"><!-- 文本框 -->
							  		<c:choose>
									   <c:when test="${operateType eq '6'}">
									   		<input id="queryColumn_${queryColumnID}_${status.index}" type="text" name="${name}_st"  class="easyui-validatebox" value="${data}" />
									   	到：	<input id="queryColumn_${queryColumnID}_${status.index}" type="text" name="${name}_end"  class="easyui-validatebox" value="${data}" />
									   </c:when>
									   <c:otherwise>
											<input id="queryColumn_${queryColumnID}_${status.index}" type="text" name="${name}"  class="easyui-validatebox" value="${data}" />
									   </c:otherwise>
								    </c:choose>
							    </c:when>
								<c:when test="${type==0}"><!-- 复选框 -->
									<input id="check_" type="checkbox" name="${name}" class="easyui-validatebox" />	
								</c:when>
								<c:when test="${type==2}"><!-- 下拉选 -->
									<select  name="${name}" >
										<option value="0"  selected="selected" >请选择</option>
										<c:forEach items="${dictionaryData}" var="dictionaryDataMap" > 
											<c:choose>
												<c:when test="${dictionaryDataMap.key ==data}">
													<option value="${dictionaryDataMap.key}" selected="selected" >${dictionaryDataMap.value}</option>
												</c:when>
												<c:otherwise>
													<option value="${dictionaryDataMap.key}" >${dictionaryDataMap.value}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach> 
									</select>
								</c:when>
								<c:when test="${type==3}"><!-- 文本域 -->
									<textarea rows="" cols=""  name="${name}" >${data}</textarea>
								</c:when>
								<c:when test="${type==4}"><!-- 日历控件 -->
								    <input value="${data}" id="datebox_${stauts.index}"  type="text" name="${name}" class="easyui-datebox" />
									  <script>
										   $(function(){
										       $('#datebox_${stauts.index}').datebox({});
										   });
									  </script>
									  &nbsp;&nbsp;<img title="清除" src="<%= request.getContextPath()%>/images/ioc-delete2.gif" onclick="clearText('datebox_${stauts.index}_${name}','null');" style="cursor:hand" />
									
								</c:when>
							   <c:otherwise>
							   <c:choose>
								   <c:when test="${operateType eq '6'}">
								   <label >(从)</label>  
								   <input id="queryColumn_${queryColumnID}_${status.index}" type="text" name="${name}_st"  class="easyui-validatebox" value="${data}" />
								   	<% out.write("</td><td style='font-size:12px;' >"); %>
								   <label >(到)</label>  
								  	<input id="queryColumn_${queryColumnID}_${status.index}" type="text" name="${name}_end"  class="easyui-validatebox" value="${data}" />
								   </c:when>
								   <c:otherwise>
										<input type="text" name="${name}"  class="easyui-validatebox"  />
									</c:otherwise>
							    </c:choose>
							   </c:otherwise>
							</c:choose>
						</td>
					<td>&nbsp;</td>	
			 	</s:iterator>
	 		</s:iterator>
	 		<td>
	 			<a  class="easyui-linkbutton" icon="icon-search"  href="javascript:comQueryForPanel('queryPage_${listPage.id}','fd_formlist_table_${listPageRander}');"  >查询</a>
			</td>
		</tr>
	</table>
</div>