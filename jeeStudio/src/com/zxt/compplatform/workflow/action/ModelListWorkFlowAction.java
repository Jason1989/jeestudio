package com.zxt.compplatform.workflow.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.zxt.compplatform.workflow.dao.impl.LiuchengdianWorkFlowDaoImpl;
import com.zxt.compplatform.workflow.entity.ActivityDef;
import com.zxt.compplatform.workflow.entity.Model;
import com.zxt.compplatform.workflow.service.ModelWorkFlowService;

public class ModelListWorkFlowAction extends BaseAction {
	private ModelWorkFlowService mwfs;
	private LiuchengdianWorkFlowDaoImpl workflowModelNodeDao;
	private List modellist;
	private static final Logger log = Logger.getLogger(ModelListWorkFlowAction.class);
	public ModelWorkFlowService getMwfs() {
		return mwfs;
	}

	public void setMwfs(ModelWorkFlowService mwfs) {
		this.mwfs = mwfs;
	}

	public String execute() {
		try {
			modellist = mwfs.modellist();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return "error";
		}

		return "success";

	}
	public String getWorkflowModelList(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		modellist = mwfs.modellist();
		List resultList = new ArrayList();
		if(modellist != null)
			for(int i=0;i<modellist.size();i++){
				Map map = new HashMap();
				Model model = (Model)modellist.get(i);
				map.put("id", new Integer(model.getProcessId()));
				map.put("text", model.getModelName());	
				log.info("text: "+model.getModelName()+"  id: "+model.getProcessId());
			
				resultList.add(map);
			}
		
		String dataJson = JSONArray.fromObject(resultList).toString();		
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getWorkflowModelNodeList(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String workflowModelId = req.getParameter("workflowModelId");
		List nodeList = null;
		if(workflowModelId != null && !workflowModelId.equals("")){
			nodeList = workflowModelNodeDao.activityDefList(workflowModelId);
			
		}
		List resultList = new ArrayList();
		if(nodeList != null)
			for(int i=0;i<nodeList.size();i++){
				Map map = new HashMap();
				ActivityDef activityDef = (ActivityDef)nodeList.get(i);
				map.put("id", new Integer(activityDef.getActivityDefId()));
				map.put("text", activityDef.getName());				
				resultList.add(map);
			}
		
		String dataJson = JSONArray.fromObject(resultList).toString();		
		try {
			res.getWriter().write(dataJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public List getModellist() {
		return modellist;
	}

	public void setModellist(List modellist) {
		this.modellist = modellist;
	}

	public void setWorkflowModelNodeDao(LiuchengdianWorkFlowDaoImpl workflowModelNodeDao) {
		this.workflowModelNodeDao = workflowModelNodeDao;
	}

}
