package com.zxt.compplatform.formengine.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.zxt.compplatform.formengine.dao.ComponentsTreeDao;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.formengine.entity.view.TreeOrgData;

/**
 * 控件树持久化操作
 * 
 * @author 007
 */
public class ComponentsTreeDaoImpl implements ComponentsTreeDao {

	private static final Logger log = Logger
			.getLogger(ComponentsTreeDaoImpl.class);
	/**
	 * spring jdbc操作模版
	 */
	private JdbcTemplate jdbcTemplate;
	/**
	 * 初始化数据源
	 */
	private DataSource initDataSource;

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.dao.ComponentsTreeDao#treeData(java.lang.String, javax.sql.DataSource)
	 */
	@SuppressWarnings("unchecked")
	public List treeData(String sql, DataSource dataSource) {
		// TODO Auto-generated method stub
		jdbcTemplate.setDataSource(dataSource);
		List list = new ArrayList();
		try {
			SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql);
			if (temSet != null) {
				int next = 1;
				TreeData treeData = null;
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
					// if (temSet.getString(next)!=null) {
					// treeData.setOname(temSet.getString(next));
					// }
					list.add(treeData);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcTemplate.setDataSource(initDataSource);
		}

		return list;
	}

	/**
	 * 查询人员组织机构树
	 */
	public List treeOrgData(String sql, DataSource dataSource) {
		// TODO Auto-generated method stub
		/**
		 * 设置为平台数据源
		 */
		jdbcTemplate.setDataSource(initDataSource);
		List list = new ArrayList();
		try {
			SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql);
			if (temSet != null) {
				int next = 1;
				TreeOrgData treeOrgData = null;
				while (temSet.next()) {
					next = 1;
					treeOrgData = new TreeOrgData();
					if (temSet.getString(next) != null) {
						treeOrgData.setId(temSet.getString(next));
					}
					next++;
					if (temSet.getString(next) != null) {
						treeOrgData.setText(temSet.getString(next));
					}
					next++;
					if (temSet.getString(next) != null) {
						treeOrgData.setParentID(temSet.getString(next));
					}
					next++;
					if (temSet.getString(next) != null) {
						treeOrgData.setIsuser(temSet.getString(next));
					}
					list.add(treeOrgData);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcTemplate.setDataSource(initDataSource);

		}

		return list;
	}

	public DataSource getInitDataSource() {
		return initDataSource;
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

}
