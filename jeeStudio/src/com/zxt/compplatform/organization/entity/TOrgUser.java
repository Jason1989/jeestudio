package com.zxt.compplatform.organization.entity;

/**
 * @author bozch
 * 2011-03-15
 * 
 */

public class TOrgUser implements java.io.Serializable {

	// Fields

	private String id;
	private Long oid; //结构id
	private Long userid; //用户id
	private String appId;
	private String parentAppId;

	// Constructors

	/** default constructor */
	public TOrgUser() {
	}

	/** minimal constructor */
	public TOrgUser(String id, Long oid, Long userid) {
		this.id = id;
		this.oid = oid;
		this.userid = userid;
	}

	/** full constructor */
	public TOrgUser(String id, Long oid, Long userid, String appId,
			String parentAppId) {
		this.id = id;
		this.oid = oid;
		this.userid = userid;
		this.appId = appId;
		this.parentAppId = parentAppId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getOid() {
		return this.oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getParentAppId() {
		return this.parentAppId;
	}

	public void setParentAppId(String parentAppId) {
		this.parentAppId = parentAppId;
	}

}