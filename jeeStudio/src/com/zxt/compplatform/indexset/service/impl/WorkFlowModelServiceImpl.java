package com.zxt.compplatform.indexset.service.impl;

import java.util.List;
import java.util.Map;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zxt.compplatform.formengine.dao.ComponentsDao;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.compplatform.indexset.dao.WorkFlowModelDao;
import com.zxt.compplatform.indexset.service.WorkFlowModelService;
import com.zxt.compplatform.workflow.dao.Daibanfl3WorkFlowDao;

public class WorkFlowModelServiceImpl implements WorkFlowModelService {
	private WorkFlowModelDao workflowmodeldao;
	private ComponentsDao componentsDao;
	private Daibanfl3WorkFlowDao daibandao;
	private SystemFrameService systemFrameService;

	/**
	 * 获取工作流任务
	 */
	public List getWorkTask(String dataSourceId,String userId, String tablename,
			String workflow_fiter) {
		String sql = " select  LMS_TASK.ENT_COM as ENT_COM , LMS_TASK.CONS_COM as CONS_COM , LMS_TASK.CONS_ADDRESS as CONS_ADDRESS , LMS_TASK.CONS_START_TIME as CONS_START_TIME , LMS_TASK.CONS_END_TIME as CONS_END_TIME , LMS_TASK.LINK_MAN as LINK_MAN , LMS_TASK.LINK_QQ as LINK_QQ , LMS_TASK.LINK_TEL as LINK_TEL , LMS_TASK.LINK_FAX as LINK_FAX , LMS_TASK.LINK_EMAIL as LINK_EMAIL , LMS_TASK.IS_PACKAGE as IS_PACKAGE , LMS_TASK.FINISH_DATE as FINISH_DATE , LMS_TASK.TSK_LEVEL as TSK_LEVEL , LMS_TASK.ORG_ROSE_ID as ORG_ROSE_ID , LMS_TASK.OPER_TIME as OPER_TIME , LMS_TASK.OPER_USER_ID as OPER_USER_ID , LMS_TASK.TASK_ID as TASK_ID , LMS_TASK.TASK_NAME as TASK_NAME , LMS_TASK.D_TASK_TYPE_ID as D_TASK_TYPE_ID , LMS_TASK.TASK_CODE as TASK_CODE , LMS_TASK.TASK_STATE_ID as TASK_STATE_ID , LMS_TASK.MASTER_ID as MASTER_ID , LMS_TASK.SUB_REASON as SUB_REASON , LMS_TASK.TASK_ATT_ID as TASK_ATT_ID , LMS_TASK.REP_ATT_ID as REP_ATT_ID , LMS_TASK.REMARK as REMARK , LMS_TASK.APP_ID as APP_ID , LMS_TASK.PARENT_APP_ID as PARENT_APP_ID , LMS_TASK.ENV_DATAMETER as ENV_DATAMETER , LMS_TASK.ENV_DATASTATE as ENV_DATASTATE  from LMS_TASK";
		String condition_fiter = "";
		// 草稿项查询
		if (workflow_fiter.equals("caogao")) {
			condition_fiter = " and " + tablename + ".env_datastate = '暂存'";
		} else if (workflow_fiter.equals("daibanxiang")) {
		// 代办项查询
			List list = daibandao.findAppid(userId);
			if (list != null && list.size() != 0) {
				condition_fiter += " and " + tablename + ".app_id in ("
						+ list.get(0) + ")";
			}
		// 已办项查询
		} else if (workflow_fiter.equals("yibanxiang")) {
			List list = componentsDao.findAppidsInLog(userId);
			if (list != null && list.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					if (i == 0) {
						condition_fiter += " and " + tablename
								+ ".app_id in ('"
								+ ((Map) list.get(0)).get("app_id") + "'";
					} else {
						condition_fiter += ",'"
								+ ((Map) list.get(i)).get("app_id") + "'";
					}
				}
				condition_fiter += ")";
			} else {
				condition_fiter += " and " + tablename + ".app_id in ('-1')";
			}

		}
		if (sql.indexOf("where")<0) {
			sql=sql+" where 1=1";
		}
		//从业务库中查询数据
		List list=null;
		ComboPooledDataSource conn = findPoolByFormId(dataSourceId);
		if(condition_fiter!=null && conn!=null){
			list=workflowmodeldao.queryFormData(sql+condition_fiter,null,conn);
		}
		return list;
	}

	/**
	 * 根据数据源Id查找数据连接
	 * @param dataSourceId
	 * @return
	 */
	public ComboPooledDataSource findPoolByFormId(String dataSourceId) {
		// 1查询数据源ID
		// 2 获取缓存中的连接池
		Map poolsMap = systemFrameService.load_connectPools("true");
		// 3 数据源ID查找对应的连接池
		ComboPooledDataSource connectPool = null;
		try {
			if (dataSourceId != null) {
				if (poolsMap.get(dataSourceId) != null) {
					connectPool = (ComboPooledDataSource) poolsMap
							.get(dataSourceId);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			//log.error("获取表单对应数据源失败。。。");
		}
		return connectPool;
	}
	
	
	// ---------------------getter and setter--------------////
	public Daibanfl3WorkFlowDao getDaibandao() {
		return daibandao;
	}

	public void setDaibandao(Daibanfl3WorkFlowDao daibandao) {
		this.daibandao = daibandao;
	}

	public ComponentsDao getComponentsDao() {
		return componentsDao;
	}

	public void setComponentsDao(ComponentsDao componentsDao) {
		this.componentsDao = componentsDao;
	}

	public WorkFlowModelDao getWorkflowmodeldao() {
		return workflowmodeldao;
	}

	public void setWorkflowmodeldao(WorkFlowModelDao workflowmodeldao) {
		this.workflowmodeldao = workflowmodeldao;
	}

	public SystemFrameService getSystemFrameService() {
		return systemFrameService;
	}

	public void setSystemFrameService(SystemFrameService systemFrameService) {
		this.systemFrameService = systemFrameService;
	}
}
