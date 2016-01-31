//data
//操作列
var LIST_BUTTON_SINGLE_ADD = '11';
var LIST_BUTTON_SINGLE_DELETE = '12';
var LIST_BUTTON_SINGLE_UPDATE = '13';
var LIST_BUTTON_SINGLE_VIEW = '14';   
var LIST_BUTTON_SINGLE_COPY = '15'; 
//按钮
var LIST_BUTTON_MUTI_ADD = '1';
var LIST_BUTTON_MUTI_DELETE = '2';
var LIST_BUTTON_MUTI_UPDATE = '3';
var LIST_BUTTON_MUTI_VIEW = '5';
var LIST_BUTTON_QUERY = '4';
var LIST_BUTTON_IMPORT = '6';
var LIST_BUTTON_EXPORT = '7';
		
var EDIT_BUTTON_CANCEL = "21";  //取消按钮
var EDIT_BUTTON_SAVE = "22";  //保存按钮

var VIEW_BUTTON_CANCEL = "31";  //取消按钮

var joinCoditionObject = {'1':'=','2':'>','3':'<','4':'LIKE','5':'IN'};

var CONFIGYES = "<font size='3px;' color='#6ff355' title='已配置'>√<font>";
var CONFIGNO = "<font size='4px;' color='red' title='未配置'>×<font>";
var NOTTHISCONFIG = "<font title='无此配置项'>无<font>";
var fmSelecttableJoinOperData = [
	{id:'-1',text:'请选择'},
	{id:'1',text:'等于'},
	{id:'2',text:'大于'},
	{id:'3',text:'小于'}	,
	{id:'4',text:'like'},
	{id:'5',text:'in'},
	{id:'6',text:'between'}
];
var fmPageTagTypeData = [
	{id:'-1',text:'请选择'},
	{id:'1',text:'文本框'},
	{id:'2',text:'下拉框'},
	{id:'3',text:'文本域'},
	{id:'4',text:'日历控件'},
	{id:'5',text:'FCKeditor'},
	{id:'6',text:'树控件'},
	{id:'9',text:'上传控件'},
	{id:'11',text:'隐藏域'}, 
	{id:'12',text:'单选按钮'},
	{id:'13',text:'复选框'},
	{id:'16',text:'时间控件（时分秒）'},
	{id:'17',text:'表格控件'},
	{id:'18',text:'组织机构控件'},
	{id:'21',text:'应急选择树控件'},
	{id:'22',text:'人员列表树控件'}
];

var COM_QUERY_TYPE = [
	{id:'-1',text:'请选择'},
	{id:'1',text:'文本框'},
	{id:'2',text:'下拉框'},
	{id:'4',text:'日历控件'},
	{id:'6',text:'树控件'}
];
/**
 *  change id
 */
function pageSetElementSerialNumber(obj){
	if(obj){
		var idIndex = obj.attr('id');			
		var newId = "";
		if(idIndex){
			var lastIndex = idIndex.lastIndexOf("_");
			var idStr = idIndex.substring(0,lastIndex+1);
			var idNumber = idIndex.substring(lastIndex+1);
			newId = idStr + (parseInt(idNumber) + 1);
		}
		obj.attr('id',newId);
	}
}
/**
 *  change Name
 */
function pageSetElementChangeName(obj){
	if(obj){
		var name = obj.attr('name');			
		var newName = "";
		if(nameStr){
			var lastIndex = name.lastIndexOf("_");
			var nameStr = name.substring(0,lastIndex+1);
			var nameNumber = name.substring(lastIndex+1);
			newName = nameStr + (parseInt(nameNumber) + 1);
		}
		obj.attr('name',newName);
	}
}
/**
 *	deleteitem
 */
function fmPagesetDynamicDeleteItem(obj){			
	$(obj.parentNode.parentNode).remove();
}
function fmPagesetSortDynamicDeleteItem(obj,tbody,upimg,downimg){			
	$(obj.parentNode.parentNode).remove();
	if(tbody != "")
		fmTableRowsSortImgHidden(tbody,upimg,downimg);	
}
function fmEditPagesetSortDynamicDeleteItem(obj,tbody,upimg,downimg){			
	fmPagesetSortDynamicDeleteItem(obj,tbody,upimg,downimg);
	fmEditTabsInfoToArray();
}
/**
 * table rows sort
 * flag up : 1,down :0,
 * operInd operate td cell,
 * sortInd sortIndex td cell,
 */
function fmTableRowsSort(obj,flag,sortInd,operInd){
	var tBody = obj.parentNode.parentNode.parentNode;
	var trs = tBody.rows;   
	var trl= trs.length; 
	var a = new Array();   
	var b = new Array();   
	var c = new Array();
	for (var i = 0; i < trl; i++) { 
		var tds = trs[i].cells;
		b[i] = $(tds[operInd]).html();
		var children = tds[sortInd].childNodes;
		c[i] = $(children[0]).val();
		a[i] = trs[i];   
	}   
	for (var i = 0; i < trl; i++) { 				
		if(obj.parentNode.parentNode == a[i]){
			var j;
			if(flag == 1){
				j = i-1;
			}else{
				j = i+1;
			}
			if(j<0)j=0;
			if(j>trl-1)j=trl-1;
			var temp = a[i];
			a[i] = a[j];
			a[j] = temp;
			break;
		}
	}  
	for (var i = 0; i < trl; i++) {   
		tBody.appendChild(a[i]);   				
	}
	for (var i = 0; i < trl; i++) { 				
		var tds = trs[i].cells;
		$(tds[operInd]).html(b[i]);				
		var children = tds[sortInd].childNodes;
		$(children[0]).val(c[i]);
	}
	
}		
function fmTableRowsSortImgHidden(tbody,upImg,downImg){
	var tbodyLength = $("#"+tbody+" tr").size();
	$("#"+tbody+" tr").each(function(i,node){ 
		var tabsUpEle = $(node).find("td img[id^='"+upImg+"']");
		var tabsDownEle = $(node).find("td img[id^='"+downImg+"']");					
		if(tbodyLength > 2){
			if(i > 1 && i < tbodyLength-1){
				tabsUpEle.css("display",'');
				tabsDownEle.css("margin-left",'0px');
				tabsUpEle.css("margin-right",'0px');
				tabsDownEle.css("display",'');
			}else if(i==1){
				tabsUpEle.css("display",'none');
				tabsDownEle.css("display",'');
				tabsDownEle.css("margin-left",'23px');
			}else if(i == tbodyLength-1){
				tabsUpEle.css("display",'');
				tabsUpEle.css("margin-right",'21px');
				tabsDownEle.css("display",'none');
			}
		}else{
			tabsUpEle.css("display",'none');
			tabsDownEle.css("display",'none');
		}
	});
}	