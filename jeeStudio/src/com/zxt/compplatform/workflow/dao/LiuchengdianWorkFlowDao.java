package com.zxt.compplatform.workflow.dao;

import java.util.List;

public interface LiuchengdianWorkFlowDao {
	/**
	 * 通过流程id拿到流程节点集
	 * 
	 * @param processDefId
	 * @return
	 */
	public List activityDefList(String processDefId);
}
