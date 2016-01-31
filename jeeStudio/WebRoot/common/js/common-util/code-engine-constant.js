//var PROJECTNAME='http://172.16.102.53:8081/compplatform/';//跨域的地址
var PROJECTNAME=getProjectName();//不跨域的时候

var LOAD_EDITPAGE_URL = PROJECTNAME+"formengine/editPageAction.action?";//加载编辑页数据
var DELETE_DATA_URL = PROJECTNAME+"com_deleteData.action?";//单行删除
var LOAD_VIEW_URL = PROJECTNAME+"formengine/viewPageAction.action?";//加载查看页
var BULKDELETE_DATA_URL = PROJECTNAME+"com_bulkDelete.action?";//批量删除

//var ENGFORM_SYSTEM_PATH = "/compplatform/";
var WORKFLOW_INFO = PROJECTNAME+"formengine/zsf_findWorkFlowViewInfo.action";
var LISTPAGE_LOAD='formengine/listPageAction.action?formId=';
var LISTPAGE_LOAD_SELECT = 'formengine/listPageForSelectAction.action?formId=';
var LOAD_PROGRESS_URL='zsf_uploadProgressinfo.action';//上传连接

/**
 * 工作流待办项 打开详情页面
 */
var WORKFLOW_INFO='workItemInfo_showWorkItemInfo.action';
function getProjectName() {   
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp   
	var curWwwPath = window.document.location.href;    
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp   
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);    
    //获取主机地址，如： http://localhost:8083  
	var localhostPaht = curWwwPath.substring(0, pos);    
      //获取带"/"的项目名，如：/uimcardprj    
	var projectName = pathName.substring(0, pathName.substr(1).indexOf("/") + 1);
	return projectName+"/";
}

function getRootPath() {   
     //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp   
	var curWwwPath = window.document.location.href;    
     //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp   
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);    
     //获取主机地址，如： http://localhost:8083  
	var localhostPaht = curWwwPath.substring(0, pos);    
     //获取带"/"的项目名，如：/uimcardprj    
	var projectName = pathName.substring(0, pathName.substr(1).indexOf("/") + 1);
	return (localhostPaht + projectName);
}
//是否设置变量--变量类型
var  VARIANT_TYPE =  '[{"id":"0","text":"常量","selected":true},{"id":"1","text":"系统变量"}]'
/**
 * 创建表单检查是否有主键
 */
var  check_parmerKey='';