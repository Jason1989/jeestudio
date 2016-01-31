package com.zxt.compplatform.datatable.dao;

import java.util.List;

import com.zxt.framework.common.dao.IDAOSupport;

/**
 * 数据对象持久化操作接口
 * 
 * @author 007
 */
public interface IDataTableDao extends IDAOSupport {
	/**
	 * 查找所有子ID
	 * 
	 * @param id
	 * @return
	 */
	public List findChildrenIdById(String id);

	/**
	 * 根据对象组Id查出所有子对象及对象组
	 * 
	 * @param groupId
	 * @return
	 */
	public List findChildrenByParentId(String parentId);
}
