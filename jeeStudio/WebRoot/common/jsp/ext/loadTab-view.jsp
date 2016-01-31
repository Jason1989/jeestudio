<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<div id="loadTab-view-div" class="easyui-tabs" style="width:675px;height:405px;">
		<div   title="${viewPage.title}">
		 	<jsp:include page="tab-viewpage.jsp"></jsp:include>
		</div>
		<s:iterator  value="#request.viewPage.tabs" status="stauts" >
			<div  title="${title}" style="padding:1px;">
				  <c:choose>
				     <c:when test="${type eq 'editPage'}">
				     	<c:set scope="request" var="editPage" value="${page}"  ></c:set>
				    	<jsp:include page="tab-editpage.jsp"></jsp:include>
				    </c:when>
				     <c:when test="${type eq 'listPage'}">
				     	<c:set scope="request" var="listPage" value="${page}"  ></c:set>
			 			<jsp:include page="list-page.jsp"></jsp:include>
					</c:when>
				     <c:when test="${type eq 'viewPage'}">
				     
				        <c:set scope="request" var="viewPage" value="${page}"  ></c:set>
				      	<jsp:include page="tab-viewpage.jsp"></jsp:include>
				     </c:when>
				  </c:choose>
			</div>
		</s:iterator>
</div>
	<script>
		$(function(){
			$('#loadTab-view-div').tabs({
				border:true
			
			});	
		});
	</script>
	