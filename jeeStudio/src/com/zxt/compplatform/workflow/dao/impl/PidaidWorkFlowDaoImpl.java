package com.zxt.compplatform.workflow.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.synchrobit.synchroflow.api.bean.ActivityDefBean;
import com.synchrobit.synchroflow.api.bean.ProcessDefBean;
import com.synchrobit.synchroflow.api.bean.SynchroFLOWBean;
import com.synchrobit.synchroflow.enactmentservice.exception.DBException;
import com.zxt.compplatform.workflow.dao.PidaidWorkFlowDao;

public class PidaidWorkFlowDaoImpl implements PidaidWorkFlowDao{
	/**
	 * 
	 * @param processDefId 过程定义id
	 * @param activityDefId 活动定义id
	 * @return List 1，过程name 2，活动name 
	 */
	public List pidaidfindfn(int processDefId, int activityDefId) {
		SynchroFLOWBean sb = new SynchroFLOWBean();
		List activitydfs = new ArrayList();
		try {
			List listprocess = sb.getProcessDefList();
			List sameProcessDefList=new ArrayList();
			
			for (int i = 0; i < listprocess.size(); i++) {
				ProcessDefBean pdb = (ProcessDefBean) listprocess.get(i);// 得到工作流过程定义对象
				if (pdb.getProcessDefId() == processDefId) {
					if (pdb.getProcessDefId() == processDefId) {
						sameProcessDefList.add(pdb);
					}
				}
			}
			if (sameProcessDefList.size()!=0) {
				ProcessDefBean  processDefBean=(ProcessDefBean)(sameProcessDefList.get(sameProcessDefList.size()-1));
				activitydfs.add(processDefBean.getProcessDefName());
				List atdl = processDefBean.getActivityDefList();
				for (int j = 0; j < atdl.size(); j++) {
					ActivityDefBean adb = (ActivityDefBean) atdl.get(j);
					if (activityDefId == adb.getActivityDefId()) {
						String adbname = adb.getName();
						activitydfs.add(adbname);
					}

				}
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 获得工作流过程定义列表
		return activitydfs;

	}
}
