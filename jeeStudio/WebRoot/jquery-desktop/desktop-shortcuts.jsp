<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>


<style>
ul,li {list-style-type:none;}
.current {background:url(assets/images/icons/li_l0.png) 0 0 no-repeat;display:block;color:#ffffff; text-decoration:none;}
.current span{background:url(assets/images/icons/li_r0.png) 100% 0 no-repeat;color:#ffffff;padding:0 8px 0 0; margin:0 0 0 8px; display:block;}
</style>

<script>
	$(function(){
		if(document.getElementById("taskId-begin")){
			var id = document.getElementById("taskId-begin").value;
			var text = document.getElementById("taskText-begin").value;
			var formUrl = document.getElementById("taskFormUrl-begin").value;
			var ioc = document.getElementById("taskIoc-begin").value;
			createShorcytWindow(id,text,formUrl,'800','500');
			desktop_add_taskbar(id,text,ioc);
		}
	});
</script>

<div style="float:right;color:#fff;margin-right:20px;margin-top:5px;">
	欢迎您，${uName}-${userLevel}！&nbsp;&nbsp;&nbsp;
	<a href="${pageContext.request.contextPath}/zsf_logout.action" style="text-decoration: none;color:#fff;cursor:hand;">
		<img src="../jquery-easyui-1.1.2/themes/images/blue/head_ico3.gif" width="12" height="11"  style="color:#fff;cursor:hand;"/>
		注销
	</a>
</div>

<s:iterator value="#request.userDeskSetVo.menuSettings" status="status" >
	<c:choose>
		<c:when test="${text eq '我的任务'}">
			<a class="abs icon" style="${style}" href="javascript:void(0);" onclick="createShorcytWindow('${id}','${text}','${formUrl}','800','500');desktop_add_taskbar('${id}','${text}','${ioc}');" >
				<img src="${ioc}" style="width: 55px;height: 60px;cursor:hand;"  />
				<div style="cursor:hand;height:18px;line-height:20px;vertical-align:middle;">
		    		<ul>
		        		<li class="current"><span>${text}</span></li>
		        	</ul>
				</div>
			</a>
			<input type="hidden" id="taskId-begin" value="${id}"/>
			<input type="hidden" id="taskText-begin" value="${text}"/>
			<input type="hidden" id="taskFormUrl-begin" value="${formUrl}"/>
			<input type="hidden" id="taskIoc-begin" value="${ioc}"/>
		</c:when>
		<c:otherwise>
			<a class="abs icon" style="${style}" href="javascript:void(0);" onclick="createShorcytWindow('${id}','${text}','${formUrl}');desktop_add_taskbar('${id}','${text}','${ioc}');" >
				<img src="${ioc}" style="width: 55px;height: 60px;cursor:hand;"  />
				<div style="cursor:hand;height:18px;line-height:20px;vertical-align:middle;">
		    		<ul>
		        		<li class="current"><span>${text}</span></li>
		        	</ul>
				</div>
			</a>
		</c:otherwise>
	</c:choose>
</s:iterator>  
	<a class="abs icon" style="right:20px;top:20px;" href="javascript:void(0);" onclick="openWindow('shortCutsSetWindow');desktop_add_taskbar('shortCutsSetWindow','快捷方式设置','assets/images/icons/icon_32_system.png')"  >
		<img src="assets/images/icons/icon_32_system.png" style="width: 62px;height: 60px;cursor:hand" />
		<div style="background:url('assets/images/icons/shortcutbg1.png') no-repeat top right;cursor:hand;height:18px;line-height:20px;width:96px;vertical-align:middle;">快捷方式设置</div>
	</a>