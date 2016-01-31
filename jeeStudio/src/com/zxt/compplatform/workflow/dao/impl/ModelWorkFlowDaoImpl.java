package com.zxt.compplatform.workflow.dao.impl;

import java.util.List;

import com.synchrobit.synchroflow.api.bean.SynchroFLOWBean;
import com.synchrobit.synchroflow.enactmentservice.exception.DBException;
import com.synchrobit.synchroflow.enactmentservice.exception.OperateException;
import com.zxt.compplatform.workflow.dao.ModlWorkFlowDao;


public class ModelWorkFlowDaoImpl implements ModlWorkFlowDao {
/**
 * 需要谨慎对待
 */
	public List modellist() {
		SynchroFLOWBean sf = new SynchroFLOWBean();
		List model = null;
		try {
			model = sf.getModelList();
//			for (int i = 0; i < model.size(); i++) {
//				ModelBean mb=(ModelBean) model.get(i);
//				ModelBean mbb=new ModelBean();
//				mbb.setModelId(mb.getModelId());
//				mbb.setProcessId(mb.getProcessId());
//				mbb.init();
//				mbb.startModel("system");
//			}
		} catch (DBException e) {

			e.printStackTrace();
		} catch (OperateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return model;
	}

}
