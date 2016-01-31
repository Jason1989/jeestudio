<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<div  class="easyui-accordion" fit="true" border="false" style="background: url(jquery-easyui-1.1.2/images/left_bg.jpg);overflow: auto;" id="frameAccordionMenu"  >
	   <div title="组织机构和权限"  headerCls="accordion-font-color"  icon="icon-accordion-node" >
			<ul id="tree_system_set_1" style="padding:8px;" class="easyui-tree" animate="true"></ul>
				<script type="text/javascript">
							$(function(){
								var treeData=[{
									text:"组织机构",
									attributes:{
										url:'organization/organization!toList.action?isAdmin=1'
									}
								},
								
								{
									text:"角色管理",
									attributes:{
										url:'authority/role!toList.action?isAdmin=1'
									}
								},{
								    id:'main_page',
								    text:"首页模块配置",
								 	//iconCls:"icon-save",
								    children:[],
								    attributes:{
										url:'pages/indexPage/indexList.jsp' 
										}
								  },{ 
								    text:"资源管理",
									attributes:{
										url:'formengine/zsf_findResource.action'
									}
								},{ 
								    text:"用户级别",
									attributes:{
										url:'authority/userlevel!toList.action'
									}
								},{ 
								    text:"报表统计",
									attributes:{
										url:'pages/authority/reports.jsp'
									}
								},{ 
								    text:"字段授权",
									attributes:{
										url:'authority/fieldGrant!list.action'
									}
								}];
								
								$('#tree_system_set_1').tree({
									  checkbox:false,//是否可以多选
									  data:treeData,
								 	  onlyLeafCheck:false,
									  onClick:function (node){
									  	  document.getElementById("main").innerHTML="";
										  $('#main').panel('refresh',node.attributes.url);
									  }
								});	
							});
						</script>
		</div>

	
		<div title="子系统配置"  headerCls="accordion-font-color"  icon="icon-accordion-node" >
			<ul id="child_system" style="padding:10px;" class="easyui-tree" animate="true">
			</ul>
			
			<script type="text/javascript">
				$(function(){
					var treeData=[{
								    id:'cache_menu',
								    text:"子系统预览",
								  	//iconCls:"icon-save",
								    children:[],
								    attributes:{
									    url:'zsf_previewSystem.action'
								    }
								  }
						   		];
						   		//zsf_previewSystem.action
					$('#child_system').tree({
						  checkbox:false,//是否可以多选
						  data:treeData,
					 	  onlyLeafCheck:false,
						  onClick:function (node){
							  //$('#main').panel('refresh','zsf_previewSystem.action');
							  document.getElementById("main").innerHTML="";
							  $('#main').panel('refresh',node.attributes.url);
						  }
					});	
				});
			</script>
			
		</div>
		<div title="缓存管理"  headerCls="accordion-font-color"  icon="icon-accordion-node" >
			<ul id="cache-ul" style="padding:10px;" class="easyui-tree" animate="true">
			</ul>
			
			<script type="text/javascript">
				$(function(){
					var treeData=[{
							    id:'cache_menu',
							    text:"缓存管理",
							  //iconCls:"icon-save",
							    children:[],
							    url:'menu/menu!main.action'
						   }];
					$('#cache-ul').tree({
						  checkbox:false,//是否可以多选
						  data:treeData,
					 	  onlyLeafCheck:false,
						  onClick:function (node){
							  $('#main').panel('refresh','pages/cache/cache.jsp');
						  }
					});	
				});
			</script>
			
		</div>
	
 </div>