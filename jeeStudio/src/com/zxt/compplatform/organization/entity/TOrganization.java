package com.zxt.compplatform.organization.entity;

/**
 * @author bozch
 * 2011-03-15
 * 
 */
public class TOrganization implements java.io.Serializable {

	// Fields
	/**
	 * 角色组织机构状态
	 */
	private String roleclassify;
	/**
	 * 部门id
	 */
	private Long oid;
	
	/**
	 * 部门名称
	 */
	private String oname;
	
	/**
	 * 主管
	 */
	private String lead;
	
	/**
	 * 职能1000
	 */
	private String ofunction;
	
	/**
	 * 电话号码
	 */
	private String tel;
	
	/**
	 * 传真
	 */
	private String fax;
	
	/**
	 * 电子邮件
	 */
	private String oemail;
	
	/**
	 * 地址1000
	 */
	private String onote;
	
	/**
	 * 
	 */
	private String appId;
	
	/**
	 * 
	 */
	private String parentAppId;
	/**
	 * 部门编号 
	 */
	private String orgno;
	
	/**
	 * 部门类型
	 */
	private String orgcate;
	/**
	 * 最后更新时间
	 */
	private String endupdatetime;
	
	// Constructors

	

	

	/** default constructor */
	public TOrganization() {
	}

	/** minimal constructor */
	public TOrganization(Long oid, String oname) {
		this.oid = oid;
		this.oname = oname;
	}

	/** full constructor */
	public TOrganization(Long oid, String oname, String lead, String ofunction,
			String tel, String fax, String oemail, String onote,String orgno,String orgcate,String endupdatetime , String appId,
			String parentAppId) {
		this.oid = oid;
		this.oname = oname;
		this.lead = lead;
		this.ofunction = ofunction;
		this.tel = tel;
		this.fax = fax;
		this.oemail = oemail;
		this.onote = onote;
		this.orgno = orgno;
		this.orgcate = orgcate;
		this.endupdatetime = endupdatetime;
		this.appId = appId;
		this.parentAppId = parentAppId;
	}

	// Property accessors

	public Long getOid() {
		return this.oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getOname() {
		return this.oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}

	public String getLead() {
		return this.lead;
	}

	public void setLead(String lead) {
		this.lead = lead;
	}

	public String getOfunction() {
		return this.ofunction;
	}

	public void setOfunction(String ofunction) {
		this.ofunction = ofunction;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getOemail() {
		return this.oemail;
	}

	public void setOemail(String oemail) {
		this.oemail = oemail;
	}

	public String getOnote() {
		return this.onote;
	}

	public void setOnote(String onote) {
		this.onote = onote;
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
	public String getOrgno() {
		return orgno;
	}

	public void setOrgno(String orgno) {
		this.orgno = orgno;
	}

	public String getOrgcate() {
		return orgcate;
	}

	public void setOrgcate(String orgcate) {
		this.orgcate = orgcate;
	}
	public String getEndupdatetime() {
		return endupdatetime;
	}

	public void setEndupdatetime(String endupdatetime) {
		this.endupdatetime = endupdatetime;
	}

	public String getRoleclassify() {
		return roleclassify;
	}

	public void setRoleclassify(String roleclassify) {
		this.roleclassify = roleclassify;
	}
}