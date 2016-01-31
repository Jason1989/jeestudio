package com.zxt.compplatform.authority.dao;

import java.util.List;

import com.zxt.compplatform.authority.entity.OrgRole;
import com.zxt.compplatform.authority.entity.Role;
import com.zxt.compplatform.authority.entity.RoleUser;
import com.zxt.compplatform.organization.entity.TOrganization;

/**
 * 角色
 * 数据持久化操作
 * @author 007
 */
public interface RoleDao{
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
	 * 添加组织机构与角色关系
	 */
    public void addOrgRole(OrgRole or);
	/**
	 * 角色数目
	 * @return
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
	public List findAllRole();
	
    /**
	 * 查询角色列表
	 */
	public List roleListPag(int page,int rows);
	
	/**
	 * 查询角色列表 带参数
	 */
	public List roleListPagWithParam(int page,int rows,String rname);
	/**
	 * 查询角色列表 带参数
	 */
	public List roleListPagWithParam(int page,int rows,String rname,String userId);
	
	//**********************************************************
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
	//**********************************************************
	
	/**
	 * 查询角色总条数 
	 */
    public int findTotalRows(); 
    /**
	 * 查询角色总条数 带参数
	 */
    public int findTotalRowsWithParam(String rname);
    /**
	 * 查询角色总条数 带参数
	 */
    public int findTotalRowsWithParam(String rname,String userId);
	
	/**
	 * 删除角色
	 * @param id
	 */
	public void deleteRole(String id);
	
	/**
	 * 添加级别(级别ID为查询出的最大值加1)
	 * 添加组织结构与角色之间关系
	 */
	public void addRole(Role role,OrgRole or);
	/**
	 * 添加级别(级别ID为查询出的最大值加1)
	 */
	public void addRole(Role role);
	/**
	 * 查询角色ID最大值
	 */
	public int findMaxId();
	
	/**
	 * 判断角色是否存在(添加时)
	 */
	public List isExist(String name);
	
	/**
	 * 判断角色是否存在(修改时)
	 */
	public int isExist(Role role);
	
	/**
	 * 根据角色id查出所在部门机构的信息
	 */	
	public TOrganization findOrgByRoId(String id );
	/**
	 * 修改角色
	 */
	public void updateRole(Role role,OrgRole oldor,OrgRole or);
	
	/**
	 * 根据id查询特定角色
	 */
	public Role findRoleById(String id);
	
	/**
	 * 查询特定角色下是否有用户 
	 */
	public List isExistUser(String id);
	/**
	 * 查找某个角色下的用户 
	 */
	public List findUserByRoleId(String id);
	
	//*****************角色修改用户************
	/**
	 * 查询角色用户表中的最大值
	 */
	public int findMaxIDFromRole_User();
	
	/**
	 * 给角色用户表添加信息
	 */
	public void addUserToRole(RoleUser roleuser);
	
	/**
	 * 判断要添加的用户是否重复
	 */
	public List isExistUserInRoleUser(String rid,String uid);
	
	/**
	 * 删除角色下的特定用户
	 */
	public void deletUserUnderRole(String rid, String uid);
	
	//************************************************************

	/**
	 * 获取所有用户
	 * @param page
	 * @param rows
	 * @param oid
	 * @param username
	 * @param uname
	 * @return
	 */
	public List getAllUserForCommon(int page, int rows, String oid ,String username, String uname);;
	/**
	 * 查询某个用户下的角色的总数
	 * @param oid
	 * @param username
	 * @param uname
	 * @return
	 */
	public int userTotalCount(String oid, String username ,String uname);
	//*********************************************************
	/**
	 * 查询某个用户下的角色
	 * @param userid
	 * @return
	 */
	public List getRoleListUnderUser(String userid);
}
