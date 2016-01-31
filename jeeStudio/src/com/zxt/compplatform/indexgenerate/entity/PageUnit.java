package com.zxt.compplatform.indexgenerate.entity;

import java.io.Serializable;

/**
 * 对应数据库的xml存储
 * 
 * @author Dexpo
 * 
 */
public class PageUnit implements Serializable{
	private String id;
	private String pagexml;
	private String name;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPagexml() {
		return pagexml;
	}

	public void setPagexml(String pagexml) {
		this.pagexml = pagexml;
	}

}
