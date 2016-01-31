package com.zxt.compplatform.indexset.service;

import java.util.List;

public interface WorkFlowModelService {
	
	/**
	 * 查询指定用户工作流信息
	 * @param userId 用户ID
	 * @param tablename  业务库表名
	 * @param filter 过滤条件：已办，代办，草稿
	 * @return
	 */
	public List getWorkTask(String dataSourceId,String userId,String tablename,String workflow_fiter);
}
