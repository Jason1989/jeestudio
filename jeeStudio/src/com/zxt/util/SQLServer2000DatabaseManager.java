package com.zxt.util;

import antlr.collections.List;

import com.mysql.jdbc.Connection;

public class SQLServer2000DatabaseManager implements DatabaseManager {
	/**
	 * 获取所有的数据库中的表(table) sqlserver2000
	 * @param conn
	 * @return
	 */
	public List getAllTable(Connection conn) {
		// 2000
		String sql2000 = "SELECT TableName=D.NAME,TableExplain=CASE WHEN A.COLORDER=1 THEN ISNULL(F.VALUE,' ') ELSE ' ' END,   "
				+ "ColumnName=A.NAME,IsIdentity = COLUMNPROPERTY( A.ID,A.NAME,'ISIDENTITY'),   "
				+ "IsPrimaryKey=CASE WHEN EXISTS(Select 1 FROM SYSOBJECTS Where XTYPE='PK ' AND PARENT_OBJ=A.ID AND NAME IN ("
				+ "SELECT NAME FROM SYSINDEXES Where INDID IN(   "
				+ "SELECT INDID FROM SYSINDEXKEYS Where ID = A.ID AND COLID=A.COLID))) THEN 1 ELSE 0 END,"
				+ "ColumnType=B.NAME,A.LENGTH,A.ISNULLABLE,DefaultValue=ISNULL(E.TEXT,' '),ColumnExplain=ISNULL(G.[VALUE],' '),"
				+ "Scale= ISNULL(COLUMNPROPERTY(A.ID,A.NAME,'SCALE '),0)   "
				+ "FROM SYSCOLUMNS A LEFT JOIN SYSTYPES B ON A.XUSERTYPE=B.XUSERTYPE"
				+ "INNER JOIN SYSOBJECTS D ON A.ID=D.ID AND D.XTYPE='U ' AND D.NAME<>'DTPROPERTIES '"
				+ "LEFT JOIN SYSCOMMENTS E ON A.CDEFAULT=E.ID LEFT JOIN  sysproperties  G    "
				+ "ON A.ID=G.id  AND A.COLID=G.smallid  LEFT JOIN  sysproperties  F    "
				+ "ON D.ID=F.id  AND F.smallid=0 order by 1,IsPrimaryKey desc ";
		
		return null;
	}

	public List getAllView(Connection conn) {
		return null;
	}

	public List getColumnTypes(String dbType) {
		// TODO Auto-generated method stub
		return null;
	}

}
