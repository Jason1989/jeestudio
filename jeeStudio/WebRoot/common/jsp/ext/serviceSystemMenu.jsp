<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<div  class="easyui-accordion" fit="true" border="false" style="background: url(jquery-easyui-1.1.2/ui/images/left_bg.jpg)" id="frameAccordionMenu"  >
<s:iterator value="#request.tabMenuList" status="stauts"   >
	<div title="${title}"  headerCls="accordion-font-color"  icon="icon-accordion-node" >
		<ul id="frame_menu_tree_${stauts.index}" style="padding:10px;"></ul>
		<script type="text/javascript">
			createMenuTree('${json}','frame_menu_tree_${stauts.index}');
		</script>
	</div>
</s:iterator>
</div>
