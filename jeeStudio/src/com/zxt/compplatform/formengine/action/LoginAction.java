package com.zxt.compplatform.formengine.action;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.workflow.dao.impl.NameridWorkFlowDaoImpl;

/**
 * 登录操作Action
 * @author 007
 */
public class LoginAction extends ActionSupport {

	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute(){
		try {
			
			String userid = NameridWorkFlowDaoImpl.namefindid(username);
			ServletActionContext.getRequest().getSession().setAttribute("userId", userid);
			ServletActionContext.getRequest().getSession().setAttribute("userName", username);
			
			return "login";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ERROR;
	}
	
	/**
	 * 登出
	 * @return
	 */
	public String logout(){
		try {
			ServletActionContext.getRequest().getSession().invalidate();
			return "logout";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ERROR;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
