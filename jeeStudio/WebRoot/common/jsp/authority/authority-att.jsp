<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%-- 控制首页配置框显示与否 --%>
<c:set var="indexSettingControl" value="hidden"></c:set>
<c:if test="${treeData.resType eq '6'}">
	<c:set var="indexSettingControl" value="visible"></c:set>
</c:if>

<script>
			
	$(function(){
				$.extend($.fn.validatebox.defaults.rules, {   
					    number: {   
					        validator: function (value, param) {
	            			return /^\d+$/.test(value)&&(value.length >= param[0])&&(value.length <= param[1]);
	        				},
	        				message: '请输入数字，至少 {0} 位,最长 {1} 位.'
					    }   
					});  
	
	});

	</script>
<form id="resourceEditForm"  action="formengine/zsf_saveResource.action"  method="post" >
	<table width="100%"  border="0" cellpadding="5" cellspacing="0">
		<tr>
		    <td colspan="4">
		    	<input id="treeDataParentID" type="hidden"  name="treeData.parentID" value="${treeData.parentID}"  > 
		    </td>
		</tr>
		
		  <tr>
			  <td align="right" >资源id：</td>
			<td  colspan="3">
		    	<input id="gridOrSystemEdit" type="hidden"   value="0"  > <!-- 标识系统修改 还是列表资源修改 -->
		    	<input id="authorit_resource_id"  name="treeData.id" value="${treeData.id}"  > 
		  	</td>  
		 </tr>
		
		<tr>
			  <td align="right" >资源名称：</td>
			  <td colspan="3">
			  		<input  id="treeDataText" type="text"   name="treeData.text" value="${treeData.text}"  >
			  		&nbsp;&nbsp;<font size="2" color="red">*</font>
			  		<script>$('#treeDataText').validatebox(
			  		{required:true,
			  		validType:"length[0,50]"
			  		})</script>
			 </td>
		  </tr>
		   <tr>
			  <td align="right" >资源序号：</td>
			  <td colspan="3">
		 		 <input id="treeDataRESSORT" type="text" name="treeData.resSort" value="${treeData.resSort}" validType="number[1,4]"  >
		 		 <script>
			 		 $('#treeDataRESSORT').validatebox({
					 	//required:true,
					 	  validType:"number[1,4]"
			 		 })
		 		 </script>
			  </td>
		  </tr>
		  <tr>
			  <td align="right">资源级别：</td>
			  <td colspan="3">
			  	<input id="treeDataResLevel"  name="treeData.level" readonly="readonly" value="${treeData.level}" />
				&nbsp;&nbsp;<font size="2" color="red">*</font> 
				<script>
				  	$(function (){
				  	 <c:choose>
						  <c:when test="${isSystemEdit eq '1' }">
									var menuLevel = [
									  		{id:"1",text:"系统"}
							  			];
								</c:when>
								<c:otherwise>
									var menuLevel = [
										{id:"2",text:"系统菜单"},
								  		{id:"3",text:"选项卡"},
								  		{id:"4",text:"功能菜单"},
								  		{id:"5",text:"页面资源"}
						  			];
								</c:otherwise>
							</c:choose>
							
				  			$('#treeDataResLevel').combobox({
							 	valueField:'id',
							    textField:'text',
							    data:menuLevel,
							    editable:false,
								required:true
							});
			  		});
				</script>
			 </td>
		  </tr>
		  <tr>
			  <td align="right">是否菜单项：</td>
			  <td colspan="3">
			  	<input id="treeDataIsMenu"  name="treeData.isMenu" readonly="readonly" value="${treeData.isMenu}" />
				&nbsp;&nbsp;<font size="2" color="red">*</font> 
				<script>
				  	$(function (){
				  		
			  			var isMenu = [
					  		{id:"1",text:"是"},
					  		{id:"0",text:"否"}
			  			];
			  			$('#treeDataIsMenu').combobox({
						 	valueField:'id',
						    textField:'text',
						    data:isMenu,
							required:true,
							editable:false
						});						
			  		});
				</script>	
			</td>
		  </tr>
		  		  <tr>
			  <td align="right">资源类型：</td>
			  <td>
			  	<input id="treeDataResType"  name="treeData.resType" readonly="readonly" value="${treeData.resType}"   />
				&nbsp;&nbsp;<font size="2" color="red">*</font>  
			  </td>
			  <td align="right" class="indexPageSettingPart" style="visibility: ${indexSettingControl};" >模板：</td>
			  <td align="left"  class="indexPageSettingPart" style="visibility: ${indexSettingControl};" >	
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select id="indexPageSettingTemplate">
					<option value="1">模板1</option>
					<option value="2">模板2</option>
					<option value="3">模板3</option>
				</select>
				<input type="hidden" id="pageSettingIdValuehidden" value="${requestScope.indexSetingId}">
			    <a id="indexPageSetting_linkbtn" href="javascript:void(0);" style="margin-top:0px;" onclick="indexpageSettingfun()">扩展页面配置</a>
				<script>
				  	$(function (){
				  		   <c:choose>
								<c:when test="${isSystemEdit eq '1' }">
									var resType = [
								  		{id:"0",text:"子系统"}
						  			];
								</c:when>
								<c:otherwise>
									var resType = [
								  		{id:"1",text:"菜单"},
								  		{id:"2",text:"页面"},
						  				{id:"6",text:"扩展页面"},
								  		{id:"3",text:"按钮"},
								  		{id:"4",text:"数据列"},
								  		{id:"5",text:"div块"}
						  			];
								</c:otherwise>
							</c:choose>
							
							//选择模板
							$("#indexPageSettingTemplate").combobox({
							     panelHeight:'auto',
							     editable:false,
							     onSelect:function(onChange){
							     }
							});
							//如果有值则将相应的值赋给模板，没有值则默认选择第一项
							<c:if test="${not empty indexPageTemplateValue}">
							$("#indexPageSettingTemplate").combobox('setValue','${indexPageTemplateValue}');
							</c:if>
							
 							//扩展页面配置链接
							$("#indexPageSetting_linkbtn").linkbutton({
							});
				  			
				  			$('#treeDataResType').combobox({
							 	valueField:'id',
							    textField:'text',
							    data:resType,
							    editable:false,
								required:true,
								onSelect:function(record){
								    if(record.id == '6'){
								    	//首页
								        $(".indexPageSettingPart").css("visibility","visible");
								        $("#treeDataAttributesUrl + span input.combo-text").attr("readonly",false);
								    }else{
										$(".indexPageSettingPart").css("visibility","hidden");
								        $("#treeDataAttributesUrl + span input.combo-text").attr("readonly",false);
								    }
								}
							});
				  	});
				  	//扩展页面配置
				  	function indexpageSettingfun(){
				  		//获取模板值
				  		var templateValue = $("#indexPageSettingTemplate").combobox('getValue');
				  		//加载配置页面的url
				  		//查看URI中是不是有值：有值（修改）,无值（添加）
					  		//添加，只传模板值
				  		var url = 'indexpage/indexpage!toIndexConfigPage.action?indexPageTemplateValue='+templateValue;
				  		//设置使用模块填充
				  		if(templateValue==1){
				  			url+="&model_Keyword=CRM&model_sum_num=7";
				  		}else if(templateValue==2){
				  			url+="&model_Keyword=CRM&model_sum_num=8";
				  		}else if(templateValue==3){
				  			url+="&model_Keyword=CRM&model_sum_num=6";
				  		}
					  		//是否为修改，如果是添加配置主键
					  	if($("#pageSettingIdValuehidden").val() && $("#pageSettingIdValuehidden").val() != ''){
				  			url += '&indexSetingId='+$("#pageSettingIdValuehidden").val();
					  	}
					  	//查看模板是不是有变化，如果有变化，则需要将原来的配置内容删掉
					  	var attrUrlForComp = $('#treeDataAttributesUrl').combobox('getValue');
					  	if(attrUrlForComp && attrUrlForComp.indexOf('?') != -1){
					  		var params = attrUrlForComp.substring(attrUrlForComp.indexOf('?')+1);
					  		if(params && params.indexOf('&') != -1){
					  		   var splitKeyVals = params.split('&');
					  		   for(var i=0 ; i<splitKeyVals.length;i++){
					  		   	   var keyVal = splitKeyVals[i].split('=');
					  		   	   if(keyVal[0] == 'indexPageTemplateValue' && keyVal[1] != templateValue ){
								  		url += "&renew=" + keyVal[1];
					  		   	   }
					  		   }
					  		}
					  	}
					    easyuiWinNew({title:'扩展页面配置',maximizable:false,width:900,height:560,
				  	   			href:url});
				  	}
				</script>
		  	</td>
		  </tr>
	
		  <tr>
			  <td align="right">资源URI：</td>
			  <td colspan="3">
			  	<input id="treeDataAttributesUrl"  name="treeData.attributes.url"     value="${treeData.attributes.url}" />
				&nbsp;&nbsp;<font size="2" color="red">*</font> 
				<script>
				  	$(function (){
			  			var forms = eval('${forms}');
			  			$('#treeDataAttributesUrl').combobox({
						 	valueField:'FO_ID',
						    textField:'FO_NAME',
						    width:240,
						    data:forms
						});
			  		});
				</script>	
			</td>
		</tr>
	
		<c:choose>
			  <c:when test="${isSystemEdit eq '1' }">
					<tr>
					  <td align="right">资源标识：</td>
					  <td colspan="3">
							<input id="treeDataResKey" type="text" name="treeData.resKey"  value="${treeData.resKey}"   />
							&nbsp;&nbsp;<font size="2" color="red">*输入英文字母或拼音进行区别</font> 
							<script>$('#treeDataResKey').validatebox({
							    required:true,
							       validType:"resourceID[0,10]"
							    })</script>
					  </td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td  colspan="4">
							<input id="treeDataResKey" type="hidden" name="treeData.resKey"  value="${treeData.resKey}" />
						</td>   
					</tr>
				</c:otherwise>
			</c:choose>
		<tr>
		  <td align="right">分配权限：</td>
		  <td colspan="3">
		    <input  id="isAuthorityAll" type="hidden" value="0" name="isAuthorityAll"   >
		    <input type="checkbox" name="isGrant"  value="checkbox" onchange="authorityAll()" />
		    <script type="text/javascript">
				function authorityAll(){
				   var checkObj=document.getElementsByName("isGrant");
				   if(checkObj[0].checked){
					document.getElementById('isAuthorityAll').value=1;}
					else {document.getElementById('isAuthorityAll').value=0;}
				}
		    </script>
		 </td>
		</tr>
		<tr>
		  <td align="right">图标地址：</td>
		  <td colspan="3">
		    <input type="text" id="treeDataImgsrc" name="treeDataImgsrc"  value="${treeData.imgsrc}"  validType="length[0,256]" />
		    <script>$("#treeDataImgsrc").validatebox({
							       validType:"length[0,256]"
							    })</script>
		  </td>
		</tr>
		<c:choose>
			  <c:when test="${isSystemEdit eq '1' }">
					<tr>
						  <td align="right">默认皮肤：</td>
						  <td colspan="3">
						    <select id="sedefaultskin" name="treeData.defaultSkin"></select>
						    <script type="text/javascript">
								$(function(){
									var skinList = eval('${skinLiString}');
									//alert('${skinLiString}');
									var defaultSkin = '${treeData.defaultSkin}' == '' ? 'deepblue':'${treeData.defaultSkin}';
									$('#sedefaultskin').combobox({
										valueField: 'name',
						   				textField: 'name',
						   				panelHeight: 'auto',
						            	data: skinList,
						            	editable:false,
						            	onLoadSuccess: function(){$('#sedefaultskin').combobox('setValue',defaultSkin);}
									});
								});
						    </script>
						  </td>
					</tr>
					<tr>
						<td align="right">是否可更换：</td>
						  <td colspan="3">
						    <select id="sechangeEnable" name="treeData.selectSkinEnable"></select>
						    <script type="text/javascript">
							$(function(){
								var changeEnableData = [{name:'是',value:'1'},{name:'否',value:'0'}];
								var selectSkinEnable = '${treeData.selectSkinEnable}' == '' ? '1':'${treeData.selectSkinEnable}';
								$('#sechangeEnable').combobox({
									valueField: 'value',
					   				textField: 'name',
					   				panelHeight: 'auto',
					   				editable:false,
					            	data: changeEnableData,
					            	onLoadSuccess: function(){$('#sechangeEnable').combobox('setValue',selectSkinEnable);}
								});
							});
					    </script>
						  </td>
					</tr>
				</c:when>
			</c:choose>
		<tr>
			<td align="right">一行几个：</td>
		  <td colspan="3">
		    <input type="radio" name="treeData.row_num" value="1"  checked="checked" />1个 &nbsp; &nbsp;
		    <input type="radio" name="treeData.row_num" value="2" />2个
			    <script>
			  		$(function (){
						if('${treeData.row_num}' != ''){
							$('input[type=radio][name=treeData.row_num]').each(function(){
								if($(this).val()=='${treeData.row_num}'){
									$(this).attr('checked',true);
								}else{
									$(this).attr('checked',false);
								}							
							})
						}
			  		});
			   </script>
		  </td>
		</tr>
		 <c:choose>
		   <c:when test="${isSystemEdit eq '1' }"></c:when>
			<c:otherwise>
				<tr>
				  <td align="right">是否启用工作流：</td>
				  <td colspan="3">
				    <input type="checkbox" id="isAbleWorkFlow_checkbox"  name="treeData.attributes.isAbleWorkFlow" value="1" >
				     <input type="hidden" id="isAbleWorkFlow_hidden"  name="isAbleWorkFlow_hidden" value="0" >
				     <script>
					  		$(function (){
					  			if('1'=='${treeData.attributes.isAbleWorkFlow}'){
					  				$("#isAbleWorkFlow_checkbox").attr('checked',true);
					  				$("#isAbleWorkFlow_hidden").val('1');
					  				$("#workflow_fiter_tr").css('display','block');
					  			};
					  			$('#isAbleWorkFlow_checkbox').click(function(){
					  				if($('#isAbleWorkFlow_checkbox').is(':checked')){
					  					$("#workflow_fiter_tr").css('display','block');
					  					$("#isAbleWorkFlow_hidden").val('1');
					  				}else{
					  					$("#workflow_fiter_tr").css('display','none');
					  					$("#isAbleWorkFlow_hidden").val('0');
					  				};
					  			})
					  		});
					   </script>	
				  </td>
				</tr>
				
				<tr id="workflow_fiter_tr" style="display: none;">
				  <td align="right">
				  		 过滤条件：
				  </td>
				  <td colspan="3">
				  		 <input id="workflow_fiter" name="treeData.attributes.workflow_fiter"/>
				  		 <script>
					  		$(function (){
					  			$('#workflow_fiter').combobox({
								 	valueField:'val',
								    textField:'text',
								    editable:false,
								    width:130,
								    editable:false,
								    data:[{val:'none',text:'无过滤条件',selected:'selected'},{val:'caogao',text:'草稿'},{val:'daibanxiang',text:'待办项'},
								    		{val:'yibanxiang',text:'已办项'},{val:'all',text:'全部'}],
								    panelHeight:'auto'
								});
								if(''=='${treeData.attributes.workflow_fiter}'){
									$('#workflow_fiter').combobox('select','none');
								}else{
									$('#workflow_fiter').combobox('select','${treeData.attributes.workflow_fiter}');
								}
					  		});
					   </script>
				  </td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
</form>