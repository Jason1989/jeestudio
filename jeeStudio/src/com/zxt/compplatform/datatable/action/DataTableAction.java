//mon:hxl-2013-5-17-1
//将字段顺序数值，作为[ENG_DATAOBJECT_COLUMN]字段表，[DF_SORTINDEX]序号列的值
//解决导入数据对象时，初始顺序错乱，不按表中的字段顺序排序问题

package com.zxt.compplatform.datatable.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.zxt.compplatform.authority.service.FieldGrantService;
import com.zxt.compplatform.datacolumn.entity.DataColumn;
import com.zxt.compplatform.datacolumn.service.IDataColumnService;
import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.datasource.service.IDataSourceService;
import com.zxt.compplatform.datatable.entity.DataTable;
import com.zxt.compplatform.datatable.service.IDataObjectGroupService;
import com.zxt.compplatform.datatable.service.IDataTableService;
import com.zxt.framework.common.util.DateUtil;
import com.zxt.framework.common.util.RandomGUID;
import com.zxt.framework.jdbc.ZXTJDBCDataSource;
import com.zxt.framework.jdbc.ZXTJDBCTemplate;

/**
 * 数据对象（table）action
 * 
 * @author Administrator
 * 
 */
public class DataTableAction {
	/**
	 * 数据源业务操作接口
	 */
	private IDataSourceService dataSourceService;
	/**
	 * 数据对象分组业务操作接口
	 */
	private IDataObjectGroupService dataObjectGroupService;
	/**
	 * 数据对象业务操作接口
	 */
	private IDataTableService dataTableService;
	/**
	 * 数据对象列业务操作接口
	 */
	private IDataColumnService dataColumnService;
	/**
	 * 数据对象(table)实体
	 */
	private DataTable dataTable;
	/**
	 * 分组id
	 */
	private String groupId;

	/**
	 * 列表入口
	 * 
	 * @return
	 */
	public String toList() {
		return "list";
	}

	/**
	 * 列表
	 * GUOWEIXIN 
	 * 字段授权：根据ID得到其所属的所有表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String listFildGrant() {
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8 ");
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
		// int totalRows = dataTableService.findTotalRows();
		// List dataTableList = dataTableService.findAllByPage(page,rows);
		Map map = new HashMap();
		List dataTableList = null;
		int totalRows=0;
		if("1".equals(groupId)){
			dataTableList=dataTableService.findAllByPageAndGroupIdsFieldGrant(page, rows, groupId);
			totalRows=dataTableService.findTotalRowsByGroupIdsFieldGrant(groupId);
		}
		else{
			List groupIdList = dataObjectGroupService.findChildrenIdById(groupId);
			String groupIds="";
			if (groupIdList != null){
				for (int i = 0; i < groupIdList.size(); i++) {
					if( groupIdList.size()==1){
						groupIds=(String)groupIdList.get(i);
						break;
					}
					groupIds += groupIds == "" ? "'" + groupIdList.get(i) + "'"
							: ",'" + groupIdList.get(i) + "'";
				}
			}
			
			 totalRows = dataTableService.findTotalRowsByGroupIdsFieldGrant(groupIds);//
			
			try {
				dataTableList = dataTableService.findAllByPageAndGroupIdsFieldGrant(page,
						rows, groupIds);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (dataTableList == null) {
				dataTableList = new ArrayList();
			}
		}
		map.put("rows", dataTableList);
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
	 * 列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String list() {
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8 ");
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
		// int totalRows = dataTableService.findTotalRows();
		// List dataTableList = dataTableService.findAllByPage(page,rows);
		Map map = new HashMap();
		List dataTableList = null;
		int totalRows=0;
		if("1".equals(groupId)){
			dataTableList=dataTableService.findAllByPageAndGroupId(page, rows, groupId);
			totalRows=dataTableService.findTotalRowsByGroupId(groupId);
		}
		else{
			List groupIdList = dataObjectGroupService.findChildrenIdById(groupId);
			String groupIds = "";
			if (groupIdList != null){
				for (int i = 0; i < groupIdList.size(); i++) {
					groupIds += groupIds == "" ? "'" + groupIdList.get(i) + "'"
							: ",'" + groupIdList.get(i) + "'";
				}
			}
			
			 totalRows = dataTableService.findTotalRowsByGroupIds(groupIds);
			
			try {
				dataTableList = dataTableService.findAllByPageAndGroupIds(page,
						rows, groupIds);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (dataTableList == null) {
				dataTableList = new ArrayList();
			}
			
		}
		map.put("rows", dataTableList);
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
	 * 
	 * 方法描述 查询
	 * <p>
	 * 传入参数： username(*):(*表示必须参数)用户名 password(*):用户名密码
	 * 
	 * 传出参数（名称/类型）： 1. fieldname/String
	 * {"name":"hsl","id":""}或者"exist"[存在]、"unexist"[不存在](中括号解释返回字符串的意义)
	 * 
	 * action访问地址： (相对于当前项目的地址) system/login_login.action
	 * 
	 * 修改记录： 1. 2011-09-12 代号 修改说明
	 * </p>
	 */
	public String search() {
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		int page = 1;
		if (req.getParameter("pageNumer") != null
				&& !req.getParameter("pageNumer").equals("")) {
			page = Integer.parseInt(req.getParameter("pageNumer"));
		}
		int rows = 0;
		if (req.getParameter("pageSize") != null
				&& !req.getParameter("pageSize").equals("")) {
			rows = Integer.parseInt(req.getParameter("pageSize"));
		}
		String datatableName = "";
		if (null != req.getParameter("datatableName"))
			datatableName = req.getParameter("datatableName");
		String datatableType = "-1";
		if (null != req.getParameter("datatableType")
				&& !"".equals(req.getParameter("datatableType")))
			datatableType = req.getParameter("datatableType");
		// int totalRows = dataTableService.findTotalRows();
		// List dataTableList = dataTableService.findAllByPage(page,rows);
		List groupIdList = dataObjectGroupService.findChildrenIdById(groupId);
		String groupIds = "";
		if (groupIdList != null)
			for (int i = 0; i < groupIdList.size(); i++) {
				groupIds += groupIds == "" ? "'" + groupIdList.get(i) + "'"
						: ",'" + groupIdList.get(i) + "'";
			}
		// int totalRows = dataTableService.findTotalRowsByGroupIds(groupIds);
		// List dataTableList = dataTableService.findAllByPageAndGroupIds(page,
		// rows, groupIds);
		int totalRows = dataTableService.findTotalRowsByCondition(groupIds,
				datatableName, datatableType);
		List dataTableList = dataTableService.findAllByCondition(page, rows,
				groupIds, datatableName, datatableType);
		Map map = new HashMap();
		if (dataTableList == null) {
			dataTableList = new ArrayList();
		}
		map.put("rows", dataTableList);
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
		return "toadd";
	}

	/**
	 * 添加
	 * 
	 * @return
	 */
	public String add() {
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/plain;charset=UTF-8 ");
		if (dataTable.getId() == null || dataTable.getId().equals("")) {
			dataTable.setId(RandomGUID.geneGuid());
		}
		dataTable.setCreateTime(DateUtil.format(DateUtil.getCurrentDate()));
		try {
			// TODO 添加时判断不准确，缺少数据源的条件
			if (dataTableService
					.isExist(dataTable.getId(), dataTable.getName())) {
				res.getWriter().write("exist");
			} else {
				dataTableService.insert(dataTable);
				res.getWriter().write("success");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 多个的导入,同时将表对应的字段悼入表中
	 * 
	 * @return
	 */
	public String addList() {
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/plain;charset=UTF-8 ");
		if (dataTable.getId() == null || dataTable.getId().equals("")) {
			String[] names = dataTable.getName().split(",");
			String flag = "";
			for (int i = 0; i < names.length; i++) {
				DataTable dataTableUtil = new DataTable();
				dataTable.setName(names[i]);
				dataTable.setId(RandomGUID.geneGuid());
				dataTable.setCreateTime(DateUtil.format(DateUtil
						.getCurrentDate()));
				try {
					BeanUtils.copyProperties(dataTableUtil, dataTable);
					if (dataTableService.isExist(dataTableUtil.getId(),
							dataTableUtil.getName(), dataTableUtil
									.getDataSource().getId())) {
						flag += dataTableUtil.getName() + " ";
					} else {
						// insert the one dataObject
						/**
						 * group id 1
						 */
						// dataTableUtil.getDataObjectGroup().setId("1");
						dataTableService.insert(dataTableUtil);
						// 将表格中对应的列添加到表中
						DataSource dataSource = dataSourceService
								.findById(dataTableUtil.getDataSource().getId());
						String tableName = dataTable.getName();
						Map businessColMap = new HashMap();
						if (dataSource != null) {
							javax.sql.DataSource ds = new ZXTJDBCDataSource(
									dataSource.getIpAddress(), dataSource
											.getPort()
											+ "", dataSource.getSid(),
									dataSource.getDbType(), dataSource
											.getUsername(), dataSource
											.getPassword());
							ZXTJDBCTemplate jdbcTemplate = new ZXTJDBCTemplate(
									ds);
							StringBuffer paramString = new StringBuffer();
							String existSql = "";
							String alterSql = "";
							String[] alterSqlStrings=new String[10];
							if (dataSource.getDbType().equals("1")) {
								/**
								 * APP_ID，PARENT_APP_ID，加上前缀ENV_
								 */
								// 添加APP_ID，PARENT_APP_ID
								existSql = " select  count(1) from user_tab_columns t1 where t1.table_name = '"
										+ tableName.toUpperCase()
										+ "' and t1.COLUMN_NAME = 'APP_ID' ";
								alterSqlStrings[0]=" alter table "+tableName+" add APP_ID NVARCHAR2(50)";
								alterSqlStrings[1]=" alter table "+tableName+" add PARENT_APP_ID NVARCHAR2(50)";
								alterSqlStrings[2]=" alter table "+tableName+" add ENV_DATAMETER NVARCHAR2(2000)";
								alterSqlStrings[3]=" alter table "+tableName+" add ENV_DATASTATE NVARCHAR2(500)";
								alterSqlStrings[4]=" alter table "+tableName+" add IS_PSEUDO_DELETED NVARCHAR2(10)";
								alterSqlStrings[5]="comment on column "+tableName+".APP_ID  is '系统添加'";
								alterSqlStrings[6]="comment on column "+tableName+".PARENT_APP_ID is '系统添加'";
								alterSqlStrings[7]="comment on column "+tableName+".ENV_DATAMETER is '工作流信息'";
								alterSqlStrings[8]="comment on column "+tableName+".ENV_DATASTATE is '状态'";
								alterSqlStrings[9]="comment on column "+tableName+".IS_PSEUDO_DELETED is '伪删除'";
//								alterSql = " alter table "
//										+ tableName
//										+ " add APP_ID NVARCHAR2(50) add PARENT_APP_ID NVARCHAR2(50) add ENV_DATAMETER NVARCHAR2(2000) add ENV_DATASTATE NVARCHAR2(500) add IS_PSEUDO_DELETED NVARCHAR2(10) ;comment on column  "
//										+ tableName
//										+ ".APP_ID  is '系统添加';comment on column "
//										+ tableName
//										+ ".PARENT_APP_ID is '系统添加';comment on column "
//										+ tableName
//										+ ".ENV_DATAMETER is '工作流信息';comment on column "
//										+ tableName
//										+ ".ENV_DATASTATE is '状态';comment on column "
//										+ tableName
//										+ ".IS_PSEUDO_DELETED is '伪删除'";

								// TODO 主键信息 添加一个字段（pk.pkey）
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
								paramString
										.append(
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
								// TODO 查询注释信息
								// TODO 也可以添加信息
								paramString
										.append(
												" left join (select comm.column_name,comm.comments from user_col_comments comm where comm.table_name='")
										.append(tableName)
										.append(
												"') cm on cm.column_name = tb.column_name");
								paramString
										.append(
												" left join (select table_name ,tablespace_name from user_tables where table_name = '")
										.append(tableName).append("') ut ");
								// .append("' and tablespace_name =
								// '").append(spaceName).append("') ut ");
								paramString
										.append(" on tb.table_name = ut.table_name ");
							} else if (dataSource.getDbType().equals("2")) {
								// 添加APP_ID，PARENT_APP_ID
								existSql = " SELECT count(1)  FROM sys.objects t,syscolumns t1 where  t.name = '"
										+ tableName
										+ "' and t.object_id = t1.id and t1.name = 'app_id' ";
								alterSql = " alter table "
										+ tableName
										+ " add APP_ID nvarchar(50); alter table "
										+ tableName
										+ " add PARENT_APP_ID nvarchar(50); alter table "
										+ tableName
										+ " add ENV_DATAMETER nvarchar(2000); alter table "
										+ tableName
										+ " add ENV_DATASTATE nvarchar(500); alter table "
										+ tableName
										+ " add IS_PSEUDO_DELETED nvarchar(10); "
										+ " EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'系统添加' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'"
										+ tableName
										+ "', @level2type=N'COLUMN', @level2name=N'APP_ID'; "
										+ " EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'系统添加' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'"
										+ tableName
										+ "', @level2type=N'COLUMN', @level2name=N'PARENT_APP_ID'; "
										+ " EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'工作流字段' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'"
										+ tableName
										+ "', @level2type=N'COLUMN', @level2name=N'ENV_DATAMETER'; "
										+ " EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'伪删除' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'"
										+ tableName
										+ "', @level2type=N'COLUMN', @level2name=N'IS_PSEUDO_DELETED'; "
										+ " EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'状态' ,@level0type=N'SCHEMA', @level0name=N'dbo', @level1type=N'TABLE', @level1name=N'"
										+ tableName
										+ "', @level2type=N'COLUMN', @level2name=N'ENV_DATASTATE'; ";

								paramString = paramString
										.append("SELECT a.colorder N'sort_no', a.name N'name', "
												+ "(case when (SELECT count(*) FROM sysobjects WHERE (name in (SELECT name FROM sysindexes WHERE "
												+ "(id = a.id) AND (indid in  (SELECT indid  FROM sysindexkeys  WHERE (id = a.id) AND "
												+ "(colid in (SELECT colid  FROM syscolumns WHERE  (id = a.id) AND (name = a.name))))))) "
												+ "AND (xtype = 'PK'))>0 then '1' else '0' end) N'primary_key', b.name N'data_type', "
												+ "COLUMNPROPERTY(a.id,a.name,'PRECISION') as N'data_length', isnull(COLUMNPROPERTY(a.id,a.name,'Scale'),0) as N'precision', "
												+ "(case when a.isnullable=1 then 'Y'else 'N' end) N'nullable', isnull(e.text,'') N'default_value', isnull(cast(g.[value] as varchar(200)),'')"
												+ " AS N'description' FROM syscolumns a left join systypes b on a.xtype=b.xusertype inner join sysobjects d on a.id=d.id and "
												+ "d.xtype in ('U','V') and d.name<>'dtproperties' and d.name='"
												+ tableName
												+ "' left join syscomments e on a.cdefault=e.id left join "
												+ "sys.extended_properties g on a.id=g.major_id AND a.colid = g.minor_id order by object_name(a.id),a.colorder");
							}
							if (!existSql.equals("")) {
								javax.sql.DataSource ds2 = new ZXTJDBCDataSource(
										dataSource.getIpAddress(), dataSource
												.getPort()
												+ "", dataSource.getSid(),
										dataSource.getDbType(), dataSource
												.getUsername(), dataSource
												.getPassword());
								ZXTJDBCTemplate jdbcTemp = new ZXTJDBCTemplate(
										ds2);
								int totalRows = jdbcTemp
										.findTotalRows(existSql);
								if (totalRows == 0) {
									if (dataSource.getDbType().equals("1")) {
										jdbcTemp.updateBatch(alterSqlStrings);
									}else
									jdbcTemp.update(alterSql);
								}
							}
							List businessColumnlist = jdbcTemplate
									.findToMaps(paramString.toString());
							if (businessColumnlist != null) {
								for (int k = 0; k < businessColumnlist.size(); k++) {
									Map map = (HashMap) businessColumnlist
											.get(k);
									DataColumn dataColumn = new DataColumn();
									dataColumn.setDataTable(dataTable);
									dataColumn.setId(RandomGUID.geneGuid());
									
									//mon:hxl-2013-5-17-1-1 begin
									dataColumn.setSortNo(Integer.valueOf(String.valueOf(k)));
									//mon:hxl-2013-5-17-1-1 end

									Long precision = new Long(0);
									Integer primaryKey = new Integer(0);
									Long dataLength = new Long(0);
									String precisionStr = map.get("precision")
											.toString();
									String primaryKeyStr = map
											.get("primaryKey").toString();
									String dataLengthStr = map
											.get("dataLength").toString();

									if (precisionStr != null
											&& !"".equals(precisionStr)) {
										precision = new Long(precisionStr);
									}

									String primaryKeyFlag = "1";

									if (primaryKeyStr != null
											&& !"".equals(primaryKeyStr)) {
										primaryKey = new Integer(primaryKeyStr);
										// 如果为主键，则将临时字段改成必用的
										if ("1".equals(primaryKeyStr)) {
											primaryKeyFlag = "0";
										}
									}

									dataColumn.setIstemp("0");

									if (dataLengthStr != null
											&& !"".equals(dataLengthStr)) {
										dataLength = new Long(dataLengthStr);
									}

									if (dataSource.getDbType().equals("1")) {

										// TODO 修改表字段的导入
										dataColumn.setPrecision(precision);
										dataColumn.setIsPrimaryKey(primaryKey);
										dataColumn.setDataLength(dataLength);
										dataColumn.setDataLength(dataLength);
										// dataColumn.setSortNo(new
										// Integer(map.get("sortNo").toString()));
										String description = StringUtils
												.abbreviate(map.get(
														"description")
														.toString(), 50);
										dataColumn.setDescription(description);
										dataColumn.setDataType(map.get(
												"dataType").toString());
										String name = map.get("name")
												.toString();
										dataColumn.setName(name);
										if (description != null
												&& !"".equals(description)) {
											dataColumn.setTitle(description);
										} else {
											dataColumn.setTitle(name);
										}
										dataColumn.setNullable(map.get(
												"nullable").toString());
										dataColumn.setDefaultValue(map.get(
												"defaultValue").toString());

									} else if (dataSource.getDbType().equals(
											"2")) {
										//mon:hxl-2013-5-17-1-2
										Integer sortNo = Integer.valueOf(map
												.get("sortNo").toString());// TODO
																			// sortNo是做什么用的
										dataColumn.setSortNo(sortNo);
										//mon:hxl-2013-5-17-1-2 end

										dataColumn.setDataType(map.get(
												"dataType").toString()
												.toLowerCase());

										String name = map.get("name")
												.toString();
										dataColumn.setName(name);

										String description = StringUtils
												.abbreviate(map.get(
														"description")
														.toString(), 50);
										if (description != null
												&& !"".equals(description)) {
											dataColumn.setTitle((map.get(
													"description").toString()
													.indexOf(",") != -1) ? (map
													.get("description")
													.toString().split(",")[0])
													: map.get("description")
															.toString());
										} else {
											dataColumn.setTitle(name);
										}
										dataColumn.setNullable(map.get(
												"nullable").toString()); // 返回值
																			// 1:是
																			// 0：否
										dataColumn.setDefaultValue(map.get(
												"defaultValue").toString());
										dataColumn.setPrecision(precision); // 没有返回0
										dataColumn.setIsPrimaryKey(primaryKey);// 返回值
																				// 1:是
																				// 0：否
										dataColumn.setDataLength(dataLength);
										
										//mon:hxl-2013-5-17-1-3 begin
										//dataColumn.setSortNo(sortNo);
										//mon:hxl-2013-5-17-1-3 end
										
										dataColumn.setDescription((map.get(
												"description").toString()
												.indexOf(",") != -1) ? (map
												.get("description").toString()
												.split(",")[0]) : map.get(
												"description").toString());
									}
									dataColumnService.insert(dataColumn);
								}
							}
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			try {
				if ("".equals(flag)) {
					res.getWriter().write("success");
				} else {
					res.getWriter().write(flag + " ");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		String dataTableId = req.getParameter("dataTableId");
		if (dataTableId != null && !dataTableId.equals("")) {
			dataTable = dataTableService.findById(dataTableId);
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
			if (dataTableService.isExistUpdate(dataTable.getId(), dataTable
					.getName())) {
				res.getWriter().write("exist");
			} else {
				dataTableService.update(dataTable);
				res.getWriter().write("success");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
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
		String dataTableId = req.getParameter("dataTableId");
		String dataTableIds = req.getParameter("dataTableIds");
		try {
			if (dataTableId != null && !dataTableId.equals("")) {
				int totalrows = dataColumnService
						.findTotalRowsByTableId(dataTableId);
				if (totalrows <= 0) {
					dataTableService.deleteById(dataTableId);
					res.getWriter().write("success");
				} else {
					res.getWriter().write("children");
				}
			}
			if (dataTableIds != null && !dataTableIds.equals("")) {
				int totalrows = dataColumnService
						.findTotalRowsByTableIds(dataTableIds);
				if (totalrows <= 0) {
					dataTableService.deleteAll(dataTableService
							.findAllByIds(dataTableIds));
					res.getWriter().write("success");
				} else {
					res.getWriter().write("children");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除数据对象对应的列
	 * 
	 * @return
	 */
	public String deleteColumns() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/plain;charset=UTF-8 ");
		String dataTableId = req.getParameter("dataTableId");
		String flag = "fail";
		// TODO 事务处理有问题
		try {
			if (dataTableId != null && !dataTableId.equals("")) {
				dataColumnService.deleteAll(dataColumnService
						.findAllByTableId(dataTableId));
				dataTableService.deleteById(dataTableId);
				flag = "success";
			}
			res.getWriter().write(flag);
		} catch (IOException e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 导入
	 * 
	 * @return
	 */
	public String toImport() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		return "toimport";
	}

	/**
	 * 数据库对象导入
	 * 
	 * @return
	 */
	public String toDbImport() {
		return "todbimport";
	}

	public void setDataSourceService(IDataSourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
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

	public void setDataColumnService(IDataColumnService dataColumnService) {
		this.dataColumnService = dataColumnService;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setDataObjectGroupService(
			IDataObjectGroupService dataObjectGroupService) {
		this.dataObjectGroupService = dataObjectGroupService;
	}
	/**
	 * 字段授权 操作业务逻辑实体 SET注入 GUOWEIXIN
	 */
	private FieldGrantService fieldGrantService;
	public void setFieldGrantService(FieldGrantService fieldGrantService) {
		this.fieldGrantService = fieldGrantService;
	}
}
