package com.zxt.compplatform.workflow.dao.impl;

import java.util.List;

import com.synchrobit.synchroflow.api.bean.ModelBean;
import com.synchrobit.synchroflow.api.bean.ProcessInsBean;
import com.synchrobit.synchroflow.api.bean.SynchroFLOWBean;
import com.synchrobit.synchroflow.enactmentservice.exception.DBException;
import com.synchrobit.synchroflow.enactmentservice.exception.OperateException;
import com.zxt.compplatform.workflow.Util.Idchangename;
import com.zxt.compplatform.workflow.dao.ChuangjianWorkFlowDao;

/**
 * 
 * @author Administrator
 * 
 */
public class ChuangjianWorkFlowDaoImpl implements ChuangjianWorkFlowDao {

	public boolean chuangjian(String processid, int userid, Object[][] obj) {

		SynchroFLOWBean sf = new SynchroFLOWBean();
		Idchangename id = new Idchangename();
		String username = id.namefindid(userid);
		try {
			List listprocess = sf.getModelList();
			for (int i = 0; i < listprocess.size(); i++) {
				ModelBean b = (ModelBean) listprocess.get(i);// 得到工作流过程定义对象
				if (Integer.toString(b.getProcessId())// 判断processDefId
						.equals(processid)) {
					int modelid = b.getModelId();
					ModelBean model = new ModelBean();// WO对象
					int modelId = modelid;// 模型id
					int processId = Integer.parseInt(processid);// 过程id
					model.setModelId(modelId);
					model.setProcessId(processId);
					String[] forms = null;
					model.init();// 初始化各参数
					forms = model.startModel(username);// 启动模型，operator是启动人
					String procId = forms[0];// 过程实例编号
					ProcessInsBean proIns = new ProcessInsBean();// 过程实例对象
					proIns.setProcessInsId(Integer.parseInt(procId));
					proIns.init();
					// Object[][] datas = new Object[2][2];// 全局变量
					// 数组的第一列是相关数据名，第二列是相关数据的值
					// datas[0][0] = "id";
					// datas[0][1] = "1";
					// datas[1][0] = "id1";
					// datas[1][1] = "1";
					// 启动流程
					proIns.setRelevantData(obj);
					proIns.start(username);
				}
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 获得工作流过程定义列表
		catch (OperateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	public String succBackProInId(String processid, int userid, Object[][] obj) {

		SynchroFLOWBean sf = new SynchroFLOWBean();
		Idchangename id = new Idchangename();
		String username = id.namefindid(userid);
		String procId="";
		try {
			List listprocess = sf.getModelList();
			for (int i = 0; i < listprocess.size(); i++) {
				ModelBean b = (ModelBean) listprocess.get(i);// 得到工作流过程定义对象
				if (Integer.toString(b.getProcessId())// 判断processDefId
						.equals(processid)) {
					int modelid = b.getModelId();
					ModelBean model = new ModelBean();// WO对象
					int modelId = modelid;// 模型id
					int processId = Integer.parseInt(processid);// 过程id
					model.setModelId(modelId);
					model.setProcessId(processId);
					String[] forms = null;
					model.init();// 初始化各参数
					forms = model.startModel(username);// 启动模型，operator是启动人
					procId = forms[0];// 过程实例编号
					ProcessInsBean proIns = new ProcessInsBean();// 过程实例对象
					proIns.setProcessInsId(Integer.parseInt(procId));
					proIns.init();
					// Object[][] datas = new Object[2][2];// 全局变量
					// 数组的第一列是相关数据名，第二列是相关数据的值
					// datas[0][0] = "id";
					// datas[0][1] = "1";
					// datas[1][0] = "id1";
					// datas[1][1] = "1";
					// 启动流程
					proIns.setRelevantData(obj);
					proIns.start(username);
				}
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 获得工作流过程定义列表
		catch (OperateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return procId;
	}
}
