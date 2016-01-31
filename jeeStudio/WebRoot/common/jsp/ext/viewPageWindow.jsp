<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<div id="viewPageWindow_${listPageRander}"     style="padding:5px;background: #fafafa;"  >
	<div class="easyui-layout" fit="true">
		<div id="viewPageData_${listPage.id}" region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
		
		</div>
		<div region="south" border="false" style="text-align:center;height:30px;line-height:30px;">
				 <a  class="easyui-linkbutton" icon="icon-cancel"  href="javascript:closeWindow('viewPageWindow_${listPageRander}');"  >关闭</a>	
		</div>
	</div>
</div>