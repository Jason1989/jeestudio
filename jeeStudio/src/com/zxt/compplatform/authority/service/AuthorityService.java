package com.zxt.compplatform.authority.service;

import java.util.List;

import com.zxt.compplatform.authority.entity.Authority;
import com.zxt.compplatform.formengine.entity.view.TreeData;

/**
 * 权限业务操作层接口
 * @author 007
 */
public interface AuthorityService {
	/**
	 * 通过角色id选出响应模板
	 */
	public List findMenuByRoleId(String roleid);

	/**
	 * 根据Id查找对象
	 * 
	 * @param id
	 * @return
	 */
	public Authority findById(String id);

	/**
	 * 根据父Id查找对象
	 * 
	 * @param id
	 * @return
	 */
	public List findAllByPid(String pid);

	/**
	 * 查找所有对象
	 * 
	 * @return
	 */
	public List findAll();

	/**
	 * 查找所有对象
	 * 
	 * @return
	 */
	public List findAllModule();

	/**
	 * 查找总记录数
	 * 
	 * @return
	 */
	public int findTotalRows();

	/**
	 * 根据父Id查找对象
	 * 
	 * @param id
	 * @param roleId
	 * @param flag
	 *            0 未授权，1 已授权
	 * @return
	 */
	public List findAllByPIdRoleId(String pid, String roleId, String flag);

	/**
	 * 根据角色id查询权限集合
	 * @param moduleId
	 * @param roleId
	 * @return
	 */
	public List getAuthorizedMenuByModuleIdRoleId(String moduleId, String roleId);

	/**
	 * 根据表单类型id查询表单 
	 * @param formTypeId
	 * @return
	 */
	public List findFormByTypeId(String formTypeId);

	/**
	 * 根绝角色id和表单类型id查询已授权表单
	 * @param formTypeId
	 * @param roleId
	 * @return
	 */
	public List findAuthorizedFormByTypeIdRoleId(String formTypeId,
			String roleId);

	/**
	 * 保存角色id和其对应的表单id
	 * @param roleId
	 * @param formIds
	 */
	public void roleFormConfigInsert(String roleId, String formIds);

	/**
	 * 查询全部资源
	 */
	public List<TreeData> findAllResource();

	/**
	 * 处理系统资源
	 * @param syetemID
	 * @param list
	 * @param checkTreeIDs
	 * @return
	 */
	public String dealWithResource(String syetemID, List<TreeData> list,
			String checkTreeIDs);

	/**
	 * 根据系统的id，查询出所有的子节点
	 * 
	 * @param systemId
	 *            系统的id活着是某个节点的id
	 * @return
	 */
	public List findAllNodesBySystemId(String systemId);

}
