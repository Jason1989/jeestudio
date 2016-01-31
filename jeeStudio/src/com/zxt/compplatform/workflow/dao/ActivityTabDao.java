package com.zxt.compplatform.workflow.dao;

import java.util.List;

import com.zxt.compplatform.workflow.entity.ActivityTabSettingVo;
import com.zxt.compplatform.workflow.entity.EngActionWorkflow;

public interface ActivityTabDao {
    public List	findActivityTabList(String [] workflowParmer  );
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
	public List findWorkItemIdByServiceID(String APP_ID, String userId)  throws Exception;
	
	public EngActionWorkflow getEngActionWorkflow(String processId, String precursor);
	public void delEngActionWorkflow(String id);
	public void saveEngActionWorkflow(EngActionWorkflow data);
}
