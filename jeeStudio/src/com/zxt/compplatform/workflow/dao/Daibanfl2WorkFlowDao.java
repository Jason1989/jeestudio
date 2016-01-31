package com.zxt.compplatform.workflow.dao;

import java.util.List;

public interface Daibanfl2WorkFlowDao {
	/**
	 * 根据流程名，userid获得 工作项
	 * @param userId
	 * @param processDefName 工作项名（流程名）
	 * @return
	 */
	public List works(String userId, String processDefName);
}
