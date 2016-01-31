package com.zxt.compplatform.workflow.entity;

public class WorkFlowDataStauts {
	/**
	 * 流程定义ID
	 */
	private String processDefId;
	/**
	 * 模型主键
	 */
	private String mid;
	/**
	 * 活动定义Id
	 */
	private String activityDefId;
	/**
	 * 前驱状态显示文本(动名词)
	 */
	private String toTransferDefStautsText;
	/**
	 * 前驱状态显示文本(名动词)
	 */
	private String toTransferDefStauts_text;
	/**
	 * 前驱状态参数值
	 */
	private String toTransferDefStautsValue;
	/**
	 * 上一个工作项
	 */
	private String preWorkItemId;
	/**
	 * 分支条件
	 */
	private String condition;

	/**
	 * 把参数拼接成url形式
	 */
	private String stitchingParameter;

	public String getToTransferDefStauts_text() {
		return toTransferDefStauts_text;
	}

	public void setToTransferDefStauts_text(String toTransferDefStauts_text) {
		this.toTransferDefStauts_text = toTransferDefStauts_text;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getPreWorkItemId() {
		return preWorkItemId;
	}

	public void setPreWorkItemId(String preWorkItemId) {
		this.preWorkItemId = preWorkItemId;
	}

	public String getToTransferDefStautsValue() {
		return toTransferDefStautsValue;
	}

	public void setToTransferDefStautsValue(String toTransferDefStautsValue) {
		this.toTransferDefStautsValue = toTransferDefStautsValue;
	}

	public String getProcessDefId() {
		return processDefId;
	}

	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getActivityDefId() {
		return activityDefId;
	}

	public void setActivityDefId(String activityDefId) {
		this.activityDefId = activityDefId;
	}

	public String getToTransferDefStautsText() {
		return toTransferDefStautsText;
	}

	public void setToTransferDefStautsText(String toTransferDefStautsText) {
		this.toTransferDefStautsText = toTransferDefStautsText;
	}

	public String getStitchingParameter() {
		return stitchingParameter;
	}

	public void setStitchingParameter(String stitchingParameter) {
		this.stitchingParameter = stitchingParameter;
	}
}
