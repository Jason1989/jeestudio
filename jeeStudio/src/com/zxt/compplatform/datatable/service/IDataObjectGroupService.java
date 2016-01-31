package com.zxt.compplatform.datatable.service;

import java.util.List;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.datatable.entity.DataObjectGroup;

/**
 * 数据对象分组业务操作接口
 * 
 * @author 007
 */
public interface IDataObjectGroupService {
	/**
	 * 根据Id查找对象
	 * @param id
	 * @return
	 */
	public DataObjectGroup findById(String id);
	/**
	 * 根据Name查找对象
	 * @param name
	 * @return
	 */
	public DataObjectGroup findByName(String name);
	/**
	 * 根据ID查找所有对象
	 * @param ids 
	 * @return
	 */
	public List findAllByIds(String ids);
	/**
	 * 根据ID查找所有对象
	 * @param ids 
	 * @return
	 */
	public List findAllByPid(String pid);
	/**
	 * 根据ID查找父对象
	 * @param id
	 * @return
	 */
	public DataObjectGroup findParentById(String id);
	/**
	 * 查找所有对象
	 * @return
	 */
	public List findAll();
	/**
	 * 插入对象
	 * @param DataTable
	 */
	public void insert(DataObjectGroup dataObjectGroup);
	/**
	 * 修改对象
	 * @param DataTable
	 */
	public void update(DataObjectGroup dataObjectGroup);
	/**
	 * 删除对象
	 * @param DataTable
	 */
	public void delete(DataObjectGroup dataObjectGroup);
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
	/**
	 * 查找所有子ID
	 * @param id
	 * @return
	 */
	public List findChildrenIdById(String id);
}
