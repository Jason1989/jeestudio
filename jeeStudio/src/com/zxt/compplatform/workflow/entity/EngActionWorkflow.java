package com.zxt.compplatform.workflow.entity;

public class EngActionWorkflow {
	
	private String id;
	private String isBundling;
	private String processId;
	private String precursor;
	private String context;
	private String remark;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getPrecursor() {
		return precursor;
	}
	public void setPrecursor(String precursor) {
		this.precursor = precursor;
	}
	public String getContext() {
		return context;
	}
	public String getIsBundling() {
		return isBundling;
	}
	public void setIsBundling(String isBundling) {
		this.isBundling = isBundling;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
