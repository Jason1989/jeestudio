package com.zxt.compplatform.formengine.upload;

import javax.servlet.http.HttpServletRequest;

import org.directwebremoting.WebContextFactory;

/**
 * 文件上传监听器
 * 
 * @author 007
 */
public class UploadMonitor {
	/**
	 * 获取上传信息
	 * 
	 * @return
	 */
	public UploadInfo getUploadInfo() {
		HttpServletRequest req = WebContextFactory.get()
				.getHttpServletRequest();

		if (req.getSession().getAttribute("uploadInfo") != null) {
			return (UploadInfo) req.getSession().getAttribute("uploadInfo");
		} else {
			return new UploadInfo();
		}
	}
}
