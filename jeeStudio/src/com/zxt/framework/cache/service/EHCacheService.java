package com.zxt.framework.cache.service;

import java.sql.Timestamp;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import com.zxt.compplatform.formengine.service.ComponentsService;
import com.zxt.compplatform.formengine.service.PageService;
import com.zxt.compplatform.formengine.service.SystemFrameService;
import com.zxt.framework.common.exceptions.AppException;
/**
 * 
 * 系统管理-缓存管理的业务实现（初始化）
 * <p>
 *     EHCacheService 构造方法，
 *     getCacheByName 利用缓存管理器获得某个名字的缓存对象，
 *     init 缓存初始化方法，负责系统启动阶段加载缓存，
 *     getAllCache 获得初始缓存，
 *     getCacheElementList 获得缓存详细信息的列表(把列表信息以键值对的形式放在list里)，
 *     getCreTimeString 获得缓存的创建时间，
 *     getUpdTimeString 获得缓存的更新时间，
 *     getCacheElement 通过键，得到缓存元素的值，
 *     addToCache 向缓存里插入一个新的元素（这个元素是键值对的形式），
 *     removeFromCacheByKey 通过键，移除缓存对象里的一个元素，
 *     removeAllFromCache 移除缓存对象里的所有元素，
 *     freshCache 更新缓存对象里的一个元素，
 *     freshAllCache 更新缓存对象里的所有元素，
 *     getCacheSize 获得缓存空间大小（单位是KB），
 *     getComponentsService 获得负责一些单独功能的serivce（加载详情页，查询数据库，批量删除，加载treejson），
 *     setComponentsService 注入负责一些单独功能的serivce，
 *     getCache 获得缓存对象，
 *     setCache 注入缓存对象，
 *     getDefaultCache 获得默认的缓存对象，
 *     getPageService 获得负责分页的service，
 *     setPageService 注入负责分页的service，
 *     getSystemFrameService 获得负责系统功能的service(连接池，用户权限，菜单的级联)，
 *     setSystemFrameService 注入负责系统功能的service，
 *     修改日志说明：<br>
 *          1、Sep 13, 2011 10:08:14 AM（+） 添加注释
 * </p>
 * @author 005
 * @version 1.00
 */
public class EHCacheService {
	
	private static final String DEFAULT_CACHE="ZXTCACHE1"; 
	private static final CacheManager cacheManager = new CacheManager();
	private ComponentsService componentsService;
	private SystemFrameService systemFrameService;
	private Cache cache;
	private PageService pageService;
	private static final Logger log = Logger.getLogger(EHCacheService.class);
	public EHCacheService(){
		this.cache=getDefaultCache();
	}
	/**
	  * 
	  * 利用缓存管理器获得某个名称的缓存对象，
	  * <p>
	  *   传入参数：
	  *     cacheName:缓存对象的名称
	  *     
	  *   传出参数（名称/类型）：
	  *     1. cache/Cache 
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 13, 2011 005 添加注释
	  * </p>
	 */
	public Cache getCacheByName(String cacheName) {
		return cacheManager.getCache(cacheName);
	}
	/**
	  * 
	  * 缓存初始化方法，负责系统启动阶段加载缓存，
	  * <p>
	  *   传入参数：
	  *     无
	  *     
	  *   传出参数（名称/类型）：
	  *     无
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 13, 2011 005 添加注释
	  * </p>
	 */
	public void init() throws AppException {
		/**
		 * example 加载ID为3、4、6的数据字典
		 */
		
		/*		
	 		componentsService.load_Dictionary("3");
			componentsService.load_Dictionary("4");
			componentsService.load_Dictionary("6");
		*/
		
		/**
		 * 缓存xml解析
		 */
		 pageService.load_service("4028864e37ea24ec0137ea29286b0003");//jz
		 pageService.load_service("4028867d37ea3d260137ea40205d0003");//bwl
		 pageService.load_service("4028863d384173d601384184184e0003");//fy
		
//		try {
//			String []array=componentsService.load_XMLConfig();
//			if (array!=null) {
//				for (int i = 0; i < array.length; i++) {
//					 pageService.load_service(array[i]);
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			log.error("表单解析过程缓存 初始化失败。。。");
//		}
	 	
	
		/**
		 * 缓存数据源连接池
		 */
		systemFrameService.load_connectPools("true");
	}
	
	/**
	  * 
	  * 获得初始缓存，
	  * <p>
	  *   传入参数：
	  *     无
	  *     
	  *   传出参数（名称/类型）：
	  *     1. list/java.util.ArrayList<Cache> 
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 13, 2011 005 添加注释
	  * </p>
	 */
	public List getAllCache() throws AppException {
		List list = new java.util.ArrayList();
		String[] names = cacheManager.getCacheNames();
		for (int i = 0; i < names.length; i++) {
			list.add(cacheManager.getCache(names[i]));
		}
		return list;
	}
	/**
	  * 
	  * 获得缓存详细信息的列表(把列表信息以键值对的形式放在list里)，
	  * <p>
	  *   传入参数：
	  *     cacheName:缓存对象的名字
	  *     
	  *   传出参数（名称/类型）：
	  *     1. list/java.util.ArrayList<Map> 
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 13, 2011 005 添加注释
	  * </p>
	 */
	public List getCacheElementList(String cacheName) {
		List list = new java.util.ArrayList();
		Cache c;
		if (cacheName == null || cacheName.equals("")) {
			c = this.getDefaultCache();
		} else {
			c = getCacheByName(cacheName);
		}
		List keys = c.getKeys();
		/**
		 * 获取所有的缓存对象
		 */
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i).toString();
			net.sf.ehcache.Element e = c.get(key);
			java.util.Map map = new java.util.HashMap();
			map.put("cacheName", c.getName());
			map.put("key", key);
			if (e.getValue()!=null) {
				map.put("valueType", e.getValue().getClass().getName());
			}
			map.put("creationTime",this.getCreTimeString(e));
			map.put("lastUpdateTime", this.getUpdTimeString(e));
			map.put("valueSize", e.getSerializedSize()+"");			
			list.add(map);
		}
		return list;
	}
	/**
	  * 
	  * 获得缓存的创建时间，
	  * <p>
	  *   传入参数：
	  *     e:缓存对象
	  *     
	  *   传出参数（名称/类型）：
	  *     1. s/String 
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 13, 2011 005 添加注释
	  * </p>
	 */
	private String getCreTimeString(net.sf.ehcache.Element e){
		String s= new Timestamp(e.getCreationTime())+"";
		return s;
	}
	/**
	  * 
	  * 获得缓存的更新时间，
	  * <p>
	  *   传入参数：
	  *     e:缓存对象
	  *     
	  *   传出参数（名称/类型）：
	  *     1. s/String 
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 13, 2011 005 添加注释
	  * </p>
	 */
	private String getUpdTimeString(net.sf.ehcache.Element e){
		if(e.getLastUpdateTime()!=0){
			String s= new Timestamp(e.getLastUpdateTime())+"";
			return s;
		}else{
			return "0";
		}
	}	
	/**
	  * 
	  * 通过键，得到缓存元素的值，
	  * <p>
	  *   传入参数：
	  *     cacheKey:缓存元素的键
	  *     
	  *   传出参数（名称/类型）：
	  *     1. value/Object 
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 13, 2011 005 添加注释
	  * </p>
	 */
	public Object getCacheElement(String cacheKey) throws AppException {
		net.sf.ehcache.Element e = cache.get(cacheKey);
		if (e == null) {
			return null;
		}
		return e.getValue();
	}
	/**
	  * 
	  * 向缓存里插入一个新的元素（这个元素是键值对的形式），
	  * <p>
	  *   传入参数：
	  *     cacheKey:缓存元素的键
	  *     result:缓存元素的值
	  *     
	  *   传出参数（名称/类型）：
	  *     无
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 13, 2011 005 添加注释
	  * </p>
	 */
	public void addToCache(String cacheKey, Object result) throws AppException {
		Element element = new Element(cacheKey, result);
		cache.put(element);
	}
	/**
	  * 
	  * 通过键，移除缓存对象里的一个元素，
	  * <p>
	  *   传入参数：
	  *     cacheKey::缓存元素的键
	  *     
	  *   传出参数（名称/类型）：
	  *     无
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 13, 2011 005 添加注释
	  * </p>
	 */
	public void removeFromCacheByKey(String cacheKey) throws AppException {
		cache.remove(cacheKey);
	}
	/**
	  * 
	  * 移除缓存对象里的所有元素，
	  * <p>
	  *   传入参数：
	  *     无
	  *     
	  *   传出参数（名称/类型）：
	  *     无
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 13, 2011 005 添加注释
	  * </p>
	 */
	public void removeAllFromCache() throws AppException {
		cache.removeAll();
	}
	/**
	  * 
	  * 更新缓存对象里的一个元素，
	  * <p>
	  *   传入参数：
	  *     cacheKey:缓存元素的键
	  *     result:缓存元素的值
	  *     
	  *   传出参数（名称/类型）：
	  *     无
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 13, 2011 005 添加注释
	  * </p>
	 */
	public void freshCache(String cacheKey, Object result) throws AppException {
		Element element = new Element(cacheKey, result);
		cache.put(element);
	}
	/**
	  * 
	  * 更新缓存对象里的所有元素，
	  * <p>
	  *   传入参数：
	  *     无
	  *     
	  *   传出参数（名称/类型）：
	  *     无
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 13, 2011 005 添加注释
	  * </p>
	 */
	public void freshAllCache() throws AppException {
		init();
	}
	/**
	  * 
	  * 获得缓存空间大小（单位是KB），
	  * <p>
	  *   传入参数：
	  *     无
	  *     
	  *   传出参数（名称/类型）：
	  *     1. size/long
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 13, 2011 005 添加注释
	  * </p>
	 */
	public long getCacheSize() throws AppException {
		return cache.getMemoryStoreSize() / 1000L;
	}

	public ComponentsService getComponentsService() {
		return componentsService;
	}

	public void setComponentsService(ComponentsService componentsService) {
		this.componentsService = componentsService;
	}

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}
	
	public Cache getDefaultCache(){
		return cacheManager.getCache(DEFAULT_CACHE);
	}

	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public SystemFrameService getSystemFrameService() {
		return systemFrameService;
	}

	public void setSystemFrameService(SystemFrameService systemFrameService) {
		this.systemFrameService = systemFrameService;
	}
}
