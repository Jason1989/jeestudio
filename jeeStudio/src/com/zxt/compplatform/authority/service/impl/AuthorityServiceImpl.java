package com.zxt.compplatform.authority.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;
import com.zxt.compplatform.authority.dao.AuthorityDao;
import com.zxt.compplatform.authority.entity.Authority;
import com.zxt.compplatform.authority.service.AuthorityService;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.formengine.util.TreeUtil;

/**
 * 权限业务操作层接口实现
 * @author 007
 */
public class AuthorityServiceImpl implements AuthorityService {
	/**
	 * 注入权限操作dao
	 */
	private AuthorityDao authorityDao;

	public AuthorityDao getAuthorityDao() {
		return authorityDao;
	}

	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.AuthorityService#findAll()
	 */
	public List findAll() {
		String paramString = " from Authority a order by a.ajaxTreeId ";
		return authorityDao.find(paramString);
	}

	/*
	 * 通过角色id选出响应模板
	 */
	public List findMenuByRoleId(String roleid) {
		List list = authorityDao.findMenuByRoleId(roleid);
		return list;

	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.AuthorityService#findById(java.lang.String)
	 */
	public Authority findById(String id) {
		// String paramString = " from Authority a where a.ajaxTreeId = '" + id
		// + "' ";
		// List list = authorityDao.find(paramString);
		StringBuffer sql = new StringBuffer();
		sql
				.append(" select t0.RESID as ajax_tree_id, t0.RESNAME as text ,t0.PARENTID as parent_id ,t0.RESURI as url,t0.MENULEVEL as menu_level,t0.RESSORT as menu_sort from ENG_RESOURCES t0 ");
		sql.append(" where t0.RESID = '").append(id).append("' ");
		List list = authorityDao.findAuthority(sql.toString());
		if (list != null && list.size() > 0) {
			return (Authority) list.get(0);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.AuthorityService#findAllByPid(java.lang.String)
	 */
	public List findAllByPid(String pid) {
		StringBuffer sql = new StringBuffer();
		sql
				.append(" select t0.RESID as ajax_tree_id, t0.RESNAME as text ,t0.PARENTID as parent_id ,t0.RESURI as url,t0.MENULEVEL as menu_level,t0.RESSORT as menu_sort from ENG_RESOURCES t0 ");
		sql.append(" where t0.PARENTID = '").append(pid).append("' ");
		return authorityDao.findAuthority(sql.toString());
	}
	/**
	 * 根据系统的id，查询出所有的子节点
	 * @param systemId 系统的id活着是某个节点的id
	 * @return
	 */
	public List findAllNodesBySystemId(String systemId){
		StringBuffer sql = new StringBuffer();
		sql.append(" with systemMenu ( RESID,RESNAME,PARENTID,RESTYPE,RESURI,RESKEY ");
		sql.append(" ,RESSTYLE,MENULEVEL,RESSORT,APP_ID,PARENT_APP_ID,IS_MENU,IS_WORKFLOWCOMPARID");
		sql.append("  ,IS_WORKFLOWCOMPARARRAY,IS_WORKFLOWCOMPAR,IS_WORKFLOW,workflow_fiter,IMGSRC");
		sql.append(" ,row_num,DEFAULT_SKIN,SELECT_ENABLE) as (");
		sql.append(" select  RESID,RESNAME,PARENTID,RESTYPE,RESURI,RESKEY");
		sql.append("  ,RESSTYLE,MENULEVEL,RESSORT,APP_ID,PARENT_APP_ID,IS_MENU,IS_WORKFLOWCOMPARID");
		sql.append(" ,IS_WORKFLOWCOMPARARRAY,IS_WORKFLOWCOMPAR,IS_WORKFLOW,workflow_fiter,IMGSRC");
		sql.append(" ,row_num,DEFAULT_SKIN,SELECT_ENABLE");
		sql.append("  FROM dbo.ENG_RESOURCES where RESID='").append(systemId).append("'");
		
		sql.append("  UNION ALL");
		sql.append(" select  res.RESID,res.RESNAME,res.PARENTID,res.RESTYPE,res.RESURI");
		sql.append(" ,res.RESKEY,res.RESSTYLE,res.MENULEVEL,res.RESSORT,res.APP_ID,res.PARENT_APP_ID");
		sql.append(" ,res.IS_MENU,res.IS_WORKFLOWCOMPARID,res.IS_WORKFLOWCOMPARARRAY");
		sql.append(" ,res.IS_WORKFLOWCOMPAR,res.IS_WORKFLOW,res.workflow_fiter");
		sql.append(" ,res.IMGSRC,res.row_num,res.DEFAULT_SKIN,res.SELECT_ENABLE");
		sql.append(" FROM dbo.ENG_RESOURCES res,systemMenu menus where res.PARENTID=menus.RESID ) ");
   
		sql.append(" select * from systemMenu ");
		return authorityDao.findAuthority(sql.toString());
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.AuthorityService#findTotalRows()
	 */
	public int findTotalRows() {
		String queryString = " select count(ajaxTreeId) from Authority a ";
		return authorityDao.findTotalRows(queryString);
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.AuthorityService#findAllModule()
	 */
	public List findAllModule() {
		StringBuffer sql = new StringBuffer();
		sql
				.append(" select t0.RESID as ajax_tree_id, t0.RESNAME as text ,t0.PARENTID as parent_id ,t0.RESURI as url,t0.MENULEVEL as menu_level,t0.RESSORT as menu_sort from ENG_RESOURCES t0 ");
		sql.append(" where t0.MENULEVEL = '1' order by t0.RESSORT ");
		return authorityDao.findAuthority(sql.toString());
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.AuthorityService#findAllByPIdRoleId(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List findAllByPIdRoleId(String pid, String roleId, String flag) {
		StringBuffer sql = new StringBuffer();
		sql
				.append(" select t0.RESID as ajax_tree_id, t0.RESNAME as text ,t0.PARENTID as parent_id ,t0.RESURI as url,t0.MENULEVEL as menu_level,t0.RESSORT as menu_sort from ENG_RESOURCES t0 ");
		sql.append(" where t0.RESID ");
		if (flag.equals("0"))
			sql.append(" not ");
		sql
				.append(
						" in (select distinct rr.resc_id from  t_role_resc rr where rr.role_id = '")
				.append(roleId).append("' ) ");
		sql.append(" and t0.PARENTID = '").append(pid).append(
				"' order by t0.RESSORT ");

		// String paramString = " from Authority a where a.parent_id = '" + pid
		// + "' and a.ajax_tree_id not in ( select rr.resc_id from
		// RARelationShip rr where rr.role_id = '" + roleId + "' )";
		return authorityDao.findAuthority(sql.toString());
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.AuthorityService#getAuthorizedMenuByModuleIdRoleId(java.lang.String, java.lang.String)
	 */
	public List getAuthorizedMenuByModuleIdRoleId(String moduleId, String roleId) {
		StringBuffer sql = new StringBuffer();
		sql
				.append(" select t0.RESID as ajax_tree_id, t0.RESNAME as text ,t0.PARENTID as parent_id ,t0.RESURI as url,t0.MENULEVEL as menu_level,t0.RESSORT as menu_sort ");
		sql.append(" from  T_ROLE_RESC t1  ");
		sql.append(" left join   ENG_RESOURCES  t0  ");
		sql.append(" on  t1.RESC_ID=t0.RESID ");
		sql.append(" where t1.ROLE_ID=   " + roleId);
		sql.append(" order by t0.RESSORT ");
		String treeSql = sql.toString();
		return authorityDao.findAuthority(treeSql);
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.AuthorityService#findFormByTypeId(java.lang.String)
	 */
	public List findFormByTypeId(String formTypeId) {
		StringBuffer sql = new StringBuffer();
		sql
				.append(" select t.FO_ID as id,t.FO_FTYPE as type,t.FO_NAME as form_name,t.FO_DESCRIPTION as description,t.FO_STATE as state");
		sql.append(" from eng_form_form t where t.FO_FTYPE = '").append(
				formTypeId).append("'");
		sql.append(" order by t.FO_SORTINDEX ");
		return authorityDao.findForm(sql.toString());
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.AuthorityService#findAuthorizedFormByTypeIdRoleId(java.lang.String, java.lang.String)
	 */
	public List findAuthorizedFormByTypeIdRoleId(String formTypeId,
			String roleId) {
		StringBuffer sql = new StringBuffer();
		sql
				.append(" select t.FO_ID as id,t.FO_FTYPE as type,t.FO_NAME as form_name,t.FO_DESCRIPTION as description,t.FO_STATE as state ");
		sql.append(" from eng_form_form t ,t_role_url t1 ");
		sql.append(" where t.FO_ID = t1.url_id and t1.role_id = '").append(
				roleId).append("' and t.FO_FTYPE = '").append(formTypeId)
				.append("' ");
		sql.append(" order by t.FO_SORTINDEX ");
		return authorityDao.findForm(sql.toString());
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.AuthorityService#roleFormConfigInsert(java.lang.String, java.lang.String)
	 */
	public void roleFormConfigInsert(String roleId, String formIds) {
		List list = new ArrayList();
		String deleteSql = " delete from t_role_url where role_id = '" + roleId
				+ "' ";
		list.add(deleteSql);
		String[] formIdArr = formIds.split(",");
		for (int i = 0; i < formIdArr.length; i++) {
			if (!formIdArr[i].equals("")) {
				String sql = " insert into t_role_url values ('" + roleId
						+ "','" + formIdArr[i] + "') ";
				list.add(sql);
			}
		}
		authorityDao.insertAll(list);
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.AuthorityService#dealWithResource(java.lang.String, java.util.List, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String dealWithResource(String syetemID, List<TreeData> list,
			String checkTreeIDs) {
		// TODO Auto-generated method stub
		String jsonString="[]";
		if (list != null && syetemID != null) {
			String checkTreeIDArray[] = checkTreeIDs.split(",");
			Map<String, String> checkTreeIDMap = new HashMap<String, String>();
			TreeData treeData=null;
			
			/**
			 * 以选中的权限集封转成map
			 */
			for (int i = 0; i < checkTreeIDArray.length; i++) {
				checkTreeIDMap.put(checkTreeIDArray[i], "checked");
			}
			/**
			 * 封装已选中的节点
			 */
			for (int i = 0; i < list.size(); i++) {
				treeData=list.get(i);
				if (treeData!=null&&checkTreeIDMap.get(treeData.getId())!=null) {
					treeData.setChecked(true);
				}
			}
			list=TreeUtil.treeAlgorithmForTreeData(list, syetemID);
			/**
			 * 转换json
			 */
			try {
				jsonString=JSONUtil.serialize(list);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	
		return jsonString;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.service.AuthorityService#findAllResource()
	 */
	@Override
	public List<TreeData> findAllResource() {
		// TODO Auto-generated method stub
		return authorityDao.findAllResource();
	}

}
