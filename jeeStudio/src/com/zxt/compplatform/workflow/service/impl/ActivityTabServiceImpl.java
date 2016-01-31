package com.zxt.compplatform.workflow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.synchrobit.synchroflow.api.bean.ActivityDefBean;
import com.synchrobit.synchroflow.api.bean.ProcessDefBean;
import com.synchrobit.synchroflow.api.bean.TransitionDefBean;
import com.zxt.compplatform.formengine.entity.view.Tab;
import com.zxt.compplatform.workflow.dao.ActivityTabDao;
import com.zxt.compplatform.workflow.entity.ActivityTabSettingVo;
import com.zxt.compplatform.workflow.entity.EngActionWorkflow;
import com.zxt.compplatform.workflow.service.ActivityTabService;

public class ActivityTabServiceImpl implements ActivityTabService {
	private ActivityTabDao activityTabDao;

	public ActivityTabDao getActivityTabDao() {
		return activityTabDao;
	}

	public void setActivityTabDao(ActivityTabDao activityTabDao) {
		this.activityTabDao = activityTabDao;
	}

	public List findActivityTabList(String[] workflowParmer) {
		// TODO Auto-generated method stub
		List list=activityTabDao.findActivityTabList(workflowParmer);
		Tab tab=null;
		if (list!=null) {
			for (int i = 0; i < list.size(); i++) {
				tab=(Tab)list.get(i);
				if (tab.getUrl()!=null) {
					if ((tab.getUrl().indexOf(".")<0)&&(!"".equals(tab.getUrl()))) {
						((Tab)list.get(i)).setIsCustom(0);//平台配置的表单
					}
				}
			}
		}
		return list;
	}

	public List findActivityIDs(String[] workflowParmer) throws Exception{
		// TODO Auto-generated method stub
		
		List list=new ArrayList();
		Map map=null;
		ActivityDefBean activityDefBean=null;
		
		ProcessDefBean processDefBean = new ProcessDefBean();
		processDefBean.setProcessDefId(Integer.parseInt(workflowParmer[0]));
		processDefBean.setModelId(Integer.parseInt(workflowParmer[1]));
		
		processDefBean.init();
	
		List temList= processDefBean.getActivityDefList();
		for (int i = 0; i < temList.size(); i++) {
			map=new HashMap();
			activityDefBean=(ActivityDefBean)temList.get(i);
			map.put("value",new  Integer(activityDefBean.getActivityDefId()));
			map.put("text", activityDefBean.getName());
			list.add(map);
		}
		
		return list;
	}

	public List findPrecursorIDs(String[] workflowParmer) throws Exception {
		// TODO Auto-generated method stub
		/**
		 * 初始化流程定义对象
		 */
		ProcessDefBean processDefBean = new ProcessDefBean();
		processDefBean.setProcessDefId(Integer.parseInt(workflowParmer[0]));
		processDefBean.setModelId(Integer.parseInt(workflowParmer[1]));
		processDefBean.init();
		/**
		 * 定义变量
		 */
		int activityId=Integer.parseInt(workflowParmer[2]);
		Map map = null;
		String temString = "";
		List fromTransfer = new ArrayList();
		TransitionDefBean transitionDefBean = null;
		String[] temTransfer = null;
		
		/**
		 * 遍历流程转移线List
		 */
		List transitionDefList = processDefBean.getTransitionDefList();
			
			
		for (int i = 0; i < transitionDefList.size(); i++) {
			transitionDefBean = (TransitionDefBean) transitionDefList
					.get(i);
			/**
			 * 取当前节点的前驱
			 */
			if (activityId == transitionDefBean.getToActivityId()) {
				temString = transitionDefBean.getDesc();
				if (!"".equals(temString)) {
					temString = temString.substring(temString
							.indexOf("status=[") + 8, temString.length());
					temString = temString.substring(0, temString
							.indexOf("]"));
					temTransfer = temString.split(",");

					map = new HashMap();
					map.put("value", temTransfer[0]);
					map.put("text", temTransfer[1]);
					fromTransfer.add(map);
				}
			}
		}
			
		return fromTransfer;
	}

	/**
	 * 业务主键 查找当前工作项ID
	 * @throws Exception 
	 */
	public List findWorkItemIdByServiceID(String APP_ID, String userId) throws Exception {
		// TODO Auto-generated method stub
		
		return activityTabDao.findWorkItemIdByServiceID(APP_ID, userId);
	}
	
	public ActivityTabSettingVo loadActivityConfig(String id) {
		// TODO Auto-generated method stub
		return activityTabDao.loadActivityConfig(id);
	}

	public String deleteActivityConfig(String id) {
		// TODO Auto-generated method stub
		return activityTabDao.deleteActivityConfig(id);
	}

	public String saveActivityConfig(ActivityTabSettingVo activityTabSettingVo) {
		// TODO Auto-generated method stub
		return activityTabDao.saveActivityConfig(activityTabSettingVo);
	}

	public List findEngEditForm() {
		// TODO Auto-generated method stub
		return activityTabDao.findEngEditForm();
	}
	
	public EngActionWorkflow getEngActionWorkflow(String processId, String precursor){
		return activityTabDao.getEngActionWorkflow(processId, precursor);
	}
	public void delEngActionWorkflow(String id){
		activityTabDao.delEngActionWorkflow(id);
	}
	public void saveEngActionWorkflow(EngActionWorkflow data){
		activityTabDao.saveEngActionWorkflow(data);
	}
	
}
