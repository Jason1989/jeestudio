package com.zxt.compplatform.formengine.dao;

import com.zxt.compplatform.formengine.entity.view.Page;

/**
 * 数据查询持久化操作dao
 * 
 * @author 007
 */
public interface IQueryDataDao {

	/**
	 * 查询tabs
	 * 
	 * @param pagtId
	 * @return
	 */
	public String queryTabs(String tabId);

	/**
	 * 查询pages
	 * 
	 * @param pageId
	 * @return
	 */
	public String queryPages(Page page);

}
