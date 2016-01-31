package com.zxt.framework.page.example;

import javax.sql.DataSource;

import com.zxt.framework.jdbc.ZXTJDBCDataSource;
import com.zxt.framework.page.dao.PaginationDAO;
import com.zxt.framework.page.dialect.Dialect;
import com.zxt.framework.page.dialect.Oracle9Dialect;
import com.zxt.framework.page.entity.PaginationEntity;

public class TestDAO extends PaginationDAO {
	/**
	 * 获取实体页面
	 */
	public PaginationEntity getEntityByPage(String strSql,PaginationEntity page, String sTableName, String orderColumn,
			Class entityClass) {
		try {
			Dialect dialect = new Oracle9Dialect();
			//Dialect dialect = new SQLServerDialect();
			//DataSource datasource=new ZXTJDBCDataSource("net.sourceforge.jtds.jdbc.Driver","jdbc:jtds:sqlserver://localhost:1433/zxt","sa","sa");
			DataSource datasource=new ZXTJDBCDataSource("oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:@192.168.0.201:1521:zxt","zxt_plat_1008","zxt");
			setDialect(dialect);
			return super.getPageBySql(strSql, page, sTableName, orderColumn,datasource);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
