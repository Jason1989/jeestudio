<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%
	String type = request.getParameter("type");
	String name = request.getParameter("name");
%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
	<div id="tree-window-${stauts.index}${columnID}" icon="icon-task-show" style="padding:5px;">
		<div class="easyui-layout" fit="true">
			<div region="west" id="tree-ul-${stauts.index}${columnID}" style="width:280px;height:400px;" border="false"></div>
			<div region="center"   border="false">
				<table id="tree-table-${stauts.index}${columnID}" style="width:280px;"></table>
			</div>
		</div>
	</div>
<script>
	$(function(){
	    var flag = ${!treeComponents.isCheckBox};
		$('#tree-table-${stauts.index}${columnID}').datagrid({
					checkbox:true,
					fitColumns: true,
					nowrap:false,
					width:350,
					height:350,
					rownumbers:true,
					singleSelect:flag,
					frozenColumns: [[
						 { field: 'ck', checkbox: true}
					]],
					onClickRow:function(rowIndex, rowData){
						if(flag){
							$('#tree-table-${stauts.index}${columnID}').datagrid('unselectAll');
							$('#tree-table-${stauts.index}${columnID}').datagrid('selectRow',rowIndex);
						}
					},
					columns:[[
						{field:'id',title:'人员编号',width:80,align:'center'},
						{field:'oname',title:'部门',width:150,align:'center'},
						{field:'text',title:'人员姓名',width:100,align:'center'}
					]]
			    });
			    
			    $.parser.parse('#tree-window-${stauts.index}${columnID}');
	})
	var height = 480;
	var width = 700;
	if(document.readyState=="complete"){
			$('#tree-window-${stauts.index}${columnID}').appendTo('body').dialog({
				title:'<%=name%>',
				height:height,
				width:width,
				buttons:[{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						var ids = [];
						var vals = [];
						var rows = $('#tree-table-${stauts.index}${columnID}').datagrid('getSelections');
						for(var i=0;i<rows.length;i++){
							ids.push(rows[i].id);
							vals.push(rows[i].text);
						}
						var values = ids.join(',');
						var texts = vals.join(',');
						$('#editColumn_${stauts.index}${columnID}${editPage.id}').val(texts);
						$('#editColumn_hidden_${stauts.index}${columnID}${editPage.id}').val(values);
						$('#tree-window-${stauts.index}${columnID}').dialog('close');
					}
				},{
					text:'关闭',
					iconCls:'icon-cancel',
					handler:function(){
						$('#tree-window-${stauts.index}${columnID}').dialog('close');
					}
				}]
			});
			$('#tree-window-${stauts.index}${columnID}').dialog('close');
	}
    initTreeDataHuman('${treeComponents.jsonTreeData}','tree-ul-${stauts.index}${columnID}',false,false,'editColumn_${stauts.index}${columnID}${editPage.id}','editColumn_hidden_${stauts.index}${columnID}${editPage.id}','tree-window-${stauts.index}${columnID}');
    /**
  * 初始化树
  */
 function initTreeDataHuman(treeData,treeID,isCheckBox,onlyLeafCheck,toTextID,hiddenID,windowID){
  
  //临时设置
  if(isCheckBox==null||isCheckBox==''){
	  isCheckBox=false;
  }
  if(onlyLeafCheck==null||onlyLeafCheck==''){
	  onlyLeafCheck=false;
  }
  
  	var treeArray=eval(treeData);//初始化数据	
		  $('#'+treeID).tree({
		    data:treeArray,
		    width:280,
	   		onlyLeafCheck:true,
			 // cascadeCheck:true,//当选完父节点。级联选中子节点
			onClick:function(node){
				$('#tree-table-${stauts.index}${columnID}').datagrid({
			    	url: 'com_*!queryForHumanList.action?orgid='+node.id+'&state='+node.state+'&dictionaryId=${treeComponents.dictionaryId}'
			    });
			}
		});	
   } 
   
</script>	