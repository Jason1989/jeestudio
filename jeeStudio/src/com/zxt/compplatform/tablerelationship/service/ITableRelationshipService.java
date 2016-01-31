package com.zxt.compplatform.tablerelationship.service;

import java.util.List;

import com.zxt.compplatform.tablerelationship.entity.TableRelationship;

public interface ITableRelationshipService {
	/**
	 * 根据Id查找对象
	 * @param id
	 * @return
	 */
	public TableRelationship findById(String id);
	/**
	 * 查找所有对象
	 * @return
	 */
	public List findAll();
	/**
	 * 插入对象
	 * @param TableRelationship
	 */
	public void insert(TableRelationship tableRelationship);
	/**
	 * 修改对象
	 * @param TableRelationship
	 */
	public void update(TableRelationship tableRelationship);
	/**
	 * 删除对象
	 * @param TableRelationship
	 */
	public void delete(TableRelationship tableRelationship);
	/**
	 * 根据Id删除对象
	 * @param id
	 */
	public void deleteById(String id);
}
