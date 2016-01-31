package com.zxt.compplatform.formengine.dao;

import java.util.List;

import javax.sql.DataSource;

/**
 * 页面组件树持久化dao
 * @author 007
 */
public interface ComponentsTreeDao {
	/**
	 * 加载树 data
	 * @param sql
	 * @return
	 */
	public List treeData(String sql,DataSource dataSource);
	
	/**
	 * 加载树 data
	 * @param sql
	 * @param dataSource
	 * @return
	 */
	public List treeOrgData(String sql,DataSource dataSource);
}
