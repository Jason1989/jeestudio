package com.zxt.compplatform.authority.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zxt.compplatform.authority.dao.RoleDao;
import com.zxt.compplatform.authority.entity.OrgRole;
import com.zxt.compplatform.authority.entity.Role;
import com.zxt.compplatform.authority.entity.RoleUser;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.entity.view.RoleBasic;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.formengine.entity.view.UserBasic;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.compplatform.formengine.util.StrTools;
import com.zxt.compplatform.organization.entity.TOrganization;
import com.zxt.framework.common.util.RandomGUID;
import com.zxt.framework.jdbc.ZXTJDBCTemplate;

/**
 * 角色 数据持久化操作实现
 * 
 * @author 007
 */
public class RoleDaoImpl extends ZXTJDBCTemplate implements RoleDao {
	/*
	 * 查询所有角色与部门无关带分页
	 * 
	 */
	public List findRoleAllList(int rows, int page) {

		Map map = systemFrameService.load_serviceConfigSQL();
		String falgChoose = (String) map.get("is_use_reference_dataSource");
		String key = "RoleDaoImpl-findRoleAllList";
		if ("true".equals(falgChoose) && null != (String) map.get(key)) {

			String sql1 = (String) map.get(key);
			// 替换拼接字符串方式 replaceRows = rows replacePage= rows*(page-1)
			int currPage = rows * (page - 1);
			String result = sql1.replaceFirst("replaceRows", rows + "");
			result = result.replaceFirst("replacePage", currPage + "");
			
			Connection con = null;
			try {
				con =StrTools.configPropertiesUtil(map, systemFrameService);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return this.execConnection(con, result, RoleBasic.class);// 调用上方连接方法
		} else {
			String result = "0";
			result = judge() + "";
			String sql = "";
			if (Constant.DB_TYPE_ORACLE.equals(result)) {
				sql = "select RID,RNAME,RFUNCTION,RNOTE from   (select ROLE.*,ROWNUM rn from( select * from   T_ROLE) ROLE where rownum<="
						+ (page * rows) + ") where rn> " + (+page - 1) * rows;
			} else if (Constant.DB_TYPE_SQLSERVER.equals(result)) {
				sql = "select top " + rows
						+ " * from T_ROLE r  where r.RID not in"
						+ "(select top " + rows * (page - 1)
						+ " t.RID from T_ROLE t order by t.RID) order by r.RID";
			}
			return find(sql, RoleBasic.class);
		}
	}

	/*
	 * 查询所有角色总数与部门无关
	 * 
	 */
	public int findRoleAllNum() {
		String sql = "select COUNT(*) from T_ROLE";
		return find(sql);

	}

	/**
	 * 查找所有角色
	 */
	public List findAllRole() {
		String sql = "select rid as id,rname as name,rfunction,rnote as note from t_role";
		return find(sql, Role.class);
	}

	/**
	 * 角色分页
	 */
	public List roleListPag(int page, int rows) {
		int result = judge();
		String sql = "";
		if (result == 1) {
			sql = "select *from " + "(" + "select A.*, rownum num "
					+ "from (select *from t_role) A " + "where rownum <= "
					+ (page * rows) + ")" + "where num > " + (+page - 1) * rows;
		} else if (result == 2) {
			sql = "select top "
					+ rows
					+ " * "
					+ "from (select r.RID ,r.RNAME ,r.RFUNCTION,r.RNOTE from ENG_ORG_ROLE eor , t_role r , T_ORGANIZATION o where r.RID = eor.ROLEID and o.OID =eor.ORGID ) t "
					+ "where t.RID not in (select top "
					+ rows
					* (page - 1)
					+ " RID from (select r.RID ,r.RNAME ,r.RFUNCTION,r.RNOTE from ENG_ORG_ROLE eor , t_role r , T_ORGANIZATION o where r.RID = eor.ROLEID and o.OID =eor.ORGID ) p "
					+ "order by p.RID asc )order by t.RID asc";
		}
		return find(sql, RoleBasic.class);
	}

	/**
	 * 查询角色列表 带参数
	 */
	public List roleListPagWithParam(int page, int rows, String rname) {
		int result = judge();
		String sql = "";
		if (result == 1) {
			sql = "select *from " + "(" + "select A.*, rownum num "
					+ "from (select *from t_role where rname like '" + rname
					+ "%') A " + "where rownum <= " + (page * rows) + ")"
					+ "where num > " + (+page - 1) * rows;
		} else if (result == 2) {
			sql = "select top "
					+ rows
					+ " * "
					+ "from (select r.RID ,r.RNAME ,r.RFUNCTION,r.RNOTE from ENG_ORG_ROLE eor , t_role r , T_ORGANIZATION o where r.RID = eor.ROLEID and o.OID =eor.ORGID and o.OID = '"
					+ rname
					+ "') t where "
					+ " t.RID not in (select top "
					+ rows
					* (page - 1)
					+ " RID from (select r.RID ,r.RNAME ,r.RFUNCTION,r.RNOTE from ENG_ORG_ROLE eor , t_role r , T_ORGANIZATION o where r.RID = eor.ROLEID and o.OID =eor.ORGID and o.OID = '"
					+ rname + "' ) p "
					+ "order by p.RID asc )order by t.RID asc";
		}
		return find(sql, RoleBasic.class);
	}

	// *************************************************************************************
	/**
	 * 用户分页(特定角色下的用户分页)
	 */
	public List ListUserUnderRole(int page, int rows, String rid) {
		if (rid == null || rid.length() == 0) {
			rid = -1 + "";
		}
		int result = judge();
		String sql = "";
		if (result == 1) {
			sql = "select userid ,uname,oname,levelname,username from " + "("
					+ "select A.*, rownum num "
					+ "from (select *from view_role_user) A " + "where rid = "
					+ rid + " and  rownum <= " + (page * rows)
					+ " order by oname asc" + ") B " + "where rid = " + rid
					+ " and num > " + (+page - 1) * rows
					+ " order by oname asc";
		} else if (result == 2) {
			sql = "select top "
					+ rows
					+ " userid ,uname,oname,levelname,username from view_role_user where rid ="
					+ rid + " and userid not in" + "(select top " + rows
					* (page - 1) + " userid from view_role_user where rid = "
					+ rid + " order by " + "oname asc)order by oname asc";
		}
		return find(sql, UserBasic.class);
	}

	/**
	 * 查询特定角色下的用户条数
	 */
	public int findTotalRowsUnderRole(String rid) {
		if (rid == null || rid.length() == 0) {
			rid = -1 + "";
		}
		String sql = "select count(*)from t_role_user where rid = " + rid;
		return find(sql);
	}

	/**
	 * 查询角色功能
	 */
	public String findRFunction(String rname) {
		StringBuffer sql = new StringBuffer();
		sql.append("select RFUNCTION ");
		sql.append("from T_ROLE ");
		List list = new ArrayList();
		if (rname.indexOf(",") == -1) {
			sql.append("where RNAME=");
			sql.append("'" + rname + "'");
			list = findToMaps(sql.toString());
		} else {
			String str[] = rname.split(",");
			sql.append("where RNAME in(");
			for (int i = 0; i < str.length - 1; i++) {
				sql.append("'" + str[i] + "',");
			}
			sql.append("'" + str[str.length - 1] + "')");
			list = findToMaps(sql.toString());
		}

		StringBuffer sql2 = new StringBuffer();
		Map map = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			map = (HashMap) list.get(i);
			sql2.append("" + map.get("rfunction") + ",");
		}
		int len = sql2.length();
		if (len > 0) {
			sql2.deleteCharAt(len - 1);
		}

		return sql2.toString();
	}

	// ******************************************************************************************

	/**
	 * 查询角色总条数
	 */
	public int findTotalRows() {
		String sql = "select COUNT(*) from ENG_ORG_ROLE";
		return find(sql);
	}

	/**
	 * 查询角色总条数 带参数
	 */
	public int findTotalRowsWithParam(String rname) {
		String sql = "select COUNT(*) from ENG_ORG_ROLE where ORGID = '"
				+ rname + "'";
		return find(sql);
	}

	/*
	 * 添加组织机构与角色关系
	 */
	public void addOrgRole(OrgRole or) {
		String sql2 = "insert into ENG_ORG_ROLE(PKID,ORGID,ROLEID) values(?,?,?) ";
		Object[] obj2 = new Object[] { or.getPkid(), or.getOrgid(),
				or.getRoleid() };
		create(sql2, obj2);
	}

	/**
	 * 添加角色(修改添加角色上下级关系表) 没有角色上下级关系，默认为根角色 添加组织机构与角色之间关系
	 */
	public void addRole(Role role, OrgRole or) {

		String sql = "insert into t_role values(?,?,?,?)";
		Object[] obj = new Object[] { role.getId(), role.getName(),
				role.getRfunction(), role.getNote() };
		create(sql, obj);
		String sql1 = "insert into t_role_role(id,uproleid,downroleid) values(?,?,?)";
		Object[] obj1 = new Object[] { RandomGUID.geneGuid(), "0", role.getId() };
		create(sql1, obj1);
		if (!"".equals(or.getOrgid())) {
			String sql2 = "insert into ENG_ORG_ROLE(PKID,ORGID,ROLEID) values(?,?,?) ";
			Object[] obj2 = new Object[] { or.getPkid(), or.getOrgid(),
					or.getRoleid() };
			create(sql2, obj2);
		}

	}

	/**
	 * 添加角色(修改添加角色上下级关系表) 没有角色上下级关系，默认为根角色
	 */
	public void addRole(Role role) {
		String sql = "insert into t_role values(?,?,?,?)";
		Object[] obj = new Object[] { role.getId(), role.getName(),
				role.getRfunction(), role.getNote() };
		create(sql, obj);
		String sql1 = "insert into t_role_role values(?,?,?)";
		Object[] obj1 = new Object[] { RandomGUID.geneGuid(), "0", role.getId() };
		create(sql1, obj1);

	}

	/**
	 * 删除角色（修改添加级联删除角色）
	 */
	public void deleteRole(String id) {
		String sql = "delete from t_role where rid in(" + id + ")";
		delete(sql);
		String sql1 = "delete from t_role_role where downroleid in(" + id + ")";
		delete(sql1);
		String sql2 = "delete from ENG_ORG_ROLE where ROLEID in (" + id + ")";
		delete(sql2);
		String sql3 = "delete from T_ROLE_RESC where ROLE_ID in (" + id + ")";
		delete(sql3);
	}

	/**
	 * 查询角色id最大值
	 */
	public int findMaxId() {
		String sql = "select max(rid) from t_role";
		return find(sql);
	}

	/**
	 * 判断某角色名是否存在(添加时)
	 */
	public List isExist(String name) {
		String sql = "select rid as id from t_role where rname = '" + name
				+ "'";
		return find(sql, Role.class);
	}

	/**
	 * 判断角色是否存在(修改时)
	 */
	public int isExist(Role role) {
		String sql = "select rid from t_role where rname = '" + role.getName()
				+ "'" + "and rid != " + role.getId();
		return find(sql);
	}

	/**
	 * 根据角色id查询特定角色
	 */
	public Role findRoleById(String id) {
		String sql = "select rid as id,rname as name,rfunction,rnote as note from t_role where rid ="
				+ id;
		return (Role) find(sql, Role.class).get(0);
	}

	/**
	 * 根据角色id查出所在部门机构的信息
	 */
	public TOrganization findOrgByRoId(String id) {
		String sql = "select * from T_ORGANIZATION where OID = (select ORGID from ENG_ORG_ROLE where ROLEID="
				+ id + ")";
		if (find(sql, TOrganization.class).size() != 0) {
			return (TOrganization) find(sql, TOrganization.class).get(0);
		}
		TOrganization to = new TOrganization();
		to.setOid(0L);
		to.setOname("");
		return to;
	}

	/**
	 * 修改角色
	 */
	public void updateRole(Role role, OrgRole oldor, OrgRole or) {
		String sql = "update t_role set rname = ?, rfunction=?,rnote = ? where rid = ?";
		Object[] obj = new Object[] { role.getName(), role.getRfunction(),
				role.getNote(), role.getId() };
		update(sql, obj);
		String sql1 = "update ENG_ORG_ROLE set ORGID=? ,ROLEID = ? where ORGID=? and ROLEID=?";
		Object[] obj1 = new Object[] { or.getOrgid(), or.getRoleid(),
				oldor.getOrgid(), oldor.getRoleid() };
		update(sql1, obj1);
	}

	/**
	 * 查询特定角色下是否有用户
	 */
	public List isExistUser(String id) {
		String sql = "select rid as id from t_role_user where rid in(" + id
				+ ")";
		return find(sql, Role.class);
	}

	/**
	 * 查找某个角色下的用户
	 */
	public List findUserByRoleId(String id) {
		String sql = "select userid ,uname,oname,levelname from view_role_user where rid in("
				+ id + ")";
		return find(sql, UserBasic.class);
	}

	// **************************************************************
	/**
	 * 查询角色用户表中的最大值(把id当作bigint类型来查)
	 */
	public int findMaxIDFromRole_User() {
		int result = judge();
		String sql = "";
		if (result == 1) {
			sql = "select max(id) from t_role_user";
		} else if (result == 2) {
			sql = "select max(convert(bigint,id)) from t_role_user";
		}
		return find(sql);
	}

	/**
	 * 判断要添加的用户是否重复
	 */
	public List isExistUserInRoleUser(String rid, String uid) {
		String sql = "select rid as id from t_role_user where userid = " + uid
				+ " and rid = " + rid;
		return find(sql, Role.class);
	}

	/**
	 * 给角色用户表添加信息
	 */
	public void addUserToRole(RoleUser roleuser) {
		String sql = "insert into t_role_user values(?,?,?)";
		Object[] obj = new Object[] { roleuser.getId(), roleuser.getRid(),
				roleuser.getUserid() };
		create(sql, obj);
	}

	/**
	 * 删除角色下的特定用户
	 */
	public void deletUserUnderRole(String rid, String uid) {
		String sql = "delete from t_role_user where rid= " + rid
				+ " and userid=" + uid;
		delete(sql);
	}

	// ***************************************************************************

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.authority.dao.RoleDao#getAllUserForCommon(int,
	 *      int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List getAllUserForCommon(int page, int rows, String oid,
			String uname, String username) {
		int result = judge();
		StringBuffer sql = new StringBuffer();
		if (result == 1) {
			sql.append("select userid ,uname,oname,levelname from ");
			sql.append("(");
			sql.append("select A.*, rownum num ");
			sql.append("from (select *from user_union_view) A ");
			sql.append("where 1=1 ");
			if (oid != null && oid.trim().length() != 0 && !oid.equals("-1")) {
				sql.append(" and oid in (" + oid + ")");
			}
			if (username != null && username.trim().length() != 0) {
				sql.append(" and username like '%" + username + "%'");
			}
			if (uname != null && uname.trim().length() != 0) {
				sql.append(" and uname like '%" + uname + "%'");
			}
			sql.append("and  rownum <= " + (page * rows)
					+ " order by oname asc");
			sql.append(") B ");
			sql.append("where 1=1 ");
			if (oid != null && oid.trim().length() != 0 && !oid.equals("-1")) {
				sql.append(" and oid in (" + oid + ")");
			}
			if (username != null && username.trim().length() != 0) {
				sql.append(" and username like '%" + username + "%'");
			}
			if (uname != null && uname.trim().length() != 0) {
				sql.append(" and uname like '%" + uname + "%'");
			}

			sql.append(" and num > " + (+page - 1) * rows);
			sql.append(" order by oname asc");
		} else if (result == 2) {

			Map map = systemFrameService.load_serviceConfigSQL();
			String falgChoose = (String) map.get("is_use_reference_dataSource");
			String key = "user_MDS_DS_USER_DEPARTMENT_USERDEPTREL";
			if ("true".equals(falgChoose)) {
				sql
						.append("select top "
								+ rows
								+ " * from user_MDS_DS_USER_DEPARTMENT_USERDEPTREL where userid not in (select top "
								+ rows * (page - 1) + " userid ");
				sql
						.append("from user_MDS_DS_USER_DEPARTMENT_USERDEPTREL where 1=1 ");
			} else {
				sql
						.append("select top "
								+ rows
								+ " * from user_union_view where userid not in (select top "
								+ rows * (page - 1) + " userid ");
				sql.append("from user_union_view where 1=1 ");
			}

			if (oid != null && oid.trim().length() != 0 && !oid.equals("-1")) {
				sql.append(" and oid in (" + oid + ")");
			}
			if (username != null && username.trim().length() != 0) {
				sql.append(" and username like '%" + username + "%'");
			}
			if (uname != null && uname.trim().length() != 0) {
				sql.append(" and uname like '%" + uname + "%'");
			}
			sql.append("order by userid asc) ");
			if (oid != null && oid.trim().length() != 0 && !oid.equals("-1")) {
				sql.append(" and oid in (" + oid + ")");
			}
			if (username != null && username.trim().length() != 0) {
				sql.append(" and username like '%" + username + "%'");
			}
			if (uname != null && uname.trim().length() != 0) {
				sql.append(" and uname like '%" + uname + "%'");
			}
			sql.append("  and  (is_pseudo_deleted is null or  is_pseudo_deleted !='1' ) order by userid asc");

			if ("true".equals(falgChoose)) {
				
				Connection con = null;
				try {
					con =StrTools.configPropertiesUtil(map, systemFrameService);
				} catch (Exception e) {
					e.printStackTrace();
				}
				 return this.execConnection(con, sql.toString(), UserBasic.class);//
			}
		}
		System.out.println("sql============="+sql.toString());
		return find(sql.toString(), UserBasic.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.authority.dao.RoleDao#userTotalCount(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public int userTotalCount(String oid, String uname, String username) {
		Map map = systemFrameService.load_serviceConfigSQL();
		String falgChoose = (String) map.get("is_use_reference_dataSource");
		String key = "user_MDS_DS_USER_DEPARTMENT_USERDEPTREL";
		String sql="";
		if ("true".equals(falgChoose)) {
			 sql = "select count(*) from user_MDS_DS_USER_DEPARTMENT_USERDEPTREL where 1=1 ";
		}
		else{
			 sql = "select count(*) from user_union_view  where 1=1 ";
		}
		
		if (oid != null && oid.length() != 0 && !oid.equals("-1")) {
			sql += " and oid in( " + oid + ")";
		}
		if (username != null && username.length() != 0) {
			sql += " and username like '%" + username + "%'";
		}
		if (uname != null && uname.length() != 0) {
			sql += " and uname like '%" + uname + "%'";
		}
		
		if ("true".equals(falgChoose)) {
			// 获取缓存中的连接池
			Map poolsMap = systemFrameService.load_connectPools("true");
			ComboPooledDataSource connectPool = (ComboPooledDataSource) poolsMap
					.get(map.get("is_use_reference_dataSourceID"));
			Connection con = null;
			PreparedStatement pst=null;
			ResultSet rst=null;
			int sumCount=0;
			try {
				con = connectPool.getConnection();
				pst=con.prepareStatement(sql);
				rst=pst.executeQuery();
				while(rst.next()){
					sumCount=rst.getInt(1);
				}
				if(con!=null) con.close();
				return sumCount;
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
			}
			// 调用上方连接方法
		}
		
		return find(sql);
	}

	// ***************************************************************************

	/*
	 * (non-Javadoc) 用户下的角色列表
	 * 
	 * @see com.zxt.compplatform.authority.dao.RoleDao#getRoleListUnderUser(java.lang.String)
	 */
	public List getRoleListUnderUser(String userid) {
		String sql = "select rid as id,rname as name,rfunction,rnote as note from"
				+ "(select t.rid,t.rname,t.rfunction,t.rnote,t2.userid  from t_role t "
				+ "left join t_role_user t1 on t.rid = t1.rid "
				+ "left join t_usertable t2 on t1.userid = t2.userid)a "
				+ "where userid = '" + userid + "' order by rid asc";
		return find(sql, Role.class);
	}

	/***************************************************************************
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

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int findRoleAllNum(String userId) {
		// TODO Auto-generated method stub
		String sql = "select COUNT(*) from T_ROLE " +
				" where  RID not in( " +
				" SELECT RID FROM T_ROLE_USER where USERID='"+userId+"' " +
				" ) ";
		return find(sql);
	}

	@Override
	public List findRoleAllList(int rows, int page, String userId) {
		// TODO Auto-generated method stub
		Map map = systemFrameService.load_serviceConfigSQL();
		String falgChoose = (String) map.get("is_use_reference_dataSource");
		String key = "RoleDaoImpl-findRoleAllList";
		if ("true".equals(falgChoose) && null != (String) map.get(key)) {

			String sql1 = (String) map.get(key);
			// 替换拼接字符串方式 replaceRows = rows replacePage= rows*(page-1)
			int currPage = rows * (page - 1);
			String result = sql1.replaceFirst("replaceRows", rows + "");
			result = result.replaceFirst("replacePage", currPage + "");
			
			Connection con = null;
			try {
				con =StrTools.configPropertiesUtil(map, systemFrameService);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return this.execConnection(con, result, RoleBasic.class);// 调用上方连接方法
		} else {
			String result = "0";
			result = judge() + "";
			String sql = "";
			if (Constant.DB_TYPE_ORACLE.equals(result)) {
				sql = "select RID,RNAME,RFUNCTION,RNOTE from   (select ROLE.*,ROWNUM rn from( select * from   T_ROLE) ROLE where rownum<="
						+ (page * rows) + ") where rn> " + (+page - 1) * rows;
			} else if (Constant.DB_TYPE_SQLSERVER.equals(result)) {
				sql = "select top " + rows
						+ " * from T_ROLE r  where r.RID not in"
						
						+ "(select top " + rows * (page - 1)
						+ " t.RID from T_ROLE t order by t.RID) " 
						+ " and  r.RID not in (SELECT RID FROM T_ROLE_USER where USERID='"+userId+"' ) "
						+ " order by r.RID  ";
					
						// + " SELECT RID FROM T_ROLE_USER where USERID='"+userId+"' " 
				
						
			}
			return find(sql, RoleBasic.class);
		}
	}

	@Override
	public int findTotalRowsWithParam(String rname, String userId) {
		// TODO Auto-generated method stub
		String sql = "select COUNT(*) from ENG_ORG_ROLE where ORGID = '"
			+ rname + "'  and  ROLEID not in( " 
			+ " SELECT RID FROM T_ROLE_USER where USERID='"+userId+"' " 
			+ " )";
	return find(sql);
	}

	@Override
	public List roleListPagWithParam(int page, int rows, String rname,
			String userId) {
		// TODO Auto-generated method stub
		int result = judge();
		String sql = "";
		if (result == 1) {
			sql = "select *from " + "(" + "select A.*, rownum num "
					+ "from (select *from t_role where rname like '" + rname
					+ "%') A " + "where rownum <= " + (page * rows) + ")"
					+ "where num > " + (+page - 1) * rows;
		} else if (result == 2) {
			sql = "select top "
					+ rows
					+ " * "
					+ "from (select r.RID ,r.RNAME ,r.RFUNCTION,r.RNOTE from ENG_ORG_ROLE eor , t_role r , T_ORGANIZATION o where r.RID = eor.ROLEID and o.OID =eor.ORGID and o.OID = '"
					+ rname
					+ "') t where "
					+ " t.RID not in (select top "
					+ rows
					* (page - 1)
					+ " RID from (select r.RID ,r.RNAME ,r.RFUNCTION,r.RNOTE from ENG_ORG_ROLE eor , t_role r , T_ORGANIZATION o where r.RID = eor.ROLEID and o.OID =eor.ORGID and o.OID = '"
					+ rname + "' ) p "
					+ " order by p.RID asc ) "
					+ " and t.RID not in ( " 
					+ " SELECT RID FROM T_ROLE_USER where USERID='"+userId+"' " 
					+ " ) "
					+ " order by t.RID asc";
		}
		return find(sql, RoleBasic.class);
	}
}
