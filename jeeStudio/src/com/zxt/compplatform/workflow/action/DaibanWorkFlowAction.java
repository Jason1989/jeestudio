package com.zxt.compplatform.workflow.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.googlecode.jsonplugin.JSONUtil;
import com.zxt.compplatform.workflow.dao.impl.Daibanfl1WorkFlowDaoImpl;
import com.zxt.compplatform.workflow.dao.impl.ScheduleWorkDaoImpl;
import com.zxt.compplatform.workflow.service.DaibanWorkFlowService;

public class DaibanWorkFlowAction extends BaseAction {
	private DaibanWorkFlowService dbwfs;
	private Daibanfl1WorkFlowDaoImpl toDoTaskTypeDao;
	private ScheduleWorkDaoImpl toDoTaskDao;
	private String userId;
	private List workit;

	public DaibanWorkFlowService getDbwfs() {
		return dbwfs;
	}

	public void setDbwfs(DaibanWorkFlowService dbwfs) {
		this.dbwfs = dbwfs;
	}

	public String execute() {

		try {
			workit = dbwfs.DaibanByUserId(userId);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return "success";

	}
	public String toToDoTaskList(){			
		return "todotasklist";
	}
	public String toToDoTaskTabs(){		
		HttpServletRequest req = ServletActionContext.getRequest();
		String tabId = req.getParameter("tabId");
		req.setAttribute("tabId", tabId);
		return "todotasktabs";
	}
	/**
	 * 获取用户待办分类，流程模板分类
	 * Explained by   heGeWei
	 * @return
	 */
	public String getToDoTaskTypeList(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		if(req.getSession().getAttribute("userId") != null){
			String userId = req.getSession().getAttribute("userId").toString();
			if(userId != null && !userId.equals("")){
				List typeList = toDoTaskTypeDao.daibanyi(userId);
				//String dataJson = JSONArray.fromObject(typeList).toString();
				String dataJson;
				try {
					dataJson = JSONUtil.serialize(typeList);
					
					res.getWriter().write(dataJson);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}
		}
		return null;
	}
	/**
	 * 获取用户当前待办
	 * Explained by   heGeWei
	 * @return
	 */
	public String getToDoTaskList(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		if(req.getSession().getAttribute("userId") != null){
			String userId = req.getSession().getAttribute("userId").toString();
			String taskTypeId = req.getParameter("taskTypeId");
			if(userId != null && !userId.equals("") && taskTypeId != null && !taskTypeId.equals("")){
				int page = 1;
				if(req.getParameter("page") != null && !req.getParameter("page").equals("")){
					page = Integer.parseInt(req.getParameter("page"));
				}
				int rows = 0;
				if(req.getParameter("rows") != null && !req.getParameter("rows").equals("")){
					rows = Integer.parseInt(req.getParameter("rows"));
				}
				List taskList = toDoTaskDao.works(userId, taskTypeId);
				Map map = new HashMap();
				if(taskList == null){
					taskList = new ArrayList();
				}
				map.put("rows", taskList);
				map.put("total", new Integer(taskList.size()));
				String formJson = JSONObject.fromObject(map).toString();
				try {
					res.getWriter().write(formJson);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List getWorkit() {
		return workit;
	}

	public void setWorkit(List workit) {
		this.workit = workit;
	}

	public void setToDoTaskTypeDao(Daibanfl1WorkFlowDaoImpl toDoTaskTypeDao) {
		this.toDoTaskTypeDao = toDoTaskTypeDao;
	}

	public void setToDoTaskDao(ScheduleWorkDaoImpl toDoTaskDao) {
		this.toDoTaskDao = toDoTaskDao;
	}

}
