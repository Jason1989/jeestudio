package com.zxt.compplatform.workflow.action;

import com.zxt.compplatform.workflow.entity.Model;
import com.zxt.compplatform.workflow.service.QidongWorkFlowService;

public class QidongWorkFlowAction extends BaseAction {
	private QidongWorkFlowService qdwfs;
	private Model moxing;
	private String name;
	private String[][] obj;

	public QidongWorkFlowService getQdwfs() {
		return qdwfs;
	}

	public void setQdwfs(QidongWorkFlowService qdwfs) {
		this.qdwfs = qdwfs;
	}

	public String execute() {
		try {
			boolean qd = qdwfs.qidongjieguo(moxing, name);
			if (qd) {
				return "success";
			}
			return "error";
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
	}

	public Model getMoxing() {
		return moxing;
	}

	public void setMoxing(Model moxing) {
		this.moxing = moxing;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[][] getObj() {
		return obj;
	}

	public void setObj(String[][] obj) {
		this.obj = obj;
	}
	
}
