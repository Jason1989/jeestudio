<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div  id="extended-attributes-query" >
	<div id="16_extendAttributes_query" style="display: none;">
		<table>
			<tr>
				<td align="right">日期格式：</td>
				<td colspan="2" >
					<select id="dateformat-query" class="easyui-combobox" style="width:129px;">
					</select>
				</td>
			</tr>
		</table>
	</div>
	<div id="3_extendAttributes_query" style="display: none;">
		<table>
			<tr>
				<td align="right">行：</td>
				<td><input id="query-rows" type="text"   /></td>
				<td align="right">列：</td>
				<td><input id="query-cols"  type="text"   /></td>
   	 		</tr> 
		</table>
	</div>
</div>
<script>
  	/**
	 * 年月日 选择
	 */
	 $(function(){
	 		$('#query-rows').numberbox({});
  			$('#query-cols').numberbox({});
			var dateFormat=[
							{id:'0',text:'请选择'},
							{id:'1',text:'yyyy'},
							{id:'2',text:'yyyy-MM'},
							{id:'3',text:'yyyy-MM-dd'}	,
							{id:'4',text:'yyyy-MM-dd HH:mm'},
							{id:'5',text:'yyyy-MM-dd HH:mm:ss'}
						];
			$('#dateformat-query').combobox({
				listWidth:148,
				panelHeight:'auto',
				data:dateFormat,
				valueField:'id',
				editable:false,
		  		textField:'text'
			});
	 });
</script>