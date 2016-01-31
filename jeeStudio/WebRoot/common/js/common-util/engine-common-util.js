//去空格
String.prototype.trim = function () {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
function MyClass() {
	this.UpdateEditorFormValue = function () {
		/**
             Fck官方网站 写法
                for ( i = 0; i < parent.frames.length; ++i )
                if ( parent.frames[i].FCK ) parent.frames[i].FCK.UpdateLinkedField();
          **/
		for (i = 0; i < document.frames.length; ++i) {
			if (document.frames[i].FCK) {
				document.frames[i].FCK.UpdateLinkedField();
			}
		}
	};
}
var MyObject = new MyClass();
function reshPartDiv(url, div) {
	if (url.indexOf("?") != -1) {
		url = url + "&date=" + new Date().getTime();
	} else {
		url = url + "?date=" + new Date().getTime();
	}
	$.ajax({url:url, type:"post", success:function (data) {
		if (document.getElementById(div)) {
			document.getElementById(div).innerHTML = data;
			var regExp = /<script[^>]*>([\s\S]*?)<\/script>/gi;
			var matchArray = data.match(regExp);
			if (matchArray) {
				for (var i = 0; i < matchArray.length; i++) {
					var jsData = matchArray[i].toString();//其实用正则表达式的向前向后查找的话是很方便的，但javascript却不支持，所以只能采取此下策
					var regExp_temp = /<script[^>]*>([\s\S]*?)<\/script>/gi;
					var matchArray_temp = jsData.match(regExp_temp);
					var jsQueryString = "" + (RegExp.$1).trim() + "";
					eval(jsQueryString);//执行
				}
			}
		}
	}});
}
/**
 * 构件平台配置后台 切换菜单
 */
function menuSwitch(panelId, url) {
	$("#" + panelId).panel("refresh", url + "?date=" + new Date().getTime());
	$("#main").panel("refresh", "pages/default_main.jsp");
}
/**
 * 打开报表
 */
function openReport(reportId){
	var r_url = PROJECTNAME+'ReportServer?__showtoolbar__=false&__pi__=true&reportlet='+reportId+'.cpt&op=write';
    window.showModalDialog(r_url, null, "dialogWidth:1024px;dialogHeight:768px;status:no;help:no;resizable:yes;");
    //window.open(r_url);
}
/**
 *
 */
 function operUrlForWindow(url){
 	url=encodeURI(url);
 	window.location=url;
 }
