/**
 * 打开对话框
 */
 function openDialog(dialogID){
	try{
		$('#'+dialogID).dialog('open');
	}catch(err){
	
	}
	
}
/**
 * 打开窗口
 */
 function openWindow(windowID){
	try{
		$('#'+windowID).window('open');
	}catch(err){
	
	}
}
/**
 * 关闭窗口
 */
 function closeWindow(windowID){
	try{
		$('#'+windowID).window('close');
	}catch(err){
	
	}
}
/**
 * 关闭对话框
 */
function closeDialog(dialogID){
	$('#'+dialogID).dialog('close');
}
 /**
 *  弹出窗口
 */
 function  OpenWindowPage(windowID,title,flag,parmerUrl,height){
 	
 	if("undefined" != typeof height){
	 	initOpenWindow(windowID,title,flag,height);
	}else{
	 	initOpenWindow(windowID,title,flag);
	}
   var url=parmerUrl;
   	   url=encodeURI(url);
	if (url.indexOf("?") != -1) {
			url = url + "&date=" + new Date().getTime();
	} else {
			url = url + "?date=" + new Date().getTime();
	}
	
    $("#"+windowID).window('refresh',url);  
 
 }
 
  /**
 *  导出弹出窗口 GUOWEIXIN
 */
 function  OpenWindowPage1(windowID,title,flag,parmerUrl,height,gridID){
 	if("undefined" != typeof height){
	 	initOpenWindow(windowID,title,flag,height);
	}else{
	 	initOpenWindow(windowID,title,flag);
	}
   var url=parmerUrl;
   	   url=encodeURI(url);
   	   url+="&gridID="+gridID;
	if (url.indexOf("?") != -1) {
			url = url + "&date=" + new Date().getTime();
	} else {
			url = url + "?date=" + new Date().getTime();
	}
	
    $("#"+windowID).window('refresh',url);  
 
 }
/**
 *  详情查看页
 */
 function  loadViewPage(parmerUrl,viewDataDiv,viewIsTab,windowID,title,flag,height,width){
 	initOpenWindow(windowID,title,flag,height,width);
 	parmerUrl=encodeURI(parmerUrl);
 	
 	var url='';
 	if(parmerUrl.indexOf(".")!=-1){//自定义链接
		if(parmerUrl.indexOf("?")!=-1){
		 	url=PROJECTNAME+parmerUrl+"&date=" + new Date().getTime();
		}else{
		 	url=PROJECTNAME+parmerUrl+"?date=" + new Date().getTime();
		}
	}else{//平台链接
			url=LOAD_VIEW_URL+'formId='+parmerUrl;
		   	if (url.indexOf("?") != -1) {
					url = url + "&date=" + new Date().getTime();
			} else {
					url = url + "?date=" + new Date().getTime();
			}
			url += '&viewPageDivId=' + windowID;
		    if(viewIsTab=='true'){
		    	if(document.getElementById('loadTab-view-div')){
					var element = document.getElementById("loadTab-view-div");
					var i=0;
					while (element.firstChild) {
					    element.removeChild(element.firstChild);
					}
				}
			}
	}
	$("#"+windowID).window('refresh',url); 
 }
 /**
 * 加载编辑页数据
 * @param opertorValue  0 insert; 1 update;
 */
 function loadEditPage_easyui(listpageId,isAbleWorkFlow,formID,keyUrl,opertorValue,IDkey,innerHTML_ID,editIsTab,windowID,title,flag,parentAppId,height,width){
	/**
	 * order 处理
		if(document.getElementById('editform_'+formID)){
			$('#editform_'+formID).remove();
		} 
	*/
	var url='';
	initOpenWindow(windowID,title,flag,height,width);
	
	if(formID.indexOf(".")!=-1){//自定义链接
		if(formID.indexOf("?")!=-1){
		 	url=PROJECTNAME+formID+"&windowID="+windowID+"&opertorType="+opertorValue+"&date=" + new Date().getTime()+keyUrl+'&tabWindowID='+windowID;
		}else{
		 	url=PROJECTNAME+formID+"?windowID="+windowID+"&opertorType="+opertorValue+"&date=" + new Date().getTime()+keyUrl+'&tabWindowID='+windowID;
		}
	}else{//平台链接
		 url=LOAD_EDITPAGE_URL+'formId='+formID+keyUrl+"&opertorType="+opertorValue+'&tabWindowID='+windowID+ "&date=" + new Date().getTime()+'&parentAppId='+parentAppId;
	}
	var lprid = windowID.substring(windowID.indexOf('_')+1,windowID.length);
	url += '&lprid='+lprid+'&isAbleWorkFlow='+isAbleWorkFlow+'&listpageId='+listpageId;
	url=encodeURI(url);
	/**
	 * 加载多标签页 和单独的页面
	 */
	$("#"+windowID).window('refresh',url); 

	/**
	$.ajax({
			    url: url,
			    type: 'post',
			    success: function(data){
					if(editIsTab=='true'){
 						   document.getElementById(windowID).innerHTML=data;
					}else{
					   if(document.getElementById(innerHTML_ID)){
					      document.getElementById(innerHTML_ID).innerHTML=data;
						}
					}
				    	
				     var regExp=/<script[^>]*>([\s\S]*?)<\/script>/gi;
				     var matchArray=data.match(regExp);
		 			 
		 			 if(matchArray){
					      for(var i=0;i<matchArray.length;i++){
					           var jsData=matchArray[i].toString();//其实用正则表达式的向前向后查找的话是很方便的，但javascript却不支持，所以只能采取此下策
						       var regExp_temp=/<script[^>]*>([\s\S]*?)<\/script>/gi;
						       var matchArray_temp=jsData.match(regExp_temp);
						       var jsQueryString=""+(RegExp.$1).trim()+"";
						       try {
						        	eval(jsQueryString);//执行
						       	}catch(err){
						       		// $.messager.alert('提示','页面js动态执行的参数不全！','info');
						      			alert('页面js动态执行的参数获取失败！: '+jsQueryString);
						       }
						    }
				      }
				    	
				   
				}
	});
	**/
	
 } 
 /**
 *自动生成保存的。。。
 */
 function createSavebutt(isAbleWorkFlow,WORKFLOW_ENABLE,listPageRander,listPageId,editPageId,keyUrl,opertorValue,IDkey,innerHTML_ID,editIsTab,windowID,title,flag){

 	loadEditPage_easyui(isAbleWorkFlow,listPageId,editPageId,keyUrl,opertorValue,IDkey,innerHTML_ID,editIsTab,windowID,title,flag);
 	
 }
 /**
  * @param form       提交的表单
  * @param datagrid   列表页grid
  * @param editWindow 编辑页窗口 
  * 保存编辑页  业务数据
  */
 function dynamicSaveData(form,gridID,editWindowID,queryFormID,opertype){
 	var ieorf = navigator.appName;
 	var type = opertype;
 	if(ieorf=="Microsoft Internet Explorer"){
	 	if(MyObject){
	    	MyObject.UpdateEditorFormValue();
	    }
 	}else{}
    
    var flag=true;
    
    try{
    	flag= $('#'+form).form('validate');
    }catch(err){
    
    }
   
	var options = {
		              type:"post",
		              data:{type:type},
		              success:function (responseText, statusText) {
			              		closeDialog(editWindowID);//关闭编辑页
			              		refreshGrid(gridID);
		              }};
	/**
	 * 验证成功
	 */			
	if(flag){
		$('[id^='+editWindowID+']').each(function(){
			$(this).attr('disabled',true);
		});
		$("#" + form).ajaxSubmit(options);
	}
 } 
 /**
  *  删除数据
  */
  function deleteData_easyui(url,gridID){

	
	var url=DELETE_DATA_URL+url;
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
 /**
  * 刷新列表
  */
  function refreshGrid(gridID){
	  	$('#'+gridID).datagrid("reload");
		$('#'+gridID).datagrid("clearSelections");//清空所有选中
   }

 function initWindow(windowID,title,height,width){
	if(document.readyState=="complete"){
		    var gaodu;
			if(height == 0||"undefined" == height||height==null||height==''){
				gaodu = 500;
			}else{
				gaodu = height;
			}
			var kuandu;
			if(width == 0||"undefined" == width||width==null||width==''){
				kuandu = 820;
			}else{
				kuandu = width;
			}
			if(windowID.indexOf('viewPageWindow_')!=-1 || windowID.indexOf('editPageWindow_')!=-1 || windowID.indexOf('_listpagezdy')!=-1){
			    $('#'+windowID).appendTo('body').window({
					  title: ''+title,
					  iconCls:"icon-task-show",
					  width: kuandu,
					  height: gaodu,
					  zIndex:9200,
					  doSize:true,
					  resizable:true,
					  minimizable:false,
					  maximizable:false,
					  modal: true,
					  onClose:function(){
						  var options = $("#" + windowID).window("options");
							$("#" + windowID).window("destroy", true);
							if (!$("#" + windowID).get(0)) {
								$("<div id='" + windowID + "'></div>").appendTo('body');
							} else {
								$("#" + windowID).window(options);
							}
					  }
				});
			}else{
				$('#'+windowID).appendTo('body').window({
					  title: ''+title,
					  iconCls:"icon-task-show",
					  width: kuandu,
					  height: gaodu,
					  zIndex:9200,
					  doSize:true,
					  resizable:true,
					  minimizable:false,
					  maximizable:false,
					  modal: true,
					  closed: true
				});
			}
	}
}
/**
弹出控制树窗口
*/
function openwindowhandle(url,windowID,name,treeID){
	url=encodeURI(url);
	$("#"+windowID).window({
		title:name+'&nbsp;',
		iconCls:"icon-task-show",
	    width: 1100,
	    height:500,
	    zIndex:9200,
	    doSize:true,
	    resizable:true,
	    minimizable:false,
	    maximizable:false,
	    modal: true,
	    href:url,
		closed:false,
		onClose:function(){
			$('#'+treeID).tree('reload');
			setTimeout(function(){
			  var options = $("#" + windowID).window("options");
				$("#" + windowID).window("destroy", true);
				if (!$("#" + windowID).get(0)) {
					$("<div id='" + windowID + "'></div>").appendTo('body');
				} else {
					$("#" + windowID).window(options);
				}
			},0);
		}
	});
	//$("#"+windowID).window({'href':''});		
    //$("#"+windowID).window('refresh');
    //$("#"+windowID).window({'href':url});		
    //$("#"+windowID).window('open');
}
/**
 * 防治grid加载两次
 */	
 
 function initGridData(gridID,url,initStat,tabIsUse){
	/**
	 * 使用多标签时
	 */
     var statValue=document.getElementById(initStat).value;
	 if(statValue=='1'){
	 	if(document.readyState=="complete"){
		    $('#'+gridID).datagrid({
			   url:url
			});
		}
	 }
	/**
	 * 不使用多标签时
	 */
	if(tabIsUse=='false'){
		$('#'+gridID).datagrid({
				   url:url
		});
	}	
	document.getElementById(initStat).value='1';
}

function formReset(formID){
   $("#"+formID).form('reset');
	// document.getElementById(formID).reset();
}
/**
 * 清除只读控件框
 */
function clearText(ID,hiddenID){
	 if(document.getElementById(ID)){
		$("#"+ID).next().children(".combo-text").val("");
	 	//document.getElementById(ID).value='';
	 }
	 if(document.getElementById(hiddenID)){
		document.getElementById(hiddenID).value='';
	 }
 }
 
 /**
  *
  */
function openWindowAndBlur(windowID,componentsID){
	openWindow(windowID);
	if(document.getElementById(componentsID).blur()){
  		document.getElementById(componentsID).blur();
	}
}	
 /**
  * 初始化树
  */
 function initTreeData(treeData,treeID,isCheckBox,onlyLeafCheck,toTextID,hiddenID,windowID){
  
  //临时设置
  if(isCheckBox==null||isCheckBox==''){
	  isCheckBox=false;
  }
  if(onlyLeafCheck==null||onlyLeafCheck==''){
	  onlyLeafCheck=false;
  }
  
  	var treeArray=eval(treeData);//初始化数据	
		$('#'+treeID).tree({
		  checkbox:isCheckBox,//是否可以多选
			  data:treeArray,
			  width:280,
	   onlyLeafCheck:onlyLeafCheck,
			 // cascadeCheck:true,//当选完父节点。级联选中子节点
			onDblClick:function (node){
				if(!isCheckBox){
						var node=$('#'+treeID).tree('getSelected');
					    var isLeaf = $('#'+treeID).tree('isLeaf', node.target);
					    
						 if(onlyLeafCheck){//只能选择叶子节点
						 	  if(isLeaf){
							 		document.getElementById(toTextID).value=node.text;
									document.getElementById(hiddenID).value=node.id;
									     closeWindow(windowID);
							 	  }
						 }else{//可以选择非叶子节点
							  document.getElementById(toTextID).value=node.text;
							  document.getElementById(hiddenID).value=node.id;
						 		     closeWindow(windowID);
						 }
				}
				
			}
		});	
   }
       /**
  * 初始化树
  */
 function initTreeDataHuman(treeData,treeID,isCheckBox,onlyLeafCheck,toTextID,hiddenID,windowID){
  
  //临时设置
  if(isCheckBox==null||isCheckBox==''){
	  isCheckBox=false;
  }
  if(onlyLeafCheck==null||onlyLeafCheck==''){
	  onlyLeafCheck=false;
  }
  
  	var treeArray=eval(treeData);//初始化数据	
		  $('#'+treeID).tree({
		    data:treeArray,
		    width:280,
	   		onlyLeafCheck:true,
			 // cascadeCheck:true,//当选完父节点。级联选中子节点
			onClick:function(node){
				$('#tree-table-${stauts.index}${columnID}').datagrid({
			    	url: 'com_*!queryForHumanList.action?orgid='+node.id+'&dictionaryId=${treeComponents.dictionaryId}'
			    });
			}
		});	
   }  
   
   
   /**
   *@param toTextID  传值的文本ID
   *@param value  传的值
   *@param windowID  关闭的窗口ID
   */
  function valueToFiedSingleChooseTree(toTextID,value,windowID){
  
     try{
      document.getElementById(toTextID).value=value;
    
     }catch(err){
	 	
	 }
     closeWindow(windowID);
  }	
   
   /**
   * @param treeID    树的ID
   * @param toTextID  传值的文本ID
   * @param windowID  关闭的窗口ID
   * @param isCheckBox 是否是多选树
   * @return
   * 关闭ajax-box 窗口 并传回所选的树
   * 
   */
  function  valueToField(treeID,toTextID,hiddenID,windowID,onlyLeafCheck){
      	try{
   		      /**
			   * 保存隐藏域属性
			   */
			    value=getAllCheckedTree(treeID,onlyLeafCheck);
			    document.getElementById(hiddenID).value=value;
			  /**
			   * 保存显示文本
			   */
			    value= getAllCheckedTreeForName(treeID,onlyLeafCheck);
		  		document.getElementById(toTextID).value=value;
		
	  }catch(err){
	 	
	  }
     closeWindow(windowID);
  }
  /**
   * @param treeID    树的ID
   * @param toTextID  传值的文本ID
   * @param windowID  关闭的窗口ID
   * @param isCheckBox 是否是多选树
   * @return
   * 关闭ajax-box 窗口 并传回所选的树
   * 单选框的时候的树
   */
  function  valueToFieldSingle(treeID,toTextID,hiddenID,windowID,onlyLeafCheck){
      	try{
   		      /**
			   * 保存隐藏域属性
			   */
			    value=getSelectedTree(treeID,onlyLeafCheck);
			    document.getElementById(hiddenID).value=value;
			  /**
			   * 保存显示文本
			   */
			    value= getSelectedTreeForNameSingle(treeID,onlyLeafCheck);
			    if(value=='undefined'||value==undefined){
			    	$.messager.alert('提示', '请选择数据!', 'warning');
			    	return false;
			    }
		  		document.getElementById(toTextID).value=value;
		
	  }catch(err){
	 	
	  }
     closeWindow(windowID);
  }
   /**
  * 获取选中的树的code
  */
   function getSelectedTree(treeID,onlyLeafCheck){
	var nodes = $('#'+treeID).tree('getSelected');
	var treename = '';
	if(nodes!=null){
		treename = nodes.id;
		return treename;
	}
}
  
 /**
  * 获取选中的所有树的code
  */
 function getAllCheckedTree(treeID,onlyLeafCheck){
	var isLeaf;
	var nodes = $('#'+treeID).tree('getChecked');
	var treename = '';
	for(var i=0; i<nodes.length; i++){
	   isLeaf = $('#'+treeID).tree('isLeaf', nodes[i].target);
			if(onlyLeafCheck){//只能选择叶子节点
				if(isLeaf){
					 if (treename != '') 
								 treename += ',';
								 treename += nodes[i].id;
				}
				continue;
			}
			
			 if (treename != '') 
				 treename += ',';
				 treename += nodes[i].id;
		 
	}
	return treename;
}
 /**
  * 获取选中的所有树的name
  */
 function getAllCheckedTreeForName(treeID,onlyLeafCheck){
    var isLeaf;
	var nodes = $('#'+treeID).tree('getChecked');
	var treename = '';
	for(var i=0; i<nodes.length; i++){
	   isLeaf = $('#'+treeID).tree('isLeaf', nodes[i].target);
	
		if(onlyLeafCheck){//只能选择叶子节点
			if(isLeaf){
				 if (treename != '') 
							 treename += ',';
							 treename += nodes[i].text;
			}
			continue;
		}
		
	   if(treename != '') 
		  treename += ',';
		  treename += nodes[i].text;
	   }
	return treename;
}
 /**
  * 获取选中的树的name
  */
 function getSelectedTreeForNameSingle(treeID,onlyLeafCheck){
	var nodes = $('#'+treeID).tree('getSelected');
	var treename = '';
    if(nodes!=null){
	  treename += nodes.text;
		return treename;
    } 
}
/**
 * 初始化 并打开一次窗口
 */
function initOpenWindow(windowID,title,flag,height,width){

	var flagValue=document.getElementById(flag).value;
	
		if(flagValue=='0'){
			//if("undefined" != typeof height){
				initWindow(windowID,title,height,width);
			//}else{
				//initWindow(windowID,title,height,width);
			//}
			document.getElementById(flag).value='1';
		}else{
			if(windowID.indexOf('viewPageWindow_')==-1 && windowID.indexOf('editPageWindow_')==-1 && windowID.indexOf('_listpagezdy')==-1){
				$('#'+windowID).window('setTitle',title);
			}else{
				initWindow(windowID,title,height,width);
			}
		}
	  openWindow(windowID);
}

  /**
   * 显示组合查询
   */    
  function comQueryForPanel(formID,gridID){
   //var dataGridOpts=$('#'+gridID).datagrid('options');
      var actionUrl=document.getElementById(formID).action;
	  var obj = Ext.getCmp(gridID).store.baseParams; 
	  actionUrl=actionUrl+'&start=0&limit='+obj.limit;
	  document.getElementById(formID).action=actionUrl;
    /**
     *
	    组合查询 jquery分页
	        if(actionUrl.indexOf('&rows=')<0){
		   	     document.getElementById(formID).action=actionUrl+'&rows='+dataGridOpts.pageSize+'&page='+dataGridOpts.pageNumber;			
			}else{
		   		actionUrl=actionUrl.substring(0,actionUrl.indexOf('&rows='));
		   		document.getElementById(formID).action=actionUrl+'&rows='+dataGridOpts.pageSize+'&page='+dataGridOpts.pageNumber;			
			}
     */
   var options = {
		              type:"post", 
		              success:function (responseText, statusText) {
		                 var dataObject=eval("("+responseText+")");
			            /**
			            	jquery方式
			            	$('#'+gridID).datagrid("loadData",dataObject);
		               	    $('#'+gridID).datagrid("clearSelections");//清空所有选中
	               	    **/
	           	     //Ext
               	     try{
               	    	 Ext.getCmp(gridID).getStore().loadData(dataObject);
               	     }catch(err){
               	     	//没有数据
               	     }
               		  
				}};
	$("#" + formID).ajaxSubmit(options);
}  
  /**
   * 批量删除
   */
function bulkDelete(gridId,ids,formId,queryFormID){
	$.messager.confirm('删除确认框', '您确认删除所选记录？', function(r){
		if (r){
			/**
			 * 定义变量
			 */
			var url=BULKDELETE_DATA_URL+'listFormID='+formId;
		    var rows = $('#'+gridId).datagrid('getSelections');
			
			var idArray=ids.split(',');//多主键 		
			var temid='';
			
			/**
			 * 拼接联合主键，单个主键 的删除参数
			 */
			for(var j=0;j<idArray.length;j++){//遍历多主键
				url=url+'&'+idArray[j]+'=';	
				for(var i=0;i<rows.length;i++){//遍历多行数据
					
					temid=rows[i][idArray[j]];
					
					if(i==0){
					  	url=url+temid;
					}else{
						url=url+','+temid;
					}
				}
			}
			/**
			 * 防止浏览器缓存
			 */
			if (url.indexOf("?") != -1) {
					url = url + "&date=" + new Date().getTime();
			} else {
					url = url + "?date=" + new Date().getTime();
			}
			/**
			 * JS乱码处理
			 */
			url=encodeURI(url);
			/**
			 * 删除action
			 */
			if(rows.length>0){
					 $.ajax({
						    url: url,
						    type: 'post',
						    success: function(data){
						     	refreshGrid(gridId);
							}
				    });	
			}else{
			 	   $.messager.alert('提示', '请选择数据！', 'warning');
			}
		}
	});
}

 /**
  * 初始化树
  */
 function createMenuTree(treeData,treeID){
 
  	var treeArray=eval(treeData);//初始化数据	
  	if(treeData.trim().length== 0)return;
  	
		$('#'+treeID).tree({
			  checkbox:false,//是否可以多选
			  data:treeArray,
		 	  onlyLeafCheck:false,
		 	  url:'',
			  onClick:function (node){
				var isLeaf=	$('#'+treeID).tree('isLeaf',node.target);
				if(!isLeaf) {
				$('#'+treeID).tree('toggle',node.target);
			  //$('#'+treeID).tree('expand',node.target);
			  //$('#'+treeID).tree('collapse',node.target); 
			  	}else{
					var treeNodeUrl=node.attributes.url;
				
					treeNodeUrl=encodeURI(treeNodeUrl);
				
					if(treeNodeUrl.indexOf(".")>0){
						if(treeNodeUrl.indexOf("?")>0){
							$('#main').panel('refresh',treeNodeUrl+ "&date=" + new Date().getTime());
						}else{
							$('#main').panel('refresh',treeNodeUrl+ "?date=" + new Date().getTime());
						}
					}else{
						$('#main').panel('refresh','formengine/listPageAction.action?formId='+treeNodeUrl+ "&date=" + new Date().getTime()+'&menuId='+node.id+'&isAbleWorkFlow='+node.attributes.isAbleWorkFlow+'&workflow_fiter='+node.attributes.workflow_fiter+'&is_resid='+node.id);
					}
			   }
			}
		});	
   } 
   
 /**
  * 切换扩展属性
  * @parmer controlType 当前选中的控件id,currentIdType 控件类型下拉选的id
  *
  */
  function switchExtended(controlType,currentIdType){
  	
  }
 /**
  * 批量编辑
  * @parmer controlType 当前选中的控件id,currentIdType 控件类型下拉选的id
  */
  function bulkEdit(formIDs,editWindowID,gridID){
   	 formIDs= eval(formIDs);
  	 var form;
     var flag=true;
    
    $("#"+editWindowID+" form").each(function(){
	      if(!$(this).form('validate')){
	         flag=false;
	      }
	      return flag;
	});
     if(flag){
		$("#"+editWindowID+" form").each(function(){
				$(this).form('submit',{ 
				 // url:'form/form!add.action?dataObjectId='+dataObjId+'&form.type=listPage', 
					onSubmit:function(){ 
						return true;
					  //return $(this).form('validate'); 
					}, 
					success:function(data){ 
						//此处待添加 表单提交数量判断
						 closeDialog(editWindowID);
				       	 //refreshGrid(gridID);
				       	$('#'+gridID).datagrid("reload");
					} 
			   }); 
		
		});
     }
     
     
     /**
     for(var i=0;i<formIDs.length;i++){
       		form='editform_'+formIDs[i].formId;
			
			if(MyObject){
		    	MyObject.UpdateEditorFormValue();
		    }
		    
		    if((document.getElementById(form)==null)){
		    	return ;
		    }
		
		    var elementArray=document.getElementById(form).elements;
		    var elementID;
		    var flag=true;
		    var temFlag=true;
		    
		    for(var j=0;j<elementArray.length;j++){
		      elementID=elementArray[j].id;
		        if(elementID){
				    try{
				    	temFlag=$('#'+elementID).validatebox("isValid");
				    }catch(err){
						continue;
				    }
			    	flag=flag&&temFlag;
			    }
			 }
		
		bulkFlag=flag;
	
		 var options = {
		              type:"post", 
		              success:function (responseText, statusText) {
				       
				       		 closeDialog(editWindowID);
				       		 refreshGrid(gridID);
		                   //comQueryForPanel(queryFormID,gridID);
		            }};
		
	  if(flag) $("#" + form).ajaxSubmit(options);		
		
	  }
	  **/
 }
 
 jQuery.cookie = function(name, value, options) {
		    if (typeof value != 'undefined') { // name and value given, set cookie
		        options = options || {};
		        if (value === null) {
		            value = '';
		            options.expires = -1;
		        }
		        var expires = '';
		        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
		            var date;
		            if (typeof options.expires == 'number') {
		                date = new Date();
		                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
		            } else {
		                date = options.expires;
		            }
		            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
		        }
		        // CAUTION: Needed to parenthesize options.path and options.domain
		        // in the following expressions, otherwise they evaluate to undefined
		        // in the packed version for some reason...
		        var path = options.path ? '; path=' + (options.path) : '';
		        var domain = options.domain ? '; domain=' + (options.domain) : '';
		        var secure = options.secure ? '; secure' : '';
		        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
		    } else { // only name given, get cookie
		        var cookieValue = null;
		        if (document.cookie && document.cookie != '') {
		            var cookies = document.cookie.split(';');
		            for (var i = 0; i < cookies.length; i++) {
		                var cookie = jQuery.trim(cookies[i]);
		                // Does this cookie string begin with the name we want?
		                if (cookie.substring(0, name.length + 1) == (name + '=')) {
		                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
		                    break;
		                }
		            }
		        }
		        return cookieValue;
		    }
		};
		
/*
 * 扩展JS，后续项目中扩展的公共JS方法都放到这里。
 * By yuweihai
 * 2011-2-9
 */
 //框架中弹出层打开编辑页面 by yuwh 2011-2-9
 //ptitle：标题 pwidth：宽度 pheight:高度 purl:url地址
 function OpenEditPage(ptitle,pwidth,pheight,purl){
			var wintop=((parent.document.body.clientHeight-pheight)/2);
			var winleft=((parent.document.body.clientWidth-pwidth)/2);
			
			$('#ParentOpenWindow').remove();
			$("body").append('<div id="ParentOpenWindow"></div>');
			
			$('#ParentOpenWindow').window({
						                title: ptitle,
						                iconCls:'icon-view',
						                modal: true,
						                resizable: false,
						                minimizable: false,
						                maximizable: false,
						                shadow: false,
						                closed: true,
						                width: pwidth,
						                height: pheight,
						                top: wintop,
						                left: winleft
						            });
//      	$("#ParentOpenWindow").window({'href':''});
//			$("#ParentOpenWindow").window('refresh');
			$("#ParentOpenWindow").window({'href':purl});				
			$("#ParentOpenWindow").window('open');
} 


function clickToPanel(url,id,isAbleWorkFlow,workflow_fiter){
	var treeNodeUrl=url;
	if(treeNodeUrl.indexOf(".")>0){
		if(treeNodeUrl.indexOf("?")>0){
		$('#main').children().empty();
			$('#main').panel('refresh',treeNodeUrl+ "&date=" + new Date().getTime());
		}else{
		
			$('#main').children().empty();
			$('#main').panel('refresh',treeNodeUrl+ "?date=" + new Date().getTime());
		}
	}else{
		$('#main').panel('refresh','formengine/listPageAction.action?formId='+treeNodeUrl+ "&date=" + new Date().getTime()+'&menuId='+id+'&isAbleWorkFlow='+isAbleWorkFlow+'&workflow_fiter='+workflow_fiter+'&is_resid='+id);
	}
}
/*
 *根据sqlid得到其SQL语句，进行相关查询
 *将返回的数据 load（加载）到当前表单
 *sqlid :基础数据管理--》SQL存储过程，触发器 的ID
 *params:参数值
 *formId:当前表单的ID
 */
function execteSqlDic(sqlid,params,formId){
	$.ajax({
		type:'post',
		data:params,
		url:'dictionary/dataDictionary!execteSqlDic.action?sqlid='+sqlid,
		success:function(data,status){
		   if(data=="" || data==null){
				$("#"+formId).form("clear");
				var dataObj=eval("("+params+")");
				$("#"+formId).form("load",dataObj);
			}else{
				var dataObj=eval(data);	
				$('#'+formId).form("load",dataObj[0]);
			}
		}
	});
}