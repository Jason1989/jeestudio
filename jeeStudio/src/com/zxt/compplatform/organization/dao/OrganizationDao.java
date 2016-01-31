package com.zxt.compplatform.organization.dao;

import java.util.List;

import com.zxt.compplatform.authority.entity.RoleUser;
import com.zxt.compplatform.organization.entity.TOrgOrg;
import com.zxt.compplatform.organization.entity.TOrgUser;
import com.zxt.compplatform.organization.entity.TOrganization;
import com.zxt.compplatform.organization.entity.TUserTable;


/**
 * @author bozch
 * 2011-03-15
 * 
 */
public interface OrganizationDao {
	
	/////////////////////////////////组织相关/////////////////////////////////////////
	/*
	 * 根据部门复制部门id选取部门间的关系
	 * 
	 */
	/**
	 * 检查用户是否被使用
	 */
	public String checkUserIsUse(String userId);
	
	
	public List getOrgOrg(String[] ooid);
	/**
	 * 获取组织机构的列表
	 * @return
	 */
	/*
	 * 根据组织结构id 修改组织结构状态
	 * 
	 */ 
	public void updateOrgClssify(String oid);
	public List getAll();
	
	/*
	 * 根据组织结构id 修改组织结构状态之前将组织机构状态全部归零
	 * 
	 */ 
	public void updateAllOrgClssify();
	/**
	 * 获取jonslist列表
	 * @return
	 */
	public List getAllJsonList();
	//GUOWEIXIN  获取部门数据组合树结构 用于后台。此方法作用是 configSql.properties 不影响后面 表单管理操作
	public List getAllJsonListByBack();
	/**
	 * 获取jonslist列表
	 * 带参数
	 * @return
	 */
	public List getAllJsonListWithParam(String oid);
	/**
	 * 跟据部门表和部门关系表和部门状态 得出所需的json
	 * @return
	 */
	public List get_jsonListByClassify();
	
	/**
	 * 根据父节点查询子节点
	 * @param pid
	 * @return
	 */
	public List getJsonChildrenList(String pid);
	
	/**
	 * 根据部门id递归查询其子部门的id
	 * @param oid
	 * @return
	 */
	public List getOidsByOid(String oid);
	
	/**
	 * 根据部门id查询部门成员
	 * @param oid
	 * @return
	 */
	public List getUserByGroupId(String oid);
	
	/**
	 * 根据部门id查询特定部门信息(部门查看)
	 * @param oid
	 * @return
	 */
	public List getOrganization(String oid);
	
	/**
	 * 部门删除
	 * @param obj
	 */
	public void deleteOrganization(String oids);
	
	/**
	 * 删除部门之间的关联
	 */
	public void deleteOrgRelations(String oids);
	
	/**
	 * 部门添加
	 * @param organization
	 */
	public void addOrganization(TOrganization organization);
	
	/**
	 * 判断部门是否存在
	 */
	public int isExistOrg(String oupid,String oname);
	
	/**
	 * 判断部门是否存在
	 */
	public int isExistOrg(String oid,String oupid,String oname);
	 
	 /**
	 * 得到部门中oid的最大值
	 */
	public int  maxvalue();
		
	/**
	 * 得到部门上下级表中的最大值
	 */
	public int maxValueFromOrgOrg();	
		
	/**
	 * 向部门的上下级表中添加
	 */
	public void addOrgOrg(TOrgOrg orgorg);
	
	
	/**
	 * 部门修改
	 */
	public void updateOrganization(TOrganization organization);
	
	
	/**
	 * 根据用户id获取用户名(LIMS)
	 * @param id
	 * @return
	 */
	public List getUserNameById(String userid);
	//*****************************************
	/**
	 * 添加用户
	 * @param usertable
	 */
	public void addUser(TUserTable usertable);
	
	//查询用户表中的最大值
	public int getMaxIdInUser();
	//查询组织用户表中的最大值
	public int getMaxIdInUserOrg();
	//判断登录名是否存在
	public List isExistUserName(String username,String userId);
	/**
	 * 填充组织用户表
	 */
	public void addOrgUser(TOrgUser tou);
	//*****************************************
	/**
	 * 保存修改的用户
	 */
	public void updateUser(TUserTable usertable);

	/**
	 * 保存修改的用户的组织机构
	 */
	public void updateUserOragination(String  userId,String oid);
	//*****************************************
	
	/**
	 * 删除用户(用户表  用户角色表  用户组织表)
	 */
	public void deleteUserById(String id);
	public void deleteUserRole(String id);
	public void deleteUserOrg(String id);
	//*****************************************
	/**
	 * 根据id查询本条用户的信息
	 * @param userId
	 * @return
	 */ 
	public TUserTable findUserAllById(String userId);
	
	/**
	 * 用户分页数据获取
	 * @param page
	 * @param rows
	 * @param oid
	 * @return
	 */
	public List usersPagination(int page,int rows,String oid);
	
	/**
	 * 用户数量
	 */
	public int getTotalCount(String oid);
	///////////////////////////涉及用户以及角色//////////////////////////
	
	/**
	 * 获取跟用户直接对应的角色id
	 */
	public List getRoleIDByUserId(String userid);
	
	/**
	 * 获取角色对应的子角色
	 */
	public List getRolesIDByRole(Long roleId);
	
	/**
	 * 根据角色id的字符串集合获取对应的权限id
	 */
	public List getAuthsID(String string);
	
	//********************************************************************
	/**
	 * 查询某部门所有直属用户
	 * @param oid
	 * @return
	 */
	public List getNeedUser(String oid);
	//*******************************************************************
	/**
	 * 修改用户级别(用户id和级别id)
	 */
	public void updateUser_UserLevel(String userid,String id);
	
	//********************************************************************
	/**
	 * 管理用户角色(给用户添加新角色)
	 */
	public void add_User_Role(RoleUser roleuser);
	
	/**
	 * 查询角色用户表最大值
	 */
	public int findMaxIDFromRole_User();
	
	/**
	 * 查询用户下某角色是否存在(避免重复插入)
	 */
	public List isExistRoleInUser(String userid, String rid);
	
	/**
	 * 管理用户角色(删除用户角色)
	 */
	public void deletRoleUnderUser(String userid, String roleid);
	//********************************************************************
	/**
	 * 根据用户id串获取用户集合
	 */
	public List getOrgLeadList(String uids);
	/**
	 * 修改部门主管
	 */
	public void updateOrgLead(String oid,String userids);
	
	//查询所有用户
	public List getAllUser();
	
}
