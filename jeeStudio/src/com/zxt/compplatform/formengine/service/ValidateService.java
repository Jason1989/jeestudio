package com.zxt.compplatform.formengine.service;

/**
 * 表单验证操作接口
 * 
 * @author 007
 */
public interface ValidateService {
	/**
	 * 添加验证
	 * 
	 * @param datasource
	 * @param sql
	 * @param name
	 * @return
	 */
	public int isExist(String datasource, String sql, String name);

	/**
	 * 修改验证
	 * 
	 * @param datasource
	 * @param sql
	 * @param name
	 * @param id 将当前的id排除在外
	 * @return
	 */
	public int isExist(String datasource, String sql, String name, String id);
}
