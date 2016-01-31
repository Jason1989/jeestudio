<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@taglib prefix="FCK" uri="/WEB-INF/FCKeditor.tld"%>
<%@page import="com.zxt.compplatform.formengine.entity.view.EditColumn"%>
<% EditColumn editColumn=(EditColumn) request.getAttribute("editColumnList"); %>
<c:choose>
	<c:when test="${type==11}">
 		 	<input type="hidden" name="${name}" value="${data}" />
	</c:when>
	<c:when test="${type==5}"><!--Fck 编辑-->
	    <% out.write("</tr><tr>"); %>
		<td colspan="4" style="border-bottom:1px solid white;">
			<div class="panel-header panel-header-noborder" style="font-weight: normal;" >&nbsp;${textColumn.name}</div>
			<FCK:editor instanceName="${name}" width="560px"  height="200px" value="${data}"   ></FCK:editor>
		</td>
		<%  
		    out.write("</tr><tr>");
			request.setAttribute("lineCount","0"); 
		%>
		
	</c:when>
	
	<c:when test="${type==3}"><!--文本域-->
	    <% out.write("</tr><tr>"); %>
		<td style="font-size:12px;width:100px;background-color: #e9e9e9;border-bottom:1px solid white;" align="right"> 
	    	<label style="width:100px;height:66px;" >${textColumn.name }：</label> 
	    </td>
		<td colspan="3" style="border-bottom:1px solid white;" >
			 <c:choose>
		    	<c:when test="${textColumn.readOnly}">
		    	 	<textarea style="margin:0;width:470px;" id="editColumn_${stauts.index}${columnID}${editPage.id}" name="${name}" readonly=""  >${data}</textarea>
				</c:when>
		    	<c:otherwise>
		    		 <textarea style="margin:0;width:470px;" id="editColumn_${stauts.index}${columnID}${editPage.id}" name="${name}" >${data}</textarea>
				</c:otherwise>
			 </c:choose>
			   <c:choose>
					<c:when test="${editMode.required}">
						&nbsp;&nbsp;<font size="2" color="red">*</font>
					</c:when>
				</c:choose>
				<!-- 验证 -->
				<jsp:include page="/common/jsp/easyui/column-validate.jsp"></jsp:include>
		 </td>
		<%  
		    out.write("</tr><tr>");
			request.setAttribute("lineCount","0"); 
		%>
		
	</c:when>
	
	<c:when test="${type==9}"><!--上传控件 -->
	<% out.write("</tr><tr>"); %>
		<td style="font-size:12px;width:100px;background-color: #e9e9e9;border-bottom:1px solid white;" align="right"> 
	    	<label style="width:100px" >${textColumn.name }：</label> 
	    </td>
		<td colspan="3"  style="border-bottom:1px solid white;">
			<input id="editColumn_${stauts.index}${columnID}${editPage.id}" type="hidden" name="${name}"   value="${data}" />
			<div class="easyui-panel" style="width:475px;height:120px;">
				<table  id="uploadgrid_${stauts.index}_${columnID}"></table>
			</div>
			<div id="img_upload_yulan_view_${stauts.index}_${columnID}">
			</div>
			<script>
				$(function(){
					var val = $('#editColumn_${stauts.index}${columnID}${editPage.id}').val();
					var str;
					if(val!=null&&val!=""){
						str = '<%=request.getContextPath()%>/formengine/uploadFindAction!find.action?time='+new Date().getTime()+'&columnUploadID='+val;
					}else{
						str = '<%=request.getContextPath()%>/formengine/uploadFindAction!find.action?time='+new Date().getTime();
					}
					$('#uploadgrid_${stauts.index}_${columnID}').datagrid({
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
					        	formatter:function(value,rec){
					        		var file = rec.FILE_NAME+rec.FILE_TYPE;
					        		var path = rec.FILE_PATH;
					        		var rname = rec.FILE_RNAME;
					        		var fileID = rec.FILE_ID;
									return "<span  style='line-hieght:30px;vertical-align:middle;'><img id='img_icon' src='<%=request.getContextPath()%>/jquery-easyui-1.1.2/themes/icons/arrow_down_green.png' title='下载' width='14px' style='cursor: hand' onclick='downloan_file(\""+path+"\",\""+rname+"\")'/>&nbsp;&nbsp;<img src='<%=request.getContextPath()%>/jquery-easyui-1.1.2/themes/icons/search.png' title='预览' width='16px' style='cursor: hand' onclick=upload_view('"+file+"') />&nbsp;&nbsp;<img src='<%=request.getContextPath()%>/jquery-easyui-1.1.2/themes/icons/cancel.png' title='删除' width='14px' style='cursor: hand' onclick='deleteFIleUpload(\""+fileID+"\")'/></span>";
								}
					        }
						]],
						toolbar:[{
							id:'btnadd',
							text:'上传',
							titleCls:'link_btn_color',
							iconCls:'icon-add',
							handler:function(){
								val = $('#editColumn_${stauts.index}${columnID}${editPage.id}').val();
								openUploadWindow('upload_mask_${stauts.index}${columnID}${editPage.id}',val);
							}
						}]
					});
				})
				function upload_view(file){
					var name = file;
					$('#img_upload_yulan_view_${stauts.index}_${columnID}').window({
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
					$('#img_yulan_view_${stauts.index}_${columnID}').remove();
					$('#img_upload_yulan_view_${stauts.index}_${columnID}').append("<img width='385' id='img_yulan_view_${stauts.index}_${columnID}' height='260' src='<%=request.getContextPath()%>/upload/file"+name+"'>");
					$('#img_upload_yulan_view_${stauts.index}_${columnID}').window('open');
				}
				
				function downloan_file(path,name){
					var filepath = path;
					var rname = name;
					window.location.href="<%=request.getContextPath()%>/formengine/uploadFindAction!download.action?time="+new Date().getTime()+"&path="+filepath+"&filername="+rname;
				}
				
				function reloadDataGrid(){
					var val = $('#editColumn_${stauts.index}${columnID}${editPage.id}').val();
					var str;
					if(val!=null&&val!=""){
						str = '<%=request.getContextPath()%>/formengine/uploadFindAction!find.action?time='+new Date().getTime()+'&columnUploadID='+val;
					}else{
						str = '<%=request.getContextPath()%>/formengine/uploadFindAction!find.action?time='+new Date().getTime();
					}
					$('#uploadgrid_${stauts.index}_${columnID}').datagrid({url:str});
				}
				
				function deleteFIleUpload(fileID){
					var id = fileID;
					$.ajax({
					   type: "POST",
					   url: "<%=request.getContextPath()%>/formengine/uploadFindAction!delete.action?time="+new Date().getTime()+"&fileID="+id,
					   success: function(msg){
					   		alert('删除成功！');
					   		$('#uploadgrid_${stauts.index}_${columnID}').datagrid("reload");
					   }
					});
				}
		   </script>
			
			
			<c:choose>
				<c:when test="${editMode.required}">
					&nbsp;&nbsp;<font size="2" color="red">*</font>
				</c:when>
			</c:choose>
  				<!-- 验证 -->
			<jsp:include page="/common/jsp/easyui/column-validate.jsp"></jsp:include>
		</td>
		<%  
		    out.write("</tr><tr>");
			request.setAttribute("lineCount","0"); 
		%>
	</c:when>
    <c:otherwise>
   	 <c:choose>
		 <c:when test="${textColumn.exclusiveLine}">
			<%out.write("</tr><tr>"); %>
		</c:when>
	
	 </c:choose>
    
    
       <td style="font-size:12px;width:100px;background-color: #e9e9e9;border-bottom:1px solid white;" align="right"> 
	    	<label style="width:100px" >${textColumn.name }：</label> 
	   </td>
 <!-- *****************************编辑列字段********************************** -->
 		<!-- 独占行 td 宽度 -->
		 <c:choose>
			 <c:when test="${textColumn.exclusiveLine}">
				<%out.write("<td colspan='3' style='border-bottom:1px solid white;' >"); %>
			</c:when>
			 <c:otherwise>
			 	<%out.write("<td style='border-bottom:1px solid white;'>"); %>
			 </c:otherwise>
		 </c:choose>
		 <c:choose>
		    	<c:when test="${type==1}"><!-- 文本框 -->
		    	    <c:choose>
				    	<c:when test="${textColumn.readOnly}">
				    	 	<c:choose>
								 <c:when test="${textColumn.exclusiveLine}">
									<input style="width: 474px;" id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"   value="${data}"  readonly="" />
								</c:when>
								 <c:otherwise>
									 <input id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"   value="${data}"  readonly="" />
								 </c:otherwise>
							 </c:choose>
				    	 </c:when>
				    	 <c:otherwise>
				    		 <c:choose>
								 <c:when test="${textColumn.exclusiveLine}">
										<input style="width: 474px;" id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"   value="${data}" />
		   						 </c:when>
								 <c:otherwise>
										<input id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"   value="${data}" />
			   				      </c:otherwise>
							 </c:choose>
				    		 <jsp:include page="/common/jsp/easyui/column-validate.jsp"></jsp:include>
						</c:otherwise>
				    </c:choose>
				    <c:choose>
							<c:when test="${editMode.required}">
								&nbsp;&nbsp;<font size="2" color="red">*</font>
							</c:when>
					</c:choose>
		    	 	<jsp:include page="/common/jsp/easyui/column-validate.jsp"></jsp:include>
				</c:when>
				<c:when test="${type==2}"><!-- 下拉选 -->
					<c:choose>
						 <c:when test="${textColumn.exclusiveLine}">
							<select class='easyui-combobox'  id="editColumn_${stauts.index}${columnID}${editPage.id}" name='${name}' style='width:473px'  >
						 			<option value="0"  selected="selected" >请选择</option>
									<c:forEach items="${dictionaryData}" var="dictionaryDataMap" > 
										<c:choose>
											<c:when test="${dictionaryDataMap.key ==data}">
												<option value="${dictionaryDataMap.key}" selected="selected" >${dictionaryDataMap.value}</option>
													<% request.setAttribute("otherSelectValue","1"); %>
											</c:when>
											<c:otherwise>
												<option value="${dictionaryDataMap.key}" >${dictionaryDataMap.value}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach> 
									<c:choose>
										<c:when test="${otherSelectValue eq '0' }">
											<option value="${data}" selected="selected" >${data}</option>
										</c:when>
										<c:otherwise> <!-- 改字段列的值在  下拉选数据字典中有值时 清空标志位 -->
												<% request.setAttribute("otherSelectValue","0"); %>
										</c:otherwise>
									</c:choose>
								</select>
								
						 </c:when>
						 <c:otherwise>
						 		<select class='easyui-combobox'  id="editColumn_${stauts.index}${columnID}${editPage.id}" name='${name}' style='width:150px'  >
						 			<option value="0"  selected="selected" >请选择</option>
									<c:forEach items="${dictionaryData}" var="dictionaryDataMap" > 
										<c:choose>
											<c:when test="${dictionaryDataMap.key ==data}">
												<option value="${dictionaryDataMap.key}" selected="selected" >${dictionaryDataMap.value}</option>
													<% request.setAttribute("otherSelectValue","1"); %>
											</c:when>
											<c:otherwise>
												<option value="${dictionaryDataMap.key}" >${dictionaryDataMap.value}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach> 
								</select>
						 </c:otherwise>
					 </c:choose>
						
					<c:choose>
						<c:when test="${editMode.required}">
							&nbsp;&nbsp;<font size="2" color="red">*</font>
						</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${editRulesEngin.is_jilian eq '1'}">
							<script>
								if(document.readyState=="complete"){
									jilian_onclick("editColumn_${stauts.index}${columnID}${editPage.id}","editColumn_${jilian_status}${columnID}${editPage.id}","${editRulesEngin.jilian_column_dictionaryId}");
								}
							</script>
						</c:when>
					</c:choose>
    				<!-- 验证 -->
					<jsp:include page="/common/jsp/easyui/column-validate.jsp"></jsp:include>
				</c:when>
					<c:when test="${type==4}"><!--日历控件 -->
				    <input value="${data}" required="${editMode.required}" id="editColumn_${stauts.index}${columnID}${editPage.id}"  type="text" name="${name}" class="easyui-datebox" readonly="readonly"  />
					  <script>
						   $(function(){
						       $('#editColumn_${stauts.index}${columnID}${editPage.id}').datebox({
						       		showSeconds:true,
						       		editable:false
						       });
						   });
					  </script>
    				<!-- 验证 -->
					<img title="清除" src="<%= request.getContextPath()%>/images/ioc-delete2.gif" onclick="clearText('editColumn_${stauts.index}${columnID}${editPage.id}','null');" style="cursor:hand" />
					<c:choose>
						<c:when test="${editMode.required}">
							&nbsp;&nbsp;<font size="2" color="red">*</font>
						</c:when>
					</c:choose>
				</c:when>
				
				<c:when test="${type==6}"><!-- ajax tree -->
		    		<input class="textIoc" id="editColumn_${stauts.index}${columnID}${editPage.id}"  type="text" onfocus="openWindowAndBlur('tree-window-${stauts.index}${columnID}','editColumn_${stauts.index}${columnID}${editPage.id}');" readonly="true"  value="${treeComponents.conversionDataValue}" />&nbsp;&nbsp;
					<input name="${name}" id="editColumn_hidden_${stauts.index}${columnID}${editPage.id}"  type="hidden"  value="${data}" />
					<jsp:include page="/common/components/ajaxbox/ajaxbox-tree.jsp" ></jsp:include>
    				<c:choose>
						<c:when test="${editMode.required}">
							&nbsp;&nbsp;<font size="2" color="red">*</font>
						</c:when>
					</c:choose>
    				<!-- 验证 -->
					<jsp:include page="/common/jsp/easyui/column-validate.jsp"></jsp:include>
    			</c:when>
    			
    			<c:when test="${type==8}"><!--自动补全控件 -->
		    		<input id="editColumn_${stauts.index}${columnID}${editPage.id}"  type="text" onfocus="openWindowAndBlur('tree-window-${stauts.index}${columnID}','editColumn_${stauts.index}${columnID}${editPage.id}');" readonly="true" class="textIoc" value="${treeComponents.conversionDataValue}" />&nbsp;&nbsp;
					
    				<!-- 验证 -->
				</c:when>
    			<c:when test="${type==12}"><!-- 单选钮 -->
			  		  <c:forEach items="${dictionaryData}" var="dictionaryDataMap" > 
							<c:choose>
								<c:when test="${dictionaryDataMap.key ==data}">
									  <input  type="radio"  name="${name}"  value="${dictionaryDataMap.key}"  checked="checked" class="radio">
									  <span style="font-size:12px;" >${dictionaryDataMap.value}</span>
								</c:when>
								<c:otherwise>
									  <input  type="radio"  name="${name}"  value="${dictionaryDataMap.key}"  class="radio">
									  <span style="font-size:12px;" >${dictionaryDataMap.value}</span>
								</c:otherwise>
		    		
							</c:choose>
						</c:forEach> 
				</c:when>
    			<c:when test="${type==13}"><!-- 复选框 -->
    					<c:choose>
								<c:when test="${data eq '1' }">
									<input type="checkbox" name="${name}" value="1" class="easyui-validatebox" checked="checked" />	
								</c:when>
								<c:otherwise>
									<input type="checkbox" name="${name}" value="1" class="easyui-validatebox" />	
								</c:otherwise>
						</c:choose>
    					<c:choose>
							<c:when test="${editMode.required}">
								&nbsp;&nbsp;<font size="2" color="red">*</font>
							</c:when>
						</c:choose>
    				<!-- 验证 -->
					<jsp:include page="/common/jsp/easyui/column-validate.jsp"></jsp:include>
				</c:when>
    			
    			<c:when test="${type==16}"><!-- 时间控件（时分秒） -->
					<c:choose>
						<c:when test="${textColumn.dateformat eq '4' }">
							<input value="${data}" id="editColumn_${stauts.index}${columnID}${editPage.id}" class="Wdate" type="text" onfocus="WdatePicker({skin:'whyGreen',readOnly:true,dateFmt:'yyyy-MM-dd HH:mm'});"  />
						</c:when>
						<c:when test="${textColumn.dateformat eq  '5' }">
							<input value="${data}" id="editColumn_${stauts.index}${columnID}${editPage.id}" class="Wdate" type="text" onfocus="WdatePicker({skin:'whyGreen',readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});"  />
						</c:when>
						<c:otherwise>
							<!-- easyui datebox -->
							 <input value="${data}" id="editColumn_${stauts.index}${columnID}${editPage.id}"   name="${name}"  readonly="readonly"  />
							  <script>
								   $(function(){
								       $('#editColumn_${stauts.index}${columnID}${editPage.id}').datebox({
								       		editable:false,
								       		formatter: function(date){
								       			<c:choose>
													<c:when test="${textColumn.dateformat==1 }">
														 return date.getFullYear()+'年'; 
								       				</c:when>
													<c:when test="${textColumn.dateformat==2 }">
														 return date.getFullYear()+'年-'+(date.getMonth()+1)+'月'; 
								       				</c:when>
													<c:when test="${textColumn.dateformat==3}">
														return date.getFullYear()+'年-'+(date.getMonth()+1)+'月-'+date.getDate()+"日"; 
								       				</c:when>
													<c:otherwise>
														return date.getFullYear()+'年-'+(date.getMonth()+1)+'月-'+date.getDate()+"日"; 
								       				</c:otherwise>
												</c:choose>
								       		}
								       });
								   });
							  </script>
							<!-- easyui datebox -->
	       				</c:otherwise>
					</c:choose>
				    <c:choose>
						<c:when test="${editMode.required}">
							&nbsp;&nbsp;<font size="2" color="red">*</font>
						</c:when>
					</c:choose>
    				<!-- 验证 -->
					<jsp:include page="/common/jsp/easyui/column-validate.jsp"></jsp:include>
					&nbsp;&nbsp;&nbsp;<img title="清除" src="<%= request.getContextPath()%>/images/ioc-delete2.gif" onclick="clearText('editColumn_${stauts.index}${columnID}${editPage.id}','null');" style="cursor:hand" />
				</c:when>
    			<c:otherwise>
					<c:choose>
						 <c:when test="${textColumn.exclusiveLine}">
							 <c:choose>
						    	<c:when test="${textColumn.readOnly}">
						    		<input style="width: 474px;" id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"  class="easyui-validatebox" value="${data}" readOnly="readonly" />
								</c:when>
						    	<c:otherwise>
						    		<input style="width: 474px;" id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"  class="easyui-validatebox" value="${data}" />
								</c:otherwise>
						     </c:choose>
						 </c:when>
						 <c:otherwise>
						 	<c:choose>
						    	<c:when test="${textColumn.readOnly}">
						    		<input id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"  class="easyui-validatebox" value="${data}" readOnly="readonly"  />
								</c:when>
						    	<c:otherwise>
						    	<c:choose>
							    	<c:when test="${name  eq 'CON_NO'}">
							    		<input id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"  class="easyui-validatebox" value="${data}" />
							    		<a   icon='icon-import' class="easyui-linkbutton"    href="javascript:importCompact('editform_${editPage.id}');" >导入</a>						
									</c:when>
							    	<c:otherwise>
							    		<input id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"  class="easyui-validatebox" value="${data}" />
									</c:otherwise>
						    	</c:choose>
						    	</c:otherwise>
						     </c:choose>
						 </c:otherwise>
					 </c:choose>
					 
			 		<c:choose>
						<c:when test="${editMode.required}">
							&nbsp;&nbsp;<font size="2" color="red">*</font>
						</c:when>
					</c:choose>
					<!-- 验证 -->
					<jsp:include page="/common/jsp/easyui/column-validate.jsp"></jsp:include>
				</c:otherwise>
			</c:choose>
			 <%out.write("</td>"); %>
		 <!-- 换行的界面 -->
		 <jsp:include page="/common/jsp/easyui/edit-column-linecode.jsp"></jsp:include>
	</c:otherwise>
</c:choose>
<jsp:include page="/common/jsp/easyui/edit-RulesEngin.jsp"></jsp:include>