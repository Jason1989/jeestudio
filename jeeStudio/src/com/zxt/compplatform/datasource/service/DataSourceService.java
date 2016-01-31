package com.zxt.compplatform.datasource.service;

import java.sql.SQLException;
import java.util.List;
import com.zxt.compplatform.datasource.dao.IDataSourceDao;
import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.framework.jdbc.ZXTJDBCDataSource;
import com.zxt.framework.jdbc.ZXTJDBCTemplate;

/**
 * 数据源业务操作接口实现
 * 
 * @author 007
 */
public class DataSourceService implements IDataSourceService {

	/**
	 * 数据源持久化操作
	 */
	private IDataSourceDao dataSourceDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datasource.service.IDataSourceService#delete(com.zxt.compplatform.datasource.entity.DataSource)
	 */
	public void delete(DataSource dataSource) {
		dataSourceDao.delete(dataSource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datasource.service.IDataSourceService#deleteAll(java.util.List)
	 */
	public void deleteAll(List paramCollection) {
		dataSourceDao.deleteAll(paramCollection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datasource.service.IDataSourceService#testSQL(java.lang.String,
	 *      java.lang.String)
	 */
	public List testSQL(String datasourceID, String sql) throws SQLException {
		DataSource dataSource = findById(datasourceID);
		if (dataSource == null) {
			throw new NullPointerException("数据源不存在");
		}
		javax.sql.DataSource targetDataSource = new ZXTJDBCDataSource(
				dataSource);

		ZXTJDBCTemplate template = new ZXTJDBCTemplate(targetDataSource);

		List result = template.findToListMaps(sql);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datasource.service.IDataSourceService#deleteById(java.lang.String)
	 */
	public void deleteById(String id) {
		dataSourceDao.delete(findById(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datasource.service.IDataSourceService#findAll()
	 */
	public List findAll() {
		String paramString = " from DataSource t order by t.name ";
		return dataSourceDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datasource.service.IDataSourceService#findAllAvailable()
	 */
	public List findAllAvailable() {
		String paramString = " from DataSource t where t.state = '1' order by t.name ";
		return dataSourceDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datasource.service.IDataSourceService#findAllByIds(java.lang.String)
	 */
	public List findAllByIds(String ids) {
		String paramString = " from DataSource t where t.id in (" + ids
				+ ") order by t.name ";
		return dataSourceDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datasource.service.IDataSourceService#findTotalRows()
	 */
	public int findTotalRows() {
		String queryString = " select count(id) from DataSource t ";
		return dataSourceDao.findTotalRows(queryString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datasource.service.IDataSourceService#findAllByPage(int,
	 *      int)
	 */
	public List findAllByPage(int page, int rows) {
		String paramString = " from DataSource t order by t.name ";
		return dataSourceDao.findAllByPage(paramString, page, rows);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datasource.service.IDataSourceService#findById(java.lang.String)
	 */
	public DataSource findById(String id) {
		String paramString = " from DataSource t where t.id = '" + id + "' ";
		List list = dataSourceDao.find(paramString);
		if (list != null && list.size() > 0) {
			return (DataSource) list.get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datasource.service.IDataSourceService#isExist(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean isExist(String id, String name) {
		String paramString = " from DataSource t where t.id = '" + id
				+ "' or t.name = '" + name + "' ";
		List list = dataSourceDao.find(paramString);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datasource.service.IDataSourceService#isExistUpdate(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean isExistUpdate(String id, String name) {
		String paramString = " from DataSource t where t.id <> '" + id
				+ "' and t.name = '" + name + "' ";
		List list = dataSourceDao.find(paramString);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datasource.service.IDataSourceService#findByName(java.lang.String)
	 */
	public DataSource findByName(String name) {
		String paramString = " from DataSource t where t.name = '" + name
				+ "' order by t.name ";
		List list = dataSourceDao.find(paramString);
		if (list != null && list.size() > 0) {
			return (DataSource) list.get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datasource.service.IDataSourceService#insert(com.zxt.compplatform.datasource.entity.DataSource)
	 */
	public void insert(DataSource dataSource) {
		dataSourceDao.create(dataSource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datasource.service.IDataSourceService#update(com.zxt.compplatform.datasource.entity.DataSource)
	 */
	public void update(DataSource dataSource) {
		dataSourceDao.update(dataSource);

	}

	public void setDataSourceDao(IDataSourceDao dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}

	/**
	 * 检查数据源是否被使用
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String checkDataSourceIsUse(String ids) {
		// TODO Auto-generated method stub

		String sql = " SELECT COUNT(do_id) FROM DataTable WHERE DO_DS_ID ='" + ids
				+ "' ";

		String sql2 = " SELECT  COUNT(DIC_ID) "
				+ " FROM DataDictionary " + " WHERE DIC_DS_ID ='" + ids
				+ "' " ;
		@SuppressWarnings("unused")
		int count = dataSourceDao.findTotalRows(sql)+dataSourceDao.findTotalRows(sql2);
		
		if (count>0) {
			return "use";
		}else {
			return "notuse";
		}
	}
}