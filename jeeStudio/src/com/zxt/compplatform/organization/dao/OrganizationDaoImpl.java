package com.zxt.compplatform.organization.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.objectweb.asm.xwork.tree.IntInsnNode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.synchrobit.synchroflow.api.bean.SynchroFLOWBean;
import com.synchrobit.synchroflow.enactmentservice.exception.DBException;
import com.zxt.compplatform.authority.entity.Authority;
import com.zxt.compplatform.authority.entity.Role;
import com.zxt.compplatform.authority.entity.RoleUser;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.formengine.entity.view.TreeDataBasic;
import com.zxt.compplatform.formengine.entity.view.UserBasic;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.compplatform.formengine.util.StrTools;
import com.zxt.compplatform.organization.entity.TOrgOrg;
import com.zxt.compplatform.organization.entity.TOrgUser;
import com.zxt.compplatform.organization.entity.TOrganization;
import com.zxt.compplatform.organization.entity.TUserTable;
import com.zxt.framework.jdbc.ZXTJDBCTemplate;

/**
 * @author bozch
 * 2011-03-15
 * 
 */
public class OrganizationDaoImpl extends ZXTJDBCTemplate implements OrganizationDao {
	/*
	 * 根据组织结构id 修改组织结构状态
	 * 
	 */ 
	public void updateOrgClssify(String oid){
		String sql="update T_ORGANIZATION set ROLECLASSIFY='1' where OID='"+oid+"'";
		update(sql);
		
	}
	/*
	 * 根据组织结构id 修改组织结构状态之前将组织机构状态全部归零
	 * 
	 */ 
	public void updateAllOrgClssify(){
		String sql="update T_ORGANIZATION set ROLECLASSIFY='0' ";
		update(sql);
	}
	//获取所有部门
	public List getAll() {
		String sql = "select t.oid,t.oname,t.lead,t.ofunction,t.tel,t.fax,t.oemail,t.onote from t_organization t";
		return find(sql, TOrganization.class);
	}
	
	/**
	 * 跟据部门表和部门关系表和部门状态 得出所需的json
	 * @return
	 */
	public List get_jsonListByClassify(){
		String sql=null;
		
		// configSQL.properties 文件
		Map map=systemFrameService.load_serviceConfigSQL();
		String falgChoose=(String)map.get("is_use_reference_dataSource");
		String key="OrganizationDaoImpl-get_jsonListByClassify";
		if("true".equals(falgChoose) && null!=(String)map.get(key)){
			 try{
				 
					sql=(String)map.get(key);
					Connection conn=null;
					PreparedStatement pstm = null;
					ResultSet rs = null;
					List list = new ArrayList();
					try {
						conn =StrTools.configPropertiesUtil(map, systemFrameService);
						pstm = conn.prepareStatement(sql);
						rs = pstm.executeQuery();
						list = transformResultSetToList(TreeData.class, rs);
						return list;
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							this.closeAll(conn, pstm, rs);
						} catch (Exception e) {
							e.printStackTrace();
						}
						 jdbcTemplate.setDataSource(initDataSource);
					}
					return list;
			 }catch(Exception e){
				 e.printStackTrace();
			 }
			 finally{
				 jdbcTemplate.setDataSource(initDataSource);
			 }
		 }else{
		 sql = "select t.oid as id ,t.oname as text,t_o.upid as parent_i_d from t_organization t left join t_org_org t_o on t.oid = t_o.downid where t.roleclassify='1'";
		 }
		return find(sql,TreeData.class);
	}
	/**
	 * 获取部门数据组合树结构 用于后台。此方法作用是 configSql.properties 不影响后面 表单管理操作 
	 */
	public List getAllJsonListByBack() {
		String sql = "select t.oid as id ,t.oname as text,t_o.upid as parent_i_d ,t.roleclassify as flag from t_organization t left join t_org_org t_o on t.oid = t_o.downid ";
		return find(sql,TreeData.class);
	}
	
	//获取部门数据组合树结构
	public List getAllJsonList() {
		
		String sql=null;
		
		//configSQL.properties 文件
		Map map=systemFrameService.load_serviceConfigSQL();
		String falgChoose=(String)map.get("is_use_reference_dataSource");
		String key="OrganizationDaoImpl-getAllJsonList";
		String sqlTest=(String)map.get(key);
		if("true".equals(falgChoose) && null!=(String)map.get(key)){
			sql=(String)map.get(key);
			Connection con=null;
			try{
				con=StrTools.configPropertiesUtil(map, systemFrameService);
			}catch(Exception e){
				e.printStackTrace();
			}	
		
			 return  execConnection(con, sql, TreeData.class);//调用上方连接方法 
		}else{
			sql = "select t.oid as id ,t.oname as text,t_o.upid as parent_i_d ,t.roleclassify as flag from t_organization t left join t_org_org t_o on t.oid = t_o.downid ";
			return find(sql,TreeData.class);
		}
	}
	
	public List getAllJsonListWithParam(String oid){
		
		String sql = "select t.oid as id ,t.oname as text,t_o.upid as parent_i_d from t_organization t left join t_org_org t_o on t.oid = t_o.downid where  t.OID in  (select ID as OID from GetChild('"+oid+"'))";
		return find(sql,TreeData.class);
	}
	
	//根据部门父节点查询子节点
	public List getJsonChildrenList(String pid){
		String sql =  "select t.oid||'' as id ,t.oname as text,t_o.upid||'' as parent_i_d,"+
		 "(select nvl2(nullif(count(tsf.downid),0),'closed','open') as state  from t_org_org tsf where tsf.upid=t.oid) as state"+
	      " from t_organization t left join t_org_org t_o  on t.oid = t_o.downid"+ 
	       " where t_o.upid="+pid ; 
		return find(sql,TreeData.class);
	}
	
	//根据部门id查询用户(根据oid参数选择是否包含子部门的用户)
	public List getUserByGroupId(String oid){
		String sql = "select t1.userid, t1.username, t1.uname, t1.levelnumber  from t_usertable t1,t_org_user t2 where t1.userid = t2.userid and t2.oid in("+oid+")";
		return find(sql, TUserTable.class);
	}
	
	//根据部门id获取其子部门的id
	public List getOidsByOid(String oid){
		int result = judge();
		String sql = "";
		if(result == 1){
			sql = "select t.downid as oid from t_org_org t start with t.upid = '"+oid+"' connect by prior t.downid = t.upid";
		}else if(result == 2){
			sql = "with cte_child(id,upid,downid) as("+
			 "select id,upid,downid from t_org_org where upid ="+" "+oid+" "+
			 "union all select a.id,a.upid,a.downid from t_org_org a inner join"+" "+
			 "cte_child b on (a.upid=b.downid))"+
			 "select downid as oid from cte_child";
		}
		return find(sql, TOrganization.class);
	}
	
	//根据部门id查询特定部门信息
	public List getOrganization(String oid) {
		String sql = "select oid,oname,lead,ofunction,tel,fax,oemail,onote,orgno,orgcate,endupdatetime from t_organization where oid = "+oid;
		return find(sql, TOrganization.class);
	}
	
	//删除部门
	public void deleteOrganization(String oids) {
		String sql = "delete from t_organization where oid in("+oids+")";
		delete(sql);
	}
	
	//删除部门关系
	public void deleteOrgRelations(String oids){
		String sql = "delete from t_org_org where upid in("+oids+")or downid in("+oids+")";
		delete(sql);
	}
	
	
	//**********************************************************************************
//////开始*******************************用到的方法*******************************//////////////////
	/**
	 * 部门下的用户分页(包括下级部门)
	 */
	public List usersPagination(int page, int rows, String oid) {
		int result = 0;
		result = judge();
		String sql = "";
		if(result == 1){
			sql = "select * from "+"("+"select A.*, rownum num "+"from (select *from user_union_view) A "+ 
			"where oid in ("+oid+") and  rownum <= "+(page*rows)+") B "+"where oid in ("+oid+") and num > "+(+page-1)*rows;
		}else if(result == 2){
			sql = "select top "+rows+" * from user_union_view where "+
			"oid in ("+oid+")and userid not in (select top "+rows*(page-1)+" userid from user_union_view where oid " +
			"in ("+oid+"))";
		}
		if(sql.indexOf("where")>0){
			sql = sql+" and (is_pseudo_deleted is null or  is_pseudo_deleted !='1' )";
		}else{
			sql = sql+"where is_pseudo_deleted is null or  is_pseudo_deleted !='1'";
		}		
		if(result==2){			
			// configSQL.properties 文件
			Map map=systemFrameService.load_serviceConfigSQL();
			String falgChoose=(String)map.get("is_use_reference_dataSource");
			String key="OrganizationDaoImpl-usersPagination";
			if("true".equals(falgChoose) && null!=(String)map.get(key)){
				String sql1=(String)map.get(key);
				int currPage=rows*(page-1);
				String resultSql=sql1.replaceFirst("replaceRows", rows+"");
				resultSql=resultSql.replaceFirst("replacePage", currPage+"");
				resultSql=resultSql.replaceFirst("replaceOid", oid);
				resultSql=resultSql.replaceFirst("replaceOid", oid);
				Connection con=null;
				try{
					con=StrTools.configPropertiesUtil(map, systemFrameService);
				}catch(Exception e){
					e.printStackTrace();
				}			
				 return  this.execConnection(con, resultSql, UserBasic.class); 
			}else{
				return find(sql, UserBasic.class);
			}
	
		}else  return find(sql, UserBasic.class);
	} 
	/**
	 * 用户数量
	 */
	public int getTotalCount(String oid){
		String sql = "select count(*) from user_union_view where oid in ("+oid+") and (is_pseudo_deleted is null or is_pseudo_deleted != '1')";
		return find(sql);
	}
	
	/**
	 * 部门添加
	 */
	public void addOrganization(TOrganization organization){
		int result = 0;
		result = judge();
		String sql = "";
		Object obj[] = null;
	
		sql = "insert into t_organization(OID,ONAME,LEAD,OFUNCTION,TEL,FAX,OEMAIL,ONOTE,ORGNO,ORGCATE,ENDUPDATETIME) values(?,?,?,?,?,?,?,?,?,?,?)";
		obj = new Object[]{
				organization.getOid(),
				organization.getOname(),
				organization.getLead(),
				organization.getOfunction(),
				organization.getTel(),
				organization.getFax(),
				organization.getOemail(),
				organization.getOnote(),
				organization.getOrgno(),
				organization.getOrgcate(),
				organization.getEndupdatetime()};
	
		
		create(sql,obj);
	}
	
	/**
	 * 判断部门是否存在
	 */
	public int isExistOrg(String oid,String oname){
		String sql = "select oid from  t_organization t, t_org_org t2 where t.oid=t2.downid and t2.upid = '"+oid+"' and t.oname = '"+oname+"'" ;
		return find(sql);
	}
	
	/**
	 * 判断部门是否存在
	 */
	public int isExistOrg(String oid,String oupid,String oname){
		String sql = "select oid from  t_organization t, t_org_org t2 where t.oid=t2.downid and t2.upid = '"+oupid+"' and t.oname = '"+oname+"' and t.oid not in ("+oid+")" ;
		return find(sql);
	}
	
	/**
	 * 得到部门中oid的最大值
	 */
	public int maxvalue(){
		String sql = "select max(oid) from t_organization " ;
		return find(sql);
	}
	
	/**
	 * 得到部门上下级表中的最大值
	 */
	public int maxValueFromOrgOrg(){
		String sql = "select max(id) from t_org_org" ;
		return find(sql);
	}
	/**
	 * 向部门的上下级表中添加
	 */
	public void addOrgOrg(TOrgOrg orgorg){
		String	sql = "insert into t_org_org  (ID ,UPID ,DOWNID) values(?,?,?)" ;
		String  deleteSql="DELETE FROM T_ORG_ORG WHERE UPID="+orgorg.getUpid()+" AND DOWNID="+orgorg.getDownid();
		delete(deleteSql);
		Object []	org = new Object[]{orgorg.getId(),orgorg.getUpid(),orgorg.getDownid()} ;
		create(sql,org);
	}
	//**************************************************************************
	
	/**
	 * 修改部门
	 */
	public void updateOrganization(TOrganization organization){
		String sql = "update t_organization set oname=?,ofunction=?,tel=?,fax=?," +
				"oemail=?,onote=? where oid=?";
		Object obj[] = new Object[]{organization.getOname(),organization.getOfunction(),
				organization.getTel(),organization.getFax(),organization.getOemail(),
				organization.getOnote(),organization.getOid()};
		update(sql, obj);
	}
	
	//*********************************************************************
	//根据用户id查询用户名(用户修改调用)
	public List getUserNameById(String userid) {
		String sql = "select t.uname from t_usertable t where t.userid="+userid;
		return find(sql, TUserTable.class);
	}
	
	//查询所有用户
	public List getAllUser(){
		String sql = "select * from t_usertable";
		return find(sql, TUserTable.class);
	}
	//***********************************************************************
	/**
	 * 添加用户
	 */
	public void addUser(TUserTable usertable) {
		String sql = "";
		Object [] obj = null;
			sql = "insert into t_usertable(USERID,USERNAME,USERPASSWORD,UNAME,SEX," +
					"BIRTHDAY,AGE,PHOTO,NPLA,NATION,MARRY,HEALTH,EDU ,BYSCHOOL,BYDATE," +
					"SUBJECT,ID,PHONE,MTEL,BP,EMAIL,ADDRESS,POSTCODE,COMDATE,TRANSDATE," +
					"BIO,UNOTE,ORACODE,YHTYPE,LEVELNUMBER,MSN,IS_PSEUDO_DELETED) " +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			obj = new Object[] { usertable.getUserid(),
					usertable.getUsername(), usertable.getUserpassword(),
					usertable.getUname(), usertable.getSex(),
					usertable.getBirthday(), usertable.getAge(),
					usertable.getPhoto(), usertable.getNpla(),
					usertable.getNation(), usertable.getMarry(),
					usertable.getHealth(), usertable.getEdu(),
					usertable.getByschool(), usertable.getBydate(),
					usertable.getSubject(), usertable.getId(),
					usertable.getPhone(), usertable.getMtel(),
					usertable.getBp(), usertable.getEmail(),
					usertable.getAddress(), usertable.getPostcode(),
					usertable.getComdate(), usertable.getTransdate(),
					usertable.getBio(), usertable.getUnote(),
					usertable.getOracode(), usertable.getYhtype(),
					usertable.getLevelnumber(), usertable.getMsn(),usertable.getIs_pseudo_deleted()};
		create(sql, obj);
	}
	//查询用户表中的id最大值
	public int getMaxIdInUser(){
		String sql = "select max(userid) from t_usertable";
		return find(sql);
	}
	//查询组织用户表中的id最大值
	public int getMaxIdInUserOrg(){
		String sql = "select max(id) from t_org_user";
		return find(sql);
	}
	//判断登录名是否存在
	public List isExistUserName(String username,String userId){
		String sql = "";
		if(StringUtils.isNotBlank(userId)){
			sql = "select userid from t_usertable where username = '"+username+"' and userid not in("+userId+")";
		}else{
			sql = "select userid from t_usertable where username = '"+username+"' and (is_pseudo_deleted is null or  is_pseudo_deleted !='1') ";
		}
		return find(sql, TUserTable.class);
	}
	/**
	 * 填充组织用户表
	 */
	public void addOrgUser(TOrgUser tou){
		String sql = "";
		Object [] obj = null;
			sql = "insert into t_org_user values(?,?,?)";
			obj = new Object[]{tou.getId(),tou.getOid(),tou.getUserid()};
		create(sql, obj);
	}
	//****************************************************************************
	/**
	 * 根据id查询本条用户的信息
	 * @param userId
	 * @return
	 */ 
	public TUserTable findUserAllById(String userId){
		String sql = "select * from t_usertable where userid = '"+userId +"'";
		List list = find(sql,TUserTable.class) ;
		TUserTable tUserTable = (TUserTable)list.get(0);
		return tUserTable;
	}
	//****************************************************************************
	//删除用户表中数据
	public void deleteUserById(String id){
		Object [] obj = {"1",id};
		//真删除
		String sql = "delete from t_usertable where userid="+id;
		delete(sql);
		
		//假删除  GUOWEIXIN此处是对用户 列表一个假删除代码
		//String sql = "update t_usertable set is_pseudo_deleted = ? where userid = ?";
		//update(sql,obj);
		
		String deleteSql="delete  from dbo.T_ORG_USER where USERID="+id;//此处是将 T_ORG_USER 表中的数据也删掉
		delete(deleteSql);
	}
	//删除用户角色表中数据
	public void deleteUserRole(String id){
		String sql = "delete from t_role_user where userid = "+id;
		delete(sql);
	}
	//删除用户组织表中数据
	public void deleteUserOrg(String id){
		String sql = "delete from t_org_user where userid="+id;
		delete(sql);
	}
	//*****************************************************************************
	/**
	 * 修改用户
	 */
	public void updateUser(TUserTable usertable){
		String sql = "update t_usertable set username = ?,userpassword=?,uname=?,sex=?,byschool=?," +
				"subject=?, phone=?, email=?,address=?,levelnumber=?  where userid = ?" ;
		Object [] obj = {usertable.getUsername(),usertable.getUserpassword(),usertable.getUname(),
				usertable.getSex(),usertable.getByschool(),usertable.getSubject(),usertable.getPhone(),usertable.getEmail(),
				usertable.getAddress(),usertable.getLevelnumber(),	usertable.getUserid()};
		update(sql,obj);
		
	}

	/**
	 * 保存修改的用户的组织机构
	 */
	public void updateUserOragination(String  userId,String oid){
		String sql = "update T_ORG_USER set OID = ? where userid = ?" ;
		Object [] obj = {oid,userId};
		update(sql,obj);
	}
	//******************************************************************************
	public List getRoleIDByUserId(String userid) {//根据用户id获取用户角色
		String sql = "select t.rid as id from t_role_user t where t.userid = "+userid;
		return find(sql, Role.class);
	}

	public List getRolesIDByRole(Long roleId) {//根据角色获取子角色(可能包含重复值)
		int result = judge();
		String sql = "";
		if(result == 1){
			sql = "select t.downroleid as id from t_role_role t start with t.uproleid ="+" "+roleId+" "+"connect by prior t.downroleid = t.uproleid";
		}else if(result == 2){
			sql = "with cte_child(id,uproleid,downroleid) as("+
					 "select id,uproleid,downroleid from t_role_role where uproleid ="+" "+roleId+" "+
					 "union all select a.id,a.uproleid,a.downroleid from t_role_role a inner join"+" "+
					 "cte_child b on (a.uproleid=b.downroleid))"+
					 "select downroleid as id from cte_child";
		}
		return find(sql, Role.class);
	}

	public List getAuthsID(String qstring) {//根据角色id拼接成的字符串获取权限id(权限id暂未递归)
		String sql = "select t.resc_id as ajaxTreeId from t_role_resc t where t.role_id in("+qstring+")";
		return find(sql, Authority.class);
	}
	
	//**************************************************************************************
	/**
	 * 查询某部门所有直属用户
	 * @param oid
	 * @return
	 */
	public List getNeedUser(String oid){
		String  sql = "select (CAST(t.userid as int)) as id,t.uname as text ,o.oid as parentID from t_usertable t, " +
				"t_org_user o where t.userid = o.userid and o.oid = "+oid;
		return find(sql, TreeDataBasic.class);
	}
	//**************************************************************************************
	/**
	 * 修改用户级别(用户id和级别id)
	 */
	public void updateUser_UserLevel(String userid,String id){
		if(id == ""){
			id = -1+"";
		}
		String sql= "update t_usertable set levelnumber = "+id+" where userid = "+userid;
		update(sql);
	}
	//********************************************************************
	/**
	 * 管理用户角色(给用户添加新角色)
	 */
	public void add_User_Role(RoleUser roleuser){
		String sql = "insert into t_role_user values(?,?,?)";
		Object []obj = new Object[]{roleuser.getId(),roleuser.getRid(),roleuser.getUserid()};
		create(sql, obj);
	}
	
	/**
	 * 查询角色用户表中的最大值
	 */
	public int findMaxIDFromRole_User(){
		String sql = "select max(id) from t_role_user";
		return find(sql);
	}
	
	/**
	 * 查询用户下某角色是否存在(避免重复插入)
	 */
	public List isExistRoleInUser(String userid, String rid){
		String sql = "select rid from t_role_user where userid = "+userid+" and rid = "+rid;
		return find(sql, RoleUser.class);
	}
	
	/**
	 * 管理用户角色(删除用户角色)
	 */
	public void deletRoleUnderUser(String userid, String roleid){
		String sql = "delete from t_role_user where rid= "+roleid+" and userid="+userid;
		delete(sql);
	}
	//*****************************************************************************
	/**
	 * 根据用户id串获取用户集合
	 */
	public List getOrgLeadList(String uids){
		String sql = "select * from user_union_view where userid in("+uids+")";
		return find(sql, UserBasic.class);
	}
	
	/**
	 * 修改部门主管
	 */
	public void updateOrgLead(String oid,String userids){
		String sql = "update t_organization set lead ='"+userids +"' where oid = "+oid;
		update(sql);
	}

	/*
	 * 根据部门复制部门id选取部门间的关系
	 */
	public List getOrgOrg(String[] ooid) {
		String sql = "";
		if(ooid.length>0){
			sql = sql+ "SELECT ID ,UPID ,DOWNID FROM T_ORG_ORG where 1=1 and UPID in  (";
			for(int i=0;i<ooid.length;i++){
				sql = sql+"'"+ooid[i]+"',";
			}
			sql = sql.substring(0, sql.length()-1) +")or DOWNID in (";
			for(int i=0;i<ooid.length;i++){
				sql = sql+"'"+ooid[i]+"',";
			}
			sql = sql.substring(0, sql.length()-1) +")";
		}	
		return find(sql, TOrgOrg.class);
	}
	/*** GUOWEIXIN
	 * 读取资源文件：configSQL.properties
	 */
	/**
	 * 系统框架业务操作接口
	 */
	/**
	 * 初始化数据源
	 */
	private DataSource initDataSource;
	
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
	public DataSource getInitDataSource() {
		return initDataSource;
	}
	public void setInitDataSource(DataSource initDataSource) {
		this.initDataSource = initDataSource;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String checkUserIsUse(String userId) {
		// TODO Auto-generated method stub
		String result="false";
		String sql="SELECT count(ID) FROM T_ROLE_USER where USERID ='"+userId+"'";
		
		@SuppressWarnings("unused")
		int count =find(sql);
		SynchroFLOWBean synchroFLOWBean = new SynchroFLOWBean();
		try {
			
			List workItemList = synchroFLOWBean.getWorkitemListByUserId(userId);
			if (workItemList!=null && (workItemList.size()!=0)) {
				result="true";
			}else if (count>0) {
				result="true";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
