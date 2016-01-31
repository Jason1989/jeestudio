/**
 * xmlUtils.createNode('EditPage','',{formId:fmEditPageValue,formName:fmEditPageText},listPagesNode);
 * <d><a  c=c  >b<a><d>
 * 被创建节点名a，被创建的节点文本b，节点属性c=c，父节点d
 */

/**
 *  保存列表页字段列
 */
function createListPageForm(dataObjId,editPageId,viewPageId,copyPageId){
	/**
	 * 构建列表页XML整体
	 */
	var xmlUtils = new XmlUtils({dataType:'json'}); 
	var formType = 'listPage';
	createListPageXml(xmlUtils,formType,editPageId,viewPageId,copyPageId);
	
	$('#fm_formadd_form').form('submit',{ 
		url:'form/form!add.action?dataObjectId='+dataObjId+'&form.type=listPage', 
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
			} else if("success" == data) {
				parent.$('#fm_form_add').window('close');
				$('#fm_formlist_table').datagrid('reload');
				$.messager.alert("提示","表单创建成功！", 'info');
			}
		} 
	}); 		
}

/**
 *  创建列表页xml整体。
 */
function createListPageXml(xmlUtils,formType,editPageId,viewPageId,copyPageId){	

	/**
	 * debugger;
	 */

   	var rowsNum = 0;//$(":input[name='rowsNumber']").val();
	var colsNum = 0;//$(":input[name='colsNumber']").val();
	var formDesc = $(":input[name='form.description']").val();
	var dataObject = '<Form version="1.0" title=""  cssClass="" css="" type="'+formType+'" rows="'+rowsNum+'" cols="'+colsNum+'" description="'+formDesc+'" isUseWorkflow="0" workflowId=""></Form>';
	/**
	 *  创建根节点
	 */
	 
	xmlUtils.loadXmlString(dataObject);
	var root = xmlUtils.getRoot();
 	var dataMappingNode = xmlUtils.createNode('DataMapping','',{},root);	
	/**
	 *  创建选中的数据对象 ,字段列
	 */
     createDataObjXML(dataMappingNode,formType,xmlUtils);
    /**
	 *   default  set
	 */
	    
    defaultPageSet(root,formType,xmlUtils);
    
	xmlUtils.createNode('Tabs','',{isConfig:'0'},root);
	xmlUtils.createNode('Params','',{isConfig:'0'},root);			
	xmlUtils.createNode('Pages','',{},root);
	xmlUtils.createNode('Pagination','',{visible:'true'},root);
	xmlUtils.createNode('Groups','',{visible:'false'},root);	
	//xmlUtils.createNode('QueryZone','',{visible:'true',showType:'0'},root);
	//xmlUtils.createNode('Columns','',{},root);	
	//xmlUtils.createNode('Buttons','',{},root);
	   
	/**
	 *   关联页面
	 */
	if(formType=='listPage'){
		var listPagesNode = xmlUtils.getChildNodeByTagName(root,'Pages');
		xmlUtils.createNode('EditPage','',{formId:editPageId,formName:'xxx'},listPagesNode);
		xmlUtils.createNode('ViewPage','',{formId:viewPageId,formName:'xxx'},listPagesNode);
		xmlUtils.createNode('CopyPage','',{formId:copyPageId,formName:'xxx'},listPagesNode);
	}
	
	/**
		//选择编辑页
		if(fmEditPageValue){
			var listEditPageNode = pageSetXmlUtils.getChildNodeByTagName(listPagesNode,'EditPage');
			if(listEditPageNode && listEditPageNode != null){
				pageSetXmlUtils.setAttribute(listEditPageNode,'formId',fmEditPageValue);
				pageSetXmlUtils.setAttribute(listEditPageNode,'formName',fmEditPageText);
			}else{
				pageSetXmlUtils.createNode('EditPage','',{formId:fmEditPageValue,formName:fmEditPageText},listPagesNode);
			}
		}	
		//选择查看页
		var fmViewPageValue = $('#fm_listpageset_selectviewpage').combotree('getValue');
		var fmViewPageText = $('#fm_listpageset_selectviewpage').combotree('getText');
		if(fmViewPageValue){
			var listViewPageNode = pageSetXmlUtils.getChildNodeByTagName(listPagesNode,'ViewPage');
			if(listViewPageNode && listViewPageNode != null){
				pageSetXmlUtils.setAttribute(listViewPageNode,'formId',fmViewPageValue);
				pageSetXmlUtils.setAttribute(listViewPageNode,'formName',fmViewPageText);
			}else{
				pageSetXmlUtils.createNode('ViewPage','',{formId:fmViewPageValue,formName:fmViewPageText},listPagesNode);
			}
		}	
	**/		
		
		/**
		 * listPage xml,填充到文本域
		 */
		$(":input[name='formSettings']").val('<?xml version="1.0" encoding="UTF-8"?>' +　xmlUtils.toString());			
}
/**
 * 创建数据对象XML
 */
function createDataObjXML(node,formType,xmlUtils){
	
	var MainTable=eval(document.getElementById('jsonForDataObj').value);
		
		//alert(MainTable.length);
		//alert(MainTable[0].dataSource.name);
		//alert(MainTable[0].dataSource.dbType);
		//alert(dataCloumnList.length);
		
		var dataSetNode = xmlUtils.createNode('DataSet','',{name:'DATASET1'},node);
		xmlUtils.createNode('DisplayName','DataSet1',{},dataSetNode);
		xmlUtils.createNode('Description','数据集1',{},dataSetNode);
		var dataSourcesNode = xmlUtils.createNode('DataSources','',{},dataSetNode);
		//数据源
		var dataTypeValue = "";
		if(MainTable[0].dataSource.dbType == '1'){
			dataTypeValue = "ORACLE";
		}else if(MainTable[0].dataSource.dbType == '2'){
			dataTypeValue = "SQLSERVER";
		}				
		var dataSourceNode = xmlUtils.createNode('DataSource','',{type:'DB', dataType:dataTypeValue},dataSourcesNode);
		xmlUtils.createNode('DSName',MainTable[0].dataSource.name,{},dataSourceNode);
		xmlUtils.createNode('DisplayName',MainTable[0].dataSource.name,{},dataSourceNode);
		var dataObjectType = "";
		if(MainTable[0].type == '1'){
			dataObjectType = "TABLE";
		}else if(MainTable[0].type == '2'){
			dataObjectType = "VIEW";
		}
		
		
		var tableNode = xmlUtils.createNode('Table','',{type:dataObjectType,name:'table1'},dataSetNode);
		xmlUtils.createNode('DisplayName','table1',{},tableNode);
		xmlUtils.createNode('Description','表1',{},tableNode);
		var getTableNode = xmlUtils.createNode('GetTable','',{type:'COMPONENT'},tableNode);
		
		var editPageToTableNode;
		if('editPage' == formType){
			var SetTableNode = xmlUtils.createNode('SetTable','',{type:'CUSTOM'},tableNode);					
			var SetOptionNode = xmlUtils.createNode('SetOption','',{oprRange:'I,U,D'},SetTableNode);
			var SetOptionItemNode = xmlUtils.createNode('SetOptionItem','',{name:'t',type:'TABLE'},SetOptionNode);
			xmlUtils.createNode('Index','0',{},SetOptionItemNode);
			editPageToTableNode = xmlUtils.createNode('ToTable','',{type:'CUSTOM'},SetOptionItemNode);
		}
		var fromTablesNode = xmlUtils.createNode('FromTables','',{},getTableNode);
	
	/**
	 * 数据表节点 设置
	 */  
	
	var fromTableNode = xmlUtils.createNode('FromTable','',{},fromTablesNode);
	var tableNameNode = xmlUtils.createNode('TableName',MainTable[0].name,{type:'NAME'},fromTableNode);
	xmlUtils.createNode('DSName',MainTable[0].dataSource.name,{},tableNameNode);
	xmlUtils.createNode('STType','TABLE',{},tableNameNode);
	
	xmlUtils.createNode('Index',0,{},fromTableNode);
	if('editPage' == formType && editPageToTableNode != null){
		var editPageTableNameNode = xmlUtils.createNode('TableName',MainTable[0].name,{type:'NAME'},editPageToTableNode);
		xmlUtils.createNode('DSName',MainTable[0].dataSource.name,{},editPageTableNameNode);
		xmlUtils.createNode('STType','TABLE',{},editPageTableNameNode);
	}

	/**
	 * 插入dataGrid
	 * $('#fm_form' + xmlOperateType + '_selectedtables').datagrid("appendRow",tableSelected[i]);
	 */
	
	/**
	 * 重新布局 dataGrid  
	 * $('#fm_form' + xmlOperateType + '_selectedtables').datagrid("acceptChanges");
	 */
		
	//选中数据列
	createDataCloumn(tableNode,formType,editPageToTableNode,xmlUtils);
	// 关联条件
	//  fmJoinConditionAppendToXML(fromTablesNode);
		
	if(formType!='listPage'){
	    //过滤条件
		fiterParmerToXML(getTableNode,xmlUtils);
	}
}
/**
 * 初始化选中的cloumn
 */
function createDataCloumn(node,formType,editPageToTableNode,xmlUtils){
	  /**
	   * 数据列List,数据对象List
	   */
	   var columnSelected=eval(document.getElementById('jsonforDataCloumn').value);
	   var MainTable=eval(document.getElementById('jsonForDataObj').value);
	   /**
	    * 定义数据列对象
	    */
	   var fieldDefNode = xmlUtils.createNode('FieldDef','',{type:'FIX'},node);
	   var mainObjectId =MainTable[0].id;
	   /**
	    * 创建数据列节点
	    */
		for(var i=0;i<columnSelected.length;i++){					
			var fieldNode = xmlUtils.createNode('Field','',{type:'FIX'},fieldDefNode);
			xmlUtils.createNode('DisplayName',columnSelected[i]['title'],{},fieldNode);
			xmlUtils.createNode('Description',columnSelected[i]['description'],{},fieldNode);
			var fromFieldNode = xmlUtils.createNode('FromField','',{type:'FIELD'},fieldNode);
			xmlUtils.createNode('Type',columnSelected[i]['dataType'],{},fromFieldNode);
			xmlUtils.createNode('Length',columnSelected[i]['dataLength'],{},fromFieldNode);
			if(columnSelected[i]['isPrimaryKey'] == '1'){
				xmlUtils.createNode('IsPrimeKey','TRUE',{},fromFieldNode);
			}else{
				xmlUtils.createNode('IsPrimeKey','FALSE',{},fromFieldNode);
			}
			xmlUtils.createNode('IfNull',columnSelected[i]['nullable'],{},fromFieldNode);
			var fieldNameNode = xmlUtils.createNode('FieldName','',{type:'NAME'},fromFieldNode);
			var tableNameNode = xmlUtils.createNode('TableName',columnSelected[i]['dataTable']['name'],{type:'NAME'},fieldNameNode);
			xmlUtils.createNode('DSName',columnSelected[i]['dataTable']['dataSource']['name'],{},tableNameNode);
			xmlUtils.createNode('STType','TABLE',{},tableNameNode);
			xmlUtils.createNode('Index',columnSelected[i]['sortNo'],{},fieldNameNode);
			xmlUtils.createNode('Name',columnSelected[i]['name'],{},fieldNameNode);
			var toFieldNode = xmlUtils.createNode('ToField','',{},fieldNode);
			xmlUtils.createNode('Index',columnSelected[i]['sortNo'],{},toFieldNode);
			xmlUtils.createNode('Name',columnSelected[i]['name'],{},toFieldNode);
			xmlUtils.createNode('Type',columnSelected[i]['dataType'],{},toFieldNode);
			xmlUtils.createNode('Length',columnSelected[i]['dataLength'],{},toFieldNode);
			//编辑页主对象 FieldMapList
			if('editPage' == formType && editPageToTableNode != null && columnSelected[i]['dataTable']['id'] == mainObjectId){
				var editPageFieldMapType = {type:'IN'};
				if(columnSelected[i]['isPrimaryKey'] == '1'){
					editPageFieldMapType = {type:'KEY'};
				}
				var editPageFieldMapNode = xmlUtils.createNode('FieldMap','',editPageFieldMapType,editPageToTableNode);
				var editPageOperandNode = xmlUtils.createNode('Operand','',{type:columnSelected[i]['dataType']},editPageFieldMapNode);
				var editPageOprNameNode = xmlUtils.createNode('OprName','',{type:'NAME'},editPageOperandNode);
				xmlUtils.createNode('Name',columnSelected[i]['name'],{},editPageOprNameNode);
				var editPageOprValueNode = xmlUtils.createNode('OprValue','',{type:'FIELD'},editPageOperandNode);
				var editPageFieldNameNode = xmlUtils.createNode('FieldName','',{type:'NAME'},editPageOprValueNode);
				var editPageTableNameNode = xmlUtils.createNode('TableName',columnSelected[i]['dataTable']['name'],{type:'NAME'},editPageFieldNameNode);
				xmlUtils.createNode('DSName',columnSelected[i]['dataTable']['dataSource']['name'],{},editPageTableNameNode);
				xmlUtils.createNode('STType','TABLE',{},editPageTableNameNode);
				xmlUtils.createNode('Name',columnSelected[i]['name'],{},editPageFieldNameNode);
			}
		//  选中行
		//	$('#fm_form' + xmlOperateType + '_selectedcolumns').datagrid("appendRow",columnSelected[i]);
		}
	//	$('#fm_form' + xmlOperateType + '_selectedcolumns').datagrid("acceptChanges");
}
/**
 * 定义页面默认选中选项（包括操作按钮、数据列、组合查询设置）
 */
function defaultPageSet(rootNode,formType,xmlUtils){
	var QueryZoneNode = xmlUtils.createNode('QueryZone','',{visible:'false',showType:'0',isConfig:'0'},rootNode);
	var ColumnsNode = xmlUtils.createNode('Columns','',{isConfig:'0'},rootNode);
	var ButtonsNode = xmlUtils.createNode('Buttons','',{isConfig:'0'},rootNode);
	var primarykeyColumnName = "";
	var primarykeyColumnType = "";
	//var columnSelected = $('#fm_formselecttable_columns').datagrid("getSelections");
	var columnSelected=eval(document.getElementById('jsonforDataCloumn').value);
	if(columnSelected){
		var columnCount = 0;
		var columnDataType;//数据列类型
		var listColumnCount=0;
		for(var i=0;i<columnSelected.length;i++){					
			//data column
			var ColumnNode = xmlUtils.createNode('Column','',{},ColumnsNode);
			var textVisible = "true";
			var QueryColumnVisible = "true";
			var dataPrimaryKey = "false";
			if(columnSelected[i]['isPrimaryKey'] == '1'){
				primarykeyColumnName += primarykeyColumnName==""? columnSelected[i]['name']:','+columnSelected[i]['name'];
				primarykeyColumnType = columnSelected[i]['dataType'];
				dataPrimaryKey = "true";
				textVisible = "false";
				QueryColumnVisible = "false";
			}else{
				columnCount ++ ;
			}
			
			var textAttr = "";
			var dataAttr = "";
			var	editMode={
				 id:'' ,
				 data:'',
				 reminder:'',
				 required:'',
				 validateRule:''
			}
			
			
			if('listPage' == formType){	
				if((listColumnCount > 5)||(columnSelected[i]['name']=="APP_ID")||(columnSelected[i]['name']=="PARENT_APP_ID")||(columnSelected[i]['name']=="ENV_DATAMETER")||(columnSelected[i]['name']=="ENV_DATASTATE")||(columnSelected[i]['name']=="IS_PSEUDO_DELETED")){
					textVisible = "false";
				}
				textAttr = {
					id:'',
					name:columnSelected[i]['title'],
					align:"",
					dataFormat:"",
					visible:textVisible,
					style:"",
					sort:"0"
				};
				dataAttr = {
					id:"",
					name:columnSelected[i]['name'],
					dictionaryId:"",
					fieldDataType:columnSelected[i]['dataType'],
					primaryKey:dataPrimaryKey,
					formula:""
				};
				listColumnCount++;//计数器  默认显示5列
			}else if('editPage' == formType){
				if((columnSelected[i]['name']=="APP_ID")||(columnSelected[i]['name']=="PARENT_APP_ID")||(columnSelected[i]['name']=="ENV_DATAMETER")||(columnSelected[i]['name']=="ENV_DATASTATE")||(columnSelected[i]['name']=="IS_PSEUDO_DELETED")){
					 textVisible = "false";
				}
				
				/**
				 * 定义数据列类型 数据类型转换 成大写;
				 */
				var columnDataType=columnSelected[i]['dataType'].toUpperCase();
			    var editPageCloumnType=""
			    /**
			     * 生成控件类型，添加相应的验证规则;
			     */
			    //if(columnDataType.indexOf('NUMBER')>0){
			     var validateRule="";
			     var required="";
			     
			     var maxlength="";
			     
			     if(columnDataType.indexOf('DATE')>0){
			    		editPageCloumnType="4";
			     }  else if((columnDataType.indexOf('NUMBER')>0)||(columnDataType.indexOf('INT')>0)){
		    		     validateRule="6";
		     			 required="false";
			     }  else if(columnDataType.indexOf('VARCHAR')>0){
			    	 validateRule="9";
		     		  required="false";
			     	maxlength=columnSelected[i]['dataLength'];
			     }
			    
			// alert(columnSelected[i]['dataType']);
		
				textAttr = {
					id:"",
					name:columnSelected[i]['title'],
					align:"",
					visible:textVisible,
					style:"",
					sortIndex:"0",
					event:"",
					readOnly:"false",
					groupId:"",
					exclusiveLine:"false"
				};
				dataAttr = {							
					id:"",
					name:columnSelected[i]['name'],
					type:editPageCloumnType,
					dictionaryId:"",
					style:"",
					event:"",
					fieldDataType:columnSelected[i]['dataType'],
					primaryKey:dataPrimaryKey,
					formula:"",
					dictionary:"",
					needs:"false"
				};
				
				
			
				editMode={
					 id:'' ,
					 data:'',
					 reminder:'',
					 required:required,
					 validateRule:validateRule,
					 maxLength:maxlength
			     };
	
			}else if('copyPage' == formType){ //复制页
				if((columnSelected[i]['name']=="APP_ID")||(columnSelected[i]['name']=="PARENT_APP_ID")||(columnSelected[i]['name']=="ENV_DATAMETER")||(columnSelected[i]['name']=="ENV_DATASTATE")||(columnSelected[i]['name']=="IS_PSEUDO_DELETED")){
					 textVisible = "false";
				}
				
				/**
				 * 定义数据列类型 数据类型转换 成大写;
				 */
				var columnDataType=columnSelected[i]['dataType'].toUpperCase();
			    var copyPageCloumnType=""
			    /**
			     * 生成控件类型，添加相应的验证规则;
			     */
			    //if(columnDataType.indexOf('NUMBER')>0){
			     var validateRule="";
			     var required="";
			     
			     var maxlength="";
			     
			     if(columnDataType.indexOf('DATE')>0){
			    		copyPageCloumnType="4";
			     }  else if((columnDataType.indexOf('NUMBER')>0)||(columnDataType.indexOf('INT')>0)){
		    		     validateRule="6";
		     			 required="false";
			     }  else if(columnDataType.indexOf('VARCHAR')>0){
			    	 validateRule="9";
		     		  required="false";
			     	maxlength=columnSelected[i]['dataLength'];
			     }
			    
			// alert(columnSelected[i]['dataType']);
		
				textAttr = {
					id:"",
					name:columnSelected[i]['title'],
					align:"",
					visible:textVisible,
					style:"",
					sortIndex:"0",
					event:"",
					readOnly:"false",
					groupId:"",
					exclusiveLine:"false"
				};
				dataAttr = {							
					id:"",
					name:columnSelected[i]['name'],
					type:copyPageCloumnType,
					dictionaryId:"",
					style:"",
					event:"",
					fieldDataType:columnSelected[i]['dataType'],
					primaryKey:dataPrimaryKey,
					formula:"",
					dictionary:"",
					needs:"false"
				};
				
				
			
				copyMode={
					 id:'' ,
					 data:'',
					 reminder:'',
					 required:required,
					 validateRule:validateRule,
					 maxLength:maxlength
			     };
	
			}else if('viewPage' == formType){
				textAttr = {
					id:"",
					name:columnSelected[i]['title'],
					align:"",
					visible:textVisible,
					style:"",
					sortIndex:"0",
					event:"",
					readOnly:"true",
					groupId:"",
					exclusiveLine:"false"
				};	
				dataAttr = {							
					id:"",
					name:columnSelected[i]['name'],
					type:"",
					dictionaryId:"",
					style:"",
					event:"",
					fieldDataType:columnSelected[i]['dataType'],
					primaryKey:dataPrimaryKey,
					formula:"",
					dictionary:"",
					needs:"false"
				};					
			}					
			xmlUtils.createNode('Text','',textAttr,ColumnNode);
			xmlUtils.createNode('Data','',dataAttr,ColumnNode);
			xmlUtils.createNode('EditMode','',editMode,ColumnNode);

			//query column
			if('listPage' == formType){	
				if(columnCount > 3){
					QueryColumnVisible = "false";
				}					
				var QueryColumnAttr = {
					id:"",
					type:"",
					tableName:columnSelected[i]['dataTable']['name'],
					name:columnSelected[i]['name'],
					text:columnSelected[i]['title'],
					fieldDataType:columnSelected[i]['dataType'],
					visible:QueryColumnVisible,
					readOnly:"false",
					cssClass:"",
					sortIndex:"0",
					dictionaryId:"",
					formula:"",
					align:"",
					exclusiveLine:"false",
					operateType:"",
					style:""
				};
				var EditModeAttr = {
					id:"",
					data:"",
					reminder:"",
					validateRule:""
				};
				xmlUtils.setAttribute(QueryZoneNode,'visible','true');
				var QueryColumnNode = xmlUtils.createNode('QueryColumn','',QueryColumnAttr,QueryZoneNode);
				xmlUtils.createNode('EditMode','',EditModeAttr,QueryColumnNode);
			}
		}
	}	
	if('listPage' == formType){
		//查看列
		var viewButtonNode = xmlUtils.createNode('Button','',{id:'',type:LIST_BUTTON_SINGLE_VIEW,name:'view',visible:'true'},ButtonsNode);
		var viewEventNode = xmlUtils.createNode('Event','',{Event:'1',JSId:''},viewButtonNode);
		xmlUtils.createNode('Param','',{key:primarykeyColumnName,fieldDataType:primarykeyColumnType,value:primarykeyColumnName},viewEventNode);
		//编辑列
		var updateButtonNode = xmlUtils.createNode('Button','',{id:'',type:LIST_BUTTON_SINGLE_UPDATE,name:'update',visible:'true'},ButtonsNode);
		var updateEventNode = xmlUtils.createNode('Event','',{Event:'1',JSId:''},updateButtonNode);
		xmlUtils.createNode('Param','',{key:primarykeyColumnName,fieldDataType:primarykeyColumnType,value:primarykeyColumnName},updateEventNode);
		//删除列	
		var deleteButtonNode = xmlUtils.createNode('Button','',{id:'',type:LIST_BUTTON_SINGLE_DELETE,name:'delete',visible:'true'},ButtonsNode);
		var deleteEventNode = xmlUtils.createNode('Event','',{Event:'1',JSId:''},deleteButtonNode);
		xmlUtils.createNode('Param','',{key:primarykeyColumnName,fieldDataType:primarykeyColumnType,value:primarykeyColumnName},deleteEventNode);
		//复制列	
		var copyButtonNode = xmlUtils.createNode('Button','',{id:'',type:LIST_BUTTON_SINGLE_COPY,name:'copy',visible:'true'},ButtonsNode);
		var copyEventNode = xmlUtils.createNode('Event','',{Event:'1',JSId:''},copyButtonNode);
		xmlUtils.createNode('Param','',{key:primarykeyColumnName,fieldDataType:primarykeyColumnType,value:primarykeyColumnName},copyEventNode);
		//添加按钮	
		xmlUtils.createNode('Button','',{id:'',type:LIST_BUTTON_MUTI_ADD,name:'add',visible:'true'},ButtonsNode);
		//批量删除按钮
		xmlUtils.createNode('Button','',{id:'',type:LIST_BUTTON_MUTI_DELETE,name:'deleteBatch',visible:'true'},ButtonsNode);
		//导入按钮
		xmlUtils.createNode('Button','',{id:'',type:LIST_BUTTON_IMPORT,name:'import',visible:'true'},ButtonsNode);
		//导出按钮
		xmlUtils.createNode('Button','',{id:'',type:LIST_BUTTON_EXPORT,name:'export',visible:'true'},ButtonsNode);
				
	}else if('eidtPage' == formType){
		xmlUtils.createNode('Button','',{id:'',type:EDIT_BUTTON_SAVE,name:'保存',visible:'true'},ButtonsNode);
		xmlUtils.createNode('Button','',{id:'',type:EDIT_BUTTON_CANCEL,name:'关闭',visible:'true'},ButtonsNode);
	}else if('viewPage' == formType){
		xmlUtils.createNode('Button','',{id:'',type:VIEW_BUTTON_CANCEL,name:'关闭',visible:'true'},ButtonsNode);
	}
}

/**
 * 添加过滤条件xml 节点
 */
function fiterParmerToXML(xmlNode,xmlUtils){
	/**
	 * 数据列集合，数据对象集合，where条件节点创建
	 */
	var columnSelected=eval(document.getElementById('jsonforDataCloumn').value);
	var MainTable=eval(document.getElementById('jsonForDataObj').value);
	var WhereNode = xmlUtils.createNode('Where','',{},xmlNode);
	
	/**
	 * 封装多主键集合
	 */  
	var primaryKeyList=new Array();
	for(var i=0;i<columnSelected.length;i++){
		if(columnSelected[i].isPrimaryKey==1){
			primaryKeyList.push(columnSelected[i]);
		}
	}
	var trObjLength=primaryKeyList.length;

	/**
	 * 构建多/单主键  Condition 节点
	 */
	
	// where ,fromTablesNode,参数操作符"=",表名,列名，PARAM（变量，常量，对象,   ），（变量是列名；常量是参数值），对象名，列名
	if(trObjLength >= 2){// 多主键
	    var ConditionNode = xmlUtils.createNode('Condition','',{type:'LIST'},WhereNode);
		var JoinConditionNode;
		for(var i=0;i<trObjLength;i++){
			if(i == 0){
				JoinConditionNode = xmlUtils.createNode('JoinCondition','',{type:''},ConditionNode);
			}else{
				JoinConditionNode = xmlUtils.createNode('JoinCondition','',{type:'and'},ConditionNode);
			}
			putConditionToXML(JoinConditionNode,xmlNode,'=',MainTable[0].name,primaryKeyList[i].name,'0',primaryKeyList[0].name,MainTable[0].name,primaryKeyList[0].name,xmlUtils);
		}
	}else{ //单主键
		    putConditionToXML(WhereNode ,xmlNode,'=',MainTable[0].name,primaryKeyList[0].name,'0',primaryKeyList[0].name,MainTable[0].name,primaryKeyList[0].name,xmlUtils);
	}
	
 return false;
}

/**
 * 把创建的xml加入到节点。
 */
function putConditionToXML(parentEleNode,xmlNode,paramOper,paramObjName,paramObjColName,paramTypeValue,paramValue,paramJoinObjName,paramJoinObjColName,xmlUtils){

	var ConditionNode = xmlUtils.createNode('Condition','',{type:'NORMAL'},parentEleNode);
	var OperateNode = xmlUtils.createNode('Operate','',{type:'='},ConditionNode);
	var OperandsNode = xmlUtils.createNode('Operands','',{},OperateNode);
	//对象Operand
	var OperandObjNode = xmlUtils.createNode('Operand','',{type:'Varchar'},OperandsNode);
	var OprNameObjNode = xmlUtils.createNode('OprName','',{type:"INDEX"},OperandObjNode);
	xmlUtils.createNode('Index','0',{},OprNameObjNode);
	var OprValueObjNode = xmlUtils.createNode('OprValue','',{type:"FIELD"},OperandObjNode);
	var FieldNameNode = xmlUtils.createNode('FieldName','',{type:"NAME"},OprValueObjNode);
	xmlUtils.createNode('Name',paramObjColName,{},FieldNameNode);
	copyTableNodeToFieldNode_Simple(xmlNode,FieldNameNode,paramObjName,xmlUtils);
	//值Operand
	var OperandParamNode = xmlUtils.createNode('Operand','',{type:'Varchar'},OperandsNode);
	var OprNameParamNode = xmlUtils.createNode('OprName','',{type:"INDEX"},OperandParamNode);
	xmlUtils.createNode('Index','1',{},OprNameParamNode);
	if(paramTypeValue == '0'){
		var OprValueParamNode = xmlUtils.createNode('OprValue','',{type:'PARAM'},OperandParamNode);
		xmlUtils.createNode('ParamName',paramValue,{},OprValueParamNode);
	}else if(paramTypeValue == '1'){
		var OprValueParamNode = xmlUtils.createNode('OprValue','',{type:'VALUE'},OperandParamNode);
		xmlUtils.createNode('Value',paramValue,{},OprValueParamNode);
	}else if(paramTypeValue == '2'){
		var OprValueParamNode = xmlUtils.createNode('OprValue','',{type:'FIELD'},OperandParamNode);
		var FieldNameNode = xmlUtils.createNode('FieldName','',{type:"NAME"},OprValueParamNode);
		xmlUtils.createNode('Name',paramJoinObjColName,{},FieldNameNode);
		copyTableNodeToFieldNode_Simple(xmlNode,FieldNameNode,paramJoinObjName,xmlUtils);
	}
}

function copyTableNodeToFieldNode_Simple(xmlNode,fieldNode,paramName,xmlUtils){
		//复制Field对象所属的Table对象
		var FromTablesNode = xmlUtils.getChildNodeByTagName(xmlNode,'FromTables');
		var formTableNodeArr = xmlUtils.getChildNodes(FromTablesNode);
		for (var i = 0; i < formTableNodeArr.length; i++){
			var formTableNode = formTableNodeArr[i];
			var tableNameNode = xmlUtils.getChildNodeByTagName(formTableNode,'TableName');
			if(xmlUtils.getInnerText(tableNameNode) == paramName){
				xmlUtils.addNode(fieldNode,xmlUtils.copyNode(tableNameNode));
			}
		}
}
