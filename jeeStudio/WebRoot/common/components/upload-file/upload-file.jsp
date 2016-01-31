<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
   
    <div id="upload_mask_${stauts.index}${columnID}${editPage.id}" class="easyui-window" title="上传" closed="true" minimizable="false" maximizable="false">
         <form id="upload_form_${stauts.index}${columnID}${editPage.id}"  action="<%=path%>/upload/uploadProAction" name="newForm" enctype="multipart/form-data" method="post" onsubmit="return startProgress(); ">
			<table>
               <tr>
                  <td align="right" >附件：</td> 
                  <td id="upload_file_td_${stauts.index}${columnID}${editPage.id}">
                  	<input id="file_upload_${stauts.index}${columnID}${editPage.id}" type="file" class="file" name="file1" maxlength="100"  onchange="getFileName(this)" size="27"/>
                  </td>
               </tr>
		       <tr>
		           <td align="right" width="80px">附件名称：</td> 
		           <td>
		           		<input id="filename_${stauts.index}${columnID}${editPage.id}" name="attachementName" type="text" style="width: 260px;" />
		           		<script >
		           	
		           			$('#filename_${stauts.index}${columnID}${editPage.id}').validatebox({   
						    	required:false,
						    	validType:'sign'
							});  
		           		</script>
		           		<input id="column_id_upload_${stauts.index}${columnID}${editPage.id}" type="hidden" name="columnidupload">
		           </td>
		       </tr>
		       <tr><td colspan="2" >
		       <!-- 进度数据显示 -->
				<div id="upload_file_ppop_${stauts.index}${columnID}${editPage.id}"><div id="ppop_${stauts.index}${columnID}${editPage.id}" style="font-size:24px;color:red;text-align: center"></div></div><br/>
				<div id="progressBar_${stauts.index}${columnID}${editPage.id}" align="center"  >
					<div style="width:189px; height:17px; background:url(<%=request.getContextPath()%>/common/image/glod_bg.gif) no-repeat center;text-align: left;padding-top:2px;" >
						<div id="eProgress_${stauts.index}${columnID}${editPage.id}" style="width:0%; height:9px; background:url(<%=request.getContextPath()%>/common/image/glod.gif) repeat-x center; margin:2px;align:left;" align="left"></div>
					</div>
				</div>
				<!-- 进度数据显示 -->
		       </td></tr>
		       <tr>
		           <td align="center" colspan="2" >
		              <a id="uploadstart_${stauts.index}${columnID}${editPage.id}" class="easyui-linkbutton" icon="icon-ok"   href="javascript:uploadInit('upload_form_${stauts.index}${columnID}${editPage.id}','uploadstart_${stauts.index}${columnID}${editPage.id}','cancel-upload_${stauts.index}${columnID}${editPage.id}','eProgress_${stauts.index}${columnID}${editPage.id}','ppop_${stauts.index}${columnID}${editPage.id}','file_upload_${stauts.index}${columnID}${editPage.id}','progressBar_${stauts.index}${columnID}${editPage.id}','${stauts.index}${columnID}${editPage.id}','fileAppID_${stauts.index}','uploadgrid_${stauts.index}_${columnID}','upload_mask_${stauts.index}${columnID}${editPage.id}');" ></a>
				      &nbsp;&nbsp;
				      <a id="cancel-upload_${stauts.index}${columnID}${editPage.id}" class="easyui-linkbutton" icon="icon-cancel"  href="javascript:closeWindow('upload_mask_${stauts.index}${columnID}${editPage.id}');"  ></a>
				   </td> 
			   </tr>
		     </table>
		</form>
	</div>
<script>
	$(function(){
		/**
		 * 初始化窗口
		 */
		/**
		 * 初始化button
		 */
		$('#uploadstart_${stauts.index}${columnID}${editPage.id}').linkbutton({text:'开始上传'});
		$('#cancel-upload_${stauts.index}${columnID}${editPage.id}').linkbutton({text:'关闭'});
 	});
 	function openUploadWindow(id,val){
 		$('#column_id_upload_'+id).val(val);
		$('#upload_mask_'+id).window('open');
 	}
 	function clearData(){
        $('#upload_form_${stauts.index}${columnID}${editPage.id}').form('clear');
        $('#file_upload_${stauts.index}${columnID}${editPage.id}').remove();
        $('#ppop_${stauts.index}${columnID}${editPage.id}').remove();
        $('#upload_file_ppop_${stauts.index}${columnID}${editPage.id}').append("<div id='ppop_${stauts.index}${columnID}${editPage.id}' style='font-size:24px;color:red;text-align: center'></div>");
        $('#upload_file_td_${stauts.index}${columnID}${editPage.id}').append('<input id=file_upload_${stauts.index}${columnID}${editPage.id} type=file class=file name=file1 maxlength=100  />');
    }
    function getFileName(obj){
    	var va=obj.value;
    	if(va.indexOf(".")>0){
    		var str = $("#file_upload_${stauts.index}${columnID}${editPage.id}").val().split(".")[0].split("\\");
    		$("#filename_${stauts.index}${columnID}${editPage.id}").val(str[(str.length-1)]);
    	}
    }
</script>