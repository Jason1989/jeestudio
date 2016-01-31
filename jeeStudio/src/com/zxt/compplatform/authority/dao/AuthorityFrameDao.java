package com.zxt.compplatform.authority.dao;

import java.util.List;

/**
 * 权限框架操作
 * @author 007
 */
public interface AuthorityFrameDao {
	
	/**
	 * 初始化指定数据的权限
	 * @param account
	 * @return
	 */
	public List initAuthorityFrameByAccount(String account);
	
	/**
	 * 根据角色名查询角色ID 黄姣
	 * @param rname
	 * @return
	 */
	public List initRolesFrameByAccount(String rname);
	
}
