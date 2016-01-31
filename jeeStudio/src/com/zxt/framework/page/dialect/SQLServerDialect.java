package com.zxt.framework.page.dialect;

/**
 * Title: Dialect Description: SQLServerDialect Create DateTime: 2010-9-29
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public class SQLServerDialect extends Dialect {
	/**获取默认列
	 * 
	 * @return
	 */
	public String getNoColumnsInsertString() {
		return "default values";
	}

	/**
	 * 获取
	 * 
	 * @param sql
	 * @return
	 */
	static int getAfterSelectInsertPoint(String sql) {
		int selectIndex = sql.toLowerCase().indexOf("select");
		final int selectDistinctIndex = sql.toLowerCase().indexOf(
				"select distinct");
		return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
	}
	/**
	 * 获取分页字符串
	 * 
	 * @param sql
	 * @return
	 */
	public String getLimitString(String querySelect, String tableName,
			String orderColumn, boolean hasOffset, int offset/* 页大小 */,
			int limit/* 页码 */, int allFlag) {

		String limitString = "SELECT TOP "
				+ offset
				+ // 页大小 *
				" * FROM "
				+ tableName
				+ //
				" WHERE (" + orderColumn + " >=" + "          (SELECT MAX("
				+ orderColumn + ")" + "         FROM (SELECT TOP "
				+ ((limit - 1) * offset + 1) + " " + orderColumn
				+ "                 FROM " + tableName
				+ "                 ORDER BY " + orderColumn + ") AS T))"
				+ " ORDER BY " + orderColumn;
		return limitString;
	}

}
