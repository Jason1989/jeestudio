
/**
 * 查看该工作项所有表单
 */
function showTaskViewInfo(workItemID, window_id, serviceDataId) {
	var url = WORKFLOW_INFO;
	url = assembleURL(url);
	url = url + "&workitemId=" + workItemID + "&APP_ID=" + serviceDataId;
	openWindow(window_id);
	document.getElementById(window_id).innerHTML = "";
	$("#" + window_id).panel("refresh", url);
}
function easyuiWinClose_workflow(id) {
	var options = $("#" + id).window("options");
	$("#" + id).window("destroy", true);
	if (!$("#" + id).get(0)) {
		$("<div id='" + id + "'></div>").appendTo("body").window(options);
	} else {
		$("#" + id).window(options);
	}
}
/**
 * 保存多标签的
 * 参数id 多标签的ID
 * event 是一个回调的函数对象
 */
function saveWorkFlowTabForm(id,event,workflowParmer,workflowDataStatus) {
    var flag=true;
	
    var index =0;
	$("#"+id+" form").each(function(){
	      if(!$(this).form('validate')){
	         flag=false;
	      }
	      return flag;
	});
	
	var count=$("#"+id+" form").length;

	if(flag){
		$("#"+id+" form").each(function(){
		         // alert($(this).attr("id")); 获取其中每个表单的id属性
		         // alert($(this).attr("action"));
		         // $(this).attr("action",'url');//修改action属性
		
		         var url=$(this).attr("action");
		         if(url && ''!=url && url.indexOf('uploadPro')==-1){
			         if(workflowDataStatus){// 分支条件参数
			            if (url.indexOf("?") != -1) {
							url = url + "&" +workflowDataStatus+"&"+ workflowParmer;
						} else {
							url = url + "?" +workflowDataStatus+"&" + workflowParmer;
						}
			         }
			        url= encodeURI(url);
		         	$(this).attr("action",url);
		     		$(this).form('submit',{ 
					 // url:'form/form!add.action?dataObjectId='+dataObjId+'&form.type=listPage', 
						onSubmit:function(){ 
							return $(this).form('validate'); 
						}, 
						success:function(data){ index++;
							if( count==index ){
								$.messager.alert("提示","操作成功！", 'info');
								if(event){
									event();
								}
							}
						}
				   }); 
		         }else{
		        	 count--;
		         }
		});
	}
}

/**
  *	工作流日志提交
  */
function submit_workflow_log(id,condition){
	  //添加
	  if('undefined' != typeof id && id !=''){
	 		$('#'+id).form("submit",{
			    url:'workflow/LogWorkFlow!workFlowLogAdd.action?time='+new Date().getTime()+"&formID="+id+"&"+condition,
			    onSubmit:function(){
			        return $(this).form('validate');
			    }
	  		});
	  }else{//编辑
	  		var url = 'workflow/LogWorkFlow!workFlowLogAddETC.action?time='+new Date().getTime()+"&"+condition;
	  		url= encodeURI(url);
	 		$.post(url,function(data){
			});
	  }
}

/**
 * 
 */
function initEasyuiWindow(windowID,title,height,width,windowIcon){
	var gaodu;
	if(height == 0||"undefined" == height||height==null||height==''){
		gaodu = 500;
	}else{
		gaodu = height;
	}
	var kuandu;
	if(width == 0||"undefined" == width||width==null||height==''){
		kuandu = 820;
	}else{
		kuandu = width;
	}
  $('#'+windowID).window({
		  title: title+'',
	      iconCls:'icon-edit',
		  width: kuandu,
		  height: gaodu,
		  zIndex:9200,
		  doSize:true,
		  resizable:true,
		  modal: true,
		  closed: true,
		  onClose:function(){
			  easyuiWinClose_workflow(windowID);
	      }
  });
}