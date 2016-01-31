<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		<script type="text/javascript" src="../jquery-easyui-1.1.2/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="../js/XmlUtils.js"></script>
		<script>
		
        $(function(){
            $('#tempform_table').datagrid({
                //title: '列表页',
                fit:true,
                nowrap: false,
                url:'tempform/tempform!list.action',
                striped: true,
                autoFit: true,
                sortName: 'foId',
                idField: 'foId',
                frozenColumns: [[{
                    field: 'ck',
                    checkbox: true
                }]],
                columns: [[
				{
                    field: 'departmentName',
                    title: '部门名称',
                    align: '',
                    width: '100'
                },
				{
                    field: 'managerId',
                    title: '上级主管',
                    align: '',
                    width: '100'
                },
				{
                    field: 'locationId',
                    title: '地址',
                    align: '',
                    width: '100'
                },
				{
                    field: 'departmentId',
                    title: '部门ID',
                    align: '',
                    width: '100'
                },
				{
                    field: 'appId',
                    title: '系统添加',
                    align: '',
                    width: '100'
                },
				{
                    field: 'parentAppId',
                    title: '系统添加',
                    align: '',
                    width: '100'
                },
				{
                    field: 'envDatameter',
                    title: '工作流字段',
                    align: '',
                    width: '100'
                },
				{
                    field: 'envDatastate',
                    title: '状态',
                    align: '',
                    width: '100'
                },
                {
                    field:'opt',
	                title:'操作',
	                width:200,
	                rowspan:2,
	                align:'center',
					formatter:function(value,rec){
					    var departmentId = rec.departmentId;
					    var links = '';
					         links += '&nbsp;&nbsp;<a href="javascript:void(0)"   onclick=view_tempform("departmentId","'+departmentId+'") >view</a>&nbsp;&nbsp;|';
					         links += '&nbsp;&nbsp;<a href="javascript:void(0)"   onclick=edit_tempform("departmentId","'+departmentId+'") >update</a>&nbsp;&nbsp;|';
					         links += '&nbsp;&nbsp;<a href="javascript:void(0)"   onclick=delete_tempform() >delete</a>&nbsp;&nbsp;';
						return links;
					}
					}]],
                pagination: true,
                rownumbers: true,
                singleSelect: true,
                toolbar: [ 
					{
	                    text: 'add',
	                    iconCls: 'icon-add',
	                    handler: function(){
	                       	                           doAdd();
	                    }
	                }
	                ,"-",
					{
	                    text: 'deleteBatch',
	                    iconCls: 'icon-cancel',
	                    handler: function(){
	                       doDelete();
	                    }
	                }
	                
                
			]
            });
            
            $('#search_tempform_window').window({
				modal: true,
				resizable: false,
				minimizable: false,
				maximizable: false,
				closed: true,
				width: "auto",
                height: "auto"
			});
			
			$('#edit_tempform_window').window({
				modal: true,
				resizable: false,
				minimizable: false,
				maximizable: false,
				closed: true,
				width: "auto",
                height: "auto"
			});
			
			$('#add_tempform_window').window({
				modal: true,
				resizable: false,
				minimizable: false,
				maximizable: false,
				closed: true,
				width: "auto",
                height: "auto"
			});
			
			$('#view_tempform_window').window({
				modal: true,
				resizable: false,
				minimizable: false,
				maximizable: false,
				closed: true,
				width: "auto",
                height: "auto"
			});
			

        });
        
        function add_tempform(){
            $('#tempform_form').form('clear');
            var url = "tempform/tempform!goEdit.action";
            if(url.indexOf('?')>0){
               url+= "&type=add";
            }else{
               url+= "?type=add";
            }
            $("#add_tempform_window").window('refresh');
           	$("#add_tempform_window").window({'href':url});
            $('#add_tempform_window').window("open");
        }
        
        function edit_tempform(key,value){
             var url = "tempform/tempform!goEdit.action";
	        if(url.indexOf('?')>0){
	               url+= "&type=update&"+key+"="+value;
	            }else{
	               url+= "?type=update&"+key+"="+value;
	            }
            $("#edit_tempform_window").window('refresh');
           	$("#edit_tempform_window").window({'href':url});
            $('#edit_tempform_window').window("open");
        }
        
        function view_tempform(key,value){
             var url = "tempform/tempform!goView.action";
	        if(url.indexOf('?')>0){
	               url+= "&type=update&"+encodeURIComponent(key)+"="+value;
	            }else{
	               url+= "?type=update&"+encodeURIComponent(key)+"="+value;
	            }
            $("#view_tempform_window").window('refresh');
           	$("#view_tempform_window").window({'href':url});
            $('#view_tempform_window').window("open");
        }
        
        function search_tempform(){ 
            $('#tempform_table').datagrid('reload',{departmentName:$('#departmentName').val(),managerId:$('#managerId').val(),locationId:$('#locationId').val()}); 
            document.getElementById("search_tempform_window").style.display = "";
	        $('#search_tempform_window').window("close");
        }
        
        function delete_tempform(){
             var selected = $('#tempform_table').datagrid('getSelected');
             if (selected == null) {
                $.messager.alert('提示', '请选择数据!', 'warning');
                return ;
             }
             else {
              $.messager.confirm('提示', '确认删除所选的数据吗？', function(r){
				if (r){
					var idstr = [];
					var rows = $('#tempform_table').datagrid('getSelections');
					for(var i=0;i<rows.length;i++){
						idstr.push("'"+rows[i].foId+"'");
					}
					var ids = idstr.join(',');
					$.post('tempform/tempform!doDelete.action',{ids:ids},function(data){
                    if(data!=""){
                         alert("删除成功");
                          search_tempform();
			              
			            }
		          });
				}
			   });
                
             }
        }
         function close__tempform(){
             $('#edit_tempform_window').window('close');
			 $('#add_tempform_window').window('close');
			 $('#view_tempform_window').window('close');
         }
        
         function submit_tempform(){
          $('#tempform_form').form("submit",{
			    url:'tempform/tempform!doSave.action',
			    onSubmit:function(){
			        return $(this).form('validate');
			    },
			    success:function(data){
			        $('#edit_tempform_window').window('close');
			        $('#add_tempform_window').window('close');
			        $('#edit_tempform_form').form('clear');
			         alert("保存成功");
			         search_tempform();
			    }
			});
        }
        
    </script>
	</head>

	<body>
		<div id="tempform_tab" class="easyui-tabs" border="false"
			fit="true" style="height: 680px;">
			<div title="tempform" closable="true" fit="true" style="height: 100%;">
			<div id="edit_tempform_window" class="easyui-window" style="padding:12px 12px 0 12px;"  title="编辑">
			   	
            </div>
            <div id="add_tempform_window" class="easyui-window" style="padding:12px 12px 0 12px;"  title="添加">
			  
            </div>
            <div id="view_tempform_window" class="easyui-window" style="padding:12px 12px 0 12px;"  title="查看">
			  
            </div>
			
			<div id="search_tempform_window" class="easyui-window" style="width:340px;height:526px;padding:12px 12px 0 12px;display:none;" title="查询">
			    <table border="0" cellpadding="4" cellspacing="0">
				  <tr>
						    <td align="right"><font size="2">
								部门名称：</font>
							</td>
					    <td>
<input class="easyui-validatebox" name="departmentName" value="" required="true" validType="length[1,32]">&nbsp;&nbsp;<font size="2" color="red">*</font>					</td>
					
				  
						    <td align="right"><font size="2">
								上级主管：</font>
							</td>
					    <td>
<input class="easyui-validatebox" name="managerId" value="" required="true" validType="length[1,32]">&nbsp;&nbsp;<font size="2" color="red">*</font>					</td>
					</tr>
				  <tr>
						    <td align="right"><font size="2">
								地址：</font>
							</td>
					    <td>
<input class="easyui-validatebox" name="locationId" value="" required="true" validType="length[1,32]">&nbsp;&nbsp;<font size="2" color="red">*</font>					</td>
					
			</table>
			    <div align="center" style="padding:40px;">
			        <a href="#" class="easyui-linkbutton" icon="icon-ok" onclick="doSearch()">查询</a>
               </div>
		    </div>
				<table id="tempform_table">
				</table>
				
			</div>
		
		</div>
		
	</body>
</html>
