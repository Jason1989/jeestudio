<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
  <table>
	<s:iterator value="#request.list" >
		<tr>
		  	<td><a style="color: #000000" href="javascript:treeLinkActive('${text}','text_${name}_${stauts.index}','${id}','text_${name}_hidden_${stauts.index}','ajaxbox-link_${name}_${stauts.index}');" >${text}</a></td>
		</tr>
	</s:iterator>	
</table>