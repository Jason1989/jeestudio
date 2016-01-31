package com.zxt.compplatform.formengine.entity.view;

/**
 * 角色下的用户列表
 */
public class UserUnderRoleBasic {

	/**
	 * 用户id
	 */
	private Integer userid;
	/**
	 * 用户中文名
	 */
	private String uname;
	/**
	 * 部门机构名称
	 */
	private String oname;
	/**
	 * 级别名称
	 */
	private String levelname;
	
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
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
	public String getLevelname() {
		return levelname;
	}
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}
	
}
