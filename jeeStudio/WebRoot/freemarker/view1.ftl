<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>代码生成</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="../css/easyui.css">
		<link rel="stylesheet" type="text/css" href="../css/icon.css">
		<link rel="stylesheet" type="text/css" href="../css/image.css">
		<link rel="stylesheet" type="text/css" href="../css/style.css">
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="../js/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="../js/XmlUtils.js"></script>
		<script>
		var jsdata = ${json}
		
        $(function(){
            $('#fd_formlist_table').datagrid({
                //title: '列表页',
                fit:true,
                nowrap: false,
                striped: true,
                autoFit: true,
                sortName: 'TAB_ID',
                idField: 'TAB_ID',
                <#if canBatch="true">
                frozenColumns: [[{
                    field: 'ck',
                    checkbox: true
                }]],
                </#if>
                columns: [[
                 <#list fields as field> 
				{
                    field: '${field.dataColumn}',
                    title: '${field.name}',
                    align: '${field.align}',
                    width: '${field.width}'
                },
                </#list>
					
				 {
                    field:'opt',
	                title:'操作',
	                width:200,
	                rowspan:2,
	                align:'center',
					formatter:function(value,rec){
					   <#list jsParam as jsParam>
					    var ${jsParam.name} = rec.${jsParam.name};
					   </#list>
					    var links = '';
					     <#list rowButtons as rowBtn>
					         links += '&nbsp;&nbsp;<a href="javascript:void(0)"  <#list rowBtn.event as event> onclick=<#if (rowBtn.type!="7")>doEdit(<#list event.paras as param>"'+${param.key}+'"<#if param_has_next>,</#if></#list>)<#elseif (rowBtn.type!="8")>doDelete()<#else>doView<#list event.paras as param>"'+${param.key}+'"<#if param_has_next>,</#if></#list></#if></#list> >${rowBtn.buttonName}</a>&nbsp;&nbsp;<#if rowBtn_has_next>|</#if>';
					     </#list>
						return links;
					}
					}]],
		       <#if canShowPagination="true">
                pagination: true,
                </#if>
                rownumbers: true,
                singleSelect: true,
                  toolbar: [ 
                     <#list gridButtons as gridBtn> 
					{
	                    text: '${gridBtn.buttonName}',
	                    iconCls: '${gridBtn.style}',
	                    handler: function(){
	                        <#if (gridBtn.style!="icon-cancel")>
	                        <#if (gridBtn.style="icon-search")>
	                        document.getElementById("searcharea").style.display = ""; 
	                        $('#searcharea').window("open");
	                        <#elseif (gridBtn.style="icon-add")>
	                           doAdd();
	                        <#else>
	                        </#if>
	                        
	                         <#else>doDelete();
	                        </#if>
	                    }
	                }
	                <#if gridBtn_has_next>,"-",</#if>
                    </#list>
                
				]
            });
              
            
            $('#fd_formlist_table').datagrid("loadData",jsdata);
            
             $('#searcharea').window({
				modal: true,
				resizable: false,
				minimizable: false,
				maximizable: false,
				closed: true,
				width: "auto",
                height: "auto"
			});
			$('#edit_area_window').window({
				modal: true,
				resizable: false,
				minimizable: false,
				maximizable: false,
				closed: true,
				width: "auto",
                height: "auto"
			});
			$('#add_area_window').window({
				modal: true,
				resizable: false,
				minimizable: false,
				maximizable: false,
				closed: true,
				width: "auto",
                height: "auto"
			});
				
				 function doAdd(){
            $('#edit_form').form('clear');
            var url = "${editUrl}"+"&type=add";
            $("#add_area_window").window('refresh');
           	$("#add_area_window").window({'href':url});
            $('#add_area_window').window("open");
        }
         function doEdit(value){
             var url = "${editUrl}&type=update&id="+value;
             $("#edit_area_window").window('refresh');
           	$("#edit_area_window").window({'href':url});
            $('#edit_area_window').window("open");
        }
		  function doSearch(){
            $('#fd_formlist_table').datagrid('reload',{${queryParams}}); 
            document.getElementById("searcharea").style.display = "";
	        $('#searcharea').window("close");
            }	   
         function doDelete(){
             var selected = $('#fd_formlist_tab').datagrid('getSelected');
                            if (selected == null) {
                                $.messager.alert('提示', '请选择数据!', 'warning');
                            }
                            else {
                            
                                $.messager.confirm('提示', '确认删除所选的数据吗？');
                                
                            }
        }
        });
             </script>
		</head>
	<body>
           
         
		<div id="fd_formlist_tab" class="easyui-tabs" border="false"
			fit="true" style="height: 680px;">
			<div title="列表页" closable="true" fit="true" style="height: 100%;">
				<div id="searcharea" class="easyui-window" style="width:340px;height:526px;padding:12px 12px 0 12px;display:none;" title="查询">
			     <table>
			        <#list queryColumns as field>
			             <#if (field_index+1)%2=1><tr></#if>
					    <td align="right"><font size="2">
							${field.name}：</font>
						</td>
				    <td>
				    <#if (field.type="number")><input id="nn" class="easyui-numberbox" min="5.5" max="20" precision="2" required="true"/>
				    <#elseif (field.type="combobox")>
				        <select id="${field.id}" class="easyui-combobox" name="state" style="width:200px;" required="true">
				        <option value="AL">Alabama</option>
				        <option value="AK">Alaska</option>
				        </select>
				      <#elseif (field.type="datebox")><input id="${field.id}" class="easyui-datebox" min="5.5" max="20" precision="2" required="true"/>   
				    <#elseif (field.type="radio")><input id="${field.id}" name="${field.name}" type="radio"/>是 <input name="${field.id}" type="radio"/>否
				    <#elseif (field.type="check")><input id="${field.id}" name="" type="checkbox" />你好
				    <#else> <input id="${field.id}" class="easyui-validatebox" />
				    </#if>
				</td>
				<#if (field_index+1)%2=0></tr></#if>
				<#assign a=(field_index+1)%2>
				</#list>
				<#if a=0>
				<td></td>
				</tr>
				</#if>
			    </table>
			    <div align="center" style="padding:40px;">
			        <a href="#" class="easyui-linkbutton" icon="icon-ok" onclick="doSearch()">查询</a>
               </div>
		    </div>
		    
		    <div title="表单" closable="true" fit="true" style="height: 100%;">
			<table id="fd_formlist_table">
				</table>
				</div>
				</div>
        </div>   
	</body>
		