package com.zxt.compplatform.formengine.service;

import javax.servlet.http.HttpServletRequest;

import com.zxt.compplatform.formengine.entity.view.ListPage;

/**
 * 列表页业务操接口
 * 
 * @author 007
 */
public interface ListPageService {
	/**
	 * 
	 * 方法描述 封装操作列
	 * 
	 */
	public ListPage controlOperationField(ListPage listPage);

	/**
	 * 封装列表加载数据URl
	 * 
	 */
	public String findGridUrl(ListPage listPage, HttpServletRequest request);

	/**
	 * 设置列表页返回值
	 */
	public void installBackValue(HttpServletRequest request, ListPage listPage);
}
