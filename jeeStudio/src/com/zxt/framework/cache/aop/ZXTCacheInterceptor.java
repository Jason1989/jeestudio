package com.zxt.framework.cache.aop;

import org.aspectj.lang.ProceedingJoinPoint;

import com.zxt.framework.cache.service.EHCacheService;
import com.zxt.framework.common.log.LogHelper;
import com.zxt.framework.common.util.ObjectUtils;
/**
 * 
 * 缓存的工具类，用于初始，刷新，删除缓存和获得缓存的key
 * <p>
 *     initCache 拦截Service和DAO中的loadXXXX方法，并查找该结果是否存在，如果存在就返回cache中的值，否则，返回数据库查询结果，并将查询结果放入cache
 *     freshCache 刷新缓存（通过key里的PREFIX_CACHE_FRESH标识所有要被刷新的缓存），
 *     removeCache 删除缓存（通过key里的PREFIX_CACHE_DEL标识所有要被删除的缓存），
 *     getCacheKey 一个工具方法，用于获得cache key，
 *     getCacheService 得到缓存service，
 *     setCacheService 注入缓存service，
 *     修改日志说明：<br>
 *          1、Sep 14, 2011 8:35:45 AM（+） 添加注释
 * </p>
 * @author 005
 * @version 1.00
 */
public class ZXTCacheInterceptor {

	private static final String PREFIX_CACHE_INIT = "load_";
	private static final String PREFIX_CACHE_FRESH = "update_";
	private static final String PREFIX_CACHE_DEL = "delete_";

	private static final LogHelper logger = new LogHelper(ZXTCacheInterceptor.class);
	private EHCacheService cacheService;
	/**
	  * 
	  * 拦截Service和DAO中的loadXXXX方法，并查找该结果是否存在，如果存在就返回cache中的值，否则，
	  * 返回数据库查询结果，并将查询结果放入cache
	  * <p>
	  *   传入参数：
	  *     pjp:--------------------
	  *     
	  *   传出参数（名称/类型）：
	  *     1. result/Object 
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 14, 2011 005 添加注释
	  * </p>
	 */
	public Object initCache(ProceedingJoinPoint pjp) throws Throwable {
		String targetName = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		Object[] arguments = pjp.getArgs();
		Object result;
		/**
		 * 初始化缓存  添加缓存键值对
		 */
		String cacheKey = getCacheKey(targetName, methodName, arguments);
		result = cacheService.getCacheElement(cacheKey);
		if (result == null) {
			result = pjp.proceed(arguments);
			cacheService.addToCache(cacheKey, result);
		}
		result=ObjectUtils.copy(result);
		return result;
	}
	/**
	  * 
	  * 刷新缓存（通过key里的PREFIX_CACHE_FRESH标识所有要被刷新的缓存），
	  * <p>
	  *   传入参数：
	  *     pjp:------------------------
	  *     
	  *   传出参数（名称/类型）：
	  *     1. result/Object 
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 14, 2011 005 添加注释
	  * </p>
	 */
	public Object freshCache(ProceedingJoinPoint pjp) throws Throwable {
		String targetName = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		methodName = methodName.replaceAll(PREFIX_CACHE_FRESH,
				PREFIX_CACHE_INIT);
		Object[] arguments = pjp.getArgs();
		Object result;
		/**
		 * 获取缓存名称
		 */
		String cacheKey = getCacheKey(targetName, methodName, arguments);
		result = pjp.proceed(arguments);
		cacheService.addToCache(cacheKey, result);
		return ObjectUtils.copy(result);
	}
	/**
	  * 
	  * 删除缓存（通过key里的PREFIX_CACHE_DEL标识所有要被删除的缓存），
	  * <p>
	  *   传入参数：
	  *     pjp:-----------------------
	  *     
	  *   传出参数（名称/类型）：
	  *     1. dataSourceJson/json 
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 14, 2011 005 添加注释
	  * </p>
	 */
	public Object removeCache(ProceedingJoinPoint pjp) throws Throwable {
		String targetName = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		methodName = methodName.replaceAll(PREFIX_CACHE_DEL, PREFIX_CACHE_INIT);
		Object[] arguments = pjp.getArgs();
		Object result;
		String cacheKey = getCacheKey(targetName, methodName, arguments);
		result = cacheService.getCacheElement(cacheKey);
		/**
		 * 开始移除缓存
		 */
		if (result != null) {
			cacheService.removeFromCacheByKey(cacheKey);
		}
		return cacheKey;
	}
	/**
	  * 
	  * 这是一个工具方法，用于获得cache key的方法，cache key是Cache中一个Element的唯一标识。cache key包括，
	  * 包名+类名+方法名+参数值，如com.co.cache.service.UserServiceImpl.getAllUser
	  * <p>
	  *   传入参数：
	  *     targetName:包名+类名
	  *     methodName:方法名
	  *     arguments:参数值
	  *     
	  *   传出参数（名称/类型）：
	  *     1. cachekey/String 
	  *     cache key(由包名+类名+方法名+参数值组成)
	  *     
	  * action访问地址： 无
	  * 
	  * 修改记录：
	  *     1. Sep 14, 2011 005 添加注释
	  * </p>
	 */
	private String getCacheKey(String targetName, String methodName,
			Object[] arguments) {
		StringBuffer sb = new StringBuffer();
		/**
		 * 获取缓存key 遍历参数
		 */
		sb.append(targetName).append(".").append(methodName);
		if ((arguments != null) && (arguments.length != 0)) {
			for (int i = 0; i < arguments.length; i++) {
				sb.append(".").append(arguments[i]);
			}
		}
		return sb.toString();
	}

	public EHCacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(EHCacheService cacheService) {
		this.cacheService = cacheService;
	}

}
