package com.zxt.compplatform.workflow.service.impl;

import com.zxt.compplatform.workflow.dao.TijiaoWorkFlowDao;
import com.zxt.compplatform.workflow.service.TijiaoWorkFlowService;

public class TijiaoWorkFlowServiceImpl implements TijiaoWorkFlowService {
	private TijiaoWorkFlowDao tijiaoWorkFlowDao;

	public TijiaoWorkFlowDao getTijiaoWorkFlowDao() {
		return tijiaoWorkFlowDao;
	}

	public void setTijiaoWorkFlowDao(TijiaoWorkFlowDao tijiaoWorkFlowDao) {
		this.tijiaoWorkFlowDao = tijiaoWorkFlowDao;
	}

	public boolean wancheng(String userid, String[][] obj, int workitemId) {
		try {
			tijiaoWorkFlowDao.wancheng(userid, obj, workitemId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}
}
