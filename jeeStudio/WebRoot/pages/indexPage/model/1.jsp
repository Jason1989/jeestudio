<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%-- 测试界面 --%>
<script type="text/javascript">
	$(function(){
		$(".firsttemmpate").panel({
			tools:[
				{iconCls:"icon-add",
				 handler:function(event){
				    alert("show");
				 }}
			]
		});
	});
</script>
<div class="firsttemmpate" fit="true" style="padding:5px;">
    <table class="indexmain-table">
				<thead>
					<tr class="datagrid-header">
						<td width="55%">
							<b>分组编号</b>
						</td>
						<td width="45%">
							<b>检测项目</b>
						</td>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${list==null}">
							<tr class="indexmain-table-body">
								<td colspan="3" class="colcenter">
									暂无待检测样品
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${list}" var="exp">
								<tr class="indexmain-table-body">
									<td align="center">
										${exp.batNo}
									</td>
									<td align="center">
										${exp.itmNames}
									</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
</div>