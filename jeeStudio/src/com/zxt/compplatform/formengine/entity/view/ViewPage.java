package com.zxt.compplatform.formengine.entity.view;

import java.util.List;

/**
 * 查看页配置实体
 * 
 * @author 007
 */
public class ViewPage extends BasePage {
	/**
	 * 分组
	 */
	private List groups;

	/**
	 * 按钮
	 */
	private List button;

	/**
	 * 查询sql
	 */
	private String findSql;

	/**
	 * 查看列
	 */
	private List viewColumn;

	/**
	 * 查看页参数
	 */
	private List viewPageParams;

	/**
	 * 规则
	 */
	private String jsRules;

	public String getJsRules() {
		return jsRules;
	}

	public void setJsRules(String jsRules) {
		this.jsRules = jsRules;
	}

	public List getGroups() {
		return groups;
	}

	public void setGroups(List groups) {
		this.groups = groups;
	}

	public List getButton() {
		return button;
	}

	public void setButton(List button) {
		this.button = button;
	}

	public List getViewColumn() {
		return viewColumn;
	}

	public void setViewColumn(List viewColumn) {
		this.viewColumn = viewColumn;
	}

	public String getFindSql() {
		return findSql;
	}

	public void setFindSql(String findSql) {
		this.findSql = findSql;
	}

	public List getViewPageParams() {
		return viewPageParams;
	}

	public void setViewPageParams(List viewPageParams) {
		this.viewPageParams = viewPageParams;
	}

}
