package com.zxt.compplatform.workflow.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.zxt.compplatform.formengine.constant.Constant;
import com.zxt.compplatform.workflow.dao.impl.WidfindpdidWorkFlowDaoImpl;
import com.zxt.compplatform.workflow.service.TijiaoWorkFlowService;

public class TijiaoWorkFlowAction extends BaseAction{
	private TijiaoWorkFlowService tjwfs;
	private String userid;
	private String[][] obj;
	private int workitemId;
	public TijiaoWorkFlowService getTjwfs() {
		return tjwfs;
	}

	public void setTjwfs(TijiaoWorkFlowService tjwfs) {
		this.tjwfs = tjwfs;
	}

	public String execute(){
		try {
			boolean tj=tjwfs.wancheng(userid, obj, workitemId);
			if(tj){
				return "success";
			}
			return "error";
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		
	}
	public String toDoTaskSubmit(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		WidfindpdidWorkFlowDaoImpl w=new WidfindpdidWorkFlowDaoImpl();
		if(req.getSession().getAttribute("userId") != null){
		//	String  a=w.findsgx(Integer.parseInt(req.getParameter("workitemId")));
			
			String formId = req.getParameter("formId");
			
			String[][] obj = new String[2][2];
			obj[0][0] = "APP_ID";
			obj[0][1] = formId;
			obj[1][0] = Constant.DEFAULT_WORKFLOW_PARMER_KEY;
			obj[1][1] = req.getParameter(Constant.DEFAULT_WORKFLOW_PARMER_KEY);



			String userid = req.getSession().getAttribute("userId").toString();
			if(req.getParameter("workitemId") != null && !req.getParameter("workitemId").equals("")){
				int workitemId = Integer.parseInt(req.getParameter("workitemId"));
				boolean flag=tjwfs.wancheng(userid, obj, workitemId);
				if(flag){
					try {
						res.getWriter().write("success");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String[][] getObj() {
		return obj;
	}

	public void setObj(String[][] obj) {
		this.obj = obj;
	}

	public int getWorkitemId() {
		return workitemId;
	}

	public void setWorkitemId(int workitemId) {
		this.workitemId = workitemId;
	}
	
}
