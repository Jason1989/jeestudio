	﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<form id="tempform_form" method="POST">
	<input type="hidden" name="typeMethod" value="${typeMethod}" />
		<table border="0" cellpadding="4" cellspacing="0">
			  <tr>
					    <td align="right"><font size="2">
							部门名称：</font>
						</td>
				    <td>
<input class="easyui-validatebox" name="departmentName" value="${field.departmentName}" required="true" validType="length[1,32]">&nbsp;&nbsp;<font size="2" color="red">*</font>				</td>
				
			  
					    <td align="right"><font size="2">
							上级主管：</font>
						</td>
				    <td>
<input class="easyui-validatebox" name="managerId" value="${field.managerId}" required="true" validType="length[1,32]">&nbsp;&nbsp;<font size="2" color="red">*</font>				</td>
				</tr>
			  <tr>
					    <td align="right"><font size="2">
							地址：</font>
						</td>
				    <td>
<input class="easyui-validatebox" name="locationId" value="${field.locationId}" required="true" validType="length[1,32]">&nbsp;&nbsp;<font size="2" color="red">*</font>				</td>
				
			  
					    <td align="right"><font size="2">
							部门ID：</font>
						</td>
				    <td>
<input class="easyui-validatebox" name="departmentId" value="${field.departmentId}" required="true" validType="length[1,32]">&nbsp;&nbsp;<font size="2" color="red">*</font>				</td>
				</tr>
			  <tr>
					    <td align="right"><font size="2">
							系统添加：</font>
						</td>
				    <td>
<input class="easyui-validatebox" name="appId" value="${field.appId}" required="true" validType="length[1,32]">&nbsp;&nbsp;<font size="2" color="red">*</font>				</td>
				
			  
					    <td align="right"><font size="2">
							系统添加：</font>
						</td>
				    <td>
<input class="easyui-validatebox" name="parentAppId" value="${field.parentAppId}" required="true" validType="length[1,32]">&nbsp;&nbsp;<font size="2" color="red">*</font>				</td>
				</tr>
			  <tr>
					    <td align="right"><font size="2">
							工作流字段：</font>
						</td>
				    <td>
<input class="easyui-validatebox" name="envDatameter" value="${field.envDatameter}" required="true" validType="length[1,32]">&nbsp;&nbsp;<font size="2" color="red">*</font>				</td>
				
			  
					    <td align="right"><font size="2">
							状态：</font>
						</td>
				    <td>
<input class="easyui-validatebox" name="envDatastate" value="${field.envDatastate}" required="true" validType="length[1,32]">&nbsp;&nbsp;<font size="2" color="red">*</font>				</td>
				</tr>
		</table>
		
		<div align="center" style="padding: 40px;">
			<a href="#" class="easyui-linkbutton" icon="icon-ok"
				onclick="submit_tempform()">保存</a>
			<a href="#" class="easyui-linkbutton" icon="icon-cancel"
				onclick="close_tempform()">关闭</a>
		</div>
	</form>
