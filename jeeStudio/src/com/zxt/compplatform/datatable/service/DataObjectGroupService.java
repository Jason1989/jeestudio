package com.zxt.compplatform.datatable.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.datatable.dao.IDataTableDao;
import com.zxt.compplatform.datatable.entity.DataObjectGroup;
/**
 * 数据对象分组业务操作接口
 * 
 * @author 007
 */
public class DataObjectGroupService implements IDataObjectGroupService {
	
	/**
	 * 数据对象持久化接口
	 */
	private IDataTableDao dataTableDao;
	
	/* (non-Javadoc)
	 * @see com.zxt.compplatform.datatable.service.IDataObjectGroupService#delete(com.zxt.compplatform.datatable.entity.DataObjectGroup)
	 */
	public void delete(DataObjectGroup dataObjectGroup) {
		dataTableDao.delete(dataObjectGroup);
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.datatable.service.IDataObjectGroupService#deleteById(java.lang.String)
	 */
	public void deleteById(String id) {
		dataTableDao.delete(findById(id));
	}
	/* (non-Javadoc)
	 * @see com.zxt.compplatform.datatable.service.IDataObjectGroupService#deleteAll(java.util.List)
	 */
	public void deleteAll(List paramCollection) {
		dataTableDao.deleteAll(paramCollection);
	}
	/* (non-Javadoc)
	 * @see com.zxt.compplatform.datatable.service.IDataObjectGroupService#findAll()
	 */
	public List findAll() {
		String paramString = " from DataObjectGroup t "; 
		return dataTableDao.find(paramString);
	}
	/* (non-Javadoc)
	 * @see com.zxt.compplatform.datatable.service.IDataObjectGroupService#findById(java.lang.String)
	 */
	public DataObjectGroup findById(String id) {
		String paramString = " from DataObjectGroup t where t.id = '" + id + "' ";
		List list = dataTableDao.find(paramString);
		if(list != null && list.size()>0){
			return (DataObjectGroup) list.get(0);
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see com.zxt.compplatform.datatable.service.IDataObjectGroupService#findAllByIds(java.lang.String)
	 */
	public List findAllByIds(String ids){
		String paramString = " from DataObjectGroup t where t.id in (" + ids + ")"; 
		return dataTableDao.find(paramString);
	}
	/* (non-Javadoc)
	 * @see com.zxt.compplatform.datatable.service.IDataObjectGroupService#findAllByPid(java.lang.String)
	 */
	public List findAllByPid(String pid){
		String paramString = " from DataObjectGroup t where t.pid = '" + pid + "' order by t.sortNo"; 
		return dataTableDao.find(paramString);
	}
	/* (non-Javadoc)
	 * @see com.zxt.compplatform.datatable.service.IDataObjectGroupService#findParentById(java.lang.String)
	 */
	public DataObjectGroup findParentById(String id){
		String paramString = " from DataObjectGroup t where t.id = ( "; 
		paramString += " select t0.pid from DataObjectGroup t0 where t0.id = '" + id + "') "; 
		List list = dataTableDao.find(paramString);
		if(list != null && list.size()>0){
			return (DataObjectGroup) list.get(0);
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see com.zxt.compplatform.datatable.service.IDataObjectGroupService#insert(com.zxt.compplatform.datatable.entity.DataObjectGroup)
	 */
	public void insert(DataObjectGroup dataObjectGroup) {
		dataTableDao.create(dataObjectGroup);
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.datatable.service.IDataObjectGroupService#update(com.zxt.compplatform.datatable.entity.DataObjectGroup)
	 */
	public void update(DataObjectGroup dataObjectGroup) {
		dataTableDao.update(dataObjectGroup);
	}
	public List findChildrenIdById(String id){
		String paramString ="select t.dg_id as id,t.dg_parent_id as pid from eng_form_dataobject_group t";
		List list = dataTableDao.findChildrenIdById(paramString);
		Iterator iter = list.iterator();
		List resultList = new ArrayList();
		while (iter.hasNext()) {
			Object[] obj = (Object[]) iter.next();
			if(obj[0].toString().equals(id)){
				resultList.add(obj[0].toString());
				findChildren(list,obj[0].toString(),resultList);
			}
		}
		return resultList;
	}
	/**
	 * 查询自节点
	 * @param list
	 * @param pid
	 * @param result
	 */
	public void findChildren(List list,String pid,List result){
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Object[] obj = (Object[]) iter.next();
			if(obj[1].toString().equals(pid)){
				result.add(obj[0].toString());
				findChildren(list,obj[0].toString(),result);
			}
		}
	}
	public void setDataTableDao(IDataTableDao dataTableDao) {
		this.dataTableDao = dataTableDao;
	}

	public DataObjectGroup findByName(String name) {
		String paramString = " from DataObjectGroup t where t.name = '" + name+ "' ";
		List list = dataTableDao.find(paramString);
		if(list != null && list.size()>0){
			return (DataObjectGroup) list.get(0);
		}
		return null;
	}
}
