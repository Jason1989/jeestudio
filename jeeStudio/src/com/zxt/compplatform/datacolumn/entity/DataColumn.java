package com.zxt.compplatform.datacolumn.entity;

import com.zxt.compplatform.datatable.entity.DataTable;
import com.zxt.framework.common.entity.BasicEntity;

/**
 * 数据对象列实体
 * @author 007
 */
public class DataColumn extends BasicEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 数据列数据类型
	 */
	private String dataType;
	/**
	 * 长度
	 */
	private Long dataLength;
	/**
	 * 精度
	 */
	private Long precision;
	/**
	 * 范围
	 */
	private Long scale;
	/**
	 * 是否为空
	 */
	private String nullable;
	/**
	 * 默认值
	 */
	private String defaultValue;
	/**
	 * 是否系统字段
	 */
	private String sysColumn;
	/**
	 * 是不是临时字段
	 */
	private String istemp;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 排序号
	 */
	private Integer sortNo;
	/**
	 * 所属的数据对象
	 */
	private DataTable dataTable;
	/**
	 * 是否主键
	 */
	private Integer isPrimaryKey;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIstemp() {
		return istemp;
	}
	public void setIstemp(String istemp) {
		this.istemp = istemp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Long getDataLength() {
		return dataLength;
	}
	public void setDataLength(Long dataLength) {
		this.dataLength = dataLength;
	}
	public Long getPrecision() {
		return precision;
	}
	public void setPrecision(Long precision) {
		this.precision = precision;
	}
	public Long getScale() {
		return scale;
	}
	public void setScale(Long scale) {
		this.scale = scale;
	}

	public String getNullable() {
		return nullable;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getSysColumn() {
		return sysColumn;
	}
	public void setSysColumn(String sysColumn) {
		this.sysColumn = sysColumn;
	}
	public Integer getSortNo() {
		return sortNo;
	}
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	public DataTable getDataTable() {
		return dataTable;
	}
	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}
	public Integer getIsPrimaryKey() {
		return isPrimaryKey;
	}
	public void setIsPrimaryKey(Integer isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}
}
