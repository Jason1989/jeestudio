package com.zxt.compplatform.workflow.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.formengine.util.StrTools;
import com.zxt.compplatform.workflow.Util.WorkFlowUtil;
import com.zxt.compplatform.workflow.entity.TaskFormNodeEntity;
import com.zxt.compplatform.workflow.entity.WorkFlowDataStauts;
import com.zxt.compplatform.workflow.service.LogWorkFlowService;
import com.zxt.compplatform.workflow.service.WorkFlowFrameService;

public class LogWorkFlowAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private LogWorkFlowService logWorkFlowService;
	private WorkFlowFrameService workFlowFrameService;

	/**
	 * 工作流日志添加
	 */
	public String workFlowLogAdd() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8");
		//获取当前用户
		String userId = (String)request.getSession().getAttribute("userId");
		//获取业务主键
		String app_id = request.getParameter("APP_ID");
		//获取formID
		String formID = request.getParameter("formID");
		//截取formID
		formID = formID.substring(formID.indexOf("_")+1,formID.length());
		//获取保存类型
		String savetype = request.getParameter("type");
		
		//定义流程id
		String processDefId = request.getParameter("processDefId");
		
		//获取工作项id
		String workitemId= request.getParameter("workitemId");
		
		//条件
		String condition = request.getParameter("condition");
		
		TaskFormNodeEntity taskFormNodeEntity = workFlowFrameService
		.findTaskFormNodeEntity(formID);
		
		WorkFlowDataStauts workflowDataStauts = new WorkFlowDataStauts();
		workflowDataStauts = WorkFlowUtil.findInitActivity(taskFormNodeEntity.getProcessInstanceID(),savetype);
		logWorkFlowService.addWorkFlowLog(userId,app_id,workflowDataStauts,processDefId,workitemId);
		return null;
	}
	
	/**
	 * 工作流日志添加
	 */
	public String workFlowLogAddETC() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8");
		
		//获取mid
		String mid = request.getParameter("mid");
		//获取当前用户
		String userId = (String)request.getSession().getAttribute("userId");
		//获取前驱状态
		String pioneer_status = request.getParameter("status_text");
		pioneer_status = StrTools.charsetFormat(pioneer_status,"ISO8859-1", "UTF-8");
		//获取前驱操作
		String pioneer_operate = request.getParameter("toTransferDefStautsText");
		pioneer_operate = StrTools.charsetFormat(pioneer_operate,"ISO8859-1", "UTF-8");
		//获取业务主键
		String app_id = request.getParameter("MAIN_APP_ID");
		
		//定义流程id
		String processDefId = request.getParameter("processDefId");
		
		//获取工作项id
		String workitemId= request.getParameter("workitemId");
		
		//条件
		String condition = request.getParameter("condition");
		logWorkFlowService.addWorkFlowLogETC(mid,userId,pioneer_status,pioneer_operate,app_id,processDefId,workitemId);
		return null;
	}

	/**
	 * 跳转到工作流日志页面
	 */
	public String toWorkFlowDetail(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8");
		
		String APP_ID = request.getParameter("APP_ID");
		request.setAttribute("APP_ID", APP_ID);
		return "toWorkFlowLog";
	}
	
	/**
	 * 工作流列表页
	 */
	public String workFlowDetails() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/plain;charset=UTF-8");
		
		String app_id = request.getParameter("APP_ID");
		List list = logWorkFlowService.findWorkFlowLogByAppID(app_id);
		Map map = new HashMap();
		if(list !=null){
			map.put("rows", list);
			map.put("total", new Integer(list.size()));
		}
		String dataJson = JSONObject.fromObject(map).toString();
		response.getWriter().write(dataJson);
		return null;
	}
	public LogWorkFlowService getLogWorkFlowService() {
		return logWorkFlowService;
	}

	public void setLogWorkFlowService(LogWorkFlowService logWorkFlowService) {
		this.logWorkFlowService = logWorkFlowService;
	}

	public WorkFlowFrameService getWorkFlowFrameService() {
		return workFlowFrameService;
	}

	public void setWorkFlowFrameService(WorkFlowFrameService workFlowFrameService) {
		this.workFlowFrameService = workFlowFrameService;
	}
	
}
