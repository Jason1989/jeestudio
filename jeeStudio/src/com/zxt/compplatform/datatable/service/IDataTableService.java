package com.zxt.compplatform.datatable.service;

import java.util.List;

import com.zxt.compplatform.datatable.entity.DataTable;

/**
 * 数据对象业务操作接口
 * 
 * @author 007
 */
public interface IDataTableService {

	/**
	 * 根据Id查找对象
	 * 
	 * @param id
	 * @return
	 */
	public DataTable findById(String id);

	/**
	 * 判断对象是否存在
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean isExist(String id, String name);

	/**
	 * 判断对象是不是存在
	 * 
	 * @param id
	 * @param name
	 *            数据对象名称
	 * @param dataSourceId
	 *            数据对象对应的数据源的id
	 * @return
	 */
	public boolean isExist(String id, String name, String dataSourceId);

	/**
	 * 判断对象是否存在
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean isExistUpdate(String id, String name);

	/**
	 * 根据name查找对象
	 * 
	 * @param name
	 * @return
	 */
	public DataTable findByName(String name);

	/**
	 * 根据条件查找所有对象
	 * 
	 * @return
	 */
	public List findAllLikeName(String nameArgs);

	/**
	 * 根据条件查找所有对象
	 * 
	 * @return
	 */
	public List findAllByDataObjectIdAndLikeName(String dataObjectId,
			String nameArgs);

	/**
	 * 查找所有对象
	 * 
	 * @return
	 */
	public List findAll();

	/**
	 * 根据分组ID查找所有对象
	 * 
	 * @param groupId
	 * @return
	 */
	public List findByGroupId(String groupId);

	/**
	 * 根据ID查找所有对象
	 * 
	 * @param ids
	 * @return
	 */
	public List findAllByIds(String ids);

	/**
	 * 查找总记录数
	 * 
	 * @return
	 */
	public int findTotalRows();

	/**
	 * GUOWEIXIN str传过来的是ID。此方法返回的总数 不包括传过来的str数组中的ID
	 */
	public int findTotalRowsByIsNotId(List<String> str, int flag,String mainObjectId);
	/**
	 * GUOWEIXIN str传过来的是ID。此分页返回的列表中 不包括传过来的ID值
	 */
	public List findAllByPageByIsNotId(int page, int rows, List<String> str,
			int flag,String mainObjectId);

	/**
	 * GUOWEIXIN str传过来的是ID。此分页返回的列表中 只包括传过来的ID值
	 */
	public List findAllByPageByIsId(int page, int rows, List<String> str);

	/**
	 * 分页查找
	 * 
	 * @param page
	 *            页码
	 * @param rows
	 *            每页记录数
	 * @return
	 */
	public List findAllByPage(int page, int rows);

	/**
	 * 查找总记录数
	 * 
	 * @return
	 */
	public int findTotalRowsByGroupIds(String groupIds);

	/**
	 * 分页查找
	 * 
	 * @param page
	 *            页码
	 * @param rows
	 *            每页记录数
	 * @return
	 */
	public List findAllByPageAndGroupIds(int page, int rows, String groupIds);
	
	 /**
	   * GUOWEIXIN 如果为1时，查询所有，即省略 groupIds in
	   */
		public List findAllByPageAndGroupId(int page, int rows, String groupId);
		/**
		   * GUOWEIXIN 如果为1时，查询总数，即省略 groupIds in
		   */		
		public int findTotalRowsByGroupId(String groupId);

	/**
	 * 查找总记录数
	 * 
	 * @return
	 */
	public int findTotalRowsByCondition(String groupIds, String name,
			String type);

	/**
	 * 分页查找
	 * 
	 * @param page
	 *            页码
	 * @param rows
	 *            每页记录数
	 * @return
	 */
	public List findAllByCondition(int page, int rows, String groupIds,
			String name, String type);

	/**
	 * 插入对象
	 * 
	 * @param DataTable
	 */
	public void insert(DataTable dataTable);

	/**
	 * 修改对象
	 * 
	 * @param DataTable
	 */
	public void update(DataTable dataTable);

	/**
	 * 删除对象
	 * 
	 * @param DataTable
	 */
	public void delete(DataTable dataTable);

	/**
	 * 根据Id删除对象
	 * 
	 * @param id
	 */
	public void deleteById(String id);

	/**
	 * 删除对象
	 * 
	 * @param paramCollection
	 */
	public void deleteAll(List paramCollection);

	/**
	 * 根据对象组Id查出所有子对象及对象组
	 * 
	 * @param groupId
	 * @return
	 */
	public List findChildrenByParentId(String parentId);

	public List findAllByDataSourceId(String dataSourceId);

	public void deleteAllByDataSourceId(String dataSourceId);
	
	/**
	 * 字段授权。根据父级ID，得到其下属表 GUOWEIXIN
	 */
	public int findTotalRowsByGroupIdsFieldGrant(String groupIds) ;
	public List findAllByPageAndGroupIdsFieldGrant(int page, int rows, String groupIds);
	//结束字段授权 GUOWEIXIN
}
