package com.zxt.compplatform.formengine.entity.view;

import java.util.List;

import com.zxt.compplatform.formengine.entity.database.Dictionary;

/**
 * 列表页中的字段
 * 
 * @author 007
 */
public class ListColumn extends Column {
	/**
	 * 字段列类型
	 */
	public static int DATACOLUMN_UI_TYPE_INPUT = 1;
	public static int DATACOLUMN_UI_TYPE_NUMBER = 2;
	public static int DATACOLUMN_UI_TYPE_SELECT = 3;
	public static int DATACOLUMN_UI_TYPE_COMBOBOX = 4;
	public static int DATACOLUMN_UI_TYPE_RADIO = 5;
	public static int DATACOLUMN_UI_TYPE_CHECKBOX = 6;
	/**
	 * 字段类型，ex：数值，大文本，货币
	 */
	private String fieldDataType;//
	/**
	 * 数据字典的id
	 */
	private String dictionaryID;
	/**
	 * 数据字典数据
	 */
	private Dictionary dictionary;
	/**
	 * 公式
	 */
	private List formula;//
	/**
	 * 表格列
	 */
	private TitleColumn titleColumn;
	/**
	 * 列类型，ex：操作列，数据列
	 */
	private String columnType;//

	/**
	 * 所属列的表名
	 */
	private String tablename;//

	public String getFieldDataType() {
		return fieldDataType;
	}

	public void setFieldDataType(String fieldDataType) {
		this.fieldDataType = fieldDataType;
	}

	public String getDictionaryID() {
		return dictionaryID;
	}

	public void setDictionaryID(String dictionaryID) {
		this.dictionaryID = dictionaryID;
	}

	public Dictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

	public List getFormula() {
		return formula;
	}

	public void setFormula(List formula) {
		this.formula = formula;
	}

	public TitleColumn getTitleColumn() {
		return titleColumn;
	}

	public void setTitleColumn(TitleColumn titleColumn) {
		this.titleColumn = titleColumn;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

}
