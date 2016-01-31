package com.zxt.compplatform.datatable.entity;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.framework.common.entity.BasicEntity;

/**
 * 数据对象（table）
 * 
 * @author 007
 */
public class DataTable extends BasicEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 类型（表、视图、触发器。。。）
	 */
	private String type;
	/**
	 * 命名空间
	 */
	private String spacename;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 用友者
	 */
	private String owner;
	/**
	 * 是否锁定
	 */
	private String locked;
	/**
	 * 是否在使用
	 */
	private String inused;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 序号
	 */
	private Integer sortNo;
	/**
	 * 数据源
	 */
	private DataSource dataSource;
	/**
	 * 数据分组
	 */
	private DataObjectGroup dataObjectGroup;

	public DataTable() {
	}

	public DataTable(String id, String name) {
		this.id = id;
		this.name = name;
	}

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSpacename() {
		return spacename;
	}

	public void setSpacename(String spacename) {
		this.spacename = spacename;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getInused() {
		return inused;
	}

	public void setInused(String inused) {
		this.inused = inused;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataObjectGroup getDataObjectGroup() {
		return dataObjectGroup;
	}

	public void setDataObjectGroup(DataObjectGroup dataObjectGroup) {
		this.dataObjectGroup = dataObjectGroup;
	}

}
