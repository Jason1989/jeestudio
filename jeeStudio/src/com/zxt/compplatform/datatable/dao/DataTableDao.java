package com.zxt.compplatform.datatable.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.zxt.compplatform.datatable.entity.DataObjectMenu;
import com.zxt.framework.common.dao.DAOSupport;

/**
 * 数据对象持久化操作实现
 * 
 * @author 007
 */
public class DataTableDao extends DAOSupport implements IDataTableDao {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.dao.IDataTableDao#findChildrenIdById(java.lang.String)
	 */
	public List findChildrenIdById(String paramString) {
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			SQLQuery q = session.createSQLQuery(paramString);
			List l = q.list();
			session.flush();
			return l;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.dao.IDataTableDao#findChildrenByParentId(java.lang.String)
	 */
	public List findChildrenByParentId(String parentId) {
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			StringBuffer paramString = new StringBuffer(
					"select id,name,type,parentid from (");
			paramString
					.append("select g.dg_id as id,g.dg_name as name ,'0' as type,g.dg_parent_id as parentid from eng_form_dataobject_group g ");
			paramString
					.append("union select d.do_id as id,d.do_name as name ,'1' as type,d.do_group_id as parentid from eng_form_dataobject d");
			paramString.append(") t where t.parentid = '").append(parentId)
					.append("' ");
			SQLQuery q = session.createSQLQuery(paramString.toString())
					.addEntity("t", DataObjectMenu.class);
			List l = q.list();
			session.flush();
			return l;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

}
