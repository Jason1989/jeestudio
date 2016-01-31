package com.zxt.compplatform.formengine.service.impl;

import java.util.List;

import com.zxt.compplatform.formengine.dao.WorkItemInfoDao;
import com.zxt.compplatform.formengine.service.WorkItemInfoService;
import com.zxt.compplatform.workflow.dao.WidfindpdidWorkFlowDao;
import com.zxt.compplatform.workflow.entity.TaskFormNodeEntity;

/**
 * 工作项信息操作实现
 * 
 * @author 007
 */
public class WorkItemInfoServiceImpl implements WorkItemInfoService {

	/**
	 * 工作项信息持久化接口
	 */
	private WorkItemInfoDao workItemInfoDao;
	/**
	 * 工作流持久化操作
	 */
	private WidfindpdidWorkFlowDao widfindpdidWorkFlowDao;

	public WorkItemInfoDao getWorkItemInfoDao() {
		return workItemInfoDao;
	}

	public void setWorkItemInfoDao(WorkItemInfoDao workItemInfoDao) {
		this.workItemInfoDao = workItemInfoDao;
	}

	/**
	 * 查找该活动项对应的表单
	 */
	public List findWorkFormList(String workItemId) {
		int workitemId = 0;
		if (workItemId != null) {
			workitemId = Integer.parseInt(workItemId);
		}
		/**
		 * 获取流程定义ID，活动节点ID
		 */
		String[] workItemInfo = widfindpdidWorkFlowDao.workItemInfo(workitemId);

		List list = workItemInfoDao.findWorkFormList(workItemInfo[0],
				workItemInfo[1]);
		for (int i = 0; i < list.size(); i++) {
			TaskFormNodeEntity tfne = (TaskFormNodeEntity) list.get(i);
			if (tfne.getFormID().indexOf(".") > 0) {
				tfne.setType("0");// 用户自定义表单
			} else {
				if ("viewPage".equals(findFormType(tfne.getFormID()))) {
					tfne.setType("viewPage");
				} else if ("editPage".equals(findFormType(tfne.getFormID()))) {
					tfne.setType("editPage");
				}

			}
		}
		return list;
	}

	/**
	 * 查找APP_ID
	 */
	public String findAppId(String workitemId) {
		// TODO Auto-generated method stub
		String APP_ID = "-1";

		if (workitemId != null) {
			APP_ID = widfindpdidWorkFlowDao.findAppid(Integer
					.parseInt(workitemId))
					+ "";
		}
		return APP_ID;
	}

	public WidfindpdidWorkFlowDao getWidfindpdidWorkFlowDao() {
		return widfindpdidWorkFlowDao;
	}

	public void setWidfindpdidWorkFlowDao(
			WidfindpdidWorkFlowDao widfindpdidWorkFlowDao) {
		this.widfindpdidWorkFlowDao = widfindpdidWorkFlowDao;
	}

	/* (non-Javadoc)
	 * @see com.zxt.compplatform.formengine.service.WorkItemInfoService#findFormType(java.lang.String)
	 */
	public String findFormType(String formId) {
		return workItemInfoDao.findFormType(formId);
	}

}
