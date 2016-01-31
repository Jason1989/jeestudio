package com.zxt.compplatform.workflow.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.synchrobit.synchroflow.api.util.RMIManager;
import com.synchrobit.synchroflow.enactmentservice.exception.OperateException;
import com.synchrobit.synchroflow.enactmentservice.processmonitor.ProcessMonitor;
import com.zxt.compplatform.formengine.util.TreeUtil;
import com.zxt.compplatform.workflow.Util.WorkFlowUtil;
import com.zxt.compplatform.workflow.dao.impl.ChuangjianWorkFlowDaoImpl;
import com.zxt.compplatform.workflow.entity.TaskFormNodeEntity;
import com.zxt.compplatform.workflow.service.WorkFlowFrameService;
import com.zxt.framework.common.util.RandomGUID;

/**
 * 
 * @author hgw
 * 
 */
public class WorkFlowFrameAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WorkFlowFrameService workFlowFrameService;
	public String list() throws Exception {
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		int page = 1;
		if(req.getParameter("page") != null && !req.getParameter("page").equals("")){
			page = Integer.parseInt(req.getParameter("page"));
		}
		int rows = 0;
		if(req.getParameter("rows") != null && !req.getParameter("rows").equals("")){
			rows = Integer.parseInt(req.getParameter("rows"));
		}
		int totalRows = workFlowFrameService.findTotalRows();
		List list = workFlowFrameService.findTaskFormNodeEntity();
		Map map = new HashMap();
		if(list == null){
			list = new ArrayList();
		}
		map.put("rows", list);
		map.put("total", new Integer(list.size()));
		String json = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 获取模板列表
	 * @return
	 * @throws Exception
	 */
	public String comlist() throws Exception {
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		
		//List list = workFlowFrameService.findTaskFormNodeEntity();
		List list = WorkFlowUtil.findAllModelFromSyn();
		
		String json = JSONArray.fromObject(list).toString();
		try {
			res.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String toAdd(){
		return "wf_form_add";
	}

	public List nodelist(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String processInstanceID = req.getParameter("processInstanceID");
		List list = workFlowFrameService.getWorkflowModelNodeListByPId(processInstanceID);
		Map map = new HashMap();
		if(list == null){
			list = new ArrayList();
		}
		for(int i = 0;i<list.size();i++){
			TaskFormNodeEntity tfne = (TaskFormNodeEntity)list.get(i) ;
			if("1".equals(tfne.getIsShowTab())){
				tfne.setIsShowTab("是");
			}else if("0".equals(tfne.getIsShowTab())){
				tfne.setIsShowTab("否");
			}
		}
		map.put("rows", pagger(req,list));
		map.put("total", list.size()+"");
		String json = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取模板节点列表
	 * 访问地址:workflow/WorkFlowFrame_nodelistOfProcess.action
	 * @return
	 */
	public List nodelistOfProcess(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String processInstanceID = req.getParameter("processInstanceID");
		String modelId = req.getParameter("ModelID");
		List list =  null;
		try{
			int processId = 0;
			if(processInstanceID != null && !"".equals(processInstanceID)){
				processId = Integer.parseInt(processInstanceID);
			}
			int modeliD = 0;
			if(modelId != null && !"".equals(modelId)){
				modeliD = Integer.parseInt(modelId);
			}
			//list = workFlowFrameService.getWFModelNodeListByPId(processInstanceID);
			list = WorkFlowUtil.findActivityDef(processId, modeliD);
		}catch(Exception  e){
			e.printStackTrace();	
		}
		Map map = new HashMap();
		map.put("rows", list);
		map.put("total", list.size()+"");
		String json = JSONObject.fromObject(map).toString();
		try {
			res.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String tabPageList(){
		HttpServletResponse res = ServletActionContext.getResponse();
		res.setContentType("text/x-json;charset=UTF-8 ");
		List list = workFlowFrameService.getWorkFlowTabPage();
		List mylist = TreeUtil.treeAlgorithm(list, "-1");
		String json = JSONArray.fromObject(mylist).toString();
		try {
			res.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String toEdit(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String processInstanceID = req.getParameter("processInstanceID");
		req.setAttribute("processInstanceID", processInstanceID);
		return "wf_form_edit";
	}
	public String toForm(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String processInstanceID = req.getParameter("processInstanceID");
		String taskNodeId = req.getParameter("taskNodeId");
		String tfId = req.getParameter("tfId");
		TaskFormNodeEntity taskFormNodeEntity  = workFlowFrameService.findById(tfId);
		req.setAttribute("processInstanceID", processInstanceID);
		req.setAttribute("taskNodeId", taskNodeId);
		req.setAttribute("tfId", tfId);
		req.setAttribute("taskFormNodeEntity", taskFormNodeEntity);
		return "wf_form_form";
	}
	public String toFormAdd(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String processInstanceID = req.getParameter("processInstanceID");
		String taskNodeId = req.getParameter("taskNodeId");
		req.setAttribute("processInstanceID", processInstanceID);
		req.setAttribute("taskNodeId", taskNodeId);
		return "wf_form_formAdd";
	}
	public String add(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		String formId = req.getParameter("formId");
		String workflowId = req.getParameter("workflowId");
		String nodeId = req.getParameter("nodeId");
		if(formId != null && !formId.equals("")){
			TaskFormNodeEntity tfne = new TaskFormNodeEntity();
			tfne.setFormID(formId);
			tfne.setProcessInstanceID(workflowId);
			tfne.setTaskNodeID(nodeId);
			tfne.setTaskFormID(RandomGUID.geneGuid());
			workFlowFrameService.insertTaskFormNodeEntity(tfne);
			try {
				res.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public String updateForm(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		String tfId = req.getParameter("tfId");
		String formId = req.getParameter("formId");
		String workflowId = req.getParameter("workflowId");
		String nodeId = req.getParameter("nodeId");
		String isShowTab = req.getParameter("isShowTab");
		String sortIndex = req.getParameter("sortIndex");
		if(formId != null && !formId.equals("")){
			TaskFormNodeEntity tfne = new TaskFormNodeEntity();
			tfne.setTfId(tfId);
			tfne.setFormID(formId);
			tfne.setProcessInstanceID(workflowId);
			tfne.setTaskNodeID(nodeId);
			tfne.setIsShowTab(isShowTab);
			tfne.setSortIndex(sortIndex);
			workFlowFrameService.updateTaskFormNodeEntityT(tfne);
			try {
				res.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public String addForm(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		String formId = req.getParameter("formId");
		String isShowTab = req.getParameter("isShowTab");
		String sortIndex = req.getParameter("sortIndex");
		String workflowId = req.getParameter("workflowId");
		String nodeId = req.getParameter("nodeId");
		if(formId != null && !formId.equals("")){
			TaskFormNodeEntity tfne = new TaskFormNodeEntity();
			tfne.setFormID(formId);
			tfne.setProcessInstanceID(workflowId);
			tfne.setTaskNodeID(nodeId);
			tfne.setTaskFormID(RandomGUID.geneGuid());
			tfne.setSortIndex(sortIndex);
			tfne.setIsShowTab(isShowTab);
			workFlowFrameService.insertTaskFormNodeEntity(tfne);
			try {
				res.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public String toUpdate(){
		HttpServletRequest req = ServletActionContext.getRequest();
		String taskFormID = req.getParameter("taskFormID");
		TaskFormNodeEntity taskFormNode = workFlowFrameService.findById(taskFormID);
		if(taskFormNode != null){
			req.setAttribute("processInstanceID", taskFormNode.getProcessInstanceID());
			req.setAttribute("formId", taskFormNode.getFormID());
			req.setAttribute("nodeId", taskFormNode.getTaskNodeID());
			req.setAttribute("taskFormId", taskFormNode.getTaskFormID());
		}
		return "wf_form_update";
	}
	public String update(){
		HttpServletResponse res = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		String taskFormId = req.getParameter("taskFormId");
		String formId = req.getParameter("formId");
		String workflowId = req.getParameter("workflowId");
		String nodeId = req.getParameter("nodeId");
		if(formId != null && !formId.equals("")){
			TaskFormNodeEntity tfne = new TaskFormNodeEntity();
			tfne.setFormID(formId);
			tfne.setProcessInstanceID(workflowId);
			tfne.setTaskNodeID(nodeId);
			tfne.setTaskFormID(taskFormId);
			workFlowFrameService.updateTaskFormNodeEntity(tfne);
			try {
				res.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public String delete(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String taskFormId = req.getParameter("taskFormID");
		workFlowFrameService.deleteTaskFormNodeEntity(taskFormId);
		try {
			res.getWriter().write("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String saveServiceParmer(){
		
		return null;
	}

	public WorkFlowFrameService getWorkFlowFrameService() {
		return workFlowFrameService;
	}

	public void setWorkFlowFrameService(WorkFlowFrameService workFlowFrameService) {
		this.workFlowFrameService = workFlowFrameService;
	}
	public List pagger(HttpServletRequest request, List data) {
		List temList = null;
		try {
			temList = new ArrayList();

			/**
			 * jquery方式
			 */
			 int pageNum = Integer.parseInt(request.getParameter("page"));
			 int size = Integer.parseInt(request.getParameter("rows"));
			 int end = pageNum * size;
			 int start = end - size;
			 
//			int size = Integer.parseInt(Request().getParameter("limit"));
//			int start = Integer.parseInt(Request().getParameter("start"));
//			int end = start + size;

			/**
			 * ExtJs方式
			 */
			int maxSize = data.size();
			if (end > maxSize) {
				end = maxSize;
			}
			if (start < 0) {
				start = 0;
			}
			for (int i = start; i < end; i++) {
				temList.add(data.get(i));
			}
		} catch (Exception e) {
			// TODO: handle exception
			temList = data;
		}
		return temList;
	}
	public String toImage(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String taskFormID = req.getParameter("taskFormID");
		String path = req.getRealPath("");
		TaskFormNodeEntity taskFormNode = workFlowFrameService.findById(taskFormID);
		String prodefId="";
		if(taskFormNode != null){
			prodefId=taskFormNode.getProcessInstanceID();
		}
		
		ChuangjianWorkFlowDaoImpl c= new ChuangjianWorkFlowDaoImpl();
		String parmer[][]=new String[3][2];
		String proInId=c.succBackProInId(prodefId,0, parmer);
	
		ProcessMonitor mPM;
		try {
			mPM = RMIManager.getInstance()
			.getPmRmiConnection();
			byte[] bytes = mPM.dynamicMonitor(Integer.parseInt(proInId),"0");
		    String newFileName = path+"\\pages\\workflow\\img\\flow.jpg";
	        FileImageOutputStream imageOutput = new FileImageOutputStream(new File(newFileName));
	        imageOutput.write(bytes, 0, bytes.length);
	        imageOutput.close();
	        res.getWriter().write("success");

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OperateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
