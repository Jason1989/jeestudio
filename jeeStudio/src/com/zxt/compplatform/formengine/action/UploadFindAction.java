package com.zxt.compplatform.formengine.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.formengine.service.UploadFindService;

/**
 * 文件上传Action
 * 
 * @author 007
 */
public class UploadFindAction extends ActionSupport {

	private static final Logger log = Logger.getLogger(UploadFindAction.class);
	/**
	 * 文件上传业务操作接口
	 */
	private UploadFindService uploadService;

	public UploadFindService getUploadService() {
		return uploadService;
	}

	public void setUploadService(UploadFindService uploadService) {
		this.uploadService = uploadService;
	}

	/**
	 * 下载文件信息
	 */
	private String downloadfile;

	public String getDownloadfile() {
		return downloadfile;
	}

	public void setDownloadfile(String downloadfile) {
		this.downloadfile = downloadfile;
	}

	/**
	 * 获取文件名称
	 * 
	 * @return
	 */
	public String getDownloadFileRealName() {
		HttpServletRequest request = ServletActionContext.getRequest();
		return request.getParameter("filername");
	}

	/**
	 * 查询 
	 * 
	 * @return
	 * @throws IOException
	 */
	public String find() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/plain;charset=UTF-8");

		String columnUploadId = request.getParameter("columnUploadID");
		if (columnUploadId != null && (!"".equals(columnUploadId.trim()))) {
			List list = uploadService.find(columnUploadId);

			Map map = new HashMap();
			map.put("rows", list);
			map.put("total", new Integer(list.size()));

			String roleJson = JSONObject.fromObject(map).toString();
			response.getWriter().write(roleJson);
		} else {
			response.getWriter().write("{'total':0,'rows':[]}");
		}
		return null;
	}

	/**
	 * 删除
	 * @return
	 */
	public String delete() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();

		String fileId = request.getParameter("fileID");
		uploadService.delete(fileId);
		return null;
	}

	/**
	 * 下载
	 * @return
	 */
	public InputStream getDownload() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();

		// 读取文件并且设置相关参数 ，文件路径 加上‘/’从端口号开始
		String filePath = request.getParameter("path");
		InputStream input = ServletActionContext.getServletContext()
				.getResourceAsStream("/" + filePath);
		return input;

	}

	/**
	 * 跳转到下载页面
	 * @return
	 */
	public String download() {
		return "success";

	}
}
