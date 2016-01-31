package com.zxt.compplatform.formengine.entity.view;

import java.util.List;

/**
 * 数据列实体
 * 
 * @author 007
 */
public class ColumnData extends Column {
	
	/**
	 * 页面表单组件类型
	 * 1、input
	 * 2、numberbox
	 * 3、select下拉框
	 * 4、combobox
	 * 5、radio
	 * 6、CHECKBOX
	 */
	public static int DATACOLUMN_UI_TYPE_INPUT = 1;
	public static int DATACOLUMN_UI_TYPE_NUMBER = 2;
	public static int DATACOLUMN_UI_TYPE_SELECT = 3;
	public static int DATACOLUMN_UI_TYPE_COMBOBOX = 4;
	public static int DATACOLUMN_UI_TYPE_RADIO = 5;
	public static int DATACOLUMN_UI_TYPE_CHECKBOX = 6;
	
	/**
	 * 类型
	 */
	private int type;
	/**
	 * 值
	 */
	private String value;
	/**
	 * 列表单
	 */
	private TextColumn text;
	/**
	 * 编辑模式
	 */
	private EditMode editMode;
	/**
	 * 数据字典id
	 */
	private String dictionaryId;
	/**
	 * 数据字典
	 */
	private String dictionary;
	/**
	 * 事件
	 */
	private String event;
	/**
	 * 是否需要
	 */
	private Boolean needs;
	/**
	 * 字段类型，ex：数值，大文本，货币
	 */
	private String fieldDataType;// 
	/**
	 * 公式
	 */
	private List formula;// 

	public TextColumn getText() {
		return text;
	}

	public void setText(TextColumn text) {
		this.text = text;
	}

	public EditMode getEditMode() {
		return editMode;
	}

	public void setEditMode(EditMode editMode) {
		this.editMode = editMode;
	}

	public String getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(String dictionaryId) {
		this.dictionaryId = dictionaryId;
	}

	public String getDictionary() {
		return dictionary;
	}

	public void setDictionary(String dictionary) {
		this.dictionary = dictionary;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getFieldDataType() {
		return fieldDataType;
	}

	public void setFieldDataType(String fieldDataType) {
		this.fieldDataType = fieldDataType;
	}

	public List getFormula() {
		return formula;
	}

	public void setFormula(List formula) {
		this.formula = formula;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Boolean getNeeds() {
		return needs;
	}

	public void setNeeds(Boolean needs) {
		this.needs = needs;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
