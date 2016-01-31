package com.zxt.framework.dictionary.dao;

import java.util.List;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.framework.common.dao.IDAOSupport;
import com.zxt.framework.dictionary.entity.DictionaryGroup;

public interface IDataDictionaryDao extends IDAOSupport {
	/**
	 * 查询全部数据对象名
	 * 
	 * @param dbSource
	 * @return
	 */
	public List findTableName(DataSource dbSource);

	/**
	 * 查询全部数据对象列
	 * 
	 * @param dbSource
	 * @return
	 */
	public List findColNames(DataSource dbSource, String string);

	/**
	 * 获取数据源
	 * 
	 * @param dbSource
	 * @return
	 */
	public DataSource getDataSourceById(String id);

	/**
	 * 获取数据对象分组
	 * 
	 * @param dbSource
	 * @return
	 */
	public DictionaryGroup getDicGroupById(String dicGroup);
}
