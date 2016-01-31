<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <base href="<%=basePath%>">
    <title>添加</title>    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script type="text/javascript" src="js/basedata.common.js"></script>
	<script language="javascript" type="text/javascript"></script>
	</head>

	<body>
		<form id="authority" method="post" action="list.action">
			<div class="pop_col_cons">
			<dl>
				<dd>
					村庄名称：
				</dd>
				<dt>
					<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
				</dt>
			</dl>
			</div>
			
			<div class="pop_col_col">
				<div class="pop_col_conc">
					<dl>
						<dd>
							项目类别：
						</dd>
						<dt>
							<select name="select" style="width: 171px;">
								<option value="一个">1</option>
								<option value="两个" selected="selected">2</option>
							</select>
						</dt>
					</dl>
					<dl>
						<dd>
							个数：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
					<dl>
						<dd>
							建设地点：
						</dd>
						<dt>
							<select name="select" style="width: 171px;">
								<option value="一个">1</option>
								<option value="两个" selected="selected">2</option>
							</select>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							乡镇：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
					<dl>
						<dd>
							申报单位：
						</dd>
						<dt>
							<select name="select" style="width: 171px;">
								<option value="一个">1</option>
								<option value="两个" selected="selected">2</option>
							</select>
						</dt>
					</dl>
					<dl>
						<dd>
							排序：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
			</div>
			<div class="pop_col_color">
				<div class="pop_col_conc">
					<dl>
						<dd>
							是否贫困县：
						</dd>
						<dt>
							<select name="select" style="width: 171px;">
								<option value="一个">1</option>
								<option value="两个" selected="selected">2</option>
							</select>
						</dt>
					</dl>
					<dl>
						<dd>
							自然生态：
						</dd>
						<dt>
							<select name="select" style="width: 171px;">
								<option value="一个">1</option>
								<option value="两个" selected="selected">2</option>
							</select>						
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							所属流域：
						</dd>
						<dt>
							<select name="select" style="width: 171px;">
								<option value="一个">1</option>
								<option value="两个" selected="selected">2</option>
							</select>
						</dt>
					</dl>
					<dl>
						<dd>
							支流 ：
						</dd>
						<dt>
							<select name="select" style="width: 171px;">
								<option value="一个">1</option>
								<option value="两个" selected="selected">2</option>
							</select>
						</dt>
					</dl>
				</div>
			</div>
			<div class="pop_col_col">
				<div class="pop_col_conc">
					<dl>
						<dd>
							开始日期：
						</dd>
						<dt>
							<select name="select" style="width: 171px;">
								<option value="一个">1</option>
								<option value="两个" selected="selected">2</option>
							</select>
						</dt>
					</dl>
					<dl>
						<dd>
							建成日期：
						</dd>
						<dt>
							<select name="select" style="width: 171px;">
								<option value="一个">1</option>
								<option value="两个" selected="selected">2</option>
							</select>						
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							申请年度：
						</dd>
						<dt>
							<select name="select" style="width: 171px;">
								<option value="一个">1</option>
								<option value="两个" selected="selected">2</option>
							</select>
						</dt>
					</dl>
					<dl>
						<dd>
							命名年份：
						</dd>
						<dt>
							<select name="select" style="width: 171px;">
								<option value="一个">1</option>
								<option value="两个" selected="selected">2</option>
							</select>
						</dt>
					</dl>
				</div>
			</div>
			<div class="pop_col_col">
				<div class="pop_col_conc">
					<dl>
						<dd>
							村庄人数：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
					<dl>
						<dd>
							村庄面积(亩)：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>					
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							村庄户数：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
					<dl>
						<dd>
							人均收入(元/年)：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
			</div>
			    <div class="pop_col_conc">
					<dl>
						<dd>产业结构：</dd>
						<dt>
						    <textarea name="dataColumn.description" cols="50" rows="4"  style="font-size: 12px;padding: 2px;"></textarea>
						</dt>
					</dl>
					<dl>
						<dd>备注：</dd>
						<dt>
						    <textarea name="dataColumn.description" cols="50" rows="4"  style="font-size: 12px;padding: 2px;"></textarea>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>主要环境问题及原因：</dd>
						<dt>
						    <textarea name="dataColumn.description" cols="50" rows="4"  style="font-size: 12px;padding: 2px;"></textarea>
						</dt>
					</dl>
					<dl>
						<dd>拟采取的主要措施：</dd>
						<dt>
						    <textarea name="dataColumn.description" cols="50" rows="4"  style="font-size: 12px;padding: 2px;"></textarea>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>预期目标：</dd>
						<dt>
						    <textarea name="dataColumn.description" cols="50" rows="4"  style="font-size: 12px;padding: 2px;"></textarea>
						</dt>
					</dl>
					<dl>
						<dd> 进度安排：</dd>
						<dt>
						    <textarea name="dataColumn.description" cols="50" rows="4"  style="font-size: 12px;padding: 2px;"></textarea>
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>已开展工作：</dd>
						<dt>
						    <textarea name="dataColumn.description" cols="50" rows="4"  style="font-size: 12px;padding: 2px;"></textarea>
						</dt>
					</dl>
					<dl>
						<dd>投资需求：</dd>
						<dt>
						    <textarea name="dataColumn.description" cols="50" rows="4"  style="font-size: 12px;padding: 2px;"></textarea>
						</dt>
					</dl>
				</div>
				<div class="pop_col_cons">
					<dl>
						<dd>县（区/市）政府意见：</dd>
						<dt>
						    <textarea name="dataColumn.description" cols="80" rows="4"  style="font-size: 12px;padding: 2px;"></textarea>
						</dt>
					</dl>
				</div>
				<div class="pop_col_cons">
				    <dl>
						<dd>是否示范省：</dd>
						<dt>
						    <input type="radio" value="0" name="dataColumn.sysColumn"  checked />是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="1" name="dataColumn.sysColumn"/>否
						</dt>
					</dl>
				</div>
				<h3>投资信息：</h3>
				单位：万元<br>
				<div class="pop_col_cons">
					<dl>
						<dd>
							自筹：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_cons">
					<dl>
						<dd>
							地方财政安排资金：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_cons">
					<dl>
						<dd>
							拟申请：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
				<div class="pop_col_cons">
					<dl>
						<dd>
							总计：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
			<h3>规模及预期目标：</h3>	
			<div class="pop_col_col">
				<div class="pop_col_conc">
					<dl>
						<dd>
							净化设施(台)：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
					<dl>
						<dd>
							水源地保护围栏(米)：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>					
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							治理污染源及排污口(个)：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
					<dl>
						<dd>
							养殖废弃物综合利用及治理设施(台,套)：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
			</div>
			<div class="pop_col_col">
				<div class="pop_col_conc">
					<dl>
						<dd>
							生活垃圾收集设施(个)：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
					<dl>
						<dd>
							生活垃圾转运设施(个)：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>					
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							生活垃圾处理设施(个)：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
					<dl>
						<dd>
							生活污水处理设施(个)：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
			</div>
			<div class="pop_col_col">
				<div class="pop_col_conc">
					<dl>
						<dd>
							治理企业数量(个)：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
					<dl>
						<dd>
							污染综合治理设施建设数量(个)：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>					
						</dt>
					</dl>
				</div>
				<div class="pop_col_conc">
					<dl>
						<dd>
							受益人口：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
					<dl>
						<dd>
							畜禽数量(头,只)：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
			</div>
			<div class="pop_col_conc">
					<dl>
						<dd>
							处理垃圾量(吨)：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
					<dl>
						<dd>
							处理污水量(吨)：
						</dd>
						<dt>
							<input name="" class="easyui-validatebox" size="23" />&nbsp;&nbsp;<font size="2" color="red">*</font>
						</dt>
					</dl>
				</div>
			<div class="pop_col_cons">
					<dl>
						<dd>省级专家意见：</dd>
						<dt>
						    <textarea name="dataColumn.description" cols="80" rows="4"  style="font-size: 12px;padding: 2px;"></textarea>
						</dt>
					</dl>
				</div>	
			<h3>项目单行材料：</h3>
			<div class="pop_col_conc">
					<dl>
						<dd>省内专家审查意见（有\无）：</dd>
						<dt>
						    <textarea name="dataColumn.description" cols="50" rows="4"  style="font-size: 12px;padding: 2px;"></textarea>
						</dt>
					</dl>
					<dl>
						<dd>企业责任主体已灭失证明（有\无）：</dd>
						<dt>
						    <textarea name="dataColumn.description" cols="50" rows="4"  style="font-size: 12px;padding: 2px;"></textarea>
						</dt>
					</dl>
				</div>
				<div class="pop_col_cons">
					<dl>
						<dd>已开展工作证明材料（列举）：</dd>
						<dt>
						    <textarea name="dataColumn.description" cols="80" rows="4"  style="font-size: 12px;padding: 2px;"></textarea>
						</dt>
					</dl>
				</div>	
			<div style="float: left; padding: 80px 0 0 180px;">
				<a href="javascript:;" class="easyui-linkbutton" icon="icon-save"
					id="">保存</a>
				<a href="javascript:;" class="easyui-linkbutton" icon="icon-redo"
					id="">取消</a>
				<a href="javascript:;" class="easyui-linkbutton" icon="icon-cancel"
					onclick="parent.$('#authority').window('close');">关闭</a>
			</div>
		</form>

	</body>
</html>
