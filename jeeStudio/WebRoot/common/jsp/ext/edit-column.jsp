<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@taglib prefix="FCK" uri="/WEB-INF/FCKeditor.tld"%>

<c:choose>
	<c:when test="${type==11}">
 		 	<input type="hidden" name="${name}" value="${data}" />
	</c:when>
	<c:when test="${type==5}"><!--Fck 编辑-->
	    <% out.write("</tr><tr>"); %>
		<td colspan="4" >
			<div class="panel-header panel-header-noborder" style="font-weight: normal;" >&nbsp;${textColumn.name}</div>
			<FCK:editor instanceName="${name}" width="560px"  height="200px" value="${defaultValue}" ></FCK:editor>
		</td>
		<%  
		    out.write("</tr><tr>");
			request.setAttribute("lineCount","0"); 
		%>
	</c:when>
	<c:when test="${type==3}"><!--文本域-->
	    <% out.write("</tr><tr>"); %>
		<td colspan="4" >
			<div class="panel-header panel-header-noborder" style="font-weight: normal;" >&nbsp;${textColumn.name }</div>
				<textarea id="editColumn_${stauts.index}${columnID}${editPage.id}"  rows="${textColumn.rows}" cols="${textColumn.cols}"  name="${name}" >${data}</textarea>
				<c:choose>
					<c:when test="${editMode.required}">
						&nbsp;&nbsp;<font size="2" color="red">*</font>
					</c:when>
				</c:choose>
   				<!-- 验证 -->
				<jsp:include page="column-validate.jsp"></jsp:include>
		</td>
		<%  
		    out.write("</tr><tr>");
			request.setAttribute("lineCount","0"); 
		%>
	</c:when>
	<c:when test="${type==9}"><!--上传控件 -->
	<% out.write("</tr><tr>"); %>
		<td colspan="4" >
			<div class="panel-header panel-header-noborder" style="font-weight: normal;" >&nbsp;${textColumn.name }
				<div style="text-align: right;margin-top: -15px;cursor:pointer;"  >
					<img  src="<%=request.getContextPath()%>/images/ioc-addrender.gif" onclick="openWindow('upload_mask_${stauts.index}_${columnID}');" title="添加附件" />
				</div>
			</div>
			
			<div id="upload_${stauts.index}_${columnID}" >
				 <div class="easyui-layout" fit="true" >
						<div  region="north" border="false" style="height:${listPage.queryZoneHeight}px;"></div>
						<div  region="center" style="height:100%;width:100%;" border="false">
							<table  id="uploadgrid_${stauts.index}_${columnID}"  style="padding:20px;width: 615px;height: 180px;" ></table>
						</div>
				  </div>
			</div>
			
			<script>
				$('#upload_${stauts.index}_${columnID}').panel({
					width:500,
					height:100,
					border: false
				});
				$('#uploadgrid_${stauts.index}_${columnID}').datagrid({
					iconCls:'icon-save',
					fit:true,
					nowrap: false,
					striped: true,
					url:'datagrid_data.json',
					sortName: 'code',
					sortOrder: 'desc',
					remoteSort: false,
					idField:'code',
					frozenColumns:[[
		                {field:'ck',checkbox:true},
		                {title:'code',align:'center',field:'code',width:80,sortable:true}
					]],
					columns:[[
				        {title:'Base Information',align:'center',colspan:3},
						{field:'opt',title:'Operation',align:'center',width:100,align:'center', rowspan:2,
							formatter:function(value,rec){
								return '<span style="color:red;"><img src="../themes/icons/icon_edit.png"/> <img src="../themes/icons/icon_delete.png"/></span>';
							}
						}
					],[
						{field:'name',title:'Name',align:'center',width:120},
						{field:'addr',title:'Address',align:'center',width:120,rowspan:2,sortable:true,
							sorter:function(a,b,order){
								return (a>b?1:-1)*(order=='asc'?1:-1);
							}
						},
						{field:'col4',title:'Col41',align:'center',width:150,rowspan:2}
					]],
					pagination:true,
					rownumbers:true,
					searchbar:{
						url:''
					},
					toolbar:[{
						id:'btnadd',
						text:'Add',
						titleCls:'link_btn_color',
						iconCls:'icon-add',
						handler:function(){
							$('#btnsave').linkbutton('enable');
							alert('add')
						}
					},{
						id:'btncut',
						text:'Cut',
						titleCls:'link_btn_color',
						iconCls:'icon-cut',
						handler:function(){
							$('#btnsave').linkbutton('enable');
							alert('cut')
						}
					},'-',{
						id:'btnsave',
						text:'Save',
						titleCls:'link_btn_color',
						disabled:true,
						iconCls:'icon-save',
						handler:function(){
							$('#btnsave').linkbutton('disable');
							alert('save')
						}
					}]
				});
		   </script>
			
			
			<c:choose>
				<c:when test="${editMode.required}">
					&nbsp;&nbsp;<font size="2" color="red">*</font>
				</c:when>
			</c:choose>
  				<!-- 验证 -->
			<jsp:include page="column-validate.jsp"></jsp:include>
		</td>
		<%  
		    out.write("</tr><tr>");
			request.setAttribute("lineCount","0"); 
		%>
	</c:when>
    <c:otherwise>
       <td style="font-size:12px;width:40px;" align="right"> 
	    	<label style="width:100px" >${textColumn.name }：</label> 
	   </td>
 <!-- *****************************编辑列字段********************************** -->
 		<!-- 独占行 td 宽度 -->
		 <c:choose>
			 <c:when test="${textColumn.exclusiveLine}">
				<%out.write("<td colspan='3' >"); %>
			</c:when>
			 <c:otherwise>
			 	<%out.write("<td>"); %>
			 </c:otherwise>
		 </c:choose>
		 <c:choose>
		    	<c:when test="${type==1}"><!-- 文本框 -->
		    	    <c:choose>
				    	<c:when test="${textColumn.readOnly}">
				    	 	<c:choose>
								 <c:when test="${textColumn.exclusiveLine}">
									<input style="width: 100%px;" id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"   value="${data}"  readonly="" />
								</c:when>
								 <c:otherwise>
									 <input id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"   value="${data}"  readonly="" />
								 </c:otherwise>
							 </c:choose>
				    	 </c:when>
				    	 <c:otherwise>
				    		<c:choose>
								 <c:when test="${textColumn.exclusiveLine}">
										<input style="width: 100%;" id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"   value="${data}" />
		   						</c:when>
								 <c:otherwise>
										<input id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"   value="${data}" />
			   				      </c:otherwise>
							 </c:choose>
				    	
				    		<jsp:include page="column-validate.jsp"></jsp:include>
						</c:otherwise>
				    </c:choose>
				    
				    
		    		<c:choose>
							<c:when test="${editMode.required}">
								&nbsp;&nbsp;<font size="2" color="red">*</font>
							</c:when>
						</c:choose>
		    	 	<jsp:include page="column-validate.jsp"></jsp:include>
				</c:when>
				<c:when test="${type==0}"><!-- 复选框 -->
					<input type="checkbox" name="${name}" class="easyui-validatebox" />	
					<c:choose>
						<c:when test="${editMode.required}">
							&nbsp;&nbsp;<font size="2" color="red">*</font>
						</c:when>
					</c:choose>
    				<!-- 验证 -->
					<jsp:include page="column-validate.jsp"></jsp:include>
				</c:when>
				<c:when test="${type==2}"><!-- 下拉选 -->
					<select  name="${name}" >
						<option value="0"  selected="selected" >请选择</option>
						<c:forEach items="${dictionaryData}" var="dictionaryDataMap" > 
							<c:choose>
								<c:when test="${dictionaryDataMap.key ==data}">
									<option value="${dictionaryDataMap.key}" selected="selected" >${dictionaryDataMap.value}</option>
								</c:when>
								<c:otherwise>
									<option value="${dictionaryDataMap.key}" >${dictionaryDataMap.value}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach> 
					</select>
					<c:choose>
						<c:when test="${editMode.required}">
							&nbsp;&nbsp;<font size="2" color="red">*</font>
						</c:when>
					</c:choose>
    				<!-- 验证 -->
					<jsp:include page="column-validate.jsp"></jsp:include>
				</c:when>
				<c:when test="${type==4}"><!-- 日历控件 -->
				
				  	<c:choose>
						<c:when test="${textColumn.dateformat eq '4' }">
							<input value="${data}" id="editColumn_${stauts.index}${columnID}${editPage.id}" class="Wdate" type="text" onfocus="new WdatePicker(this,'%Y-%M-%D %h:%m',true,'whyGreen')" readonly="readonly"  />
						</c:when>
						<c:when test="${textColumn.dateformat==5}">
							<input value="${data}" id="editColumn_${stauts.index}${columnID}${editPage.id}" class="Wdate" type="text" onfocus="new WdatePicker(this,'%Y-%M-%D %h:%m:%s',true,'whyGreen')" readonly="readonly"  />
						</c:when>
						<c:otherwise>
							<!-- easyui datebox -->
							 <input value="${data}" id="editColumn_${stauts.index}${columnID}${editPage.id}"  type="text" name="${name}" class="easyui-datebox" readonly="readonly"  />
							  <script>
								   $(function(){
								       $('#editColumn_${stauts.index}${columnID}${editPage.id}').datebox({
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
    				<!-- 验证 -->
					<jsp:include page="column-validate.jsp"></jsp:include>
					&nbsp;&nbsp;&nbsp;<img title="清除" src="<%= request.getContextPath()%>/images/ioc-delete2.gif" onclick="clearText('editColumn_${stauts.index}${columnID}${editPage.id}','null');" style="cursor:hand" />
				   <c:choose>
						<c:when test="${editMode.required}">
							&nbsp;&nbsp;<font size="2" color="red">*</font>
						</c:when>
					</c:choose>
				</c:when>
				<c:when test="${type==6}"><!-- ajax tree -->
		    		<input id="editColumn_${stauts.index}${columnID}${editPage.id}"  type="text" onfocus="openWindowAndBlur('tree-window-${stauts.index}${columnID}','editColumn_${stauts.index}${columnID}${editPage.id}');" readonly="true" class="textIoc" value="${treeComponents.conversionDataValue}" />&nbsp;&nbsp;
					<img title="清除" src="<%= request.getContextPath()%>/images/ioc-delete.gif" onclick="clearText('editColumn_${stauts.index}${columnID}${editPage.id}','editColumn_hidden_${stauts.index}${columnID}${editPage.id}');" style="cursor:hand" />
					
					<input name="${name}" id="editColumn_hidden_${stauts.index}${columnID}${editPage.id}"  type="hidden"  value="${data}" />
					<jsp:include page="/common/components/ajaxbox/ajaxbox-tree.jsp" ></jsp:include>
    				<c:choose>
						<c:when test="${editMode.required}">
							&nbsp;&nbsp;<font size="2" color="red">*</font>
						</c:when>
					</c:choose>
    				<!-- 验证 -->
					<jsp:include page="column-validate.jsp"></jsp:include>
    			</c:when>
    			<c:when test="${type==12}"><!-- 单选钮 -->
			  		  <c:forEach items="${dictionaryData}" var="dictionaryDataMap" > 
							<c:choose>
								<c:when test="${dictionaryDataMap.key ==data}">
									  <input  type="radio"  name="${name}"  value="${dictionaryDataMap.key}"  checked="checked" >${dictionaryDataMap.value}
								</c:when>
								<c:otherwise>
									  <input  type="radio"  name="${name}"  value="${dictionaryDataMap.key}" >${dictionaryDataMap.value}
								</c:otherwise>
							</c:choose>
						</c:forEach> 
				</c:when>
    			<c:when test="${type==16}"><!-- 时间控件 -->
				    <input value="${data}" id="editColumn_${stauts.index}${columnID}${editPage.id}"  type="text" name="${name}" class="easyui-datebox" readonly="readonly"  />
					  <script>
						   $(function(){
						       $('#editColumn_${stauts.index}${columnID}${editPage.id}').datebox({
						       		showSeconds:true,
						       		formatter:function(date){
										var y=date.getFullYear();
										var m=date.getMonth()+1;
										var d=date.getDate();
										return y+"年"+m+"月"+d+"日";
										}
						       });
						   });
					  </script>&nbsp;&nbsp;
					<c:choose>
						<c:when test="${editMode.required}">
							&nbsp;&nbsp;<font size="2" color="red">*</font>
						</c:when>
					</c:choose>
    				<!-- 验证 -->
					<jsp:include page="column-validate.jsp"></jsp:include>
				
					<img title="清除" src="<%= request.getContextPath()%>/images/ioc-delete.gif" onclick="clearText('editColumn_${stauts.index}${columnID}${editPage.id}','null');" style="cursor:hand" />
				</c:when>
    			<c:otherwise>
					<c:choose>
						 <c:when test="${textColumn.exclusiveLine}">
							<input style="width: 493px;" id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"  class="easyui-validatebox" value="${data}" />
					
   						 </c:when>
						 <c:otherwise>
								<input id="editColumn_${stauts.index}${columnID}${editPage.id}" type="text" name="${name}"  class="easyui-validatebox" value="${data}" />
						 </c:otherwise>
					 </c:choose>
					 
			 		<c:choose>
						<c:when test="${editMode.required}">
							&nbsp;&nbsp;<font size="2" color="red">*</font>
						</c:when>
					</c:choose>
					<!-- 验证 -->
					<jsp:include page="column-validate.jsp"></jsp:include>
				</c:otherwise>
			</c:choose>
			 <%out.write("</td>"); %>
		 <!-- 换行的界面 -->
		 <jsp:include page="edit-column-linecode.jsp"></jsp:include>
	</c:otherwise>
</c:choose>