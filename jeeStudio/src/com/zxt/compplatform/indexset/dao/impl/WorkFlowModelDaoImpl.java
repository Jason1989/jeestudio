package com.zxt.compplatform.indexset.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zxt.compplatform.indexset.dao.WorkFlowModelDao;

public class WorkFlowModelDaoImpl implements WorkFlowModelDao {
	private JdbcTemplate jdbcTemplate;

	public List queryFormData(String sql, String[] parmerValue,
			ComboPooledDataSource pool) {
		if (pool != null) {
			jdbcTemplate.setDataSource(pool);
		}
		List list = new ArrayList();
		try {
			list = jdbcTemplate.queryForList(sql, parmerValue);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return list;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
