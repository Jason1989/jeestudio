package com.zxt.compplatform.formengine.service;

import java.util.List;

/**
 * 文件上传
 * @author 007
 */
public interface UploadFindService {
	/**
	 * 查询上传文件
	 * @param columnUploadId
	 * @return
	 */
	public List find(String columnUploadId);
	/**
	 * 删除
	 * @param fileId
	 */
	public void delete(String fileId);
}
