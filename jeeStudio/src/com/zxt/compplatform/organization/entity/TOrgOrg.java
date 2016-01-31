package com.zxt.compplatform.organization.entity;

/**
 * @author bozch
 * 2011-03-15
 * 
 */

public class TOrgOrg implements java.io.Serializable {

	// Fields

	private String id;
	private Long upid; //父类id
	private Long downid; //子类id
	private String appId;
	private String parentAppId;

	// Constructors

	/** default constructor */
	public TOrgOrg() {
	}

	/** minimal constructor */
	public TOrgOrg(String id, Long upid, Long downid) {
		this.id = id;
		this.upid = upid;
		this.downid = downid;
	}

	/** full constructor */
	public TOrgOrg(String id, Long upid, Long downid, String appId,
			String parentAppId) {
		this.id = id;
		this.upid = upid;
		this.downid = downid;
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

	public Long getUpid() {
		return this.upid;
	}

	public void setUpid(Long upid) {
		this.upid = upid;
	}

	public Long getDownid() {
		return this.downid;
	}

	public void setDownid(Long downid) {
		this.downid = downid;
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