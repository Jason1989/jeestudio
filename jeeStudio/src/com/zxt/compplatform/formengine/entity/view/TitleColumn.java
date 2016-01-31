package com.zxt.compplatform.formengine.entity.view;

/**
 * Title: TitleColumn Description: 列表页面字段名称 Create DateTime: 2010-9-27
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public class TitleColumn extends Column {

	/**
	 * 是否可见
	 */
	private String visible;
	/**
	 * 跨列
	 */
	private String colspan;
	/**
	 * 跨行
	 */
	private String rowspan;
	/**
	 * 排序序号
	 */
	private int sortIndex;
	/**
	 * 宽度
	 */
	private String width;
	/**
	 * 对齐方式
	 */
	private String align;
	/**
	 * 对应数据库名称等信息
	 */
	private ListColumn dataColumnName;//
	/**
	 * 字段的规则引擎，自定义js 弹出窗口
	 */
	private String columnrules;// 

	public String getColumnrules() {
		return columnrules;
	}

	public void setColumnrules(String columnrules) {
		this.columnrules = columnrules;
	}

	// public String getStyle() {
	// return style;
	// }
	// public void setStyle(String style) {
	// this.style = style;
	// }
	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getColspan() {
		return colspan;
	}

	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

	public String getRowspan() {
		return rowspan;
	}

	public void setRowspan(String rowspan) {
		this.rowspan = rowspan;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public int getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

	public ListColumn getDataColumnName() {
		return dataColumnName;
	}

	public void setDataColumnName(ListColumn dataColumnName) {
		this.dataColumnName = dataColumnName;
	}
}
