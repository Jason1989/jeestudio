package com.zxt.compplatform.formengine.entity.view;

/**
 * 查询列实体
 * 
 * @author 007
 */
public class QueryColumn extends EditColumn {

	/**
	 * 控件类型
	 */
	private String type;
	/**
	 * 表名称
	 */
	private String tableName;
	/**
	 * 文本
	 */
	private String text;
	/**
	 * 查询类型
	 */
	private Integer serchType = new Integer(0);
	/**
	 * 默认为false
	 */
	private Boolean exclusiveLine = new Boolean(false);// 
	/**
	 * 操作类型取值 1:等于, 2:大于, 3:小于, 4:like, 5:in, 6:between // 默认 4
	 */
	private int operateType;// 
	/**
	 * 日期格式
	 */
	private String dateformat;//
	/**
	 * 排序
	 */
	private String listSort;
	/**
	 * 组合查询区域 下拉选 下拉树的数据
	 */
	private String dicDataJson;

	public String getDicDataJson() {
		return dicDataJson;
	}

	public void setDicDataJson(String dicDataJson) {
		this.dicDataJson = dicDataJson;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSerchType() {
		return serchType;
	}

	public void setSerchType(Integer serchType) {
		this.serchType = serchType;
	}

	public Boolean getExclusiveLine() {
		return exclusiveLine;
	}

	public void setExclusiveLine(Boolean exclusiveLine) {
		this.exclusiveLine = exclusiveLine;
	}

	public int getOperateType() {
		return operateType;
	}

	public void setOperateType(int operateType) {
		this.operateType = operateType;
	}

	public String getDateformat() {
		return dateformat;
	}

	public void setDateformat(String dateformat) {
		this.dateformat = dateformat;
	}

	public String getListSort() {
		return listSort;
	}

	public void setListSort(String listSort) {
		this.listSort = listSort;
	}

}
