	${page}
	<form id="${className}_form" method="POST">
	<input type="hidden" name="typeMethod" value="${type}" />
		<table border="0" cellpadding="4" cellspacing="0">
			<#list fields as field>
			  <#if (field_index+1)%2=1><tr></#if>
					    <td align="right"><font size="2">
							${field.textColumn.name}：</font>
						</td>
				    <td>
				    <@zxtInput name="${field.name}" value="${field.data}" type="${field.type}" dictionaryId="${field.editMode.data.dictionaryId}" />
				</td>
				<#if (field_index+1)%2=0></tr></#if>
				<#assign a=(field_index+1)%2>
				</#list>
				<#if a=0>
				<td></td>
				</tr>
				</#if>
		</table>
		
		<div align="center" style="padding: 40px;">
			<a href="#" class="easyui-linkbutton" icon="icon-ok"
				onclick="submit_${className}()">保存</a>
			<a href="#" class="easyui-linkbutton" icon="icon-cancel"
				onclick="close_${className}()">关闭</a>
		</div>
	</form>
