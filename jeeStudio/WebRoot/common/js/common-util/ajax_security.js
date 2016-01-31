 /*event (object):type:ajaxComplete
 *			 :timeStamp 
*				 :preventDefault:function()
* request(XMLHttprequest):getResponseHeader("noauth")
                          getAllResponseHeaders()
*/
$(function(){
	 $("body").ajaxComplete(function(event,request, settings){ 
	   var __forbidden = request.getResponseHeader("__forbidden");
	   if(__forbidden){
	        $("#main").panel("refresh","common/jsp/403.jsp");
	   }
	   var __timeout = request.getResponseHeader("__timeout");
	   if(__timeout){
	        $("#main").panel("refresh","common/jsp/timeout.jsp");
	   }
	   /*
	   var status = request.status;// 404  500
	   var heads = request.getAllResponseHeaders();
	   var headers ="";
	   for(var i=0;i<heads.length;i++){
	   		headers +=  ""+heads[i];
	   }
	   $(this).append(headers+"");
	   */
	 });
 });