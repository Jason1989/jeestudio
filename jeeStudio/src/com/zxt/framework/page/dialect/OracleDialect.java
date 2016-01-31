package com.zxt.framework.page.dialect;

import java.sql.Types;

import org.hibernate.sql.CaseFragment;
import org.hibernate.sql.DecodeCaseFragment;
import org.hibernate.sql.JoinFragment;
import org.hibernate.sql.OracleJoinFragment;

import com.zxt.framework.common.log.LogHelper;

/**
 * Title: Dialect Description: Oracle Dialect Create DateTime: 2010-9-29
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public class OracleDialect extends Oracle9Dialect {

	private static final LogHelper log = new LogHelper(OracleDialect.class);

	/**
	 * oracle方式
	 */
	public OracleDialect() {
		super();
		log
				.warn("The OracleDialect dialect has been deprecated; use Oracle8iDialect instead");
	}

	public JoinFragment createOuterJoinFragment() {
		return new OracleJoinFragment();
	}

	public CaseFragment createCaseFragment() {
		return new DecodeCaseFragment();
	}

	/**
	 * 获取分页
	 * @param sql
	 * @param hasOffset
	 * @return
	 */
	public String getLimitString(String sql, boolean hasOffset) {

		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (hasOffset) {
			pagingSelect
					.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (hasOffset) {
			pagingSelect.append(" ) row_ ) where rownum_ <= ? and rownum_ > ?");
		} else {
			pagingSelect.append(" ) where rownum <= ?");
		}

		if (isForUpdate) {
			pagingSelect.append(" for update");
		}

		return pagingSelect.toString();
	}

	public String getSelectClauseNullString(int sqlType) {
		switch (sqlType) {
		case Types.VARCHAR:
		case Types.CHAR:
			return "to_char(null)";
		case Types.DATE:
		case Types.TIMESTAMP:
		case Types.TIME:
			return "to_date(null)";
		default:
			return "to_number(null)";
		}
	}

	public String getCurrentTimestampSelectString() {
		return "select sysdate from dual";
	}

	public String getCurrentTimestampSQLFunctionName() {
		return "sysdate";
	}
}
