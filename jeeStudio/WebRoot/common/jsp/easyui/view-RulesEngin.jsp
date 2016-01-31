<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@page language="java" contentType="text/html; charset=UTF-8" %>	
<%@page import="com.zxt.compplatform.formengine.entity.view.EditColumn"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.EditRulesEngin"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.ViewPage"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<script>
	if(document.readyState=="complete"){
	<%
		try{
		  ViewPage viewpage=(ViewPage) request.getAttribute("viewPage");
		  if(viewpage.getJsRules()!=null){
	%>
				eval(decodeURIComponent('<%=viewpage.getJsRules() %>'));
	<%
		  }
		}catch(Exception e){
		}
	%>
	}
</script>