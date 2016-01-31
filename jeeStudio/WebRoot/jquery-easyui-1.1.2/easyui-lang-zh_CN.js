if($.fn.panel){
   $.fn.panel.defaults.loadingMessage = '加载中……';
}
if ($.fn.pagination){
	$.fn.pagination.defaults.beforePageText = '第';
	$.fn.pagination.defaults.afterPageText = '共{pages}页';
	$.fn.pagination.defaults.displayMsg = '显示{from}至{to}条，共{total}条记录';
}
if ($.fn.datagrid){
	$.fn.datagrid.defaults.loadMsg = '正在处理，请稍待。。。';
}
if ($.messager){
	$.messager.defaults.ok = '确定';
	$.messager.defaults.cancel = '取消';
}
if ($.fn.validatebox){
	$.fn.validatebox.defaults.missingMessage = '该输入项为必填项';
	$.fn.validatebox.defaults.rules.email.message = '请输入有效的电子邮件地址';
	$.fn.validatebox.defaults.rules.url.message = '请输入有效的URL地址';
	$.fn.validatebox.defaults.rules.length.message = '输入内容长度必须介于{0}和{1}之间';
	$.fn.validatebox.defaults.rules.compDate.message = '日期不合理';
	//扩展验证
	$.fn.validatebox.defaults.rules.sign.message = '请输入汉字字母数字下划线等有效字符';
	$.fn.validatebox.defaults.rules.mobile.message = '请输入有效的手机号码';
	$.fn.validatebox.defaults.rules.idcard.message = '请输入18位有效的身份证';
	$.fn.validatebox.defaults.rules.phone.message = '请输入正确的电话号或传真号';
	$.fn.validatebox.defaults.rules.phone_mobile.message = '请输入正确的电话号';
	$.fn.validatebox.defaults.rules.price.message = '请输入正确的价格';
	$.fn.validatebox.defaults.rules.positive_Integer.message = '请输入正整数';
	$.fn.validatebox.defaults.rules.age.message = '请输入正确的年龄';
	$.fn.validatebox.defaults.rules.zipcode.message = '请输入正确的邮政编码';
	$.fn.validatebox.defaults.rules.number.message = '请您输入正整数或最多输入6位小数';
	$.fn.validatebox.defaults.rules.jingduxj.message = '请输入有效范围值（88.4 到 90.0度）';
	$.fn.validatebox.defaults.rules.weiduxj.message = '请输入有效范围值（41.3 到 43.7度）';
}
if ($.fn.numberbox){
	$.fn.numberbox.defaults.missingMessage = '该输入项为必填项';
}
if ($.fn.combobox){
	$.fn.combobox.defaults.missingMessage = '该输入项为必填项';
}
if ($.fn.combotree){
	$.fn.combotree.defaults.missingMessage = '该输入项为必填项';
}
if ($.fn.calendar){
	$.fn.calendar.defaults.weeks = ['日','一','二','三','四','五','六'];
	$.fn.calendar.defaults.months = ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'];
}
if ($.fn.datebox){
	$.fn.datebox.defaults.currentText = '今天';
	$.fn.datebox.defaults.clearText = '清空';
	$.fn.datebox.defaults.closeText = '关闭';
	$.fn.datebox.defaults.missingMessage = '该输入项为必填项';
}
