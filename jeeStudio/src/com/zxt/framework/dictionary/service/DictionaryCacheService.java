package com.zxt.framework.dictionary.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.JSQLParserException;

/**
 * 数据字段缓存操作
 * 
 * @author chinazxt
 *
 */
public interface DictionaryCacheService {

	
	/**
	 * 数据库表添加缓存功能
	 *    1、添加trigger
	 *    2、添加数据库表记录
	 *    
	 *    表名，数据源（类型），列名(主键)，trigger名称,缓存表主键值
	 * @param params 包含数据源的id和动态数据字典的表达式
	 * @exception JSQLParserException sql解析异常
	 */
	public void insertCache(Map params)throws JSQLParserException,SQLException;
	
	
	/**
	 * 获取某个表的触发器
	 * @param dataSourceId 数据源的id
	 * @param sqlExpression 动态数据字典的表达式
	 * @return
	 */
	public List getTriggers(String dataSourceId,String sqlExpression)throws JSQLParserException;
	
	/**
	 * 根据表名查询相应的缓存记录
	 * @param sqlExpression 动态数据字典的表达式
	 * @return
	 */
	public List getDictionaryCacheRecord(String sqlExpression) throws JSQLParserException;
	
	
	/**
	 * 清除缓存相关的trigger和cache记录
	 * 
	 * @param params 包含sql表达式和datasoureid
	 * @throws JSQLParserException
	 */
	public void deleteCache(Map params)throws JSQLParserException;
	
	/**
	 * 获取相应的trigger脚本
	 * @param dataSourceId
	 * @param sqlExpression
	 * @return
	 */
	public String getTriggerScript(String dataSourceId,String sqlExpression) throws JSQLParserException ;
}
