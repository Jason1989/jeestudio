package com.zxt.ht.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.zxt.ht.dao.PlanLimitDao;

public class PlanLimitDaoImpl  implements PlanLimitDao{
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	
	public void insertTo(String sql,Object[] endParmer) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			pstm = conn.prepareStatement(sql);
			if (endParmer != null) {
				for (int i = 0; i < endParmer.length; i++) {
					pstm.setObject(i + 1, endParmer[i]);
				}
			}

			int count = pstm.executeUpdate();
		}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();

		} finally {
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}

	@Override
	public String findPt(String sql) {
		// TODO Auto-generated method stub
		String findStr = null;
		ResultSet count=null;
		PreparedStatement pstm = null;
		Connection conn = null;

		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			pstm = conn.prepareStatement(sql);
			SqlRowSet temSet=jdbcTemplate.queryForRowSet(sql);
			

			if (temSet != null) {
				while (temSet.next()) {
					findStr = temSet.getString("SP_SETS");
					
				}
			}
		}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();

		} finally {
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
		return findStr;
	}
	
	
	
	
	
}