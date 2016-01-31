function chooseBudget(treeId,windowID,dictionaryID,invMonthId,urlParmers){
	var names=getAllCheckedTreeForName(treeId,true);
	var codes=getAllCheckedTree(treeId,true);
	var url=PROJECTNAME+'fbudget/zfb_toAddBudgetPage.action?windowID='+windowID+'&budgetDictionaryId='+dictionaryID;
	
	 if(invMonthId.trim()!=''){
		url=url+'&fndMonInvestModel.invMonthId='+invMonthId
	 }
	 if((names!='')&&(codes!='')){
		url=url+'&names='+names+'&codes='+codes+urlParmers;
		url=encodeURI(url);
		$("#"+windowID).window('refresh',url);  
	}
}
/**
 * 
 */
function saveFndPrjInvestModel(formID,url,isAbleWorkFolw,addOrEdit,windowID,gridID,zanCunOrTijiao){
	monthFundFormInit(zanCunOrTijiao);
	/**
	 * 数据变更复制
	 */
	var isChangeData=$("input[name='isChangeData']").val();
	if(isChangeData=='true'){
			var invMonthIdOld=$("#fndMonInvestModel_invMonthId").val();
			$("#fndMonInvestModel_invMonthIdOld").val(invMonthIdOld+'');
			$("#fndMonInvestModel_invMonthId").val('');
			$("#fndMonInvestModel_appId").val('');
	}
	
	
	url=encodeURI(url);
	$('#'+formID).form('submit',{ 
	    url:url, 
		onSubmit:function(){  
		 	return ($(this).form('validate'))&&($('#fb_budget_choose_month').datebox("isValid")); 
		}, 
		success:function(data){ 
		    if(data=='0'){
		    	$.messager.alert("提示","当前月度预算已存在，请重新选择月份！", 'info');
		    }else{
		   		refreshGrid(gridID);
		    	$('#'+windowID).window('close');
		    }	
		} 
	});	
	
}
function changeFundYearState(name,value){
	$("input[name='"+name+"']").val(''+value);
}
/**
 * 年度预算变更把老数据的ID copy到新数据里。
 */
function replaceFundYearOldId(now_name,old_name,fund_year_state,form,gridID,editWindowID,queryFormID,opertype){

	
	var value_now=$("input[name='"+now_name+"']").val();
	var value_old=$("input[name='"+old_name+"']").val();
	var old_data_stauts;
	var url=getProjectName()+'fbudget/zfb_updateOldDataStauts.action';

	
	/**
	 * 变更状态中  4=变更暂存,5=已变更
	 */
	if(fund_year_state=='4'){
	    // 老数据变更为 3=变更中
	   old_data_stauts=3;
	}else if(fund_year_state=='5'){
		// 老数据变更为 2=作废
	   old_data_stauts=2;
	}
	 if(value_old.trim().length==0){//第一次拷贝老数据
		value_old=value_now;
		$("input[name='"+old_name+"']").val(value_now);//把老数据的ID赋值给新数据
		$("input[name='"+value_now+"']").val('');//把新数据ID主键 清空 生成一条新数据
		
		$("input[name='opertorType']").val('0');//表单 操作类型修改为编辑
		// alert($("input[name='opertorType']").val());
	}
	
	var flag=$('#'+form).form('validate');
	$("input[name='BUDGET_STATE']").val(fund_year_state);//修改新数据状态
	
	/**
	 *  表单通过验证 修改老数据,保存变更的新数据
	 */
	if(flag){
		 $.post(url,{YEAR_INV_ID_OLD:value_old,fundYearStauts:old_data_stauts},function(data){
		 });
	     dynamicSaveData(form,gridID,editWindowID,queryFormID,opertype);
	}
}
/**
 *  计算投资计划总额
 */
function countAllFund(){
  var MAKE_HOLE=  new Number($("input[name='MAKE_HOLE']").val());
  var INVEST   =  new Number($("input[name='INVEST']").val());
  var GROUND   =  new Number($("input[name='GROUND']").val()); 
  var INVEST_COUNT_ALL=$("input[name='INVEST_COUNT_ALL']").val((MAKE_HOLE+INVEST+GROUND));
}
/**
 * 导入
 * compactCode合同流水号,数据源ID
 */
function importCompact(formID){
	var CON_ID=$("input[name='CON_NO']").val();
	var url=getProjectName()+'fbudget/zfb_importContactByConNumber.action?CON_ID='+CON_ID;
	$('#'+formID).form('load',url);
	//加载之后，把附件路径中的../去掉 待沟通其域名 和文件服务器的存储形式
	
}

/**
 * 表单提交前初始化 属性值
 */
function monthFundFormInit(zanCunOrTijiao){
	changeFundYearState('zanCunOrTijiao',zanCunOrTijiao);
	zanCunOrTijiao=zanCunOrTijiao.trim();
	var invState=($("#fndMonInvestModel_invState").val()).trim();//业务数据状态
	
	/**
	 *  提交状态变更
	 */
	if((zanCunOrTijiao.trim()=='tijiao')&&(invState=='0')){//暂存提交 -->审核中
		$("#fndMonInvestModel_invState").val('6');
	}else if((zanCunOrTijiao=='tijiao')&&(invState=='4')){ //变更暂存-->变更审核中
		$("#fndMonInvestModel_invState").val('7');
	}else if((zanCunOrTijiao=='tijiao')&&(invState=='1')){//归档-->  变更审核中
		$("#fndMonInvestModel_invState").val('7');
	}

    /**
	 *  暂存状态变更
	 */
	if((zanCunOrTijiao=='zancun')&&(invState=='1')){// 归档--> 变更暂存
			$("#fndMonInvestModel_invState").val('4');
	}
	if(document.getElementById('fndMonInvestModel_invMonth')){
		document.getElementById('fndMonInvestModel_invMonth').value=$('#fb_budget_choose_month').datebox("getValue");
	}
}
/**
 * 工作流提交 更新业务状态
 */
function serviceStautsChangeByWorkFlowStauts(){
	var	invState=$("#fndMonInvestModel_invState").val();
	if(invState=='6'){//审核中-->归档
		$("#fndMonInvestModel_invState").val('1');
	}else if(invState=='7'){ //变更审核中-->已变更
		$("#fndMonInvestModel_invState").val('5');
	}
}

function deleteInvMonth(url,gridID){
	url=getProjectName()+url;
	if (url.indexOf("?") != -1) {
			url = url + "&date=" + new Date().getTime();
	} else {
			url = url + "?date=" + new Date().getTime();
	}
	url=encodeURI(url);

	$.messager.confirm('删除确认框', '您确认删除该条记录？', function(r){
			if (r){
					$.ajax({
						    url: url,
						    type: 'post',
						    success: function(data){
						  	   refreshGrid(gridID); 
						    }
				    });
			}
		});
  }