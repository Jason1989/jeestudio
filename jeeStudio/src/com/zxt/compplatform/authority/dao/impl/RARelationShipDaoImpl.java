package com.zxt.compplatform.authority.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zxt.compplatform.authority.dao.RARelationShipDao;
import com.zxt.compplatform.authority.entity.Authority;
import com.zxt.compplatform.authority.entity.RARelationShip;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.compplatform.formengine.util.StrTools;
import com.zxt.framework.jdbc.ZXTJDBCTemplate;

/**
 * 角色资源关系
 * 数据持久化操作实现
 * @author 007
 */
public class RARelationShipDaoImpl implements RARelationShipDao {
	/**
	 * 数据源
	 */
	private DataSource initDataSource;

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.dao.RARelationShipDao#addRelationShip(com.zxt.compplatform.authority.entity.RARelationShip)
	 */
	public void addRelationShip(RARelationShip relationShip) {
		ZXTJDBCTemplate zxtJDBCTemplate = new ZXTJDBCTemplate(initDataSource);
		String sql = "insert into t_role_resc values(?,?)";
		Object[] obj = new Object[] { relationShip.getRoleId(),
				relationShip.getRescId() };
		zxtJDBCTemplate.create(sql, obj);
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.dao.RARelationShipDao#insert(java.lang.String)
	 */
	public void insert(String sql) {
		ZXTJDBCTemplate zxtJDBCTemplate = new ZXTJDBCTemplate(initDataSource);
		zxtJDBCTemplate.create(sql);
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.dao.RARelationShipDao#insertAll(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public void insertAll(List sqlList) {
		ZXTJDBCTemplate zxtJDBCTemplate = new ZXTJDBCTemplate(initDataSource);
		zxtJDBCTemplate.createAll(sqlList);
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.dao.RARelationShipDao#deleteRoleMenu(java.lang.String)
	 */
	public void deleteRoleMenu(String sql) {
		ZXTJDBCTemplate zxtJDBCTemplate = new ZXTJDBCTemplate(initDataSource);
		zxtJDBCTemplate.delete(sql);
	}

	/**
	 * @param initDataSource
	 */
	public void setInitDataSource(DataSource initDataSource) {
		this.initDataSource = initDataSource;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.dao.RARelationShipDao#getAllRecords(java.lang.String)
	 */
	public List getAllRecords(String roleId) {
		String sql = "select ROLE_ID,RESC_ID from T_ROLE_RESC where ROLE_ID = "
				+ roleId;
		ZXTJDBCTemplate zxtJDBCTemplate = new ZXTJDBCTemplate(initDataSource);
		return zxtJDBCTemplate.findToMaps(sql);

	}

	/**
	 * 使用spring提供的jdbctemplate 
	 */
	private JdbcTemplate jdbcTemplate;

	private static final Logger log = Logger
			.getLogger(RARelationShipDaoImpl.class);

	/**
	 * 通过传入角色名的字符串集合（一个或多个）查到对应权限记录集合（去除重复的记录） T_ROLE（RNAME） TEST_AJAX_TREE
	 */
	public List listAuthoritys(String rnameString) {
		List Authoritys = new ArrayList();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			jdbcTemplate.setDataSource(initDataSource);
			conn = jdbcTemplate.getDataSource().getConnection();
			StringBuffer sql = new StringBuffer();
			sql
					.append("select DISTINCT RESID,RESNAME,PARENTID,RESURI,MENULEVEL,RESSORT,APP_ID,PARENT_APP_ID ");
			sql.append("from ENG_RESOURCES ");
			sql.append("left join T_ROLE_RESC on RESID=RESC_ID ");
			sql.append("left join T_ROLE on RID=ROLE_ID ");
			if (rnameString.indexOf(",") == -1) {
				sql.append("where RNAME=?");
				pstm = conn.prepareStatement(sql.toString());
				pstm.setString(1, rnameString);
			} else {
				String str[] = rnameString.split(",");
				sql.append("where RNAME in(");
				for (int i = 0; i < str.length - 1; i++) {
					sql.append("'" + str[i] + "',");
				}
				sql.append("'" + str[str.length - 1] + "')");
				pstm = conn.prepareStatement(sql.toString());
			}

			rs = pstm.executeQuery();
			while (rs.next()) {
				Authority au = new Authority();
				au.setAjaxTreeId(rs.getString("AJAX_TREE_ID"));
				au.setText(rs.getString("TEXT"));
				au.setParentId(rs.getString("PARENT_ID"));
				au.setUrl(rs.getString("PARENT_ID"));
				au.setMenuLevel(new Integer(rs.getInt("MENU_LEVEL")));
				au.setMenuSort(new Integer(rs.getInt("MENU_SORT")));
				au.setAppId(rs.getString("APP_ID"));
				au.setParentAppId(rs.getString("PARENT_APP_ID"));
				Authoritys.add(au);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			try {
				conn.close();// 放回连接池
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/**
			 * jdbc操作类 切换至初始化根连接池
			 */
			try {
				jdbcTemplate.setDataSource(initDataSource);
			} catch (Exception e) {
				// TODO: handle exception
				log.error("切换初始化连接池 失败...");
			}
		}
		return Authoritys;
	}

	/**
	 * 通过传入角色名的字符串集合（一个或多个）查到对应权限id集合(去除重复记录) T_ROLE(RNAME) T_ROLE_RESC(RESC_ID)
	 * 
	 * @return 传出的List是角色对应权限所有权限，每个list中存储的是resource的id
	 */
	public List listrights(String rnameString) {
		List rights = new ArrayList();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			jdbcTemplate.setDataSource(initDataSource);
			conn = jdbcTemplate.getDataSource().getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("select DISTINCT RESC_ID ");
			sql.append("from T_ROLE_RESC ");
			sql.append("left join T_ROLE on RID=ROLE_ID ");
			if (rnameString.indexOf(",") == -1) {
				sql.append("where RNAME=?");
				pstm = conn.prepareStatement(sql.toString());
				pstm.setString(1, rnameString);
			} else {
				String str[] = rnameString.split(",");
				sql.append("where RNAME in(");
				for (int i = 0; i < str.length - 1; i++) {
					sql.append("'" + str[i] + "',");
				}
				sql.append("'" + str[str.length - 1] + "')");
				pstm = conn.prepareStatement(sql.toString());
			}

			rs = pstm.executeQuery();

			while (rs.next()) {
				// RARelationShip ra=new RARelationShip();
				// ra.setResc_id(rs.getString("RESC_ID"));
				rights.add("'" + rs.getString("RESC_ID") + "'");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			
			/**
			 * jdbc操作类 切换至初始化根连接池
			 */
			try {
				jdbcTemplate.setDataSource(initDataSource);
			} catch (Exception e) {
				// TODO: handle exception
				log.error("切换初始化连接池 失败...");
			}
		}
		return rights;
	}

	/**
	 * 通过传入角色名的字符串集合（一个或多个）查到对应权限id集合(去除重复记录) T_ROLE(RNAME) T_ROLE_RESC(RESC_ID)
	 * 
	 * @return 传出的List是角色对应权限所有权限，每个list中存储的是resource的实体
	 */
	public List listRights(String rnameString, String systemId) {
		List rights = new ArrayList();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		StringBuffer sql = new StringBuffer();
		try {
			Map map=systemFrameService.load_serviceConfigSQL();
			String falgChoose=(String)map.get("is_use_reference_dataSource");
			String keya="RARelationShipDaoImpl-listRights";
			String keyb="RARelationShipDaoImpl-listRights-news";
			Connection con=null;
			if("true".equals(falgChoose) && null !=(String)map.get(keyb)){
				//通过configSQL.properties调用外部组织机构数据
				String key="RARelationShipDaoImpl-listRightsZxtPlat";
					String sqlCurr=(String)map.get(key);
					//sql.append(sqlCurr+"(SELECT RESID FROM [SGS_DIGICITY_MDS].[SGS_DIGICITY_MDS].[VIEW_ROLEPRIVI] where ROLEID in( select PKID from SGS_DIGICITY_MDS.SGS_DIGICITY_MDS.MDS_DS_ROLE");
					sql.append(sqlCurr + keya);
					
					String str[] = rnameString.split(",");
					//sql.append("  where ROLENAME in(");
					sql.append("  where " + keyb + " in(");
					for (int i = 0; i < str.length - 1; i++) {
						sql.append("'" + str[i] + "',");
					}
					sql.append("'" + str[str.length - 1] + "')))");
					
					jdbcTemplate.setDataSource(initDataSource);
					conn = jdbcTemplate.getDataSource().getConnection();
				
			}else{
				//调用平台组织机构数据
				jdbcTemplate.setDataSource(initDataSource);
				conn = jdbcTemplate.getDataSource().getConnection();
				
				sql
						.append(
								"select RESID,RESNAME,PARENTID,RESTYPE,RESURI,RESKEY,RESSTYLE,MENULEVEL,DEFAULT_SKIN,SELECT_ENABLE,")
						.append(
								"RESSORT,IS_MENU,IS_WORKFLOW,workflow_fiter,IMGSRC,ROW_NUM from ENG_RESOURCES where RESID in (");
				sql.append("select DISTINCT RESC_ID ");
				sql.append("from T_ROLE_RESC ");
				sql.append("left join T_ROLE on RID=ROLE_ID ");
				if (rnameString.indexOf(",") == -1) {
					sql.append("where RNAME='"+rnameString+"'");
					sql.append(") ");
					pstm = conn.prepareStatement(sql.toString()
							+ " order by RESSORT ");
					//pstm.setString(1, rnameString);
				} else {
					String str[] = rnameString.split(",");
					sql.append("where RNAME in(");
					for (int i = 0; i < str.length - 1; i++) {
						sql.append("'" + str[i] + "',");
					}
					sql.append("'" + str[str.length - 1] + "'))");
				}
			}
			pstm = conn.prepareStatement(sql.toString()
					+ " order by RESSORT ");
			rs = pstm.executeQuery();
			TreeData resource = null;
			while (rs.next()) {
				resource = new TreeData();
				resource.setId(rs.getString("RESID"));
				resource.setText(rs.getString("RESNAME"));
				if("".equals(rs.getString("PARENTID"))){
					resource.setParentID("0");
				}else
				resource.setParentID(rs.getString("PARENTID"));
				resource.setResType(rs.getString("RESTYPE"));
				resource.getAttributes().setUrl(rs.getString("RESURI"));
				resource.setResKey(rs.getString("RESKEY"));
				resource.setIconCls(rs.getString("RESSTYLE"));
				resource.setLevel(rs.getString("MENULEVEL"));
				resource.setResSort(rs.getString("RESSORT"));
				resource.setIsMenu(rs.getString("IS_MENU"));
				resource.setImgsrc(rs.getString("IMGSRC"));
				resource.setRow_num(rs.getString("ROW_NUM"));
				resource.setDefaultSkin(rs.getString("DEFAULT_SKIN"));
				resource.setSelectSkinEnable(rs.getString("SELECT_ENABLE"));
				resource.getAttributes().setIsAbleWorkFlow(
						rs.getInt("IS_WORKFLOW"));
				resource.getAttributes().setWorkflow_fiter(
						rs.getString("workflow_fiter"));
				// resource.getAttributes().setIsWorkFlowComPar(rs.getString("IS_WORKFLOWCOMPAR"));
				// resource.getAttributes().setIsWorkFlowComParId(rs.getString("IS_WORKFLOWCOMPARID"));
				// resource.getAttributes().setIsWorkFlowComParArray(rs.getString("IS_WORKFLOWCOMPARARRAY"));
				rights.add(resource);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/**
			 * jdbc操作类 切换至初始化根连接池
			 */
			try {
				jdbcTemplate.setDataSource(initDataSource);
			} catch (Exception e) {
				log.error("切换初始化连接池 失败...");
			}
		}
		return rights;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public DataSource getInitDataSource() {
		return initDataSource;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.dao.RARelationShipDao#findResourceIDsByRoleID(java.lang.String)
	 */
	@Override
	public String findResourceIDsByRoleID(String roleID) {
		// TODO Auto-generated method stub
		StringBuffer resourceIDs = new StringBuffer();
		resourceIDs.append("NO_RESOURCEID");
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		String sql=null;
		int falgCon=0;//标识是否关闭Connection
		try {
			
			//configSQL.properties 文件
			Map map=systemFrameService.load_serviceConfigSQL();
			String falgChoose=(String)map.get("is_use_reference_dataSource");
			String key="RARelationShipDaoImpl-findResourceIDsByRoleID";
			if("true".equals(falgChoose) && null!=(String)map.get(key)){
				 sql=(String)map.get(key);					
				 conn=StrTools.configPropertiesUtil(map, systemFrameService);
				 falgCon=1;
			}else{
				jdbcTemplate.setDataSource(initDataSource);
				conn = jdbcTemplate.getDataSource().getConnection();
				 sql = " select DISTINCT RESC_ID from T_ROLE_RESC  where ROLE_ID=? ";			
			}
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, roleID);
			rs = pstm.executeQuery();

			while (rs.next()) {
				if (rs.getString("RESC_ID")!=null) {
					resourceIDs.append("," + rs.getString("RESC_ID"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			if(falgCon==1){
				try {
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			jdbcTemplate.setDataSource(initDataSource);
		}
		return resourceIDs.toString();
	}
	/***
	 * 读取资源文件：configSQL.properties
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
}
