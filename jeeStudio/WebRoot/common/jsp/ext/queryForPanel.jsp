<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<!-- style="font-size:10;background-color: #F5F5F5;"  -->
<div id="queryarea_${listTabPageID}" >
<form id="queryPage_${listPage.id}" action="${listPage.gridUrl}" method="post"   >
	<%
		request.setAttribute("queryColumnID",RandomGUID.geneGuid());//生成表单列ID
	%>
	<div class="cont_mar">
		<s:iterator value="#request.listPage.queryZone"  id="queryZone" >
			    <s:iterator value="#queryZone.queryColumns"  id="queryZone"  status="status" >
		<div class="cont_l">
        <dl>
          <dd style="font-size:12px;" >  
          		<label >&nbsp;&nbsp;&nbsp;${text}： </label> 
          </dd>
           <dt>
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
							<input type="text" name="${name}" style="width: 100%;" class="easyui-validatebox"  />
						</c:otherwise>
				    </c:choose>
				   </c:otherwise>
				</c:choose>
			</dt>
        </dl>
      </div>
  
       	</s:iterator>
	 </s:iterator>
     <div>
         &nbsp;
     	  <a id="${queryColumnID}_btu"   href="javascript:comQueryForPanel('queryPage_${listPage.id}','fd_formlist_table_${listPageRander}');"  ></a>
			<script type="text/javascript">
				$(function(){
					$('#${queryColumnID}_btu').linkbutton({
						text:'确定',
						iconCls:'icon-search'
					});
				});
			</script>
	</div>
    </div>
    </form>
</div>