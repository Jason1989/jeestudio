package com.zxt.compplatform.workflow.entity;

public class Model {
	/**
	 * 工作流模型对象信息实体
	 * 
	 * @author zxt-wudehui
	 */
	private int modelid;// 模型编号(模型定义编号）
	private int processId;// 过程定义编号
	private int modelFlag;// 模型修改标志
	private String modelName;// 模型名
	private String path;// 模型文件路径
	private String processDesc;// 过程注释
	private String processName;// 过程名

	public Model() {
	}

	public Model(int modelid, int processId) {
		super();
		this.modelid = modelid;
		this.processId = processId;
	}

	public int getModelid() {
		return modelid;
	}

	public void setModelid(int modelid) {
		this.modelid = modelid;
	}

	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public int getModelFlag() {
		return modelFlag;
	}

	public void setModelFlag(int modelFlag) {
		this.modelFlag = modelFlag;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getProcessDesc() {
		return processDesc;
	}

	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

}
