package com.zxt.compplatform.formengine.entity.view;

import java.util.List;

/**
 * 选项卡菜单
 * 
 * @author Administrator
 * 
 */
public class TabMenu {

	/**
	 * 标题
	 */
	private String title;
	/**
	 * 菜单json
	 */
	private String json;

	/**
	 * 是否默认被选中
	 */
	private boolean selected;

	/**
	 * 标签列表
	 */
	private List tabList;

	/**
	 * 序号
	 */
	private String row_num;

	public List getTabList() {
		return tabList;
	}

	public void setTabList(List tabList) {
		this.tabList = tabList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getRow_num() {
		return row_num;
	}

	public void setRow_num(String row_num) {
		this.row_num = row_num;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
