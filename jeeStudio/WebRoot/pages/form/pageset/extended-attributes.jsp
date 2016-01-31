<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div  id="extended-attributes" >
	<div id="16_extendAttributes" >
		<table>
			<tr>
				<td align="right">日期格式：</td>
				<td colspan="2" >
					<select id="fm_editpageset_dc_dateformat" class="easyui-combobox" style="width:129px;">
					</select>
				</td>
			</tr>
		</table>
	</div>
	<div id="3_extendAttributes" >
		<table>
			<tr>
				<td align="right">行：</td>
				<td><input id="fm_editpageset_dc_rows" type="text"   /></td>
				<td align="right">列：</td>
				<td><input id="fm_editpageset_dc_cols"  type="text"   /></td>
   	 		</tr> 
		</table>
	</div>
	<div id="6_extendAttributes" >
		<table>
			<tr>
				<td align="right">是否多选树：</td>
				<td><input id="fm_editpageset_tree_isCheckBox" type="checkbox"   /></td>
				<td align="right">只选择子节点：</td>
				<td><input id="fm_editpageset_tree_isSpread"  type="checkbox"   /></td>
   	 		</tr> 
		</table>
	</div>
</div>
<script>
  
  //alert(document.getElementById('fm_editpageset_dc_tagtype').value);
	changeExtendedAttributes('extended-attributes','');
  	/**
	 * 年月日 选择
	 */
	 $(function(){
	 		$('#fm_editpageset_dc_rows').numberbox({});
  			$('#fm_editpageset_dc_cols').numberbox({});
			var dateFormat=[
							{id:'0',text:'请选择'},
							{id:'1',text:'yyyy'},
							{id:'2',text:'yyyy-MM'},
							{id:'3',text:'yyyy-MM-dd'}	,
							{id:'4',text:'yyyy-MM-dd HH:mm'},
							{id:'5',text:'yyyy-MM-dd HH:mm:ss'}
						];
			$('#fm_editpageset_dc_dateformat').combobox({
				listWidth:148,
				data:dateFormat,
				valueField:'id',
				editable:false,
		  		textField:'text'
			});
	 });
</script>