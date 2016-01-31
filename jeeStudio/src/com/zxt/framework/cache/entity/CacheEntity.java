package com.zxt.framework.cache.entity;

import java.util.List;

public class CacheEntity {
	
	private String name;//名称
	private String key;//键
	private String loadType;//加载方式
	private Object values;//缓存返回值
	private String valuesSource;//com.xxx.xxx.test()
	
	private String createTime;//创建时间
	private String createUser;//创建用户
	private String previousFreshTime;//上次刷新时间
	private String desc;//描述
	
	public String getName() {
		return name;
	}
	public String getCreateTime() {
		return createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public String getDesc() {
		return desc;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public void setValues(List values) {
		this.values = values;
	}
	public String getPreviousFreshTime() {
		return previousFreshTime;
	}
	public void setPreviousFreshTime(String previousFreshTime) {
		this.previousFreshTime = previousFreshTime;
	}
	public Object getValues() {
		return values;
	}
	public void setValues(Object values) {
		this.values = values;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
	public String getValuesSource() {
		return valuesSource;
	}
	public void setValuesSource(String valuesSource) {
		this.valuesSource = valuesSource;
	}
	
}
