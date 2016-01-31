package com.zxt.compplatform.workflow.dao;

public interface ChaosongWorkFlowDao {
	/**
	 * 抄送
	 * 
	 * @param workitemId
	 * @param userid
	 * @param touserid
	 *            抄送的人组
	 * @return
	 */
	public boolean copyWork(int workitemId, int userid, int[] touserid);
}
