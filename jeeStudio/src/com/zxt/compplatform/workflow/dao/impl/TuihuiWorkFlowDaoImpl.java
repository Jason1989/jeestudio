package com.zxt.compplatform.workflow.dao.impl;

import com.synchrobit.synchroflow.api.bean.ActivityDefBean;
import com.synchrobit.synchroflow.api.bean.ActivityInsBean;
import com.synchrobit.synchroflow.api.bean.WorkitemBean;
import com.synchrobit.synchroflow.enactmentservice.exception.DBException;
import com.zxt.compplatform.workflow.dao.TuihuiWorkFlowDao;

public class TuihuiWorkFlowDaoImpl implements TuihuiWorkFlowDao {
	/**
	 * 回退  1 回退
	 */
	
	
	public boolean tuihui(int workitemId) {
		try {
			WorkitemBean wb = new WorkitemBean();// 得到工作项对象
			wb.setWorkitemId(workitemId);
			wb.init();

			int activityInsId = wb.getActivityInsId();// 得到活动实例编号
			ActivityInsBean aib = new ActivityInsBean();// 得到活动实例对象
			aib.setActivityInsId(activityInsId);
			aib.init(); // 初始化参数
			int activityDefId = aib.getActivityDefId();// 得到活动定义编号
			int mid = aib.getMId(); // 模型运行id
			ActivityDefBean adb = new ActivityDefBean(); // 活动定义对象
			adb.setActivityDefId(activityDefId);
			adb.setModelId(mid);
			adb.init();
			if (adb.getPrevious() == 1) {
				if (adb.getDesc() != null) {// T
					if (adb.getDesc().equals("1")) {
						aib.previous(adb.getPrevious());
					}
				}
			}
		} catch (DBException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;

	}
}
