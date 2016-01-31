package com.zxt.compplatform.workflow.entity;

public class ActivityTabSettingVo {
	private String mid;
	private String processId;
	private String activityId;
	private String toActivityId;// 前驱
	private String url;
	private String title;
	private String sortIndex;
	private String id;
	private String isMainTable;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getToActivityId() {
		return toActivityId;
	}

	public void setToActivityId(String toActivityId) {
		this.toActivityId = toActivityId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(String sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsMainTable() {
		return isMainTable;
	}

	public void setIsMainTable(String isMainTable) {
		this.isMainTable = isMainTable;
	}

}
