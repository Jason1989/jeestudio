<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
	<script src="js/XmlUtils.js"></script>
	<script>
	$(function(){
		//将值赋给URL
		$('#treeDataAttributesUrl').combobox('setValue','indexpage/indexpage!load_Index.action?indexPageTemplateValue=${indexPageTemplateValue}&indexSetingId=${subSystemId}&isconfig=false');
		//如果是新生成的settingid，则将值赋给隐藏域pageSettingIdValuehidden
		$("#pageSettingIdValuehidden").val('${subSystemId}');
		
		//配置栏可拖动
		$('#page_setpanel').draggable({axis:null,handle:'#page_setpaneltitle'});
		//模块下拉
		$('#modelIdBtn').combobox({
			url:'index/indexModel!comboList.action', 
			valueField:'id',
			textField:'name'
		});
		$('#design_form').form();
		//$('#authorityButtonForIndexPageSetting').linkbutton({text:'设置可访问角色'});
		$('#rolelist').appendTo('body').window({
              title: "选择角色",
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
	function authorityAll(){
				$("#rolelist").window({'href':''});
		 		$("#rolelist").window('refresh');
		 		$("#rolelist").window({'href':'pages/indexTemplate/roleList.jsp'});				
		 		$("#rolelist").window('open');
			}
	var rows;
	$.getJSON("index/indexModel!comboList.action", function(json){
		rows=json;
	}); 
	
	//初始化xml
	var xmlUtils = new XmlUtils({dataType:'json'});
	var dataObject = '${xmlparam}';
	if(!dataObject || dataObject==""){
		dataObject = '<map></map>';
	}
	xmlUtils.loadXmlString(dataObject);
	var root = xmlUtils.getRoot();	
	var divid="";
	
	function conf(id){
		//初始化 清除之前的设置
		divid=id;
		$("#xmlparam").val('');
		roleids="";
		$('#modelIdBtn').combobox('setValue','请选择');
		if(divid!=null){
			$.ajax({
			   type: "POST",
			   url: "indexpage/indexpage!getInitDiv.action",
			   data: "subSystemId=<s:property value='#request.subSystemId'/>&id="+divid,
			   success: function(msg){
			     var result=msg.split(",");
			     $('#modelIdBtn').combobox('setValue',result[0]); 
				 $('#modelIdBtn').combobox('setText',result[1]);
			   }
			}); 
		}
		$("#page_setpanel").hide();
		$("#page_setpanel").show("fast");
	}
	
	function undo(id){
		$("#page_setpanel").hide();
		if($("#xmlparam").val()==""){
		   $.messager.alert('提示','该模块还未进行配置','info');
		  		return ;
		}else{
		$.messager.confirm('提示', '确定要取消此模块的所有配置吗?', function(r){
			if (r){
				var entry = xmlUtils.createNode('entry',"",{},root);
				var key = xmlUtils.createNode('key',id,{},entry);
				var model = xmlUtils.createNode('model',"",{},entry);
				var name = xmlUtils.createNode('name',"",{},model);
				var url = xmlUtils.createNode('url',"",{},model);
				var role = xmlUtils.createNode('role',"",{},model);
				
				$("#xmlparam").val('<?xml version="1.0" encoding="UTF-8"?>' + xmlUtils.toString());
				$('#design_form').form('submit',{ 
					url:'indexpage/indexpage!update_Index.action', 
					onSubmit:function(){ 
						return $(this).form('validate'); 
					}, 
					success:function(data){ 
						$.messager.alert('提示',data,'info');
	             		$("#page_setpanel").hide();	
	             		refreshDesignViewIframe();
					} 
				}); 
			}
		});
		}
	}
	
	function bindmodel(){
		  	var modelid = $('#modelIdBtn').combobox('getValue'); // 取值 
		  	if(modelid=="请选择"){
		  		$.messager.alert('提示','请选择模块','info');
		  		return ;
		  	};
		  	if(divid==""){
		  		$.messager.alert('提示','参数未传递','info');
		  		return ;
		  	}else{
		  		addrowdata(divid,modelid);
		  	}
		  	
		  	$("#xmlparam").val('<?xml version="1.0" encoding="UTF-8"?>' + xmlUtils.toString());
			$('#design_form').form('submit',{ 
				url:'indexpage/indexpage!update_Index.action', 
				onSubmit:function(){ 
					return $(this).form('validate'); 
				}, 
				success:function(data){ 
					$.messager.alert('提示',data,'info',function(){
	             		$("#page_setpanel").hide();	
	             		refreshDesignViewIframe();
					});
				} 
			}); 
	}
	
	function addrowdata(divid,modelid){
		//查询是否已存在结点
		for(var i=0;i<rows.length;i++){
			if(rows[i].id==modelid){
				var entry = xmlUtils.createNode('entry',"",{},root);
				var key = xmlUtils.createNode('key',divid,{},entry);
				var model = xmlUtils.createNode('model',"",{},entry);
				var name = xmlUtils.createNode('name',rows[i].name,{},model);
				var url = xmlUtils.createNode('url',rows[i].url,{},model);
				var role = xmlUtils.createNode('role',roleids,{},model);
				roleids="";
			}
		}
	}
	
	var roleids="";
	function getrols(id){
		 $("#rolelist").window('close');
		 roleids=id;
		 $.messager.alert('提示',"设置访问角色成功",'info');
	}
	
	//刷新显示
	function refreshDesignViewIframe(){
		window.frames['designview'].location.reload();
	}
	
	function cancel(){
		$("#page_setpanel").hide();
	}
</script>
	
<div class="easyui-panel" fit="true" border="false" style="padding:3px 0 3px 3px;">
		<iframe  id="designview" src='indexpage/indexpage!load_Index.action?indexPageTemplateValue=${indexPageTemplateValue}&indexSetingId=${subSystemId}&isconfig=true' style="width:100%;height:100%;margin: 0;padding:0;" framespacing="0" marginwidth="0" marginheight="0" frameborder="0" >
		</iframe>
		<!-- 	<textarea id ="tempshow" cols="90" rows="90"></textarea>  -->
		<div id="page_setpanel"
			style="width: 180px; background: #fafafa; border: 1px solid #8DB2E3; position: absolute; top: 70px; right: 60px;margin-left:10px;display: none">
			<div id="page_setpaneltitle"
				class="panel-header panel-header-noborder">
				配置面板
			</div> 
			<div style="width: 180px;margin:8px;"><br/>
				&nbsp;&nbsp;请为区域设置匹配的模块：<br/><br/>
				&nbsp;&nbsp;模块选择：<select id="modelIdBtn" class="easyui-combobox"  style="width:70px;">
					<option value="请选择">请选择</option>
				</select>
				
				<!-- 
				 &nbsp;<a  id="authorityButtonForIndexPageSetting" href="javascript:void(0);" icon='icon-auth-group' onclick="authorityAll();"  ></a>
				 -->
				<br><br>&nbsp;&nbsp;&nbsp;
					<a class="easyui-linkbutton" href="javascript:void(0);"
					style="width: 60px;" plain="true" icon="icon-add" required="true"
					onclick="bindmodel();">确定</a>
					
					<a class="easyui-linkbutton" href="javascript:void(0);"
					style="width: 60px;" plain="true" icon="icon-cancel"
					onclick="cancel();">取消</a>
					
				<br/><br/>
				
				<form id="design_form" method="post" style="margin: 0;padding:0;">
					<input type="hidden" id="xmlparam" name="xmlparam"/>
					<input type="hidden"  name="subSystemId" value='<s:property value="subSystemId"/>'/>
				</form>	
			</div>
		</div>
		<div id="rolelist">
		</div>  
</div>
