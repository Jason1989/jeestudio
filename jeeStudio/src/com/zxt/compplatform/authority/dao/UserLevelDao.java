package com.zxt.compplatform.authority.dao;

import java.util.List;

import com.zxt.compplatform.authority.entity.UserLevel;

/**
 * 用户级别
 * 数据持久化操作
 * @author 007
 */
public interface UserLevelDao {

	/**
	 * 查询级别列表
	 */
	public List findUserLevel(int page,int rows);
	
	/**
	 * 查询级别总条数
	 */
	public int findCount();
	
	/**
	 * 查询所有级别
	 */
	public List findAll();
	
	/**
	 * 删除级别
	 * @param id
	 */
	public void deleteUserLevel(String id);
	
	/**
	 * 添加级别(级别ID查询最大值加1)
	 */
	public void addUserLevel(UserLevel userlevel);
	
	/**
	 * 查询级别ID最大值
	 */
	public int findMaxId();
	
	/**
	 * 判断某名称的级别是否存在(添加)
	 */
	public List isExist(String name);
	/**
	 * 判断某名称的级别是否存在(删除)
	 */
	public List isExist(String name ,String id);
	
	/**
	 * 修改级别
	 */
	public void updateUserLevel(UserLevel userlevel);
	
	/**
	 * 根据id得到要修改某一条级别的信息
	 */
	public UserLevel updateUserLevelById(String id);
	
	/**
	 * 级别下的用户列表
	 */
	public List getUserListUnderLevel(String levelID, int rows ,int page);
	
	/**
	 * 级别下的用户总数
	 */
	public int getTotleUnderLevel(String levelid);
	
	/**
	 * 用户修改级别
	 */
	public void updateUserLevel(String levelID ,String userid);
}
