package com.zxt.compplatform.authority.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.authority.entity.OrgRole;
import com.zxt.compplatform.authority.entity.Role;
import com.zxt.compplatform.authority.service.RoleService;
import com.zxt.compplatform.organization.entity.TOrganization;
import com.zxt.compplatform.organization.service.OrganizationService;
import com.zxt.compplatform.organization.service.OrganizationServiceImpl;
import com.zxt.framework.common.util.RandomGUID;

/**
 * 角色操作action
 * @author 007
 */
public class RoleAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * 角色操作业务逻辑实体
	 */
	private RoleService roleService;
	
	
	private OrganizationService organizationService;
	/**
	 * 角色实体
	 */
	private Role role;
	/**
	 * 角色id
	 */
	private String rid;
	/**
	 * 用户id
	 */
	private String userid;

	/**
	 * 跳转到列表页
	 * @return
	 */
	public String toList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		/*flag用于标识 是否是 平台操作，还是平台做出的页面操作
		 * flag=1，平台操作， 否则，页面操作（主要目的是为了在页面上可以根据当前用户得到其下级节点,而非全部节点
		 */
		String isAdmin=request.getParameter("isAdmin");
		String rname = request.getParameter("rname");
		request.setAttribute("isAdmin",isAdmin);
		request.setAttribute("rname", rname);
		return "toList";
	}
	
	/**
	 * 分页展示角色列表
	 * @return
	 * @throws IOException
	 */
	public String listRoles() throws IOException{
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8");
		String isall = req.getParameter("isall");
		String userid=req.getParameter("userid");
		
		if("1".equals(isall)){
			int page = 1;
			if(req.getParameter("page") != null && !req.getParameter("page").equals("")){
				page = Integer.parseInt(req.getParameter("page"));
			}
			int rows = 0;
			if(req.getParameter("rows") != null && !req.getParameter("rows").equals("")){
				rows = Integer.parseInt(req.getParameter("rows"));
			}
			
			int totalRows = 0;
			List roleList = null;
			
			if (userid==null || "".equals(userid) ) {
				totalRows=roleService.findRoleAllNum();
				roleList = roleService.findRoleAllList(rows, page);
			}else {
				totalRows=roleService.findRoleAllNum(userid);
				roleList = roleService.findRoleAllList(rows, page,userid);
			}
			
			
			Map map = new HashMap();
			if(roleList == null){
				roleList = new ArrayList();
			}
			map.put("rows", roleList);
			map.put("total", new Integer(totalRows));
			String roleJson = JSONObject.fromObject(map).toString();
			res.getWriter().write(roleJson);
			
		}else{
			String orgid = req.getParameter("orgid");
			int page = 1;
			if(req.getParameter("page") != null && !req.getParameter("page").equals("")){
				page = Integer.parseInt(req.getParameter("page"));
			}
			int rows = 0;
			if(req.getParameter("rows") != null && !req.getParameter("rows").equals("")){
				rows = Integer.parseInt(req.getParameter("rows"));
			}
			int totalRows = 0;
			List roleList = new ArrayList();
			
			if("".equals(orgid)||orgid==null){
				if (userid==null || "".equals(userid) ) {
					totalRows=roleService.findRoleAllNum();
					roleList = roleService.findRoleAllList(rows, page);
				}else {
					totalRows=roleService.findRoleAllNum(userid);
					roleList = roleService.findRoleAllList(rows, page,userid);
				}
			}else{
				if (userid==null || "".equals(userid) ) {
					totalRows = roleService.findTotalRowsWithParam(orgid);
					roleList= roleService.findRolePagWithParam(page,rows,orgid);
				}else {
					totalRows = roleService.findTotalRowsWithParam(orgid,userid);
					roleList= roleService.findRolePagWithParam(page,rows,orgid,userid);
				}
			}
			
			Map map = new HashMap();
			if(roleList == null){
				roleList = new ArrayList();
			}
			map.put("rows", roleList);
			map.put("total", new Integer(totalRows));
		
			String roleJson = JSONObject.fromObject(map).toString();
			res.getWriter().write(roleJson);
			
		}
		
		return null;
	}
	
	/**
	 * 角色添加
	 * @return
	 * @throws IOException
	 */
	public String roleAdd() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/plain;charset=UTF-8 ");
		String orgroleid = request.getParameter("orgroleid");
		String orgrolename = request.getParameter("orgrolename");
		OrgRole or = new OrgRole();
		or.setPkid(RandomGUID.geneGuid());
		or.setOrgid(orgroleid);
		String result = roleService.addRole(role,or);
		response.getWriter().write(result);
		return null ;
	}
	
	/**
	 * 角色删除
	 * @return
	 * @throws IOException
	 */
	public String roleDelete() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		String id = request.getParameter("rid");
		String result = roleService.deleteRole(id);
		response.getWriter().write(result);
		return null;
	}
	
	/**
	 * 跳转到添加页面
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String toroleAdd() throws UnsupportedEncodingException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String orgid = java.net.URLDecoder.decode(request.getParameter("orgid"),"utf-8");
		String orgname = java.net.URLDecoder.decode(request.getParameter("orgname"),"utf-8");
		String isAdmin=request.getParameter("isAdmin");
		request.setAttribute("isAdmin", isAdmin);
		request.setAttribute("orgroleid", orgid);
		request.setAttribute("orgrolename", orgname);
		return "toroleadd" ;
	}
	
	/**
	 * 跳转到角色复制页面
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String toroleCopy() throws UnsupportedEncodingException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String orgid = java.net.URLDecoder.decode(request.getParameter("orgid"),"utf-8");
		String orgname = java.net.URLDecoder.decode(request.getParameter("orgname"),"utf-8");
		request.setAttribute("copyorgroleid", orgid);
		request.setAttribute("copyorgrolename", orgname);
		return "torolecopy" ;
	}
	/**
	 * 判断复制的角色名称是否存在
	 * @throws IOException
	 */
	public void roleNameExist() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		String roleName = ServletActionContext.getRequest().getParameter("roleName");
		//调用service判断角色名是否存在
		String result = roleService.RoleIsExist(roleName);
		
		response.setContentType("text/plain;charset=UTF-8 ");
		response.getWriter().write(result);
	}
	/**
	 * 跳转到角色复制页面
	 * @throws IOException
	 */
	public void roleCopy() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/plain;charset=UTF-8 ");
		String orgroleid = request.getParameter("copyorgroleid");
		String orgrolename = request.getParameter("copyorgrolename");
		OrgRole or = new OrgRole();
		or.setPkid(RandomGUID.geneGuid());
		or.setOrgid(orgroleid);
		String roleCopyNames = request.getParameter("roleCopyNames");
		String result = roleService.copyRole(role,roleCopyNames,or);
		response.getWriter().write(result);
	}
	
	/**
	 * 跳转到修改页面
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String  toroleUpdate() throws UnsupportedEncodingException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		
		String id = request.getParameter("rid");//角色id
		TOrganization tor =  roleService.findOrgByRoId(id);
		request.setAttribute("updateorgroleid", Long.toString(tor.getOid()));
		request.setAttribute("updateorgrolename", tor.getOname());
		role = roleService.findRoleById(id);
		return  "toroleupdate" ;
	}
	
	/**
	 * 跳转到用户管理页面 x
	 * @return
	 */
	public String toroleUserManger(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		rid = request.getParameter("rid");
		String isAdmin=request.getParameter("isAdmin");
		request.setAttribute("isAdmin",isAdmin);
		return "toroleusermanager";
	}
	
	/**
	 * 在角色的管理用户下跳转全部用户的页面
	 * @return
	 */
	public String toRoleUnderUserAll(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		rid = request.getParameter("rid");
		String isAdmin=request.getParameter("isAdmin");
		request.setAttribute("isAdmin",isAdmin);
		return "toroleunderuserall" ;
	}
	
	/**
	 * 修改角色
	 * @return
	 * @throws IOException
	 */
	public String updateRole()throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		String orgroleid = request.getParameter("updateorgroleid");
		String orgrolename = request.getParameter("updateorgrolename");
		String oldorgroleid = request.getParameter("oldupdateorgroleid");
		if(oldorgroleid.equals("0")){
			OrgRole orgrole = new OrgRole();
			orgrole.setPkid(RandomGUID.geneGuid());
			orgrole.setOrgid(orgroleid);
			orgrole.setRoleid(Long.toString(role.getId()));
			roleService.addOrgRole(orgrole);
			response.getWriter().write("success");
		}else{
			OrgRole oldor = new OrgRole();
			oldor.setOrgid(oldorgroleid);
			oldor.setRoleid(Long.toString(role.getId()));
			OrgRole or = new OrgRole();
			or.setOrgid(orgroleid);
			or.setRoleid(Long.toString(role.getId()));
			response.setContentType("text/plain;charset=UTF-8 ");
			String result = roleService.updateRole(role,oldor,or);
			response.getWriter().write(result);
		}
		return null;
	}
	
	//**********************************************************************
	/**
	 * 当前角色下的用户列表
	 * @return
	 * @throws IOException
	 */
	public String getUserListUnder() throws IOException{
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8");
		int page = 1;
		if(req.getParameter("page") != null && !req.getParameter("page").equals("")){
			page = Integer.parseInt(req.getParameter("page"));
		}
		int rows = 0;
		if(req.getParameter("rows") != null && !req.getParameter("rows").equals("")){
			rows = Integer.parseInt(req.getParameter("rows"));
		}
		List roleList = roleService.ListUserUnderRole(page, rows, rid);
		int count = roleService.findTotalRowsUnderRole(rid);
		
		Map map = new HashMap();
		if(roleList == null){
			roleList = new ArrayList();
		}
		map.put("rows", roleList);
		map.put("total", new Integer(count));
	
		String roleJson = JSONObject.fromObject(map).toString();
		res.getWriter().write(roleJson);
		return null;
	}
	
	
	/**
	 * 删除角色下的用户(参数:角色id和用户串)
	 * @return
	 */
	public String deleteUserUnderRole(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/x-json;charset=UTF-8");
		String userids = request.getParameter("userid");
		roleService.deleteUserUnderRole(rid, userids);
		return null;
	}
	
	/**
	 * 给角色添加用户(参数:角色id和用户串)
	 * @return
	 */
	public String addUserToRole(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("text/plain;charset=UTF-8 ");
		String userid = request.getParameter("userid");
		roleService.addUsersToRole(rid, userid);
		return null;
	}
	//*******************************************************************
	/**
	 * 给角色添加用户(参数:角色id和用户串)
	 * @return
	 * @throws IOException
	 */
	public String userListForCommon() throws IOException{
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8");	

		int page = 1;
		if(req.getParameter("page") != null && !req.getParameter("page").equals("")){
			page = Integer.parseInt(req.getParameter("page"));
		}
		int rows = 0;
		if(req.getParameter("rows") != null && !req.getParameter("rows").equals("")){
			rows = Integer.parseInt(req.getParameter("rows"));
		}
		String oid = "";
		String oids="";
		if(req.getParameter("org") != null && !req.getParameter("org").equals("")){
			oid = req.getParameter("org");
			 oids = organizationService.getOidsByOid(oid);
			
		}
		String username = "";
		if(req.getParameter("username") != null && !req.getParameter("username").equals("")){
			username = req.getParameter("username");
		}
		String uname = "";
		if(req.getParameter("uname") != null && !req.getParameter("uname").equals("")){
			uname = req.getParameter("uname");
		}
		
		List roleList = roleService.selectAllUserForCommon(page, rows, oids, username, uname);
		int count = roleService.userTotalCount(oids, username, uname);

		
		Map map = new HashMap();
		if(roleList == null){
			roleList = new ArrayList();
		}
		map.put("rows", roleList);
		map.put("total", new Integer(count));
	
		String roleJson = JSONObject.fromObject(map).toString();
		res.getWriter().write(roleJson);
		return null;
	}
	
	//*******************************************************************
	/**
	 * 用户下角色列表
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String getRoleListUnderUser() throws IOException{
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8");
		List rolelist = roleService.getRoleListUnderUser(userid);
		Map map = new HashMap();
		if(rolelist == null){
			rolelist = new ArrayList();
		}
		map.put("rows", rolelist);
		map.put("total", new Integer(rolelist.size()));
	
		String roleJson = JSONObject.fromObject(map).toString();
		res.getWriter().write(roleJson);
		return null;
	}
	
	/**
	 * 判断用户是否销售经理
	 * @return
	 * @throws IOException
	 */
	public String getRolesUserHave() throws IOException{
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		res.setContentType("text/x-json;charset=UTF-8");
		String userID = req.getParameter("userID");
		List rolelist = roleService.getRoleListUnderUser(userID);
		int count = 0;
		
		if(rolelist == null){
			rolelist = new ArrayList();
		}
		String rname = "";
		for(int i=0;i<rolelist.size();i++){
			rname = ((Role)rolelist.get(i)).getName();
			if("xiaoshoujingli".equals(rname)){
				count ++;
			}
		}
		
		if(count !=0){
			req.getSession().setAttribute("currrole", "yes");
			//res.getWriter().write("yes");
		}else{
			req.getSession().setAttribute("currrole", "no");
		}
		return null;
	}
	
	/**
	 * 获取所有的角色的名称
	 * 访问地址：authority/role!getAllRoles.action
	 * @return
	 * @throws IOException
	 */
	public String getAllRoles() throws IOException{
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/plain;charset=UTF-8");
		
		List roleList = roleService.findAll();
		Map map = new HashMap();
		if(roleList == null){
			roleList = new ArrayList();
		}
		/*
		 * 1、返回datagrid数据
		 */
		map.put("rows", roleList);
		map.put("total", new Integer(roleList.size()));
	
		String roleJson = JSONObject.fromObject(map).toString();
		/*
		 * 2、返回tree数据 String roleJson = JSONArray.fromArray(roleList).toString();
		 */
		res.getWriter().write(roleJson);
		return null;
	}
	//*******************************************************************
	/**
	 * 管理权限
	 * @return
	 */
	public String rolePerManager(){
		return "torolepermanager";
	}
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	
}
