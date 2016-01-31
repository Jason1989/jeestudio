<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<div class="easyui-accordion" fit="true" border="false" style="background: url(jquery-easyui-1.1.2/themes/images/left_bg.jpg)">
	<c:forEach var="menu" items="${menus}">
		<div title="${menu.menuName}" headerCls="accordion-font-color" icon="icon-accordion-node" split="true">
			<ul id="lt${menu.menuId}"></ul>
			<script type="text/javascript">
				$(function(){
					$('#lt${menu.menuId}').tree({
						url: 'menu/menu!show.action?parentId=${menu.menuId}&typeId=${menu.menuType}',
						onClick:function(node){
							clearDom();
							document.getElementById("main").innerHTML="";
						  	executeMenuURL(node);	
						},
						onBeforeLoad:function(){//此事件是为了使当鼠标移到节点上时的背景颜色足够长
							$(this).width($(this).parent().get(0).scrollWidth);
						},
						onLoadSuccess:function(node,data){
							/**
							if(validationRuleFlag == "1" && '${menu.menuId}' == '1'){								
								var validationRuleNode = $('#lt1').tree('find','8');
								if(validationRuleNode){
									$('#lt1').tree("collapseAll");	
									executeMenuURL(validationRuleNode);						
									$('#lt1').tree('select',validationRuleNode.target);
								}
							}
							**/
							$('#lt${menu.menuId}').tree("collapseAll");
						}
					});
					
				});
				function executeMenuURL(node){
					var url =  node.attributes.url;
					if(url!=null&&url!=""){
						if(url.indexOf("?") != -1){
							url += "&random=" + parseInt(10*Math.random());
						}else{
							url += "?random=" + parseInt(10*Math.random());
						}
						$("#main").panel({href :url});
						$("#main").panel("refresh");
					}
				}
			</script>
		</div>
	</c:forEach>
</div>