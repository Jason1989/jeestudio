package com.zxt.compplatform.formengine.dao;

import java.util.List;

/**
 * 工作项信息持久化操作
 * 
 * @author 007
 */
public interface WorkItemInfoDao {
	/**
	 * 查找该节点配置的表单
	 * 
	 * @param proInsId
	 * @param taskId
	 * @return
	 */
	public List findWorkFormList(String proInsId, String taskId);

	/**
	 * 查找配置表单的类型
	 */
	public String findFormType(String formId);
}
