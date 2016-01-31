package com.zxt.compplatform.formengine.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.formengine.service.WorkItemInfoService;

/**
 * 工作项Action
 * @author 007
 */
public class WorkItemInfoAction extends ActionSupport {
	private static final Logger log = Logger.getLogger(ComponentsAction.class);
	/**
	 * 工作项业务操作接口
	 */
	private WorkItemInfoService workItemInfoService;

	/**
	 * 工作项列表
	 */
	private List workItemFormList;
	/**
	 * 工作项id
	 */
	private String workitemId;

	/**
	 * 展示工作项信息
	 * @return
	 */
	public String showWorkItemInfo() {
		workitemId = ServletActionContext.getRequest().getParameter(
				"workitemId");
		// APP_ID=workItemInfoService.findAppId(workitemId);
		String APP_ID = findRequest().getParameter("APP_ID");

		workItemFormList = workItemInfoService.findWorkFormList(workitemId);
		findRequest().setAttribute("APP_ID", APP_ID);
		return "workItemInfo";
	}

	/**
	 * 查看所有的工作项
	 * @return
	 */
	public String workItemViewAll() {
		log.info("excute...");
		return "workItemViewAll";
	}

	public WorkItemInfoService getWorkItemInfoService() {
		return workItemInfoService;
	}

	public void setWorkItemInfoService(WorkItemInfoService workItemInfoService) {
		this.workItemInfoService = workItemInfoService;
	}

	public List getWorkItemFormList() {
		return workItemFormList;
	}

	public void setWorkItemFormList(List workItemFormList) {
		this.workItemFormList = workItemFormList;
	}

	public String getWorkitemId() {
		return workitemId;
	}

	public void setWorkitemId(String workitemId) {
		this.workitemId = workitemId;
	}

	public HttpServletRequest findRequest() {
		return ServletActionContext.getRequest();
	}
}