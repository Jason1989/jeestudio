package com.zxt.compplatform.menu.entity;

import java.util.List;
import java.util.Map;

public class TreeDataJson implements Comparable {
	private String id;
	private String text;
	private Map attributes;
	private boolean checked;
	private String state;
	private String iconCls = null;
	private List children;
	private long sortIndex;

	
	public long getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(long sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

	public Map getAttributes() {
		return attributes;
	}

	public void setAttributes(Map attributes) {
		this.attributes = attributes;
	}

	public int compareTo(Object o) {
		if(o instanceof TreeDataJson){
			TreeDataJson myTreeJson = (TreeDataJson)o;
			if(this.sortIndex == myTreeJson.getSortIndex()){
				return 0;
			}else if(this.sortIndex > myTreeJson.getSortIndex()){
				return 1;
			}else{
				return -1;
			}
		}else{
		   return -1;
		}
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}


	
}
