package com.zxt.compplatform.datatable.entity;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 数据对象菜单
 * 
 * @author 007
 */
public class DataObjectMenu extends BasicEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 类型
	 */
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
