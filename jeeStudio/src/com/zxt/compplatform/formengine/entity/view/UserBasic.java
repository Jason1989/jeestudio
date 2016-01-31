package com.zxt.compplatform.formengine.entity.view;

/**
 * 用户基础信息
 * 
 * @author 007
 */
public class UserBasic {

	/**
	 * 用户id
	 */
	private Long userid;
	/**
	 * 机构id
	 */
	private Long oid;
	/**
	 * 级别id
	 */
	private Long levelid;
	/**
	 * 机构名称
	 */
	private String oname;
	/**
	 * 用户名（帐号）
	 */
	private String username;
	/**
	 * 用户中文名称
	 */
	private String uname;
	/**
	 * 级别名称
	 */
	private String levelname;
	/**
	 * 角色名
	 */
	private String rname; // 
	/**
	 * 
	 */
	private Long num;
	/**
	 * 是否假删除
	 */
	private String isPseudoDeleted;//

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

	public Long getLevelid() {
		return levelid;
	}

	public void setLevelid(Long levelid) {
		this.levelid = levelid;
	}

	public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
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

	public String getLevelname() {
		return levelname;
	}

	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getIsPseudoDeleted() {
		return isPseudoDeleted;
	}

	public void setIsPseudoDeleted(String isPseudoDeleted) {
		this.isPseudoDeleted = isPseudoDeleted;
	}

}
