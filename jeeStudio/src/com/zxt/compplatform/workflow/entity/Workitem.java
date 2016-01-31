package com.zxt.compplatform.workflow.entity;

public class Workitem {
	/**
	 * 工作流工作项对象信息实体
	 * 
	 * @author zxt-wudehui
	 */
	private int activityInsId;// 活动实例编号
	private String activityInsName;// 活动实例名
	private int appId;// 应用程序编号
	private Object[][] args;// 获得工作项参数 返回值第一列是名称，第二列是值
	private String completedTime;// 获得工作项完成时间
	private String createdTime;// 获得工作项创建时间
	private String formId;// 获得FORM编号
	private int mId;              // 获得模型运行编号
	private int participant;      // 获得参与者编号
	private int processDefId;    // 获得过程定义编号
	private String processDefName;// 获得过程定义名
	private int processInsId;     // 获得过程实例编号
	private String startTime;     // 获得工作项开始时间
	private String state;         // 获得工作项启动者
	private String timeLimit;  // 获得超时时间值
	private String timeUnit;   // 获得超时时间单位
	private int type;          // 获得工作项类型
	private int workitemId;    // 获得工作项编号
	private boolean isCompeted;   //判断当前工作项是否竞争 
	private String tuihui;	//退回标记
	
	
	/**
	 * @author hegewei
	 * 业务数据ID
	 */
	private String serviceAppId;
	
	public String getServiceAppId() {
		return serviceAppId;
	}
	public void setServiceAppId(String serviceAppId) {
		this.serviceAppId = serviceAppId;
	}
	public String getTuihui() {
		return tuihui;
	}
	public void setTuihui(String tuihui) {
		this.tuihui = tuihui;
	}
	public int getActivityInsId() {
		return activityInsId;
	}
	public void setActivityInsId(int activityInsId) {
		this.activityInsId = activityInsId;
	}
	public String getActivityInsName() {
		return activityInsName;
	}
	public void setActivityInsName(String activityInsName) {
		this.activityInsName = activityInsName;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public Object[][] getArgs() {
		return args;
	}
	public void setArgs(Object[][] args) {
		this.args = args;
	}
	public String getCompletedTime() {
		return completedTime;
	}
	public void setCompletedTime(String completedTime) {
		this.completedTime = completedTime;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public int getMId() {
		return mId;
	}
	public void setMId(int id) {
		mId = id;
	}
	public int getmId() {
		return mId;
	}
	public void setmId(int mId) {
		this.mId = mId;
	}
	public int getParticipant() {
		return participant;
	}
	public void setParticipant(int participant) {
		this.participant = participant;
	}
	public int getProcessDefId() {
		return processDefId;
	}
	public void setProcessDefId(int processDefId) {
		this.processDefId = processDefId;
	}
	public String getProcessDefName() {
		return processDefName;
	}
	public void setProcessDefName(String processDefName) {
		this.processDefName = processDefName;
	}
	public int getProcessInsId() {
		return processInsId;
	}
	public void setProcessInsId(int processInsId) {
		this.processInsId = processInsId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String getTimeUnit() {
		return timeUnit;
	}
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getWorkitemId() {
		return workitemId;
	}
	public void setWorkitemId(int workitemId) {
		this.workitemId = workitemId;
	}
	public boolean isCompeted() {
		return isCompeted;
	}
	public void setCompeted(boolean isCompeted) {
		this.isCompeted = isCompeted;
	}
	
}
