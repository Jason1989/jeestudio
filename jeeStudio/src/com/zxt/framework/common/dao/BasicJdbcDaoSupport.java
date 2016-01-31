/**
 * Copyright© 2010 zxt Co. Ltd.
 * All right reserved.
 * 
 */
package com.zxt.framework.common.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.zxt.framework.common.exceptions.AppException;
import com.zxt.framework.common.log.LogHelper;
import com.zxt.framework.common.util.DBUtil;

/**
 * 
 * 数据查询Dao层
 * <p>
 * 
 * 修改日志说明：<br>
 * 1、Sep 14, 2011 8:35:45 AM（+） 添加注释
 * </p>
 * 
 * @author 005
 * @version 1.00
 */
public abstract class BasicJdbcDaoSupport extends JdbcDaoSupport {
	protected LogHelper log;

	/**
	 * 构造方法
	 */
	public BasicJdbcDaoSupport() {
		this.log = new LogHelper(super.getClass());
	}

	/**
	 * 查询数据
	 * 
	 * @param obj
	 * @return
	 * @throws AppException
	 */
	public Object doSelect(Object obj) throws AppException {
		return null;
	}

	public Collection doSelectSet(Object obj) throws AppException {
		return null;
	}
	/**
	 * 创建对象
	 * 
	 * @param obj
	 * @return
	 * @throws AppException
	 */
	public Object doCreate(Object obj) throws AppException {
		return null;
	}
	/**
	 * 创建集合对象
	 * 
	 * @param coll
	 * @return
	 * @throws AppException
	 */
	public Collection doCreateSet(Collection coll) throws AppException {
		return null;
	}
	/**
	 * 更新数据
	 * 
	 * @param obj
	 * @return
	 * @throws AppException
	 */
	public void doUpdate(Object obj) throws AppException {
	}
	/**
	 * 更新数据
	 * 
	 * @param obj
	 * @return
	 * @throws AppException
	 */
	public void doUpdateSet(Collection coll) throws AppException {
	}
	/**
	 * 删除对象
	 * 
	 * @param obj
	 * @return
	 * @throws AppException
	 */
	public void doDelete(Object obj) throws AppException {
	}
	/**
	 * 删除集合
	 * 
	 * @param obj
	 * @return
	 * @throws AppException
	 */
	public void doDeleteSet(Collection coll) throws AppException {
	}
	/**
	 * 获取结果集
	 * 
	 * @param obj
	 * @return
	 * @throws AppException
	 */
	protected HashMap getHashMapByRS(ResultSet rs) throws AppException,
			SQLException {
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
	}

	public Object getObjByRS(ResultSet rs, Object oBean) throws AppException {
		try {
			HashMap hm = getHashMapByRS(rs);
			BeanUtils.populate(oBean, hm);
			return oBean;
		} catch (IllegalAccessException e) {
			throw new AppException("0", "���Ϸ��Ķ������" + getSign(), e
					.getMessage());
		} catch (InvocationTargetException e) {
			throw new AppException("0", "������ô���" + getSign(), e
					.getMessage());
		} catch (SQLException ex) {
			throw DBUtil.convertException(ex);
		}
	}
	/**
	 * 取消sqlserver标记
	 * 
	 * @param obj
	 * @return
	 * @throws AppException
	 */
	private final String getSign() {
		return "[" + super.getClass().getName() + "]";
	}
}