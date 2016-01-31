<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<div id="queryPageWindow_${listPageRander}" style="padding:5px;background: #fafafa;"  >
	<div class="easyui-layout" fit="true">
		<div   region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;">
		  <jsp:include page="comQueryPage.jsp"></jsp:include>
		</div>
		<div region="south" border="false" style="text-align:center;height:30px;line-height:30px;">
		<!--   comQueryReloadListGrid(queryFormID,gridID,queryWindowID); -->
			<a  class="easyui-linkbutton" icon="icon-search"  href="javascript:comQueryReloadListGrid('queryPage_${listPage.id}','fd_formlist_table_${listPageRander}','queryPageWindow_${listPageRander}');"  >查询</a>
			<a  class="easyui-linkbutton" icon="icon-cancel"  href="javascript:closeWindow('queryPageWindow_${listPageRander}');"  >关闭</a>	
		</div>
	</div>
</div>


