package com.zxt.compplatform.authority.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zxt.compplatform.authority.dao.RARelationShipDao;
import com.zxt.compplatform.authority.entity.RARelationShip;
import com.zxt.compplatform.authority.service.RARelationShipService;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.compplatform.formengine.util.StrTools;
import com.zxt.framework.common.util.SQLFomatter;
import com.zxt.framework.jdbc.ZXTJDBCTemplate;

public class RARelationShipServiceImpl  implements RARelationShipService{
    /**
     * 注入角色资源关系数据持久化类
     */
    private RARelationShipDao relationShipDao;

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.RARelationShipService#deleteAll(java.util.List)
	 */
	public void deleteAll(List raList) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.RARelationShipService#findAuths(java.lang.String)
	 */
	public List findAuths(String rString){
		return relationShipDao.listAuthoritys(rString);
	}
    /* (non-Javadoc)
     * @see com.zxt.compplatform.authority.service.RARelationShipService#findAll(java.lang.String)
     */
    public List findAll(String rString){
    	return relationShipDao.listrights(rString);
    }
	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.RARelationShipService#findAll()
	 */
	public List findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.RARelationShipService#findById(java.lang.String)
	 */
	public RARelationShip findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.RARelationShipService#findTotalRows()
	 */
	public int findTotalRows() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	/* (non-Javadoc)
	 * 1 根据roleId查询出现有权限 rec_id_list 列表
	 * 2 遍历rec_id_list，根据当前某个子系统修改后的资源权限列表menuIds，找出需要删除和需要添加的rec_id，拼写sql list
	 * 3 批量执行sql list
	 * @see com.zxt.compplatform.authority.service.RARelationShipService#roleMenuConfigInsert(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void roleMenuConfigInsert(String roleId,String menuIds,String systemRescId){ 
		String[] menuIdArr = menuIds.split(",");
		String sql=null;
		if(StringUtils.isNotEmpty(menuIds)){
			
		}
		int flag= zxtJDBCTemplate.judge();//验证是哪个数据库
		List list = new ArrayList();
		if(flag==1){ //oracle 给 T_ROLE_RESC多加个：SYSTEM_NAME字段
			 String deleteSql=" delete from T_ROLE_RESC where ROLE_ID="+roleId;			
			
				 deleteSql+=" and SYSTEM_NAME="+"'"+systemRescId+"'";		 
			 list.add(deleteSql);
			 for(int i=0;i<menuIdArr.length;i++){
					if(!menuIdArr[i].equals("")){
						String delSql=" delete FROM T_ROLE_RESC  where ROLE_ID="+roleId+"  and  RESC_ID= '"+ menuIdArr[i]+"'";
						list.add(delSql);
						sql = " insert into T_ROLE_RESC(ROLE_ID,RESC_ID,SYSTEM_NAME) values (" + roleId + ",'" + menuIdArr[i] + "','"+systemRescId+"') ";						
						list.add(sql);
					}				
				}
				relationShipDao.insertAll(list); 
			 
		}else if(flag==2){
			//递归出某个系统下所有的资源
			String recursionRescIds = " with SUBSYSTEM_RESID_LIST as( select RESID from ENG_RESOURCES Where RESID='" + systemRescId 
			+ "' union all select ENG_RESOURCES.RESID from SUBSYSTEM_RESID_LIST inner join ENG_RESOURCES on SUBSYSTEM_RESID_LIST.RESID=ENG_RESOURCES.PARENTID) ";		
			
			//删除某些资源
			String deleteSql = recursionRescIds + " delete from T_ROLE_RESC where RESC_ID in ( select RESID from SUBSYSTEM_RESID_LIST ) and RESC_ID not in ("
				+ ("".equals(menuIds.trim())?"'-1'":SQLFomatter.changeToSqlType(menuIds)) + ") and ROLE_ID = " + roleId +"  ";
			list.add(deleteSql);
			
			
			// configSQL.properties 文件
			Map map=systemFrameService.load_serviceConfigSQL();
			String falgChoose=(String)map.get("is_use_reference_dataSource");
			String key="RARelationShipServiceImpl-roleMenuConfigInsert";
			 if("true".equals(falgChoose) && null!=(String)map.get(key)){
				//删除某些资源
				 List delList=new ArrayList();
				try{
				 	
					sql=(String)map.get(key);
					List listPro = new ArrayList();
					 String deleteSqlBy=" delete from SGS_DIGICITY_MDS.MDS_DS_ROLEPRIREL where ROLEID="+roleId;
						 deleteSqlBy+=" and PRISTATE="+"'"+systemRescId+"'"; 
					 listPro.add(deleteSqlBy);
					StringBuffer strSql=new StringBuffer(deleteSqlBy+"   ");
					 for(int i=0;i<menuIdArr.length;i++){
						if(!menuIdArr[i].equals("")){
							String resultSql=sql.replaceFirst("replaceROLEID", roleId);
							resultSql=resultSql.replaceFirst("replaceRESCID",menuIdArr[i]);
							resultSql=resultSql.replaceFirst("replaceROLE_ID",roleId);
							resultSql=resultSql.replaceFirst("replaceRESC_ID", menuIdArr[i]);	
							resultSql=resultSql.replaceFirst("PRISTATEVALUE",systemRescId);//PRISTATEVALUE		
							listPro.add(resultSql);
							strSql.append(resultSql+"  ");
						}				
					}
					Connection con=null;
					PreparedStatement pst=null;
					try{
						con=StrTools.configPropertiesUtil(map, systemFrameService);
						pst=con.prepareStatement(strSql.toString());
						int count=pst.executeUpdate();
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(con!=null )con.close();
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					relationShipDao.insertAll(delList);
				}
					
			 }else{

					for(int i=0;i<menuIdArr.length;i++){
						if(!menuIdArr[i].equals("")){
							sql = "if (select RESC_ID from T_ROLE_RESC where ROLE_ID = " + roleId +" and RESC_ID = '" + menuIdArr[i] + "') is null begin insert into T_ROLE_RESC(ROLE_ID,RESC_ID) values (" + roleId + ",'" + menuIdArr[i] + "') end ";						
							list.add(sql);
						}				
					}
					relationShipDao.insertAll(list);
			 }
		}
		
		
	}
	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.RARelationShipService#insert(com.zxt.compplatform.authority.entity.RARelationShip)
	 */
	public void insert(RARelationShip raRelationShip) {
		String sql = " insert into T_ROLE_RESC values (" + raRelationShip.getRoleId() + ",'" + raRelationShip.getRescId() + "') ";
		relationShipDao.insert(sql);
	}
	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.RARelationShipService#delete(com.zxt.compplatform.authority.entity.RARelationShip)
	 */
	public void delete(RARelationShip raRelationShip) {
		String sql = " delete from T_ROLE_RESC where role_id = " + raRelationShip.getRoleId() + " and resc_id = '" + raRelationShip.getRescId() + "' ";
		relationShipDao.deleteRoleMenu(sql);
		
	}
	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.RARelationShipService#deleteByRoleId(java.lang.String)
	 */
	public void deleteByRoleId(String roleId){
		String sql = " delete from T_ROLE_RESC where role_id = " + roleId ;
		relationShipDao.deleteRoleMenu(sql);
	}
	/**
	 * @return
	 */
	public RARelationShipDao getRelationShipDao() {
		return relationShipDao;
	}

	public void setRelationShipDao(RARelationShipDao relationShipDao) {
		this.relationShipDao = relationShipDao;
	}
	
	public List getMenusByRoleNames(String roles){
		return relationShipDao.listrights(roles);
	} 
	
	public List getMenusByRoleNames(String roles,String systemId){
		return relationShipDao.listRights(roles,systemId);
	}
	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.RARelationShipService#findResourceIDsByRoleID(java.lang.String)
	 */
	@Override
	public String findResourceIDsByRoleID(String roleID) {
		return relationShipDao.findResourceIDsByRoleID(roleID);
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
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	private ZXTJDBCTemplate zxtJDBCTemplate;

	public ZXTJDBCTemplate getZxtJDBCTemplate() {
		return zxtJDBCTemplate;
	}
	public void setZxtJDBCTemplate(ZXTJDBCTemplate zxtJDBCTemplate) {
		this.zxtJDBCTemplate = zxtJDBCTemplate;
	}
}
