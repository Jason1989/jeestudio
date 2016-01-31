package com.zxt.framework.page.example;

import com.zxt.framework.page.entity.PaginationEntity;

public class TestService {

	private TestDAO testDAO = new TestDAO();

	/**
	 * 分页dao层
	 * @param strSql
	 * @param page
	 * @param entityClass
	 * @return
	 */
	public PaginationEntity getEntityByPage(String strSql,
			PaginationEntity page, Class entityClass) {
		String sTableName = "ENG_FORM_DATASOURCE";
		String orderColumn = "DS_ID";
		return testDAO.getEntityByPage(strSql, page, sTableName, orderColumn,
				entityClass);
	}
}
