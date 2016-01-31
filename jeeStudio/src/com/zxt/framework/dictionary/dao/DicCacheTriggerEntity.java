package com.zxt.framework.dictionary.dao;

import java.io.Serializable;
import java.util.List;

import com.zxt.compplatform.datasource.entity.DataSource;
/**
 * 数据对象依赖缓存
 * @author h
 *
 */
public class DicCacheTriggerEntity implements Serializable {

	private static final long serialVersionUID = 1881064643445361442L;
	
	/**
	 * ENG_CACHE_DICTIONARY 更新用
	 */
	private String cacheKey;
	/**
	 * 触发器名称
	 */
	private String triggerName;
	/**
	 * 创建触发器对应的业务数据库
	 */
	private DataSource dataSource;
	/**
	 * 创建触发器对应的表名
	 */
	private String tableName;
	/**
	 * 创建触发器对应的表的主键
	 */
	private String keyColumn;
	/**
	 * 创建触发器对应的表的非主键
	 */
	private List columns;
	
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getKeyColumn() {
		return keyColumn;
	}
	public void setKeyColumn(String keyColumn) {
		this.keyColumn = keyColumn;
	}
	public List getColumns() {
		return columns;
	}
	public void setColumns(List columns) {
		this.columns = columns;
	}
	public String getCacheKey() {
		return cacheKey;
	}
	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
}
