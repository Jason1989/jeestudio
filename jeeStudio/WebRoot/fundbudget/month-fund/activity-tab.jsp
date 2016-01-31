<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<div id="activity-tab"  class="tta">
	<div  title="月度预算信息" style="padding:1px;" href="<%=request.getContextPath()%>/fbudget/zfb_toChooseBudgetPageWorkflow.action?budgetDictionaryId=358038d23cd5f2278a2b170936861902&budgetIDs=-1&fndMonInvestModel.appId=${APP_ID}&windowID&gridID=${gridId}&startOrNextNode=nextnode" ></div>
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
						  monthFundFormInit('zancun');
						  document.getElementById('fndMonInvestModel_invMonth').value=$('#fb_budget_choose_month').datebox("getValue");
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
							
							document.getElementById('fndMonInvestModel_invMonth').value=$('#fb_budget_choose_month').datebox("getValue");
						  //alert(document.getElementById('fndMonInvestModel_invMonth').value);
							monthFundFormInit('tijiao');
							//设置工作流状态
							$("#fndMonInvestModel_envDatastate").val('${toTransferDefStauts_text}');
							//设置业务状态
							if('${stitchingParameter}'.indexOf('con_param=5')!=-1){
								 serviceStautsChangeByWorkFlowStauts();
							}
							
							saveWorkFlowTabForm(
								'activity-tab',
								function (){
								  $('#activity-tab-window').window('close');
									try{
										refreshGrid('${gridId}');
									}catch(err){
									}
								},
							   '${condition}',
							   '${stitchingParameter}&MAIN_APP_ID=${APP_ID}&status_text=${toTransferDefStauts_text}'
						  	);
						}
					  }
				</s:iterator>
			]
		});	
	});
</script>