package com.zxt.compplatform.authority.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zxt.compplatform.authority.dao.FieldGrantDao;
import com.zxt.compplatform.authority.entity.FieldGrant;
import com.zxt.compplatform.authority.service.FieldGrantService;


public class FieldGrantServiceImpl implements FieldGrantService{
	//持久化DAO
	private FieldGrantDao fieldGrantDao;	
	public FieldGrantDao getFieldGrantDao() {
		return fieldGrantDao;
	}
	public void setFieldGrantDao(FieldGrantDao fieldGrantDao) {
		this.fieldGrantDao = fieldGrantDao;
	}
	
	/**
	 * 执行添加操作
	 *
	 */
	 public void addFieldGrant(FieldGrant fieldGrant){
		 fieldGrantDao.addFieldGrant(fieldGrant);
	 }
	/**
	* 根据 角色RID，删除该角色所有操作
	* @param id
	*/
	public void deleteFieldGrant(Long rid){
		fieldGrantDao.deleteFieldGrant(rid);
	}
	
	/**
	* 根据角色ID，得到其所有表名和字段
	* @param id
	*/
	public List getAllByRid(Long rid){
		return fieldGrantDao.getAllByRid(rid);
	}
	 /**
	    * 根据角色ID，得到其所属表名称
	    *  select tableName from T_FIELD_GRANT where rid=2 group by tableName 
	    *  如果flag==0时，则让其查所有表名称。rid不起作用。如果是其它，flag不起作用
	    */	
		public List getTableNameByRid(Long rid,int flag){
			return fieldGrantDao.getTableNameByRid(rid, flag);
		}
		
		/**
		* 根据角色ID，和表名，得到其所有表名和字段
		* @param id
		* select * from T_FIELD_GRANT where RID='55' AND tableName='LS_PUTBIOLOGY'
		*/
		public List getAllByRidAndTableName(Long rid,String tableName){
			return fieldGrantDao.getAllByRidAndTableName(rid, tableName);
		}
		/**
		 * 加载 cache
		 * GUOWEIXIN
		 * @param url
		 * @return
		 */
		public Map<Long,Map> load_service(Long  rid){
			List listAll=this.getAllByRid(rid);//根据角色ID，得到所有的。
			List listTableName=this.getTableNameByRid(rid,1);//得到该角色下的所有表名
			Map<Long,Map> mapRoot=new HashMap<Long,Map>();;//key:角色ID，value:Map<表名,[字段名的列表]>
			Map<String,List> mapTable=new HashMap<String,List>();//key:表名,list:字侧面名
			if(listAll!=null){
				if(listAll.size()<1) return null;
			}else return null;
			for(int i=0;i<listTableName.size();i++){
				List fieldList=null;//字段列List
				FieldGrant f=(FieldGrant)listTableName.get(i);
				String tableNameGroup=f.getTablename();
				fieldList=new ArrayList();
				for(int j=0;j<listAll.size();j++){			
						FieldGrant fg=(FieldGrant)listAll.get(j); 						
						if(tableNameGroup.equals(fg.getTablename())){
							fieldList.add(fg.getFieldname());
						}else continue;												
					}
				mapTable.put(tableNameGroup,fieldList);
			}
			    mapRoot.put(rid, mapTable);
			return  mapRoot;
		}
		/**
		 * 更新cache
		 * GUOWEIXIN
		 * @param url
		 * @return
		 */
		public Map<Long,Map> update_service(Long rid){
			List listAll=this.getAllByRid(rid);//根据角色ID，得到所有的。
			List listTableName=this.getTableNameByRid(rid,1);//得到该角色下的所有表名
			Map<Long,Map> mapRoot=new HashMap<Long,Map>();;//key:角色ID，value:Map<表名,[字段名的列表]>
			Map<String,List> mapTable=new HashMap<String,List>();//key:表名,list:字侧面名
			if(listAll!=null){
				if(listAll.size()<1) return null;
			}else return null;
			for(int i=0;i<listTableName.size();i++){
				List fieldList=null;//字段列List
				FieldGrant f=(FieldGrant)listTableName.get(i);
				String tableNameGroup=f.getTablename();
				fieldList=new ArrayList();
				for(int j=0;j<listAll.size();j++){			
						FieldGrant fg=(FieldGrant)listAll.get(j); 						
						if(tableNameGroup.equals(fg.getTablename())){
							fieldList.add(fg.getFieldname());
						}else continue;												
					}
				mapTable.put(tableNameGroup,fieldList);
			}
			    mapRoot.put(rid, mapTable);
			return  mapRoot;
		}
}
