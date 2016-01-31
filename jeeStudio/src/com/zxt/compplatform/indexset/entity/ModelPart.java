package com.zxt.compplatform.indexset.entity;

import java.io.Serializable;

/**
 * 模块实体
 * @author bozch
 */
public class ModelPart implements Serializable{

	private String id;
	private String name;
	private String url;
	private String role;
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
