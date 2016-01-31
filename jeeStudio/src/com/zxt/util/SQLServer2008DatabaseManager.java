package com.zxt.util;

import antlr.collections.List;

import com.mysql.jdbc.Connection;

public class SQLServer2008DatabaseManager implements DatabaseManager {

	public List getAllTable(Connection conn) {
		/**
		 * 获取所有的数据库中的表(table) sqlserver2008
		 * @param conn
		 * @return
		 */
		String sql = "SELECT TableName=D.NAME,TableExplain=CASE WHEN A.COLORDER=1 THEN ISNULL(F.VALUE,' ') ELSE ' ' END,"
				+ "ColumnName=A.NAME,IsIdentity = COLUMNPROPERTY( A.ID,A.NAME,'ISIDENTITY '),   "
				+ "IsPrimaryKey=CASE WHEN EXISTS(Select 1 FROM SYSOBJECTS Where XTYPE='PK ' AND PARENT_OBJ=A.ID AND NAME IN (   "
				+ "SELECT NAME FROM SYSINDEXES Where INDID IN(   "
				+ "SELECT INDID FROM SYSINDEXKEYS Where ID = A.ID AND COLID=A.COLID))) THEN 1 ELSE 0 END,   "
				+ "ColumnType=B.NAME,A.LENGTH,A.ISNULLABLE,DefaultValue=ISNULL(E.TEXT,' '),ColumnExplain=ISNULL(G.[VALUE],' '),   "
				+ "Scale= ISNULL(COLUMNPROPERTY(A.ID,A.NAME,'SCALE '),0)   "
				+ "FROM SYSCOLUMNS A LEFT JOIN SYSTYPES B ON A.XUSERTYPE=B.XUSERTYPE   "
				+ "INNER JOIN SYSOBJECTS D ON A.ID=D.ID AND D.XTYPE='U ' AND D.NAME<>'DTPROPERTIES '  "
				+ "		LEFT JOIN SYSCOMMENTS E ON A.CDEFAULT=E.ID LEFT JOIN sys.extended_properties  G    "
				+ "ON A.ID=G.major_id  AND A.COLID=G.minor_id  LEFT JOIN sys.extended_properties  F    "
				+ "ON D.ID=F.major_id  AND F.minor_id=0 order by 1,IsPrimaryKey desc  ";
		return null;
	}

	public List getAllView(Connection conn) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.zxt.util.DatabaseManager#getColumnTypes(java.lang.String)
	 */
	public List getColumnTypes(String dbType) {
		String sql = "select * from  systypes ";
		return null;
	}

}
