package com.zxt.compplatform.formula.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.zxt.framework.common.dao.DAOSupport;
import com.zxt.framework.common.dao.IDAOSupport;

/**
 * Title: FurmulaDAO
 * Description:  
 * Create DateTime: 2010-10-08
 * @author xxl
 * @since v1.0
 * 
 */
public class FurmulaDAO extends DAOSupport implements IDAOSupport {

	public List find(String queryString) {
		return getHibernateTemplate().find(queryString);
	}

	public List find(String queryString, Object value) {
		return getHibernateTemplate().find(queryString, value);
	}

	public List find(String queryString, Object[] values) {
		return getHibernateTemplate().find(queryString, values);
	}

	public void create(Object entity) {
		getHibernateTemplate().save(entity);
	}

	public void delete(Object entity) {
		getHibernateTemplate().delete(entity);
	}

	public void update(Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	public List findAll() {
		return null;
	}
	public int findTotalRows(String queryString){
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		try{
			Query query = session.createQuery(queryString);
			Object o = query.uniqueResult();
			session.flush();
			return Integer.parseInt(o.toString());
		}catch(Exception e){			
			e.printStackTrace();
		}finally{
			session.close();
		}
		return 0;
	}
	public List findAllByPage(String paramString,int page,int rows){
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		try{
			Query q = session.createQuery(paramString);    
			q.setFirstResult((page-1)*rows);    
			q.setMaxResults(rows);    
			List l = q.list(); 
			session.flush();
			return l;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return null;
	}
	public void deleteAll(Collection entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	public void updateAll(Collection entities) {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	public void createAll(Collection entities) {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}
}
