<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<form id="fd_code_form" method="post">
	          <input id="forms_id" name="forms_id" value="${formId}" type="hidden" />
			      <table>
			         <tr>
			            <td align="right">生成目录：</td><td><input class="easyui-validatebox" type="text" name="base_path" value="d:/tempform" required="true"><font color="red">&nbsp;*</font></td>
			         </tr>
			         <tr>
			            <td align="right">代码包名：</td><td><input class="easyui-validatebox" type="text" name="package_name" value="com.zxt.tempform" required="true"><font color="red">&nbsp;*</font></td>
			         </tr>
			         <tr>
			            <td align="right">jar包文件名：</td><td><input class="easyui-validatebox" type="text" name="jar_name" value="tempform" required="true"><font color="red">&nbsp;*&nbsp;&nbsp;(请使用小写)</font></td>
			         </tr>
			          <tr>
			            <td align="right">页面代码根目录：</td><td><input class="easyui-validatebox" type="text" name="page_path" value="/pages/tempform" required="true"><font color="red">&nbsp;*</font></td>
			         </tr>
			         <tr>
			            <td align="right">版本说明：</td><td><textarea name="version_remark" style="height:60px;"></textarea></td>
			         </tr>
			      </table>
         <div align="center" style="padding: 40px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-ok"
				onclick="codeGenerate()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-cancel"
				onclick="closeWin()">关闭</a> 
		</div>
 </form>