package com.zxt.compplatform.authority.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.zxt.compplatform.authority.dao.AuthorityDao;
import com.zxt.compplatform.authority.entity.Authority;
import com.zxt.compplatform.form.entity.Form;
import com.zxt.compplatform.formengine.entity.view.TreeAttributes;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.framework.common.dao.DAOSupport;
import com.zxt.framework.jdbc.ZXTJDBCTemplate;

/**
 *     权限
 * 数据库持久化操作实现
 * @author 007
 */
public class AuthorityDaoImpl extends DAOSupport implements AuthorityDao {
	/**
	 * 初始化数据源
	 */
	private DataSource initDataSource;
	/**
	 * 使用spring的jdbc
	 */
	private JdbcTemplate jdbcTemplate;

	/**
	 * 通过角色id选出响应模板
	 */
	public List findMenuByRoleId(String roleid) {
		String sql = " select t0.RESID as id, t0.RESNAME as text ,t0.PARENTID as parent_i_d from ENG_RESOURCES t0 where t0.RESID in (select po.RESC_ID from T_ROLE_RESC   po where po.ROLE_ID='"
				+ roleid + "')";
		ZXTJDBCTemplate zxtJDBCTemplate = new ZXTJDBCTemplate(initDataSource);
		return zxtJDBCTemplate.find(sql, TreeData.class);
	}

	/**
	 * 从sql查询权限列表
	 * @see com.zxt.compplatform.authority.dao.AuthorityDao#findAuthority(java.lang.String)
	 */
	public List findAuthority(String sql) {
		ZXTJDBCTemplate zxtJDBCTemplate = new ZXTJDBCTemplate(initDataSource);
		return zxtJDBCTemplate.find(sql, Authority.class);
	}

	/**
	 * 根据sql查询所有的表单
	 * @see com.zxt.compplatform.authority.dao.AuthorityDao#findForm(java.lang.String)
	 */
	public List findForm(String sql) {
		ZXTJDBCTemplate zxtJDBCTemplate = new ZXTJDBCTemplate(initDataSource);
		return zxtJDBCTemplate.find(sql, Form.class);
	}

	/**
	 * 将集合中的内容插入到数据库
	 * @see com.zxt.compplatform.authority.dao.AuthorityDao#insertAll(java.util.List)
	 */
	public void insertAll(List sqlList) {
		ZXTJDBCTemplate zxtJDBCTemplate = new ZXTJDBCTemplate(initDataSource);
		zxtJDBCTemplate.createAll(sqlList);
	}

	public void setInitDataSource(DataSource initDataSource) {
		this.initDataSource = initDataSource;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.authority.dao.AuthorityDao#findAllResource()
	 */
	@Override
	public List<TreeData> findAllResource() {
		// TODO Auto-generated method stub		
		String sql = "select RESID,RESNAME,PARENTID,RESURI,IS_WORKFLOW from ENG_RESOURCES  order by RESSORT";
		List<TreeData> list = new ArrayList<TreeData>();
		SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql);
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
	/***
	 * 读取资源文件：configSQL.properties
	 * 系统框架业务操作接口 GUOWEIXIN
	 */
	private SystemFrameService systemFrameService;
	public SystemFrameService getSystemFrameService() {
		return systemFrameService;
	}
	public void setSystemFrameService(SystemFrameService systemFrameService) {
		this.systemFrameService = systemFrameService;
	}
}
