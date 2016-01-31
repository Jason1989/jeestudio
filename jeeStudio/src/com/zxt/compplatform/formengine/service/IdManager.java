package com.zxt.compplatform.formengine.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.zxt.compplatform.formengine.dao.BaseDao;

/**
 * id管理
 * @author 007
 */
public class IdManager extends BaseDao {

	/**
	 * 创建id
	 * @param formId
	 * @param modelName
	 * @return
	 */
	static public String createID(String formId, String modelName) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		String formName = "";
		ResultSet rs = null;
		try {
			conn = getConn();
			String sql = " select fo_name from eng_form_form where fo_id='"
					+ formId + "'";
			// System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				formName = rs.getString("fo_name");
			}
		} catch (Exception e) {

		}
		return formName + modelName;
	}
}
