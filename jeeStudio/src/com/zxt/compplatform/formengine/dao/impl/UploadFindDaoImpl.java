package com.zxt.compplatform.formengine.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.zxt.compplatform.formengine.dao.UploadFindDao;

/**
 * 文件上传持久化实现
 * 
 * @author 007
 */
public class UploadFindDaoImpl implements UploadFindDao {
	/**
	 * spring jdbc数据库操作模版
	 */
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.dao.UploadFindDao#find(java.lang.String)
	 */
	public List find(String sql) {
		return jdbcTemplate.queryForList(sql);
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.dao.UploadFindDao#delete(java.lang.String)
	 */
	public void delete(String sql) {
		jdbcTemplate.execute(sql);
	}
}
