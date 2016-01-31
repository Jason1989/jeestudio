package com.zxt.compplatform.workflow.dao;

import com.zxt.compplatform.workflow.entity.Workitem;

public interface WidfindpdidWorkFlowDao {
	/**
	 * 通过工作项id得到 活动定义id 和流程定义id
	 * 
	 * @param workitemId
	 * @return
	 */
	public Workitem widfindpdid(int workitemId);
	/**
	 * 通过工作项id得到 活动定义id 和流程定义id
	 * @param workitemId
	 * @return String[] workItemInfo  工作项所对应的活动定义id和流程定义id
	 */
	public String[] workItemInfo(int workitemId);
	
	
	/**
	 * 通过workitemId find 工作流中的APP_ID
	 * 
	 * @param workitemId
	 * @return String APP_ID
	 */
	public String findAppid(int workitemId);
}
