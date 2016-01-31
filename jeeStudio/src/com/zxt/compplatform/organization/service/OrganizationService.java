package com.zxt.compplatform.organization.service;

import java.util.List;

import com.zxt.compplatform.organization.entity.TOrgOrg;
import com.zxt.compplatform.organization.entity.TOrganization;
import com.zxt.compplatform.organization.entity.TUserTable;

/**
 * @author bozch
 * 2011-03-15
 * 
 */
public interface OrganizationService {
	/*
	 * 根据组织结构id 修改组织结构状态之前将组织机构状态全部归零
	 * 
	 */ 
	public void updateAllOrgClssify();
	/*
	 * 根据组织结构id 修改组织结构状态
	 * 
	 */ 
	public void updateOrgClssify(String oid);
	
	/*
	 * 根据部门复制部门id选取部门间的关系
	 * 
	 */ 
	public List getOrgOrg(String ooid[]);
	/**
	 * 获取所有的组织机构列表
	 * @return
	 */
	public List get_allOrginationList();
	
	
	/**
	 * 跟据部门表和部门关系表 得出所需的json
	 * @return
	 */
	public List get_jsonList();
	/**
	 * 跟据部门表和部门关系表和部门状态 得出所需的json
	 * @return
	 */
	public List get_jsonListByClassify();
	
	
	/**
	 * 跟据部门表和部门关系表 得出所需的json
	 * 带参数
	 * @return
	 */
	public List get_jsonListWithParam(String oid);
	
	
	/**
	 * 根据父节点id查询子节点
	 * @param pid
	 * @return
	 */
	public List  load_childrenJsonList(String pid);
	
	/**
	 * 根据部门id查询部门成员
	 * @param oid
	 * @return
	 */
	public List getUserByGroupId(String oid);
	
	/**
	 * 根据部门id查询其子部门的字符串
	 * @param oid
	 * @return
	 */
	public String getOidsByOid(String oid);
	
	/**
	 * 根据部门id查询特定部门信息
	 * @param oid
	 * @return
	 */
	public TOrganization getOrganization(String oid);
	
	/**
	 * 部门添加
	 */
	public void addOrganization(TOrganization organization,String upOrgId);
	
	/**
	 * 判断部门是否存在
	 */
	public int isExistOrg(String oid,String oupid,String oname);
	/**
	 * 得到部门中oid的最大值
	 */
	public int  maxvalue() ;
	
	/**
	 * 得到部门上下级表中的最大值
	 */
	public int maxValueFromOrgOrg();
	
	/**
	 * 向部门的上下级表中添加
	 */
	public void addOrgOrg(TOrgOrg orgorg) ;
	
	/**
	 * 部门修改
	 */
	public void updateOrganization(TOrganization organization);
	
	/**
	 * 部门删除
	 */
	public String deleteOrganization(String oid);
	
	//************************************************************************
	/**
	 * 根据用户id查询用户名(pass)
	 * @param uid
	 * @return
	 */
	public String getUserName(String uid);
	
	/**
	 * 分页查询(部门下)
	 * @param page  当前页
	 * @param rows  每页显示条数
	 * @param oid   部门id
	 * @return
	 */
	public List findAllByPageAndOid(int page,int rows,String oid);
	/**
	 * 用户条数(部门下)
	 */
	public int findUserTotalUnderOid(String oid);
	
	/**
	 *  添加用户(用户名可以重复)
	 * @param usertable
	 */
	public String addUser(TUserTable usertable,String oid);

	/**
	 * 修改用户
	 */
	public void updateUser(TUserTable usertable);
	
	/**
	 * 保存修改的用户的组织机构
	 */
	public void updateUserOragination(String  userId,String oid);
	
	//查询所有用户
	public List getAllUser();
	
	/**
	 * 删除用户
	 */
	public void deleteUserById(String id);
	
	/**
	 * 删除用户
	 */
	public String checkUserIsUse(String userId);
	/**
	 * 根据userid查询本条用户的信息
	 * @param userId
	 * @return
	 */ 
	public TUserTable findUserAllById(String userId);
	
	/**
	 * 判断用户名不是不是存在
	 * @param userName
	 * @param userId
	 * @return
	 */
	public boolean isUserNameExist(String userName,String userId);

	//***********************************************
	/**
	 * 根据用户名得到其包含的角色(包含角色的子角色)
	 * @param userid
	 * @return
	 */
	public List findAllRoleByUserId(String userid);
	
	/**
	 * 获取用户所对应的权限
	 * @return
	 */
	public List getAuthoritysIDByUserId(String userid);
	
	//**********************************************************
	/**
	 * 获取带用户的树
	 * @param treeDataList
	 * @param parentID
	 * @return
	 */
	public List treeAlgorithm(List treeDataList,String parentID);
	//**********************************************************
	
	/**
	 * 管理用户级别
	 */
	public void updateUser_UserLevel(String userid,String id);
	
	/**
	 * 管理用户角色(删除用户角色)
	 */
	public void deleteRoleUnderUser(String userid, String rids);
	
	/**
	 * 管理用户角色(添加用户角色)
	 */
	public void addRolesToUser(String userid, String rids);
	//***********************************************************
	/**
	 * 获取部门主管(根据部门id)
	 */
	public List getOrgLead(String oid);
	/**
	 * 给部门添加主管
	 */
	public void addLeadToOrg(String oid,String userids);
	/**
	 * 从部门移除主管
	 */
	public void removeLeadFormOrg(String oid,String userids);
	
	//GUOWEIXIN  获取部门数据组合树结构 用于后台。此方法作用是 configSql.properties 不影响后面 表单管理操作
	public List getAllJsonListByBack();
	//**********************************************************
}
