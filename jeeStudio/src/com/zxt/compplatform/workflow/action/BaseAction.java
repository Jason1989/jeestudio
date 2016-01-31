package com.zxt.compplatform.workflow.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

public class BaseAction implements SessionAware {
	protected Map session;

	public void setSession(Map session) {
		this.session = session;
	}

}
