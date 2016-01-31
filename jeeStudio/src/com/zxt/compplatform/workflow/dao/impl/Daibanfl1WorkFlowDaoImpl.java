package com.zxt.compplatform.workflow.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.synchrobit.synchroflow.api.bean.SynchroFLOWBean;
import com.synchrobit.synchroflow.api.bean.WorkitemBean;
import com.synchrobit.synchroflow.enactmentservice.exception.DBException;
import com.zxt.compplatform.workflow.dao.Daibanfl1WorkFlowDao;

public class Daibanfl1WorkFlowDaoImpl implements Daibanfl1WorkFlowDao{
	/**
	 * 待办分类
	 * @return 待办工作类别 List String  中可能有重复数据 请处理   可显示为  
	 * 工作项类别  个数     重复数据用于统计数据
	 */
	public List daibanyi(String userId) {
		SynchroFLOWBean sf = new SynchroFLOWBean();
		List worktype = new ArrayList();

		try {
			List works = sf.getWorkitemListByUserId(userId);
			for (int i = 0; i < works.size(); i++) {
				WorkitemBean wib = (WorkitemBean) works.get(i);
				String a = wib.getProcessDefName();
				worktype.add(a);
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
