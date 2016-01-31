package com.zxt.framework.dictionary.entity;

import java.io.Serializable;

public class DictionaryCache implements Serializable {

	private static final long serialVersionUID = -2792318992034668095L;

	/**
	 * 主键
	 */
	private String cacheKey;
	
	/**
	 * 是否脏数据 1：是 2：不是 
	 */
	private int isDirty;
	
	public String getCacheKey() {
		return cacheKey;
	}
	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
	public int getIsDirty() {
		return isDirty;
	}
	public void setIsDirty(int isDirty) {
		this.isDirty = isDirty;
	}
	
}
