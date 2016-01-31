var dataSourceTypeData=[{id:1,text:"ORACLE"},{id:2,text:"SQLSERVER"}];
var dataObjectTypeData=[{id:-1,text:"请选择"},{id:1,text:"表"},{id:2,text:"视图"}];
var dataObjectStateData=[{id:-1,text:"请选择"},{id:1,text:"暂存"},{id:2,text:"就绪"},{id:3,text:"停用"}];
var dataColumnDataTypeData=[{id:"varchar2",text:"varchar2"},{id:"varchar",text:"varchar"},{id:"nvarchar",text:"nvarchar"},{id:"nvarchar2",text:"nvarchar2"},{id:"text",text:"text"},{id:"char",text:"char"},{id:"number",text:"number"},{id:"datetime",text:"datetime"}];
var dataColumnScaleData=[{id:"1",text:"1"},{id:"2",text:"2"},{id:"3",text:"3"}];
function parseNameToVarName(b){var a=b.split("_");var d=b;if(a.length>1){d="";for(var c=0;c<a.length;c++){if(a[c]&&a[c].length>0){if(c==0){d+=a[c].toLowerCase()}else{d+=a[c].substring(0,1).toUpperCase()+a[c].substring(1).toLowerCase()}}}}return d}
function refreshDataObjectGroup(){$.post("menu/menu!refreshDataObjectMap.action",{},function(a){refreshLeftMenu()})};