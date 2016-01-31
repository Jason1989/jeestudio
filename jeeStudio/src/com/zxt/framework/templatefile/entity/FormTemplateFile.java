package com.zxt.framework.templatefile.entity;


import java.io.File;
import java.util.Date;

import com.zxt.framework.common.entity.BasicEntity;

public class FormTemplateFile extends BasicEntity{

	private static final long serialVersionUID = 3688063349684264197L;
	
	private String id;//模板ID
	private String dgId;//数据对象分组ID
	private String name;//模板名称
	private File data;//模板数据
	private String remark;//标记
	private String type;//模板类型
	private Date updateTime;//模板修改时间
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDgId() {
		return dgId;
	}
	public void setDgId(String dgId) {
		this.dgId = dgId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public File getData() {
		return data;
	}
	public void setData(File data) {
		this.data = data;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
