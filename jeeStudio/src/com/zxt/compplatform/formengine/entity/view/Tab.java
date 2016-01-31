package com.zxt.compplatform.formengine.entity.view;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * Title: Tab Description: 标签页 Create DateTime: 2010-9-27
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public class Tab extends BasicEntity {

	/**
	 * 标题
	 */
	private String title;// title
	/**
	 * 页面
	 */
	private Page pageId;// title
	/**
	 * 是否延迟加载
	 */
	private Boolean lazyLoading;
	/**
	 * 样式
	 */
	private String style;
	/**
	 * 排列序号
	 */
	private int sortIndex;
	/**
	 * 多标签是否可见
	 */
	private Boolean visible;//
	/**
	 * 是否在使用
	 */
	private Boolean isuse = new Boolean(false);
	/**
	 * 多标签属性
	 */
	private String tabId;// APP_ID
	/**
	 * 取值：listPage/editPage/viewPage
	 */
	private String type;//
	/**
	 * url
	 */
	private String url;
	/**
	 * 标签存放的页面
	 */
	private BasePage page;//
	/**
	 * PARENT_APP_ID
	 */
	private String childAppID;//
	/**
	 * 父ID
	 */
	private String parentAppID;//
	/**
	 * 0平台配置的表单 1 自定义表单
	 */
	private int isCustom = 1;//
	/**
	 * 是否主对象
	 */
	private String isMainTable;

	public String getParentAppID() {
		return parentAppID;
	}

	public void setParentAppID(String parentAppID) {
		this.parentAppID = parentAppID;
	}

	public String getChildAppID() {
		return childAppID;
	}

	public void setChildAppID(String childAppID) {
		this.childAppID = childAppID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Page getPageId() {
		return pageId;
	}

	public void setPageId(Page pageId) {
		this.pageId = pageId;
	}

	public int getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int compare(Object o1, Object o2) {
		Tab tab1 = (Tab) o1;
		Tab tab2 = (Tab) o2;
		if (tab1.getSortIndex() > tab2.getSortIndex())
			return 1;
		else if (tab1.getSortIndex() < tab2.getSortIndex())
			return -1;
		else
			return 0;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BasePage getPage() {
		return page;
	}

	public void setPage(BasePage page) {
		this.page = page;
	}

	public Boolean getLazyLoading() {
		return lazyLoading;
	}

	public void setLazyLoading(Boolean lazyLoading) {
		this.lazyLoading = lazyLoading;
	}

	public Boolean getIsuse() {
		return isuse;
	}

	public void setIsuse(Boolean isuse) {
		this.isuse = isuse;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public int getIsCustom() {
		return isCustom;
	}

	public void setIsCustom(int isCustom) {
		this.isCustom = isCustom;
	}

	public String getIsMainTable() {
		return isMainTable;
	}

	public void setIsMainTable(String isMainTable) {
		this.isMainTable = isMainTable;
	}

}
