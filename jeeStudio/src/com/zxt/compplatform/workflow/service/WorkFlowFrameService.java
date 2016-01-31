package com.zxt.compplatform.workflow.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.workflow.entity.TaskFormNodeEntity;


public interface WorkFlowFrameService {
	public List findTaskFormNodeEntity();
	public List getWorkflowModelNodeListByPId(String processInstanceID);
	public List getWFModelNodeListByPId(String processInstanceID);
	public List getWorkFlowTabPage();
	public TaskFormNodeEntity findById(String taskFormId);
	public boolean insertTaskFormNodeEntity(TaskFormNodeEntity tfne);
	public boolean updateTaskFormNodeEntity(TaskFormNodeEntity tfne);
	public boolean updateTaskFormNodeEntityT(TaskFormNodeEntity tfne);
	public boolean deleteTaskFormNodeEntity(String tf_id);
	public int findTotalRows();
	/**
	 * formId获取流程ID
	 */
	public TaskFormNodeEntity findTaskFormNodeEntity(String formID);
	/**
	 * 启动一个新流程实例
	 */
	public void startProcessInstance(TaskFormNodeEntity taskFormNodeEntity,HttpServletRequest request);
	
	/**
	 * 启动一个新流程实例 带Editpage
	 */
	public void startProcessInstance(TaskFormNodeEntity taskFormNodeEntity,HttpServletRequest request,EditPage editPage);
	
	
	/**
	 *拼装字符串 
	 */
	public String getWorkflowModelNodeStringByPId(String processInstanceID);
}
