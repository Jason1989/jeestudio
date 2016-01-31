package com.zxt.compplatform.workflow.dao;

import com.zxt.compplatform.workflow.entity.Model;

public interface QidongWorkFlowDao {
	/**
	 * 启动流程
	 */
	public boolean qidongjieguo(Model model,String name);
}
