package com.zxt.framework.jdbc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.zxt.compplatform.formengine.entity.view.TreeData;
import com.zxt.framework.common.util.SQLBlobUtil;

public class ZXTJDBCTemplate {

	private static final Logger log = Logger.getLogger(ZXTJDBCTemplate.class);
	private DataSource dataSource;
	private Connection conn;

	public ZXTJDBCTemplate() {
	}

	public ZXTJDBCTemplate(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 获取列表
	 */
	public synchronized List find(String sql, Class clazz) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			conn = dataSource.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			list = transformResultSetToList(clazz, rs);
			// System.out.println("执行："+sql+",共有："+(list==null?0:list.size())+"条记录");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, pstm, rs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 查询封装到map
	 * 
	 * @param sql
	 * @return
	 */
	public synchronized List findToMaps(String sql) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			conn = dataSource.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			list = transformResultSetToList(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, pstm, rs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 抛出异常
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public synchronized List findToListMaps(String sql) throws SQLException {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			conn = dataSource.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			list = transformResultSetToList(rs);
		} finally {
			this.closeAll(conn, pstm, rs);
		}
		return list;
	}

	/**
	 * 查询封装map
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public synchronized Map findToMap(String sql, Object[] params) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Map map = new HashMap();
		List list = new ArrayList();
		try {
			conn = dataSource.getConnection();
			pstm = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstm.setObject(i + 1, params[i]);
			}
			/**
			 * 编辑结果集
			 */
			rs = pstm.executeQuery();
			list = transformResultSetToList(rs);
			log.info("执行：" + sql + ",共有：" + (list == null ? 0 : list.size())
					+ "条记录");
			if (null != list && list.size() > 0) {
				map = (Map) list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, pstm, rs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * 查询封装map
	 * 
	 * @param sql
	 * @return
	 */
	public synchronized Map findToMap(String sql) {
		Statement stmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		List list = new ArrayList();
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			list = transformResultSetToList(rs);
			log.info("执行：" + sql + ",共有：" + (list == null ? 0 : list.size())
					+ "条记录");
			if (null != list && list.size() > 0) {
				map = (Map) list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, stmt, rs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * 查询封装对象
	 * 
	 * @param sql
	 * @param params
	 * @param clazz
	 * @return
	 */
	public synchronized Object findToObject(String sql, Object[] params,
			Class clazz) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Object obj = null;
		List list = new ArrayList();
		try {
			conn = dataSource.getConnection();
			pstm = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstm.setObject(i + 1, params[i]);
			}
			rs = pstm.executeQuery();
			list = transformResultSetToList(clazz, rs);
			if (null != list && list.size() > 0) {
				obj = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, pstm, rs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * 查询结果集
	 * 
	 * @param sql
	 * @param params
	 * @param clazz
	 * @return
	 */
	public synchronized List find(String sql, Object[] params, Class clazz) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			conn = dataSource.getConnection();
			pstm = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstm.setObject(i + 1, params[i]);
			}
			rs = pstm.executeQuery();
			list = transformResultSetToList(clazz, rs);
			log.info("执行：" + sql + ",共有：" + (list == null ? 0 : list.size())
					+ "条记录");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, pstm, rs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 查询结果集合
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public synchronized List findToMaps(String sql, Object[] params) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			conn = dataSource.getConnection();
			pstm = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstm.setObject(i + 1, params[i]);
			}
			rs = pstm.executeQuery();
			list = transformResultSetToList(rs);
			log.info("执行：" + sql + ",共有：" + (list == null ? 0 : list.size())
					+ "条记录");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, pstm, rs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 查询结果
	 * 
	 * @param sql
	 * @return
	 */
	// add start(查询记录条数)
	public synchronized int find(String sql) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int count = -1;
		try {
			conn = dataSource.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
			log.info("执行：" + sql + ",共有：" + count + "条记录");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, pstm, rs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}

	/**
	 * 判断数据库
	 */
	public int judge() {
		int result = 0;
		String url = "";
		try {
			url = dataSource.getConnection().getMetaData().getURL();
			if (url.contains("oracle")) {
				result = 1;
			} else if (url.contains("sqlserver")) {
				result = 2;
			} else if (url.contains("mysql")) {
				result = 3;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// add end
	/**
	 * 创建连接
	 */
	public synchronized void create(String sql, Object[] params) {
		PreparedStatement pstm = null;
		try {
			conn = dataSource.getConnection();
			pstm = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstm.setObject(i + 1, params[i]);
			}
			int rows = pstm.executeUpdate();
			log.info("执行：" + sql + ",共插入：" + rows + "条记录，参数为" + params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, pstm, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建连接
	 * 
	 * @param sql
	 */
	public synchronized void create(String sql) {
		Statement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			boolean result = stmt.execute(sql);
			log.info("执行：" + sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, stmt, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * added by bozch for Exception deal 创建计数器
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public synchronized void createRecord(String sql) throws SQLException {
		Statement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			boolean result = stmt.execute(sql);
			log.info("执行：" + sql);
		} finally {
			try {
				this.closeAll(conn, stmt, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除方法
	 * 
	 * @param sql
	 * @param params
	 */
	public synchronized void delete(String sql, Object[] params) {
		PreparedStatement pstm = null;
		try {
			conn = dataSource.getConnection();
			pstm = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstm.setObject(i + 1, params[i]);
			}
			int rows = pstm.executeUpdate();
			log.info("执行：" + sql + ",共删除：" + rows + "条记录，参数为" + params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, pstm, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除方法
	 * 
	 * @param sql
	 */
	public synchronized void delete(String sql) {
		Statement stm = null;
		try {
			conn = dataSource.getConnection();
			stm = conn.createStatement();
			int rows = stm.executeUpdate(sql);
			log.info("执行：" + sql + ",共删除：" + rows + "条记录");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, null, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除多条或者一条
	 * 
	 * @param sql
	 * @param params
	 */
	public synchronized void delete(String[] sql) {
		Statement stm = null;
		try {
			conn = dataSource.getConnection();
			stm = conn.createStatement();
			for (int i = 0; i < sql.length; i++) {
				int rows = stm.executeUpdate(sql[i]);
				log.info("执行：" + sql + ",共删除：" + rows + "条记录");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, null, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 更新方法
	 * 
	 * @param sql
	 * @param params
	 */
	public synchronized void update(String sql, Object[] params) {
		PreparedStatement pstm = null;
		try {
			conn = dataSource.getConnection();
			pstm = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstm.setObject(i + 1, params[i]);
			}
			int rows = pstm.executeUpdate();
			log.info("执行：" + sql + ",共修改：" + rows + "条记录，参数为");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, pstm, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 更新方法
	 * 
	 * @param sql
	 */
	public synchronized void update(String sql) {
		Statement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			int rows = stmt.executeUpdate(sql);
			log.info("执行：" + sql + ",共修改：" + rows + "条记录，参数为");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, stmt, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 批处理 更新方法
	 * 
	 * @param sql
	 */
	public synchronized void updateBatch(String[] sql) {
		Statement stmt = null;
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			for(int i=0;i<10;i++)
			stmt.addBatch(sql[i]);
			int[] rows=stmt.executeBatch();
			//int rows = stmt.executeUpdate(sql);
			log.info("执行：" + sql+ ",共修改：" + rows.length + "条记录，参数为");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, stmt, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 删除全部数据
	 * 
	 * @param sql
	 * @param params
	 */
	public synchronized void deleteAll(String sql, Object[] params) {
		PreparedStatement sm = null;
		try {
			conn = dataSource.getConnection();
			sm = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			sm.clearBatch();// 清空此 Statement 对象的当前 SQL 命令列表
			if (conn != null) {
				for (int i = 0; i < params.length; i++) {
					sm.setObject(i + 1, params[i]);
					sm.addBatch();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, sm, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public synchronized void updateAll(Collection paramCollection) {

	}

	/**
	 * 创建全部数据
	 * 
	 * @param paramList
	 */
	public synchronized void createAll(List paramList) {
		Statement pstm = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			pstm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Iterator it = paramList.iterator();
			while (it.hasNext()) {
				pstm.addBatch(it.next().toString());
			}
			int[] rows = pstm.executeBatch();
			conn.commit();
			// int rows = pstm.executeUpdate();
			// System.out.println("执行："+sql+",共插入："+rows+"条记录，参数为"+params);
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
				this.closeAll(conn, pstm, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public List findAllByPage(String paramString, int page, int rows) {
		return null;
	}

	/**
	 * 查找总数
	 * 
	 * @param queryString
	 * @return
	 */
	public int findTotalRows(String queryString) {
		java.sql.ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			rs = conn.createStatement().executeQuery(queryString);
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return 0;
	}

	/**
	 * 关闭全部
	 * 
	 * @param con
	 * @param pt
	 * @param rs
	 */
	public void closeAll(Connection con, Statement pt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (pt != null) {
			try {
				pt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 添加结果集
	 * 
	 * @param clazz
	 * @param rs
	 * @return
	 */
	protected List transformResultSetToList(Class clazz, ResultSet rs) {
		List list = new ArrayList();
		try {
			Object obj = null;
			Object recordValue = null;
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				obj = clazz.newInstance();
				for (int i = 1; i < columnCount + 1; i++) {
					// System.out.println(StringUtils.center(rsmd.getColumnType(i)+"",
					// 80, '*'));
					/**
					 * 判断数据类型
					 */
					if (rs.getObject(rsmd.getColumnName(i)) != null) {
						recordValue = rs.getObject(rsmd.getColumnName(i));
						// System.out.println("recordValue---->"+recordValue);
						if (recordValue instanceof BigDecimal) {

							if (obj instanceof TreeData) {
								recordValue = new String(rs.getLong(rsmd
										.getColumnName(i))
										+ "");
							} else {
								recordValue = new Long(rs.getLong(rsmd
										.getColumnName(i)));
							}

							// oracle
							// recordValue =new
							// Long(rs.getLong(rsmd.getColumnName(i)));

						} else if (recordValue instanceof Integer) {
							recordValue = new Integer(rs.getInt(rsmd
									.getColumnName(i)));
						} else if (recordValue instanceof java.sql.Date) {
							recordValue = new Date(rs.getDate(
									rsmd.getColumnName(i)).getTime());
						} else if (recordValue instanceof Blob
								|| recordValue instanceof Clob) {
							/**
							 * blod类型的处理
							 */
							BufferedReader bf = null;
							if (recordValue instanceof Blob) {
								java.sql.Blob blob = (java.sql.Blob) rs
										.getBlob(rsmd.getColumnName(i));
								bf = new BufferedReader(new InputStreamReader(
										blob.getBinaryStream()));
								String temp = "";
								StringBuffer sb = new StringBuffer();
								while ((temp = bf.readLine()) != null) {
									sb.append(temp);
								}
								recordValue = sb.toString();
							} else {
								java.sql.Clob clob = (java.sql.Clob) rs
										.getClob(rsmd.getColumnName(i));
								recordValue = clob == null ? null : clob
										.getSubString(1L, (int) clob.length());
							}
						}
					} else {
						recordValue = "";
					}
					/**
					 * 获取方法类型
					 */
					String methodName = "set"
							+ transformTableName(rsmd.getColumnName(i));
					if (!"".equals(recordValue)) {
						Method method = clazz.getMethod(methodName,
								new Class[] { recordValue.getClass() });
						method.invoke(obj, new Object[] { recordValue });
					}

				}
				list.add(obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public String findBlobById(String sql) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String str = null;
		try {
			conn = dataSource.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			str = transformResultSetToString(rs);
			log.info("执行：" + sql + ",共有：" + (str == null ? 0 : str.length())
					+ "条记录");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, pstm, rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	public List queryFormData(String sql) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List listPage = new ArrayList();
		String[] fieldArray = null;
		String temsql = sql.substring(sql.indexOf("select") + 6, sql
				.indexOf("from"));
		temsql = temsql.trim();
		String[] temString;
		fieldArray = temsql.split(",");
		try {
			conn = dataSource.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Map map = new HashMap();
				for (int i = 0; i < fieldArray.length; i++) {
					String name = fieldArray[i].substring(fieldArray[i]
							.indexOf(".") + 1);
					temString = name.split("as");
					map.put(temString[0].trim(), rs.getString(temString[0]
							.trim()));
				}
				listPage.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				this.closeAll(conn, pstm, rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listPage;
	}

	private List transformResultSetToList(ResultSet rs) {
		List list = new ArrayList();
		try {
			Object recordValue = null;
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				Map map = new HashMap();
				for (int i = 1; i < columnCount + 1; i++) {
					// System.out.println(StringUtils.center(rsmd.getColumnType(i)+"",
					// 80, '*'));
					if (rs.getObject(rsmd.getColumnName(i)) != null) {
						recordValue = rs.getObject(rsmd.getColumnName(i));
						if (recordValue instanceof BigDecimal) {
							recordValue = new Long(rs.getLong(rsmd
									.getColumnName(i)));
						} else if (recordValue instanceof java.sql.Date) {
							recordValue = new Date(rs.getDate(
									rsmd.getColumnName(i)).getTime());
						} else if (recordValue instanceof Blob
								|| recordValue instanceof Clob) {
							BufferedReader bf = null;
							if (recordValue instanceof Blob) {
								java.sql.Blob blob = (java.sql.Blob) rs
										.getBlob(rsmd.getColumnName(i));
								bf = new BufferedReader(new InputStreamReader(
										blob.getBinaryStream()));
								String temp = "";
								StringBuffer sb = new StringBuffer();
								while ((temp = bf.readLine()) != null) {
									sb.append(temp);
								}
								recordValue = sb.toString();
							} else {
								java.sql.Clob clob = (java.sql.Clob) rs
										.getClob(rsmd.getColumnName(i));
								recordValue = clob == null ? null : clob
										.getSubString(1L, (int) clob.length());
							}
						}
					} else {
						recordValue = "";
					}
					map.put(transformColumnName(rsmd.getColumnName(i)),
							recordValue);
				}
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private String transformResultSetToString(ResultSet rs) {
		String res = null;
		if (rs != null) {
			try {
				while (rs.next()) {
					Blob b = rs.getBlob("fo_settings");
					res = SQLBlobUtil.blobToString(b);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;

	}

	private String transformTableName(String tableName) {
		StringBuffer sb = new StringBuffer();
		if (tableName.indexOf("_") > 0) {
			String[] aa = tableName.split("_");
			for (int i = 0; i < aa.length; i++) {
				sb.append(aa[i].substring(0, 1).toUpperCase()
						+ aa[i].substring(1).toLowerCase());
			}
		} else {
			sb.append(tableName.substring(0, 1).toUpperCase()
					+ tableName.substring(1).toLowerCase());
		}
		return sb.toString();
	}

	private String transformColumnName(String columnName) {
		StringBuffer sb = new StringBuffer();
		String column;
		if (columnName.indexOf("_") > 0) {
			String[] aa = columnName.split("_");
			for (int i = 0; i < aa.length; i++) {
				sb.append(aa[i].substring(0, 1).toUpperCase()
						+ aa[i].substring(1).toLowerCase());
			}
			column = sb.toString().substring(0, 1).toLowerCase()
					+ sb.toString().substring(1);
		} else {
			column = sb.append(columnName.toLowerCase()).toString();
		}
		return column;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * GUOWEIXIN额外加一 连接方法 目的用于操作：其它数据源的
	 */
	public List execConnection(Connection conn, String sql, Class clazz) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			list = transformResultSetToList(clazz, rs);
			// System.out.println("执行："+sql+",共有："+(list==null?0:list.size())+"条记录");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();// 放回连接池
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				 e.printStackTrace();
			}
		}
		return list;
	}
}
