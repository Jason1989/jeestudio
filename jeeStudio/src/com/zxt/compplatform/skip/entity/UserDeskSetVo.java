package com.zxt.compplatform.skip.entity;

import java.util.List;

public class UserDeskSetVo {
	
	public String userId;
	public List menuSettings;
	public String systemId;
	public String id;
	public String xmlSet;

	public String getXmlSet() {
		return xmlSet;
	}

	public void setXmlSet(String xmlSet) {
		this.xmlSet = xmlSet;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List getMenuSettings() {
		return menuSettings;
	}

	public void setMenuSettings(List menuSettings) {
		this.menuSettings = menuSettings;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
}
