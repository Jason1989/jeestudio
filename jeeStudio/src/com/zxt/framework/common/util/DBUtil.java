package com.zxt.framework.common.util;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.zxt.framework.common.exceptions.AppException;

public class DBUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	private static final Log log = LogFactory.getLog(DBUtil.class);

	private static final SQLErrorCodeSQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator(
			"Oracle");

	public static AppException convertException(DataAccessException ex) {
		if (ex.getCause() instanceof AppException) {
			return (AppException) ex.getCause();
		}
		if (ex instanceof UncategorizedSQLException) {
			System.err.println(((SQLException) ex.getCause()).getLocalizedMessage());
			return new AppException("0", "以下SQL语句执行错误,请与系统管理员联系: \n"
					+ ((UncategorizedSQLException) ex).getCause().getLocalizedMessage(), "");
		}

		if (ex.getCause() instanceof SQLException) {
			System.err.println(((SQLException) ex.getCause()).getLocalizedMessage());
			int errorCode = ((SQLException) ex.getCause()).getErrorCode();
			switch (errorCode) {
			case 942:
				return new AppException("20007");
			case 1:
				return new AppException("20009");
			case 1400:
				return new AppException("20010");
			case 2292:
				return new AppException("20011");
			}
		} else {
			if (ex instanceof ObjectOptimisticLockingFailureException) {
				return new AppException("20008");
			}
			if (ex instanceof DataAccessResourceFailureException) {
				return new AppException("20001");
			}
			if (ex instanceof DataIntegrityViolationException) {
				return new AppException("20002");
			}
			if (ex instanceof BadSqlGrammarException) {
				return new AppException("20003");
			}
			if (ex instanceof InvalidResultSetAccessException) {
				return new AppException("20004");
			}
			if (ex instanceof CannotAcquireLockException) {
				return new AppException("20005");
			}
		}
		return new AppException("0", ex.getMessage(), ex.getMessage());
	}

	public static DataAccessException convertException(SQLException ex) {
		return translator.translate(null, null, ex);
	}

	public static DataAccessException convertException(HibernateException ex) {
		if (ex instanceof JDBCException) {
			return convertJdbcAccessException((JDBCException) ex);
		}
		return SessionFactoryUtils.convertHibernateAccessException(ex);
	}

	protected static DataAccessException convertJdbcAccessException(JDBCException ex) {
		return translator.translate("Hibernate operation: " + ex.getMessage(), null, ex.getSQLException());
	}

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}

	public static Object getBeanByClass(Class clazz) {
		Map map = applicationContext.getBeansOfType(clazz);
		for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
			Object bean = map.get(iter.next());
			if (null != bean) {
				return bean;
			}
		}
		return null;
	}

	public static Object getBeanByName(String name) {
		return applicationContext.getBean(name);
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Method getMethod(Class clazz, String methodName) {
		return BeanUtils.findMethodWithMinimalParameters(clazz, methodName);
	}
}