package com.zxt.compplatform.formengine.dao;

import java.util.List;
import java.util.Map;

import com.zxt.compplatform.formengine.entity.database.SystemLog;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.skip.entity.UserDeskSetVo;

/**
 * 系统框架持久化dao接口
 * 
 * @author 007
 */
public interface SystemFrameDao {

	/**
	 * 加载全部连接池 key 数据源id value连接池
	 */
	public Map findConnectPools(String isCache);

	/**
	 * 获得当前子系统的名称
	 */
	public String systemName(String systemID);

	/**
	 * 查找选项卡下的菜单树
	 */
	public List findTreeList(String menuLevel);

	/**
	 * 查找选项卡
	 */
	public List findTreeList(String menuLevel, String parentID);

	/**
	 * 查找选项卡-权限过滤
	 */
	public List findTreeList(String menuLevel, String parentID, String treeIds);

	/**
	 * 查找选项卡下的功能菜单-权限过滤
	 */
	public List findTreeList_(String menuLevel, String treeIds);

	/**
	 * desktop 桌面 系统下所有菜单。
	 */
	public List findTreeList_(String treeIds);

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
	public List queryForDownOrg(String orgId);

	/**
	 * 通过用户Id查找 桌面配置
	 * 
	 * @param id
	 * @return
	 */
	public UserDeskSetVo finDeskSetVo(String id, String systemId);

	/**
	 * 保存用户设置
	 */
	public int saveOrUpdateUserSetting(UserDeskSetVo userDeskSetVo);

	/**
	 * 查找用户id
	 */
	public String findUserIdByUserName(String userName);

	/**
	 * 根据用户ID查询用户名(uName不是登录名)
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
	 * 保存权限资源
	 */
	public int saveResource(TreeData treeData);

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
	 * 根据父节点查询
	 */
	public List findParntId(String parentId);

	/**
	 * 根绝用户id查询部门
	 * 
	 * @param uid
	 * @return
	 */
	public Map findOrgByUid(String uid);

	/**
	 * 插入一条用户登录信息
	 */
	public int addSystemLog(SystemLog log);
}
