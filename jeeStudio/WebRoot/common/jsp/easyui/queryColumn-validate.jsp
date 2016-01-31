<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
<c:choose>
	
	<c:when test="${editMode.validateRule.name eq 'email' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"email"
			    };
				$('#queryColumn_${queryColumnID}_${status.index}').validatebox(options)
			});
		</script>
	</c:when>
	
	<c:when test="${editMode.validateRule.name eq 'telphone' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"telphone"
			    };
				$('#queryColumn_${queryColumnID}_${status.index}').validatebox(options)
			});
		</script>
	</c:when>
	<c:when test="${editMode.validateRule.name eq 'url' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"url"
			    };
				$('#queryColumn_${queryColumnID}_${status.index}').validatebox(options)
			});
		</script>
	</c:when>
</c:choose>
