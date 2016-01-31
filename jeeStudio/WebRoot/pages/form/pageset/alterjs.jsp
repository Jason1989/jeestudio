<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'alterjs.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script>
		/**
		*保存修改后的业务逻辑
		*/
		var row_alterjs = $('#fm_listpageset_operateButton_table').datagrid('getSelected');
		$(function(){
		
			//页面
			$("#tabspageurl_alterJS").combobox({
				valueField:'id',
   				textField:'text',  
            	treeWidth:157,
            	panelHeight:160,
			    url:'form/form!getTabsPageList.action?mainObjectId=' + mainObjectId + '&pageType=editPage&random='+parseInt(10*Math.random())
			});
			
			//页面类型
			$("#tabspagetype_alterJS").combobox({
				valueField:'value',
   				textField:'text',
   				data:[{value:'listPage',text:'列表页'},{value:'editPage',text:'编辑页',selected:true},{value:'viewPage',text:'查看页'}],
				onChange:function(newValue,oldValue){			
					$("#tabspageurl_alterJS").combobox("setValue","");
					$("#tabspageurl_alterJS").combobox({						
						url:'form/form!getTabsPageList.action?mainObjectId=' + mainObjectId + '&pageType=' + newValue+ '&random='+parseInt(10*Math.random())
					});
					if('listPage' == newValue){
						$('#params_alterjs').removeAttr('disabled');					
					}else{
						$('#params_alterjs').attr('disabled','disabled');			
					}	
				}
			});
			
			//加载业务guize
			if(row_alterjs && row_alterjs.buttonrules != null && row_alterjs.buttonrules != ""){
				var buttonrules = eval("("+row_alterjs.buttonrules+")");
				if('zidingyi' == buttonrules.isJS){
					$('#funcname_alterjs').val(buttonrules.funcname);
					$('#method_alterjs').val(decodeURIComponent(buttonrules.funcmethod));
				}else if('jiekou' ==buttonrules.isJS){
					$("#jiekoudiv").css('display','block');
					$("#zidingyidiv").css('display','none');
					setTimeout(function(){
						$('#tabspagetype_alterJS').combobox('setValue',buttonrules.tabspagetype);
						$('#tabspageurl_alterJS').combobox('setValue',buttonrules.tabspageurl);
						$('#title_alterjs').val(buttonrules.tabstitle);
						if('listPage' == buttonrules.tabspagetype){
							$('#params_alterjs').removeAttr('disabled');
							$('#params_alterjs').val(buttonrules.params);					
						}else{
							$('#params_alterjs').attr('disabled','disabled');			
						}	
					
					},500);
					
				}else{
					buttonrules.isJS = 'zidingyi';
				}
				$("input[name=isJS]").each(function(){
					if($(this).val() == buttonrules.isJS){
						$(this).attr('checked',true);
					}else{
						$(this).attr('checked',false);
					}
				})
				
			}else{
				$("input[name=isJS]").each(function(){
					if($(this).val() == 'zidingyi'){
						$(this).attr('checked',true);
					}else{
						$(this).attr('checked',false);
					}	
				})
			
			}
			
			$("input[name=isJS]").click(function(){
				if($(this).val() =='jiekou'){
					$("#jiekoudiv").css('display','block');
					$("#zidingyidiv").css('display','none');
				}else{
					$("#jiekoudiv").css('display','none');
					$("#zidingyidiv").css('display','block');
				}
			
			})
		
		})
	
		function saveAlterJS(){
			if($("input[name=isJS]:checked").val() == 'zidingyi'){
				//自定义js
				if('' != $('#funcname_alterjs').val()){
					var buttonrules = new Object();
					buttonrules.funcname = $('#funcname_alterjs').val();
					buttonrules.funcmethod = encodeURIComponent($('#method_alterjs').val());
					$("input[name=isJS][@checked]").each(function(){
						if($(this).attr('checked')){
							buttonrules.isJS = $(this).val();
						}
					})
					row_alterjs.buttonrules = JSON2.stringify(buttonrules);
					$.messager.alert('提示','保存成功','info');
					closeWindow('ParentOpenWindow');
				}else{
					$.messager.alert('提示','函数名为空','info');
					return;
				}
			}else if($("input[name=isJS]:checked").val() == 'jiekou'){
				//平台接口
				if($('#tabspageurl_alterJS').combobox('getValue')==''){
					$.messager.alert('提示','请选择页面','info');
					return;
				}else if($('#title_alterjs').val()==''){
					$.messager.alert('提示','标题为空','info');
					return;
				}else{
					var buttonrules = new Object();
					buttonrules.tabspagetype = $('#tabspagetype_alterJS').combobox('getValue');
					buttonrules.tabspageurl = $('#tabspageurl_alterJS').combobox('getValue');
					buttonrules.tabstitle = $('#title_alterjs').val();
					if('listPage'==$('#tabspagetype_alterJS').combobox('getValue')){
						buttonrules.params = $('#params_alterjs').val();
					}
					$("input[name=isJS][@checked]").each(function(){
						if($(this).attr('checked')){
							buttonrules.isJS = $(this).val();
						}
					})
					row_alterjs.buttonrules = JSON2.stringify(buttonrules);
					$.messager.alert('提示','保存成功','info');
					closeWindow('ParentOpenWindow');
				};
			}
		}
	</script>
  </head>
  
  <body>
  		<div style="width:670px;height:239px;margin-left: 20px;margin-top: 15px;">
  			<input type="radio" name='isJS' value='zidingyi'> 自定义触发函数 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  			<input type="radio" name='isJS' value='jiekou'>使用平台接口 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>
  			<div id="zidingyidiv" style="width:100%;height:85%;padding-top: 15px;">
  			
  				<table>
  					<tr>
  						<td align="right">函数名：</td>
  						<td><font color="green">function &nbsp;&nbsp; <input type="text" id="funcname_alterjs" style="width: 180px;"/>&nbsp;(rowData)</font></td>
  						<td align='center'>注：函数会带有默认参数本行数据 rowData</td>
  					</tr>
  					<tr>
	  					<td align="right">方法体：</td>
	  					<td><font color="green">{&nbsp;&nbsp;<textarea id="method_alterjs" cols="39" rows="39"></textarea>&nbsp;&nbsp;}&nbsp;</font></td>
	  					<td align='center'><font color='red'>字符串变量不要用单引号，要用双引号</font></td>
  					</tr>
  				</table>
  			</div>
  			
  			<div id="jiekoudiv" style="width:100%;height:85%;padding-top: 35px;display: none;text-align: center;">
  				<table>
  					<tr>
  						<td align="right">
  							页面类型：
  						</td>
  						<td align="left">
  							<input id="tabspagetype_alterJS" style="width:100px;"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  						</td>
  						<td align="right">
							页面：
						</td>
						<td align="left">
							<select id="tabspageurl_alterJS" style="width:260px;"></select>
						</td>
				   </tr>
				   <tr>
				   		<td align="right">
							标题：
						</td>
						<td align="right">
							<input type="text" id="title_alterjs" style="width: 180px;"/>&nbsp;
						</td>
						<td align="right">
							参数：
						</td>
						<td align="left">
							<input type="text" id="params_alterjs" style="width: 260px;" disabled="disabled"/>&nbsp;
						</td>
				   </tr>
				   <tr>
				   		<td align="right" colspan="4">
							注：参数格式 a=1&b=2&c=#ccc a,b,c为参数名，后面为值 带#号的为这行数据的字段值 ccc为字段名
						</td>
				   </tr>
				</table>
  			</div>
	    </div>
   		<div style="width:670px;height:50px;margin-left: 20px;text-align: center;padding-top:15px; ">
	    	<a  class="easyui-linkbutton" icon="icon-ok"  href="javascript:saveAlterJS();"  >保存</a>
			<a  class="easyui-linkbutton" icon="icon-cancel"  href="javascript:closeWindow('ParentOpenWindow');">关闭</a>	
	    </div>
  </body>
</html>
