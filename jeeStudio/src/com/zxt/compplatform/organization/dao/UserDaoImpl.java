package com.zxt.compplatform.organization.dao;

import com.zxt.framework.jdbc.ZXTJDBCTemplate;

public class UserDaoImpl extends ZXTJDBCTemplate implements UserDao{

	/**
	 * 清空用户级别
	 */
	public void setUserLevelNone(String id){
		String sql = "update t_usertable set levelnumber = 0 where levelnumber in ("+id+")";
		update(sql);
	}
}
