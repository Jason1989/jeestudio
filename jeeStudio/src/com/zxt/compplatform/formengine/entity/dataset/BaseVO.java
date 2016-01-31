package com.zxt.compplatform.formengine.entity.dataset;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 基础类vo
 * 
 * @author 007
 */
public class BaseVO extends BasicEntity {

	/**
	 * 名称
	 */
	private String name;
	/**
	 * 类型
	 */
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
