package com.zxt.compplatform.authority.entity;

/**
 * 用户角色关系实体
 * 
 * @author 007
 */
public class OrgRole {
	/**
	 * 组织机构与角色关系表主键
	 */
	private String pkid;
	/**
	 * 组织机构与角色关系表组织结构id
	 */
	private String orgid;
	/**
	 * 组织机构与角色关系表角色id
	 */
	private String roleid;

	// construct
	public String getPkid() {
		return pkid;
	}

	public void setPkid(String pkid) {
		this.pkid = pkid;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

}
