function createViewPageForm(dataObjId){
	/**
	 * 构建列表页XML整体
	 */
	var xmlUtils = new XmlUtils({dataType:'json'}); 
	var formType = 'viewPage';
	createListPageXml(xmlUtils,formType,null,null);
	
	$('#fm_formadd_form').form('submit',{ 
		url:'form/form!addForAll.action?dataObjectId='+dataObjId+'&form.type=viewPage', 
		onSubmit:function(){ 
			var dataObjectInfo = $(":input[name='formSettings']").val();
			if(dataObjectInfo && dataObjectInfo != ''){
				var xmlUtilsTemp = new XmlUtils({dataType:'json'}); 
				xmlUtilsTemp.loadXmlString(dataObjectInfo);
				var dataObjectNodeTemp = xmlUtilsTemp.getRoot();
				xmlUtilsTemp.setAttribute(dataObjectNodeTemp,'description',$(":input[name='form.description']").val());
				$(":input[name='formSettings']").val('<?xml version="1.0" encoding="UTF-8"?>' + xmlUtilsTemp.toString());
			}
			return $(this).form('validate'); 
		}, 
		success:function(data){ 
		    if("exist" == data) {						
				$.messager.alert("提示","表单已存在！", 'warning');
			} else  {
				var responseObject=eval("("+data+")");
				//alert('editPageId: '+responseObject.editPageId);
				//alert('viewPageId: '+responseObject.viewPageId);
				fmformlistpagename = "fm_form_add";	
            	$("#fm_form_add").window({'href':''});		
            	$("#fm_form_add").window('refresh');
            	$("#fm_form_add").window({'href':'form/form!toAddForALL.action?dataObjectId='+dataObjId+'&pageType=listPage&editPageId='+responseObject.editPageId+'&viewPageId='+responseObject.viewPageId+'&random='+parseInt(10*Math.random())});		
            	$("#fm_form_add").window('open');
			}
		} 
	}); 		
}