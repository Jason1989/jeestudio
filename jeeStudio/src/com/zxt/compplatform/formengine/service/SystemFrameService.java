package com.zxt.compplatform.formengine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zxt.compplatform.formengine.entity.database.SystemLog;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.formengine.entity.view.ViewPage;
import com.zxt.compplatform.formengine.util.ReadConfigSQL;
import com.zxt.compplatform.skip.entity.UserDeskSetVo;

/**
 * 系统业务操作接口
 * 
 * @author 007
 */
public interface SystemFrameService {
	/**
	 * 加载全部连接池 key 数据源id value连接池
	 */
	public Map load_connectPools(String isCache);

	public Map update_connectPools(String isCache);

	/**
	 * 返回非副本形式连接池
	 * 
	 * @param isCache
	 * @return
	 */
	public Map findConnectPools(String isCache);

	/**
	 * 返回选项卡菜单 和选项卡菜单下的树菜单json
	 */
	public List findTabMenus(String systemMenuID);

	/**
	 * 
	 * @param systemMenuID
	 * @return
	 */
	public List findTabMenus(String systemMenuID, String treeIds);

	/**
	 * 查询所有当前系统中有权限的选项卡
	 * 
	 * @param systemMenuID
	 * @param menuList
	 *            有权限的所有的系统资源
	 * @return
	 */
	public List findTabMenus(String systemMenuID, List menuList);

	public List findTabMenusNew(String systemMenuID, List menuList);

	/**
	 * 查询所有当前系统中有权限的选项卡
	 * 
	 * @param systemMenuID
	 * @param menuList
	 *            有权限的所有的系统资源
	 * @return
	 */
	public List findTabMenus(String systemMenuID, List menuList,
			String defaultTreeNode);

	/**
	 * 查询所有当前系统中有权限的选项卡 主要是针对左侧菜单排布方式
	 * 
	 * @param systemMenuID
	 * @param menuList
	 *            有权限的所有的系统资源
	 * @return
	 */
	public List findSystemMenuNew(String systemID, List menuList);

	/**
	 * by系统ID 查找系统菜单
	 */
	public List findSystemMenu(String systemID);

	/**
	 * by系统ID 查找系统菜单--权限过滤
	 */
	public List findSystemMenu(String systemID, String treeIds);

	/**
	 * by系统ID 查找系统菜单--权限过滤
	 */
	public List findSystemMenu(String systemID, List menuList);

	/**
	 * 获得当前子系统的名称
	 */
	public String systemName(String systemID);

	/**
	 * 加载工作流节点详细信息
	 */
	public ViewPage findViewPage(ViewPage viewPage, String App_ID);

	/**
	 * 查询系统id
	 * 
	 * @param id_mark
	 * @return
	 */
	public String treeId(String id_mark);

	/**
	 * 查询当前组织机构下的下级部门
	 */
	public String queryForDownOrg(String orgId);

	/**
	 * 通过用户Id查找 桌面配置
	 * 
	 * @param id
	 * @return
	 */
	public UserDeskSetVo finDeskSetVo(String userId, String systemID);

	/**
	 * desktop 桌面 系统下所有菜单。
	 */
	public List findTreeList_(String treeIds);

	/**
	 * 保存用户设置
	 */
	public int saveOrUpdateUserSetting(UserDeskSetVo userDeskSetVo);

	/**
	 * 查找用户id
	 */
	public String findUserIdByUserName(String userName);

	/**
	 * 根据用户ID查询用户名(不是登录名)
	 */
	public String findUNameByUserID(String userID);

	/**
	 * 根据用户ID查询用户级别
	 */
	public String findULevelByUserID(String userID);

	/**
	 * 查找所有的菜单资源
	 */
	public List findAllResource();

	/**
	 * 查找所有的菜单资源
	 */
	public List findAllSystem(List list);

	/**
	 * 保存权限资源
	 */
	public int saveResource(TreeData temData);

	/**
	 * 删除资源
	 */
	public String deleteResource(String resID);

	/**
	 * 加载一条资源
	 */
	public TreeData loadResource(String resID);

	/**
	 * 查找配置的所有表单
	 */
	public List loadSystemForm();

	/**
	 * 查找所有角色
	 */
	public List findAllRole();

	/**
	 * 插入一条角色资源配置
	 */
	public int saveRoleResource(Map map);

	/**
	 * 给全部角色 插入该页面资源
	 * 
	 */
	public int saveAllPageResourceToRole(TreeData treeData);

	/**
	 * 插入页面下级资源
	 */
	public int savePageChildResource(TreeData treeData);

	/**
	 * 
	 */
	public int savaAllCustomResourceToRole(String pageId);

	public Map findOrgByUid(String userID);

	/**
	 * 查询系统菜单下的扩展页面信息
	 * 
	 * @param systemMenuId
	 *            系统菜单的id
	 * @param treeIds
	 *            有权限的各种菜单id
	 * @return
	 */
	public List findExtendsPages(String systemMenuId, String treeIds);

	/**
	 * 查询系统菜单下的扩展页面信息
	 * 
	 * @param systemMenuId
	 *            系统菜单的id
	 * @param menuList
	 *            全部有权限的系统资源
	 * @return
	 */
	public List findExtendsPages(String systemMenuId, List menuList);

	/**
	 * 添加用户登录日志
	 * 
	 * @param log
	 *            用户登录日志对象
	 */
	public int addSystemLog(SystemLog log);
	/**
	 * 当前用户的下级组织机构的ID
	 */
	public String load_orgAlgorithmIds(String oid);
	
	/***GUOWEIXIN
	 * 加载configSQL.properties资源文件
	 */
	public Map load_serviceConfigSQL();

}
