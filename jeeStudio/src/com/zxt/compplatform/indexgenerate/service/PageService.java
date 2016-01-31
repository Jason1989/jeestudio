package com.zxt.compplatform.indexgenerate.service;

import java.util.List;
import java.util.Map;

import com.zxt.compplatform.indexgenerate.entity.PageUnit;

public interface PageService {

	/**
	 * 缓存机制 加载首页
	 * @param subSystemId
	 * @return
	 */
	public Map load_Index(String subSystemId);

	/**
	 * 更新首页缓存
	 * @param subSystemId
	 */
	public Map update_Index(String subSystemId);
	/**
	 * 删除数据中对应的首页缓存
	 * @param subSystemId
	 */
	public void delete_Index(String subSystemId);
	/**
	 * 向数据中添加配置
	 * @param page
	 */
	public void add(PageUnit page);

	/**
	 * 更新数据库配置
	 * @param subSystemId
	 * @param xmlparam
	 */
	public void update(String subSystemId, String xmlparam);

	/**
	 * 从数据库中删除
	 * @param subSystemId
	 */
	public void delete(String subSystemId);

	/**
	 * 显示所有页面的列表 
	 * 不含xml配置
	 * @param rows 
	 * @param page 
	 * @return
	 */
	public List listPage(int page, int rows);

	/**
	 * 查询结果数
	 * @return
	 */
	public int findTotalRows();

	/**
	 * 根据ID查找
	 * @param ids
	 * @return
	 */
	public List findAllByIds(String ids);
	/**
	 * 当生成首页时 为首页填充默认的模块
	 * @param keyword
	 * @param num 填充的模块数量
	 * @return
	 */
	public String fillDefaultModel(String keyword, int num);
	
	public String findtemplateurl(String subSystemId);

	public PageUnit findById(String subSystemId);

	public void update(PageUnit pageunit);


}
