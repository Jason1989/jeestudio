package com.zxt.compplatform.workflow.dao;

public interface ChuangjianWorkFlowDao {
	/**
	 * 工作流启动
	 * @param processid 工作流id
	 * @param userid 用户id
	 * @param obj   参数组
	 * @return
	 */
	public boolean chuangjian(String processid, int userid, Object[][] obj);
}
