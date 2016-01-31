/**
 * 刷新页面进度条 
 * @parmer progressDivID 上传进度条显示
 * @parmer progressBarID 上传进度 -数据显示
 */
function updateProgress(progressBar,progressData,uploadstartID,uploadCancelID,windowID,dataGridID,barDivID){
  $.ajax({
	       url: LOAD_PROGRESS_URL,
		   type: 'post',
		   success: function(data){

		   	var uploadInfo=eval("("+data+")");

					  if (uploadInfo.inProgress){
						 /**
						  * 上传进度的百分比
						  */
					    var fileIndex = uploadInfo.fileIndex;
					    var progressPercent = Math.ceil((uploadInfo.bytesRead / uploadInfo.totalSize) * 100);
						 /**
						  * 上传文件KB
						  */
						var progressKB=Math.ceil(( uploadInfo.bytesRead/1024))+'KB';
						var totalKB=Math.ceil(( uploadInfo.totalSize/1024))+'KB';
						 /**
						  * 刷新进度条 和 上传数据size显示
						  */
					    document.getElementById(progressBar).style.width = progressPercent + '%'; 
						document.getElementById(progressData).innerHTML=progressPercent + '%'+'<br/>'+progressKB+'/'+totalKB; 
					     /**
						  * 3秒后 递归调用自己
						  */
					    window.setTimeout(function (){
					    		updateProgress(progressBar,progressData,uploadstartID,uploadCancelID,windowID,dataGridID,barDivID);
					    }, 1500); 
		  		    }else{
		  		  			//已经上传完成
  		  				 /**
						  * 启用 关闭，上传 按钮
						  */
						document.getElementById(uploadstartID).disabled = false;//上传按钮
						document.getElementById(uploadCancelID).disabled = false;//关闭按钮
						
						
						document.getElementById(progressBar).style.width =  '0%'; //进度条
					  //document.getElementById(barDivID).style.display = 'none';//进度条隐藏
						document.getElementById(progressData).innerHTML=''; //进度数据
					   	
					   	
					   	$('#'+dataGridID).datagrid("reload");//刷新附件列表
				    	$('#'+dataGridID).datagrid("clearSelections");//清空所有选中
				    	$.messager.alert('提示','上传成功');
				    	closeWindow(windowID);
		  		    }
	  
		 }
	});
	return true;
}



/**
 * 上传控件提交
 */
 
 function uploadInit(formID,uploadstartID,uploadCancelID,progressBarID,progressDataID,fileID,barDivID,hiddenID,fileAppID,dataGridID,windowID){
	
	// document.getElementById(barDivID).style.display = 'block';// 打开进度条 div

	var options = {
	              type:"post", 
	              success:function (responseText, statusText) {
	              /**
		             $('#'+dataGridID).datagrid({
				    			queryParams:{	
				    				fileAppID:responseText
				    			}
				    		});
				    	 $('#'+dataGridID).datagrid("reload");//刷新附件列表
				    	 $('#'+dataGridID).datagrid("clearSelections");//清空所有选中
				  **/
            	if(responseText.indexOf('?')!=-1){
  					responseText = responseText.substring(0,responseText.indexOf('?'));
  				}
	            if(responseText.indexOf(' ')!=-1){
  					responseText = responseText.substring(0,responseText.indexOf(' '));
  				}
				document.getElementById(formID).reset();
			var columnID = "#editColumn_"+windowID.split("_")[2];
			    $(columnID).val(responseText);
			    reloadDataGrid(dataGridID,columnID);
			}};
			
		if(document.getElementById(fileID).value.length==0){//没有文件时退出
			$.messager.alert('提示','没有文件','error');
		 	return ;
		 }else if(document.getElementById(fileID).value.indexOf(".")<0){
			$.messager.alert('提示','附件格式不正确','error');
			return ;
		}	
			
		var val = $('#column_id_upload_'+hiddenID).val();
		var filename = $('#filename_'+hiddenID).val();
		var url = document.getElementById(formID).action;
		if(url.indexOf('?')!='-1'){
			url = url.substring(0,url.indexOf('?'));
		}
		document.getElementById(formID).action=url+"?attachementName="+filename+"&columnidupload="+val;
		
		//附件上传 设置APPID信息保存
		//document.getElementById(fileAppID).value=document.getElementById(hiddenID).value;		
		$("#" + formID).ajaxSubmit(options);
		
		 /**
		  * 禁用 关闭，上传 按钮
		  */
		document.getElementById(uploadstartID).disabled = true;
		document.getElementById(uploadCancelID).disabled = true;
		 /**
		  * 1.5秒后 开始刷新进度条
		  */
	 	 window.setTimeout(function (){
		  		updateProgress(progressBarID,progressDataID,uploadstartID,uploadCancelID,windowID,dataGridID,barDivID); 
		 }, 1500);
}

/**
 * 删除附件
 */
function deleteAtt(attID,gridID){

	 	$.messager.confirm('删除确认框', '您确认删除该条记录？', function(r){
		if (r){
				$.ajax({
					    url: DELETE_PROGRESS_URL+'?attID='+attID,
					    type: 'post',
					    success: function(data){
					  		$('#'+gridID).datagrid("reload");
					  		$('#'+gridID).datagrid("clearSelections");//清空所有选中
					    }
			    });
		}
	});
}

/**
 * 下载附件
 */
function downloan_file(url){
	
	window.location.href=url;
}

/**
 *图片预览
*/
function upload_view(filePath,windowID,yulanviewID,title){
			var filepath = filePath;
			$(windowID).window({
				title: title,
				width: 600,
				minimizable:false,
				maximizable:false,
				collapsible:false,
				modal: true,
				shadow: true,
				closed: true,
				height: 400
			})
			$(yulanviewID).remove();
			$(windowID).append("<img id="+yulanviewID+" src="+filePath+">");
			$(windowID).window('open');
			
	}
/**
 *重载datagrid
*/	
function reloadDataGrid(dataGridID,columnID){
					var val = $(columnID).val();
					var str;
					if(val!=null&&val!=""){
						str = getProjectName()+'formengine/uploadFindAction!find.action?time='+new Date().getTime()+'&columnUploadID='+val;
					}else{
						str = getProjectName()+'formengine/uploadFindAction!find.action?time='+new Date().getTime();
					}
					$('#'+dataGridID).datagrid({url:str});
			}
/**
 *删除文件上传
*/				
function deleteFIleUpload(getvalID,fileID,clstr,deleteurl,datagridID,ID,deleteID){
					
					$.messager.confirm('删除确认框', '您确认删除该条记录？', function(r){
						if (r){
									var clval = $(getvalID).val();
									var clstr = clstr;
									var clid = ID;
									if(clval!=null&&clval!=""){
										clstr = clstr+'?time='+new Date().getTime()+'&'+clid+'='+clval;
									}else{
										clstr = clstr+'?time='+new Date().getTime();
									}
									//var delid = deleteID;
									//var url = deleteurl;
									//var id = fileID;
									
									$.ajax({
									   type: "POST",
									   url: deleteurl+'?time='+new Date().getTime()+'&'+deleteID+'='+fileID,
									   success: function(msg){
										   $('#'+datagridID).datagrid("reload");//刷新附件列表
					    				   $('#'+datagridID).datagrid("clearSelections");//清空所有选中
									   		$.messager.alert('提示','删除成功');
											//$("#"+datagridID).datagrid({url:clstr});
									   }
									});
									
									
								}
					});
					
				}
