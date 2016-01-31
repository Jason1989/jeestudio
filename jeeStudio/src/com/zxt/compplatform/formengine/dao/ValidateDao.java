package com.zxt.compplatform.formengine.dao;

/**
 * 验证持久化操作dao
 * 
 * @author 007
 */
public interface ValidateDao {
	/**
	 * 验证是否存在
	 * 
	 * @param datasource
	 * @param sql
	 * @param name
	 * @return
	 */
	public int isExist(String datasource, String sql, String name);

	/**
	 * 验证是否存在
	 * @param datasource
	 * @param sql
	 * @param name
	 * @param id
	 * @return
	 */
	public int isExist(String datasource, String sql, String name, String id);
}
