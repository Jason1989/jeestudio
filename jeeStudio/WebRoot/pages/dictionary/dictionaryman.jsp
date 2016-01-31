<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>数据字典管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		var url="";
		$(function(){
		    	$('#dataobject_menu').tree({			
			 	 url: 'dictionary/dictionaryGroup!listTree.action',height:350,width:200,		 		 		 	
				onClick:function(node){
					if(node.id==1){
						$.post("dictionary/dataDictionary!search.action",{pageNumer:1,pageSize:20},
			          		function(data){
								if(data && data != ""){
									$('#bd_dictionary_search').window('close');
									$('#bd_dictionarylist_table').datagrid('loadData',eval('(' + data + ')'));
								}
			       			}
			       		);	
					}else{
						url='dictionary/dataDictionary!list.action';
						$("#bd_dictionarylist_table").datagrid({
							queryParams:{dictionaryGroup:node.id},
							url:url
						});//直接加载json对象	
						
						$.post("dictionary/dataDictionary!search.action",{dictionaryGroup:node.id,pageNumer:1,pageSize:20},
			          		function(data){
								if(data && data != ""){
									$('#bd_dictionary_search').window('close');
									$('#bd_dictionarylist_table').datagrid('loadData',eval('(' + data + ')'));
								}
			       			}
			       		);	
			       	}				
				}
			});
		  
		  
		
		
            $('#bd_dictionarylist_table').datagrid({
                iconCls: 'icon-save',
                //width:1000,
                height: 400,
                nowrap: false,
                fitColumns:true,
                striped: true,
                autoFit: true,
                url:'dictionary/dataDictionary!list.action',
                sortName: 'code',
                sortOrder: 'desc',
                idField: 'id',
                pageSize:20,
                frozenColumns: [[
					{ field: 'ck', checkbox: true}
				]],
                columns: [[
                	{field: 'sortNo',title: '序号',width: 50,sortable: true,align:'center'},//
                	{field: 'dictionaryGroup',title: '字典分组',width: 100,sortable: true,
                		formatter:function(value,rec){
                		  	if(rec.dictionaryGroup && rec.dictionaryGroup != null){
                				return rec.dictionaryGroup.name;
                		  	}else{
                		  		return "";
						   	}
						}},
                	{field: 'name',title: '名称',width: 150,sortable: true},
                	{field: 'type',title: '类型',width: 80,sortable: true,align:'center',
                		formatter:function(value){
							if ('1' == value) return '静态';
							else if ('2' == value) return '动态';
							return value;
						}},
                	{field: 'dataSource',title: '数据源',width: 120,
                		formatter:function(value,rec){
                		  	if(rec.dataSource && rec.dataSource != null){
                				return rec.dataSource.name;
                		  	}else{
                		  		return "";
						   	}
						}},                	
                	{field: 'expression',title: '表达式',width: 350},                	
                	{field: 'state',title: '状态',width: 80,align:'center',
                		formatter:function(value){
							if ('1' == value) return '可用';
							return '不可用';
						}},
                	{field: 'operate',title: '操作',width: 100,align:'center',
	                    formatter:function(value,rec){
						    var links = '<a href="javascript:;" onclick=bdDictionaryUpdate("' + rec.id + '","'+rec.type+'")>修改</a>&nbsp;&nbsp;|&nbsp;&nbsp;';
						    links += '<a href="javascript:;" onclick=bdDictionaryDelete("' + rec.id + '")>删除</a>';
							return links;
						}
					}
				]],
                pagination: true,
                rownumbers: true,
                //singleSelect: true,
                toolbar: ['-', 
                	{text: '查询',iconCls: 'icon-search',handler: bdDictionarySearch},
		  			'-',
		  			{text: '添加',iconCls: 'icon-add',handler: bdDictionaryAdd},
		  			'-', 
		  			{text: '删除',iconCls: 'icon-remove',handler: bdDictionaryDelete},
		  			'-', 
		  			{text: '快速添加',iconCls: 'icon-wand',handler: bdDictionaryBatchCreate}, 
		  			'-' ]
            });
            $('#bd_dictionary_add').appendTo('body').window({
                title: "添加",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 380
            });
             $('#bd_dictionary_magicadd').appendTo('body').window({
                title: "动态数据字典批量添加",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 900,
                height: 400
            });
            $('#bd_dictionary_update').appendTo('body').window({
                title: "修改",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 650,
                height: 460
            });
             $('#bd_dictionary_search').window({
                title: "查询",
                modal: true,
                resizable: false,
                minimizable: false,
                maximizable: false,
                shadow: false,
                closed: true,
                width: 400,
                height: 250
            }); 
            function bdDictionaryAdd(){
            	//$.post("dictionary/dataDictionary!toAdd.action",{},
            	//	function(data){
		        // 		$("#bd_dictionary_add").html(data);
		        //  		$("#bd_dictionary_add").window('open');
		        //  		initDictionaryAddData();
          		//	}
          		//);
          		$("#bd_dictionary_add").window({'href':''});//防止再次打开的时候,重复加载,造成页面的混乱
  				$("#bd_dictionary_add").window('refresh');//先刷新再加载新的页面
  				$("#bd_dictionary_add").window({'href':'dictionary/dataDictionary!toAdd.action'});				
  				$("#bd_dictionary_add").window('open');
            }  
            function bdDictionarySearch(){
            	$("#bd_dictionary_search").window('open');            	         	
            } 
            $('#bd_dictsearch_group').combobox({
			    //url:'dictionary/dictionaryGroup!getAllItem.action',
			    url:'dictionary/dictionaryGroup!getAllItem.action?random='+new Date(),
			    valueField:'id',
			    textField:'name',
			    listWidth:150,
			    editable:false
			}); 
			$('#bd_dictsearch_group').combobox('setValue','-1');
			$('#bd_dict_search_sumbit').linkbutton();         
            $('#bd_dict_search_cancel').linkbutton();   
        });
        function bdDictionarySearchSubmit(){
        	var name = $('#bd_dictsearch_name').val();
        	var group = $('#bd_dictsearch_group').combobox('getValue'); 
        	var pager = $('#bd_dictionarylist_table').datagrid('getPager');
        	var pageNumer = 1;
        	var pageSize = 10;
			if (pager){
				pageNumer = $(pager).pagination('options').pageNumber;
				pageSize = $(pager).pagination('options').pageSize;
			}
        	$.post("dictionary/dataDictionary!search.action",{dictionaryName:name,dictionaryGroup:group,pageNumer:pageNumer,pageSize:pageSize},
          		function(data){
					if(data && data != ""){
						$('#bd_dictionary_search').window('close');
						$('#bd_dictionarylist_table').datagrid('loadData',eval('(' + data + ')'));
					}
       			}
       		);
        }
        function bdDictionaryDelete(dictionaryId){
        	if(dictionaryId && dictionaryId != ""){
        		$.messager.confirm('提示', '确认删除吗？',function(a){
	         		if(a)$.post("dictionary/dataDictionary!delete.action",{"dictionaryId":dictionaryId},bdDictionaryDeleteRollback);
	         	});
        	}else{
         		var selected = $('#bd_dictionarylist_table').datagrid('getSelections');
	            if (selected == null || selected.length < 1) {
	                $.messager.alert('提示', '请选择数据!', 'warning');
	            } else {
	            	$.messager.confirm('提示', '确认删除吗？',function(a){
	            		if(a){
		            		var dictionaryIds = "";
		            		for(var i=0;i<selected.length;i++){
		            			if(dictionaryIds == ""){
		            				dictionaryIds += "'" + selected[i]['id'] + "'"
		            			}else{
		            				dictionaryIds += ",'" + selected[i]['id'] + "'"
		            			}
		                	}
		                	$.post("dictionary/dataDictionary!delete.action",{"dictionaryIds":dictionaryIds},bdDictionaryDeleteRollback);
	                	}
	            	});	               
	            }
            }
        }
        function bdDictionaryDeleteRollback(data){
        	if("success" == data){
   				$.messager.alert('提示','删除成功','info');			         				
   				$('#bd_dictionarylist_table').datagrid('reload');
   			}
        }
        function bdDictionaryUpdate(dictionaryId,type){
        	//$.post("dictionary/dataDictionary!toUpdate.action",{"dictionaryId":dictionaryId},
        	//	function(data){
	        //		$("#bd_dictionary_update").html(data);
	        //		$("#bd_dictionary_update").window('open');
	        //		initDictionaryEditData();
      		//	}
      		//);
      		var url = 'dictionary/dataDictionary!toUpdate.action?dictionaryId='+dictionaryId;
      		if(type == '2'){//如果为动态的话则需要显示动态缓存
      		   url += '&dicType=auto';
      		}
      		$("#bd_dictionary_update").window({'href':''});//防止再次打开的时候,重复加载,造成页面的混乱
			$("#bd_dictionary_update").window('refresh');//先刷新再加载新的页面
			$("#bd_dictionary_update").window({href:url});				
			$("#bd_dictionary_update").window('open');
        }

        function bdDictionaryBatchCreate(){
            //easyuiWinNew({title:"数据字典魔法添加",iconCls:'icon-wand',href:'pages/dictionary/dictionaryMagicAdd.jsp'},1);
           	$("#bd_dictionary_magicadd").window({'href':''});//防止再次打开的时候,重复加载,造成页面的混乱
			$("#bd_dictionary_magicadd").window('refresh');//先刷新再加载新的页面
			$("#bd_dictionary_magicadd").window({'href':'pages/dictionary/dictionaryMagicAdd.jsp?random='+parseInt(10*Math.random())});				
			$("#bd_dictionary_magicadd").window('open');
        }
		
		
		
	</script> 
	    
  </head>

  <body>
     <div class="easyui-layout" collapsible="true" fit="true" border="false">
				<div region="west" split="true" border="false" style="width:220px;padding:5px;height:380px">
					</br>数据字典对象分组</br>
				    <div class="easyui-panel"  icon="icon-organisation"  border="true" headerCls="header_cls" style="padding:3px ;width:210px;height:420px">	 
								 <ul id="dataobject_menu"></ul>
				    </div>
				</div>
				<div region="center"   style="overflow:hidden; width: 600px;padding:5px 0 5px 0;" border="false">
   <table width="99%" border="0" cellpadding="1" cellspacing="0" class="table_form1" height="400px;">		
		 <tr  ></br></tr>
		 <tr>		
			<td width="10px"></td>
			<td>字典分组所属数据字典</td>
			<td width="10px"></td>
		</tr>
		<tr>
			
			
			<td></td>
			<td style="background:#dedede;">
				<table border="0" cellpadding="0" cellspacing="0" width="100%" style="background:#ffffff;" height="420px;">					
					<tr valign="top">
						<td>
							<div class="easyui-panel" fit="true" border="false" style="margin:0px;">
								
								
								
								
	<div id="bd_dictionarylist_panel" title="字典列表" class="easyui-panel" fit="true" border="false" collapsible="true" class="main_panel">
		<table id="bd_dictionarylist_table" fit="true"></table>      	
	</div>
	<div id="bd_dictionary_search" class="easyui-window" style="padding:10px;">   
	  <!-- 
		<table width="100%">
			<tr><td align="right" width="40%">字典分组：</td><td align="left"><select id="bd_dictsearch_group" style="width:132px;">
				</select></td></tr>
			<tr><td align="right">字典名称：</td><td align="left"><input id="bd_dictsearch_name" type="text"/></td></tr>
			<tr><td align="center" colspan="2"><a id="bd_dict_search_sumbit" href="javascript:;" class="easyui-linkbutton" icon="icon-save" onclick="bdDictionarySearchSubmit()">提交</a>
					<a id="bd_dict_search_cancel" href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="$('#bd_dictionary_search').window('close')">关闭</a>
			</td></tr>
		</table>
	   -->   	
		<div class="pop_col_col">
				<div class="pop_col_conc">
					<dl>
						<dd>
							字典分组：
						</dd>
						<dt>
							<select id="bd_dictsearch_group" style="width:132px;">
							</select>
						</dt>
					</dl>
					<dl>
						<dd>
							字典名称：
						</dd>
						<dt>
							<input id="bd_dictsearch_name" type="text"/>
						</dt>
					</dl>
				</div>
			</div>
			 <div style="float:left;padding:13px 0 0 100px;">
			 		<a id="bd_dict_search_sumbit" href="javascript:;" class="easyui-linkbutton" icon="icon-save" onclick="bdDictionarySearchSubmit()">提交</a>
					<a id="bd_dict_search_cancel" href="javascript:;" class="easyui-linkbutton" icon="icon-cancel" onclick="$('#bd_dictionary_search').window('close')">关闭</a></div>
    </div>
	<div id="bd_dictionary_add" class="easyui-window" style="padding:10px;">      	
    </div>
	<div id="bd_dictionary_update" class="easyui-window" style="padding:10px;">      	
    </div>
    <div id="bd_dictionary_magicadd" class="easyui-window" style="padding:10px;">      	
    </div>
								
								
								
								
								
								
							</div>
						</td>
					</tr>					
				</table>
			</td>		
	</table>
   
   
   			</div>
     </div>
  </body>
</html>
