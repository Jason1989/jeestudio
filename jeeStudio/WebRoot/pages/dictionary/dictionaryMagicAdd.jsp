<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
    $(function(){
       //是否执行了查询操作；
       var groupFlag = 0;
       //选择数据源
       $('#dbSourceForDictionary').combobox({
		    url:'datasource/dataSource!getAllItem.action?ti='+new Date,
		    valueField:'id',
		    textField:'name',
		    listHeight:200,
		    editable:false
		});
		//数据分组
		$('#dicDictionaryForMagic').combobox({
		    url:'dictionary/dictionaryGroup!getAllItem.action?random='+parseInt(10*Math.random()),
		    valueField:'id',
		    textField:'name',
		    listHeight:200,
		    editable:false,
		    onChange:function(){
		       if(groupFlag != 0){
		          getDbResource_dic();
		       }
		    },
		    editable:false
		});
		setTimeout(function(){
	        $('#dbSourceForDictionary').combobox('setValue','-1');
			$('#dicDictionaryForMagic').combobox('setValue','-1');
		},0);
		
		 $("#dictionaryMagicAddListTable").datagrid({
                fit:true,
                nowrap: false,
                fitColumns:true,
                striped: true,
                url: '',
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'id',
                rownumbers: true,
                singleSelect: true,
                onLoadSuccess:function(data){
                   if(data.rows.length <= 0){
                   	   $("#dicExpressLoadMessage").text("没有符合条件的数据库表");
                   }else{
                       $("#dicExpressLoadMessage").text("");
                   }
                },
                columns: [[
                	{field: 'dictionaryGroup',title: '字典分组',width: 100,sortable: true,
                		formatter:function(value,rec){
                		  	if(rec.dictionaryGroup && rec.dictionaryGroup != null){
                				return rec.dictionaryGroup.name;
                		  	}else{
                		  		return "";
						   	}
						}},
                	{field: 'name',title: '表名称',width: 100},
					{field: 'extra',title: '附加信息',width: 0,hidden:true,
                		formatter:function(value){
                			column=value;
							return "";
						}},
                	{field: 'columnKey',title: '键列',width: 100,align:'center'},
                	{field: 'columnVal',title: '值列',width: 100},                	
                	{field: 'expression',title: '表达式',width: 400},                	
                	{field: 'state',title: '状态',width: 80,align:'center',
                		formatter:function(value){
							if ('1' == value) return '可用';
							return '不可用';
						}},
                	{field: 'operate',title: '操作',width:80,align:'center',
                		 formatter:function(value,row,index){
                		 	var e = '<a href="javascript:void(0);" onclick="editDic('+index+')">修改</a> ';
							var d = '<a href="javascript:void(0);" onclick="deleterow('+index+')">删除</a>';
							return e+d;
						}}
				]],
                toolbar: ['->', 
                	{text: '查询',iconCls: 'icon-search',handler:function(){
                		getDbResource_dic();
                		groupFlag++;
                	}}]
		 });
		 $('#dicConfig').appendTo('body').window({
                title: "修改",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 500,
                height: 300
            });  
    });
    function submitAllDic(){
    	var data=$("#dictionaryMagicAddListTable").datagrid('getRows');
    	
    	if(data.length==0){
    	   $.messager.alert('提示', '字典分组中没有查询到相应的数据字典，无法快速添加!', 'info');
    	   return;
    	}
    	var result="[";
    	for(var i=0;i<data.length;i++){
    		result+="{\"name\":\""+data[i].name+"\",\"expression\":\""+data[i].expression+"\",\"dictionaryGroup\":\""+data[i].dictionaryGroup.id+"\",\"dataSource\":\""+data[i].dataSource.id+"\"}";
    		if(i<data.length-1)
    			result+=",";
    	}
    	result+="]";
    	$.ajax({
		   type: "POST",
		   url: "dictionary/dataDictionary!saveMagicDic.action",
		   data: "data="+result,
		   success: function(msg){
		   	if(msg=="success"){
		   		$.messager.alert('提示', '添加成功!', 'info');
		   		parent.$('#bd_dictionary_magicadd').window('close');
		   		$('#bd_dictionarylist_table').datagrid('reload');
		   	}
		   	//20120815
		   	else{
		   	$.messager.alert('提示', '数据库已存在相应的数据字典，记录重复，请重新选择!', 'info');
		   	}
		   }
		}); 
    }
    var currentEditRow;
    var currentindex;
    function editDic(index){
   		 currentEditRow=$("#dictionaryMagicAddListTable").datagrid('getRows')[index];
   		 currentindex=index;
    	 $("#dicConfig").window({'href':''});//防止再次打开的时候,重复加载,造成页面的混乱
		 $("#dicConfig").window('refresh');//先刷新再加载新的页面
		 $("#dicConfig").window({'href':'pages/dictionary/dictionaryMagicConf.jsp'});				
		 $("#dicConfig").window('open');
    }
    function deleterow(index){
			$.messager.confirm('Confirm','确认删除吗?',function(r){
				if (r){
					$('#dictionaryMagicAddListTable').datagrid('deleteRow', index);
				}
			});
		}
	function getDbResource_dic(){
	    	//验证是不是已经选择了数据源、数据字典分组
              	var dbSource=$('#dbSourceForDictionary').combobox('getValue');
           		if(dbSource==-1){
           			$.messager.alert('提示', '请选择数据源!', 'warning');
           			return;
           		}
           		var dicGroup=$('#dicDictionaryForMagic').combobox('getValue');
           		if(dicGroup==-1){
           			$.messager.alert('提示', '请选择字典分组!', 'warning');
           			return;
           		}
           		var tableKey=$('#tableKey').combobox('getValue');
           		var primaryKey=$('#primaryKey').combobox('getValue');
           		var nameKey=$('#nameKey').combobox('getValue');
           		//提交
           		$("#dictionaryMagicAddListTable").datagrid('options').url = '';
           		$("#dictionaryMagicAddListTable").datagrid('reload');
				$("#dictionaryMagicAddListTable").datagrid('options').url = 'dictionary/dataDictionary!magicAdd.action';
				$("#dictionaryMagicAddListTable").datagrid('options').queryParams = {dbSource:dbSource,dicGroup:dicGroup,tableKey:tableKey,primaryKey:primaryKey,nameKey:nameKey};
				$("#dictionaryMagicAddListTable").datagrid('reload');
	}
</script>

<div class="easyui-layout" fit="true" border="false" >
   <div region="north" border="false" style="height: 59px;">
   	   <div class="easyui-panel" fit="true" border="false" style="padding: 12px 0 ;">
   	      <div style="padding-left: 13px;vertical-align:middle; ">
			<div style="line-height: 26px;display: inline;">数据源：</div><select id="dbSourceForDictionary"></select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<div style="line-height: 26px;display: inline;">数据字典分组：</div><select id="dicDictionaryForMagic"></select>
   	      </div>
   	   </div>
   </div>
   <div region="center" border="false"> 
         <div class="easyui-layout" fit="true" border="false">
         	<div region="north" border="false" style="height:50px;padding-left: 13px;">
         	    表名关键词：<select class="easyui-combobox" panelHeight="auto" id="tableKey">
         	       <option value="DIC">DIC</option>
         	       <option value="DIC.*">DIC*</option>
         	       <option value=".*DIC">*DIC</option>
         	       <option value=".*DIC.*">*DIC*</option>
         	       <option value="DICTIONARY">DICTIONARY</option>
         	    </select>&nbsp;&nbsp;
         	    主键关键词：<select class="easyui-combobox" panelHeight="auto" id="primaryKey">
         	       <option value="ID">ID</option>
         	       <option value="ID.*">ID*</option>
         	       <option value=".*ID">*ID</option>
         	       <option value=".*ID.*">*ID*</option>
         	       <option value="CODE">CODE</option>
         	       <option value=".*CODE">*CODE</option>
         	       <option value="CODE.*">CODE*</option>
         	       <option value=".*CODE.*">*CODE*</option>
         	    </select>&nbsp;&nbsp;
         	    名称关键词：<select class="easyui-combobox" panelHeight="auto" id="nameKey">
         	       <option value="NAME">NAME</option>
         	       <option value=".*NAME">*NAME</option>
         	       <option value="NAME.*">NAME*</option>
         	       <option value=".*NAME.*">*NAME*</option>
         	    </select> &nbsp; &nbsp; <div id="dicExpressLoadMessage" style="display: inline;color: red;"></div>
         	</div>
         	<div region="center" border="false">
         	   <div class="easyui-panel" fit="true" border="false">
         			<table id="dictionaryMagicAddListTable" fit="true"></table>   	  
         	   </div>
         	</div>
         </div>
   </div>
   <div region="south" border="false" style="height:59px;padding-top:13px;">
       <table style="width: 100%;">
          <tr>
             <td align="center"><a class="easyui-linkbutton" href="javascript:void(0);" onclick="submitAllDic()" icon="icon-save">保存</a></td>
             <td align="center"><a class="easyui-linkbutton" href="javascript:void(0);" onclick="parent.$('#bd_dictionary_magicadd').window('close');" icon="icon-cancel">关闭</a></td>
          </tr>
       </table>
   </div>
   <div id="dicConfig">      	
    </div>
</div>