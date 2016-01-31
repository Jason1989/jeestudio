package com.zxt.compplatform.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zxt.compplatform.form.entity.Form;
import com.zxt.compplatform.form.service.IFormService;
import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.formengine.entity.view.EditColumn;
import com.zxt.compplatform.formengine.entity.view.EditPage;
import com.zxt.compplatform.formengine.entity.view.TextColumn;
import com.zxt.compplatform.workflow.dao.ChuangjianWorkFlowDao;
import com.zxt.compplatform.workflow.dao.PidaidWorkFlowDao;
import com.zxt.compplatform.workflow.dao.WorkFlowFrameDao;
import com.zxt.compplatform.workflow.dao.impl.LiuchengdianWorkFlowDaoImpl;
import com.zxt.compplatform.workflow.entity.ActivityDef;
import com.zxt.compplatform.workflow.entity.TaskFormNodeEntity;
import com.zxt.compplatform.workflow.service.WorkFlowFrameService;

public class WorkFlowFrameServiceImpl implements WorkFlowFrameService {
	private WorkFlowFrameDao workFlowFrameDao;
	private PidaidWorkFlowDao pwfdi;
	private ChuangjianWorkFlowDao chuangjianWorkFlowDao;
	private IFormService formService;
	private LiuchengdianWorkFlowDaoImpl workflowModelNodeDao;

	public WorkFlowFrameDao getWorkFlowFrameDao() {
		return workFlowFrameDao;
	}

	public void setFormService(IFormService formService) {
		this.formService = formService;
	}

	public void setWorkFlowFrameDao(WorkFlowFrameDao workFlowFrameDao) {
		this.workFlowFrameDao = workFlowFrameDao;
	}

	public PidaidWorkFlowDao getPwfdi() {
		return pwfdi;
	}

	public void setPwfdi(PidaidWorkFlowDao pwfdi) {
		this.pwfdi = pwfdi;
	}

	public TaskFormNodeEntity findById(String taskFormId) {
		return workFlowFrameDao.findById(taskFormId);
	}

	public List findTaskFormNodeEntity() {
		// TODO Auto-generated method stub
		try {
			List taskFormNodeEntityList = workFlowFrameDao
					.findTaskFormNodeEntity();
			for (int i = 0; i < taskFormNodeEntityList.size(); i++) {
				TaskFormNodeEntity tfne = (TaskFormNodeEntity) taskFormNodeEntityList
						.get(i);
				int processInstanceID = Integer.parseInt(tfne
						.getProcessInstanceID());
				int taskNodeID = Integer.parseInt(tfne.getTaskNodeID());
				List nodeList = null;
				nodeList = workflowModelNodeDao
						.activityDefList(processInstanceID + "");
				Form form = formService.findById(tfne.getFormID());
				List pwfdlist = pwfdi.pidaidfindfn(processInstanceID,
						taskNodeID);
				if (pwfdlist != null && pwfdlist.size() > 1) {
					tfne.setProcessInstanceName(pwfdlist.get(0).toString());
					// /tfne.setTaskNodeName(pwfdlist.get(1).toString());
					// tfne.setTaskNodeName(nodeList.size()+"");
				}
				tfne.setTaskNodeName(processInstanceID + "");
				/*
				 * if(form != null) tfne.setFormName("<a
				 * >hello"+form.getFormName()+"</a>");
				 */
				tfne
						.setFormName("<a href=\"javascript: onclick=wfFormconfigEdit("
								+ processInstanceID
								+ ",'"
								+ tfne.getTaskFormID() + "')\">配置</a>");
			}
			return taskFormNodeEntityList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List getWorkflowModelNodeListByPId(String processInstanceID) {
		// TODO Auto-generated method stub
		List nodeList = null;
		nodeList = workflowModelNodeDao.activityDefListNew(processInstanceID);
		List resultList = new ArrayList();
		if (nodeList != null)
			for (int i = 0; i < nodeList.size(); i++) {
				ActivityDef activityDef = (ActivityDef) nodeList.get(i);
				int id = activityDef.getActivityDefId();
				String name = activityDef.getName();
				List list = workFlowFrameDao.findTaskFormByNode(id,
						processInstanceID);
				for (int j = 0; j < list.size(); j++) {
					TaskFormNodeEntity tfne = (TaskFormNodeEntity) list.get(j);
					if (tfne == null) {
						tfne = new TaskFormNodeEntity();
						tfne
								.setTaskNodeName(name
										+ "<br/><a href=\"javascript: onclick=wf_formconfig_edit_add('"
										+ id + "')\">新增表单</a>");
						tfne.setTaskNodeID(id + "");
					} else {
						tfne
								.setTaskNodeName(name
										+ "<br/><a href=\"javascript: onclick=wf_formconfig_edit_add('"
										+ id + "')\">新增表单</a>");
						tfne.setTaskNodeID(id + "");
					}
					resultList.add(tfne);
				}
				if (list.size() == 0) {
					TaskFormNodeEntity tfne = new TaskFormNodeEntity();
					tfne
							.setTaskNodeName(name
									+ "<br/><a href=\"javascript: onclick=wf_formconfig_edit_add('"
									+ id + "')\">新增表单</a>");
					tfne.setTaskNodeID(id + "");
					resultList.add(tfne);
				}
			}
		return resultList;
	}

	public List getWFModelNodeListByPId(String processInstanceID) {
		try {
			return workflowModelNodeDao.activityDefListNew(processInstanceID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List getWorkFlowTabPage() {
		return workFlowFrameDao.getWorkFlowTabPage();
	}

	/**
	 * formId获取流程ID
	 */
	public TaskFormNodeEntity findTaskFormNodeEntity(String formID) {
		// TODO Auto-generated method stub
		return workFlowFrameDao.findTaskFormNodeEntity(formID);
	}

	/**
	 * 启动新的流程实例
	 */
	public void startProcessInstance(TaskFormNodeEntity taskFormNodeEntity,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		int userId = Integer.parseInt(request.getSession().getAttribute(
				"userId").toString());

		String parmer[][] = new String[3][2];
		parmer[0][0] = "APP_ID";
		parmer[0][1] = request.getParameter("APP_ID");
		
		// parmer[1][0] = "userId";
		// parmer[1][1] = userId+"";

		parmer[1][0] = "con_param";// 启动时不传参数
		if ((request.getParameter("con_param") != null)
				&& (!"".equals(request.getParameter("con_param")))) {
			parmer[1][1] = request.getParameter("con_param");
		} else {
			parmer[1][1] = "-1";
		}
		parmer[2][0] = "REC_ID";
		parmer[2][1] = request.getParameter("REC_ID");
		
		String processInstanceID = "";
		if (request.getParameter(Constant.CUSTOM_PROCESSDEFID_ID) != null
				&& !"".equals(request
						.getParameter(Constant.CUSTOM_PROCESSDEFID_ID))) {
			processInstanceID = request
					.getParameter(Constant.CUSTOM_PROCESSDEFID_ID);
		} else {
			processInstanceID = taskFormNodeEntity.getProcessInstanceID();
		}

		chuangjianWorkFlowDao.chuangjian(processInstanceID, userId, parmer);
	}
	
	public void startProcessInstance(TaskFormNodeEntity taskFormNodeEntity,
			HttpServletRequest request,EditPage editPage) {
		// TODO Auto-generated method stub
		int userId = Integer.parseInt(request.getSession().getAttribute(
				"userId").toString());

		String parmer[][] = new String[3][2];
		parmer[0][0] = "APP_ID";
		parmer[0][1] = request.getParameter("APP_ID");
		
		// parmer[1][0] = "userId";
		// parmer[1][1] = userId+"";

		parmer[1][0] = "con_param";// 启动时不传参数
		if ((request.getParameter("con_param") != null)
				&& (!"".equals(request.getParameter("con_param")))) {
			parmer[1][1] = request.getParameter("con_param");
		} else {
			parmer[1][1] = "-1";
		}
		parmer[2][0] = "REC_ID";
		
		String REC_ID = "";
		List list = editPage.getEditColumn();
		for(int i = 0;i<list.size();i++){
			EditColumn editColumn = (EditColumn)list.get(i);
			TextColumn textColumn = editColumn.getTextColumn();
			if("true".equals(textColumn.getIsworkflow())){
				REC_ID = request.getParameter(editColumn.getName());
				break;
			}
		}
		//parmer[2][1] = request.getParameter("REC_ID");
		parmer[2][1] = REC_ID;
		
		String processInstanceID = "";
		if (request.getParameter(Constant.CUSTOM_PROCESSDEFID_ID) != null
				&& !"".equals(request
						.getParameter(Constant.CUSTOM_PROCESSDEFID_ID))) {
			processInstanceID = request
					.getParameter(Constant.CUSTOM_PROCESSDEFID_ID);
		} else {
			processInstanceID = taskFormNodeEntity.getProcessInstanceID();
		}

		chuangjianWorkFlowDao.chuangjian(processInstanceID, userId, parmer);
	}

	public boolean insertTaskFormNodeEntity(TaskFormNodeEntity tfne) {
		return workFlowFrameDao.insertTaskFormNodeEntity(tfne);
	}

	public boolean updateTaskFormNodeEntity(TaskFormNodeEntity tfne) {
		return workFlowFrameDao.updateTaskFormNodeEntity(tfne);
	}

	public boolean updateTaskFormNodeEntityT(TaskFormNodeEntity tfne) {
		return workFlowFrameDao.updateTaskFormNodeEntityT(tfne);
	}

	public boolean deleteTaskFormNodeEntity(String tf_id) {
		return workFlowFrameDao.deleteTaskFormNodeEntity(tf_id);
	}

	public int findTotalRows() {
		return workFlowFrameDao.findTotalRows();
	}

	public ChuangjianWorkFlowDao getChuangjianWorkFlowDao() {
		return chuangjianWorkFlowDao;
	}

	public void setChuangjianWorkFlowDao(
			ChuangjianWorkFlowDao chuangjianWorkFlowDao) {
		this.chuangjianWorkFlowDao = chuangjianWorkFlowDao;
	}

	public LiuchengdianWorkFlowDaoImpl getWorkflowModelNodeDao() {
		return workflowModelNodeDao;
	}

	public void setWorkflowModelNodeDao(
			LiuchengdianWorkFlowDaoImpl workflowModelNodeDao) {
		this.workflowModelNodeDao = workflowModelNodeDao;
	}

	/*
	 * 拼装字符串
	 */
	public String getWorkflowModelNodeStringByPId(String processInstanceID) {
		int nodeTotal = 0;
		List nodeList = workflowModelNodeDao
				.activityDefListNew(processInstanceID);
		if (nodeList != null && nodeList.size() > 0) {
			nodeTotal = nodeList.size();
		}
		StringBuffer sb = new StringBuffer(nodeTotal + "");
		sb.append(":");
		if (nodeList != null) {
			for (int i = 0; i < nodeList.size(); i++) {
				ActivityDef activityDef = (ActivityDef) nodeList.get(i);
				int id = activityDef.getActivityDefId();
				String name = activityDef.getName();
				List list = workFlowFrameDao.findTaskFormByNode(id,
						processInstanceID);
				sb.append(name);
				sb.append("(");
				sb.append(list == null ? 0 : list.size());
				sb.append(")、");
			}
		}
		return sb.toString().substring(0, sb.toString().lastIndexOf("、"));
	}
}
