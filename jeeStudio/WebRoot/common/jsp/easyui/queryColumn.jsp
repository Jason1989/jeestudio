<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
 <%@ page trimDirectiveWhitespaces="true" %> 
<!-- 显示控件 -->
<c:choose>
		<c:when test="${type eq '2'}">
			{
				type: 'combobox',
				name: '${name}',
				label:'${text}',
				editable:true,
				options:{
					panelHeight:'auto',
					data:$.parseJSON('${dicDataJson}')
				}
			}
		</c:when>
		<c:when test="${type eq '4'}">
			<c:choose>
				<c:when test="${operateType eq '6'}">
					{
						type: 'datebox',
						name: '${name}_st',
						label:'${text}',
						options:{
							formatter:function(date){
								var month = date.getMonth()+1;
								 var mon = '';
					 			if(month!='10'&&month!='11'&&month!='12'){
									mon = '0'+month;
								}else{
									mon = month;
								}
					
								var day = date.getDate();
								var days = '';
								if(day=="1"||day=="2"||day=="3"||day=="4"||day=="5"||day=="6"||day=="7"||day=="8"||day=="9"){
									days = '0'+day;
								}else{
									days = day+'';
								}
						<c:choose>
							<c:when test="${dateformat==1 }">
								 return date.getFullYear(); 
		       				</c:when>
							<c:when test="${dateformat==2 }">
								 return date.getFullYear()+'-'+mon; 
		       				</c:when>
							<c:when test="${dateformat==3}">
								return date.getFullYear()+'-'+mon+'-'+days; 
		       				</c:when>
							<c:otherwise>
								return date.getFullYear()+'-'+mon+'-'+days; 
		       				</c:otherwise>
						</c:choose>
							}
						}
					},
					{
						type: 'datebox',
						name: '${name}_end',
						label:'至&nbsp;&nbsp;',
						options:{
							formatter:function(date){
								var month = date.getMonth()+1;
								 var mon = '';
					 			if(month!='10'&&month!='11'&&month!='12'){
									mon = '0'+month;
								}else{
									mon = month;
								}
					
								var day = date.getDate();
								var days = '';
								if(day=="1"||day=="2"||day=="3"||day=="4"||day=="5"||day=="6"||day=="7"||day=="8"||day=="9"){
									days = '0'+day;
								}else{
									days = day+'';
								}
						<c:choose>
							<c:when test="${dateformat==1 }">
								 return date.getFullYear(); 
		       				</c:when>
							<c:when test="${dateformat==2 }">
								 return date.getFullYear()+'-'+mon; 
		       				</c:when>
							<c:when test="${dateformat==3}">
								return date.getFullYear()+'-'+mon+'-'+days; 
		       				</c:when>
							<c:otherwise>
								return date.getFullYear()+'-'+mon+'-'+days; 
		       				</c:otherwise>
						</c:choose>
							}
						}
					}
				</c:when>
				<c:when test="${operateType eq '7'}">
					{
						type: 'datebox',
						name: '${name}_st',
						label:'${text}',
						options:{
							formatter:function(date){
								var month = date.getMonth()+1;
								 var mon = '';
					 			if(month!='10'&&month!='11'&&month!='12'){
									mon = '0'+month;
								}else{
									mon = month;
								}
					
								var day = date.getDate();
								var days = '';
								if(day=="1"||day=="2"||day=="3"||day=="4"||day=="5"||day=="6"||day=="7"||day=="8"||day=="9"){
									days = '0'+day;
								}else{
									days = day+'';
								}
						<c:choose>
							<c:when test="${dateformat==1 }">
								 return date.getFullYear(); 
		       				</c:when>
							<c:when test="${dateformat==2 }">
								 return date.getFullYear()+'-'+mon; 
		       				</c:when>
							<c:when test="${dateformat==3}">
								return date.getFullYear()+'-'+mon+'-'+days; 
		       				</c:when>
							<c:otherwise>
								return date.getFullYear()+'-'+mon+'-'+days; 
		       				</c:otherwise>
						</c:choose>
							}
						}
					},
					{
						type: 'datebox',
						name: '${name}_end',
						label:'至&nbsp;&nbsp;',
						options:{
							formatter:function(date){
								var month = date.getMonth()+1;
								 var mon = '';
					 			if(month!='10'&&month!='11'&&month!='12'){
									mon = '0'+month;
								}else{
									mon = month;
								}
					
								var day = date.getDate();
								var days = '';
								if(day=="1"||day=="2"||day=="3"||day=="4"||day=="5"||day=="6"||day=="7"||day=="8"||day=="9"){
									days = '0'+day;
								}else{
									days = day+'';
								}
						<c:choose>
							<c:when test="${dateformat==1 }">
								 return date.getFullYear(); 
		       				</c:when>
							<c:when test="${dateformat==2 }">
								 return date.getFullYear()+'-'+mon; 
		       				</c:when>
							<c:when test="${dateformat==3}">
								return date.getFullYear()+'-'+mon+'-'+days; 
		       				</c:when>
							<c:otherwise>
								return date.getFullYear()+'-'+mon+'-'+days; 
		       				</c:otherwise>
						</c:choose>
							}
						}
					}
				</c:when>
				<c:otherwise>
					{
						type: 'datebox',
						name: '${name}',
						label:'${text}',
						options:{
							formatter:function(date){
						<c:choose>
							<c:when test="${dateformat==1 }">
								 return date.getFullYear(); 
		       				</c:when>
							<c:when test="${dateformat==2 }">
								 return date.getFullYear()+'-'+(date.getMonth()+1); 
		       				</c:when>
							<c:when test="${dateformat==3}">
								return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate(); 
		       				</c:when>
							<c:otherwise>
								return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate(); 
		       				</c:otherwise>
						</c:choose>
							}
						}
					}
				</c:otherwise>
		    </c:choose>
		</c:when>
		<c:when test="${type eq '6'}">
			{
				type: 'combotree',
				name: '${name}',
				label:'${text}',
				options:{
					data:$.parseJSON('${dicDataJson}')
				}
			}
		</c:when>
		<c:otherwise>
		  <c:choose>
				<c:when test="${operateType eq '6'}">
					{
						type:'text',
						name: '${name}_st',
						label:'${text}'
					},
					{
						type:'text',
						name: '${name}_end',
						label:'至&nbsp;&nbsp;'
					}
				</c:when>
				<c:otherwise>
					{
						type:'text',
						name: '${name}',
						label:'${text}'
					}
				</c:otherwise>
		  </c:choose>
		</c:otherwise>
</c:choose>