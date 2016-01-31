package com.zxt.compplatform.formengine.entity.view;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * WorkFlowDataStauts.java 中使用该对象作为条件分支参数对象
 * 
 */
public class Param extends BasicEntity {
	/**
	 * 键
	 */
	private String key;
	/**
	 * 常量 ,变量
	 */
	private String type;//
	/**
	 * 值
	 */
	private String value;
	/**
	 * 排列序号
	 */
	private int sortIndex;
	private String flagType;//where 条件后边字段 条件类型（变量，系统变量..）
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getFlagType() {
		return flagType;
	}

	public void setFlagType(String flagType) {
		this.flagType = flagType;
	}
}
