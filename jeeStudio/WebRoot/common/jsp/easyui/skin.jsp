<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style type="text/css">
   .skin_list{
	   	text-align: right;
	   	margin-top:5px;
   }
   .skin_theme_title{
	   font-size:18px;
	   font-weight: bolder;
	   font-family: 黑体;
	   float: left;
	   padding:15px;
   } 
   .radio{
	height:21px;
	line-height:21px;
	vertical-align:middle;
	border:1px solid white;
}
</style>
<div class="easyui-layout" fit="true">
  	    <div region="center" border="true" style="overflow: auto;" >
  	    		<div style="float: left;">
	  	    		<img src="<%=basePath%>images/blue.jpg" style="width:180px;height:120px;border:2px solid gray;margin:5px;">
	  	    		<div style="display: block;" align="center"><input type="radio" class="radio" name="skin" value="blue">蓝色主题</div>
  	    		</div>
  	    		<div  style="float: left;">
	  	    		<img src="<%=basePath%>images/green.jpg" style="width:180px;height:120px;border:2px solid gray;margin:5px;">
	  	    		<div style="display: block;" align="center"><input type="radio" class="radio"  name="skin" value="green">灰色主题</div>
  	    		</div>
  	    		<div  style="float: left;">
	  	    		<img src="<%=basePath%>images/desktop.jpg" style="width:180px;height:120px;border:2px solid gray;margin:5px;">
	  	    		<div style="display: block;" align="center"><input type="radio" class="radio"  name="skin" value="desktop">Desktop主题</div>
  	    		</div>
  	    		<div  style="float: left;">
	  	    		<img src="<%=basePath%>images/green2.jpg" style="width:180px;height:120px;border:2px solid gray;margin:5px;">
	  	    		<div style="display: block;" align="center"><input type="radio" class="radio"  name="skin" value="dgreen">绿色主题</div>
  	    		</div>
  	    		<div  style="float: left;">
	  	    		<img src="<%=basePath%>images/deepblue.jpg" style="width:180px;height:120px;border:2px solid gray;margin:5px;">
	  	    		<div style="display: block;" align="center"><input type="radio" class="radio"  name="skin" value="deepblue">深蓝主题</div>
  	    		</div>
  	    		<div  style="float: left;">
	  	    		<img src="<%=basePath%>images/deepblue.jpg" style="width:180px;height:120px;border:2px solid gray;margin:5px;">
	  	    		<div style="display: block;" align="center"><input type="radio" class="radio"  name="skin" value="yingji">应急主题</div>
  	    		</div>
  	    		</br>
  	    		
  	    		
  	    </div>
  	    <div region="south" style="height: 38px;padding:5px;" align="right" border="true">
			<a href="javascript:void(0)" class="easyui-linkbutton change_skin_button" iconCls="icon-photo" >应用</a>
			<a href="javascript:void(0)" onclick="$('#zxtplat_change_skin').window('close');return;" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
		</div>
  	</div>