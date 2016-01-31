<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  <base href="<%=basePath%>">
    <title>编辑页配置</title>    
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
<script type="text/javascript" src="js/jquery-1.4.4.js"></script>
<script type="text/javascript">
$(function(){
	var keyid = '${keyid}';
	var isBuilding = '${isBuilding}';
	var context = '${ibcontext}';
	var remark = '${ibremark}';

	$('#wf_evel_id_hidden').val(keyid);
	$('#wf_evel_isBundling_function').val(context);
	$('#wf_evel_isBundling_remark').val(remark);
	if( isBuilding=='on' ){
		$('#wf_evel_isBundling_tr1').show();
		$('#wf_evel_isBundling_tr2').show();
		$('#wf_evel_isBundling_tr3').show();
		$('#wf_evel_isBundling_tr4').show();
		$('#wf_evel_isBundling_hidden').val(isBuilding);
		$('#wf_evel_isBundling_checked').attr('checked',true)
	}else{
		$('#wf_evel_isBundling_tr1').hide();
		$('#wf_evel_isBundling_tr2').hide();
		$('#wf_evel_isBundling_tr3').hide();
		$('#wf_evel_isBundling_tr4').hide();
		$('#wf_evel_isBundling_hidden').val('off');
	}

	$('#wf_evel_isBundling_checked').click(function(){
		if( $('#wf_evel_isBundling_checked').attr('ckecked')==true || $('#wf_evel_isBundling_checked').attr('ckecked')=='true' ){
			$('#wf_evel_isBundling_tr1').hide();
			$('#wf_evel_isBundling_tr2').hide();
			$('#wf_evel_isBundling_tr3').hide();
			$('#wf_evel_isBundling_tr4').hide();
			$('#wf_evel_isBundling_hidden').val('off');
		}else{
			$('#wf_evel_isBundling_tr1').show();
			$('#wf_evel_isBundling_tr2').show();
			$('#wf_evel_isBundling_tr3').show();
			$('#wf_evel_isBundling_tr4').show();
			$('#wf_evel_isBundling_hidden').val('on');
		}
	});
});
</script>
</head>

<body>
<form>
<table>
	<tr>
	  <td>
	    &nbsp;&nbsp;&nbsp;<input id="wf_evel_isBundling_checked" type="checkbox" />当前状态是否绑定事件
	  </td>
	</tr>
	<tr id="wf_evel_isBundling_tr1" style="display: none;">
	  <td>
	    &nbsp;&nbsp;&nbsp;前驱事件描述： <input id="wf_evel_id_hidden" name="keyid" type="hidden" />
	  </td>
	</tr>
	<tr id="wf_evel_isBundling_tr2" style="display: none;">
	  <td>
	    &nbsp;&nbsp;&nbsp;<input id="wf_evel_isBundling_remark" name="buildingremark" type="text"/>
	  </td>
	</tr>
	<tr id="wf_evel_isBundling_tr3" style="display: none;">
	  <td>
	    &nbsp;&nbsp;&nbsp;前驱事件内容： <input id="wf_evel_isBundling_hidden" name="isBuilding" type="hidden" />
	  </td>
	</tr>
	<tr id="wf_evel_isBundling_tr4" style="display: none;">
	  <td>
	    &nbsp;&nbsp;&nbsp;<textarea id="wf_evel_isBundling_function" name="buildingfunction" rows="20" cols="70"></textarea>
	  </td>
	</tr>
</table>
</form>
</body>
</html>