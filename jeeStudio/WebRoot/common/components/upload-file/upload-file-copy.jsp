<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>测试上传</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <link rel="stylesheet" type="text/css" href="css/image.css">
	<link rel="stylesheet" type="text/css" href="css/auto_row.css" />
	<link rel="stylesheet" type="text/css" href="css/style.css">

	<link rel="stylesheet" type="text/css" href="jquery-easyui-1.1.2/themes/easyui.blue.css"/>

	<script type="text/javascript" src="jquery-easyui-1.1.2/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.1.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.1.2/easyui-lang-zh_CN.js"></script>
	
	<script type="text/javascript" src="common/js/common-util/jquery_form.js"></script>
	<script type="text/javascript" src="common/js/common-util/ajax_security.js"></script>
	<script type="text/javascript" src="common/components/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="common/js/common-util/service-frame.js"></script>
	<script type="text/javascript" src="common/js/common-util/code-engine-constant.js"></script>
	<script type="text/javascript" src="common/js/common-util/engine-common-util.js"></script>
	<script type="text/javascript" src="common/js/common-util/upload.js"></script>
	
	<script type="text/javascript" src="common/js/version-easyui/engine-util-easyui.js"></script>
	<script type="text/javascript" src="common/js/version-easyui/workflow-util.js"></script>
	<script type="text/javascript" src="js/XmlUtils.js"></script>
  </head>
  
  <body>
		<div id="upload_mask" class="easyui-window"  style="width: 500px;height: 260px;" title="附件上传" >
         <form id="upload-form-1"  action="upload/uploadProAction" name="newForm" enctype="multipart/form-data" method="post" onsubmit="return startProgress(); ">
			<table>
               <tr>
                  <td align="right" >附件：</td> 
                  <td>
                  	<input id="file1" type="file" class="file" name="file1" maxlength="100" size="27" />
                  </td>
               </tr>
		       <tr>
		           <td align="right">附件名称：</td> 
		           <td>
		           		<input id="filename" name="attachementName" type="text" style="width: 260px;" />
		           		<input id="fileAppID_${stauts.index}" type="hidden"  name="fileAppID"  />
		           </td>
		       </tr>
		       <tr><td colspan="2" >
		       <!-- 进度数据显示 -->
				<div id="ppop" style="font-size:24px;color:red;text-align: center"></div><br/>
				<div id="progressBar" align="center" style="display: none" >
					<div style="width:189px; height:13px; background:url(common/image/glod_bg.gif) no-repeat center;text-align: left;" >
						<div id="eProgress" style="width:1%; height:9px; background:url(common/image/glod.gif) repeat-x center; margin:2px;align:left;" align="left"></div>
					</div>
				</div>
				<!-- 进度数据显示 -->
		       </td></tr>
		       <tr>
		           <td align="center" colspan="2" >
		              <a id="uploadstart" class="easyui-linkbutton" icon="icon-print"   href="javascript:uploadInit('upload-form-1','uploadstart','cancel-upload','eProgress','ppop','file1','progressBar','upload_hidden_${stauts.index}','fileAppID_${stauts.index}','uploadgrid_${stauts.index}','upload_mask_${stauts.index}');" >开始上传</a>
				      &nbsp;&nbsp;
				      <a id="cancel-upload" class="easyui-linkbutton" icon="icon-cancel"  href="javascript:closeWindow('upload_mask');"  >关闭</a>
				   </td> 
			   </tr>
		     </table>
		</form>
	</div>
  </body>
</html>
