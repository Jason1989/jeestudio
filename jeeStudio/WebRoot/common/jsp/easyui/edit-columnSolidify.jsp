<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@taglib prefix="FCK" uri="/WEB-INF/FCKeditor.tld"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	String dataStart="${editPage.editColumn[";//${editPage.editColumn[index].data}
	String dataEnd="].data}";
%>
<!-- 此页面是第一次固化 所引用的页面 -->
<c:choose>
	<c:when test="${type==20}">
	  <% out.write("</tr>\r\n <tr>"); %>
	  <td colspan="4" style="border-bottom:0px solid white;" width="701px"  height="300px"> 
		<div href="${url}" class="easyui-panel" title="${textColumn.name}" style="width:701px;height: 300px;"></div>
	  </td>
	  <%  
		    out.write("</tr>\r\n <tr>"); 
			request.setAttribute("lineCount","0"); 
		%>
	</c:when>
	<c:when test="${type==11}">
 		 	<input type="hidden" name="${name}" value="<%=dataStart%>${stauts.index}<%=dataEnd%>" />
	</c:when>
	<c:when test="${type==5}"><!--Fck 编辑-->
	    <% out.write("</tr>\r\n <tr>");  %>
		<td colspan="4" style="border-bottom:0px solid white;">
			<div class="panel-header panel-header-noborder" style="font-weight: normal;" >&nbsp;${textColumn.name}</div>
			<FCK:editor instanceName="${name}" width="705px"  height="200px" value="${data}"   ></FCK:editor>
		</td>
		<%  
		    out.write("</tr>\r\n <tr>");
			request.setAttribute("lineCount","0"); 
		%>
	</c:when>
	<c:when test="${type==3}"><!--文本域-->
	    <% out.write("</tr>\r\n <tr>"); %>
		<td style="font-size:12px;width:160px;background-color: #e9e9e9;border-bottom:0px solid white;" align="right"> 
	    	<label style="width:160px;height:66px;" >${textColumn.name }：</label> 
	    </td>
		<td colspan="3" style="border-bottom:0px solid white;">
			 <c:choose>
		    	<c:when test="${textColumn.readOnly}">
		    	 	<textarea style="margin:0;width:552px;color:#565656;" id="editColumn_${stauts.index}${columnID}${editPage.id}" name="${name}" readonly=""  ><%=dataStart%>${stauts.index}<%=dataEnd%></textarea>
				</c:when>
		    	<c:otherwise>
		    		 <textarea style="margin:0;width:552px;color:#565656;" id="editColumn_${stauts.index}${columnID}${editPage.id}" name="${name}" ><%=dataStart%>${stauts.index}<%=dataEnd%></textarea>
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
		    out.write("</tr>\r\n <tr>");
			request.setAttribute("lineCount","0"); 
		%>
		
	</c:when>
	
	<c:when test="${type==9}"><!--上传控件 -->
	<% out.write("</tr>\r\n <tr>"); %>
		<td style="font-size:12px;width:80px;background-color: #e9e9e9;border-bottom:0px solid white;" align="right"> 
	    	<label style="width:160px" >${textColumn.name }：</label> 
	    </td>
		<td colspan="3"  style="border-bottom:0px solid white;">
			<input id="editColumn_${stauts.index}${columnID}${editPage.id}" type="hidden" name="${name}"   value="<%=dataStart%>${stauts.index}<%=dataEnd%>" />
			<div id="uploadpanel_${stauts.index}_${columnID}" class="easyui-layout" border="false" style="width:572px;height:120px;">
			    <div region="center" border="false">
					<table id="uploadgrid_${stauts.index}_${columnID}"></table>
					<c:choose>
						<c:when test="${editMode.required}">
							<input id="editColumn_${stauts.index}${columnID}${editPage.id}_valid" class="easyui-validatebox" required="true" missingMessage="请至少上传一个文件！" readonly="readonly" type="text" style="border:0;width:15px;position: absolute; margin-left: -15px; z-index: 100;background-color: #f5f5f5;" name="${name}_" value="" />
						</c:when>
					</c:choose>	
			    </div>
			    <div region="east" style="width:18px;vertical-align: middle;" border="false" >
					<c:choose>
						<c:when test="${editMode.required}">
							<br><br><br><br>&nbsp;&nbsp;<font size="2" color="red">*</font>
						</c:when>
					</c:choose>
			    </div>
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
					        	title:'附件名称',
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
					        	align:'center',
					        	width:1,
					        	formatter:function(value,rec){
					        		var file = rec.FILE_NAME+rec.FILE_TYPE;
					        		var path = rec.FILE_PATH;
					        		var rname = rec.FILE_RNAME+rec.FILE_TYPE;
					        		var fileID = rec.FILE_ID;
					        		var url = "<%=request.getContextPath()%>/formengine/uploadFindAction!download.action?time="+new Date().getTime()+"&path="+path+"&filername="+rname;
									url = encodeURI(encodeURI(url));
									return "<span  style='line-hieght:30px;vertical-align:middle;'><img id='img_icon' src='<%=request.getContextPath()%>/jquery-easyui-1.1.2/themes/icons/arrow_down_green.png' title='下载' width='14px' style='cursor: hand' onclick='downloan_file(\""+url+"\")'/>&nbsp;&nbsp;<img src='<%=request.getContextPath()%>/jquery-easyui-1.1.2/themes/icons/cancel.png' title='删除' width='14px' style='cursor: hand' onclick='deleteFIleUpload(\"#editColumn_${stauts.index}${columnID}${editPage.id}\",\""+fileID+"\",\"<%=request.getContextPath()%>/formengine/uploadFindAction!find.action\",\"<%=request.getContextPath()%>/formengine/uploadFindAction!delete.action\",\"uploadgrid_${stauts.index}_${columnID}\",\"columnUploadID\",\"fileID\")'/></span>";
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
								openUploadWindow('${stauts.index}${columnID}${editPage.id}',val);
							}
						}]
						,onLoadSuccess:function(data){
							<c:if test="${editMode.required}">
							    $('#editColumn_${stauts.index}${columnID}${editPage.id}_valid').val(data.total==0?'':data.total);
							</c:if>
						
						}
					});
				});
				//reloadDataGrid(dataGridID,columnID);
		   </script>
  				<!-- 验证 -->
			<jsp:include page="/common/jsp/easyui/column-validate.jsp"></jsp:include>
		</td>
		<%  
		    out.write("</tr>\r\n <tr>");
			request.setAttribute("lineCount","0"); 
		%>
	</c:when>
    <c:otherwise>
   	 <c:choose>
		 <c:when test="${textColumn.exclusiveLine}">
			<%out.write("</tr>\r\n <tr>"); %>
		</c:when>
	 </c:choose>
       <td  id="${name}_title" style="font-size:12px;width:${editMode.tdWidth}px;background-color: #e9e9e9;border-bottom:0px solid white;word-wrap:break-word;" align="right"> 
	    	${textColumn.name }：
	   </td>
 <!-- *****************************编辑列字段********************************** -->
 		<!-- 独占行 td 宽度 -->
		 <c:choose>
			 <c:when test="${textColumn.exclusiveLine}">
				<%out.write("<td colspan='3' style='border-bottom:0px solid white;' >\r\n "); %>
			</c:when>
			 <c:otherwise>
			 	<%out.write("<td style='width:${editMode.tdWidth}px;border-bottom:0px solid white;'>\r\n "); %>
			 </c:otherwise>
		 </c:choose>
		 <c:choose>
		    	<c:when test="${type==1}"><!-- 文本框 -->
		    	    <c:choose>
				    	<c:when test="${textColumn.readOnly}">
				    	 	<c:choose>
								 <c:when test="${textColumn.exclusiveLine}">
									<input style="width:${editMode.tdWidth}px;" id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"   value="<%=dataStart%>${stauts.index}<%=dataEnd%>"  readonly="" />

								</c:when>
								 <c:otherwise>
									 <input style="width:${editMode.tdWidth}px;" id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"   value="<%=dataStart%>${stauts.index}<%=dataEnd%>"  readonly="" />
								 </c:otherwise>
							 </c:choose>
				    	 </c:when>
				    	 <c:otherwise>
				    		 <c:choose>
								 <c:when test="${textColumn.exclusiveLine}">
								 		<input style="width:${editMode.tdWidth}px;" id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"   value="<%=dataStart%>${stauts.index}<%=dataEnd%>" />
		   						 </c:when>
								 <c:otherwise>
										<input style="width:${editMode.tdWidth}px;" id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"   value="<%=dataStart%>${stauts.index}<%=dataEnd%>" />
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
							<select class="easyui-combobox" id="editColumn_${stauts.index}${columnID}${editPage.id}" name='${name}' style='width:493px;' >
						 			<c:choose>
										<c:when test="${data eq '0' }">
											<option value="<%=dataStart%>${stauts.index}<%=dataEnd%>" selected="selected" >请选择</option>
										</c:when>
										<c:otherwise> <!-- 改字段列的值在  下拉选数据字典中有值时 清空标志位 -->
											<option value="0"  >请选择</option>
										</c:otherwise>
									</c:choose>
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
											<c:choose>
												<c:when test="${data eq '0' }">
												</c:when>
												<c:otherwise> <!-- 改字段列的值在  下拉选数据字典中有值时 清空标志位 -->
													<option value="<%=dataStart%>${stauts.index}<%=dataEnd%>" selected="selected" ><%=dataStart%>${stauts.index}<%=dataEnd%></option>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise> <!-- 改字段列的值在  下拉选数据字典中有值时 清空标志位 -->
												<% request.setAttribute("otherSelectValue","0"); %>
										</c:otherwise>
									</c:choose>
								</select>
								<script>
									setTimeout(function(){
										$('#editColumn_${stauts.index}${columnID}${editPage.id}').combobox({
											editable:true,
											<c:if test="${fn:length(dictionaryData) <= 5}">panelHeight:'auto'</c:if>
											<c:if test="${fn:length(dictionaryData) > 5}">panelHeight:200</c:if>
										});
									},3000);
								</script>
						 </c:when>
						 <c:otherwise>
						 		<select class="easyui-combobox" id="editColumn_${stauts.index}${columnID}${editPage.id}" name='${name}' style='width:170px;height:auto;' >
									<c:choose>
										<c:when test="${editMode.required}">
											<c:if test="${fn:length(dictionaryData) ==1}"></c:if>
											<c:if test="${fn:length(dictionaryData) >1}">
												<option value="0"  ></option>
											</c:if>
										</c:when>
										<c:otherwise><option value="0"  >请选择</option></c:otherwise>
									</c:choose>
									
									
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
								<script>
									setTimeout(function(){
										$('#editColumn_${stauts.index}${columnID}${editPage.id}').combobox({
											editable:true,
											<c:if test="${fn:length(dictionaryData) <= 5}">panelHeight:'auto'</c:if>
											<c:if test="${fn:length(dictionaryData) > 5}">panelHeight:200</c:if>});;
									},2000);
								</script>
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
				    <input sid="${name}" value="<%=dataStart%>${stauts.index}<%=dataEnd%>" required="${editMode.required}" id="editColumn_${stauts.index}${columnID}${editPage.id}"  type="text" name="${name}" class="easyui-datebox" readonly="readonly" style="width:150px;" />
					  <script>
						   $(function(){
						       $('#editColumn_${stauts.index}${columnID}${editPage.id}').datebox({
						       		showSeconds:true,
						       		editable:false
						       });
						   });
					  </script>
					
    				<!-- 验证 -->
					<img title="清除" src="<%= request.getContextPath()%>/images/ioc-delete2.gif" onclick="clearText('editColumn_${stauts.index}${columnID}${editPage.id}','null');" style="cursor:hand;" />
					<c:choose>
						<c:when test="${editMode.required}">
							&nbsp;<font size="2" color="red">*</font>
						</c:when>
					</c:choose>
				</c:when>
				
				<c:when test="${type==6}"><!-- ajax tree -->
		    		<input class="textIoc" id="editColumn_${stauts.index}${columnID}${editPage.id}"  type="text" onfocus="openWindowAndBlur('tree-window-${stauts.index}${columnID}','editColumn_${stauts.index}${columnID}${editPage.id}');" readonly="true"  value="${treeComponents.conversionDataValue}" />
					<input name="${name}" id="editColumn_hidden_${stauts.index}${columnID}${editPage.id}"  type="hidden"  value="<%=dataStart%>${stauts.index}<%=dataEnd%>" />
					<jsp:include page="/common/components/ajaxbox/ajaxbox-tree.jsp" ></jsp:include>
    				<c:choose>
						<c:when test="${editMode.required}">
							&nbsp;&nbsp;<font size="2" color="red">*</font>
						</c:when>
					</c:choose>
    				<!-- 验证 -->
					<jsp:include page="/common/jsp/easyui/column-validate.jsp"></jsp:include>
    			</c:when>
    			
    			<c:when test="${type==18}"><!-- 组织机构带人员的树 -->
		    		<input class="textIoc" style="width: 170px;" id="editColumn_${stauts.index}${columnID}${editPage.id}"  type="text" onfocus="openWindowAndBlur('tree-window-${stauts.index}${columnID}','editColumn_${stauts.index}${columnID}${editPage.id}');" readonly="true"  value="${treeComponents.conversionDataValue}" />
					<input name="${name}" id="editColumn_hidden_${stauts.index}${columnID}${editPage.id}"  type="hidden"  value="<%=dataStart%>${stauts.index}<%=dataEnd%>" />
					<jsp:include page="/common/components/ajaxbox/ajaxbox-tree.jsp?type=orgtree&name=${textColumn.name }" ></jsp:include>
    				<c:choose>
						<c:when test="${editMode.required}">
							&nbsp;&nbsp;<font size="2" color="red">*</font>
						</c:when>
					</c:choose>
    				<!-- 验证 -->
					<jsp:include page="/common/jsp/easyui/column-validate.jsp"></jsp:include>
    			</c:when>
    			<c:when test="${type==21}"><!-- 应急树控件 -->
		    		<input class="textIoc" id="editColumn_${stauts.index}${columnID}${editPage.id}"  type="text" onfocus="openWindowAndBlur('tree-window-${stauts.index}${columnID}','editColumn_${stauts.index}${columnID}${editPage.id}');" readonly="true"  value="${treeComponents.conversionDataValue}" />
					<input name="${name}" id="editColumn_hidden_${stauts.index}${columnID}${editPage.id}"  type="hidden"  value="<%=dataStart%>${stauts.index}<%=dataEnd%>" />
					<jsp:include page="/common/components/ajaxbox/emergencyajaxbox-tree.jsp?type=orgtree&name=${textColumn.name }" ></jsp:include>
    				<c:choose>
						<c:when test="${editMode.required}">
							&nbsp;&nbsp;<font size="2" color="red">*</font>
						</c:when>
					</c:choose>
    				<!-- 验证 -->
					<jsp:include page="/common/jsp/easyui/column-validate.jsp"></jsp:include>
    			</c:when>
    			<c:when test="${type==22}"><!-- 人员列表树控件 -->
		    		<input class="textIoc" id="editColumn_${stauts.index}${columnID}${editPage.id}"  type="text" onfocus="openWindowAndBlur('tree-window-${stauts.index}${columnID}','editColumn_${stauts.index}${columnID}${editPage.id}');" readonly="true"  value="${treeComponents.conversionDataValue}" />
					<input name="${name}" id="editColumn_hidden_${stauts.index}${columnID}${editPage.id}"  type="hidden"  value="<%=dataStart%>${stauts.index}<%=dataEnd%>" />
					<jsp:include page="/common/components/ajaxbox/ajaxbox-human-tree.jsp?type=orgtree&name=${textColumn.name }" ></jsp:include>
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
			  		<jsp:include page="/common/jsp/easyui/edit-column-radio.jsp"></jsp:include>		
    			</c:when>
    			<c:when test="${type==13}"><!-- 复选框 -->
    				<jsp:include page="/common/jsp/easyui/edit-column-checkbox.jsp"></jsp:include>		
    			</c:when>
    			
    			<c:when test="${type==16}"><!-- 时间控件（时分秒） -->
					<c:choose>
						<c:when test="${textColumn.dateformat eq '4' }">
							<input value="<%=dataStart%>${stauts.index}<%=dataEnd%>" id="editColumn_${stauts.index}${columnID}${editPage.id}" class="Wdate" type="text" onfocus="WdatePicker({skin:'whyGreen',readOnly:true,dateFmt:'yyyy-MM-dd HH:mm'});"  />
						</c:when>
						<c:when test="${textColumn.dateformat eq  '5' }">
							<input value="<%=dataStart%>${stauts.index}<%=dataEnd%>" id="editColumn_${stauts.index}${columnID}${editPage.id}" class="Wdate" type="text" onfocus="WdatePicker({skin:'whyGreen',readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});"  />
						</c:when>
						<c:otherwise>
							<!-- easyui datebox -->
							 <input value="<%=dataStart%>${stauts.index}<%=dataEnd%>" sid="${name}" required="${editMode.required}" id="editColumn_${stauts.index}${columnID}${editPage.id}"   name="${name}"  readonly="readonly"  />
							  <script>
								   $(function(){
								       $('#editColumn_${stauts.index}${columnID}${editPage.id}').datebox({
								       		editable:false,
								       		formatter: function(date){
												var month = date.getMonth()+1;
												 var mon = '';
									 			if(month!='10'&&month!='11'&&month!='12'){
													mon = '0'+month;
												}else{
													mon = month;
												}
									
												var day = date.getDate();
												var days = '';
												if(day=="1"||day=="2"||day=="3"||day=="4"||day=="5"||day=="6"||day=="7"||day=="8"||day=="9"){
													days = '0'+day;
												}else{
													days = day+'';
												}
								       			<c:choose>
													<c:when test="${textColumn.dateformat==1 }">
														 return date.getFullYear(); 
								       				</c:when>
													<c:when test="${textColumn.dateformat==2 }">
														 return date.getFullYear()+'-'+mon; 
								       				</c:when>
													<c:when test="${textColumn.dateformat==3}">
														return date.getFullYear()+'-'+mon+'-'+days; 
								       				</c:when>
													<c:otherwise>
														return date.getFullYear()+'-'+mon+'-'+days; 
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
					&nbsp;&nbsp;&nbsp;<img title="清除" src="<%= request.getContextPath()%>/images/ioc-delete.gif" onclick="clearText('editColumn_${stauts.index}${columnID}${editPage.id}','null');" style="cursor:hand" />
				</c:when>
    			<c:otherwise>
					<c:choose>
						 <c:when test="${textColumn.exclusiveLine}">
							 <c:choose>
						    	<c:when test="${textColumn.readOnly}">
						    		<input style="width:${editMode.textWidth}px;" id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"  class="easyui-validatebox" value="<%=dataStart%>${stauts.index}<%=dataEnd%>" readOnly="readonly" />
								</c:when>
						    	<c:otherwise>
						    		<input style="width:${editMode.textWidth}px;" id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"  class="easyui-validatebox" value="<%=dataStart%>${stauts.index}<%=dataEnd%>" />
								</c:otherwise>
						     </c:choose>
						 </c:when>
						 <c:otherwise>
						 	<c:choose>
						    	<c:when test="${textColumn.readOnly}">
						    		<input id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"  class="easyui-validatebox" value="<%=dataStart%>${stauts.index}<%=dataEnd%>" readOnly="readonly"  />
								</c:when>
						    	<c:otherwise>
						    		<input style="width:${editMode.textWidth}px;" id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"  class="easyui-validatebox" value="<%=dataStart%>${stauts.index}<%=dataEnd%>" />
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
<jsp:include page="/common/jsp/easyui/edit_listPageForValue.jsp"></jsp:include>