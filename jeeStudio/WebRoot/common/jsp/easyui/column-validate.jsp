<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
<c:choose>
	
	<c:when test="${editMode.validateRule.name eq '' }">
		<c:choose>
			<c:when test="${type==2}">
				<c:choose>
					<c:when test="${editMode.required}">
						<script>
							$(function(){ 
					    		<c:choose>
									<c:when test="${editMode.required}">
										$('#editColumn_${stauts.index}${columnID}${editPage.id}').combobox({
											required:true
										});
									</c:when>
								</c:choose>
							});
						</script>
					</c:when>
				</c:choose>
			</c:when>
			<c:when test="${type==4}"></c:when>
			<c:when test="${type==9}"></c:when>
			<c:when test="${type==16}"></c:when>
			<c:otherwise>
				<script>
					$(function(){ 
					    var options={
					    		<c:choose>
									<c:when test="${editMode.required}">
										,required:true
									</c:when>
								</c:choose>
					    };
						$('#editColumn_${stauts.index}${columnID}${editPage.id}').validatebox(options)
					});
				</script>
			</c:otherwise>
		</c:choose>
	</c:when>

	<c:when test="${editMode.validateRule.name eq 'date_compare'}">
		<script>
			$(function(){ 
			    var options={
			    	validType:"compDate['editColumn_${editMode.compIndex}${columnID}${editPage.id}','${editMode.compCon}','${empty textColumn.dateformat}']"
			    };
				$('#editColumn_${stauts.index}${columnID}${editPage.id}').datebox(options)
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
	
	<c:when test="${editMode.validateRule.name eq 'maxlength' }">
		<c:choose>
			<c:when test="${!empty   editMode.maxLength2}">
				<c:choose>
					<c:when test="${type==4}"></c:when>
					<c:when test="${type==9}"></c:when>
					<c:when test="${type==16}"></c:when>
					<c:otherwise>
						<script>
							$(function(){ 
							    var options={
							    	validType:"length[${editMode.minLength},${editMode.maxLength2}]"
						    		<c:choose>
										<c:when test="${editMode.required}">
											,required:true
										</c:when>
									</c:choose>
							    };
								$('#editColumn_${stauts.index}${columnID}${editPage.id}').validatebox(options)
							});
						</script>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${type==4}"></c:when>
					<c:when test="${type==9}"></c:when>
					<c:when test="${type==16}"></c:when>
					<c:otherwise>
						<script>
							$(function(){ 
							    var options={
						    		<c:choose>
										<c:when test="${editMode.required}">
											,required:true
										</c:when>
									</c:choose>
							    };
								$('#editColumn_${stauts.index}${columnID}${editPage.id}').validatebox(options)
							});
						</script>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
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
	
	<c:when test="${editMode.validateRule.name eq 'phone_mobile' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"phone_mobile"
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
	
	<c:when test="${editMode.validateRule.name eq 'price' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"price"
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
	<c:when test="${editMode.validateRule.name eq 'number' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"number"
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
	
	<c:when test="${editMode.validateRule.name eq 'jingduxj' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"jingduxj"
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
	
	<c:when test="${editMode.validateRule.name eq 'weiduxj' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"weiduxj"
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
	
	
	<c:when test="${editMode.validateRule.name eq 'IP' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"IP"
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
	
	<c:when test="${editMode.validateRule.name eq 'zipcode' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"zipcode"
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
	
	<c:when test="${editMode.validateRule.name eq 'comboRequire' }">
		<script>
			$(function(){ 
			    var options={
			    	validType:"comboRequire",
			    	invalidMessage:'请选择有效数据'
			    };
				$('#editColumn_${stauts.index}${columnID}${editPage.id}').combobox(options)
			});
		</script>
	</c:when>
	
	<c:when test="${editMode.validateRule.name eq 'isExist' }">
		<script>
			$(function(){ 
				var editPageKeyId_name = '${editPageKeyId}';
				var editPageKeyId_value = $(':hidden[name='+editPageKeyId_name+']').val();
			    var options={
			    	validType:"remote['formengine/validate!isExist.action?id=${editPage.id}&myname=${name}&opertorType=${opertorType}&keyId="+editPageKeyId_value+"']"
			    	<c:choose>
						<c:when test="${editMode.required}">
							,required:true
						</c:when>
					</c:choose>
			    };
				$('#editColumn_${stauts.index}${columnID}${editPage.id}').validatebox(options);
			});
		</script>
	</c:when>
	
	
	<c:when test="${editMode.validateRule.name eq 'maxlength_isExist' }">
		<script>
			$(function(){ 
				var editPageKeyId_name = '${editPageKeyId}';
				var editPageKeyId_value = $(':hidden[name='+editPageKeyId_name+']').val();
			    var options={
			    	validType:"maxlength_isExist['formengine/validate!isExist.action?id=${editPage.id}&myname=${name}&opertorType=${opertorType}&keyId="+editPageKeyId_value+"',"+${editMode.maxLength2}+"]"
			    	
			    	<c:choose>
						<c:when test="${editMode.required}">
							,required:true
						</c:when>
					</c:choose>
			    };
				$('#editColumn_${stauts.index}${columnID}${editPage.id}').validatebox(options);
			});
		</script>
	</c:when>
	
	<c:otherwise></c:otherwise>
	
</c:choose>