package com.zxt.compplatform.authority.dao.impl;

import java.util.List;

import com.zxt.compplatform.authority.dao.UserLevelDao;
import com.zxt.compplatform.authority.entity.UserLevel;
import com.zxt.compplatform.formengine.entity.view.UserBasic;
import com.zxt.compplatform.formengine.entity.view.UserLevelBasic;
import com.zxt.framework.jdbc.ZXTJDBCTemplate;

public class UserLevelDaoImpl extends ZXTJDBCTemplate implements UserLevelDao{

	/**
	 * 查询级别列表
	 */
	public List findUserLevel(int page,int rows) {
		int result = judge();
		String sql = "";
		if(result == 1){
			sql = "select id,level_name as levelname,level_number as levelnumber,level_note as levelnote " +
				"from "+
				"("+
				"select A.*, rownum num "+ 
				"from (select *from t_user_level) A "+ 
				"where rownum <= "+(page*rows)+
				")"+
				"where num > "+(+page-1)*rows;
		}else if(result == 2){
			sql = "select top "+rows+" id,level_name as levelname,level_number as levelnumber,"+
				"level_note as levelnote from t_user_level where id not in" +
				"(select top "+rows*(page-1)+" id from t_user_level)";
		}
		return find(sql, UserLevelBasic.class);
	}
	
	/**
	 * 查询所有级别
	 */
	public List findAll(){
		String sql = "select id,level_name as levelname, level_number as levelnumber ," +
				"level_note as levelnote from t_user_level";
		return find(sql, UserLevel.class);
	}
	
	/**
	 * 查询级别总条数
	 */
	public int findCount(){
		String sql = "select count(*) from t_user_level";
		return find(sql);
	}
	
	/**
	 * 删除级别
	 * @param id
	 */
	public void deleteUserLevel(String id){
		String sql = "delete from t_user_level where id in("+id+")" ;
		delete(sql);
	}
	
	/**
	 * 添加级别(级别ID查询最大值加1)
	 */
	public void addUserLevel(UserLevel userlevel){
		String sql = "insert into t_user_level values(?,?,?,?)";
		Object[] obj = new Object[]{userlevel.getId(),userlevel.getLevelname(),userlevel.getLevelnumber(),userlevel.getLevelnote()};
		create(sql, obj);
	}
	
	/**
	 * 查询级别ID最大值
	 */
	public int findMaxId(){
		String sql = "select max(id) as id from t_user_level t";
		return find(sql);
	}
	
	/**
	 * 判断某名称的级别是否存在
	 */
	public List isExist(String name){
		String sql = "select id  from t_user_level where level_name = '"+name+"'";
		return find(sql, UserLevel.class);
	}
	/**
	 * 判断某名称的级别是否存在(删除)
	 */
	public List isExist(String name ,String id){
		String sql = "select id  from t_user_level where level_name = '"+name+"' and id != '"+id+"'" ;
		return find(sql, UserLevel.class);
	}
	
	/**
	 * 修改级别
	 */
	public void updateUserLevel(UserLevel userlevel){
		String sql = "update t_user_level set level_name = ?, level_number=?,level_note = ? where id = ?";
		Object[] obj = new Object[]{userlevel.getLevelname(),userlevel.getLevelnumber(),userlevel.getLevelnote(),userlevel.getId()};
		update(sql, obj);
	}
	
	/**
	 * 根据id得到要修改某一条级别的信息
	 */
	public UserLevel updateUserLevelById(String id){
		String sql = "select id,LEVEL_NAME as levelname ,LEVEL_NUMBER as levelnumber ,LEVEL_NOTE as levelnote  from t_user_level where id = "+ id ;
		List list = find(sql,UserLevel.class);
		UserLevel userlevel = (UserLevel)list.get(0) ;
		return userlevel ;
	}
	
	//********************************************************************
	/**
	 * 级别下的用户列表
	 */
	public List getUserListUnderLevel(String levelID, int rows ,int page){
		int result = judge();
		String sql = "";
		if(result == 1){
			sql = "select * " +
				"from "+
				"("+
				"select A.*, rownum num "+ 
				"from (select *from user_union_view) A "+ 
				"where A.levelid = "+levelID+" and rownum <= "+(page*rows)+
				") B "+
				"where B.levelid = "+levelID+" and num > "+(+page-1)*rows;
		}else if(result == 2){
			sql = "select top "+rows+" * " +
			"from user_union_view where levelid = "+levelID+" and userid not in (select top "+rows*(page-1)+
			" userid from user_union_view where levelid = "+levelID+
			" order by oname asc)order by oname asc";
		}
		return find(sql, UserBasic.class);
	}
	
	/**
	 * 级别下的用户总数
	 */
	public int getTotleUnderLevel(String levelid){
		String sql = "select count(*) from user_union_view where levelid = "+levelid;
		return find(sql);
	}
	
	/**
	 * 用户修改级别
	 */
	public void updateUserLevel(String levelID ,String userid){
		String sql = "update t_usertable set levelnumber = "+levelID+" where userid = "+userid;
		update(sql);
	}
}
