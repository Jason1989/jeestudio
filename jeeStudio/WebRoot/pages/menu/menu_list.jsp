<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>菜单管理</title>
		<script type="text/javascript">
		$(function(){
			$('#menuManagement').layout();		
			$('#tt2').tree({
				url: 'menu/menu!list.action',
				onClick:function(node){
					$("#treeName").val(node.text);				
					$("#nodeURL").val(node.attributes.url);
					$("#nodeDesc").val(node.attributes.desc);
					$("#nodeSort").val(node.attributes.sort);
					$("#nodeId").val(node.id);
					$("#parentNodeId").val(node.id);
					$("#parentNodeType").val(node.attributes.isleaf);
					//alert("节点ID："+node.id+" 是否是叶子节点："+node.attributes.isleaf);			
					if(node.attributes.enabled=='false'){					
						$("#nodeEnabled").attr("checked",false); 
					}else{
						$("#nodeEnabled").attr("checked",true);
					}
				}
			});
			$('#tt').tabs();
			$('#childNodeForm').form({ 
				url:"ou2/menu?method=add", 
				onSubmit: function(){ 
					if(false==append($("#childNodeName").val(),
										$("#parentNodeId").val(),
										$("#childNodeURL").val(),
										$("#childNodeDesc").val(),
										$("#childNodeSort").val(),
										$("#childNodeEnabled").attr("checked")))
						return false;
				}, 
				success:function(data){ 
					alert(data) ;
				} 
			});	
			$('#nodeForm').form({ 
				url:"ou2/menu?method=update", 
				onSubmit: function(){ 
					if(false==updateNode($("#nodeId").val(),
							$("#treeName").val(),
							$("#nodeURL").val(),
							$("#nodeDesc").val(),
							$("#nodeSort").val(),
							$("#nodeEnabled").attr("checked")))  
					return false;
				}, 
				success:function(data){ 
					alert(data) ;
				} 
			});	
		});
		function reload(){
			$('#tt2').tree('reload');
		}
		function getSelected(){
			var node = $('#tt2').tree('getSelected');
			alert(node.text);
		}
		function collapse(){
			var node = $('#tt2').tree('getSelected');
			$('#tt2').tree('collapse',node.target);
		}
		function expand(){
			var node = $('#tt2').tree('getSelected');
			$('#tt2').tree('expand',node.target);
		}
		function append(name,parentid,url,desc,sort,enable){
			var node = $('#tt2').tree('getSelected');
			if(node==null){
				alert("请选中一个节点！");
				return false;
			}
			if($.trim(name)==''){
				alert('节点名称不能为空！');
				return false;
			}
			if(!checkIsInteger($.trim(sort))){
				alert('节点顺序应该是数字！');
				return false;
			}
			$('#tt2').tree('append',{
				parent: node.target,
				data:[{
					text:$.trim(name),
					attributes:{
						"parentid":parentid,
						"url":$.trim(url),
						"desc":$.trim(desc),
						"sort":$.trim(sort),
						"enabled":enable
					}
				}]
			});
			return true;
		}
		function remove(name){
			var node = $('#tt2').tree('getSelected');
			$('#tt2').tree('remove', node.target);
		}
		function updateNode(menuid,name,url,desc,sort,enable) {		
			var node = $('#tt2').tree('getSelected');		
			if (node) {
			//alert(node.attributes.enabled+" enabled:"+enable);
				node.id = menuid;
				node.text = name;
				node.attributes.url=url;
				node.attributes.desc=desc;
				node.attributes.sort=sort;
				node.attributes.enabled=enable;		
				$('#tt2').tree('update', node);
				return true;
			}else{
				return false;
			}
		}
		function isLeaf() {
			var node = $('#tt2').tree('getSelected');
			var b = $('#tt2').tree('isLeaf', node.target);
		}
		function checkIsInteger(str){          
           	if(/^(\-?)(\d+)$/.test(str)) 
           		return true; 
         	else 
           		return false; 
        }
		</script>
	</head>
	 <body>
				<div id="menuManagement" class="easyui-layout" fit="true">		
				<div region="east" icon="icon-reload" title="编辑" split="true" style="width:700px">
					<div id="tt" class="easyui-tabs" fit="true">
						<div id="nodeDIV" title="当前节点" style="padding:20px">
							<form id="nodeForm" name="nodeForm" method="post" action="">
							  	<input type="hidden" id="nodeId" name="nodeId"/>
							    <label>节点名称
							    <input type="text" id="treeName" name="treeName" />
							    </label>
						            <p>
						              <label>URL
						              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="nodeURL" name="nodeURL" />
						              </label>
						        </p>
						            <p>
						              <label>节点描述
						              <input type="text" id="nodeDesc" name="nodeDesc" />
						              </label>
						            </p>
						            <p>
						              <label>节点顺序
						              <input name="nodeSort" id="nodeSort" type="text"/>
						              </label>              
						            </p>
						            <p>
						              <label>
						              <input name="nodeEnabled" id="nodeEnabled" type="checkbox" value="1" checked="checked" />
						              是否启用</label>
						              
						            </p>
						            <p>
						              <label>			  
									  <input type="submit" name="update" value="修改" />
						              </label>
						            </p>
							  </form>
						</div>
						<div id="childNodeDIV" title="子节点" style="padding:20px;height:480px;">
							<form id="childNodeForm" name="childNodeForm" method="post" action="/ou2/menuAdd">
							    <input type="hidden" id="parentNodeId" name="parentNodeId"/>
							    <input type="hidden" id="parentNodeType" name="parentNodeType"/>
							    <label>节点名称
							    <input type="text" id="childNodeName" name="childNodeName" />
							    </label>
						            <p>
						              <label>URL
						              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="childNodeURL" name="childNodeURL" />
						              </label>
						        	</p>
						            <p>
						              <label>节点描述
						              <input type="text" id="childNodeDesc" name="childNodeDesc" />
						              </label>
						            </p>
						            <p>
						              <label>节点顺序
						              <input name="childNodeSort" id="childNodeSort" type="text"/>
						              </label>              
						            </p>
						            <p>
						              <label>
						              <input name="childNodeEnabled" id="childNodeEnabled" type="checkbox" value="1" checked="checked" />
						              是否启用</label>              
						            </p>
						            <p>
						              <label>
						              <input type="submit" name="Submit" value="添加" />
						              </label>
						            </p>
							  </form>		
						</div>
					</div>
				</div> 
				<div region="center" title="菜单列表" style="padding:5px;background:#eee;text-align:left">
					<ul id="tt2"></ul>
				</div> 
			</div>
      
</body>
</html>




