package com.zxt.compplatform.formengine.entity.view;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 字段列的读写类型
 * 
 * @author 007
 */
public class ColumnRoles extends BasicEntity {

	/**
	 * 是否自定义读写类型
	 * 
	 */
	private String isCustomRole;
	/**
	 * 是否可读
	 */
	private String isCustomRoleRead;
	/**
	 * 读主键
	 */
	private String isCustomRoleReadId;
	/**
	 * 读名称
	 */
	private String isCustomRoleReadName;
	/**
	 * 是否可写
	 */
	private String isCustomRoleWrite;
	/**
	 * 写id
	 */
	private String isCustomRoleWriteId;
	/**
	 * 写名称
	 */
	private String isCustomRoleWriteName;

	public String getIsCustomRole() {
		return isCustomRole;
	}

	public void setIsCustomRole(String isCustomRole) {
		this.isCustomRole = isCustomRole;
	}

	public String getIsCustomRoleRead() {
		return isCustomRoleRead;
	}

	public void setIsCustomRoleRead(String isCustomRoleRead) {
		this.isCustomRoleRead = isCustomRoleRead;
	}

	public String getIsCustomRoleReadId() {
		return isCustomRoleReadId;
	}

	public void setIsCustomRoleReadId(String isCustomRoleReadId) {
		this.isCustomRoleReadId = isCustomRoleReadId;
	}

	public String getIsCustomRoleReadName() {
		return isCustomRoleReadName;
	}

	public void setIsCustomRoleReadName(String isCustomRoleReadName) {
		this.isCustomRoleReadName = isCustomRoleReadName;
	}

	public String getIsCustomRoleWrite() {
		return isCustomRoleWrite;
	}

	public void setIsCustomRoleWrite(String isCustomRoleWrite) {
		this.isCustomRoleWrite = isCustomRoleWrite;
	}

	public String getIsCustomRoleWriteId() {
		return isCustomRoleWriteId;
	}

	public void setIsCustomRoleWriteId(String isCustomRoleWriteId) {
		this.isCustomRoleWriteId = isCustomRoleWriteId;
	}

	public String getIsCustomRoleWriteName() {
		return isCustomRoleWriteName;
	}

	public void setIsCustomRoleWriteName(String isCustomRoleWriteName) {
		this.isCustomRoleWriteName = isCustomRoleWriteName;
	}

}
