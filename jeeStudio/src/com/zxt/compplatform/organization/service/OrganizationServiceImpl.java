package com.zxt.compplatform.organization.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.chinazxt.hb.encoder.util.Base64EncoderFlow;
import com.zxt.compplatform.authority.entity.Role;
import com.zxt.compplatform.authority.entity.RoleUser;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.organization.dao.OrganizationDao;
import com.zxt.compplatform.organization.entity.TOrgOrg;
import com.zxt.compplatform.organization.entity.TOrgUser;
import com.zxt.compplatform.organization.entity.TOrganization;
import com.zxt.compplatform.organization.entity.TUserTable;


/**
 * @author bozch
 * 2011-03-15
 * 
 */
public class OrganizationServiceImpl implements OrganizationService{

	private OrganizationDao organizationDao;

	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

	public List get_allOrginationList() {
		return organizationDao.getAll();
	}
	//GUOWEIXIN  获取部门数据组合树结构 用于后台。此方法作用是 configSql.properties 不影响后面 表单管理操作
	public List getAllJsonListByBack(){
		return organizationDao.getAllJsonListByBack();
	}
	public List get_jsonList(){
		return organizationDao.getAllJsonList();
	}
	/*
	 * 根据组织结构id 修改组织结构状态之前将组织机构状态全部归零
	 * 
	 */ 
	public void updateAllOrgClssify(){
		organizationDao.updateAllOrgClssify();
	}
	/**
	 * 跟据部门表和部门关系表和部门状态 得出所需的json
	 * @return
	 */
	public List get_jsonListByClassify(){
		return organizationDao.get_jsonListByClassify();
	}
	/*
	 * 根据组织结构id 修改组织结构状态
	 * 
	 */ 
	public void updateOrgClssify(String oid){
		organizationDao.updateOrgClssify(oid);
	}
	
	/**
	 * 带参数
	 * @return
	 */
	public List get_jsonListWithParam(String oid){
		return organizationDao.getAllJsonListWithParam(oid);
	}
	
	public List load_childrenJsonList(String pid) {
		return organizationDao.getJsonChildrenList(pid);
	}
	
	public List getUserByGroupId(String oid) {
		return organizationDao.getUserByGroupId(oid);
	}
	
	public TOrganization getOrganization(String oid){
		List list = organizationDao.getOrganization(oid);
		if(list !=null && list.size()>0){
			TOrganization org = (TOrganization)list.get(0);
			if(org.getLead() != null && org.getLead().length() != 0){
				String lead = "";
				String[] uid = org.getLead().split(",");
				for(int i=0;i<uid.length;i++){
					lead +=getUserName(uid[i])+",";
				}
				org.setLead(lead.substring(0,lead.length()-1));
			}
			return org;
		}
		return new TOrganization();
	}
	
//**********************************************************	
	/**
	 * 添加部门
	 */
	public void addOrganization(TOrganization organization,String upOrgId) {
		
		
		
		//添加到部门列表中
		organizationDao.addOrganization(organization);
		
		//添加到部门的上下级表中   
//		TOrgOrg orgorg = new TOrgOrg();
//		
//		orgorg.setId(new Long(System.currentTimeMillis())+"");
//			//1.设置部门上下级表中的id
//		orgorg.setUpid(new Long(upOrgId));
//		orgorg.setDownid(new Long(val.longValue()+1));
//		
//		addOrgOrg(orgorg);
	}
	
	/**
	 * 判断部门是否存在
	 * @param oid 当前部门的id
	 * @param oupid 上级部门的id
	 * @param oname 当前部门的名称
	 */
	public int isExistOrg(String oid,String oupid,String oname){
		if(StringUtils.isBlank(oupid)){
			throw new NullPointerException("上级部门id不能为空");
		}
		if(StringUtils.isNotBlank(oid)){
			return organizationDao.isExistOrg(oid,oupid,oname);
		}else{
			return organizationDao.isExistOrg(oupid,oname);
		}
	}
	/**
	 * 得到部门中oid的最大值
	 */
	public int maxvalue(){
		return organizationDao.maxvalue();
	}
	
	/**
	 * 得到部门上下级表中的最大值
	 */
	public int maxValueFromOrgOrg(){
		return organizationDao.maxValueFromOrgOrg();
	}
	
	/**
	 * 向部门的上下级表中添加
	 */
	public void addOrgOrg(TOrgOrg orgorg){
		organizationDao.addOrgOrg(orgorg);
	}
	
//**************************************************************
	/**
	 * 删除部门
	 */
	public String deleteOrganization(String oid) {
		//判断该部门下有没有子部门
		List org = organizationDao.getOidsByOid(oid);
		//判断该部门下有没有用户
		List user = organizationDao.getUserByGroupId(oid);
		
		if(user!=null && user.size()>0){
			return "hasuser";
		}
		
		if(org !=null && org.size()>0){
			return "has";
		}
		
		//删除部门
		organizationDao.deleteOrganization(oid);
		//删除关联
		organizationDao.deleteOrgRelations(oid);
		return "do";
	}
//**************************************************************
	//修改部门
	public void updateOrganization(TOrganization organization) {
		organizationDao.updateOrganization(organization);
	}

//***********************************************************
	public String getUserName(String uid) {//根据用户id获取用户名
		List list = organizationDao.getUserNameById(uid);
		TUserTable usertable = (TUserTable)list.get(0);
		return usertable.getUname();
	}
	
	//查询所有用户
	public List getAllUser(){
		return organizationDao.getAllUser();
	}
//***********************************************************	
	/**
	 * 添加用户
	 */
	public String addUser(TUserTable usertable ,String oid){
		String username = usertable.getUsername();
		List list = organizationDao. isExistUserName(username,null);
		if(list != null && list.size()>0){
			return "fail";
		}
		//添加用户
		Long newUserid = new Long(organizationDao.getMaxIdInUser()+1);
		usertable.setUserid(newUserid);
		
		//encode the password
		String tempPass = usertable.getUserpassword();
		byte[] by = tempPass.getBytes();
		if ("MD5".equals(Constant.PASSWORD_TYPE)) {
			//tempPass = new Base64EncoderFlow().encodeBuffer(by);
		}else{
			tempPass = new Base64EncoderFlow().encodeBuffer(by);
		}
		
		
		
		usertable.setUserpassword(tempPass);
		organizationDao.addUser(usertable);
		
		//填充组织用户表
		TOrgUser tou = new TOrgUser();
		tou.setId(new Long(System.currentTimeMillis())+"");
		tou.setOid(new Long(oid));
		tou.setUserid(new Long(newUserid.longValue()));
		organizationDao.addOrgUser(tou);
		return "success";
	}
	
	/**
	 * 验证用户名是不是存在
	 */
	public boolean isUserNameExist(String userName,String userId){
		List list = organizationDao. isExistUserName(userName,userId);
		if(list != null && list.size() > 0){
			return true;
		}
		return false;
	}
//************************************************************	
	/**
	 * 修改用户
	 */
	public void updateUser(TUserTable usertable){
		
		//encode the password
		String tempPass = usertable.getUserpassword();
		TUserTable user=this.findUserAllById(usertable.getUserid().toString());
		if(!tempPass.equals(user.getUserpassword())){
			byte[] by = tempPass.getBytes();
			
			if ("MD5".equals(Constant.PASSWORD_TYPE)) {
				//tempPass = new Base64EncoderFlow().encodeBuffer(by);
			}else{
				tempPass = new Base64EncoderFlow().encodeBuffer(by);
			}
			
			usertable.setUserpassword(tempPass);
			
		}
	    organizationDao.updateUser(usertable);
	}

	/**
	 * 保存修改的用户的组织机构
	 */
	public void updateUserOragination(String  userId,String oid){
		organizationDao.updateUserOragination(userId, oid);
	}
//************************************************************	
	/**
	 * 删除用户(删除前判断未终断)
	 */
	public void deleteUserById(String id){
		organizationDao.deleteUserById(id);
		//organizationDao.deleteUserRole(id);
		//organizationDao.deleteUserOrg(id);
	}
//************************************************************
	/**
	 * 根据userid查询本条用户的信息
	 * @param userId
	 * @return
	 */ 
	public TUserTable findUserAllById(String userId){
		TUserTable user = organizationDao.findUserAllById(userId);
		if(user.getSex()!=null){
			user.setSex(user.getSex().trim());
		}
		return user ;
	}
	
//*****************************************************************
	/**
	 * 用户分页(部门下)
	 */
	public List findAllByPageAndOid(int page, int rows, String oid) {
		return organizationDao.usersPagination(page, rows, oid);
	}
	/**
	 * 用户条数(部门下)
	 */
	public int findUserTotalUnderOid(String oid){
		return organizationDao.getTotalCount(oid);
	}
	////////////////////////////////分割线///////////////////////////////////
	/**
	 * 获取某组织以及其子组织的id的字符串(pass)
	 */
	public String getOidsByOid(String oid){
		List list = organizationDao.getOidsByOid(oid);
		List tempList = new ArrayList();
		String sqlquery = "";
		for(int i=0;i<list.size();i++){
			TOrganization organization = (TOrganization)list.get(i);
			if(!tempList.contains(organization.getOid())){
				tempList.add(organization.getOid());
				sqlquery+=organization.getOid()+",";
			}
		}
		//加上自己
		sqlquery+=oid;
		return sqlquery;
	}
	
	/**
	 * 根据用户名获取角色id(包含角色的子角色)
	 */
	public List findAllRoleByUserId(String userid) {
		List list = organizationDao.getRoleIDByUserId(userid);
		
		List roleIdList = new ArrayList();
		List tempList = null;
		for(int i=0;i<list.size();i++){
			Role role = (Role)list.get(i);
			tempList = organizationDao.getRolesIDByRole(role.getId());
			roleIdList.add(role.getId());
			for(int j=0;j<tempList.size();j++){
				roleIdList.add(((Role)tempList.get(j)).getId());
			}
		}
		
		return roleIdList;
	}
	
	/**
	 * 获取用户所对应的权限ID集合
	 * @return
	 */
	public List getAuthoritysIDByUserId(String userid){
		List list = findAllRoleByUserId(userid);//获取到用户包含的角色(以及角色的子角色)(有重复值)
		
		String queryStr = "";
		List tempList = new ArrayList();
		for(int i=0;i<list.size();i++){//去除重复值
			if(!tempList.contains(list.get(i))){
				tempList.add(list.get(i));
				queryStr +=list.get(i)+",";
			}
		}
		queryStr = queryStr.substring(0, queryStr.length()-1);
		if(queryStr == null){
			return null;
		}
		return organizationDao.getAuthsID(queryStr);
	}
	//*****************************************************************
	/**
	 * 获取带用户的树
	 */
	public List treeAlgorithm(List treeDataList,String parentID){
    	List  list=null;
    	TreeData temData=null;
    	for (int i = 0; i < treeDataList.size(); i++) {
    		temData=(TreeData)treeDataList.get(i);
    		
			if (temData.getParentID().equals(parentID)) {
				List li = treeAlgorithm(treeDataList,temData.getId());
				if(li == null || li.size() == 0){
					li = organizationDao.getNeedUser(temData.getId());
				}else{
					li.addAll(organizationDao.getNeedUser(temData.getId()));
				}
				
				if(li !=null && li.size()>0){
					temData.setChildren(li);
				}else{
					temData.setChildren(null);
				}
			    
			    if (temData.getChildren()!=null) {
					temData.setState("closed");
				}
			    if (list==null) {
					list=new ArrayList ();
				}
				list.add(temData);
			}
		}
    	return list;
    }
	//********************************************************************
	/**
	 * 修改用户级别(用户id和级别id)
	 */
	public void updateUser_UserLevel(String userid,String id){
		organizationDao.updateUser_UserLevel(userid, id);
	}
	//*********************************************************************
	/**
	 * 删除用户角色
	 */
	public void deleteRoleUnderUser(String userid, String rids){
		String[] rid = rids.split(",");
		for(int i=0;i<rid.length;i++){
			String roleid = rid[i];
			organizationDao.deletRoleUnderUser(userid, roleid);
		}
	}
	
	/**
	 * 管理用户角色(添加用户角色)
	 */
	public void addRolesToUser(String userid, String rids){
		//int id = 0;
		RoleUser roleuser = null;
		List tempList = null;
		String [] str = rids.split(",");
		for(int i=0;i<str.length;i++){
			tempList = organizationDao.isExistRoleInUser(userid, str[i]);
			if(tempList != null && tempList.size() !=0){
				System.out.println("id为"+str[i]+"角色已存在");
			}else{
				//id = organizationDao.findMaxIDFromRole_User()+1;
				roleuser = new RoleUser();
				roleuser.setId(new Long(System.currentTimeMillis())+"");
				roleuser.setRid(new Long(str[i]));
				roleuser.setUserid(new Long(userid));
				organizationDao.add_User_Role(roleuser);
			}
		}
	}
	//***********************************************************
	/**
	 * 获取部门主管的集合(根据部门id)
	 */
	public List getOrgLead(String oid){
		List list = organizationDao.getOrganization(oid);
		TOrganization org = (TOrganization)list.get(0);
		String uids = org.getLead();
		if(uids != null && uids.length()!=0){
			return organizationDao.getOrgLeadList(uids);
		}else{
			return new ArrayList();
		}
	}
	
	/**
	 * 给部门添加主管(不一定存在主管)
	 */
	public void addLeadToOrg(String oid,String userids){
		List list = organizationDao.getOrganization(oid);
		TOrganization org = (TOrganization)list.get(0);
		String uids = org.getLead();
		if(uids != null && uids.length() !=0){
			Set set = new HashSet();
			List a = Arrays.asList(uids.split(","));
			List b = Arrays.asList(userids.split(","));
			for(int i=0;i<a.size();i++){
				set.add(a.get(i));
			}
			for(int i=0;i<b.size();i++){
				set.add(b.get(i));
			}
			uids = set.toString().substring(1, set.toString().length()-1);
			organizationDao.updateOrgLead(oid, uids);
		}else{
			organizationDao.updateOrgLead(oid, userids);
		}
	}
	/**
	 * 从部门移除主管(肯定存在主管)
	 */
	public void removeLeadFormOrg(String oid,String userids){
		List list = organizationDao.getOrganization(oid);
		TOrganization org = (TOrganization)list.get(0);
		String[] big = org.getLead().split(",");
		String[] small= userids.split(",");
		
		//把数组分别放入set中
		Set setA = new HashSet();
		Set setB = new HashSet();
		for (int i = 0; i < big.length; i++) {
            setA.add(big[i].trim());
        }
        for (int i = 0; i < small.length; i++) {
            setB.add(small[i].trim());
        }
        
        //求差
        Set setDifference = new HashSet();
        String s = "";
        Iterator iterA = setA.iterator();
        while (iterA.hasNext()) {
            s = (String)iterA.next();
            if(!setB.contains(s)) {
                setDifference.add(s);
            }
        }
        //转换为字符串
        userids = setDifference.toString().substring(1, setDifference.toString().length()-1);
		organizationDao.updateOrgLead(oid, userids);
	}
	//***********************************************************
	
	
	/*
	 * 根据部门复制部门id选取部门间的关系
	 * 
	 */
	public List getOrgOrg(String[] ooid) {
		// TODO Auto-generated method stub
		List list = organizationDao.getOrgOrg(ooid);
		return list; 
	}

	@Override
	public String checkUserIsUse(String userId) {
		// TODO Auto-generated method stub
		return organizationDao.checkUserIsUse(userId);
	}

}
