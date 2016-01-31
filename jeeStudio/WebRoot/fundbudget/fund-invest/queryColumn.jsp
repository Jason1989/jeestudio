<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
  
<!-- 显示控件 -->
<c:choose>
		<c:when test="${type eq '2'}">
			{
				type: 'combobox',
				name: '${name}',
				label:'${text}',
				options:{
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
								return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
							}
						}
					},
					{
						type: 'datebox',
						name: '${name}_end',
						label:'到',
						options:{
							formatter:function(date){
								return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
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
								return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
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
						label:'到'
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