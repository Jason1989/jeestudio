package com.zxt.compplatform.workflow.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.synchrobit.synchroflow.api.bean.ModelBean;
import com.zxt.compplatform.workflow.dao.ModlWorkFlowDao;
import com.zxt.compplatform.workflow.entity.Model;
import com.zxt.compplatform.workflow.service.ModelWorkFlowService;

public class ModelWorkFlowServiceImpl implements ModelWorkFlowService {
	private ModlWorkFlowDao md;

	public ModlWorkFlowDao getMd() {
		return md;
	}

	public void setMd(ModlWorkFlowDao md) {
		this.md = md;
	}

	public List modellist() {
	List modellist=new ArrayList();
	List modelbeanlist=md.modellist();
	for(int i=0;i<modelbeanlist.size();i++){
		ModelBean mb=(ModelBean) modelbeanlist.get(i);
		Model m=new Model();
		m.setModelFlag(mb.getModelFlag());
		m.setModelid(mb.getModelId());
		m.setModelName(mb.getModelName());
		m.setPath(mb.getPath());
		m.setProcessDesc(mb.getProcessDesc());
		m.setProcessId(mb.getProcessId());
		m.setProcessName(mb.getProcessName());
		modellist.add(m);
		
	}
		return modellist;
	}

}
