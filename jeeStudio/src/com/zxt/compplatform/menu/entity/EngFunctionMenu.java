package com.zxt.compplatform.menu.entity;

import java.util.Date;

/**
 * EngFunctionMenu entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EngFunctionMenu implements java.io.Serializable {

	// Fields
	public static final long MENU_TYPE_NODE = 0;
	public static final long MENU_TYPE_ROOT = 1;
	
	public static final long MENU_ROOT_PARENT_ID = 0;
	
	
	public static final long MENU_STATE_ENABLE = 0;
	public static final long MENU_STATE_DISABLE = 1;
	
	public static final long MENU_DELETE_FALSE = 0;
	public static final long MENU_DELETE_TURE = 1;
	
	public static final long MENU_BASE_DATA_MANAGE_ID = 1;
	public static final long MENU_FORM_MANAGE_ID = 2;
	public static final long MENU_CODE_GENERATE=5;//代码生成
	
	public static final int MENU_DATA_OBJECT_MANAGE_SORT_INDEX = 1;

	private Long menuId;
	private Long menuParentId;
	private String menuName;
	private String menuUrl;
	private Long menuSort;
	private String menuDescription;
	private Long menuType;
	private Long menuState;
	private Long menuIsDelete;
	private Date menuDeleteDate;
	private String menuIcon;

	// Constructors

	/** default constructor */
	public EngFunctionMenu() {
	}

	/** minimal constructor */
	public EngFunctionMenu(Long menuId) {
		this.menuId = menuId;
	}

	/** full constructor */
	public EngFunctionMenu(Long menuId, Long menuParentId, String menuName,
			String menuUrl, Long menuSort, String menuDescription,
			Long menuType, Long menuState, Long menuIsDelete,
			Date menuDeleteDate) {
		this.menuId = menuId;
		this.menuParentId = menuParentId;
		this.menuName = menuName;
		this.menuUrl = menuUrl;
		this.menuSort = menuSort;
		this.menuDescription = menuDescription;
		this.menuType = menuType;
		this.menuState = menuState;
		this.menuIsDelete = menuIsDelete;
		this.menuDeleteDate = menuDeleteDate;
	}

	// Property accessors

	public Long getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getMenuParentId() {
		return this.menuParentId;
	}

	public void setMenuParentId(Long menuParentId) {
		this.menuParentId = menuParentId;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Long getMenuSort() {
		return this.menuSort;
	}

	public void setMenuSort(Long menuSort) {
		this.menuSort = menuSort;
	}

	public String getMenuDescription() {
		return this.menuDescription;
	}

	public void setMenuDescription(String menuDescription) {
		this.menuDescription = menuDescription;
	}

	public Long getMenuType() {
		return this.menuType;
	}

	public void setMenuType(Long menuType) {
		this.menuType = menuType;
	}

	public Long getMenuState() {
		return this.menuState;
	}

	public void setMenuState(Long menuState) {
		this.menuState = menuState;
	}

	public Long getMenuIsDelete() {
		return this.menuIsDelete;
	}

	public void setMenuIsDelete(Long menuIsDelete) {
		this.menuIsDelete = menuIsDelete;
	}

	public Date getMenuDeleteDate() {
		return this.menuDeleteDate;
	}

	public void setMenuDeleteDate(Date menuDeleteDate) {
		this.menuDeleteDate = menuDeleteDate;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

}