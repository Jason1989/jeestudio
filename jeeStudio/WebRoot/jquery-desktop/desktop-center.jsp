<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<s:iterator value="#request.userDeskSetVo.menuSettings" status="status" >
    <div id="window_desktop_${status.index}" class="abs window">
	<div class="abs window_inner">
		<div class="window_top">
				<span class="float_left">
					<img src="assets/images/icons/icon_16_computer.png" />
					顶部左侧
				</span>
				<span class="float_right">
				<!-- 顶部右侧 -->
					<a href="#" class="window_min"></a>
					<a href="#" class="window_resize"></a>
					<a href="#taskbar_${status.index}" class="window_close" ></a>
				</span>
			</div>
			<div class="abs window_content">
				<div class="window_aside">
					<!-- 窗口中部左侧  -->
				</div>
				<div class="window_main">
					<!-- 窗口中部右侧  -->
					<div id="desktop-data-${status.index}" class="easyui-panel"  style="width:500px;height:300px;" >
					
					</div>
				</div>
			</div>
			<div class="abs window_bottom">
					<span style="font-weight: normal">构件平台v1.0</span>
			</div>
		</div>
		<span class="abs ui-resizable-handle ui-resizable-se"></span>
	</div>
</s:iterator>  	
 <jsp:include page="desktop-center-setmenu.jsp"></jsp:include>