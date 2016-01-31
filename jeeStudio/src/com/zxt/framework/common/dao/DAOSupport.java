/**
 * Copyright© 2010 zxt Co. Ltd.
 * All right reserved.
 * 
 */
package com.zxt.framework.common.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Title: DAOSupport Description: Create DateTime: 2010-9-10
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public class DAOSupport extends HibernateDaoSupport implements IDAOSupport {

	/**
	 * 查询工具方法
	 */
	public List find(String queryString) {
		List list = getHibernateTemplate().find(queryString);

		return list;
	}

	/**
	 * 查询工具方法
	 */
	public List find(String queryString, Object value) {
		return getHibernateTemplate().find(queryString, value);
	}

	/**
	 * 查询工具方法
	 */
	public List find(String queryString, Object[] values) {
		return getHibernateTemplate().find(queryString, values);
	}

	/**
	 * 保存工具方法
	 */
	public void create(Object entity) {
		getHibernateTemplate().save(entity);
	}

	/**
	 * 删除工具方法
	 */
	public void delete(Object entity) {
		getHibernateTemplate().delete(entity);
	}

	/**
	 * 执行sql更新
	 */
	public boolean executeSQLUpdate(String updateString) {
		boolean flag = false;
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			Query query = session.createQuery(updateString);
			query.executeUpdate();
			session.flush();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return flag;
	}

	/**
	 * 更新方法
	 */
	public void update(Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 查询全部数据
	 */
	public List findAll() {
		return null;
	}

	/**
	 * 查询全部行
	 */
	public int findTotalRows(String queryString) {
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			Query query = session.createQuery(queryString);
			Object o = query.uniqueResult();
			session.flush();
			return Integer.parseInt(o.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return 0;
	}

	/**
	 * 查询所有页面
	 */
	public List findAllByPage(String paramString, int page, int rows) {
		Session session = getHibernateTemplate().getSessionFactory()
				.openSession();
		try {
			Query q = session.createQuery(paramString);
			q.setFirstResult((page - 1) * rows);
			q.setMaxResults(rows);
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

	/**
	 * 删除全部实体
	 */
	public void deleteAll(Collection entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	/**
	 * hibernate更新方法
	 */
	public void updateAll(Collection entities) {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	/**
	 * 批量保存
	 */
	public void createAll(Collection entities) {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

}