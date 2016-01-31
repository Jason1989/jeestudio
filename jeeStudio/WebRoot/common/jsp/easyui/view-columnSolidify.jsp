<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.ViewColumn"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.TextColumn"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	String dataStart="${viewPage.viewColumn[";//${editPage.editColumn[index].data}
	String dataEnd="].data}";
%>
<%
	try{
		  ViewColumn viewColumn=(ViewColumn) request.getAttribute("editColumnList");
	  	  TextColumn textColumn=null;
		  if(viewColumn!=null){
		  	 textColumn= viewColumn.getTextColumn();
		  	 if(textColumn.getExclusiveLine()!=null){
		  	    if(textColumn.getExclusiveLine().booleanValue()){
		  	    	if((Integer.parseInt(request.getAttribute("lineCount").toString())+1)==2){
							out.write("</tr>\r\n<tr>");//2列 换行
					}
		  		 }
		  	 }
		  }
	}catch(Exception e){
	
	}
%>
<!-- 此页面是第一次固化 所引用的页面 -->
<c:choose>
	<c:when test="${type eq '9' }">
		 <% out.write("</tr>\r\n <tr>"); %>
		<td id="${textColumn.name }_label_id" colspan="1" style="font-size:12px;background-color: #e9e9e9;width:100px;height:22px;border-bottom:0px solid white;" align="right"> 
			<label >${textColumn.name }：</label> 
		</td>
		<td id="${textColumn.name }_text_id" colspan="3">
			<input id="viewColumn_${stauts.index}${columnID}" type="hidden" name="${name}"   value="<%=dataStart%>${stauts.index}<%=dataEnd%>" />
			<div class="easyui-panel" style="width:535px;height:120px;">
				<table  id="uploadgrid_view_${stauts.index}_${columnID}"></table>
			</div>
			<div id="img_upload_yulan_view_${stauts.index}${columnID}_view">
			</div>
		</td>
		 <% out.write("</tr>\r\n<tr>"); %>
			<script>
				$(function(){
					var val = $('#viewColumn_${stauts.index}${columnID}').val();
					var str;
					if(val!=null&&val!=""){
						str = '<%=request.getContextPath()%>/formengine/uploadFindAction!find.action?time='+new Date().getTime()+'&columnUploadID='+val;
					}else{
						str = '<%=request.getContextPath()%>/formengine/uploadFindAction!find.action?time='+new Date().getTime();
					}
					$('#uploadgrid_view_${stauts.index}_${columnID}').datagrid({
						fit:true,
				        iconCls:'icon-detail',
				        nowrap: false,
						url:str,
						remoteSort: false,
						fitColumns:true,
						striped: true,
						columns:[[
					        {
					        	title:'文件名称',
					        	field:'FILE_RNAME',
					        	width:1,
					        	align:'center'
					        },
					        {
					        	title:'文件大小',
					        	field:'FILE_SIZE',
					        	width:1,
					        	align:'center'
					        },
					        {
					        	title:'文件类型',
					        	field:'FILE_TYPE',
					        	width:1,
					        	align:'center'
					        },
					        {
					        	title:'上传时间',
					        	field:'UPLOAD_DATE',
					        	width:1,
					        	align:'center'
					        },
					        {
					        	title:'上传人',
					        	field:'USER_NAME',
					        	width:1,
					        	align:'center'
					        },
					        {
					        	title:'操作',
					        	field:'opt',
					        	width:1,
					        	align:'center',
					        	formatter:function(value,rec){
					        		var file = rec.FILE_NAME+rec.FILE_TYPE;
					        		var path = rec.FILE_PATH;
					        		var rname = rec.FILE_RNAME+rec.FILE_TYPE;
					        		var fileID = rec.FILE_ID;
					        		var url = "<%=request.getContextPath()%>/formengine/uploadFindAction!download.action?time="+new Date().getTime()+"&path="+path+"&filername="+rname;
									url = encodeURI(encodeURI(url));
									
									return "<span  style='line-hieght:30px;vertical-align:middle;'><img id='img_icon' src='<%=request.getContextPath()%>/jquery-easyui-1.1.2/themes/icons/arrow_down_green.png' title='下载' width='14px'  onclick='downloan_file(\""+path+"\",\""+rname+"\")'/></span>";
								
									/**&nbsp;&nbsp;<img src='<%=request.getContextPath()%>/jquery-easyui-1.1.2/themes/icons/search.png' title='预览' width='16px' onclick=upload_view('"+file+"') />
									*/
								}
					        }
						]]
					});
				})
				function upload_view(file){
					var name = file;
					$('#img_upload_yulan_view_${stauts.index}${columnID}_view').window({
						title: '图片预览',
						width: 400,
						minimizable:false,
						maximizable:false,
						collapsible:false,
						modal: true,
						shadow: true,
						closed: true,
						height: 300
					})
					$('#img_yulan_view_${stauts.index}${columnID}_view').remove();
					$('#img_upload_yulan_view_${stauts.index}${columnID}_view').append("<img width='385' id='img_yulan_view_${stauts.index}${columnID}_view' height='260' src='<%=request.getContextPath()%>/upload/file"+name+"'>");
					$('#img_upload_yulan_view_${stauts.index}${columnID}_view').window('open');
				}
		   </script>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${textColumn.exclusiveLine eq 'true' }">
				<td id="${textColumn.name }_label_id" colspan="1" style="font-size:12px;background-color: #e9e9e9;width:100px;height:22px;border-bottom:0px solid white;" align="right"> 
					<label >${textColumn.name }：</label> 
				</td>
				<td id="${textColumn.name }_text_id" colspan="3" style="word-break:break-all;width: 500px;height:22px;border:1px solid #e9e9e9;word-spacing: 40px;" ><input id="${name }_view_id" type="hidden" value='<%=dataStart%>${stauts.index}<%=dataEnd%>'/><%=dataStart%>${stauts.index}<%=dataEnd%>&nbsp;</td>
			</c:when>
			<c:otherwise>
				<td id="${textColumn.name }_label_id" colspan="1" style="font-size:12px;background-color: #e9e9e9;width:100px;height:22px;border-bottom:0px solid white;" align="right"> 
					<label >${textColumn.name }：</label> 
				</td>
				<td id="${textColumn.name }_text_id" colspan="1" style="word-break:break-all;width: 200px;height:22px;border:1px solid #e9e9e9;" ><input id="${name }_view_id" type="hidden" value='<%=dataStart%>${stauts.index}<%=dataEnd%>'/><%=dataStart%>${stauts.index}<%=dataEnd%>&nbsp;</td>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<jsp:include page="view-column-linecode.jsp"></jsp:include>
