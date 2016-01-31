<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	Object obj_menutitle = request.getAttribute("menutitle");
	String menutitle = "";
	if(obj_menutitle!=null){
		menutitle = obj_menutitle.toString();
	}
	Object obj_menuurl = request.getAttribute("menuurl");
	String menuurl = "";
	if(obj_menuurl!=null){
		menuurl = obj_menuurl.toString();
	}
	Object obj_menuid = request.getAttribute("menuid");
	String menuid = "";
	if(obj_menuid!=null){
		menuid = obj_menuid.toString();
	}
	boolean b = ((menuurl==null)||("".equals(menuurl)));
%>
<script type="text/javascript">
	//快捷方式跳转的时候页面自动跳转到对应的菜单
	if("<%=menuurl%>"!=null&&"<%=menuurl%>"!=""&&"<%=menuid%>"!=null&&"<%=menuid%>"!=""){
		$("#yjxt_btn").click();
		setTimeout(function(){
			$('#frameAccordionMenu').accordion("select","<%=menutitle%>");
			$("#btn_<%=menuid%>").click();
			//clickToPanel("<%=menuurl%>","<%=menuid%>","","");
			//$('#main').panel('refresh',"<%=menuurl%>?date=" + new Date().getTime());
		},100);
	}
</script>
<div  class="easyui-accordion" fit="true" border="false" style="background: url(jquery-easyui-1.1.2/themes/images/left_bg.jpg);overflow: auto;" id="frameAccordionMenu"  >
		<c:choose>
     		<c:when test="${themes eq 'yingji'}">
				<s:iterator value="#request.tabMenuList" status="status" id="mlist">
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
		     							<table style="cursor: pointer;">
		     								<tr>
		     									<td align="center"><img class="btn_id" id="btn_${id }" src="${imgsrc }" style="cursor:hand;vertical-align:middle;" onclick="clickToPanel('${attributes.url }','${id }','${attributes.isAbleWorkFlow }','${attributes.workflow_fiter }'),$('.btn_text').css({'color':'#2A3939','font-weight':'normal'}),$('#${text }_text').css({'color':'#3070DC','font-weight':'bold'}),$('.btn_id').css({'background-image':'url()'}),$('#btn_${id }').css({'background-image':'url(emergencydir/image/bg.png)'})" height="50px" width="50px" /></td>
		     								</tr>
		     								<tr>
		     									<td align="center" id="${text }_text" class="btn_text">${text }</td>
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
			     				    <c:if test="<%=b%>">
				     					<c:if test="${status.index==0&&tab.index==0}">
				     						<div id="yingji_first">
					     						<script>
						     						clickToPanel('${attributes.url }','${id }','${attributes.isAbleWorkFlow }','${attributes.workflow_fiter }');
					     							document.getElementById("yingji_first").innerHTML = '';
					     						</script>
				     						</div>
				     					</c:if>
			     					</c:if>
			     					<tr>
			     						<td align="center">
			     							<table style="cursor: pointer;">
			     								<tr>
			     									<td align="center"><img class="btn_id" id="btn_${id }" src="${imgsrc }" style="cursor:hand;vertical-align:middle;" onclick="clickToPanel('${attributes.url }','${id }','${attributes.isAbleWorkFlow }','${attributes.workflow_fiter }'),$('.btn_text').css({'color':'#2A3939','font-weight':'normal'}),$('#${text }_text').css({'color':'#3070DC','font-weight':'bold'}),$('.btn_id').css({'background-image':'url()'}),$('#btn_${id }').css({'background-image':'url(emergencydir/image/bg.png)'})"  height="50px" width="50px" /></td>
			     								</tr>
			     								<tr>
			     									<td align="center" id="${text }_text" class="btn_text">${text }</td>
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
     			<s:iterator value="#request.tabMenuList" status="stauts"   >
					<div title="${title}"  headerCls="accordion-font-color"  icon="icon-accordion-node" >
						<ul id="frame_menu_tree_${stauts.index}" style="padding:10px;"></ul>
						<script type="text/javascript">
							$(function(){  
								if(document.readyState=="complete"){
								   createMenuTree('${json}','frame_menu_tree_${stauts.index}');
								   if('${stauts.index}'==0){
									   	if('${json}'.trim().length!= 0){
											var test_obj=eval('${json}');
											var abc=test_obj[0];
											var treeNodeUrl=abc.attributes.url;
											treeNodeUrl=encodeURI(treeNodeUrl);
											
											var isLeaf = $('#frame_menu_tree_${stauts.index}').tree('isLeaf', abc.target);
												  
											if(!isLeaf){
												setTimeout(function(){
													$('#main').children().empty();
													$('#main').panel('refresh','common/jsp/easyui/common_blank.jsp');
												},0);
											}else{
												if(treeNodeUrl.indexOf(".")>0){
													if(treeNodeUrl.indexOf("?")>0){
														$('#main').children().empty();
														$('#main').panel('refresh',treeNodeUrl+ "&date=" + new Date().getTime());
													}else{
														$('#main').children().empty();
														$('#main').panel('refresh',treeNodeUrl+ "?date=" + new Date().getTime());
													}
												}else{
													setTimeout(function(){
														$('#main').children().empty();
														$('#main').panel('refresh','formengine/listPageAction.action?isAbleWorkFlow='+abc.attributes.isAbleWorkFlow+'&workflow_fiter='+abc.attributes.workflow_fiter+'&is_workflowcompar='+abc.attributes.isWorkFlowComPar+'&is_workflowcomparid='+abc.attributes.isWorkFlowComParId+'&is_workflowcompararray='+abc.attributes.isWorkFlowComParArray+'&formId='+treeNodeUrl+ "&date=" + new Date().getTime()+"&menuId="+abc.id);
													},0);
												}
											}
											
										}else{
											setTimeout(function(){
													$('#main').children().empty();
													$('#main').panel('refresh','common/jsp/easyui/common_blank.jsp');
											},0);
										}
									}
								}
				   		 	});
						</script>
					</div>
				</s:iterator>
     		</c:otherwise>
     	</c:choose>
		
	
	<c:choose>
		<c:when test="${empty  tabMenuList}">
			<script>
				$('#main').panel('refresh','common/jsp/easyui/common_blank.jsp');
			</script>
		</c:when>
	</c:choose>
</div>
