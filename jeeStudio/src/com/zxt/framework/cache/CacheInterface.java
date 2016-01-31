/**
* Copyright© 2010 zxt Co. Ltd.
* All right reserved.
* 
*/
package com.zxt.framework.cache;

import java.util.List;

import com.zxt.framework.cache.entity.CacheEntity;

/**
 * Title: CacheInterface
 * Description:  cache Interface
 * Create DateTime: 2010-9-10
 * @author xxl
 * @since v1.0
 * 
 */
public abstract interface CacheInterface{
	
	/**
	 * 获取所有缓存对象
	 * @return
	 */
	public List getCacheList();
	/**
	 * 获取所有初始化加载的对象
	 * @return
	 */
	public List getInitLoadList();	
	/**
	 * 添加缓存对象到初始化服务器中
	 * @return
	 */
	public boolean saveToInitCacheList(CacheEntity cacheEntity);
	/**
	 * 添加缓存
	 * @return
	 */
	public boolean addToCache(CacheEntity cacheEntity);
	/**
	 * 添加缓存资源实体
	 * @return
	 */
	public boolean addToCacheBySource(CacheEntity cacheEntity);
	/**
	 * 移除表单缓存
	 * @return
	 */
	public boolean removeFromCacheByKey(String key);
	/**
	 * 移除全部表单缓存
	 * @return
	 */
	public boolean removeAllFromCache();
	/**
	 * 刷新表单缓存
	 * @return
	 */
	public boolean freshCache(String key);
	/**
	 * 刷新全部表单缓存
	 * @return
	 */
	public boolean freshAllCache();
	/**
	 * 获取缓存对象
	 * @return
	 */
	public Object getCacheObject(String key);
	/**
	 * 获取缓存对象数量
	 * @return
	 */
	public int getCacheLength();
	/**
	 * 获取缓存占用内存
	 * @return
	 */
	public int getCacheSize();
	
	    
}