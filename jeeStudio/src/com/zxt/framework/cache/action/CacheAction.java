package com.zxt.framework.cache.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.zxt.framework.cache.service.EHCacheService;
/**
 * 
 * 系统管理-缓存管理列表，查看，及手动刷新缓存
 * <p>
 *     toList 点击“缓存管理”菜单显示缓存列表页面，
 *     list 查询缓存列表数据，
 *     freshAll 点击“手动刷新缓存”菜单刷新缓存，
 *     getCacheService 获得操作缓存的service，
 *     setCacheService 注入操作缓存的service，
 *     修改日志说明：<br>
 *          1、Sep 13, 2011 9:19:22 AM（+）添加注释
 * </p>
 * @author 005
 * @version 1.00
 */
public class CacheAction {
	
	private EHCacheService cacheService;

	/**
	 * 
	  * 点击“缓存管理”菜单显示缓存列表页面，
	  * <p>
	  *   传入参数：
	  *     无
	  *     
	  *   传出参数（名称/类型）：
	  *     1. result（struts-Cache.xml配置文件里的结果页面）/String 
	  *     
	  * action访问地址： 
	  * 
	  * 修改记录：
	  *     1. 2011-09-13 005 添加注释
	  * </p>
	 */
	public String toList(){
		return "list";		
	}

	/**
	 * 
	  * 查询缓存列表数据，
	  * <p>
	  *   传入参数：
	  *     无
	  *     
	  *   传出参数（名称/类型）：
	  *     1. dataSourceJson/json
	  *     {"total":2,"rows":[{"creationTime":"2011-09-13 09:59:29.859","key":"com.zxt.compplatform.load_XMLConfig","valueSize":"2963","valueType":"[Ljava.lang.String;","lastUpdateTime":"0","cacheName":"ZXTCACHE1"}]}
	  *     
	  * action访问地址： cache/cacheing!list.action
	  * 
	  * 修改记录：
	  *     1. 2011-09-13 005 添加注释
	  * </p>
	 */
	public String list(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		
		String cacheName=req.getParameter("cacheName");
		String test=req.getParameter("test");
		/**
		 * 缓存列表分页
		 */
		List entitiesList = new ArrayList();
		entitiesList=cacheService.getCacheElementList(null);	
		Map map = new HashMap();
		if(entitiesList != null){
			map.put("rows", entitiesList);
			map.put("total",new Integer(entitiesList.size()));
		}
		String dataSourceJson = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(dataSourceJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	} 
	/**
	 * 
	  * 点击“手动刷新缓存”菜单刷新缓存，
	  * <p>
	  *   传入参数：
	  *     无
	  *     
	  *   传出参数（名称/类型）：
	  *     无
	  *     
	  * action访问地址： cache/cacheing!freshAll.action
	  * 
	  * 修改记录：
	  *     1. 2011-09-13 005 添加注释
	  * </p>
	 */
	public String freshAll(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		String test=req.getParameter("test");
		System.out.println(test);
		try{
			cacheService.freshAllCache();
			res.getWriter().write("0");
		}catch (Exception e) {
			e.printStackTrace();
			try{
				res.getWriter().write(e.getMessage());
			}catch(java.io.IOException ee){
				ee.printStackTrace();
			}
		}
		return null;
	}
		
	public EHCacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(EHCacheService cacheService) {
		this.cacheService = cacheService;
	}
	
}
