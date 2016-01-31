package com.zxt.framework.dictionary.dao;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zxt.compplatform.formengine.entity.view.Field;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.framework.dictionary.entity.SqlObj;

public class SqlDicDaoImpl implements SqlDicDao {
	private JdbcTemplate jdbcTemplate;
	/**
	 * 基础数据源
	 */
	private DataSource initDataSource;
	private SystemFrameService systemFrameService;
	private static final Logger log = Logger.getLogger(SqlDicDaoImpl.class);

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public DataSource getInitDataSource() {
		return initDataSource;
	}

	public void setInitDataSource(DataSource initDataSource) {
		this.initDataSource = initDataSource;
	}

	public SystemFrameService getSystemFrameService() {
		return systemFrameService;
	}

	public void setSystemFrameService(SystemFrameService systemFrameService) {
		this.systemFrameService = systemFrameService;
	}
	
	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.dao.SqlDicDao#deleteData(java.lang.String, java.lang.String[], java.lang.String)
	 */
	public int deleteData(String sql, String[] pramers,String dataSourceId) {
		ComboPooledDataSource pool = findPoolByDataSourceId(dataSourceId);
		if (pool != null) {
			jdbcTemplate.setDataSource(pool);
		}
		/**
		 * 删除操作
		 */
		int updateCount = jdbcTemplate.update(sql, pramers);
		/**
		 * 切换初始化连接
		 */
		jdbcTemplate.setDataSource(initDataSource);
		return updateCount;
	}

	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.dao.SqlDicDao#dynamicSave(java.lang.String, java.lang.String[], java.lang.String)
	 */
	public int dynamicSave(String sql, String[] endParmer, String dataSourceId) {
		// TODO Auto-generated method stub
		/**
		 * 切换连接池
		 */
		ComboPooledDataSource pool = findPoolByDataSourceId(dataSourceId);
		if (pool != null) {
			jdbcTemplate.setDataSource(pool);
		}
		/**
		 * 执行sql
		 */
		// int count=jdbcTemplate.update(sql, endParmer);
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String str = null;
		Connection conn = null;

		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			pstm = conn.prepareStatement(sql);
			if (endParmer != null) {
				for (int i = 0; i < endParmer.length; i++) {

					pstm.setObject(i + 1, endParmer[i]);

				}
			}

			int count = pstm.executeUpdate();
			log.info(count + " data update...");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/**
		 * 切换初始化连接池
		 */
		jdbcTemplate.setDataSource(initDataSource);
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.dao.SqlDicDao#createIDkey(java.lang.String, java.lang.String)
	 */
	public String createIDkey(String tableName, String IDkey) {
		/**
		 * 使用 序列
		 */
		String IDvalue = "";
		IDvalue = new Date().getTime() + "";
		return IDvalue;
	}


	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.dao.SqlDicDao#loadDynamicDictionary(java.lang.String, java.lang.String)
	 */
	public Map loadDynamicDictionary(String sql, String dataSourceID) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		// List resultList = jdbcTemplate
		// .queryForList(sql);
		try {
			Map poolsMap = systemFrameService.load_connectPools("true");
			// 3 数据源ID查找对应的连接池
			ComboPooledDataSource connectPool = null;
			connectPool = (ComboPooledDataSource) poolsMap.get(dataSourceID);
			jdbcTemplate.setDataSource(connectPool);
			SqlRowSet temSet = jdbcTemplate.queryForRowSet(sql);
			if (temSet != null) {
				while (temSet.next()) {
					map.put(temSet.getString(1), temSet.getString(2));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcTemplate.setDataSource(initDataSource);
		}

		return map;
	}

	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.dao.SqlDicDao#findBlobXMLById(java.lang.String, java.lang.String)
	 */
	public String findBlobXMLById(String sql, String formID) {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String str = null;
		Connection conn = null;
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, formID);
			rs = pstm.executeQuery();
			if (rs.next()) {
				Blob b = rs.getBlob("fo_settings");
				// str = SQLBlobUtil.blobToString(b);

				byte[] byteArray = b.getBytes(1, (int) b.length());
				str = new String(byteArray);

			}
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
		return str;
	}

	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.dao.SqlDicDao#queryFormData(java.lang.String, java.lang.String[], com.zxt.compplatform.formengine.entity.view.ListPage, javax.servlet.http.HttpServletRequest)
	 */
	public List queryFormData(String sql, String[] parmerValue,
			ListPage listPage, HttpServletRequest request) {

		Map dictionaryCacheMap = new HashMap();// 数据字典缓存

	
		List fiList = listPage.getFields();
		Field temFieldEntiy = null;
		/**
		 * 定义数据库操作对象
		 */
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = null;
		List listPageData = new ArrayList();
		/**
		 * 获取连接池
		 */
		ComboPooledDataSource pool = findPoolByDataSourceId(listPage.getId());

		try {
			conn = pool.getConnection();
			pstm = conn.prepareStatement(sql);
			if (parmerValue != null) {
				for (int i = 0; i < parmerValue.length; i++) {
					pstm.setObject(i + 1, parmerValue[i]);
				}
			}

			rs = pstm.executeQuery();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();// 放回连接池
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/**
			 * jdbc操作类 切换至初始化根连接池
			 */
			try {
				jdbcTemplate.setDataSource(initDataSource);
			} catch (Exception e) {
				// TODO: handle exception
				log.error("切换初始化连接池 失败...");
			}

		}
		return listPageData;
	}

	

	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.dao.SqlDicDao#findPoolByDataSourceId(java.lang.String)
	 */
	public ComboPooledDataSource findPoolByDataSourceId(String dataSourceId) {
		// TODO Auto-generated method stub
	
		// 1 获取缓存中的连接池
		Map poolsMap = systemFrameService.load_connectPools("true");
		// 2 数据源ID查找对应的连接池
		ComboPooledDataSource connectPool = null;
		try {
			if (dataSourceId != null) {
				if (poolsMap.get(dataSourceId) != null) {
					connectPool = (ComboPooledDataSource) poolsMap
							.get(dataSourceId);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("获取表单对应数据源失败。。。");
		}
		return connectPool;
	}

	

	/* (non-Javadoc)
	 * @see com.zxt.framework.dictionary.dao.SqlDicDao#queryForExport(java.lang.String, java.lang.String, java.lang.String[], com.zxt.compplatform.formengine.entity.view.ListPage, javax.servlet.http.HttpServletRequest)
	 */
	public List queryForExport(String formId, String sql, String[] conditions,
			ListPage listPage, HttpServletRequest request) {
		ComboPooledDataSource pool = null;
		try {
			pool = findPoolByDataSourceId(formId);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (pool != null) {
			jdbcTemplate.setDataSource(pool);
		}
		List list = new ArrayList();
		try {
			list = jdbcTemplate.queryForList(sql, conditions);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			/**
			 * 切换初始化连接池
			 */
			jdbcTemplate.setDataSource(initDataSource);
		}

		return list;
	}
	/**
	 * 找出sqldic总条数
	 */
	public int findTotalRows(String queryString) {
		// TODO Auto-generated method stub
		Map map = jdbcTemplate.queryForMap(queryString);
		int countoo = (Integer)map.get("countoo");
		return countoo;
	}

	@Override
	public List findAllByPage(String paramString, int page, int rows) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		list = jdbcTemplate.queryForList(paramString);
		return list;
	}
	
	public List queryForList(String sql,Object[] conditions) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		list = jdbcTemplate.queryForList(sql, conditions);
		return list;
	}
	public Map queryForObject(String sql,Object[] conditions) {
		Map map = jdbcTemplate.queryForMap(sql, conditions);
		return map;
	}

	@Override
	public boolean addSql(SqlObj sqlObj, String params) {
		// TODO Auto-generated method stub
		String sql = "insert into eng_dic_sql(sqlid,sqlname,sqlvalue,sqltype,params,remark,datasourceid) values (?,?,?,?,?,?,?)";
		Object[] conditions = new Object[]{sqlObj.getSqldicid(),sqlObj.getSqldicname(),sqlObj.getSqldic_expression(),
										sqlObj.getSqldic_type(),params,sqlObj.getSqldic_remark(),sqlObj.getSqldic_dataSourceid()};
		try {
			int b = jdbcTemplate.update(sql, conditions);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public boolean updateSql(SqlObj sqlObj, String params) {
		// TODO Auto-generated method stub
		String sql = "update ENG_DIC_SQL set sqlname=?,sqlvalue=?,sqltype=?,params=?,datasourceid=?,remark=? where sqlid=?";
		Object[] conditions = new Object[]{sqlObj.getSqldicname(),sqlObj.getSqldic_expression(),
										sqlObj.getSqldic_type(),params,sqlObj.getSqldic_dataSourceid(),sqlObj.getSqldic_remark(),sqlObj.getSqldicid()};
		try {
			int b = jdbcTemplate.update(sql, conditions);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	@Override
	public boolean update(String sql, Object[] conditions) {
		// TODO Auto-generated method stub
		try {
			int b = jdbcTemplate.update(sql, conditions);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Object execteSqlDic(SqlObj sqlobj,HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Object returnvalue = null;
		if(!"".equals(sqlobj.getSqldic_dataSourceid())){
			try {
				Map poolsMap = systemFrameService.load_connectPools("true");
				// 3 数据源ID查找对应的连接池
				ComboPooledDataSource connectPool = null;
				connectPool = (ComboPooledDataSource) poolsMap.get(sqlobj.getSqldic_dataSourceid());
				jdbcTemplate.setDataSource(connectPool);
				String sql = sqlobj.getSqldic_expression();
				List paramlist = sqlobj.getSqlParam();
				Object[] params = null;
				if(paramlist!=null && paramlist.size()>0){
					params = new Object[paramlist.size()];
					for (int i = 0; i < paramlist.size(); i++) {
						Param param = (Param) paramlist.get(i);
						if(param.getType().equals("1")){//手工输入
							params[i] = param.getValue();
						}else if(param.getType().equals("2")){//系统变量
							params[i] = request.getSession().getAttribute(param.getValue());
						}else if(param.getType().equals("3")){//变量
							params[i] = request.getParameter(param.getValue());
						}
					}
				}
				
				switch (Integer.parseInt(sqlobj.getSqldic_type())) {
					case 1://查询
						returnvalue = jdbcTemplate.queryForList(sql, params);
						String jsonarr = JSONArray.fromObject(returnvalue).toString();
						response.getWriter().print(jsonarr);
						break;
					case 2://增删改
						returnvalue = jdbcTemplate.update(sql, params);
						response.getWriter().print(returnvalue);
						break;
					case 3://存储过程
						//returnvalue = jdbcTemplate.queryForList(sql, params);
						Map inparams = new HashMap();
						Map outparams = new HashMap();
						for (int i = 0; i < paramlist.size(); i++) {
							Param param = (Param) paramlist.get(i);
							if(param.getType().equals("1")){//手工输入
								inparams.put((i+1)+"", param.getValue());
							}else if(param.getType().equals("2")){//系统变量
								inparams.put((i+1)+"", request.getSession().getAttribute(param.getValue()));
							}else if(param.getType().equals("3")){//变量
								inparams.put((i+1)+"", request.getParameter(param.getValue()));
							}else if(param.getType().equals("4")){//输出参数
								outparams.put((i+1)+"", java.sql.Types.VARCHAR);
							}
						}
						outparams = execProcedure2(sql, inparams, outparams);
						String jsonstr = JSONObject.fromObject(outparams).toString();
						response.getWriter().print(jsonstr); 
						break;
					case 4://创建触发器
						//returnvalue = jdbcTemplate.queryForList(sql, params);
						break;
					default:
						break;
				}
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				jdbcTemplate.setDataSource(initDataSource);
			}
		}
		
		return returnvalue;
	}
	
	/**
	 * 读取存储过程
	 * 
	 * @param sql:调用存储过程的格式为{call
	 *            过程名称(?,?)}[注意?为输入参数或者为输出参数]
	 * @param inparams:过程输入参数(Map的key为参数所在的第几个？号,value为输入参数值)
	 * @param
	 *            outparams:过程输出参数(Map的key为参数所在的第几个？,value为输出参数值为输出参数类型比如java.sql.Types.BIGINT)
	 * @return 输出参数(Map的key为参数所在的第几个？,value为输出参数值)
	 */

	public Map execProcedure2(String sql, final Map inparams, final Map outparams) {

		try {

			jdbcTemplate.execute(sql,
					new CallableStatementCallback() {
						public Object doInCallableStatement(CallableStatement cs)
								throws SQLException, DataAccessException {
							java.util.Iterator inIterator = inparams.entrySet()
									.iterator();// 设置输入参数
							while (inIterator.hasNext()) {
								java.util.Map.Entry inEntry = (java.util.Map.Entry) inIterator
										.next();
								cs.setString(Integer.parseInt(inEntry.getKey()
										.toString()), inEntry.getValue()
										.toString());
							}
							java.util.Iterator outIterator = outparams
									.entrySet().iterator();// 注册输出参数
							while (outIterator.hasNext()) {
								java.util.Map.Entry outEntry = (java.util.Map.Entry) outIterator
										.next();
								cs.registerOutParameter(
										Integer.parseInt(outEntry.getKey()
												.toString()), Integer
												.parseInt(outEntry.getValue()
														.toString()));
							}
							cs.execute();// 执行存储过程
							java.util.Iterator outIteratorContent = outparams
									.entrySet().iterator();
							while (outIteratorContent.hasNext()) {// 将运行存储过程的数据重新放到Map中
								java.util.Map.Entry outEntry = (java.util.Map.Entry) outIteratorContent
										.next();
								outparams.put(outEntry.getKey().toString(), cs
										.getObject(Integer.parseInt(outEntry
												.getKey().toString())));
							}
							return null;
						}
					});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			
		}
		return outparams;
	}
}
