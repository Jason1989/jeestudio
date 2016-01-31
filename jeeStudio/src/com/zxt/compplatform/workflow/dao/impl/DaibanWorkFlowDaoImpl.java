package com.zxt.compplatform.workflow.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.synchrobit.synchroflow.api.bean.ActivityDefBean;
import com.synchrobit.synchroflow.api.bean.ActivityInsBean;
import com.synchrobit.synchroflow.api.bean.SynchroFLOWBean;
import com.synchrobit.synchroflow.api.bean.WorkitemBean;
import com.zxt.compplatform.workflow.dao.DaibanWorkFlowDao;
import com.zxt.compplatform.workflow.entity.Workitem;
/**
 * 暂不使用
 * @author Administrator
 *
 */
public class DaibanWorkFlowDaoImpl implements DaibanWorkFlowDao {
	public List DaibanByUserId(String userId) {// 用户编号获得工作项的列表
		SynchroFLOWBean sf = new SynchroFLOWBean();
		List workitemlist = new ArrayList();
		try {
			List workitemListByUserIdlist = sf.getWorkitemListByUserId(userId);

			for (int i = 0; i < workitemListByUserIdlist.size(); i++) {
				WorkitemBean wb = (WorkitemBean) workitemListByUserIdlist
						.get(i);// 得到工作项对象
				Workitem wt = new Workitem();
				wt.setProcessDefName(wb.getProcessDefName()); // 过程定义名
				wt.setActivityInsName(wb.getActivityInsName());// 活动实例名
				wt.setCreatedTime(wb.getCreatedTime());// 工作项创建时间
				wt.setWorkitemId(wb.getWorkitemId());
				wt.setArgs(wb.getArgs());// dates

				int activityInsId = wb.getActivityInsId();
				ActivityInsBean aib = new ActivityInsBean();// 得到活动实例对象
				aib.setActivityInsId(activityInsId);
				aib.init(); // 初始化参数
				int activityDefId = aib.getActivityDefId();// 得到活动定义编号
				int mid = aib.getMId(); // 模型运行id
				ActivityDefBean adb = new ActivityDefBean(); // 活动定义对象
				adb.setActivityDefId(activityDefId);
				adb.setModelId(mid);
				adb.init();
				wt.setTuihui(adb.getDesc());// 退回标记

				workitemlist.add(wt);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 返回userid所对应的工作项列表
		return workitemlist;
	}
}
