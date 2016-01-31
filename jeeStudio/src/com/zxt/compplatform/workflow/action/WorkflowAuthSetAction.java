package com.zxt.compplatform.workflow.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.googlecode.jsonplugin.JSONException;
import com.googlecode.jsonplugin.JSONUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.zxt.compplatform.workflow.Util.WorkFlowUtil;
import com.zxt.compplatform.workflow.service.WorkflowAuthSettingService;

public class WorkflowAuthSetAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	
	private WorkflowAuthSettingService workflowAuthSettingService;

	
	/**
	 * 功能：保存权限配置
	 * 访问地址：/workflow/nodeAuthSet!add.action
	 * @return
	 */
	public String add(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		PrintWriter out = null;
		String flag = "fail";
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		
		//获取相关的配置信息
		//解析配置信息
		String setting = request.getParameter("setting");
		//System.out.println(setting);
		try {
			Object object = JSONUtil.deserialize(setting);
			workflowAuthSettingService.addAuth(object);
			/*Map settings = (HashMap)object;
			String nodeid = settings.get("nodeid").toString();
			List setlist = (List)settings.get("setting");
			for (int i = 0; i < setlist.size(); i++) {//不同用户
				Map userset = (HashMap)setlist.get(i);
				
				EngWorkflowNodesetting nodeset = new EngWorkflowNodesetting();
				if(nodeset.getId() == null|| "".equals(nodeset.getId())){
					nodeset.setId(RandomGUID.geneGuid());
				}
				nodeset.setNodeId(nodeid);
				
				Set userKeySet = userset.keySet();
				Iterator userKeySetIter = userKeySet.iterator();
				StringBuffer buffer = new StringBuffer("<functions>");
				while(userKeySetIter.hasNext()){
					String current = userKeySetIter.next().toString();
					if("userid".equals(current)){//用户
						nodeset.setNodeParticipator(userset.get("userid").toString());
					}else{//配置函数
						buffer.append("<function>").append("<authType>").append(current).append("</authType>");
						Map map = (HashMap)userset.get(current);
						buffer.append("<funcName>").append(map.get("funName")).append("<funcName>");
						List list = (List)map.get("rows");
						buffer.append("<params>");
						for (int j = 0; j < list.size(); j++) {
							Map paramMap = (Map)list.get(i);
							buffer.append("<param>").append("<paramName>").append(paramMap.get("paramname")).append("</paramName>")
							.append("<paramType>").append(paramMap.get("paramtype")).append("</paramType>")
							.append("<paramValue>").append(paramMap.get("paramvalue")).append("</paramValue>")
							.append("<paramOrder>").append(paramMap.get("order")).append("</paramOrder>")
							.append("</param>");
						}
						buffer.append("</params></function>");
					}
				}
				buffer.append("</functions>");
				nodeset.setNodeAuthSetting(buffer.toString());
			} */
			flag = "success";
		} catch (JSONException e) {
			e.printStackTrace();
			flag = "fail";
		}
		out.print(flag);
		return null;
	}
	
	
	/**
	 * 获取某个流程定义的版本号列表
	 * 访问地址:/workflow/nodeAuthSet!getTemplateHis.action
	 * @return
	 */
	public String getTemplateHis(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String processId = request.getParameter("processInstanceID");
			
			int processID = 0;
			
			processID = Integer.parseInt(processId);
			
			JSONArray array = JSONArray.fromObject(WorkFlowUtil.findModelListByProcessId(processID));
			out.print(array.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
		
		
		return null;
	}
	
	
	/**
	 * 获取前驱线的状态
	 * 访问地址：/workflow/nodeAuthSet!getPreLineStatus.action
	 * @return
	 */
	public String getPreLineStatus(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		String processInstanceID = req.getParameter("processInstanceID");
		String modelId = req.getParameter("modelId");
		String nodeid = req.getParameter("nodeid");
		List list =  null;
		try{
			int processId = 0;
			if(processInstanceID != null && !"".equals(processInstanceID)){
				processId = Integer.parseInt(processInstanceID);
			}
			int mid = 0;
			if(modelId != null && !"".equals(modelId)){
				mid = Integer.parseInt(modelId);
			}
			int nodeKey = 0 ;
			if(nodeid != null && !"".equals(nodeid)){
				nodeKey = Integer.parseInt(nodeid);
			}
			//list = workFlowFrameService.getWFModelNodeListByPId(processInstanceID);
			list = WorkFlowUtil.findAllTransfer(processId, mid, nodeKey);
		}catch(Exception  e){
			e.printStackTrace();	
		}
		
		try {
			String json = JSONArray.fromObject(list).toString();
			res.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获得节点参与者,同时将相关的以配置的权限查询出来
	 * 访问地址:workflow/nodeAuthSet!getNodeParticipants.action
	 * @return
	 */
	public String getNodeParticipants(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		
		PrintWriter out = null;
		try {
			out = res.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String nodeidString = req.getParameter("nodeId");
		String modelIdString = req.getParameter("modelId");
		
		int nodeid = 0 ;
		if(nodeidString != null && !"".equals(nodeidString)){
			nodeid = Integer.parseInt(nodeidString);
		}
		int modelid = 0;
		if(modelIdString != null && !"".equals(modelIdString)){
			modelid = Integer.parseInt(modelIdString);
		}
		List list = WorkFlowUtil.backAllParticipantByactivityDefId(nodeid,modelid);
		
		Map map = new HashMap();
		map.put("rows", list);
		map.put("total", list.size()+"");
		
		JSONObject array = JSONObject.fromObject(map);
		out.print(array.toString());
		out.close();
		
		return null;
	}
	
	/**
	 * 获取去node权限配置列表
	 * 访问地址：/workflow/nodeAuthSet!getNodeSetting.action
	 * @return
	 */
	public String getNodeSetting(){
		HttpServletRequest req = ServletActionContext.getRequest();
		HttpServletResponse res = ServletActionContext.getResponse();
		
		PrintWriter out = null;
		try {
			out = res.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String nodeidString = req.getParameter("nodeId");
		String modelIdString = req.getParameter("modelId");
		
		int nodeid = 0 ;
		if(nodeidString != null && !"".equals(nodeidString)){
			nodeid = Integer.parseInt(nodeidString);
		}
		int modelid = 0;
		if(modelIdString != null && !"".equals(modelIdString)){
			modelid = Integer.parseInt(modelIdString);
		}
		List listssList = workflowAuthSettingService.getPartinerOwnAuthList(modelIdString,nodeidString);
		
		Map reMap = new HashMap();
		reMap.put("rows", listssList);
		reMap.put("total", listssList.size()+"");
		JSONObject array = JSONObject.fromObject(reMap);

		out.print(array.toString());
		out.close();
		return null;
	} 
	
	public String delete(){
		return null;
	}
	
	public String update(){
		return null;
	}
	
	public WorkflowAuthSettingService getWorkflowAuthSettingService() {
		return workflowAuthSettingService;
	}

	public void setWorkflowAuthSettingService(
			WorkflowAuthSettingService workflowAuthSettingService) {
		this.workflowAuthSettingService = workflowAuthSettingService;
	}

}
