package com.zxt.compplatform.formengine.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.zxt.compplatform.formengine.dao.UploadFindDao;
import com.zxt.compplatform.formengine.service.UploadFindService;

/**
 * 文件上传业务操作实现
 * 
 * @author 007
 */
public class UploadFindServiceImpl implements UploadFindService {
	private static final Logger log = Logger
			.getLogger(UploadFindServiceImpl.class);
	/**
	 * 文件上传持久化操作接口
	 */
	private UploadFindDao uploadFindDao;

	public UploadFindDao getUploadFindDao() {
		return uploadFindDao;
	}

	public void setUploadFindDao(UploadFindDao uploadFindDao) {
		this.uploadFindDao = uploadFindDao;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.UploadFindService#find(java.lang.String)
	 */
	public List find(String columnUploadId) {
		String sql = "select * from ENG_ATTACHMENTS where COLUMN_ID =" + "'"
				+ columnUploadId + "'";
		return uploadFindDao.find(sql);
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.UploadFindService#delete(java.lang.String)
	 */
	public void delete(String fileId) {
		String sql = "delete from eng_attachments where FIlE_ID =" + "'"
				+ fileId + "'";
		uploadFindDao.delete(sql);
	}
}
