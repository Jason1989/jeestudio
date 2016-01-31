//mno:hxl-2012-5-17-1
//TODO:将字段顺序作为dataColumn.SortNo，解决插入到ENG_DATAOBJECT_COLUMN表中的字段，顺序字段DF_SORTINDEX为空的问题

package com.zxt.compplatform.datacolumn.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.zxt.compplatform.datacolumn.entity.DataColumn;
import com.zxt.compplatform.datacolumn.service.IDataColumnService;
import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.datasource.service.IDataSourceService;
import com.zxt.compplatform.datatable.entity.DataTable;
import com.zxt.compplatform.datatable.service.IDataTableService;
import com.zxt.compplatform.form.service.IFormService;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.framework.common.util.RandomGUID;
import com.zxt.framework.jdbc.ZXTJDBCDataSource;
import com.zxt.framework.jdbc.ZXTJDBCTemplate;

/**
 * 数据对象列操作
 * @author 007
 */
public class DataColumnAction {
	private static final Logger log = Logger.getLogger(DataColumnAction.class);
	/**
	 * 数据源业务操作接口
	 */
	private IDataSourceService dataSourceService;
	/**
	 * 表单业务操作接口
	 */
	private IFormService formService;
	/**
	 * 数据对象列业务操作接口
	 */
	private IDataColumnService dataColumnService;
	/**
	 * 数据对象业务操作接口
	 */
	private IDataTableService dataTableService;
	/**
	 * 数据对象列实体
	 */
	private DataColumn dataColumn;
	/**
	 * 数据对象
	 */
	private DataTable dataTable;
	/**
	 * 数据对象的id
	 */
	private String dataTableId;

	/**
	 * 列表入口
	 * 
	 * @return
	 */
	public String toList() {
		return "list";
	}

	/**
	 * 跳转到数据对象导入界面
	 * 
	 * @return
	 */
	public String toImportList() {
		return "toimport";
	}

	/**
	 * 返回数据到列表页
	 * 
	 * @return
	 */
	public String list() {
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		int page = 1;
		if (req.getParameter("page") != null
				&& !req.getParameter("page").equals("")) {
			page = Integer.parseInt(req.getParameter("page"));
		}
		int rows = 0;
		if (req.getParameter("rows") != null
				&& !req.getParameter("rows").equals("")) {
			rows = Integer.parseInt(req.getParameter("rows"));
		}
		dataTableId = req.getParameter("dataTableId");
		List dataColumnList = null;
		int totalRows = 0;
		if (dataTableId != null && !dataTableId.equals("")) {
			totalRows = dataColumnService.findTotalRowsByTableId(dataTableId);
			dataColumnList = dataColumnService.findAllByPageAndTableId(page,
					rows, dataTableId);
		}
		Map map = new HashMap();
		if (dataColumnList == null) {
			dataColumnList = new ArrayList();
		}
		map.put("rows", dataColumnList);
		map.put("total", new Integer(totalRows));
		String dataJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 跳转到字段导入页面
	 * 
	 * @return
	 */
	public String toimport() {
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		res.setContentType("text/x-json;charset=UTF-8 ");
		int page = 1;
		if (req.getParameter("page") != null
				&& !req.getParameter("page").equals("")) {
			page = Integer.parseInt(req.getParameter("page"));
		}
		int rows = 0;
		if (req.getParameter("rows") != null
				&& !req.getParameter("rows").equals("")) {
			rows = Integer.parseInt(req.getParameter("rows"));
		}
		dataTableId = req.getParameter("dataTableId");
		String isTemp = req.getParameter("isTemp");
		List dataColumnList = null;
		int totalRows = 0;
		if (dataTableId != null && !dataTableId.equals("")) {
			totalRows = dataColumnService.findTotalRowsByTableId(dataTableId,
					isTemp);
			dataColumnList = dataColumnService.findAllByPageAndTableId(page,
					rows, dataTableId, isTemp);
		}
		Map map = new HashMap();
		if (dataColumnList == null) {
			dataColumnList = new ArrayList();
		}
		map.put("rows", dataColumnList);
		map.put("total", new Integer(totalRows));
		String dataJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 跳转到字段导入页面 GUOWEIXIN
	 * 根据传过来的多个ID名称。得到多个表名称 所属的列
	 * @return
	 */
	public String toimportByArrayId() {
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		res.setContentType("text/x-json;charset=UTF-8 ");
		int page = 1;
		if (req.getParameter("page") != null
				&& !req.getParameter("page").equals("")) {
			page = Integer.parseInt(req.getParameter("page"));
		}
		int rows = 0;
		if (req.getParameter("rows") != null
				&& !req.getParameter("rows").equals("")) {
			rows = Integer.parseInt(req.getParameter("rows"));
		}
		String dataTableIds = req.getParameter("dataTableId");
		String[] dataTableArr=null;
		if(!"".equals(dataTableIds)){
			dataTableArr=dataTableIds.split(",");
		}
		String isTemp = req.getParameter("isTemp");
		List dataColumnList = null;
		int totalRows = 0; 
		if (dataTableArr != null) {
			totalRows = dataColumnService.findTotalRowsByTableIdS(dataTableArr,isTemp);
			dataColumnList = dataColumnService.findAllByPageAndTableIdS(page,rows, dataTableArr, isTemp);
		}
		Map map = new HashMap();
		if (dataColumnList == null) {
			dataColumnList = new ArrayList();
		}
		map.put("rows", dataColumnList);
		map.put("total", new Integer(totalRows));
		String dataJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 跳转添加页面
	 * 
	 * @return
	 */
	public String toAdd() {
		if (dataTableId != null)
			dataTable = dataTableService.findById(dataTableId);
		return "toadd";
	}

	/**
	 * 添加
	 * 
	 * @return
	 */
	public String add() {
		HttpServletResponse res = ServletActionContext.getResponse();
		if (dataColumn.getId() == null || dataColumn.getId().equals("")) {
			dataColumn.setId(RandomGUID.geneGuid());
		}
		try {
			if (dataColumnService.isExist(dataColumn.getId(), dataColumn
					.getName())) {
				res.getWriter().write("exist");
			} else {
				//根据得到的 datatable id得到其数据源，然后执行添加SQL
				String dataTableId=dataColumn.getDataTable().getId();
				DataTable dataTableUtil = new DataTable();
				String alterSqlStrings=null; //修改数据库表的SQL语句
				try {
					if (dataTableId != null && !dataTableId.equals("")) {
						dataTable = dataTableService.findById(dataTableId);
					}					
					BeanUtils.copyProperties(dataTableUtil, dataTable);
					DataSource dataSource = dataSourceService.findById(dataTableUtil.getDataSource().getId());
					javax.sql.DataSource ds = new ZXTJDBCDataSource(
							dataSource.getIpAddress(), dataSource
									.getPort()
									+ "", dataSource.getSid(),
							dataSource.getDbType(), dataSource
									.getUsername(), dataSource
									.getPassword());
					ZXTJDBCTemplate jdbcTemplate = new ZXTJDBCTemplate(
							ds);
					if (dataSource.getDbType().equals("2")) { //如果是SQLSERVER数据库

					alterSqlStrings=" alter table "+dataTableUtil.getName()+" add "+dataColumn.getName()+"  "+dataColumn.getDataType() +"("+dataColumn.getDataLength()+")  ";					
					alterSqlStrings+="EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'"+dataColumn.getTitle()+"' , @level0type=N'SCHEMA',@level0name=N'dbo', " +
							"@level1type=N'TABLE',@level1name=N'"+dataTableUtil.getName()+"', @level2type=N'COLUMN',@level2name=N'"+dataColumn.getName()+"'  ";
					
					}
					jdbcTemplate.update(alterSqlStrings);
					
				} catch (Exception e) {
					// TODO: handle exception
				}	
				dataColumnService.insert(dataColumn);
				res.getWriter().write("success");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 跳转修改页面
	 * 
	 * @return
	 */
	public String toUpdate() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String dataColumnId = req.getParameter("dataColumnId");
		if (dataColumnId != null && !dataColumnId.equals("")) {
			dataColumn = dataColumnService.findById(dataColumnId);
		}
		return "toupdate";
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	public String update() {
		HttpServletResponse res = ServletActionContext.getResponse();
		try {
			if (dataColumnService.isExistUpdate(dataColumn.getId(), dataColumn
					.getName(),dataColumn.getDataTable().getId())) {
				res.getWriter().write("exist");
			} else {
				dataColumn.setDataTable(dataTableService.findById(dataColumn
						.getDataTable().getId()));
				dataColumn.setIstemp("0");	
				dataColumnService.update(dataColumn);
				res.getWriter().write("success");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 更新字段是不是为temp
	 * 
	 * @return
	 */
	public String updateTemp() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/plain;charset=UTF-8 ");
		String dataColumnId = req.getParameter("dataColumnId");
		String isTemp = req.getParameter("isTemp");
		try {
			if (dataColumnId != null && !"".equals(dataColumnId)) {
				DataColumn dataColumn = dataColumnService
						.findById(dataColumnId);
				dataColumn.setIstemp(isTemp);

				dataColumnService.update(dataColumn);
				final String dataTableId = req.getParameter("dataTableId");
				/**
				 * 调用移除字段的接口
				 */
				if (Constant.DATACLOMUN_ZANCUN.equals(isTemp)) {
					formService.updateAllFormBydeleteField(dataTableId,
							dataColumn.getName());
					new Thread(){public void run() {
						formService.updatePageService(dataTableId);
					}}.start();
				}
			}
			res.getWriter().write("success");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String dataColumnId = req.getParameter("dataColumnId");
		String dataColumnIds = req.getParameter("dataColumnIds");
		try {
			if (dataColumnId != null && !dataColumnId.equals("")) {
				dataColumnService.deleteById(dataColumnId);
				res.getWriter().write("success");
			}
			if (dataColumnIds != null && !dataColumnIds.equals("")) {
				dataColumnService.deleteAll(dataColumnService
						.findAllByIds(dataColumnIds));
				res.getWriter().write("success");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得所有数据对象信息
	 * 
	 * @return
	 */
	public String synchronousColumn() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		final String dataTableId = req.getParameter("dataTableId");
		String dataSourceId = null;
		DataSource dataSource = null;
		if (dataTableId != null)
			dataTable = dataTableService.findById(dataTableId);
		if (dataTable != null)
			dataSourceId = dataTable.getDataSource().getId();
		if (dataSourceId != null && !dataSourceId.equals("")) {
			// 获取数据源(需要注入)
			dataSource = dataSourceService.findById(dataSourceId);
		}
		if (dataSource != null) {
			StringBuffer paramString = new StringBuffer();
			if (dataSource.getDbType().equals("1")) {
				// sql oracle
				paramString
						.append(" select tb.column_name as name,tb.data_type ,tb.data_length");
				paramString
						.append(",tb.data_precision as precision,tb.data_scale as scale");
				paramString
						.append(",tb.nullable as nullable,tb.data_default as default_value,pk.pkey as primary_key,cm.comments as description");
				// paramString.append(",pk.constraint_name,pk.pkey,ut.tablespace_name
				// ");
				paramString
						.append(" from (select t.column_name,t.data_type,t.data_length,t.data_precision,t.data_scale ,t.nullable,t.data_default,t.table_name ");
				paramString.append(
						" from user_tab_columns t where t.table_name = '")
						.append(dataTable.getName()).append("' ) tb ");
				paramString
						.append(" left join (select col.constraint_name,col.column_name ,'1' as pkey  from user_constraints con,user_cons_columns col ");
				paramString
						.append(
								" where con.constraint_name = col.constraint_name and col.table_name = '")
						.append(dataTable.getName()).append("' ");
				paramString
						.append(" and con.constraint_type = 'P') pk on tb.column_name = pk.column_name ");
				// TODO 查询注释信息
				// TODO 也可以添加信息
				paramString
						.append(
								" left join (select comm.column_name,comm.comments from user_col_comments comm where comm.table_name='")
						.append(dataTable.getName()).append(
								"') cm on cm.column_name = tb.column_name");
				paramString
						.append(
								" left join (select table_name ,tablespace_name from user_tables where table_name = '")
						.append(dataTable.getName()).append("') ut ");
				// .append("' and tablespace_name =
				// '").append(spaceName).append("') ut ");
				paramString.append(" on tb.table_name = ut.table_name ");
			} else if (dataSource.getDbType().equals("2")) {
				// sql server2005
				paramString = paramString
						.append("SELECT a.colorder N'sort_no', a.name N'name', "
								+ "(case when (SELECT count(*) FROM sysobjects WHERE (name in (SELECT name FROM sysindexes WHERE "
								+ "(id = a.id) AND (indid in  (SELECT indid  FROM sysindexkeys  WHERE (id = a.id) AND "
								+ "(colid in (SELECT colid  FROM syscolumns WHERE  (id = a.id) AND (name = a.name))))))) "
								+ "AND (xtype = 'PK'))>0 then '1' else '0' end) N'primary_key', b.name N'data_type', "
								+ "COLUMNPROPERTY(a.id,a.name,'PRECISION') as N'data_length', isnull(COLUMNPROPERTY(a.id,a.name,'Scale'),0) as N'precision', "
								+ "(case when a.isnullable=1 then '1'else '0' end) N'nullable', isnull(e.text,'') N'default_value', isnull(cast(g.[value] as varchar(200)),'')"
								+ " AS N'description' FROM syscolumns a left join systypes b on a.xtype=b.xusertype inner join sysobjects d on a.id=d.id and "
								+ "d.xtype in ('U','V') and d.name<>'dtproperties' and d.name='"
								+ dataTable.getName()
								+ "' left join syscomments e on a.cdefault=e.id left join "
								+ "sys.extended_properties g on a.id=g.major_id AND a.colid = g.minor_id order by object_name(a.id),a.colorder");
			}
			// 根据数据源查找表空间
			javax.sql.DataSource ds = new ZXTJDBCDataSource(dataSource
					.getIpAddress(), dataSource.getPort().toString(),
					dataSource.getSid(), dataSource.getDbType(), dataSource
							.getUsername(), dataSource.getPassword());
			ZXTJDBCTemplate jdbcTemplate = new ZXTJDBCTemplate(ds);
			//debug begin
			System.out.println("synchronousColumn sql: " + paramString.toString());
			//debug end
			List list = jdbcTemplate.findToMaps(paramString.toString());
			try {

				List columnListAll = null; // 平台数据表中的 字段list
				Map columnMapAll = new HashMap(); // 平台数据表中的 字段map
				List sDBColumnListAll = new ArrayList(); // 业务库表的字段list
				Map sDBColumnMapAll = new HashMap(); // 业务库表的字段Map

				DataColumn dColumn = null;
				columnListAll = dataColumnService // 初始化平台数据表中的 字段list
						.findAllByTableId(dataTableId);
				
				//int dataColumnSortNo = 0; //mno:hxl-2012-5-17-1-1
				for (Iterator it = list.iterator(); it.hasNext();) {
					Map map = (Map) it.next();
					/**
					 * 业务表的字段列封装
					 */
					DataColumn dataColumn = new DataColumn();
					dataColumn.setIstemp("1");
					dataColumn.setDataTable(dataTable);
					dataColumn.setId(RandomGUID.geneGuid());
					
					//mno:hxl-2012-5-17-1-2 begin
					//dataColumn.setSortNo(dataColumnSortNo++);
					//mno:hxl-2012-5-17-1-2 end

					if (dataSource.getDbType().equals(Constant.DATABASE_ORACLE)) {
						// TODO 需要薄振成处理一下字段问题
						// dataColumn.setPrecision(new
						// Long(map.get("precision").toString()));
						// dataColumn.setIsPrimaryKey(new
						// Integer(map.get("primaryKey").toString()));
						dataColumn.setDataLength(new Long(map.get("dataLength")
								.toString()));
						// dataColumn.setSortNo(new
						// Integer(map.get("sortNo").toString()));
						// dataColumn.setDescription(map.get("description").toString());
						dataColumn.setDataType(map.get("dataType").toString()
								.toLowerCase());
						dataColumn.setName(map.get("name").toString());
						dataColumn.setTitle(map.get("description").toString());
						dataColumn.setNullable(map.get("nullable").toString());
						dataColumn.setDefaultValue(map.get("defaultValue")
								.toString());
					} else if (dataSource.getDbType().equals(
							Constant.DATABASE_SQLSERVER)) {
						dataColumn.setDataType(map.get("dataType").toString()
								.toLowerCase());
						dataColumn.setName(map.get("name").toString());
						dataColumn.setTitle(map.get("description").toString());
						dataColumn.setNullable(map.get("nullable").toString()); // 返回值
						// 1:是
						// 0：否
						dataColumn.setDefaultValue(map.get("defaultValue")
								.toString());
						dataColumn.setPrecision(new Long(map.get("precision")
								.toString())); // 没有返回0
						dataColumn.setIsPrimaryKey(new Integer(map.get(
								"primaryKey").toString()));// 返回值 1:是 0：否
						dataColumn.setDataLength(new Long(map.get("dataLength")
								.toString()));
						dataColumn.setSortNo(new Integer(map.get("sortNo")
								.toString()));
						dataColumn.setDescription(map.get("description")
								.toString());
					}
					sDBColumnMapAll.put(dataColumn.getName(), dataColumn);// 初始化业务库表的字段Map
					sDBColumnListAll.add(dataColumn);// 初始化业务库表的字段List
					/**
					 * 判断如果数据字段表中没有该字段 则新添加一条数据字段
					 */
					if (!dataColumnService.isExist(RandomGUID.geneGuid(), map
							.get("name").toString().trim(), dataTableId)) {
						dataColumnService.insert(dataColumn);
					}
				}

				/**
				 * 判断减少的字段
				 */
				for (int i = 0; i < columnListAll.size(); i++) {
					dColumn = (DataColumn) columnListAll.get(i);
					if (sDBColumnMapAll.get(dColumn.getName()) == null) {
						log.info(dColumn.getName() + "被修改或删除...");
						dataColumnService.delete(dColumn);
						/**
						 * 更新该数据对象下 所有表单的字段列
						 */
						formService.updateAllFormBydeleteField(dataTableId,
								dColumn.getName());
						new Thread(){public void run() {
							formService.updatePageService(dataTableId);
						}}.start();
					}
				}

				res.getWriter().write("success");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 
	 * 输入：dataTableId : 要查询表的Id 输出：dataTableId 对应的表的列信息
	 * 
	 * @return
	 */
	public String synchronous() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String dataTableId = req.getParameter("dataTableId");
		List resultList = new ArrayList();
		if (dataTableId != null) {
			try {
				dataTable = dataTableService.findById(dataTableId);
				List columnList = dataColumnService
						.findAllByTableId(dataTableId);
				Map columnMap = new HashMap();
				Map businessColMap = new HashMap();
				if (columnList != null)
					for (int i = 0; i < columnList.size(); i++) {
						DataColumn dataColumn = (DataColumn) columnList.get(i);
						columnMap.put(dataColumn.getName().toLowerCase(),
								dataColumn);
					}
				String tableName = dataTable.getName().toUpperCase();
				// String spaceName = dataTable.getSpacename().toUpperCase();
				DataSource dataSource = dataTable.getDataSource();
				if (dataSource != null) {
					javax.sql.DataSource ds = new ZXTJDBCDataSource(dataSource
							.getIpAddress(), dataSource.getPort().toString(),
							dataSource.getSid(), dataSource.getDbType(),
							dataSource.getUsername(), dataSource.getPassword());
					ZXTJDBCTemplate jdbcTemplate = new ZXTJDBCTemplate(ds);

					StringBuffer paramString = new StringBuffer();
					paramString
							.append(" select tb.column_name as name,tb.data_type ,tb.data_length");
					paramString
							.append(",tb.data_precision as precision,tb.data_scale as scale");
					paramString
							.append(",tb.nullable as nullable,tb.data_default as default_value");
					// paramString.append(",pk.constraint_name,pk.pkey,ut.tablespace_name
					// ");
					paramString
							.append(" from (select t.column_name,t.data_type,t.data_length,t.data_precision,t.data_scale ,t.nullable,t.data_default,t.table_name ");
					paramString.append(
							" from user_tab_columns t where t.table_name = '")
							.append(tableName).append("' ) tb ");
					paramString
							.append(" left join (select col.constraint_name,col.column_name ,'1' as pkey  from user_constraints con,user_cons_columns col ");
					paramString
							.append(
									" where con.constraint_name = col.constraint_name and col.table_name = '")
							.append(tableName).append("' ");
					paramString
							.append(" and con.constraint_type = 'P') pk on tb.column_name = pk.column_name ");
					paramString
							.append(
									" left join (select table_name ,tablespace_name from user_tables where table_name = '")
							.append(tableName)
							// .append("' and tablespace_name =
							// '").append(spaceName).append("') ut ");
							.append("') ut ");
					paramString.append(" on tb.table_name = ut.table_name ");

					// List businessColumnlist =
					// jdbcTemplate.find(paramString.toString(),
					// DataColumn.class);
					
					//debug begin
					System.out.println("synchronous sql: " + paramString.toString());
					//debug end
					
					List businessColumnlist = jdbcTemplate
							.findToMaps(paramString.toString());
					if (businessColumnlist != null)
						for (int j = 0; j < businessColumnlist.size(); j++) {
							Map dataColumn = (Map) businessColumnlist.get(j);
							if (null != dataColumn.get("name"))
								businessColMap.put(dataColumn.get("name")
										.toString().toLowerCase(), dataColumn);
						}
				}
				Iterator it = columnMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry entry = (Map.Entry) it.next();
					Object key = entry.getKey();
					DataColumn dataColumn = (DataColumn) entry.getValue();
					if (businessColMap.containsKey(key)) {
						Map bdc = (Map) businessColMap.get(key);
						String tempDataType = null;
						if (null != bdc.get("dataType"))
							tempDataType = bdc.get("dataType").toString();
						long remoteDataLength = 0;
						if (null != bdc.get("dataLength"))
							remoteDataLength = Long.parseLong(bdc.get(
									"dataLength").toString());
						Long localDataLength = dataColumn.getDataLength();
						if (null == localDataLength
								|| localDataLength.equals("")
								|| localDataLength.longValue() != (long) (remoteDataLength / 2)
								|| null == dataColumn.getDataType()
								|| !dataColumn.getDataType().equalsIgnoreCase(
										tempDataType)) {
							resultList.add(dataColumn.getId());
						}
					} else {
						resultList.add(dataColumn.getId());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			res.getWriter().write(JSONArray.fromObject(resultList).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String synchronousUpdate() {
		HttpServletRequest req = ServletActionContext.getRequest();

		String dataColumnId = req.getParameter("dataColumnId");
		DataColumn dataColumn = dataColumnService.findById(dataColumnId);
		try {
			if (dataColumn != null) {
				String columnName = dataColumn.getName().toUpperCase();
				String tableName = dataColumn.getDataTable().getName()
						.toUpperCase();
				String spaceName = dataColumn.getDataTable().getSpacename()
						.toUpperCase();
				DataSource dataSource = dataColumn.getDataTable()
						.getDataSource();
				if (dataSource != null) {
					javax.sql.DataSource ds = new ZXTJDBCDataSource(dataSource
							.getIpAddress(), dataSource.getPort().toString(),
							dataSource.getSid(), dataSource.getDbType(),
							dataSource.getUsername(), dataSource.getPassword());
					ZXTJDBCTemplate jdbcTemplate = new ZXTJDBCTemplate(ds);
					StringBuffer paramString = new StringBuffer();
					paramString
							.append(" select tb.column_name as name,tb.data_type ,tb.data_length,tb.data_precision as precision,");
					paramString
							.append("tb.data_scale as scale,tb.nullable as nullable,tb.data_default as default_value,pk.constraint_name,pk.pkey ");
					paramString
							.append(" from (select t.column_name,t.data_type,t.data_length,t.data_precision,t.data_scale ,t.nullable,t.data_default,t.table_name ");
					paramString.append(
							" from user_tab_columns t where t.table_name = '")
							.append(tableName)
							.append("' and t.column_name = '").append(
									columnName).append("' ) tb ");
					paramString
							.append(" left join (select col.constraint_name,col.column_name ,'1' as pkey  from user_constraints con,user_cons_columns col ");
					paramString
							.append(
									" where con.constraint_name = col.constraint_name and col.table_name = '")
							.append(tableName).append(
									"' and col.column_name = '").append(
									columnName).append("' ");
					paramString
							.append(" and con.constraint_type = 'P') pk on tb.column_name = pk.column_name ");
					paramString
							.append(
									" left join (select table_name ,tablespace_name from user_tables where table_name = '")
							.append(tableName)
							/*
							 * .append("' and tablespace_name =
							 * '").append(spaceName)
							 */.append("') ut ");
					paramString.append(" on tb.table_name = ut.table_name ");
					Map map = jdbcTemplate.findToMap(paramString.toString());
					StringBuffer updateSql = new StringBuffer();
					updateSql.append(" update eng_dataobject_column t ");
					updateSql.append(" set t.df_name = '").append(
							map.get("name")).append("',t.df_datatype = '")
							.append(map.get("dataType")).append("' ");
					if (null != map.get("dataLength")
							&& !map.get("dataLength").equals("")) {
						updateSql.append(",t.df_length = ").append(
								(long) (Long.parseLong(map.get("dataLength")
										.toString()) / 2));
					}
					if (null != map.get("precision")
							&& !map.get("precision").equals("")) {
						updateSql.append(",t.df_precision = ")
								.append(
										Long.parseLong(map.get("precision")
												.toString()));
					}
					if (null != map.get("scale")
							&& !map.get("scale").equals("")) {
						updateSql.append(",t.df_scale = ").append(
								Long.parseLong(map.get("scale").toString()));
					}
					updateSql.append(" ,t.df_nullable = '").append(
							map.get("nullable")).append(
							"',t.df_defaultvalue = '").append(
							map.get("defaultValue")).append(
							"',t.df_issystem_field = '")
							.append(map.get("pkey")).append(
									"' where t.df_id = '").append(dataColumnId)
							.append("' ");
					ds = new ZXTJDBCDataSource(dataSource.getIpAddress(),
							dataSource.getPort().toString(), dataSource
									.getSid(), dataSource.getDbType(),
							dataSource.getUsername(), dataSource.getPassword());
					jdbcTemplate.setDataSource(ds);
					jdbcTemplate.update(updateSql.toString());

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将字段管理中的字段批量上移、下移
	 * 
	 * @return
	 */
	public String toBatch() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/plain;charset=UTF-8 ");

		String ids = req.getParameter("ids");
		String isTemp = req.getParameter("isTemp");
		String flag = "fail";
		try {
			String[] idArray = ids.split(",");
			StringBuffer idsStr = new StringBuffer();
			for (int i = 0; i < idArray.length; i++) {
				idsStr.append("'" + idArray[i] + "',");
			}
			String queryIds = idsStr.substring(0, idsStr.length() - 1);
			List list = dataColumnService.findAllByIds(queryIds);
			for (int i = 0; i < list.size(); i++) {
				DataColumn column = (DataColumn) list.get(i);
				column.setIstemp(isTemp);
				dataColumnService.update(column);
				final String dataObjectId = column.getDataTable().getId();
				//更新数据对象状态的时候，将相应的表单的状态改变
				if("0".equals(isTemp)){
					formService.updateFormFrameByDoId(dataObjectId, column.getId());
				}else{
					formService.updateAllFormBydeleteField(dataObjectId, column.getName());
				}
				new Thread(){public void run() {
					formService.updatePageService(dataObjectId);
				}}.start();
			}
			// dataColumnService.updateBatchCloumnTemp(ids, isTemp);
			flag = "success";
		} catch (Exception e) {
			flag = "fail";
		}

		try {
			res.getWriter().write(flag);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取数据列信息
	 * @return
	 */
	public String getDataColumnInfo() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String dataColumnId = req.getParameter("dataColumnId");

		dataColumn = dataColumnService.findById(dataColumnId);
		try {
			res.getWriter().write(JSONArray.fromObject(dataColumn).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查看数据对象是不是存在
	 * @return
	 */
	public String checkTableIsParmerkey() {
		String dataTableId = ServletActionContext.getRequest().getParameter(
				"dataTableId");
		String resultForIsParmerkey = dataColumnService
				.checkTableIsParmerkey(dataTableId);
		try {
			ServletActionContext.getResponse().getWriter().write(
					resultForIsParmerkey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ServletActionContext.getResponse().getWriter().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public void setDataColumnService(IDataColumnService dataColumnService) {
		this.dataColumnService = dataColumnService;
	}

	public DataColumn getDataColumn() {
		return dataColumn;
	}

	public void setDataColumn(DataColumn dataColumn) {
		this.dataColumn = dataColumn;
	}

	public String getDataTableId() {
		return dataTableId;
	}

	public void setDataTableId(String dataTableId) {
		this.dataTableId = dataTableId;
	}

	public void setDataTableService(IDataTableService dataTableService) {
		this.dataTableService = dataTableService;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public void setDataSourceService(IDataSourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	public IFormService getFormService() {
		return formService;
	}

	public void setFormService(IFormService formService) {
		this.formService = formService;
	}

}
