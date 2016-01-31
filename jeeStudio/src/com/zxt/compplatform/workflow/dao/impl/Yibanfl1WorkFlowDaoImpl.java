package com.zxt.compplatform.workflow.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.synchrobit.synchroflow.api.bean.SynchroFLOWBean;
import com.synchrobit.synchroflow.api.bean.WorkitemBean;
import com.synchrobit.synchroflow.enactmentservice.exception.DBException;
import com.zxt.compplatform.workflow.dao.Yibanfl1WorkFlowDao;
import com.zxt.compplatform.workflow.entity.Workitem;

public class Yibanfl1WorkFlowDaoImpl implements Yibanfl1WorkFlowDao {
	public List yibanyi(String userId) {
		SynchroFLOWBean sf = new SynchroFLOWBean();
		List worktype = new ArrayList();

		try {
			List works = sf.getHWorkitemListByUserId(userId);
			for (int i = 0; i < works.size(); i++) {
				WorkitemBean wb = (WorkitemBean) works.get(i);
				Workitem wt = new Workitem();
				wt.setProcessDefName(wb.getProcessDefName()); // 过程定义名
				wt.setActivityInsName(wb.getActivityInsName());// 活动实例名
				wt.setCreatedTime(wb.getCreatedTime());// 工作项创建时间
				wt.setWorkitemId(wb.getWorkitemId());
				wt.setArgs(wb.getArgs());// dates
				worktype.add(wt);
			}

		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return worktype;

	}
}
