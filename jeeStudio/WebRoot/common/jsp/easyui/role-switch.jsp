<%@page   contentType="text/html;charset=UTF-8"  language="java" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
	<!-- 角色选择界面 多角色出现角色切换界面-->
<c:choose>
	<c:when test="${empty stwitchRole}"></c:when>
	<c:otherwise>
		<div id="fm_formdesignlist_setpanel" style="width:152px;background:#fafafa;border:1px solid #8DB2E3;position:absolute;top:78px;right:135px;z-index: 9999999;">
			<div id="fm_formdesignlist_setpaneltitle" class="panel-header panel-header-noborder"><img src='images/2.gif'  style="vertical-align: middle;" width='15px;'/>&nbsp;&nbsp;角色切换
				<a class="easyui-linkbutton" plain="true" iconCls="icon-cancel" onClick="head_cc()" style="float:right;margin-top:-6px;margin-right:-5px;"></a>
				<!--<img src="images/cancel.png" width="15px" height="15px" style="float:right;"></img>
			--></div>
			<c:forEach items="${stwitchRoleArray}" var="roleName" >
				<div style="width:150px;">
					<c:choose>
						<c:when test="${roleName eq stwitchRole}">
							<a href="javascript:void(0);" onclick="operUrlForWindow('${pageContext.request.contextPath}/zsf_.action?bak_url=${bak_url}&stwitchRole=${roleName}');" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-role" >${roleName}</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:void(0);" onclick="operUrlForWindow('${pageContext.request.contextPath}/zsf_.action?bak_url=${bak_url}&stwitchRole=${roleName}');" class="easyui-linkbutton" style="width:117px;" plain="true" icon="icon-role-unchecked" onclick="fmOpenListPageSetWindow('页面配置')">${roleName}</a>
						</c:otherwise>
					</c:choose>
				</div>
			</c:forEach>
		</div>
		<script type="text/javascript">
				$('#fm_formdesignlist_setpanel').draggable({axis:null,handle:'#fm_formdesignlist_setpaneltitle'});
		</script>
	</c:otherwise>
</c:choose>