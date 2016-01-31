package com.zxt.framework.dictionary.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.framework.dictionary.entity.SqlObj;

public interface SqlDicDao {

	/**
	 * 单行删除
	 */
	public abstract int deleteData(String sql, String[] pramers,
			String dataSourceId);

	/**
	 * 单行保存
	 * 
	 * @param dataSourceId
	 */
	public abstract int dynamicSave(String sql, String[] endParmer,
			String dataSourceId);

	/**
	 * 主键生成
	 */
	public abstract String createIDkey(String tableName, String IDkey);

	/**
	 * 加载数据字典
	 */
	public abstract Map loadDynamicDictionary(String sql, String dataSourceID);

	/**
	 * 查找 配置xml
	 * 
	 * @param sql
	 * @return
	 */
	public abstract String findBlobXMLById(String sql, String formID);

	/**
	 * 列表页数据
	 * 
	 * @param sql
	 * @return
	 */
	public abstract List queryFormData(String sql, String[] parmerValue,
			ListPage listPage, HttpServletRequest request);

	/**
	 * 根据表单Id 查找 在缓存中对应的数据源连接池
	 */
	public abstract ComboPooledDataSource findPoolByDataSourceId(
			String dataSourceId);

	/**
	 * 查询表格
	 * 
	 * @param formId
	 * @param sql
	 * @param conditions
	 * @param listPage
	 * @param request
	 * @return
	 */
	public abstract List queryForExport(String formId, String sql,
			String[] conditions, ListPage listPage, HttpServletRequest request);

	/**
	 * 查询总行数
	 * 
	 * @param queryString
	 * @return
	 */
	public abstract int findTotalRows(String queryString);

	/**
	 * 查询所有页面
	 * 
	 * @param paramString
	 * @param page
	 * @param rows
	 * @return
	 */
	public abstract List findAllByPage(String paramString, int page, int rows);

	/**
	 * 查询列表页
	 * 
	 * @param paramString
	 * @param objects
	 * @return
	 */
	public abstract List queryForList(String paramString, Object[] objects);

	/**
	 * 添加语句
	 * 
	 * @param sqlObj
	 * @param params
	 * @return
	 */
	public abstract boolean addSql(SqlObj sqlObj, String params);

	/**
	 * 查询接口
	 * 
	 * @param sql
	 * @param objects
	 * @return
	 */
	public abstract Map queryForObject(String sql, Object[] objects);

	/**
	 * 更新接口
	 * 
	 * @param sql
	 * @param objects
	 * @return
	 */
	public boolean updateSql(SqlObj sqlObj, String params);

	/**
	 * 更新接口
	 * 
	 * @param sql
	 * @param conditions
	 * @return
	 */
	boolean update(String sql, Object[] conditions);

	/**
	 * 执行sql类 数据字典
	 * @param sqlobj
	 * @param request
	 * @param response
	 * @return
	 */
	public abstract Object execteSqlDic(SqlObj sqlobj,
			HttpServletRequest request, HttpServletResponse response);

}