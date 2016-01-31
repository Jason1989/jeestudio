<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script language="javascript" type="text/javascript">
	$(function(){
		//数据源管理菜单
		var dataSourceMenuData = new Array({			
			"text":"数据源管理1",
			"iconCls":"icon-database-manager",
			"attributes":{
	            "url":"datasource/dataSource!toList.action"
	        }
		});
		
		//数据源
		$('#datasource_menu').tree({
			onClick:clickmenu
		});
		$('#datasource_menu').tree("loadData",dataSourceMenuData);
		
		//数据对象
		$('#dataobject_menu').tree({			
		 	url: 'datatable/dataObjectGroup!getAllItemByParentId.action?parentId=-1',		 		 		 	
			onClick:function(node){
				node.attributes = {
			      	"url":"datatable/dataTable!toList.action?groupId="+node.id,
			      	"price":100
				};
				node.target.name = "dataobject";
				clickmenu(node);
			},
			onBeforeExpand:function(node){
				$('#dataobject_menu').tree('options').url = "datatable/dataObjectGroup!getAllItemByParentId.action?parentId=" + node.id;
			}
		});
		//基础数据
		var basicDataMenuData = [{
			"text":"验证规则管理",			
		    "attributes":{
		      	"url":"validationrule/validationRule!toList.action",
		      	"price":100
			 }
		},{
			"text":"数据字典管理",
			"state":"closed",
			"children":[{
				"text":"字典分组",
			    "attributes":{
			      	"url":"dictionary/dictionaryGroup!toList.action",
			      	"price":100
			    }
			},{
				"text":"数据字典",
			    "attributes":{
			      	"url":"dictionary/dataDictionary!toList.action",
			      	"price":100
			    }
			}]
		}];
		$('#basicdata_menu').tree({
			onClick:clickmenu
		});
		$('#basicdata_menu').tree("loadData",basicDataMenuData);
		//表单设计菜单
		$('#form_menu').tree({
			//url: 'form/form!menu.action?parentId=1',	
			url: 'datatable/dataObjectGroup!getAllItemByParentId.action?parentId=1',	
			onClick:function(node){
				//if(node.attributes && node.attributes == '1'){//数据对象
				node.attributes = {
			      	//"url":"form/form!toList.action?parentId="+node.id,
			      	"url":"form/form!menuDOList.action?groupId="+node.id,
			      	"price":100
				};	
				//}	
				clickmenu(node);
			},
			onBeforeExpand:function(node){
				//$('#form_menu').tree('options').url = "form/form!menu.action?parentId=" + node.id;
				$('#form_menu').tree('options').url = "datatable/dataObjectGroup!getAllItemByParentId.action?parentId=" + node.id;		
			}
		});
		//$('#form_menu').tree("loadData",formMenuData);
		//menu click
		function clickmenu(node){
			clearDom();
			document.getElementById("main").innerHTML="";
			//alert(node.target.text);	
			if(node.target.name == "dataobject"){
				$("#datasource_menu").tree("select","");
				$("#basicdata_menu").tree("select","");				
			}else if(node.text == "数据源管理"){
				$("#dataobject_menu").tree("select","");
				$("#basicdata_menu").tree("select","");
			}else{
				$("#dataobject_menu").tree("select","");
				$("#datasource_menu").tree("select","");
			}
			if(node.attributes && node.attributes != null){			
			  	var url =  node.attributes.url;
			  	if(url!=null&&url!=""){
					$("#main").panel({href :url});
					$("#main").panel("refresh");
				}
			}
		}
	});
</script>
<div class="easyui-accordion" fit="true" border="false">
	<div title="基础数据管理" icon="icon-reload" selected="true" class="menu1" style="margin-top: 5px;">
		<ul id="datasource_menu"></ul>		
		<ul id="dataobject_menu"></ul>		
		<ul id="basicdata_menu"></ul>		
	</div>	
	<div title="表单管理" icon="icon-reload" class="menu1">
		<ul id="form_menu"></ul>
	</div>
	<div title="工作流表单配置" icon="icon-reload"   split="true" style="width:180px;">
		<ul id="lt11"></ul>
		<script type="text/javascript">
			$(function(){
				$('#lt11').tree({
					url: 'jsondata/WF_Grid_data.json',
					onClick:function(node){
						clearDom();
						document.getElementById("main").innerHTML="";
					  	var url =  node.attributes.url;
						if(url!=null&&url!=""){
						$("#main").panel({href :url});
						$("#main").panel("refresh");
						}
					}
				});
			});
		</script>
	</div>
	
	
	
<div title="表单权限配置" icon="icon-reload"   split="true" style="width:180px;">
		<ul id="lt10"></ul>
		<script type="text/javascript">
			$(function(){
				$('#lt10').tree({
					url: 'jsondata/right_Grid_data.json',
					onClick:function(node){
						clearDom();
						document.getElementById("main").innerHTML="";
					  	var url =  node.attributes.url;
						if(url!=null&&url!=""){
						$("#main").panel({href :url});
						$("#main").panel("refresh");
						}
					}
				});
			});
		</script>
</div>
	
	<div title="代码生成" icon="icon-reload"   style="padding:10px;">
		<ul id="lt4"></ul>
		<script type="text/javascript">
		var codeMenuData = [{
								"id":10,
								"text":"代码生成",
								"iconCls":"icon-ok",
								"attributes":{
							            "url":"codegenerate/codeGenerate.action",
							            "price":100
							        }
							}];
			$(function(){
				$('#lt4').tree({
					onClick:function(node){
						clearDom();
						document.getElementById("main").innerHTML="";
					  	var url =  node.attributes.url;
						if(url!=null&&url!=""){
						$("#main").panel({href :url});
						$("#main").panel("refresh");
						}
					}
				});
			});
			
			$('#lt4').tree("loadData",codeMenuData);
		</script>
	</div>
	
	<div title="系统设置" icon="icon-reload"   split="true" style="width:180px;">
		<ul id="lt5"></ul>
		<script type="text/javascript">
			$(function(){
				$('#lt5').tree({
					url: 'jsondata/tree_data.json',
					onClick:function(node){
						clearDom();
						document.getElementById("main").innerHTML="";
					  	var url =  node.attributes.url;
						if(url!=null&&url!=""){
						$("#main").panel({href :url});
						$("#main").panel("refresh");
						}
					}
				});
			});
		</script>
	</div>
	<div title="page页面测试" icon="icon-reload"   style="padding:10px;">
		<ul id="lt61"></ul>
		<script type="text/javascript">
		var codeMenuData = [{
								"id":10,
								"text":"列表页",
								"iconCls":"icon-ok",
								"attributes":{

							             "url":"formengine/pageList.action",
							            "price":100
							        }
							}];
			$(function(){
				$('#lt61').tree({
					onClick:function(node){
						clearDom();
						document.getElementById("main").innerHTML="";
					  	var url =  node.attributes.url;
						if(url!=null&&url!=""){
						$("#main").panel({href :url});
						$("#main").panel("refresh");
						}
					}
				});
			});
			
			$('#lt61').tree("loadData",codeMenuData);
		</script>
	</div>
</div>