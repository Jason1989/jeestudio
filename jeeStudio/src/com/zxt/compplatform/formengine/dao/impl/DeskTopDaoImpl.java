package com.zxt.compplatform.formengine.dao.impl;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.zxt.compplatform.formengine.dao.DeskTopDao;
import com.zxt.compplatform.formengine.entity.view.TreeAttributes;
import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.framework.dictionary.service.IDataDictionaryService;

/**
 * 桌面版数据持久化操作
 * 
 * @author 007
 */
public class DeskTopDaoImpl implements DeskTopDao {
	/**
	 * spring jdbc数据库操作模版
	 */
	private JdbcTemplate jdbcTemplate;
	/**
	 * 数据字典操作业务操作接口
	 */
	private IDataDictionaryService iDataDictionaryService;
	/**
	 * 基础数据源
	 */
	private DataSource initDataSource;
	/**
	 * 系统框架操作接口
	 */
	private SystemFrameService systemFrameService;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public IDataDictionaryService getIDataDictionaryService() {
		return iDataDictionaryService;
	}

	public void setIDataDictionaryService(
			IDataDictionaryService dataDictionaryService) {
		iDataDictionaryService = dataDictionaryService;
	}

	public DataSource getInitDataSource() {
		return initDataSource;
	}

	public void setInitDataSource(DataSource initDataSource) {
		this.initDataSource = initDataSource;
	}

	public SystemFrameService getSystemFrameService() {
		return systemFrameService;
	}

	public void setSystemFrameService(SystemFrameService systemFrameService) {
		this.systemFrameService = systemFrameService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.dao.DeskTopDao#findTreeData(java.lang.String)
	 */
	public TreeData findTreeData(String menuID) {
		// TODO Auto-generated method stub
		TreeData treeData = new TreeData();
		TreeAttributes treeAttributes = new TreeAttributes();
		/**
		 * 查询菜单配置
		 */
		String sql = " SELECT RESID,RESNAME,IS_WORKFLOW "
				+ " FROM ENG_RESOURCES " + " where RESID=? ";
		/**
		 * 设置基础数据源
		 */
		jdbcTemplate.setDataSource(initDataSource);
		SqlRowSet temRowSet = jdbcTemplate.queryForRowSet(sql,
				new Object[] { menuID });
		/**
		 * 封装菜单字段结果集
		 */
		if (temRowSet != null) {
			if (temRowSet.next()) {
				if (temRowSet.getString("resid") != null) {
					treeData.setId(temRowSet.getString("resid"));
				}
			}
		}

		return null;
	}

}
