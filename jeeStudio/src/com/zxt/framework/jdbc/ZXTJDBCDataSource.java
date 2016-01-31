package com.zxt.framework.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

public class ZXTJDBCDataSource implements DataSource {

	private String JDBCDriver = "";
	private String URL = "";
	private String USER = "";
	private String PASSWORD = "";
	protected Connection connection;

	/**
	 * 构造方法
	 * 
	 * @param driver
	 * @param url
	 * @param user
	 * @param pwd
	 */
	public ZXTJDBCDataSource(final String driver, final String url,
			final String user, final String pwd) {
		this.JDBCDriver = driver;
		this.URL = url;
		this.USER = user;
		this.PASSWORD = pwd;
	}

	/**
	 * 构造方法
	 * 
	 * @param ip
	 * @param port
	 * @param sid
	 * @param dbType
	 * @param username
	 * @param password
	 */
	public ZXTJDBCDataSource(String ip, String port, String sid, String dbType,
			String username, String password) {
		String url = "";
		String driverName = "";
		String dbSid = "";
		if (dbType.equals("1")) {
			url = "jdbc:oracle:thin:@";
			driverName = "oracle.jdbc.driver.OracleDriver";
			dbSid = ":" + sid;
		} else if (dbType.equals("2")) {
			url = "jdbc:sqlserver://";
			// driverName = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
			driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			dbSid = ";databasename=" + sid;
		} else if (dbType.equals("3")) {
			url = "jdbc:mysql://";
			driverName = "com.mysql.jdbc.Driver";
			dbSid = "/" + sid;
		}
		url += ip;
		if (port != null && !port.trim().equals("")
				&& !port.trim().equals("null")) {
			url += ":" + port;
		}
		url += dbSid;
		this.JDBCDriver = driverName;
		this.URL = url;
		this.USER = username;
		this.PASSWORD = password;
	}

	/**
	 * 构造方法
	 * 
	 * @param dataSource
	 */
	public ZXTJDBCDataSource(
			com.zxt.compplatform.datasource.entity.DataSource dataSource) {
		String url = "";
		String driverName = "";
		String dbSid = "";
		if (dataSource.getDbType().equals("1")) {
			url = "jdbc:oracle:thin:@";
			driverName = "oracle.jdbc.driver.OracleDriver";
			dbSid = ":" + dataSource.getSid();
		} else if (dataSource.getDbType().equals("2")) {
			url = "jdbc:sqlserver://";
			// driverName = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
			driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			dbSid = ";databasename=" + dataSource.getSid();
		} else if (dataSource.getDbType().equals("3")) {
			url = "jdbc:mysql://";
			driverName = "com.mysql.jdbc.Driver";
			dbSid = "/" + dataSource.getSid();
		}
		url += dataSource.getIpAddress();
		if (dataSource.getPort() != null
				&& !dataSource.getPort().toString().trim().equals("")
				&& !dataSource.getPort().toString().trim().equals("null")) {
			url += ":" + dataSource.getPort();
		}
		url += dbSid;
		this.JDBCDriver = driverName;
		this.URL = url;
		this.USER = dataSource.getUsername();
		this.PASSWORD = dataSource.getPassword();
	}

	/**
	 * 创建连接
	 * 
	 * @return
	 */
	protected synchronized Connection createConnection() {
		try {
			if (connection != null)
				return connection;
			Class.forName(JDBCDriver).newInstance();
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			System.out.println("数据库加载失败！");
			e.printStackTrace();

		}
		return connection;
	}

	public String getJDBCDriver() {
		return JDBCDriver;
	}

	public String getURL() {
		return URL;
	}

	public String getUSER() {
		return USER;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	/**
	 * 继承父类获取连接的方法
	 */
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return createConnection();
	}

	/**
	 * 继承父类获取连接的方法
	 */
	public Connection getConnection(String username, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 继承父类方法
	 */
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 继承父类方法
	 */
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 继承父类方法
	 */
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	/**
	 * 继承父类方法
	 */
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	/**
	 * 封装属性
	 */
	public boolean isWrapperFor(Class arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 封装属性
	 */
	public Object unwrap(Class arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
