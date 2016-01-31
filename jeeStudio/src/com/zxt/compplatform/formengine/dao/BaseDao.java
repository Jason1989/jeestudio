package com.zxt.compplatform.formengine.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 持久化操作基类
 * 
 * @author 007
 */
public class BaseDao {

	public BaseDao() {
	}

	/**
	 * jdbc文件路径
	 */
	private static String FILEPATH = "/jdbc.properties";
	/**
	 * 单例
	 */
	private static BaseDao configConnection = new BaseDao();
	/**
	 * 连接字符串
	 */
	public String url;
	/**
	 * 用户名
	 */
	public String userName;
	/**
	 * 密码
	 */
	public String password;
	/**
	 * 驱动类名
	 */
	public String driverClassName;

	/**
	 * 获取所有的建
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		Properties props = new Properties();
		try {
			InputStream ips = configConnection.getClass().getResourceAsStream(
					FILEPATH);
			props.load(ips);
			String value = props.getProperty(key);
			return value;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取数据库连接
	 * @return
	 */
	public static Connection getConn() {
		String driverClassName = BaseDao.get("driverClassName");
		String url = BaseDao.get("url");// 设置连接字符串
		String userName = BaseDao.get("userName");// 用户名
		String password = BaseDao.get("password");// 密码
		Connection conn = null; // 创建数据库连接对象
		try {
			Class.forName(driverClassName);
			conn = DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

}
