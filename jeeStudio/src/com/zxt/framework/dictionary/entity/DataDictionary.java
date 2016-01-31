package com.zxt.framework.dictionary.entity;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.framework.common.entity.BasicEntity;

public class DataDictionary extends BasicEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String type;
	private String expression;
	private String description;
	private String state;
	private Integer sortNo;
	private DataSource dataSource;
	private DictionaryGroup dictionaryGroup;
	//@GUOWEIXIN 父节点的ID
	private String dic_root_id;
	public DictionaryGroup getDictionaryGroup() {
		return dictionaryGroup;
	}
	public void setDictionaryGroup(DictionaryGroup dictionaryGroup) {
		this.dictionaryGroup = dictionaryGroup;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getSortNo() {
		return sortNo;
	}
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	public String getDic_root_id() {
		return dic_root_id;
	}
	public void setDic_root_id(String dic_root_id) {
		this.dic_root_id = dic_root_id;
	}
	
}
