package com.zxt.compplatform.formengine.entity.view;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 编辑页规则引擎
 * 
 * @author 007
 */
public class EditRulesEngin extends BasicEntity {

	/**
	 * js语句
	 */
	private String rulesService;// 
	/**
	 * 
	 */
	private String rulesRowsParmer;
	/**
	 * 事件类型
	 */
	private String[] eventTypes;// 
	/**
	 * 是否级联
	 */
	private String is_jilian;// 
	/**
	 * 级联的列
	 */
	private String jilian_column;//
	/**
	 * 级联列的数据字典id
	 */
	private String jilian_column_dictionaryId;// 

	public String getIs_jilian() {
		return is_jilian;
	}

	public void setIs_jilian(String is_jilian) {
		this.is_jilian = is_jilian;
	}

	public String getJilian_column() {
		return jilian_column;
	}

	public void setJilian_column(String jilian_column) {
		this.jilian_column = jilian_column;
	}

	public String getJilian_column_dictionaryId() {
		return jilian_column_dictionaryId;
	}

	public void setJilian_column_dictionaryId(String jilian_column_dictionaryId) {
		this.jilian_column_dictionaryId = jilian_column_dictionaryId;
	}

	public String getRulesService() {
		return rulesService;
	}

	public void setRulesService(String rulesService) {
		this.rulesService = rulesService;
	}

	public String getRulesRowsParmer() {
		return rulesRowsParmer;
	}

	public void setRulesRowsParmer(String rulesRowsParmer) {
		this.rulesRowsParmer = rulesRowsParmer;
	}

	public String[] getEventTypes() {
		return eventTypes;
	}

	public void setEventTypes(String[] eventTypes) {
		this.eventTypes = eventTypes;
	}

}
