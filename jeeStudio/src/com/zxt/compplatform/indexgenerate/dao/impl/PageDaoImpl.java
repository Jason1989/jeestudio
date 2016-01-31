package com.zxt.compplatform.indexgenerate.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.zxt.compplatform.indexgenerate.dao.PageDao;
import com.zxt.compplatform.indexgenerate.entity.PageUnit;
import com.zxt.framework.common.dao.DAOSupport;

public class PageDaoImpl extends DAOSupport implements PageDao {

	public PageUnit findById(String subSystemId) {
		List list = getHibernateTemplate().find(
				"from PageUnit pu where pu.id=?", new String[] { subSystemId });
		if (list != null && list.size() > 0) {
			PageUnit pu = (PageUnit) list.get(0);
			return pu;
		}
		return null;
	}

	public List listPage(int page, int rows) {
		Session session = getSession();
		session.beginTransaction();
		String HQL="select new map(pu.id as id ,pu.name as name,pu.description as description) from PageUnit pu";
		Query query = session.createQuery(HQL); 
		if (query != null) {            
			return query.setFirstResult((page-1)*rows).setMaxResults(rows).list();     
		}
		session.getTransaction().commit();
		return getHibernateTemplate().find(HQL);
	}

	public int findTotal() {
		String queryString = " select count(id) from PageUnit mp"; 
		return findTotalRows(queryString);
	}

	public List findmodel(String keyword, int num) {
		String paramString = " from ModelPart m where m.description like '" + keyword + "%' ";
		Session session = getSession();
		Query query = session.createQuery(paramString); 
		return query.setMaxResults(num).list();     
	}

}
