${page}
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
		
        $(function(){
            $('#${className}_table').datagrid({
                //title: '列表页',
                fit:true,
                nowrap: false,
                url:'${url}',
                striped: true,
                autoFit: true,
                sortName: 'foId',
                idField: 'foId',
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
					         links += '&nbsp;&nbsp;<a href="javascript:void(0)"  <#list rowBtn.event as event> onclick=<#if (rowBtn.type=="13")>edit_${className}(<#list event.paras as param>"${param.key}","'+${param.key}+'"<#if param_has_next>,</#if></#list>)<#elseif (rowBtn.type=="12")>delete_${className}()<#else>view_${className}(<#list event.paras as param>"${param.key}","'+${param.key}+'"<#if param_has_next>,</#if></#list>)</#if></#list> >${rowBtn.buttonName}</a>&nbsp;&nbsp;<#if rowBtn_has_next>|</#if>';
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
	                    iconCls: '${gridBtn.iconCls}',
	                    handler: function(){
	                       <#if (gridBtn.iconCls="icon-cancel")>doDelete();
	                        <#elseif (gridBtn.iconCls="icon-search")>
	                        document.getElementById("search_${className}_window").style.display = ""; 
	                        $('#search_${className}_window').window("open");
	                        <#elseif (gridBtn.iconCls="icon-add")>
	                           doAdd();
	                         <#else>
	                        </#if>
	                    }
	                }
	                <#if gridBtn_has_next>,"-",</#if>
                    </#list>
                
			]
            });
            
            $('#search_${className}_window').window({
				modal: true,
				resizable: false,
				minimizable: false,
				maximizable: false,
				closed: true,
				width: "auto",
                height: "auto"
			});
			
			$('#edit_${className}_window').window({
				modal: true,
				resizable: false,
				minimizable: false,
				maximizable: false,
				closed: true,
				width: "auto",
                height: "auto"
			});
			
			$('#add_${className}_window').window({
				modal: true,
				resizable: false,
				minimizable: false,
				maximizable: false,
				closed: true,
				width: "auto",
                height: "auto"
			});
			
			$('#view_${className}_window').window({
				modal: true,
				resizable: false,
				minimizable: false,
				maximizable: false,
				closed: true,
				width: "auto",
                height: "auto"
			});
			

        });
        
        function add_${className}(){
            $('#${className}_form').form('clear');
            var url = "${editUrl}";
            if(url.indexOf('?')>0){
               url+= "&type=add";
            }else{
               url+= "?type=add";
            }
            $("#add_${className}_window").window('refresh');
           	$("#add_${className}_window").window({'href':url});
            $('#add_${className}_window').window("open");
        }
        
        function edit_${className}(key,value){
             var url = "${editUrl}";
	        if(url.indexOf('?')>0){
	               url+= "&type=update&"+key+"="+value;
	            }else{
	               url+= "?type=update&"+key+"="+value;
	            }
            $("#edit_${className}_window").window('refresh');
           	$("#edit_${className}_window").window({'href':url});
            $('#edit_${className}_window').window("open");
        }
        
        function view_${className}(key,value){
             var url = "${viewUrl}";
	        if(url.indexOf('?')>0){
	               url+= "&type=update&"+encodeURIComponent(key)+"="+value;
	            }else{
	               url+= "?type=update&"+encodeURIComponent(key)+"="+value;
	            }
            $("#view_${className}_window").window('refresh');
           	$("#view_${className}_window").window({'href':url});
            $('#view_${className}_window').window("open");
        }
        
        function search_${className}(){ 
            $('#${className}_table').datagrid('reload'<#if (isQueryZone="true")>,{${queryParams}}</#if>); 
            document.getElementById("search_${className}_window").style.display = "";
	        $('#search_${className}_window').window("close");
        }
        
        function delete_${className}(){
             var selected = $('#${className}_table').datagrid('getSelected');
             if (selected == null) {
                $.messager.alert('提示', '请选择数据!', 'warning');
                return ;
             }
             else {
              $.messager.confirm('提示', '确认删除所选的数据吗？', function(r){
				if (r){
					var idstr = [];
					var rows = $('#${className}_table').datagrid('getSelections');
					for(var i=0;i<rows.length;i++){
						idstr.push("'"+rows[i].foId+"'");
					}
					var ids = idstr.join(',');
					$.post('${deleteUrl}',{ids:ids},function(data){
                    if(data!=""){
                         alert("删除成功");
                          search_${className}();
			              
			            }
		          });
				}
			   });
                
             }
        }
         function close__${className}(){
             $('#edit_${className}_window').window('close');
			 $('#add_${className}_window').window('close');
			 $('#view_${className}_window').window('close');
         }
        
         function submit_${className}(){
          $('#${className}_form').form("submit",{
			    url:'${saveUrl}',
			    onSubmit:function(){
			        return $(this).form('validate');
			    },
			    success:function(data){
			        $('#edit_${className}_window').window('close');
			        $('#add_${className}_window').window('close');
			        $('#edit_${className}_form').form('clear');
			         alert("保存成功");
			         search_${className}();
			    }
			});
        }
        
    </script>
	</head>

	<body>
		<div id="${className}_tab" class="easyui-tabs" border="false"
			fit="true" style="height: 680px;">
			<div title="${className}" closable="true" fit="true" style="height: 100%;">
			<div id="edit_${className}_window" class="easyui-window" style="padding:12px 12px 0 12px;"  title="编辑">
			   	
            </div>
            <div id="add_${className}_window" class="easyui-window" style="padding:12px 12px 0 12px;"  title="添加">
			  
            </div>
            <div id="view_${className}_window" class="easyui-window" style="padding:12px 12px 0 12px;"  title="查看">
			  
            </div>
			
			<div id="search_${className}_window" class="easyui-window" style="width:340px;height:526px;padding:12px 12px 0 12px;display:none;" title="查询">
			    <table border="0" cellpadding="4" cellspacing="0">
			    <#if isQueryZone = "true">
				<#list queryColumns as field>
				  <#if (field_index+1)%2=1><tr></#if>
						    <td align="right"><font size="2">
								${field.text}：</font>
							</td>
					    <td>
					    <@zxtInput name="${field.name}" value="" type="${field.type}"/>
					</td>
					<#if (field_index+1)%2=0></tr></#if>
					<#assign a=(field_index+1)%2>
					</#list>
					<#if a=0>
					<td></td>
					</tr>
					</#if>
				</#if>
			</table>
		    <div align="center" style="padding:40px;">
		        <a href="#" class="easyui-linkbutton" icon="icon-ok" onclick="doSearch()">查询</a>
           </div>
		    </div>
			<table id="${className}_table">
			</table>				
			</div>
		</div>
	</body>
</html>
