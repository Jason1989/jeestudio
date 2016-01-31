package com.zxt.compplatform.workflow.dao.impl;

import com.synchrobit.synchroflow.api.bean.WorkitemBean;
import com.zxt.compplatform.workflow.dao.TijiaoWorkFlowDao;

public class TijiaoWorkFlowDaoImpl implements TijiaoWorkFlowDao {
	/**
	 * 提交
	 * @throws Exception 
	 */
	public boolean wancheng(String userid, String[][] obj, int workitemId) throws Exception {
		// 完成工作项
	
		WorkitemBean wb = new WorkitemBean();// 工作项对象
		wb.setWorkitemId(workitemId);
	
		wb.init();
		
		if (wb.isCompeted()) { // 判断当前工作项是否竞争
			wb.pick(userid); // 拾取工作项
		}
		wb.complete(obj);
	
		return true;
	}
}
