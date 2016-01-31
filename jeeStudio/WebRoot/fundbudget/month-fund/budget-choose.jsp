<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@page import="com.zxt.framework.common.util.RandomGUID"%>
<% 
 	request.setAttribute("fundProject_treeId",RandomGUID.geneGuid());//生成表单列ID 	
%>

<div class="easyui-layout" fit="true">
	<div region="center"  border="false" style="padding:10px;background:#fff;border:1px solid #ccc;width:100%;height:340px;">
		<div class="easyui-layout" fit="true">
		   <div region="center" style="margin-left: 5px;" id="fb_budget_fund" href="<%=request.getContextPath()%>/fbudget/zfb_toAddBudgetPage.action?budgetDictionaryId=${budgetDictionaryId}&fndMonInvestModel.invMonthId=${fndMonInvestModel.invMonthId}&fndMonInvestModel.invState=${fndMonInvestModel.invState}&startOrNextNode=${startOrNextNode}&fndMonInvestModel.appId=${fndMonInvestModel.appId}&isChangeData=${isChangeData}"  >
		 
		   </div>
		   <div region="west"  style="width:300px;" >
		   		<table>
			   		<tr>
			   		 	<td width="100px" align="center" >选择月份：</td>
			   		 	<td>
			   		 		<input style="width: 165px;" value="${fndMonInvestModel.invMonth}" id="fb_budget_choose_month"   name="fb_budget_choose_month" class="easyui-datebox"  required="true" />
						</td>
			   		</tr>  
			   		<tr>
			   		 	<td colspan="2" ><ul id="${fundProject_treeId}"></ul></td>
			   		</tr>
		   		</table>
		   </div>
		</div>
	</div>
	<div region="south" border="false" style="text-align:center;height:55px;padding: 13px;">
		<c:choose>
			<c:when test="${startOrNextNode eq 'nextnode' }">
			
			</c:when>
			<c:otherwise>
				<a  class="easyui-linkbutton" icon="icon-temp"      href="javascript:saveFndPrjInvestModel('zfb_FndPrjInvestModelForm','<%=request.getContextPath()%>/fbudget/zfb_saveFndPrjInvestModel.action','false','editORAdd','${windowID}','${gridID}','zancun');">暂存</a>
			 	<a  class="easyui-linkbutton" icon="icon-ok"        href="javascript:saveFndPrjInvestModel('zfb_FndPrjInvestModelForm','<%=request.getContextPath()%>/fbudget/zfb_saveFndPrjInvestModel.action','true','editOrAdd','${windowID}','${gridID}','tijiao');" >提交</a>
			 	<a  class="easyui-linkbutton" icon='icon-undo'      href="javascript:formReset('zfb_FndPrjInvestModelForm');"  >重置</a>
			 	<a  class="easyui-linkbutton" icon="icon-cancel"    href="javascript:closeWindow('${windowID}');"  >关闭</a>
			</c:otherwise>
		</c:choose>
		
	</div>
</div>

<script>
	$(function (){
	 	var treeID='${fundProject_treeId}';
		var treeArray=eval('${treeData}');//初始化数据	
		$('#'+treeID).tree({
			  checkbox:true,//是否可以多选
			  data:treeArray,
		      onlyLeafCheck:true,
		      cascadeCheck:false,//当选完父节点。级联选中子节点
		      expand:true,
			  onCheck:function(node,_14f){
			 	 chooseBudget('${fundProject_treeId}','fb_budget_fund','${budgetDictionaryId}','${fndMonInvestModel.invMonthId}','&fndMonInvestModel.invState=${fndMonInvestModel.invState}&startOrNextNode=${startOrNextNode}&fndMonInvestModel.appId=${fndMonInvestModel.appId}&isChangeData=${isChangeData}');
			  }
		});	
		
		$('#'+treeID).tree('expandAll');
		$('#fb_budget_choose_month').datebox({
			editable:false,
	   		formatter: function(date){
	   		 	return date.getFullYear()+'-'+(date.getMonth()+1); 
	   		}
	    });
	});
</script>