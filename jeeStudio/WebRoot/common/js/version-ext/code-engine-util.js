//去空格
 String.prototype.trim = function(){ 
       return this.replace(/(^\s*)|(\s*$)/g, ""); 
 } 

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
 * 加载编辑页数据
 * @param opertorValue  0 insert; 1 update;
 */
 function loadEditPage(formID,keyUrl,opertorValue,IDkey,innerHTML_ID,editIsTab,windowID,title,flag){
	initOpenWindow(windowID,title,flag);
	var url=LOAD_EDITPAGE_URL+'formId='+formID+keyUrl+"&opertorType="+opertorValue;
	
	if (url.indexOf("?") != -1) {
		url = url + "&date=" + new Date().getTime();
	} else {
		url = url + "?date=" + new Date().getTime();
	}
	
	url=encodeURI(url);
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
 } 
 /**
  * @param form       提交的表单
  * @param datagrid   列表页grid
  * @param editWindow 编辑页窗口 
  * 保存编辑页  业务数据
  */
 function dynamicSaveData(form,gridID,editWindowID,queryFormID){
 
    if(MyObject){
    	MyObject.UpdateEditorFormValue();
    }
    if(!document.getElementById(form)){
    		return ;
    }
    var elementArray=document.getElementById(form).elements;
    var elementID;
    var flag=true;
    var temFlag=true;
    for(i=0;i<elementArray.length;i++){
      elementID=elementArray[i].id;
        if(elementID){
		    try{
		    	temFlag=$('#'+elementID).validatebox("isValid");
		    }catch(err){
		    	 continue;
		    }
	    	flag=flag&&temFlag;
	    }
	 }
	var options = {
		              type:"post", 
		              success:function (responseText, statusText) {
		              		closeDialog(editWindowID);//关闭编辑页
		              		// comQueryForPanel(queryFormID,gridID);
		              		try{
		              			 Ext.getCmp(gridID).getStore().reload({
							      	params:{
										//'renderInfo.topid':topid,
										//'renderInfo.renderType':RENDER_TYPE_SYMBOL
										}
								 });	
		              			}catch(err){
		              		
		              			}
		               		}};
	/**
	 * 验证成功
	 */			
	if(flag) $("#" + form).ajaxSubmit(options);
 } 
 /**
  *  删除数据
  */
  function deleteData(url,gridID){

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
						    // Ext方式
						   	  	Ext.getCmp(gridID).getStore().reload({
							      	params:{
										//'renderInfo.topid':topid,
										//'renderInfo.renderType':RENDER_TYPE_SYMBOL
										}
								});
						    //jquery 方式
					  		// refreshGrid(gridID); 
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
   /**
    * 组合查询

    
  function comQueryReloadListGrid(formID,gridID,viewPageID){

	   var options = {
			              type:"post", 
			              success:function (responseText, statusText) {
			              var dataObject=eval("("+responseText+")");
			              if(viewPageID){
			              	 closeDialog(viewPageID);//关闭编辑页
			              }
			              //jquery 
			      		// $('#'+gridID).datagrid("loadData",dataObject);
	               	    // $('#'+gridID).datagrid("clearSelections");//清空所有选中
	               	      
	               	     //Ext
	               	   	Ext.getCmp(gridID).getStore().reload(dataObject);
	               	     
					}};
		$("#" + formID).ajaxSubmit(options);
 
}  
**/
/**
 *  详情查看页
 */
 function  loadViewPage(parmerUrl,viewDataDiv,viewIsTab,windowID,title,flag){
   initOpenWindow(windowID,title,flag);

   var url=LOAD_VIEW_URL+parmerUrl;
   	   url=encodeURI(url);
	if (url.indexOf("?") != -1) {
			url = url + "&date=" + new Date().getTime();
	} else {
			url = url + "?date=" + new Date().getTime();
	}
 
 	$.ajax({
			    url: url,
			    type: 'post',
			    success: function(data){
			    	if(viewIsTab=='true'){
			    		if(document.getElementById('loadTab-view-div')){
				
							var element = document.getElementById("loadTab-view-div");
							var i=0;
							while (element.firstChild) {
							    element.removeChild(element.firstChild);
							}
							
							
							/**
								var obj=$('#loadTab-view-div').tabs('tabs');
								for(i=0;i++;i<obj.length){
									alert(obj[i].title);
								}
							**/
						}
						document.getElementById(windowID).innerHTML=data;
					}else{
						document.getElementById(viewDataDiv).innerHTML=data;
					}
					
					 var regExp=/<script[^>]*>([\s\S]*?)<\/script>/gi;
				     var matchArray=data.match(regExp);
		 			 
		 			 if(matchArray){
					      for(var i=0;i<matchArray.length;i++){
					           var jsData=matchArray[i].toString();//其实用正则表达式的向前向后查找的话是很方便的，但javascript却不支持，所以只能采取此下策
						       var regExp_temp=/<script[^>]*>([\s\S]*?)<\/script>/gi;
						       var matchArray_temp=jsData.match(regExp_temp);
						       var jsQueryString=""+(RegExp.$1).trim()+"";
						       eval(jsQueryString);//执行
					        }
				      }
					
				
				}
	});
 }
 
 
function initWindow(windowID,title){
	if(document.readyState=="complete"){
		    $('#'+windowID).appendTo('body').window({
				  title: ''+title,
				  iconCls:"icon-save",
				  width: 700,
				  height: 450,
				  zIndex:9200,
				  doSize:true,
				  resizable:true,
				  modal: true,
				  closed: true
		});
	}
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
	document.getElementById(formID).reset();
}
/**
 * 清除只读控件框
 */
function clearText(ID,hiddenID){
	 if(document.getElementById(ID)){
	 	document.getElementById(ID).value="";
	 }
	 if(document.getElementById(hiddenID)){
		document.getElementById(hiddenID).value="";
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
  isCheckBox=false;
  onlyLeafCheck=false;
  
  var treeArray=eval(treeData);//初始化数据	
		$('#'+treeID).tree({
		  checkbox:isCheckBox,//是否可以多选
		  data:treeArray,
	   	  onlyLeafCheck:false,
	    //cascadeCheck:true,//当选完父节点。级联选中子节点
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
 * 初始化 并打开一次窗口
 */
function initOpenWindow(windowID,title,flag){
	var flagValue=document.getElementById(flag).value;
		if(flagValue=='0'){
			initWindow(windowID,title);
			document.getElementById(flag).value='1';
		}
	  openWindow(windowID)
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
		
		   //jquery
		   //var rows = $('#'+gridId).datagrid('getSelections');
		   
		   //ExtJs
	       var rows=Ext.getCmp(gridId).getSelectionModel().getSelections();

		
			var idArray=ids.split(',');//多主键 		
			var temid='';
			
			/**
			 * 拼接联合主键，单个主键 的删除参数
			 */
			for(var j=0;j<idArray.length;j++){//遍历多主键
				url=url+'&'+idArray[j]+'=';	
				for(var i=0;i<rows.length;i++){//遍历多行数据
					
					//jquery
					// temid=rows[i][idArray[j]];
					
					//ExtJs
					temid=rows[i].get(idArray[j])
					
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
						    //Ext模式
						     
						     // comQueryForPanel(queryFormID,gridId);
						  	 // refreshGrid(gridId);
						  	  	Ext.getCmp(gridId).getStore().reload({
							      	params:{
										//'renderInfo.topid':topid,
										//'renderInfo.renderType':RENDER_TYPE_SYMBOL
										}
								});	
								
						    }
				    });	
			}else{
			 	   $.messager.alert('提示', '请选择数据！', 'warning');
			}
		}
	});
/**
if(dataTableId && dataTableId != ""){
        		$.messager.confirm('提示', '确认删除吗？',function(a){
	         		if(a)$.post("datatable/dataTable!delete.action",{"dataTableId":dataTableId},bdDataTableDeleteRollback);
	         	});
        	}else{
         		var selected = $('#bd_datatablelist_table').datagrid('getSelections');
	            if (selected == null || selected.length < 1) {
	                $.messager.alert('提示', '请选择数据!', 'warning');
	            } else {
	            	$.messager.confirm('提示', '确认删除吗？',function(a){
	            		if(a){
		            		var dataTableIds = "";
		            		for(var i=0;i<selected.length;i++){
		            			if(dataTableIds == ""){
		            				dataTableIds += "'" + selected[i]['id'] + "'"
		            			}else{
		            				dataTableIds += ",'" + selected[i]['id'] + "'"
		            			}
		                	}
		                	$.post("datatable/dataTable!delete.action",{"dataTableIds":dataTableIds},bdDataTableDeleteRollback);
	                	}
	            	});	               
	            }
            }**/

}

 /**
  * 初始化树
  */
 function createMenuTree(treeData,treeID){
  
  	var treeArray=eval(treeData);//初始化数据	
		$('#'+treeID).tree({
			  checkbox:false,//是否可以多选
			  data:treeArray,
		 	  onlyLeafCheck:false,
		 	  url:'',
			  onClick:function (node){
				
				var type = window.navigator.userAgent.toLowerCase();
				var treeNodeUrl=node.attributes.url;
				
				if (type.indexOf("msie") != -1) {
					if(treeNodeUrl.indexOf(".")>0){
						window.frames["xxxframe"].location=treeNodeUrl;
					}else{
						window.frames["xxxframe"].location='listPageAction.action?formId='+treeNodeUrl+ "&date=" + new Date().getTime();// + "&date=" + new Date().getTime()
					}
				}else if (type.indexOf("firefox") != -1) {
					if(treeNodeUrl.indexOf(".")>0){
						window.top.document.getElementById("xxxframe").src = treeNodeUrl;
					}else{
						window.top.document.getElementById("xxxframe").src = '/compplatform/formengine/listPageAction.action?formId='+treeNodeUrl+ "&date=" + new Date().getTime();
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
  function bulkEdit(formIDs,editWindowID){
   	 
   	 formIDs= eval(formIDs);
  	 var form;
     var bulkFlag=true;
     for(var i=0;i<formIDs.length;i++){
       
     		form='editform_'+formIDs[i].formId;
			
			if(MyObject){
		    	MyObject.UpdateEditorFormValue();
		    }
		    
		    if((document.getElementById(form)==null)){
		    	return ;
		    }
		    
		    /**
		     * 表单的验证
		     */
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
			 /**
			  * 全部标签状态
			  */
		bulkFlag=flag;
		/**
		 * 提交的参数
		 */
		 var options = {
		              type:"post", 
		              success:function (responseText, statusText) {
				              /**
				              if(i=(formIDs.length-1)){
					              	if(bulkFlag){
					             			closeDialog(editWindowID);//关闭编辑页
					              	}
				       		  }
				       		  **/
				       		 closeDialog(editWindowID);
		                     // comQueryForPanel(queryFormID,gridID);
		            }};
		  
		/**
		 * 验证成功
		 */			
	  if(flag) $("#" + form).ajaxSubmit(options);		
	
	}
 }