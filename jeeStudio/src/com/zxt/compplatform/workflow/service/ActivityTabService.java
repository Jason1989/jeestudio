package com.zxt.compplatform.workflow.service;

import java.util.List;

import com.zxt.compplatform.workflow.entity.ActivityTabSettingVo;
import com.zxt.compplatform.workflow.entity.EngActionWorkflow;

public interface ActivityTabService {
	/**
	 * 查询前驱下多标签
	 * @param workflowParmer[0] processId,workflowParmer[1] mid
	 * @return
	 */
	public List findActivityTabList(String[] workflowParmer);

	/**
	 * 查询版本号下活动节点
	 * @param workflowParmer[0]  processId,workflowParmer[1] mid
	 * @return
	 * @throws Exception 
	 */
	public List findActivityIDs(String[] workflowParmer) throws Exception;
	/**
	 * 查询活动节点下前驱
	 * @param  workflowParmer[0]  processId,workflowParmer[1] mid,workflowParmer[2] activityId
	 * @return
	 * @throws Exception
	 */
	public List findPrecursorIDs(String[] workflowParmer) throws Exception;
	/**
	 * 加载前驱配置的多标签
	 * @return
	 */
	public ActivityTabSettingVo loadActivityConfig(String id);
	/**
	 * 保存节点多标签
	 * 
	 * @return
	 */
	
	public String saveActivityConfig(ActivityTabSettingVo activityTabSettingVo);
	/**
	 * 删除节点多标签
	 * 
	 * @return
	 */
	public String deleteActivityConfig(String id);
	/**
	 * 查找所有编辑表单
	 */
	public List findEngEditForm();
	/**
	 * 业务主键 查找当前工作项ID
	 */
	public List findWorkItemIdByServiceID(String APP_ID, String userId)throws Exception ;
	public EngActionWorkflow getEngActionWorkflow(String processId, String precursor);
	public void delEngActionWorkflow(String id);
	public void saveEngActionWorkflow(EngActionWorkflow data);
}
