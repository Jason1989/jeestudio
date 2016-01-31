package com.zxt.framework.dictionary.dao;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.framework.common.dao.DAOSupport;
import com.zxt.framework.dictionary.entity.DictionaryCache;
import com.zxt.framework.jdbc.ZXTJDBCDataSource;
import com.zxt.framework.jdbc.ZXTJDBCTemplate;

/**
 * @author chinazxt
 * 
 * ************************************************************ sqlserver
 * ************************************************************ create trigger
 * TRIGGER_ENG_CACHE_CHANGE_ENG_CACHE_PARAMS on ENG_CACHE_PARAMS after insert,
 * update,delete as
 * 
 * if ((select count(*) from inserted ) > 0 and (select count(*) from deleted ) =
 * 0) or ((select count(*) from inserted ) = 0 and (select count(*) from deleted ) >
 * 0) or ((select count(*) from ( select a.* from inserted a left outer join
 * deleted b on a.{column1} = b.{column1} and a.{column2} = b.{column2} and
 * a.{column3} = b.{column3} where b.{column1} is null and b.{column2} is null
 * and b.{column3} is null ) goals ) > 0) begin update ENG_CACHE_DICTIONARY set
 * IS_DIRTY=1 where CACHE_KEY='1' end
 * ***********************************************************
 * 
 */
public class DictionaryCacheDaoImpl extends DAOSupport implements
		DictionaryCacheDao {

	private static final Log log = LogFactory
			.getLog(DictionaryCacheDaoImpl.class);

	/**
	 * (non-Javadoc)
	 * 添加触发器
	 * @see com.zxt.framework.dictionary.dao.DictionaryCacheDao#addTriiger(com.zxt.framework.dictionary.dao.DicCacheTriggerEntity)
	 */
	public void addTriiger(DicCacheTriggerEntity cacheTriggerEntity) throws SQLException {
		DataSource dataSource = cacheTriggerEntity.getDataSource();

		String triggerSql = getTriggerSqlScript(cacheTriggerEntity,dataSource.getDbType());

		log.info(triggerSql);
		// 执行sql，创建触发器
		javax.sql.DataSource targetDatasource = new ZXTJDBCDataSource(
				dataSource.getIpAddress(), dataSource.getPort() + "",
				dataSource.getSid(), dataSource.getDbType(), dataSource
						.getUsername(), dataSource.getPassword());

		ZXTJDBCTemplate jdbcTemplate = new ZXTJDBCTemplate(targetDatasource);

		jdbcTemplate.createRecord(triggerSql.toString());

	}
	/**
	 * 添加触发器
	 */
	public void addTriiger(String triggerScript,DataSource dataSource) throws SQLException {
		
		// 执行sql，创建触发器
		javax.sql.DataSource targetDatasource = new ZXTJDBCDataSource(
				dataSource.getIpAddress(), dataSource.getPort() + "",
				dataSource.getSid(), dataSource.getDbType(), dataSource
						.getUsername(), dataSource.getPassword());

		ZXTJDBCTemplate jdbcTemplate = new ZXTJDBCTemplate(targetDatasource);

		jdbcTemplate.createRecord(triggerScript.toString());

	}

	/**
	 * (non-Javadoc)
	 * 插入数据字典缓存
	 * @see com.zxt.framework.dictionary.dao.DictionaryCacheDao#insertDicCacheRecord(com.zxt.framework.dictionary.entity.DictionaryCache)
	 */
	public void insertDicCacheRecord(DictionaryCache cache) {
		this.create(cache);
	}

	/**
	 * (non-Javadoc)
	 * 更新数据字典
	 * @see com.zxt.framework.dictionary.dao.DictionaryCacheDao#updateDicCacheReord(com.zxt.framework.dictionary.entity.DictionaryCache)
	 */
	public void updateDicCacheReord(DictionaryCache cache) {
		// TODO 更新时cache是离线的
		this.update(cache);
	}

	/**
	 * (non-Javadoc)
	 * 数据字典缓存更新
	 * @see com.zxt.framework.dictionary.dao.DictionaryCacheDao#findById(java.lang.String)
	 */
	public DictionaryCache findById(String cacheId) {
		String sql = " from DictionaryCache where cacheKey=?";
		List list = this.find(sql, new Object[] { cacheId });
		if (list != null && list.size() > 0) {
			return (DictionaryCache) list.get(0);
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 查询数据对象表
	 * @see com.zxt.framework.dictionary.dao.DictionaryCacheDao#findByTableName(java.lang.String)
	 */
	public List findByTableName(String tableName) {
		String sql = " from DictionaryCache where cacheKey like :param";
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		List list = null;
		try {
			Query q = session.createQuery(sql);
			q.setString("param", tableName);
			list = q.list();
			session.flush();
		} finally {
			session.close();
		}
		return list;
	}

	/**
	 * (non-Javadoc)
	 * 查询数据表触发器
	 * @see com.zxt.framework.dictionary.dao.DictionaryCacheDao#findTableTriggers(com.zxt.compplatform.datasource.entity.DataSource,
	 *      java.lang.String)
	 */
	public List findTableTriggers(DataSource dataSource, String tableName) {
		javax.sql.DataSource targetDatasource = new ZXTJDBCDataSource(
				dataSource.getIpAddress(), dataSource.getPort() + "",
				dataSource.getSid(), dataSource.getDbType(), dataSource
						.getUsername(), dataSource.getPassword());

		ZXTJDBCTemplate jdbcTemplate = new ZXTJDBCTemplate(targetDatasource);

		return jdbcTemplate
				.findToMaps(
						" select sys1.name,sys1.id,sys1.xtype,sys1.parent_obj,comments1.text from  sysobjects sys1 join sysobjects sys2 " +
						" on sys1.parent_obj = sys2.id and sys1.xtype='TR' and sys2.xtype='U' and sys2.name=? join syscomments comments1 " +
						" on comments1.id = sys1.id",
						new Object[] { tableName });

	}

	/**
	 * (non-Javadoc)
	 * 删除触发器
	 * @see com.zxt.framework.dictionary.dao.DictionaryCacheDao#deleteTriggerByTriggerName(com.zxt.compplatform.datasource.entity.DataSource,
	 *      java.lang.String)
	 */
	public void deleteTriggerByTriggerName(DataSource dataSource,
			String triggerName) {
		javax.sql.DataSource targetDatasource = new ZXTJDBCDataSource(
				dataSource.getIpAddress(), dataSource.getPort() + "",
				dataSource.getSid(), dataSource.getDbType(), dataSource
						.getUsername(), dataSource.getPassword());

		ZXTJDBCTemplate jdbcTemplate = new ZXTJDBCTemplate(targetDatasource);

		jdbcTemplate.delete(" drop trigger " + triggerName );
	}

	/**
	 * (non-Javadoc)
	 * 删除缓存计数
	 * @see com.zxt.framework.dictionary.dao.DictionaryCacheDao#deleteCacheRecord(java.lang.String)
	 */
	public void deleteCacheRecord(String cacheKey) {
		this.delete(this.findById(cacheKey));
	}
	
	/**
	 * 获取触发器脚本
	 */
	public String getTriggerSqlScript(DicCacheTriggerEntity cacheTriggerEntity,String dbType){
		StringBuffer triggerSql = new StringBuffer(" ");

		// 判断数据源的类型
		if (Constant.DB_TYPE_ORACLE.equals(dbType)) {
			// TODO 数据字典缓存处理Trigger For oracle
		} else if (Constant.DB_TYPE_SQLSERVER.equals(dbType)) {
			// 拼接字符串
			triggerSql
					.append(" create trigger ")
					.append(cacheTriggerEntity.getTriggerName())
					.append(" on ")
					.append(cacheTriggerEntity.getTableName())
					.append(" after ")
					.append(" insert, update,delete ")
					.append(" as ")
					.append(
							" if ((select count(*) from inserted ) > 0  and (select count(*) from deleted ) = 0) ")
					.append(
							" or ((select count(*) from inserted ) = 0  and (select count(*) from deleted ) > 0) ")
					.append(
							" or ((select count(*) from ( select a.* from inserted a left outer join deleted b ")
					.append(" on a.").append(cacheTriggerEntity.getKeyColumn())
					.append(" = b.").append(cacheTriggerEntity.getKeyColumn());
			if (cacheTriggerEntity.getColumns() != null
					&& cacheTriggerEntity.getColumns().size() > 0) {
				for (Iterator iterator = cacheTriggerEntity.getColumns()
						.iterator(); iterator.hasNext();) {
					String column = (String) iterator.next();
					triggerSql.append(" and a.").append(column).append(" = b.")
							.append(column).append(" ");
				}
			}
			/**
			 * 创建触发器语句
			 */
			triggerSql.append(" where b.").append(
					cacheTriggerEntity.getKeyColumn()).append(" is null ");
			if (cacheTriggerEntity.getColumns() != null
					&& cacheTriggerEntity.getColumns().size() > 0) {
				for (Iterator iterator = cacheTriggerEntity.getColumns()
						.iterator(); iterator.hasNext();) {
					String column = (String) iterator.next();
					triggerSql.append(" and b.").append(column).append(
							" is null ");
				}
			}
			triggerSql
					.append(" ) goals ) > 0) ")
					.append(
							" begin  update ENG_CACHE_DICTIONARY set IS_DIRTY=1 where CACHE_KEY='")
					.append(cacheTriggerEntity.getCacheKey()).append("' end ");
		}
		return triggerSql.toString();
	}

}
