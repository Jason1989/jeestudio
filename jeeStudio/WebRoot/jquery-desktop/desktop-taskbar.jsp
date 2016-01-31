<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<div class="abs" id="bar_bottom">
		<a class="float_left" href="#" id="show_desktop" title="Show Desktop">
			<img src="assets/images/icons/icon_22_desktop.png" />
		</a>
		<ul id="dock">
			
		</ul>
		<a class="float_right change_skin_zxt" href="javascript:void(0)" title="切换主题" id="head_skin_div">
			<img src="assets/images/misc/firehost.png" />
		</a>
		<!-- 换肤 -->
	    <div id="zxtplat_change_skin">
	    </div>
</div>
<script>
	$(function(){
		var selectedEnable = '${selectskinenable}';
					//设置是否可以更换皮肤
					if(selectedEnable == '1'){
						$('#head_skin_div').show();	
					}else{
						$('#head_skin_div').hide();	
					}
	});
</script>