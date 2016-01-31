<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%
	String type = request.getParameter("type");
	String name = request.getParameter("name");
%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
	<!-- <div id="tree-window-${stauts.index}${columnID}" class="easyui-window"
	title="ajaxbox" style="padding: 5px; background: #fafafa;"
	closed="true">
<div class="easyui-layout" fit="true">
		<div region="center" border="false"
			style="padding: 10px; background: #fff; border: 1px solid #ccc; width: 100%; height: 365px;">
			<table width="100%" border="0" cellpadding="5" cellspacing="0">
				<tr>
						  <td><input  type="text" >&nbsp;&nbsp;
						  <img title="搜索" style="cursor:hand"  src="<%= request.getContextPath()%>/css/icons/search.png"/></td>
					  </tr> 
				<tr>
					<td>
						<ul id="tree-ul-${stauts.index}${columnID}"></ul>
					</td>
				</tr>
			</table>
		</div>
		<div region="south" border="false"
			style="text-align: center; height: 30px; line-height: 30px;">
			<c:choose>
				<c:when test="${treeComponents.isCheckBox}">
					<a id="button-ok-${stauts.index}${columnID}"
						class="easyui-linkbutton" icon="icon-ok"
						href="javascript:valueToField('tree-ul-${stauts.index}${columnID}','editColumn_${stauts.index}${columnID}${editPage.id}','editColumn_hidden_${stauts.index}${columnID}${editPage.id}','tree-window-${stauts.index}${columnID}',${treeComponents.onlyLeafCheck});"></a>&nbsp;&nbsp;&nbsp;
				     </c:when>
			</c:choose>
			<a id="button-close-${stauts.index}${columnID}"
				class="easyui-linkbutton" icon="icon-cancel"
				href="javascript:closeWindow('tree-window-${stauts.index}${columnID}');"></a>
		</div>
</div>
	</div> -->
	<div align="center" id="tree-window-${stauts.index}${columnID}" icon="icon-task-show" style="overflow: hidden;opadding-top:5px;width:550px;height:380px;">
	     <div style="margin-top: 10px;">	
			<dd style="height:250px; width: 150px;float:left;text-align: center;margin-left:10px;">
			 	<fieldset style="height:240px; width: 210px;">
					<legend>
						可选人员
					</legend>
					<div style="height:240px;">
					   <ul id="tree-ul-${stauts.index}${columnID}" style="overflow-y:scroll ;height:240px;"></ul>
					</div>
				</fieldset>
			</dd>
			<dd style="height:250px; width: 80px;float:left; text-align: center;vertical-align: middle;margin-top: 80px">	
				<input type="button" value='&nbsp;&nbsp;>>&nbsp;&nbsp;'  onclick="getTreeValue()" style= "cursor:hand;text-align: center ; font-family:'Arial'; font-size: 12px; color:#000000; font-style:normal; font-weight:normal; text-decoration:none "><br/><br/>
				<input type="button" value='&nbsp;&nbsp;<<&nbsp;&nbsp;'  onclick="removeTreeValue()" style= "cursor:hand ;text-align: center ; font-family:'Arial'; font-size: 12px; color:#000000; font-style:normal; font-weight:normal; text-decoration:none">	
			</dd>
			<dd style="height:250px; width: 150px;text-align: center;">		
				<fieldset style="height:240px; width: 210px;">
					<legend>
						已选人员
					</legend>
					<div style="height:240px;">
					   <select id="tree-ul-${stauts.index}${columnID}bak" multiple="multiple" style="height:240px;width: 190px;">
					   </select>
					</div>
				</fieldset>
			</dd>
		</div>
	</div>
<script>
	var height = 450;
	var width = 725;
	if('orgtree'=='<%=type%>'){
		height = 380;
		width = 550;
	}
	
	//获得选择值
	function getTreeValue(){
	    $('#tree-ul-${stauts.index}${columnID}bak').html('');
	   var value= getAllCheckedTreeForName('tree-ul-${stauts.index}${columnID}',${treeComponents.onlyLeafCheck});
	   var treeids=getAllCheckedTree('tree-ul-${stauts.index}${columnID}',${treeComponents.onlyLeafCheck});
	   var ids=treeids.split(',');
	   var names=value.split(',');
	   
	   var divHtml='';
	   
	   for(var i=0;i<names.length;i++){
	      var name=names[i];
	      var id=ids[i];
	      divHtml+='<option value='+id+'>'+name+'</option>';
	     
	   }
	   $('#tree-ul-${stauts.index}${columnID}bak').html(divHtml);
	}
	//移除选择值
	function removeTreeValue(){
	
	  var selectValue= $('#tree-ul-${stauts.index}${columnID}bak').val();
	  $("#tree-ul-${stauts.index}${columnID}bak option:selected").remove();
	     if(selectValue!=null&&selectValue!=''){
	     
	          var vals=(selectValue+'').split(',');
		  
			  for(var i=0;i<vals.length;i++){
			     
			     var obj=$('#tree-ul-${stauts.index}${columnID}').tree('find', vals[i]);
			     $('#tree-ul-${stauts.index}${columnID}').tree('uncheck', obj.target);
			  }
	        
	     }
	 
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
					
					    var selectObj= $('#tree-ul-${stauts.index}${columnID}bak option').toArray();
					    var html='';
					    for(var i=0;i<selectObj.length;i++){
					      var text= $(selectObj[i]).text();
					       if (html != ''){
				                html += ',';
				            }
					      html+=text;
					    }
					    
						valueToFiedSingleChooseTree('editColumn_${stauts.index}${columnID}${editPage.id}',html,'tree-window-${stauts.index}${columnID}');
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
				title:'<%=name%>',
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