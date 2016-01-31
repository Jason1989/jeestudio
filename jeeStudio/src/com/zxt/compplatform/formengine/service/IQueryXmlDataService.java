package com.zxt.compplatform.formengine.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.PagerEntiy;

/**
 * 查询xml
 * 
 * @author 007
 */
public interface IQueryXmlDataService {

	/**
	 * 根据表单id查询表单xml配置
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String queryBlobByFormId(String url) throws Exception;

	/**
	 * 根据id查询表单xml配置
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String findBolbById(String url) throws Exception;

	/**
	 * 查询表单数据
	 * 
	 * @param listPage
	 * @param parmerValue
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public PagerEntiy queryFormData(ListPage listPage, String[] parmerValue,
			HttpServletRequest request) throws Exception;

}
