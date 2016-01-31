package com.zxt.compplatform.formengine.entity.view;

import java.util.List;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 编辑表单分组
 * 
 * @author 007
 */
public class Group extends BasicEntity {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 布局类型
	 */
	private Layout layout;
	/**
	 * 排序
	 */
	private int sortIndex;
	/**
	 * 是否可见
	 */
	private Boolean visible;
	/**
	 * 数据列
	 */
	private List dataColumns;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public int getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

	public List getDataColumns() {
		return dataColumns;
	}

	public void setDataColumns(List dataColumns) {
		this.dataColumns = dataColumns;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
}
