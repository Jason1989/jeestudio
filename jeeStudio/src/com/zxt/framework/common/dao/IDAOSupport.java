/**
 * Copyright© 2010 zxt Co. Ltd.
 * All right reserved.
 * 
 */
package com.zxt.framework.common.dao;

import java.util.Collection;
import java.util.List;

/**
 * 数据持久化基类
 * Title: IDAOSupport 
 * Description: 
 * Create DateTime: 2010-9-10
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public abstract interface IDAOSupport {
	/**
	 *  查询数据
	 * @param paramString
	 * @return 数据集合
	 */
	public abstract List find(String paramString);

	/**
	 * 根据单个参数查询数据
	 * @param paramString
	 * @param paramObject
	 * @return 数据集合
	 */
	public abstract List find(String paramString, Object paramObject);

	/**
	 * 根绝多个参数查询数据集合
	 * @param paramString
	 * @param paramArrayOfObject
	 * @return 数据集合结果
	 */
	public abstract List find(String paramString, Object[] paramArrayOfObject);

	/**
	 * 创建实体
	 * @param paramObject
	 */
	public abstract void create(Object paramObject);

	/**
	 * 删除实体
	 * @param paramObject
	 */
	public abstract void delete(Object paramObject);

	/**
	 * 更新某个实体
	 * @param paramObject
	 */
	public abstract void update(Object paramObject);

	/**
	 * 删除多条记录
	 * @param paramCollection
	 */
	public abstract void deleteAll(Collection paramCollection);

	/**
	 * 更新多条记录
	 * @param paramCollection
	 */
	public abstract void updateAll(Collection paramCollection);

	/**
	 * 查询所有的记录
	 * @return
	 */
	public abstract List findAll();

	/**
	 * 查询所有的结果数目
	 * @param queryString
	 * @return
	 */
	public abstract int findTotalRows(String queryString);

	/**
	 * 分页查询列表数据
	 * @param paramString
	 * @param page
	 * @param rows
	 * @return
	 */
	public abstract List findAllByPage(String paramString, int page, int rows);

	/**
	 * 同时创建多个数据
	 * @param paramCollection
	 */
	public abstract void createAll(Collection paramCollection);

	/**
	 * 执行更新语句
	 * @param updateString
	 * @return
	 */
	public abstract boolean executeSQLUpdate(String updateString);

}