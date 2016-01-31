package com.zxt.compplatform.formengine.entity.view;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 表单组件
 * 
 * @author 007
 */
public class Field extends BasicEntity {
	/**
	 * 数据列
	 */
	private String dataColumn;
	/**
	 * 对其方式：center left right
	 */
	private String align;
	/**
	 * 宽度
	 */
	private String width;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 数据字典id
	 */
	private String dictionaryId;
	/**
	 * 是否可见
	 */
	private String visible;
	/**
	 * 是否主键
	 */
	private String isParmerKey;
	/**
	 * 字段规则
	 */
	private String columnrules;

	public String getColumnrules() {
		return columnrules;
	}

	public void setColumnrules(String columnrules) {
		this.columnrules = columnrules;
	}

	public String getIsParmerKey() {
		return isParmerKey;
	}

	public void setIsParmerKey(String isParmerKey) {
		this.isParmerKey = isParmerKey;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(String dictionaryId) {
		this.dictionaryId = dictionaryId;
	}

	public String getDataColumn() {
		return dataColumn;
	}

	public void setDataColumn(String dataColumn) {
		this.dataColumn = dataColumn;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
