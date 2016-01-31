	<form id="edit_form" method="POST">
	<input type="hidden" name="typeMethod" value="${type}" />
		<table border="0" cellpadding="4" cellspacing="0">
			<#list fields as field>
			  <#if (field_index+1)%2=1><tr></#if>
					    <td align="right"><font size="2">
							${field.text.name}£º</font>
						</td>
				    <td>
				    <@zxtInput name="${field.name}" value="${field.value}" type="${field.type}"/>
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
				onclick="doSubmit()">±£´æ</a>
			<a href="#" class="easyui-linkbutton" icon="icon-cancel"
				onclick="doClose()">¹Ø±Õ</a>
		</div>
	</form>
