package com.zxt.compplatform.workflow.entity;

public class TaskFormNodeEntity {

	/**
	 * 实体ID
	 */
	private String taskFormID;
	/**
	 * 流程实例ID
	 */
	private String processInstanceID;
	/**
	 * 任务节点ID
	 */
	private String TaskNodeID;
	/**
	 * 表单ID
	 */
	private String formID;
	/**
	 * 表单名称
	 */
	private String formName;
	/**
	 * 流程实例名称
	 */
	private String processInstanceName;
	/**
	 * 节点名称
	 */
	private String TaskNodeName;
	
	/**
	 * 参与者ID
	 */
	private String userID;
	/**
	 * 参数
	 */
	private String[][] parmers;
	/**
	 * 0 系统，1 自定义
	 */
	private String type;
	/**
	 * 表单排列顺序
	 */
	private String sortIndex;
	/**
	 * 是否显示tab页
	 */
	private String isShowTab;
	/**
	 * 主键
	 */
	private String tfId;
	
	public String getTfId() {
		return tfId;
	}

	public void setTfId(String tfId) {
		this.tfId = tfId;
	}

	public String getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(String sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getIsShowTab() {
		return isShowTab;
	}

	public void setIsShowTab(String isShowTab) {
		this.isShowTab = isShowTab;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String[][] getParmers() {
		return parmers;
	}

	public void setParmers(String[][] parmers) {
		this.parmers = parmers;
	}

	public String getTaskFormID() {
		return taskFormID;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public void setTaskFormID(String taskFormID) {
		this.taskFormID = taskFormID;
	}

	public String getProcessInstanceName() {
		return processInstanceName;
	}

	public void setProcessInstanceName(String processInstanceName) {
		this.processInstanceName = processInstanceName;
	}

	public String getProcessInstanceID() {
		return processInstanceID;
	}

	public void setProcessInstanceID(String processInstanceID) {
		this.processInstanceID = processInstanceID;
	}

	public String getTaskNodeName() {
		return TaskNodeName;
	}

	public void setTaskNodeName(String taskNodeName) {
		TaskNodeName = taskNodeName;
	}

	public String getTaskNodeID() {
		return TaskNodeID;
	}

	public void setTaskNodeID(String taskNodeID) {
		TaskNodeID = taskNodeID;
	}

	public String getFormID() {
		return formID;
	}

	public void setFormID(String formID) {
		this.formID = formID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
