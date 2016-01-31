<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>

<c:choose>
	<c:when test="${(type=='' || type==1 || type==17) && textColumn.is_listPageForvalue}">
		<script>
			if(document.readyState=="complete"){
				var id = 'editColumn_${stauts.index}${columnID}${editPage.id}';
				var url = LISTPAGE_LOAD_SELECT+'${textColumn.pageurl_listPage}&listvalue=${textColumn.list_value}&listtext=${textColumn.list_text}&id='+id;
				var editcolumn_input_${stauts.index}${columnID}${editPage.id} = $("#editColumn_${stauts.index}${columnID}${editPage.id}").parent().html();
				editcolumn_input_${stauts.index}${columnID}${editPage.id} = editcolumn_input_${stauts.index}${columnID}${editPage.id}.replace('editColumn_${stauts.index}${columnID}${editPage.id}','editColumn_${stauts.index}${columnID}${editPage.id}_block');
				editcolumn_input_${stauts.index}${columnID}${editPage.id} = editcolumn_input_${stauts.index}${columnID}${editPage.id}.replace('&nbsp;&nbsp;<FONT color=red size=2>*</FONT>','');
				$("#editColumn_${stauts.index}${columnID}${editPage.id}").css('display','none').parent().append(
					editcolumn_input_${stauts.index}${columnID}${editPage.id}+'<img src="<%=request.getContextPath() %>/images/ioc-addrender.gif" onclick=OpenEditPage("${textColumn.name }",600,490,"'+url+'") style="cursor:hand;vertical-align:middle;" title="选择列表页数据">'+
					'<img src="<%=request.getContextPath() %>/images/ioc-delete.gif" style="cursor:hand;vertical-align:middle;" onclick=ClearListValue() title="清空">');
				var content = $("#editColumn_${stauts.index}${columnID}${editPage.id}").css('display','none').parent().html();
				content = content.replace('&nbsp;&nbsp;<FONT color=red size=2>*</FONT>','');
				$("#editColumn_${stauts.index}${columnID}${editPage.id}").parent().html(content);
				$('#editColumn_${stauts.index}${columnID}${editPage.id}_block').attr('readonly','readonly');
				if("${editMode.required}"){
					$("#editColumn_${stauts.index}${columnID}${editPage.id}").parent().append('<font size="2" color="red">*</font>');
				}	
				$('#editColumn_${stauts.index}${columnID}${editPage.id}_block').val('${dictionary.dictionaryName}');
			
			}
			function ClearListValue(){
				$('#editColumn_${stauts.index}${columnID}${editPage.id}').val('');
				$('#editColumn_${stauts.index}${columnID}${editPage.id}_block').val('');
			}
		</script>
	</c:when>
</c:choose>
  
  