package com.zxt.compplatform.workflow.service.impl;

import com.zxt.compplatform.workflow.dao.QidongWorkFlowDao;
import com.zxt.compplatform.workflow.entity.Model;
import com.zxt.compplatform.workflow.service.QidongWorkFlowService;

public class QidongWorkFlowServiceImpl implements QidongWorkFlowService {
	private QidongWorkFlowDao qidongworkflowdao;

	public QidongWorkFlowDao getQidongworkflowdao() {
		return qidongworkflowdao;
	}

	public void setQidongworkflowdao(QidongWorkFlowDao qidongworkflowdao) {
		this.qidongworkflowdao = qidongworkflowdao;
	}

	public boolean qidongjieguo(Model model, String name) {
		
		return qidongworkflowdao.qidongjieguo(model, name);
	}

}
