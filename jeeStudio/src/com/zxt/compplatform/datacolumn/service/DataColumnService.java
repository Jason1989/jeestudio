//mon:hxl-2013-5-17-2
//将字段排序按name改为按sortNo
//TODO: 增加单元测试

package com.zxt.compplatform.datacolumn.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zxt.compplatform.datacolumn.dao.IDataColumnDao;
import com.zxt.compplatform.datacolumn.entity.DataColumn;
import com.zxt.compplatform.datatable.entity.DataTable;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.service.SystemFrameService;

/**
 * 数据对象列操作接口实现
 * 
 * @author 007
 */
public class DataColumnService implements IDataColumnService {

	/**
	 * 数据对象列持久化dao实体
	 */
	private IDataColumnDao dataColumnDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#delete(com.zxt.compplatform.datacolumn.entity.DataColumn)
	 */
	public void delete(DataColumn dataColumn) {
		dataColumnDao.delete(dataColumn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#deleteById(java.lang.String)
	 */
	public void deleteById(String id) {
		dataColumnDao.delete(findById(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#deleteAll(java.util.List)
	 */
	public void deleteAll(List paramCollection) {
		dataColumnDao.deleteAll(paramCollection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#findAll()
	 */
	public List findAll() {
		String paramString = " from DataColumn t order by t.sortNo ";
		return dataColumnDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#findAllByTableId(java.lang.String)
	 */
	public List findAllByTableId(String tableId) {
		String paramString = " from DataColumn t where t.dataTable.id = '"
				+ tableId + "' order by t.sortNo ";
		return dataColumnDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#findAllByTableIdAndStatus(java.lang.String,
	 *      java.lang.String)
	 */
	public List findAllByTableIdAndStatus(String tableId, String status) {
		String paramString = " from DataColumn t where t.dataTable.id = '"
				+ tableId + "' and t.istemp='" + status
				+ "'  order by t.sortNo ";
		return dataColumnDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#findAllByTableName(java.lang.String)
	 */
	public List findAllByTableName(String tableName) {
		String paramString = " from DataColumn t where t.dataTable.name = '"
				+ tableName + "' order by t.sortNo ";
		return dataColumnDao.find(paramString);
	}

	/**
	 * @GUOWEIXIN 根据 表的 名称（非ID）得到其列
	 */
	public List findAllByTableNames(List tableIds, String isTemp) {
		String paramString = " from DataColumn t where t.dataTable.name in (";
		if (tableIds.size() > 0) {
			for (int i = 0; i < tableIds.size(); i++) {
				if (tableIds.size() - 1 == i)
					paramString += "'" + tableIds.get(i) + "')";
				else
					paramString += "'" + tableIds.get(i) + "',";
			}
		}
		//mon:hxl-2013-5-17-2-1
		paramString += " and t.istemp='" + isTemp
				+ "' order by t.dataTable.name asc ,t.sortNo asc ";
		return dataColumnDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#findAllByTableIds(java.lang.String,
	 *      java.lang.String)
	 */
	public List findAllByTableIds(String tableIds, String isTemp) {
		//mon:hxl-2013-5-17-2-2
		String paramString = " from DataColumn t where t.dataTable.id in ("
				+ tableIds + ") and t.istemp='" + isTemp
				+ "' order by t.dataTable.name asc ,t.sortNo asc ";
		return dataColumnDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#isExist(java.lang.String,
	 *      java.lang.String)
	 */
	public boolean isExist(String id, String name) {
		String paramString = " from DataColumn t where t.id = '" + id
				+ "' or t.name = '" + name + "' ";
		List list = dataColumnDao.find(paramString);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#isExist(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public boolean isExist(String id, String name, String tableName) {
		String paramString = " from DataColumn t where t.dataTable.id='"
				+ tableName + "' and (t.id = '" + id + "' or t.name = '" + name
				+ "')";
		List list = dataColumnDao.find(paramString);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#isExistUpdate(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public boolean isExistUpdate(String id, String name, String datatableId) {
		String paramString = "";
		if (datatableId != null && !"".equals(datatableId)) {
			paramString = " from DataColumn t where t.id <> '" + id
					+ "' and t.dataTable.id = '" + datatableId
					+ "' and t.name = '" + name + "' ";
		} else {
			paramString = " from DataColumn t where t.id <> '" + id
					+ "' and t.name = '" + name + "' ";
		}
		List list = dataColumnDao.find(paramString);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#findById(java.lang.String)
	 */
	public DataColumn findById(String id) {
		String paramString = " from DataColumn t where t.id = '" + id + "' ";
		List list = dataColumnDao.find(paramString);
		if (list != null && list.size() > 0) {
			return (DataColumn) list.get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#findAllByIds(java.lang.String)
	 */
	public List findAllByIds(String ids) {
		String paramString = " from DataColumn t where t.id in (" + ids
				+ ") order by t.sortNo ";
		return dataColumnDao.find(paramString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#findTotalRowsByTableId(java.lang.String)
	 */
	public int findTotalRowsByTableId(String tableId) {
		String queryString = " select count(id) from DataColumn t where t.dataTable.id = '"
				+ tableId + "'";
		return dataColumnDao.findTotalRows(queryString);
	}

	// 获取导入字段的信息
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#findTotalRowsByTableId(java.lang.String,
	 *      java.lang.String)
	 */
	public int findTotalRowsByTableId(String tableId, String isTemp) {
		String queryString = " select count(id) from DataColumn t where t.dataTable.id = '"
				+ tableId + "' and t.istemp='" + isTemp + "'";
		return dataColumnDao.findTotalRows(queryString);
	}

	/**
	 * GUOWEIXIN tableIdArr:存放表的ID数组。查找里面有 ID的字段列全部显示 select * from
	 * ENG_DATAOBJECT_COLUMN where DF_DO_ID
	 * in('34333d9330a1ba7c343c92842eb08b41'
	 * ,'11ebc66b9d5864fed961f227a3a0e3b1')
	 */
	public List findAllByPageAndTableIdS(int page, int rows,
			String[] tableIdArr, String isTemp) {
		// GUOWEIXIN configSQL.properties 文件
		Map map = systemFrameService.load_serviceConfigSQL();
		String falgChoose = (String) map.get("is_use_reference_dataSource");
		String key = "DataColumnService-findAllByPageAndTableIdS";
		if ("true".equals(falgChoose) && null != (String) map.get(key)
				&& !"".equals((String) map.get(key))) {
			
			Map poolsMap = systemFrameService.load_connectPools("true");
			String mapStr = (String) map.get("is_use_reference_dataSourceID");
			String dataSourceID = map.get("is_use_reference_dataSourceID")
					.toString();
			ComboPooledDataSource connectPool = (ComboPooledDataSource) poolsMap
					.get(dataSourceID);
			String sql = (String) map.get(key);
			if (tableIdArr != null) {
				for (int i = 0; i < tableIdArr.length; i++) {
					if (tableIdArr.length - 1 == i) {
						sql += "'" + tableIdArr[i] + "')";
					} else {
						sql += "'" + tableIdArr[i] + "',";
					}
				}
				//加第二个SQL过滤条件
				sql+="  ORDER BY sc_table.TABLENAME   ";
			} else {
				sql += "'-1')";
			}
			List<DataColumn> listDC=new ArrayList<DataColumn>();
			Connection con=null;
			PreparedStatement pst=null;
			ResultSet rst=null;
			try{
				con=connectPool.getConnection();
				pst=con.prepareStatement(sql);
				rst=pst.executeQuery();
				while(rst.next()){
					DataColumn d=new DataColumn();
					d.setId(rst.getString("DATAFIELDID"));//字段ID
					d.setName(rst.getString("FLDNAME"));//字段名称
					DataTable dataTable=new DataTable();
					dataTable.setName(rst.getString("TABLENAME"));//所属表名
					d.setDataTable(dataTable);
					d.setTitle(rst.getString("FLDALIAS"));
					listDC.add(d);
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
			return listDC;
		} else {
			String paramString = " from DataColumn t where t.dataTable.id in(";
			if (tableIdArr != null) {
				for (int i = 0; i < tableIdArr.length; i++) {
					if (tableIdArr.length - 1 == i) {
						paramString += "'" + tableIdArr[i] + "')";
					} else {
						paramString += "'" + tableIdArr[i] + "',";
					}
				}
			} else {
				paramString += "'-1')";
			}
			//mon:hxl-2013-5-17-2-3
			paramString += " and t.istemp='" + isTemp
					+ "' order by t.dataTable.name, t.sortNo ";
			return dataColumnDao.find(paramString);
		}
	}

	/**
	 * GUOWEIXIN tableIdArr:存放表的ID数组。查找里面有 ID的字段列全部显示 select * from
	 * ENG_DATAOBJECT_COLUMN where DF_DO_ID
	 * in('34333d9330a1ba7c343c92842eb08b41'
	 * ,'11ebc66b9d5864fed961f227a3a0e3b1')
	 */
	public int findTotalRowsByTableIdS(String[] tableIdArr, String isTemp) {
		// GUOWEIXIN configSQL.properties 文件
		Map map = systemFrameService.load_serviceConfigSQL();
		String falgChoose = (String) map.get("is_use_reference_dataSource");
		String key = "DataColumnService-findTotalRowsByTableIdS";
		if ("true".equals(falgChoose) && null != (String) map.get(key)
				&& !"".equals((String) map.get(key))) {
			Map poolsMap = systemFrameService.load_connectPools("true");
			String mapStr = (String) map.get("is_use_reference_dataSourceID");
			String dataSourceID = map.get("is_use_reference_dataSourceID")
					.toString();
			ComboPooledDataSource connectPool = (ComboPooledDataSource) poolsMap
					.get(dataSourceID);

			String sql = (String) map.get(key);
			if (tableIdArr != null) {
				for (int i = 0; i < tableIdArr.length; i++) {
					if (tableIdArr.length - 1 == i) {
						sql += "'" + tableIdArr[i] + "')";
					} else {
						sql += "'" + tableIdArr[i] + "',";
					}
				}
			} else {
				sql += "'-1')";
			}
			Connection con = null;
			PreparedStatement pst = null;
			ResultSet rst = null;
			int count = 0;
			try {
				con = connectPool.getConnection();
				pst = con.prepareStatement(sql);
				rst = pst.executeQuery();
				while (rst.next()) {
					count = rst.getInt(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (con != null)
						con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return count;

		} else {
			String queryString = " select count(id) from DataColumn t where t.dataTable.id in( ";
			if (tableIdArr != null) {
				for (int i = 0; i < tableIdArr.length; i++) {
					if (tableIdArr.length - 1 == i) {
						queryString += "'" + tableIdArr[i] + "')";
					} else {
						queryString += "'" + tableIdArr[i] + "',";
					}
				}
			} else {
				queryString += "'-1')";
			}
			queryString += " and t.istemp='" + isTemp + "'";

			return dataColumnDao.findTotalRows(queryString);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#findTotalRowsByTableIds(java.lang.String)
	 */
	public int findTotalRowsByTableIds(String tableIds) {
		String queryString = " select count(id) from DataColumn t where t.dataTable.id in ("
				+ tableIds + ")";
		return dataColumnDao.findTotalRows(queryString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#findAllByPageAndTableId(int,
	 *      int, java.lang.String)
	 */
	public List findAllByPageAndTableId(int page, int rows, String tableId) {
		//mon:hxl-2013-5-17-2-4
		String paramString = " from DataColumn t where t.dataTable.id = '"
				+ tableId + "' order by t.sortNo  ";
		return dataColumnDao.findAllByPage(paramString, page, rows);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#findAllByPageAndTableId(int,
	 *      int, java.lang.String, java.lang.String)
	 */
	public List findAllByPageAndTableId(int page, int rows, String tableId,
			String isTemp) {
		//mon:hxl-2013-5-17-2-5
		String paramString = " from DataColumn t where t.dataTable.id = '"
				+ tableId + "' and t.istemp='" + isTemp + "' order by t.sortNo  ";
		return dataColumnDao.findAllByPage(paramString, page, rows);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#insert(com.zxt.compplatform.datacolumn.entity.DataColumn)
	 */
	public void insert(DataColumn dataColumn) {
		dataColumnDao.create(dataColumn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#update(com.zxt.compplatform.datacolumn.entity.DataColumn)
	 */
	public void update(DataColumn dataColumn) {
		dataColumnDao.update(dataColumn);
	}

	public void setDataColumnDao(IDataColumnDao dataColumnDao) {
		this.dataColumnDao = dataColumnDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.datacolumn.service.IDataColumnService#updateBatchCloumnTemp(java.lang.String,
	 *      java.lang.String)
	 */
	public void updateBatchCloumnTemp(String ids, String state) {
		String[] idArray = ids.split(",");
		StringBuffer idsStr = new StringBuffer();
		for (int i = 0; i < idArray.length; i++) {
			idsStr.append("'" + idArray[i] + "',");
		}
		String queryIds = idsStr.substring(0, idsStr.length() - 1);
		List list = findAllByIds(queryIds);
		for (int i = 0; i < list.size(); i++) {
			DataColumn column = (DataColumn) list.get(i);
			column.setIstemp(state);
			update(column);
		}
	}

	/**
	 * 创建表单 检查是否包含主键
	 */
	public String checkTableIsParmerkey(String tableId) {
		// TODO Auto-generated method stub
		List list = findAllByTableId(tableId);
		DataColumn dataColumn = null;
		String isParmerKey = "false";
		for (int i = 0; i < list.size(); i++) {
			dataColumn = (DataColumn) list.get(i);
			if (Constant.PARMERYKEY_TRUE.equals(dataColumn.getIsPrimaryKey())) {
				isParmerKey = "true";
				break;
			}
		}
		return isParmerKey;
	}

	/**
	 * * GUOWEIXIN 读取资源文件：configSQL.properties
	 */
	private SystemFrameService systemFrameService;

	public SystemFrameService getSystemFrameService() {
		return systemFrameService;
	}

	public void setSystemFrameService(SystemFrameService systemFrameService) {
		this.systemFrameService = systemFrameService;
	}
}
