<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<script type="text/javascript">
  	function butsub(){
  	  var flag= $('#htform').form('validate');
  	    var options = {
  		             type:"post",
  		              success:function () {
  		            	$.messager.alert('提示','保存成功！','info');
  		         }};
	  	if(flag){
			
	  		$('#htform').ajaxSubmit(options);
		}
  	}
  	function rize(){
  	 
  	  var	arrSon=$(":checkbox");
  	  for(i=0;i<arrSon.length;i++){
  	  	 if(arrSon[i].checked==true){
  			arrSon[i].checked=false;}
  	  	  }
  	 var arrSonRadio=$(":radio");
	  	for(i=0;i<arrSonRadio.length;i++){
	 	  	 if(arrSonRadio[i].checked==true){
	 	  		arrSonRadio[i].checked=false;}
	 	}
  	   var arrSonText=$(":text");
	   	for(i=0;i<arrSonText.length;i++){
		   	arrSonText[i].value="";
	  	}
  	  	  
    }
  </script>
<div class="easyui-panel"  title="系统设置" fit=true >
   <div class="easyui-layout" fit="true">
	  <div id="editColumn_${lprid}" region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc; height: 355px;">
		<form action="ht/htlist!operHtList.action" id="htform">
			<table border="0" cellpadding="5" cellspacing="0" width="100%"
				style="font-size: 12px;" id="dd">
				<tr style="background: #ffffff; font-size: 12px;">
					<td style="background: #eeeeee; width: 35%; padding-left: 20px">
						&nbsp;
						<input type="hidden" value="caozuo" name="htlist_operate">
						参数内容：
					</td>
					<td style="background: #eeeeee; width: 15%;">
						<input type="hidden" value="wenjian" name="htlist_fileupload">
						参数设置：
					</td>
					<td style="background: #eeeeee; width: 60%; padding-left: 20px">
					</td>
				</tr>

				<tr>
					<td style="padding-left: 50px; width: 250px">
						是否保留登陆日志
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p1" value="Y"
							 />
						是
					</td>
					<td>
						<input type="radio" name="params.p1" value="N"
							<c:if test="${params.p1 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						是否保留单据终止操作日志
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p2" value="Y"
							<c:if test="${params.p2 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p2" value="N"
							<c:if test="${params.p2 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						项目是否需要按项目区域分管
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p3" value="Y"
							<c:if test="${params.p3 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p3" value="N"
							<c:if test="${params.p3 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						新建项目部时是否复制第一个项目部组织结构
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p4" value="Y"
							<c:if test="${params.p4 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p4" value="N"
							<c:if test="${params.p4 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						新建项目部时是否复制第一个项目部角色权限
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p5" value="Y"
							<c:if test="${params.p5 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p5" value="N"
							<c:if test="${params.p5 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						角色权限调整时是否重新给员工授权
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p6" value="Y"
							<c:if test="${params.p6 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p6" value="N"
							<c:if test="${params.p6 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						员工调整岗位后是否保留原岗位权限
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p7" value="Y"
							<c:if test="${params.p7 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p7" value="N"
							<c:if test="${params.p7 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						是否允许用户设置项目部编号
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p8" value="Y"
							<c:if test="${params.p8 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p8" value="N"
							<c:if test="${params.p8 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						被删除数据是否在查询结果中显示
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p9" value="Y"
							<c:if test="${params.p9 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p9" value="N"
							<c:if test="${params.p9 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						付款是否必须申请
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p10" value="Y"
							<c:if test="${params.p10 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p10" value="N"
							<c:if test="${params.p10 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						劳务\分包合同编制依据
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p11" value="Y"
							<c:if test="${params.p11 eq('Y')}">checked="checked"</c:if> />
						成本核算科目
					</td>
					<td>
						<input type="radio" id="fm_viewpageset_isshowexport"
							name="params.p11" value="N"
							<c:if test="${params.p11 eq('N')}">checked="checked"</c:if> />
						项目目标成本
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						承包合同是否与作业项目关联
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p12" value="Y"
							<c:if test="${params.p12 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p12" value="N"
							<c:if test="${params.p12 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						分包合同是否与作业项目关联
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p13" value="Y"
							<c:if test="${params.p13 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="isfenbao" value="N"
							<c:if test="${params.p13 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						劳务合同是否与作业项目关联
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p14" value="Y"
							<c:if test="${params.p14 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" id="fm_viewpageset_isshowexport"
							name="params.p14" value="N"
							<c:if test="${params.p14 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						项目仓库设置
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p15" value="Y"
							<c:if test="${params.p15 eq('Y')}">checked="checked"</c:if> />
						单一仓库
					</td>
					<td>
						<input type="radio" name="params.p15" value="N"
							<c:if test="${params.p15 eq('N')}">checked="checked"</c:if> />
						多库管理
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						材料计划是否需要根据材料来源属性进行区分
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p16" value="Y"
							<c:if test="${params.p16 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p16" value="N"
							<c:if test="${params.p16 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						材料计划是否需要与使用部位关联
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p17" value="Y"
							<c:if test="${params.p17 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p17" value="N"
							<c:if test="${params.p17 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						材料领用是否需要与使用部位关联
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p18" value="Y"
							<c:if test="${params.p18 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p18" value="N"
							<c:if test="${params.p18 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						是否允许采购时效已过计划中材料
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p19" value="Y"
							<c:if test="${params.p19 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p19" value="N"
							<c:if test="${params.p19 eq('N')}">checked="checked"</c:if> />
						不允许
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						集中采购结算单位
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p20" value="Y"
							<c:if test="${params.p20 eq('Y')}">checked="checked"</c:if> />
						集中采购部门
					</td>
					<td>
						<input type="radio" name="params.p20" value="N"
							<c:if test="${params.p20 eq('N')}">checked="checked"</c:if> />
						项目部
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						采购结算依据
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p21" value="Y"
							<c:if test="${params.p21 eq('Y')}">checked="checked"</c:if> />
						收货单
					</td>
					<td>
						<input type="radio" name="params.p21" value="N"
							<c:if test="${params.p21 eq('N')}">checked="checked"</c:if> />
						结算单
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						调拨单确认依据联
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p22" value="Y"
							<c:if test="${params.p22 eq('Y')}">checked="checked"</c:if> />
						调拨出货
					</td>
					<td>
						<input type="radio" name="params.p22" value="N"
							<c:if test="${params.p22 eq('N')}">checked="checked"</c:if> />
						调拨收货
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						收货是否需要前置单据
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p23" value="Y"
							<c:if test="${params.p23 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p23" value="N"
							<c:if test="${params.p23 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						出货是否需要前置单据
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p24" value="Y"
							<c:if test="${params.p24 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p24" value="N"
							<c:if test="${params.p24 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						是否允许收货时修改材料价格
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p25" value="Y"
							<c:if test="${params.p25 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p25" value="N"
							<c:if test="${params.p25 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						设备管理部门
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p26" value="Y"
							<c:if test="${params.p26 eq('Y')}">checked="checked"</c:if> />
						所有权单位
					</td>
					<td>
						<input type="radio" name="params.p26" value="N"
							<c:if test="${params.p26 eq('N')}">checked="checked"</c:if> />
						使用单位
					</td>
				</tr>

				<tr>
					<td style="padding-left: 50px; width: 250px">
						设备年检提前提示时间
					</td>
					<td style="padding-left: 60px" colspan="2" align="left">
						提前<input type="text" size="10" name="params.p27" value="${params.p27 }" />天通知用户
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						是否允许超计划采购
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p28" value="Y"
							<c:if test="${params.p28 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p28" value="N"
							<c:if test="${params.p28 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						是否允许超计划调拨
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p29" value="Y"
							<c:if test="${params.p29 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p29" value="N"
							<c:if test="${params.p29 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						是否允许超计划借料
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p30" value="Y"
							<c:if test="${params.p30 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p30" value="N"
							<c:if test="${params.p30 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						是否允许超计划租赁
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p31" value="Y"
							<c:if test="${params.p31 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p31" value="N"
							<c:if test="${params.p31 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						是否允许超计划收货
					</td>
					<td style="padding-left: 30px">
						<input type="radio" name="params.p32" value="Y"
							<c:if test="${params.p32 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p32" value="N"
							<c:if test="${params.p32 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
				<tr>
					<td style="padding-left: 50px; width: 250px">
						是否允许超计划出货
					</td>
					<td style="padding-left: 30px">
						<input type="radio" id="" name="params.p33" value="Y"
							<c:if test="${params.p33 eq('Y')}">checked="checked"</c:if> />
						是
					</td>
					<td>
						<input type="radio" name="params.p33" value="N"
							<c:if test="${params.p33 eq('N')}">checked="checked"</c:if> />
						否
					</td>
				</tr>
			</table>
		</form>

	</div>
	<div region="south" border="false"
		style="text-align: center; height: 45px; padding-top: 13px;">
		<a class="easyui-linkbutton" icon="icon-ok" id="" onclick="butsub()"
			style="cursor: pointer;">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="easyui-linkbutton" icon='icon-undo' onclick="rize()" id=""
			style="cursor: pointer;">重置</a>&nbsp;&nbsp;
	</div>
	</div>
</div>

