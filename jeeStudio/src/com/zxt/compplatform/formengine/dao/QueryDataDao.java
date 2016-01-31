package com.zxt.compplatform.formengine.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zxt.compplatform.formengine.entity.view.Page;

/**
 * 数据查询实现
 * @author 007
 */
public class QueryDataDao extends BaseDao implements IQueryDataDao {

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.dao.IQueryDataDao#queryPages(com.zxt.compplatform.formengine.entity.view.Page)
	 */
	public String queryPages(Page page) {
		String formId = page.getFormId();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = new ArrayList();
		String res = "";
		StringBuffer query = new StringBuffer();
		try {
			conn = getConn();
			query.append(" SELECT ");
			query.append(" TY.STY_CONTENT");
			query.append(" FROM BP_T_FORM_FORMSDEFINITION TY ");
			query.append(" WHERE FORMS_ID = '" + formId + "'");
//			System.out.println(query.toString());
			pstmt = conn.prepareStatement(query.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {

				Blob b = rs.getBlob("STY_CONTENT");
				res = new String(b.getBytes(1L, (int) b.length()));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.dao.IQueryDataDao#queryTabs(java.lang.String)
	 */
	public String queryTabs(String tabId) {
		return null;
	}

}
