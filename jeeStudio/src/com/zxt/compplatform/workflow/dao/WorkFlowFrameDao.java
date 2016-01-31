package com.zxt.compplatform.workflow.dao;

import java.util.List;

import com.zxt.compplatform.workflow.entity.TaskFormNodeEntity;

public interface WorkFlowFrameDao {
	public List findTaskFormNodeEntity();
	public TaskFormNodeEntity findById(String taskFormId);
	public boolean insertTaskFormNodeEntity(TaskFormNodeEntity tfne);

	public boolean updateTaskFormNodeEntity(TaskFormNodeEntity tfne);
	
	public boolean updateTaskFormNodeEntityT(TaskFormNodeEntity tfne);

	public boolean deleteTaskFormNodeEntity(String tf_id);

	public int findTotalRows();
	
	public List findTaskFormByNode(int id,String processInstanceID);
	/**
	 * 获取表单树
	 * @return
	 */
	public List getWorkFlowTabPage();

	/**
	 * formId获取流程ID
	 */
	public TaskFormNodeEntity findTaskFormNodeEntity(String formID);
}
