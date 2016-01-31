package com.zxt.compplatform.workflow.service.impl;

import java.util.List;

import com.zxt.compplatform.workflow.dao.DaibanWorkFlowDao;
import com.zxt.compplatform.workflow.service.DaibanWorkFlowService;

public class DaibanWorkFlowServiceImpl implements DaibanWorkFlowService{
	private DaibanWorkFlowDao db;

	public DaibanWorkFlowDao getDb() {
		return db;
	}

	public void setDb(DaibanWorkFlowDao db) {
		this.db = db;
	}

	public List DaibanByUserId(String userId) {
		return db.DaibanByUserId(userId);
	}
}
