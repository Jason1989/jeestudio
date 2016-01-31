<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.compplatform.formengine.util.PropertiesUtil"%>
<%
	String basePathActivityTab = PropertiesUtil.findSystemPath(request);
%>
<div id="activity-tab"  class="tta">
	<s:iterator  value="#request.activityTabList" status="stauts" >
		<c:choose>
			<c:when test="${isCustom == 0 }">
				<div cache="false" title="${title}" style="padding:1px;" href="<%=basePathActivityTab%>/formengine/editPageAction.action?formId=${url}&APP_ID=${APP_ID}&PARENT_APP_ID=${APP_ID}&opertorType=1&workflowForm=true&lprid=${lprid}&isAbleWorkFlow=${isAbleWorkFlow}&listpageId=${listpageId}&atw=1&disabletab=true" ></div>
			</c:when>
			<c:otherwise>
				<div  title="${title}" style="padding:1px;" href="<%=basePathActivityTab%>/${url}APP_ID=${APP_ID}&PARENT_APP_ID=${APP_ID}&opertorType=1&workflowForm=true&lprid=${lprid}&isAbleWorkFlow=${isAbleWorkFlow}&listpageId=${listpageId}&atw=1" ></div>
			</c:otherwise>
		</c:choose>
	</s:iterator>
</div>
<script>
	var button_${gridId}_submittype = false;
	$(function(){
		$('#activity-tab').tabs({
		   //title:'&nbsp;',
			border:true,
			fit:true,
			closable:false,
			tools:[{
				 iconCls:'icon-temp',
				 plain:false,
				 text:'暂存',
				 handler: function(){
						 /**
							  if(document.getElementById('activityFuctionForTemporary')){
							  	  var fctString=document.getElementById('activityFuctionForTemporary').innerHTML;
							  	  eval(fctString);
							  }
						  **/
						  if(button_${gridId}_submittype){
							  return;
						  }else{
							  button_${gridId}_submittype = true;
						  }
						  saveWorkFlowTabForm(
						     'activity-tab',
					  		  function (){
							    $('#activity-tab-window').window('close');
							    try{
									refreshGrid('${gridId}');
								}catch(err){
								  
								}
							  }
							 );
					}
				}
				<%int index=0;%>
				<s:iterator  value="#request.buttonList" status="stauts" >
				<%index++;%>
					,{
						    iconCls:'icon-workflow${stauts.index}',
							text:'${toTransferDefStautsText}',
							handler: function(){
								if(!activityFunContext()){
									return;
								}
								$.messager.confirm('操作确认框', '确认是否提交？', function(r){if(r){
									if(button_${gridId}_submittype){
										  return;
									  }else{
									  	button_${gridId}_submittype = true;
									  }
									saveWorkFlowTabForm(
										'activity-tab',
										function (){
										  submit_workflow_log('','${stitchingParameter}&MAIN_APP_ID=${APP_ID}&status_text=${toTransferDefStauts_text}','${condition}');
										  $('#activity-tab-window').window('close');
											try{
												refreshGrid('${gridId}');
											}catch(err){
											  
											}
										},
									   '${condition}',
									   '${stitchingParameter}&MAIN_APP_ID=${APP_ID}&status_text=${toTransferDefStauts_text}'
								  	);
								}});
							/**
								 if(document.getElementById('activityFuctionForSubmitted')){
							  	  var fctString=document.getElementById('activityFuctionForSubmitted').innerHTML;
							  	  eval(fctString);
							 	 }
							**/
							}
					  }
				</s:iterator>
				/**
				,{
					iconCls:'icon-save',
					text:'回退',
					handler: function(){
						if(document.getElementById('activityFuctionForBack')){
					  	  var fctString=document.getElementById('activityFuctionForBack').innerHTML;
					  	  eval(fctString);
					 	 }
					}
				}**/
			]
		});	

	});

	function activityFunContext(){
		var isbundling = '${isbundling}';
		if( 'on'==isbundling ){
			var submit_flag = true;
			eval('${activityFunContext}');
			return submit_flag;
		}else{
			return true;
		}
	}
</script>