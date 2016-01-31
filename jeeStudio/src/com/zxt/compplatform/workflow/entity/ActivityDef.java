package com.zxt.compplatform.workflow.entity;

public class ActivityDef {

	private int activityDefId; // 获得活动定义编号
	private int appId; // 获得应用程序编号
	private String desc; // 获得注释信息号
	private int formId; // 获得FORM编号
	private int modelId; // 获得模型运行编号
	private String name; // 获得活动定义名
	private String participant; // 获得活动参与者
	private int participantType; // 获得活动参与者类型
	private int previous; // 获得回退标志
	private int processDefId; // 获得过程定义编号
	private String subprocessFileName; // 获得子过程所在文件名
	private int subprocessId; // 获得子过程编号
	private int timeLimit; // 获得超时时间
	private int timeLimitType; // 获得超时时间类型
	private String timeUnit; // 获得超时时间单位

	public int getActivityDefId() {
		return activityDefId;
	}

	public void setActivityDefId(int activityDefId) {
		this.activityDefId = activityDefId;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public int getParticipantType() {
		return participantType;
	}

	public void setParticipantType(int participantType) {
		this.participantType = participantType;
	}

	public int getPrevious() {
		return previous;
	}

	public void setPrevious(int previous) {
		this.previous = previous;
	}

	public int getProcessDefId() {
		return processDefId;
	}

	public void setProcessDefId(int processDefId) {
		this.processDefId = processDefId;
	}

	public String getSubprocessFileName() {
		return subprocessFileName;
	}

	public void setSubprocessFileName(String subprocessFileName) {
		this.subprocessFileName = subprocessFileName;
	}

	public int getSubprocessId() {
		return subprocessId;
	}

	public void setSubprocessId(int subprocessId) {
		this.subprocessId = subprocessId;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public int getTimeLimitType() {
		return timeLimitType;
	}

	public void setTimeLimitType(int timeLimitType) {
		this.timeLimitType = timeLimitType;
	}

	public String getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

}
