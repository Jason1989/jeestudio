package com.zxt.compplatform.formengine.entity.view;

import java.util.List;

/**
 * 系统菜单
 * 
 * @author 007
 */
public class SystemMenu {
	/**
	 * 菜单 id
	 */
	private String menuId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 图标地址或者样式
	 */
	private String imageUrl;
	/**
	 * 资源类型
	 */
	private String resType;
	/**
	 * 资源统一资源定位符
	 */
	private String url;
	/**
	 * 选项卡
	 */
	private List tabMenuList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List getTabMenuList() {
		return tabMenuList;
	}

	public void setTabMenuList(List tabMenuList) {
		this.tabMenuList = tabMenuList;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
