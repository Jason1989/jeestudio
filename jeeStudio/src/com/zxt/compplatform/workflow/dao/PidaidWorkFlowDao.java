package com.zxt.compplatform.workflow.dao;

import java.util.List;

public interface PidaidWorkFlowDao {
	/**
	 * 
	 * @param processDefId 过程定义id
	 * @param activityDefId 活动定义id
	 * @return List 1，过程name 2，活动name 
	 */
	public List pidaidfindfn(int processDefId, int activityDefId);
}
