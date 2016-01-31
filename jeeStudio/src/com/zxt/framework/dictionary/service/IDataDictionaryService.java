package com.zxt.framework.dictionary.service;

import java.util.List;

import com.zxt.framework.dictionary.entity.DataDictionary;
import com.zxt.framework.dictionary.entity.DictionaryGroup;

public interface IDataDictionaryService {
	/**
	 * 根据Id查找对象
	 * @param id
	 * @return
	 */
	public DataDictionary findById(String id);
	/**
	 * 根据名称查找对象
	 * @param name
	 * @return
	 */
	public DictionaryGroup findGroupByName(String name);
	/**
	 * 判断对象是否存在
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean isExist(String id,String name);
	/**
	 * 判断对象是否存在
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean isExistUpdate(String id,String name);
	/**
	 * 判断数据字典分组是否存在
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean isDicGroupExistUpdate(String id,String name);
	/**
	 * 根据ID查找所有对象
	 * @param ids  
	 * @return
	 */
	public List findAllByIds(String ids);
	/**
	 * 根据多个GroupID查找所有对象
	 * @param ids  
	 * @return
	 */
	public List findAllByGroupIds(String ids);
	/**
	 * 查找所有对象
	 * @return
	 */
	public List findAll();
	/**
	 * 根据GroupID查找所有对象
	 * @param dictGroupId
	 * @return
	 */
	public List findByDictGroupId(String dictGroupId);
	/**
	 * 根据名称模糊查询对象
	 * @return
	 */
	public List findDictLikeName(String dictName);
	/**
	 * 查找总记录数
	 * @return
	 */
	public int findTotalRows();
	/**
	 * 分页查找
	 * @param page 页码
	 * @param rows 每页记录数
	 * @return
	 */
	public List findAllByPage(int page,int rows);
	/**
	 * 查找总记录数
	 * @return
	 */
	public int findTotalRowsByCondition(String dictName,String dictGroup);
	/**
	 * 分页查找
	 * @param page 页码
	 * @param rows 每页记录数
	 * @return
	 */
	public List findAllByCondition(int page,int rows,String dictName,String dictGroup);
	/**
	 * 插入对象
	 * @param DataDictionary
	 */
	public void insert(DataDictionary dataDictionary);
	public void insertAll(List dataDictionarys) ;
	/**
	 * 修改对象
	 * @param DataDictionary
	 */
	public void update(DataDictionary dataDictionary);
	/**
	 * 删除对象
	 * @param DataDictionary
	 */
	public void delete(DataDictionary dataDictionary);
	/**
	 * 根据Id删除对象
	 * @param id
	 */
	public void deleteById(String id);
	/**
	 * 删除对象
	 * @param paramCollection
	 */
	public void deleteAll(List paramCollection);
	//group
	/**
	 * 根据Id查找对象
	 * @param id
	 * @return
	 */
	public DictionaryGroup findDictGroupById(String id);
	/**
	 * 根据ID查找所有DictionaryGroup对象
	 * @param ids 
	 * @return
	 */
	public List findAllDictGroupByIds(String ids);
	/**
	 * 查找所有DictionaryGroup对象
	 * @return
	 */
	public List findAllDictGroup();
	/**
	 * 查找DictionaryGroup总记录数
	 * @return
	 */
	public int findDictGroupTotalRows();
	/**
	 * 分页查找DictionaryGroup
	 * @param page 页码
	 * @param rows 每页记录数
	 * @return
	 */
	public List findAllDictGroupByPage(int page,int rows);
	/**
	 * 插入DictionaryGroup对象
	 * @param DictionaryGroup
	 */
	public void insertDictGroup(DictionaryGroup dictionaryGroup);
	/**
	 * 修改DictionaryGroup对象
	 * @param DictionaryGroup
	 */
	public void updateDictGroup(DictionaryGroup dictionaryGroup);
	/**
	 * 删除DictionaryGroup对象
	 * @param DictionaryGroup
	 */
	public void deleteDictGroup(DictionaryGroup dictionaryGroup);
	/**
	 * 根据Id删除DictionaryGroup对象
	 * @param id
	 */
	public void deleteDictGroupById(String id);
	/**
	 * 删除对象
	 * @param paramCollection
	 */
	public void deleteAllDictGroup(List paramCollection);
	/**
	 * 对象转成字典项
	 * @param id
	 * @return
	 */
	public List findDictItemById(String id);
	/**
	 * 快速批量生成动态字典
	 * @param dbSource
	 * @param dicGroup
	 * @param tableKey
	 * @param primaryKey
	 * @param nameKey
	 * @return
	 */
	public List magicMake(String dbSource, String dicGroup, String tableKey,
			String primaryKey, String nameKey);
	
	/**
	 * 
	 * @return
	 */
	public List findAllOrgGroup();
	
	public void deleteAllByDataSourceId(String dataSourceId);
	
	public List findAllByDataSourceId(String dataSourceId);
	
	/**
	 * 查询当前页数据
	 * GUOWEIXIN
	 */
	public List findAllByPageByGroupId(int page, int rows,String groupId);
	/**
	 * 根据字典分组ID得到下方字典行。
	 * GUOWEIXIN
	 */
	public int findTotalRowsByGroupId(String groupId);
	/**
	 * 插入全部数据
	 */
	public boolean insertAllDataDictionary(List list);
}

