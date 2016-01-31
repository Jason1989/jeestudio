package com.zxt.compplatform.datasource.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.compplatform.datasource.service.IDataSourceService;
import com.zxt.compplatform.datatable.entity.DataTable;
import com.zxt.compplatform.datatable.service.IDataTableService;
import com.zxt.compplatform.form.service.IFormService;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.framework.common.util.RandomGUID;
import com.zxt.framework.dictionary.service.IDataDictionaryService;
import com.zxt.framework.jdbc.ZXTJDBCDataSource;
import com.zxt.framework.jdbc.ZXTJDBCTemplate;

/**
 * 数据源增删查改操作
 * <p>
 * toList 点击数据对象菜单显示数据源列表页面， list 查询列表数据， toAdd 点击添加按钮显示添加数据源页面，
 * testDBConnection 点击数据源编辑页连接测试按钮测试数据连接是否成功， add 点击数据源编辑页的保存按钮，保存添加的数据源，
 * toUpdate 点击数据源列表页的编辑图标跳转到数据源编辑页， update 点击数据源编辑页的保存按钮，保存修改的数据源， view
 * 点击数据源列表页的查看图标跳转到数据源查看页， delete 点击数据源列表页的删除图标，删除一条数据源， getAllItem
 * 查询数据源,给下拉选赋值， 修改日志说明：<br>
 * 1、001 Sep 9, 2011 10:06:24 AM （+） 描述 2、
 * </p>
 * 
 * @author 001
 * @version 2.00
 */
public class DataSourceAction implements ParameterAware {
	/**
	 * 数据元业务操作接口
	 */
	private IDataSourceService dataSourceService;
	/**
	 * 数据源实体
	 */
	private DataSource dataSource;
	/**
	 * 系统操作类
	 */
	private SystemFrameService systemFrameService;
	/**
	 * 数据对象业务操作接口
	 */
	private IDataTableService dataTableService;
	/**
	 * 字典业务操作接口
	 */
	private IDataDictionaryService dataDictionaryService;
	/**
	 * 表单业务操作接口
	 */
	private IFormService formService;
	/**
	 * 参数
	 */
	private Map params;

	/**
	 * 
	 * 方法描述
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
	public String toList() {
		return "list";
	}

	/**
	 * 
	 * 查询列表页数据，填充到grid，
	 * <p>
	 * 传入参数： page:页号 rows:每页显示数据条数
	 * 
	 * 传出参数（名称/类型）： 1. dataSourceJson/json
	 * {"name":"hsl","id":""}或者"exist"[存在]、"unexist"[不存在](中括号解释返回字符串的意义) 2.
	 * action访问地址： (相对于当前项目的地址) system/login_login.action
	 * 
	 * 修改记录： 1. 2011-09-12 代号 操作说明 2. 2011-09-13 001 操作说明
	 * </p>
	 */
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
		int totalRows = dataSourceService.findTotalRows();
		List dataSourceList = dataSourceService.findAllByPage(page, rows);
		Map map = new HashMap();
		if (dataSourceList == null) {
			dataSourceList = new ArrayList();
		}
		map.put("rows", dataSourceList);
		map.put("total", new Integer(totalRows));
		String dataSourceJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(dataSourceJson);
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
	 * 测试数据源连接是否成功
	 * 
	 * @return
	 */
	public String testDBConnection() {
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/plain;charset=UTF-8 ");
		/*
		 * String url = ""; String driverName = ""; String dbSid = "";
		 * if(dataSource.getDbType().equals("1")){ url = "jdbc:oracle:thin:@";
		 * driverName = "oracle.jdbc.driver.OracleDriver"; dbSid = ":" +
		 * dataSource.getSid(); }else if(dataSource.getDbType().equals("2")){
		 * url = "jdbc:sqlserver://"; driverName =
		 * "com.microsoft.jdbc.sqlserver.SQLServerDriver"; dbSid =
		 * ";databasename=" + dataSource.getSid(); }else
		 * if(dataSource.getDbType().equals("3")){ url = ""; driverName = ""; }
		 * url += dataSource.getIpAddress() + ":" + dataSource.getPort() +
		 * dbSid; String user = dataSource.getUsername(); String password =
		 * dataSource.getPassword(); Connection conn = null; try {
		 * Class.forName(driverName); conn = DriverManager.getConnection(url,
		 * user, password); } catch (ClassNotFoundException e1) {
		 * e1.printStackTrace(); } catch (SQLException e) { e.printStackTrace();
		 * }finally{ try { conn.close(); } catch (Exception e) {
		 * e.printStackTrace(); } }
		 */
		String msg = "fail";
		try {
			if (dataSource != null) {
				String port = "";
				if (dataSource.getPort() != null
						&& !dataSource.getPort().equals("")) {
					port = dataSource.getPort().toString();
				}
				javax.sql.DataSource ds = new ZXTJDBCDataSource(dataSource
						.getIpAddress(), port, dataSource.getSid(), dataSource
						.getDbType(), dataSource.getUsername(), dataSource
						.getPassword());
				if (ds.getConnection() != null) {
					ds.getConnection().close();
					msg = "success";
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			res.getWriter().write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 测试SQl是不是可以执行，如果可以执行返回相应的结果
	 * 
	 * 访问地址：datasource/dataSource!testSQL.action
	 * 
	 * @return
	 * @throws IOException
	 */
	public String testSQL() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8 ");
		// 如果发生异常则返回fail，成功则发挥相应的结果集
		String flag = "fail";

		try {
			// 查看是不是参数传递正确
			String[] dataSourceId = (String[]) params.get("dataSourceId");
			String[] sql = (String[]) params.get("sql");
			if (StringUtils.isNotEmpty(dataSourceId[0])
					&& StringUtils.isNotEmpty(sql[0])) {
				List list = dataSourceService.testSQL(dataSourceId[0], sql[0]);
				flag = JSONArray.fromObject(list).toString();
			}
		} catch (NullPointerException e) {
			flag = "dbErro";// 数据源不存在
		} catch (SQLException se) {
			flag = "sqlError";// sql表达式错误
		}
		response.getWriter().write(flag);

		return null;
	}

	/**
	 * 添加
	 * 
	 * @return
	 */
	public String add() {
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/plain;charset=UTF-8 ");
		if (dataSource.getId() == null || dataSource.getId().equals("")) {
			dataSource.setId(RandomGUID.geneGuid());
		}
		try {

			if (dataSourceService.isExist(dataSource.getId(), dataSource
					.getName())) {
				res.getWriter().write("exist");
			} else {
				dataSourceService.insert(dataSource);
				res.getWriter().write("success");
			}
		} catch (IOException e) {
			e.printStackTrace();
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
		String dataSourceId = req.getParameter("dataSourceId");
		if (dataSourceId != null && !dataSourceId.equals("")) {
			dataSource = dataSourceService.findById(dataSourceId);
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
		res.setContentType("text/plain;charset=UTF-8 ");
		try {
			if (dataSourceService.isExistUpdate(dataSource.getId(), dataSource
					.getName())) {
				res.getWriter().write("exist");
			} else {
				dataSourceService.update(dataSource);
				// 更新连接池
				systemFrameService.update_connectPools("true");
				res.getWriter().write("success");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查看
	 * 
	 * @return
	 */
	public String view() {
		HttpServletRequest req = ServletActionContext.getRequest();
		String dataSourceId = req.getParameter("dataSourceId");
		if (dataSourceId != null && !dataSourceId.equals("")) {
			dataSource = dataSourceService.findById(dataSourceId);
		}
		return "view";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/plain;charset=UTF-8 ");
		String dataSourceId = req.getParameter("dataSourceId");
		String dataSourceIds = req.getParameter("dataSourceIds");
		try {
			if (dataSourceId != null && !dataSourceId.equals("")) {
				dataDictionaryService.deleteAllByDataSourceId(dataSourceId);
				List dataTableList = dataTableService
						.findAllByDataSourceId(dataSourceId);
				if (dataTableList != null && dataTableList.size() > 0) {
					for (int i = 0; i < dataTableList.size(); i++) {
						DataTable dt = (DataTable) dataTableList.get(i);
						formService.deleteAllByObjectId(dt.getId());
					}
				}
				dataTableService.deleteAllByDataSourceId(dataSourceId);
				dataSourceService.deleteById(dataSourceId);
				res.getWriter().write("success");
			}
			if (dataSourceIds != null && !dataSourceIds.equals("")) {
				dataSourceService.deleteAll(dataSourceService
						.findAllByIds(dataSourceIds));
				res.getWriter().write("success");
			}
			// 更新连接池
			systemFrameService.update_connectPools("true");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 查询数据源是否被使用
	 * 
	 * @return
	 */
	public String checkDataSourceIsUse() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/plain;charset=UTF-8 ");
		String dataSourceId = req.getParameter("dataSourceId");
	
		String  result=dataSourceService.checkDataSourceIsUse(dataSourceId);
		
		try {
			res.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 功能：获取数据源的列表
	 * 
	 * @return
	 */
	public String getAllItem() {
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8 ");
		try {
			List list = dataSourceService.findAllAvailable();
			dataSource = new DataSource();
			dataSource.setId("-1");
			dataSource.setName("请选择");
			dataSource.setSortNo(new Integer(-1));
			list.add(0, dataSource);
			String dataJson = JSONArray.fromObject(list).toString();
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取oracle表空间列表 输入：dataSourceId 数据源对象的id 输出：相应表空间列表
	 * 
	 * @return
	 */
	public String getDbSpaces() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/x-json;charset=UTF-8 ");

		String dataSourceId = req.getParameter("dataSourceId");
		if (dataSourceId != null && !dataSourceId.equals("")) {
			// 获取数据源
			dataSource = dataSourceService.findById(dataSourceId);
		}
		if (dataSource != null) {
			// 根据数据源查找表空间
			javax.sql.DataSource ds = new ZXTJDBCDataSource(dataSource
					.getIpAddress(), dataSource.getPort().toString(),
					dataSource.getSid(), dataSource.getDbType(), dataSource
							.getUsername(), dataSource.getPassword());
			ZXTJDBCTemplate jdbcTemplate = new ZXTJDBCTemplate(ds);

			StringBuffer querydbspaceString = new StringBuffer(
					"select tablespaces.tablespace_name as tablespace_id , tablespaces.tablespace_name as tablespace_name from sys.dba_tablespaces tablespaces");

			List tableSpaceList = jdbcTemplate.findToMaps(querydbspaceString
					.toString());

			try {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("tableSapaceId", "-1");
				jsonObj.put("tableSpaceName", "请选择");

				JSONArray array = JSONArray.fromObject(tableSpaceList);
				array.add(jsonObj);
				resp.getWriter().write(array.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 获取oracle中某个表空间中的表或者视图列表 输入：查询的是表还是视图 输出：导出某个用户下的表或视图的列表
	 * 
	 * @return
	 */
	public String getTablesList() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/x-json;charset=UTF-8 ");

		String dataSourceId = req.getParameter("dataSourceId");

		String dataObjectType = req.getParameter("dataObjectType");

		List tableList = null;

		if (dataSourceId != null && !dataSourceId.equals("")) {
			// 获取数据源
			dataSource = dataSourceService.findById(dataSourceId);
		}

		String queryClause = "";
		if (dataSource.getDbType().equals("1")) {
			// sql oracle
			if ("1".equals(dataObjectType)) {// 表
				queryClause = "select tableObj.table_name as table_name , tableObj.tablespace_name as tablespace_name from all_tables tableObj where tableObj.owner = '"
						+ dataSource.getUsername().toUpperCase() + "'";
			} else if ("2".equals(dataObjectType)) {// 视图
				queryClause = "select viewObj.view_name as table_name , viewObj.view_name as tablespace_name from all_views viewObj where viewObj.owner = '"
						+ dataSource.getUsername().toUpperCase() + "'";
			}

		} else if (dataSource.getDbType().equals("2")) {
			String tableType = "";
			// sql server2005
			if (dataObjectType.equals("1")) {
				tableType = "U";
			} else if (dataObjectType.equals("2")) {
				tableType = "V";
			}
			queryClause = new StringBuffer(
					"select s.name as table_name,isnull(cast(e.value as varchar(200)),'') as table_desc "
							+ "from sysobjects s left join (select * from sys.extended_properties where minor_id = 0 and name='MS_Description') e on s.id=e.major_id"
							+ " where type = '"
							+ tableType
							+ "' order by table_name ").toString();
		}

		if (dataSource != null) {
			// 根据数据源查找表空间
			javax.sql.DataSource ds = new ZXTJDBCDataSource(dataSource
					.getIpAddress(), dataSource.getPort() + "", dataSource
					.getSid(), dataSource.getDbType(),
					dataSource.getUsername(), dataSource.getPassword());
			ZXTJDBCTemplate jdbcTemplate = new ZXTJDBCTemplate(ds);

			StringBuffer queryeString = new StringBuffer(queryClause);

			tableList = jdbcTemplate.findToMaps(queryeString.toString());

		}
		try {
			// 列表的纪录个数
			JSONObject gridObj = new JSONObject();
			gridObj.put("total", new Integer(tableList.size()));

			JSONArray array = JSONArray.fromObject(tableList);
			gridObj.put("rows", array);

			resp.getWriter().write(gridObj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 手动数据数据源的id时，验证数据源的id是不是已经存在
	 * 
	 * @return
	 */
	public String idExist() {
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8 ");

		String id = req.getParameter("name");

		DataSource datasource = dataSourceService.findById(id);

		try {
			if (datasource != null) {
				resp.getWriter().write("exist");
			} else {
				resp.getWriter().write("unexist");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void setDataSourceService(IDataSourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setSystemFrameService(SystemFrameService systemFrameService) {
		this.systemFrameService = systemFrameService;
	}

	public IDataSourceService getDataSourceService() {
		return dataSourceService;
	}

	public IDataTableService getDataTableService() {
		return dataTableService;
	}

	public void setDataTableService(IDataTableService dataTableService) {
		this.dataTableService = dataTableService;
	}

	public IDataDictionaryService getDataDictionaryService() {
		return dataDictionaryService;
	}

	public void setDataDictionaryService(
			IDataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}

	public IFormService getFormService() {
		return formService;
	}

	public void setFormService(IFormService formService) {
		this.formService = formService;
	}

	public SystemFrameService getSystemFrameService() {
		return systemFrameService;
	}

	public void setParameters(Map params) {
		this.params = params;
	}

	public Map getParameters() {
		return this.params;
	}

}
