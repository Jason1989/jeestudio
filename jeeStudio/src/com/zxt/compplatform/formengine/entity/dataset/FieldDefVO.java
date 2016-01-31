package com.zxt.compplatform.formengine.entity.dataset;

/**
 * 字段vo
 * 
 * @author 007
 */
public class FieldDefVO extends BaseVO {

	/**
	 * 来源字段名称
	 */
	private String fromFieldName;// 
	/**
	 * 来源字段位置信息
	 */
	private String fromFieldIndex;// 
	/**
	 * 表单类型
	 */
	private String fromType;
	/**
	 * 来源字段的类型
	 */
	private String fromFieldType;// 
	/**
	 * 来源字段长度
	 */
	private String fromFieldLength;//
	/**
	 * 来源字段规则
	 */
	private String fromFielddRule;// 
	/**
	 * 来源字段是否为主键
	 */
	private String fromFieldIsPrimKey;// 
	/**
	 * 来源字段是否为空
	 */
	private String fromFieldIsNull;// 
	/**
	 * 目标字段的位置
	 */
	private String toFieldIndex;// 
	/**
	 * 目标字段的名称
	 */
	private String toFieldName;//
	/**
	 * 目标字段类型
	 */
	private String toFieldType;// 
	/**
	 * 目标字段长度
	 */
	private String toFieldLength;// 
	/**
	 * 目标字段规则
	 */
	private String toFieldRule;// 
	/**
	 * 字段来源表名
	 */
	private TableVO fromTable;//
	/**
	 * 前缀
	 */
	private String prefix;
	/**
	 * 是否为公式
	 */
	private String isFormula;// 
	/**
	 * 公式类型
	 */
	private String formulaType;// 
	/**
	 * 排序类型
	 */
	private String orderType;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getFromFieldIndex() {
		return fromFieldIndex;
	}

	public void setFromFieldIndex(String fromFieldIndex) {
		this.fromFieldIndex = fromFieldIndex;
	}

	public String getFromFieldName() {
		return fromFieldName;
	}

	public void setFromFieldName(String fromFieldName) {
		this.fromFieldName = fromFieldName;
	}

	public String getToFieldRule() {
		return toFieldRule;
	}

	public void setToFieldRule(String toFieldRule) {
		this.toFieldRule = toFieldRule;
	}

	public String getFromFieldIsNull() {
		return fromFieldIsNull;
	}

	public void setFromFieldIsNull(String fromFieldIsNull) {
		this.fromFieldIsNull = fromFieldIsNull;
	}

	public String getFromFieldIsPrimKey() {
		return fromFieldIsPrimKey;
	}

	public void setFromFieldIsPrimKey(String fromFieldIsPrimKey) {
		this.fromFieldIsPrimKey = fromFieldIsPrimKey;
	}

	public String getFromFieldLength() {
		return fromFieldLength;
	}

	public void setFromFieldLength(String fromFieldLength) {
		this.fromFieldLength = fromFieldLength;
	}

	public String getFromFieldType() {
		return fromFieldType;
	}

	public String toFieldString() {

		String tableName = "";
		if (fromTable != null) {
			tableName = fromTable.getName();
		}
		return " " + tableName + "." + toFieldName + " as " + toFieldName + " ";// 修改
	}

	public String toEditFieldString() {

		String tableName = "";
		if (fromTable != null) {
			tableName = fromTable.getName();
		}
		return " " + tableName + "." + toFieldName + " as " + tableName + "__"
				+ toFieldName + " ";// 修改
	}

	public void setFromFieldType(String fromFieldType) {
		this.fromFieldType = fromFieldType;
	}

	public TableVO getFromTable() {
		return fromTable;
	}

	public void setFromTable(TableVO fromTable) {
		this.fromTable = fromTable;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public String getToFieldIndex() {
		return toFieldIndex;
	}

	public void setToFieldIndex(String toFieldIndex) {
		this.toFieldIndex = toFieldIndex;
	}

	public String getToFieldLength() {
		return toFieldLength;
	}

	public void setToFieldLength(String toFieldLength) {
		this.toFieldLength = toFieldLength;
	}

	public String getToFieldName() {
		return toFieldName;
	}

	public void setToFieldName(String toFieldName) {
		this.toFieldName = toFieldName;
	}

	public String getToFieldType() {
		return toFieldType;
	}

	public void setToFieldType(String toFieldType) {
		this.toFieldType = toFieldType;
	}

	public String getFromFielddRule() {
		return fromFielddRule;
	}

	public void setFromFielddRule(String fromFielddRule) {
		this.fromFielddRule = fromFielddRule;
	}

	public boolean equals(Object obj) {

		if (obj instanceof FieldDefVO) {
			// if (this.fromTable.equals(((FieldDefVO)
			// obj).getFromTable())&&this.fromFieldName.equals(((FieldDefVO)
			// obj).getFromFieldName())&& this.toFieldName.equals(((FieldDefVO)
			// obj).getToFieldName())) {
			if (this.fromFieldName
					.equals(((FieldDefVO) obj).getFromFieldName())
					&& this.toFieldName.equals(((FieldDefVO) obj)
							.getToFieldName())) {
				return true;
			}
		}

		return false;
	}

	public int hashCode() {
		return 0;
	}

	public String getFormulaType() {
		return formulaType;
	}

	public void setFormulaType(String formulaType) {
		this.formulaType = formulaType;
	}

	public String getIsFormula() {
		return isFormula;
	}

	public void setIsFormula(String isFormula) {
		this.isFormula = isFormula;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
}
