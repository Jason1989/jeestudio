package com.zxt.compplatform.formengine.dao.impl;

import java.io.BufferedReader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import a.e;
import bsh.This;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.dao.ComponentsDao;
import com.zxt.compplatform.formengine.entity.dataset.FieldDefVO;
import com.zxt.compplatform.formengine.entity.dataset.TableVO;
import com.zxt.compplatform.formengine.entity.view.EditColumn;
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.Field;
import com.zxt.compplatform.formengine.entity.view.ListColumn;
import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.PagerEntiy;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.entity.view.ViewColumn;
import com.zxt.compplatform.formengine.entity.view.ViewPage;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.compplatform.formengine.util.PagerUtil;
import com.zxt.framework.common.util.SQLBlobUtil;
import com.zxt.framework.dictionary.entity.DataDictionary;
import com.zxt.framework.dictionary.service.DataDictionaryService;
import com.zxt.framework.dictionary.service.IDataDictionaryService;
import com.zxt.framework.jdbc.ZXTJDBCTemplate;

public class ComponentsDaoImpl implements ComponentsDao {
	private JdbcTemplate jdbcTemplate;
	private IDataDictionaryService iDataDictionaryService;
	/**
	 * 基础数据源
	 */
	private DataSource initDataSource;
	private SystemFrameService systemFrameService;


	/**   <property name="zxtJDBCTemplate"><ref bean="zxtJDBCTemplate"/></property>
	 * zxtJDBC
	 */
	private ZXTJDBCTemplate zxtJDBCTemplate;

	public ZXTJDBCTemplate getZxtJDBCTemplate() {
		return zxtJDBCTemplate;
	}
	public void setZxtJDBCTemplate(ZXTJDBCTemplate zxtJDBCTemplate) {
		this.zxtJDBCTemplate = zxtJDBCTemplate;
	}
	
	private static final Logger log = Logger.getLogger(ComponentsDaoImpl.class);

	/**
	 * 单行删除
	 */
	public int deleteData(String sql, String[] pramers, ListPage listPage) {
		ComboPooledDataSource pool = findPoolByFormId(listPage.getId());
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

		/**
		 * 加入 系统操作日志功能
		 * 数据库表：ENG_SYSTEM_ACTIONLOG
		 */				
		if("true".equals(listPage.getIsSystemActionlog())){
			String operDetail="操作SQL语句："+sql+"\t\n 删除值为：";//用于记录日志记录的详细信息
			for(int i=0;i<pramers.length;i++){
				operDetail+=pramers[i]+"\t";
			}
			this.addlogSave(listPage.getId(),"删除",operDetail);
		}
		
		return updateCount;
	}
   /**
    * GUOWEIXIN  2013-2-28
    * 日志记录保存
    * 功能：用于查询用户对指定表单的增删
    * parmas:操作的对象类型  1:表单ID，2表单名称，3操作动作（删除，添加）4，操作详细数据信息
    */
	public void addlogSave(String operFormid,String operAction,String operDetail){
		String loginName = (String) ServletActionContext.getRequest().getSession().getAttribute("lcv");// 登录人帐号
		if(loginName==null)loginName="配置添加";
		String rolename=(String)ServletActionContext.getRequest().getSession().getAttribute("stwitchRole");//登录人角色		
		if(rolename==null)rolename="配置添加";
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 当前操作时间
		String operDate = dateformat.format(new Date());
		String operip=(String)ServletActionContext.getRequest().getSession().getAttribute("ipAddress");//当前登录IP
		if(operip==null)operip="配置添加";
		
		String systemName=(String) ServletActionContext.getRequest().getSession().getAttribute("systemName");
		if(systemName==null) systemName="后台系统";
		String operFormname=""; //操作表单名称根据operFormid得到
		String operFormnameSQL="select FO_NAME from ENG_FORM_FORM WHERE FO_ID='"+operFormid+"'";//SQL语句
		List listFormName=jdbcTemplate.queryForList(operFormnameSQL);
		//操作表单名称根据operFormid得到
		if(listFormName.size()!=0){
			Map map = (Map) listFormName.get(0);
			operFormname=(String) map.get("fo_name");
		}
		int result = zxtJDBCTemplate.judge();
		Object[] obj=null;
		String insertSql=null;
		
		if(result==1){
			Long long1=System.currentTimeMillis(); //oracle ID是根据时间自动生成。
//			insertSql = "insert into ENG_SYSTEM_ACTIONLOG(loginName,roleName,operDate,operFormname,operFormid,operAction,operip) " +
//					"values(?,?,?,?,?,?,?);";
//			obj=new Object[] {long1,
//					log.getUser_name(), log.getUser_id(), log.getLog_ip(),
//					log.getLog_time(), log.getUser_loginName(),
//					log.getUser_roleName() };
		}
		else if(result==2){//sqlserver ID是自增的
			
			insertSql = "insert into ENG_SYSTEM_ACTIONLOG(loginName,roleName,operDate,operFormname,operFormid,operAction,operip,operDetail,systemmodule) " +
			"values(?,?,?,?,?,?,?,?,?);";
			obj=new Object[] {
					loginName, rolename,operDate,
					operFormname,operFormid,operAction,operip,operDetail,systemName};
		}
		else{		
			
		}
		 jdbcTemplate.update(insertSql,obj);
		
	}
	
	/**
	 * 单行保存
	 */
	public int dynamicSave(String sql, String[] endParmer, EditPage editPage,
			Map<String, EditColumn> editCloumnMap, List<Param> list) {
		// TODO Auto-generated method stub
		/**
		 * 切换连接池
		 */
		ComboPooledDataSource pool = findPoolByFormId(editPage.getId());
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
		String fieldDataType;// 字段类型
		Param param;
		EditColumn editColumn;
		String filedName;
		String value;
		int count=0;//用于判断SQL语句执行返回值
		String operDetail="操作SQL语句："+sql+"\t\n";//用于记录日志记录的详细信息
			
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
			pstm = conn.prepareStatement(sql);
			if (endParmer != null) {
				for (int i = 0; i < endParmer.length; i++) {
					value = endParmer[i];
					param = list.get(i);
					filedName = param.getKey();
					editColumn = editCloumnMap.get(filedName);
					fieldDataType = editColumn.getFieldDataType();
					operDetail+=filedName+":"+value+"\t";	
					if ("".equals(value)) {
						if ("datetime".equals(fieldDataType)) {
							pstm.setObject(i + 1, null);
						} else if ("decimal".equals(fieldDataType)) {
							pstm.setBigDecimal(i + 1, null);
						} else if ("numeric".equals(fieldDataType)) {
							pstm.setObject(i + 1, null);
						} else {
							pstm.setObject(i + 1, value);
						}
					} else {
						pstm.setObject(i + 1, value);
					}

					// if ((i+1)==5) {
					// pstm.setBlob(i+1, Hibernate.createBlob("sss".getBytes())
					// );
					// }else{
					// }
				}
			}

			 count = pstm.executeUpdate();
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
		/**
		 * 加入 系统操作日志功能
		 * 数据库表：ENG_SYSTEM_ACTIONLOG
		 */		
		if("true".equals(editPage.getIsSystemActionlog())){
			String operAction="";//用于记录日志记录的动作 添加/删除/编辑
			StringBuffer sBuffer=new StringBuffer("update");
			boolean bool=sql.contentEquals(sBuffer);		
			if(bool){
					operAction="编辑数据";
			}else 
					operAction="添加数据";
			
			this.addlogSave(editPage.getId(),operAction,operDetail);
		}
		
		return count;
	}

	/**
	 * 加载单行数据
	 */
	public EditPage loadEditPage(EditPage editPage, String[] parmers) {
		// TODO Auto-generated method stub

		ComboPooledDataSource pool = findPoolByFormId(editPage.getId());
		if (pool != null) {
			jdbcTemplate.setDataSource(pool);
		}

		try {
			Object[] array = new Object[parmers.length];
			for (int i = 0; i < array.length; i++) {
				array[i] = parmers[i];
			}
			List resultList = jdbcTemplate.queryForList(editPage.getFindSql(),
					array);
			if (resultList!=null&&resultList.size()!=0) {
				Map map = (Map) resultList.get(0);
				String data = "";
				String key = "";
				EditColumn editColumn = null;
				for (int i = 0; i < editPage.getEditColumn().size(); i++) {
					editColumn = (EditColumn) editPage.getEditColumn().get(i);
					String tablename = editColumn.getTablename();
					String keytable = editPage.getInsertTableName();

					key = editColumn.getName();
					if (map.get(key) == null) {
						key = editColumn.getTablename() + "__" + key;
						if (map.get(key) == null) {
							if (StringUtils.isBlank(tablename)) {
								key = editPage.getInsertTableName() + "__"
										+ editColumn.getName();
								if (map.get(key) == null) {
									continue;
								}
							} else {
								continue;
							}
						}
					}
					if (Constant.FORM_FIELD_DATETIME.equals(editColumn
							.getFieldDataType())) {
						data = map.get(key).toString();
						data = data.substring(0, 10);
					} else if (Constant.FORM_FIELD_DATE.equals(editColumn
							.getFieldDataType())) {
						data = map.get(key).toString();
						data = data.substring(0, 10);
					} else {
						data = map.get(key).toString();
					}

					if (StringUtils.isNotBlank(tablename)) {
						if (!StringUtils.equals(tablename, keytable)) {
							editColumn.setReadOnly("true");
							if (editColumn.getTextColumn() != null) {
								editColumn.getTextColumn().setReadOnly(true);
							}
							// String editColumnName = editColumn.getName();
							// if( StringUtils.isNotBlank(editColumnName) ){
							// editColumn.setName("view_" + editColumnName);
							// }
						}
					}

					editColumn.setData(data);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("没有数据。。");
		} finally {
			/**
			 * 切换初始化连接
			 */
			jdbcTemplate.setDataSource(initDataSource);
		}
		return editPage;
	}

	/**
	 * 主键生成
	 */
	public String createIDkey(String tableName, String IDkey) {
		/**
		 * 使用 序列
		 */
		String IDvalue = "";
		IDvalue = new Date().getTime() + "";
		return IDvalue;
	}

	/**
	 * 加载详情页数据
	 * 
	 */
	public ViewPage loadViewPage(String sql, String[] parmer, ViewPage viewPage) {
		// TODO Auto-generated method stub
		try {

			ComboPooledDataSource pool = findPoolByFormId(viewPage.getId());
			if (pool != null) {
				jdbcTemplate.setDataSource(pool);
			}

			List resultList = jdbcTemplate.queryForList(sql, parmer);

			if (resultList.size() > 1) {
				return viewPage;
			}
			if (parmer == null) {
				return viewPage;
			}
			Map map = (Map) resultList.get(0);
			/**
			 * 加载查看页数据
			 */
			ViewColumn viewColumn = null;
			String data = "";
			String key = "";
			for (int i = 0; i < viewPage.getViewColumn().size(); i++) {
				viewColumn = (ViewColumn) viewPage.getViewColumn().get(i);
				key = viewColumn.getName();
				if (map.get(key) == null) {
					continue;
				}
				if (Constant.FORM_FIELD_DATETIME.equals(viewColumn
						.getFieldDataType())) {
					data = map.get(key).toString();
					data = data.substring(0, 10);
				} else if (Constant.FORM_FIELD_DATE.equals(viewColumn
						.getFieldDataType())) {
					data = map.get(key).toString();
					data = data.substring(0, 10);
				} else {
					data = map.get(key).toString();
				}

				((ViewColumn) viewPage.getViewColumn().get(i)).setData(data);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			/**
			 * 切换初始化连接
			 */
			jdbcTemplate.setDataSource(initDataSource);
		}

		return viewPage;
	}

	/**
	 * 加载数据字典
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

		// for (int i = 0; i < resultList.size(); i++) {
		// Object object[]=(Object [])resultList.get(i);
		// map.put(object[0],object[1]);
		// }

		return map;
	}
	/**加载数据字典
	 * @GUOWEIXIN
	 *  数据字典中 设置动态SQL语句的解析和查询功能。
	 *  例如：[]在request范围中取。{}在session范围中取。
	 *  select * from TableName where first='[first]' and second='{second}'
	 */
	public Map loadDynamicDictionary(String sql, String dataSourceID,HttpServletRequest request) {
		 if( sql!=null &&(sql.indexOf("[")>0 || sql.indexOf("{")>0))	{
				String result=ComponentsDaoImpl.dyncJoinStr(sql,request);
				sql=result;
		 }	
		Map map = new HashMap();
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

		// for (int i = 0; i < resultList.size(); i++) {
		// Object object[]=(Object [])resultList.get(i);
		// map.put(object[0],object[1]);
		// }

		return map;
	}
	
	/**
	 * 查找 配置xml
	 * 
	 * @param sql
	 * @return
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

	/**
	 * 列表页数据
	 * 
	 * @param sql
	 * @return
	 */
	public PagerEntiy queryFormData(String sql, String[] parmerValue,
			ListPage listPage, HttpServletRequest request) {

		Map dictionaryCacheMap = new HashMap();// 数据字典缓存
		PagerEntiy pagerEntiy=new PagerEntiy();//分页对象
		int initRowNumber=0;//初始分页号
		
		/**
		 * sql 语句中 结果集对象顺序
		 */
		TableVO tableVO = (TableVO) listPage.getTable().get(0);
		List fieldList = tableVO.getFieldList();
		FieldDefVO fieldDefVO = null;
		/**
		 * 数据字典 ,来自主表字段加载
		 */

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
		ComboPooledDataSource pool =null;
		String strMessage="重要提示：系统缓存未加载，请重新启动服务器/ 与业务 数据库连接发生失败，请检查所在数据库连接";
		try {
			pool= findPoolByFormId(listPage.getId());
			
			if(pool==null){				
				System.out.println(strMessage);	
			}
			conn = pool.getConnection();
			pstm = conn.prepareStatement(sql);
			if (parmerValue != null) {
				for (int i = 0; i < parmerValue.length; i++) {
					pstm.setObject(i + 1, parmerValue[i]);
				}
			}

			rs = pstm.executeQuery();
			Map map = null;
			Map dictionaryMap = null;

			/**
			 * key：字段名 value： 字段值 filedDataType ：字段类型
			 */
			String key = "";
			String value = "";
			String filedDataType = "";
			String[] dictionaryValue = null;
			
			/***
			 * 执行分页操作功能
			 */
			pagerEntiy =PagerUtil.pagger(request);
			
			/**
			 * 遍历结果集
			 */
			while (rs.next()) {
				initRowNumber++;
				if(initRowNumber>pagerEntiy.getStart()&&initRowNumber<=pagerEntiy.getEnd()){
					/*******************************
					 * 加载数据
					 ********************************/
					map = new HashMap();
					for (int j = 0; j < fieldList.size(); j++) {
						/**
						 * 数据列对象
						 */
						fieldDefVO = (FieldDefVO) fieldList.get(j);
						key = fieldDefVO.getToFieldName();
						if (fieldDefVO.getToFieldType() != null) {
							filedDataType = fieldDefVO.getToFieldType()
									.toLowerCase();
						}
						/**
						 * 判断数据类型
						 */
						if (Constant.FORM_FIELD_LONG.equals(filedDataType)) {
							value = rs.getLong(key) + "";
						} else if (Constant.FORM_FIELD_NUMBER.equals(filedDataType)) {
							value = rs.getInt(key) + "";
						} else if (Constant.FORM_FIELD_DATE.equals(filedDataType)) {
							value = rs.getString(key) + "";
							if (value.length() >= 10) {
								value = value.substring(0, 10);
							}
						} else if (Constant.FORM_FIELD_DATETIME
								.equals(filedDataType)) {
							value = rs.getString(key) + "";
							if (value.length() >= 10)
								value = value.substring(0, 10);
						} else if (Constant.FORM_FIELD_TIMESTAMP
								.equals(filedDataType)
								|| (Constant.FORM_FIELD_DATETIME
										.equals(filedDataType))) {
							value = value.substring(0, 10);
						} else {
							value = rs.getString(key);
						}

						/**
						 * 查询数据字典值,来自主表字段的方式
						 */
						for (int i = 0; i < fiList.size(); i++) {
							temFieldEntiy = (Field) fiList.get(i);
							if (key.equals(temFieldEntiy.getDataColumn())) {
								if (temFieldEntiy.getDictionaryId() != null) {
									if (!"".equals(temFieldEntiy.getDictionaryId()
											.trim())) {
										/**
										 * 方法内 调用缓存。
										 */
										if (dictionaryCacheMap.get(temFieldEntiy
												.getDictionaryId()) != null) {
											dictionaryMap = (Map) dictionaryCacheMap
													.get(temFieldEntiy
															.getDictionaryId());
										} else {
											dictionaryMap = findDictionary(temFieldEntiy
													.getDictionaryId());
											dictionaryCacheMap.put(temFieldEntiy
													.getDictionaryId(),
													dictionaryMap);
										}
										if (value == null) {
											value = "";
										}else {
											value=value.trim();
										}
										dictionaryValue = value.split(",");
										if (dictionaryMap != null) {
											if ((dictionaryValue[0] != null)
													&& (dictionaryMap
															.get(dictionaryValue[0]) != null)) {

												for (int k = 0; k < dictionaryValue.length; k++) {
													if (k == 0) {
														value = dictionaryMap.get(
																dictionaryValue[k])
																.toString();
													} else {
														value = value
																+ ","
																+ dictionaryMap
																		.get(
																				dictionaryValue[k])
																		.toString();
													}
												}
											}
										}
									}
								}
							}
						}

						/**
						 * 查询数据字典值
						 */
						map.put(key, value);
					}
					listPageData.add(map);
					/*******************************
					 * 加载数据
					 ********************************/
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(strMessage,e);
			log.debug(strMessage,e);
		} finally {
			try {
				conn.close();// 放回连接池
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
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
		
		pagerEntiy.setResult(listPageData);
		pagerEntiy.setTotal(initRowNumber);
		return pagerEntiy;
	}

	/**
	 * 加载xml配置
	 */
	public String[] load_XMLConfig() {
		// TODO Auto-generated method stub
		String[] array = null;
		try {
			String sql = "select fo_id from eng_form_form ";
			List list = jdbcTemplate.queryForList(sql);
			array = new String[list.size()];
			Map map = null;
			for (int i = 0; i < array.length; i++) {
				map = (Map) list.get(i);
				array[i] = map.get("fo_id").toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return array;
	}

	/**
	 * 查询验证名称
	 */

	public String serchValidate(String id) {
		// TODO Auto-generated method stub
		String sql = "select dv_val_id, dv_name from eng_dic_validation where dv_val_id=? ";
		String value = "";

		if ((id == null) || "".equals(id)) {
			return "";
		}

		try {
			List list = jdbcTemplate.queryForList(sql, new Object[] { id });

			if (list != null) {
				Map map = (Map) list.get(0);
				value = map.get("dv_name").toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return value;
	}

	/**
	 * 查找数据源ID
	 */
	public String findDataSourceId(String formId) {
		// TODO Auto-generated method stub
		String sql = " select do_ds_id from eng_form_dataobject where eng_form_dataobject.do_id=( "
				+ " select fo_do_id from eng_form_form where fo_id=?)";
		jdbcTemplate.setDataSource(initDataSource);
		List list = jdbcTemplate.queryForList(sql, new Object[] { formId });
		String dataSourceId = "-1";

		if (list != null) {
			dataSourceId = ((Map) list.get(0)).get("do_ds_id").toString();
		}
		return dataSourceId;
	}

	/**
	 * 根据表单Id 查找 在缓存中对应的数据源连接池
	 */
	public ComboPooledDataSource findPoolByFormId(String formId) {
		// TODO Auto-generated method stub
		// 1查询数据源ID
		String dataSourceId = findDataSourceId(formId);
		// 2 获取缓存中的连接池
		Map poolsMap = systemFrameService.load_connectPools("true");
		// 3 数据源ID查找对应的连接池
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

	public DataSource getInitDataSource() {
		return initDataSource;
	}

	public void setInitDataSource(DataSource initDataSource) {
		this.initDataSource = initDataSource;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public SystemFrameService getSystemFrameService() {
		return systemFrameService;
	}

	public void setSystemFrameService(SystemFrameService systemFrameService) {
		this.systemFrameService = systemFrameService;
	}

	public Map findDictionary(String dictionaryID) {
		// TODO Auto-generated method stub
		String[] array = null;
		String[] mapString = null;
		Map map = new HashMap();
		DataDictionary dictionary = null;
		/**
		 * 静态数据字典 拆分表达式 ；返回map
		 */
		try {
			dictionary = iDataDictionaryService.findById(dictionaryID);
			if (dictionary.getType().equals(Constant.DICTIONARY_STATIC)) {
				array = dictionary.getExpression().split(",");
				for (int i = 0; i < array.length; i++) {
					mapString = array[i].split("=");
					map.put(mapString[0], mapString[1]);
				}
			} else if (dictionary.getType().equals(Constant.DICTIONARY_DYNAMIC)) {
				map = loadDynamicDictionary(dictionary.getExpression(),
						dictionary.getDataSource().getId());
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("数据字典获取失败.. ");
			e.printStackTrace();
		}
		if (map.get("0")==null) {
			map.put("0", "无");
		}
		return map;
	}

	public IDataDictionaryService getIDataDictionaryService() {
		return iDataDictionaryService;
	}

	public void setIDataDictionaryService(
			IDataDictionaryService dataDictionaryService) {
		iDataDictionaryService = dataDictionaryService;
	}

	/**
	 * author:guoweixin
	 * 根据所传数据源ID得到其数据源连接池
	 * param: dataSourceId(数据源ID) String
	 */
	public ComboPooledDataSource findPoolByDataSourceId(String dataSourceId){
		// 1数据源ID dataSourceId
		// 2 获取缓存中的连接池
		Map poolsMap = systemFrameService.load_connectPools("true");
		// 3 数据源ID查找对应的连接池
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
	
	/***
	 * 保存后SQL GUOWEIXIN
	 * param1:数据源ID
	 * param2:sql语句
	 * param3:字段
	 * param4:request范围取值
	 */
	public List queryForAfterSql(String dataSourceId, String sql, String[] conditions,
			 HttpServletRequest request) {
		ComboPooledDataSource pool = null;
		try {
			pool = findPoolByDataSourceId(dataSourceId);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (pool != null) {
			jdbcTemplate.setDataSource(pool);
		}
		List list = new ArrayList();
		try {
			list=jdbcTemplate.queryForList(sql, conditions);

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
	
	
	@SuppressWarnings("unchecked")
	public List queryForExport(String formId, String sql, String[] conditions,
			ListPage listPage, HttpServletRequest request) {
		Map<String, Map> dictionaryCacheMap = new HashMap<String, Map>();// 数据字典缓存
		Map rowMap = new HashMap();// 业务数据行
		Map dictionaryMap=new HashMap();//数据字典
		
		List<Field> fields = listPage.getFields();// 数据列
		String fieldName="";//字段列名
		String fieldValue="";//字段列值
		
		Field temField = null;

		ComboPooledDataSource pool = null;
		try {
			pool = findPoolByFormId(formId);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (pool != null) {
			jdbcTemplate.setDataSource(pool);
		}
		List list = new ArrayList();
		try {
			list = jdbcTemplate.queryForList(sql, conditions);
			/**
			 * 加载数据字典
			 */
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					rowMap = (Map) list.get(i);//获取数据行
					for (int j = 0; j < fields.size(); j++) {//获取每行数据 每列的数据字典
						temField = (Field) fields.get(j);
						if (temField.getDictionaryId()!=null&&!"".equals(temField.getDictionaryId())) {
							fieldName=temField.getDataColumn();
							
							if (dictionaryCacheMap.get(temField
									.getDictionaryId()) != null) {
								dictionaryMap =  dictionaryCacheMap
										.get(temField
												.getDictionaryId());
							} else {
								dictionaryMap = findDictionary(temField
										.getDictionaryId());
								dictionaryCacheMap.put(temField
										.getDictionaryId(),
										dictionaryMap);
							}
						
							if (rowMap.get(fieldName)!=null) {
								fieldValue=rowMap.get(fieldName).toString();
								if (dictionaryMap.get(fieldValue)!=null) {
									fieldValue=dictionaryMap.get(fieldValue).toString();
								}
							}
								
							rowMap.put(fieldName, fieldValue);
						}
						
					}
				}
			}
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

	public DataSource queryForDataSource(String dataSourceId) {
		// TODO Auto-generated method stub
		ComboPooledDataSource connectPool = null;
		try {
			Map poolsMap = systemFrameService.load_connectPools("true");
			connectPool = (ComboPooledDataSource) poolsMap.get(dataSourceId);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return connectPool;
	}

	public List findAppidsInLog(String userId) {
		// TODO Auto-generated method stub
		String sqlxxxx = "select app_id from dbo.t_process_log where user_id = ?";
		List resultList = jdbcTemplate.queryForList(sqlxxxx,
				new Object[] { userId });
		return resultList;
	}
	/**
	 * author:GUOWEIXIN
	 * @param 所传查询出来的SQL语句
	 * @return 拼接成后的SQL语句
	 */
	public static String dyncJoinStr(String sql,HttpServletRequest request){
		String left=sql;
		String right=sql;
		String result=sql;
		int[] leftRequest=new int[10];
		int[] rightRequest=new int[10];
		//两个数组，分别存放值
		if(left.indexOf("[")>0){		
			int z=0;
			int i=left.indexOf("[");
			int j=right.indexOf("]");
			while(true){
				if(i<0)break;
			if(z==0){
				leftRequest[z]=i;
				rightRequest[z]=j;
			}else{
				leftRequest[z]=i+leftRequest[z-1]+1;
				rightRequest[z]=j+rightRequest[z-1]+1;
			}
				
				left=left.substring(i+1,left.length());
				right=right.substring(j+1,right.length());
				z++;
				 i=left.indexOf("[");
				 j=right.indexOf("]");
			}
		}		
		//request
		String[] varRequest=new String[10];//request中查出的值
		String[] replaceRequest=new String[10];//替换 字段的值
		for(int g=0;g<leftRequest.length;g++){
			if(leftRequest[g]==0)break;
			
			String fieldName=result.substring(leftRequest[g]+1,rightRequest[g]);//得到所传的字段名称
			//System.out.println(fieldName);//得到字段名称，用于request.getParameter("");			
			//fieldName="你好中国人";//
			fieldName=request.getParameter(fieldName);
			if(null==fieldName)fieldName="";
			varRequest[g]=fieldName;
			
			String fieldReplace=result.substring(leftRequest[g]+1-1,rightRequest[g]+1);//得到所要替换的内容
			//System.out.println(fieldReplace);//得到字段名称，用于request.getParameter("");
			replaceRequest[g]=fieldReplace;
		}
		
		for(int i=0;i<replaceRequest.length;i++){
			if(replaceRequest[i]!=null && !"".equals(replaceRequest[i])){
				result=result.replace(replaceRequest[i],varRequest[i]);
			}
			
		}
		String leftS=result;
		String rightS=result;
		
		//两个数组，分别存放值 session
		int[] leftSession=new int[10];
		int[] rightSession=new int[10];
		if(leftS.indexOf("{")>0){
			
			int z=0;
			int i=leftS.indexOf("{");
			int j=rightS.indexOf("}");
			while(true){
				if(i<0)break;
			if(z==0){
				leftSession[z]=i;
				rightSession[z]=j;
			}else{
				leftSession[z]=i+leftSession[z-1]+1;
				rightSession[z]=j+rightSession[z-1]+1;
			}
				
				leftS=leftS.substring(i+1,leftS.length());
				rightS=rightS.substring(j+1,rightS.length());
				z++;
				 i=leftS.indexOf("{");
				 j=rightS.indexOf("}");
			}
		}
		//session的
		String[] varSession=new String[10];//Session中查出的值
		String[] replaceSession=new String[10];//Session替换 字段的值
		for(int g=0;g<leftSession.length;g++){
			if(leftSession[g]==0)break;
			
			String fieldName=result.substring(leftSession[g]+1,rightSession[g]);//得到所传的字段名称
			//System.out.println(fieldName);//得到字段名称，用于request.getParameter("");			
			//fieldName="LUCK";//
			try{
			 fieldName=(String)request.getSession().getAttribute(fieldName);
			 if(fieldName==null)fieldName="";
			}catch(Exception e){
				System.out.println("结果："+(String)request.getSession().getAttribute(fieldName));
				log.error(e.getMessage()+" session 的值为="+fieldName);
			}
			 varSession[g]=fieldName;
			
			String fieldReplace=result.substring(leftSession[g]+1-1,rightSession[g]+1);//得到所要替换的内容
			//System.out.println(fieldReplace);//得到字段名称，用于request.getParameter("");
			replaceSession[g]=fieldReplace;
		}
		for(int i=0;i<replaceSession.length;i++){
			if(replaceSession[i]!=null && !"".equals(replaceSession[i])){
				result=result.replace(replaceSession[i],varSession[i]);
			}
			
		}	
		log.info("输出："+result);
		return result;
	}
	
}
