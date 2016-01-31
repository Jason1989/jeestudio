<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>数字环保中心</title>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <link rel="stylesheet" type="text/css" href="css/image.css">
	<link rel="stylesheet" type="text/css" href="css/auto_row.css" />
	<link rel="stylesheet" type="text/css" href="css/style.css">

	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/particular.css"/>
	
	<script type="text/javascript" src="jquery-easyui-1.1.2/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.1.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.1.2/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="common/components/My97DatePicker3.0.1/My97DatePicker/WdatePicker.js"></script>
	

	<script type="text/javascript" src="js/engine-util-easyui.js"></script>
	<script type="text/javascript" src="js/engine_frame.js"></script>
	<script type="text/javascript" src="js/service-frame.js"></script>
	
	
	<script type="text/javascript" src="js/engine-common-util.js"></script>
	<script type="text/javascript" src="js/code-engine-constant.js"></script>
	
	<script type="text/javascript" src="jquery-easyui-1.1.2/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="js/XmlUtils.js"></script>
	<script type="text/javascript" src="js/jquery_form.js"></script>
	
	
	
	<script>
		$(function(){
			 $('#test_table').datagrid({
                iconCls: 'icon-save',
                height: 400,
                fit:true,
                nowrap: false,
                striped: true,
                autoFit: true,
                url: 'datatable/dataTable!list.action?groupId=1',
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'id',
                fitColumns:true,
                pageSize:20,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'name',title: '名称',width: 10,sortable: true,align:'left'},
                	{field: 'dataSource',title: '数据源',width: 10,sortable: true,align:'left'},
                	{field: 'type',title: '对象类型',width: 10,align:'center'},
                	{field: 'owner',title: '所有者',width: 10,align:'center'},
                   	{field: 'locked',title: '是否锁定',width: 10,align:'center'},
                   	{field: 'inused',title: '是否可用',width: 10,align:'center'},                	
                	{field: 'state',title: '状态',width: 10,align:'center'},
                	{field: 'operate',title: '操作',width: 10,align:'center'}
				]],
                pagination: true,
                rownumbers: true,
                //singleSelect: true,
                toolbar: ['-', 
		  			{text: '添加',iconCls: 'icon-add'},
		  			'-', 
		  			{text: '删除',iconCls: 'icon-remove'}, 
		  			'-', 
		  			{text: '检查',iconCls: 'icon-search'}, 
		  			'-' ]
            });
			//top标签选中
			
		   $('body').layout();
	       $('body').css('visibility', 'visible');
		});
		
	</script>
  </head>
<body class="easyui-layout" style="visibility: hidden;">	
		<!-- 头部 -->
		<div region="north" border="false"  split="false" style="height:83px;padding:0;margin:0;">
		    <div class="head_bg">
				<div class="head_logo">
					<div class="head_t">
						<div class="head_t_msg" style="width: 150px;">欢迎您，${userName}！</div>
						<div class="head_t_if">
							<div class="head_t_img"><img src="jquery-easyui-1.1.2/ui/images/head_ico.jpg" width="12" height="11" /></div>
							<div class="head_t_font"><a href="${pageContext.request.contextPath}/formengine/loginAction!logout.action">注销</a></div>
							<div class="head_t_img"><img src="jquery-easyui-1.1.2/ui/images/head_ico1.jpg" width="14" height="11" /></div>
							<div class="head_t_font"><a href="javascript:;">皮肤</a></div>
						</div>
					</div>
				</div>
				<div class="head_right">
						<div class="head_btn5"  align="center"><a href="javascript:wfFormconfigToDoTask();"></a><span>我的任务</span></div>
					<s:iterator value="#request.systemMenuList" status="stauts"   >
						<div class="head_btn${stauts.index%4+1}" align="center"><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menuId}','contents','frameAccordionMenu');"></a><span>${title}</span></div>
					</s:iterator>
					<!--  
						<div class="head_btn1"><a href="javascript:;"></a></div>
						<div class="head_btn2"><a href="javascript:;"></a></div>
						<div class="head_btn3"><a href="javascript:;"></a></div>
						<div class="head_btn4"><a href="javascript:;"></a></div>
					-->
				</div>
			</div>
		</div>
		<!-- 底部 -->
		<div region="south" border="false" split="true" style="height:30px;padding:0px;background:#f5f5f5;">
			<div class="bottom">
				<div class="bottom_font" align="center">&copy; 1998-2010  版权所有  Copyright&copy;2010 All Right Reserved</div>
			</div>
		</div>
		<!--左部的菜单-->	
		<div id="contents" region="west" border="true" split="true" title="菜单栏" icon="icon-menu" style="width:200px;padding1:1px;"  >
			<div  class="easyui-accordion" fit="true" border="false" style="background: url(jquery-easyui-1.1.2/ui/images/left_bg.jpg)"   >
				<!--   
					<div title="业务功能菜单1" headerCls="accordion-font-color" icon="icon-accordion-node"  selected="true" style="overflow:auto;padding:5px;">
						<ul class="left_nav_col">
						  <li>
							  <div class="left_nav_colimg"><img src="jquery-easyui-1.1.2/ui/images/nav_ico_1.gif" width="42" height="34" /></div>
							  <div class="left_nav_colfont"><a href="javascript:void(0);">数据源文件</a></div>
						  </li>
						  <li class="left_nav_colbg">
							  <div class="left_nav_colimg"><img src="jquery-easyui-1.1.2/ui/images/nav_ico_2.gif" width="32" height="33"/></div>
							  <div class="left_nav_colfont"><a href="javascript:void(0);">数据源文件</a></div>
						  </li>
						</ul>
					</div>
				 -->
				<s:iterator value="#request.tabMenuList" status="stauts"   >
					<div title="${title}"  headerCls="accordion-font-color"  icon="icon-accordion-node" >
						<ul id="frame_menu_tree_${stauts.index}" style="padding:10px;"></ul>
						<script type="text/javascript">
							createMenuTree('${json}','frame_menu_tree_${stauts.index}');
						</script>
					</div>
				</s:iterator>
			</div>
		</div>
		
		<!-- 右部主框架 -->
		<div id="main" region="center" border="true"  style="overflow:hidden;">		
		     <table id="test_table"></table>
		</div>
		<!-- 待办的窗口 -->
			<div id="wf_formconfig_todotask" class="easyui-window" ></div>
    	<!-- 待办的详情 -->
  </body>

</html>
