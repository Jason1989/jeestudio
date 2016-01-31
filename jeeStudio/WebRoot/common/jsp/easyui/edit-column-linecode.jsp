<%@page language="java" contentType="text/html; charset=UTF-8" %>	
<%@page import="com.zxt.compplatform.formengine.entity.view.EditColumn"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.TextColumn"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	request.setAttribute("lineCount",(Integer.parseInt(request.getAttribute("lineCount").toString())+1)+"");
	
		try{
			  EditColumn editColumn=(EditColumn) request.getAttribute("editColumnList");
		  	  TextColumn textColumn=null;
			  if(editColumn!=null){
			  	 textColumn= editColumn.getTextColumn();
			  	 if(textColumn.getExclusiveLine()!=null){
			  	    if(textColumn.getExclusiveLine().booleanValue()){
			  	 		request.setAttribute("lineCount","2");
			  		 }
			  	 }
			  }
		}catch(Exception e){
		}
	
		if("2".equals(request.getAttribute("lineCount").toString())){
			out.write("</tr>\r\n <tr>");//2列 换行
			request.setAttribute("lineCount","0");
		}
%>