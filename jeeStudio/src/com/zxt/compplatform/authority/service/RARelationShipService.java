package com.zxt.compplatform.authority.service;

import java.util.List;

import com.zxt.compplatform.authority.entity.RARelationShip;

/**
 * 角色资源关系管理接口
 * 
 * @author 007
 */
public interface RARelationShipService {

	/**
	 * 通过传入角色名的字符串集合（一个或多个）查到对应权限记录集合（去除重复的记录） T_ROLE（RNAME） TEST_AJAX_TREE
	 * 
	 * @param rString
	 */
	public List findAuths(String rString);

	/**
	 * 通过传入角色名的字符串集合（一个或多个）查到对应权限id集合(去除重复记录)
	 * 
	 * @param rString
	 * @return
	 */
	public List findAll(String rString);

	/**
	 * 根据联合主键查询关系实体
	 * 
	 * @param id
	 * @return
	 */
	public RARelationShip findById(String id);

	/**
	 * 查找所有对象
	 * 
	 * @return
	 */
	public List findAll();

	/**
	 * 查找总记录数
	 * 
	 * @return
	 */
	public int findTotalRows();

	/**
	 * 插入对象
	 * 
	 * @param raRelationShip
	 */
	public void insert(RARelationShip raRelationShip);

	/**
	 * 插入对象(给固定角色添加权限 批量插入)
	 * 
	 * @param raRelationShip
	 */
	public void roleMenuConfigInsert(String roleId, String menuIds,
			String systemRescId);

	/**
	 * 删除对象
	 * 
	 * @param raRelationShip
	 */
	public void delete(RARelationShip raRelationShip);

	/**
	 * 修改对象
	 * 
	 * @param raRelationShip
	 */
	public void deleteAll(List raList);

	/**
	 * 删除
	 * 
	 * @param roleId
	 */
	public void deleteByRoleId(String roleId);

	/**
	 * 根据角色选取菜单id
	 * 
	 * @param raRelationShip
	 */
	public List getMenusByRoleNames(String roles);

	/**
	 * 根据角色的名称，查询该角色拥有所有资源权限的实体
	 * 
	 * @param rnameString
	 *            角色字符串（逗号隔开）
	 * @param systemId（备用）
	 * @return
	 */
	public List getMenusByRoleNames(String roles, String systemId);

	/**
	 * 根据角色ID查询
	 * 
	 * @param roles
	 * @return
	 */
	public String findResourceIDsByRoleID(String roleID);
}