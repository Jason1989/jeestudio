<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    
    <title>My JSP 'datepicker.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>

  </head>
  
  <body>
   <input class="Wdate" type="text" onClick="WdatePicker()"> <font color=red>&lt;- 点我弹出日期控件</font>
<br> IE Opera Safari浏览器可以直接双击打开看演示
<br><font color=red><b>注意:如果您使用Firefox或Chrome浏览器,请使用 localhost/虚拟目录/ 这样的地址打开,不要直接双击文件</b></font>

<br><br><br><br>
更多demo请访问官方主页 <a href="http://www.my97.net">http://www.my97.net</a>

<br><br>
<h1>请务必仔细阅读下面的文字</h1><br>
<pre>
注意:此版本为 4.7 Release build 20110113

更新内容:
[新增]输入智能调整,在输入日期时,会根据日期格式自动调整
[新增]默认显示快速选择的选项
[新增]周数算法选择
[新增]全键盘操作(键盘控制开关),不用鼠标也可以选择日期
[新增]dateFmt可以直接使用%y %M %d %ld等内置常量
[新增]isShowOK属性,可以隐藏确定按钮
[新增]当使用onfocus触发时,焦点在日期控件时,也会弹出日期控件
[新增]autoUpdateOnChanged属性:在修改年月日时分秒等元素时是否自动更新到el
[修改]源文件的编码问题
[修正]在双月模式时,可以隐藏其他月份的日期,并且自动对齐
[修正]{M:+1},逢2月或大小月会有些误差
[修正]掩码输入功能的一个bug



使用方法:

1. 去官方网站看看,你当前下载的是否是最新的版本,很多bug都是因为使用的不是最新版本造成的
官方主页:<a href="http://www.my97.net" target="_blank">http://www.my97.net</a>


2. 将My97DatePicker整个目录包,放入您的项目的相应目录下

My97DatePicker目录下各文件的作用:
  1.1 My97DatePicker目录是一个整体,不可破坏里面的目录结构,也不可对里面的文件改名,可以改目录名
  1.2 My97DatePicker.htm是必须文件,不可删除
  1.3 各目录及文件的用途:
    WdatePicker.js 配置文件,在调用的地方仅需使用该文件,可多个共存,以xx_WdatePicker.js方式命名
    config.js 语言和皮肤配置文件,无需引入
    calendar.js 日期库主文件,无需引入
    My97DatePicker.htm 临时页面文件,不可删除
    目录lang 存放语言文件,你可以根据需要清理或添加语言文件
    目录skin 存放皮肤的相关文件,你可以根据需要清理或添加皮肤文件包


3. 您可以根据您自己的需要,删除不必要的皮肤和语言文件


4. 您可以根据您自己的需要,添加新的皮肤包
皮肤中心地址:<a href="http://www.my97.net/dp/skin.asp" target="_blank">http://www.my97.net/dp/skin.asp</a>


5. 详细阅读在线演示和使用说明,大部分问题都可以通过这里解决,请细看
在线演示:<a href="http://www.my97.net/dp/demo/" target="_blank">http://www.my97.net/dp/demo/</a>


6. 如果遇到无法解决的问题
请先参考:<a href="http://www.my97.net/dp/support.asp" target="_blank">http://www.my97.net/dp/support.asp</a>


7. 如果遇到问题,而技术支持页面无法解决的
您可以通过技术支持页面中提供的联系方式联系我,注意:问问题时,一定要附上相关的HTML代码和详细的错误信息


8. 您有什么意见或建议,你可以通过技术支持页面中提供的联系方式联系我


9. 如果您对日期控件的许可协议有兴趣,您可以访问:http://www.my97.net/dp/sponsor.asp


10.最后祝大家项目顺利,月月加薪!

---------------------------------------------------------------------
官方主页
<a href="http://www.my97.net" target="_blank">http://www.my97.net</a>

在线演示和使用说明
<a href="http://www.my97.net/dp/demo/" target="_blank">http://www.my97.net/dp/demo/</a>

皮肤中心:
<a href="http://www.my97.net/dp/skin.asp" target="_blank">http://www.my97.net/dp/skin.asp</a>

技术支持页面
<a href="http://www.my97.net/dp/support.asp" target="_blank">http://www.my97.net/dp/support.asp</a>
博客
<a href="http://my97.cnblogs.com" target="_blank">http://my97.cnblogs.com</a>
<a href="http://blog.csdn.net/my97/" target="_blank">http://blog.csdn.net/my97/</a>
</pre>

  </body>
</html>