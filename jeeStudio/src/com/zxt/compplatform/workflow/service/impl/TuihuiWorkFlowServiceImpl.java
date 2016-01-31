package com.zxt.compplatform.workflow.service.impl;

import com.zxt.compplatform.workflow.dao.TuihuiWorkFlowDao;
import com.zxt.compplatform.workflow.service.TuihuiWorkFlowService;

public class TuihuiWorkFlowServiceImpl implements TuihuiWorkFlowService {
	private TuihuiWorkFlowDao th;

	public TuihuiWorkFlowDao getTh() {
		return th;
	}

	public void setTh(TuihuiWorkFlowDao th) {
		this.th = th;
	}

	public boolean tuihui(int workitemId) {

		return th.tuihui(workitemId);
	}
}
