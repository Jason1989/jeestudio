package com.zxt.compplatform.authority.entity;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 权限实体
 * 
 * @author 007
 */
public class Authority extends BasicEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String ajaxTreeId;
	/**
	 * 名称
	 */
	private String text;
	/**
	 * 父节点的id
	 */
	private String parentId;
	/**
	 * 资源地址
	 */
	private String url;
	/**
	 * 资源级别
	 */
	private Integer menuLevel;
	/**
	 * 资源序号
	 */
	private Integer menuSort;
	/**
	 * 系统主键id
	 */
	private String appId;
	/**
	 * 系统父主键id
	 */
	private String parentAppId;

	public Authority() {
		super();
	}

	public Authority(String ajax_tree_id, String text, String parent_id,
			String url, Integer menu_level, Integer menu_sort, String app_id,
			String parent_app_id) {
		super();
		this.ajaxTreeId = ajax_tree_id;
		this.text = text;
		this.parentId = parent_id;
		this.url = url;
		this.menuLevel = menu_level;
		this.menuSort = menu_sort;
		this.appId = app_id;
		this.parentAppId = parent_app_id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAjaxTreeId() {
		return ajaxTreeId;
	}

	public void setAjaxTreeId(String ajaxTreeId) {
		this.ajaxTreeId = ajaxTreeId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}
	public void setMenuLevel(Long menuLevel) {
		this.menuLevel = menuLevel.intValue();
	}
	public Integer getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
	}
	
	public void setMenuSort(Long menuSort) {
		this.menuSort = menuSort.intValue();
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getParentAppId() {
		return parentAppId;
	}

	public void setParentAppId(String parentAppId) {
		this.parentAppId = parentAppId;
	}
}
