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
 * Title: JDBCDAOSupport
 * Description:  
 * Create DateTime: 2010-9-10
 * @author xxl
 * @since v1.0
 * 
 */
public abstract class JDBCDAOSupport extends JdbcDaoSupport {
	protected LogHelper log;

	public JDBCDAOSupport() {
		this.log = new LogHelper(super.getClass());
	}

	public Object doSelect(Object obj) throws AppException {
		return null;
	}

	public Collection doSelectSet(Object obj) throws AppException {
		return null;
	}

	public Object doCreate(Object obj) throws AppException {
		return null;
	}

	public Collection doCreateSet(Collection coll) throws AppException {
		return null;
	}

	public void doUpdate(Object obj) throws AppException {
	}

	public void doUpdateSet(Collection coll) throws AppException {
	}

	public void doDelete(Object obj) throws AppException {
	}

	public void doDeleteSet(Collection coll) throws AppException {
	}

	protected HashMap getHashMapByRS(ResultSet rs) throws AppException, SQLException {
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
			throw new AppException("0", "���Ϸ��Ķ������" + getSign(), e.getMessage());
		} catch (InvocationTargetException e) {
			throw new AppException("0", "������ô���" + getSign(), e.getMessage());
		} catch (SQLException ex) {
			throw DBUtil.convertException(ex);
		}
	}

	private final String getSign() {
		return "[" + super.getClass().getName() + "]";
	}
}