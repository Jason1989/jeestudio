package com.zxt.compplatform.formengine.action;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 查询操作Action
 * 
 * @author 007
 */
public class QueryPageAction extends ActionSupport {
	
	private static final Logger log = Logger.getLogger(ComponentsAction.class);
	/**
	 * json字符串
	 */
	private String json;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() throws Exception {
		return null;
	}

	/**
	 * 保存编辑页业务数据
	 * 
	 * @return
	 */
	public String dynamicSave() {

		return null;
	}

	/**
	 * 刷新文件列表
	 * 
	 * @return
	 */
	public String refreshFileList() {

		json = "";
		return null;
	}

	/**
	 * 工作流查看页
	 * 
	 * @return
	 */
	public String workFlowView() {

		return "workflowView";
	}

	/**
	 * 返回json数据
	 * 
	 * @return
	 */
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

}
