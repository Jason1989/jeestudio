package com.zxt.framework.jdbc;

public class  Configuration  {
	
	private  String JDBCDriver="";
	private  String URL="";
	private  String USER="";
	private  String PASSWORD="";
	/**
	 * jdbc配置
	 * @param driver
	 * @param url
	 * @param user
	 * @param pwd
	 */
	public Configuration(final String driver,final String url,
				final String user,final String pwd){
		this.JDBCDriver=driver;
		this.URL=url;
		this.USER=user;
		this.PASSWORD=pwd;
	}
	/**
	 * jdbc工具类拼接连接信息
	 * @param ip
	 * @param port
	 * @param sid
	 * @param dbType
	 * @param username
	 * @param password
	 */
	public Configuration(String ip,String port,String sid,String dbType,String username,String password){
		String url = "";
		String driverName = "";
		String dbSid = "";
		if(dbType.equals("1")){
			url = "jdbc:oracle:thin:@";
			driverName = "oracle.jdbc.driver.OracleDriver";
			dbSid = ":" + sid;
		}else if(dbType.equals("2")){
			url = "jdbc:sqlserver://";
			driverName = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
			dbSid = ";databasename=" + sid;
		}else if(dbType.equals("3")){
			url = "";
			driverName = "";
		}
		url += ip + ":" + port + dbSid;   
		this.JDBCDriver = driverName;
		this.URL = url;
		this.USER = username;   
		this.PASSWORD = password;
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

		
}
