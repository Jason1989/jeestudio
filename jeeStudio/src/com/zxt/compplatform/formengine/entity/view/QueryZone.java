package com.zxt.compplatform.formengine.entity.view;

import java.util.List;

/**
 * Title: QueryZone Description: 查询区域 Create DateTime: 2010-9-27
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public class QueryZone extends QueryColumn {

	/**
	 * 查询项
	 */
	private List queryColumns; //
	/**
	 * 显示样式
	 */
	private String viewStyle;
	/**
	 * 是否一直显示查询区域
	 */
	private String showQuery;//
	/**
	 * 类型
	 */
	private String type;

	public List getQueryColumns() {
		return queryColumns;
	}

	public void setQueryColumns(List queryColumns) {
		this.queryColumns = queryColumns;
	}

	public String getViewStyle() {
		return viewStyle;
	}

	public void setViewStyle(String viewStyle) {
		this.viewStyle = viewStyle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShowQuery() {
		return showQuery;
	}

	public void setShowQuery(String showQuery) {
		this.showQuery = showQuery;
	}

}
