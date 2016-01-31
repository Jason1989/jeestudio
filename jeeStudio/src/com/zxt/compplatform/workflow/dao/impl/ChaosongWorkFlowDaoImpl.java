package com.zxt.compplatform.workflow.dao.impl;

import com.synchrobit.synchroflow.api.bean.WorkitemBean;
import com.synchrobit.synchroflow.enactmentservice.exception.DBException;
import com.synchrobit.synchroflow.enactmentservice.exception.OperateException;
import com.zxt.compplatform.workflow.dao.ChaosongWorkFlowDao;

public class ChaosongWorkFlowDaoImpl implements ChaosongWorkFlowDao{
	/**
	 * 抄送
	 * @param workitemId
	 * @param userid
	 * @param touserid  抄送的人组
	 * @return
	 */
	public boolean copyWork(int workitemId,int userid, int[] touserid){
		WorkitemBean wtb=new WorkitemBean();
		wtb.setWorkitemId(workitemId);
		try {
			wtb.init();
			wtb.copyWorkitem(userid, touserid);
		} catch (DBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
