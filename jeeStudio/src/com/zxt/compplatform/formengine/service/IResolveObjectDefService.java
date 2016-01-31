package com.zxt.compplatform.formengine.service;

import org.jdom.Document;

import com.zxt.compplatform.formengine.entity.view.BasePage;

/**
 * 
 * xml对象解析
 * 
 * @author 007
 */
public interface IResolveObjectDefService {

	/**
	 * 解析xml对象
	 * 
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public BasePage resolveObject(Document doc) throws Exception;

	/**
	 * 根据表单id解析
	 * 
	 * @param formId
	 * @return
	 */
	public BasePage resolveObject(String formId);

}
