package com.zxt.compplatform.formengine.entity.view;

/**
 * 领导基本信息
 * 
 * @author 007
 */
public class UserLeadBasic {

	/**
	 * 用户id
	 */
	private Long userid;
	/**
	 * 部门id
	 */
	private Long oid;
	/**
	 * 用户名称
	 */
	private String username;
	/**
	 * 用户中文名
	 */
	private String uname;
	/**
	 * 部门名称
	 */
	private String oname;

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}
}
