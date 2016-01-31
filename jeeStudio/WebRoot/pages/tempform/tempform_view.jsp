<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<form id="tempform_form" method="POST">
	<table border="0" cellpadding="4" cellspacing="0">
		  <tr>
			    <td align="right"><font size="2">
					部门名称：</font>
				</td>
			    <td>
			   		${field.departmentName}
			    </td>
			    <td align="right">
				    <font size="2">
						上级主管：
					</font>
				</td>
			    <td>
			    	${field.managerId}
			    </td>
			</tr>
		    <tr>
			    <td align="right">
		    		<font size="2">
						地址：
					</font>
				</td>
			    <td>
			    	${field.locationId}
			    </td>
			    <td align="right">
			    	<font size="2">
						部门ID：
					</font>
				</td>
			    <td>
			    	${field.departmentId}
			    </td>
			</tr>
		    <tr>
			    <td align="right">
				      <font size="2">
							系统添加：
					  </font>
				</td>
			    <td>
			    ${field.appId}
			    </td>
			
		  
				    <td align="right"><font size="2">
						系统添加：</font>
					</td>
			    <td>
			    ${field.parentAppId}
			    </td>
			</tr>
		    <tr>
			    <td align="right">
				    <font size="2">
						工作流字段：
					</font>
				</td>
			    <td>${field.envDatameter}</td>
				<td align="right">
			       <font size="2">状态：</font>
				</td>
			    <td>
			    	${field.envDatastate}
			    </td>
			</tr>
	</table>
	<div align="center" style="padding:40px;">
		<a href="#" class="easyui-linkbutton" icon="icon-cancel" onclick="close_tempform();">关闭</a>
	</div>
</form>