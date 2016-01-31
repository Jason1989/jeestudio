package com.zxt.compplatform.formengine.entity.database;

import com.zxt.compplatform.datasource.entity.DataSource;
import com.zxt.framework.common.entity.BasicEntity;

/**
 * Title: Column Description: 数据对象 Create DateTime: 2010-9-27
 * 
 * @author xxl
 * @since v1.0
 * 
 */
public class DataObject extends BasicEntity {

	/**
	 * 数据对象id
	 */
	private String tableId;
	/**
	 * 数据对象对应的数据源
	 */
	private DataSource dataSource;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 表空间名称
	 */
	private String tablespaceName;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 拥有者
	 */
	private String owner;
	/**
	 * 是否锁定
	 */
	private String locked;
	/**
	 * 是否在锁定
	 */
	private String inused;
	/**
	 * 类型
	 */
	private String createType;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 创建者
	 */
	private String createor;

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
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

	public String getTablespaceName() {
		return tablespaceName;
	}

	public void setTablespaceName(String tablespaceName) {
		this.tablespaceName = tablespaceName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getCreateType() {
		return createType;
	}

	public void setCreateType(String createType) {
		this.createType = createType;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateor() {
		return createor;
	}

	public void setCreateor(String createor) {
		this.createor = createor;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
