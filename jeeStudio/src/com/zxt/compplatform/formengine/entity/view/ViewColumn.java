package com.zxt.compplatform.formengine.entity.view;

import java.util.List;

import com.zxt.compplatform.formengine.entity.database.Dictionary;

/**
 * 查看列表单信息
 * 
 * @author 007
 */
public class ViewColumn extends Column {
	/**
	 * 字段类型，ex：数值，大文本，货币
	 */
	private String fieldDataType;// 
	/**
	 * 是否隐藏
	 */
	private String isHidden;
	/**
	 * 是否只读
	 */
	private String readOnly;
	/**
	 * css样式
	 */
	private String cssClass;
	/**
	 * 排列序号
	 */
	private int sortIndex;
	/**
	 * 是否独占行
	 */
	private String isColspan;// 
	/**
	 * 标题
	 */
	private TextColumn textColumn;// 
	/**
	 * 读写模式
	 */
	private EditMode editMode;
	/**
	 * 注释
	 */
	private Note note;
	/**
	 * 字段值
	 */
	private String data;// 
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 数据字典id
	 */
	private String dictionaryID;
	/**
	 * 数据字典实体
	 */
	private Dictionary dictionary;
	/**
	 * 分组信息
	 */
	private Dictionary group;// 
	/**
	 * 验证规则，确定不可为空等
	 */
	private List validateRules; // 
	/**
	 * 公式
	 */
	private List formula;// 
	/**
	 * 所属列的表名
	 */
	private String tablename;// 

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getFieldDataType() {
		return fieldDataType;
	}

	public void setFieldDataType(String fieldDataType) {
		this.fieldDataType = fieldDataType;
	}

	public String getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(String isHidden) {
		this.isHidden = isHidden;
	}

	public String getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public int getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getIsColspan() {
		return isColspan;
	}

	public void setIsColspan(String isColspan) {
		this.isColspan = isColspan;
	}

	public TextColumn getTextColumn() {
		return textColumn;
	}

	public void setTextColumn(TextColumn textColumn) {
		this.textColumn = textColumn;
	}

	public EditMode getEditMode() {
		return editMode;
	}

	public void setEditMode(EditMode editMode) {
		this.editMode = editMode;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Dictionary getGroup() {
		return group;
	}

	public void setGroup(Dictionary group) {
		this.group = group;
	}

	public List getValidateRules() {
		return validateRules;
	}

	public void setValidateRules(List validateRules) {
		this.validateRules = validateRules;
	}

	public List getFormula() {
		return formula;
	}

	public void setFormula(List formula) {
		this.formula = formula;
	}

}
