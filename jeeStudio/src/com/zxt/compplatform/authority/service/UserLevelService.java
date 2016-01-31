package com.zxt.compplatform.authority.service;

import java.util.List;

import com.zxt.compplatform.authority.entity.UserLevel;

/**
 * 用户级别操作接口
 * @author 007
 */
public interface UserLevelService {

	/**
	 * 级别分页
	 */
	public List findUserLevel(int page,int rows);

	/**
	 * 查询级别总条数
	 */
	public int findCount();
	
	/**
	 * 获取全部级别
	 */
	public List findAll();
	
	/**
	 * 添加级别(通过查询现有级别的最大值加1得到id)
	 *        (判断级别名称是否存在 )
	 * @param userLevelDao
	 */
	public String addUserLevel(UserLevel userlevel);
	
	/**
	 * 删除级别(删除级别必须同时把该级别下的用户的级别清空)
	 * @param userLevelDao
	 */
	public void deleteUserLevel(String id);
	
	/**
	 * 修改级别
	 * @param userLevelDao
	 */
	public String updateUserLevel(UserLevel userlevel);
	
	/**
	 * 根据id得到要修改某一条级别的信息
	 */
	public UserLevel updateUserLevelById(String id);
	
	//*****************************************************************
	/**
	 * 级别下的用户
	 */
	public List findUsersUnderLevel(String levelid, int rows, int page);
	
	/**
	 * 级别下的用户总数
	 */
	public int getTotleUnderLevel(String levelid);
	
	
	/**
	 * 级别下的用户添加
	 */
	public void levelAddUser(String levelID, String userids);
	
	/**
	 * 级别下的用户删除
	 */
	public void levelRemoveUser(String levelID, String userids);
}
