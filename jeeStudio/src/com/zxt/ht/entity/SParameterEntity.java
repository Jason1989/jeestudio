package com.zxt.ht.entity;

import java.util.Map;

/**
 * 
 * 功能注释
 * <p>
 * key值说明
 * isSaveLogginLog;是否保留登录日志
 * isSaveVoucherStopLog;是否保留单据终止操作日志
 * is
 * </p>
 * @author hegewei
 * @version 1.0
 */
public class SParameterEntity {

	private Map<String, String> systemParmer;
	private Map<String, PlanLimitEntity> planLimitMap;

	public Map<String, String> getSystemParmer() {
		return systemParmer;
	}

	public void setSystemParmer(Map<String, String> systemParmer) {
		this.systemParmer = systemParmer;
	}

	public Map<String, PlanLimitEntity> getPlanLimitMap() {
		return planLimitMap;
	}

	public void setPlanLimitMap(Map<String, PlanLimitEntity> planLimitMap) {
		this.planLimitMap = planLimitMap;
	}


	
}
