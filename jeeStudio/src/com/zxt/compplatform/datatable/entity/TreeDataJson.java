package com.zxt.compplatform.datatable.entity;

import java.util.List;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 树数据实体
 * 
 * @author 007
 */
public class TreeDataJson extends BasicEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 名称
	 */
	private String text;
	/**
	 * 是否选中
	 */
	private boolean checked;

	/**
	 * 状态
	 */
	private String state;
	/**
	 * 子节点
	 */
	private List children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}
}
