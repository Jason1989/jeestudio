/**
 * 平台固化之加载编辑页数据
 * @param opertorValue  0 insert; 1 update;
 */
 function loadEditPage_curing(urlvalue,listpageId,isAbleWorkFlow,formID,keyUrl,opertorValue,IDkey,innerHTML_ID,editIsTab,windowID,title,flag,parentAppId,height,width){
	var url='';
	initOpenWindow(windowID,title,flag,height,width);
	
	if(formID.indexOf(".")!=-1){//自定义链接
		if(formID.indexOf("?")!=-1){
		 	url=PROJECTNAME+formID+"&windowID="+windowID+"&opertorType="+opertorValue+"&date=" + new Date().getTime()+keyUrl+'&tabWindowID='+windowID+'&forwardType=curing&pageUrl='+urlvalue;
		}else{
		 	url=PROJECTNAME+formID+"?windowID="+windowID+"&opertorType="+opertorValue+"&date=" + new Date().getTime()+keyUrl+'&tabWindowID='+windowID+'&forwardType=curing&pageUrl='+urlvalue;
		}
	}else{//平台链接
		 url=LOAD_EDITPAGE_URL+'formId='+formID+keyUrl+"&opertorType="+opertorValue+'&tabWindowID='+windowID+ "&date=" + new Date().getTime()+'&parentAppId='+parentAppId+'&forwardType=curing&pageUrl='+urlvalue;
	}
	
	var lprid = windowID.substring(windowID.indexOf('_')+1,windowID.length);
	url += '&lprid='+lprid+'&isAbleWorkFlow='+isAbleWorkFlow+'&listpageId='+listpageId;
	url=encodeURI(url);
	$("#"+windowID).window('refresh',url); 
	
 } 
 /**
 *  平台固化之详情查看页
 */
 function  loadViewPage_curing(urlvalue,parmerUrl,viewDataDiv,viewIsTab,windowID,title,flag,height,width){
 	initOpenWindow(windowID,title,flag,height,width);
 	parmerUrl=encodeURI(parmerUrl);
 	
 	var url='';
 	if(parmerUrl.indexOf(".")!=-1){//自定义链接
		if(parmerUrl.indexOf("?")!=-1){
		 	url=PROJECTNAME+parmerUrl+"&date=" + new Date().getTime()+'&forwardType=curing&pageUrl='+urlvalue;
		}else{
		 	url=PROJECTNAME+parmerUrl+"?date=" + new Date().getTime()+'&forwardType=curing&pageUrl='+urlvalue;
		}
	}else{//平台链接
			url=LOAD_VIEW_URL+'formId='+parmerUrl;
		   	if (url.indexOf("?") != -1) {
					url = url + "&date=" + new Date().getTime()+'&forwardType=curing&pageUrl='+urlvalue;
			} else {
					url = url + "?date=" + new Date().getTime()+'&forwardType=curing&pageUrl='+urlvalue;
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