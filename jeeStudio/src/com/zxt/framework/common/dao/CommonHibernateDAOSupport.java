package com.zxt.framework.common.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.type.Type;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zxt.framework.common.exceptions.AppException;
import com.zxt.framework.common.util.DBUtil;

public abstract class CommonHibernateDAOSupport extends HibernateDaoSupport {

	public static final int FLUSH_EAGER = 2;

	private static Log log = LogFactory.getLog(CommonHibernateDAOSupport.class);

	private static Map caches = new HashMap();
	private static Map hmListener = new HashMap();

	/**
	 * 初始化hibernate
	 * 
	 * @throws AppException
	 */
	public void initCache() throws AppException {
	}

	/**
	 * 创建hibernate
	 * 
	 * @throws AppException
	 */
	public CommonHibernateDAOSupport() {
		if (!(this instanceof ICacheListener))
			return;
		hmListener.put(super.getClass(), this);
	}

	/**
	 * 刷新hibernate缓存
	 * 
	 * @throws AppException
	 */
	protected void notifyCacheRefresh(Class clazz, int actionType) {
		for (Iterator it = hmListener.values().iterator(); it.hasNext();) {
			ICacheListener listener = (ICacheListener) it.next();
			listener.updateCache(clazz, 4);
			listener.updateCache(clazz, actionType);
		}
	}

	/**
	 * 全部保存
	 * 
	 * @throws AppException
	 */
	public void saveAll(Collection entities) {

	}

	/**
	 * 检查操作权限
	 * 
	 * @throws AppException
	 */
	private void checkWriteOperationAllowed(Session session)
			throws InvalidDataAccessApiUsageException {
		if ((!getHibernateTemplate().isCheckWriteOperations())
				|| (getHibernateTemplate().getFlushMode() == 2)
				|| (!FlushMode.NEVER.equals(session.getFlushMode()))) {
			return;
		}
		throw new InvalidDataAccessApiUsageException(
				"Write operations are not allowed in read-only mode (FlushMode.NEVER) - turn your Session into FlushMode.AUTO or remove 'readOnly' marker from transaction definition");
	}

	/**
	 * 设置参数
	 * 
	 * @throws AppException
	 */
	protected void putParams(PreparedStatement ps, List params)
			throws SQLException {
		if ((params == null) || (params.size() == 0))
			return;
		for (int i = 1; i <= params.size(); ++i)
			ps.setObject(i, params.get(i - 1));
	}

	/**
	 * 获取结果集
	 * 
	 * @throws AppException
	 */
	protected HashMap getHashMapByReS(ResultSet rs) {
		try {
			ResultSetMetaData rsMeta = rs.getMetaData();
			int i = rsMeta.getColumnCount();
			HashMap hm = new HashMap();
			String sColumName = null;
			String value = null;
			for (; i > 0; --i) {
				sColumName = rsMeta.getColumnName(i).toLowerCase();
				value = rs.getString(sColumName);
				hm.put(sColumName, value);
			}
			return hm;
		} catch (SQLException e) {
			throw DBUtil.convertException(e);
		}
	}

	/**
	 * 获取结果集对象
	 * 
	 * @throws AppException
	 */
	public Object getObjByReS(ResultSet rs, Object oBean) {
		try {
			HashMap hm = getHashMapByReS(rs);
			BeanUtils.populate(oBean, hm);
			return oBean;
		} catch (IllegalAccessException e) {
			throw new DataAccessException("锟斤拷锟较凤拷锟侥讹拷锟斤拷锟斤拷锟�" + getSign()) {
				private static final long serialVersionUID = 2824558151892360419L;
			};
		} catch (InvocationTargetException e) {
			throw new DataAccessException("锟斤拷锟斤拷锟斤拷么锟斤拷锟�" + getSign()) {
				private static final long serialVersionUID = 1011938197463188956L;
			};
		}
	}

	/**
	 * 执行hibernate语句
	 * 
	 * @throws AppException
	 */
	public Object execute(HibernateCallback action) {
		return getHibernateTemplate().execute(action);
	}

	/**
	 * 执行hibernate语句
	 * 
	 * @throws AppException
	 */
	public Object execute(HibernateCallback action, boolean exposeNativeSession) {
		return getHibernateTemplate().execute(action, exposeNativeSession);
	}

	/**
	 * 执行hibernate查询语句
	 * 
	 * @throws AppException
	 */
	public List executeFind(HibernateCallback action) {
		return getHibernateTemplate().executeFind(action);
	}

	/**
	 * 获取对象序列化模板
	 * 
	 * @throws AppException
	 */
	public Object get(Class clazz, String id) {
		return get(clazz, id, null);
	}

	/**
	 * 执行hibernate 对象序列化
	 * 
	 * @throws AppException
	 */
	public Object get(Class clazz, Serializable id) {
		return get(clazz, id, null);
	}

	/**
	 * 获取hibernate序列化的对象
	 * 
	 * @throws AppException
	 */
	public Object get(Class entityClass, Serializable id, LockMode lockMode) {
		return getHibernateTemplate().get(entityClass, id, lockMode);
	}

	/**
	 * 加载hibernate序列化对象。
	 * 
	 * @throws AppException
	 */
	public Object load(Class entityClass, Serializable id) {
		return load(entityClass, id, null);
	}

	/**
	 * 加载hibernate序列化对象。
	 * 
	 * @throws AppException
	 */
	public Object load(Class entityClass, Serializable id, LockMode lockMode) {
		return getHibernateTemplate().load(entityClass, id, lockMode);
	}

	/**
	 * 加载加载全部实体对象。
	 * 
	 * @throws AppException
	 */
	public List loadAll(Class entityClass) {
		return getHibernateTemplate().loadAll(entityClass);
	}

	/**
	 * 加载加载实体对象。
	 * 
	 * @throws AppException
	 */
	public void load(Object entity, Serializable id) {
		getHibernateTemplate().load(entity, id);
	}

	/**
	 * 刷新实体对象。
	 * 
	 * @throws AppException
	 */
	public void refresh(Object entity) {
		refresh(entity, null);
	}

	/**
	 * 刷新持久态实体对象。
	 * 
	 * @throws AppException
	 */
	public void refresh(Object entity, LockMode lockMode) {
		getHibernateTemplate().refresh(entity, lockMode);
	}

	/**
	 * 判断当前持久是否包含改实体。
	 * 
	 * @throws AppException
	 */
	public boolean contains(Object entity) {
		return getHibernateTemplate().contains(entity);
	}

	/**
	 * 移除实体。
	 * 
	 * @throws AppException
	 */
	public void evict(Object entity) {
		getHibernateTemplate().evict(entity);
	}

	/**
	 * 序列化代理实体。
	 * 
	 * @throws AppException
	 */
	public void initialize(Object proxy) {
		getHibernateTemplate().initialize(proxy);
	}

	/**
	 * 锁定实体
	 * 
	 * @param entity
	 * @param lockMode
	 */
	public void lock(Object entity, LockMode lockMode) {
		getHibernateTemplate().lock(entity, lockMode);
	}

	/**
	 * 保存实体
	 * 
	 * @param entity
	 * @return
	 */
	public Serializable save(Object entity) {
		return getHibernateTemplate().save(entity);
	}

	/**
	 * 保存
	 * 
	 * @param entity
	 * @param id
	 */
	public void save(Object entity, Serializable id) {
		getHibernateTemplate().save(entity);
	}

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	public void update(Object entity) {
		update(entity, null);
	}

	/**
	 * 更新
	 * 
	 * @param entity
	 * @param lockMode
	 */
	public void update(Object entity, LockMode lockMode) {
		getHibernateTemplate().update(entity, lockMode);
	}

	/**
	 * 更新
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 更新
	 * 
	 * @param entity
	 */
	public void saveOrUpdateAll(Collection entities) {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	/**
	 * 替换持久太实体
	 * 
	 * @param entity
	 */
	public Object saveOrUpdateCopy(Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);
		return "success";
	}

	/**
	 * 删除实体
	 * 
	 * @param entity
	 */
	public void delete(Object entity) {
		delete(entity, null);
	}

	/**
	 * 删除实体
	 * 
	 * @param entity
	 * @param lockMode
	 */
	public void delete(Object entity, LockMode lockMode) {
		getHibernateTemplate().delete(entity, lockMode);
	}

	/**
	 * 删除全部实体
	 * 
	 * @param entities
	 */
	public void deleteAll(Collection entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	/**
	 * 刷新缓存
	 */
	public void flush() {
		getHibernateTemplate().flush();
	}

	/**
	 * 清除实体
	 */
	public void clear() {
		getHibernateTemplate().clear();
	}

	/**
	 * 查询集合
	 * 
	 * @param queryString
	 * @param paramName
	 * @param value
	 * @return
	 */
	public List findByNamedParam(String queryString, String paramName,
			Object value) {
		return findByNamedParam(queryString, paramName, value, null);
	}

	/**
	 * 查询集合
	 * 
	 * @param queryString
	 * @param paramName
	 * @param value
	 * @param type
	 * @return
	 */
	public List findByNamedParam(String queryString, String paramName,
			Object value, Type type) {
		return findByNamedParam(queryString, new String[] { paramName },
				new Object[] { value }, new Type[] { type });
	}

	/**
	 * 查询实体
	 * 
	 * @param queryString
	 * @param paramNames
	 * @param values
	 * @return
	 */
	public List findByNamedParam(String queryString, String[] paramNames,
			Object[] values) {
		return findByNamedParam(queryString, paramNames, values, null);
	}

	/**
	 * 对象参数查询
	 * 
	 * @param queryString
	 * @param paramNames
	 * @param values
	 * @return
	 */
	public List findByNamedParam(String queryString, String[] paramNames,
			Object[] values, Type[] types) {
		return getHibernateTemplate().findByNamedParam(queryString, paramNames,
				values);
	}

	/**
	 * 查询valuebean
	 * 
	 * @param queryString
	 * @param valueBean
	 * @return
	 */
	public List findByValueBean(String queryString, Object valueBean) {
		return getHibernateTemplate().findByValueBean(queryString, valueBean);
	}

	/**
	 * 查询and封装实体类
	 * @param queryName
	 * @param valueBean
	 * @return
	 */
	public List findByNamedQueryAndValueBean(String queryName, Object valueBean) {
		return getHibernateTemplate().findByNamedQueryAndValueBean(queryName,
				valueBean);
	}
	/**
	 * 关闭迭代器
	 * @param queryName
	 * @param valueBean
	 * @return
	 */
	public void closeIterator(Iterator it) {
		getHibernateTemplate().closeIterator(it);
	}
	/**
	 * 拼接查询字符串语句
	 * @param queryName
	 * @param valueBean
	 * @return
	 */
	private final String getSign() {
		return "[" + super.getClass().getName() + "]";
	}
	/**
	 * 全表检索
	 * @param queryName
	 * @param valueBean
	 * @return
	 */
	public String geneAcctYearGovSqlFilter(String tablecode) {
		return null;
	}
	/**
	 * 全表检索
	 * @param queryName
	 * @param valueBean
	 * @return
	 */
	public String geneAcctYearGovSqlFilter(String tablecode, String alias) {
		return null;
	}
	/**
	 * 设置全表检索
	 * @param queryName
	 * @param valueBean
	 * @return
	 */
	public void setAcctYearGov(String tablecode, Object entity) {
	}

	public void setAcctYearGov(String tablecode, List entityList) {
	}
}