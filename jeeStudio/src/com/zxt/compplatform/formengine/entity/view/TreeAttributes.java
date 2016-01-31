package com.zxt.compplatform.formengine.entity.view;

/**
 * 树控件属性
 * 
 * @author 007
 */
public class TreeAttributes {
	/**
	 * 列表页启用工作流设置
	 */
	private int isAbleWorkFlow = 0;

	/**
	 * 加载数据地址
	 */
	private String url;

	/**
	 * 工作流过滤器
	 */
	private String workflow_fiter;

	// private String isWorkFlowComPar;
	//	
	// private String isWorkFlowComParId;
	//	
	// private String isWorkFlowComParArray;

	public String getWorkflow_fiter() {
		return workflow_fiter;
	}

	public void setWorkflow_fiter(String workflow_fiter) {
		this.workflow_fiter = workflow_fiter;
	}

	public int getIsAbleWorkFlow() {
		return isAbleWorkFlow;
	}

	public void setIsAbleWorkFlow(int isAbleWorkFlow) {
		this.isAbleWorkFlow = isAbleWorkFlow;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// public String getIsWorkFlowComPar() {
	// return isWorkFlowComPar;
	// }
	//
	// public void setIsWorkFlowComPar(String isWorkFlowComPar) {
	// this.isWorkFlowComPar = isWorkFlowComPar;
	// }
	//
	// public String getIsWorkFlowComParId() {
	// return isWorkFlowComParId;
	// }
	//
	// public void setIsWorkFlowComParId(String isWorkFlowComParId) {
	// this.isWorkFlowComParId = isWorkFlowComParId;
	// }
	//
	// public String getIsWorkFlowComParArray() {
	// return isWorkFlowComParArray;
	// }
	//
	// public void setIsWorkFlowComParArray(String isWorkFlowComParArray) {
	// this.isWorkFlowComParArray = isWorkFlowComParArray;
	// }

}
