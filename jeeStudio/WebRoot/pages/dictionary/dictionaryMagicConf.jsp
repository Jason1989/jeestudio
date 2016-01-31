<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <script type="text/javascript">
  		var keys;
		var vals;
		var allcols;
		function init(){
			//初始化combo的显示
		 	var str=parent.currentEditRow.extra;
		 	var cols=str.split("&");
		 	var keystemp=cols[0].split(",");
		 	var valstemp=cols[1].split(",");
		 	var allcolstemp=cols[2].split(",");
		 	
		 	//设置key combobox
		 	var temp="[";
		 	for(var i=0;i<keystemp.length-1;i++){
		 		temp+="{\"column\":\""+keystemp[i]+"\"}";
		 		if(i<keystemp.length-2){
		 			temp+=",";
		 		}
		 	}
		 	temp+="]";
		 	keys=eval(temp);
		 	
		 	//设置val combobox
		 	temp="[";
		 	for(var i=0;i<valstemp.length-1;i++){
		 		temp+="{\"column\":\""+valstemp[i]+"\"}";
		 		if(i<valstemp.length-2){
		 			temp+=",";
		 		}
		 	}
		 	temp+="]";
		 	vals=eval(temp);
		 	
		 	//设置allcolumn combobox
		 	temp="[";
		 	for(var i=0;i<allcolstemp.length-1;i++){
		 		temp+="{\"column\":\""+allcolstemp[i]+"\"}";
		 		if(i<allcolstemp.length-2){
		 			temp+=",";
		 		}
		 	}
		 	temp+="]";
		 	allcols=eval(temp);
		 };
		 init();
		 
		 
		 $(function(){
		 	$('#KeyCol').combobox({
		 		data:keys,
				valueField:'column',
				textField:'column',
				editable:false,
				onChange:change
			});
			
			$('#ValCol').combobox({
				data:vals,
				valueField:'column',
				textField:'column',
				editable:false,
				onChange:change
			});
			
			$('#allCol').combobox({
				data:allcols,
				valueField:'column',
				textField:'column',
				editable:false,
				onChange:change
			});
			
			//设置默认选中状态
		 	var aaaKey=parent.currentEditRow.columnKey;
		 	var bbbVal=parent.currentEditRow.columnVal;
		 	$("#KeyCol").combobox('setValue',aaaKey);
		 	$("#ValCol").combobox('setValue',bbbVal);
		 	$("#KeyCol").combobox('setText',aaaKey);
		 	$("#ValCol").combobox('setText',bbbVal);
		 	
		 	$("#dataDictionaryname").val(parent.currentEditRow.name);
		 	$("#expression").val(parent.currentEditRow.expression);
		 	$("#showthird").hide();
		 });
		 
		//返回修改
    	function submitConfi(){  
    		$("#dictionaryMagicAddListTable").datagrid('getRows')[parent.currentindex].name=$("#dataDictionaryname").val();
    		$("#dictionaryMagicAddListTable").datagrid('getRows')[parent.currentindex].expression=$("#expression").val();
    		$("#dictionaryMagicAddListTable").datagrid('getRows')[parent.currentindex].columnKey=$("#KeyCol").combobox('getValue');
    		$("#dictionaryMagicAddListTable").datagrid('getRows')[parent.currentindex].columnVal=$("#ValCol").combobox('getValue');
    		$('#dictionaryMagicAddListTable').datagrid('acceptChanges').datagrid('refreshRow',parent.currentindex);
    		parent.$('#dicConfig').window('close');			
			}
			
		//combobox值改变触发事件
		function change(){
			var key=$("#KeyCol").combobox('getValue');
			var val=$("#ValCol").combobox('getValue');
			var expression=$("#expression").val();
			
			var index=expression.indexOf(' from');
			var end=expression.substring(index,expression.length);
			
			var cc=$("#radiothird").attr('checked');
			if(cc==true){
				var extra=$("#allCol").combobox('getValue');
				document.getElementById("expression").value="select "+key+","+val+","+extra+end;
			}else{
				document.getElementById("expression").value="select "+key+","+val+end;
			}
		}
		
		//是否显示扩展列
		function ifable(){
			var cc=$("#radiothird").attr('checked');
			if(cc==true){
				$("#showthird").show();
			}else{
				$("#showthird").hide();
				change();
			}
		}
</script>
  <body class="easyui-layout">
  <form id="magicconfig">
    <div region="north" border="false" height:300px;>
   	   <div class="easyui-panel" fit="true" border="false" style="padding: 12px 0 ;">
   	      <div style="padding-left: 13px;vertical-align:middle; ">
   	      	<div style="line-height: 26px;display: inline;">表名称： 
   	      		<input id="dataDictionaryname"  required="true" readonly="readonly" style="width:180px;"> </input><font size="2" color="red">*</font>
			</div><br/><br/>
			<div style="line-height: 26px;display: inline;">键列：</div>
				<select id="KeyCol" ></select>&nbsp;&nbsp;&nbsp;&nbsp;
			<div style="line-height: 26px;display: inline;">值列：</div>
				<select id="ValCol" ></select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<br/>
			<div style="line-height: 26px;display: inline;">是否启用第三列：<input type="checkbox" onclick="ifable()" id="radiothird"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<div style="line-height: 26px;display: inline;"  id="showthird">扩展字段：
					<select id="allCol" ></select>
				</div>
			</div>
   	      </div>
   	   </div>
   </div>
   <div region="center" border="false"> 
         <div class="easyui-layout" fit="true" border="false">
         	&nbsp;&nbsp;表达式:
         	<textarea cols="45" rows='50' required="true" id="expression"  readonly="readonly"></textarea>
         	<font size="2" color="red">*</font>
         </div>
   </div>
   <div region="south" border="false" style="height:50px;padding-top:13px;">
       <table style="width: 100%;">
          <tr>
             <td align="center"><a class="easyui-linkbutton" href="javascript:void(0);" onclick="submitConfi()" icon="icon-save">修改</a></td>
             <td align="center"><a class="easyui-linkbutton" href="javascript:void(0);" onclick="parent.$('#dicConfig').window('close');" icon="icon-cancel">取消</a></td>
          </tr>
       </table>
   </div>
   </form>
