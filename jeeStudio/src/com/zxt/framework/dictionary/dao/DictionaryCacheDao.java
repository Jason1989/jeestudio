package com.zxt.framework.dictionary.dao;

import java.sql.SQLException;
import java.util.List;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.framework.dictionary.entity.DictionaryCache;

/**
 * 数据字段缓存操作
 * 数据对象依赖缓存接口
 * @author chinazxt
 *
 */
public interface DictionaryCacheDao {
	
	/**
	 * 添加trigger
	 *    需要的参数：表名，数据源（类型），列名(主键)，trigger名称,缓存表主键值
	 */
	public void addTriiger(DicCacheTriggerEntity dicCacheTriggerEntity)throws SQLException;
	
	/**
	 * 给某个数据源添加trigger
	 * @param triggerScript 已经编写好的触发器脚本
	 * @param dataSource 数据源
	 */
	public void addTriiger(String triggerScript,DataSource dataSource)throws SQLException;
	
	/**
	 * 添加记录
	 * @param cache
	 */
	public void insertDicCacheRecord(DictionaryCache cache);
	
	/**
	 * 更新表
	 * @param cache
	 */
	public void updateDicCacheReord(DictionaryCache cache);
	
	/**
	 * 查找cache
	 * @param cacheId
	 * @return
	 */
	public DictionaryCache findById(String cacheId);
	
	/**
	 * 通过表名称查找相关的记录
	 * @param tableName
	 * @return
	 */
	public List findByTableName(String tableName);
	
	/**
	 * 根据数据源和表名查询对应表的triggers
	 * @param dataSource 数据源实体 
	 * @param tableName 表名
	 * @return
	 */
	public List findTableTriggers(DataSource dataSource,String tableName);
	
	
	/**
	 *  根据表名删除相应的trigger
	 * @param dataSource 数据源
	 * @param triggerName 表名
	 */
	public void deleteTriggerByTriggerName(DataSource dataSource,String triggerName);
	
	/**
	 * 删除缓存记录
	 * 
	 * @param cacheKey 缓存主键
	 */
	public void deleteCacheRecord(String cacheKey);
	
	/**
	 * 根据相应的实体获取触发器脚本
	 * 
	 * @param cacheTriggerEntity
	 * @param dbType 数据类型
	 * @return
	 */
	public String getTriggerSqlScript(DicCacheTriggerEntity cacheTriggerEntity,String dbType);
	
}
