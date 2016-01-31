package com.zxt.compplatform.authority.dao.impl;

import java.util.List;

import com.zxt.compplatform.authority.dao.FieldGrantDao;
import com.zxt.compplatform.authority.entity.FieldGrant;
import com.zxt.compplatform.authority.entity.Role;
import com.zxt.framework.jdbc.ZXTJDBCTemplate;
/**
 * 字段授权
 * 数据持久化操作实现
 * @author GUOWEIXIN
 */
public class FieldGrantDaoImpl extends ZXTJDBCTemplate implements FieldGrantDao{
	/**
	 * 执行添加操作
	 *
	 */
	@Override
	public void addFieldGrant(FieldGrant fieldGrant) {
		// TODO Auto-generated method stub
		String sql = "insert into T_FIELD_GRANT values(?,?,?,?)";
		Object []obj = new Object[]{fieldGrant.getId(),fieldGrant.getRid(),fieldGrant.getTablename(),fieldGrant.getFieldname()};
		create(sql, obj);
	}
	/**
	* 根据 角色RID，删除该角色所有操作
	* @param id
	*/
	@Override
	public void deleteFieldGrant(Long rid) {
		// TODO Auto-generated method stub
		String sql = "delete from T_FIELD_GRANT where rid= "+rid;
		delete(sql);
	}
	/**
	* 根据角色ID，得到其所有表名和字段
	* @param id
	*/
	@Override
	public List getAllByRid(Long rid) {
		// TODO Auto-generated method stub
		String sql = "select id,rid,tableName,fieldName from  T_FIELD_GRANT where rid="+rid;
		return find(sql, FieldGrant.class);
	}
	/**
	* 根据角色ID，和表名，得到其所有表名和字段
	* @param id
	* select * from T_FIELD_GRANT where RID='55' AND tableName='LS_PUTBIOLOGY'
	*/
	@Override
	public List getAllByRidAndTableName(Long rid,String tableName) {
		// TODO Auto-generated method stub
		String sql = "select id,rid,tableName,fieldName from  T_FIELD_GRANT where rid="+rid+"   and  "+"tableName='"+tableName+"'";
		return find(sql, FieldGrant.class);
	}
	
   /**
    * 根据角色ID，得到其所属表名称
    *  select tableName from T_FIELD_GRANT where rid=2 group by tableName 
    *  如果flag==0时，则让其查所有表名称。rid不起作用。如果是其它，flag不起作用
    */	
	public List getTableNameByRid(Long rid,int flag){
		String sql=" select tableName from T_FIELD_GRANT ";
		if(flag!=0){
			sql+="  where  rid="+rid+" ";
		}
		sql+=" group by tableName ";
		return find(sql, FieldGrant.class);
	}
	
}
