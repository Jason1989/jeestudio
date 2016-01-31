<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%-- 数据字典数据库依赖缓存 --%>
<script type="text/javascript">
   $(function(){
   	   /*$("#triggerEditBtl").toggle(function(){
   	   		$("#triggerScriptEditText").show('slow');
   	   },function(){
   	   	    $("#triggerScriptEditText").hide('slow');
   	   });*/
   });
   //创建触发器和缓存记录
   function createTriggerAndCacheReFun(){
   	   $("#createTriggerAndCacheReForm").form('submit',{
   	   		url:'dictionary/dictionaryCache.action',
   	   		onSubmit:function(){
   	   		   return $(this).form('validate');
   	   		},
   	   		success:function(data){
   	   		    var result = $.parseJSON(data);
   	   		    if(result.flag == 'success'){
   	   		    	$.messager.alert('提示','创建成功！','info',function(){
   	   		    		$('#easyuiwin2').window('refresh');
   	   		    	});
   	   		    	//提交完毕之后，改变数据字典编辑页上的按钮
   	   		    	checkDicCacheSettingFlagFun('${param.dataSourceId }','${param.sql }');
   	   		    } else if(result.flag == 'sqlError'){
   	   		    	$.messager.alert('提示','创建触发器出现异常，请确保触发器脚本的准确性！','warning');
   	   		    } else if(result.flag == 'systemError'){
   	   		    //	$.messager.alert('提示','参数异常！','warning');
   	   		    } else {
   	   		    //	$.messager.alert('提示','系统出错请稍候再试！','error');
   	   		    }
   	   		}
   	   	});
   }
   
   //清楚缓存（触发器以及缓存记录）
   function clearTriggerAndCacheReFun(){
   		$("#createTriggerAndCacheReForm").form('submit',{
   	   		url:'dictionary/dictionaryCache_clearCacheAndTriiger.action',
   	   		onSubmit:function(){
   	   		   return $(this).form('validate');
   	   		},
   	   		success:function(data){
   	   		    var result = $.parseJSON(data);
   	   		    if(result.flag == 'success'){
   	   		    	$.messager.alert('提示','删除成功！','info',function(){
   	   		    		$('#easyuiwin2').window('refresh');
   	   		    	});
   	   		    	//提交完毕之后，改变数据字典编辑页上的按钮
   	   		    	checkDicCacheSettingFlagFun('${param.dataSourceId }','${param.sql }');
   	   		    } else if(result.flag == 'systemError'){
   	   		    	// $.messager.alert('提示','参数异常！','warning');
   	   		    } else {
   	   		    	// $.messager.alert('提示','系统出错请稍候再试！','warning');
   	   		    }
   	   		}
   	   	});
   }
   
</script>
<div class="easyui-layout" fit="true" border="false">
    <div region="center" border="false">
        <form id="createTriggerAndCacheReForm" method="post">
			<div class="pop_col_col">
				<div class="pop_col_conc">
					<dl>
						<dd>
							触发器：
						</dd>
						<dt>
						    <c:choose>
						     <c:when  test="${requestScope.cacheRecord > 0}">
						     	<font color="blue">已创建（触发器名称：${requestScope.triggerName[0].name}）</font>
						     	<input type="hidden" name="cacheKey" value="${requestScope.cacheRecordKey[0].cacheKey}">
						     </c:when>
						     <c:otherwise>
						     	<font color="blue">未创建</font>
						     </c:otherwise>
						    </c:choose>
						    <!-- <img src="jquery-easyui-1.1.2/themes/icons/icon_edit.png" title="编辑触发器" style="cursor: hand;" id="triggerEditBtl" /><br> -->
						    <textarea class="easyui-validatebox" required="true" rows="6" cols="50" id="triggerScriptEditText" name="triggerScript">${requestScope.triggersScript}</textarea>
						    <input type="hidden" name="sql" value="${param.sql }">
						    <input type="hidden" name="dataSourceId" value="${param.dataSourceId }">
						</dt>
					</dl>
					<dl>
						<dd>
						   缓存记录：
						</dd>
						<dt>
						<c:choose>
							<c:when test="${requestScope.triggers > 0}">
						     	<font color="blue">已创建（缓存主键：${requestScope.cacheRecordKey[0].cacheKey}）</font>
						     	<input type="hidden" name="triggerName" value="${requestScope.triggerName[0].name}">
							</c:when>
							<c:otherwise>
						     	<font color="blue">未创建</font>
							</c:otherwise>
						</c:choose>
						</dt>
					</dl>
				</div>
			</div>
        </form>
    </div>
    <div region="south" border="false" style="height:39px;">
    	<div style="width:100%;height:100%;" align="center">
    	   <div align="left" style="display: inline;">
    	   		<c:choose>
    	   			<c:when test="${requestScope.triggers > 0 && requestScope.cacheRecord > 0}">
    	   				<a class="easyui-linkbutton" onclick="clearTriggerAndCacheReFun()"
				       		icon="icon-remove">清除</a>
    	   			</c:when>
    	   			<c:otherwise>
				       <a class="easyui-linkbutton" onclick="createTriggerAndCacheReFun()"
				       icon="icon-save">创建</a>
    	   			</c:otherwise>
    	   		</c:choose>
		   
    	   </div>
    	   <div align="left" style="display: inline;">
		       <a class="easyui-linkbutton" onclick="$('#easyuiwin2').window('close')" icon="icon-cancel">关闭</a>
    	   </div>
    	</div>
    </div>
</div>