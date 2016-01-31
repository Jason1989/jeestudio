package com.zxt.compplatform.datatable.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zxt.compplatform.datasource.dao.IDataSourceDao;
import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.datasource.service.IDataSourceService;
import com.zxt.compplatform.datatable.dao.IDataTableDao;
import com.zxt.compplatform.datatable.entity.DataObjectGroup;
import com.zxt.compplatform.datatable.entity.DataTable;
import com.zxt.compplatform.formengine.service.SystemFrameService;

/**
 * 数据对象业务操作接口
 * 
 * @author 007
 */
public class DataTableService implements IDataTableService {

	/**
	 * 数据对象持久化接口
	 */
	private IDataTableDao dataTableDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#delete(com.zxt.compplatform.datatable.entity.DataTable)
	 */
	public void delete(DataTable dataTable) {
		dataTableDao.delete(dataTable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#deleteById(java.lang.String)
	 */
	public void deleteById(String id) {
		dataTableDao.delete(findById(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#deleteAll(java.util.List)
	 */
	public void deleteAll(List paramCollection) {
		dataTableDao.deleteAll(paramCollection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#deleteAllByDataSourceId(java.lang.String)
	 */
	public void deleteAllByDataSourceId(String dataSourceId) {
		List list = findAllByDataSourceId(dataSourceId);
		if (list != null && list.size() > 0) {
			dataTableDao.deleteAll(list);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#findAllLikeName(java.lang.String)
	 */
	public List findAllLikeName(String nameArgs) {
		String paramString = " from DataTable t where t.name like '%"
				+ nameArgs + "%' order by t.sortNo asc ,t.createTime desc ";
		return dataTableDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#findAllByDataObjectIdAndLikeName(java.lang.String,
	 *      java.lang.String)
	 */
	public List findAllByDataObjectIdAndLikeName(String dataObjectId,
			String nameArgs) {
//		String paramString = " from DataTable t where t.id = '" + dataObjectId
//				+ "' or t.name like '%" + nameArgs
//				+ "%' order by t.name asc  ";
	//根据数据源分组	
		String paramString = "  from DataTable t   where t.dataSource.id =(select dataSource.id from DataTable where id='" + dataObjectId
		+ "')   and t.name like '%" + nameArgs
		+ "%' and t.id !='"+dataObjectId+"'   order by t.name asc  ";
		// from DataTable t,DataSource s where s.id='' and  s.id=t.dataSource.id
		
		/***
		 *  GUOWEIXIN:此处注释
		 */
		//return dataTableDao.find(paramString);
		return dataTableDao.findAllByPage(paramString, 1, 10);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#findAll()
	 */
	public List findAll() {
		String paramString = " from DataTable t order by t.sortNo asc ,t.createTime desc ";
		return dataTableDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#findByGroupId(java.lang.String)
	 */
	public List findByGroupId(String groupId) {
		String paramString = " from DataTable t where t.dataObjectGroup.id = '"
				+ groupId + "' ";
		return dataTableDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#findAllByIds(java.lang.String)
	 */
	public List findAllByIds(String ids) {
		String paramString = " from DataTable t where t.id in (" + ids
				+ ")  order by t.sortNo asc ,t.createTime desc ";
		return dataTableDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#findAllByDataSourceId(java.lang.String)
	 */
	public List findAllByDataSourceId(String dataSourceId) {
		String paramString = " from DataTable t where t.dataSource.id = '"
				+ dataSourceId + "'";
		return dataTableDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#findTotalRows()
	 */
	public int findTotalRows() {
		String queryString = " select count(id) from DataTable t ";
		return dataTableDao.findTotalRows(queryString);
	}

	/**
	 * GUOWEIXIN str传过来的是ID。此方法返回的总数 不包括传过来的ID
	 */
	public int findTotalRowsByIsNotId(List<String> str, int flag,String mainObjectId) {
		String queryString = " select count(id) from DataTable t ";
		 if(str.size()==0){
			 if(flag==0) queryString += "where t.name  not in ('null')";
			 if(flag==1) queryString += "where t.name   in ('null')";	 
		 }else{
			if (flag == 0)
				queryString += "where t.name  not in (";
			if (flag == 1)
				queryString += "where t.name   in (";
			for (int i = 0; i < str.size(); i++) {
				if (str.size() - 1 == i)
					queryString += "'" + str.get(i) + "')";
				else
					queryString += "'" + str.get(i) + "',";
			}
			//此行是根据数据源分组查询。仅查询该数据源下方的表
			queryString+="  and t.dataSource.id =(select dataSource.id from DataTable where id='" + mainObjectId+ "')";
		 }
		return dataTableDao.findTotalRows(queryString);
	}

	/**
	 * GUOWEIXIN str传过来的是ID。此分页返回的列表中 不包括传过来的ID值 param: flag:如果传过来的是0，则str中的ID
	 * 查不出来。如果等于1，则str[]中的值
	 */
	public List findAllByPageByIsNotId(int page, int rows, List<String> str,
			int flag,String mainObjectId) {
		String paramString = " from DataTable t   ";
		 if(str.size()==0){
			 if(flag==0) paramString += "where t.name  not in ('null')";
			 if(flag==1) paramString += "where t.name   in ('null')";	
			 
		 }else{
			if (flag == 0 )
				paramString += "where t.name  not in (";		
			if (flag == 1)
				paramString += "where t.name   in (";
			for (int i = 0; i < str.size(); i++) {
				if (str.size() - 1 == i)
					paramString += "'" + str.get(i) + "')";
				else
					paramString += "'" + str.get(i) + "',";
			}
		 }
		//此行是根据数据源分组查询。仅查询该数据源下方的表
		 paramString+="  and t.dataSource.id =(select dataSource.id from DataTable where id='" + mainObjectId+ "')";
		 
		 paramString+="  order by t.name asc ";
		return dataTableDao.findAllByPage(paramString, page, rows);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#findAllByPage(int,
	 *      int)
	 */
	public List findAllByPage(int page, int rows) {
		String paramString = " from DataTable t  order by t.sortNo asc ,t.createTime desc ";
		return dataTableDao.findAllByPage(paramString, page, rows);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#findTotalRowsByGroupIds(java.lang.String)
	 */
	public int findTotalRowsByGroupIds(String groupIds) {
		if("".equals(groupIds)){
			groupIds="-1";
		}
		String queryString = " select count(id) from DataTable t where t.dataObjectGroup.id in ("
				+ groupIds + ") ";
		return dataTableDao.findTotalRows(queryString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#findAllByPageAndGroupIds(int,
	 *      int, java.lang.String)
	 */
	public List findAllByPageAndGroupIds(int page, int rows, String groupIds) {
		String paramString = " from DataTable t where t.dataObjectGroup.id in ("
				+ groupIds + ") order by t.name asc  ";
		return dataTableDao.findAllByPage(paramString, page, rows);
	}
	/**
	 * 字段授权。根据父级ID，得到其下属表 GUOWEIXIN
	 */
	public int findTotalRowsByGroupIdsFieldGrant(String groupIds) {
		
		//GUOWEIXIN configSQL.properties 文件
		Map map=systemFrameService.load_serviceConfigSQL();
		String falgChoose=(String)map.get("is_use_reference_dataSource");
		String key="DataTableService-findTotalRowsByGroupIdsFieldGrant";
		String sqlTest=(String)map.get(key);
		if("true".equals(falgChoose) && null!=(String)map.get(key)){			
			/**
			 * 此处的groupIds的值是现在要根据其 分组ID得到其 数据库实例名称。
			 * 根据实例名称去  现有库 得到其 数据源ID
			 */
//			DataSource dataSource=null;
//			String paramString = "from DataSource t where t.id='"+groupIds+"'";
//			List list=dataSourceDao.find(paramString);
//			if(list!=null && list.size()>0){
//				 dataSource=(DataSource)list.get(0);
//			}
			// 获取缓存中的连接池
			Map poolsMap = systemFrameService.load_connectPools("true");
			String mapStr=(String)map.get("is_use_reference_dataSourceID");
			String dataSourceID=map.get("is_use_reference_dataSourceID").toString();
			ComboPooledDataSource   connectPool=(ComboPooledDataSource) poolsMap.get(dataSourceID);
			
			
			String sql=(String)map.get(key);
			Connection con=null;
			PreparedStatement pst=null;
			ResultSet rst=null;
			int count=-1;
//			String groupId=null;
//			try{
//				if(dataSource!=null){
//					String secondSql="select DATASOURCEID  from SGS_DIGICITY_MDS.CORE_DM_DATASOURCEINFO  where DATABASENAME='"+dataSource.getSid()+"'";
//					con=connectPool.getConnection();
//					pst=con.prepareStatement(secondSql);
//					rst=pst.executeQuery();
//					while(rst.next()){
//						groupId=rst.getString("DATASOURCEID");
//					}
//					pst.close();
//					rst.close();
//				}	
//			}catch(Exception e){
//				e.printStackTrace();
//			}	
//			if(groupId!=null){
//				sql+=" where DATASOURCEID="+groupId;
//			}
			if(!groupIds.equals("1") && !"".equals(groupIds)){
				sql+=" where DATASETDESC='"+groupIds+"'";
				
			}
			
			try{
				if(con==null)
				con=connectPool.getConnection();
				pst=con.prepareStatement(sql);
				rst=pst.executeQuery();
				while(rst.next()){
					count=rst.getInt(1);
				}
			}catch(Exception e){
				e.printStackTrace();
			}	
			finally{
				try{
					if(con!=null) con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			 return count;
		}	 
		else{
			if("".equals(groupIds)){
				groupIds="-1";
			}
			String queryString = " select count(id) from DataTable t where t.dataObjectGroup.id in ('"
					+ groupIds + "') ";
			return dataTableDao.findTotalRows(queryString);
		}
	}
	//GUOWEIXIN
	public List findAllByPageAndGroupIdsFieldGrant(int page, int rows, String groupIds) {
		//GUOWEIXIN configSQL.properties 文件
		Map map=systemFrameService.load_serviceConfigSQL();
		String falgChoose=(String)map.get("is_use_reference_dataSource");
		String key="DataTableService-findAllByPageAndGroupIdsFieldGrant";
		String sqlTest=(String)map.get(key);
		if("true".equals(falgChoose) && null!=(String)map.get(key) && !"".equals((String)map.get(key))){
			/**
			 * 此处的groupIds的值是现在要根据其 分组ID得到其 数据库实例名称。
			 * 根据实例名称去  现有库 得到其 数据源ID
			 */
//			DataSource dataSource=null;
//			String paramString = "from DataSource t where t.id='"+groupIds+"'";
//			List list=dataSourceDao.find(paramString);
//			if(list!=null && list.size()>0){
//				 dataSource=(DataSource)list.get(0);
//			}
			//根据所传ID，得到其分组对象名称
			String dsName=null;
			String firstSql=" from DataObjectGroup t where t.id ='"+groupIds+"'";
			DataObjectGroup dataObjectGroup=null;
			List list = dataTableDao.find(firstSql);
			if(list != null && list.size()>0){
				dataObjectGroup=(DataObjectGroup) list.get(0);
				dsName=dataObjectGroup.getName();
			}
			
			// 获取缓存中的连接池
			Map poolsMap = systemFrameService.load_connectPools("true");
			String mapStr=(String)map.get("is_use_reference_dataSourceID");
			String dataSourceID=map.get("is_use_reference_dataSourceID").toString();
			ComboPooledDataSource   connectPool=(ComboPooledDataSource) poolsMap.get(dataSourceID);
			List<DataTable> listData=new ArrayList<DataTable>();
			String sql=(String)map.get(key);
			int currPage=rows*(page-1);
			String resultSql=sql.replaceFirst("replaceRows", rows+"");
			resultSql=resultSql.replaceFirst("replacePage", currPage+"");
			
			Connection con=null;
			PreparedStatement pst=null;
			ResultSet rst=null;
			String groupId=null;

//			try{
//				if(dataSource!=null){
//					dsName=dataSource.getName();
//					String secondSql="select DATASOURCEID  from SGS_DIGICITY_MDS.CORE_DM_DATASOURCEINFO  where DATABASENAME='"+dataSource.getSid()+"'";
//					con=connectPool.getConnection();
//					pst=con.prepareStatement(secondSql);
//					rst=pst.executeQuery();
//					while(rst.next()){
//						groupId=rst.getString("DATASOURCEID");
//					}
//					pst.close();
//					rst.close();
//				}	
//			}catch(Exception e){
//				e.printStackTrace();
//			}	
//			if(groupId!=null){
//				resultSql+=" where DATASOURCEID="+groupId;
//			}
			
			
			if(!groupIds.equals("1") && !"".equals(groupIds)){
				resultSql+=" and DATASETDESC='"+groupIds+"') ";
				resultSql+=" and DATASETDESC='"+groupIds+"'";
			}else{
				resultSql+=")";
			}
			
			
			try{
				if(con==null)
				con=connectPool.getConnection();
				resultSql+="  ORDER BY DATASETID ASC";
				pst=con.prepareStatement(resultSql);
				rst=pst.executeQuery();
				while(rst.next()){
					//此处写上SQL语句
					DataTable dt=new DataTable();
					dt.setName(rst.getString("TABLENAME"));//表名
					DataSource dataSource1=new DataSource();//所属分组
					if(dsName!=null)dataSource1.setName(dsName);
					else dataSource1.setName("未知");
					dt.setDataSource(dataSource1);
					dt.setId(rst.getString("DATASETID"));//表名ID
					listData.add(dt);
				}
			}catch(Exception e){
				e.printStackTrace();
			}	
			finally{
				try{
					if(con!=null) con.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return listData;
		}
		else{
		String paramString = " from DataTable t where t.dataObjectGroup.id in ('"
				+ groupIds + "') order by t.sortNo asc ,t.createTime desc ";
		return dataTableDao.findAllByPage(paramString, page, rows);
		}
	}
  /**
   * GUOWEIXIN 如果为1时，查询所有，即省略 groupIds in
   */
	public List findAllByPageAndGroupId(int page, int rows, String groupId) {
		String paramString = " from DataTable t  order by t.name asc ";
		return dataTableDao.findAllByPage(paramString, page, rows);
	}
	/**
	   * GUOWEIXIN 如果为1时，查询总数，即省略 groupIds in
	   */		
	public int findTotalRowsByGroupId(String groupId) {
		String paramString = "  select count(id) from DataTable  ";
		return dataTableDao.findTotalRows(paramString);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#findTotalRowsByCondition(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public int findTotalRowsByCondition(String groupIds, String name,
			String type) {
		String queryString = " select count(id) from DataTable t where t.dataObjectGroup.id in ("
				+ groupIds + ") and t.name like '%" + name + "%' ";
		if (!type.equals("-1")) {
			queryString += " and t.type = '" + type + "' ";
		}
		return dataTableDao.findTotalRows(queryString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#findAllByCondition(int,
	 *      int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List findAllByCondition(int page, int rows, String groupIds,
			String name, String type) {
		String paramString = " from DataTable t where t.dataObjectGroup.id in ("
				+ groupIds + ") and t.name like '%" + name + "%' ";
		if (!type.equals("-1")) {
			paramString += " and t.type = '" + type + "' ";
		}
		paramString += " order by t.sortNo asc ,t.createTime desc ";
		return dataTableDao.findAllByPage(paramString, page, rows);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#isExist(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean isExist(String id, String name) {
		String paramString = " from DataTable t where t.id = '" + id
				+ "' or t.name = '" + name + "' ";
		List list = dataTableDao.find(paramString);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#isExist(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public boolean isExist(String id, String name, String dataSourceId) {
		String paramString = " from DataTable t where t.id = '" + id
				+ "' or (t.name = '" + name + "' and t.dataSource.id='"
				+ dataSourceId + "')";
		;
		List list = dataTableDao.find(paramString);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#isExistUpdate(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean isExistUpdate(String id, String name) {
		String paramString = " from DataTable t where t.id <> '" + id
				+ "' and t.name = '" + name + "' ";
		List list = dataTableDao.find(paramString);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#findById(java.lang.String)
	 */
	public DataTable findById(String id) {
		String paramString = " from DataTable t where t.id = '" + id + "' ";
		List list = dataTableDao.find(paramString);
		if (list != null && list.size() > 0) {
			return (DataTable) list.get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#findByName(java.lang.String)
	 */
	public DataTable findByName(String name) {
		String paramString = " from DataTable t where t.name = '" + name
				+ "' order by t.sortNo asc ,t.createTime desc ";
		List list = dataTableDao.find(paramString);
		if (list != null && list.size() > 0) {
			return (DataTable) list.get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#insert(com.zxt.compplatform.datatable.entity.DataTable)
	 */
	public void insert(DataTable dataTable) {
		dataTableDao.create(dataTable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#update(com.zxt.compplatform.datatable.entity.DataTable)
	 */
	public void update(DataTable dataTable) {
		dataTableDao.update(dataTable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#findChildrenByParentId(java.lang.String)
	 */
	public List findChildrenByParentId(String parentId) {
		return dataTableDao.findChildrenByParentId(parentId);
	}

	public void setDataTableDao(IDataTableDao dataTableDao) {
		this.dataTableDao = dataTableDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datatable.service.IDataTableService#findAllByPageByIsId(int,
	 *      int, java.util.List)
	 */
	@Override
	public List findAllByPageByIsId(int page, int rows, List<String> str) {
		// TODO Auto-generated method stub
		return null;
	}
	/*** GUOWEIXIN
	 * 读取资源文件：configSQL.properties
	 */
	private SystemFrameService systemFrameService;
	public SystemFrameService getSystemFrameService() {
		return systemFrameService;
	}
	public void setSystemFrameService(SystemFrameService systemFrameService) {
		this.systemFrameService = systemFrameService;
	}
	/**
	 * 数据源持久化操作 GUOWEIXIN
	 */
	private IDataSourceDao dataSourceDao;
	public void update(DataSource dataSource) {
		dataSourceDao.update(dataSource);

	}

	public void setDataSourceDao(IDataSourceDao dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}

}
