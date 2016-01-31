package com.zxt.compplatform.formengine.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.zxt.compplatform.formengine.dao.WorkItemInfoDao;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.compplatform.workflow.entity.TaskFormNodeEntity;

/**
 * 工作项信息持久化操作
 * 
 * @author 007
 */
public class WorkItemInfoDaoImpl implements WorkItemInfoDao {
	private JdbcTemplate jdbcTemplate;
	/**
	 * 基础数据源
	 */
	private DataSource initDataSource;
	/**
	 * 系统框架业务操作接口
	 */
	private SystemFrameService systemFrameService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.dao.WorkItemInfoDao#findWorkFormList(java.lang.String,
	 *      java.lang.String)
	 */
	public List findWorkFormList(String proInsId, String taskId) {
		// TODO Auto-generated method stub
		List formIdList = new ArrayList();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = initDataSource.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("select FORM_ID ");
			sql.append("from ENG_FORM_TASK ");
			sql.append("where PRO_INS_ID=? and TASK_ID=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, proInsId);
			pstm.setString(2, taskId);
			rs = pstm.executeQuery();
			while (rs.next()) {
				TaskFormNodeEntity tfne = new TaskFormNodeEntity();
				tfne.setTaskNodeID(taskId);
				tfne.setFormID(rs.getString("FORM_ID"));
				formIdList.add(tfne);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formIdList;
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

	public SystemFrameService getSystemFrameService() {
		return systemFrameService;
	}

	public void setSystemFrameService(SystemFrameService systemFrameService) {
		this.systemFrameService = systemFrameService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.formengine.dao.WorkItemInfoDao#findFormType(java.lang.String)
	 */
	public String findFormType(String formId) {

		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = initDataSource.getConnection();
			String sql = "SELECT FO_FTYPE FROM ENG_FORM_FORM WHERE FO_ID=?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, formId);

			rs = pstm.executeQuery();
			if (rs.next()) {
				return rs.getString("FO_FTYPE");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
