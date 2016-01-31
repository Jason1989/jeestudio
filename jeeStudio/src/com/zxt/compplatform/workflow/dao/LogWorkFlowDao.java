package com.zxt.compplatform.workflow.dao;

import java.util.List;

import com.zxt.compplatform.workflow.entity.WorkFlowDataStauts;

/**
 * 流程日志相关操作dao层接口
 * @author 003
 *
 */
public interface LogWorkFlowDao {
	public void addWorkFlowLog(String userID, String currentTime,String app_id,WorkFlowDataStauts workflowDataStauts, String processDefId, String workitemId);
	public void addWorkFlowLogETC(String mid,String userId,String currentTime,String pioneer_status,String pioneer_operate,String app_id, String processDefId, String workitemId);
	
	/**
	 * 查询流程日志详情
	 */
	public List findWorkFlowLogByAppID(String app_id);
}
