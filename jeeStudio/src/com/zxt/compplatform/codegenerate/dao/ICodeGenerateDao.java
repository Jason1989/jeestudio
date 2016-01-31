package com.zxt.compplatform.codegenerate.dao;

import java.util.List;

import com.zxt.compplatform.codegenerate.entity.EngCodeLog;
import com.zxt.framework.common.dao.IDAOSupport;

/**
 * 生成代码持久层
 * 
 * @author zxt-hejun
 * @date:2010-9-19 上午02:34:05
 */
public interface ICodeGenerateDao extends IDAOSupport {

	/**
	 * 获取表单日志信息
	 * 
	 * @param formId
	 * @return
	 */
	public List getCodeLogList(String formId);

	/**
	 * 获取日志详细信息
	 * 
	 * @param codeFormsId
	 * @param codeVersionId
	 * @return
	 */
	public EngCodeLog getBpTCodeLog(String codeFormsId, String codeVersionId);

	/**
	 * 保存日志详细信息
	 * 
	 * @param bpTCodeLog
	 */
	public void saveBpTCodeLog(EngCodeLog bpTCodeLog);

	/**
	 * 获取版本号
	 * 
	 * @param formId
	 * @return
	 */
	public String getEngCodeLogVersionId(String formId);

}
