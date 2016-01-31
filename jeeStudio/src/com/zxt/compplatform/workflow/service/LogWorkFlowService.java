package com.zxt.compplatform.workflow.service;

import java.util.List;

import com.zxt.compplatform.workflow.entity.WorkFlowDataStauts;

public interface LogWorkFlowService {

	/**
	 * 流程日志添加
	 * @param workitemId 
	 * @param processDefId 
	 */
	public void addWorkFlowLog(String userId,String app_id,WorkFlowDataStauts workflowDataStauts, String processDefId, String workitemId);
	
	/**
	 * 流程日志添加ETC
	 * @param workitemId 
	 * @param processDefId 
	 */
	public void addWorkFlowLogETC(String mid,String userId,String pioneer_status,String pioneer_operate,String app_id, String processDefId, String workitemId);
	
	/**
	 * 查询流程日志详情 
	 */
	public List findWorkFlowLogByAppID(String app_id);
}
