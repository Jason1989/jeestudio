package com.zxt.compplatform.workflow.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.synchrobit.synchroflow.api.bean.ActivityDefBean;
import com.synchrobit.synchroflow.api.bean.ProcessDefBean;
import com.synchrobit.synchroflow.api.bean.SynchroFLOWBean;
import com.synchrobit.synchroflow.api.util.DbTools;
import com.synchrobit.synchroflow.api.util.ReadPropertyConfig;
import com.synchrobit.synchroflow.enactmentservice.exception.DBException;
import com.zxt.compplatform.workflow.dao.LiuchengdianWorkFlowDao;
import com.zxt.compplatform.workflow.entity.ActivityDef;

public class LiuchengdianWorkFlowDaoImpl implements LiuchengdianWorkFlowDao {
	private static final Logger log = Logger
			.getLogger(LiuchengdianWorkFlowDaoImpl.class);

	/**
	 * 通过流程id拿到流程节点集
	 * 
	 * @param processDefId
	 * @return
	 */
	public List activityDefList(String processDefId) {
		SynchroFLOWBean sb = new SynchroFLOWBean();
		List activitydfs = new ArrayList();
		int mid = 0;
		try {
			List listprocess = sb.getProcessDefList();// 获得工作流过程定义列表
			ProcessDefBean pdb=null;
			for (int i = 0; i < listprocess.size(); i++) {
				pdb = (ProcessDefBean) listprocess.get(i);// 得到工作流过程定义对象
				if (Integer.toString(pdb.getProcessDefId())// 判断processDefId
						.equals(processDefId)) {
					if (pdb.getModelId() > mid) {
						mid = pdb.getModelId();
					}
				}
			}
			/**
			 * 初始化模型的最新版本
			 */
			pdb.setProcessDefId(Integer.parseInt(processDefId));
			pdb.setModelId(mid);
			pdb.init();
			List adl = pdb.getActivityDefList();
			for (int j = 0; j < adl.size(); j++) {
				ActivityDefBean adb = (ActivityDefBean) adl.get(j);// synchroflow内置对象
				ActivityDef atdf = new ActivityDef(); // 替换对象
				atdf.setActivityDefId(adb.getActivityDefId());
				atdf.setAppId(adb.getAppId());
				atdf.setDesc(adb.getDesc());
				atdf.setFormId(adb.getFormId());
				atdf.setModelId(adb.getModelId());
				atdf.setName(adb.getName());
				atdf.setParticipant(adb.getParticipant());
				atdf.setParticipantType(adb.getParticipantType());
				atdf.setPrevious(adb.getPrevious());
				atdf.setProcessDefId(adb.getProcessDefId());
				atdf.setSubprocessFileName(adb.getSubprocessFileName());
				atdf.setSubprocessId(adb.getSubprocessId());
				atdf.setTimeLimit(adb.getTimeLimit());
				atdf.setTimeLimitType(adb.getTimeLimitType());
				atdf.setTimeUnit(adb.getTimeUnit());
				activitydfs.add(atdf);
			}
			
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return activitydfs;

	}

	public List activityDefListNew(String processDefId) {
		String monitorRmiStr = ReadPropertyConfig.getInstance().getDbrmi();
		StringBuffer s = new StringBuffer();
		s
				.append("select t.name,t.activity_def_id from t_activity_def t where t.process_def_id='");
		s.append(processDefId);
		s
				.append("' and t.m_id=(select max(m_id) from t_activity_def where process_def_id='");
		s.append(processDefId);
		s.append("') and t.name is not null");
		List activitydfs = new ArrayList();
		try {
			Object obj[][] = DbTools.queryDb(monitorRmiStr, s.toString());
			for (int i = 0; i < obj.length; i++) {
				ActivityDef atdf = new ActivityDef();
				atdf.setActivityDefId(Integer.parseInt(obj[i][1].toString()));
				atdf.setName(obj[i][0].toString());
				activitydfs.add(atdf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return activitydfs;
	}
}
