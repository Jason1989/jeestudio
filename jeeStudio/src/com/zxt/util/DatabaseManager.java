package com.zxt.util;

import antlr.collections.List;

import com.mysql.jdbc.Connection;

/**
 * 查询数据库的相关信息
 * 1、oracle
 * 2、sqlserver(2000/2005/2008)
 * @author bozch
 *
 */
public interface DatabaseManager {

	/**
	 * 获取所有的数据库中的表(table)
	 * @param conn
	 * @return
	 */
	public List getAllTable(Connection conn);
	/**
	 * 获取所有的数据库中的表(table)
	 * @param conn
	 * @return
	 */
	public List getAllView(Connection conn);
	
	
	/**
	 * 获取数据库中所有内置的数据类型
	 * @param dbType
	 * @return
	 */
	public List getColumnTypes(String dbType);
	
	
}
