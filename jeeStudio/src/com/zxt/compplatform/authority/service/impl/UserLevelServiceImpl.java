package com.zxt.compplatform.authority.service.impl;

import java.util.List;

import com.zxt.compplatform.authority.dao.UserLevelDao;
import com.zxt.compplatform.authority.entity.UserLevel;
import com.zxt.compplatform.authority.service.UserLevelService;
import com.zxt.compplatform.organization.dao.UserDao;

/**
 * 用户级别操作接口实现
 * @author 007
 */
public class UserLevelServiceImpl implements UserLevelService {

	/**
	 * 用户级别持久化操作dao
	 */
	private UserLevelDao userLevelDao;
	/**
	 * 用户持久化操作dao
	 */
	private UserDao userDao;
	
	/**
	 * 级别分页
	 */
	public List findUserLevel(int page,int rows) {
		return userLevelDao.findUserLevel(page,rows);
	}

	/**
	 * 查询级别总条数
	 */
	public int findCount(){
		return userLevelDao.findCount();
	}
	
	/**
	 * 获取全部级别
	 */
	public List findAll(){
		return userLevelDao.findAll();
	}
	
	/**
	 * 添加级别(通过查询现有级别的最大值加1得到id)
	 *        (判断级别名称是否存在 )
	 * @param userLevelDao
	 */
	public String addUserLevel(UserLevel userlevel){
		List existList = userLevelDao.isExist(userlevel.getLevelname());
		if(existList == null || existList.size() == 0){
			int count = userLevelDao.findMaxId();
			Long id = new Long(count+1L);
			userlevel.setId(id);
			userLevelDao.addUserLevel(userlevel);
			return "success";
		}
		return "fail";
	}
	
	/**
	 * 删除级别(删除级别必须同时把该级别下的用户的级别清空)
	 * @param userLevelDao
	 */
	public void deleteUserLevel(String id){
		userLevelDao.deleteUserLevel(id);
		userDao.setUserLevelNone(id);
	}
	
	/**
	 * 修改级别
	 * @param userLevelDao
	 */
	public String updateUserLevel(UserLevel userlevel){
		
		List existList = userLevelDao.isExist(userlevel.getLevelname(),userlevel.getId()+"");
		if(existList == null || existList.size() == 0){
			userLevelDao.updateUserLevel(userlevel);
			return "success";
		}
		return "fail";
	}
	
	/**
	 * 根据id得到要修改某一条级别的信息
	 */
	public UserLevel updateUserLevelById(String id){
		return userLevelDao.updateUserLevelById(id);
	}
	
	//****************************************************************
	/**
	 * 级别下的用户
	 */
	public List findUsersUnderLevel(String levelid, int rows, int page){
		return userLevelDao.getUserListUnderLevel(levelid, rows, page);
	}
	
	/**
	 * 特定级别下的用户总数
	 */
	public int getTotleUnderLevel(String levelid){
		return userLevelDao.getTotleUnderLevel(levelid);
	}
	
	/**
	 * 级别下的用户添加
	 */
	public void levelAddUser(String levelID, String userids){
		String[] userid = userids.split(",");
		for(int i=0;i<userid.length;i++){
			userLevelDao.updateUserLevel(levelID, userid[i]);
		}
	}
	
	/**
	 * 级别下的用户删除
	 */
	public void levelRemoveUser(String levelID, String userids){
		String[] userid = userids.split(",");
		for(int i=0;i<userid.length;i++){
			userLevelDao.updateUserLevel("-1", userid[i]);
		}
	}
	
	//**********************************************************
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public void setUserLevelDao(UserLevelDao userLevelDao) {
		this.userLevelDao = userLevelDao;
	}
}
