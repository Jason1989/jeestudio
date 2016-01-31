package com.zxt.compplatform.formengine.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zxt.compplatform.formengine.dao.ValidateDao;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.framework.jdbc.ZXTJDBCTemplate;

/**
 * 验证数据持久化操作
 * 
 * @author 007
 */
public class ValidateDaoImpl extends ZXTJDBCTemplate implements ValidateDao {
	private SystemFrameService systemFrameService;

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.dao.ValidateDao#isExist(java.lang.String, java.lang.String, java.lang.String)
	 */
	public int isExist(String datasource, String sql, String name) {
		// TODO Auto-generated method stub
		Map poolsMap = systemFrameService.load_connectPools("true");
		PreparedStatement pstm = null;
		ComboPooledDataSource connectPool = null;
		Connection conn = null;
		ResultSet rs = null;
		int index = 0;
		connectPool = (ComboPooledDataSource) poolsMap.get(datasource);
		try {
			conn = connectPool.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, name);
			rs = pstm.executeQuery();
			while (rs.next()) {
				index++;
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return index;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.dao.ValidateDao#isExist(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public int isExist(String datasource, String sql, String name, String id) {
		// TODO Auto-generated method stub
		Map poolsMap = systemFrameService.load_connectPools("true");
		PreparedStatement pstm = null;
		ComboPooledDataSource connectPool = null;
		Connection conn = null;
		ResultSet rs = null;
		int index = 0;
		connectPool = (ComboPooledDataSource) poolsMap.get(datasource);
		try {
			conn = connectPool.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, name);
			pstm.setString(2, id);
			rs = pstm.executeQuery();
			while (rs.next()) {
				index++;
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return index;
	}

	public SystemFrameService getSystemFrameService() {
		return systemFrameService;
	}

	public void setSystemFrameService(SystemFrameService systemFrameService) {
		this.systemFrameService = systemFrameService;
	}

}
