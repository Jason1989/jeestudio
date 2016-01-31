package com.zxt.compplatform.organization.entity;

/**
 * @author bozch
 * 2011-03-15
 * 
 */

public class TOrgInfoHis implements java.io.Serializable {

	// Fields

	private String id;
	private Long oid;
	private Long upOidOld;
	private Long upOidNew;
	private String onameOld;
	private String onameNew;
	private String leadOld;
	private String leadNew;
	private String ofunctionOld;
	private String ofunctionNew;
	private String telOld;
	private String telNew;
	private String faxOld;
	private String faxNew;
	private String oemailOld;
	private String oemailNew;
	private String onoteOld;
	private String onoteNew;
	private String modifyDate;
	private String modifyManagerName;
	private String modifyFlag;
	private String appId;
	private String parentAppId;

	// Constructors

	/** default constructor */
	public TOrgInfoHis() {
	}

	/** minimal constructor */
	public TOrgInfoHis(String id) {
		this.id = id;
	}

	/** full constructor */
	public TOrgInfoHis(String id, Long oid, Long upOidOld, Long upOidNew,
			String onameOld, String onameNew, String leadOld, String leadNew,
			String ofunctionOld, String ofunctionNew, String telOld,
			String telNew, String faxOld, String faxNew, String oemailOld,
			String oemailNew, String onoteOld, String onoteNew,
			String modifyDate, String modifyManagerName, String modifyFlag,
			String appId, String parentAppId) {
		this.id = id;
		this.oid = oid;
		this.upOidOld = upOidOld;
		this.upOidNew = upOidNew;
		this.onameOld = onameOld;
		this.onameNew = onameNew;
		this.leadOld = leadOld;
		this.leadNew = leadNew;
		this.ofunctionOld = ofunctionOld;
		this.ofunctionNew = ofunctionNew;
		this.telOld = telOld;
		this.telNew = telNew;
		this.faxOld = faxOld;
		this.faxNew = faxNew;
		this.oemailOld = oemailOld;
		this.oemailNew = oemailNew;
		this.onoteOld = onoteOld;
		this.onoteNew = onoteNew;
		this.modifyDate = modifyDate;
		this.modifyManagerName = modifyManagerName;
		this.modifyFlag = modifyFlag;
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

	public Long getUpOidOld() {
		return this.upOidOld;
	}

	public void setUpOidOld(Long upOidOld) {
		this.upOidOld = upOidOld;
	}

	public Long getUpOidNew() {
		return this.upOidNew;
	}

	public void setUpOidNew(Long upOidNew) {
		this.upOidNew = upOidNew;
	}

	public String getOnameOld() {
		return this.onameOld;
	}

	public void setOnameOld(String onameOld) {
		this.onameOld = onameOld;
	}

	public String getOnameNew() {
		return this.onameNew;
	}

	public void setOnameNew(String onameNew) {
		this.onameNew = onameNew;
	}

	public String getLeadOld() {
		return this.leadOld;
	}

	public void setLeadOld(String leadOld) {
		this.leadOld = leadOld;
	}

	public String getLeadNew() {
		return this.leadNew;
	}

	public void setLeadNew(String leadNew) {
		this.leadNew = leadNew;
	}

	public String getOfunctionOld() {
		return this.ofunctionOld;
	}

	public void setOfunctionOld(String ofunctionOld) {
		this.ofunctionOld = ofunctionOld;
	}

	public String getOfunctionNew() {
		return this.ofunctionNew;
	}

	public void setOfunctionNew(String ofunctionNew) {
		this.ofunctionNew = ofunctionNew;
	}

	public String getTelOld() {
		return this.telOld;
	}

	public void setTelOld(String telOld) {
		this.telOld = telOld;
	}

	public String getTelNew() {
		return this.telNew;
	}

	public void setTelNew(String telNew) {
		this.telNew = telNew;
	}

	public String getFaxOld() {
		return this.faxOld;
	}

	public void setFaxOld(String faxOld) {
		this.faxOld = faxOld;
	}

	public String getFaxNew() {
		return this.faxNew;
	}

	public void setFaxNew(String faxNew) {
		this.faxNew = faxNew;
	}

	public String getOemailOld() {
		return this.oemailOld;
	}

	public void setOemailOld(String oemailOld) {
		this.oemailOld = oemailOld;
	}

	public String getOemailNew() {
		return this.oemailNew;
	}

	public void setOemailNew(String oemailNew) {
		this.oemailNew = oemailNew;
	}

	public String getOnoteOld() {
		return this.onoteOld;
	}

	public void setOnoteOld(String onoteOld) {
		this.onoteOld = onoteOld;
	}

	public String getOnoteNew() {
		return this.onoteNew;
	}

	public void setOnoteNew(String onoteNew) {
		this.onoteNew = onoteNew;
	}

	public String getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyManagerName() {
		return this.modifyManagerName;
	}

	public void setModifyManagerName(String modifyManagerName) {
		this.modifyManagerName = modifyManagerName;
	}

	public String getModifyFlag() {
		return this.modifyFlag;
	}

	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
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