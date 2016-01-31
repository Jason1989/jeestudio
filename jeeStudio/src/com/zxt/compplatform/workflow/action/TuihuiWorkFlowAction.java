package com.zxt.compplatform.workflow.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.zxt.compplatform.workflow.service.TuihuiWorkFlowService;

public class TuihuiWorkFlowAction extends BaseAction {
	private TuihuiWorkFlowService thwfs;
	private int workitemId;

	public TuihuiWorkFlowService getThwfs() {
		return thwfs;
	}

	public void setThwfs(TuihuiWorkFlowService thwfs) {
		this.thwfs = thwfs;
	}

	public String execute() {

		try {
			boolean tw = thwfs.tuihui(workitemId);

			if (tw) {
				return "success";
			}
			return "error";
		} catch (RuntimeException e) {

			e.printStackTrace();
			return "error";
		}

	}
	public String toDoTaskBack(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		if(req.getParameter("workitemId") != null && !req.getParameter("workitemId").equals("")){
			int workitemId = Integer.parseInt(req.getParameter("workitemId"));
			boolean flag = thwfs.tuihui(workitemId);
			if(flag){
				try {
					res.getWriter().write("success");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	public int getWorkitemId() {
		return workitemId;
	}

	public void setWorkitemId(int workitemId) {
		this.workitemId = workitemId;
	}

}
