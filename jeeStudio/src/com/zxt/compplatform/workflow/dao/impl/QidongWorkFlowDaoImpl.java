package com.zxt.compplatform.workflow.dao.impl;

import com.synchrobit.synchroflow.api.bean.ModelBean;
import com.synchrobit.synchroflow.api.bean.ProcessInsBean;
import com.synchrobit.synchroflow.enactmentservice.exception.DBException;
import com.synchrobit.synchroflow.enactmentservice.exception.OperateException;
import com.zxt.compplatform.workflow.dao.QidongWorkFlowDao;
import com.zxt.compplatform.workflow.entity.Model;

public class QidongWorkFlowDaoImpl implements QidongWorkFlowDao {
	/**
	 * 启动流程
	 */
	public boolean qidongjieguo(Model model, String name) {
		ModelBean modelbean = new ModelBean();// WO对象
		int modelId = model.getModelid();// 模型id
		int processId = model.getProcessId();// 过程id
		modelbean.setModelId(modelId);
		modelbean.setProcessId(processId);
		String[] forms = null;
		try {
			modelbean.init();// 初始化各参数
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			forms = modelbean.startModel(name);// 启动模型，operator是启动人
		} catch (OperateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String procId = forms[0];// 过程实例编号
		ProcessInsBean proIns = new ProcessInsBean();// 过程实例对象
		proIns.setProcessInsId(Integer.parseInt(procId));
		try {
			proIns.init();
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		try {
//			proIns.setRelevantData(obj);
//		} catch (OperateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		try {
			proIns.start(name);
		} catch (OperateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
