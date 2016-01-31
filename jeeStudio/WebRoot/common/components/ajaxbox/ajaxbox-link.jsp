<%@page   contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<div id="ajaxbox-link_${name}_${stauts.index}" class="easyui-window" title="ajaxbox"  >
	<div class="easyui-layout" fit="true">
		<div region="center" border="false" style="padding:10px;background:#fff;border:1px solid #ccc;width:100%;height:230px;z-index:2000;">
			<table width="100%"  border="0" cellpadding="5" cellspacing="0">
		      <tr>
				   <td>选择：<input  id="linkbox_${stauts.index}"  type="text"   >
				   		&nbsp;&nbsp;
				   <img title="搜索" style="cursor:hand" onclick="serchLinkBox('${editPage.formId}','${name}','linkbox_${stauts.index}','linkboxdatalist_${stauts.index}');"  src="<%= request.getContextPath()%>/jquery-easyui-1.2/themes/icons/search.png"/></td>
			  </tr>
	
			  
			</table>
			<div id="linkboxdatalist_${stauts.index}" >
			    <table>
					<s:iterator value="#editColumnList.treeLink" >
						<tr>
						  	<td><a style="color: #000000" href="javascript:treeLinkActive('${text}','text_${name}_${stauts.index}','${id}','text_${name}_hidden_${stauts.index}','ajaxbox-link_${name}_${stauts.index}');" >${text}</a></td>
						</tr>
					</s:iterator>	
				</table>
			</div>
		</div>
		<div region="south" border="false" style="text-align:center;height:30px;line-height:30px;">
		</div>
	</div>
</div>

	<script>
		$(function(){
			/**
			 * 初始化窗口
			 */
			$('#ajaxbox-link_${name}_${stauts.index}').appendTo('body').window({
			  title: '&nbsp;',
			  iconCls:"icon-save",
				width: 550,
				modal: true,
				shadow: false,
				closed: true,
				height: 300
			});
			/**  解决ie6  select 层 在弹出层之上的bug..
				$('#ajaxbox-link_${name}_${stauts.index}').bgiframe({
					width: 550,
					height: 300
				});  
			**/
			/**
			 * 初始化button
			 */
			$('#ajaxbox_link_${name}_${stauts.index}').linkbutton({text:'确定'});
		});
	</script>
	