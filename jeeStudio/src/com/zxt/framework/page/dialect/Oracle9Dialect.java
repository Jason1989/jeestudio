
package com.zxt.framework.page.dialect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: Dialect
 * Description:  Oracle Dialect 
 * Create DateTime: 2010-9-29
 * @author xxl
 * @since v1.0
 * 
 */
public class Oracle9Dialect extends Dialect {

	private static final Logger log = LoggerFactory.getLogger(Oracle9Dialect.class);
	
	/**
	 * 获取分页查询语句
	 */
	public String getLimitString(String querySelect,String tableName,String orderColumn, boolean hasOffset,int pageRows,int currPage, int allFlag) {
/*		querySelect = querySelect.trim();
		boolean isForUpdate = false;
		if (querySelect.toLowerCase().endsWith(" for update")) {
			querySelect = querySelect.substring(0, querySelect.length() - 11);
			isForUpdate = true;
		}

		StringBuffer pagingSelect = new StringBuffer(querySelect.length() + 100);
		if (hasOffset) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		} else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(querySelect);
		if (hasOffset) {
			pagingSelect.append(" ) row_ where rownum <= "+offset*limit+") where rownum_ > "+(offset-1)*limit);
		} else {
			pagingSelect.append(" ) where rownum <= "+offset*limit);
		}

		if (isForUpdate) {
			pagingSelect.append(" for update");
		}
		return pagingSelect.toString();*/
		// 分页查询
		// 处理查询条件
        String tableNameWithAlias = null;
        String tablename = tableName.trim();
        if (tablename.endsWith(" t"))
            tableNameWithAlias = tablename;
        else
            tableNameWithAlias = tablename + " t";
		
        StringBuffer querySQL = new StringBuffer();
        if (orderColumn != null && orderColumn.trim().length() > 0) {
            // 带排序的分页查询
            querySQL.append(" Select * from");
            querySQL.append(" (Select rid from (");
            querySQL.append(" Select row_number() ");
            querySQL.append("over(order by " + orderColumn + " )");
            querySQL.append(" rn,rowid rid from ");
            querySQL.append(tableNameWithAlias);
            querySQL.append(") page2");
            querySQL.append(" where ");
            // 查询当前页，否则查询剩余页
            if (allFlag == 0) {
                querySQL.append(" rn<= " + currPage * pageRows + " and ");
            }
            querySQL.append(" rn> " + (currPage - 1) * pageRows);
            querySQL.append(") page1, " + " (select * from " + tableNameWithAlias + ")" + " page2 ");
            querySQL.append(" Where page2.rowid=page1.rid ");
            querySQL.append(" order by page2." + orderColumn + "  ");// cairuimin070727
        }else {
            // 不带排序的分页查询
            querySQL.append(" select * from ( ");
            querySQL.append(" select rownum rn, page2.* from " + "(select * from " + tableNameWithAlias + ")" + " page2 ");
            // 查询当前页，否则查询剩余页
            if (allFlag == 0) {
                querySQL.append(" where rownum<= " + currPage * pageRows);
            }
            querySQL.append(")");
            querySQL.append(" where rn > " + (currPage - 1) * pageRows);
        }
		return querySQL.toString();
	}

}
