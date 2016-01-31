package com.zxt.compplatform.formengine.entity.view;

import com.zxt.compplatform.formengine.entity.database.Dictionary;
import com.zxt.framework.common.entity.BasicEntity;

/**
 * Title: CacheInterface Description: 界面控件 Create DateTime: 2010-9-27
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public class EditMode extends BasicEntity {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 
	 */
	private String reminder;
	/**
	 * 数据字典数据
	 */
	private Dictionary data;
	/**
	 * 验证规则
	 */
	private ValidateRule validateRule;//
	/**
	 * 是否存在sql
	 */
	private String isExistSQL;
	/**
	 * 日期
	 */
	private String compDate;
	/**
	 * 
	 */
	private String compCon;
	/**
	 * 指数
	 */
	private String compIndex;
	/**
	 * 数据源是否存在
	 */
	private String isExistDBSource;
	/**
	 * 是否为必填项，默认为不是必填
	 */
	private Boolean required = new Boolean(false);//

	/**
	 * 数据库最大长度
	 */
	private String maxLength;//
	/**
	 * 最小长度
	 */
	private String minLength;
	/**
	 * 用户设定最大长度
	 */
	private String maxLength2;//
	
	private String tdWidth;//
	
	
	private String textWidth;//
	

	public String getMaxLength2() {
		return maxLength2;
	}

	public void setMaxLength2(String maxLength2) {
		this.maxLength2 = maxLength2;
	}

	public String getMinLength() {
		return minLength;
	}

	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}

	public String getCompIndex() {
		return compIndex;
	}

	public void setCompIndex(String compIndex) {
		this.compIndex = compIndex;
	}

	public String getCompCon() {
		return compCon;
	}

	public void setCompCon(String compCon) {
		this.compCon = compCon;
	}

	public String getCompDate() {
		return compDate;
	}

	public void setCompDate(String compDate) {
		this.compDate = compDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Dictionary getData() {
		return data;
	}

	public void setData(Dictionary data) {
		this.data = data;
	}

	public ValidateRule getValidateRule() {
		return validateRule;
	}

	public void setValidateRule(ValidateRule validateRule) {
		this.validateRule = validateRule;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	public String getIsExistSQL() {
		return isExistSQL;
	}

	public void setIsExistSQL(String isExistSQL) {
		this.isExistSQL = isExistSQL;
	}

	public String getIsExistDBSource() {
		return isExistDBSource;
	}

	public void setIsExistDBSource(String isExistDBSource) {
		this.isExistDBSource = isExistDBSource;
	}

	public String getTdWidth() {
		return tdWidth;
	}

	public void setTdWidth(String tdWidth) {
		this.tdWidth = tdWidth;
	}

	public String getTextWidth() {
		return textWidth;
	}

	public void setTextWidth(String textWidth) {
		this.textWidth = textWidth;
	}

	
	
}
