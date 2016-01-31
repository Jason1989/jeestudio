<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<form id="zfb_FndPrjInvestModelForm" action="<%=request.getContextPath()%>/fbudget/zfb_saveFndPrjInvestModel.action"  method="post"  >
	<table>
	 	<s:iterator value="#request.fndPrjInvestList" status="stauts" id="editColumnList" >
			<tr>
			  <td width="110px;" align="right">${budName}：</td>
			  <td>
			       <input type="text"    name="budMoney" value="${budMoney}" class="easyui-validatebox" required="true" validType="price" style="width: 220px;"  />
			  	   <input type="hidden"  name="budCode"  value="${budCode}" />
			  	   <input type="hidden"  name="invId"    value="${invId}" > 
			  </td>
			</tr> 
		</s:iterator> 
	</table>
	<!-- 工作流参数   -->
	<input name="taskFormNodeEntity.processInstanceID" type="hidden" value="559487891" />
	<input name="startOrNextNode" type="hidden" value="${startOrNextNode}" />
	<input name="zanCunOrTijiao"  type="hidden" value="zancun" />
	<input name="APP_ID"  		  type="hidden" value="${APP_ID}" />
	<input name="con_param"  	  type="hidden" value="${con_param}" />
	
	
	<!-- 业务数据参数   -->
	
	<input name="isChangeData"  type="hidden" value="${isChangeData}" /><!-- 是否变更 -->
	<input name="fndMonInvestModel.envDatastate"  type="hidden" value="${con_param}" id="fndMonInvestModel_envDatastate" />
	<input name="fndMonInvestModel.invState"      type="hidden" value="${invState}" id="fndMonInvestModel_invState" />
	<input name="fndMonInvestModel.appId"         type="hidden" value="${APP_ID}" id="fndMonInvestModel_appId" />
	<input name="fndMonInvestModel.invMonthId"    type="hidden" value="${fndMonInvestModel.invMonthId}"  id="fndMonInvestModel_invMonthId" />
	<input name="fndMonInvestModel.invMonthIdOld" type="hidden" value="${fndMonInvestModel.invMonthIdOld}" id="fndMonInvestModel_invMonthIdOld" />
	<input name="fndMonInvestModel.invMonth" 	  type="hidden" value="${fndMonInvestModel.invMonth}" id="fndMonInvestModel_invMonth" />
	<input name="fndMonInvestModel.invDepId" 	  type="hidden" value="${oid}"  />
</form>	  