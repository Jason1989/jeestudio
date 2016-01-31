package com.zxt.compplatform.form.entity;

import java.sql.Blob;

import com.zxt.compplatform.datatable.entity.DataTable;
import com.zxt.framework.common.entity.BasicEntity;

/**
 * 表单实体
 * 
 * @author 007
 */
public class Form extends BasicEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 表单名称
	 */
	private String formName;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 配置
	 */
	private Blob settings;
	/**
	 * 排序指数
	 */
	private Integer sortIndex;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 数据对象
	 */
	private DataTable dataTable;

	/**
	 * 基础配置
	 */
	private String confBasic;
	/**
	 * 数据列配置
	 */
	private String confDatacolumn;
	/**
	 * 参数配置
	 */
	private String confParam;
	/**
	 * 多标签配置
	 */
	private String confTabs;
	/**
	 * 查询配置
	 */
	private String confQuery;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Blob getSettings() {
		return settings;
	}

	public void setSettings(Blob settings) {
		this.settings = settings;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public String getConfBasic() {
		return confBasic;
	}

	public void setConfBasic(String confBasic) {
		this.confBasic = confBasic;
	}

	public String getConfDatacolumn() {
		return confDatacolumn;
	}

	public void setConfDatacolumn(String confDatacolumn) {
		this.confDatacolumn = confDatacolumn;
	}

	public String getConfParam() {
		return confParam;
	}

	public void setConfParam(String confParam) {
		this.confParam = confParam;
	}

	public String getConfTabs() {
		return confTabs;
	}

	public void setConfTabs(String confTabs) {
		this.confTabs = confTabs;
	}

	public String getConfQuery() {
		return confQuery;
	}

	public void setConfQuery(String confQuery) {
		this.confQuery = confQuery;
	}
}
