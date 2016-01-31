package com.zxt.compplatform.workflow.dao.impl;

import com.synchrobit.synchroflow.api.bean.WorkitemBean;
import com.synchrobit.synchroflow.enactmentservice.exception.DBException;
import com.synchrobit.synchroflow.enactmentservice.exception.OperateException;
import com.zxt.compplatform.workflow.Util.Idchangename;
import com.zxt.compplatform.workflow.dao.ChongdxWorkFlowDao;

public class ChongdxWorkFlowDaoImpl implements ChongdxWorkFlowDao{
	/**
	 * 重定向工作项
	 * workitemId 工作项id
	 * @param performer
	 *            要重定向给的人
	 * @param operator
	 *            操作人
	 * @return boolean true 成功 false失败           
	 */
	public boolean Chongdingxiang(String  performer, int userid,int workitemId) {
		Idchangename id = new Idchangename();
		String operator = id.namefindid(userid);
		WorkitemBean wib = new WorkitemBean();
		wib.setWorkitemId(workitemId);
		try {
			wib.init();
			wib.reassign(performer, operator);

		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (OperateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
