<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<div id="activity-tab"  class="tta">
	
				<div  title="资金结算审批" style="padding:1px;" href="<%=request.getContextPath()%>/formengine/editPageAction.action?formId=402886d433494cfd01334964f2a70007&APP_ID=${APP_ID}&opertorType=1&workflowForm=true" ></div>
		
</div>
<script>
	$(function(){
		$('#activity-tab').tabs({
		   //title:'&nbsp;',
			border:true,
			fit:true,
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
				<s:iterator  value="#request.buttonList" status="stauts" >
					,{
						    iconCls:'icon-workflow${stauts.index}',
							text:'${toTransferDefStautsText}',
							handler: function(){
							saveWorkFlowTabForm(
								'activity-tab',
								function (){
								  submit_workflow_log('','${stitchingParameter}&MAIN_APP_ID=${APP_ID}&status_text=${toTransferDefStauts_text}');
								  $('#activity-tab-window').window('close');
									try{
										refreshGrid('${gridId}');
									}catch(err){
									  
									}
								},
							   '${condition}',
							   '${stitchingParameter}&MAIN_APP_ID=${APP_ID}&status_text=${toTransferDefStauts_text}'
						  	);
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
</script>