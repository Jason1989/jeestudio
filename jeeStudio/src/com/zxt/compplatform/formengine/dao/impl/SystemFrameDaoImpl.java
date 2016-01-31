package com.zxt.compplatform.formengine.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.dao.SystemFrameDao;
import com.zxt.compplatform.formengine.entity.database.SystemLog;
import com.zxt.compplatform.formengine.entity.view.PoolParmers;
import com.zxt.compplatform.formengine.entity.view.TreeAttributes;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.formengine.entity.view.UserDeptrel;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.compplatform.formengine.util.DynamicPoolFactory;
import com.zxt.compplatform.formengine.util.StrTools;
import com.zxt.compplatform.formengine.util.StringFormat;
import com.zxt.compplatform.skip.entity.UserDeskSetVo;
import com.zxt.compplatform.skip.util.DeskTopSetUtil;
import com.zxt.framework.common.util.RandomGUID;
import com.zxt.framework.jdbc.ZXTJDBCTemplate;

/**
 * 系统框架数据持久化操作实现
 * 
 * @author 007
 */
public class SystemFrameDaoImpl  extends ZXTJDBCTemplate implements SystemFrameDao {

	private static final Logger log = Logger
			.getLogger(SystemFrameDaoImpl.class);
	/**
	 * spring jdbc数据库操作模版
	 */
	private JdbcTemplate jdbcTemplate;
	/**
	 * 初始化数据源
	 */
	private DataSource initDataSource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.dao.SystemFrameDao#treeId(java.lang.String)
	 */
	public String treeId(String id_mark) {
		jdbcTemplate.setDataSource(initDataSource);
		String treeId = "-1";
		if ((id_mark != null) && (!"".equals(id_mark))) {
			// String sql = "select ajax_tree_id from test_ajax_tree where
			// id_mark=?";

			// String sql = "select resid from eng_resources where reskey=? and
			// IS_MENU =1";
			String sql = "select resid from eng_resources where reskey=? ";
			SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql,
					new Object[] { id_mark });
			if (temSet.next()) {
				treeId = temSet.getString(1);
			}
		}
		return treeId;
	}

	/**
	 * 获得当前子系统的名称
	 */
	public String systemName(String systemID) {
		String systemName = "";
		jdbcTemplate.setDataSource(initDataSource);
		String sql = "select resname from ENG_RESOURCES where resid= ? ";
		SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql,
				new Object[] { systemID });
		if (temSet != null) {
			if (temSet.next()) {
				systemName = temSet.getString(1);
			}
		}
		return systemName;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.dao.SystemFrameDao#findConnectPools(java.lang.String)
	 */
	public Map findConnectPools(String isCache) {
		/**
		 * 定义多数据源参数 临时变量
		 */
		List poolParmersList = new ArrayList(); // 连接池参数list
		Map temMap = null;
		PoolParmers poolParmers = null;

		/**
		 * 查询多数据源
		 */
		String sql = "select ds_id,ds_type,ds_username,ds_password,ds_ipadress,ds_sid,ds_port from eng_form_datasource";
		List list = jdbcTemplate.queryForList(sql);
		/**
		 * 数据源 连接
		 */
		String url = "";
		/**
		 * 数据源类型
		 */
		int dataSourceType = 0;

		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				try {
					poolParmers = new PoolParmers();
					temMap = (Map) list.get(i);
					poolParmers.setDataSourceID(temMap.get("ds_id").toString());
					poolParmers.setUserName(temMap.get("ds_username")
							.toString());
					poolParmers.setPassword(temMap.get("ds_password")
							.toString());
					dataSourceType = Integer.parseInt(temMap.get("ds_type")
							.toString());

					switch (dataSourceType) {
					case Constant.DATASOURCE_TYPE_ORACLE:
						url = "jdbc:oracle:thin:@"
								+ temMap.get("ds_ipadress").toString() + ":"
								+ temMap.get("ds_sid").toString();
						poolParmers.setUrl("jdbc:oracle:thin:@"
								+ temMap.get("ds_ipadress").toString() + ":"
								+ temMap.get("ds_port").toString() + ":"
								+ temMap.get("ds_sid").toString());
						poolParmers
								.setDriverClassName("oracle.jdbc.driver.OracleDriver");
						break;
					case Constant.DATASOURCE_TYPE_SQLSERVER:

						if (temMap.get("ds_port") == null) {
							url = "jdbc:sqlserver://"
									+ temMap.get("ds_ipadress").toString()
									+ ";databasename="
									+ temMap.get("ds_sid").toString();
						} else {
							url = "jdbc:sqlserver://"
									+ temMap.get("ds_ipadress").toString()
									+ ":" + temMap.get("ds_port").toString()
									+ ";databasename="
									+ temMap.get("ds_sid").toString();

						}

						poolParmers.setUrl(url);
						poolParmers
								.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
						break;
					case Constant.DATASOURCE_TYPE_MYSQL:
						break;
					case Constant.DATASOURCE_TYPE_DB2:
						break;
					default:
						break;
					}

					poolParmersList.add(poolParmers);
				} catch (Exception e) {
					// TODO: handle exception
					log.error("数据源参数封装失败。。");
				}
			}
		}
		/**
		 * 封装多数据源参数
		 */
		Map connectPools = DynamicPoolFactory.createPoolsUtil(poolParmersList);
		return connectPools;
	}

	/**
	 * 查找选项卡下的菜单树
	 */
	public List findTreeList(String menuLevel) {
		// TODO Auto-generated method stub

		// String sql = " select ajax_tree_id,text,parent_id,url from
		// test_ajax_tree where menu_level=? order by MENU_SORT ";

		String sql = "select RESID,RESNAME,PARENTID,RESURI,IS_WORKFLOW from ENG_RESOURCES where MENULEVEL=? and IS_MENU =1 order by RESSORT";
		List list = new ArrayList();
		SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql,
				new Object[] { menuLevel });
		if (temSet != null) {
			int next = 1;
			TreeData treeData = null;
			while (temSet.next()) {
				next = 1;
				treeData = new TreeData();
				treeData.setAttributes(new TreeAttributes());
				if (temSet.getString(next) != null) {
					treeData.setId(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setText(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setParentID(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.getAttributes().setUrl(temSet.getString(next));
				} else {
					treeData.getAttributes().setUrl(temSet.getString("-1"));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.getAttributes().setIsAbleWorkFlow(
							Integer.parseInt(temSet.getString(next)));
				} else {
					treeData.getAttributes().setIsAbleWorkFlow(0);
				}
				list.add(treeData);
			}
		}
		return list;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.dao.SystemFrameDao#findTreeList(java.lang.String,
	 *      java.lang.String)
	 */
	public List findTreeList(String menuLevel, String parentID) {
		// TODO Auto-generated method stub

		// String sql = "select ajax_tree_id,text,parent_id,url from
		// test_ajax_tree where parent_id=? and menu_level=? order by MENU_SORT
		// ";
		String sql = " select RESID,RESNAME,PARENTID,RESURI,IS_WORKFLOW  from ENG_RESOURCES where PARENTID=? and  MENULEVEL=? and IS_MENU =1 order by RESSORT ";

		List list = new ArrayList();
		SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql, new Object[] {
				parentID, menuLevel });
		if (temSet != null) {
			int next = 1;
			TreeData treeData = null;
			while (temSet.next()) {
				next = 1;
				treeData = new TreeData();
				treeData.setAttributes(new TreeAttributes());
				if (temSet.getString(next) != null) {
					treeData.setId(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setText(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setParentID(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.getAttributes().setUrl(temSet.getString(next));
				} else {
					treeData.getAttributes().setUrl(temSet.getString("-1"));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.getAttributes().setIsAbleWorkFlow(
							Integer.parseInt(temSet.getString(next)));
				} else {
					treeData.getAttributes().setIsAbleWorkFlow(0);
				}
				list.add(treeData);
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.dao.SystemFrameDao#findTreeList(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public List findTreeList(String menuLevel, String parentID, String treeIds) {
		// TODO Auto-generated method stub
		// String sql = "select ajax_tree_id,text,parent_id,url from
		// test_ajax_tree where parent_id=? and menu_level=? and ajax_tree_id
		// in("+treeIds+") order by MENU_SORT ";
		String sql = " select RESID,RESNAME,PARENTID,RESURI,IS_WORKFLOW,RESSTYLE,RESTYPE,workflow_fiter  from ENG_RESOURCES where PARENTID=? and  MENULEVEL=? and IS_MENU =1 and RESID in("
				+ treeIds + ")  order by RESSORT ";

		List list = new ArrayList();
		jdbcTemplate.setDataSource(initDataSource);
		SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql, new Object[] {
				parentID, menuLevel });
		if (temSet != null) {
			int next = 1;
			TreeData treeData = null;

			while (temSet.next()) {
				next = 1;
				treeData = new TreeData();
				treeData.setAttributes(new TreeAttributes());
				if (temSet.getString(next) != null) {
					treeData.setId(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setText(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setParentID(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.getAttributes().setUrl(temSet.getString(next));
				} else {
					treeData.getAttributes().setUrl(temSet.getString("-1"));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.getAttributes().setIsAbleWorkFlow(
							Integer.parseInt(temSet.getString(next)));
				} else {
					treeData.getAttributes().setIsAbleWorkFlow(0);
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setIconCls(temSet.getString(next));
				} else {
					treeData.setIconCls("head_btn1");
				}
				// 资源类型
				next++;
				if (temSet.getString(next) != null) {
					treeData.setResType(temSet.getString(next));
				}

				next++;
				if (temSet.getString(next) != null) {
					treeData.getAttributes().setWorkflow_fiter(
							temSet.getString(next));
				} else {
					treeData.getAttributes().setWorkflow_fiter("none");
				}
				list.add(treeData);
			}
		}
		return list;
	}

	public DataSource getInitDataSource() {
		return initDataSource;
	}

	public void setInitDataSource(DataSource initDataSource) {
		this.initDataSource = initDataSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.dao.SystemFrameDao#findTreeList_(java.lang.String,
	 *      java.lang.String)
	 */
	public List findTreeList_(String menuLevel, String treeIds) {
		// TODO Auto-generated method stub

		// String sql = " select ajax_tree_id,text,parent_id,url from
		// test_ajax_tree where menu_level=? and ajax_tree_id in("+treeIds+")
		// order by MENU_SORT ";
		String sql = " select RESID,RESNAME,PARENTID,RESURI,IS_WORKFLOW,workflow_fiter from ENG_RESOURCES where   MENULEVEL=?  and IS_MENU =1 and RESID in("
				+ treeIds + ")   order by RESSORT ";
		List list = new ArrayList();
		jdbcTemplate.setDataSource(initDataSource);
		SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql,
				new Object[] { menuLevel });
		if (temSet != null) {
			int next = 1;
			TreeData treeData = null;

			while (temSet.next()) {
				next = 1;
				treeData = new TreeData();
				treeData.setAttributes(new TreeAttributes());
				if (temSet.getString(next) != null) {
					treeData.setId(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setText(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setParentID(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.getAttributes().setUrl(temSet.getString(next));
				} else {
					treeData.getAttributes().setUrl(temSet.getString("-1"));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.getAttributes().setIsAbleWorkFlow(
							Integer.parseInt(temSet.getString(next)));
				} else {
					treeData.getAttributes().setIsAbleWorkFlow(0);
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.getAttributes().setWorkflow_fiter(
							temSet.getString(next));
				} else {
					treeData.getAttributes().setWorkflow_fiter("none");
				}
				list.add(treeData);
			}
		}

		return list;

	}

	/**
	 * 查询当前组织机构下的下级部门
	 */
	public List queryForDownOrg(String orgId) {
		// TODO Auto-generated method stub
		String oracleSql = " select t_organization.oid,t_organization.oname,t_org_org.upid "
				+ " from t_organization "
				+ " left join t_org_org "
				+ " on t_org_org.downid = t_organization.oid "
				+ " start with t_organization.oid=230900 "
				+ " connect by prior t_organization.oid = t_org_org.upid ";

		String sqlServer = " select t_organization.oid,t_organization.oname,t_org_org.upid "
				+ " from t_organization "
				+ " left join t_org_org "
				+ " on t_org_org.downid = t_organization.oid ";
		jdbcTemplate.setDataSource(initDataSource);

		String dataSourceType = "";
		try {
			String connectUrl = initDataSource.getConnection().getMetaData()
					.getURL();
			if (connectUrl.indexOf("jdbc:sqlserver:") > 0) {
				dataSourceType = "SQLSERVER";
			} else if (connectUrl.indexOf("jdbc:oracle:thin") > 0) {
				dataSourceType = "SQLSERVER";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (dataSourceType.equals("SQLSERVER")) {
			return jdbcTemplate.queryForList(oracleSql, new Object[] { orgId });
		} else {
			return jdbcTemplate.queryForList(sqlServer);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.dao.SystemFrameDao#finDeskSetVo(java.lang.String,
	 *      java.lang.String)
	 */
	public UserDeskSetVo finDeskSetVo(String userId, String systemId) {
		// TODO Auto-generated method stub
		UserDeskSetVo userDeskSetVo = new UserDeskSetVo();
		int next = 1;

		String sql = " select  DK_ID,DK_DESKTOP_SETTINGS,DK_USER_ID,DK_SYSTEM_ID from ENG_USER_DESKTOP  where DK_USER_ID=? and DK_SYSTEM_ID=?  ";
		List list = new ArrayList();
		jdbcTemplate.setDataSource(initDataSource);
		SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql, new Object[] {
				userId, systemId });

		if (temSet != null) {
			if (temSet.next()) {
				if (temSet.getString(next) != null) {
					userDeskSetVo.setId(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					userDeskSetVo.setXmlSet(temSet.getString(next));
					userDeskSetVo.setMenuSettings(DeskTopSetUtil
							.settingToUserSet(temSet.getString(next)));
				}
				next++;
				if (temSet.getString(next) != null) {
					userDeskSetVo.setUserId(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					userDeskSetVo.setSystemId(temSet.getString(next));

				}
			}
		}
		// return DeskTopSetUtil.xmlToUserDeskSetVo();
		return userDeskSetVo;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.dao.SystemFrameDao#findTreeList_(java.lang.String)
	 */
	public List findTreeList_(String treeIds) {
		// TODO Auto-generated method stub
		// String sql= " select RESID,RESNAME,PARENTID,RESURI from ENG_RESOURCES
		// where RESID in("+treeIds+") order by RESSORT ";

		String sql = " select RESID,RESNAME,PARENTID,RESURI,MENULEVEL,DEFAULT_SKIN,SELECT_ENABLE from ENG_RESOURCES where  resid in("
				+ treeIds + ")  and IS_MENU =1 order by RESSORT ";

		List list = new ArrayList();
		if ((treeIds == null) || "".equals(treeIds)) {
			return list;
		}
		jdbcTemplate.setDataSource(initDataSource);
		SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql);
		if (temSet != null) {
			int next = 1;
			TreeData treeData = null;
			TreeAttributes attributes = null;
			while (temSet.next()) {
				next = 1;
				treeData = new TreeData();
				if (temSet.getString(next) != null) {
					treeData.setId(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setText(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setParentID(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					attributes = new TreeAttributes();
					attributes.setUrl(temSet.getString(next));
					treeData.setAttributes(attributes);
				} else {
					attributes = new TreeAttributes();
					attributes.setUrl("-1");
					treeData.setAttributes(attributes);
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setLevel(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setDefaultSkin(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setSelectSkinEnable(temSet.getString(next));
				}

				list.add(treeData);
			}
		}
		return list;
	}

	/* 根据父节点查询
	 * @see com.zxt.compplatform.formengine.dao.SystemFrameDao#findParntId(java.lang.String)
	 */
	public List findParntId(String parentId) {
		// TODO Auto-generated method stub
		// String sql= " select RESID,RESNAME,PARENTID,RESURI from ENG_RESOURCES
		// where RESID in("+treeIds+") order by RESSORT ";

		String sql = " select RESID,RESNAME,PARENTID,RESURI,MENULEVEL from ENG_RESOURCES where  parentid ="
				+ parentId;

		List list = new ArrayList();
		if ((parentId == null) || "".equals(parentId)) {
			return list;
		}
		jdbcTemplate.setDataSource(initDataSource);
		SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql);
		if (temSet != null) {
			int next = 1;
			TreeData treeData = null;
			TreeAttributes attributes = null;
			while (temSet.next()) {
				next = 1;
				treeData = new TreeData();
				if (temSet.getString(next) != null) {
					treeData.setId(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setText(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setParentID(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					attributes = new TreeAttributes();
					attributes.setUrl(temSet.getString(next));
					treeData.setAttributes(attributes);
				} else {
					attributes = new TreeAttributes();
					attributes.setUrl("-1");
					treeData.setAttributes(attributes);
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setLevel(temSet.getString(next));
				}

				list.add(treeData);
			}
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.dao.SystemFrameDao#saveOrUpdateUserSetting(com.zxt.compplatform.skip.entity.UserDeskSetVo)
	 */
	public int saveOrUpdateUserSetting(UserDeskSetVo userDeskSetVo) {
		// TODO Auto-generated method stub

		String updateSql = " UPDATE ENG_USER_DESKTOP "
				+ " SET DK_USER_ID =? ,DK_DESKTOP_SETTINGS = ?,DK_SYSTEM_ID= ?"
				+ " WHERE DK_ID=?";

		String insertSql = "  INSERT INTO ENG_USER_DESKTOP"
				+ " (DK_ID,DK_USER_ID,DK_DESKTOP_SETTINGS,DK_SYSTEM_ID) "
				+ " VALUES(?,?,?,?) ";
		String menuSetting = DeskTopSetUtil.saveToXmlString(userDeskSetVo
				.getMenuSettings());
		menuSetting = StringFormat.replaceBlank(menuSetting);
		userDeskSetVo.setXmlSet(menuSetting);
		jdbcTemplate.setDataSource(initDataSource);
		int updateCount = 0;
		if (userDeskSetVo.getId() != null) {
			updateCount = jdbcTemplate.update(updateSql, new Object[] {
					userDeskSetVo.getUserId(), userDeskSetVo.getXmlSet(),
					userDeskSetVo.getSystemId(), userDeskSetVo.getId() });
		} else {
			updateCount = jdbcTemplate.update(insertSql, new Object[] {
					RandomGUID.geneGuid(), userDeskSetVo.getUserId(),
					userDeskSetVo.getXmlSet(), userDeskSetVo.getSystemId() });
		}

		return 0;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.dao.SystemFrameDao#findUserIdByUserName(java.lang.String)
	 */
	public String findUserIdByUserName(String userName) {
		// TODO Auto-generated method stub
		String userId = "";
		String sql=null;
		// configSQL.properties 文件
		Map map=systemFrameService.load_serviceConfigSQL();
		String falgChoose=(String)map.get("is_use_reference_dataSource");
		String key="SystemFrameDaoImpl-findUserIdByUserName";
		 if("true".equals(falgChoose) && null !=(String)map.get(key)){
			 try{
					sql=(String)map.get(key);
					sql+=" where LOGINNAME='"+userName+"'";
					Connection con=null;
					ResultSet rs=null;
					PreparedStatement pst=null;
					try{
						con=StrTools.configPropertiesUtil(map, systemFrameService);
						pst=con.prepareStatement(sql);
						rs=pst.executeQuery();
						while(rs.next()){
							userId=rs.getString(1);
						}
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(con!=null)con.close();
					}
					
					
					return userId;
			 }catch(Exception e){
				 e.printStackTrace();
			 }
			 finally{
				 jdbcTemplate.setDataSource(initDataSource);
			 }
		 }else{
			jdbcTemplate.setDataSource(initDataSource);
			 sql = "SELECT USERID FROM T_USERTABLE WHERE USERNAME= ? ORDER BY USERID desc"; 
			SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql,
					new Object[] { userName });
			if (temSet != null) {
				if (temSet.next()) {
					userId = temSet.getString(1);
				}
			}
		 }	
		return userId;
	}

	/**
	 * 根据用户ID查询用户名(uName不是登录名)
	 */
	public String findUNameByUserID(String userID) {
		// TODO Auto-generated method stub
		String userId = "";
		String sql=null;
		// configSQL.properties 文件
		Map map=systemFrameService.load_serviceConfigSQL();
		String falgChoose=(String)map.get("is_use_reference_dataSource");
		String key="SystemFrameDaoImpl-findUNameByUserID";
		 if("true".equals(falgChoose) && null!=(String)map.get(key)){
			 try{	
				 	sql=(String)map.get(key);
				 	sql+="  WHERE PKID="+userID;
				 	ResultSet rs=null;
					PreparedStatement pst=null;
					Connection con=null;
					try{
						con=StrTools.configPropertiesUtil(map, systemFrameService);
						pst=con.prepareStatement(sql);
						rs=pst.executeQuery();
						while(rs.next()){
							userId=rs.getString("uname");
						}
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						con.close();
					}
			 }catch(Exception e){
				 e.printStackTrace();
			 }
			 finally{
				 jdbcTemplate.setDataSource(initDataSource);
			 }
		 }else{
			jdbcTemplate.setDataSource(initDataSource);
			 sql = "SELECT uname FROM T_USERTABLE WHERE userID= ? ";
			SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql,
					new Object[] { userID });
			if (temSet != null) {
				if (temSet.next()) {
					userId = temSet.getString(1);
				}
			}
		 }
		return userId;
	}

	/**
	 * 根据用户ID查询用户级别
	 */
	public String findULevelByUserID(String userID) {
		// TODO Auto-generated method stub
		String userLevel = "";
		jdbcTemplate.setDataSource(initDataSource);
		String sql = "select l.LEVEL_NAME from T_USER_LEVEL l,T_USERTABLE u where u.USERID = "
				+ userID + " and u.LEVELNUMBER = l.ID ";
		SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql);
		if (temSet != null) {
			if (temSet.next()) {
				userLevel = temSet.getString(1);
			}
		}
		return userLevel;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.dao.SystemFrameDao#findAllResource()
	 */
	@SuppressWarnings("unchecked")
	public List findAllResource() {
		// TODO Auto-generated method stub
		String sql = " select RESID,RESNAME,PARENTID,RESURI,MENULEVEL,RESTYPE,IS_MENU,RESKEY,IMGSRC,ROW_NUM from ENG_RESOURCES    order by RESSORT ";

		List list = new ArrayList();
		jdbcTemplate.setDataSource(initDataSource);
		SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql);
		if (temSet != null) {
			int next = 1;
			TreeData treeData = null;
			TreeAttributes attributes = null;
			while (temSet.next()) {
				next = 1;
				treeData = new TreeData();
				if (temSet.getString(next) != null) {
					treeData.setId(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setText(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setParentID(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					attributes = new TreeAttributes();
					attributes.setUrl(temSet.getString(next));
					treeData.setAttributes(attributes);
				} else {
					attributes = new TreeAttributes();
					attributes.setUrl("-1");
					treeData.setAttributes(attributes);
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setLevel(temSet.getString(next));
					switch (Integer.parseInt(treeData.getLevel())) {
					case 1:
						treeData.setIconCls("icon-system");
						break;
					case 2:
						treeData.setIconCls("icon-system-menu");
						break;
					case 3:
						treeData.setIconCls("icon-system-accordion");
						break;
					case 4:
						treeData.setIconCls("icon-system-function");
						break;
					default:
						treeData.setIconCls("");
						break;
					}
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setResType(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setIsMenu(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setResKey(temSet.getString(next));
				}

				list.add(treeData);
			}
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.dao.SystemFrameDao#saveResource(com.zxt.compplatform.formengine.entity.view.TreeData)
	 */
	public int saveResource(TreeData treeData) {

		String insertSql = "INSERT INTO ENG_RESOURCES (RESID,RESNAME,PARENTID,RESTYPE,RESURI,RESKEY,MENULEVEL,RESSORT,IS_MENU,IS_WORKFLOW,workflow_fiter,IMGSRC,ROW_NUM,DEFAULT_SKIN,SELECT_ENABLE) VALUES(?,?,?  ,?,?,?  ,?,?,? ,?,?,?,? ,?,?) ";
		String deleteSql = " DELETE FROM  ENG_RESOURCES WHERE RESID=? ";
		if ((treeData.getId() == null) || "".equals(treeData.getId())) {
			treeData.setId(RandomGUID.geneGuid());
		} else {
			try {
				jdbcTemplate.update(deleteSql,
						new Object[] { treeData.getId() });
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		try {
			Integer isWorkFlow = null;
			String workflow_fiter = "none";
			if (treeData.getAttributes() != null) {
				isWorkFlow = new Integer(treeData.getAttributes()
						.getIsAbleWorkFlow());
				workflow_fiter = treeData.getAttributes().getWorkflow_fiter();
			} else {
				isWorkFlow = new Integer(0);
			}

			Object[] parmerObjects = new Object[] { treeData.getId(),
					treeData.getText(), treeData.getParentID(),

					treeData.getResType(), treeData.getAttributes().getUrl(),
					treeData.getResKey(),

					treeData.getLevel(), treeData.getResSort(),
					treeData.getIsMenu(),

					isWorkFlow, workflow_fiter, treeData.getImgsrc(),
					treeData.getRow_num(), treeData.getDefaultSkin(),
					treeData.getSelectSkinEnable()

			// ,treeData.getAttributes().getIsWorkFlowComPar(),
			// treeData.getAttributes().getIsWorkFlowComParId(),
			// treeData.getAttributes().getIsWorkFlowComParArray()
			};

			jdbcTemplate.update(insertSql, parmerObjects);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 删除资源
	 */
	public String deleteResource(String resID) {
		String sql = " DELETE FROM ENG_RESOURCES WHERE   RESID in(" + resID
				+ ") ";
		try {
			jdbcTemplate.setDataSource(initDataSource);
			jdbcTemplate.update(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.dao.SystemFrameDao#loadResource(java.lang.String)
	 */
	public TreeData loadResource(String resID) {
		// TODO Auto-generated method stub

		String sql = " select RESID,RESNAME,PARENTID,RESURI,MENULEVEL,RESTYPE,RESSORT,RESKEY,IS_MENU,IS_WORKFLOW,workflow_fiter,IMGSRC,ROW_NUM,DEFAULT_SKIN,SELECT_ENABLE from ENG_RESOURCES  WHERE RESID=?    ";
		TreeData treeData = null;

		jdbcTemplate.setDataSource(initDataSource);
		SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql,
				new Object[] { resID });
		if (temSet != null) {
			int next = 1;
			TreeAttributes attributes = new TreeAttributes();
			;
			if (temSet.next()) {
				next = 1;
				treeData = new TreeData();
				if (temSet.getString(next) != null) {
					treeData.setId(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setText(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setParentID(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {

					attributes.setUrl(temSet.getString(next));
				} else {

					attributes.setUrl("-1");

				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setLevel(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setResType(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setResSort(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setResKey(temSet.getString(next));
				}
				next++;
				if (temSet.getString(next) != null) {
					treeData.setIsMenu(temSet.getString(next));
				}
				next++;
				if ((temSet.getString(next) != null)
						&& (!"".equals(temSet.getString(next)))) {
					try {
						attributes.setIsAbleWorkFlow(Integer.parseInt(temSet
								.getString(next)));
					} catch (Exception e) {
						// TODO: handle exception
						log.error("启用工作流状态错误");
					}

				}
				next++;
				if (temSet.getString(next) != null) {
					attributes.setWorkflow_fiter(temSet.getString(next));
				}

				next++;
				if (temSet.getString(next) != null) {
					treeData.setImgsrc(temSet.getString(next));
				}

				next++;
				if (temSet.getString(next) != null) {
					treeData.setRow_num(temSet.getString(next));
				}

				// 查找是否缺省皮肤
				next++;
				if (temSet.getString(next) != null) {
					treeData.setDefaultSkin(temSet.getString(next));
				}

				// 是否可以使用换肤功能
				next++;
				if (temSet.getString(next) != null) {
					treeData.setSelectSkinEnable(temSet.getString(next));
				}
				// next++;
				// if (temSet.getString(next) != null) {
				// attributes.setIsWorkFlowComPar(temSet.getString(next));
				// }
				// next++;
				// if (temSet.getString(next) != null) {
				// attributes.setIsWorkFlowComParId(temSet.getString(next));
				// }
				// next++;
				// if (temSet.getString(next) != null) {
				// attributes.setIsWorkFlowComParArray(temSet.getString(next));
				// }
				treeData.setAttributes(attributes);
			}
		}
		return treeData;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.dao.SystemFrameDao#loadSystemForm()
	 */
	public List loadSystemForm() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		String sql = "select FO_ID,FO_NAME from eng_form_form where fo_ftype='listPage'";
		jdbcTemplate.setDataSource(initDataSource);
		List list = jdbcTemplate.queryForList(sql);

		return list;
	}

	/**
	 * 查找所有角色
	 */
	public List findAllRole() {
		// TODO Auto-generated method stub
		String sql = "SELECT RID,RNAME FROM T_ROLE  ";
		jdbcTemplate.setDataSource(initDataSource);
		return jdbcTemplate.queryForList(sql);
	}

	/**
	 * 保存角色资源配置
	 */
	public int saveRoleResource(Map map) {
		// TODO Auto-generated method stub
		jdbcTemplate.setDataSource(initDataSource);

		String sql = "INSERT INTO T_ROLE_RESC(ROLE_ID,RESC_ID) VALUES( ?,? )";
		String deleteSql = "DELETE T_ROLE_RESC WHERE ROLE_ID=? AND RESC_ID=?  ";
		try {
			jdbcTemplate.update(deleteSql, new Object[] { map.get("ROLE_ID"),
					map.get("RESC_ID") });
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			jdbcTemplate.update(sql, new Object[] { map.get("ROLE_ID"),
					map.get("RESC_ID") });
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.dao.SystemFrameDao#findOrgByUid(java.lang.String)
	 */
	public Map findOrgByUid(String uid) {
		String sql=null;
		List list =null;
		// configSQL.properties 文件
		Map mapPro=systemFrameService.load_serviceConfigSQL();
		String falgChoose=(String)mapPro.get("is_use_reference_dataSource");
		String key="SystemFrameDaoImpl-findOrgByUid";
		if("true".equals(falgChoose) && null!=(String)mapPro.get(key)){
			 try{
				 	
					Connection con=null;
					PreparedStatement pst=null;
					ResultSet rst=null;
					sql=(String)mapPro.get(key);
					if("".equals(uid))sql+=" where userid=-1";
					else sql+=" where userid="+uid;
					List<UserDeptrel> listUser=new ArrayList<UserDeptrel>();
					try{
						con=StrTools.configPropertiesUtil(mapPro, systemFrameService);
						pst=con.prepareStatement(sql);
						rst=pst.executeQuery();
						while(rst.next()){
							UserDeptrel u=new UserDeptrel();
							u.setUserid(rst.getString("userid"));
							u.setOid(rst.getString("oid"));
							u.setOname(rst.getString("oname"));
							listUser.add(u);
						}
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						con.close();
					}
					Map mapFirst = new HashMap();
					if (listUser != null && listUser.size() > 0) {
						mapFirst.put(0,listUser);
					}
					return mapFirst;
//					if("".equals(uid)) return null;
//					Object[] conditions = new Object[] { uid };
//					list = jdbcTemplate.queryForList(sql, conditions);
			 }catch(Exception e){
				 e.printStackTrace();
			 }
			 finally{
				 jdbcTemplate.setDataSource(initDataSource);
			 }
		 }
		else{
		 sql = "select userid,oid,oname from user_union_view where userid = ?";
		 Object[] conditions = new Object[] { uid };
		 list = jdbcTemplate.queryForList(sql, conditions);
		}
		Map map = null;
		if (list != null && list.size() > 0) {
			map = (Map) list.get(0);
		}
		return map;
	}

	/**
	 * 插入一条用户登录信息
	 */
	public int addSystemLog(SystemLog log) {
		int result = zxtJDBCTemplate.judge();
		Object[] obj=null;
		String insertSql=null;
		
		if(result==1){
			Long long1=System.currentTimeMillis(); //oracle ID是根据时间自动生成。
			insertSql = "  INSERT INTO ENG_SYSTEM_LOG"
				+ " (LOG_ID,LOG_USERNAME,LOG_USER_ID,LOG_IP,LOG_TIME,LOG_LOGINNAME,LOG_ROLE) "
				+ " VALUES(?,?,?,?,?,?,?) ";
			obj=new Object[] {long1,
					log.getUser_name(), log.getUser_id(), log.getLog_ip(),
					log.getLog_time(), log.getUser_loginName(),
					log.getUser_roleName() };
		}
		else if(result==2){//sqlserver ID是自增的
			insertSql = "  INSERT INTO ENG_SYSTEM_LOG"
				+ " (LOG_USERNAME,LOG_USER_ID,LOG_IP,LOG_TIME,LOG_LOGINNAME,LOG_ROLE) "
				+ " VALUES(?,?,?,?,?,?) ";
			obj=new Object[] {
					log.getUser_name(), log.getUser_id(), log.getLog_ip(),
					log.getLog_time(), log.getUser_loginName(),
					log.getUser_roleName() };
			
			/**
			 * 加入 系统操作日志功能
			 * 数据库表：ENG_SYSTEM_ACTIONLOG
			 * 登录功能记录添加  <property name="componentsDao" ref="zxtcomponentsDao" ></property>	
			 */		
			componentsDao.addlogSave(null,"用户登录","用户进行登录功能--》登录成功");
		}
		else{		
			return 0;
		}
		return jdbcTemplate.update(insertSql,obj);
	}
	/***
	 * 读取资源文件：configSQL.properties
	 * GUOWEIXIN
	 */
	/**
	 * 系统框架业务操作接口
	 */
	private SystemFrameService systemFrameService;
	public SystemFrameService getSystemFrameService() {
		return systemFrameService;
	}
	public void setSystemFrameService(SystemFrameService systemFrameService) {
		this.systemFrameService = systemFrameService;
	}
	private ZXTJDBCTemplate zxtJDBCTemplate;

	public ZXTJDBCTemplate getZxtJDBCTemplate() {
		return zxtJDBCTemplate;
	}
	public void setZxtJDBCTemplate(ZXTJDBCTemplate zxtJDBCTemplate) {
		this.zxtJDBCTemplate = zxtJDBCTemplate;
	}
	
	private ComponentsDaoImpl componentsDao;

	public ComponentsDaoImpl getComponentsDao() {
		return componentsDao;
	}

	public void setComponentsDao(ComponentsDaoImpl componentsDao) {
		this.componentsDao = componentsDao;
	}


}
