package com.zxt.compplatform.formengine.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zxt.compplatform.formengine.entity.view.ListPage;
import com.zxt.compplatform.formengine.entity.view.PagerEntiy;

/**
 * xml数据持久化操作dao
 * @author 007
 */
public interface IQueryXmlData {
	
	
//	public String queryBlobByFormId(String url) throws Exception;
	
//	public List queryLayoutIdByUrl(String url)throws Exception;
	
	/**
	 * 查询表单数据
	 * @param sql
	 * @param parmerValue
	 * @param listPage
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public PagerEntiy queryFormData(String sql,String[]parmerValue,ListPage listPage,HttpServletRequest request)throws Exception;
	
	/**
	 * 根据id查询表单配置信息
	 * @param sql
	 * @param formID
	 * @return
	 * @throws Exception
	 */
	public String findBolbXMLById(String sql,String formID) throws Exception;


}
