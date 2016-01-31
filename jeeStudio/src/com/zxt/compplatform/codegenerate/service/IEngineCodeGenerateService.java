package com.zxt.compplatform.codegenerate.service;

import java.util.List;

import com.zxt.compplatform.codegenerate.entity.EngCodeLog;
import com.zxt.compplatform.codegenerate.util.CodeGenerateException;
import com.zxt.compplatform.formengine.entity.view.BasePage;
/**
 * 代码生成业务层
 * @author zxt-hejun
 * @date:2010-9-19 上午02:34:33
 */
public interface IEngineCodeGenerateService {
	/**
	 * 生成代码
	 * @param packageName
	 * @param basePath
	 * @param moduleName
	 * @param jarName
	 * @param pagePath
	 * @param userBasePath
	 * @param formsId
	 * @param versionRemark
	 * @param userId
	 * @return
	 */
	public boolean saveGenerateCode(String packageName,String basePath,String moduleName,String jarName,String pagePath,String userBasePath,String formsId,String versionRemark,Long userId) throws CodeGenerateException;
	/**
	 * 获取某个表单的日志记录
	 * @param formId
	 * @return
	 */
	public List findCodeLogList(String formId);
	/**
	 * 获取单表某个版本的详细信息
	 * @param codeFormsId
	 * @param codeVersionId
	 * @return
	 */
	public EngCodeLog findBpTCodeLog(String codeFormsId, String codeVersionId);
	
	public BasePage parseXml(String formId, boolean isCodeGenerate);
}
