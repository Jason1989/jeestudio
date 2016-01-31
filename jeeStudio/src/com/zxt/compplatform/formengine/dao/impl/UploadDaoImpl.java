package com.zxt.compplatform.formengine.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.zxt.compplatform.formengine.entity.dataset.AttachementVo;
import com.zxt.compplatform.formengine.util.DBUtil;

/**
 * 文件上传实现
 * 
 * @author 007
 */
public class UploadDaoImpl {
	
	private static final Logger log = Logger.getLogger(UploadDaoImpl.class);
	
	/**
	 * 添加上传文件
	 * @param attachementVo
	 * @return
	 */
	public static int add(AttachementVo attachementVo){
		int updateCount = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "insert into dbo.eng_attachments (FILE_ID,FILE_RNAME,FILE_SIZE,FILE_TYPE,UPLOAD_DATE,USER_ID,USER_NAME,COLUMN_ID,FILE_NAME,FILE_PATH) values (?,?,?,?,?,?,?,?,?,?)";
		try {
			conn = DBUtil.getConn();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, attachementVo.getFileid());
			pstm.setString(2, attachementVo.getFilename());
			pstm.setString(3, attachementVo.getFilesize());
			pstm.setString(4, attachementVo.getFiletype());
			pstm.setString(5, attachementVo.getUploaddate());
			pstm.setString(6, attachementVo.getUserid());
			pstm.setString(7, attachementVo.getUsername());
			pstm.setString(8, attachementVo.getColumnid());
			pstm.setString(9, attachementVo.getFilername());
			pstm.setString(10, attachementVo.getFilepath());
			updateCount=pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return updateCount;
	}
	
}
