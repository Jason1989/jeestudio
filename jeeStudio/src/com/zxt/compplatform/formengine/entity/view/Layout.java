package com.zxt.compplatform.formengine.entity.view;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * Title: Layout Description: 界面布局 Create DateTime: 2010-9-27
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public class Layout extends BasicEntity {

	/**
	 * 名称
	 */
	private String name;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * x
	 */
	private String xindex;
	/**
	 * y
	 */
	private String yindex;
	/**
	 * x坐标跨度
	 */
	private String xspan;
	/**
	 * y坐标跨度
	 */
	private String yspan;
	/**
	 * 样式
	 */
	private String style;
	/**
	 * 表格id
	 */
	private String tabId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getXindex() {
		return xindex;
	}

	public void setXindex(String xindex) {
		this.xindex = xindex;
	}

	public String getYindex() {
		return yindex;
	}

	public void setYindex(String yindex) {
		this.yindex = yindex;
	}

	public String getXspan() {
		return xspan;
	}

	public void setXspan(String xspan) {
		this.xspan = xspan;
	}

	public String getYspan() {
		return yspan;
	}

	public void setYspan(String yspan) {
		this.yspan = yspan;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

}
