
/**
 *	切换编辑列 扩展属性
 */
function changeExtendedAttributes(parentDiv,childDiv){
    $("#"+parentDiv+" > div").hide();
    if(childDiv && childDiv != ''){
        $("#"+childDiv).show();
    }
    return;
    /**
		var parentObj=document.getElementById(parentDiv);
		var childNodes=parentObj.childNodes;
		var temChildNode=null;
		
		for(var i=0;i<childNodes.length;i++){
			temChildNode=childNodes[i]
			temChildNode.style.display='none';
		}
	
		if(document.getElementById(childDiv)){
			document.getElementById(childDiv).style.display='block';
		}
	**/
}

function editpage_choose_role(id,name,wid){
	var url = 'pages/form/pageset/editpageset_datacolumn_role.jsp?wid='+wid+'&r_id='+id+'&r_name='+name;
	var data = $('#'+id).val();
	if(data && data!='' && data!=null){
		url += '&data='+data;
	}
	$('#'+wid).window({href:''}).window('refresh').window({href:url}).window('open');
}