package com.zxt.compplatform.formengine.entity.view;

import java.util.List;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 按钮事件
 * @author 007
 */
public class EventForButton extends BasicEntity {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 按钮事件类型
	 */
	private String type;// ex:[onclick,ondbclick....]
	/**
	 * 按钮执行时间函数
	 */
	private List JSFunctionList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List getJSFunctionList() {
		return JSFunctionList;
	}

	public void setJSFunctionList(List functionList) {
		JSFunctionList = functionList;
	}

}
