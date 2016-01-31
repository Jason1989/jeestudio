<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>

<form  method="post" id="queryPage_${listPage.id}" action="${gridUrl}" >
   <input type="hidden" name="sql" value="${listPage.sql}" />
   <input type="hidden" name="formID" value="${listPage.id}" /><!-- listPage id -->
   
   <input type="hidden" name="rows" value="9" />
   <input type="hidden" name="page" value="1" />
	
	<%
		int i=0;
		request.setAttribute("queryColumnID",RandomGUID.geneGuid());//生成表单列ID
	
	%>
<table align="left" >
<tr>
<s:iterator value="#request.listPage.queryZone"  id="queryZone" >
    <s:iterator value="#queryZone.queryColumns"  id="queryZone"  status="status" >
 		<td style="font-size:12px;width:40px;" align="right"> 
					    	<label style="width:100px" >${text}：</label> 
					  	</td>
				 <!-- *****************************编辑列字段********************************** -->
						<td>
						 	 <c:choose>
						    	<c:when test="${type==1}"><!-- 文本框 -->
						  			<input id="queryColumn_${queryColumnID}_${status.index}" type="text" name="${name}"  class="easyui-validatebox" value="${data}" />
							   
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
							   <c:otherwise><input type="text" name="${name}"  class="easyui-validatebox"  /></c:otherwise>
							</c:choose>
						</td>
							<%
								i++;
								if(i==2){
									out.write("</tr><tr>");//2列 换行
									i=0;
								}
							%>
		
		 </s:iterator>
	
   </s:iterator>
   
	</tr>
	</table>
</form>