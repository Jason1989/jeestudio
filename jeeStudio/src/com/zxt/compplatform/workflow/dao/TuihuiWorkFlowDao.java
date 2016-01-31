package com.zxt.compplatform.workflow.dao;

public interface TuihuiWorkFlowDao {
	/**
	 * 回退  1 回退
	 * @param workitemId 工作项id
	 */
	public boolean tuihui(int workitemId);
}
