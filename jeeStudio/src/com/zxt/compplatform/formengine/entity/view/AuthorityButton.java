package com.zxt.compplatform.formengine.entity.view;

import java.util.List;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 权限按钮
 * 
 * @author 007
 */
public class AuthorityButton extends BasicEntity {
	/**
	 * 主键
	 */
	private String id;//
	/**
	 * 名称
	 */
	private String name;//
	/**
	 * 事件
	 */
	private List EventForButtonList;// 

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

	public List getEventForButtonList() {
		return EventForButtonList;
	}

	public void setEventForButtonList(List eventForButtonList) {
		EventForButtonList = eventForButtonList;
	}
}
