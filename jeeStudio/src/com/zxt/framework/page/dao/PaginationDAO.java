package com.zxt.framework.page.dao;

import java.util.List;

import javax.sql.DataSource;

import com.zxt.framework.jdbc.ZXTJDBCDaoSupport;
import com.zxt.framework.page.entity.PaginationEntity;
/**
 * Title: CacheInterface
 * Description:  PaginationDAO JDBC分页DAO
 * Create DateTime: 2010-9-29
 * @author xxl
 * @since v1.0
 */
public class PaginationDAO extends  ZXTJDBCDaoSupport{

	public PaginationDAO() {}
	public int getAllRows() {
		return 1;
	}

	public int getRowsPerPage() {
		return 1;
	}
	/**
	 * 获取分页实体
	 * @param strSql
	 * @param page
	 * @param sTableName
	 * @param orderColumn
	 * @param currentDataSource
	 * @return
	 */
	public PaginationEntity getPageBySql(String strSql, PaginationEntity page, String sTableName, String orderColumn,DataSource currentDataSource) {
		String tableNameWithAlias = null;
		String tablename = sTableName.trim();
		if (tablename.endsWith("t"))
			tableNameWithAlias = tablename;
		else {
			tableNameWithAlias = tablename + " t";
		}
		String condition = " 1 = 1 ";
		super.setDataSource(currentDataSource);
		if (page.getAllflag() == 1) {// 不分页查询
			String querySQL = "select * from " + tableNameWithAlias + " where " + condition;

			if ((orderColumn != null) && (orderColumn.trim().length() > 0)) {
				querySQL = querySQL + " order by " + orderColumn;
			}
			page.setQuerySQL(querySQL);
		} else {
			/**
			 * 分页查询语句
			 */
			String pageQuery = "select /*+index_FFS()*/ count(*) from " + tableNameWithAlias + " where  " + condition;
			int rowCount =super.findTotalRows(pageQuery);//总行数
			page.setTotalrows(rowCount);
			int currPage = page.getCurrpage();
			int pageRows = page.getRows();
			if (pageRows < 1)
				pageRows = 20;
			page.setTotalpages((rowCount % pageRows == 0) ? rowCount / pageRows : rowCount / pageRows + 1);

			if (currPage > page.getTotalpages()) {
				currPage = page.getTotalpages();
				page.setCurrpage(currPage);
			}
			/*int offset= (currPage - 1) * pageRows;
			int limit=currPage * pageRows;*/
			String querySql = super.getDialect().getLimitString(strSql, sTableName,orderColumn,true,pageRows,currPage,page.getAllflag());
			page.setQuerySQL(querySql);
		}
		/**
		 * 获取查询结果集
		 */
		List result =super.findToMap(page.getQuerySQL());
		int rows = result.size();
		if (page.getAllflag() == 1) {
			page.setCurrpage(1);
			page.setRows(rows);
			page.setTotalpages(1);
			page.setTotalrows(rows);
		}
		page.setResult(result);
		return page;
	}


}
