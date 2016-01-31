package com.zxt.compplatform.menu.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.zxt.compplatform.menu.entity.EngFunctionMenu;
import com.zxt.framework.common.dao.DAOSupport;

public class FunctionMenuDaoImpl extends DAOSupport implements FunctionMenuDao {

	public List findMenus(final long parentId) {
		return (List) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "from EngFunctionMenu a where a.menuParentId = ? and a.menuState = ? and a.menuIsDelete = ?";
				return session.createQuery(hql).setLong(0, parentId)
				                               .setLong(1, EngFunctionMenu.MENU_STATE_ENABLE)
				                               .setLong(2, EngFunctionMenu.MENU_DELETE_FALSE)
				                               .list();
			}
		});
	}

	public List findParentMenus() {
		return (List) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "from EngFunctionMenu a where  a.menuParentId = ? and a.menuState = ? and a.menuIsDelete = ? order by menu_sort ";
				return session.createQuery(hql).setLong(0, EngFunctionMenu.MENU_ROOT_PARENT_ID)
				                               .setLong(1, EngFunctionMenu.MENU_STATE_ENABLE)
				                               .setLong(2, EngFunctionMenu.MENU_DELETE_FALSE)
				                               .list();
			}
		});
	}

	public List findMenus() {
		return (List) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String hql = "from EngFunctionMenu a where  a.menuState = ? and a.menuIsDelete = ? order by menu_sort";
				return session.createQuery(hql).setLong(0, EngFunctionMenu.MENU_STATE_ENABLE)
				                               .setLong(1, EngFunctionMenu.MENU_DELETE_FALSE)
				                               .list();
			}
		});
	}

}
