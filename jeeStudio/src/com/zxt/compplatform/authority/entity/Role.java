package com.zxt.compplatform.authority.entity;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 角色实体
 * 
 * @author 007
 */
public class Role extends BasicEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 角色ID
	 */
	private Long id;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色功能
	 */
	private String rfunction;
	/**
	 * 角色记录
	 */
	private String note;

	public Role() {
		super();
	}

	public Role(Long id, String name, String rfunction, String note) {
		super();
		this.id = id;
		this.name = name;
		this.rfunction = rfunction;
		this.note = note;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRfunction() {
		return rfunction;
	}

	public void setRfunction(String rfunction) {
		this.rfunction = rfunction;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
