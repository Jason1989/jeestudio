package com.zxt.compplatform.workflow.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zxt.compplatform.workflow.entity.EngWorkflowNodesetting;

/**
 * 工作流节点的配置
 * 
 * @author bozch
 *
 */
public interface WorkflowAuthSettingService {


	/**
	 * 对工作流的某个节点添加相应的权限配置
	 * 
	 * @param obj 节点权限配置实体
	 */
	public void addAuth(EngWorkflowNodesetting obj);
	
	/**
	 * 删除工作流的某个节点对应应的权限配置
	 * 
	 * @param obj 节点权限配置实体id
	 */
	public void delete(String id);
	
	
	/**
	 * 对工作流的某个节点更新相应的权限配置
	 * 
	 * @param obj 节点权限配置实体
	 */
	public void update(EngWorkflowNodesetting obj);
	
	
	/**
	 * 添加相应节点操作权限
	 * @param object
	 */
	public void addAuth(Object object);
	
	
	/**
	 *  查询某个参与者拥有的权限
	 * 
	 * @param modelId 模板历史版本id
	 * @param nodeId 节点的id
	 * @param status 前驱线的状态
	 * @return
	 */
	public List getPartinerOwnAuthList(String modelId,String nodeId);

	/**
	 * 通过modelid 和nodeid 删除对应数据库的实例
	 * @param modelId
	 * @param nodeId
	 * @return
	 */
	public boolean deleteByMidNid(String modelId,String nodeId);
	
	
	/**
	 * 通过工作相以及用户的id获取相应的按钮配置
	 * @param mAnId
	 * @param userid
	 * @return
	 */
	public Map findSettingByMoIdNodeIdUserId(int worktemplate,String userid,HttpServletRequest request,Map data);
}
