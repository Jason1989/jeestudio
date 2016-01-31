package com.zxt.compplatform.formengine.log4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.jdbc.JDBCAppender;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 日志存储到数据库
 * 
 * @author 007
 */
public class JdbcAppender extends JDBCAppender {

	/* (non-Javadoc)
	 * @see org.apache.log4j.jdbc.JDBCAppender#getConnection()
	 */
	protected Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return com.zxt.compplatform.formengine.log4j.HibernateUtil
				.currentSession().connection();

	}

	/* (non-Javadoc)
	 * @see org.apache.log4j.jdbc.JDBCAppender#closeConnection(java.sql.Connection)
	 */
	protected void closeConnection(Connection con) {
		// TODO Auto-generated method stub
		try {

			if (connection != null && !connection.isClosed())

				connection.close();

		} catch (SQLException e) {

			errorHandler.error("Error closing connection", e,

			ErrorCode.GENERIC_FAILURE);

		}

	}

	/* (non-Javadoc)
	 * @see org.apache.log4j.jdbc.JDBCAppender#execute(java.lang.String)
	 */
	protected void execute(String sql) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = null;

		PreparedStatement stmt = null;

		try {

			con = getConnection();

			stmt = con.prepareStatement(sql);

			stmt.execute();
			con.commit();

		} catch (SQLException e) {

			if (stmt != null)

				stmt.close();

			throw e;

		}

		stmt.close();

		closeConnection(con);

	}

	// 把日志存入数据库，有特殊符号如单引号，要进行转换操作
	/* (non-Javadoc)
	 * @see org.apache.log4j.jdbc.JDBCAppender#getLogStatement(org.apache.log4j.spi.LoggingEvent)
	 */
	protected String getLogStatement(LoggingEvent event) {
		String fqnOfCategoryClass = event.fqnOfCategoryClass;
		Category logger = Logger.getRootLogger();
		Priority level = event.getLevel();
		Object message = event.getMessage();
		Throwable throwable = null;
		ReLoggingEvent bEvent = new ReLoggingEvent(fqnOfCategoryClass, logger,
				level, message, throwable);
		return super.getLogStatement(bEvent);
	}
}
