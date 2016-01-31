/**
 * 
 */
function  createNewWindow(pannelId,url){
	    url='http://localhost:8080/compplatform/formengine/listPageAction.action?formId=402886b82da21efc012da224384c0006';
	    url=assembleURL(url);
	    $('#'+pannelId).panel('refresh',url);
	 /**
		$.ajax({
			    url: url,
			    type: 'post',
			    success: function(data){
			 
				    if(document.getElementById(pannelId)){
				        document.getElementById(pannelId).innerHTML=data;
					
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
							       		document.getElementById(pannelId).innerHTML=jsQueryString;
							       	}catch(err){
							       		document.getElementById(pannelId).innerHTML=jsQueryString;
							        }
							    }
					      }
					  
				
				    }
				}
		});
	**/
	// $('#'+pannelId).panel('refresh','http://localhost:8080/compplatform/formengine/listPageAction.action?formId=402887a22ba99c87012ba9ae97090001');
}

/**
 * 
 */
function desktop_setmenu(treeData,treeID){
 
 	var treeArray=eval(treeData);//初始化数据	
 	if(treeData.trim().length== 0)return;
 	
	$('#'+treeID).tree({
		  checkbox:true,//是否可以多选
		  data:treeArray,
		  cascadeCheck:false,
	 	  onlyLeafCheck:false,
	 	  url:'',
		  onClick:function (node){
		  		document.getElementById('desktop-menu-name').value=node.text;
		  }
	});	
} 

/**
 *  多行编辑保存 分类  新增，删除，修改。
 */
 function findChangeRowsTOString(gridId){
 	var insertRows = $('#'+gridId).datagrid('getChanges','inserted');
	var updateRows = $('#'+gridId).datagrid('getChanges','updated');
	var deleteRows = $('#'+gridId).datagrid('getChanges','deleted');
	var changesRows = {
	 	    			   inserted : [],
	 	    				updated : [],
	 	    				deleted : [],
 	    				};
		if (insertRows.length>0) {
			for (var i=0;i<insertRows.length;i++) {
				changesRows.inserted.push(insertRows[i]);
			}
		}

		if (updateRows.length>0) {
			for (var k=0;k<updateRows.length;k++) {
				changesRows.updated.push(updateRows[k]);
			}
		}
   					
		if (deleteRows.length>0) {
			for (var j=0;j<deleteRows.length;j++) {
				changesRows.deleted.push(deleteRows[j]);
			}
		}
		//alert(JSON.stringify(changesRows));
   		return 	JSON.stringify(changesRows);
}

 function saveEditGrid(gridId,url){
 	
 	var rows = $('#'+gridId).datagrid('getRows');
 	var parmer=JSON2.stringify(rows);
 	url=PROJECTNAME+url;
 	
    /**
        url=url+"?parmer="+parmer;
	    url=assembleURL(url);
	    $.ajax({ 
			 url: url,
			 type: 'post',
			 success: function(data){
			
			}
	 	});
 	**/
 	var screenWidth=document.body.offsetWidth;
    $.post(url,{parmer:parmer,screenWidth:screenWidth},function(data){
    });
        
    return 	false;
}
/**
 * 同步菜单的挂接的页面
 */
function  createShorcytWindow(menuId,title,formId,width,height){

	/**
	 * 判断是否初始化
	 */
			 if(document.getElementById(menuId)){
				$('#'+menuId).window('destroy',true);
			 }
	 	   	 var wid=(width!=null&&width!='')?width:860;
	 	   	 var hei=(height!=null&&height!='')?height:430;
	 	     $("body").append('<div id="'+menuId+'" ></div>'); 
		 	 $('#'+menuId).appendTo('body').window({
				  title: ''+title,
				  iconCls:"icon-database-table",
				  width: wid,
				  height: hei,
				  zIndex:9200,
				  doSize:true,
				  resizable:true,
				  modal:false,
				  shadow:false,
				  closed:false,
				  cache:true,
				  onClose:function(){
				  	$('#taskbar_'+menuId).remove();
				  }
			});
	
		  url=PROJECTNAME+LISTPAGE_LOAD+formId+'&menuId='+menuId;
		 
		  if(formId.indexOf('.')>0){
				url=PROJECTNAME+formId;
		  }
		  url=assembleURL(url);
		
		  $('#'+menuId).window({'href':url});
	
}


/**
*  在状态栏添加状态条
*/
function desktop_add_taskbar(menuId,title,ioc){
		 var taskbar = null; 
		if(!document.getElementById('taskbar_'+menuId)){
			taskbar = $('<li id="taskbar_'+menuId+'"><a href="#'+menuId+'"><img src="'+ioc+'" style="height:22px;width:22px;"  />'+title+'</a></li>').appendTo('#dock');		
		}else{
		   taskbar = $('#taskbar_'+menuId);
		}
		
		// Show the taskbar button.
		if (taskbar.is(':hidden')) {
			taskbar.remove().appendTo('#dock');
			taskbar.show('fast');
		}
			
}