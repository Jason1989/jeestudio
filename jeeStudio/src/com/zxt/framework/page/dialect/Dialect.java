package com.zxt.framework.page.dialect;

import com.zxt.framework.common.log.LogHelper;

/**
 * Title: Dialect Description: Dialect Create DateTime: 2010-9-29
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public abstract class Dialect {

	private static final LogHelper log = new LogHelper(Dialect.class);
	/**
	 * 获取分页sql
	 * @param querySelect
	 * @param tableName
	 * @param orderColumn
	 * @param hasOffset
	 * @param offset
	 * @param limit
	 * @param allFlag
	 * @return
	 */
	public abstract String getLimitString(String querySelect, String tableName,
			String orderColumn, boolean hasOffset, int offset/* 页大小 */,
			int limit/* 页码 */, int allFlag);

	public boolean forceLimitUsage() {
		return false;
	}

}
