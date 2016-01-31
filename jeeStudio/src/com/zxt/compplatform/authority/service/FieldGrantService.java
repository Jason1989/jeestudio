package com.zxt.compplatform.authority.service;

import java.util.List;
import java.util.Map;

import com.zxt.compplatform.authority.entity.FieldGrant;

public interface FieldGrantService {
	/**
	 * 执行添加操作
	 *
	 */
	 public void addFieldGrant(FieldGrant fieldGrant);
	/**
	* 根据 角色RID，删除该角色所有操作
	* @param id
	*/
	public void deleteFieldGrant(Long rid);
	
	/**
	* 根据角色ID，得到其所有表名和字段
	* @param id
	*/
	public List getAllByRid(Long rid);
	 /**
	    * 根据角色ID，得到其所属表名称
	    *  select tableName from T_FIELD_GRANT where rid=2 group by tableName 
	    *  如果flag==0时，则让其查所有表名称。rid不起作用。如果是其它，flag不起作用
	    */	
		public List getTableNameByRid(Long rid,int flag);
		/**
		* 根据角色ID，和表名，得到其所有表名和字段
		* @param id
		* select * from T_FIELD_GRANT where RID='55' AND tableName='LS_PUTBIOLOGY'
		*/
		public List getAllByRidAndTableName(Long rid,String tableName);	
		
		/**
		 * 加载 cache
		 * GUOWEIXIN
		 * @param url
		 * @return
		 */
		public Map<Long,Map> load_service(Long  rid);
		/**
		 * 更新 cache
		 * GUOWEIXIN
		 * @param url
		 * @return
		 */
		public Map<Long,Map> update_service(Long  rid);
}
