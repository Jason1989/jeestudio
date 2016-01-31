var fmPagesetGroupTypeData = [
	{id:'-1',text:'请选择'},
	{id:'0',text:'上下'},
	{id:'1',text:'左右'}
];
var fmPagesetStaticParamTypeData = [
	//0 --systemParam
	{id:'-1',text:'请选择'},
	{id:'1',text:'手工输入'},
	{id:'2',text:'自动计算'}
];
var fmPagesetDatacolumnAlignData = [
	{id:'-1',text:'请选择'},
    {id:'left',text:'居左'},
    {id:'center',text:'居中'},
    {id:'right',text:'居右'}
];
var fmPagesetFormulaData = [
	{id:'-1',text:'无'},
    {id:'0',text:'公式1'},
    {id:'1',text:'公式2'}
];
var fmPagesetDataFormatData = [
	{id:'-1',text:'无'},
    {id:'0',text:'日期'},
    {id:'1',text:'货币'}
];
var fmPagesetFunctionData = [
	{id:'-1',text:'无'},
    {id:'0',text:'函数1'},
    {id:'1',text:'函数2'}
   
];

//-----------TABS BEGIN----------

//tabs add
function fmPagesetTabssetAddItem(cloneObject,imgup,imgdown){	
	var flag = false;
	if(cloneObject.css("display") == 'none'){
		flag = true;
		cloneObject.css("display",'');
	}
	cloneObject.attr("id","");	
	var elementTd = $(cloneObject).find("td");
	for(var j=0;j<elementTd.length;j++){			
		if(j<2)
			pageSetElementSerialNumber(elementTd.eq(j).find("input"));
		else if(j==4){
			var inputSort = elementTd.eq(j).find("input");
			pageSetElementSerialNumber(inputSort);
			$(inputSort).val(parseInt($(inputSort).val())+1);
			//alert($(inputSort).val());
		}else if(j==2 || j==3){
			pageSetElementSerialNumber(elementTd.eq(j).find("select"));
			elementTd.eq(j).find("span").remove();
		}else if(j==5){
			var imgUp = elementTd.eq(j).find("img[id^='"+imgup+"']");
			var imgDown = elementTd.eq(j).find("img[id^='"+imgdown+"']");
			pageSetElementSerialNumber(imgUp);
			pageSetElementSerialNumber(imgDown);
			//hrefChild.attr("style","display:''");
			//if(flag)
				//hrefChild.linkbutton();
		}
	}	
	//$("#fm_listpageset_tabspagetype_1").combobox();
	//target.appendChild(a.cloneNode(true));
}
function fmPagesetEditTabssetAddItem(cloneObject,imgup,imgdown){	
	var flag = false;
	if(cloneObject.css("display") == 'none'){
		flag = true;
		cloneObject.css("display",'');
	}
	cloneObject.attr("id","");	
	var elementTd = $(cloneObject).find("td");
	for(var j=0;j<elementTd.length;j++){			
		if(j==0){
			var inputId = elementTd.eq(j).find("input");
			pageSetElementSerialNumber(inputId);
			$(inputId).val(parseInt(1000*Math.random()));
		}else if(j>0 && j<3)
			pageSetElementSerialNumber(elementTd.eq(j).find("input"));
		else if(j==6){
			var inputSort = elementTd.eq(j).find("input");
			pageSetElementSerialNumber(inputSort);
			$(inputSort).val(parseInt($(inputSort).val())+1);
			//alert($(inputSort).val());
		}else if(j>2 && j<6){
			pageSetElementSerialNumber(elementTd.eq(j).find("select"));
			elementTd.eq(j).find("span").remove();
		}else if(j==7){
			var imgUp = elementTd.eq(j).find("img[id^='"+imgup+"']");
			var imgDown = elementTd.eq(j).find("img[id^='"+imgdown+"']");
			pageSetElementSerialNumber(imgUp);
			pageSetElementSerialNumber(imgDown);
			//hrefChild.attr("style","display:''");
			//if(flag)
				//hrefChild.linkbutton();
		}
	}	
	//$("#fm_listpageset_tabspagetype_1").combobox();
	//target.appendChild(a.cloneNode(true));
	
}

//------------TABS END---------------
//------------GROUP END---------------
/*j代表table中第几个td，在原有table中添加td时，最好从相应tr的最后一个td开始添加，否则会打乱td顺利，不利于该方法的判断*/
function fmPagesetGroupsetAdditem(cloneObject,imgup,imgdown){
	var flag = false;
	if(cloneObject.css("display") == 'none'){
		flag = true;
		cloneObject.css("display",'');
	}
	cloneObject.attr("id","");
	var elementTd = $(cloneObject).find("td");
	for(var j=0;j<elementTd.length;j++){			
		if(j==1){
			pageSetElementSerialNumber(elementTd.eq(j).find("input"));
			$(elementTd.eq(j).find("input")).val("");
		}else if(j==3){
			pageSetElementSerialNumber(elementTd.eq(j).find("input"));
		}else if(j==7){
			var inputSort = elementTd.eq(j).find("input");
			pageSetElementSerialNumber(inputSort);
			$(inputSort).val(parseInt($(inputSort).val())+1);
		}else if(j==5||j==9){
			pageSetElementSerialNumber(elementTd.eq(j).find("select"));
			elementTd.eq(j).find("span").remove();
		}else if(j==10){
			var imgUp = elementTd.eq(j).find("img[id^='"+imgup+"']");
			var imgDown = elementTd.eq(j).find("img[id^='"+imgdown+"']");
			pageSetElementSerialNumber(imgUp);
			pageSetElementSerialNumber(imgDown);
			//var hrefChild = elementTd.eq(j).find("a");
			//pageSetElementSerialNumber(hrefChild);
			//hrefChild.attr("style","display:''");
			//if(flag)
			//	hrefChild.linkbutton();
		}
	}
}
//------------GROUP END---------------
//------------DATAGRID BEGIN---------------
function fmPagesetDataGridColumnSort(flag,datagridName,rowIndex,rowId){
	var newRowIndex;
	if(flag == 'up'){			
		newRowIndex = rowIndex - 1;
	}else{
		newRowIndex = rowIndex + 1;
	}
	var rows = $('#'+datagridName).datagrid("getRows");
	//alert(rowId);
	var newRowData;
	var rowData = eval('('+rowId+')');
	var temp;
	for(var i=0;i<rows.length;i++){
		if(newRowIndex == (rows[i].sortIndex-1)){
			newRowData = rows[i];
			break;
		}
	}
	for(var j=0;j<rows.length;j++){				
		if(newRowIndex == (rows[j].sortIndex-1) ){
			rows[j] = rowData;
		}else if(rowIndex == (rows[j].sortIndex-1)){
			rows[j] = newRowData;
		}
	}

	$('#'+datagridName).datagrid("refreshRow",newRowIndex);
	$('#'+datagridName).datagrid("refreshRow",rowIndex);

}
function fmPagesetDataGridColumnShowHidden(showGridName,hiddenGridName,rowIndex){
	$('#'+showGridName).datagrid("clearSelections");
	$('#'+showGridName).datagrid("selectRow",rowIndex);
	var row = $('#'+showGridName).datagrid("getSelected");
	$('#'+hiddenGridName).datagrid("appendRow",row);
	$('#'+showGridName).datagrid("deleteRow", rowIndex);			
	$('#'+showGridName).datagrid("acceptChanges");			
	$('#'+hiddenGridName).datagrid("acceptChanges");			

}
//------------DATAGRID END---------------
//------------PARAM BEGIN---------------
function fmPagesetStaticParamAddItem(tbodyName,selectId){
	var tbody = $("#"+tbodyName);	
	var cloneObject = $(tbody.find("tr").last()).clone();	
	var flag = false;
	if(cloneObject.css("display") == 'none'){
		flag = true;
		cloneObject.css("display",'');
	}
	cloneObject.attr("id","");	
	var elementTd = $(cloneObject).find("td");
	for(var j=0;j<elementTd.length;j++){			
		if(j==0 || j==2)
			pageSetElementSerialNumber(elementTd.eq(j).find("input"));
		else if(j==1){
			pageSetElementSerialNumber(elementTd.eq(j).find("select"));
			elementTd.eq(j).find("span").remove();
		}else if(j==3){
			var hrefChild = elementTd.eq(j).find("a");
			pageSetElementSerialNumber(hrefChild);
			//hrefChild.attr("style","display:''");
			if(flag)
				hrefChild.linkbutton();
		}
	}
	cloneObject.appendTo(tbody);
	var paramTypeSelect = $(cloneObject).find("td select[id^='"+selectId+"']");
	paramTypeSelect.combobox({
		valueField:'id',
		textField:'text',  
       	treeWidth:157,
	    data:fmPagesetStaticParamTypeData
	});
}
function fmPagesetSystemParamAddItem(tbodyName){
	var tbody = $("#"+tbodyName);	
	var cloneObject = $(tbody.find("tr").last()).clone();	
	var flag = false;
	if(cloneObject.css("display") == 'none'){
		flag = true;
		cloneObject.css("display",'');
	}
	cloneObject.attr("id","");
	var elementTd = $(cloneObject).find("td");
	for(var j=0;j<elementTd.length;j++){			
		if(j==0)
			pageSetElementSerialNumber(elementTd.eq(j).find("input"));
		else if(j==2){
			var hrefChild = elementTd.eq(j).find("a");
			pageSetElementSerialNumber(hrefChild);
			//hrefChild.attr("style","display:''");
			if(flag)
				hrefChild.linkbutton();
		}
	}
	cloneObject.appendTo(tbody);

}
//------------PARAM END---------------