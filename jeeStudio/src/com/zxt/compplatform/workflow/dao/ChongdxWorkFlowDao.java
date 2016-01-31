package com.zxt.compplatform.workflow.dao;

public interface ChongdxWorkFlowDao {
	/**
	 * 重定向工作项 workitemId 工作项id
	 * 
	 * @param performer
	 *            要重定向给的人
	 * @param operator
	 *            操作人
	 * @return boolean true 成功 false失败
	 */
	public boolean Chongdingxiang(String performer, int userid, int workitemId);
}
