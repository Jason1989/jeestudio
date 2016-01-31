package com.zxt.compplatform.formengine.entity.database;

/**
 * 
 * 用户登录信息日志
 * 
 * @author 005
 * @version 1.00
 */
public class SystemLog {
	/**
	 * 登录主键
	 */
	private String log_id;
	/**
	 * 登录帐号
	 */
	private String user_loginName; //
	/**
	 * 登录人角色名
	 */
	private String user_roleName; //
	/**
	 * 登录人姓名
	 */
	private String user_name; //
	/**
	 * 登录人ID
	 */
	private String user_id; //
	/**
	 * 登录人IP
	 */
	private String log_ip; //
	/**
	 * 登录时间
	 */
	private String log_time; //

	public String getUser_loginName() {
		return user_loginName;
	}

	public void setUser_loginName(String user_loginName) {
		this.user_loginName = user_loginName;
	}

	public String getUser_roleName() {
		return user_roleName;
	}

	public void setUser_roleName(String user_roleName) {
		this.user_roleName = user_roleName;
	}

	public String getLog_id() {
		return log_id;
	}

	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getLog_ip() {
		return log_ip;
	}

	public void setLog_ip(String log_ip) {
		this.log_ip = log_ip;
	}

	public String getLog_time() {
		return log_time;
	}

	public void setLog_time(String log_time) {
		this.log_time = log_time;
	}

}
