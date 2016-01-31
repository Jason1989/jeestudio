package com.zxt.compplatform.formengine.entity.view;

import java.util.ArrayList;
import java.util.List;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * Title: Column Description: 界面 Create DateTime: 2010-9-27 colspan=""
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public class Column extends BasicEntity {

	/**
	 * 字段id
	 */
	private String id;//
	/**
	 * 字段名，标题
	 */
	private String name;//
	/**
	 * 字段风格
	 */
	private String style;//
	/**
	 * 布局类型
	 */
	private Layout layout;//
	/**
	 * 是否显示
	 */
	private Boolean visible;//
	/**
	 * 排序
	 */
	private int sortIndex;//
	/**
	 * 是否为主键
	 */
	private boolean primaryKey;//
	/**
	 * 列角色
	 */
	private ColumnRoles roles;
	/**
	 * 事件
	 */
	private List Events = new ArrayList();//

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

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public List getEvents() {
		return Events;
	}

	public void setEvents(String events) {
		Events.add(events);
	}

	public int getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

	public Boolean isVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public boolean getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public ColumnRoles getRoles() {
		return roles;
	}

	public void setRoles(ColumnRoles roles) {
		this.roles = roles;
	}

}
