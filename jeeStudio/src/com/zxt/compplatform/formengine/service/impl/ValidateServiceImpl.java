package com.zxt.compplatform.formengine.service.impl;

import com.zxt.compplatform.formengine.dao.ValidateDao;
import com.zxt.compplatform.formengine.service.ValidateService;

public class ValidateServiceImpl implements ValidateService {
	/**
	 * 表单验证持久化操作接口
	 */
	private ValidateDao validateDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.ValidateService#isExist(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public int isExist(String datasource, String sql, String name) {
		return validateDao.isExist(datasource, sql, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.ValidateService#isExist(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public int isExist(String datasource, String sql, String name, String id) {
		return validateDao.isExist(datasource, sql, name, id);
	}

	public ValidateDao getValidateDao() {
		return validateDao;
	}

	public void setValidateDao(ValidateDao validateDao) {
		this.validateDao = validateDao;
	}

}
