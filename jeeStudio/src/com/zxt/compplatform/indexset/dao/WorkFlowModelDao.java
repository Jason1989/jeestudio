package com.zxt.compplatform.indexset.dao;

import java.util.List;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public interface WorkFlowModelDao {
	
	/**
	 * 从数据源中查询数据
	 * @param sql
	 * @param parmerValue
	 * @param pool 数据连接
	 * @return
	 */
	public List queryFormData(String sql, String[] parmerValue,
			ComboPooledDataSource pool);
}
