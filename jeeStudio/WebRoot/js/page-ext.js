/*
 * eaysui验证 方法写入
 * 2011-2-9
 */

//校验
//这是对easyui-validatebox的扩展使用方法：
//1.对form表单的验证： 在相应的标签上加上validType="chn"，’chn'在下面的有定义，你可以换成别的相应的。 当form表单提交时会自动给你验证 如果不符合标准 他会不能提交并且有相应的提示
//2.对某个或某几个标签的验证，像AJAX的验证：可以在AJAX方法里面 获取该标签，然后对枪单独验证，例如对ID为'def'的标签进行验证：
//$('#dfe').validatebox('isValid')，这个表达式能返回一个boolean： true表示该标签的值满足要求，相反不满足，
//你可以判断下 当是true的时候在提交 或者false时不提交，页面也会给出相应的不满足条件的提示
//下面是 几个简单常用的验证，你可以自己添加其他的
$.extend($.fn.validatebox.defaults.rules, { 
	 
     chn: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '请输入汉字'
    },
    textContentLength: {
    	validator: function (value, param) {    	
    		return value.length <= param[0] ;
	    },
	    message:'您所输入的内容不能超过 {0} 个字符'
    },
    entityCode: {
        validator: function (value, param) {
            return (/^\w+$/.test(value))&&(value.length >= param[0])&&(value.length <= param[1]);
        },
        message: '只允许英文字母、数字及下划线。最小长度为 {0}，最大长度为 {1}。'
    },
    //只能输入非0开头的数字
    sort: {
        validator: function (value, param) {
            return /^([1-9][0-9]*)$/.test(value);
        },
        message: '请输入非负整数'
    },
     sortlength: {
         validator: function (value, param) {
            return /^([1-9][0-9]*)$/.test(value)&&(value.length >= param[0])&&(value.length <= param[1]);
        },
        message: '请输入非负整数，至少 {0} 位，最长 {1} 位。'
    },
    textlenght: {
        validator: function (value, param) {
            return /^.{1,50}$/.test(value);
        },
        message: '请输入小于50字'
    },
    textarealenght: {
        validator: function (value, param) {
            return /^.{1,1000}$/.test(value);
        },
        message: '请输入小于1000字'
    },
    //只能输入1-2位小数的正实数
    h: {
        validator: function (value, param) {
            return /^[0-9]+(.[0-9]{1,2})?$/.test(value);
        },
        message: '请您输入正整数或最多输入2位小数'
    },
    shenhe: {
        validator: function (value, param) {
            return /^[0-9]+(.[0-9]{1,2})?$/.test(value);
        },
        message: '请您输入正整数或最多输入2位小数'
    },
    //只能输入小数的正实数
    xiaoshu: {
        validator: function (value, param) {
            return /^[1-9]+(.[0-9]{1,10})?$/.test(value);
        },
        message: '请您输入小数'
    },
     dian: {
        validator: function (value, param) {
            return /^[0-9]+(.[0-9]{1,10})?$/.test(value);
        },
        message: '请输入有效数字或小数'
    },
     money: {
        validator: function (value, param) {
            return /^[0-9]+(.[0-9]{1,2})?$/.test(value);
        },
        message: '请最多输入2位小数'
    },
    zip: {
        validator: function (value, param) {
            return /^[1-9]\d{5}$/.test(value);
        },
        message: '邮政编码不存在'
    },
      IP: {
        validator: function (value, param) {
            return /((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)/.test(value);
        },
        message: '服务器地址格式不正确'
    },
    QQ: {
        validator: function (value, param) {
            return /^[1-9]\d{4,10}$/.test(value);
        },
        message: 'QQ号码不正确'
    },
    mobile: {
        validator: function (value, param) {
            return /^((\(\d{2,3}\))|(\d{3}\-))?13\d{9}$/.test(value);
        },
        message: '手机号码不正确'
    },
    loginName: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5\w]+$/.test(value);
        },
        message: '不允许输入特殊符号。'
    },
    
    loginName1: {
        validator: function (value, param) {
            return /^%&',;=?$\" 等字符：[^%&',;=?$\x22]+/;
        },
        message: '不允许输入特殊符号11111111。'
    },
    safepass: {
        validator: function (value, param) {
            return safePassword(value);
        },
        message: '密码由字母和数字组成，至少6位'
    },
    number: {
        validator: function (value, param) {
        
            return /^\-??\d+\.??\d+$/.test(value);
        },
        message: '请输入数字'
    },
    numberlength: {
         validator: function (value, param) {
            return /^\d+$/.test(value)&&(value.length >= param[0])&&(value.length <= param[1]);
        },
        message: '请输入数字，至少 {0} 位,最长 {1} 位.'
    },
    dataSourceChecked: {
        validator: function (value, param) {
            return (/^\w+$/.test(value))&&(value.length >= param[0])&&(value.length <= param[1]);
        },
        message: '只允许英文字母、数字及下划线。最小长度为 {0},最大长度为 {1}.'
    },
    serverAdressLength: {
    	validator: function (value, param) {    	
    		return value.length <= param[0] ;
	    },
	    message:'您所输入的内容不能超过 {0} 个字符'
    },
    idcard: {
        validator: function (value, param) {
            return idCard(value);
        },
        message:'请输入正确的身份证号码'
    },
    riverLength:{
    	validator: function (value, param) {
            return value != 0;
        },
        message:'请输入河流长度'
    }
});

$.extend($.fn.numberbox.defaults.rules, {
	jingdu: {
        validator: function (value, param) {
            return /^(?:(?:\d|[1-9]\d|1[0-7]\d)(\.\d{1,6})?|180.0000|180)$/.test(value);
            
        },
        message: '请输入有效范围值（0-180度）'
    },
    weidu: {
        validator: function (value, param) {
            return /^(?:(?:\d|[1-8]\d)(\.\d{1,6})?|90.0000|90)$/.test(value);
        },
         message: '请输入有效范围值（0-90度）'
    }
 });
 //新疆 吐鲁番地区经纬度控制
 $.extend($.fn.numberbox.defaults.rules, {
	jingduxj: {
        validator: function (value, param) {
            return   /^(?:88\.[4-9]\d{0,5}|89\.\d{1,6}|90.0{1,6})$/.test(value);
        },
        message: '请输入有效范围值（88.4 到 90.0度）'
    },
    weiduxj: {
        validator: function (value, param) {
            return   /^(?:41\.[3-9]\d{0,5}|42\.\d{1,6}|43\.[0-7]\d{0,5})$/.test(value);
        },
        message: '请输入有效范围值（41.3 到 43.7度）'
    }
 });
/* 密码由字母和数字组成，至少6位 */
var safePassword = function (value) {
    return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));
}
/*身份证号码*/
var idCard = function (value) {
    if (value.length == 18 && 18 != value.length) return false;
    var number = value.toLowerCase();
    var d, sum = 0, v = '10x98765432', w = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
    var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
    if (re == null || a.indexOf(re[1]) < 0) return false;
    if (re[2].length == 9) {
        number = number.substr(0, 6) + '19' + number.substr(6);
        d = ['19' + re[4], re[5], re[6]].join('-');
    } else d = [re[9], re[10], re[11]].join('-');
    if (!isDateTime.call(d, 'yyyy-MM-dd')) return false;
    for (var i = 0; i < 17; i++) sum += number.charAt(i) * w[i];
    return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
}

var isDateTime = function (format, reObj) {
    format = format || 'yyyy-MM-dd';
    var input = this, o = {}, d = new Date();
    var f1 = format.split(/[^a-z]+/gi), f2 = input.split(/\D+/g), f3 = format.split(/[a-z]+/gi), f4 = input.split(/\d+/g);
    var len = f1.length, len1 = f3.length;
    if (len != f2.length || len1 != f4.length) return false;
    for (var i = 0; i < len1; i++) if (f3[i] != f4[i]) return false;
    for (var i = 0; i < len; i++) o[f1[i]] = f2[i];
    o.yyyy = s(o.yyyy, o.yy, d.getFullYear(), 9999, 4);
    o.MM = s(o.MM, o.M, d.getMonth() + 1, 12);
    o.dd = s(o.dd, o.d, d.getDate(), 31);
    o.hh = s(o.hh, o.h, d.getHours(), 24);
    o.mm = s(o.mm, o.m, d.getMinutes());
    o.ss = s(o.ss, o.s, d.getSeconds());
    o.ms = s(o.ms, o.ms, d.getMilliseconds(), 999, 3);
    if (o.yyyy + o.MM + o.dd + o.hh + o.mm + o.ss + o.ms < 0) return false;
    if (o.yyyy < 100) o.yyyy += (o.yyyy > 30 ? 1900 : 2000);
    d = new Date(o.yyyy, o.MM - 1, o.dd, o.hh, o.mm, o.ss, o.ms);
    var reVal = d.getFullYear() == o.yyyy && d.getMonth() + 1 == o.MM && d.getDate() == o.dd && d.getHours() == o.hh && d.getMinutes() == o.mm && d.getSeconds() == o.ss && d.getMilliseconds() == o.ms;
    return reVal && reObj ? d : reVal;
    function s(s1, s2, s3, s4, s5) {
        s4 = s4 || 60, s5 = s5 || 2;
        var reVal = s3;
        if (s1 != undefined && s1 != '' || !isNaN(s1)) reVal = s1 * 1;
        if (s2 != undefined && s2 != '' && !isNaN(s2)) reVal = s2 * 1;
        return (reVal == s1 && s1.length != s5 || reVal > s4) ? -10000 : reVal;
    }
};
