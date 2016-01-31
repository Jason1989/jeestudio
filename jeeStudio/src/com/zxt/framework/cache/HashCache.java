/**
 * Copyright© 2010 zxt Co. Ltd.
 * All right reserved.
 * 
 */
package com.zxt.framework.cache;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import com.zxt.framework.common.exceptions.AppRuntimeException;

/**
 * Title: HashCache Description: Create DateTime: 2010-9-10
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public class HashCache {
	int count = 0;

	private List keyList = new Vector();
	private Hashtable cache;

	public HashCache() {
		this.cache = new Hashtable();
	}

	/**
	 * 获取缓存对象
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		checkNull(key);
		if (!this.keyList.contains(key))
			return null;
		return this.cache.get(key);
	}

	/**
	 * 获取缓存对象
	 * 
	 * @param key
	 * @param key
	 * @return
	 */
	public Object get(String key, int refreshPeriod) {
		return get(key);
	}

	/**
	 * 停止更新缓存
	 * 
	 * @param key
	 * @param refreshPeriod
	 * @return
	 */
	public void cancelUpdate(String key) {
	}

	/**
	 * 读取缓存
	 * 
	 * @param key
	 * @param key
	 * @return
	 */
	public Object read(String key) {
		checkNull(key);
		return this.cache.get(key);
	}

	/**
	 * 更新缓存
	 * @param key
	 * @param value
	 */
	public void update(String key, Object value) {
		checkNull(key);
		if (this.keyList.contains(key))
			this.cache.put(key, value);
	}

	/**
	 * 添加缓存
	 * @param key
	 * @param value
	 */
	public void put(String key, Object value) {
		checkNull(key);
		if (!this.keyList.contains(key)) {
			this.keyList.add(key);
			this.count += 1;
		}
		this.cache.put(key, value);
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 */
	public void remove(String key) {
		checkNull(key);
		if (this.keyList.contains(key)) {
			this.keyList.remove(key);
			this.count -= 1;
			this.cache.remove(key);
		}
	}

	public void destroy(String key) {
		this.count = 0;
		this.cache.clear();
	}

	public String toString() {
		return "HashCache[" + super.getClass().getName() + ']';
	}

	public int getCachedCount() {
		return this.count;
	}

	public void checkNull(String key) {
		if ((key == null) || ("".equals(key)))
			throw new AppRuntimeException("");
	}

	public void clear() {

	}

	public void destroyAll() {

	}

	public void refreshAll() {

	}

}