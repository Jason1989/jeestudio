package com.zxt.compplatform.authority.dao;
import java.util.List;

import com.zxt.compplatform.authority.entity.RARelationShip;

/**
 * 角色资源关系
 * 数据持久化操作
 * @author 007
 */
public interface RARelationShipDao {

	/**
	 * 添加角色资源关系
	 * @param relationShip
	 */
	public void addRelationShip(RARelationShip relationShip);
	/**
	 * 执行sql，插入数据
	 * @param sql
	 */
	public void insert(String sql);
	/**
	 * 删除角色的资源
	 * @param sql
	 */
	public void deleteRoleMenu(String sql);
    /**
     * 根据角色名称获取的资源
     * @param rnameString
     * @return
     */
    public List listrights(String rnameString);
    /**
     * 根据角色名称获取的资源
     * @param rnameString
     * @return
     */
    public List listAuthoritys(String rnameString);
	/**
	 * 将集合插入到数据库
	 * @param list
	 */
	public void insertAll(List list);
	/**
	 * 查询所有的记录
	 * @return
	 */
	public List getAllRecords(String roleId);
	
	/**
	 * 根据角色的名称，查询该角色拥有所有资源权限的实体
	 * @param rnameString 角色字符串（逗号隔开）
	 * @param systemId（备用）
	 * @return
	 */
	public List listRights(String rnameString,String systemId);
	/**
	 * 根据角色ID查询
	 * @param roles
	 * @return
	 */
	public String findResourceIDsByRoleID(String roleID); 
}
