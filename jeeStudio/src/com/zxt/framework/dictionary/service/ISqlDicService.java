package com.zxt.framework.dictionary.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zxt.framework.dictionary.dao.IDataDictionaryDao;
import com.zxt.framework.dictionary.dao.SqlDicDao;
import com.zxt.framework.dictionary.entity.DataDictionary;
import com.zxt.framework.dictionary.entity.DictionaryGroup;
import com.zxt.framework.dictionary.entity.SqlObj;

public interface ISqlDicService {

	public abstract SqlDicDao getZxtSqlDicDao();

	public abstract void setZxtSqlDicDao(SqlDicDao zxtSqlDicDao);

	public abstract void delete(DataDictionary dataDictionary);

	public abstract void deleteById(String id);

	public abstract void deleteAllByDataSourceId(String dataSourceId);

	public abstract void deleteAll(List paramCollection);

	public abstract List findAll();

	public abstract boolean isExist(String id, String name);

	public abstract boolean isExistUpdate(String id, String name);

	public abstract DataDictionary findById(String id);

	public abstract List findAllByDataSourceId(String dataSourceId);

	public abstract List findAllByIds(String ids);

	public abstract List findAllByGroupIds(String ids);

	public abstract List findByDictGroupId(String dictGroupId);

	public abstract List findDictLikeName(String dictName);

	public abstract int findTotalRows();

	public abstract List findAllByPage(int page, int rows);

	public abstract int findTotalRowsByCondition(String dictName,
			String dictGroup);

	public abstract List findAllByCondition(int page, int rows,
			String dictName, String dictGroup);

	public abstract void insertAll(List list);

	public abstract void update(DataDictionary dataDictionary);

	//group
	public abstract void deleteDictGroup(DictionaryGroup dictionaryGroup);

	public abstract void deleteDictGroupById(String id);

	public abstract void deleteAllDictGroup(List paramCollection);

	public abstract List findAllDictGroup();

	public abstract List findAllOrgGroup();

	public abstract DictionaryGroup findDictGroupById(String id);

	public abstract List findAllDictGroupByIds(String ids);

	public abstract int findDictGroupTotalRows();

	public abstract List findAllDictGroupByPage(int page, int rows);

	public abstract List findDictItemById(String id);

	public abstract void insertDictGroup(DictionaryGroup dictionaryGroup);

	public abstract void updateDictGroup(DictionaryGroup dictionaryGroup);

	public abstract void setDataDictionaryDao(
			IDataDictionaryDao dataDictionaryDao);

	public abstract List magicMake(String dbSource, String dicGroup,
			String tableKey, String primaryKey, String nameKey);

	public abstract boolean insert(SqlObj sqlObj, String params);

	public abstract String getParamsDataById(String id);

	public abstract boolean updateSql(SqlObj sqlObj, String params);

	public abstract void deleteSql(String ids);

	public abstract SqlObj findSqlById(String id);

	public abstract Object execteSqlDic(SqlObj sqlobj,
			HttpServletRequest request, HttpServletResponse response);

}