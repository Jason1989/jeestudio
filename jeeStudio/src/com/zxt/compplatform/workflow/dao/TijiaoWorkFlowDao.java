package com.zxt.compplatform.workflow.dao;

public interface TijiaoWorkFlowDao {
	/**
	 * 提交
	 * @param userid 用户id 组织机构中可以得到，数据库T_USERTABLE表
	 * @param obj    参数
	 * @param workitemId  工作项id
	 * @return
	 */
	public boolean wancheng(String userid, String[][] obj, int workitemId)  throws Exception ;
}
