package com.zxt.compplatform.organization.action;

/**
 * @author bozch
 * 2011-03-15
 * 
 */
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.chinazxt.hb.encoder.util.Base64EncoderFlow;
import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.formengine.util.TreeUtil;
import com.zxt.compplatform.organization.entity.TOrgOrg;
import com.zxt.compplatform.organization.entity.TOrganization;
import com.zxt.compplatform.organization.entity.TUserTable;
import com.zxt.compplatform.organization.service.OrganizationService;

public class OrganizationAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * 组织机构业务注入
	 */
	private OrganizationService organizationService;
	/**
	 * 
	 */
	private List organizationList;
	private String userid;
	private String oid;
	private TUserTable usertable;
	private TOrganization org;
	private String orgname;
	private String orgupid;

	/*
	 * 
	 * 根据组织结构id 改变其角色状态 roleclassify = 1 角色选中 roleclassify = 0
	 * 角色未选中（数据库中该字段默认为0）
	 */
	public String changeClassify() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String allid = request.getParameter("ids");
		String[] ids = allid.split(",");
		String id = "";
		organizationService.updateAllOrgClssify();
		for (int i = 0; i < ids.length; i++) {
			id = ids[i];
			organizationService.updateOrgClssify(id);
		}
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 跳转到组织机构界面
	 * <p>
	 * 访问地址：organization/organization!toList.action
	 * </p>
	 * 
	 * @return
	 */
	public String toList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String oid = request.getParameter("oid");
		request.setAttribute("oid", oid);
		String rname = request.getParameter("rname");
		String isAdmin=request.getParameter("isAdmin");
		request.setAttribute("isAdmin",isAdmin);
		request.setAttribute("rname", rname);
		return "list";
	}

	/**
	 * 跳转到显示某个组织的用户列表
	 * <p>
	 * 访问地址：organization/organization!show.action
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	public String show() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/html;charset=UTF-8 ");
		String rname = request.getParameter("rname");
		request.setAttribute("rname", rname);
		
		String isAdmin=request.getParameter("isAdmin");
		request.setAttribute("isAdmin",isAdmin);
		
		return "show";
	}

	/**
	 * 跳转到部门添加页面
	 * 
	 * @return
	 */
	public String AddOrg() {
		return "toaddOrg";
	}

	/**
	 * 修改部门跳转
	 */
	public String toupdateOrg() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		org = organizationService.getOrganization(oid);
		return "toupdateOrg";
	}

	/**
	 * 跳转到级别修改页面
	 */
	public String tolevelManager() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		String userid = request.getParameter("uid");
		usertable = organizationService.findUserAllById(userid);
		return "tolevelManager";
	}

	/**
	 * 管理用户级别
	 */
	public String updateUser_UserLevel() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/x-json;charset=UTF-8 ");

		String levelID = (String) request.getParameter("levelID");
		String userid = request.getParameter("userid");
		organizationService.updateUser_UserLevel(userid, levelID);

		return null;
	}

	// *****************************************************************
	/**
	 * 添加角色到用户(参数:用户id和角色串)
	 */
	public String addRoleToUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/plain;charset=UTF-8 ");
		String rids = request.getParameter("rid");
		organizationService.addRolesToUser(userid, rids);
		return null;
	}

	/**
	 * 删除用户的角色(参数:用户id和角色串)
	 */
	public String deleteRoleUnderUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/x-json;charset=UTF-8");
		String rids = request.getParameter("rid");
		organizationService.deleteRoleUnderUser(userid, rids);
		return null;
	}

	// ******************************************************************
	// 获取所有的根据角色状态组织结构tree
	public String getAllOrganizationsByClassify() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/x-json;charset=UTF-8 ");
	//	String oid = request.getParameter("oid");
		organizationList = organizationService.get_jsonListByClassify();
		
		String isAdmin=request.getParameter("isAdmin");
		request.setAttribute("isAdmin",isAdmin);
		/**
		 * 设置已被选中的组织机构模板
		 */
		TreeData treeData = null;
		for (int i = 0; i < organizationList.size(); i++) {
			treeData = (TreeData) organizationList.get(i);
			if ("1".equals(treeData.getFlag())) {
				treeData.setChecked(true);
			}
		}
		
		List list = new ArrayList();
		
		if("1".equals(isAdmin)){
			//执行所有
			list = TreeUtil.treeAlgorithmForTreeData(organizationList, "1");
		}else{
			if(!"".equals(request.getSession().getAttribute("oid"))){
				String oid =(String)request.getSession().getAttribute("oid"); //request.getParameter("oid");
				if(oid!=null && !oid.equals(""))
				list = TreeUtil.treeAlgorithmForTreeData(organizationList, oid);
			}else{
				//如果SESSION超时，此处应该跳回登录页面
			}
		}
		// organizationList = organizationService.get_jsonListWithParam(oid);
		// List list = OrgTreeUtil.treeAlgorithm(organizationList, "1");
//		if (oid == null || "".equals(oid)) {
//			list = TreeUtil.treeAlgorithmForTreeData(organizationList, "1");
//		} else {
//			list = TreeUtil.treeAlgorithmForTreeData(organizationList, oid);
//		}
		// List list = TreeUtil.treeAlgorithmForTreeData(organizationList, "1");
		// 带用户的树形结构
		// List list = organizationService.treeAlgorithm(organizationList, "1");

		JSONArray jsonarray = JSONArray.fromObject(list);
		response.getWriter().write(jsonarray.toString());
		return null;
	}
	/**
	 * @GUOWEIXIN 额外添加
	 * @return
	 * @throws IOException
	 * 分为平台 和界面。 平台，如果isAdmin=1则为平台操作， 显示所有
	 * 如果isAdmin不为1，则得到当前登录的用户，进行权限查询(生成的界面，有用户登录的）
	 */
	// 获取所有的组织结构tree
	public String getAllOrganizationsBy() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/x-json;charset=UTF-8 ");
	//	String oid = request.getParameter("oid");
		organizationList = organizationService.get_jsonList();
		/**
		 * 设置已被选中的组织机构模板
		 */
		 TreeData treeData = null;
		for (int i = 0; i < organizationList.size(); i++) {
			treeData = (TreeData) organizationList.get(i);
			if ("1".equals(treeData.getFlag())) {
				((TreeData) organizationList.get(i)).setChecked(true);
			}
		}
		String isAdmin=request.getParameter("isAdmin");
		request.setAttribute("isAdmin",isAdmin);
		List list = new ArrayList();
		
		if("1".equals(isAdmin)){
			//执行所有
			list = TreeUtil.treeAlgorithmForTreeData(organizationList, "1");
		}else{
			if(!request.getSession().getAttribute("oid").equals("")){
				String oid =request.getSession().getAttribute("oid").toString(); //request.getParameter("oid");
				if(oid!=null && !oid.equals(""))
					list = TreeUtil.treeAlgorithmForTreeData(organizationList, oid);
			}else{
				//如果SESSION超时，此处应该跳回登录页面
			}
		}
		JSONArray jsonarray = JSONArray.fromObject(list);
		response.getWriter().write(jsonarray.toString());
		return null;
	}

	// 获取所有的组织结构tree
	public String getAllOrganizations() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/x-json;charset=UTF-8 ");
		String oid = request.getParameter("oid");
		organizationList = organizationService.get_jsonList();
		List list = new ArrayList();
		// organizationList = organizationService.get_jsonListWithParam(oid);
		// List list = OrgTreeUtil.treeAlgorithm(organizationList, "1");
		if (oid == null || "".equals(oid)) {
			list = TreeUtil.treeAlgorithmForTreeData(organizationList, "1");
		} else {
			list = TreeUtil.treeAlgorithmForTreeData(organizationList, oid);
		}
		// List list = TreeUtil.treeAlgorithmForTreeData(organizationList, "1");
		// 带用户的树形结构
		// List list = organizationService.treeAlgorithm(organizationList, "1");

		JSONArray jsonarray = JSONArray.fromObject(list);
		response.getWriter().write(jsonarray.toString());
		return null;
	}
	// 获取组织和用户组合树
	public String getTreeWithUserAndOrg() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/x-json;charset=UTF-8 ");

		organizationList = organizationService.get_jsonList();
		// 带用户的树形结构
		List list = organizationService.treeAlgorithm(organizationList, "1");

		JSONArray jsonarray = JSONArray.fromObject(list);
		response.getWriter().write(jsonarray.toString());
		return null;
	}

	// 加载特定组织包含的用户(pass)
	public String getUserListByGroupId() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");

		int page = 1;
		if (request.getParameter("page") != null
				&& !request.getParameter("page").equals("")) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		int rows = 0;
		if (request.getParameter("rows") != null
				&& !request.getParameter("rows").equals("")) {
			rows = Integer.parseInt(request.getParameter("rows"));
		}

		// 获取部门的字符串集合
		String oids = organizationService.getOidsByOid(oid);
		// 获取所需数据
		List userList = organizationService.findAllByPageAndOid(page, rows,
				oids);
		// 获取长度
		int count = organizationService.findUserTotalUnderOid(oids);

		Map map = new HashMap();
		if (userList != null) {
			map.put("rows", userList);
			map.put("total", new Integer(count));
		}

		String dataJson = JSONObject.fromObject(map).toString();
		response.getWriter().write(dataJson);
		return null;
	}

	// ******************************************************************
	/**
	 * 删除组织
	 * 
	 * @return
	 * @throws IOException
	 */
	public String deleteOrg() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/plain;charset=UTF-8 ");
		String result = organizationService.deleteOrganization(oid);
		response.getWriter().write(result);
		return null;
	}

	/**
	 * 修改组织
	 * 
	 * @throws IOException
	 */
	public String updateOrg() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/plain;charset=UTF-8 ");

		String flag = "fail";
		try {
			organizationService.updateOrganization(org);
			flag = "success";
		} catch (Exception e) {
		}
		response.getWriter().write(flag);
		return null;
	}

	// *******************************************************************

	/**
	 * 添加部门
	 * 
	 * @return
	 */
	public String saveAddOrg() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		String repliorgnos = request.getParameter("repliorgno");// 获取复制部门所有id
		String oid = request.getParameter("oid");
		String flag = "fail";
		Map map = new HashMap();
		// 1.判断部门名称是否已存在
		try {
			int i = organizationService.isExistOrg(null, oid, org.getOname());
			if (i != -1) {
				flag = "exist";
			} else {
				// 将新部门添加到部门表中
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// 设置日期格式
				String endupdatetime = df.format(new Date());// new
																// Date()为获取当前系统时间
				org.setEndupdatetime(endupdatetime);
				// 获取最大的部门id
				Integer val = new Integer(organizationService.maxvalue());
				org.setOid(new Long(val.longValue() + 1L));
				organizationService.addOrganization(org, oid);
				TOrgOrg firstoo = new TOrgOrg();
				firstoo.setId(new Long(System.currentTimeMillis()) + "");
				firstoo.setUpid(Long.parseLong(oid));
				firstoo.setDownid(new Long(val.longValue() + 1L));
				organizationService.addOrgOrg(firstoo);
				boolean panduangen = true;
				// 添加到部门表中
				if (repliorgnos != null && !repliorgnos.equals("")) {
					// 将复制部门首先添加到数据库中
					String repliorgno[] = repliorgnos.split(",");
					for (int j = 0; j < repliorgno.length; j++) {
						String oldoid = repliorgno[j];// 循环获取复制部门id；
						TOrganization torigzation = organizationService
								.getOrganization(oldoid);
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");// 设置日期格式
						String endupdatetimefz = sdf.format(new Date());// new
																		// Date()为获取当前系统时间
						torigzation.setEndupdatetime(endupdatetimefz);
						// 获取最大的部门id
						Integer valfz = new Integer(organizationService
								.maxvalue());
						torigzation.setOid(new Long(valfz.longValue() + 1L));
						organizationService
								.addOrganization(torigzation, oldoid);
						map.put(oldoid, new Long(valfz.longValue() + 1L));
					}
					// 提取到选择的需要复制的根部门id号集合
					List list = organizationService.getOrgOrg(repliorgno);
					Iterator it = list.iterator();
					while (it.hasNext()) {
						// 替换关系操作
						TOrgOrg torgorg = (TOrgOrg) it.next();
						String ooupid = Long.toString(torgorg.getUpid());// 取出list中的TOrgOrg对象
																			// 对应的upid
						String oodownid = Long.toString(torgorg.getDownid());// 取出list中的TOrgOrg对象
																				// 对应的downid
						Long newooupid = (Long) map.get(ooupid);// 提取map集合中转换后的upid值
						Long newoodownid = (Long) map.get(oodownid);// 提取map集合中转换后的downid值
						if (newoodownid == null) {
							continue;
						}
						TOrgOrg torgorgnew = new TOrgOrg();
						torgorgnew.setId(new Long(System.currentTimeMillis())
								+ "");
						if (repliorgno[0].equals(oodownid)) { // 如果提取的第一个节点就是根节点的话则它不会再downid节点中出现
							panduangen = false;
						}
						if (newooupid == null) {// 如果提取节点是一个子节点的话那么对应的第一个提取map集合中转换后的upid值一定为null
												// 这时候将我们
							torgorgnew.setUpid(new Long(val.longValue() + 1L));
							torgorgnew.setDownid(newoodownid);
						} else {// 除了第一个提取的节点外其他节点均正常
							torgorgnew.setUpid(newooupid);
							torgorgnew.setDownid(newoodownid);
						}
						organizationService.addOrgOrg(torgorgnew);
					}
					if (panduangen) {
						TOrgOrg torgorgnew = new TOrgOrg();
						torgorgnew.setId(new Long(System.currentTimeMillis())
								+ "");
						torgorgnew.setUpid(new Long(val.longValue() + 1L));
						torgorgnew.setDownid((Long) map.get(repliorgno[0]));
						organizationService.addOrgOrg(torgorgnew);
					}
				}

				flag = "success";
			}

		} catch (Exception e) {
		}

		try {
			response.getWriter().write(flag);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 部门名称验证
	 * <p>
	 * 验证部门是不是存在，分为两中情况： 1、添加部门时：根据用户填入的部门名称进行验证 2、修改部门时：根据用户填入的部门名称和其id进行验证
	 * 访问地址：organization/organization!isOrgExist.action
	 * </p>
	 * 
	 * @return
	 */
	public String isOrgExist() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");

		String oupid = request.getParameter("oupid");// 所属上级部门的id
		String oid = request.getParameter("oid");// 修改时，部门的id
		String orgName = request.getParameter("name");// 获取文本框中输入的值

		String flag = "unexist";

		// 1.判断部门名称是否已存在
		try {
			int i = organizationService.isExistOrg(oid, oupid, orgName);
			if (i != -1) {
				flag = "exist";
			}
		} catch (Exception e) {
		}
		try {
			response.getWriter().write(flag);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 添加部门时部门主管的页面跳转
	 */
	public String toOrgLead() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		oid = request.getParameter("oid");
		return "toorglead";
	}

	//
	public String orgUnderLeadAdd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		oid = request.getParameter("oid");
		return "toOrgLeadAll";
	}

	/**
	 * 组织用户列表内的角色管理
	 */
	public String roleManager() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		String uid = request.getParameter("userid");
		request.setAttribute("userid", uid);
		String rname = request.getParameter("rname");
		request.setAttribute("rname", rname);
		String isAdmin=request.getParameter("isAdmin");
		request.setAttribute("isAdmin",isAdmin);
		return "toorgrolemanager";
	}

	// 组织的角色管理中添加的跳转页面
	public String orgUnderRoleAdd() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		String uid = request.getParameter("userid");
		request.setAttribute("userid", uid);
		String rname = request.getParameter("rname");
		request.setAttribute("rname", rname);
		
		String isAdmin=request.getParameter("isAdmin");
		request.setAttribute("isAdmin",isAdmin);
		
		return "toorgunderroleadd";
	}

	/**
	 * 组织内用户级别的管理
	 */
	public String levelManager() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		String uid = request.getParameter("uid");
		request.setAttribute("uid", uid);
		return "toorglevelmanager";
	}

	// ******************************************************************
	/**
	 * 添加用户
	 */
	public String addUser() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/plain;charset=UTF-8 ");
		String oid = ServletActionContext.getRequest().getParameter(
				"organizationId");
		String result = organizationService.addUser(usertable, oid);
		response.getWriter().write(result);
		return null;
	}

	/**
	 * 验证用户名是不是存在
	 * 
	 * <p>
	 * 访问地址：organization/organization!isUserNameExist.action
	 * </p>
	 */
	public String isUserNameExist() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/plain;charset=UTF-8 ");

		String flag = "unexist";

		String username = request.getParameter("name");

		boolean result = false;

		try {
			result = organizationService.isUserNameExist(username, userid);
		} catch (Exception e) {

		}

		if (result) {
			flag = "exist";
		}

		response.getWriter().write(flag);
		return null;
	}

	/**
	 * 修改用户
	 * 
	 * @throws IOException
	 */
	public String updateOrgUser() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		String oid = ServletActionContext.getRequest().getParameter(
				"organizationId");
		String flag = "fail";
		try {
			organizationService.updateUser(usertable);
			organizationService.updateUserOragination(usertable.getUserid()
					.toString(), oid);
			flag = "success";
		} catch (Exception e) {
		}
		response.getWriter().write(flag);
		return null;
	}

	public void checkOldPwd() throws IOException {
		String flag = "fail";
		HttpServletResponse response = ServletActionContext.getResponse();
		String pwd = ServletActionContext.getRequest().getParameter("pwd");// 输入的密码
		String pwdOld = ServletActionContext.getRequest()
				.getParameter("pwdOld");// 加密后的原密码
		byte[] by = pwd.getBytes();
		pwd = new Base64EncoderFlow().encodeBuffer(by);
		if (pwdOld.equals(pwd)) {
			flag = "success";
		}
		response.setContentType("text/plain;charset=UTF-8 ");
		response.getWriter().write(flag);
	}

	/**
	 * 删除用户
	 */
	public String deleteOrgUser() {
		organizationService.deleteUserById(userid);
		return null;
	}
	
	/**
	 * 删除用户
	 */
	public String checkUserIsUse() {
		
		String reslut=organizationService.checkUserIsUse(userid);
		
		try {
			ServletActionContext.getResponse().getWriter().write(reslut);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	// *******************************************************************
	/**
	 * 跳转到用户添加页面
	 */
	public String AddOrgUser() {
		return "toorguseradd";
	}

	/**
	 * 跳转到用户修改页面
	 */
	public String toupdateOrgUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/plain;charset=UTF-8 ");
		String userid = request.getParameter("userId");
		String organizationID = request.getParameter("oid");
		if ((organizationID == null) || ("".equals(organizationID))) {
			organizationID = "0";
		}
		request.setAttribute("organizationID", organizationID);
		usertable = organizationService.findUserAllById(userid);
		String oldPwd = request.getParameter("oldPwd");
		if (oldPwd == null || oldPwd.equals("")) {
			return "toorguserupdate";
		} else {
			return "toorguserupdate2";
		}
	}

	/**
	 * 跳转到用户修改页面(LIMS)
	 */
	public String toupdateOrgUserLimsBh() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/plain;charset=UTF-8 ");
		String userid = request.getParameter("userId");
		usertable = organizationService.findUserAllById(userid);
		return "toorguserupdatelimsbh";
	}

	// ******************************************************************
	/**
	 * 部门已有主管显示(传入参数部门id)
	 */
	public String userListForOrgLead() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();

		response.setContentType("text/plain;charset=UTF-8 ");
		List list = organizationService.getOrgLead(oid);

		int count = 0;
		if (list != null) {
			count = list.size();
		}
		Map map = new HashMap();
		if (list != null) {
			map.put("rows", list);
			map.put("total", new Integer(count));
		}

		String dataJson = JSONObject.fromObject(map).toString();
		response.getWriter().write(dataJson);
		return null;
	}

	/**
	 * 给组织添加主管
	 */
	public String addLeadToOrg() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");

		String userids = request.getParameter("userid");
		oid = request.getParameter("oid");
		organizationService.addLeadToOrg(oid, userids);

		return null;
	}

	/**
	 * 从组织移除主管
	 */
	public String removeLeadFromOrg() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");

		String userids = request.getParameter("userid");
		oid = request.getParameter("oid");
		organizationService.removeLeadFormOrg(oid, userids);
		return null;
	}

	/**
	 * 获取所有部门列表
	 */
	public String getAllOrgList() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");

		List list = organizationService.get_allOrginationList();
		// String str = "" ;
		// for (int i = 0; i < list.size(); i++) {
		// TOrganization to = (TOrganization)list.get(i);
		// if(i == 0){
		// str += "[{oid:'-1',oname:'请选择部门'},";
		// }else if(i != list.size()-1){
		// str += "{oid:'"+to.getOid()+"',oname:'"+to.getOname()+"'}," ;
		// }else{
		// str += "{oid:'"+to.getOid()+"',oname:'"+to.getOname()+"'}]" ;
		// }
		// }
		JSONArray jsonarray = JSONArray.fromObject(list);
		String jsonString = jsonarray.toString();
		String a = jsonString.replace("[{",
				"[{\"oid\":-1,\"oname\" :\"请选择\",\"selected\":true},{");
		response.getWriter().write(a);
		return null;
	}

	/**
	 * 根据用户ID获取用户名
	 * 
	 * @param org
	 */
	public String getNameByID() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/plain;charset=UTF-8 ");

		String userId = request.getParameter("userID");
		TUserTable user = organizationService.findUserAllById(userId);
		String userName = user.getUname();
		response.getWriter().write(userName);
		return null;
	}

	/**
	 * 查询所有用户
	 */
	public String getAllUser() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/plain;charset=UTF-8 ");

		List list = organizationService.getAllUser();

		JSONArray jsonarray = JSONArray.fromObject(list);
		String jsonString = jsonarray.toString();
		response.getWriter().write(jsonString);
		return null;
	}

	public void setOrg(TOrganization org) {
		this.org = org;
	}

	public TUserTable getUsertable() {
		return usertable;
	}

	public void setUsertable(TUserTable usertable) {
		this.usertable = usertable;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public List getOrganizationList() {
		return organizationList;
	}

	public void setOrganizationList(List organizationList) {
		this.organizationList = organizationList;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public TOrganization getOrg() {
		return org;
	}

	public String getOrgupid() {
		return orgupid;
	}

	public void setOrgupid(String orgupid) {
		this.orgupid = orgupid;
	}

}
