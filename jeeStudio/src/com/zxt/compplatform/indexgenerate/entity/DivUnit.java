package com.zxt.compplatform.indexgenerate.entity;

import java.io.Serializable;
import java.util.List;

import com.zxt.compplatform.indexset.entity.ModelPart;
/**
 * 对应前台的一个div块
 * @author Dexpo
 *
 */
public class DivUnit implements Serializable{
	private String divid;//前台div id暂时用于静态的模板
	private String region;
	private String width;
	private String height;
	private ModelPart model;
	private List list;

	public String getDivid() {
		return divid;
	}

	public void setDivid(String divid) {
		this.divid = divid;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public ModelPart getModel() {
		return model;
	}

	public void setModel(ModelPart model) {
		this.model = model;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
}
