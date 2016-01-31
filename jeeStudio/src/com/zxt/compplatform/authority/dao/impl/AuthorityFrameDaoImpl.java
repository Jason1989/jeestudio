package com.zxt.compplatform.authority.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zxt.compplatform.authority.dao.AuthorityFrameDao;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.compplatform.formengine.util.StrTools;

public class AuthorityFrameDaoImpl implements AuthorityFrameDao {
	private JdbcTemplate jdbcTemplate;
	private DataSource initDataSource;

	private static final Logger log = Logger.getLogger(AuthorityFrameDaoImpl.class);

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.dao.AuthorityFrameDao#initAuthorityFrameByAccount(java.lang.String)
	 */
	public List initAuthorityFrameByAccount(String account) {
		// TODO Auto-generated method stub
		List list=null;
		String sql=null;
		// configSQL.properties 文件
		Map map=systemFrameService.load_serviceConfigSQL();
		String falgChoose=(String)map.get("is_use_reference_dataSource");
		String key="AuthorityFrameDaoImpl-initAuthorityFrameByAccount";
		 if("true".equals(falgChoose) && null!=(String)map.get(key)){
			 try{
					sql=(String)map.get(key);
					sql+=" where LOGINNAME='"+account+"'";
					ResultSet rs=null;
					PreparedStatement pst=null;
					Connection con=null;
					try{
						con=StrTools.configPropertiesUtil(map, systemFrameService);
						pst=con.prepareStatement(sql);
						rs=pst.executeQuery();
						list=new ArrayList();
						while(rs.next()){
							list.add(rs.getString("rname"));
						}
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						con.close();
					}
					return list;
			 }catch(Exception e){
				 e.printStackTrace();
			 }
			 finally{
				 jdbcTemplate.setDataSource(initDataSource);
			 }
		 }else{
			 jdbcTemplate.setDataSource(initDataSource);
			 sql=" SELECT T_ROLE.RNAME " +
					" FROM T_USERTABLE " +
					" left join T_ROLE_USER " +
					" on T_ROLE_USER.USERID=T_USERTABLE.USERID " +
					" left join T_ROLE " +
					" on T_ROLE.RID=T_ROLE_USER.RID " +
					" where USERNAME=?  ";
		 }
		return jdbcTemplate.queryForList(sql,new Object[]{account});
		
	   }
	
	/**
	 * 根据角色名查询角色ID 黄姣
	 * @param rname
	 * @return
	 */
	public List initRolesFrameByAccount(String rname) {
		// TODO Auto-generated method stub
		//GUOWEIXIN configSQL.properties 文件
		Map map=systemFrameService.load_serviceConfigSQL();
		String falgChoose=(String)map.get("is_use_reference_dataSource");
		 if("true".equals(falgChoose)){
			List list=null;
			 String sqlRemote="";
			 try{
				 	Map poolsMap = systemFrameService.load_connectPools("true");
				 	String mapStr=(String)map.get("is_use_reference_dataSourceID");
					String dataSourceID=map.get("is_use_reference_dataSourceID").toString();
					ComboPooledDataSource   connectPool=(ComboPooledDataSource) poolsMap.get(dataSourceID);
				 	
				 	//jdbcTemplate.setDataSource( (ComboPooledDataSource) poolsMap.get(map.get("is_use_reference_dataSourceID")));
					String key="AuthorityFrameDaoImpl-initRolesFrameByAccount";
					sqlRemote=(String)map.get(key);
					sqlRemote+=" WHERE ROLENAME='"+rname+"'";
					ResultSet rs=null;
					PreparedStatement pst=null;
					Connection con=null;
					try{
						con=connectPool.getConnection();
						pst=con.prepareStatement(sqlRemote);
						rs=pst.executeQuery();
						list=new ArrayList();
						while(rs.next()){
							list.add(rs.getString("rid"));
						}
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						con.close();
					}
					return list;
			 }catch(Exception e){
				 e.printStackTrace();
			 }
			 finally{
				 jdbcTemplate.setDataSource(initDataSource);
			 }
			 return jdbcTemplate.queryForList(sqlRemote,new Object[]{rname});
		 }else{
			jdbcTemplate.setDataSource(initDataSource);
			String sql=" SELECT T_ROLE.RNAME,T_ROLE.RID " +
					" FROM T_ROLE " +
					" where T_ROLE.RNAME=?  ";
			return jdbcTemplate.queryForList(sql,new Object[]{rname});
		 }
		
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

	public void setInitDataSource(DataSource initDataSource) {
		this.initDataSource = initDataSource;
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
}
