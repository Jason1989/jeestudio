package com.zxt.compplatform.formengine.entity.view;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 树控件
 * 
 * @author 007
 */
public class TreeComponents extends BasicEntity {
	/**
	 * 是否是多选树
	 */
	private Boolean isCheckBox = new Boolean(false);
	/**
	 * 只选择叶子节点
	 */
	private Boolean onlyLeafCheck = new Boolean(false);
	/**
	 * 默认展开层级
	 */
	private String defaultExpandLevel;

	/**
	 * 是否允许过滤
	 */
	private String ifAlowfilter;

	/**
	 * treedata 数据
	 */
	private String jsonTreeData;
	/**
	 * 业务数据ID对应 的name
	 */
	private String conversionDataValue;

	/**
	 * 数据字典id
	 */
	private String dictionaryId;

	public String getDefaultExpandLevel() {
		return defaultExpandLevel;
	}

	public void setDefaultExpandLevel(String defaultExpandLevel) {
		this.defaultExpandLevel = defaultExpandLevel;
	}

	public String getIfAlowfilter() {
		return ifAlowfilter;
	}

	public void setIfAlowfilter(String ifAlowfilter) {
		this.ifAlowfilter = ifAlowfilter;
	}

	public Boolean getIsCheckBox() {
		return isCheckBox;
	}

	public void setIsCheckBox(Boolean isCheckBox) {
		this.isCheckBox = isCheckBox;
	}

	public Boolean getOnlyLeafCheck() {
		return onlyLeafCheck;
	}

	public void setOnlyLeafCheck(Boolean onlyLeafCheck) {
		this.onlyLeafCheck = onlyLeafCheck;
	}

	public String getJsonTreeData() {
		return jsonTreeData;
	}

	public void setJsonTreeData(String jsonTreeData) {
		this.jsonTreeData = jsonTreeData;
	}

	public String getConversionDataValue() {
		return conversionDataValue;
	}

	public void setConversionDataValue(String conversionDataValue) {
		this.conversionDataValue = conversionDataValue;
	}

	public String getDictionaryID() {
		return dictionaryId;
	}

	public void setDictionaryID(String dictionaryId) {
		this.dictionaryId = dictionaryId;
	}

	public String getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(String dictionaryId) {
		this.dictionaryId = dictionaryId;
	}

}
