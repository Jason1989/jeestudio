package com.zxt.compplatform.formengine.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.googlecode.jsonplugin.JSONUtil;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.dao.ComponentsDao;
import com.zxt.compplatform.formengine.dao.SystemFrameDao;
import com.zxt.compplatform.formengine.entity.database.SystemLog;
import com.zxt.compplatform.formengine.entity.view.SystemMenu;
import com.zxt.compplatform.formengine.entity.view.TabMenu;
import com.zxt.compplatform.formengine.entity.view.TreeAttributes;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.formengine.entity.view.ViewPage;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.compplatform.formengine.util.ReadConfigSQL;
import com.zxt.compplatform.formengine.util.TreeUtil;
import com.zxt.compplatform.indexgenerate.service.PageService;
import com.zxt.compplatform.organization.service.OrganizationService;
import com.zxt.compplatform.skip.entity.UserDeskSetVo;
import com.zxt.framework.common.util.UrlUtils;

/**
 * 系统框架业务操作实现
 * 
 * @author 007
 */
public class SystemFrameServiceImpl implements SystemFrameService {

	private static final Logger log = Logger
			.getLogger(SystemFrameServiceImpl.class);
	/**
	 * 系统框架持久化操作
	 */
	private SystemFrameDao systemFrameDao;
	/**
	 * 组件持久化操作
	 */
	private ComponentsDao componentsDao;
	/**
	 * 界面业务操作接口
	 */
	private PageService pageService;

	public SystemFrameDao getSystemFrameDao() {
		return systemFrameDao;
	}

	public void setSystemFrameDao(SystemFrameDao systemFrameDao) {
		this.systemFrameDao = systemFrameDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#load_connectPools(java.lang.String)
	 */
	public Map load_connectPools(String isCache) {
		return systemFrameDao.findConnectPools(isCache);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#update_connectPools(java.lang.String)
	 */
	public Map update_connectPools(String isCache) {
		return systemFrameDao.findConnectPools(isCache);
	}

	/*
	 * (non-Javadoc) 返回非副本形式的连接池
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#findConnectPools(java.lang.String)
	 */
	public Map findConnectPools(String isCache) {
		return systemFrameDao.findConnectPools(isCache);
	}

	/**
	 * 返回选项卡菜单 和选项卡菜单下的树菜单json
	 */
	public List findTabMenus(String systemMenuID) {
		// TODO Auto-generated method stub
		/**
		 * 查询该系统菜单下 所有的选项卡菜单
		 */
		List systemMenu = systemFrameDao.findTreeList(Constant.MENU_LEVEL_TAB,
				systemMenuID);
		TreeData temData = null;

		/**
		 * 返回的选项卡list
		 */
		List tabMenuList = new ArrayList();
		TabMenu temTabMenu = null;

		/**
		 * 遍历封装查询选项卡下的菜单
		 */
		for (int i = 0; i < systemMenu.size(); i++) {
			temData = (TreeData) systemMenu.get(i);

			/**
			 * 封装选项卡菜单下的树菜单
			 */
			List treeList = systemFrameDao
					.findTreeList(Constant.MENU_LEVEL_TREE);
			String json = "";

			/**
			 * 基于根节点 封装
			 */
			treeList = TreeUtil.treeAlgorithm(treeList, temData.getId());
			try {
				json = JSONUtil.serialize(treeList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}

			/**
			 * 封装选项卡树菜单 和 属性
			 */
			temTabMenu = new TabMenu();
			temTabMenu.setJson(json);
			temTabMenu.setTitle(temData.getText());
			tabMenuList.add(temTabMenu);
		}
		return tabMenuList;
	}

	/**
	 * by系统ID 查找系统菜单
	 */
	public List findSystemMenu(String systemID) {
		// TODO Auto-generated method stub

		List list = systemFrameDao.findTreeList(Constant.MENU_LEVEL_SYSMENU,
				systemID);
		TreeData temData = null;
		/**
		 * 
		 */
		List systemMenuList = new ArrayList();
		SystemMenu temSystemMenu = null;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				temSystemMenu = new SystemMenu();
				temData = (TreeData) list.get(i);
				temSystemMenu.setMenuId(temData.getId());
				temSystemMenu.setTitle(temData.getText());
				temSystemMenu.setTabMenuList(findTabMenus(temData.getId()));
				systemMenuList.add(temSystemMenu);
			}
		}
		return systemMenuList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#findViewPage(com.zxt.compplatform.formengine.entity.view.ViewPage,
	 *      java.lang.String)
	 */
	public ViewPage findViewPage(ViewPage viewPage, String App_ID) {
		// TODO Auto-generated method stub

		String sql = viewPage.getFindSql();
		String[] parmer = new String[] { App_ID };
		viewPage = componentsDao.loadViewPage(sql, parmer, viewPage);

		return viewPage;
	}

	public ComponentsDao getComponentsDao() {
		return componentsDao;
	}

	public void setComponentsDao(ComponentsDao componentsDao) {
		this.componentsDao = componentsDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#treeId(java.lang.String)
	 */
	public String treeId(String id_mark) {
		return systemFrameDao.treeId(id_mark);
	}

	/**
	 * 获得当前子系统的名称
	 */
	public String systemName(String systemID) {
		return systemFrameDao.systemName(systemID);
	}

	public List findSystemMenu(String systemID, String treeIds) {

		List list = systemFrameDao.findTreeList(Constant.MENU_LEVEL_SYSMENU,
				systemID, treeIds);
		TreeData temData = null;
		/**
		 * 
		 */
		List systemMenuList = new ArrayList();
		SystemMenu temSystemMenu = null;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				temSystemMenu = new SystemMenu();
				temData = (TreeData) list.get(i);
				temSystemMenu.setMenuId(temData.getId());
				temSystemMenu.setTitle(temData.getText());
				// 如果是扩展页面，则查询相应的扩展页面记录信息
				if ("6".equals(temData.getResType())) {// 6:资源类型（扩展页面）
					temSystemMenu.setTabMenuList(findExtendsPages(temData
							.getId(), treeIds));
				} else {
					temSystemMenu.setTabMenuList(findTabMenus(temData.getId(),
							treeIds));
				}
				temSystemMenu.setImageUrl(temData.getIconCls());
				temSystemMenu.setResType(temData.getResType());
				systemMenuList.add(temSystemMenu);

			}
		}
		return systemMenuList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#findSystemMenu(java.lang.String,
	 *      java.util.List)
	 */
	public List findSystemMenu(String systemID, List menuList) {
		// TODO Auto-generated method stub

		List list = new ArrayList();

		TreeData temData = null;

		List systemMenuList = new ArrayList();

		// 从有权限的资源中获取本系统的系统菜单
		if (menuList == null || menuList.size() <= 0
				|| StringUtils.isEmpty(systemID)) {
			return systemMenuList;
		}
		TreeData menu = null;
		for (int i = 0; i < menuList.size(); i++) {
			menu = (TreeData) menuList.get(i);
			if (Constant.MENU_LEVEL_SYSMENU.equals(menu.getLevel())
					&& systemID.equals(menu.getParentID()))
				list.add(menu);
		}

		SystemMenu temSystemMenu = null;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				temSystemMenu = new SystemMenu();
				temData = (TreeData) list.get(i);
				temSystemMenu.setMenuId(temData.getId());
				temSystemMenu.setTitle(temData.getText());
				// 如果是扩展页面，则查询相应的扩展页面记录信息
				if (Constant.RESC_TYPE_EXTENDPAGE.equals(temData.getResType())) {// 6:资源类型（扩展页面）
					/*
					 * temSystemMenu.setTabMenuList(findExtendsPages(temData.getId(),
					 * menuList));
					 */
					temSystemMenu.setUrl(temData.getAttributes().getUrl());
				} else {
					temSystemMenu.setTabMenuList(findTabMenus(temData.getId(),
							menuList));
				}
				temSystemMenu.setImageUrl(temData.getIconCls());
				temSystemMenu.setResType(temData.getResType());
				systemMenuList.add(temSystemMenu);

			}
		}
		return systemMenuList;
	}

	/*
	 * (non-Javadoc) 左侧的树形菜单改成多列图标形式。
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#findSystemMenuNew(java.lang.String,
	 *      java.util.List)
	 */
	public List findSystemMenuNew(String systemID, List menuList) {
		// TODO Auto-generated method stub

		List list = new ArrayList();

		TreeData temData = null;

		List systemMenuList = new ArrayList();

		// 从有权限的资源中获取本系统的系统菜单
		if (menuList == null || menuList.size() <= 0
				|| StringUtils.isEmpty(systemID)) {
			return systemMenuList;
		}
		TreeData menu = null;
		for (int i = 0; i < menuList.size(); i++) {
			menu = (TreeData) menuList.get(i);
			if (Constant.MENU_LEVEL_SYSMENU.equals(menu.getLevel())
					&& systemID.equals(menu.getParentID()))
				list.add(menu);
		}

		SystemMenu temSystemMenu = null;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				temSystemMenu = new SystemMenu();
				temData = (TreeData) list.get(i);
				temSystemMenu.setMenuId(temData.getId());
				temSystemMenu.setTitle(temData.getText());
				// 如果是扩展页面，则查询相应的扩展页面记录信息
				if (Constant.RESC_TYPE_EXTENDPAGE.equals(temData.getResType())) {// 6:资源类型（扩展页面）
					/*
					 * temSystemMenu.setTabMenuList(findExtendsPages(temData.getId(),
					 * menuList));
					 */
					temSystemMenu.setUrl(temData.getAttributes().getUrl());
				} else {
					temSystemMenu.setTabMenuList(findTabMenusNew(temData
							.getId(), menuList));
				}
				temSystemMenu.setImageUrl(temData.getIconCls());
				temSystemMenu.setResType(temData.getResType());
				systemMenuList.add(temSystemMenu);

			}
		}
		return systemMenuList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#findExtendsPages(java.lang.String,
	 *      java.lang.String)
	 */
	public List findExtendsPages(String systemMenuId, String treeIds) {
		List list = systemFrameDao.findTreeList(Constant.MENU_LEVEL_TREE,
				systemMenuId, treeIds);
		return list;
	}

	public List findExtendsPages(String systemMenuId, List menuList) {
		List authorityExpage = new ArrayList();
		if (StringUtils.isEmpty(systemMenuId) || menuList == null
				|| menuList.size() <= 0) {
			return authorityExpage;
		}
		TreeData menu = null;
		for (int i = 0; i < menuList.size(); i++) {
			menu = (TreeData) menuList.get(i);
			if (Constant.MENU_LEVEL_TREE.equals(menu.getLevel())
					&& systemMenuId.equals(menu.getParentID()))
				authorityExpage.add(menu);
		}
		return authorityExpage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#findTabMenus(java.lang.String,
	 *      java.lang.String)
	 */
	public List findTabMenus(String systemMenuID, String treeIds) {
		// TODO Auto-generated method stub
		/**
		 * 查询该系统菜单下 所有的选项卡菜单
		 */
		List systemMenu = systemFrameDao.findTreeList(Constant.MENU_LEVEL_TAB,
				systemMenuID, treeIds);
		TreeData temData = null;

		/**
		 * 返回的选项卡list
		 */
		List tabMenuList = new ArrayList();
		TabMenu temTabMenu = null;

		/**
		 * 遍历封装查询选项卡下的菜单
		 */
		for (int i = 0; i < systemMenu.size(); i++) {
			temData = (TreeData) systemMenu.get(i);

			/**
			 * 封装选项卡菜单下的树菜单
			 */
			List treeList = systemFrameDao.findTreeList_(
					Constant.MENU_LEVEL_TREE, treeIds);
			String json = "";
			/**
			 * 基于根节点 封装
			 */
			treeList = TreeUtil.treeAlgorithm(treeList, temData.getId());
			try {
				if (treeList != null) {
					json = JSONUtil.serialize(treeList);
				} else {
					json = "";
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
			}

			/**
			 * 封装选项卡树菜单 和 属性
			 */
			temTabMenu = new TabMenu();
			temTabMenu.setJson(json);
			temTabMenu.setTitle(temData.getText());
			tabMenuList.add(temTabMenu);
		}
		return tabMenuList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#findTabMenus(java.lang.String,
	 *      java.util.List)
	 */
	public List findTabMenus(String systemMenuID, List menuList) {
		// TODO Auto-generated method stub

		// 过滤掉非menu的资源
		List menuLists = new ArrayList();

		/**
		 * 查询该系统菜单下 所有的选项卡菜单
		 */
		// 有权限的当前登录系统资源（未封装）
		List systemMenu = new ArrayList();

		// 有权限的当前登录系统资源
		List tabMenuList = new ArrayList();
		// 从有权限的资源中获取本系统的系统菜单
		if (menuList == null || menuList.size() <= 0
				|| StringUtils.isEmpty(systemMenuID)) {
			return tabMenuList;
		}
		TreeData menu = null;
		for (int i = 0; i < menuList.size(); i++) {
			menu = (TreeData) menuList.get(i);
			if ("1".equals(menu.getIsMenu())) {// 取出所有的菜单项
				menuLists.add(menu);
			}
			if (Constant.MENU_LEVEL_TAB.equals(menu.getLevel())
					&& systemMenuID.equals(menu.getParentID()))
				systemMenu.add(menu);
		}

		TreeData temData = null;

		/**
		 * 返回的选项卡list
		 */
		TabMenu temTabMenu = null;
		List treeList = null;

		/**
		 * 遍历封装查询选项卡下的菜单
		 */
		for (int i = 0; i < systemMenu.size(); i++) {
			temData = (TreeData) systemMenu.get(i);

			/**
			 * 封装选项卡菜单下的树菜单
			 */
			/*
			 * List menuList = systemFrameDao.findTreeList_(
			 * Constant.MENU_LEVEL_TREE, treeIds);
			 */
			String json = "";
			/**
			 * 基于根节点 封装
			 */
			treeList = TreeUtil.treeAlgorithm(menuLists, temData.getId());
			try {
				if (treeList != null) {
					json = JSONUtil.serialize(treeList);
				} else {
					json = "";
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
			}

			/**
			 * 封装选项卡树菜单 和 属性
			 */
			temTabMenu = new TabMenu();
			temTabMenu.setJson(json);
			temTabMenu.setTitle(temData.getText());
			temTabMenu.setTabList(treeList);
			temTabMenu.setRow_num(temData.getRow_num());
			tabMenuList.add(temTabMenu);
		}
		return tabMenuList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#findTabMenusNew(java.lang.String,
	 *      java.util.List)
	 */
	public List findTabMenusNew(String systemMenuID, List menuList) {
		// TODO Auto-generated method stub

		// 过滤掉非menu的资源
		List menuLists = new ArrayList();

		/**
		 * 查询该系统菜单下 所有的选项卡菜单
		 */
		// 有权限的当前登录系统资源（未封装）
		List systemMenu = new ArrayList();

		// 有权限的当前登录系统资源
		List tabMenuList = new ArrayList();
		// 从有权限的资源中获取本系统的系统菜单
		if (menuList == null || menuList.size() <= 0
				|| StringUtils.isEmpty(systemMenuID)) {
			return tabMenuList;
		}
		TreeData menu = null;
		for (int i = 0; i < menuList.size(); i++) {
			menu = (TreeData) menuList.get(i);
			if ("1".equals(menu.getIsMenu())) {// 取出所有的菜单项
				menuLists.add(menu);
			}
			if (Constant.MENU_LEVEL_TAB.equals(menu.getLevel())
					&& systemMenuID.equals(menu.getParentID()))
				systemMenu.add(menu);
		}

		TreeData temData = null;

		/**
		 * 返回的选项卡list
		 */
		TabMenu temTabMenu = null;
		List treeList = null;

		/**
		 * 遍历封装查询选项卡下的菜单
		 */
		for (int i = 0; i < systemMenu.size(); i++) {
			temData = (TreeData) systemMenu.get(i);

			/**
			 * 封装选项卡菜单下的树菜单
			 */
			/*
			 * List menuList = systemFrameDao.findTreeList_(
			 * Constant.MENU_LEVEL_TREE, treeIds);
			 */
			String json = "";
			/**
			 * 基于根节点 封装
			 */
			treeList = TreeUtil.treeAlgorithm(menuLists, temData.getId());
			try {
				if (treeList != null) {
					json = JSONUtil.serialize(treeList);
				} else {
					json = "";
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
			}

			/**
			 * 封装选项卡树菜单 和 属性
			 */
			temTabMenu = new TabMenu();
			temTabMenu.setJson(json);
			temTabMenu.setTitle(temData.getText());
			temTabMenu.setTabList(treeList);
			tabMenuList.add(temTabMenu);
		}
		return tabMenuList;
	}

	/**
	 * 查询当前组织机构下的下级部门
	 */
	public String queryForDownOrg(String orgId) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#finDeskSetVo(java.lang.String,
	 *      java.lang.String)
	 */
	public UserDeskSetVo finDeskSetVo(String userid, String systemId) {
		return systemFrameDao.finDeskSetVo(userid, systemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#findTreeList_(java.lang.String)
	 */
	public List findTreeList_(String treeIds) {
		return systemFrameDao.findTreeList_(treeIds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#saveOrUpdateUserSetting(com.zxt.compplatform.skip.entity.UserDeskSetVo)
	 */
	public int saveOrUpdateUserSetting(UserDeskSetVo userDeskSetVo) {
		return systemFrameDao.saveOrUpdateUserSetting(userDeskSetVo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#findUserIdByUserName(java.lang.String)
	 */
	public String findUserIdByUserName(String userName) {
		return systemFrameDao.findUserIdByUserName(userName);
	}

	// 根据用户ID查询用户名(不是登录名)
	public String findUNameByUserID(String userID) {
		return systemFrameDao.findUNameByUserID(userID);
	}

	// 根据用户ID查询用户级别
	public String findULevelByUserID(String userID) {
		return systemFrameDao.findULevelByUserID(userID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#findAllResource()
	 */
	public List findAllResource() {
		return systemFrameDao.findAllResource();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#findAllSystem(java.util.List)
	 */
	public List findAllSystem(List list) {
		List systemList = new ArrayList();
		TreeData temData = null;
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				temData = (TreeData) list.get(i);
				if ("1".equals(temData.getLevel())) {
					systemList.add(temData);
				}
			}
		}
		return systemList;
	}

	/**
	 * 批量编辑资源
	 */
	public int saveResource(TreeData temData) {
		// TODO Auto-generated method stub

		systemFrameDao.saveResource(temData);

		return 0;
	}

	/**
	 * 删除资源
	 */
	@SuppressWarnings("unchecked")
	public String deleteResource(String resID) {
		// TODO Auto-generated method stub
		/**
		 * 级联删除下级资源
		 */
		String childIDs = TreeUtil.findAllChildTreeID(systemFrameDao
				.findAllResource(), resID)
				+ "'" + resID + "'";// 删除所有的子节点
		systemFrameDao.deleteResource(childIDs);

		// 如果资源是扩展页面，则需要将首页配置删掉
		TreeData treeData = loadResource(resID);
		if (treeData != null
				&& Constant.RESC_TYPE_EXTENDPAGE.equals(treeData.getResType())) {
			Map map = UrlUtils
					.getQueryParams(treeData.getAttributes().getUrl());
			if (map.get("indexSetingId") != null) {
				pageService.delete(map.get("indexSetingId").toString());
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#loadResource(java.lang.String)
	 */
	public TreeData loadResource(String resID) {
		return systemFrameDao.loadResource(resID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#loadSystemForm()
	 */
	public List loadSystemForm() {
		return systemFrameDao.loadSystemForm();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#findAllRole()
	 */
	public List findAllRole() {
		return systemFrameDao.findAllRole();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#saveRoleResource(java.util.Map)
	 */
	public int saveRoleResource(Map map) {
		return systemFrameDao.saveRoleResource(map);
	}

	/**
	 * 全部角色插入资源
	 */
	public int saveAllPageResourceToRole(TreeData treeData) {
		// TODO Auto-generated method stub

		List list = findAllRole();
		Map map = new HashMap();
		Map roleMap = new HashMap();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {

				roleMap = (Map) list.get(i);
				String roleId = roleMap.get("rid").toString();
				map.put("ROLE_ID", roleId);
				map.put("RESC_ID", treeData.getId());
				saveRoleResource(map);

				/**
				 * 添加按钮资源
				 */

				map.put("RESC_ID", treeData.getAttributes().getUrl() + "_add");// 添加按钮
				saveRoleResource(map);

				map.put("RESC_ID", treeData.getAttributes().getUrl()
						+ "_delete");// 删除按钮
				saveRoleResource(map);

				map.put("RESC_ID", treeData.getAttributes().getUrl() + "_edit");// 编辑按钮
				saveRoleResource(map);

				map.put("RESC_ID", treeData.getAttributes().getUrl() + "_copy");// 编辑按钮
				saveRoleResource(map);

				map.put("RESC_ID", treeData.getAttributes().getUrl() + "_view");// 详情页查看按钮
				saveRoleResource(map);

				map.put("RESC_ID", treeData.getAttributes().getUrl()
						+ "_batchDelete");// 批量删除按钮
				saveRoleResource(map);

				map.put("RESC_ID", treeData.getAttributes().getUrl()
						+ "_batchEdit");// 批量编辑按钮
				saveRoleResource(map);

			}
		}
		return 0;
	}

	/**
	 * 插入页面下级资源
	 */
	public int savePageChildResource(TreeData treeData) {
		TreeData temData = new TreeData();
		TreeAttributes temAttributes = new TreeAttributes();
		temData.setAttributes(temAttributes);

		/**
		 * 基本属性设置
		 */
		temData.setParentID(treeData.getId());// 上级节点ID
		temData.setResType("3");// 类型： 页面
		temData.setIsMenu("0");// 是否菜单项： 是

		temData.setLevel("5");// 资源级别： 页面资源
		temData.getAttributes().setUrl("");// 资源uri
		temData.setResKey("");// 资源标识
		/**
		 * 按钮资源设置
		 */
		String pageId = treeData.getAttributes().getUrl();
		temData.setResSort("1");// 排序
		temData.setId(pageId + "_add");// 资源ID
		temData.setText("添加按钮");// 资源名称
		saveResource(temData);

		temData.setResSort("2");// 排序
		temData.setId(pageId + "_delete");// 资源ID
		temData.setText("删除按钮");// 资源名称
		saveResource(temData);

		temData.setResSort("3");// 排序
		temData.setId(pageId + "_edit");// 资源ID
		temData.setText("编辑按钮");// 资源名称
		saveResource(temData);

		temData.setResSort("4");// 排序
		temData.setId(pageId + "_view");// 资源ID
		temData.setText("查看详情按钮");// 资源名称
		saveResource(temData);

		temData.setResSort("5");// 排序
		temData.setId(pageId + "_batchDelete");// 资源ID
		temData.setText("批量删除");// 资源名称
		saveResource(temData);

		temData.setResSort("6");// 排序
		temData.setId(pageId + "_batchEdit");// 资源ID
		temData.setText("批量编辑");// 资源名称
		saveResource(temData);

		temData.setResSort("7");// 排序
		temData.setId(pageId + "_import");// 资源ID
		temData.setText("导入");// 资源名称
		saveResource(temData);

		temData.setResSort("8");// 排序
		temData.setId(pageId + "_export");// 资源ID
		temData.setText("导出");// 资源名称
		saveResource(temData);

		temData.setResSort("9");// 排序
		temData.setId(pageId + "_copy");// 资源ID
		temData.setText("复制");// 资源名称
		saveResource(temData);

		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#savaAllCustomResourceToRole(java.lang.String)
	 */
	public int savaAllCustomResourceToRole(String pageId) {
		List list = findAllRole();
		Map map = new HashMap();
		Map roleMap = new HashMap();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				roleMap = (Map) list.get(i);
				String roleId = roleMap.get("rid").toString();
				map.put("ROLE_ID", roleId);

				map.put("RESC_ID", pageId);
				saveRoleResource(map);
			}
		}
		return 0;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#findOrgByUid(java.lang.String)
	 */
	public Map findOrgByUid(String userID) {
		return systemFrameDao.findOrgByUid(userID);
	}

	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.service.SystemFrameService#findTabMenus(java.lang.String,
	 *      java.util.List, java.lang.String)
	 */
	public List findTabMenus(String systemMenuID, List menuList,
			String defaultTreeNode) {
		// 过滤掉非menu的资源
		List menuLists = new ArrayList();

		/**
		 * 查询该系统菜单下 所有的选项卡菜单
		 */
		// 有权限的当前登录系统资源（未封装）
		List systemMenu = new ArrayList();

		// 有权限的当前登录系统资源
		List tabMenuList = new ArrayList();
		// 从有权限的资源中获取本系统的系统菜单
		if (menuList == null || menuList.size() <= 0
				|| StringUtils.isEmpty(systemMenuID)) {
			return tabMenuList;
		}
		TreeData menu = null;

		// 默认节点的父节点
		String defaultParentNode = null;

		for (int i = 0; i < menuList.size(); i++) {
			menu = (TreeData) menuList.get(i);
			if ("1".equals(menu.getIsMenu())) {// 取出所有的菜单项
				if (defaultTreeNode.equals(menu.getId())) {// 如果是默认的树节点，则将其设为选中的
					menu.setSelected(true);
					defaultParentNode = menu.getParentID();// 获取之后
					// 将相应的选项卡设为默认选中的
					// 注意判断资源的类型
				}
				menuLists.add(menu);

			}
			if (Constant.MENU_LEVEL_TAB.equals(menu.getLevel())
					&& systemMenuID.equals(menu.getParentID()))
				systemMenu.add(menu);
		}

		TreeData temData = null;

		/**
		 * 返回的选项卡list
		 */
		TabMenu temTabMenu = null;
		List treeList = null;

		/**
		 * 遍历封装查询选项卡下的菜单
		 */
		for (int i = 0; i < systemMenu.size(); i++) {
			temData = (TreeData) systemMenu.get(i);

			/**
			 * 封装选项卡菜单下的树菜单
			 */
			/*
			 * List menuList = systemFrameDao.findTreeList_(
			 * Constant.MENU_LEVEL_TREE, treeIds);
			 */
			String json = "";
			/**
			 * 基于根节点 封装
			 */
			treeList = TreeUtil.treeAlgorithm(menuLists, temData.getId());
			try {
				if (treeList != null) {
					json = JSONUtil.serialize(treeList);
				} else {
					json = "";
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
			}

			/**
			 * 封装选项卡树菜单 和 属性
			 */
			temTabMenu = new TabMenu();
			temTabMenu.setJson(json);
			// set the default accordion
			if (temData.getId().equals(defaultParentNode)) {
				// temData.setSelected(true);
				temTabMenu.setSelected(true);
			}
			temTabMenu.setTitle(temData.getText());
			tabMenuList.add(temTabMenu);
		}
		return tabMenuList;
	}

	/**
	 * 添加用户登录日志
	 */
	public int addSystemLog(SystemLog log) {
		return systemFrameDao.addSystemLog(log);
	}
	/**
	 * 当前用户的下级组织机构的ID
	 */
	public String load_orgAlgorithmIds(String oid){
		List organizationList= organizationService.get_jsonListByClassify();
		String resultStr=TreeUtil.orgAlgorithmIds(organizationList, oid);
		return resultStr;
	}
	/**
	 * 加载configSQL.properties资源文件
	 */
	public static final String FILEPATH="/configSQL.properties";
	public Map load_serviceConfigSQL() {
		try {
		Map map=new HashMap();	
			map=ReadConfigSQL.readProperties(FILEPATH);
			return map;
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 组织机构业务注入 
	 */
	private OrganizationService organizationService;

	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
}