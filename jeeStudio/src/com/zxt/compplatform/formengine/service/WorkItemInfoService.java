package com.zxt.compplatform.formengine.service;

import java.util.List;

/**
 * 工作项信息操作接口
 * 
 * @author 007
 */
public interface WorkItemInfoService {
	/**
	 * 根据工作项id获取工作表单
	 * 
	 * @param workItemId
	 * @return
	 */
	public List findWorkFormList(String workItemId);

	/**
	 * 工作项ID查找 APP_ID
	 */
	public String findAppId(String workitemId);

	/**
	 * 查找配置表单的类型
	 */
	public String findFormType(String formId);
}
