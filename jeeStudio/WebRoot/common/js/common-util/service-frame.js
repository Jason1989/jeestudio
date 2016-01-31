/**
 * 加载系统菜单
 */
function sleep(numberMillis) {
    var now = new Date();
    var exitTime = now.getTime() + numberMillis;
    while (true) {
        now = new Date();
        if (now.getTime() > exitTime)
            return;
    }
}
function systemMenu(url,divId,accordionID,resType,extendPageUrl){
	if(resType && resType == '6'){
	    $("#body.index").layout('panel','west').panel({width:0}).panel('close');
	    $("#body.index").layout('resize');
	  
	    var treeNodeUrl = extendPageUrl;
		if(treeNodeUrl.trim().length!=0){
			if(treeNodeUrl.indexOf(".")>0){
				if(treeNodeUrl.indexOf("?")>0){
					$('#main').panel('refresh',treeNodeUrl+ "&date=" + new Date().getTime());
				}else{		
					$('#main').panel('refresh',treeNodeUrl+ "?date=" + new Date().getTime());
				}
			}
		}
	}else{
		   $("#body.index").layout('panel','west').panel({width:200}).panel("open");
	 	   $("#frameAccordionMenu").find('.accordion-header').css("height","15px");
	       $("#body.index").layout('resize');
	       url=assembleURL(url);
	       	setTimeout(function(){
				$('#'+divId).children().empty();
				$('#'+divId).panel('refresh',url+ "?date=" + new Date().getTime());
			},0);
		   
	}
}
/**
 *
 */
function assembleURL(url){
	if (url.indexOf("?") != -1) {
		url = url + "&date=" + new Date().getTime();
	} else {
		url = url + "?date=" + new Date().getTime();
	}
	url=encodeURI(url);
	return url;
}
/**
 *  查看待办详情
 */
 function daibanViewInfo(windowID,workId){
	 	
	 	/**
		 	var url="";
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
					
						    	$('#'+windowID).window('open');
						   
						}
			});
		**/
		
 		$('#'+windowID).window('open');
 }
  /**
  * 初始化权限资源配置
  */
function autorityTreeForSystem(treeData,treeID){
  
  	var treeArray=eval(treeData);//初始化数据	
  	if(treeData.trim().length== 0)return;
  	
	$('#'+treeID).tree({
		  checkbox:false,//是否可以多选
		  data:treeArray,
	 	  onlyLeafCheck:false,
	 	  url:'',
		  onClick:function (node){
		    	loadSystemChildResource(node.id,'systemChildResource');
		  }
	});	
} 
   
/**
 *  资源管理模块>> 加载系统下所有资源
 */  
function loadSystemChildResource(systemId,treeID){
 
 var url='formengine/zsf_findResourceBySystemId.action?systemId='+systemId
 	 url=assembleURL(url);
 	 $.ajax({
			    url: url,
			    type: 'post',
			    success: function(treeData){
				    if(document.getElementById(treeID)){
				    	/**
				    	 *  初始化树
				    	 */
						  	var treeArray=eval(treeData);//初始化数据	
						  	if(treeData.trim().length== 0)return;
						  	
							$('#'+treeID).tree({
								  checkbox:false,//是否可以多选
								  data:treeArray,
							 	  onlyLeafCheck:false,
							 	  url:'',
								  onClick:function (node){
									findChildResource(node.id,'autority_grid_cloumn');
								  }
							});	
						/**
				    	 *  初始化树 end
				    	 */
				    }
				}
		});
} 
/**
 *  当前资源>>加载当前资源的下级资源
 */ 
function findChildResource(parentId,gridId){
	 var url='formengine/zsf_findChildResource.action?parentId='+parentId
 	 url=assembleURL(url);
 	 $.ajax({
		    url: url,
		    type: 'post',
		    success: function(responseText){
			    if(document.getElementById(gridId)){
			    	var dataObject=eval("("+responseText+")");
			    	 
				   	$("#"+gridId).datagrid("loadData",dataObject);//直接加载json对象
			    }
			}
	});
}

/**
 * 保存系统
 */
function saveAuortityGrid(formId,treeID,windowID){
	var node=$('#'+treeID).tree('getSelected');
    /**
	    var options = {
	             type:"post", 
	             success:function (responseText, statusText) {
					closeWindow(windowID);
					if(document.getElementById('gridOrSystemEdit').value=='1'){//列表资源修改
						var resNode=$('#systemChildResource').tree('getSelected');
						if(resNode){
							findChildResource(resNode.id,'autority_grid_cloumn');
						}
						document.getElementById('gridOrSystemEdit').value='0';
						if(node){
							loadSystemChildResource(node.id,'systemChildResource');
						}else{
							$('#systemChildResource').tree('loadData','{}');
						}
					}else{//系统资源修改
						reloadSysteMenu(treeID);//重新加载系统
						if(node){
							loadSystemChildResource(node.id,'systemChildResource');
						}
					}
		}};
		
		var elementArray=document.getElementById(formId).elements;
	    var elementID;
	    var flag=true;
	    var temFlag=true;
	    for(i=0;i<elementArray.length;i++){
	      elementID=elementArray[i].id;
	        if(elementID){
			    try{
			    	temFlag=$('#'+elementID).validatebox("isValid");
			    	flag=flag&&temFlag;
			    }catch(err){
			    	 continue;
			    }
		    	
		    }
		 }
		if(flag){
			$("#" + formId).ajaxSubmit(options);
		}
	**/
	
		$('#'+ formId).form('submit',{ 
				url:'formengine/zsf_saveResource.action', 
				onSubmit:function(){
					 return $(this).form('validate');  
				}, 
				success:function(data){ 
				 	 /*************************************************/
				   		closeWindow(windowID);
						if(document.getElementById('gridOrSystemEdit').value=='1'){//列表资源修改
							var resNode=$('#systemChildResource').tree('getSelected');
							if(resNode){
								findChildResource(resNode.id,'autority_grid_cloumn');
							}
							document.getElementById('gridOrSystemEdit').value='0';
							if(node){
								loadSystemChildResource(node.id,'systemChildResource');
							}else{
								$('#systemChildResource').tree('loadData','{}');
							}
						}else{//系统资源修改
							reloadSysteMenu(treeID);//重新加载系统
							if(node){
								loadSystemChildResource(node.id,'systemChildResource');
							}
						}
				    /*************************************************/
				 } 
	}); 
}

/**
 * 删除资源
 */
 function deleteSystem(treeid){
 var node=$('#'+treeid).tree('getSelected');
 	$.messager.confirm('提示', '您确认要删除该系统？', function(r){
		if (r){
			if(node){
				var url='formengine/zsf_deleteResource.action?resid='+node.id;
 					url=assembleURL(url);
 	   		$.ajax({
				    url: url,
				    type: 'post',
				    success: function(data){
							$.messager.alert('提示','删除成功！','info');	 	
							reloadSysteMenu('systemAutorityList');//页面上dom节点
							loadSystemChildResource(node.id,'systemChildResource');
						
							findChildResource(node.id,'autority_grid_cloumn');
							loadSystemChildResource(node.id,'systemChildResource');
							 
							
							if(node){
								try{
							  		$('#systemChildResource').tree('loadData','{}');
							  	}catch(err){
							  	
							  	}
								// loadSystemChildResource(node.id,'systemChildResource');
							}
							/**
							 * 级联更新列表
							 */
							try{
						  		$("#"+gridId).datagrid("loadData",'{}');//直接加载json对象
						  	}catch(err){
						  	
						  	}
							
							
					}
				});
 			}else{
 				$.messager.alert('提示','没有选中系统！','info');
 			}
	  	}
	});
 }
 
 
/**
 * 列表删除资源
 */
 function deleteGridResource(id,parentId){

 	$.messager.confirm('提示', '您确认要删除该资源？', function(r){
		if (r){
			
				var url='formengine/zsf_deleteResource.action?resid='+id;
 					url=assembleURL(url);
 	   	
 				$.ajax({
				    url: url,
				    type: 'post',
				    success: function(data){
							$.messager.alert('提示','删除成功！','info');	 
							/**
							 * 刷新列表页
							 */
							findChildResource(parentId,'autority_grid_cloumn');
							/**
							 * 刷新 资源树
							 */	
							 var node=$('#systemAutorityList').tree('getSelected');
							 if(node){
							 
							 	loadSystemChildResource(node.id,'systemChildResource');
							 }
					},
					error:function(XMLResponse){
						//alert(XMLResponse.responseText)
						$.messager.alert('提示','删除成功！','info');	 
							/**
							 * 刷新列表页
							 */
							findChildResource(parentId,'autority_grid_cloumn');
							/**
							 * 刷新 资源树
							 */	
							 var node=$('#systemAutorityList').tree('getSelected');
							 if(node){
							 
							 	loadSystemChildResource(node.id,'systemChildResource');
							 }
					}
				});
	  	}
	});
 }
 
 /**
  *  重新加载系统
  */
 function reloadSysteMenu(treeID){
 
 var url='formengine/zsf_reloadSysteMenu.action'
 	 url=assembleURL(url);
 	 $.ajax({
			    url: url,
			    type: 'post',
			    success: function(treeData){
				    if(document.getElementById(treeID)){
				    	/**
				    	 *  初始化树
				    	 */
						  	var treeArray=eval(treeData);//初始化数据	
						  	if(treeData.trim().length== 0)return;
						  	
							$('#'+treeID).tree({
								  checkbox:false,//是否可以多选
								  data:treeArray,
							 	  onlyLeafCheck:false,
							 	  url:'',
								  onClick:function (node){
										loadSystemChildResource(node.id,'systemChildResource');
								  }
							});	
						/**
				    	 *  初始化树 end
				    	 */
				    }
				}
		});
 }
 /**
  * 添加系统
  */
 function addSystem(windowID){
	  var url='formengine/zsf_loadSystemForm.action?isSystemEdit=1'
 	  url=assembleURL(url);
 	  $.ajax({
		    url: url,
		    type: 'post',
		    success: function(data){
		     	  document.getElementById('resourceEditData').innerHTML=data;
			      addJsExcute(data);
				 
			      $('#treeDataResLevel').combobox('setValue','1');//菜单级别 系统
			      $('#treeDataIsMenu').combobox('setValue','1');//是否菜单项 是
			      $('#treeDataResType').combobox('setValue','0');//资源类型  子系统
			      
			      document.getElementById('treeDataParentID').value='0';//上级节点 id
			  
			      if(document.getElementById(windowID)){
				    	//$('#'+windowID).window('setTitle','添加系统');
				    	$('#'+windowID).window({
				    	title:'添加系统',
				    	minimizable:false
				    	});
				    	openWindow(windowID);
				  }
			}
	 });
 }
 /**
  * 列表添加资源
  */
  function addGridResource(windowID){
   		var url='formengine/zsf_loadSystemForm.action?isSystemEdit=0'
	 	  url=assembleURL(url);
	 	  $.ajax({
				    url: url,
				    type: 'post',
				    success: function(data){
			          document.getElementById('resourceEditData').innerHTML=data;
			          addJsExcute(data);
				      var resNode=$('#systemChildResource').tree('getSelected');
				      if(resNode){
				      	 document.getElementById('treeDataParentID').value=resNode.id;//上级资源id
				      	 document.getElementById('gridOrSystemEdit').value='1';//列表资源添加
				      	 if(document.getElementById('authorit_resource_id')){
				      	 	document.getElementById('authorit_resource_id').readOnly = false;//列表id可手动修改
				      	 }
				      	 openWindow(windowID);
				      	 $('#'+windowID).window('setTitle','添加资源');
				      }else{
				     	 $.messager.alert('提示','没有选中上级资源！','info');
				      }
				      
				}
		 });
 }
 /**
  * 系统资源编辑
  */
 function editSystem(windowId,treeid){
 	 
 	 var node=$('#'+treeid).tree('getSelected');
	 if(node){
	  var url='formengine/zsf_loadResource.action?isSystemEdit=1&resid='+node.id;
	 	  url=assembleURL(url);
	 	  $.ajax({
				    url: url,
				    type: 'post',
				    success: function(data){
				      document.getElementById('resourceEditData').innerHTML=data;
				      addJsExcute(data);
				      
					    $('#treeDataResLevel').combobox('disable');//菜单级别 系统
				   		$('#treeDataIsMenu').combobox('disable');//是否菜单项 是
				  		$('#treeDataResType').combobox('disable');//资源类型  子系统
				  	
				  	  	openWindow(windowId);
				  	  	$('#'+windowID).window('setTitle','系统编辑');
					}
		 });
	 }else{
	 	$.messager.alert('提示','没有选中系统！','info');
	 }
}


 /**
  * 列表资源编辑
  */
 function editGridResource(windowId,id){

	  var url='formengine/zsf_loadResource.action?isSystemEdit=0&resid='+id;
	 	  url=assembleURL(url);
	 	  $.ajax({
				    url: url,
				    type: 'post',
				    success: function(data){
				        document.getElementById('resourceEditData').innerHTML=data;
				        addJsExcute(data);
				        if(document.getElementById(windowId)){
					    	openWindow(windowId);
				    		if(document.getElementById('authorit_resource_id')){
				    			document.getElementById('authorit_resource_id').readOnly = true;//列表id不可手动修改
							}
				    	    $('#'+windowID).window('setTitle','资源编辑');
					    }
					}
		 });
	document.getElementById('gridOrSystemEdit').value='1';
}

function addJsExcute(data){

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