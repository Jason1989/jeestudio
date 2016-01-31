<%@page   contentType="text/html;charset=UTF-8"  language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
    String idMark = request.getSession().getAttribute("bak_url").toString();	
	if (request.getAttribute("themes") != null) {
		Cookie cookie2 = new Cookie("themes_"+idMark, request.getAttribute(
				"themes").toString());
		cookie2.setPath("/");
		//  cookie2.setDomain(host);
		cookie2.setMaxAge(33333333);
		response.addCookie(cookie2);
	}

	if (request.getAttribute("clientWidth") != null) {
		Cookie cookie2 = new Cookie("clientWidth", request.getAttribute(
				"clientWidth").toString());
		cookie2.setPath("/");
		cookie2.setMaxAge(33333333);
		response.addCookie(cookie2);
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<base href="<%=basePath%>">
    <title> ${systemName}</title>
    <meta http-equiv="X-UA-Compatible" content="IE=100">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <link rel="stylesheet" type="text/css" href="css/image.css">
	<link rel="stylesheet" type="text/css" href="css/auto_row.css" />
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css" href="css/indexPage.css">

	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/easyui.yl.css"/>
	<script type="text/javascript" src="jquery-easyui-1.1.2/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.1.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.1.2/jquery.easyui.extends.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.1.2/easyui-lang-zh_CN.js"></script>
	
	<script type="text/javascript" src="common/js/common-util/jquery_form.js"></script>
	<script type="text/javascript" src="common/js/common-util/ajax_security.js"></script>
	<script type="text/javascript" src="common/components/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="common/js/common-util/service-frame.js"></script>
	<script type="text/javascript" src="common/js/common-util/code-engine-constant.js"></script>
	<script type="text/javascript" src="common/js/common-util/engine-common-util.js"></script>
	<script type="text/javascript" src="common/js/common-util/upload.js"></script>
	<script type="text/javascript" src="js/page-ext.js"></script>
	
	<script type="text/javascript" src="common/js/version-easyui/engine-util-easyui.js"></script>
	<script type="text/javascript" src="common/js/version-easyui/engine-util-curing.js"></script>
	<script type="text/javascript" src="common/js/version-easyui/workflow-util.js"></script>
	<script type="text/javascript" src="js/XmlUtils.js"></script>
	<script type="text/javascript" src="js/json2.js"></script>
	<script type="text/javascript" src="common/js/fund-budget/fund-budget.js"></script>
	<script>
		function initToDoTaskWindow(){
			$('#wf_formconfig_todotask').window({
                title: "待办任务",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
          //    left:leftValue,
          //    top:topValue,s
                width: 600,
                height: 300,
                onClose:function(){
                	easyuiWinClose_workflow('wf_formconfig_todotask');
	            }
            });
		
		}
	
		$(function(){
		
			var selectedEnable = '${selectskinenable}';
			//设置是否可以更换皮肤
			if(selectedEnable == '0'){
				$('div[name="head_skin_div"]').each(function(){
				  	$(this).hide();
				});				
			}
			else
			{
				$('div[name="head_skin_div"]').each(function(){
				  	$(this).show();
				});	
			}
		
		   <c:if test="${systemMenuList[0].resType eq '6'}">
	   		   	$("#body.index").layout('panel','west').panel({width:0}).panel('close');//body.index是layout
				$("#body.index").layout('resize');
		   </c:if>
		   /**
		 	* 初始化保存的cookie
		 	*/
		
			$(".left_nav_col li").click(function(){
				$(".left_nav_colbg").removeClass("left_nav_colbg");
				$(this).addClass("left_nav_colbg");
			});
			initToDoTaskWindow();
		
			//top标签选中
			
		   $('body').layout();
	       $('body').css('visibility', 'visible');
	       
	       //换肤窗口初始化
           $("#zxtplat_change_skin").window({
                closed:true,
                width:600,
                height:400,
                modal:true,
                shadow: false,
                collapsible:false,
                draggable:false,
                minimizable:false,
                maximizable:false,
                cache:false,
                title:'更换皮肤',
                onClose:function(){
                    //when close event occour ,the following must get node by "zxtplat_change_skin" 
                	var options = $("#zxtplat_change_skin").window('options');
                	$("#zxtplat_change_skin").window('destroy',true);
                	var cw = null;
                	if(!$("#zxtplat_change_skin").get(0)){
                		cw = $("<div id='zxtplat_change_skin'></div>").appendTo('body').window(options);
                	}else{
                		cw = $('#zxtplat_change_skin').window(options);
                	}
                }
                
           });
           //换肤窗口弹出
	       $(".change_skin_zxt").click(function(){
	       		$("#zxtplat_change_skin").window('refresh','common/jsp/easyui/skin.jsp').window('open');
	       });
	       
	       //换肤实现
	       $(".change_skin_button").live('click',function(){
	           var theme = $("input[name=skin]:checked").val();	           
	           if("blue" == theme || "green" == theme || "dgreen"==theme|| "deepblue"==theme|| "yingji"==theme){
	           		if("${themes}" == theme){
			              $.messager.alert('提示','此主题已应用','info');
			              return;
	           		}
	           		window.location.href="<%=basePath%>compplatform/formengine/zsf_.action?sysName=${bak_url}&theme="+theme+"&cwidth="+$("body").width();
			   }else if ("desktop" == theme){
	           	    window.location.href="<%=basePath%>jquery-desktop/zsf_desktop.action?sysName=${bak_url}&theme="+theme+"#taskbar_setup";
	           }else {
	              $.messager.alert('提示','请选择主题','info');
	           }
	       });
	       
	       //禁用回退键
	       $('body').bind('keydown',function(event){
		           if(((event.keyCode == 8) && ((event.srcElement.type != "text" && event.srcElement.type != "textarea" && event.srcElement.type != "password") || event.srcElement.readOnly == true)) 
		           || ((event.ctrlKey) && ((event.keyCode == 78) || (event.keyCode == 82)) ) ||(event.keyCode == 116) ) {
		            window.event.keyCode = 0; 
		            window.event.returnValue = false; 
	           }
	       });
	       
		});
		function clearDom() {
			$("div.window-mask ~ div").remove();
		}
		function refreshLeftMenu(){
			$("#contents").panel("refresh");
		}
		function wfFormconfigToDoTask(){
			initToDoTaskWindow();
         	$("#wf_formconfig_todotask").window({'href':'workflow/DaibanWorkFlow!toToDoTaskList.action'});		
           	$("#wf_formconfig_todotask").window('open');
        }
        $(function(){
       		$(".head_t .head_t_msg").ajaxStart(function(){
		      //显示遮罩层
		      $("#overlay").show();
		    });
		    $(".head_t .head_t_msg").ajaxStop(function(event,request, settings){
		       //隐藏遮罩层
		       setTimeout(function(){
		        $("#overlay").hide();
		       },500);
		    });
        });
        
	</script>
  </head>
  <body class="easyui-layout index" style="visibility: hidden;">	
	<div region="center">
	     <div id="body" class="easyui-layout index" fit="true" border="false" >
		     <!-- top -->
		<!-- 角色选择界面 -->
		<!--左部的菜单-->	
		<div id="contents" region="west" border="false" split="true" title="菜单栏" icon="icon-menu" style="width:200px;padding1:1px;overflow: hidden;"  >
			<div  class="easyui-accordion" fit="true" border="false" style="background: url(jquery-easyui-1.1.2/themes/images/left_bg.jpg);overflow: auto;"   >
				 <!-- 加载第一个选项卡下的菜单 -->
				 
					<c:choose>
			     		<c:when test="${themes eq 'yingji'}">
			     			<s:iterator value="#request.tabMenuList" status="stauts" id="mlist">
				     			<div title="${title}"  headerCls="accordion-font-color"  icon="icon-accordion-node" >
				     				<table align="center">
				     					<c:if test="${row_num==2}">
						     				<s:iterator value="#mlist.tabList" status="tab">
						     					<c:if test="${status.index==0&&tab.index==0}">
						     						<script>
						     							clickToPanel('${attributes.url }','${id }','${attributes.isAbleWorkFlow }','${attributes.workflow_fiter }');
						     						</script>
						     					</c:if>
						     					<c:if test="${tab.index%2==0}">
					     							<tr>
							     				</c:if>
					     						<td align="center">
					     							<table>
					     								<tr>
					     									<td align="center"><img src="${imgsrc }" style="cursor:hand;vertical-align:middle;" onclick=clickToPanel('${attributes.url }','${id }','${attributes.isAbleWorkFlow }','${attributes.workflow_fiter }') height="50px" width="50px" /></td>
					     								</tr>
					     								<tr>
					     									<td align="center">${text }</td>
					     								</tr>
					     							</table>
					     						</td>
							     				<c:if test="${tab.index%2!=0}">
							     						<%out.write("</tr>"); %>
							     				</c:if>
							     				<s:if test="#mlist.tabList.size()==1">
							     						<%out.write("</tr>"); %>
							     				</s:if>
											</s:iterator>
				     					</c:if>
				     					<c:if test="${row_num==1||row_num==null||row_num==''}">
						     				<s:iterator value="#mlist.tabList" status="tab">
						     					<tr>
						     						<td align="center">
						     							<table>
						     								<tr>
						     									<td align="center"><img src="${imgsrc }" style="cursor:hand;vertical-align:middle;" onclick=clickToPanel('${attributes.url }','${id }','${attributes.isAbleWorkFlow }','${attributes.workflow_fiter }')  height="50px" width="50px" /></td>
						     								</tr>
						     								<tr>
						     									<td align="center">${text }</td>
						     								</tr>
						     							</table>
						     						</td>
						     					</tr>
											</s:iterator>
				     					</c:if>
									</table>
								</div>
							</s:iterator>
			     		</c:when>
			     		<c:otherwise>
			     			<s:iterator value="#request.tabMenuList" status="stauts" id="mlist">
				     			<div title="${title}"  headerCls="accordion-font-color"  icon="icon-accordion-node" >
									<ul id="frame_menu_tree_${stauts.index}" style="padding:10px;"></ul>
									<script type="text/javascript">
										createMenuTree('${json}','frame_menu_tree_${stauts.index}');
									</script>
								</div>
							</s:iterator>
			     		</c:otherwise>
			     	</c:choose>
				
			</div>
		</div>
		
		<!-- 右部主框架 -->
		<div id="main" region="center" border="false"  style="overflow:hidden;">		
		</div>
		 </div>
	 </div>
	 <div region="east" border="false"  style="width:100px;opacity:0;-ms-filter:'alpha(opacity=0)';filter: alpha(opacity=0);zoom: 1;">
		
	 </div>
	 <div region="west"  border="false"  style="width:100px;opacity:0;-ms-filter:'alpha(opacity=0)';filter: alpha(opacity=0);zoom: 1;">

	 </div>
	 <jsp:include page="/common/jsp/easyui/frame_top.jsp"></jsp:include>
	 <div region="south" border="false"  style="height:88px;background: none;">
		<span class="bottom" style="margin-top:10px;">
				<div class="bottom_font" align="center" style="margin-top:63px;">&copy; 版权所有：${systemName}</div>
			</span>
	 </div>
		<!-- 待办的窗口 -->
		<div id="wf_formconfig_todotask" ></div>
    	<!-- 待办的详情 -->
    	<div id="viewTaskNode" title="工作项详情" style="width: 700px;height: 450px;" class="easyui-window" closed=true    modal=true ></div>
		<!--  
               resizable: false,
               minimizable: false,
               maximizable: false,
               shadow: false,
               closed: true,
              });
		-->
		<!-- 加载第一个菜单的页面 -->
			<c:forEach items='${systemMenuList}' var='menu' varStatus='ss'>
			<c:choose>
				<%-- 如果第一个菜单对应的页面，为扩展页面时 --%>
			    <c:when test="${ss.first}">
			    	<c:choose>
			    		<c:when test="${menu.resType eq '6'}">
				    	  <script type="text/javascript">
						        $(function(){
							        var treeNodeUrl='${menu.url}';
									if(treeNodeUrl.trim().length!=0){
										if(treeNodeUrl.indexOf(".")>0){
											if(treeNodeUrl.indexOf("?")>0){
												$('#main').panel('refresh',treeNodeUrl+ "&date=" + new Date().getTime());
											}else{
												$('#main').panel('refresh',treeNodeUrl+ "?date=" + new Date().getTime());
											}
										}
									}
						        });
						  </script>
			    		</c:when>
			    		<c:otherwise>
			    			<!-- 加载第一个菜单的页面 -->
							<s:iterator value="#request.tabMenuList" status="stauts"   >
								<script type="text/javascript">
											$(function(){
											   if('${stauts.index}'==0){
											      	if('${json}'.trim().length!= 0){
														var test_obj=eval('${json}');
														var abc=test_obj[0];
														var treeNodeUrl=abc.attributes.url;
														if(treeNodeUrl.trim().length!=0){
															if(treeNodeUrl.indexOf(".")>0){
																if(treeNodeUrl.indexOf("?")>0){
																	$('#main').panel('refresh',treeNodeUrl+ "&date=" + new Date().getTime());
																}else{
																	$('#main').panel('refresh',treeNodeUrl+ "?date=" + new Date().getTime());
																}
															}else{
																$('#main').panel('refresh','formengine/listPageAction.action?isAbleWorkFlow='+abc.attributes.isAbleWorkFlow+'&workflow_fiter='+abc.attributes.workflow_fiter+'&formId='+treeNodeUrl+ "&date=" + new Date().getTime()+'&menuId='+abc.id);
															}
														}
												     }
												  }
								   		 	});
									</script>
							</s:iterator>
			    		</c:otherwise>
			    	</c:choose>
			    </c:when>
			</c:choose>
		</c:forEach>
    <!-- 皮肤 -->
    <div id="zxtplat_change_skin">
    </div>
    <!-- 有ajax请求的时候，产生遮罩层，方式用户操作 -->
    <div id="overlay" class="overlay" style="display: none; z-index: 9998; filter: alpha(opacity=0); left: 0; width: 100%; position: fixed; _position: absolute; top: 0; height: 100%; background-color: #000; moz-opacity: 0; opacity: 0;"></div>
	</body>
</html>
