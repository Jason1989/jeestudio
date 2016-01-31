package com.zxt.compplatform.formengine.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zxt.compplatform.formengine.dao.impl.ComponentsDaoImpl;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.PagerEntiy;
import com.zxt.framework.common.util.SQLBlobUtil;

/**
 * xml持久化实现
 * 
 * @author 007
 */
public class QueryXmlData implements IQueryXmlData {

	public QueryXmlData() {
	}

	/**
	 * 页面组件持久化操作
	 */
	private ComponentsDaoImpl complnentsDaoImpl;

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.dao.IQueryXmlData#findBolbXMLById(java.lang.String, java.lang.String)
	 */
	public String findBolbXMLById(String sql, String formID) {
		return complnentsDaoImpl.findBlobXMLById(sql, formID);
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.dao.IQueryXmlData#queryFormData(java.lang.String, java.lang.String[], com.zxt.compplatform.formengine.entity.view.ListPage, javax.servlet.http.HttpServletRequest)
	 */
	public PagerEntiy queryFormData(String sql, String[] parmerValue,
			ListPage listPage, HttpServletRequest request) {

		return complnentsDaoImpl.queryFormData(sql, parmerValue, listPage,
				request);
	}

	/**
	 * 根据表单id查询编辑页配置xml
	 * @param formId
	 * @return
	 * @throws Exception
	 */
	public List queryEditXmlByFormId(String formId) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		List editList = new ArrayList();
		ResultSet rs = null;
		String res = "";
		try {
			// conn = getConn();
			String sql = "select fo_settings from eng_form_form where fo_id ='"
					+ formId + "' ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Blob b = rs.getBlob("FO_SETTINGS");
				// res = new String(b.getBytes(1, (int) b.length()));
				res = SQLBlobUtil.blobToString(b);
				editList.add(res);
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

		return editList;

	}

	public ComponentsDaoImpl getComplnentsDaoImpl() {
		return complnentsDaoImpl;
	}

	public void setComplnentsDaoImpl(ComponentsDaoImpl complnentsDaoImpl) {
		this.complnentsDaoImpl = complnentsDaoImpl;
	}

}
