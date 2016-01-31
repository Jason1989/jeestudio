package com.zxt.compplatform.formengine.entity.database;
/**
 * 
 * @author guoweixin
 * @Description TODO  表单操作日志。添加，删除记录
 * @created Feb 26, 2013 4:52:24 PM
 * @History 
 * @version v1.0
 */
public class SystemOperatorLog {
	/**
	 * 登录主键   SQLSERVER是：自增的
	 */
	private String id;
	/**
	 * 登录帐号
	 */
	private String loginName; //
	/**
	 * 登录人角色名
	 */
	private String roleName; //
	/**
	 * 操作时间
	 */
	private String operDate;
	/**
	 * 操作表单名称
	 */
	private String operFormname;
	/**
	 * 操作表单ID
	 */
	private String operFormid;
	/**
	 * 操作的动作
	 */
	private String operAction;
	
	/**
	 * 操作者IP
	 */
	private String operip; //
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getOperDate() {
		return operDate;
	}
	public void setOperDate(String operDate) {
		this.operDate = operDate;
	}
	public String getOperFormname() {
		return operFormname;
	}
	public void setOperFormname(String operFormname) {
		this.operFormname = operFormname;
	}
	public String getOperAction() {
		return operAction;
	}
	public void setOperAction(String operAction) {
		this.operAction = operAction;
	}
	public String getOperFormid() {
		return operFormid;
	}
	public void setOperFormid(String operFormid) {
		this.operFormid = operFormid;
	}
	public String getOperip() {
		return operip;
	}
	public void setOperip(String operip) {
		this.operip = operip;
	}
	
}
