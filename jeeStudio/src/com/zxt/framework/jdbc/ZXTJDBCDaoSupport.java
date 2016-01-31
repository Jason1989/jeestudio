package com.zxt.framework.jdbc;

import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import com.zxt.framework.common.dao.IDAOSupport;
import com.zxt.framework.page.dialect.Dialect;

public class ZXTJDBCDaoSupport implements IDAOSupport {

	// private Dialect dialect;
	private ZXTJDBCTemplate zxtJDBCTemplate;
	private Dialect dialect;

	public ZXTJDBCDaoSupport() {
	}

	/**
	 * 设置数据源
	 * 
	 * @param dataSource
	 */
	public final void setDataSource(DataSource dataSource) {
		if (zxtJDBCTemplate == null
				|| dataSource != zxtJDBCTemplate.getDataSource()) {
			zxtJDBCTemplate = createJdbcTemplate(dataSource);
			initTemplateConfig();
		}
	}

	/**
	 * 创建jdbc模板
	 * 
	 * @param dataSource
	 * @return
	 */
	protected ZXTJDBCTemplate createJdbcTemplate(DataSource dataSource) {
		return new ZXTJDBCTemplate(dataSource);
	}

	public final DataSource getDataSource() {
		return zxtJDBCTemplate == null ? null : zxtJDBCTemplate.getDataSource();
	}

	/**
	 * 设置jdbc模板类
	 * 
	 * @param zxtJDBCTemplate
	 */
	public final void setJdbcTemplate(ZXTJDBCTemplate zxtJDBCTemplate) {
		this.zxtJDBCTemplate = zxtJDBCTemplate;
		initTemplateConfig();
	}

	public final ZXTJDBCTemplate getJdbcTemplate() {
		return zxtJDBCTemplate;
	}

	protected void initTemplateConfig() {
	}

	/**
	 * 检查sql配置
	 */
	protected void checkDaoConfig() {
		if (zxtJDBCTemplate == null)
			throw new IllegalArgumentException(
					"'dataSource' or 'jdbcTemplate' is required");
		else
			return;
	}

	// protected final SQLExceptionTranslator getExceptionTranslator()
	// {
	// return getJdbcTemplate().getExceptionTranslator();
	// }
	//
	// protected final Connection getConnection()
	// throws CannotGetJdbcConnectionException
	// {
	// return DataSourceUtils.getConnection(getDataSource());
	// }
	//
	// protected final void releaseConnection(Connection con)
	// {
	// DataSourceUtils.releaseConnection(con, getDataSource()){}
	// }
	/**
	 * 继承父类方法
	 */
	public List find(String paramString) {
		return null;
	}

	/**
	 * 继承父类方法
	 */
	public List find(String paramString, Object paramObject) {
		return null;
	}

	/**
	 * 继承父类方法
	 */
	public List find(String paramString, Object[] paramArrayOfObject) {
		return null;
	}

	/**
	 * 继承父类方法
	 */
	public void create(Object paramObject) {
	}

	/**
	 * 继承父类方法
	 */
	public void delete(Object paramObject) {
	}

	/**
	 * 继承父类方法
	 */
	public boolean executeSQLUpdate(String updateString) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 继承父类方法
	 */
	public void update(Object paramObject) {
	}

	/**
	 * 继承父类方法
	 */
	public void deleteAll(Collection paramCollection) {
	}

	/**
	 * 继承父类方法
	 */
	public void updateAll(Collection paramCollection) {
	}

	/**
	 * 继承父类方法
	 */
	public List findAll() {
		return null;
	}

	/**
	 * 继承父类方法
	 */
	public int findTotalRows(String queryString) {
		return zxtJDBCTemplate.findTotalRows(queryString);
	}

	/**
	 * 继承父类方法
	 */
	public List findAllByPage(String paramString, int page, int rows) {
		return null;
	}

	public void createAll(Collection paramCollection) {
	}

	/**
	 * 继承父类方法
	 */
	public List findToMap(String sql) {
		return zxtJDBCTemplate.findToMaps(sql);
	}

	/**
	 * 继承父类方法
	 */
	public Dialect getDialect() {
		return dialect;
	}

	/**
	 * 继承父类方法
	 */
	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	/**
	 * 继承父类方法
	 */
	public ZXTJDBCTemplate getZxtJDBCTemplate() {
		return zxtJDBCTemplate;
	}

	/**
	 * 继承父类方法
	 */
	public void setZxtJDBCTemplate(ZXTJDBCTemplate zxtJDBCTemplate) {
		this.zxtJDBCTemplate = zxtJDBCTemplate;
	}

	// public Dialect getDialect() {
	// return dialect;
	// }
	//
	// public void setDialect(Dialect dialect) {
	// this.dialect = dialect;
	// }
}
