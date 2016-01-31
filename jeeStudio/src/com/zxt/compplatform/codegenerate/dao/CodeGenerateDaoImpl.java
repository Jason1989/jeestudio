package com.zxt.compplatform.codegenerate.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.zxt.compplatform.codegenerate.entity.EngCodeLog;
import com.zxt.framework.common.dao.DAOSupport;

/**
 * 生成代码持久层实现
 * 
 * @author 007
 */
public class CodeGenerateDaoImpl extends DAOSupport implements ICodeGenerateDao {

	/**
	 * 获取表格的列
	 * 
	 * @param tableName
	 * @param packageName
	 * @param className
	 * @param versionId
	 * @return
	 */
	public Map getTableColumn(final String tableName, String packageName,
			String className, String versionId) {
		final Map root = new HashMap();
		final Map switchMap = new HashMap();
		final Set properties = new HashSet();
		final Set typeSet = new HashSet();
		root.put("package", packageName + ".model");
		root.put("class", className);
		root.put("version", versionId);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		root.put("currentTime", sf.format(new Date()));
		Set imports = new HashSet();
		root.put("imports", imports);
		switchMap.put("int", "Integer");
		switchMap.put("varchar2", "String");
		switchMap.put("char", "String");
		switchMap.put("number", "Long");
		this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Connection conn = session.connection();
				ResultSet rs = conn.createStatement().executeQuery(
						"select * from " + tableName);
				int count = rs.getMetaData().getColumnCount();
				for (int i = 0; i < count; i++) {
					Map columnName = new HashMap();
					columnName.put("name", rs.getMetaData()
							.getColumnName(i + 1));
					columnName.put("type", switchMap.get(rs.getMetaData()
							.getColumnTypeName(i + 1).toLowerCase()));
					typeSet.add(switchMap.get(rs.getMetaData()
							.getColumnTypeName(i + 1)));
					properties.add(columnName);
				}
				return null;
			}
		});
		if (typeSet.contains("Date")) {
			Map importsName = new HashMap();
			importsName.put("name", "java.sql.Date");
			imports.add(importsName);
		}
		root.put("properties", properties);
		return root;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.codegenerate.dao.ICodeGenerateDao#getCodeLogList(java.lang.String)
	 */
	public List getCodeLogList(final String formId) {
		List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						String hql = "from EngCodeLog where codeFormsId =? Order By codeVersionId desc";
						return session.createQuery(hql).setString(0, formId)
								.list();
					}

				});
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.codegenerate.dao.ICodeGenerateDao#getBpTCodeLog(java.lang.String,
	 *      java.lang.String)
	 */
	public EngCodeLog getBpTCodeLog(String codeFormsId, String codeVersionId) {
		EngCodeLog log = new EngCodeLog(codeFormsId, codeVersionId);
		EngCodeLog bpTCodeLog = (EngCodeLog) this.getHibernateTemplate().get(
				EngCodeLog.class, log);
		return bpTCodeLog;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.codegenerate.dao.ICodeGenerateDao#saveBpTCodeLog(com.zxt.compplatform.codegenerate.entity.EngCodeLog)
	 */
	public void saveBpTCodeLog(EngCodeLog bpTCodeLog) {
		this.getHibernateTemplate().save(bpTCodeLog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zxt.compplatform.codegenerate.dao.ICodeGenerateDao#getEngCodeLogVersionId(java.lang.String)
	 */
	public String getEngCodeLogVersionId(final String formId) {
		List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						// String oracleSql = "select code_version_id from
						// (select code_version_id from ENG_CODE_LOG where
						// CODE_FORMS_ID = ? order by to_number(code_version_id)
						// desc) where rownum = 1";
						String sqlServer = " select code_version_id from ENG_CODE_LOG where CODE_FORMS_ID = ? order by code_version_id desc ";

						return session.createSQLQuery(sqlServer).setString(0,
								formId).list();
					}
				});
		if (list != null && list.size() > 0) {
			String versionString = list.get(0).toString();
			int b2 = Integer.parseInt(versionString);
			b2++;
			return "" + b2;
		} else {
			return "1";
		}

	}

	public static void main(String[] args) {
		float test = 1.0f;
		System.out.println();
	}
}
