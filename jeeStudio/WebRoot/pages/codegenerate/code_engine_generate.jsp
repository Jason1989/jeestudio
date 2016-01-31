<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<form id="fd_code_form" method="post">
	          <input id="forms_id" name="forms_id" value="${formId}" type="hidden" />
			  <input id="form_type" name="form_type" value="${type}" type="hidden" />
			  <input class="easyui-validatebox" type="hidden" size="40" name="basePath" value="<%=basePath%>"  required="true">
			      <table>
			         <tr>
			            <td align="right">生成目录：</td><td><input class="easyui-validatebox" type="text" name="base_path" size="40" value="d:/customFile" required="true"><font color="red">&nbsp;*</font></td>
			         </tr>
			         <tr>
			            <td align="right">页面名称：(.jsp)</td><td><input class="easyui-validatebox" id="formName" size="40" type="text" name="formNameJsp" value="${formName}" required="true"><font color="red">&nbsp;*</font></td>
			         </tr>
			         
			         
			         
			      </table>
         <div align="center" style="padding: 40px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-ok"
				onclick="engineCodeGenerate()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-cancel"
				onclick="closeWin()">关闭</a> 
		</div>
 </form>