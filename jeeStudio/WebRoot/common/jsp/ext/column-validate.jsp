<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<c:choose>
	
	<c:when test="${editMode.validateRule.name eq '' }">
		<script>
			$(function(){ 
			    var options={
			    		<c:choose>
							<c:when test="${editMode.required}">
								required:true
							</c:when>
						</c:choose>
			    	
			    };
				$('#editColumn_${stauts.index}${columnID}${editPage.id}').validatebox(options)
			});
		</script>
	</c:when>
	
	<c:when test="${editMode.validateRule.name eq 'email' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"email"
		    		<c:choose>
						<c:when test="${editMode.required}">
							,required:true
						</c:when>
					</c:choose>
			    };
				$('#editColumn_${stauts.index}${columnID}${editPage.id}').validatebox(options)
			});
		</script>
	</c:when>
	<c:when test="${editMode.validateRule.name eq 'mobile' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"mobile"
			    	<c:choose>
						<c:when test="${editMode.required}">
							,required:true
						</c:when>
					</c:choose>
			    };
				$('#editColumn_${stauts.index}${columnID}${editPage.id}').validatebox(options)
			});
		</script>
	</c:when>
	<c:when test="${editMode.validateRule.name eq 'phone' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"phone"
			    	<c:choose>
						<c:when test="${editMode.required}">
							,required:true
						</c:when>
					</c:choose>
			    };
				$('#editColumn_${stauts.index}${columnID}${editPage.id}').validatebox(options)
			});
		</script>
	</c:when>
	<c:when test="${editMode.validateRule.name eq 'url' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"url"
			    	<c:choose>
						<c:when test="${editMode.required}">
							,required:true
						</c:when>
					</c:choose>
			    };
				$('#editColumn_${stauts.index}${columnID}${editPage.id}').validatebox(options)
			});
		</script>
	</c:when>
	<c:when test="${editMode.validateRule.name eq 'positive_Integer' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"positive_Integer"
			    	<c:choose>
						<c:when test="${editMode.required}">
							,required:true
						</c:when>
					</c:choose>
			    };
				$('#editColumn_${stauts.index}${columnID}${editPage.id}').validatebox(options)
			});
		</script>
	</c:when>
	<c:when test="${editMode.validateRule.name eq 'age' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"age"
			    	<c:choose>
						<c:when test="${editMode.required}">
							,required:true
						</c:when>
					</c:choose>
			    };
				$('#editColumn_${stauts.index}${columnID}${editPage.id}').validatebox(options)
			});
		</script>
	</c:when>
	<c:when test="${editMode.validateRule.name eq 'idcard' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"idcard"
			    	<c:choose>
						<c:when test="${editMode.required}">
							,required:true
						</c:when>
					</c:choose>
			    };
				$('#editColumn_${stauts.index}${columnID}${editPage.id}').validatebox(options)
			});
		</script>
	</c:when>
</c:choose>