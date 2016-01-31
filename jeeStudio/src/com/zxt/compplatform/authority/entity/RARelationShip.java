package com.zxt.compplatform.authority.entity;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 角色资源关系实体
 * 
 * @author 007
 */
public class RARelationShip extends BasicEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * 角色id
	 */
	private Long roleId;
	/**
	 * 资源id
	 */
	private String rescId;

	public RARelationShip() {
		super();
	}

	public RARelationShip(Long role_id, String resc_id) {
		super();
		this.roleId = role_id;
		this.rescId = resc_id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRescId() {
		return rescId;
	}

	public void setRescId(String rescId) {
		this.rescId = rescId;
	}

}
