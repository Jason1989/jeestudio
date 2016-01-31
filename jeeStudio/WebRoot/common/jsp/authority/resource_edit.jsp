<%@page language="java" contentType="text/html; charset=UTF-8" %>
<div id="resource_edit_window"  class="easyui-window" closed=true  title="资源编辑" style="width: 700px; height: 550px;" >
	<div class="easyui-layout" fit="true">
			<div region="center"  id="resourceEditData"  border="false" style="padding:10px;background:#fff;border:1px solid #ccc;width:100%;height:365px;">
				<jsp:include page="authority-att.jsp"></jsp:include>
			</div>
			<div region="south" border="false" style="text-align:center;height:50px;line-height:30px;">
				<table width="100%" >
					<tr height="5px;" ></tr>
					<tr>
						<td align="right" >
							<a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-save"  onclick="saveAuortityGrid('resourceEditForm','systemAutorityList','resource_edit_window');" >保存</a>
						</td> 
						<td align="center" width="10px;" >
						</td>  
						<td align="left" >
							<a  href="javascript:void(0);" class="easyui-linkbutton" icon="icon-cancel" onclick="$('#resource_edit_window').window('close');">关闭</a>
						</td>   
					</tr>
				</table>
			</div>
		</div>
</div>