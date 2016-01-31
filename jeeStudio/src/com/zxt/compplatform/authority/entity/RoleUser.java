package com.zxt.compplatform.authority.entity;

import com.zxt.framework.common.entity.BasicEntity;

/**
 * 角色用户实体
 * 
 * @author 007
 */
public class RoleUser extends BasicEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 关联主键
	 */
	private String id;
	/**
	 * 角色id
	 */
	private Long rid;
	/**
	 * 用户id
	 */
	private Long userid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

}
