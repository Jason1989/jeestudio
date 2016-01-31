package com.zxt.compplatform.workflow.service.impl;

import java.util.Calendar;
import java.util.List;

import com.zxt.compplatform.workflow.dao.LogWorkFlowDao;
import com.zxt.compplatform.workflow.entity.WorkFlowDataStauts;
import com.zxt.compplatform.workflow.service.LogWorkFlowService;

public class LogWorkFlowServiceImpl implements LogWorkFlowService {

	//dao层注入
	private LogWorkFlowDao logWorkFlowDao;
	
	public void addWorkFlowLog(String userId,String app_id,WorkFlowDataStauts workflowDataStauts, String processDefId, String workitemId) {
		//获取当前时间
		Calendar ca = Calendar.getInstance();
	    int year = ca.get(Calendar.YEAR);
	    int month=ca.get(Calendar.MONTH);
	    int day=ca.get(Calendar.DATE);
	    int minute=ca.get(Calendar.MINUTE);
	    int hour=ca.get(Calendar.HOUR);
	    int second=ca.get(Calendar.SECOND);
	    String currentTime = year +"年"+ month +"月"+ day + "日"+hour +"时"+ minute +"分"+ second +"秒";
	      
		logWorkFlowDao.addWorkFlowLog(userId,currentTime,app_id,workflowDataStauts,processDefId,workitemId);
	}
	
	/**
	 * 流程日志添加ETC
	 */
	public void addWorkFlowLogETC(String mid,String userId,String pioneer_status,String pioneer_operate,String app_id,String processDefId,String workitemId){
		//获取当前时间
		Calendar ca = Calendar.getInstance();
	    int year = ca.get(Calendar.YEAR);
	    int month=ca.get(Calendar.MONTH);
	    int day=ca.get(Calendar.DATE);
	    int minute=ca.get(Calendar.MINUTE);
	    int hour=ca.get(Calendar.HOUR);
	    int second=ca.get(Calendar.SECOND);
	    String currentTime = year +"年"+ month +"月"+ day + "日"+hour +"时"+ minute +"分"+ second +"秒";
	      
		logWorkFlowDao.addWorkFlowLogETC(mid,userId,currentTime,pioneer_status,pioneer_operate,app_id,processDefId,workitemId);
	}
	
	/**
	 * 查询流程日志详情 
	 */
	public List findWorkFlowLogByAppID(String app_id){
		return logWorkFlowDao.findWorkFlowLogByAppID(app_id);
	}

	public LogWorkFlowDao getLogWorkFlowDao() {
		return logWorkFlowDao;
	}

	public void setLogWorkFlowDao(LogWorkFlowDao logWorkFlowDao) {
		this.logWorkFlowDao = logWorkFlowDao;
	}

}
