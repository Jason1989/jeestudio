package com.zxt.compplatform.formengine.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.util.AssertionHolder;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.authority.service.AuthorityFrameService;
import com.zxt.compplatform.authority.service.RARelationShipService;
import com.zxt.compplatform.authority.service.RoleService;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.entity.database.SystemLog;
import com.zxt.compplatform.formengine.entity.view.SystemMenu;
import com.zxt.compplatform.formengine.entity.view.TabMenu;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.formengine.entity.view.UserDeptrel;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.compplatform.formengine.upload.UploadInfo;
import com.zxt.compplatform.formengine.util.EngineTools;
import com.zxt.compplatform.formengine.util.StrTools;
import com.zxt.compplatform.formengine.util.ThemesManager;
import com.zxt.compplatform.formengine.util.TreeUtil;
import com.zxt.compplatform.organization.service.OrganizationService;
import com.zxt.compplatform.skip.entity.MenuSetting;
import com.zxt.compplatform.skip.entity.UserDeskSetVo;
import com.zxt.framework.common.util.RandomGUID;
import com.zxt.framework.common.util.UrlUtils;
import com.zxt.util.GetMACAddress;

/**
 * 系统操作Action
 * 
 * @author 007
 */
public class SystemFrameAction extends ActionSupport {

	private static final Logger log = Logger.getLogger(SystemFrameAction.class);
	/**
	 * 系统框架业务操作接口
	 */
	private SystemFrameService systemFrameService;
	/**
	 * 资源关系操作接口
	 */
	private RARelationShipService relationShipService;
	/**
	 * 权限框架业务操作接口
	 */
	private AuthorityFrameService authorityFrameService;
	/**
	 * 角色操作接口
	 */
	private RoleService roleService;
	/**
	 * 菜单json数据
	 */
	private String menujson;
	/**
	 * 系统菜单
	 */
	private List systemMenuList = null;
	/**
	 * 选项卡菜单 and 选项卡下 树菜单json
	 */
	private List tabMenuList;
	/**
	 * 桌面版系统用户实体
	 */
	private UserDeskSetVo userDeskSetVo;
	/**
	 * 初始化json数据
	 */
	private String initMenuJson;
	/**
	 * 主题
	 */
	private String themes;
	/**
	 * 客户端宽度
	 */
	private String clientWidth;
	/**
	 * 
	 * desktop
	 */
	private List treeDataList;

	/**
	 * 树数据
	 */
	private TreeData treeData;

	/**
	 * 皮肤清单列表
	 */
	private String skinLiString = Constant.FORM_SKIN_LIST;

	/*
	 * (non-Javadoc) 系统登录入口
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings("unchecked")
	public String execute() {
		// TODO Auto-generated method stub
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		//用于标识系统唯一名称（资源标识）  第一次有值
		String idMark = request.getParameter("sysName");
		Object bak_url = request.getSession().getAttribute("bak_url");
		// bakup the first access url
		//判断传入的sysName参数，如果为空并且bak_url为空，则导向子系统预览页面：/common/jsp/easyui/systemPreview.jsp
		//该页面列出了当前配置库中的所有子系统
		if (idMark == null || "".equals(idMark)) {
			if (bak_url == null || "".equals(bak_url)) {
				return previewSystem();
			} else {
				//登录请求。当idMark为null时，通过bak_url赋值
				idMark = bak_url.toString();
			}
		}
		ServletActionContext.getRequest().getSession().setAttribute("bak_url",
				idMark);
		ServletActionContext.getRequest().setAttribute("bak_url", idMark);

		//通过idMark子系统编码，取得子系统名称，用于显示登陆页面的标题
		String systemID = systemFrameService.treeId(idMark);
		String systemName = systemFrameService.systemName(systemID);
		ServletActionContext.getRequest().getSession().setAttribute(
				"systemName", systemName);

		String roles = "";
		roles = initUserInfo();
		if ("logout".equals(roles)) {
			return "logout";
		}

		/**
		 * 角色切换功能 默认出一个角色的权限
		 * 如果是通过角色切换，即stwitchRole有值，那么将当前角色设置为stwitchRole指向的角色
		 */
        String rolesAll=roles;//得到全部角色
		String roleArray[] = roles.split(",");
		if (roleArray.length > 1) {
			roles = ServletActionContext.getRequest().getParameter(
					"stwitchRole");
			if (roles != null && !"".equals(roles)) {
				roles = StrTools.charsetFormat(roles, "ISO8859-1", "UTF-8");
			} else {
				//此处是给予用户登录默认设置的角色
				roles = roleArray[0];
			}
			//roleArray[roleArray.length]="全部角色"; 用于角色切换
			ServletActionContext.getRequest().setAttribute("stwitchRoleArray",
					roleArray);

		}
		ServletActionContext.getRequest().setAttribute("stwitchRole", roles);
		// 此处存放一个Session GUOWEIXIN 登录人角色
		ServletActionContext.getRequest().getSession().setAttribute(
				"stwitchRole", roles);

		/**
		 * GUOWEIXIN 根据得到的角色ID(利用上方的rid)， 目的：得到该 组织机构 中 当前节点和所属下级节点 的OID值，
		 * Param:String (之间用,号分隔）
		 */

		String oid = (String) ServletActionContext.getRequest().getSession()
				.getAttribute("oid");
		if (oid != null) {
			//调试代码，值没有使用
			String resultStr = systemFrameService.load_orgAlgorithmIds(oid);
			// 当前组织机构及下属ID
			ServletActionContext.getRequest().getSession().setAttribute(
					"currOrgIds", resultStr);
		}
		// ---END GUOWEIXIN

		//获得当前用户的菜单列表
		List menuList = relationShipService
				.getMenusByRoleNames(roles, systemID);

		/**
		 * 权限检查的map
		 */
		Map authority = new HashMap();

		String defaultSkin = "";
		@SuppressWarnings("unused")
		String seEnabled = "";

		if (menuList != null) {
			String authorityId = "";
			for (int i = 0; i < menuList.size(); i++) {
				TreeData treeDataObjData = (TreeData) menuList.get(i);
				authorityId = treeDataObjData.getId();
				//将有权限的菜单id放到authority中
				if (StringUtils.isNotEmpty(authorityId)) {
					authority.put("'" + authorityId + "'", "AUTHORITY");
				}

				if (idMark.equals(treeDataObjData.getResKey())) {
					// 设置默认皮肤
					if (!com.zxt.framework.common.util.StringUtils
							.isNull(treeDataObjData.getDefaultSkin())
							|| !com.zxt.framework.common.util.StringUtils
									.isEmpty(treeDataObjData.getDefaultSkin())) {
						defaultSkin = treeDataObjData.getDefaultSkin();
						findReq().setAttribute("theme", defaultSkin);
					}

					// 设置是否可选择皮肤
					if (!com.zxt.framework.common.util.StringUtils
							.isNull(treeDataObjData.getSelectSkinEnable())
							|| !com.zxt.framework.common.util.StringUtils
									.isEmpty(treeDataObjData
											.getSelectSkinEnable())) {
						findReq().setAttribute("selectskinenable",
								treeDataObjData.getSelectSkinEnable());
					}
				}
			}
		}
		//通过当前角色，取得角色ID
		String rid = authorityFrameService
				.initAuthorityRidFrameByAccount(roles);
		findReq().getSession().setAttribute("rid", rid);
		findReq().getSession().setAttribute("roles", roles);
		findReq().getSession().setAttribute("authority", authority);
		themes = ThemesManager.getTheme(findReq(), findResp(), idMark);
		// themes ="deepblue";
		// String authorutyIDS = StringUtils.join(menuIds, ',');
		if (menuList != null && menuList.size() > 0)
			if ("yingji".equals(themes)) {
				//yingji皮肤，菜单特殊处理
				systemMenuList = systemFrameService.findSystemMenuNew(systemID,
						menuList);
			} else {
				systemMenuList = systemFrameService.findSystemMenu(systemID,
						menuList);
			}
		if (systemMenuList != null) {
			if (systemMenuList.size() != 0) {
				SystemMenu systemMenu = (SystemMenu) systemMenuList.get(0);
				tabMenuList = systemMenu.getTabMenuList();
			} else {
				tabMenuList = null;
			}
		} else {
			tabMenuList = null;
		}

		// 设置样式主题
		clientWidth = ThemesManager.getClientWidth(findReq(), findResp());
		findReq().getSession().setAttribute("clientWidth", clientWidth);
		// ThemesManager.changeTheme(findReq(), findResp(),themes);

		// 把当前登录人的信息写入日志。
		try {
			if (ServletActionContext.getRequest().getSession().getAttribute(
					"isLoginSystem") != null) {

				String userId = (String) ServletActionContext.getRequest()
						.getSession().getAttribute("userID");
				String user_loginName = (String) ServletActionContext
						.getRequest().getSession().getAttribute("lcv");// 登录人帐号
				String uName = (String) ServletActionContext.getRequest()
						.getSession().getAttribute("uName");// 登录人姓名
				
				String logIP = ServletActionContext.getRequest()
						.getRemoteAddr();// 当前用户IP
				//IP存到SESSION中
				ServletActionContext.getRequest().getSession().setAttribute("ipAddress",logIP);
				
				SimpleDateFormat dateformat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// 当前登录时间
				String log_time = dateformat.format(new Date());

				SystemLog systemLog = new SystemLog();
				systemLog.setUser_id(userId);
				systemLog.setUser_loginName(user_loginName);
				systemLog.setUser_name(uName);
				systemLog.setUser_roleName(roles + "");
				systemLog.setLog_ip(logIP);
				systemLog.setLog_time(log_time);
				systemFrameService.addSystemLog(systemLog);

				ServletActionContext.getRequest().getSession().removeAttribute(
						"isLoginSystem");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * 跳转页面
		 */
		if ("desktop".equals(themes)) {
			try {
				findResp().sendRedirect(
						"../jquery-desktop/zsf_desktop.action?sysName="
								+ idMark);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "frame";
	}

	/**
	 * 
	 * 方法描述
	 * <p>
	 * 初始化组织机构，角色，用户。
	 * </p>
	 */
	public String initUserInfo() {

		String roles = "";
		String rid = "";
		String userID = "";
		try {

			String userName = null;// acegi
			// guoweixin cas 在configSQL.properties中 验证是否CAS单点登录。 true为是
			// is_use_reference_dataSource=false 是否使用外部数据源，如果true，则从外部数据库读取组织机构和用户信息
			// 保存组织机构之间关系的时候，保存到平台组织机构表中，保存时将平台中没有的部门、用户等数据保存到相关表中（用触发器实现的）
			// is_use_cas=false 是否单点登录
			// 在web.xml中定义单点登陆配置，实现跨域取session中的登陆名（登陆ID）的功能
			Map map = systemFrameService.load_serviceConfigSQL();
			String flagCas = (String) map.get("is_use_cas");
			if ("true".equals(flagCas)) {
				userName = AssertionHolder.getAssertion().getPrincipal()
						.getName();
			}
			if ((userName != null) && (!"".equals(userName))) {//
				roles = authorityFrameService
						.initAuthorityFrameByAccount(userName);
				userID = systemFrameService.findUserIdByUserName(userName);
			} else {
				//如果从跨域session中未取到登陆名，则从跨域session中获取全部用户信息
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				//获得用户的多个角色
				GrantedAuthority[] authorities = userDetails.getAuthorities();
				//将角色数组处理成角色名字符串，中间用,分隔
				for (int i = 0; i < authorities.length; i++) {
					GrantedAuthority authority = authorities[i];
					if (i == authorities.length - 1) {
						roles += authority.getAuthority();
					} else {
						roles += authority.getAuthority() + ",";
					}
				}
				// 如果登录的用户没有相应的角色退出登录
				if (StringUtils.equals(roles, ""))
					return "logout";
				//通过登录名得到ID，将登录名放到session中，lcv
				userID = findUserId(userDetails.getUsername());
				ServletActionContext.getRequest().getSession().setAttribute(
						"lcv", userDetails.getUsername());

			}

			/* 当Session里有用户名，用户人等级等信息时，就不去数据库查询了 */
			//获取组织机构ID
			String oid = (String) ServletActionContext.getRequest()
					.getSession().getAttribute("oid");
			//获取组织机构名称
			String oname = (String) ServletActionContext.getRequest()
					.getSession().getAttribute("oname");
			/**
			 * 查询当前登录用户的组织机构
			 * 如果从session中取不到组织机构组织机构ID oid或者组织机构名称oname
			 * 则通过用户id userID到数据库中查询出来
			 */
			if (oid == null || oname == null) {
				Map orgmap = systemFrameService.findOrgByUid(userID);
				if (orgmap != null) { // GUOWEIXIN 修改
					if (orgmap.get("oid") != null
							&& orgmap.get("oname") != null) {
						oid = orgmap.get("oid").toString();
						oname = orgmap.get("oname").toString();
					} else {
						//调试过程代码，不会执行
						List<UserDeptrel> listUser = (List<UserDeptrel>) orgmap
								.get(0);
						UserDeptrel ud = listUser.get(0);
						oid = ud.getOid();
						oname = ud.getOname();
					}
				}
			}
			/**
			 * 用户等级
			 */
			String userLevel = (String) ServletActionContext.getRequest()
					.getSession().getAttribute("userLevel");
			if (userLevel == null) {
				userLevel = systemFrameService.findULevelByUserID(userID);
			}

			//姓名
			String uName = (String) ServletActionContext.getRequest()
					.getSession().getAttribute("uName");
			if (uName == null) {
				uName = systemFrameService.findUNameByUserID(userID);
			}

			ServletActionContext.getRequest().getSession().setAttribute("oid",
					oid);
			ServletActionContext.getRequest().getSession().setAttribute(
					"oname", oname);

			ServletActionContext.getRequest().getSession().setAttribute(
					"uName", uName);
			ServletActionContext.getRequest().getSession().setAttribute(
					"roles", roles);
			ServletActionContext.getRequest().getSession().setAttribute(
					"userID", userID);
			ServletActionContext.getRequest().getSession().setAttribute(
					"userLevel", userLevel);
			findReq().getSession().setAttribute("userName", userName);
			findReq().getSession().setAttribute("userId", userID);

		} catch (Exception e) {
			 //e.printStackTrace();
			return "logout";
		}

		return roles;
	}

	/**
	 * 注销时，清空session里的信息
	 * 
	 */
	public String logout() {
		/**
		 * 记录登录过的系统
		 */
		String idMark = ServletActionContext.getRequest().getParameter(
				"bak_url");
		String systemID = systemFrameService.treeId(idMark);
		String systemName = systemFrameService.systemName(systemID);
		ServletActionContext.getRequest().getSession().setAttribute("bak_url",
				idMark);
		ServletActionContext.getRequest().getSession().setAttribute(
				"systemName", systemName);

		findReq().getSession().removeAttribute("oid");
		findReq().getSession().removeAttribute("oname");
		findReq().getSession().removeAttribute("lcv");
		findReq().getSession().removeAttribute("uName");
		findReq().getSession().removeAttribute("roles");
		findReq().getSession().removeAttribute("userId");
		findReq().getSession().removeAttribute("userLevel");
		findReq().getSession().removeAttribute("rid");
		SecurityContextHolder.getContext().getAuthentication()
				.setAuthenticated(false);
		return "logout";
	}

	/**
	 * 代码生成
	 * 
	 * @return
	 */
	public String analysisTemplatesHtml() {
		EngineTools
				.writeHtml(
						"D:/engine-code/jsp/",
						"list_402887a32d78747c012d789ef6e20001.jsp",
						EngineTools
								.getHtmlContent("http://localhost:8080/compplatform/formengine/listPageAction.action?formId=402887a32d78747c012d789ef6e20001"),
						"yes");
		return null;
	}

	/**
	 * 查询系统菜单节点下的菜单
	 * 
	 * @return
	 */
	public String switchSystemMenu() {

		String idMark = ServletActionContext.getRequest().getParameter(
				"sysName");

		// cas的赋值 acegi
		String userName = null;
		// guoweixin cas 在configSQL.properties中 验证是否CAS单点登录。 true为是
		// 在web.xml中定义单点登陆配置，实现跨域取session中的登陆名（登陆ID）的功能
		Map map = systemFrameService.load_serviceConfigSQL();
		String flagCas = (String) map.get("is_use_cas");
		if ("true".equals(flagCas)) {
			userName = AssertionHolder.getAssertion().getPrincipal().getName();
		}

		String roles = "";
		String rid = "";
		String userID = "";

		if ((userName != null) && (!"".equals(userName))) {//
			roles = authorityFrameService.initAuthorityFrameByAccount(userName);
			userID = systemFrameService.findUserIdByUserName(userName);
		} else {
			UserDetails userDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			GrantedAuthority[] authorities = userDetails.getAuthorities();
			for (int i = 0; i < authorities.length; i++) {
				GrantedAuthority authority = authorities[i];
				if (i == authorities.length - 1) {
					roles += authority.getAuthority();
				} else {
					roles += authority.getAuthority() + ",";
				}
			}
		}
		// get the menu ids
		// List menuIds = relationShipService.getMenusByRoleNames(roles);
		String systemID = systemFrameService.treeId(idMark);
		List menuList = relationShipService
				.getMenusByRoleNames(roles, systemID);

		String menuId = ServletActionContext.getRequest()
				.getParameter("menuId");
		// title和url是针对于应急皮肤里边点击首页右下角的快捷方式跳转到左侧菜单对应的选项中，所以需要传几个参数。
		String title = "";
		try {
			if (ServletActionContext.getRequest().getParameter("title") != null
					&& !"".equals(ServletActionContext.getRequest()
							.getParameter("title"))) {
				title = new String(ServletActionContext.getRequest()
						.getParameter("title").getBytes("ISO8859-1"), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "";
		try {
			if (ServletActionContext.getRequest().getParameter("url") != null
					&& !"".equals(ServletActionContext.getRequest()
							.getParameter("url"))) {
				url = new String(ServletActionContext.getRequest()
						.getParameter("url").getBytes("ISO8859-1"), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String defaultNode = ServletActionContext.getRequest().getParameter(
				"defaultNode");

		// if there is default treeNode execute follow process --added by bozch
		// 2011 12 23
		if (StringUtils.isNotEmpty(defaultNode)) {
			tabMenuList = systemFrameService.findTabMenus(menuId, menuList,
					defaultNode);
		} else {
			tabMenuList = systemFrameService.findTabMenus(menuId, menuList);
			// tabMenuList =systemFrameService.findTabMenus(menuId);
		}
		// 遍历list获取对应快捷方式在数据库中存储的路径，将其作为参数传到页面上，进行跳转。
		String id = ServletActionContext.getRequest().getParameter("id");
		ServletActionContext.getRequest().setAttribute("menutitle", title);
		for (int i = 0; i < tabMenuList.size(); i++) {
			TabMenu tabMenu = (TabMenu) tabMenuList.get(i);
			if (title.equals(tabMenu.getTitle())) {
				List tabList = tabMenu.getTabList();
				for (int j = 0; j < tabList.size(); j++) {
					TreeData treeData = (TreeData) tabList.get(j);
					if (url.equals(treeData.getText())) {
						ServletActionContext.getRequest()
								.setAttribute(
										"menuurl",
										treeData.getAttributes().getUrl()
												+ "?id=" + id);
						ServletActionContext.getRequest()
								.setAttribute(
										"url",
										treeData.getAttributes().getUrl()
												+ "?id=" + id);
						ServletActionContext.getRequest().setAttribute(
								"menuid", treeData.getId());
						return "serviceSystemMenu";
					}
				}
			}
		}
		return "serviceSystemMenu";
	}

	/**
	 * 换肤desktop
	 * 
	 * @return
	 */
	public String desktop() {
		/**
		 * 查询系统Id
		 */
		String idMark = ServletActionContext.getRequest().getParameter(
				"sysName");
		Object bak_url = ServletActionContext.getRequest().getSession()
				.getAttribute("bak_url");
		// bakup the first access url
		if (idMark == null || "".equals(idMark)) {
			if (bak_url == null || "".equals(bak_url)) {
				return "logout";
			} else {
				idMark = bak_url.toString();
			}
		}

		String systemID = systemFrameService.treeId(idMark);
		String systemName = systemFrameService.systemName(systemID);

		findReq().getSession().setAttribute("systemName", systemName);
		findReq().getSession().setAttribute("bak_url", idMark);
		findReq().setAttribute("systemId", systemID);

		String roles = initUserInfo();
		String userId = "";
		// 如果登录的用户没有相应的角色推出登录
		if ("logout".equals(roles)) {
			return "logout";
		} else {
			userId = ServletActionContext.getRequest().getSession()
					.getAttribute("userId").toString();
		}

		List menuIds = relationShipService.getMenusByRoleNames(roles);
		String authorutyIDS = StringUtils.join(menuIds, ',');
		/**
		 * 菜单
		 */

		treeDataList = systemFrameService.findTreeList_(authorutyIDS);
		// TreeUtil.treeAlgorithm(treeDataList, systemID, null);
		/**
		 * 缓存用户配置信息
		 */

		userDeskSetVo = systemFrameService.finDeskSetVo(userId, systemID);
		/**
		 * 判断用户是否读取默认快捷方式配置
		 */
		userDeskSetVo = initDefaultSetting(userDeskSetVo);
		userDeskSetVo.setUserId(userId);
		userDeskSetVo.setSystemId(systemID);
		try {

			/**
			 * 可编辑表格的JSON
			 */
			if (userDeskSetVo != null) {
				if (userDeskSetVo.getMenuSettings() != null) {
					/**
					 * 设置表单url
					 */
					TreeData temTreeData = null;
					MenuSetting menuSetting = null;
					for (int i = 0; i < userDeskSetVo.getMenuSettings().size(); i++) {
						if (treeDataList != null) {
							for (int j = 0; j < treeDataList.size(); j++) {
								temTreeData = (TreeData) treeDataList.get(j);
								menuSetting = (MenuSetting) (userDeskSetVo
										.getMenuSettings().get(i));
								if ((menuSetting.getId() != null)
										&& (menuSetting.getId()
												.equals(temTreeData.getId()))) {
									((MenuSetting) userDeskSetVo
											.getMenuSettings().get(i))
											.setFormUrl(temTreeData
													.getAttributes().getUrl());
								}
								// 设置默认皮肤
								if (!com.zxt.framework.common.util.StringUtils
										.isNull(temTreeData.getDefaultSkin())
										|| !com.zxt.framework.common.util.StringUtils
												.isEmpty(temTreeData
														.getDefaultSkin())) {
									String defaultSkin = temTreeData
											.getDefaultSkin();
									findReq()
											.setAttribute("theme", defaultSkin);
								}

								// 设置是否可选择皮肤
								if (!com.zxt.framework.common.util.StringUtils
										.isNull(temTreeData
												.getSelectSkinEnable())
										|| !com.zxt.framework.common.util.StringUtils
												.isEmpty(temTreeData
														.getSelectSkinEnable())) {
									findReq().setAttribute("selectskinenable",
											temTreeData.getSelectSkinEnable());
								}
							}
						}
					}

					Map map = new HashMap();
					map.put("total", userDeskSetVo.getMenuSettings().size()
							+ "");
					map.put("rows", userDeskSetVo.getMenuSettings());
					ServletActionContext.getRequest().setAttribute(
							"userMenuSets", JSONUtil.serialize(map));

				}
			}

			/**
			 * 菜单快捷方式设置 树形结构json，list结构json
			 */
			findReq().setAttribute(
					"desktopTreeData",
					JSONUtil.serialize(TreeUtil.treeAlgorithm(treeDataList,
							systemID)));
			findReq().setAttribute("desktopTreeListData",
					JSONUtil.serialize(treeDataList));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		findReq().setAttribute("themes", "desktop");
		return "desktop";
	}

	/**
	 * 返回工作流查看页
	 * 
	 * @return
	 */
	public String findWorkFlowViewInfo() {

		String APP_ID = ServletActionContext.getRequest()
				.getParameter("APP_ID");
		// systemFrameService.findViewPage(viewPage, APP_ID);
		// userDeskSetVo=systemFrameService.finDeskSetVo("",);

		return "view-page";
	}

	/**
	 * 保存用户配置
	 * 
	 * @return
	 */
	public String saveUserSetting() {
		String setJson = ServletActionContext.getRequest().getParameter(
				"parmer");

		// setJson = StrTools.charsetFormat(setJson, "ISO8859-1", "UTF-8");
		List list = null;
		List menuSetList = new ArrayList();
		try {
			Object object = JSONUtil.deserialize(setJson);
			list = (List) object;

			MenuSetting menuSetting = null;
			Map temMap = null;

			int countLeft = 0;
			int countCenter = 0;
			int screenWidth = Integer.parseInt(ServletActionContext
					.getRequest().getParameter("screenWidth"));// 屏幕总宽度 应该js获取
			// 提交后台
			double menuRows = 3.0;// 桌面菜单 行数
			int menuWidth = 140;// 桌面菜单列宽
			int menuWidthCount = (int) ((list.size() / menuRows) * menuWidth);// 桌面菜单总宽度
			int centinit = (screenWidth - menuWidthCount) / 2;

			for (int i = 0; i < list.size(); i++) {
				menuSetting = new MenuSetting();
				temMap = (Map) list.get(i);

				if (temMap.get("menuLayout") == null) {
					temMap.put("menuLayout", "1");
				}

				if (temMap.get("id") != null) {
					menuSetting.setId(temMap.get("id").toString());

				}
				if (temMap.get("text") != null) {
					menuSetting.setText(temMap.get("text").toString());

				}
				if (temMap.get("ioc") != null) {
					menuSetting.setIoc(temMap.get("ioc").toString());
				}

				if ("1".equals(temMap.get("menuLayout").toString())) {// 居左
					menuSetting.setStyle("left:" + (20 + (countLeft / 3) * 140)
							+ "px;top:" + (50 + (countLeft % 3) * 140) + "px");

					countLeft++;
				} else if ("2".equals(temMap.get("menuLayout").toString())) {// 居中
					menuSetting.setStyle("left:"
							+ (centinit + (countCenter / 3) * 140) + "px;top:"
							+ (50 + (countCenter % 3) * 140) + "px");
					countCenter++;
				}
				menuSetting.setMenuLayout(temMap.get("menuLayout").toString());
				menuSetList.add(menuSetting);
			}
			userDeskSetVo.setMenuSettings(menuSetList);
			systemFrameService.saveOrUpdateUserSetting(userDeskSetVo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 权限资源配置模块
	 * 
	 * @return
	 */
	public String findResource() {
		List list = systemFrameService.findAllResource();
		List systemList = systemFrameService.findAllSystem(list);
		try {
			findReq()
					.setAttribute("systemJson", JSONUtil.serialize(systemList));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "authority";
	}

	/**
	 * 查找系统下的所有资源
	 * 
	 * @return
	 */
	public String findResourceBySystemId() {
		List list = systemFrameService.findAllResource();
		String systemId = findReq().getParameter("systemId");
		try {

			findResp().getWriter().write(
					JSONUtil.serialize(TreeUtil.treeAlgorithmForTreeData(list,
							systemId)));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @return
	 */

	/**
	 * 只查找下级的资源
	 * 
	 * @return
	 */
	public String findChildResource() {
		List list = systemFrameService.findAllResource();
		String parentId = findReq().getParameter("parentId");

		try {

			list = TreeUtil.findChildResource(list, parentId);
			Map map = new HashMap();
			map.put("rows", list);
			map.put("total", new Integer(list.size()));
			findResp().getWriter().write(JSONUtil.serialize(map));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 保存资源
	 * 
	 * @return
	 */
	public String saveResource() {
		try {
			/**
			 * 级联添加资源
			 */
			String isAbleWorkFlow = ServletActionContext.getRequest()
					.getParameter("isAbleWorkFlow_hidden");
			if (StringUtils.isEmpty(isAbleWorkFlow)) {
				treeData.getAttributes().setIsAbleWorkFlow(0);
			} else {
				treeData.getAttributes().setIsAbleWorkFlow(
						Integer.parseInt(isAbleWorkFlow));
			}
			
			if ("2".equals(treeData.getResType())) {
				if ((!"".equals(treeData.getId()))
						&& (treeData.getId() != null)) {
					systemFrameService.deleteResource(treeData.getId());
				} else {
					treeData.setId(RandomGUID.geneGuid());
				}

				if (treeData.getAttributes() != null) {
					if ((treeData.getAttributes().getUrl().indexOf(".") < 0)
							&& (!"".equals(treeData.getAttributes().getUrl()))) {// 平台配置的页面
						systemFrameService.saveResource(treeData);//插入当前真实页面。下面的是该页面的 按钮资源
						/**
						 * 保存页面按钮资源
						 */
						systemFrameService.savePageChildResource(treeData);
						/**
						 * 给所有角色分配页面权限
						 */
						String isAuthorityAll = findReq().getParameter(
								"isAuthorityAll");
						if ("1".equals(isAuthorityAll)) {
							systemFrameService
									.saveAllPageResourceToRole(treeData);
						}
					} else { // 自定义的页面 给全部角色赋值
						systemFrameService.savaAllCustomResourceToRole(treeData
								.getId());
						systemFrameService.saveResource(treeData);
					}
				}
			} else {// 其他资源 菜单 div 数据列。。。
				if ("1".equals(findReq().getParameter("isAuthorityAll"))) {
					if ("".equals(treeData.getId())){
						treeData.setId(RandomGUID.geneGuid());
					}
					systemFrameService.savaAllCustomResourceToRole(treeData
							.getId());
				}
				systemFrameService.saveResource(treeData);
			}
			
			findResp().getWriter().write("success");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除资源
	 * 
	 * @return
	 */
	public String deleteResource() {
		String resID = ServletActionContext.getRequest().getParameter("resid");

		try {
			systemFrameService.deleteResource(resID);
			ServletActionContext.getResponse().getWriter().write("sucess");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * loading 资源
	 * 
	 * @return
	 */
	public String loadResource() {
		String resID = findReq().getParameter("resid");
		treeData = systemFrameService.loadResource(resID);

		// 查看是否为扩展页资源
		if ("6".equals(treeData.getResType())) {
			// 取出相应的首页配置主键的值、模板的值
			String urlString = treeData.getAttributes().getUrl();
			// 解析url中的查询参数
			Map queryParams = UrlUtils.getQueryParams(urlString);

			/*
			 * if(queryParams.get("indexSetingId")== null ||
			 * StringUtils.isEmpty(queryParams.get("indexSetingId").toString())){
			 * queryParams.put("indexSetingId",
			 * RandomGUID.geneGuid());//如果不存在id，则创建相应的id }
			 */findReq().setAttribute("indexSetingId",
					queryParams.get("indexSetingId"));
			findReq().setAttribute("indexPageTemplateValue",
					queryParams.get("indexPageTemplateValue"));
		}

		try {
			List list = systemFrameService.loadSystemForm();
			String formJson = JSONUtil.serialize(list);
			findReq().setAttribute("forms", formJson);

			findReq().setAttribute("isSystemEdit",
					findReq().getParameter("isSystemEdit"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// findReq().setAttribute("", "");
		findReq().setAttribute("skinLiString", skinLiString);
		return "authority-att";
	}

	/**
	 * reloading system
	 * 
	 * @return
	 */
	public String reloadSysteMenu() {
		List list = systemFrameService.findAllResource();
		List systemList = systemFrameService.findAllSystem(list);
		try {
			findResp().getWriter().write(JSONUtil.serialize(systemList));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String tipAction() {

		return "tipjsp";
	}

	/**
	 * 返回添加页面
	 * 
	 * @return
	 */
	public String loadSystemForm() {
		treeData = null;
		try {
			List list = systemFrameService.loadSystemForm();
			String formJson = JSONUtil.serialize(list);
			findReq().setAttribute("forms", formJson);
			findReq().setAttribute("skinLiString", skinLiString);

			findReq().setAttribute("isSystemEdit",
					findReq().getParameter("isSystemEdit"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "authority-att";
	}

	public SystemFrameService getSystemFrameService() {
		return systemFrameService;
	}

	public void setSystemFrameService(SystemFrameService systemFrameService) {
		this.systemFrameService = systemFrameService;
	}

	public String getMenujson() {
		return menujson;
	}

	public void setMenujson(String menujson) {
		this.menujson = menujson;
	}

	public List getSystemMenuList() {
		return systemMenuList;
	}

	public void setSystemMenuList(List systemMenuList) {
		this.systemMenuList = systemMenuList;
	}

	public List getTabMenuList() {
		return tabMenuList;
	}

	public void setTabMenuList(List tabMenuList) {
		this.tabMenuList = tabMenuList;
	}

	public RARelationShipService getRelationShipService() {
		return relationShipService;
	}

	public void setRelationShipService(RARelationShipService relationShipService) {
		this.relationShipService = relationShipService;
	}

	public String getInitMenuJson() {
		return initMenuJson;
	}

	public void setInitMenuJson(String initMenuJson) {
		this.initMenuJson = initMenuJson;
	}

	public UserDeskSetVo getUserDeskSetVo() {
		return userDeskSetVo;
	}

	public void setUserDeskSetVo(UserDeskSetVo userDeskSetVo) {
		this.userDeskSetVo = userDeskSetVo;
	}

	public List getTreeDataList() {
		return treeDataList;
	}

	public void setTreeDataList(List treeDataList) {
		this.treeDataList = treeDataList;
	}

	public HttpServletRequest findReq() {

		return ServletActionContext.getRequest();
	}

	public String getThemes() {
		return themes;
	}

	public void setThemes(String themes) {
		this.themes = themes;
	}

	/**
	 * 初始化默认配置
	 * 
	 * @param userDeskSetVo
	 * @return
	 */
	public UserDeskSetVo initDefaultSetting(UserDeskSetVo userDeskSetVo) {
		TreeData temTreeData = null;
		/**
		 * 设置用户 默认的快捷方式配置
		 */
		if (userDeskSetVo.getId() == null) {
			if (userDeskSetVo.getMenuSettings() == null) {
				if (treeDataList != null) {
					List menuSettingList = new ArrayList();
					MenuSetting menuSetting = null;
					for (int j = 0; j < treeDataList.size(); j++) {
						menuSetting = new MenuSetting();
						temTreeData = (TreeData) treeDataList.get(j);
						menuSetting.setId(temTreeData.getId());
						menuSetting.setText(temTreeData.getText());
						if (j == 0) {
							menuSetting
									.setIoc("assets/images/icons/icon_32_drive.png");
						} else if (j == 1) {
							menuSetting
									.setIoc("assets/images/icons/icon_32_disc.png");
						} else {
							menuSetting
									.setIoc("assets/images/icons/icon_32_network.png");
						}

						menuSetting.setStyle("left:" + (20 + (j / 3) * 140)
								+ "px;top:" + (50 + (j % 3) * 140) + "px");
						menuSetting.setMenuLayout("1");
						menuSettingList.add(menuSetting);
						if (j == 2) {
							break;
						}
					}
					userDeskSetVo.setMenuSettings(menuSettingList);
				}
			}

		}
		return userDeskSetVo;
	}

	/**
	 * 子系统预览 
	 * 
	 * @return
	 */
	public String previewSystem() {
		List list = null;
		List systemList = null;
		try {

			list = systemFrameService.findAllResource();
			systemList = systemFrameService.findAllSystem(list);
		} catch (Exception e) {

		}

		findReq().setAttribute("resoureList", list);
		findReq().setAttribute("systemList", systemList);

		return "preview";

	}

	/**
	 * 根据用户名查询用户id
	 * 
	 * @param userName
	 * @return
	 */
	public String findUserId(String userName) {
		String userId = "";
		if (findReq().getSession().getAttribute("userId") != null) {
			userId = findReq().getSession().getAttribute("userId").toString();
		} else {
			userId = systemFrameService.findUserIdByUserName(userName);
			findReq().getSession().setAttribute("userId", userId);
		}
		return userId;
	}

	public HttpServletResponse findResp() {

		return ServletActionContext.getResponse();
	}

	/**
	 * 获取当前上传进度
	 * 
	 * @return
	 */
	public String uploadProgressinfo() {
		UploadInfo uploadInfo = new UploadInfo();

		HttpServletRequest req = ServletActionContext.getRequest();

		if (req.getSession().getAttribute("uploadInfo") != null) {
			uploadInfo = (UploadInfo) req.getSession().getAttribute(
					"uploadInfo");
		}
		try {

			findReq().setAttribute("json", JSONUtil.serialize(uploadInfo));
		} catch (Exception e) {

		}
		return "json";
	}

	public TreeData getTreeData() {
		return treeData;
	}

	public void setTreeData(TreeData treeData) {
		this.treeData = treeData;
	}

	public AuthorityFrameService getAuthorityFrameService() {
		return authorityFrameService;
	}

	public void setAuthorityFrameService(
			AuthorityFrameService authorityFrameService) {
		this.authorityFrameService = authorityFrameService;
	}

	public String getClientWidth() {
		return clientWidth;
	}

	public void setClientWidth(String clientWidth) {
		this.clientWidth = clientWidth;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}
