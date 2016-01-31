package com.zxt.framework.dictionary.service;

import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.Union;

import org.apache.commons.lang.StringUtils;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.datasource.service.IDataSourceService;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.framework.dictionary.dao.DicCacheTriggerEntity;
import com.zxt.framework.dictionary.dao.DictionaryCacheDao;
import com.zxt.framework.dictionary.entity.DictionaryCache;

public class DictionaryCacheServiceImpl implements DictionaryCacheService{

	/**
	 * 数据字典缓存处理Dao
	 */
	private DictionaryCacheDao dictionaryCacheDao;
	
	/**
	 * 数据源操作service
	 */
	private IDataSourceService dataSourceService;
	
	/**
	 * sql解析器 
	 */
	private CCJSqlParserManager sqlParserManager;
	
	
	
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.DictionaryCacheService#insertCache(java.util.Map)
	 */
	public void insertCache(Map params) throws JSQLParserException, SQLException {
		
		String []sql = (String[]) params.get("sql");
		String []dataSourceId = (String[]) params.get("dataSourceId");
		String []triggerScript = (String[])params.get("triggerScript");
		
		//验证参数为空
		if(StringUtils.isBlank(sql[0]) || StringUtils.isBlank(dataSourceId[0]) || StringUtils.isBlank(triggerScript[0])){
			throw new NullPointerException("参数为空错误");
		}
		DicCacheTriggerEntity cacheTriggerEntity = getDiCacheTriggerEntity(dataSourceId[0],sql[0]);
		DictionaryCache dictionaryCache = new DictionaryCache();
		
		//1、添加trigger
		dictionaryCacheDao.addTriiger(triggerScript[0],cacheTriggerEntity.getDataSource());
		//2、添加cache记录
		dictionaryCache.setCacheKey(cacheTriggerEntity.getCacheKey());
		dictionaryCache.setIsDirty(1);
		dictionaryCacheDao.insertDicCacheRecord(dictionaryCache);
		
	}
	
	/**
	 * 根据数据源获取相应的trigger封装实体
	 * @param dataSourceId
	 * @param sqlExpression
	 * @return
	 * @throws JSQLParserException
	 */
	private DicCacheTriggerEntity getDiCacheTriggerEntity(String dataSourceId,String sqlExpression) throws JSQLParserException{
		DicCacheTriggerEntity cacheTriggerEntity = new DicCacheTriggerEntity();
		//1、sql解析（解析器中的表名以及字段名）
		String cacheKey = "";
		
		PlainSelect plainSelect = (PlainSelect)((Select)sqlParserManager.parse(new StringReader(sqlExpression))).getSelectBody();
		Table table = (Table)plainSelect.getFromItem();
		
		cacheTriggerEntity.setTableName(table.getName().toUpperCase());
		cacheKey += table.getName();
		
		List selectColumns = plainSelect.getSelectItems();
		List otherColumns = new ArrayList();//非主键列
		if(null != selectColumns && selectColumns.size() > 0){
			for (Iterator colIterator = selectColumns.iterator(); colIterator
					.hasNext();) {
				String columnName = ((Column)((SelectExpressionItem) colIterator.next()).getExpression()).getColumnName();
				if(columnName.toUpperCase().contains("ID")){
					cacheTriggerEntity.setKeyColumn(columnName.toUpperCase());
					continue;
				}
				otherColumns.add(columnName.toUpperCase());
			}
		}
		cacheTriggerEntity.setColumns(otherColumns);
		
		/*List joinTables = plainSelect.getJoins();
		//判断是不是有其他的关联表
		if(null != joinTables && joinTables.size() > 0){
			for(int i = 0 ; i < joinTables.size() ; i++){
				Join joinTable = (Join)joinTables.get(i);
				cacheKey += "_" + ((Table)joinTable.getRightItem()).getName();
			}
		}*/
		
		//2、数据源
		if(StringUtils.isNotBlank(dataSourceId)){
			cacheTriggerEntity.setDataSource(dataSourceService.findById(dataSourceId));
		}
		
		//3、trigger名称定义
		//TODO 验证trigger名称是不是已经存在
		cacheTriggerEntity.setTriggerName(Constant.DICTIONARY_CHACHE_PREFIX + cacheKey.toUpperCase());
		
		//4、cache key
		//TODO 验证cache key是不是已经存在
		cacheTriggerEntity.setCacheKey(cacheKey.toLowerCase());
		
		return cacheTriggerEntity;
	}

	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.DictionaryCacheService#getTriggerScript(java.lang.String)
	 */
	public String getTriggerScript(String dataSourceId,String sqlExpression) throws JSQLParserException {
		DicCacheTriggerEntity cacheTriggerEntity = getDiCacheTriggerEntity(dataSourceId,sqlExpression);
		return dictionaryCacheDao.getTriggerSqlScript(cacheTriggerEntity, cacheTriggerEntity.getDataSource().getDbType());
	}
	
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.DictionaryCacheService#getDictionaryCacheRecord(java.lang.String)
	 */
	public List getDictionaryCacheRecord(String sqlExpression) throws JSQLParserException {
		List list = new ArrayList();
		Select select = (Select)sqlParserManager.parse(new StringReader(sqlExpression));
		SelectBody selectBody = select.getSelectBody();
		Table table = null;
		if(selectBody instanceof Union){
			Union union = (Union)selectBody;
			List plainSelects = union.getPlainSelects();
			for (Iterator iterator = plainSelects.iterator(); iterator.hasNext();) {
				PlainSelect plainSelect = (PlainSelect) iterator.next();
				list = dictionaryCacheDao.findByTableName(((Table)plainSelect.getFromItem()).getName());
				break;
			}
		}else if(selectBody instanceof PlainSelect){
			PlainSelect plainSelect = (PlainSelect)selectBody;
			table = (Table)plainSelect.getFromItem();
			list = dictionaryCacheDao.findByTableName(table.getName());
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.DictionaryCacheService#getTriggers(java.lang.String, java.lang.String)
	 */
	public List getTriggers(String dataSourceId, String sqlExpression) throws JSQLParserException {
		//获取数据源
		DataSource dataSource = dataSourceService.findById(dataSourceId);
		if(null == dataSource){
			throw new NullPointerException("数据源不存在");
		}
		//获取表名
		Table table = getOneTableInSql(sqlExpression);
		//查找trigger
		return dictionaryCacheDao.findTableTriggers(dataSource,table.getName());
	}

	public DictionaryCacheDao getDictionaryCacheDao() {
		return dictionaryCacheDao;
	}

	public void setDictionaryCacheDao(DictionaryCacheDao dictionaryCacheDao) {
		this.dictionaryCacheDao = dictionaryCacheDao;
	}

	public IDataSourceService getDataSourceService() {
		return dataSourceService;
	}

	public void setDataSourceService(IDataSourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	public CCJSqlParserManager getSqlParserManager() {
		return sqlParserManager;
	}

	public void setSqlParserManager(CCJSqlParserManager sqlParserManager) {
		this.sqlParserManager = sqlParserManager;
	}
	
	/**
	 * 获取某个sql中的表名
	 * 
	 * TODO 目前只是获得sql的一个表名，其他的有待扩展
	 * 
	 * @param sql 规则的sql表达式
	 * @return
	 * @throws JSQLParserException sql解析异常
	 */
	
	private List getTables(String sql) throws JSQLParserException{
		
		List tablesList = new ArrayList();//sql包含的表名，均变成了大写
		
		Table table = getOneTableInSql(sql);
		
		tablesList.add(table.getName());
		return tablesList;
	}
	
	
	/**
	 * 获取sql中的一个表名
	 * @param sqlExpression sql表达式
	 * @return
	 * @throws JSQLParserException
	 */
	private Table getOneTableInSql(String sqlExpression) throws JSQLParserException{
		PlainSelect plainSelect = null;
		try {
			plainSelect = (PlainSelect)((Select)sqlParserManager.parse(new StringReader(sqlExpression))).getSelectBody();
		} catch (JSQLParserException e) {
			throw new JSQLParserException("sql解析错误");
		}
		return (Table)plainSelect.getFromItem();
	}

	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.service.DictionaryCacheService#clearCache(java.util.Map)
	 */
	public void deleteCache(Map params) throws JSQLParserException {
		
		String []triggerName = (String[]) params.get("triggerName");//触发器名称
		String []cacheKey = (String[]) params.get("cacheKey");//缓存记录主键
		String []dataSourceId = (String[]) params.get("dataSourceId");//数据源主键
		
		//验证参数为空
		if(StringUtils.isBlank(triggerName[0]) || StringUtils.isBlank(cacheKey[0]) || StringUtils.isBlank(dataSourceId[0])){
			throw new NullPointerException("参数为空错误");
		}
		
		//1、获取数据源
		DataSource dataSource = dataSourceService.findById(dataSourceId[0]);
		
		//2、执行删除trigger
		dictionaryCacheDao.deleteTriggerByTriggerName(dataSource, triggerName[0]);
		
		//3、执行删除cache记录
		dictionaryCacheDao.deleteCacheRecord(cacheKey[0]);
	}
	
}
