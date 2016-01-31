package com.zxt.compplatform.formengine.entity.view;

import java.util.List;
import java.util.Map;

import com.zxt.compplatform.formengine.entity.database.Dictionary;

/**
 * 编辑页字段
 * 
 * @author 007
 */
public class EditColumn extends Column {

	/**
	 * 字段类型，ex：数值，大文本，货币
	 */
	private String fieldDataType;//
	/**
	 * 是否隐藏
	 */
	private String isHidden;
	/**
	 * 是否可读写
	 */
	private String readOnly;
	/**
	 * 样式类名称
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
	 * 是否可编辑
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
	 * 触发事件
	 */
	private EditRulesEngin editRulesEngin;//
	/**
	 * 级联状态
	 */
	private String jilian_status;//
	// private String groupId="0";//默认的分组
	/**
	 * 0 文本框 1 复选框 2 下拉选 3 textarea 4 日期控件 5 FCKeditor 6 ajaxbox-tree 7
	 * ajaxbox-link 8 自动补全控件 9 上传控件 10 word编辑器 11 隐藏域
	 */
	private String type;
	/**
	 * 编辑列 tree配置
	 */
	private TreeComponents treeComponents;
	/**
	 * 数据字典id
	 */
	private String dictionaryID;
	/**
	 * 数据字典
	 */
	private Dictionary dictionary;
	/**
	 * 存放数据字典<key ,value>
	 */
	private Map dictionaryData;//

	/**
	 * 验证规则，确定不可为空等
	 */
	private List validateRules; //
	/**
	 * 公式
	 */
	private List formula;//
	/**
	 * URL
	 */
	private String url;
	/**
	 * 表名称
	 */
	private String tablename;

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

	public String getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public void setValidateRules(List validateRules) {
		this.validateRules = validateRules;
	}

	public void setFormula(List formula) {
		this.formula = formula;
	}

	public List getValidateRules() {
		return validateRules;
	}

	public List getFormula() {
		return formula;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Map getDictionaryData() {
		return dictionaryData;
	}

	public void setDictionaryData(Map dictionaryData) {
		this.dictionaryData = dictionaryData;
	}

	// public String getGroupId() {
	// return groupId;
	// }
	// public void setGroupId(String groupId) {
	// this.groupId = groupId;
	// }
	public TreeComponents getTreeComponents() {
		if (treeComponents == null) {
			treeComponents = new TreeComponents();
		}
		return treeComponents;
	}

	public void setTreeComponents(TreeComponents treeComponents) {
		this.treeComponents = treeComponents;
	}

	public EditRulesEngin getEditRulesEngin() {
		return editRulesEngin;
	}

	public void setEditRulesEngin(EditRulesEngin editRulesEngin) {
		this.editRulesEngin = editRulesEngin;
	}

	public String getJilian_status() {
		return jilian_status;
	}

	public void setJilian_status(String jilian_status) {
		this.jilian_status = jilian_status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

}
