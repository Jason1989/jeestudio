package com.zxt.framework.dictionary.dao;

import java.util.List;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.framework.common.dao.DAOSupport;
import com.zxt.framework.dictionary.entity.DictionaryGroup;
import com.zxt.framework.jdbc.ZXTJDBCDataSource;
import com.zxt.framework.jdbc.ZXTJDBCTemplate;

public class DataDictionaryDao extends DAOSupport implements IDataDictionaryDao {
	/**
	 * 获取数据源
	 * 
	 * @param dbSource
	 * @return
	 */
	public DataSource getDataSourceById(String id){
		String paramString = " from DataSource t where t.id = '" + id + "' ";
		List list = getHibernateTemplate().find(paramString);
		DataSource dataSource=null;
		if(list != null && list.size()>0){
			dataSource=(DataSource) list.get(0);
		}
		return dataSource;
	}

	/**
	 * 获取数据对象分组
	 * 
	 * @param dbSource
	 * @return
	 */
	public DictionaryGroup getDicGroupById(String dicGroup) {
		String paramString = " from DictionaryGroup g where g.id = '" + dicGroup + "' ";
		List list = getHibernateTemplate().find(paramString);
		DictionaryGroup group=null;
		if(list != null && list.size()>0){
			group=(DictionaryGroup) list.get(0);
		}
		return group;
	}
	/**
	 * 查询全部数据对象名
	 * 
	 * @param dbSource
	 * @return
	 */
	public List findTableName(DataSource dataSource) {
		List result =null;
		if(dataSource!=null){
			String queryClause = new StringBuffer("select s.name " +
					"from sysobjects s left join (select * from sys.extended_properties where minor_id = 0 and name='MS_Description') e on s.id=e.major_id" +
					" where type = 'U'").toString();
			//根据数据源查找表空间
			javax.sql.DataSource ds = new ZXTJDBCDataSource(dataSource.getIpAddress(),dataSource.getPort()+"",dataSource.getSid(),dataSource.getDbType()
					,dataSource.getUsername(),dataSource.getPassword());
			
			ZXTJDBCTemplate jdbcTemplate = new ZXTJDBCTemplate(ds);
			 
			result =jdbcTemplate.findToMaps(queryClause.toString());
		}
		return result;
	}
	/**
	 * 查询全部数据对象列
	 * 
	 * @param dbSource
	 * @return
	 */
	public List findColNames(DataSource dataSource, String string) {
		List result =null;
		if(dataSource!=null){
			String queryClause = new StringBuffer("select name from syscolumns where id=object_id('"+string+"')").toString();
			//根据数据源查找表空间
			javax.sql.DataSource ds = new ZXTJDBCDataSource(dataSource.getIpAddress(),dataSource.getPort()+"",dataSource.getSid(),dataSource.getDbType()
					,dataSource.getUsername(),dataSource.getPassword());
			
			ZXTJDBCTemplate jdbcTemplate = new ZXTJDBCTemplate(ds);
			 
			result =jdbcTemplate.findToMaps(queryClause.toString());
			
		}
		return result;
	}

}
