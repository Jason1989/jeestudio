package com.zxt.compplatform.workflow.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Hibernate;

import com.zxt.compplatform.formengine.entity.view.AuthorityButton;
import com.zxt.compplatform.formengine.entity.view.EventForButton;
import com.zxt.compplatform.formengine.entity.view.JSFunction;
import com.zxt.compplatform.formengine.entity.view.Param;
import com.zxt.compplatform.formengine.util.ButtonXmlUtil;
import com.zxt.compplatform.workflow.Util.WorkConfig;
import com.zxt.compplatform.workflow.Util.WorkFlowUtil;
import com.zxt.compplatform.workflow.dao.WorkflowAuthSettingDao;
import com.zxt.compplatform.workflow.entity.EngWorkflowNodesetting;
import com.zxt.compplatform.workflow.service.WorkflowAuthSettingService;
import com.zxt.framework.common.util.RandomGUID;
import com.zxt.framework.common.util.SQLBlobUtil;

public class WorkflowAuthSettingServiceImpl implements
		WorkflowAuthSettingService {

	private WorkflowAuthSettingDao workflowAuthSetDao;

	public void addAuth(EngWorkflowNodesetting obj) {
		workflowAuthSetDao.create(obj);
	}

	public void delete(String id) {
		EngWorkflowNodesetting setting = new EngWorkflowNodesetting();
		setting.setId(id);
		workflowAuthSetDao.delete(setting);
	}
	
	public boolean deleteByMidNid(String modelId,String nodeId){
		String sql = "delete from ENG_WORKFLOW_NODESETTING where MODEL_ID='"+modelId+"' and MODEL_ID='"+nodeId+"'";
		return workflowAuthSetDao.executeSQLUpdate(sql);
	}
	
	
	public void delete(EngWorkflowNodesetting nodeset){
		workflowAuthSetDao.delete(nodeset);
	}

	public void update(EngWorkflowNodesetting obj) {
		workflowAuthSetDao.update(obj);
	}

	public WorkflowAuthSettingDao getWorkflowAuthSetDao() {
		return workflowAuthSetDao;
	}

	public void setWorkflowAuthSetDao(WorkflowAuthSettingDao workflowAuthSetDao) {
		this.workflowAuthSetDao = workflowAuthSetDao;
	}

	/**
	 * 存储配置
	 * (non-Javadoc)
	 * @see com.zxt.compplatform.workflow.service.WorkflowAuthSettingService#addAuth(java.lang.Object)
	 */
	public void addAuth(Object object) {
		Map settings = (HashMap)object;
		String nodeid = settings.get("nodeid").toString();
		String templateId = settings.get("templateId").toString();
		String modelId = settings.get("modelId").toString();
		List setlist = (List)settings.get("setting");

		//先删除后保存
		List deletelist = workflowAuthSetDao.find("from EngWorkflowNodesetting where nodeId=? and modelId=? ",new Object[]{nodeid,modelId});
		for (int i = 0; i < deletelist.size(); i++) {
			delete((EngWorkflowNodesetting)deletelist.get(i));
		}
		
		for (int i = 0; i < setlist.size(); i++) {//不同用户
			Map userset = (HashMap)setlist.get(i);
			
			
			EngWorkflowNodesetting nodeset = new EngWorkflowNodesetting();
			if(nodeset.getId() == null|| "".equals(nodeset.getId())){
				nodeset.setId(RandomGUID.geneGuid());
			}
			nodeset.setNodeId(nodeid);
			nodeset.setTemplateId(templateId);
			nodeset.setModelId(modelId);
			
			nodeset.setNodeParticipator(userset.get("userid").toString());
			nodeset.setNodeState(userset.get("status").toString());
			
			Set userKeySet = userset.keySet();
			Iterator userKeySetIter = userKeySet.iterator();
			List buttonList = new ArrayList();
			while(userKeySetIter.hasNext()){
				String current = userKeySetIter.next().toString();
				
				if(!"userid".equals(current)&&!"status".equals(current)){//配置函数，edit,delete,view
					AuthorityButton button = new AuthorityButton();
					button.setName(current);
					
					List eventList = new ArrayList();
					
					EventForButton eventButton = new EventForButton();
					eventButton.setType("click");
					eventList.add(eventButton);
					
					button.setEventForButtonList(eventList);
					Map map = null;
					try {
						map = (HashMap)userset.get(current);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					List funcList = new ArrayList();
					JSFunction jsfunction = new JSFunction();
					jsfunction.setName(map.get("funName").toString());
					
					
					
					List list = (List)map.get("rows");
					List paramList = new ArrayList();
					for (int j = 0; j < list.size(); j++) {
						Map paramMap = null;
						Param param = new Param();
						try {
							paramMap = (Map)list.get(j);
							param.setValue(paramMap.get("paramvalue").toString());
							param.setType(paramMap.get("paramtype").toString());
							param.setSortIndex(Integer.parseInt(paramMap.get("order").toString()));
							param.setKey(paramMap.get("paramname").toString());
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						
						paramList.add(param);
					}
					
					jsfunction.setParmers(paramList);
					funcList.add(jsfunction);
					eventButton.setJSFunctionList(funcList);
					
					buttonList.add(button);
					
				}
			}
			String buxml = ButtonXmlUtil.authorityButtonListToxml(buttonList);
			
			nodeset.setNodeAuthSetting(Hibernate.createBlob(buxml.getBytes()));
			//delete(nodeset);// handling transient entity in delete processing 只能删除session存在的实体对象
			addAuth(nodeset);
		} 
		
	}

	/**
	 * 
	 * 查询参与者配置列表
	 *  (non-Javadoc)
	 * @see com.zxt.compplatform.workflow.service.WorkflowAuthSettingService#getPartinerOwnAuthList(java.lang.String, java.lang.String)
	 */
	public List getPartinerOwnAuthList(String modelId, String nodeId) {
		List list =workflowAuthSetDao.find("from EngWorkflowNodesetting t where t.modelId=? and t.nodeId=?  ", new Object[]{modelId,nodeId}); 
		List newarryList = new ArrayList();
		EngWorkflowNodesetting engWorkflow = null;
		for (int j=0 ; j<list.size();j++) {
			EngWorkflowNodesetting engWorkflowNodesetting = (EngWorkflowNodesetting) list.get(j);
			List buttonList = ButtonXmlUtil.xmlToAuthorityButtonList(SQLBlobUtil.blobToString(engWorkflowNodesetting.getNodeAuthSetting()));
			StringBuffer setting = new StringBuffer();
			JSONArray buttons = new JSONArray();
			for (int i = 0 ; i < buttonList.size();i++) {
				AuthorityButton button = (AuthorityButton)buttonList.get(i);
				buttons.add(getButtonSetting(button));
				if(i==(buttonList.size()-1)){
					setting.append(button.getName());
				}else{
					setting.append(button.getName()).append(",");
				}
			}
			//集合各种配置好的按钮
			engWorkflowNodesetting.setButtons(setting.toString());
			//engWorkflowNodesetting.setNodeAuthSetting(buttons.toString());
			//转化成相应的json数据，用于页面的缓存以及数据的保存
			engWorkflowNodesetting.setJsonSetting(buttons.toString());
			newarryList.add(engWorkflowNodesetting);
			
		}
		return newarryList;
	}
	
	/**
	 * 
	 * 将相应的button转化成相应的json字符串
	 * 
	 * @param button
	 * @return
	 */
	public JSONObject getButtonSetting(AuthorityButton button){
		JSONObject reJson = new JSONObject();
		JSONObject jsonButton = new JSONObject();
		String buttonName = button.getName();
		
		List eventButtonlist = button.getEventForButtonList();
		for(int i=0;i<eventButtonlist.size();i++){
			EventForButton eventForButton = (EventForButton)eventButtonlist.get(i);
			String eventTypeString = eventForButton.getType();
			List jsFunctionList = eventForButton.getJSFunctionList();
			for(int j=0;j<jsFunctionList.size();j++){
				JSFunction jsFunction = (JSFunction)jsFunctionList.get(j);
				String jsFunctionName = jsFunction.getName();
				JSONObject setting = new JSONObject();
				setting.put("funName", jsFunctionName);
				List paramList = jsFunction.getParmers();
				JSONArray paramRows = new JSONArray();
				for (int k = 0; k < paramList.size(); k++) {
					Param param = (Param)paramList.get(k);
					JSONObject paramJson = new JSONObject();
					String paramName = param.getKey();
					String paramType = param.getType();
					int paramSotrIndex = param.getSortIndex();
					String paramValue = param.getValue();
					paramJson.put("paramname", paramName);
					paramJson.put("paramtype", paramType);
					paramJson.put("paramvalue", paramValue);
					paramJson.put("order", paramSotrIndex+"");
					
					paramRows.add(paramJson);
				}
				setting.put("rows", paramRows);
				jsonButton.put(buttonName, setting);
				reJson.put("data", jsonButton);
				reJson.put("type",buttonName);
			}
		}
		return reJson;
	}

	/**
	 * worktemplate:工作项id
	 * userid:用户的id（联合worktemplate获取相关的按钮配置）
	 * request：用户的请求（从中获取页面参数）
	 * data:行级数据
	 * (non-Javadoc)
	 * @see com.zxt.compplatform.workflow.service.WorkflowAuthSettingService#findSettingByMoIdNodeIdUserId(int, java.lang.String, javax.servlet.http.HttpServletRequest, java.util.Map)
	 */
	public Map findSettingByMoIdNodeIdUserId(int worktemplate, String userid,HttpServletRequest request,Map data) {
		String[] modelIdAndActivityId = WorkFlowUtil.findMidAndActivtyIdByWorkItemId(worktemplate);
System.out.println("setting : "+modelIdAndActivityId[0]+"   "+modelIdAndActivityId[1]+"  "+modelIdAndActivityId[2]+"  "+userid);
		String[] workUserIdStrings = WorkConfig.workUserIdStrings;
		
		Map returnMap = new HashMap();
		for (int i = 0; i < workUserIdStrings.length; i++) {
			if(workUserIdStrings[i].equals(modelIdAndActivityId[1])){
				returnMap.put("status", WorkConfig.getStatus(workUserIdStrings[i]));
				returnMap.put("buttons",WorkConfig.getJsFunction(data,modelIdAndActivityId[1]));
			}else{
				List list =workflowAuthSetDao.find("from EngWorkflowNodesetting t where t.modelId=? and t.nodeId=? and nodeState=? and t.nodeParticipator=? ", new Object[]{modelIdAndActivityId[0],modelIdAndActivityId[1],modelIdAndActivityId[2],userid});
				
				if(list != null&& list.size() >0 ){
					EngWorkflowNodesetting engWorkflowNodesetting = (EngWorkflowNodesetting) list.get(0);
					List buttonList = ButtonXmlUtil.xmlToAuthorityButtonList(SQLBlobUtil.blobToString(engWorkflowNodesetting.getNodeAuthSetting()));
					returnMap.put("status", engWorkflowNodesetting.getNodeState());
					returnMap.put("buttons",  WorkFlowUtil.transferButtonSettingToString(buttonList,request,data,engWorkflowNodesetting.getNodeState()));
				}
				
			}
		}
		
		return returnMap;
	}
	
	
}
