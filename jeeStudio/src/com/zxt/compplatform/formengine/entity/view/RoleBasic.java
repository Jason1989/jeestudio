package com.zxt.compplatform.formengine.entity.view;

/**
 * 角色实体
 * 
 * @author 007
 */
public class RoleBasic {

	/**
	 * 角色id
	 */
	private Long rid;

	/**
	 * 角色名
	 */
	private String rname;

	/**
	 * 角色机能
	 */
	private String rfunction;

	/**
	 * 角色备注
	 */
	private String rnote;

	/**
	 * 角色权限
	 */
	private String rauthority;

	/**
	 * 角色对应用户
	 */
	private String uname;

	/**
	 * rownum
	 */
	private Long num;

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public String getRfunction() {
		return rfunction;
	}

	public void setRfunction(String rfunction) {
		this.rfunction = rfunction;
	}

	public String getRnote() {
		return rnote;
	}

	public void setRnote(String rnote) {
		this.rnote = rnote;
	}

	public String getRauthority() {
		return rauthority;
	}

	public void setRauthority(String rauthority) {
		this.rauthority = rauthority;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

}
