package com.zxt.compplatform.authority.service;

import java.util.List;

import com.zxt.compplatform.authority.entity.OrgRole;
import com.zxt.compplatform.authority.entity.Role;
import com.zxt.compplatform.organization.entity.TOrganization;

/**
 * 角色操作业务逻辑接口
 * @author 007
 */
public interface RoleService {
	/**
	 * 查询所有角色与部门无关带分页
	 * 
	 */
	public List findRoleAllList(int rows,int page);
	/**
	 * 查询当前用户未选中的角色
	 * 
	 */
	public List findRoleAllList(int rows,int page,String userId);
	/**
	 * 查询所有角色总数与部门无关
	 * 
	 */
	public int findRoleAllNum();
	/**
	 * 查询当前用户未添加的角色
	 * @param userId
	 * @return
	 */
	public int findRoleAllNum(String userId);
	/**
	 * 查找所有角色
	 */
	public List findAll();
	
	/**
	 * 角色分页
	 */
	public List findRolePag(int page,int rows);
	
	/**
	 * 角色分页 带参数
	 */
	public List findRolePagWithParam(int page,int rows,String rname);
	/**
	 * 部门下 当前用户未选中的角色
	 */
	public List findRolePagWithParam(int page,int rows,String rname,String userId);
	
	//*******************************************************
	/**
	 * 用户列表(特定角色下的用户列表)
	 */
	public List ListUserUnderRole(int page,int rows,String rid);
	/**
	 * 查询特定角色下的用户条数
	 */
	public int findTotalRowsUnderRole(String rid);

	/**
	 * 查询角色功能
	 */
	public String findRFunction(String rname);
	//*******************************************************
	
	/**
	 * 查找角色的总条数
	 * @return
	 */
	public int findTotalRows();
	/**
	 * 查找角色的总条数 带参数
	 * @return
	 */
	public int findTotalRowsWithParam(String rname);
	
	/**
	 * 查找部门下 当前用户未选中的角色
	 * @return
	 */
	public int findTotalRowsWithParam(String rname,String userId);
	
	/**
	 * 添加角色(通过查询现有角色的最大值加1得到id)
	 *        (判断角色名称是否存在 )
	 */
	public String addRole(Role role,OrgRole or);

	/**
	 * 判断角色名称是否存在
	 */
	public String RoleIsExist(String roleName);
	
	/**
	 * 复制角色
	 */
	public String copyRole(Role role,String roleCopyNames,OrgRole or);
	
	/**
	 * 删除角色(删除级别必须同时把该级别下的用户的级别清空)
	 */
	public String deleteRole(String id);
	/*
	 * 添加组织机构与角色关系
	 */
    public void addOrgRole(OrgRole or);
	/**
	 * 修改角色
	 */
	public String updateRole(Role role,OrgRole oldor,OrgRole or);
	/**
	 * 根据角色id查出所在部门机构的信息
	 */	
	public TOrganization findOrgByRoId(String id );
	/**
	 * 根据id得到要修改的某一条级别的信息
	 */
	public Role findRoleById(String id);
	//*******************************************************
	/**
	 * 角色添加用户
	 */
	public void addUsersToRole(String rid,String usersid);
	
	/**
	 * 角色删除用户
	 */
	public void deleteUserUnderRole(String rid,String userids);
	
	
	//*********************************************************
	/**
	 * 全部用户列表(通用方法)
	 */
	public List selectAllUserForCommon(int page, int rows, String oid ,String username ,String uname);
	
	/**
	 * 用户数量
	 */
	public int userTotalCount(String oid, String username, String uname);
	//*********************************************************
	/**
	 * 根据用户的id查询其角色列表
	 * @param userid
	 * @return
	 */
	public List getRoleListUnderUser(String userid);
}
