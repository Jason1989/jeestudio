<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="com.zxt.compplatform.formengine.util.PropertiesUtil"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	String basePathFrame_top = PropertiesUtil.findSystemPath(request);
 %>
		<script type="text/javascript">
			function head_uu(){
				document.getElementById("head_uu").className="no";
			}
			function head_cc(){
				document.getElementById("head_uu").className="head_ro";
			}
			
		
			
		</script>
		<script>
			$(function(){
	           	 $('#updateUserMessage').appendTo("body").window({
	                modal: true,
	                resizable: false,
	                minimizable: false,
	                maximizable: false,
	                shadow: false,
	                closed: true,
	                width: 600,
	                height: 305
	            });           
			  });    
			function orgUserUpdateLimsBh(){
		         var url = 'organization/organization!toupdateOrgUser.action?userId=${sessionScope.userId}&oldPwd=true';
	      		$("#updateUserMessage").window({'href':''});
				$("#updateUserMessage").window('refresh');
				$("#updateUserMessage").window({'href':url});				
				$("#updateUserMessage").window('open');
		    }
		    setTimeout(function(){
				$(".head_btn1 a").click();
			},100);
		</script>
<!-- 头部 -->
<c:choose>
	<c:when test="${themes eq 'blue'}">
        <div region="north" border="false"  split="false" style="height:69px;padding:0;margin:0;overflow:hidden;">
        	<div class="easyui-panel" fit="true" border="false">
				<div class="head_bg">
					<div class="head_logo">
						<div class="head_t_0">
							<img src="jquery-easyui-1.1.2/themes/images/deepblue/jee_logo_subSystem.png" />
						</div>
						<div class="head_t_2">
							<font color="white" face="黑体" size="5">${systemName}</font><br/>
							<div class="head_t_msg">欢迎您，${uName}-${userLevel}!</div>
							<div class="head_t_if">
								<div class="head_t_img"><img src="jquery-easyui-1.1.2/themes/images/blue/head_ico.jpg" width="12" height="11" /></div>
								<div class="head_t_font"><a href="<%= basePathFrame_top%>/zsf_logout.action?bak_url=${bak_url}" style="text-decoration: none;">注销</a></div>
								<div name="head_skin_div" class="head_t_img"><img src="jquery-easyui-1.1.2/themes/images/blue/head_ico1.jpg" width="14" height="11" /></div>
								<div name="head_skin_div" class="head_t_font change_skin_zxt"><a href="javascript:void(0);" style="text-decoration: none;">皮肤</a></div>
							</div>
						</div>
					</div>
					<div class="head_right">
						<s:iterator value="#request.systemMenuList" status="stauts"   >
							<!-- ${imageUrl} --><div class="head_btn5" align="center"><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menuId}','contents','frameAccordionMenu','${menu.resType}');"></a><span>${title}</span></div>
						</s:iterator>
						<div class="head_btn5"  align="center"><a onfocus="this.blur();" href="javascript:wfFormconfigToDoTask();"></a><span>我的任务</span></div>
					</div>
				</div>
		   </div>
		</div>
			
		<script>
		   $(function(){
				$(".left_nav_col li").click(function(){
					$(".left_nav_colbg").removeClass("left_nav_colbg");
					$(this).addClass("left_nav_colbg");
				});
	
				//head中选择某个标签
				$(".head_right div a").click(function(){
				      $(".head_right div a").html('');
					  $(this).html('<span></span>');
				});
		   });
		</script>
   	</c:when>
       
    	<c:when test="${themes eq 'green'}">
         <div region="north" border="false"  split="false" style="height:90px;padding:0;margin:0;overflow:hidden;">
	    	   <div class="easyui-panel" fit="true" border="false" id="greenTheme_top_panel">
					<div class="head_bg">
						<div class="head_logo">
							<div class="head_t_0">
								<img src="jquery-easyui-1.1.2/themes/images/deepblue/jee_logo_subSystem.png" />
							</div>
							<div class="head_t_1">
								<font color="white" face="黑体" size="5">${systemName}</font>
							</div>
						</div>
						<div style="float:right;">
							<div class="head_t_msg">欢迎您，${uName}-${userLevel}!</div>
							<div class="head_t_if">
								<div class="head_t_img"><img src="jquery-easyui-1.1.2/themes/images/green/head_ico.gif" width="15" height="13" /></div>
								<div class="head_t_font"><a href="<%= basePathFrame_top%>/zsf_logout.action?bak_url=${bak_url}" style="text-decoration: none;">注销</a></div>
								<div name="head_skin_div" class="head_t_img"><img src="jquery-easyui-1.1.2/themes/images/green/head_ico1.gif" width="15" height="13" /></div>
								<div name="head_skin_div" class="head_t_font change_skin_zxt"><a href="javascript:void(0);" style="text-decoration: none;">皮肤</a></div>
							</div>
						</div>
					</div>
					<ul class="head_nav">
						<s:iterator value="#request.systemMenuList" status="stauts"   >
							<li><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menuId}','contents','frameAccordionMenu','${menu.resType}');">${title}</a></li>
						</s:iterator>
					</ul>
	    	   </div>
		</div>
		
		<script>
		   $(function(){
			 //默认被选中的导航栏
		   		$(".head_nav li a:eq(0)").addClass("head_nav_li_hover");
				$(".head_nav li a").click(function(){//点击某个标题当前显示被选中，其他的选中消除
				    $(".head_nav li a").removeClass("head_nav_li_hover");
						$(this).addClass('head_nav_li_hover');
					});
				});
		</script>
       </c:when>
     
	<c:when test="${themes eq 'dgreen'}">  
		<div region="north" border="false"  split="false" style="background:#1b7325;height:70px;padding:0;margin:0;">
		    <div class="head_bg">
				<div class="head_logo">
					<div class="head_t_0">
						<img src="jquery-easyui-1.1.2/themes/images/deepblue/jee_logo_subSystem.png" />
					</div>
					<div class="head_t_1">
						<font color="white" face="黑体" size="5">${systemName}</font><br/>
						<div class="head_t_msg">欢迎您，${uName}-${userLevel}！</div>
						<div class="head_t_if">
							<div class="head_t_img"><img src="jquery-easyui-1.1.2/themes/images/dgreen/head_ico.gif" width="12" height="11" /></div>
							<div class="head_t_font"><a href="<%= basePathFrame_top%>/zsf_logout.action?bak_url=${bak_url}">注销</a></div>
							<div name="head_skin_div" class="head_t_img2"><img src="jquery-easyui-1.1.2/themes/images/dgreen/head_ico1.gif" width="14" height="11" /></div>
							<div name="head_skin_div" class="head_t_font change_skin_zxt"><a href="javascript:void(0);">换肤</a></div>
						</div>
					</div>
				</div> 
				<div class="head_right" ><div style="float:right">
								<ul>
						<c:forEach items='${systemMenuList}' var='menu' varStatus='ss'>
							<c:choose>
									<c:when test="${menu.resType eq '6'}">
										<c:choose>
											<c:when test='${ss.first}'><li><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}','${menu.url}');" onclick="javascript:getBg('${ss.index }','${fn:length(systemMenuList)}')" id="mynav${ss.index }" class="current"><span>${menu.title}</span></a></li></c:when>
											<c:otherwise><li><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}','${menu.url}');" onclick="javascript:getBg('${ss.index }','${fn:length(systemMenuList)}')" id="mynav${ss.index }" class="nocurrent"><span>${menu.title}</span></a></li></c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test='${ss.first}'><li><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}');" id="mynav${ss.index }" onclick="javascript:getBg('${ss.index }','${fn:length(systemMenuList)}')" class="current"><span>${menu.title}</span></a></li></c:when>
											<c:otherwise><li><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}');" id="mynav${ss.index }" onclick="javascript:getBg('${ss.index }','${fn:length(systemMenuList)}')" class="nocurrent"><span>${menu.title}</span></a></li></c:otherwise>
										</c:choose>
									</c:otherwise>
							</c:choose>	
						</c:forEach>
		   						</ul>
				</div></div>
			</div>
		</div>	     	     	
				
		<script>
			function getBg(num,total){
				for(var id = 0;id<=total;id++){
					if(id==num){
						$("#mynav"+id).attr("class","current"); 
					}else{
						$("#mynav"+id).attr("class","nocurrent"); 
					}
				}
			}
		</script>
    </c:when>
        <c:when test="${themes eq 'deepblue'}">  
		<div region="north" border="false"  split="false" style="background:#083465;height:70px;padding:0;margin:0;">
		    <div class="head_bg">
				<div class="head_logo">
					<div class="head_t_0">
						<img src="jquery-easyui-1.1.2/themes/images/deepblue/jee_logo_subSystem.png" style="height:67px;" />
					</div>
					<div class="head_t_1">
						<font color="white" face="黑体" size="5">${systemName}</font><br/>
						
					</div>
					
				</div>
				<div>
					
					<div class="head_t_if">
						<div class="head_t_img"><img src="jquery-easyui-1.1.2/themes/images/deepblue/head_ico.gif" width="12" height="11" /></div>
						<div class="head_t_font"><a href="<%= basePathFrame_top%>/zsf_logout.action?bak_url=${bak_url}" id="hege">注销</a></div>
						<div name="head_skin_div" class="head_t_img2"><img src="jquery-easyui-1.1.2/themes/images/deepblue/head_ico1.gif" width="14" height="11" /></div>
						<div name="head_skin_div" class="head_t_font change_skin_zxt"><a href="javascript:void(0);">换肤</a></div>
						
						<div class="head_t_img"><img src="jquery-easyui-1.1.2/themes/images/deepblue/head_ico3.png" width="14" height="11" /></div>
						<div class="head_t_font"><a href='javascript:void(0)' onclick='orgUserUpdateLimsBh()'>修改密码</a></div>							
					</div>
			<c:choose>
				<c:when test="${empty stwitchRoleArray}"></c:when>		
				<c:otherwise>	
					<div style="color:#fff;line-height: 35px;width: auto;float: right;margin-left:5px;">
							<div style="line-height: 35px;float: left;margin-top: 9px;"><img src='images/2.gif'   width='14px;'/></div>
							<div style="color: #fff;line-height: 35px;float: left;padding: 0 0px 0 3px;" class="head_u">
								<ul>
									<li>
										<a href="javascript:void(0);return false;" style="color: #fff;"  onClick="head_uu();" >角色切换</a>	
									</li>
								</ul>
							</div>
					</div>
				</c:otherwise>
			</c:choose>			
					<div class="head_right">
						<div style="float:right;margin-right: 10px;">							
			 				<ul>
								<c:forEach items='${systemMenuList}' var='menu' varStatus='ss'>
									<c:choose>
											<c:when test="${menu.resType eq '6'}">
												<c:choose>
													<c:when test='${ss.first}'><li><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}','${menu.url}');" onclick="javascript:getBg('${ss.index }','${fn:length(systemMenuList)}')" id="mynav${ss.index }" class="current"><span>${menu.title}</span></a></li></c:when>
													<c:otherwise><li><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}','${menu.url}');" onclick="javascript:getBg('${ss.index }','${fn:length(systemMenuList)}')" id="mynav${ss.index }" class="nocurrent"><span>${menu.title}</span></a></li></c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test='${ss.first}'><li><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}');" id="mynav${ss.index }" onclick="javascript:getBg('${ss.index }','${fn:length(systemMenuList)}')" class="current"><span>${menu.title}</span></a></li></c:when>
													<c:otherwise><li><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}');" id="mynav${ss.index }" onclick="javascript:getBg('${ss.index }','${fn:length(systemMenuList)}')" class="nocurrent"><span>${menu.title}</span></a></li></c:otherwise>
												</c:choose>
											</c:otherwise>
									</c:choose>	
								</c:forEach>
			     			</ul>
						</div>
					</div>
					
					<div class="head_t_msg">欢迎您，${uName}-${stwitchRole} !</div> <!-- 用户等级:${userLevel} -->
				</div>	
			</div>
		</div>							
			
		<script>
			function getBg(num,total){
				for(var id = 0;id<=total;id++){
					if(id==num){
						$("#mynav"+id).attr("class","current"); 
					}else{
						$("#mynav"+id).attr("class","nocurrent"); 
					}
				}
			}
		</script>
       </c:when>
       <c:when test="${themes eq 'yingji'}">
		<div region="north" border="false"  split="false" style="background:#083465;height:70px;padding:0;margin:0;">
		    <div class="head_bg">
				<div class="head_logo">
					<div class="head_t_0">
						<img src="jquery-easyui-1.1.2/themes/images/deepblue/logo_subSystem.png" />
					</div>
					<div class="head_t_1">
						<font color="white" face="黑体" size="5">${systemName}</font><br/>
						<div class="head_t_msg">欢迎您，${uName}-${userLevel}!</div>
						<div class="head_t_if">
							<div class="head_t_img"><img src="jquery-easyui-1.1.2/themes/images/deepblue/head_ico.gif" width="12" height="11" /></div>
							<div class="head_t_font"><a href="<%= basePathFrame_top%>/zsf_logout.action?bak_url=${bak_url}">注销</a></div>
							<div name="head_skin_div" class="head_t_img2"><img src="jquery-easyui-1.1.2/themes/images/deepblue/head_ico1.gif" width="14" height="11" /></div>
							<div name="head_skin_div" class="head_t_font change_skin_zxt"><a href="javascript:void(0);">换肤</a></div>
							
							<div class="head_t_img"><img src="jquery-easyui-1.1.2/themes/images/deepblue/head_ico3.png" width="14" height="11" /></div>
							<div class="head_t_font"><a href='javascript:void(0)' onclick='orgUserUpdateLimsBh()'>修改密码</a></div>							
						</div>
					</div>
				</div>
				<div class="head_right">
					<s:iterator value="#request.systemMenuList" status="stauts"   >
						<c:choose>
							<c:when test="${title=='个人桌面'}">
								<div class="head_btn1" align="center"><a onfocus="this.blur();" href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menuId}','contents','frameAccordionMenu','${resType}','${url }');"></a><span>${title}</span></div>
							</c:when>
							<c:when test="${title=='应急系统'}">
								<div class="head_btn2" align="center"><a onfocus="this.blur();"  id="yjxt_btn" href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menuId}','contents','frameAccordionMenu','${resType}','${url }');"></a><span>${title}</span></div>
							</c:when>
							<c:when test="${title=='指挥桌面'}">
								<div class="head_btn3" align="center"><a onfocus="this.blur();" id="xczm_btn" href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menuId}','contents','frameAccordionMenu','${resType}','${url }');"></a><span>${title}</span></div>
							</c:when>
							<c:when test="${title=='现场桌面'}">
								<div class="head_btn4" align="center"><a onfocus="this.blur();"  href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menuId}','contents','frameAccordionMenu','${resType}','${url }');"></a><span>${title}</span></div>
							</c:when>
							<c:otherwise>
								<div class="head_btn5" align="center"><a onfocus="this.blur();" href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menuId}','contents','frameAccordionMenu','${resType}','${url }');"></a><span>${title}</span></div>
							</c:otherwise>
						</c:choose>
					</s:iterator>
				</div>
			</div>
		</div>							
				
		<script>
			$(function(){
				//head中选择某个标签
				$(".head_right div a").click(function(){
				      $(".head_right div a").html('');
					  $(this).html('<span></span>');
				});
		   });
		</script>
       </c:when>
       <c:when test="${themes eq 'national'}">
       		<div region="north" border="false"  split="false" style="background:#fff;height:70px;padding:0;margin:0;">
	       		 <div class="head_bg">
					<div class="head_logo">
						<div class="head_t_logo">
							<img src="jquery-easyui-1.1.2/themes/images/deepblue/jee_logo_subSystem.png" width="63px" height="66px" style="margin-top: 0;"/>
						</div>
						<div class="head_t_1">
							<font color="#3D90DE" face="黑体" size="5">${systemName}</font>
						</div>
						<div class="head_t">
							<div class="head_t_msg">欢迎您，${uName}-${userLevel}！</div>
							<div class="head_t_if">
								<div class="head_t_img"><img src="jquery-easyui-1.1.2/themes/national/images/head_ico.gif" width="12" height="11" /></div>
								<div class="head_t_font"><a href="<%= basePathFrame_top%>/zsf_logout?bak_url=${bak_url}.action">注销</a></div>
								<div class="head_t_img2"><img src="jquery-easyui-1.1.2/themes/national/images/head_ico2.gif" style="cursor: hand;" class="themeLinkBTN" width="14" height="11" /></div>
								<div class="head_t_font change_skin_zxt"><a href="javascript:void(0);">换肤</a></div>
							</div>
						</div>
					</div> 
		
					<div class="head_right">	
						<ul>
							<c:forEach items='${systemMenuList}' var='menu' varStatus='ss'>
								<c:choose>
										<c:when test="${menu.resType eq '6'}">
											<c:choose>
												<c:when test='${ss.first}'><li><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}','${menu.url}');" onclick="javascript:getBg('${ss.index }','${fn:length(systemMenuList)}')" id="mynav${ss.index }" class="current"><span>${menu.title}</span></a></li></c:when>
												<c:otherwise><li><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}','${menu.url}');" onclick="javascript:getBg('${ss.index }','${fn:length(systemMenuList)}')" id="mynav${ss.index }" class="nocurrent"><span>${menu.title}</span></a></li></c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test='${ss.first}'><li><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}');" id="mynav${ss.index }" onclick="javascript:getBg('${ss.index }','${fn:length(systemMenuList)}')" class="current"><span>${menu.title}</span></a></li></c:when>
												<c:otherwise><li><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}');" id="mynav${ss.index }" onclick="javascript:getBg('${ss.index }','${fn:length(systemMenuList)}')" class="nocurrent"><span>${menu.title}</span></a></li></c:otherwise>
											</c:choose>
										</c:otherwise>
								</c:choose>	
							</c:forEach>
							
						</ul>
					</div>
					<script type="text/javascript">
						$(function(){
							$(".head_right ul li").click(function(){
							    $(".head_right ul li a").attr("class","nocurrent");
							    $(this).find("a").attr("class","current");
							});
						});
						function getBg(num,total){
							for(var id = 0;id<=total;id++){
								if(id==num){
									$("#mynav"+id).attr("class","current"); 
								}else{
									$("#mynav"+id).attr("class","nocurrent"); 
								}
							}
						}
					</script>
				</div>
			</div>
	       </c:when>
			<c:when test="${themes eq 'yl'}">
				<div region="north" border="false" style="height: 100px; background: none;">
					<div style="opacity: 1; -ms-filter: 'alpha(opacity=100)'; filter: alpha(opacity = 100); zoom: 1;padding-top: 30px;">
						<img src="jquery-easyui-1.1.2/themes/yl/images/systemtitle.png" style="margin-left: 120px;">
						<table style="position: absolute;top:5px;right: 100px;"><tr> 
						<td><img src="jquery-easyui-1.1.2/themes/yl/images/head_ico2.gif" style="cursor: hand;" class="themeLinkBTN" width="14" height="11" /></td>
						<td> <a href='javascript:void(0)' onclick='orgUserUpdateLimsBh()'>修改密码</a> </td>
						<td><img src="jquery-easyui-1.1.2/themes/yl/images/head_ico.gif" width="12" height="11" /> </td>
						<td> <a href="<%= basePathFrame_top%>/zsf_logout?bak_url=${bak_url}.action">注销</a> </td>
						<td>
						
						  <c:choose>
								<c:when test="${empty stwitchRoleArray}"></c:when>		
								<c:otherwise>	
									<div style="line-height: 35px;width: auto;float: right;margin-left:5px;">
											<div style="line-height: 35px;float: left;margin-top: 9px;"><img src='images/2.gif'   width='14px;'/></div>
											<div style="line-height: 35px;float: left;padding: 0 0px 0 3px;" class="head_u">
												<ul>
													<li>
														<a href="javascript:void(0);return false;"  onClick="head_uu();" >角色切换</a>	
													</li>
												</ul>
											</div>
									</div>
								</c:otherwise>
							</c:choose>	
						
						</td>
						</tr></table>
						<ul class="ylitems">
					<c:forEach items='${systemMenuList}' var='menu' varStatus='ss'>
									<c:choose>
											<c:when test="${menu.resType eq '6'}">
												<c:choose>
													<c:when test='${ss.first}'><li  class="item selected" onclick="systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}','${menu.url}');">${menu.title}</li></c:when>
													<c:otherwise><li style="float: left;height: 32px;vertical-align: middle;padding-top:15px;"> &nbsp; | &nbsp; </li><li  class="item" style="float: left;height: 32px;vertical-align: middle;padding-top:15px;" onclick="systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}','${menu.url}');">${menu.title}</li></c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test='${ss.first}'><li   class="item selected" onclick="systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}');" >${menu.title}</li></c:when>
													<c:otherwise><li style="float: left;height: 32px;vertical-align: middle;padding-top:15px;"> &nbsp; | &nbsp; </li><li   class="item" style="float: left;height: 32px;vertical-align: middle;padding-top:15px;" onclick="systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}');" >${menu.title}</li></c:otherwise>
												</c:choose>
											</c:otherwise>
									</c:choose>	
								</c:forEach>	
						</ul>
					</div>
				</div>
				<script type="text/javascript">
					$(function(){
						$(".ylitems li").click(function(){
						   $(".ylitems li").removeClass("selected");
						   $(this).addClass("selected");
						});
					});
				</script>
			</c:when>
			<c:when test="${themes eq 'ww'}">
				<div region="north" border="false" style="height: 100px; background: none;">
					<div style="opacity: 1; -ms-filter: 'alpha(opacity=100)'; filter: alpha(opacity = 100); zoom: 1;padding-top: 25px;">
						<img src="jquery-easyui-1.1.2/themes/ww/images/maintitlefont.png" style="margin-left: 20px;">
						<table style="position: absolute;top:2px;right: 100px;"><tr> 
						<td><img src="jquery-easyui-1.1.2/themes/yl/images/head_ico2.gif" style="cursor: hand;" class="themeLinkBTN" width="14" height="11" /></td>
						<td> <a href='javascript:void(0)' onclick='orgUserUpdateLimsBh()'>修改密码</a> </td>
						<td><img src="jquery-easyui-1.1.2/themes/yl/images/head_ico.gif" width="12" height="11" /> </td>
						<td> <a href="${pageContext.request.contextPath}/zsf_logout?bak_url=${bak_url}.action">注销</a> </td>
						<td>
						
						  <c:choose>
								<c:when test="${empty stwitchRoleArray}"></c:when>		
								<c:otherwise>	
									<div style="line-height: 35px;width: auto;float: right;margin-left:5px;">
											<div style="line-height: 35px;float: left;margin-top: 9px;"><img src='images/2.gif'   width='14px;'/></div>
											<div style="line-height: 35px;float: left;padding: 0 0px 0 3px;" class="head_u">
												<ul>
													<li>
														<a href="javascript:void(0);return false;"  onClick="head_uu();" >角色切换</a>	
													</li>
												</ul>
											</div>
									</div>
								</c:otherwise>
							</c:choose>	
						
						</td>
						</tr></table>
						<ul class="ylitems">
					<c:forEach items='${systemMenuList}' var='menu' varStatus='ss'>
									<c:choose>
											<c:when test="${menu.resType eq '6'}">
												<c:choose>
													<c:when test='${ss.first}'><li  class="item selected" onclick="systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}','${menu.url}');">${menu.title}</li></c:when>
													<c:otherwise><li style="float: left;height: 32px;vertical-align: middle;padding-top:15px;"> &nbsp; | &nbsp; </li><li  class="item" style="float: left;height: 32px;vertical-align: middle;padding-top:15px;" onclick="systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}','${menu.url}');">${menu.title}</li></c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test='${ss.first}'><li   class="item selected" onclick="systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}');" >${menu.title}</li></c:when>
													<c:otherwise><li style="float: left;height: 32px;vertical-align: middle;padding-top:15px;"> &nbsp; | &nbsp; </li><li   class="item" style="float: left;height: 32px;vertical-align: middle;padding-top:15px;" onclick="systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}');" >${menu.title}</li></c:otherwise>
												</c:choose>
											</c:otherwise>
									</c:choose>	
								</c:forEach>	
						</ul>
					</div>
				    <div style="background: url('jquery-easyui-1.1.2/themes/ww/images/maintitlebcg.png') repeat-x;width:100%;height: 79px;position: absolute;top:15px;z-index: -1;">
				       <img src="jquery-easyui-1.1.2/themes/ww/images/maintitle1.png" />
				    </div>
				</div>
				<script type="text/javascript">
					$(function(){
						$(".ylitems li").click(function(){
						   $(".ylitems li").removeClass("selected");
						   $(this).addClass("selected");
						});
					});
				</script>
			</c:when>
		<c:when test="${themes eq 'gray'}">
       		<div region="north" border="false"  split="false" style="background:#fff;height:70px;padding:0;margin:0;">
	       		<div class="head_bg">
					<div class="head_logo">
						<div class="head_t_logo">
							<img src="jquery-easyui-1.1.2/themes/images/deepblue/jee_logo_subSystem.png" width="63px" height="66px" />
						</div>
						<div class="head_t_1">
							<font color="#3D90DE" face="黑体" size="5">${systemName}</font>
						</div>
						<div class="head_t">
							<div class="head_t_msg">欢迎您，${uName}-${userLevel}！</div>
							<div class="head_t_if">
								<div class="head_t_img"><img src="jquery-easyui-1.1.2/themes/gray/images/head_ico.gif" width="12" height="11" /></div>
								<div class="head_t_font"><a href="<%= basePathFrame_top%>/zsf_logout.action?bak_url=${bak_url}">注销</a></div>
								<div class="head_t_img"><img src="jquery-easyui-1.1.2/themes/gray/images/head_ico2.gif" style="cursor: hand;" class="themeLinkBTN" width="14" height="11" /></div>
								<div class="head_t_font change_skin_zxt"><a href="javascript:void(0);" class="themeLinkBTN">换肤</a></div>
							</div>
						</div>
					</div> 
		
					<div class="head_right">						
						<ul>
							<c:forEach items='${systemMenuList}' var='menu' varStatus='ss'>
								<c:choose>
										<c:when test="${menu.resType eq '6'}">
											<c:choose>
												<c:when test='${ss.first}'><li><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}','${menu.url}');" onclick="javascript:getBg('${ss.index }','${fn:length(systemMenuList)}')" id="mynav${ss.index }" class="current"><span>${menu.title}</span></a></li></c:when>
												<c:otherwise><li><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}','${menu.url}');" onclick="javascript:getBg('${ss.index }','${fn:length(systemMenuList)}')" id="mynav${ss.index }" class="nocurrent"><span>${menu.title}</span></a></li></c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test='${ss.first}'><li><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}');" id="mynav${ss.index }" onclick="javascript:getBg('${ss.index }','${fn:length(systemMenuList)}')" class="current"><span>${menu.title}</span></a></li></c:when>
												<c:otherwise><li><a href="javascript:systemMenu('formengine/zsf_switchSystemMenu.action?menuId=${menu.menuId}','contents','frameAccordionMenu','${menu.resType}');" id="mynav${ss.index }" onclick="javascript:getBg('${ss.index }','${fn:length(systemMenuList)}')" class="nocurrent"><span>${menu.title}</span></a></li></c:otherwise>
											</c:choose>
										</c:otherwise>
								</c:choose>	
							</c:forEach>
						</ul>
					</div>
					<script type="text/javascript">
						$(function(){
							$(".head_right ul li").click(function(){
							    $(".head_right ul li a").attr("class","nocurrent");
							    $(this).find("a").attr("class","current");
							});
						});
						
						function getBg(num,total){
							for(var id = 0;id<=total;id++){
								if(id==num){
									$("#mynav"+id).attr("class","current"); 
								}else{
									$("#mynav"+id).attr("class","nocurrent"); 
								}
							}
						}
					</script>
				</div>
			</div>
       </c:when>
   </c:choose>
   
   <div id="updateUserMessage" title="修改密码" style="display: none;"></div>
   <div id="head_uu" class="head_ro"><div><jsp:include page="/common/jsp/easyui/role-switch.jsp"></jsp:include></div></div>
  
   