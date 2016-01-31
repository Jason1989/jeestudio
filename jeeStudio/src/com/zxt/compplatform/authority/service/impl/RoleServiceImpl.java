package com.zxt.compplatform.authority.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.zxt.compplatform.authority.dao.RARelationShipDao;
import com.zxt.compplatform.authority.dao.RoleDao;
import com.zxt.compplatform.authority.entity.OrgRole;
import com.zxt.compplatform.authority.entity.RARelationShip;
import com.zxt.compplatform.authority.entity.Role;
import com.zxt.compplatform.authority.entity.RoleUser;
import com.zxt.compplatform.authority.service.RoleService;
import com.zxt.compplatform.organization.entity.TOrganization;

/**
 * 角色操作业务逻辑接口实现
 * 
 * @author 007
 */
public class RoleServiceImpl implements RoleService {
	/**
	 * 角色持久化dao
	 */
	private RoleDao roleDao;
	/**
	 * 角色资源持久化dao
	 */
	private RARelationShipDao raRelationShipDao;

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setRaRelationShipDao(RARelationShipDao raRelationShipDao) {
		this.raRelationShipDao = raRelationShipDao;
	}

	/*
	 * 查询所有角色与部门无关带分页
	 * 
	 */
	public List findRoleAllList(int rows, int page) {

		return roleDao.findRoleAllList(rows, page);

	}

	/*
	 * 查询所有角色总数与部门无关
	 * 
	 */
	public int findRoleAllNum() {
		return roleDao.findRoleAllNum();
	}

	/**
	 * 查找所有角色
	 */
	public List findAll() {
		return roleDao.findAllRole();
	}

	/**
	 * 角色分页
	 */
	public List findRolePag(int page, int rows) {
		return roleDao.roleListPag(page, rows);
	}

	/**
	 * 角色分页 带参数
	 */
	public List findRolePagWithParam(int page, int rows, String rname) {
		return roleDao.roleListPagWithParam(page, rows, rname);
	}

	// *********************************************************
	/**
	 * 用户列表(特定角色下的用户列表)
	 */
	public List ListUserUnderRole(int page, int rows, String rid) {
		return roleDao.ListUserUnderRole(page, rows, rid);
	}

	/**
	 * 查询特定角色下的用户条数
	 */
	public int findTotalRowsUnderRole(String rid) {
		return roleDao.findTotalRowsUnderRole(rid);
	}

	/**
	 * 查询角色功能
	 */
	public String findRFunction(String rname) {
		return roleDao.findRFunction(rname);
	}

	// *********************************************************
	/**
	 * 查询角色总条数
	 */
	public int findTotalRows() {
		return roleDao.findTotalRows();
	}

	/**
	 * 查找角色的总条数 带参数
	 * 
	 * @return
	 */
	public int findTotalRowsWithParam(String rname) {
		return roleDao.findTotalRowsWithParam(rname);
	}

	/**
	 * 添加角色(角色名不能重复 角色id为查询最大值加1)
	 */
	public String addRole(Role role, OrgRole or) {
		List existList = roleDao.isExist(role.getName());// 判断角色名是否存在
		if (existList == null || existList.size() == 0) {
			int id = roleDao.findMaxId();
			Long needid = new Long(Long.parseLong(id + 1 + ""));
			role.setId(needid);
			or.setRoleid(Long.toString(needid));
			roleDao.addRole(role, or);
			return "success";
		}
		return "fail";
	}

	/*
	 * 添加组织机构与角色关系
	 */
	public void addOrgRole(OrgRole or) {
		roleDao.addOrgRole(or);
	}

	/**
	 * 判断角色名称是否存在
	 */
	public String RoleIsExist(String roleName) {
		List existList = roleDao.isExist(roleName);// 判断角色名是否存在
		if (existList == null || existList.size() == 0) {
			return "success";
		}
		return "fail";
	}

	/**
	 * 复制角色
	 */
	public String copyRole(Role role, String roleCopyNames, OrgRole or) {
		/* 调用dao,把角色保存进T_ROLE表里 */
		try {
			int id = roleDao.findMaxId();
			Long needid = new Long(Long.parseLong(id + 1 + ""));
			role.setId(needid);
			or.setRoleid(Long.toString(needid));
			roleDao.addRole(role, or);
			/* 调用dao,把角色和权限保存进T_ROLE_RESC表里 */
			List functionList = raRelationShipDao.listrights(roleCopyNames);
			RARelationShip relationShip = new RARelationShip();
			for (int i = 0; i < functionList.size(); i++) {
				relationShip.setRoleId(needid);
				relationShip.setRescId(functionList.get(i).toString());
				raRelationShipDao.addRelationShip(relationShip);
			}
			return "success";
		} catch (Exception e) {
			return "fail";
		}
	}

	/**
	 * 删除角色(删除当前角色,如有用户不能删除)
	 */
	public String deleteRole(String id) {
		List existList = roleDao.isExistUser(id);
		if (existList == null || existList.size() == 0) {// 判断该角色下是否有用户
			roleDao.deleteRole(id);
			return "success";
		} else {
			// --------------------------------------------------------------------------------------------------
			/*
			 * List list = roleDao.findUserByRoleId(id); Map map = new
			 * HashMap(); map.put("rows", list); map.put("total", list.size());
			 * String roleJson = JSONObject.fromObject(map).toString(); return
			 * roleJson;
			 */
			return "fail";
		}
	}

	/**
	 * 根据角色id查出所在部门机构的信息
	 */
	public TOrganization findOrgByRoId(String id) {

		return roleDao.findOrgByRoId(id);
	}

	/**
	 * 修改角色
	 */
	public String updateRole(Role role, OrgRole oldor, OrgRole or) {
		int count = roleDao.isExist(role);
		if (count == -1) {
			roleDao.updateRole(role, oldor, or);
			return "success";
		}
		return "fail";
	}

	/**
	 * 根据角色id查询特定角色
	 */
	public Role findRoleById(String id) {
		return roleDao.findRoleById(id);
	}

	// *******************************************************
	/**
	 * 给角色添加用户(如果重复不做提醒 暂时在后台不重复插入)
	 */
	public void addUsersToRole(String rid, String usersid) {
		RoleUser roleuser = null;
		List tempList = null;
		String[] str = usersid.split(",");
		for (int i = 0; i < str.length; i++) {
			tempList = roleDao.isExistUserInRoleUser(rid, str[i]);
			if (tempList != null && tempList.size() != 0) {
				// System.out.println("id为"+str[i]+"用户已存在");
			} else {
				roleuser = new RoleUser();
				roleuser.setId(new Long(System.currentTimeMillis()) + "");
				roleuser.setRid(new Long(rid));
				roleuser.setUserid(new Long(str[i]));
				roleDao.addUserToRole(roleuser);
			}
		}
	}

	/**
	 * 删除角色下的特定用户
	 */
	public void deleteUserUnderRole(String rid, String userids) {
		String[] userid = userids.split(",");
		for (int i = 0; i < userid.length; i++) {
			String uid = userid[i];
			roleDao.deletUserUnderRole(rid, uid);
		}
	}

	// **********************************************************
	/**
	 * 全部用户列表(通用方法)
	 */
	public List selectAllUserForCommon(int page, int rows, String oid,
			String username, String uname) {
		return roleDao.getAllUserForCommon(page, rows, oid, username, uname);
	}

	/**
	 * 用户数量
	 */
	public int userTotalCount(String oid, String username, String uname) {
		return roleDao.userTotalCount(oid, username, uname);
	}

	// *********************************************************
	/*
	 * (non-Javadoc)
	 * 
	 * 用户下的角色列表
	 * 
	 * @see com.zxt.compplatform.authority.service.RoleService#getRoleListUnderUser(java.lang.String)
	 */
	public List getRoleListUnderUser(String userid) {
		return roleDao.getRoleListUnderUser(userid);
	}

	/**
	 * 查询当前用户未添加的角色
	 */
	@Override
	public int findRoleAllNum(String userId) {
		// TODO Auto-generated method stub
		return roleDao.findRoleAllNum(userId);
	}

	@Override
	public List findRoleAllList(int rows, int page, String userId) {
		// TODO Auto-generated method stub
		return roleDao.findRoleAllList(rows, page, userId);
	}

	@Override
	public List findRolePagWithParam(int page, int rows, String rname,
			String userId) {
		// TODO Auto-generated method stub
		
		return roleDao.roleListPagWithParam(page, rows, rname, userId);
	}

	@Override
	public int findTotalRowsWithParam(String rname, String userId) {
		// TODO Auto-generated method stub
		
		return roleDao.findTotalRowsWithParam(rname, userId);
	}
}
