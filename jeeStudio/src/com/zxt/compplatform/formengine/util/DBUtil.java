package com.zxt.compplatform.formengine.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 数据库操作工具类
 * 
 * @author 007
 */
public class DBUtil {
	/**
	 * 获取连接
	 * 
	 * @return
	 */
	public static Connection getConn() {
		try {

			Properties properties = DBUtil.jdbcProperties();
			Class.forName(properties.getProperty("driverClassName"));
			Connection connection = DriverManager.getConnection(properties
					.getProperty("url"), properties.getProperty("userName"),
					properties.getProperty("password"));
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 关闭连接
	 * @param connection
	 * @param statement
	 */
	public static void close(Connection connection, Statement statement) {
		try {
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭连接
	 * @param connection
	 * @param statement
	 * @param rs
	 */
	public static void close(Connection connection, Statement statement,
			ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取jdbc配置
	 * 
	 * @return
	 */
	public static Properties jdbcProperties() {
		Properties properties = new Properties();
		InputStream inStream;
		try {
			inStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("/jdbc.properties");
			properties.load(inStream);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}
}
