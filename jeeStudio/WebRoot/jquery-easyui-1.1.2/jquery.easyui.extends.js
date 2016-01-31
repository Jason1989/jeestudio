
/*
 *  针对query easyUI的扩展
 *  author:bozch 
 *  date:2011-10-26
 */
/**
    * 打开窗口的简化
	**/
function easyuiWinNew(settings, level, tools) {
	if (!level) {
		level = 1;
	}
	var defaultSetting = {title:"new Title", modal:true, resizable:false, minimizable:false, maximizable:false, collapsible:false, shadow:false, closed:true, width:675, height:560, onClose:function () {
		var options = $("#easyuiwin" + level).window("options");
		$("#easyuiwin" + level).window("destroy", true);
		if (!$("#easyuiwin" + level).get(0)) {
			$("<div id='easyuiwin" + level + "'></div>").appendTo("body").window(options);
		} else {
			$("#easyuiwin" + level).window(options);
		}
                   //将相应的保存的信息删除
	}};
	if (tools) {
		$.extend(defaultSetting, {tools:[{iconCls:"icon-goback", handler:function () {
			easyuiWinBack(level);
		}}, {iconCls:"icon-goforward", handler:function () {
			easyuiWinPre(level);
		}}, {iconCls:"icon-history", handler:function (event) {
			var his_menu = $("<div style='width:150px;'></div>");
			var his_setting = $("#easyuiwin" + level).data("easyuiWinPageSetting");
			for (var i = 0; i < his_setting.length; i++) {
				his_menu.append("<div onclick='easyui_win_his_menu_click(\"" + his_setting[i].title + "\",\"" + his_setting[i].href + "\",\"" + level + "\")'>" + his_setting[i].title + "</div>");
			}
						  
						  //展示历史记录
			his_menu.menu({}).menu("show", {left:event.pageX, top:event.pageY});
		}}]});
	}
	$.extend(defaultSetting, settings);
	$("#easyuiwin" + level).remove();
	$("body").append("<div id=\"easyuiwin" + level + "\"></div>");
	$("#easyuiwin" + level).window(defaultSetting).window("open");
   //存储第一次打开窗口的信息
	$("#easyuiwin" + level).data("easyuiWinPageSetting", new Array($.extend(settings, {page:1})));
   //设置当前页
	$("#easyuiwin" + level).data("easyuiWinCurrentPage", 1);
}
function easyui_win_his_menu_click(title, href, level) {
	easyuiWinChange({title:title, href:href}, level);
}
/**
  * 跳转到某个页面，其中setting是
  */
function easyuiWinGo(setting, level) {
	if (!level) {
		level = 1;
	}
	easyuiWinChange(setting, level);
//存储页面跳转信息
	//设置当前页
	var currentPage = $("#easyuiwin" + level).data("easyuiWinCurrentPage") + 1;
	$("#easyuiwin" + level).data("easyuiWinCurrentPage", currentPage);
	//保存配置
	var beforeArray = $("#easyuiwin" + level).data("easyuiWinPageSetting");
	beforeArray.push($.extend(setting, {page:currentPage}));
	$("#easyuiwin" + level).data("easyuiWinPageSetting", beforeArray);
}


//窗口关闭
function easyuiWinClose(level) {
	if (!level) {
		level = 1;
	}
	$("#easyuiwin" + level).window("close");
}


//后退
function easyuiWinBack(level) {
	var currentPgae = $("#easyuiwin" + level).data("easyuiWinCurrentPage");
	var beforeArray = $("#easyuiwin" + level).data("easyuiWinPageSetting");
	//alert("currentPgae:"+currentPgae+"  beforeArray-length:"+beforeArray.length);
	if ((currentPgae - 1) >= 1) {
		for (var i = 0; i < beforeArray.length; i++) {
			var beforePage = beforeArray[i];
			if (beforePage.page == (currentPgae - 1)) {
			    //设置当前页
				$("#easyuiwin" + level).data("easyuiWinCurrentPage", (currentPgae - 1));
				//页面跳转
				easyuiWinChange(beforePage, level);
			}
		}
	}
}
//前进
function easyuiWinPre(level) {
	var currentPgae = $("#easyuiwin" + level).data("easyuiWinCurrentPage");
	var beforeArray = $("#easyuiwin" + level).data("easyuiWinPageSetting");
	//alert("currentPgae:"+currentPgae+"  beforeArray-length:"+beforeArray.length);
	if ((currentPgae + 1) <= beforeArray.length) {
		for (var i = 0; i < beforeArray.length; i++) {
			var beforePage = beforeArray[i];
			if (beforePage.page == (currentPgae + 1)) {
			    //设置当前页
				$("#easyuiwin" + level).data("easyuiWinCurrentPage", (currentPgae + 1));
				//页面跳转
				easyuiWinChange(beforePage, level);
			}
		}
	}
}
/**
  * 根据配置执行页面跳转
  * 纯粹的执行页面的跳转
  */
function easyuiWinChange(setting, level) {
	//改变标题
	if (setting.title) {
		$("#easyuiwin" + level).window("setTitle", setting.title);
	}
	//改变大小
	if (setting.width && setting.height) {
		$("#easyuiwin" + level).window("resize", {width:setting.width, height:setting.height});
	}
	//改变页面url
	if (setting.href) {
		$("#easyuiwin" + level).window("refresh", setting.href);
	}
}

function easyuiWinRefresh(level){
   $("#easyuiwin" + level).window("refresh");
}

