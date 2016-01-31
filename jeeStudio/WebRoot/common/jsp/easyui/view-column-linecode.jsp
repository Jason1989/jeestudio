<%@page language="java" contentType="text/html; charset=UTF-8" %>	
<%@page import="com.zxt.compplatform.formengine.entity.view.ViewColumn"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.TextColumn"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	request.setAttribute("lineCount",(Integer.parseInt(request.getAttribute("lineCount").toString())+1)+"");
	
		try{
			  ViewColumn viewColumn=(ViewColumn) request.getAttribute("editColumnList");
		  	  TextColumn textColumn=null;
			  if(viewColumn!=null){
			  	 textColumn= viewColumn.getTextColumn();
			  	 if(textColumn.getExclusiveLine()!=null){
			  	    if(textColumn.getExclusiveLine().booleanValue()){
			  	 		request.setAttribute("lineCount","2");
			  		 }
			  	 }
			  }
		}catch(Exception e){
		}
	
		if("2".equals(request.getAttribute("lineCount").toString())){
			out.write("</tr><tr>");//2列 换行
			request.setAttribute("lineCount","0");
		}
%>