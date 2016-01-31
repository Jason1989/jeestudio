/**
 * 刷新div
 */
function partAction(url,div){
	url=treatMentUrl(url);
	$.ajax({
			    url: url,
			    type: 'post',
			    success: function(data){
				    if(document.getElementById(div)){
 						document.getElementById(div).innerHTML=data;
 						excuteJsHTML(data)
					}
				}
	});
}
/**
 * 执行js字符串
 */
function excuteJsHTML(data){

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

/**
 * 封装url
 */
 
 function treatMentUrl(url){
 	if (url.indexOf("?") != -1) {
		url = url + "&date=" + new Date().getTime();
	} else {
		url = url + "?date=" + new Date().getTime();
	}
	url=encodeURI(url);
	return url;
 }
 