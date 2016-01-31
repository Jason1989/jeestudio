<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script>
	function chooseCopyOrganization(){
	
		document.getElementById('repliorgtext').value= getAllCheckedTreeForName('repltree',false);
		document.getElementById('repliorgno').value =getAllCheckedTree('repltree',false);
		$('#orgreplication').window('close');
	}
	
	
     $('#repltree').tree({
				checkbox: true,	
				cascadeCheck:false,
				fit:true,							
				url: 'organization/organization!getAllOrganizations.action?dcis='+new Date().getTime(),
				onClick:function(node){
					
					var id = node.id;
					var nodes = $('#repltree').tree('getChecked');
					var idsArr = [];
					var textArr = [];
						for(var i=0;i<nodes.length;i++){
							if(id==nodes[i].id){
								idsArr.push(nodes[i].id);
								textArr.push(nodes[i].text);
								for(var j=i+1;j<nodes.length;j++){
									idsArr.push(nodes[j].id);
									textArr.push(nodes[j].text);
								}
							  break;
							}
						}
					var ids = idsArr.join(',');
					var texts = textArr.join(',');
					if(node){
						document.getElementById('repliorgtext').value = texts;
						document.getElementById('repliorgno').value = ids;
						//$('#orgreplication').window('close');
					}
					
				},
				onLoadSuccess: function(){
					setTimeout(function(){
						var node = $('#repltree').tree('getRoot');
						$('#repltree').tree('expand',node.target);
					},100);
				}
			});
</script>
   
<div class="easyui-layout" fit="true">
	<div region="center"   border="false" style="padding:10px;background:#fff;border:1px solid #ccc;height: 210px;">
		 <ul id="repltree"></ul>
	</div>
	
		<div region="south" border="false" style="text-align:center;height:45px;padding-top: 13px;">
			<a href="javascript:chooseCopyOrganization();" class="easyui-linkbutton" icon="icon-save" >确定</a>
		</div>
	 
	
</div>
 