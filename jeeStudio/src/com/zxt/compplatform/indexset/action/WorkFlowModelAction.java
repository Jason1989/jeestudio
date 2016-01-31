package com.zxt.compplatform.indexset.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.indexset.service.WorkFlowModelService;

/**
 * 工作流相关模块
 * 
 * @author Dexpo 从工作流查已办代办项
 */
public class WorkFlowModelAction extends ActionSupport {
	private WorkFlowModelService workservice;

	public String getWorkTaskData() {
		//获取传递的参数,过滤条件
		HttpServletRequest request = ServletActionContext.getRequest();
		String workflow_fiter = request.getParameter("workflow_fiter");
		Object o = request.getSession().getAttribute("userId");
		//测试，默认值
		String tablename = "LMS_TASK";
		String dataSourceId="0a83565f28b013048632ca023d812125";
		
		//用户名非空时,查询工作流
		List workTask =null;
		if (o != null) {
			String userId = o.toString();
			workTask = workservice.getWorkTask(dataSourceId,userId, tablename, workflow_fiter);
		}
		//结果转化json
		String json=null;
		if (workTask != null) {
			Map data = new HashMap();
			data.put("total", new Integer(workTask.size()));
			data.put("rows", workTask);
			json = JSONObject.fromObject(data).toString();
		} else {
			json = "{total:0,rows:[]}";
		}
		//输出结果
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public WorkFlowModelService getWorkservice() {
		return workservice;
	}

	public void setWorkservice(WorkFlowModelService workservice) {
		this.workservice = workservice;
	}
	
	
}
