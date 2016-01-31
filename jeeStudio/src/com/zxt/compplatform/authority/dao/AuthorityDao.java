package com.zxt.compplatform.authority.dao;

import java.util.List;

import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.framework.common.dao.IDAOSupport;

/**
 *  权限
 * 数据库持久化操作
 * @author 007
 */
public interface AuthorityDao extends IDAOSupport {
	
	/**
	 * 从sql查询权限列表
	 * @param sql
	 * @return
	 */
	public List findAuthority(String sql);

	/**
	 * 根据sql查询所有的表单
	 * @param sql
	 * @return
	 */
	public List findForm(String sql);

	/**
	 * 将集合中的内容插入到数据库
	 * @param sqlList
	 */
	public void insertAll(List sqlList);

	/**
	 * 通过角色id选出响应模板
	 */
	public List findMenuByRoleId(String roleid);

	/**
	 * 获取所有的资源
	 * @return
	 */
	public List<TreeData> findAllResource();
}
