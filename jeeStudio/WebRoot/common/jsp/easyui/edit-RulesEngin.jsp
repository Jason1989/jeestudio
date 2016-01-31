<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page language="java" contentType="text/html; charset=UTF-8" %>	
<%@page import="com.zxt.compplatform.formengine.entity.view.EditColumn"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.EditRulesEngin"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
<c:choose>
<c:when test="${type==3}"><!--文本域-->
<script>
	if(document.readyState=="complete"){
		    
	<%
		try{
			  EditColumn editColumn=(EditColumn) request.getAttribute("editColumnList");
			  if(editColumn!=null){
			  	EditRulesEngin rulesEngin = editColumn.getEditRulesEngin();
			  	String types[] = rulesEngin.getEventTypes();
			  	if(types!=null && types.length!=0){
			  		for(int i = 0; i<types.length;i++){
			  			if("onclick".equals(types[i])){
			  			%>
			  				$('#editColumn_${stauts.index}${columnID}${editPage.id}').click(function(){eval(decodeURIComponent('<%=rulesEngin.getRulesService()%>'))});
			  			<%
			  			}else if("onchange".equals(types[i])){
			  			%>
			  				$('#editColumn_${stauts.index}${columnID}${editPage.id}').change(function(){eval(decodeURIComponent('<%=rulesEngin.getRulesService()%>'))});
			  			<%
			  			}else if("onblur".equals(types[i])){
			  			%>
			  				$('#editColumn_${stauts.index}${columnID}${editPage.id}').blur(function(){eval(decodeURIComponent('<%=rulesEngin.getRulesService()%>'))});
			  			<%
			  			}else if("focus".equals(types[i])){
			  			%>
			  				$('#editColumn_${stauts.index}${columnID}${editPage.id}').focus(function(){eval(decodeURIComponent('<%=rulesEngin.getRulesService()%>'))});
			  			<%
			  			}else if("onkeyup".equals(types[i])){
			  			%>
			  				$('#editColumn_${stauts.index}${columnID}${editPage.id}').keyup(function(event){eval(decodeURIComponent('<%=rulesEngin.getRulesService()%>'))});
			  			<%
			  			}
			  		}
			  	}
			  }
		}catch(Exception e){
		}
	
	
	%>
	}
</script>
</c:when>

<c:when test="${type==2}"><!-- 下拉选 -->
	<!-- 事件 -->
  		<script>
		if(document.readyState=="complete"){
		<%
			EditColumn editColumn=(EditColumn) request.getAttribute("editColumnList");
			try{
				  if(editColumn!=null){
				  	EditRulesEngin rulesEngin = editColumn.getEditRulesEngin();
				  	String types[] = rulesEngin.getEventTypes();
				  	if(types!=null && types.length!=0){
				  		for(int i = 0; i<types.length;i++){
				  			if("onclick".equals(types[i])){
				  			%>
				  				$('#editColumn_${stauts.index}${columnID}${editPage.id}').combobox({
				  					onSelect:function(value){eval(decodeURIComponent('<%=rulesEngin.getRulesService()%>'))}
				  				});
				  			<%
				  			}else if("onchange".equals(types[i])){
				  			%>
				  				$('#editColumn_${stauts.index}${columnID}${editPage.id}').combobox({
				  					onChange:function(newvalue,oldvalue){eval(decodeURIComponent('<%=rulesEngin.getRulesService()%>'))}
				  				});
				  			<%
				  			}else if("onblur".equals(types[i])){
				  			%>
				  				$('#editColumn_${stauts.index}${columnID}${editPage.id}').combobox({
				  					onBlur:function(){eval(decodeURIComponent('<%=rulesEngin.getRulesService()%>'))}
				  				});
				  			<%
				  			}
				  		}
				  	}
				  }
			}catch(Exception e){
			}
		
		%>
		}
	</script>
</c:when>
<c:when test="${type=='' || type==1}"><!-- 文本框 -->
<script>
	if(document.readyState=="complete"){
		    
	<%
		try{
			  EditColumn editColumn=(EditColumn) request.getAttribute("editColumnList");
			  if(editColumn!=null){
			  	EditRulesEngin rulesEngin = editColumn.getEditRulesEngin();
			  	String types[] = rulesEngin.getEventTypes();
			  	if(types!=null && types.length!=0){
			  		for(int i = 0; i<types.length;i++){
			  			if("onclick".equals(types[i])){
			  			%>
			  				$('#editColumn_${stauts.index}${columnID}${editPage.id}').click(function(){eval(decodeURIComponent('<%=rulesEngin.getRulesService()%>'))});
			  			<%
			  			}else if("onchange".equals(types[i])){
			  			%>
			  				$('#editColumn_${stauts.index}${columnID}${editPage.id}').change(function(){eval(decodeURIComponent('<%=rulesEngin.getRulesService()%>'))});
			  			<%
			  			}else if("onblur".equals(types[i])){
			  			%>
			  				$('#editColumn_${stauts.index}${columnID}${editPage.id}').blur(function(){eval(decodeURIComponent('<%=rulesEngin.getRulesService()%>'))});
			  			<%
			  			}else if("focus".equals(types[i])){
			  			%>
			  				$('#editColumn_${stauts.index}${columnID}${editPage.id}').focus(function(){eval(decodeURIComponent('<%=rulesEngin.getRulesService()%>'))});
			  			<%
			  			}else if("onkeyup".equals(types[i])){
			  			%>
			  				$('#editColumn_${stauts.index}${columnID}${editPage.id}').keyup(function(event){eval(decodeURIComponent('<%=rulesEngin.getRulesService()%>'))});
			  			<%
			  			}
			  		}
			  	}
			  }
		}catch(Exception e){
		}
	
	
	%>
	}
</script>
</c:when>
</c:choose>