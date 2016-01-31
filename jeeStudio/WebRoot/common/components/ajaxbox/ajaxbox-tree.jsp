<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%
	String type = request.getParameter("type");
	String name = request.getParameter("name");
%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
	<div id="tree-window-${stauts.index}${columnID}" icon="icon-task-show" style="padding:5px;width:400px;height:200px;">
		<ul id="tree-ul-${stauts.index}${columnID}"></ul>
	</div>
<script>
	var height = 450;
	var width = 725;
	if('orgtree'=='<%=type%>'){
		height = 480;
		width = 280;
	}
	if(document.readyState=="complete"){
		if('true'=='${treeComponents.isCheckBox}'){
			$('#tree-window-${stauts.index}${columnID}').appendTo('body').dialog({
				title:'<%=name%>',
				height:height,
				width:width,
				buttons:[{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						valueToField('tree-ul-${stauts.index}${columnID}','editColumn_${stauts.index}${columnID}${editPage.id}','editColumn_hidden_${stauts.index}${columnID}${editPage.id}','tree-window-${stauts.index}${columnID}',${treeComponents.onlyLeafCheck});
					}
				},{
					text:'关闭',
					iconCls:'icon-cancel',
					handler:function(){
						$('#tree-window-${stauts.index}${columnID}').dialog('close');
					}
				}]
			});
		}else{
			$('#tree-window-${stauts.index}${columnID}').appendTo('body').dialog({
				title:'${textColumn.name }',
				height:height,
				width:width,
				buttons:[{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						valueToFieldSingle('tree-ul-${stauts.index}${columnID}','editColumn_${stauts.index}${columnID}${editPage.id}','editColumn_hidden_${stauts.index}${columnID}${editPage.id}','tree-window-${stauts.index}${columnID}',${treeComponents.onlyLeafCheck});
					}
				},{
					text:'关闭',
					iconCls:'icon-cancel',
					handler:function(){
						$('#tree-window-${stauts.index}${columnID}').dialog('close');
					}
				}]
			});
		}
		$('#tree-window-${stauts.index}${columnID}').dialog('close');
	}
    initTreeData('${treeComponents.jsonTreeData}','tree-ul-${stauts.index}${columnID}',${treeComponents.isCheckBox},${treeComponents.onlyLeafCheck},'editColumn_${stauts.index}${columnID}${editPage.id}','editColumn_hidden_${stauts.index}${columnID}${editPage.id}','tree-window-${stauts.index}${columnID}');
</script>	